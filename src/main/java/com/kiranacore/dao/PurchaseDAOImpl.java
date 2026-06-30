package com.kiranacore.dao;

import com.kiranacore.model.Purchase;
import com.kiranacore.model.PurchaseItem;
import com.kiranacore.config.DatabaseConfig;
import java.sql.*;
import java.util.*;

public class PurchaseDAOImpl implements PurchaseDAO {
    
    @Override
    public void recordNewPurchase(Purchase purchase,List<PurchaseItem> items){
        String purchaseQuery = "INSERT INTO purchases (supplier_id,purchase_date,total_amount) VALUES (?,?,?)";
        String purchaseItemQuery = "INSERT INTO purchase_items (purchase_id,product_id,quantity,buying_price,expiry_date) VALUES (?,?,?,?,?)";
        try(Connection connection = DatabaseConfig.getConnection()){
            connection.setAutoCommit(false);
            try(PreparedStatement preparedStatement = connection.prepareStatement(purchaseQuery,Statement.RETURN_GENERATED_KEYS)){
                preparedStatement.setInt(1, purchase.getSupplierId());
                preparedStatement.setTimestamp(2, purchase.getPurchaseDate());
                preparedStatement.setDouble(3, purchase.getTotalAmount());

                int affectedRows = preparedStatement.executeUpdate();
                if(affectedRows==0){
                    throw new SQLException("Creating a purchase failed");
                }
                ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
                if(generatedKeys.next()){
                    purchase.setPurchaseId(generatedKeys.getInt(1));
                }

                if(items!=null && !items.isEmpty()){
                    PreparedStatement preparedStatement2 = connection.prepareStatement(purchaseItemQuery);
                    for(PurchaseItem item:items){
                        preparedStatement2.setInt(1, purchase.getPurchaseId());
                        preparedStatement2.setInt(2, item.getProductId());
                        preparedStatement2.setDouble(3, item.getQuantity());
                        preparedStatement2.setDouble(4, item.getBuyingPrice());
                        preparedStatement2.setDate(5, item.getExpiryDate());
                        preparedStatement2.addBatch();
                    }
                    preparedStatement2.executeBatch();
                }
                connection.commit();
            }
            catch(SQLException e){
                System.out.println("Error during purchase. Rolling back...");
                connection.rollback();
                e.printStackTrace();
                throw new RuntimeException("Failed to record purchase", e);
            }
            finally {
                try {
                    connection.setAutoCommit(true);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        catch(SQLException e){
            System.out.println("Error in inserting purchase");
            e.printStackTrace();
        }
    }

    @Override
    public Purchase getPurchaseById(int purchaseId){
        String query = "SELECT * FROM purchases WHERE purchase_id=?";
        try(Connection connection = DatabaseConfig.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query)){
                preparedStatement.setInt(1, purchaseId);
                ResultSet rs = preparedStatement.executeQuery();
                if(rs.next()){
                    return new Purchase(
                        rs.getInt("purchase_id"),
                        rs.getInt("supplier_id"),
                        rs.getTimestamp("purchase_date"),
                        rs.getDouble("total_amount")
                    );
                }
            }
            catch(SQLException e){
                System.out.println("Error in fetching purchase by Id");
                e.printStackTrace();
            }
            return null;
    }

    @Override
    public List<Purchase> getAllPurchases() {
        String query = "SELECT * FROM purchases";
        try(Connection connection = DatabaseConfig.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet rs = preparedStatement.executeQuery()){
                List<Purchase> purchases = new ArrayList<>();
                while(rs.next()){
                    purchases.add(new Purchase(
                        rs.getInt("purchase_id"),
                        rs.getInt("supplier_id"),
                        rs.getTimestamp("purchase_date"),
                        rs.getDouble("total_amount")
                    ));
                }
                return purchases;
            }
            catch(SQLException e){
                System.out.println("Error in fetching all purchases");
                e.printStackTrace();
            }
            return null;
    }

    @Override
    public List<Purchase> getPurchasesByDate(String date) {
        String query = "SELECT * FROM purchases WHERE DATE(purchase_date) = ?";
        try(Connection connection = DatabaseConfig.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query)){
                preparedStatement.setString(1, date);
                ResultSet rs = preparedStatement.executeQuery();
                List<Purchase> purchases = new ArrayList<>();
                while(rs.next()){
                    purchases.add(new Purchase(
                        rs.getInt("purchase_id"),
                        rs.getInt("supplier_id"),
                        rs.getTimestamp("purchase_date"),
                        rs.getDouble("total_amount")
                    ));
                }
                return purchases;
            }
            catch(SQLException e){
                System.out.println("Error in fetching purchases by date");
                e.printStackTrace();
            }
            return null;
    }

    @Override
    public List<Purchase> getPurchasesBySupplierId(int supplierId) {
        String query = "SELECT * FROM purchases WHERE supplier_id = ?";
        try(Connection connection = DatabaseConfig.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query)){
                preparedStatement.setInt(1, supplierId);
                ResultSet rs = preparedStatement.executeQuery();
                List<Purchase> purchases = new ArrayList<>();
                while(rs.next()){
                    purchases.add(new Purchase(
                        rs.getInt("purchase_id"),
                        rs.getInt("supplier_id"),
                        rs.getTimestamp("purchase_date"),
                        rs.getDouble("total_amount")
                    ));
                }
                return purchases;
            }
            catch(SQLException e){
                System.out.println("Error in fetching purchases by supplier id");
                e.printStackTrace();
            }
            return null;
    }

    @Override
    public List<PurchaseItem> getPurchaseItems(int purchaseId) {
        String query = "SELECT * FROM purchase_items WHERE purchase_id = ?";
        try(Connection connection = DatabaseConfig.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query)){
                preparedStatement.setInt(1, purchaseId);
                ResultSet rs = preparedStatement.executeQuery();
                List<PurchaseItem> purchaseItems = new ArrayList<>();
                while(rs.next()){
                    purchaseItems.add(new PurchaseItem(
                        rs.getInt("purchase_item_id"),
                        rs.getInt("purchase_id"),
                        rs.getInt("product_id"),
                        rs.getDouble("quantity"),
                        rs.getDouble("buying_price"),
                        rs.getDate("expiry_date")
                    ));
                }
                return purchaseItems;
            }
            catch(SQLException e){
                System.out.println("Error in fetching purchase items");
                e.printStackTrace();
            }
            return null;
    }
}
