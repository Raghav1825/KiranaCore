package com.kiranacore;

import java.util.*;
import com.kiranacore.service.*;
import com.kiranacore.utils.SessionManager;
import com.kiranacore.model.*;
import com.kiranacore.dao.*;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);

    private static final AuthService authService = new AuthService();
    private static final CheckoutService checkoutService = new CheckoutService();
    private static final CustomerService customerService = new CustomerService();
    private static final InventoryService inventoryService = new InventoryService();

    private static User loggedInUser = null;

    public static void main(String[] args) {
        System.out.println("==============================");
        System.out.println("  Welcome to KiranaCore Terminal   ");
        System.out.println("==============================\n");

        while (true) {
            if(loggedInUser!=null){
                showAuthMenu();
            }else{
                showMainMenu();
            }
            
        }
    }

    private static void showAuthMenu(){
        System.out.println("1. Login");
        System.out.println("2. Exit System");
        System.out.print("Choose an option: ");
        String choice = scanner.nextLine();

        switch(choice){
            case "1":
                System.out.print("Enter username: ");
                String username = scanner.nextLine();
                System.out.print("Enter Password: ");
                String password = scanner.nextLine();

                boolean isLoginSuccessful = authService.login(username, password);
                if(isLoginSuccessful){
                    loggedInUser = SessionManager.getCurrentUser();
                    System.out.println("\nLogin Successful!!");
                }
                else{
                    System.out.println("\nLogin Failed. Please check your credentials.");
                }
                break;
            case "2":
                System.out.println("Shutting down KiranaCore.... Goodbye!!");
                scanner.close();
                System.exit(0);
                break;
            default:
                System.out.println("Invalid choice. Please press 1 or 2");
                break;
        }
        
    }

    private static void showMainMenu(){
        System.out.println("\n-------- Main Dashboard --------");
        System.out.println("1. New Sale(Checkout)");
        System.out.println("2. Record khata payment(Customer Ledger)");
        System.out.println("3. Inventory Management");
        System.out.println("4. New Purchase");
        System.out.println("5. Logout");
        System.out.print("Select Operation: ");
        String choice = scanner.nextLine();
        switch(choice){
            case "1":
                handleCheckOutFlow();
                break;
            case "2":
                handleKhataPayment();
                break;
            case "3":
                handleInventoryManagement();
                break;
            case "4":
                handlePurchaseFlow();
                break;
            case "5":
                loggedInUser = null;
                System.out.println("\nLogged out Successfully\n");
                break;
            default:
                System.out.println("Invalid choice. Please select between 1 to 5");
                break;
        }
    }

    private static void handleCheckOutFlow(){
        System.out.println("\n----New Sale----");

        ProductDAO productDAO = new ProductDAOImpl();
        CustomerDAO customerDAO = new CustomerDAOImpl();

        System.out.println("\n----Product Catalog----");
        List<Product> allProducts = productDAO.getAllProducts();

        if(allProducts.isEmpty()){
            System.out.println("No products available in the inventory");
            return;
        }

        for(Product product:allProducts){
            System.out.println("ID: "+product.getProductId()+" | Name: "+product.getName()+" | Unit: "+product.getUnit()+" | Price: Rs."+product.getSellingPrice());
        }
        System.out.println("-------------------------------------------");

        List<SaleItem> cart = new ArrayList<>();
        double totalAmount=0.0;

        while(true){
            System.out.print("\nEnter Product ID (or type '0' to finish): ");
            int productID = Integer.parseInt(scanner.nextLine());

            if(productID==0){
                break;
            }

            Product product = productDAO.getProductById(productID);
            if(product==null){
                System.out.println("Product not found");
                continue;
            }

            System.out.print("Enter quantity for "+product.getName()+": ");
            double quantity = scanner.nextDouble();
            SaleItem item = new SaleItem();
            item.setProductId(productID);
            item.setQuantity(quantity);
            item.setPriceAtSale(product.getSellingPrice()*quantity);
            cart.add(item);
            totalAmount+=item.getPriceAtSale();
            System.out.println("Added: "+quantity+"x "+product.getName()+" | Subtotal: Rs."+item.getPriceAtSale());
        }
        if(cart.isEmpty()){
            System.out.println("Sale cancelled. Cart is empty");
            return;
        }

        System.out.println("\nTotal bill amount: Rs."+totalAmount);
        System.out.print("Enter customer phone number(Leave blanck for walk-in customer): ");
        String phoneNo = scanner.nextLine();

        int customerID=0;
        if(!phoneNo.isBlank()){
            Customer customer = customerDAO.getCustomerByPhone(phoneNo);
            if(customer!=null){
                customerID=customer.getCustomerId();
                System.out.println("Linked to customer: "+customer.getName());

                System.out.print("Add this to khata(credit)? (y/n): ");
                if(scanner.nextLine().equalsIgnoreCase("y")){
                    customerDAO.updateCustomerKhata(customerID, totalAmount);
                    System.out.println("Added Rs."+totalAmount+" to "+customer.getName()+"'s khata.");
                }
            }else{
                System.out.println("Customer not found.Processing as walk-in customer");
            }
        }
        Sale newSale = new Sale();
        newSale.setCustomerId(customerID);
        newSale.setTotalAmount(totalAmount);
        newSale.setUserId(loggedInUser.getUserId());
        checkoutService.processSale(newSale, cart);
        System.out.println("\n------Sale recorded successfully------");
    }

    private static void handleKhataPayment(){
        System.out.println("\n----Record Khata Payment----");
        System.out.print("Enter customer phone number: ");
        String phone = scanner.nextLine();
        System.out.print("Enter payment amount: ");
        double amount = scanner.nextDouble();
        boolean isSuccess = customerService.processKhataPayment(phone, amount);
        if(isSuccess){
            System.out.println("Payment recorded successfully");
        }
        else{
            System.out.println("Payment recording failed");
        }
    }

    private static void handleInventoryManagement(){
        System.out.println("\n-----Inventory Management-----");
        System.out.println("1.View low stock alerts");
        System.out.println("2. Add new product");
        System.out.println("3. Back to main menu");
        System.out.print("Select an option: ");
        String choice = scanner.nextLine();
        switch(choice){
            case "1":
                inventoryService.displayLowStockAlert();
                break;
            case "2":
                System.out.println("\n----Add new product----");
                System.out.print("Enter product name: ");
                String name = scanner.nextLine();
                System.out.println("\n-----Available Categories-----");
                List<Category> categories = inventoryService.getAllCategories();
                for(Category cat: categories){
                    System.out.println("ID: "+cat.getCategoryId()+" | Name: "+cat.getCategoryName());
                }
                System.out.print("Select category id from above list: ");
                int categoryId = Integer.parseInt(scanner.nextLine());
                
                System.out.print("Enter Unit (e.g., PIECE, PACKET, KG): ");
                String unit = scanner.nextLine().toUpperCase();
                System.out.print("Enter MRP (Maximum retail price): ");
                double mrp = Double.parseDouble(scanner.nextLine());
                System.out.print("Enter selling price: ");
                double sellingPrice =  Double.parseDouble(scanner.nextLine());
                System.out.print("Enter Initial Stock quantity: ");
                double stock = Double.parseDouble(scanner.nextLine());

                Product newProduct = new Product();
                newProduct.setName(name);
                newProduct.setCategoryId(categoryId);
                newProduct.setUnit(unit);
                newProduct.setMrp(mrp);
                newProduct.setSellingPrice(sellingPrice);
                newProduct.setCurrentStock(stock);

                inventoryService.addNewProductToInventory(newProduct)
                System.out.println("---Product added successfully---");
                break;
            case "3":
                break;
            default:
                System.out.println("Invalid choice. Please select between 1 to 3");
                break;
        }
    }

}
