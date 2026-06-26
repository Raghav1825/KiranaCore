package com.kiranacore.service;

import com.kiranacore.dao.*;
import com.kiranacore.model.*;
import java.util.List;

public class CheckoutService {
    private SaleDAO saleDAO = new SaleDAOImpl();
    private ProductDAO productDAO = new ProductDAOImpl();
    private CustomerDAO customerDAO = new CustomerDAOImpl();

    public boolean processSale(Sale sale, List<SaleItem> items) {
        try{
            for(SaleItem item : items){
                Product product = productDAO.getProductById(item.getProductId());
                if(product.getCurrentStock()<item.getQuantity()){
                    System.out.println("Sale failed: Not enough stock for "+product.getName());
                    return false;
                }
            }
            saleDAO.recordNewSale(sale, items);

            for(SaleItem item:items){
                productDAO.updateStock(item.getProductId(),item.getQuantity());
            }
            if(sale.getPaymentMode().equalsIgnoreCase("UDHAAR")){
                customerDAO.updateCustomerKhata(sale.getCustomerId(),sale.getTotalAmount());
            }
            System.out.println("Sale processed successfully!");
            return true;
        }catch(Exception e){
            System.out.println("Error processing sale");
            e.printStackTrace();
            return false;
        }
    }
}
