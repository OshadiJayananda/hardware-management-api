package com.hw.hardwaremanagement.brand;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.hw.hardwaremanagement.brand.dto.BrandRequest;
import com.hw.hardwaremanagement.brand.dto.BrandResponse;

@Service
public class BrandService {

    private final BrandRepository brandRepository;

    public BrandService(BrandRepository brandRepository) {
        this.brandRepository = brandRepository;
    }

    public List<BrandResponse> getAllBrands() {
        return brandRepository.findByStatusOrderByNameAsc("ACTIVE")
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    public BrandResponse getBrandById(Long id) {
        return brandRepository.findByIdAndStatus(id, "ACTIVE")
                .map(this::mapToResponse)
                .orElseThrow(() -> new RuntimeException("Active brand not found with id: " + id));
    }

    public BrandResponse createBrand(BrandRequest request) {
        String name = request.getName().trim();

        Optional<Brand> existingBrand = brandRepository.findByNameIgnoreCase(name);

        if (existingBrand.isPresent()) {
            Brand brand = existingBrand.get();

            if ("ACTIVE".equals(brand.getStatus())) {
                throw new RuntimeException("Brand name already exists");
            }

            brand.setStatus("ACTIVE");
            brand.setDescription(request.getDescription());

            Brand reactivatedBrand = brandRepository.save(brand);

            return mapToResponse(reactivatedBrand);
        }

        Brand brand = new Brand();
        brand.setName(name);
        brand.setDescription(request.getDescription());
        brand.setStatus("ACTIVE");

        Brand savedBrand = brandRepository.save(brand);

        return mapToResponse(savedBrand);
    }

    public BrandResponse updateBrand(Long id, BrandRequest request) {
        Brand brand = brandRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Brand not found with id: " + id));

        String name = request.getName().trim();

        if (brandRepository.existsByNameIgnoreCaseAndIdNot(name, id)) {
            throw new RuntimeException("Brand name already exists");
        }

        brand.setName(name);
        brand.setDescription(request.getDescription());

        Brand updatedBrand = brandRepository.save(brand);

        return mapToResponse(updatedBrand);
    }

    public void deleteBrand(Long id) {
        Brand brand = brandRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Brand not found with id: " + id));

        brand.setStatus("INACTIVE");
        brandRepository.save(brand);
    }

    private BrandResponse mapToResponse(Brand brand) {
        return new BrandResponse(
                brand.getId(),
                brand.getName(),
                brand.getDescription(),
                brand.getStatus(),
                brand.getCreatedAt(),
                brand.getUpdatedAt());
    }
}