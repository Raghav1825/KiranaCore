package com.kiranacore.dao;

import com.kiranacore.model.Sale;
import com.kiranacore.model.SaleItem;
import com.kiranacore.config.DatabaseConfig;
import java.util.List;
import java.util.ArrayList;
import java.sql.*;

public class SaleDAOImpl implements SaleDAO {
    @Override
    public void recordNewSale(Sale sale,List<SaleItem> items){
        String querySale = "INSERT INTO sales (customer_id,user_id,sale_date,total_amount,discount_applied,payment_mode) VALUES (?,?,?,?,?,?)";
        String querySaleItem = "INSERT INTO sale_items (sale_id, product_id, quantity, price_at_sale) VALUES (?,?,?,?)";

        try(Connection connection = DatabaseConfig.getConnection()){
                connection.setAutoCommit(false);
                try(PreparedStatement preparedStatement = connection.prepareStatement(querySale,Statement.RETURN_GENERATED_KEYS)){
                    if (sale.getCustomerId() == 0) {
                        preparedStatement.setNull(1, java.sql.Types.INTEGER);
                    } else {
                        preparedStatement.setInt(1, sale.getCustomerId());
                    }
                    preparedStatement.setInt(2, sale.getUserId());
                    preparedStatement.setTimestamp(3, sale.getSaleDate());
                    preparedStatement.setDouble(4, sale.getTotalAmount());
                    preparedStatement.setDouble(5, sale.getDiscountApplied());
                    preparedStatement.setString(6, sale.getPaymentMode());

                    int affectedRows = preparedStatement.executeUpdate();
                    if(affectedRows == 0){
                        throw new SQLException("Creating sale failed, no rows affected.");
                    }
                    try(ResultSet generatedKeys = preparedStatement.getGeneratedKeys()){
                        if(generatedKeys.next()){
                            sale.setSaleId(generatedKeys.getInt(1));
                        }
                    }
                    if (items != null && !items.isEmpty()) {
                        try (PreparedStatement itemStatement = connection.prepareStatement(querySaleItem)) {
                            for (SaleItem item : items) {
                                itemStatement.setInt(1, sale.getSaleId());
                                itemStatement.setInt(2, item.getProductId());
                                itemStatement.setDouble(3, item.getQuantity());
                                itemStatement.setDouble(4, item.getPriceAtSale());
                                itemStatement.addBatch();
                            }
                            itemStatement.executeBatch();
                        }
                    }
                    connection.commit();
                }
                    catch(SQLException e){
                        System.out.println("Error during sale processing. Rolling back transaction...");
                        connection.rollback();
                        e.printStackTrace();
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
                System.out.println("Error in inserting sale");
                e.printStackTrace();
            }
        }
    

    @Override
    public Sale getSaleById(int saleId){
        String query = "SELECT * FROM sales WHERE sale_id = ?";
        Sale sale = null;
        
        try(Connection connection = DatabaseConfig.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query)){
                preparedStatement.setInt(1, saleId);
                try(ResultSet resultSet = preparedStatement.executeQuery()){
                    if(resultSet.next()){
                        sale = new Sale();
                        sale.setSaleId(resultSet.getInt("sale_id"));
                        sale.setSaleDate(resultSet.getTimestamp("sale_date"));
                        sale.setCustomerId(resultSet.getInt("customer_id"));
                        sale.setTotalAmount(resultSet.getDouble("total_amount"));
                        sale.setDiscountApplied(resultSet.getDouble("discount_applied"));
                        sale.setPaymentMode(resultSet.getString("payment_mode"));
                        sale.setUserId(resultSet.getInt("user_id"));
                    }
                }catch(SQLException e){
                    System.out.println("Error in getting sale");
                    e.printStackTrace();
                }
            }
            catch(SQLException e){
                System.out.println("Error in getting sale information");
                e.printStackTrace();
            }
            return sale;
        }
    
