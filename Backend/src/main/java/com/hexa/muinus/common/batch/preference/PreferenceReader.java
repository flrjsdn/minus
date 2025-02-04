package com.hexa.muinus.common.batch.preference;

import com.hexa.muinus.users.domain.preference.Preference;
import com.hexa.muinus.users.domain.preference.PreferenceId;
import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

@Component
public class PreferenceReader implements ItemReader<Preference> {

    private final JdbcTemplate jdbcTemplate;
    private final RestTemplate restTemplate;

    private Iterator<Preference> preferenceIterator;

    @Autowired
    public PreferenceReader(@Qualifier("dataDBSource") DataSource dataDBSource, RestTemplate restTemplate) {
        this.jdbcTemplate = new JdbcTemplate(dataDBSource);
        this.restTemplate = restTemplate;
        List<Preference> preferences = fetchPreferencesFromAPI(jdbcTemplate, restTemplate);
        this.preferenceIterator = preferences.iterator();
    }

    @Override
    public Preference read() {
        return preferenceIterator.hasNext() ? preferenceIterator.next() : null;
    }

    private List<Preference> fetchPreferencesFromAPI(JdbcTemplate jdbcTemplate, RestTemplate restTemplate) {
        List<Long> users = jdbcTemplate.query("SELECT user_no FROM users", (rs, rowNum) -> rs.getLong("user_no"));
        List<Long> items = jdbcTemplate.query("SELECT item_id FROM item", (rs, rowNum) -> rs.getLong("item_id"));

//        ResponseEntity<List<Map<Long, Map<Long, BigDecimal>>>> response =
//                restTemplate.postForEntity("",
//                        Map.of("users", users, "items", items),
//                        (Class<List<Map<Long, Map<Long, BigDecimal>>>>)(Object)List.class);

        List<Preference> preferences = new ArrayList<>();
        LocalDate today = LocalDate.now();

        // 모든 userNo - itemId 조합을 만들고, score = 0 설정
        for (Long userNo : users) {
            for (Long itemId : items) {
                PreferenceId id = new PreferenceId(userNo, itemId);
                preferences.add(new Preference(id, BigDecimal.ZERO, today));
            }
        }

//        for (Map<Long, Map<Long, BigDecimal>> userMap : response.getBody()) {
//        for (Map<Long, Map<Long, BigDecimal>> userMap : Map.of("users", users, "items", items), "score", 0)) {
//            for (Map.Entry<Long, Map<Long, BigDecimal>> userEntry : userMap.entrySet()) {
//                Long userNo = userEntry.getKey();
//                for (Map.Entry<Long, BigDecimal> itemEntry : userEntry.getValue().entrySet()) {
//                    PreferenceId id = new PreferenceId(userNo, itemEntry.getKey());
//                    preferences.add(new Preference(id, itemEntry.getValue(), yesterday));
//                }
//            }
//        }
        return preferences;
    }
}
