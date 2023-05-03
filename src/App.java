import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import BMICalculator.BMICalculator; 
import WaistHipCalculator.WaistHipCalculator;
import RelativeFatMassCalculator.RelativeFatMassCalculator;
import BodySurfaceAreaCalculator.BodySurfaceAreaCalculator;
import CorpulenceIndexCalculator.CorpulenceIndexCalculator;




public class App extends JFrame implements ActionListener {
    private JFrame frame;

    public App() {
        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Fitness Calculator");
        frame.setSize(550, 400);
        frame.getContentPane().setBackground(Color.WHITE);
        
        JButton btnBmi = new JButton("Calculate BMI");
        btnBmi.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    new BMICalculator();
                   
                    // Files.lines(bmiFile).forEach(System.out::println);
                    // You can also execute the file with ProcessBuilder:
                    // ProcessBuilder pb = new ProcessBuilder("java", "BMI");
                    // pb.directory(bmiFile.getParent().toFile());
                    // pb.start();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        btnBmi.setBackground(Color.BLUE);
        btnBmi.setForeground(Color.WHITE);
        btnBmi.setFocusPainted(false);

        // btnBmi.setBounds(25, 30, 150, 25);
        // frame.getContentPane().add(btnBmi);
        
        JButton btnWHR = new JButton("Calculate WHR");
        btnWHR.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                try {
                    new WaistHipCalculator();
                    //Files.lines(whrFile).forEach(System.out::println);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
        btnWHR.setBackground(Color.BLUE);
        btnWHR.setForeground(Color.WHITE);
        btnWHR.setFocusPainted(false);
        // btnWHR.setBounds(25, 70, 150, 25);
        // frame.getContentPane().add(btnWHR);
        
        JButton btnRFM = new JButton("Calculate RFM");
        btnRFM.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                   new RelativeFatMassCalculator();
                //Files.lines(rfmFile).forEach(System.out::println);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
        btnRFM.setBackground(Color.BLUE);
        btnRFM.setForeground(Color.WHITE);
        btnRFM.setFocusPainted(false);
        // btnRFM.setBounds(25, 110, 150, 25);
        // frame.getContentPane().add(btnRFM);
        
        JButton btnBSA = new JButton("Calculate BSA");
        btnBSA.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    new BodySurfaceAreaCalculator();
                    //Files.lines(bsaFile).forEach(System.out::println);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
        btnBSA.setBackground(Color.BLUE);
        btnBSA.setForeground(Color.WHITE);
        btnBSA.setFocusPainted(false);

        // btnBSA.setBounds(25, 150, 150, 25);
        // frame.getContentPane().add(btnBSA);
        
        JButton btnCI = new JButton("Calculate CI");
        btnCI.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    new CorpulenceIndexCalculator();
                    // Files.lines(ciFile).forEach(System.out::println);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
        btnCI.setBackground(Color.BLUE);
        btnCI.setForeground(Color.WHITE);
        btnCI.setFocusPainted(false);
        // btnCI.setBounds(25, 190, 150, 25);
        // frame.getContentPane().add(btnCI);
      

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 2));
        panel.setBackground(Color.WHITE);
        panel.add(btnBmi);
        panel.add(btnWHR);
        panel.add(btnRFM);
        panel.add(btnBSA);
        panel.add(btnCI);
        
        frame.add(panel, BorderLayout.CENTER);
        frame.setVisible(true);
      
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'actionPerformed'");
    }


  
}