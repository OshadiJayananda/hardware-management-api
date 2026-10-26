
package com.hw.hardwaremanagement.category;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository
        extends JpaRepository<Category, Long> {

    Optional<Category> findBySlugIgnoreCase(String slug);

    boolean existsBySlugIgnoreCaseAndIdNot(String slug, Long id);

    List<Category> findByStatusOrderByNameAsc(String status);

    Optional<Category> findByIdAndStatus(Long id, String status);

    List<Category> findByParentIsNullAndStatusOrderByNameAsc(
            String status);

    List<Category> findByParentIdAndStatusOrderByNameAsc(
            Long parentId, String status);

    boolean existsByParentIdAndStatus(Long parentId, String status);
}
