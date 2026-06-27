package UI;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

import UI.DepartmentsList;
import UI.FeedbackForm;
import UI.AboutUsPanel;
import UI.ShowComplaintsForm;
import UI.TermsConditionsPanel;

public class UserDashboard extends JFrame {

    private final String userEmail;
    private JTextField searchField;
    private JPanel cardsContainer;
    private final List<ServiceCard> serviceCards = new ArrayList<>();
    private String username; // Store username for potential use

    public UserDashboard(String username, String userEmail) {
        this.username = username;
        this.userEmail = userEmail;
        setTitle("User Dashboard - Citizen's Voice");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1100, 720);
        setLocationRelativeTo(null);

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

        JLabel title = new JLabel("  Citizen's Voice");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));
        navbar.add(title, BorderLayout.WEST);

        JPanel navButtons = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 18));
        navButtons.setOpaque(false);

        String[] navs = {"Home", "About", "Contact Us"};
        for (String n : navs) {
            JButton b = createNavButton(n);
            b.addActionListener(e -> {
                switch (n) {
                    case "About" -> showAboutUs();
                    case "Contact Us" -> showContactUsPage();
                    default -> JOptionPane.showMessageDialog(this, n + " clicked (demo)");
                }
            });
            navButtons.add(b);
        }

        JButton servicesBtn = createNavButton("Services");
        servicesBtn.setIcon(new ArrowIcon(Color.WHITE, 8, 5));
        servicesBtn.setHorizontalTextPosition(SwingConstants.LEFT);
        servicesBtn.setIconTextGap(4);

        JPopupMenu servicesMenu = new JPopupMenu();
        String[] svcs = {
                "File Complaint",
                "View Complaints",
                "Download Complaint",
                "Feedback Form",
                "FAQ / Help Center",
                "Department Info"
        };
        for (String s : svcs) {
            JMenuItem item = new JMenuItem(s);
            item.addActionListener(e -> handleCardAction(s));
            servicesMenu.add(item);
        }
        servicesBtn.addActionListener(e ->
                servicesMenu.show(servicesBtn, 0, servicesBtn.getHeight()));

        navButtons.add(servicesBtn);

        navbar.add(navButtons, BorderLayout.CENTER);

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

        JButton profileBtn = new JButton(username + " ▾");
        profileBtn.setFocusPainted(false);
        profileBtn.setBackground(new Color(255, 255, 255, 200));
        profileBtn.setBorder(BorderFactory.createEmptyBorder(6, 12, 6, 12));
        profileBtn.setFont(new Font("Segoe UI", Font.PLAIN, 13));

        JPopupMenu profileMenu = new JPopupMenu();
        JMenuItem editProfile = new JMenuItem("Edit Profile");
        JMenuItem changePassword = new JMenuItem("Change Password");
        JMenuItem logout = new JMenuItem("Logout");

        editProfile.addActionListener(e -> showEditProfileDialog());
        changePassword.addActionListener(e -> showChangePasswordDialog());
        logout.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Logged out successfully!");
            dispose();
            System.exit(0);
        });

        profileMenu.add(editProfile);
        profileMenu.add(changePassword);
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

        Font normalFont = new Font("Segoe UI", Font.PLAIN, 14);
        Font hoverFont = normalFont.deriveFont(Font.BOLD);
        b.setFont(normalFont);

        b.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                b.setOpaque(true);
                b.setBackground(new Color(30, 110, 180));
                b.setFont(hoverFont);
                b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                b.setOpaque(false);
                b.setBackground(new Color(0, 0, 0, 0));
                b.setFont(normalFont);
                b.setCursor(Cursor.getDefaultCursor());
            }
        });
        return b;
    }

    // CARDS AREA
    private JPanel createCardsPanel() {
        cardsContainer = new JPanel(new GridLayout(2, 3, 20, 20));
        cardsContainer.setOpaque(false);
        cardsContainer.setBorder(new EmptyBorder(24, 40, 24, 40));

        addServiceCard("File Complaint", "Lodge a new grievance", new Color(52, 152, 219));
        addServiceCard("View Complaints", "See your complaints", new Color(46, 204, 113));
        addServiceCard("Download Complaint", "Download your complaint", new Color(241, 196, 15));
        addServiceCard("Feedback Form", "Share your feedback", new Color(155, 89, 182));
        addServiceCard("FAQ / Help Center", "Common questions", new Color(230, 126, 34));
        addServiceCard("Department Info", "Department contacts", new Color(231, 76, 60));

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
                handleCardAction(title);
            }
        });
        serviceCards.add(card);
    }

    // ACTION HANDLER (View Complaints wired)
    private void handleCardAction(String title) {
        switch (title) {
            case "File Complaint" -> new ComplaintForm();
            case "View Complaints" -> new ShowComplaintsForm(userEmail);   // connectivity added
            case "Download Complaint" -> JOptionPane.showMessageDialog(this, "Download complaint file.");
            case "Feedback Form" -> new FeedbackForm();
            case "FAQ / Help Center" -> showFAQDialog();
            case "Department Info" -> showDepartmentList();
            case "Feedback" -> new FeedbackForm(); // footer 'Feedback'
            default -> JOptionPane.showMessageDialog(this, title + " clicked.");
        }
    }

    // DEPARTMENT LIST WINDOW
    private void showDepartmentList() {
        JFrame frame = new JFrame("Departments - CITIZEN'S VOICE");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setMinimumSize(new Dimension(1100, 700));
        frame.setLocationRelativeTo(this);
        frame.setContentPane(new DepartmentsList());
        frame.setVisible(true);
    }

    // ABOUT US WINDOW
    private void showAboutUs() {
        JFrame frame = new JFrame("About Us - Citizen's Voice");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(1100, 720);
        frame.setMinimumSize(new Dimension(900, 600));
        frame.setLocationRelativeTo(this);
        frame.setContentPane(new AboutUsPanel());
        frame.setVisible(true);
    }

    //  CONTACT US WINDOW
    private void showContactUsPage() {
        new ContactUsPage();
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

    // FOOTER
    private JPanel createFooter() {
        JPanel footer = new JPanel(new GridLayout(1, 3));
        footer.setBackground(new Color(15, 56, 101));
        footer.setBorder(new EmptyBorder(18, 40, 18, 40));

        JPanel services = new JPanel();
        services.setOpaque(false);
        services.setLayout(new BoxLayout(services, BoxLayout.Y_AXIS));
        JLabel sTitle = new JLabel("SERVICES");
        sTitle.setForeground(Color.WHITE);
        sTitle.setFont(new Font("Segoe UI", Font.BOLD, 14));
        services.add(sTitle);
        services.add(Box.createVerticalStrut(10));
        String[] servs = {"File Complaint", "View Complaints", "Department Info", "FAQ", "Feedback"};
        for (String s : servs) {
            JLabel l = new JLabel("- " + s);
            l.setForeground(Color.WHITE);
            l.setFont(new Font("Segoe UI", Font.PLAIN, 13));
            l.putClientProperty("baseText", l.getText());

            l.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if ("FAQ".equals(s)) {

                        showFAQDialog();
                    } else {
                        handleCardAction(s);
                    }
                }

                @Override
                public void mouseEntered(MouseEvent e) {
                    l.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                    String base = (String) l.getClientProperty("baseText");
                    l.setText("<html><u>" + base + "</u></html>");
                }
                @Override
                public void mouseExited(MouseEvent e) {
                    l.setCursor(Cursor.getDefaultCursor());
                    String base = (String) l.getClientProperty("baseText");
                    l.setText(base);
                }
            });

            services.add(l);
        }

        JPanel info = new JPanel();
        info.setOpaque(false);
        info.setLayout(new BoxLayout(info, BoxLayout.Y_AXIS));
        JLabel iTitle = new JLabel("INFORMATION");
        iTitle.setForeground(Color.WHITE);
        iTitle.setFont(new Font("Segoe UI", Font.BOLD, 14));
        info.add(iTitle);
        info.add(Box.createVerticalStrut(10));
        String[] infs = {"About Citizen's Voice", "Help Center", "Terms & Conditions", "Contact Us"};
        for (String s : infs) {
            JLabel l = new JLabel("- " + s);
            l.setForeground(Color.WHITE);
            l.setFont(new Font("Segoe UI", Font.PLAIN, 13));
            l.putClientProperty("baseText", l.getText());

            l.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    switch (s) {
                        case "About Citizen's Voice" -> showAboutUs();
                        case "Help Center" -> showFAQDialog();
                        case "Terms & Conditions" -> {
                            // OPEN TERMS & CONDITIONS PAGE HERE
                            JFrame frame = new JFrame("Terms & Conditions - Citizen's Voice");
                            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                            frame.setSize(1100, 720);
                            frame.setLocationRelativeTo(UserDashboard.this);
                            frame.setContentPane(new TermsConditionsPanel());
                            frame.setVisible(true);
                        }
                        case "Contact Us" -> showContactUsPage();
                    }
                }
                @Override
                public void mouseEntered(MouseEvent e) {
                    l.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                    String base = (String) l.getClientProperty("baseText");
                    l.setText("<html><u>" + base + "</u></html>");
                }
                @Override
                public void mouseExited(MouseEvent e) {
                    l.setCursor(Cursor.getDefaultCursor());
                    String base = (String) l.getClientProperty("baseText");
                    l.setText(base);
                }
            });

            info.add(l);
        }

        JPanel contact = new JPanel();
        contact.setOpaque(false);
        contact.setLayout(new BoxLayout(contact, BoxLayout.Y_AXIS));
        JLabel cTitle = new JLabel("CONTACT US");
        cTitle.setForeground(Color.WHITE);
        cTitle.setFont(new Font("Segoe UI", Font.BOLD, 14));
        contact.add(cTitle);
        contact.add(Box.createVerticalStrut(10));
        JLabel name = new JLabel("Citizen's Voice Portal");
        name.setForeground(Color.WHITE);
        name.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        contact.add(name);
        contact.add(Box.createVerticalStrut(6));
        JLabel address = new JLabel("Address: New Delhi, India");
        address.setForeground(Color.WHITE);
        address.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        contact.add(address);
        contact.add(Box.createVerticalStrut(6));
        JLabel helpline = new JLabel("Helpline: 1800-123-999");
        helpline.setForeground(Color.WHITE);
        helpline.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        contact.add(helpline);
        contact.add(Box.createVerticalStrut(6));
        JLabel email = new JLabel("Email: support@citizensvoice.in");
        email.setForeground(Color.WHITE);
        email.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        contact.add(email);

        footer.add(services);
        footer.add(info);
        footer.add(contact);

        return footer;
    }

    // DIALOGS
    private void showFAQDialog() {
        String faqs = """
                Q1: How to file a complaint?
                A: Click 'File Complaint' and fill the form.

                Q2: How long does it take for a complaint to resolve?
                A: Depends on department, typically 7-30 days.

                Q3: Can I edit my complaint?
                A: Yes, you can.
                """;
        JTextArea area = new JTextArea(faqs);
        area.setEditable(false);
        area.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        JScrollPane sp = new JScrollPane(area);
        sp.setPreferredSize(new Dimension(560, 260));
        JOptionPane.showMessageDialog(this, sp, "FAQ / Help Center", JOptionPane.PLAIN_MESSAGE);
    }

    private void showEditProfileDialog() {
        JDialog d = new JDialog(this, "Edit Profile", true);
        d.setSize(420, 320);
        d.setLocationRelativeTo(this);
        d.setLayout(new BorderLayout());

        JPanel p = new JPanel(new GridLayout(4, 2, 8, 8));
        p.setBorder(new EmptyBorder(12, 12, 12, 12));

        p.add(new JLabel("Full Name:"));
        JTextField name = new JTextField("XYZ");
        p.add(name);

        p.add(new JLabel("Email:"));
        JTextField email = new JTextField("xyz@example.com");
        p.add(email);

        p.add(new JLabel("Phone:"));
        JTextField phone = new JTextField("9898XXXXXX");
        p.add(phone);

        p.add(new JLabel("City:"));
        JTextField city = new JTextField("YYY");
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
            case "File Complaint" -> "complain.png";
            case "View Complaints" -> "showfiles.png";
            case "Download Complaint" -> "download.png";
            case "Feedback Form" -> "feedback.png";
            case "FAQ / Help Center" -> "faq.png";
            case "Department Info" -> "department.png";
            default -> "default.png";
        };
    }

    // CARD CLASS
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

            // Icon image loading
            try {
                ImageIcon raw = new ImageIcon(
                        getClass().getResource("/UI/icons/" + titleToFile(title))
                );
                int size = "Download Complaint".equals(title) ? 80 : 72;
                Image scaled = raw.getImage().getScaledInstance(size, size, Image.SCALE_SMOOTH);
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

            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                    white.setBorder(BorderFactory.createLineBorder(accent.darker(), 2));
                }
                @Override
                public void mouseExited(MouseEvent e) {
                    setCursor(Cursor.getDefaultCursor());
                    white.setBorder(BorderFactory.createEmptyBorder(18, 16, 16, 16));
                }
            });
        }

        public String getTitle() { return title; }
        public String getSubtitle() { return subtitle; }
    }

    // ---------------- ARROW ICON FOR DROPDOWN
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
        public int getIconWidth() { return width; }
        @Override
        public int getIconHeight() { return height; }
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(
                    UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {}

        SwingUtilities.invokeLater(() -> new UserDashboard("Rohan", "rohan@gmail.com"));
    }
}
