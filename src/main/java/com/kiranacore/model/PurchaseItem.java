package com.kiranacore.model;
import java.sql.Date;

public class PurchaseItem {
    private int purchaseItemId;
    private int purchaseId;
    private int productId;
    private double quantity;
    private double buyingPrice;
    private Date expiryDate;

    public PurchaseItem(){}    

    public PurchaseItem(int purchaseItemId,int purchaseId,int productId,double quantity,double buyingPrice,Date expiryDate){
        this.purchaseItemId = purchaseItemId;
        this.purchaseId = purchaseId;
        this.productId = productId;
        this.quantity = quantity;
        this.buyingPrice = buyingPrice;
        this.expiryDate = expiryDate;
    }

    public int getPurchaseItemId(){return purchaseItemId;}
    public void setPurchaseItemId(int purchaseItemId){this.purchaseItemId = purchaseItemId;}

    public int getPurchaseId(){return purchaseId;}
    public void setPurchaseId(int purchaseId){this.purchaseId = purchaseId;}

    public int getProductId(){return productId;}
    public void setProductId(int productId){this.productId = productId;}

    public double getQuantity(){return quantity;}
    public void setQuantity(double quantity){this.quantity = quantity;}

    public double getBuyingPrice(){return buyingPrice;}
    public void setBuyingPrice(double buyingPrice){this.buyingPrice = buyingPrice;}

    public Date getExpiryDate(){return expiryDate;}
    public void setExpiryDate(Date expiryDate){this.expiryDate = expiryDate;}
}
