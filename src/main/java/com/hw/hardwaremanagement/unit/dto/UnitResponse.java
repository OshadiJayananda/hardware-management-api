package com.hw.hardwaremanagement.unit.dto;

public class UnitResponse {

    private Long id;
    private String name;
    private String shortName;
    private String status;

    public UnitResponse(Long id, String name, String shortName, String status) {
        this.id = id;
        this.name = name;
        this.shortName = shortName;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getShortName() {
        return shortName;
    }

    public String getStatus() {
        return status;
    }

}
