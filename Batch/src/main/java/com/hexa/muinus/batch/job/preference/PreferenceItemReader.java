package com.hexa.muinus.batch.job.preference;

import com.hexa.muinus.batch.domain.Preference;
import com.hexa.muinus.batch.domain.PreferenceId;
import com.hexa.muinus.batch.exeption.BatchErrorCode;
import com.hexa.muinus.batch.exeption.BatchProcessingException;
import org.springframework.batch.item.ItemReader;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

@Component
public class PreferenceItemReader implements ItemReader<Preference> {

    // MySQL 구매 횟수 + 기존 월간(29일치) 점수 조회
    private final static String mySqlQuery = """
                SELECT  ui.user_no, ui.item_id,
                        IFNULL(SUM(td.quantity), 0) AS daily_purchase_count,
                        IFNULL(SUM(up.monthly_score), 0) AS monthly_score 
                FROM (SELECT u.user_no, i.item_id FROM users u CROSS JOIN item i) AS ui 
                LEFT JOIN hexa.transactions t ON t.user_no = ui.user_no
                AND t.created_at >= CURDATE() - INTERVAL 1 DAY
                AND t.created_at < CURDATE()                     
                LEFT JOIN hexa.transaction_details td ON td.transaction_id = t.transaction_id
                AND td.store_item_id IS NOT NULL                
                LEFT JOIN ( SELECT p.user_no, p.item_id, SUM(p.daily_score) AS monthly_score
                            FROM preference p
                            WHERE p.updated_at >= CURDATE() - INTERVAL 30 DAY
                            AND p.updated_at < CURDATE()            
                            GROUP BY p.user_no, p.item_id ) up ON up.user_no = ui.user_no
                AND up.item_id = ui.item_id
                GROUP BY ui.user_no, ui.item_id
                ORDER BY ui.user_no, ui.item_id
            """;

        private final RedisTemplate<String, Object> redisTemplate;
        private final JdbcTemplate jdbcTemplate;
        private final Iterator<Map.Entry<String, Integer>> redisDataIterator;

        public PreferenceItemReader(RedisTemplate<String, Object> redisTemplate, JdbcTemplate jdbcTemplate) {
            this.redisTemplate = redisTemplate;
            this.jdbcTemplate = jdbcTemplate;

            // Redis 데이터 로드 (userNo:itemId)
            HashOperations<String, String, Integer> hashOps = redisTemplate.opsForHash();
            String redisKey = "SEARCH_COUNT";
            Map<String, Integer> redisData = hashOps.entries(redisKey);

            this.redisDataIterator = redisData.entrySet().iterator();
        }

        @Override
        public Preference read() {
            if (!redisDataIterator.hasNext()) return null;

            Map.Entry<String, Integer> entry = redisDataIterator.next();
            String key = entry.getKey();
            int searchCount = entry.getValue() != null ? entry.getValue() : 0;

            try {
                String[] parts = key.split(":");
                Long userNo = Long.parseLong(parts[0]);
                Long itemId = Long.parseLong(parts[1]);

                Map<String, Object> result = jdbcTemplate.queryForMap(mySqlQuery);
                int purchaseCount = ((Number) result.get("daily_purchase_count")).intValue();
                BigDecimal monthlyScore = (BigDecimal) result.get("monthly_score");

                // 점수 계산
                BigDecimal dailyScore = BigDecimal.valueOf(searchCount + purchaseCount);
                BigDecimal updatedMonthlyScore = monthlyScore.add(dailyScore);

                LocalDate yesterday = LocalDate.now().minusDays(1);
                PreferenceId id = new PreferenceId(userNo, itemId, yesterday);
                return Preference.builder()
                        .id(id)
                        .dailyScore(dailyScore)
                        .monthlyScore(updatedMonthlyScore)
                        .build();

            } catch (Exception e) {
                throw new BatchProcessingException(BatchErrorCode.ROW_MAPPER_ERROR, e);
            }
        }
    }
