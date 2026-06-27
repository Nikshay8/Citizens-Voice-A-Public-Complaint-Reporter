package UI;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ShowComplaintsForm extends JFrame {

    private JTable table;
    private DefaultTableModel tableModel;
    private String userEmail;  // Store logged-in user's email

    public ShowComplaintsForm(String userEmail) {
        this.userEmail = userEmail;  // Receive email from login

        setTitle("My Complaints - Citizen's Voice");
        setSize(1100, 650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // background
        getContentPane().setBackground(new Color(245, 247, 250));
        setLayout(new BorderLayout());

        // Top title bar
        JLabel title = new JLabel("My Complaints");
        title.setFont(new Font("Segoe UI", Font.BOLD, 26));
        title.setForeground(Color.BLACK);
        title.setBorder(new EmptyBorder(20, 28, 8, 0));

        JLabel userLabel = new JLabel("Logged in as: " + userEmail);
        userLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        userLabel.setForeground(new Color(100, 100, 100));

        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.setOpaque(false);
        titlePanel.add(title, BorderLayout.WEST);
        titlePanel.add(userLabel, BorderLayout.EAST);
        add(titlePanel, BorderLayout.NORTH);

        // Table model / columns
        String[] columns = {
                "ID",
                "Category",
                "Sub Category",
                "Subject",
                "Issue Description",
                "Address",
                "Landmark",
                "First Name",
                "Last Name",
                "Mobile",
                "Email"
        };
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };

        table = new JTable(tableModel);
        table.setFillsViewportHeight(true);
        table.setRowHeight(26);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.setGridColor(new Color(220, 220, 220));
        table.setShowHorizontalLines(true);
        table.setShowVerticalLines(true);

        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 14));
        header.setBackground(new Color(230, 236, 245));
        header.setForeground(Color.BLACK);
        header.setReorderingAllowed(false);
        header.setPreferredSize(new Dimension(header.getPreferredSize().width, 30));

        // Column widths
        table.getColumnModel().getColumn(0).setPreferredWidth(40);
        table.getColumnModel().getColumn(1).setPreferredWidth(110);
        table.getColumnModel().getColumn(2).setPreferredWidth(150);
        table.getColumnModel().getColumn(3).setPreferredWidth(160);
        table.getColumnModel().getColumn(4).setPreferredWidth(220);
        table.getColumnModel().getColumn(5).setPreferredWidth(220);
        table.getColumnModel().getColumn(6).setPreferredWidth(150);
        table.getColumnModel().getColumn(7).setPreferredWidth(120);
        table.getColumnModel().getColumn(8).setPreferredWidth(120);
        table.getColumnModel().getColumn(9).setPreferredWidth(130);
        table.getColumnModel().getColumn(10).setPreferredWidth(200);

        // Card-style panel (same as before)
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(Color.WHITE);
        card.setBorder(new EmptyBorder(16, 18, 18, 18));

        JScrollPane tableScroll = new JScrollPane(
                table,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED
        );
        tableScroll.getViewport().setBackground(Color.WHITE);
        tableScroll.setBorder(BorderFactory.createEmptyBorder());

        card.add(tableScroll, BorderLayout.CENTER);

        JPanel outer = new JPanel(new BorderLayout());
        outer.setBackground(new Color(245, 247, 250));
        outer.setBorder(new EmptyBorder(10, 24, 24, 24));
        outer.add(card, BorderLayout.CENTER);

        add(outer, BorderLayout.CENTER);

        // Dynamic resize behavior (same as before)
        addComponentListener(new java.awt.event.ComponentAdapter() {
            @Override
            public void componentResized(java.awt.event.ComponentEvent e) {
                int frameWidth = getWidth();
                int totalColsWidth = 0;
                for (int i = 0; i < table.getColumnModel().getColumnCount(); i++) {
                    totalColsWidth += table.getColumnModel().getColumn(i).getPreferredWidth();
                }
                if (frameWidth > totalColsWidth + 150) {
                    table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
                } else {
                    table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
                }
            }
        });

        fetchData();
        setVisible(true);
    }

    private void fetchData() {
        String sql = "SELECT id, category, sub_category, complaint_subject, " +
                "issue_description, address, landmark, first_name, " +
                "last_name, mobile_number, email " +
                "FROM Show_Complaints WHERE email = ? " +  // Filter by logged-in user's email
                "ORDER BY id DESC";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn != null ? conn.prepareStatement(sql) : null) {

            if (conn == null || ps == null) {
                JOptionPane.showMessageDialog(this,
                        "Could not connect to database.",
                        "Database Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            ps.setString(1, userEmail);
            ResultSet rs = ps.executeQuery();
            tableModel.setRowCount(0);

            int complaintCount = 0;
            while (rs.next()) {
                Object[] row = {
                        rs.getInt("id"),
                        rs.getString("category"),
                        rs.getString("sub_category"),
                        rs.getString("complaint_subject"),
                        rs.getString("issue_description"),
                        rs.getString("address"),
                        rs.getString("landmark"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("mobile_number"),
                        rs.getString("email")
                };
                tableModel.addRow(row);
                complaintCount++;
            }

            if (complaintCount == 0) {
                JOptionPane.showMessageDialog(this,
                        "No complaints found for this account.",
                        "No Data",
                        JOptionPane.INFORMATION_MESSAGE);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this,
                    "Error fetching your complaints from database.",
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ShowComplaintsForm("test@example.com"));
    }
}
