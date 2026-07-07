package com.mutsa.delivery.store.entity;

import com.mutsa.delivery.category.entity.Category;
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
@Table(name = "stores")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Store extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "store_id")
    private Long storeId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @Column(name = "store_name", nullable = false)
    private String storeName;

    @Column(name = "store_rating")
    private Double storeRating;

    @Column(name = "image_url")
    private String imageUrl;

    @Builder(access = AccessLevel.PRIVATE)
    private Store(Category category, String storeName, Double storeRating, String imageUrl) {
        this.category = category;
        this.storeName = storeName;
        this.storeRating = storeRating;
        this.imageUrl = imageUrl;
    }

    public static Store createNew(Category category, String storeName, Double storeRating, String imageUrl) {
        if (category == null) {
            throw new IllegalArgumentException("category must not be null");
        }
        if (storeName == null || storeName.isBlank()) {
            throw new IllegalArgumentException("storeName must not be blank");
        }
        return Store.builder()
                .category(category)
                .storeName(storeName)
                .storeRating(storeRating)
                .imageUrl(imageUrl)
                .build();
    }
}
