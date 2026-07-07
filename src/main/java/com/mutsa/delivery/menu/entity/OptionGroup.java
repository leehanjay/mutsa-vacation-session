package com.mutsa.delivery.menu.entity;

import com.mutsa.delivery.common.entity.BaseTimeEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
@Table(name = "option_groups")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OptionGroup extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "option_group_id")
    private Long optionGroupId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_id", nullable = false)
    private Menu menu;

    @Column(name = "group_name", nullable = false)
    private String groupName;

    @Enumerated(EnumType.STRING)
    @Column(name = "selection_type", nullable = false)
    private SelectionType selectionType;

    @Column(name = "is_required", nullable = false)
    private boolean required;

    @Builder(access = AccessLevel.PRIVATE)
    private OptionGroup(Menu menu, String groupName, SelectionType selectionType, boolean required) {
        this.menu = menu;
        this.groupName = groupName;
        this.selectionType = selectionType;
        this.required = required;
    }

    public static OptionGroup createNew(Menu menu, String groupName, SelectionType selectionType, boolean required) {
        if (menu == null) {
            throw new IllegalArgumentException("menu must not be null");
        }
        if (groupName == null || groupName.isBlank()) {
            throw new IllegalArgumentException("groupName must not be blank");
        }
        if (selectionType == null) {
            throw new IllegalArgumentException("selectionType must not be null");
        }
        return OptionGroup.builder()
                .menu(menu)
                .groupName(groupName)
                .selectionType(selectionType)
                .required(required)
                .build();
    }
}
