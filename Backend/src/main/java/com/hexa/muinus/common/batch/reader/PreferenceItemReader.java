package com.hexa.muinus.common.batch.reader;

import com.hexa.muinus.common.batch.exeption.BatchErrorCode;
import com.hexa.muinus.common.batch.exeption.BatchProcessingException;
import com.hexa.muinus.users.domain.preference.Preference;
import com.hexa.muinus.users.domain.preference.PreferenceId;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

@Slf4j
@Component
public class PreferenceItemReader implements ItemReader<Preference> {

    private final JdbcTemplate jdbcTemplate;
    private final RestTemplate restTemplate;

    private Iterator<Preference> preferenceIterator;

    @Autowired
    public PreferenceItemReader(@Qualifier("dataDBSource") DataSource dataDBSource, RestTemplate restTemplate) {
        this.jdbcTemplate = new JdbcTemplate(dataDBSource);
        this.restTemplate = restTemplate;
        List<Preference> preferences = fetchPreferencesFromAPI(jdbcTemplate, restTemplate);
        this.preferenceIterator = preferences.iterator();
    }

    @Override
    public Preference read() {
        return preferenceIterator.hasNext() ? preferenceIterator.next() : null;
    }

    // ITEM READ
    /**
     * 선호도 배치 작업 (매일)
     * FOR EACH (userNo, itemNo)
     * @param jdbcTemplate
     * @param restTemplate
     * @return
     */
    private List<Preference> fetchPreferencesFromAPI(JdbcTemplate jdbcTemplate, RestTemplate restTemplate) {
        // dataDB에서 필요 data 읽어오기
        List<Integer> userNos = jdbcTemplate.query("SELECT user_no FROM users ORDER BY user_no", (rs, rowNum) -> rs.getInt("user_no"));
        List<Integer> itemNos = jdbcTemplate.query("SELECT item_id FROM item ORDER BY item_id", (rs, rowNum) -> rs.getInt("item_id"));

        // key type : int -> long 변경
        List<Long> users = userNos.stream().map(Long::valueOf).toList();
        List<Long> items = itemNos.stream().map(Long::valueOf).toList();

        log.debug("FOR BATCH API 호출 users : {} ", users);
        log.debug("FOR BATCH API 호출 items : {} ", items);

        List<Preference> preferences = new ArrayList<>();
        LocalDate today = LocalDate.now();

        // score 호출하기
        try {
            log.debug("PREFERENCE BATCH ITEM API 호출");
            ResponseEntity<List<Map<Long, Map<Long, BigDecimal>>>> response =
                    restTemplate.exchange(
                            "http://localhost:8090/api/batch/preferences",
                            HttpMethod.POST,
                            new HttpEntity<>(Map.of("users", users, "items", items)),
                            new ParameterizedTypeReference<List<Map<Long, Map<Long, BigDecimal>>>>() {
                            }
                    );

            log.debug("PREFERENCE BATCH API 호출 결과{}", response.getBody());
            if(response.getBody() == null){
                throw new BatchProcessingException(BatchErrorCode.API_CALL_FAILED);
            }
    
            // 받아온 데이터 변환
            for (Map<Long, Map<Long, BigDecimal>> userMap : response.getBody()) {
                for (Map.Entry<Long, Map<Long, BigDecimal>> userEntry : userMap.entrySet()) {
                    Long userNo = userEntry.getKey();
                    for (Map.Entry<Long, BigDecimal> itemEntry : userEntry.getValue().entrySet()) {
                        PreferenceId id = new PreferenceId(userNo, itemEntry.getKey());
                        preferences.add(new Preference(id, itemEntry.getValue(), today));
                    }
                }
            }
        } catch (HttpClientErrorException e) {
            log.error("API 요청 실패 (HTTP 오류): StatusCode={}, Message={}", e.getStatusCode(), e.getMessage(), e);
            throw new BatchProcessingException(BatchErrorCode.API_CALL_FAILED, e);
        } catch (ResourceAccessException e) {
            log.error("API 요청 실패 (네트워크 오류): {}", e.getMessage(), e);
            throw new BatchProcessingException(BatchErrorCode.API_CALL_FAILED, e);
        } catch (Exception e) {
            log.error("알 수 없는 오류 발생: {}", e.getMessage(), e);
            throw new BatchProcessingException(BatchErrorCode.UNKNOWN_ERROR, e);
        }

        return preferences;
    }
}

