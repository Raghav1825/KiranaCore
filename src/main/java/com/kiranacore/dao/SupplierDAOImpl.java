package com.kiranacore.dao;

import com.kiranacore.model.Supplier;
import com.kiranacore.config.DatabaseConfig;
import java.sql.*;
import java.util.*;

public class SupplierDAOImpl implements SupplierDAO {
    
    @Override
    public void addSupplier(Supplier supplier) {
        String query = "INSERT INTO suppliers (supplier_name, contact_number, balance_due) VALUES (?, ?, ?)";
        try (Connection connection = DatabaseConfig.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, supplier.getSupplierName());
            preparedStatement.setString(2, supplier.getContactNumber());
            preparedStatement.setDouble(3, supplier.getBalanceDue());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error adding supplier");
            e.printStackTrace();
        }
    }

    @Override
    public void updateSupplierDetails(Supplier supplier, String name, String contactNumber){
        String query = "UPDATE suppliers SET supplier_name = ?, contact_number = ? WHERE supplier_id = ?";
        try (Connection connection = DatabaseConfig.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, contactNumber);
            preparedStatement.setInt(3, supplier.getSupplierId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error updating supplier details");
            e.printStackTrace();
        }
    }

    @Override
    public void updateSupplierBalance(int supplierId, double balance){
        String query = "UPDATE suppliers SET balance_due = balance_due+? WHERE supplier_id = ?";
        try(Connection connection = DatabaseConfig.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setDouble(1, balance);
            preparedStatement.setInt(2, supplierId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error updating supplier balance");
            e.printStackTrace();
        }
    }

    @Override
    public void deleteSupplier(int supplierId){
        String query = "DELETE FROM suppliers WHERE supplier_id = ?";
        try(Connection connection = DatabaseConfig.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, supplierId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error deleting supplier");
            e.printStackTrace();
        }
    }

    @Override
    public Supplier getSupplierById(int supplierId){
        String query = "SELECT * FROM suppliers WHERE supplier_id = ?";
        try(Connection connection = DatabaseConfig.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, supplierId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) {
                return new Supplier(
                    resultSet.getInt("supplier_id"),
                    resultSet.getString("supplier_name"),
                    resultSet.getString("contact_number"),
                    resultSet.getDouble("balance_due")
                );
            }
        } catch (SQLException e) {
            System.out.println("Error getting supplier by ID");
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Supplier getSupplierByContactNumber(String contactNumber){
        String query = "SELECT * FROM suppliers WHERE contact_number = ?";
        try(Connection connection = DatabaseConfig.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, contactNumber);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) {
                return new Supplier(
                    resultSet.getInt("supplier_id"),
                    resultSet.getString("supplier_name"),
                    resultSet.getString("contact_number"),
                    resultSet.getDouble("balance_due")
                );
            }
        } catch (SQLException e) {
            System.out.println("Error getting supplier by contact number");
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Supplier> getAllSuppliers(){
        String query = "SELECT * FROM suppliers";
        List<Supplier> suppliers = new ArrayList<>();
        try(Connection connection = DatabaseConfig.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                suppliers.add(new Supplier(
                    resultSet.getInt("supplier_id"),
                    resultSet.getString("supplier_name"),
                    resultSet.getString("contact_number"),
                    resultSet.getDouble("balance_due")
                ));
            }
        } catch (SQLException e) {
            System.out.println("Error getting all suppliers");
            e.printStackTrace();
        }
        return suppliers;
    }

}
