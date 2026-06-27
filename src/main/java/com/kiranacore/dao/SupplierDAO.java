package com.kiranacore.dao;

import com.kiranacore.model.Supplier;
import java.util.List;

public interface SupplierDAO {
    void addSupplier(Supplier supplier);

    void updateSupplierDetails(Supplier supplier, String name, String contactNumber);

    void updateSupplierBalance(int supplierId, double balance);

    void deleteSupplier(int supplierId);

    Supplier getSupplierById(int supplierId);

    Supplier getSupplierByContactNumber(String contactNumber);

    List<Supplier> getAllSuppliers();
}
