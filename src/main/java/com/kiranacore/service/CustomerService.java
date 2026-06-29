package com.kiranacore.service;

import com.kiranacore.dao.*;
import com.kiranacore.model.*;

public class CustomerService {
    
    private CustomerDAO customerDAO = new CustomerDAOImpl();

    public boolean registerCustomer(String name,String phoneNumber){
        if(name==null || name.trim().isEmpty()){
            System.out.println("Name is required");
            return false;
        }

        if(phoneNumber==null || phoneNumber.trim().isEmpty()){
            System.out.println("Phone number is required");
            return false;
        }

        try{
            if(customerDAO.getCustomerByPhone(phoneNumber)!=null){
                System.out.println("Customer already exists with phone number: " + phoneNumber);
                return false;
            }

            Customer customer = new Customer();
            customer.setName(name);
            customer.setPhone(phoneNumber);
            customer.setKhataBalance(0.0);
            customerDAO.addCustomer(customer);
            System.out.println("Customer: "+name+" registered successfully");
            return true;
        }
        catch(Exception e){
            System.out.println("Error in registering customer");
            e.printStackTrace();
            return false;
        }
    }
    
    public void viewCustomerKhata(String phoneNumber){
        Customer customer = customerDAO.getCustomerByPhone(phoneNumber);

        if(customer==null){
            System.out.println("No customer found with phone number: " + phoneNumber);
            return;
        }
        System.out.println("--------------Khata Details--------------");
        System.out.println("Customer Name: " + customer.getName());
        System.out.println("Phone Number: " + customer.getPhone());
        System.out.println("Khata Balance: " + customer.getKhataBalance());
        System.out.println("-------------------END-------------------");
    }

    public boolean processKhataPayment(String phone, double paymentAmount){
        if(paymentAmount<=0){
            System.out.println("Payment amount must be greater than 0");
            return false;
        }

        try{
            Customer customer = customerDAO.getCustomerByPhone(phone);
            if(customer==null){
                System.out.println("No customer found with phone number: " + phone);
                return false;
            }

            if(customer.getKhataBalance()<=0){
                System.out.println("Customer: "+customer.getName()+ " has no debt");
                return false;
            }

            double actualDeduction = paymentAmount;
            if (paymentAmount > customer.getKhataBalance()) {
                System.out.println("Notice: Payment exceeds outstanding balance.");
                System.out.println("Please return Rs. " + (paymentAmount - customer.getKhataBalance()) + " in change.");
                actualDeduction = customer.getKhataBalance(); 
            }

            customerDAO.reduceCustomerKhata(customer.getCustomerId(), actualDeduction);
            System.out.println("Payment processed successfully!");
            System.out.println("Remaining Debt: " + (customer.getKhataBalance() - actualDeduction));
            return true;
        }
        catch(Exception e){
            System.out.println("Error processing payment for reducing khata balance");
            e.printStackTrace();
            return false;
        }
    }
}
