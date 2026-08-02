package com.hw.hardwaremanagement.unit.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class UnitRequest {

    @NotBlank(message = "Name is required")
    @Size(max = 100, message = "Unit name must not exceed 100 characters")
    private String name;

    @NotBlank(message = "Short name is required")
    @Size(max = 20, message = "Short name must not exceed 20 characters")
    private String shortName;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

}
