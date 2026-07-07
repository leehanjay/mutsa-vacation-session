package com.mutsa.delivery.menu.entity;

import com.mutsa.delivery.common.entity.BaseTimeEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "options")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Option extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "option_id")
    private Long optionId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "option_group_id", nullable = false)
    private OptionGroup optionGroup;

    @Column(name = "option_name", nullable = false)
    private String optionName;

    @Column(name = "extra_price", nullable = false)
    private Long extraPrice;

    @Builder(access = AccessLevel.PRIVATE)
    private Option(OptionGroup optionGroup, String optionName, Long extraPrice) {
        this.optionGroup = optionGroup;
        this.optionName = optionName;
        this.extraPrice = extraPrice;
    }

    public static Option createNew(OptionGroup optionGroup, String optionName, Long extraPrice) {
        if (optionGroup == null) {
            throw new IllegalArgumentException("optionGroup must not be null");
        }
        if (optionName == null || optionName.isBlank()) {
            throw new IllegalArgumentException("optionName must not be blank");
        }
        if (extraPrice == null || extraPrice < 0) {
            throw new IllegalArgumentException("extraPrice must not be negative");
        }
        return Option.builder()
                .optionGroup(optionGroup)
                .optionName(optionName)
                .extraPrice(extraPrice)
                .build();
    }
}
