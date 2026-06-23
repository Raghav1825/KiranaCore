package com.kiranacore.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.io.ObjectInputFilter.Config;
import java.sql.Connection;
import java.sql.SQLException;
import io.github.cdimascio.dotenv.Dotenv;

public class DatabaseConfig {
    private static HikariDataSource dataSource;

    static{
        Dotenv dotenv = Dotenv.configure()
                        .ignoreIfMissing()
                        .load();

        HikariConfig config =  new HikariConfig();
        
        String dbUrl=dotenv.get("DB_URL");
        String dbUser=dotenv.get("DB_USER");
        String dbPassword=dotenv.get("DB_PASSWORD");

        config.setJdbcUrl(dbUrl);
        config.setUsername(dbUser);
        config.setPassword(dbPassword);

        config.setMaximumPoolSize(10);
        config.setMinimumIdle(2);

        dataSource = new HikariDataSource(config);
    }

    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }
    
}
