package com.kiranacore.service;

import com.kiranacore.dao.*;
import com.kiranacore.model.*;
import com.kiranacore.utils.*;

public class AuthService {
    private UserDAO userDAO = new UserDAOImpl();

    public boolean login(String username,String simplePassword){
        try{
            User user = userDAO.getUserByUsername(username);
            if(user==null){
                System.out.println("Login failed: User not found");
                return false;
            }

            boolean isPasswordMatch = PasswordUtils.verifyPassword(simplePassword,user.getPasswordHash());
            if(!isPasswordMatch){
                System.out.println("Login failed: Wrong password");
                return false;
            }

            SessionManager.loginUser(user);
            System.out.println("Login successful for user: " + username);
            return true;
        }
        catch(Exception e){
            System.out.println("Login failed: Internal error");
            e.printStackTrace();
            return false;
        }
    }

    public void logout(){
        if(SessionManager.getCurrentUser()==null){
            System.out.println("No user is currently logged in.");
            return;
        }
        System.out.println("User: "+SessionManager.getCurrentUser().getUsername()+" is logging out.");
        SessionManager.logoutUser();
        return;
    }

    public boolean registerNewUser(String userName,String simplePassword,String role){
        try{
            if(!SessionManager.isAdmin()){
                System.out.println("Access denied: Only admin can register new member.");
                return false;
            }

            User newUser =  new User();
            newUser.setUsername(userName);
            newUser.setPasswordHash(simplePassword);
            newUser.setRole(role.toUpperCase());

            userDAO.registerUser(newUser);
            System.out.println("User: "+userName+" registered successfully as "+role);
            return true;
        }
        catch(Exception e){
            System.out.println("Registration failed: Internal error");
            e.printStackTrace();
            return false;
        }
    }
}