    @Override
    public List<Sale> getAllSales(){
        String query = "SELECT * FROM sales";
        List<Sale> sales = new ArrayList<>();
        
        try(Connection connection = DatabaseConfig.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query)){
                try(ResultSet resultSet = preparedStatement.executeQuery()){
                    while(resultSet.next()){
                        Sale sale = new Sale();
                        sale.setSaleId(resultSet.getInt("sale_id"));
                        sale.setSaleDate(resultSet.getTimestamp("sale_date"));
                        sale.setCustomerId(resultSet.getInt("customer_id"));
                        sale.setTotalAmount(resultSet.getDouble("total_amount"));
                        sale.setDiscountApplied(resultSet.getDouble("discount_applied"));
                        sale.setPaymentMode(resultSet.getString("payment_mode"));
                        sale.setUserId(resultSet.getInt("user_id"));
                        sales.add(sale);
                    }
                }catch(SQLException e){
                    System.out.println("Error in getting all sales");
                    e.printStackTrace();
                }
            }
            catch(SQLException e){
                System.out.println("Error in getting all sale information");
                e.printStackTrace();
            }
            return sales;
    }

    @Override
    public List<Sale> getSaleByDate(String date){

        String query = "SELECT * FROM sales WHERE DATE(sale_date) = ?";
        List<Sale> sales = new ArrayList<>();
        
        try(Connection connection = DatabaseConfig.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query)){
                preparedStatement.setString(1, date);
                try(ResultSet resultSet = preparedStatement.executeQuery()){
                    while(resultSet.next()){
                        Sale sale = new Sale();
                        sale.setSaleId(resultSet.getInt("sale_id"));
                        sale.setSaleDate(resultSet.getTimestamp("sale_date"));
                        sale.setCustomerId(resultSet.getInt("customer_id"));
                        sale.setTotalAmount(resultSet.getDouble("total_amount"));
                        sale.setDiscountApplied(resultSet.getDouble("discount_applied"));
                        sale.setPaymentMode(resultSet.getString("payment_mode"));
                        sale.setUserId(resultSet.getInt("user_id"));
                        sales.add(sale);
                    }
                }catch(SQLException e){
                    System.out.println("Error in getting sale by date");
                    e.printStackTrace();
                }
            }
            catch(SQLException e){
                System.out.println("Error in getting sale by date information");
                e.printStackTrace();
            }
            return sales;
    }

    @Override
    public List<Sale> getSaleByCustomerId(int customerId){
        String query = "SELECT * FROM sales WHERE customer_id = ?";
        List<Sale> sales = new ArrayList<>();
        
        try(Connection connection = DatabaseConfig.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query)){
                preparedStatement.setInt(1, customerId);
                try(ResultSet resultSet = preparedStatement.executeQuery()){
                    while(resultSet.next()){
                        Sale sale = new Sale();
                        sale.setSaleId(resultSet.getInt("sale_id"));
                        sale.setSaleDate(resultSet.getTimestamp("sale_date"));
                        sale.setCustomerId(resultSet.getInt("customer_id"));
                        sale.setTotalAmount(resultSet.getDouble("total_amount"));
                        sale.setDiscountApplied(resultSet.getDouble("discount_applied"));
                        sale.setPaymentMode(resultSet.getString("payment_mode"));
                        sale.setUserId(resultSet.getInt("user_id"));
                        sales.add(sale);
                    }
                }catch(SQLException e){
                    System.out.println("Error in getting sale by customer id");
                    e.printStackTrace();
                }
            }
            catch(SQLException e){
                System.out.println("Error in getting sale by customer id information");
                e.printStackTrace();
            }
            return sales;
    }


    @Override
    public List<SaleItem> getAllSaleItemsBySaleId(int saleId){
        String query = "SELECT * FROM sale_items WHERE sale_id=?";
        List<SaleItem> saleItems = new ArrayList<>();
        
        try(Connection connection = DatabaseConfig.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query)){
                preparedStatement.setInt(1, saleId);
                try(ResultSet resultSet = preparedStatement.executeQuery()){
                    while(resultSet.next()){
                        SaleItem saleItem = new SaleItem();
                        saleItem.setSaleItemId(resultSet.getInt("sale_item_id"));
                        saleItem.setSaleId(resultSet.getInt("sale_id"));
                        saleItem.setProductId(resultSet.getInt("product_id"));
                        saleItem.setPriceAtSale(resultSet.getDouble("price_at_sale"));
                        saleItem.setQuantity(resultSet.getDouble("quantity"));;
                        saleItems.add(saleItem);
                    }
                }catch(SQLException e){
                    System.out.println("Error in getting sale items by sale id");
                    e.printStackTrace();
                }
            }
            catch(SQLException e){
                System.out.println("Error in getting sale items by sale id information");
                e.printStackTrace();
            }
            return saleItems;
    }
}
