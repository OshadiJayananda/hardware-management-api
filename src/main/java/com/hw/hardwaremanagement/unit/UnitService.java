package com.hw.hardwaremanagement.unit;

import java.util.List;

import org.springframework.stereotype.Service;

import com.hw.hardwaremanagement.unit.dto.UnitRequest;
import com.hw.hardwaremanagement.unit.dto.UnitResponse;

@Service
public class UnitService {

    private final UnitRepository unitRepository;

    public UnitService(UnitRepository unitRepository) {
        this.unitRepository = unitRepository;
    }

    public List<UnitResponse> getAllUnits() {
        return unitRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    public UnitResponse getUnitById(Long id) {
        return unitRepository.findById(id)
                .map(this::mapToResponse)
                .orElseThrow(() -> new RuntimeException("Unit not found with id: " + id));
    }

    public UnitResponse createUnit(UnitRequest request) {
        if (unitRepository.existsByName(request.getName())) {
            throw new RuntimeException("Unit name already exists");
        }

        if (unitRepository.existsByShortName(request.getShortName())) {
            throw new RuntimeException("Unit short name already exists");
        }

        Unit unit = new Unit();
        unit.setName(request.getName());
        unit.setShortName(request.getShortName());
        unit.setStatus("ACTIVE");

        Unit savedUnit = unitRepository.save(unit);

        return mapToResponse(savedUnit);
    }

    public UnitResponse updateUnit(Long id, UnitRequest request) {
        Unit unit = unitRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Unit not found with id: " + id));

        unit.setName(request.getName());
        unit.setShortName(request.getShortName());

        Unit updatedUnit = unitRepository.save(unit);

        return mapToResponse(updatedUnit);
    }

    public void deleteUnit(Long id) {
        Unit unit = unitRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Unit not found with id: " + id));

        unit.setStatus("INACTIVE");
        unitRepository.save(unit);
    }

    private UnitResponse mapToResponse(Unit unit) {
        return new UnitResponse(
                unit.getId(),
                unit.getName(),
                unit.getShortName(),
                unit.getStatus());
    }
}