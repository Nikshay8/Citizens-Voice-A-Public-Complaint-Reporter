package UI;

import java.util.Properties;
import java.io.FileInputStream;
import java.sql.*;
import javax.swing.JOptionPane;

public class DatabaseConnection {

    private static String URL;
    private static String USER;
    private static String PASSWORD;

    static {
        try {
            Properties props = new Properties();
            FileInputStream fis = new FileInputStream("config.properties");
            props.load(fis);
            URL = props.getProperty("db.url");
            USER = props.getProperty("db.username");
            PASSWORD = props.getProperty("db.password");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Connection utility
    public static Connection getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null,
                    "MySQL JDBC Driver not found.\nPlease ensure connector JAR is added.",
                    "Connection Error", JOptionPane.ERROR_MESSAGE);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,
                    "Unable to connect to database.\nPlease check your connection settings.",
                    "Database Error", JOptionPane.ERROR_MESSAGE);
        }
        return null;
    }

    // Existing: Register user with email
    public static boolean registerUser(String fullName, String username, String password, String email) {
        String query = "INSERT INTO users (fullname, username, password, email) VALUES (?, ?, ?, ?)";
        try (Connection con = getConnection();
             PreparedStatement pst = con.prepareStatement(query)) {

            if (con == null) return false;

            pst.setString(1, fullName);
            pst.setString(2, username);
            pst.setString(3, password);
            pst.setString(4, email);
            pst.executeUpdate();
            return true;

        } catch (SQLIntegrityConstraintViolationException e) {
            JOptionPane.showMessageDialog(null,
                    "Username or Email already exists. Please choose another.",
                    "Sign Up Error", JOptionPane.WARNING_MESSAGE);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,
                    "Error while registering user. Please try again.",
                    "Database Error", JOptionPane.ERROR_MESSAGE);
        }
        return false;
    }

    // Validate exisiting login credentials
    public static String validateLogin(String username, String email, String password) {
        String query = "SELECT fullname FROM users WHERE username = ? AND email = ? AND password = ?";
        try (Connection con = getConnection();
             PreparedStatement pst = con.prepareStatement(query)) {

            if (con == null) return null;

            pst.setString(1, username);
            pst.setString(2, email);
            pst.setString(3, password);

            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                return rs.getString("fullname");
            } else {
                JOptionPane.showMessageDialog(null,
                        "Invalid Username, Email, or Password.",
                        "Login Failed", JOptionPane.WARNING_MESSAGE);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,
                    "Error while verifying credentials.",
                    "Database Error", JOptionPane.ERROR_MESSAGE);
        }
        return null;
    }

    // COMPLAINT FORM
    public static boolean registerComplaint(
            String category,
            String subCategory,
            String subject,
            String issueDesc,
            String address,
            String landmark,
            String firstName,
            String lastName,
            String mobile,
            String email
    ) {
        String sql = "INSERT INTO Show_Complaints (category, sub_category, complaint_subject, issue_description, address, landmark, first_name, last_name, mobile_number, email) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            if (con == null) return false;

            ps.setString(1, category);
            ps.setString(2, subCategory);
            ps.setString(3, subject);
            ps.setString(4, issueDesc);
            ps.setString(5, address);
            ps.setString(6, landmark);
            ps.setString(7, firstName);
            ps.setString(8, lastName);
            ps.setString(9, mobile);
            ps.setString(10, email);

            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,
                    "Error while recording complaint. Please check all fields.",
                    "Database Error", JOptionPane.ERROR_MESSAGE);
        }
        return false;
    }


    // FEEDBACK FORM
    public static boolean registerFeedback(
            String name,
            String email,
            String issueType,
            int rating,
            String message
    ) {
        String sql = "INSERT INTO feedback (user_name, email, issue_type, rating, message) " +
                "VALUES (?, ?, ?, ?, ?)";

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            if (con == null) return false;

            ps.setString(1, name);
            ps.setString(2, email);
            ps.setString(3, issueType);
            ps.setInt(4, rating);
            ps.setString(5, message);

            ps.executeUpdate();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,
                    "Error while saving feedback. Please try again.",
                    "Database Error", JOptionPane.ERROR_MESSAGE);
        }
        return false;
    }
}
