package UI;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.util.regex.Pattern;

public class ComplaintForm extends JFrame {

    private JComboBox<String> comboCategory;
    private JComboBox<String> comboSubCategory;
    private JTextField tfSubject, tfAddress, tfLandmark, tfFirstName, tfLastName, tfMobile, tfEmail;
    private JTextArea taIssueDesc;

    public ComplaintForm() {
        setTitle("Complaint Registration Form");
        setSize(1000, 900);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        getContentPane().setBackground(new Color(245, 247, 250));

        JPanel outerPanel = new JPanel(new GridBagLayout());
        outerPanel.setBackground(new Color(245, 247, 250));
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(new Color(245, 247, 250));
        mainPanel.setPreferredSize(new Dimension(750, 1000));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0; gbc.gridy = 0;
        outerPanel.add(mainPanel, gbc);

        JScrollPane scrollPane = new JScrollPane(outerPanel);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        add(scrollPane);

        comboCategory = comboCategory();
        comboSubCategory = comboSubCategory();

        comboCategory.addActionListener(e -> updateSubCategories());
        updateSubCategories();

        tfSubject = textField();

        taIssueDesc = new JTextArea(4, 20);
        taIssueDesc.setLineWrap(true);
        taIssueDesc.setWrapStyleWord(true);
        taIssueDesc.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        taIssueDesc.setBorder(new RoundedBorder(8));

        mainPanel.add(section("Complaint And Grievance Details",
                row("Category *", comboCategory),
                row("Sub Category *", comboSubCategory),
                row("Complaint Subject *", tfSubject),
                row("Issue Description", taIssueDesc)    // <-- NO JScrollPane
        ));

        tfAddress = textField();
        tfLandmark = textField();
        mainPanel.add(section("Address Details",
                rowTwoFields("Address *", tfAddress, "Landmark", tfLandmark)
        ));

        tfFirstName = textField();
        tfLastName = textField();
        tfMobile = textField();
        tfEmail = textField();
        mainPanel.add(section("Complainer Details",
                row("First Name", tfFirstName),
                row("Last Name", tfLastName),
                row("Mobile Number *", tfMobile),
                row("Email *", tfEmail)
        ));

        JPanel btnPanel = new JPanel();
        btnPanel.setBackground(Color.WHITE);
        btnPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 25, 13));
        JButton submitBtn = styledButton("Submit", new Color(0, 123, 255), Color.WHITE);
        submitBtn.addActionListener(e -> submitForm());
        JButton resetBtn = styledButton("Reset", Color.WHITE, Color.BLACK);
        resetBtn.addActionListener(e -> resetForm());
        btnPanel.add(submitBtn);
        btnPanel.add(resetBtn);
        mainPanel.add(section(null, btnPanel));

        setVisible(true);
    }

    private void resetForm() {
        comboCategory.setSelectedIndex(0);
        comboSubCategory.setSelectedIndex(0);
        tfSubject.setText("");
        taIssueDesc.setText("");
        tfAddress.setText("");
        tfLandmark.setText("");
        tfFirstName.setText("");
        tfLastName.setText("");
        tfMobile.setText("");
        tfEmail.setText("");
    }

    private void updateSubCategories() {
        String selectedCategory = (String) comboCategory.getSelectedItem();
        String[] subItems;

        switch (selectedCategory) {
            case "Streetlights" -> subItems = new String[]{
                    "Select", "Streetlight not working", "Installation of new streetlight"
            };
            case "Garbage" -> subItems = new String[]{
                    "Select", "Garbage needs to be cleared", "Burning of garbage",
                    "Damaged garbage bin", "Non-sweeping of road"
            };
            case "Drains and Sewage" -> subItems = new String[]{
                    "Select", "Overflowing/blocked drain", "Cleaning of drains",
                    "Water entering house during rainy season", "Illegal discharge of sewage",
                    "Blocked or overflowing sewage", "Broken water pipe/leakage",
                    "No water supply or low water pressure", "Sewage manhole cover missing or broken"
            };
            case "Roads and Footpaths" -> subItems = new String[]{
                    "Select", "Damaged road", "Waterlogged road",
                    "Manhole cover missing or broken", "Damaged or blocked footpath",
                    "Construction material lying on road", "Illegal hawkers/stalls on road"
            };
            case "Land and Property Violations" -> subItems = new String[]{
                    "Select", "Illegal shops on footpath", "Illegal construction", "Illegal parking"
            };
            case "Others" -> subItems = new String[]{
                    "Select", "Miscellaneous issues not covered above"
            };
            default -> subItems = new String[]{"Select"};
        }
        DefaultComboBoxModel<String> model = (DefaultComboBoxModel<String>) comboSubCategory.getModel();
        model.removeAllElements();
        for (String subItem : subItems) {
            model.addElement(subItem);
        }
    }

    private void submitForm() {
        String category = (String) comboCategory.getSelectedItem();
        String subCategory = (String) comboSubCategory.getSelectedItem();
        String subject = tfSubject.getText().trim();
        String issueDesc = taIssueDesc.getText().trim();
        String address = tfAddress.getText().trim();
        String landmark = tfLandmark.getText().trim();
        String firstName = tfFirstName.getText().trim();
        String lastName = tfLastName.getText().trim();
        String mobile = tfMobile.getText().trim();
        String email = tfEmail.getText().trim();

        if (category.equals("Select") || subCategory.equals("Select") ||
                subject.isEmpty() || address.isEmpty() ||
                mobile.isEmpty() || email.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all required fields (*)", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (!Pattern.matches("\\d{10}", mobile)) {
            JOptionPane.showMessageDialog(this, "Mobile number must be 10 digits", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (!Pattern.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$", email)) {
            JOptionPane.showMessageDialog(this, "Invalid email format", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        boolean success = DatabaseConnection.registerComplaint(
                category, subCategory, subject, issueDesc, address, landmark, firstName, lastName, mobile, email
        );
        if (success) {
            JOptionPane.showMessageDialog(this, "Complaint submitted successfully!");
            resetForm();
        }
    }

    private JComboBox<String> comboCategory() {
        String[] items = {
                "Select",
                "Streetlights",
                "Garbage",
                "Drains and Sewage",
                "Roads and Footpaths",
                "Land and Property Violations",
                "Others"
        };
        JComboBox<String> box = new JComboBox<>(items);
        box.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        box.setBorder(new RoundedBorder(8));
        box.setBackground(Color.WHITE);
        return box;
    }

    private JComboBox<String> comboSubCategory() {
        JComboBox<String> box = new JComboBox<>();
        box.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        box.setBorder(new RoundedBorder(8));
        box.setBackground(Color.WHITE);
        return box;
    }

    private JTextField textField() {
        JTextField tf = new JTextField(20);
        tf.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        tf.setBorder(new RoundedBorder(8));
        return tf;
    }

    private JPanel section(String title, JComponent... comps) {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(Color.WHITE);
        card.setBorder(new CompoundBorder(
                new EmptyBorder(15, 15, 15, 15),
                new RoundedBorder(12)
        ));

        if (title != null) {
            JLabel lbl = new JLabel(title);
            lbl.setFont(new Font("Segoe UI", Font.BOLD, 16));
            lbl.setAlignmentX(JLabel.CENTER_ALIGNMENT);
            lbl.setBorder(new EmptyBorder(0, 0, 10, 0));
            card.add(lbl);
        }

        for (JComponent c : comps) {
            card.add(c);
            card.add(Box.createVerticalStrut(8));
        }

        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setBackground(new Color(245, 247, 250));
        wrapper.setBorder(new EmptyBorder(15, 20, 15, 20));
        wrapper.add(card);

        return wrapper;
    }

    private JPanel row(String label, JComponent field) {
        JPanel p = new JPanel(new BorderLayout());
        p.setBackground(Color.WHITE);

        JLabel lbl = new JLabel(label);
        lbl.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lbl.setPreferredSize(new Dimension(170, 25));

        p.add(lbl, BorderLayout.WEST);
        p.add(field, BorderLayout.CENTER);

        return p;
    }

    private JPanel rowTwoFields(String label1, JComponent field1, String label2, JComponent field2) {
        JPanel p = new JPanel();
        p.setLayout(new GridBagLayout());
        p.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(0, 0, 0, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        JLabel lbl1 = new JLabel(label1);
        lbl1.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        p.add(lbl1, gbc);

        gbc.gridx = 1;
        gbc.weightx = 1;
        p.add(field1, gbc);

        gbc.gridx = 2;
        gbc.weightx = 0;
        JLabel lbl2 = new JLabel(label2);
        lbl2.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        p.add(lbl2, gbc);

        gbc.gridx = 3;
        gbc.weightx = 1;
        p.add(field2, gbc);

        return p;
    }

    private JButton styledButton(String text, Color bg, Color fg) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setBackground(bg);
        btn.setForeground(fg);
        btn.setFocusPainted(false);
        btn.setBorder(new RoundedBorder(8));
        btn.setPreferredSize(new Dimension(130, 38));
        return btn;
    }

    class RoundedBorder extends AbstractBorder {
        private final int radius;

        public RoundedBorder(int radius) {
            this.radius = radius;
        }

        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int w, int h) {
            g.setColor(new Color(200, 200, 200));
            g.drawRoundRect(x, y, w - 1, h - 1, radius, radius);
        }

        @Override
        public Insets getBorderInsets(Component c) {
            return new Insets(10, 10, 10, 10);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ComplaintForm::new);
    }
}
