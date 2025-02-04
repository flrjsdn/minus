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
            @AttributeOverride(name = "itemId", column = @Column(name = "item_id", nullable = false))
    })
    private PreferenceId id;

    @Column(name = "score", nullable = false, precision = 4, scale = 1)
    private BigDecimal score;

    @Column(name = "updated_at", nullable = false)
    private LocalDate updatedAt;

}
