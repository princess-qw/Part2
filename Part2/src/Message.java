import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Message extends JFrame {

    public Message() {
        setTitle("Message App");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);  // Center the window

        // Create buttons
        JButton sendButton = new JButton("Send Message");
        JButton comingSoonButton = new JButton("Coming Soon");
        JButton quitButton = new JButton("Quit");

        // Layout buttons horizontally
        JPanel panel = new JPanel();
        panel.add(sendButton);
        panel.add(comingSoonButton);
        panel.add(quitButton);

        add(panel);

        // Button listeners
        sendButton.addActionListener(e -> sendMessage());
        comingSoonButton.addActionListener(e -> JOptionPane.showMessageDialog(this, "Coming soon..."));
        quitButton.addActionListener(e -> System.exit(0));
    }

    private void sendMessage() {
        String senderName = JOptionPane.showInputDialog(this, "Enter your name:");
        if (senderName == null || senderName.trim().isEmpty()) {
            showError("Name cannot be empty.");
            return;
        }

        String phoneNumber = null;
        while (true) {
            phoneNumber = JOptionPane.showInputDialog(this, "Enter your phone number (must start with +27):");
            if (phoneNumber == null) return; // user canceled
            phoneNumber = phoneNumber.trim();
            if (phoneNumber.startsWith("+27")) {
                break;
            } else {
                showError("Phone number must start with +27.");
            }
        }

        int messageCount = 0;
        while (true) {
            String input = JOptionPane.showInputDialog(this, "How many messages do you want to send?");
            if (input == null) return; // user canceled
            try {
                messageCount = Integer.parseInt(input.trim());
                if (messageCount > 0) {
                    break;
                } else {
                    showError("Please enter a positive number.");
                }
            } catch (NumberFormatException ex) {
                showError("Invalid number. Please try again.");
            }
        }

        StringBuilder allMessages = new StringBuilder();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        for (int i = 1; i <= messageCount; i++) {
            String message;
            while (true) {
                message = JOptionPane.showInputDialog(this, "Enter message " + i + " (max 250 words):");
                if (message == null) return; // user canceled
                int wordCount = message.trim().isEmpty() ? 0 : message.trim().split("\\s+").length;
                if (wordCount <= 250) {
                    break;
                } else {
                    showError("Message too long! Please limit to 250 words.");
                }
            }

            String time = LocalDateTime.now().format(formatter);

            allMessages.append("--- Message ").append(i).append(" ---\n");
            allMessages.append("From: ").append(senderName).append("\n");
            allMessages.append("Phone: ").append(phoneNumber).append("\n");
            allMessages.append("Time: ").append(time).append("\n");
            allMessages.append("Message: ").append(message).append("\n\n");
        }

        JTextArea textArea = new JTextArea(allMessages.toString());
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(400, 300));

        JOptionPane.showMessageDialog(this, scrollPane, "Sent Messages", JOptionPane.INFORMATION_MESSAGE);
    }

    private void showError(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new Message().setVisible(true);
        });
    }
}

