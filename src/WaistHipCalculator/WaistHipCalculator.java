package WaistHipCalculator;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;

public class WaistHipCalculator extends JFrame implements ActionListener {
    private JTextField waistField, hipField, ratioField;
    private JButton calculateButton;

    public WaistHipCalculator() {
        super("Waist-to-Hip Ratio Calculator");

        JLabel waistLabel = new JLabel("Waist (in cm):");
        waistField = new JTextField(5);
        JLabel hipLabel = new JLabel("Hip (in cm):");
        hipField = new JTextField(5);
        JLabel ratioLabel = new JLabel("Waist-to-Hip Ratio:");
        ratioField = new JTextField(5);
        ratioField.setEditable(false);

        calculateButton = new JButton("Calculate");
        calculateButton.addActionListener(this);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2));
        panel.add(waistLabel);
        panel.add(waistField);
        panel.add(hipLabel);
        panel.add(hipField);
        panel.add(ratioLabel);
        panel.add(ratioField);

        getContentPane().add(panel, BorderLayout.CENTER);
        getContentPane().add(calculateButton, BorderLayout.SOUTH);

        pack();
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        try {
            double waist = Double.parseDouble(waistField.getText());
            double hip = Double.parseDouble(hipField.getText());

            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/javapproject", "root", "12345");
            Statement statement = connection.createStatement();
            statement.execute("INSERT INTO waisthip (waist, hip) VALUES (" + waist + ", " + hip + ")");
            ResultSet resultSet = statement.executeQuery("SELECT * FROM waisthip");
            while (resultSet.next()) {
                double dbWaist = resultSet.getDouble("waist");
                double dbHip = resultSet.getDouble("hip");
                double ratio = dbWaist / dbHip;
                ratioField.setText(String.format("%.2f", ratio));
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
        new WaistHipCalculator();
    }
}
