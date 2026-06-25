package com.kiranacore.dao;

import com.kiranacore.model.Sale;
import com.kiranacore.model.SaleItem;
import java.util.List;

public interface SaleDAO {
    void recordNewSale(Sale sale,List<SaleItem> items);
    Sale getSaleById(int saleId);
    List<Sale> getAllSales();
    List<Sale> getSaleByDate(String date);
    List<Sale> getSaleByCustomerId(int customerId);
    List<SaleItem> getAllSaleItemsBySaleId(int saleId);
}
