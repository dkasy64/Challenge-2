import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

public class Main {
    public static void main (String[] args) {
        JFrame frame = new JFrame("Swing Demo");
        frame.setSize(800,600);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(null);
        mainPanel.setBackground(Color.pink);
        frame.setContentPane(mainPanel);

        JLabel helloLabel = new JLabel("Hello World");
        helloLabel.setSize(100,100);
        helloLabel.setLocation(50,50);
        mainPanel.add(helloLabel);

        JButton clickMeButton = new JButton("Click me");
        clickMeButton.setSize(100,50);
        clickMeButton.setLocation(50,200);
        clickMeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("testing time");
                helloLabel.setText("Goodbye All");
            }
        });
        mainPanel.add(clickMeButton);


        frame.setVisible(true);
    }
}
