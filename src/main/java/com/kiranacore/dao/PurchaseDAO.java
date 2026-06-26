package com.kiranacore.dao;

import com.kiranacore.model.Purchase;
import com.kiranacore.model.PurchaseItem;
import java.util.List;

public interface PurchaseDAO {
    void recordNewPurchase(Purchase purchase,List<PurchaseItem> items);
    Purchase getPurchaseById(int purchaseId);
    List<Purchase> getAllPurchases();
    List<Purchase> getPurchasesByDate(String date);
    List<Purchase> getPurchasesBySupplierId(int supplierId);
    List<PurchaseItem> getPurchaseItems(int purchaseId);
    
}
