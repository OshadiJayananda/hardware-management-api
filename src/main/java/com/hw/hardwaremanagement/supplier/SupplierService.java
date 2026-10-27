
package com.hw.hardwaremanagement.supplier;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.hw.hardwaremanagement.supplier.dto.SupplierRequest;
import com.hw.hardwaremanagement.supplier.dto.SupplierResponse;

@Service
public class SupplierService {

    private final SupplierRepository supplierRepository;

    public SupplierService(SupplierRepository supplierRepository) {
        this.supplierRepository = supplierRepository;
    }

    public List<SupplierResponse> getAllSuppliers() {
        return supplierRepository
                .findByStatusOrderByNameAsc("ACTIVE")
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    public SupplierResponse getSupplierById(Long id) {
        return supplierRepository
                .findByIdAndStatus(id, "ACTIVE")
                .map(this::mapToResponse)
                .orElseThrow(() -> new RuntimeException(
                        "Active supplier not found with id: " + id));
    }

    public SupplierResponse createSupplier(SupplierRequest request) {

        String name = request.getName().trim();

        Optional<Supplier> existingSupplier = supplierRepository.findByNameIgnoreCase(name);

        if (existingSupplier.isPresent()) {
            Supplier supplier = existingSupplier.get();

            if ("ACTIVE".equals(supplier.getStatus())) {
                throw new RuntimeException(
                        "Supplier name already exists");
            }

            // Reactivate an existing inactive supplier.
            supplier.setName(name);
            supplier.setContactPerson(request.getContactPerson());
            supplier.setPhone(request.getPhone());
            supplier.setEmail(request.getEmail());
            supplier.setAddress(request.getAddress());
            supplier.setStatus("ACTIVE");

            Supplier reactivatedSupplier = supplierRepository.save(supplier);

            return mapToResponse(reactivatedSupplier);
        }

        Supplier supplier = new Supplier();

        supplier.setName(name);
        supplier.setContactPerson(request.getContactPerson());
        supplier.setPhone(request.getPhone());
        supplier.setEmail(request.getEmail());
        supplier.setAddress(request.getAddress());
        supplier.setStatus("ACTIVE");

        Supplier savedSupplier = supplierRepository.save(supplier);

        return mapToResponse(savedSupplier);
    }

    public SupplierResponse updateSupplier(
            Long id, SupplierRequest request) {

        Supplier supplier = supplierRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(
                        "Supplier not found with id: " + id));

        String name = request.getName().trim();

        Optional<Supplier> existingSupplier = supplierRepository.findByNameIgnoreCase(name);

        if (existingSupplier.isPresent()
                && !existingSupplier.get().getId().equals(id)) {
            throw new RuntimeException(
                    "Supplier name already exists");
        }

        supplier.setName(name);
        supplier.setContactPerson(request.getContactPerson());
        supplier.setPhone(request.getPhone());
        supplier.setEmail(request.getEmail());
        supplier.setAddress(request.getAddress());

        Supplier updatedSupplier = supplierRepository.save(supplier);

        return mapToResponse(updatedSupplier);
    }

    public void deleteSupplier(Long id) {

        Supplier supplier = supplierRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(
                        "Supplier not found with id: " + id));

        supplier.setStatus("INACTIVE");

        supplierRepository.save(supplier);
    }

    private SupplierResponse mapToResponse(Supplier supplier) {
        return new SupplierResponse(
                supplier.getId(),
                supplier.getName(),
                supplier.getContactPerson(),
                supplier.getPhone(),
                supplier.getEmail(),
                supplier.getAddress(),
                supplier.getStatus(),
                supplier.getCreatedAt(),
                supplier.getUpdatedAt());
    }
}
