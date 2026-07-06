package com.mutsa.delivery.category.repository;

import com.mutsa.delivery.category.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}