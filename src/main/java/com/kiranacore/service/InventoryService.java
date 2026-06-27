package com.kiranacore.service;

import com.kiranacore.dao.*;
import com.kiranacore.model.*;
import java.util.List;

public class InventoryService {
    private PurchaseDAO purchaseDAO = new PurchaseDAOImpl();
    private ProductDAO productDAO = new ProductDAOImpl();
    private SupplierDAO supplierDAO = new SupplierDAOImpl();
    
    public boolean processPurchase(Purchase purchase,List<PurchaseItem> items,double amountPaidUpfront){
        try{
            if(items == null || items.isEmpty()){
                System.out.println("Error: Cannot process a purchase with zero items");
                return false;
            }
            purchaseDAO.recordNewPurchase(purchase, items);

            for(PurchaseItem item: items){
                productDAO.updateStock(item.getProductId(), -item.getQuantity());
            }
            double balanceOwned = purchase.getTotalAmount()-amountPaidUpfront;
            if(balanceOwned>0){
                supplierDAO.updateSupplierBalance(purchase.getSupplierId(),balanceOwned);
                System.out.println("Note: Added Rs."+balanceOwned+" to supplier's outstanding balance.");
            }
            System.out.println("Purchase Processed Successfully!");
            return true;
        }
        catch(Exception e){
            System.out.println("Error processing purchase!");
            e.printStackTrace();
            return false;
        }
    }

    public void displayLowStockAlert(){
        System.out.println("\n---LOW STOCK PRODUCTS ---");
        List<Product> allProductsWithLowStock  =  productDAO.getLowStockAlert();
        
        if(allProductsWithLowStock.isEmpty()||allProductsWithLowStock==null){
            System.out.println("No low stock products found!");
            return;
        }
        System.out.println("Products with low stock:\n");
        for(Product product:allProductsWithLowStock){
            System.out.println("Product: "+product.getName()+" ,Current Stock: "+product.getCurrentStock());
        }
        System.out.println("---------END---------");
        return;
    }

    public boolean addNewProductToInventory(Product product){

        if(product.getMrp()<=0 || product.getSellingPrice()<=0){
            System.out.println("Error: Invalid prices!");
            return false;
        }
        if(product.getCurrentStock()<0){
            System.out.println("Error: Invalid stock!");
            return false;
        }

        try{
            productDAO.addProduct(product);
            System.out.println("Product: "+product.getName()+" added successfully!");
            return true;
        }catch(Exception e){
            System.out.println("Error adding product!");
            e.printStackTrace();
            return false;
        }
    }
    
}
