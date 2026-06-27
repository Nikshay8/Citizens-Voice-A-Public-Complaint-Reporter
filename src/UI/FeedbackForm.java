package UI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class FeedbackForm {

    private int rating = 0; // Star rating

    public static void main(String[] args) {
        SwingUtilities.invokeLater(FeedbackForm::new);
    }

    public FeedbackForm() {

        JFrame frame = new JFrame("Feedback - CITIZEN'S VOICE");
        frame.setSize(520, 580);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        frame.setLayout(new GridBagLayout());
        JPanel mainPanel = new JPanel(null);
        mainPanel.setPreferredSize(new Dimension(500, 540));

        JPanel header = new JPanel(null);
        header.setBackground(new Color(0x0A4F8A));  // bluish-navy
        header.setBounds(0, 0, 500, 60);

        JLabel title = new JLabel("Feedback Form");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Arial", Font.BOLD, 22));
        title.setBounds(20, 15, 300, 30);
        header.add(title);

        mainPanel.add(header);

        // NAME
        JLabel lblName = new JLabel("Your Name:");
        lblName.setBounds(40, 80, 150, 25);
        mainPanel.add(lblName);

        JTextField txtName = new JTextField();
        txtName.setBounds(180, 80, 250, 28);
        mainPanel.add(txtName);

        // EMAIL
        JLabel lblEmail = new JLabel("Your Email:");
        lblEmail.setBounds(40, 130, 150, 25);
        mainPanel.add(lblEmail);

        JTextField txtEmail = new JTextField();
        txtEmail.setBounds(180, 130, 250, 28);
        mainPanel.add(txtEmail);

        // ISSUE TYPE
        JLabel lblIssue = new JLabel("Issue Type:");
        lblIssue.setBounds(40, 180, 150, 25);
        mainPanel.add(lblIssue);

        JComboBox<String> issueBox = new JComboBox<>(new String[]{
                "App Issue",
                "Complaint Not Submitted",
                "Slow Response",
                "Incorrect Details",
                "Login Problem",
                "Other"
        });
        issueBox.setBounds(180, 180, 250, 28);
        mainPanel.add(issueBox);

        // STAR RATING
        JLabel lblRating = new JLabel("Rating:");
        lblRating.setBounds(40, 230, 150, 25);
        mainPanel.add(lblRating);

        JPanel starPanel = new JPanel();
        starPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 0));
        starPanel.setBounds(180, 220, 250, 40);

        JLabel[] stars = new JLabel[5];

        for (int i = 0; i < 5; i++) {
            int starNum = i + 1;

            stars[i] = new JLabel("☆");
            stars[i].setFont(new Font("Serif", Font.BOLD, 40));
            stars[i].setCursor(new Cursor(Cursor.HAND_CURSOR));

            stars[i].addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    rating = starNum;
                    updateStars(stars, rating);
                }
            });

            starPanel.add(stars[i]);
        }

        mainPanel.add(starPanel);

        // MESSAGE BOX
        JLabel lblMessage = new JLabel("Message:");
        lblMessage.setBounds(40, 280, 150, 25);
        mainPanel.add(lblMessage);

        JTextArea txtMessage = new JTextArea();
        txtMessage.setLineWrap(true);
        txtMessage.setWrapStyleWord(true);
        JScrollPane scroll = new JScrollPane(txtMessage);
        scroll.setBounds(180, 280, 250, 130);
        mainPanel.add(scroll);

        // SUBMIT BUTTON
        JButton btnSubmit = new JButton("Submit");
        btnSubmit.setBounds(180, 440, 100, 35);
        btnSubmit.setBackground(new Color(0x42A5F5));
        btnSubmit.setForeground(Color.WHITE);
        btnSubmit.setFont(new Font("Arial", Font.BOLD, 14));
        mainPanel.add(btnSubmit);

        // CANCEL BUTTON
        JButton btnCancel = new JButton("Cancel");
        btnCancel.setBounds(300, 440, 100, 35);
        btnCancel.setBackground(new Color(200, 60, 60));
        btnCancel.setForeground(Color.WHITE);
        btnCancel.setFont(new Font("Arial", Font.BOLD, 14));
        btnCancel.addActionListener(e -> frame.dispose());
        mainPanel.add(btnCancel);

        btnSubmit.addActionListener(e -> {

            // DB CONNECTIVITY
            boolean ok = DatabaseConnection.registerFeedback(
                    txtName.getText(),
                    txtEmail.getText(),
                    issueBox.getSelectedItem().toString(),
                    rating,
                    txtMessage.getText()
            );

            if (ok) {
                JOptionPane.showMessageDialog(frame,
                        "Feedback Submitted!\n\n" +
                                "Name: " + txtName.getText() + "\n" +
                                "Email: " + txtEmail.getText() + "\n" +
                                "Issue Type: " + issueBox.getSelectedItem() + "\n" +
                                "Rating (Stars): " + rating + "\n" +
                                "Message: " + txtMessage.getText(),
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE
                );

                txtName.setText("");
                txtEmail.setText("");
                issueBox.setSelectedIndex(0);
                txtMessage.setText("");
                rating = 0;
                updateStars(stars, rating);
            }
        });

        frame.add(mainPanel, new GridBagConstraints());
        frame.setVisible(true);
    }

    private void updateStars(JLabel[] stars, int rating) {
        for (int i = 0; i < stars.length; i++) {
            stars[i].setText(i < rating ? "★" : "☆");
        }
    }
}
