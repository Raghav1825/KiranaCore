package com.kiranacore.model;

public class Supplier {
    private int supplierId;
    private String supplierName;
    private String contactNumber;
    private double balanceDue;

    public Supplier(){}

    public Supplier(int supplierId,String supplierName,String contactNumber,double balanceDue){
        this.supplierId = supplierId;
        this.supplierName = supplierName;
        this.contactNumber = contactNumber;
        this.balanceDue = balanceDue;
    }

    public int getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(int supplierId) {
        this.supplierId = supplierId;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public double getBalanceDue() {
        return balanceDue;
    }

    public void setBalanceDue(double balanceDue) {
        this.balanceDue = balanceDue;
    }
}
