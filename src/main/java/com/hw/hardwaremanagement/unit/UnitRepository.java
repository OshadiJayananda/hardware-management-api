package com.hw.hardwaremanagement.unit;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UnitRepository extends JpaRepository<Unit, Long> {

    boolean existsByName(String name);

    boolean existsByShortName(String shortName);

    boolean existsByNameAndIdNot(String name, Long id);

    boolean existsByShortNameAndIdNot(String shortName, Long id);

    List<Unit> findByStatus(String status);

    Optional<Unit> findByIdAndStatus(Long id, String status);
}