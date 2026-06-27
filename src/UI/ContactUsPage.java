package UI;

import javax.swing.*;
import java.awt.*;

public class ContactUsPage {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ContactUsPage::new);
    }

    public ContactUsPage() {

        JFrame frame = new JFrame("Contact Us - CITIZEN'S VOICE");
        frame.setSize(1000, 600);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        JPanel main = new JPanel(null);
        main.setBackground(Color.WHITE);
        frame.setContentPane(main);

        int panelW = 380;
        int panelH = 300;
        int gap = 80;

        JPanel p1 = new JPanel(null);
        p1.setBackground(new Color(235, 235, 235));

        JPanel p2 = new JPanel(null);
        p2.setBackground(new Color(235, 235, 235));

        main.add(p1);
        main.add(p2);

        // FEEDBACK PANEL
        JLabel t1 = new JLabel("Any Feedback Or Suggestion :");
        t1.setFont(new Font("Arial", Font.BOLD, 24));
        t1.setBounds(20, 20, 350, 30);
        p1.add(t1);

        JLabel m1 = new JLabel("Mail to : citizensvoice11@gmail.com");
        m1.setFont(new Font("Arial", Font.PLAIN, 17));
        m1.setBounds(20, 90, 330, 25);
        p1.add(m1);

        JLabel c1 = new JLabel("Contact at : 9711169222");
        c1.setFont(new Font("Arial", Font.PLAIN, 17));
        c1.setBounds(20, 130, 330, 25);
        p1.add(c1);

        // COMPLAINT PANEL
        JLabel t2 = new JLabel("If Any Complaint :");
        t2.setFont(new Font("Arial", Font.BOLD, 24));
        t2.setBounds(20, 20, 350, 30);
        p2.add(t2);

        JLabel m2 = new JLabel("Mail to : support@citizensvoice.in");
        m2.setFont(new Font("Arial", Font.PLAIN, 17));
        m2.setBounds(20, 90, 330, 25);
        p2.add(m2);

        JLabel c2 = new JLabel("Contact at : 1800-123-999");
        c2.setFont(new Font("Arial", Font.PLAIN, 17));
        c2.setBounds(20, 130, 330, 25);
        p2.add(c2);

        // NEW ADDRESS LINE
        JLabel address = new JLabel("<html>Address : Building No. 88A,<br>Paschim Vihar, near BVICAM, New Delhi</html>");
        address.setFont(new Font("Arial", Font.PLAIN, 17));
        address.setBounds(20, 170, 330, 50);
        p2.add(address);

        frame.setVisible(true);

        frame.addComponentListener(new java.awt.event.ComponentAdapter() {
            @Override
            public void componentResized(java.awt.event.ComponentEvent e) {

                SwingUtilities.invokeLater(() -> {

                    int fw = frame.getWidth();
                    int fh = frame.getHeight();

                    int totalW = panelW * 2 + gap;
                    int startX = (fw - totalW) / 2;

                    int panelY = 120;

                    if (fh > 800) {
                        panelY = (int) (fh * 0.20);
                    }

                    p1.setBounds(startX, panelY, panelW, panelH);
                    p2.setBounds(startX + panelW + gap, panelY, panelW, panelH);
                });
            }
        });
    }
}
