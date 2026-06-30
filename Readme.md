# KiranaCore – Enterprise Inventory & Ledger Management System

## Overview
KiranaCore is a robust, console-based retail backend engineered using pure Java and MySQL. It is specifically designed to handle the complex edge cases of a real-world Indian retail shop, including tracking standard inventory, inward purchases, and a custom "Udhaar" (Khata) credit ledger system. By utilizing manual SQL transaction management (commit/rollback), the system guarantees data integrity across multiple tables during complex checkouts, completely eliminating the risk of ghost data.

## Tech Stack
* **Language:** Java
* **Database:** MySQL
* **Database API:** JDBC
* **Connection Pooling:** HikariCP
* **Security:** BCrypt
* **Build Tool:** Maven

## System Architecture
The application is built on a strict DAO/DTO architectural pattern to enforce a clean separation of concerns. 

* **Model Layer (DTOs):** Pure Java data structures acting as temporary data containers moving between the database and the console.
* **DAO Layer (Data Access Object):** Interfaces and implementation classes that isolate all SQL execution and handle purely database logic.
* **Service Layer:** The business logic center that combines multiple DAO calls to perform real-world actions wrapped in SQL transactions.
* **Presentation Layer:** A centralized `Main.java` router handling user inputs via the console and directing them to the appropriate services.

## Core Features
* **High-Performance Database Connections:** Optimized database performance and scalability by integrating the HikariCP connection pooling library, eliminating connection bottlenecks during high-frequency transactions.
* **Khata (Credit) Management:** Designed a custom ledger system to handle local business edge cases, allowing cashiers to register sales on credit and process Khata payments safely.
* **Role-Based Access Control (RBAC):** Implemented strict authorization for Admin and Cashier user sessions, securing authentication by hashing passwords with BCrypt to prevent rainbow table attacks.
* **Real-World Retail Modeling:** Modeled retail constraints such as loose-item fractional quantities (decimals) and dynamic pricing by cross-referencing real-time stock levels with historical purchase data.
* **Transactional Integrity:** Engineered multi-table SQL transactions to safely process sales, dynamically adjust stock, and automatically manage the Udhaar system simultaneously.