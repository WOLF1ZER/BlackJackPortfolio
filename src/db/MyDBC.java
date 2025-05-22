package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import constants.CommonConstants;

public class MyDBC {
    public static boolean register(String username, String password) {
        try {
            if (!checkUser(username)) {
                Connection connection = DriverManager.getConnection(CommonConstants.DB_URL, CommonConstants.DB_USERNAME,
                        CommonConstants.DB_PASSWORD);

                PreparedStatement insertUser = connection.prepareStatement(
                        "INSERT INTO " + CommonConstants.DB_USERS_TABLE_NAME + "(username, password)" + "VALUES(?, ?)");
                insertUser.setString(1, username);
                insertUser.setString(2, password);
                insertUser.executeUpdate();
                return true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }

    public static boolean checkUser(String username) {
        try {
            Connection connection = DriverManager.getConnection(CommonConstants.DB_URL, CommonConstants.DB_USERNAME,
                    CommonConstants.DB_PASSWORD);

            PreparedStatement checkUserExists = connection
                    .prepareStatement("SELECT * FROM " + CommonConstants.DB_USERS_TABLE_NAME + " WHERE USERNAME = ?");
            checkUserExists.setString(1, username);
            ResultSet resultSet = checkUserExists.executeQuery();
            if (!resultSet.isBeforeFirst()) {
                return false;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    public static boolean validateLogin(String username, String password){
        try{
            Connection connection = DriverManager.getConnection(CommonConstants.DB_URL, CommonConstants.DB_USERNAME,
            CommonConstants.DB_PASSWORD);
            PreparedStatement validateUser = connection.prepareStatement("SELECT * FROM "+ CommonConstants.DB_USERS_TABLE_NAME + " WHERE USERNAME = ? AND PASSWORD = ?");
            validateUser.setString(1, username);
            validateUser.setString(2, password);
            ResultSet resultSet = validateUser.executeQuery();
            if(!resultSet.isBeforeFirst()){
                return false;
            }

        }catch(SQLException e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static int getVictories(String username) {
        int victories = 0;
        try {
            Connection connection = DriverManager.getConnection(CommonConstants.DB_URL, CommonConstants.DB_USERNAME,
                    CommonConstants.DB_PASSWORD);
    
            PreparedStatement getVictories = connection.prepareStatement(
                    "SELECT victories FROM " + CommonConstants.DB_USERS_TABLE_NAME + " WHERE USERNAME = ?");
            getVictories.setString(1, username);
            ResultSet resultSet = getVictories.executeQuery();

    
            if (resultSet.next()) {
                victories = resultSet.getInt("victories");
                System.out.println("Retrieved victories: " + victories);
            }else{
                System.out.println("No user found with username: " + username);
            }
    
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return victories;
    }
    
    public static void updateVictories(String username) {
        try {
            Connection connection = DriverManager.getConnection(CommonConstants.DB_URL, CommonConstants.DB_USERNAME,
                    CommonConstants.DB_PASSWORD);
    
            String query = "UPDATE " + CommonConstants.DB_USERS_TABLE_NAME + " SET victories = victories + 1 WHERE username = ?";
            PreparedStatement updateVictories = connection.prepareStatement(query);
            updateVictories.setString(1, username);
            updateVictories.executeUpdate();
    
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    //to show users from db into the table
    public static Object[][] fetchTopPlayers() {
        try {
            Connection connection = DriverManager.getConnection(CommonConstants.DB_URL, CommonConstants.DB_USERNAME,
                    CommonConstants.DB_PASSWORD);

            PreparedStatement fetchTopPlayers = connection.prepareStatement(
                "SELECT username, victories FROM " + CommonConstants.DB_USERS_TABLE_NAME + " ORDER BY victories DESC LIMIT 10"
            );
            ResultSet rs = fetchTopPlayers.executeQuery();

            Object[][] result = new Object[10][2]; //array with 10 rows and 2 columns
            int index = 0;

            while (rs.next() && index < 10) {
                result[index][0] = rs.getString("username").toUpperCase(); 
                result[index][1] = rs.getInt("victories");  
                index++;
            }
    
            if (index < 10) {
                Object[][] trimmed = new Object[index][2];
                System.arraycopy(result, 0, trimmed, 0, index);
                return trimmed;
            }
    
            return result;
    
        } catch (SQLException e) {
            e.printStackTrace();
            return new Object[0][0];
        }
    }

    //deleting account 
    public static boolean deleteAccount(String username, String password) {
        try {
            Connection connection = DriverManager.getConnection(CommonConstants.DB_URL, CommonConstants.DB_USERNAME,
                    CommonConstants.DB_PASSWORD);
    
            PreparedStatement deleteUser = connection.prepareStatement(
                    "DELETE FROM " + CommonConstants.DB_USERS_TABLE_NAME + " WHERE username = ? AND password = ?");
            deleteUser.setString(1, username);
            deleteUser.setString(2, password);
            int deleted = deleteUser.executeUpdate();
    
            return deleted > 0; // true if user was deleted
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
        

}
