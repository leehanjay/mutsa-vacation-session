package com.mutsa.delivery.menu.repository;

import com.mutsa.delivery.menu.entity.Menu;
import com.mutsa.delivery.menu.entity.OptionGroup;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OptionGroupRepository extends JpaRepository<OptionGroup, Long> {
    List<OptionGroup> findByMenuIn(List<Menu> menus);
}
