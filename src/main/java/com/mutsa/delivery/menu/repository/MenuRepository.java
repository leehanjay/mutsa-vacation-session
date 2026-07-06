package com.mutsa.delivery.menu.repository;

import com.mutsa.delivery.menu.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuRepository extends JpaRepository<Menu, Long> {
}
