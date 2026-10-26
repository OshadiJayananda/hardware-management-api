
package com.hw.hardwaremanagement.category;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.hw.hardwaremanagement.category.dto.CategoryRequest;
import com.hw.hardwaremanagement.category.dto.CategoryResponse;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<CategoryResponse> getAllCategories() {
        return categoryRepository
                .findByStatusOrderByNameAsc("ACTIVE")
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    public CategoryResponse getCategoryById(Long id) {
        return categoryRepository
                .findByIdAndStatus(id, "ACTIVE")
                .map(this::mapToResponse)
                .orElseThrow(() -> new RuntimeException(
                        "Active category not found with id: " + id));
    }

    public List<CategoryResponse> getParentCategories() {
        return categoryRepository
                .findByParentIsNullAndStatusOrderByNameAsc("ACTIVE")
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    public List<CategoryResponse> getSubcategories(Long parentId) {
        getCategoryById(parentId);

        return categoryRepository
                .findByParentIdAndStatusOrderByNameAsc(
                        parentId, "ACTIVE")
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    public CategoryResponse createCategory(CategoryRequest request) {

        String name = request.getName().trim();
        String slug = request.getSlug().trim().toLowerCase();

        Optional<Category> existing = categoryRepository.findBySlugIgnoreCase(slug);

        if (existing.isPresent()) {
            Category category = existing.get();

            if ("ACTIVE".equals(category.getStatus())) {
                throw new RuntimeException(
                        "Category slug already exists");
            }

            Category parent = resolveParent(
                    request.getParentId(), category.getId());

            category.setName(name);
            category.setSlug(slug);
            category.setDescription(request.getDescription());
            category.setParent(parent);
            category.setStatus("ACTIVE");

            return mapToResponse(categoryRepository.save(category));
        }

        Category category = new Category();
        category.setName(name);
        category.setSlug(slug);
        category.setDescription(request.getDescription());
        category.setParent(resolveParent(request.getParentId(), null));
        category.setStatus("ACTIVE");

        return mapToResponse(categoryRepository.save(category));
    }

    public CategoryResponse updateCategory(
            Long id, CategoryRequest request) {

        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(
                        "Category not found with id: " + id));

        String name = request.getName().trim();
        String slug = request.getSlug().trim().toLowerCase();

        if (categoryRepository
                .existsBySlugIgnoreCaseAndIdNot(slug, id)) {
            throw new RuntimeException(
                    "Category slug already exists");
        }

        Category parent = resolveParent(request.getParentId(), id);

        category.setName(name);
        category.setSlug(slug);
        category.setDescription(request.getDescription());
        category.setParent(parent);

        return mapToResponse(categoryRepository.save(category));
    }

    public void deleteCategory(Long id) {

        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(
                        "Category not found with id: " + id));

        if (categoryRepository
                .existsByParentIdAndStatus(id, "ACTIVE")) {
            throw new RuntimeException(
                    "Cannot deactivate category with active subcategories");
        }

        category.setStatus("INACTIVE");
        categoryRepository.save(category);
    }

    private Category resolveParent(Long parentId, Long categoryId) {

        if (parentId == null) {
            return null;
        }

        Category parent = categoryRepository
                .findByIdAndStatus(parentId, "ACTIVE")
                .orElseThrow(() -> new RuntimeException(
                        "Active parent category not found"));

        Set<Long> visited = new HashSet<>();
        Category current = parent;

        while (current != null) {
            if (!visited.add(current.getId())) {
                throw new RuntimeException(
                        "Circular category relationship detected");
            }

            if (current.getId().equals(categoryId)) {
                throw new RuntimeException(
                        "Category cannot be its own parent or descendant");
            }

            current = current.getParent();
        }

        return parent;
    }

    private CategoryResponse mapToResponse(Category category) {
        return new CategoryResponse(
                category.getId(),
                category.getName(),
                category.getSlug(),
                category.getParent() == null
                        ? null
                        : category.getParent().getId(),
                category.getDescription(),
                category.getStatus(),
                category.getCreatedAt(),
                category.getUpdatedAt());
    }
}
