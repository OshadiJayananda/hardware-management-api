package com.hw.hardwaremanagement.unit;

import jakarta.persistence.Entity;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "units")
public class Unit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(name = "short_name", nullable = false)
    private String shortName;

    @Column(nullable = false)
    private String status = "Active";

    @Column(name = "created_at")
    private String createdAt;

    @Column(name = "updated_at")
    private String updatedAt;

    @PrePersist
    public void beforeCreate() {
        this.createdAt = LocalDateTime.now().toString();
        this.updatedAt = LocalDateTime.now().toString();

        if (this.status == null) {
            this.status = "Active";
        }
    }

    @PreUpdate
    public void beforeUpdate() {
        this.updatedAt = LocalDateTime.now().toString();
    }

    public Unit() {
    }

    public Unit(String name, String shortName, String status, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.name = name;
        this.shortName = shortName;
        this.status = status;
        this.createdAt = createdAt.toString();
        this.updatedAt = updatedAt.toString();
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

    public String getCreatedAt() {
        return createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }
}
