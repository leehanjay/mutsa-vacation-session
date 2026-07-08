package com.mutsa.delivery.menu.repository;

import com.mutsa.delivery.menu.entity.Option;
import com.mutsa.delivery.menu.entity.OptionGroup;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OptionRepository extends JpaRepository<Option, Long> {
    List<Option> findByOptionGroupIn(List<OptionGroup> optionGroups);
}
