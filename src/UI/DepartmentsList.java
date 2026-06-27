package UI;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class DepartmentsList extends JPanel {
    private static final Color HEADING_BLACK = Color.BLACK;
    private static final Color DESC_COLOR = new Color(44, 62, 80);
    private static final Color PANEL_BG = new Color(245, 245, 247);
    private static final Color CARD_BG = new Color(230, 230, 230);

    // Department data
    private static final Object[][] DEPARTMENTS = {
            {"Sanitation Department", "Garbage, waste, sweeping", "sanitation@citizensvoice.in", "011-12345678"},
            {"Water Supply Department", "Leakage, no water, contamination", "water@citizensvoice.in", "011-23456789"},
            {"Street Lighting Dept", "Street light faults", "lighting@citizensvoice.in", "011-34567890"},
            {"Roads & Infrastructure", "Potholes, roads, footpaths", "roads@citizensvoice.in", "011-45678901"},
            {"Drainage & Sewer Dept", "Sewer overflow, blocked drains", "sewer@citizensvoice.in", "011-56789012"},
            {"Public Health Dept", "Mosquito breeding, stray animals", "health@citizensvoice.in", "011-67890123"},
            {"Parks & Horticulture", "Park maintenance, broken equipment", "parks@citizensvoice.in", "011-78901234"},
            {"Building Control Dept", "Unauthorized construction", "building@citizensvoice.in", "011-89012345"}
    };

    public DepartmentsList() {
        setLayout(new BorderLayout());
        setBackground(PANEL_BG);

        JLabel mainTitle = new JLabel("Department Directory");
        mainTitle.setFont(new Font("Segoe UI", Font.BOLD, 34));
        mainTitle.setForeground(HEADING_BLACK);
        mainTitle.setBorder(new EmptyBorder(36, 56, 28, 0));

        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        headerPanel.setOpaque(false);
        headerPanel.add(mainTitle);
        add(headerPanel, BorderLayout.NORTH);

        JPanel cardGrid = new JPanel();
        cardGrid.setLayout(new BoxLayout(cardGrid, BoxLayout.Y_AXIS));
        cardGrid.setOpaque(false);
        cardGrid.setBorder(new EmptyBorder(12, 48, 24, 48));

        int deptIndex = 0;
        // 1st row (3 cards)
        cardGrid.add(makeRow(deptIndex, 3));
        deptIndex += 3;
        cardGrid.add(Box.createVerticalStrut(26));
        // 2nd row (3 cards)
        cardGrid.add(makeRow(deptIndex, 3));
        deptIndex += 3;
        cardGrid.add(Box.createVerticalStrut(26));
        // 3rd row (2 cards, centered using empty placeholder)
        cardGrid.add(makeLastRow(deptIndex, 2));
        cardGrid.add(Box.createVerticalStrut(26));

        JScrollPane centerScroll = new JScrollPane(cardGrid,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        centerScroll.setBorder(null);
        centerScroll.getViewport().setOpaque(false);
        centerScroll.setOpaque(false);
        centerScroll.getVerticalScrollBar().setUnitIncrement(18);
        centerScroll.getHorizontalScrollBar().setUnitIncrement(16);

        add(centerScroll, BorderLayout.CENTER);
    }

    private JPanel makeRow(int start, int count) {
        JPanel rowPanel = new JPanel(new GridLayout(1, 3, 32, 0));
        rowPanel.setOpaque(false);
        for (int i = 0; i < 3; i++) {
            if (i < count && start + i < DEPARTMENTS.length) {
                Object[] d = DEPARTMENTS[start + i];
                rowPanel.add(makeDeptCard(d[0].toString(), d[1].toString(), d[2].toString(), d[3].toString()));
            } else {
                rowPanel.add(new JPanel() {{ setOpaque(false); }});
            }
        }
        return rowPanel;
    }

    private JPanel makeLastRow(int start, int count) {
        // Places two cards in first two grid slots, leaves third blank for perfect alignment
        JPanel rowPanel = new JPanel(new GridLayout(1, 3, 32, 0));
        rowPanel.setOpaque(false);
        for (int i = 0; i < 3; i++) {
            if (i < count && start + i < DEPARTMENTS.length) {
                Object[] d = DEPARTMENTS[start + i];
                rowPanel.add(makeDeptCard(d[0].toString(), d[1].toString(), d[2].toString(), d[3].toString()));
            } else {
                rowPanel.add(new JPanel() {{ setOpaque(false); }});
            }
        }
        return rowPanel;
    }

    private JPanel makeDeptCard(String deptName, String handles, String email, String phone) {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(CARD_BG);
        card.setBorder(new EmptyBorder(32, 22, 26, 22));

        JLabel heading = new JLabel(deptName);
        heading.setFont(new Font("Segoe UI", Font.BOLD, 21));
        heading.setForeground(HEADING_BLACK);
        heading.setBorder(new EmptyBorder(0, 0, 16, 0));
        card.add(heading);

        JLabel handlesLbl = new JLabel("<html><b>Handles:</b> " + handles + "</html>");
        handlesLbl.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        handlesLbl.setForeground(DESC_COLOR);
        handlesLbl.setBorder(new EmptyBorder(0, 0, 10, 0));
        card.add(handlesLbl);

        JLabel emailLbl = new JLabel("<html><b>Email:</b> " + email + "</html>");
        emailLbl.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        emailLbl.setForeground(DESC_COLOR);
        emailLbl.setBorder(new EmptyBorder(0, 0, 6, 0));
        card.add(emailLbl);

        JLabel phoneLbl = new JLabel("<html><b>Phone:</b> " + phone + "</html>");
        phoneLbl.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        phoneLbl.setForeground(DESC_COLOR);
        card.add(phoneLbl);

        return card;
    }

    public static void main(String[] args) {
        try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); } catch (Exception ignored) {}
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Departments - CITIZEN'S VOICE");
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.setMinimumSize(new Dimension(1100, 700));
            frame.setLocationRelativeTo(null);
            frame.setContentPane(new DepartmentsList());
            frame.setVisible(true);
        });
    }
}
