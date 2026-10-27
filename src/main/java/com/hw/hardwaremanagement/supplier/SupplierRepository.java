package com.hw.hardwaremanagement.supplier;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SupplierRepository
        extends JpaRepository<Supplier, Long> {

    Optional<Supplier> findByNameIgnoreCase(String name);

    List<Supplier> findByStatusOrderByNameAsc(String status);

    Optional<Supplier> findByIdAndStatus(Long id, String status);
}
