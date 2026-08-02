package com.hw.hardwaremanagement.unit;

import com.hw.hardwaremanagement.unit.dto.UnitRequest;
import com.hw.hardwaremanagement.unit.dto.UnitResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/units")
public class UnitController {

    private final UnitService unitService;

    public UnitController(UnitService unitService) {
        this.unitService = unitService;
    }

    @GetMapping
    public List<UnitResponse> getAllUnits() {
        return unitService.getAllUnits();
    }

    @GetMapping("/{id}")
    public UnitResponse getUnitById(@PathVariable Long id) {
        return unitService.getUnitById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UnitResponse createUnit(
            @Valid @RequestBody UnitRequest request) {
        return unitService.createUnit(request);
    }

    @PutMapping("/{id}")
    public UnitResponse updateUnit(
            @PathVariable Long id,
            @Valid @RequestBody UnitRequest request) {
        return unitService.updateUnit(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUnit(@PathVariable Long id) {
        unitService.deleteUnit(id);
    }
}