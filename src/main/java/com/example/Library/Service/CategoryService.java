package com.example.Library.Service;

import com.example.Library.dto.CategoryDTO;
import com.example.Library.model.Category;
import com.example.Library.repository.BookRepository;
import com.example.Library.repository.CategoryRepository;
import com.example.Library.util.DTOMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {
    private CategoryRepository categoryRepository;
    private DTOMapper dtoMapper;
    private final BookRepository bookRepository;
     @Autowired
    public CategoryService(CategoryRepository categoryRepository, DTOMapper dtoMapper, BookRepository bookRepository) {
         this.categoryRepository = categoryRepository;
         this.dtoMapper = dtoMapper;
         this.bookRepository = bookRepository;
     }
     //Create Category
    public ResponseEntity<?> createCategory(CategoryDTO categoryDTO) {
        if (categoryDTO == null || categoryDTO.getName() == null || categoryDTO.getName().isEmpty()) {
            return ResponseEntity.badRequest().body("Category name cannot be empty");
        }
        if (!categoryRepository.findByName(categoryDTO.getName()).isEmpty()) {
            return ResponseEntity.badRequest().body("Category with this name already exists");
        }
        //get the parent category id if provided and set it
        if (categoryDTO.getParentCategoryId() != null && !categoryRepository.existsById(categoryDTO.getParentCategoryId())){
            return ResponseEntity.badRequest().body("Parent category with this ID does not exist");
        }
        // If parent category exists, set it
        Category parentCategory = null;
        if (categoryDTO.getParentCategoryId() != null) {
            parentCategory = categoryRepository.findById(categoryDTO.getParentCategoryId())
                    .orElseThrow(() -> new RuntimeException("Parent category not found"));
        }
        Category category = dtoMapper.mapToCategory(categoryDTO);
        return ResponseEntity.ok(categoryRepository.save(category));
    }

    // Update Category
    public ResponseEntity<?> updateCategory(Long id, CategoryDTO categoryDTO) {
        if (!categoryRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        Category category = dtoMapper.mapToCategory(categoryDTO);
        Category existingCategory = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));
        // Update the category fields based on the provided DTO and ignore unsent fields
        if (category.getName() != null && !category.getName().isEmpty()) {
            existingCategory.setName(category.getName());
        }
        // update parent category if provided
        if (category.getParent() != null) {
            if (!categoryRepository.existsById(category.getParent().getId())) {
                return ResponseEntity.badRequest().body("Parent category with this ID does not exist");
            }
            Category parentCategory = categoryRepository.findById(category.getParent().getId())
                    .orElseThrow(() -> new RuntimeException("Parent category not found"));

            existingCategory.setParent(parentCategory);
        }
        return ResponseEntity.ok(categoryRepository.save(existingCategory));

    }

    // Delete Category
    public ResponseEntity<?> deleteCategory(Long id) {
        if (!categoryRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        categoryRepository.deleteById(id);
        return ResponseEntity.ok().body("Category deleted successfully");
    }
    // Get Category by ID
    public ResponseEntity<?> getCategoryById(Long id) {
        if (!categoryRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));
        return ResponseEntity.ok(category);
    }
    // Get all Categories
    public ResponseEntity<?> getAllCategories() {
        return ResponseEntity.ok(categoryRepository.findAll());
    }
    // Get Categories by Parent ID
    public ResponseEntity<?> getCategoriesByParentId(Long parentId) {
        if (parentId == null) {
            return ResponseEntity.badRequest().body("Parent ID cannot be null");
        }
        if (!categoryRepository.existsById(parentId)) {
            return ResponseEntity.notFound().build();
        }
        Category parentCategory = categoryRepository.findById(parentId)
                .orElseThrow(() -> new RuntimeException("Parent category not found"));
        return ResponseEntity.ok(parentCategory);
    }

    // Get Categories by Name
    public ResponseEntity<?> getCategoryByName(String name) {
        if (name == null || name.isEmpty()) {
            return ResponseEntity.badRequest().body("Category name cannot be empty");
        }
        return ResponseEntity.ok(categoryRepository.findByName(name));
    }



}
