package BodySurfaceAreaCalculator;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;

public class BodySurfaceAreaCalculator extends JFrame implements ActionListener {
    private JTextField heightField, weightField, bsaField;
    private JButton calculateButton;

    public BodySurfaceAreaCalculator() {
        super("Body Surface Area Calculator");

        JLabel heightLabel = new JLabel("Height (in cm):");
        heightField = new JTextField(5);
        JLabel weightLabel = new JLabel("Weight (in kg):");
        weightField = new JTextField(5);
        JLabel bsaLabel = new JLabel("Body Surface Area (in m²):");
        bsaField = new JTextField(5);
        bsaField.setEditable(false);

        calculateButton = new JButton("Calculate");
        calculateButton.addActionListener(this);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2));
        panel.add(heightLabel);
        panel.add(heightField);
        panel.add(weightLabel);
        panel.add(weightField);
        panel.add(bsaLabel);
        panel.add(bsaField);

        getContentPane().add(panel, BorderLayout.CENTER);
        getContentPane().add(calculateButton, BorderLayout.SOUTH);

        pack();
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        try {
            double height = Double.parseDouble(heightField.getText());
            double weight = Double.parseDouble(weightField.getText());

            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/javapproject", "root", "12345");
            Statement statement = connection.createStatement();
            statement.execute("INSERT INTO bsa (height, weight) VALUES (" + height + ", " + weight + ")");
            ResultSet resultSet = statement.executeQuery("SELECT * FROM bsa");
            while (resultSet.next()) {
                double dbHeight = resultSet.getDouble("height");
                double dbWeight = resultSet.getDouble("weight");
                double bsa = calculateBSA(dbHeight, dbWeight);
                bsaField.setText(String.format("%.2f", bsa));
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

    private double calculateBSA(double height, double weight) {
        return Math.sqrt((height * weight) / 3600.0);
    }

    public static void main(String[] args) {
        new BodySurfaceAreaCalculator();
    }
}
