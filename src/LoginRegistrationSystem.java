import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;

public class LoginRegistrationSystem extends JFrame implements ActionListener {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton registerButton;

    public LoginRegistrationSystem() {
        super("Login");

        // Set window properties
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setSize(400, 250);
        setLocationRelativeTo(null);

        // Create UI elements
        JLabel title = new JLabel("Login", SwingConstants.CENTER);
        title.setFont(new Font("Helvetica", Font.BOLD, 24));

        JLabel usernameLabel = new JLabel("Username:");
        usernameField = new JTextField(10);

        JLabel passwordLabel = new JLabel("Password:");
        passwordField = new JPasswordField(10);

        loginButton = new JButton("Login");
        loginButton.addActionListener(this);

        registerButton = new JButton("Register");
        registerButton.addActionListener(this);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.add(usernameLabel);
        panel.add(usernameField);
        panel.add(passwordLabel);
        panel.add(passwordField);
        panel.add(new JLabel(""));
        panel.add(loginButton);
        panel.add(new JLabel(""));
        panel.add(registerButton);

        // Set colors and fonts
        panel.setBackground(Color.WHITE);
        usernameLabel.setForeground(Color.BLACK);
        passwordLabel.setForeground(Color.BLACK);
        loginButton.setBackground(new Color(50, 205, 50));
        loginButton.setForeground(Color.WHITE);
        registerButton.setBackground(new Color(70, 130, 180));
        registerButton.setForeground(Color.WHITE);

        // Add elements to the frame
        getContentPane().add(title, BorderLayout.NORTH);
        getContentPane().add(panel, BorderLayout.CENTER);

        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loginButton) {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());

            try {
                System.out.println("Trying to connect to database...");
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/javapproject", "root",
                        "12345");
                PreparedStatement preparedStatement = connection
                        .prepareStatement("SELECT * FROM users WHERE BINARY username=? AND BINARY password=?");
                preparedStatement.setString(1, username);
                preparedStatement.setString(2, password);
                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    JOptionPane.showMessageDialog(this, "Login successful!");
                    App obj = new App();
                    obj.setVisible(true);
                    // dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "Invalid username or password!");
                }
                connection.close();
            } catch (ClassNotFoundException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            }
        } else if (e.getSource() == registerButton) {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());

            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/javapproject", "root",
                        "12345");
                PreparedStatement preparedStatement = connection
                        .prepareStatement("SELECT * FROM users WHERE BINARY username=?");
                preparedStatement.setString(1, username);
                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    JOptionPane.showMessageDialog(this, "Username already exists!");
                } else {
                    preparedStatement = connection
                            .prepareStatement("INSERT INTO users (username, password) VALUES (?, ?)");
                    preparedStatement.setString(1, username);
                    preparedStatement.setString(2, password);
                    preparedStatement.executeUpdate();
                    JOptionPane.showMessageDialog(this, "Registration successful!");
                }
                connection.close();
            } catch (ClassNotFoundException ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            }
        }
    }

    public static void main(String[] args) {

        new LoginRegistrationSystem();

    }
}