package UI;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class AdminDashboard extends JFrame {

    private JTextField searchField;
    private JPanel cardsContainer;
    private final List<ServiceCard> serviceCards = new ArrayList<>();

    public AdminDashboard(String username) {
        setTitle("Citizen's Voice Admin");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1100, 720);
        setLocationRelativeTo(null);

        // Main scrollable page
        JPanel fullPage = new JPanel();
        fullPage.setLayout(new BoxLayout(fullPage, BoxLayout.Y_AXIS));
        fullPage.setBackground(new Color(245, 245, 247));
        fullPage.setOpaque(true);

        fullPage.add(createNavbar(username));
        fullPage.add(Box.createVerticalStrut(25));
        fullPage.add(createCardsPanel());
        fullPage.add(Box.createVerticalStrut(25));
        fullPage.add(createFooter());

        JScrollPane scroll = new JScrollPane(fullPage,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scroll.getVerticalScrollBar().setUnitIncrement(16);

        setContentPane(scroll);
        setVisible(true);
    }

    // NAVBAR
    private JPanel createNavbar(String username) {
        JPanel navbar = new JPanel(new BorderLayout());
        navbar.setBackground(new Color(20, 86, 143));
        navbar.setPreferredSize(new Dimension(0, 70));

        JLabel title = new JLabel("  Citizen's Voice Admin");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));
        navbar.add(title, BorderLayout.WEST);

        // Center navigation
        JPanel navButtons = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 18));
        navButtons.setOpaque(false);

        JButton homeBtn = createNavButton("Home");
        homeBtn.addActionListener(e ->
                JOptionPane.showMessageDialog(this, "Admin Home (demo)"));
        navButtons.add(homeBtn);

        // Dropdown
        JButton adminMenuBtn = createNavButton("Admin Options");
        adminMenuBtn.setIcon(new ArrowIcon(Color.WHITE, 8, 5));
        adminMenuBtn.setHorizontalTextPosition(SwingConstants.LEFT);
        adminMenuBtn.setIconTextGap(4);

        JPopupMenu adminMenu = new JPopupMenu();
        String[] opts = {
                "Show All Complaints",
                "Verify Complaints",
                "Update Complaint Status",
                "Manage Users",
                "Departments",
                "Reports"
        };

        for (String s : opts) {
            JMenuItem i = new JMenuItem(s);
            i.addActionListener(e -> handleAdminAction(s));
            adminMenu.add(i);
        }

        adminMenuBtn.addActionListener(e ->
                adminMenu.show(adminMenuBtn, 0, adminMenuBtn.getHeight()));

        navButtons.add(adminMenuBtn);

        navbar.add(navButtons, BorderLayout.CENTER);

        // Right panel (search + profile)
        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 12));
        rightPanel.setOpaque(false);

        searchField = new JTextField(18);
        searchField.setPreferredSize(new Dimension(260, 34));
        searchField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        searchField.setBorder(BorderFactory.createEmptyBorder(6, 10, 6, 10));

        searchField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                filterCards(searchField.getText().trim());
            }
        });

        rightPanel.add(wrapSearchField(searchField));

        // Profile dropdown
        JButton profileBtn = new JButton(username + " ▾");
        profileBtn.setFocusPainted(false);
        profileBtn.setBackground(new Color(255, 255, 255, 200));
        profileBtn.setBorder(BorderFactory.createEmptyBorder(6, 12, 6, 12));
        profileBtn.setFont(new Font("Segoe UI", Font.PLAIN, 13));

        JPopupMenu profileMenu = new JPopupMenu();
        JMenuItem edit = new JMenuItem("Edit Profile");
        JMenuItem pass = new JMenuItem("Change Password");
        JMenuItem logout = new JMenuItem("Logout");

        edit.addActionListener(e -> showEditProfileDialog());
        pass.addActionListener(e -> showChangePasswordDialog());
        logout.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Logged out successfully!");
            dispose();
            System.exit(0);
        });

        profileMenu.add(edit);
        profileMenu.add(pass);
        profileMenu.addSeparator();
        profileMenu.add(logout);

        profileBtn.addActionListener(e ->
                profileMenu.show(profileBtn, 0, profileBtn.getHeight()));

        rightPanel.add(profileBtn);
        navbar.add(rightPanel, BorderLayout.EAST);

        return navbar;
    }

    private Component wrapSearchField(JTextField field) {
        JPanel p = new JPanel(new BorderLayout());
        p.setOpaque(true);
        p.setBackground(Color.WHITE);
        p.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1, true));
        field.setBackground(Color.WHITE);
        p.add(field, BorderLayout.CENTER);

        JButton searchIcon = new JButton("🔍");
        searchIcon.setPreferredSize(new Dimension(40, 34));
        searchIcon.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        searchIcon.setFocusPainted(false);
        searchIcon.setContentAreaFilled(true);
        searchIcon.setOpaque(true);
        searchIcon.setBackground(Color.WHITE);
        searchIcon.addActionListener(e -> filterCards(searchField.getText().trim()));

        p.add(searchIcon, BorderLayout.EAST);
        return p;
    }

    private JButton createNavButton(String text) {
        JButton b = new JButton(text);
        b.setFocusPainted(false);
        b.setBorder(BorderFactory.createEmptyBorder(6, 12, 6, 12));
        b.setForeground(Color.WHITE);
        b.setContentAreaFilled(false);

        Font normal = new Font("Segoe UI", Font.PLAIN, 14);
        Font hover = normal.deriveFont(Font.BOLD);
        b.setFont(normal);

        b.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                b.setOpaque(true);
                b.setBackground(new Color(30, 110, 180));
                b.setFont(hover);
                b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                b.setOpaque(false);
                b.setBackground(new Color(0, 0, 0, 0));
                b.setFont(normal);
            }
        });

        return b;
    }

    // CARDS
    private JPanel createCardsPanel() {
        cardsContainer = new JPanel(new GridLayout(2, 3, 20, 20));
        cardsContainer.setOpaque(false);
        cardsContainer.setBorder(new EmptyBorder(24, 40, 24, 40));

        addServiceCard("Show All Complaints", "View every complaint", new Color(52, 152, 219));
        addServiceCard("Verify Complaints", "Check and verify complaints", new Color(46, 204, 113));
        addServiceCard("Update Complaint Status", "Modify complaint status", new Color(241, 196, 15));
        addServiceCard("Manage Users", "View & manage users", new Color(155, 89, 182));
        addServiceCard("Departments", "Manage departments", new Color(230, 126, 34));
        addServiceCard("Reports", "View analytics & reports", new Color(231, 76, 60));

        for (ServiceCard sc : serviceCards) {
            cardsContainer.add(sc);
        }

        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setOpaque(false);
        wrapper.add(cardsContainer, BorderLayout.CENTER);

        return wrapper;
    }

    private void addServiceCard(String title, String subtitle, Color color) {
        ServiceCard card = new ServiceCard(title, subtitle, color);

        card.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                handleAdminAction(title);
            }
        });

        serviceCards.add(card);
    }

    private void handleAdminAction(String s) {
        if (s.equals("Show All Complaints")) {
            new ShowComplaintsFormAdmin();
        } else {
            JOptionPane.showMessageDialog(this, s + " clicked (demo)");
        }
    }

    private void filterCards(String query) {
        query = query.toLowerCase();
        cardsContainer.removeAll();

        for (ServiceCard c : serviceCards) {
            if (query.isEmpty()
                    || c.getTitle().toLowerCase().contains(query)
                    || c.getSubtitle().toLowerCase().contains(query)) {
                cardsContainer.add(c);
            }
        }

        cardsContainer.revalidate();
        cardsContainer.repaint();
    }

    //  FOOTER
    private JPanel createFooter() {
        JPanel footer = new JPanel(new GridLayout(1, 3));
        footer.setBackground(new Color(15, 56, 101));
        footer.setBorder(new EmptyBorder(18, 40, 18, 40));

        // Admin Services
        footer.add(makeFooterColumn("SERVICES", new String[]{
                "Show All Complaints",
                "Verify Complaints",
                "Update Complaint Status",
                "Manage Users",
                "Departments",
                "Reports"
        }));

        // Info
        footer.add(makeFooterColumn("INFORMATION", new String[]{
                "About Citizen's Voice",
                "Help Center",
                "Terms & Conditions",
                "Contact Us"
        }));

        // Contact
        JPanel contact = new JPanel();
        contact.setOpaque(false);
        contact.setLayout(new BoxLayout(contact, BoxLayout.Y_AXIS));

        JLabel t = new JLabel("CONTACT US");
        t.setForeground(Color.WHITE);
        t.setFont(new Font("Segoe UI", Font.BOLD, 14));
        contact.add(t);
        contact.add(Box.createVerticalStrut(10));

        String[] lines = {
                "Citizen's Voice Portal",
                "Address: New Delhi, India",
                "Helpline: 1800-123-999",
                "Email: support@citizensvoice.in"
        };

        for (String s : lines) {
            JLabel l = new JLabel(s);
            l.setForeground(Color.WHITE);
            l.setFont(new Font("Segoe UI", Font.PLAIN, 13));
            contact.add(l);
            contact.add(Box.createVerticalStrut(6));
        }

        footer.add(contact);
        return footer;
    }

    private JPanel makeFooterColumn(String title, String[] items) {
        JPanel col = new JPanel();
        col.setOpaque(false);
        col.setLayout(new BoxLayout(col, BoxLayout.Y_AXIS));

        JLabel t = new JLabel(title);
        t.setForeground(Color.WHITE);
        t.setFont(new Font("Segoe UI", Font.BOLD, 14));
        col.add(t);
        col.add(Box.createVerticalStrut(10));

        for (String s : items) {
            JLabel l = new JLabel("- " + s);
            l.setForeground(Color.WHITE);
            l.setFont(new Font("Segoe UI", Font.PLAIN, 13));
            l.putClientProperty("baseText", l.getText());

            l.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    String base = (String) l.getClientProperty("baseText");
                    l.setText("<html><u>" + base + "</u></html>");
                    l.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    String base = (String) l.getClientProperty("baseText");
                    l.setText(base);
                }

                @Override
                public void mouseClicked(MouseEvent e) {
                    handleAdminAction(s);
                }
            });

            col.add(l);
        }

        return col;
    }

    // EDIT PROFILE & CHANGE PASSWORD
    private void showEditProfileDialog() {
        JDialog d = new JDialog(this, "Edit Profile", true);
        d.setSize(420, 320);
        d.setLocationRelativeTo(this);
        d.setLayout(new BorderLayout());

        JPanel p = new JPanel(new GridLayout(4, 2, 8, 8));
        p.setBorder(new EmptyBorder(12, 12, 12, 12));

        p.add(new JLabel("Full Name:"));
        JTextField name = new JTextField("Admin User");
        p.add(name);

        p.add(new JLabel("Email:"));
        JTextField email = new JTextField("admin@example.com");
        p.add(email);

        p.add(new JLabel("Phone:"));
        JTextField phone = new JTextField("9999XXXXXX");
        p.add(phone);

        p.add(new JLabel("City:"));
        JTextField city = new JTextField("Delhi");
        p.add(city);

        d.add(p, BorderLayout.CENTER);

        JPanel btns = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton save = new JButton("Save");
        save.addActionListener(e -> {
            JOptionPane.showMessageDialog(d, "Profile updated (demo)");
            d.dispose();
        });
        JButton cancel = new JButton("Cancel");
        cancel.addActionListener(e -> d.dispose());
        btns.add(cancel);
        btns.add(save);

        d.add(btns, BorderLayout.SOUTH);
        d.setVisible(true);
    }

    private void showChangePasswordDialog() {
        JDialog d = new JDialog(this, "Change Password", true);
        d.setSize(380, 220);
        d.setLocationRelativeTo(this);
        d.setLayout(new BorderLayout());

        JPanel p = new JPanel(new GridLayout(3, 2, 8, 8));
        p.setBorder(new EmptyBorder(12, 12, 12, 12));

        p.add(new JLabel("Old Password:"));
        JPasswordField oldp = new JPasswordField();
        p.add(oldp);

        p.add(new JLabel("New Password:"));
        JPasswordField newp = new JPasswordField();
        p.add(newp);

        p.add(new JLabel("Confirm Password:"));
        JPasswordField conf = new JPasswordField();
        p.add(conf);

        d.add(p, BorderLayout.CENTER);

        JPanel btns = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton change = new JButton("Change");
        change.addActionListener(e -> {
            if (!String.valueOf(newp.getPassword())
                    .equals(String.valueOf(conf.getPassword()))) {
                JOptionPane.showMessageDialog(d, "New passwords do not match");
                return;
            }
            JOptionPane.showMessageDialog(d, "Password changed (demo)");
            d.dispose();
        });
        JButton cancel = new JButton("Cancel");
        cancel.addActionListener(e -> d.dispose());
        btns.add(cancel);
        btns.add(change);

        d.add(btns, BorderLayout.SOUTH);
        d.setVisible(true);
    }

    // ICON MAPPING
    private String titleToFile(String title) {
        return switch (title) {
            case "Show All Complaints" -> "a_showcomplain.png";
            case "Verify Complaints" -> "a_verify.png";
            case "Update Complaint Status" -> "status.png";
            case "Manage Users" -> "a_users.png";
            case "Departments" -> "a_dept.png";
            case "Reports" -> "a_reports.png";
            default -> "default.png";
        };
    }

    //  CARD CLASS
    private class ServiceCard extends JPanel {

        private final String title;
        private final String subtitle;
        private final Color accent;

        public ServiceCard(String title, String subtitle, Color accent) {
            this.title = title;
            this.subtitle = subtitle;
            this.accent = accent;

            setOpaque(false);
            setLayout(new BorderLayout());
            setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));

            JPanel white = new JPanel(new BorderLayout());
            white.setBackground(Color.WHITE);
            white.setBorder(BorderFactory.createEmptyBorder(18, 16, 16, 16));

            try {
                ImageIcon raw = new ImageIcon(
                        getClass().getResource("/UI/icons/" + titleToFile(title))
                );
                Image scaled = raw.getImage().getScaledInstance(72, 72, Image.SCALE_SMOOTH);

                JLabel icon = new JLabel(new ImageIcon(scaled));
                icon.setHorizontalAlignment(SwingConstants.CENTER);

                JPanel top = new JPanel(new FlowLayout(FlowLayout.CENTER));
                top.setOpaque(false);
                top.add(icon);

                white.add(top, BorderLayout.NORTH);
            } catch (Exception ex) {
                JLabel fallback = new JLabel("●", SwingConstants.CENTER);
                fallback.setFont(new Font("Segoe UI", Font.BOLD, 32));
                fallback.setForeground(accent);

                JPanel top = new JPanel(new FlowLayout(FlowLayout.CENTER));
                top.setOpaque(false);
                top.add(fallback);

                white.add(top, BorderLayout.NORTH);
            }

            JLabel t = new JLabel(title, SwingConstants.CENTER);
            t.setFont(new Font("Segoe UI", Font.BOLD, 16));
            t.setBorder(new EmptyBorder(8, 0, 6, 0));
            white.add(t, BorderLayout.CENTER);

            JLabel s = new JLabel(subtitle, SwingConstants.CENTER);
            s.setFont(new Font("Segoe UI", Font.PLAIN, 13));
            s.setForeground(Color.DARK_GRAY);
            white.add(s, BorderLayout.SOUTH);

            add(white, BorderLayout.CENTER);

            // Hover effect
            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    white.setBorder(BorderFactory.createLineBorder(accent.darker(), 2));
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    white.setBorder(BorderFactory.createEmptyBorder(18, 16, 16, 16));
                }
            });
        }

        public String getTitle() {
            return title;
        }

        public String getSubtitle() {
            return subtitle;
        }
    }

    private static class ArrowIcon implements Icon {
        private final Color color;
        private final int width;
        private final int height;

        public ArrowIcon(Color color, int width, int height) {
            this.color = color;
            this.width = width;
            this.height = height;
        }

        @Override
        public void paintIcon(Component c, Graphics g, int x, int y) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(color);

            int[] xs = {x, x + width / 2, x + width};
            int[] ys = {y, y + height, y};
            g2.fillPolygon(xs, ys, 3);
            g2.dispose();
        }

        @Override
        public int getIconWidth() {
            return width;
        }

        @Override
        public int getIconHeight() {
            return height;
        }
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {}

        SwingUtilities.invokeLater(() -> new AdminDashboard("Admin"));
    }
}
