package com.kiranacore.dao;

import com.kiranacore.model.Customer;
import java.util.List;

public interface CustomerDAO {
    void addCustomer(Customer customer);
    void updateCustomer(int customerId, String name,String phone);
    void updateCustomerKhata(int customerId, double amount);
    void deleteCustomer(int customerId);
    Customer getCustomerById(int customerId);
    Customer getCustomerByPhone(String phoneNumber);
    List<Customer> getAllCustomers();
    void reduceCustomerKhata(int customerId, double amount);
}
