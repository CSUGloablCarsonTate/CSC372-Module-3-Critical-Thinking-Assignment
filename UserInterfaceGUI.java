import javax.swing.*;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class UserInterfaceGUI extends JFrame {

    // Declare the main panels, text box, and status label
    private JPanel mainPanel;
    private JPanel homePanel;
    private JTextPane textBox;
    private JLabel statusLabel;

    // Constructor
    public UserInterfaceGUI() {

        // Creating the application's JFrame window 
        setTitle("CSC372 Module 3 Critical Thinking Assignment User Interface Application");
        setSize(550, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Create the main JPanel with CardLayout
        CardLayout cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        add(mainPanel);

        // Create the navigation bar
        JMenuBar menuBar = new JMenuBar();
        menuBar.setLayout(new FlowLayout(FlowLayout.LEFT, 12, 2));

        JMenuItem homeNavigation = new JMenuItem("Home");
        JMenuItem optionsNavigation = new JMenuItem("Options Menu");

        // Format the navigation menu text
        homeNavigation.setFont(new Font("SansSerif", Font.BOLD, 13));
        optionsNavigation.setFont(new Font("SansSerif", Font.BOLD, 13));
        homeNavigation.setForeground(Color.BLACK);
        optionsNavigation.setForeground(Color.BLACK);

        menuBar.add(homeNavigation);
        menuBar.add(optionsNavigation);
        setJMenuBar(menuBar);

        // Create the Home page
        homePanel = new JPanel(new BorderLayout(15, 15));
        homePanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        homePanel.setBackground(Color.WHITE);

        // Create the application's heading
        JLabel headingLabel = new JLabel("User Interface", SwingConstants.CENTER);
        headingLabel.setFont(new Font("SansSerif", Font.BOLD, 22));
        headingLabel.setForeground(Color.BLACK);

        // Create the instruction label
        JLabel instructionLabel = new JLabel(
            "Use the Options Menu to display and save the date and time.",
            SwingConstants.CENTER);
        instructionLabel.setFont(new Font("SansSerif", Font.BOLD, 12));
        instructionLabel.setForeground(Color.BLACK);

        // Create the header panel
        JPanel headerPanel = new JPanel(new GridLayout(2, 1, 0, 5));
        headerPanel.setOpaque(false);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        headerPanel.add(headingLabel);
        headerPanel.add(instructionLabel);

        // Create the centered text box
        textBox = new JTextPane();
        textBox.setFont(new Font("SansSerif", Font.BOLD, 17));
        textBox.setForeground(Color.BLACK);
        textBox.setBorder(BorderFactory.createEmptyBorder(20, 12, 12, 12));
        textBox.setText("Select Options Menu to display the date and time.");
        centerText();

        JScrollPane scrollPane = new JScrollPane(textBox);
        scrollPane.setPreferredSize(new Dimension(450, 125));
        scrollPane.setBorder(BorderFactory.createLineBorder(
            new Color(210, 210, 210)));

        // Create a label for the text box
        JLabel textLabel = new JLabel(
            "CURRENT DATE AND TIME", SwingConstants.CENTER);
        textLabel.setFont(new Font("SansSerif", Font.BOLD, 12));
        textLabel.setForeground(Color.BLACK);

        // Create a panel for the text box
        JPanel textPanel = new JPanel(new BorderLayout(0, 8));
        textPanel.setOpaque(false);
        textPanel.add(textLabel, BorderLayout.NORTH);
        textPanel.add(scrollPane, BorderLayout.CENTER);

        // Organize the layout of the Home page
        JPanel contentPanel = new JPanel(new BorderLayout(0, 15));
        contentPanel.setOpaque(false);
        contentPanel.add(headerPanel, BorderLayout.NORTH);
        contentPanel.add(textPanel, BorderLayout.CENTER);

        homePanel.add(contentPanel, BorderLayout.NORTH);

        // Create the centered status label
        statusLabel = new JLabel("Status: Ready", SwingConstants.CENTER);
        statusLabel.setFont(new Font("SansSerif", Font.BOLD, 12));
        statusLabel.setForeground(Color.BLACK);
        statusLabel.setOpaque(true);
        statusLabel.setBackground(Color.WHITE);
        statusLabel.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));

        homePanel.add(statusLabel, BorderLayout.SOUTH);

        // Create the Options Menu page
        JPanel optionsPanel = new JPanel(new BorderLayout(15, 15));
        optionsPanel.setBorder(BorderFactory.createEmptyBorder(25, 40, 25, 40));
        optionsPanel.setBackground(Color.WHITE);

        // Create the Options Menu heading
        JLabel optionsTitle = new JLabel(
            "Options Menu", SwingConstants.CENTER);
        optionsTitle.setFont(new Font("SansSerif", Font.BOLD, 24));
        optionsTitle.setForeground(Color.BLACK);

        JLabel optionsInstructions = new JLabel(
            "Select one of the following actions:",
            SwingConstants.CENTER);
        optionsInstructions.setFont(new Font("SansSerif", Font.BOLD, 13));
        optionsInstructions.setForeground(Color.BLACK);

        JPanel optionsHeader = new JPanel(new GridLayout(2, 1, 0, 5));
        optionsHeader.setOpaque(false);
        optionsHeader.add(optionsTitle);
        optionsHeader.add(optionsInstructions);

        optionsPanel.add(optionsHeader, BorderLayout.NORTH);

        // Create the four menu buttons
        JButton dateTimeButton = new JButton("Display Date and Time");
        JButton saveButton = new JButton("Save to File");
        JButton colorButton = new JButton("Change Background Color");
        JButton exitButton = new JButton("Exit");

        // Organize the layout of the four menu buttons
        JPanel buttonPanel = new JPanel(new GridLayout(4, 1, 0, 12));
        buttonPanel.setOpaque(false);

        JButton[] buttons = {
            dateTimeButton, saveButton, colorButton, exitButton
        };

        for (JButton button : buttons) {
            button.setFont(new Font("SansSerif", Font.BOLD, 14));
            button.setBackground(Color.WHITE);
            button.setForeground(Color.BLACK);
            button.setFocusPainted(false);
            buttonPanel.add(button);
        }

        optionsPanel.add(buttonPanel, BorderLayout.CENTER);

        // Add both pages to the application
        mainPanel.add(homePanel, "HOME");
        mainPanel.add(optionsPanel, "OPTIONS");

        // Navigate between the two pages
        homeNavigation.addActionListener(e -> {
            cardLayout.show(mainPanel, "HOME");
        });

        optionsNavigation.addActionListener(e -> {
            cardLayout.show(mainPanel, "OPTIONS");
        });

        // Display the current date and time when selected
        dateTimeButton.addActionListener(e -> {

            LocalDateTime now = LocalDateTime.now();

            DateTimeFormatter dateFormatter =
                DateTimeFormatter.ofPattern("MMMM dd, yyyy");

            DateTimeFormatter timeFormatter =
                DateTimeFormatter.ofPattern("hh:mm:ss a");

            textBox.setText("Date: " + now.format(dateFormatter)
                + "\nTime: " + now.format(timeFormatter));

            centerText();

            statusLabel.setText("Status: Date and time updated");
            cardLayout.show(mainPanel, "HOME");
        });

        // Save the text box contents to log.txt when selected
        saveButton.addActionListener(e -> {

            try (FileWriter writer = new FileWriter("log.txt")) {

                writer.write(textBox.getText());

                JOptionPane.showMessageDialog(this,
                    "Text successfully saved to log.txt");

                statusLabel.setText("Status: Saved to log.txt");

            } catch (IOException ex) {

                JOptionPane.showMessageDialog(this,
                    "Error saving file: " + ex.getMessage());

                statusLabel.setText("Status: Error saving file");
            }

            cardLayout.show(mainPanel, "HOME");
        });

        // Generate a random shade of the color green
        Random random = new Random();

        int greenValue = 165 + random.nextInt(91);
        Color randomGreen = new Color(0, greenValue, 0);

        // Change the background frame to a random shade of the color green
        colorButton.addActionListener(e -> {

            homePanel.setBackground(randomGreen);

            String hexColor = String.format("#%02X%02X%02X",
                randomGreen.getRed(),
                randomGreen.getGreen(),
                randomGreen.getBlue());

            colorButton.setText("Change Background Color - " + hexColor);
            statusLabel.setText("Status: Background changed to " + hexColor);

            cardLayout.show(mainPanel, "HOME");
        });

        // Exit the application when selected
        exitButton.addActionListener(e -> {
            System.exit(0);
        });

        // Display the Home page
        cardLayout.show(mainPanel, "HOME");

        // Display the application's window
        setVisible(true);
    }

    // Center the text inside the text box
    private void centerText() {

        SimpleAttributeSet centeredText = new SimpleAttributeSet();
        StyleConstants.setAlignment(
            centeredText, StyleConstants.ALIGN_CENTER);

        textBox.getStyledDocument().setParagraphAttributes(
            0, textBox.getDocument().getLength(), centeredText, false);
    }

    // Main method
    public static void main(String[] args) {
        new UserInterfaceGUI();
    }
}