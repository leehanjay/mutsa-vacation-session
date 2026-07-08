package com.mutsa.delivery.menu.repository;

import com.mutsa.delivery.menu.entity.Option;
import com.mutsa.delivery.menu.entity.OptionGroup;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OptionRepository extends JpaRepository<Option, Long> {
    List<Option> findByOptionGroupIn(List<OptionGroup> optionGroups); // 옵션그룹들(리스트) 기준으로 옵션을 한 번에 찾기
}
