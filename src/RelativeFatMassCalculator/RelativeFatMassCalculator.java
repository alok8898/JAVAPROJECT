package RelativeFatMassCalculator;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;

public class RelativeFatMassCalculator extends JFrame implements ActionListener {
    private JTextField heightField, waistField, sexField, rfMField;
    private JButton calculateButton;

    public RelativeFatMassCalculator() {
        super("Relative Fat Mass Calculator");

        JLabel heightLabel = new JLabel("Height (in cm):");
        heightField = new JTextField(5);
        JLabel waistLabel = new JLabel("Waist (in cm):");
        waistField = new JTextField(5);
        JLabel sexLabel = new JLabel("Sex (M/F):");
        sexField = new JTextField(5);
        // JLabel ageLabel = new JLabel("Age (in years):");
        // ageField = new JTextField(5);
        JLabel rfMLabel = new JLabel("Relative Fat Mass:");
        rfMField = new JTextField(5);
        rfMField.setEditable(false);

        calculateButton = new JButton("Calculate");
        calculateButton.addActionListener(this);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 2));
        panel.add(heightLabel);
        panel.add(heightField);
        panel.add(waistLabel);
        panel.add(waistField);
        panel.add(sexLabel);
        panel.add(sexField);
        // panel.add(ageLabel);
        // panel.add(ageField);
        panel.add(rfMLabel);
        panel.add(rfMField);

        getContentPane().add(panel, BorderLayout.CENTER);
        getContentPane().add(calculateButton, BorderLayout.SOUTH);

        pack();
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        try {
            double height = Double.parseDouble(heightField.getText());
            double waist = Double.parseDouble(waistField.getText());
            String sex = sexField.getText().toUpperCase();
            
            if (sex.length() != 1 || (sex.charAt(0) != 'M' && sex.charAt(0) != 'F')) {
                throw new IllegalArgumentException("Invalid sex input. Please enter 'M' or 'F'.");
            }
    
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/javapproject", "root", "12345");
            Statement statement = connection.createStatement();
            statement.execute("INSERT INTO rfm (height, waist, sex) VALUES (" + height + ", " + waist + ", '" + sex + "')");
            ResultSet resultSet = statement.executeQuery("SELECT * FROM rfm");
            while (resultSet.next()) {
                double dbHeight = resultSet.getDouble("height");
                double dbWaist = resultSet.getDouble("waist");
                String dbSex = resultSet.getString("sex");
                
                if (dbSex.equalsIgnoreCase("M")) {
                    double rfm = 64 - (20 * (dbHeight / dbWaist));
                    rfMField.setText(String.format("%.2f", rfm));
                } else if (dbSex.equalsIgnoreCase("F")) {
                    double rfm = 76 - (20 * (dbHeight / dbWaist));
                    rfMField.setText(String.format("%.2f", rfm));
                }
            }
            connection.close();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid input!");
        } catch (ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }
    
    public static void main(String[] args) {
        new RelativeFatMassCalculator();
    }
}
