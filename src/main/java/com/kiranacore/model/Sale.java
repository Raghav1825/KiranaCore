package com.kiranacore.model;
import java.sql.Timestamp;

public class Sale {
    private int saleId;
    private int customerId;
    private int userId;
    private Timestamp saleDate;
    private double totalAmount;
    private double discountApplied;
    private String paymentMode;

    public Sale(){}

    public Sale(int saleId,int customerId,int userId,Timestamp saleDate,double totalAmount,double discountApplied,String paymentMode){
        this.saleId = saleId;
        this.customerId = customerId;
        this.userId = userId;
        this.saleDate = saleDate;
        this.totalAmount = totalAmount;
        this.discountApplied = discountApplied;
        this.paymentMode = paymentMode;
    }

    public int getSaleId() {return saleId;}
    public void setSaleId(int saleId) {this.saleId = saleId;}

    public int getCustomerId() {return customerId;}
    public void setCustomerId(int customerId) {this.customerId = customerId;}

    public int getUserId() {return userId;}
    public void setUserId(int userId) {this.userId = userId;}

    public Timestamp getSaleDate() {return saleDate;}
    public void setSaleDate(Timestamp saleDate) {this.saleDate = saleDate;}

    public double getTotalAmount() {return totalAmount;}
    public void setTotalAmount(double totalAmount) {this.totalAmount = totalAmount;}

    public double getDiscountApplied() {return discountApplied;}
    public void setDiscountApplied(double discountApplied) {this.discountApplied = discountApplied;}

    public String getPaymentMode() {return paymentMode;}
    public void setPaymentMode(String paymentMode) {this.paymentMode = paymentMode;}
}
