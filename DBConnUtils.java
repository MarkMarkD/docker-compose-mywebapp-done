/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.transasia.mvnproject1.model;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

/**
 *
 * @author banashko.dv
 */
public class DBConnUtils {
    public static Connection getConnection() throws ClassNotFoundException, SQLException {

        //--------------------------------------------------------------
//        Properties prop = new Properties();
//        try {
//                String resourceName = "config.properties"; // could also be a constant
//                ClassLoader loader = Thread.currentThread().getContextClassLoader();
//                
//                InputStream resourceStream = loader.getResourceAsStream(resourceName); 
//                prop.load(resourceStream);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        String url = prop.getProperty("url");
//        String name = prop.getProperty("name");
//        String pass = prop.getProperty("password");
//        Class.forName("org.h2.Driver");
//        Connection conn = DriverManager.getConnection(url, name, pass);
//---------------------------------------------------------------------

        //old version without .properties
        String url = "jdbc:postgresql://dockerfirstimage_mypostgres_1:5432/mytestdb";
        String name = "postgres";
        String pass = "mysecretpassword";
        Class.forName("org.postgresql.Driver");
        Connection conn = DriverManager.getConnection(url, name, pass);

        
        //Connection conn = DriverManager.getConnection("jdbc:h2:~/test", "test", "" );
        
        return conn;
       
    }

    public static void closeQuietly(Connection conn) {
        try {
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

