package com.example.Library.controller;

import com.example.Library.Service.CategoryService;
import com.example.Library.dto.CategoryDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/categories")
public class CategoryController {
    private CategoryService categoryService;
    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }
    // This method will handle the creation of a new category
    @PostMapping("/create")
    public ResponseEntity<?> createCategory(@RequestBody CategoryDTO categoryDTO) {
        return categoryService.createCategory(categoryDTO);
    }

    // This method will handle the update of an existing category
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateCategory(@PathVariable("id") Long id, @RequestBody CategoryDTO categoryDTO) {
        return categoryService.updateCategory(id, categoryDTO);
    }

    // This method will handle the deletion of a category
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable("id") Long id) {
        return categoryService.deleteCategory(id);
    }
    // This method will handle the retrieval of a category by ID
    @GetMapping("/get/{id}")
    public ResponseEntity<?> getCategoryById(@PathVariable("id") Long id) {
        return categoryService.getCategoryById(id);
    }
    // This method will handle the retrieval of all categories
    @GetMapping("/getAll")
    public ResponseEntity<?> getAllCategories() {
        return categoryService.getAllCategories();
    }
// This method will handle the retrieval of categories by name
    @GetMapping("/getByName/{name}")
    public ResponseEntity<?> getCategoriesByName(@PathVariable("name") String name) {
        return categoryService.getCategoryByName(name);
    }


}
