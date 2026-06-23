package com.kiranacore.model;
import java.sql.Timestamp;


public class Purchase {
    private int purchaseId;
    private int supplierId;
    private Timestamp purchaseDate;
    private double totalAmount;

    public Purchase(){}

    public Purchase(int purchaseId,int supplierId,Timestamp purchaseDate,double totalAmount){
        this.purchaseId = purchaseId;
        this.supplierId = supplierId;
        this.purchaseDate = purchaseDate;
        this.totalAmount = totalAmount;
    }

    public int getPurchaseId(){
        return purchaseId;
    }

    public void setPurchaseId(int purchaseId){
        this.purchaseId = purchaseId;
    }

    public int getSupplierId(){
        return supplierId;
    }

    public void setSupplierId(int supplierId){
        this.supplierId = supplierId;
    }

    public Timestamp getPurchaseDate(){
        return purchaseDate;
    }

    public void setPurchaseDate(Timestamp purchaseDate){
        this.purchaseDate = purchaseDate;
    }

    public double getTotalAmount(){
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount){
        this.totalAmount = totalAmount;
    }
}
