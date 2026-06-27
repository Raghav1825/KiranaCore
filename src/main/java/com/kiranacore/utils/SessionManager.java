package com.kiranacore.utils;

import com.kiranacore.model.User;

public class SessionManager {
    private static User currentUser;

    public static void loginUser(User user){
        currentUser = user;
    }
    public static void logoutUser(){
        currentUser = null;
    }
    public static User getCurrentUser(){
        return currentUser;
    }

    public static boolean isAdmin(){
        return currentUser != null && currentUser.isAdmin();
    }
}
