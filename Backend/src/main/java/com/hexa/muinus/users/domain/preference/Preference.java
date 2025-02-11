package com.hexa.muinus.users.domain.preference;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "preference")
public class Preference {

    @EmbeddedId
    @AttributeOverrides({
            @AttributeOverride(name = "userNo", column = @Column(name = "user_no", nullable = false)),
            @AttributeOverride(name = "itemId", column = @Column(name = "item_id", nullable = false)),
            @AttributeOverride(name = "updatedAt", column = @Column(name = "updated_at", nullable = false))
    })
    private PreferenceId id;

    @Column(name = "daily_score", nullable = false, precision = 4, scale = 1)
    private BigDecimal dailyScore;

    @Column(name = "monthly_score", nullable = false, precision = 4, scale = 1)
    private BigDecimal monthlyScore;

}
