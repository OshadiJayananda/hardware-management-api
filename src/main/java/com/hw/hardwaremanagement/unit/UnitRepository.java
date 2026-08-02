package com.hw.hardwaremanagement.unit;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UnitRepository extends JpaRepository<Unit, Long> {

    boolean existsByName(String name);

    boolean existsByShortName(String shortName);

    Optional<Unit> findByName(String name);
}
