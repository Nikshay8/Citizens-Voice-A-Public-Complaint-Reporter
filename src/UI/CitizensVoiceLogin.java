package UI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import UI.AdminDashboard;
import UI.UserDashboard;

public class CitizensVoiceLogin {

    public static void main(String[] args) {

        JFrame frame = new JFrame("Citizen's Voice - Public Complaint Reporter");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setLayout(new BorderLayout());

        JPanel background = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                int w = getWidth();
                int h = getHeight();
                Color c1 = new Color(34, 97, 140);
                Color c2 = new Color(140, 200, 220);
                g2.setPaint(new GradientPaint(0, 0, c1, w, h, c2));
                g2.fillRect(0, 0, w, h);
            }
        };
        background.setLayout(null);

        JLabel title = new JLabel("Citizen's Voice", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 42));
        title.setForeground(Color.WHITE);
        title.setBounds(0, 60, 1920, 60);
        background.add(title);

        JLabel subtitle = new JLabel("Public Complaint Reporter", SwingConstants.CENTER);
        subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 22));
        subtitle.setForeground(Color.WHITE);
        subtitle.setBounds(0, 120, 1920, 36);
        background.add(subtitle);

        JPanel box = new JPanel();
        box.setLayout(null);
        box.setBackground(new Color(255, 255, 255, 235));
        box.setBorder(BorderFactory.createLineBorder(new Color(60, 90, 100), 2, true));
        box.setBounds(700, 260, 500, 500);
        background.add(box);

        JLabel userLabel = new JLabel("Username:");
        userLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        userLabel.setBounds(60, 60, 110, 28);
        box.add(userLabel);

        JTextField userField = new JTextField();
        userField.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        userField.setBounds(180, 60, 240, 32);
        box.add(userField);

        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        emailLabel.setBounds(60, 110, 110, 28);
        box.add(emailLabel);

        JTextField emailField = new JTextField();
        emailField.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        emailField.setBounds(180, 110, 240, 32);
        box.add(emailField);

        JLabel passLabel = new JLabel("Password:");
        passLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        passLabel.setBounds(60, 160, 110, 28);
        box.add(passLabel);

        JPasswordField passField = new JPasswordField();
        passField.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        passField.setBounds(180, 160, 240, 32);
        box.add(passField);

        JButton loginBtn = new JButton("Login");
        loginBtn.setFont(new Font("Segoe UI", Font.BOLD, 18));
        loginBtn.setBackground(new Color(70, 130, 180));
        loginBtn.setForeground(Color.WHITE);
        loginBtn.setFocusPainted(false);
        loginBtn.setBounds(220, 210, 140, 40);
        box.add(loginBtn);

        JLabel newUserLabel = new JLabel("If new user, Sign Up");
        newUserLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        newUserLabel.setForeground(new Color(40, 60, 70));
        newUserLabel.setBounds(220, 270, 200, 22);
        box.add(newUserLabel);

        JButton signupBtn = new JButton("Sign Up");
        signupBtn.setFont(new Font("Segoe UI", Font.BOLD, 16));
        signupBtn.setBackground(new Color(88, 162, 121));
        signupBtn.setForeground(Color.WHITE);
        signupBtn.setFocusPainted(false);
        signupBtn.setBounds(220, 300, 140, 40);
        box.add(signupBtn);

        frame.add(background);
        frame.setVisible(true);

        // LOGIN BUTTON ACTION WITH DASHBOARD LAUNCH
        loginBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = userField.getText().trim();
                String email = emailField.getText().trim();
                String password = new String(passField.getPassword()).trim();

                if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
                    JOptionPane.showMessageDialog(frame,
                            "Please enter Username, Email and Password.",
                            "Login Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Admin override with hardcoded email check
                if (username.equalsIgnoreCase("admin") && password.equals("admin@1234")
                        && email.equalsIgnoreCase("admin@gmail.com")) {
                    frame.dispose();
                    new AdminDashboard("Admin"); // LAUNCHES ADMIN DASHBOARD
                } else {
                    String fullName = DatabaseConnection.validateLogin(username, email, password);
                    if (fullName != null) {
                        frame.dispose();
                        new UserDashboard(fullName, email); // Pass both fullName AND email
                    }
                }
            }
        });

        signupBtn.addActionListener(e -> openSignUpWindow(frame));
    }

    public static void openSignUpWindow(JFrame parent) {
        JFrame signUp = new JFrame("Sign Up - Citizen's Voice");
        signUp.setSize(420, 500);
        signUp.setResizable(false);
        signUp.setLocationRelativeTo(parent);
        signUp.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        signUp.setLayout(null);
        signUp.getContentPane().setBackground(new Color(245, 247, 248));

        JLabel head = new JLabel("Create New Account", SwingConstants.CENTER);
        head.setFont(new Font("Segoe UI", Font.BOLD, 20));
        head.setBounds(60, 18, 300, 30);
        signUp.add(head);

        JLabel nameL = new JLabel("Full Name:");
        nameL.setBounds(40, 70, 120, 24);
        signUp.add(nameL);

        JTextField nameF = new JTextField();
        nameF.setBounds(160, 70, 200, 24);
        signUp.add(nameF);

        JLabel userL = new JLabel("Username:");
        userL.setBounds(40, 120, 120, 24);
        signUp.add(userL);

        JTextField userF = new JTextField();
        userF.setBounds(160, 120, 200, 24);
        signUp.add(userF);

        JLabel emailL = new JLabel("Email:");
        emailL.setBounds(40, 170, 120, 24);
        signUp.add(emailL);

        JTextField emailF = new JTextField();
        emailF.setBounds(160, 170, 200, 24);
        signUp.add(emailF);

        JLabel passL = new JLabel("Password:");
        passL.setBounds(40, 220, 120, 24);
        signUp.add(passL);

        JPasswordField passF = new JPasswordField();
        passF.setBounds(160, 220, 200, 24);
        signUp.add(passF);

        JButton submit = new JButton("Submit");
        submit.setFont(new Font("Segoe UI", Font.BOLD, 16));
        submit.setBackground(new Color(70, 130, 180));
        submit.setForeground(Color.WHITE);
        submit.setBounds(140, 280, 140, 36);
        signUp.add(submit);

        signUp.setVisible(true);

        submit.addActionListener(e -> {
            String name = nameF.getText().trim();
            String username = userF.getText().trim();
            String email = emailF.getText().trim();
            String password = new String(passF.getPassword()).trim();

            if (name.isEmpty() || username.isEmpty() || password.isEmpty() || email.isEmpty()) {
                JOptionPane.showMessageDialog(signUp,
                        "Please fill all fields!",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            boolean valid = true;
            for (int i = 0; i < name.length(); i++) {
                char c = name.charAt(i);
                if (!(Character.isLetter(c) || c == ' ')) {
                    valid = false;
                    break;
                }
            }

            if (!valid) {
                JOptionPane.showMessageDialog(signUp,
                        "Full Name must contain only letters and spaces!",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            boolean allDigits = true;
            for (int i = 0; i < username.length(); i++) {
                if (!Character.isDigit(username.charAt(i))) {
                    allDigits = false;
                    break;
                }
            }
            if (allDigits) {
                JOptionPane.showMessageDialog(signUp,
                        "Username cannot be only numbers!",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (username.equalsIgnoreCase("admin")) {
                JOptionPane.showMessageDialog(signUp,
                        "Username 'admin' is reserved.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (password.length() < 8) {
                JOptionPane.showMessageDialog(signUp,
                        "Password must be at least 8 characters long!",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (!password.matches(".*[A-Za-z].*")) {
                JOptionPane.showMessageDialog(signUp,
                        "Password must contain at least one alphabet!",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (!password.matches(".*\\d.*")) {
                JOptionPane.showMessageDialog(signUp,
                        "Password must contain at least one number!",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (!password.matches(".*[!@#$%^&*()_+\\-={}\\[\\]:;\"'|,<.>/?`~].*")) {
                JOptionPane.showMessageDialog(signUp,
                        "Password must contain at least one special character!",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (!email.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,6}$")) {
                JOptionPane.showMessageDialog(signUp,
                        "Please enter a valid email address!",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            boolean success = DatabaseConnection.registerUser(name, username, password, email);
            if (success) {
                JOptionPane.showMessageDialog(signUp,
                        "Registration successful for " + name + "!",
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE);
                signUp.dispose();
            }
        });
    }
}
