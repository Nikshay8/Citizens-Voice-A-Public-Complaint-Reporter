package UI;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class TermsConditionsPanel extends JPanel {

    private static final Color HEADING_BLACK = Color.BLACK;
    private static final Color BULLET_BLUE = new Color(36, 142, 210);
    private static final Color PANEL_BG = new Color(245, 245, 247);

    public TermsConditionsPanel() {

        setLayout(new BorderLayout());
        setBackground(PANEL_BG);

        // TOP HEADING
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        topPanel.setOpaque(false);
        JLabel title = new JLabel("Terms & Conditions");
        title.setFont(new Font("Segoe UI", Font.BOLD, 38));
        title.setForeground(HEADING_BLACK);
        title.setBorder(new EmptyBorder(32, 48, 0, 0));
        topPanel.add(title);
        add(topPanel, BorderLayout.NORTH);

        // CONTENT PANEL
        JPanel content = new JPanel();
        content.setOpaque(false);
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setBorder(new EmptyBorder(30, 60, 30, 60));

        // 1. Introduction
        content.add(sectionHeader("1. Introduction"));
        content.add(sectionText("These Terms & Conditions govern the use of Citizen’s Voice - a civic platform that enables users to submit public complaints, track issues and communicate with civic authorities."));
        content.add(sectionText("By accessing or using this platform, you agree to comply with and be bound by these terms."));
        content.add(sectionSpacer());

        // 2. User Responsibilities
        content.add(sectionHeader("2. User Responsibilities"));
        content.add(bulletText(" You must provide accurate, complete, and truthful information while registering or filing any complaint."));
        content.add(bulletText(" You agree not to misuse the platform for false, abusive, or misleading complaints."));
        content.add(bulletText(" Any uploaded documents or photos must be genuine and legally permissible."));
        content.add(bulletText(" You must not attempt to hack, disrupt, or compromise platform security."));
        content.add(bulletText(" You agree to follow local laws while submitting civic-related issues."));
        content.add(sectionSpacer());

        // 3. Complaint Processing & Limitations
        content.add(sectionHeader("3. Complaint Processing & Limitations"));
        content.add(sectionText("Citizen’s Voice forwards user complaints to relevant municipal or civic authorities. The platform does not guarantee immediate resolution."));
        content.add(bulletText(" Complaint timelines depend on the specific department."));
        content.add(bulletText(" The platform is not liable for delays caused by authorities or external agencies."));
        content.add(bulletText(" Complaints outside the jurisdiction of civic bodies may not be accepted."));
        content.add(sectionSpacer());

        // 4. Data Privacy & Usage
        content.add(sectionHeader("4. Data Privacy & Usage"));
        content.add(sectionText("We prioritize user privacy and ensure that personal information is used only for complaint verification and communication."));
        content.add(bulletText(" Your data will not be sold or shared for marketing."));
        content.add(bulletText(" Information may be shared with government departments for processing complaints."));
        content.add(bulletText(" The platform follows standard data-security guidelines."));
        content.add(sectionSpacer());

        // 5. Prohibited Activities
        content.add(sectionHeader("5. Prohibited Activities"));
        content.add(bulletText(" Submitting fake complaints."));
        content.add(bulletText(" Impersonating government officials or other individuals."));
        content.add(bulletText(" Posting abusive, threatening, or harmful content."));
        content.add(bulletText(" Uploading illegal documents or copyrighted material."));
        content.add(sectionSpacer());

        // 6. Platform Rights
        content.add(sectionHeader("6. Platform Rights"));
        content.add(sectionText("Citizen’s Voice reserves the right to:"));
        content.add(bulletText(" Suspend or block users who violate the Terms & Conditions."));
        content.add(bulletText(" Modify or update platform features without prior notice."));
        content.add(bulletText(" Forward user data to authorities in case of legal violations."));
        content.add(sectionSpacer());

        // 7. Liability Disclaimer
        content.add(sectionHeader("7. Liability Disclaimer"));
        content.add(sectionText("Citizen’s Voice is a mediator platform and is not responsible for final resolutions. All actions taken by government departments are beyond platform control."));
        content.add(sectionText("The platform is not liable for any loss, delay, or damages arising from usage."));
        content.add(sectionSpacer());

        // 8. Changes to Terms
        content.add(sectionHeader("8. Changes to Terms"));
        content.add(sectionText("The platform reserves full rights to update or modify these Terms & Conditions at any time. Continued use of the platform implies acceptance of updated terms."));
        content.add(sectionSpacer());

        // 9. Contact Information
        content.add(sectionHeader("9. Contact Information"));
        content.add(contactBullet("Helpline: 1800-123-999"));
        content.add(contactBullet("Email: support@citizensvoice.in"));
        content.add(contactBullet("Address: Paschim Vihar, New Delhi"));
        content.add(sectionSpacer());

        // SCROLL WRAPPER
        JScrollPane scroll = new JScrollPane(content, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scroll.setBorder(null);
        scroll.getViewport().setOpaque(false);
        scroll.setOpaque(false);
        scroll.getVerticalScrollBar().setUnitIncrement(18);

        JPanel outer = new JPanel(new BorderLayout());
        outer.setBackground(PANEL_BG);
        outer.setBorder(new EmptyBorder(8, 0, 16, 0));
        outer.add(scroll, BorderLayout.CENTER);

        add(outer, BorderLayout.CENTER);
    }

    // UI helpers
    private JLabel sectionHeader(String text) {
        JLabel lbl = new JLabel(text);
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 32));
        lbl.setForeground(HEADING_BLACK);
        lbl.setBorder(new EmptyBorder(28, 0, 10, 0));
        return lbl;
    }

    private JLabel sectionText(String text) {
        JLabel lbl = new JLabel("<html><div style='font-size:20pt;'>" + text + "</div></html>");
        lbl.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        lbl.setForeground(new Color(44, 62, 80));
        lbl.setBorder(new EmptyBorder(0, 10, 14, 0));
        return lbl;
    }

    private JLabel bulletText(String text) {
        JLabel lbl = new JLabel(
                "<html><div style='font-size:18pt;'><span style='color:#2489d3;'>&#8226;</span>" + text + "</div></html>"
        );
        lbl.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        lbl.setBorder(new EmptyBorder(0, 32, 14, 0));
        return lbl;
    }

    private JLabel contactBullet(String text) {
        JLabel lbl = new JLabel(
                "<html><div style='font-size:18pt;'><span style='color:#209680;'>&#8226;</span> " + text + "</div></html>"
        );
        lbl.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        lbl.setBorder(new EmptyBorder(0, 32, 14, 0));
        return lbl;
    }

    private Component sectionSpacer() {
        return Box.createVerticalStrut(26);
    }


    public static void main(String[] args) {
        try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); } catch (Exception ignored) {}

        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Terms & Conditions - Citizen's Voice");
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.setSize(1100, 720);
            frame.setLocationRelativeTo(null);
            frame.setContentPane(new TermsConditionsPanel());
            frame.setVisible(true);
        });
    }
}
