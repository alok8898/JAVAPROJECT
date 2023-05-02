package CorpulenceIndexCalculator;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;

public class CorpulenceIndexCalculator extends JFrame implements ActionListener {
    private JTextField weightField, heightField, corpulenceIndexField;
    private JButton calculateButton;

    public CorpulenceIndexCalculator() {
        super("Corpulence Index Calculator");

        JLabel weightLabel = new JLabel("Weight (in kg):");
        weightField = new JTextField(5);
        JLabel heightLabel = new JLabel("Height (in cm):");
        heightField = new JTextField(5);
        JLabel corpulenceIndexLabel = new JLabel("Corpulence Index:");
        corpulenceIndexField = new JTextField(5);
        corpulenceIndexField.setEditable(false);

        calculateButton = new JButton("Calculate");
        calculateButton.addActionListener(this);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2));
        panel.add(weightLabel);
        panel.add(weightField);
        panel.add(heightLabel);
        panel.add(heightField);
        panel.add(corpulenceIndexLabel);
        panel.add(corpulenceIndexField);

        getContentPane().add(panel, BorderLayout.CENTER);
        getContentPane().add(calculateButton, BorderLayout.SOUTH);

        pack();
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        try {
            double weight = Double.parseDouble(weightField.getText());
            double height = Double.parseDouble(heightField.getText());

            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/javapproject", "root", "12345");
            Statement statement = connection.createStatement();
            statement.execute("INSERT INTO ci(weight, height) VALUES (" + weight + ", " + height + ")");
            ResultSet resultSet = statement.executeQuery("SELECT * FROM ci");
            while (resultSet.next()) {
                double dbWeight = resultSet.getDouble("weight");
                double dbHeight = resultSet.getDouble("height");
                double corpulenceIndex = dbWeight / Math.pow(dbHeight / 100.0, 3);
                corpulenceIndexField.setText(String.format("%.2f", corpulenceIndex));
            }
            connection.close();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid input!");
        } catch (ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        new CorpulenceIndexCalculator();
    }
}
