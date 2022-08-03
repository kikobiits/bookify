package com.example.bookify.init;

import com.example.bookify.model.entity.Category;
import com.example.bookify.model.enums.CategoryNameEnum;
import com.example.bookify.repository.CategoryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class DatabaseSeeder implements CommandLineRunner {

    private final CategoryRepository categoryRepository;

    public DatabaseSeeder(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (categoryRepository.count() != 0) {
            return;
        }

        Arrays.stream(CategoryNameEnum.values())
                .forEach(category -> {
                    Category current = new Category();
                    current.setCategory(category);
                    categoryRepository.save(current);
                });
    }
}
