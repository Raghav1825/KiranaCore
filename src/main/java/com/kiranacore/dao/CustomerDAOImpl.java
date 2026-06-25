package com.kiranacore.dao;

import com.kiranacore.config.DatabaseConfig;
import com.kiranacore.model.Customer;
import java.sql.*;
import java.util.*;

public class CustomerDAOImpl implements CustomerDAO {
    
    @Override
    public void addCustomer(Customer customer){
        String query = "INSERT INTO customers(name,phone,khata_balance) VALUES (?,?,?)";
        try(Connection connection = DatabaseConfig.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query)){
            preparedStatement.setString(1, customer.getName());
            preparedStatement.setString(2, customer.getPhone());
            preparedStatement.setDouble(3, customer.getKhataBalance());
            preparedStatement.executeUpdate();
        }catch(SQLException e){
            System.out.println("Error in adding customer");
            e.printStackTrace();
        }
    }

    @Override
    public void updateCustomer(int customerId, String name,String phone){
        String query = "UPDATE customers SET name = ? , phone = ? WHERE customer_id = ?";
        try(Connection connection = DatabaseConfig.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query)){
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, phone);
            preparedStatement.setInt(3, customerId);
            preparedStatement.executeUpdate();
        }catch(SQLException e){
            System.out.println("Error in updating customer");
            e.printStackTrace();
        }
    }

    @Override
    public void updateCustomerKhata(int customerId, double amount){
        String query = "UPDATE customers SET khata_balance = khata_balance + ? WHERE customer_id = ?";
        try(Connection connection = DatabaseConfig.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query)){
            preparedStatement.setDouble(1, amount);
            preparedStatement.setInt(2, customerId);
            preparedStatement.executeUpdate();
        }catch(SQLException e){
            System.out.println("Error in updating customer khata");
            e.printStackTrace();
        }
    }

    @Override
    public void deleteCustomer(int customerId){
        String query = "DELETE FROM customers WHERE customer_id = ?";
        try(Connection connection = DatabaseConfig.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query)){
            preparedStatement.setInt(1, customerId);
            preparedStatement.executeUpdate();
        }catch(SQLException e){
            System.out.println("Error in deleting customer");
            e.printStackTrace();
        }
    }

    @Override
    public Customer getCustomerById(int customerId){
        String query = "SELECT * FROM customers WHERE customer_id = ?";
        try(Connection connection = DatabaseConfig.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query)){
            preparedStatement.setInt(1, customerId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                return new Customer(resultSet.getInt("customer_id"), resultSet.getString("name"), resultSet.getString("phone"), resultSet.getDouble("khata_balance"));
            }
        }catch(SQLException e){
            System.out.println("Error in getting customer by id");
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Customer getCustomerByPhone(String phoneNumber){
        String query = "SELECT * FROM customers WHERE phone = ?";
        try(Connection connection = DatabaseConfig.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query)){
            preparedStatement.setString(1, phoneNumber);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                return new Customer(resultSet.getInt("customer_id"), resultSet.getString("name"), resultSet.getString("phone"), resultSet.getDouble("khata_balance"));
            }
        }catch(SQLException e){
            System.out.println("Error in getting customer by phone");
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Customer> getAllCustomers(){
        String query = "SELECT * FROM customers";
        try(Connection connection = DatabaseConfig.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query)){
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Customer> customers = new ArrayList<>();
            while(resultSet.next()){
                customers.add(new Customer(resultSet.getInt("customer_id"), resultSet.getString("name"), resultSet.getString("phone"), resultSet.getDouble("khata_balance")));
            }
            return customers;
        }catch(SQLException e){
            System.out.println("Error in getting all customers");
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void reduceCustomerKhata(int customerId, double amount){
        String query = "UPDATE customers SET khata_balance = khata_balance - ? WHERE customer_id = ?";
        try(Connection connection = DatabaseConfig.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query)){
            preparedStatement.setDouble(1, amount);
            preparedStatement.setInt(2, customerId);
            preparedStatement.executeUpdate();
        }catch(SQLException e){
            System.out.println("Error in reducing customer khata");
            e.printStackTrace();
        }
    }
}
