package com.example.bookify.web;

import com.example.bookify.model.enums.CategoryNameEnum;
import com.example.bookify.service.CategoryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/category")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/sea")
    public String allSea(Model model) {

        model.addAttribute("allSea", categoryService.getAllByCategory(CategoryNameEnum.SEA));

        return "category-sea";
    }

    @GetMapping("/mountain")
    public String allMountain(Model model) {

        model.addAttribute("allSea", categoryService.getAllByCategory(CategoryNameEnum.MOUNTAIN));

        return "category-mountain";
    }

    @GetMapping("/city")
    public String allCity(Model model) {

        model.addAttribute("allSea", categoryService.getAllByCategory(CategoryNameEnum.CITY));

        return "category-city";
    }
}
