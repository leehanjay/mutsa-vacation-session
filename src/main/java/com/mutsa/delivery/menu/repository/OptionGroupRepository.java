package com.mutsa.delivery.menu.repository;

import com.mutsa.delivery.menu.entity.Menu;
import com.mutsa.delivery.menu.entity.OptionGroup;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OptionGroupRepository extends JpaRepository<OptionGroup, Long> {
    List<OptionGroup> findByMenuIn(List<Menu> menus); // 메뉴들(리스트) 기준으로 옵션그룹을 한 번에 찾기
}
