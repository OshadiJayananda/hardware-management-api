
package com.hw.hardwaremanagement.category.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class CategoryRequest {

    @NotBlank(message = "Category name is required")
    @Size(max = 150)
    private String name;

    @NotBlank(message = "Category slug is required")
    @Size(max = 180)
    private String slug;

    private Long parentId;

    @Size(max = 1000)
    private String description;

    public String getName() {
        return name;
    }

    public String getSlug() {
        return slug;
    }

    public Long getParentId() {
        return parentId;
    }

    public String getDescription() {
        return description;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
