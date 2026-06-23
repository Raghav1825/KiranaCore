package com.kiranacore.model;

public class Product {
    private int  productId;
    private String name;
    private int categoryId;
    private String unit;
    private double currentStock;
    private double minStockAlert;
    private double mrp;
    private double sellingPrice;

    public Product(){}

    public Product(int productId,String name,int categoryId,String unit,double currentStock,double minStockAlert,double mrp,double sellingPrice){
        this.productId = productId;
        this.name = name;
        this.categoryId = categoryId;
        this.unit = unit;
        this.currentStock = currentStock;
        this.minStockAlert = minStockAlert;
        this.mrp = mrp;
        this.sellingPrice = sellingPrice;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public double getCurrentStock() {
        return currentStock;
    }

    public void setCurrentStock(double currentStock) {
        this.currentStock = currentStock;
    }

    public double getMinStockAlert() {
        return minStockAlert;
    }

    public void setMinStockAlert(double minStockAlert) {
        this.minStockAlert = minStockAlert;
    }

    public double getMrp() {
        return mrp;
    }

    public void setMrp(double mrp) {
        this.mrp = mrp;
    }

    public double getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(double sellingPrice) {
        this.sellingPrice = sellingPrice;
    }
}
