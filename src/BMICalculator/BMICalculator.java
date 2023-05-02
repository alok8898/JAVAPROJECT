package BMICalculator;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;

public class BMICalculator extends JFrame implements ActionListener {
    private JTextField heightField, weightField, bmiField;
    private JButton calculateButton;

    public BMICalculator() {
        super("BMI Calculator");

        JLabel heightLabel = new JLabel("Height (in cm):");
        heightField = new JTextField(5);
        JLabel weightLabel = new JLabel("Weight (in kg):");
        weightField = new JTextField(5);
        JLabel bmiLabel = new JLabel("BMI:");
        bmiField = new JTextField(5);
        bmiField.setEditable(false);

        calculateButton = new JButton("Calculate");
        calculateButton.addActionListener(this);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2));
        panel.add(heightLabel);
        panel.add(heightField);
        panel.add(weightLabel);
        panel.add(weightField);
        panel.add(bmiLabel);
        panel.add(bmiField);

        getContentPane().add(panel, BorderLayout.CENTER);
        getContentPane().add(calculateButton, BorderLayout.SOUTH);

        pack();
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        try {
            double height = Double.parseDouble(heightField.getText()); // In cm
            double weight = Double.parseDouble(weightField.getText()); // In kg 

            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/javapproject", "root", "12345");
            Statement statement = connection.createStatement();
            statement.execute("INSERT INTO bmi (height, weight) VALUES (" + height + ", " + weight + ")");
            ResultSet resultSet = statement.executeQuery("SELECT * FROM bmi");
            while (resultSet.next()) {
                double dbHeight = resultSet.getDouble("height") / 100; // convert cm to m
                double dbWeight = resultSet.getDouble("weight"); // In kg
                double bmi = dbWeight / (dbHeight * dbHeight); // use cm and kg
                bmiField.setText(String.format("%.2f", bmi));
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
        new BMICalculator();
    }
}
