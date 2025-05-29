package com.example.Library.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class CategoryDTO {
    private Long id;
    private String name;
    private Long parentCategoryId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getParentCategoryId() {
        return parentCategoryId;
    }

    public void setParentCategoryId(Long parentCategoryId) {
        this.parentCategoryId = parentCategoryId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

// Assuming parent category is a Long ID
}
