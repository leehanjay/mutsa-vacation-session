package com.mutsa.delivery.category.entity;

import com.mutsa.delivery.common.entity.BaseTimeEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "categories")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Category extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Long categoryId;

    @Column(name = "tag_name", nullable = false)
    private String tagName;

    @Builder(access = AccessLevel.PRIVATE)
    private Category(String tagName) {
        this.tagName = tagName;
    }

    public static Category createNew(String tagName) {
        if (tagName == null || tagName.isBlank()) {
            throw new IllegalArgumentException("tagName must not be blank");
        }
        return Category.builder()
                .tagName(tagName)
                .build();
    }
}
