package com.kiranacore.dao;

import com.kiranacore.config.DatabaseConfig;
import com.kiranacore.model.User;
import com.kiranacore.utils.PasswordUtils;
import java.sql.*;
import java.util.*;

public class UserDAOImpl implements UserDAO{

    @Override
    public void registerUser(User user){
        String query= "INSERT INTO users (username,password_hash,role) VALUES (?,?,?)";

        try{
            Connection connection = DatabaseConfig.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            String hashPassword = PasswordUtils.hashPassword(user.getPasswordHash());

            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, hashPassword);
            preparedStatement.setString(3, user.getRole());
            preparedStatement.executeUpdate();
        }catch(SQLException e){
            System.out.println("Error in registering the user");
            e.printStackTrace();
        }
    }

    @Override
    public User getUserByUsername(String username){
        String query = "SELECT user_id,username,role FROM users WHERE username=?";
        User user = null;

        try{
            Connection connection = DatabaseConfig.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                user = new User();
                user.setUserId(resultSet.getInt("user_id"));
                user.setUsername(resultSet.getString("username"));
                user.setRole(resultSet.getString("role"));
            }
        }catch(SQLException e){
            System.out.println("Error in getting the user by username");
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public User getUserById(int userId){
        String query = "SELECT user_id,username,role FROM users WHERE user_id=?";
        User user = null;

        try{
            Connection connection = DatabaseConfig.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setInt(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                user = new User();
                user.setUserId(resultSet.getInt("user_id"));
                user.setUsername(resultSet.getString("username"));
                user.setRole(resultSet.getString("role"));
            }
        }catch(SQLException e){
            System.out.println("Error in getting the user by Id");
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public void updatePassword(int userId,String newPassword,String oldPassword){
        String query = "SELECT password_hash FROM users WHERE user_id=?";

        try{
            Connection connection = DatabaseConfig.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setInt(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                String oldHash = resultSet.getString("password_hash");

                if(PasswordUtils.verifyPassword(oldPassword,oldHash)){
                    String newHash = PasswordUtils.hashPassword(newPassword);
                    String query2 = "UPDATE users SET password_hash=? WHERE user_id=?";

                    try{
                        PreparedStatement preparedStatement2 = connection.prepareStatement(query2);
                        preparedStatement2.setString(1, newHash);
                        preparedStatement2.setInt(2, userId);
                        preparedStatement2.executeUpdate();
                    }catch(SQLException e){
                        System.out.println("Error in updating the password");
                        e.printStackTrace();
                    }
                }
                else{
                    System.out.println("Invalid old password");
                }
            }
        }catch(SQLException e){
            System.out.println("Error in getting the password");
            e.printStackTrace();
        }
    }

    @Override
    public void deleteUser(int userId){
        String query = "DELETE FROM users WHERE user_id=?";

        try{
            Connection connection = DatabaseConfig.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setInt(1, userId);
            preparedStatement.executeUpdate();
        }catch(SQLException e){
            System.out.println("Error in deleting the user");
            e.printStackTrace();
        }
    }

    @Override
    public List<User> getAllUsers(){
        String query = "SELECT user_id,username,role FROM users";
        List<User> userList = new ArrayList<>();

        try{
            Connection connection = DatabaseConfig.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                User user = new User();
                user.setUserId(resultSet.getInt("user_id"));
                user.setUsername(resultSet.getString("username"));
                user.setRole(resultSet.getString("role"));
                userList.add(user);
            }
        }catch(SQLException e){
            System.out.println("Error in getting all the users");
            e.printStackTrace();
        }
        return userList;
    }

}
