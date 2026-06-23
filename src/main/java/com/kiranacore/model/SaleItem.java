package com.kiranacore.model;

public class SaleItem {
    private int saleItemId;
    private int saleId;
    private int productId;
    private double quantity;
    private double priceAtSale;

    public SaleItem(){}

    public SaleItem(int saleItemId,int saleId,int productId,double quantity,double priceAtSale){
        this.saleItemId = saleItemId;
        this.saleId = saleId;
        this.productId = productId;
        this.quantity = quantity;
        this.priceAtSale = priceAtSale;
    }

    public int getSaleItemId() {return saleItemId;}
    public void setSaleItemId(int saleItemId) {this.saleItemId = saleItemId;}

    public int getSaleId() {return saleId;}
    public void setSaleId(int saleId) {this.saleId = saleId;}

    public int getProductId() {return productId;}
    public void setProductId(int productId) {this.productId = productId;}

    public double getQuantity() {return quantity;}
    public void setQuantity(double quantity) {this.quantity = quantity;}

    public double getPriceAtSale() {return priceAtSale;}
    public void setPriceAtSale(double priceAtSale) {this.priceAtSale = priceAtSale;}
}
