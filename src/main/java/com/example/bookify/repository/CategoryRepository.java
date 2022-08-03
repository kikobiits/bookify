package com.example.bookify.repository;

import com.example.bookify.model.entity.Category;
import com.example.bookify.model.enums.CategoryNameEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    Optional<Category> findByCategory(CategoryNameEnum category);
}
