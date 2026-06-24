package com.kiranacore.dao;

import com.kiranacore.model.User;
import java.util.*;

public interface UserDAO {
    
    void registerUser(User user);

    User getUserById(int userId);
    User getUserByUsername(String username);

    void updatePassword(int userId,String newPassword,String oldPassword);
    void deleteUser(int userId);

    List<User> getAllUsers();
}
