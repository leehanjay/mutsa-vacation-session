package com.mutsa.delivery.menu.entity;

import com.mutsa.delivery.common.entity.BaseTimeEntity;
import com.mutsa.delivery.store.entity.Store;
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
@Table(name = "menus")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Menu extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "menu_id")
    private Long menuId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id", nullable = false)
    private Store store;

    @Column(name = "menu_name", nullable = false)
    private String menuName;

    @Column(name = "menu_price", nullable = false)
    private Long menuPrice;

    @Builder(access = AccessLevel.PRIVATE)
    private Menu(Store store, String menuName, Long menuPrice) {
        this.store = store;
        this.menuName = menuName;
        this.menuPrice = menuPrice;
    }

    public static Menu createNew(Store store, String menuName, Long menuPrice) {
        if (store == null) {
            throw new IllegalArgumentException("store must not be null");
        }
        if (menuName == null || menuName.isBlank()) {
            throw new IllegalArgumentException("menuName must not be blank");
        }
        if (menuPrice == null || menuPrice < 0) {
            throw new IllegalArgumentException("menuPrice must not be negative");
        }
        return Menu.builder()
                .store(store)
                .menuName(menuName)
                .menuPrice(menuPrice)
                .build();
    }
}
