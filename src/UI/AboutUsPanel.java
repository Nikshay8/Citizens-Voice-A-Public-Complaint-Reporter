package UI;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class AboutUsPanel extends JPanel {
    private static final Color HEADING_BLACK = Color.BLACK;
    private static final Color BULLET_BLUE = new Color(36, 142, 210);
    private static final Color PANEL_BG = new Color(245, 245, 247);

    public AboutUsPanel() {
        setLayout(new BorderLayout());
        setBackground(PANEL_BG);

        // Top panel: main title
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        topPanel.setOpaque(false);
        topPanel.setBorder(null);

        JLabel title = new JLabel("About Citizen's Voice");
        title.setFont(new Font("Segoe UI", Font.BOLD, 38));
        title.setForeground(HEADING_BLACK);
        title.setBorder(new EmptyBorder(32, 48, 0, 0)); // Padding: top, left
        topPanel.add(title);

        add(topPanel, BorderLayout.NORTH);

        // Content panel
        JPanel content = new JPanel();
        content.setOpaque(false);
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setBorder(new EmptyBorder(30, 60, 30, 60)); // Padding

        // Section: Empowering Citizens
        content.add(sectionHeader("Empowering Citizens:"));
        content.add(sectionText("Citizen's Voice is a modern civic-tech platform for transparent public complaint reporting, designed to connect individuals and authorities seamlessly."));
        content.add(sectionText("We believe every citizen should have a direct channel to voice civic issues and drive positive change in society."));
        content.add(sectionText("Through our platform, users can quickly engage with government bodies and stay updated on redressal progress."));
        content.add(sectionSpacer());

        // Section: Project Features
        content.add(sectionHeader("Project Features:"));
        content.add(bulletText(" Smart complaint submission with category and sub-category selection, secure data entry, and real-time progress tracking."));
        //content.add(bulletText(" Automated routing to relevant departments ensures every issue reaches the right authority without manual intervention."));
        content.add(bulletText(" Secure data management and privacy protection adhering to governmental and industry standards."));
        content.add(bulletText(" Accessible design for web/mobile and easy-to-navigate interface."));
        content.add(bulletText(" Feedback forms and FAQs empower citizens with information and a sense of responsiveness from civic agencies."));
        content.add(bulletText(" Relaxed feedback system users can report resolved cases, provide department ratings, and suggest improvements."));
        content.add(sectionSpacer());

        // Section: Mission Statement
        content.add(sectionHeader("Mission Statement:"));
        content.add(sectionText("We strive to strengthen community trust and drive efficiency in public grievance redressal."));
        content.add(sectionText("We envision a future where every citizen feels empowered to participate in civic improvement through accessible digital tools."));
        content.add(sectionSpacer());

        // Section: Contact & Support
        content.add(sectionHeader("Contact & Support:"));
        content.add(contactBullet("Helpline: 1800-123-999"));
        content.add(contactBullet("Email: support@citizensvoice.in"));
        content.add(contactBullet("Address: New Delhi, India"));
        content.add(contactBullet("Support Timings: 9 AM - 7 PM (Monday to Saturday)"));
        content.add(contactBullet("You can also reach us through the online contact form on our website."));
        content.add(sectionSpacer());


        JScrollPane centerScroll = new JScrollPane(
                content,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED
        );
        centerScroll.setBorder(null);
        centerScroll.getViewport().setOpaque(false);
        centerScroll.setOpaque(false);
        centerScroll.getVerticalScrollBar().setUnitIncrement(18);
        centerScroll.getHorizontalScrollBar().setUnitIncrement(16); //smooth horizontal

        JPanel outerPanel = new JPanel(new BorderLayout());
        outerPanel.setBackground(PANEL_BG);
        outerPanel.setBorder(new EmptyBorder(8, 0, 16, 0)); // (top, left, bottom, right)
        outerPanel.add(centerScroll, BorderLayout.CENTER);

        add(outerPanel, BorderLayout.CENTER);
    }

    private JLabel sectionHeader(String text) {
        JLabel lbl = new JLabel(text, SwingConstants.LEFT);
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 32));
        lbl.setForeground(HEADING_BLACK);
        lbl.setBorder(new EmptyBorder(32, 0, 8, 0));
        return lbl;
    }

    private JLabel sectionText(String text) {
        JLabel lbl = new JLabel("<html><div style='font-size:20pt;text-align:left;'>" + text + "</div></html>");
        lbl.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        lbl.setForeground(new Color(44, 62, 80));
        lbl.setBorder(new EmptyBorder(0, 10, 12, 0));
        return lbl;
    }

    private JLabel bulletText(String text) {
        JLabel lbl = new JLabel(
                String.format("<html><div style='font-size:18pt;text-align:left;'><span style='color:#2489d3;'>&#8226;</span> %s</div></html>", text)
        );
        lbl.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        lbl.setBorder(new EmptyBorder(0, 32, 14, 0));
        return lbl;
    }

    private JLabel contactBullet(String text) {
        JLabel lbl = new JLabel(
                String.format("<html><div style='font-size:18pt;text-align:left;'><span style='color:#209680;'>&#8226;</span> %s</div></html>", text)
        );
        lbl.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        lbl.setBorder(new EmptyBorder(0, 32, 14, 0));
        return lbl;
    }

    private Component sectionSpacer() {
        return Box.createVerticalStrut(28);
    }

    public static void main(String[] args) {
        try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); } catch (Exception ignored) {}

        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("About Us - Citizen's Voice");
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.setMinimumSize(new Dimension(900, 600));
            frame.setSize(1100, 720);
            frame.setLocationRelativeTo(null);
            frame.setContentPane(new AboutUsPanel());
            frame.setVisible(true);
        });
    }
}
