import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import git.tools.client.GitSubprocessClient;

import git.tools.client.GitSubprocessClient;

public class Main {
    
    public static void main (String[] args) {
        String repoPath = "/Users/ryanseely/Desktop/Quinnipiac/Freshman/CSC111/MazeProject/Maze.java";
        GitSubprocessClient gitSubprocessClient = new GitSubprocessClient(repoPath);
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
        helloLabel.setLocation(50,20);
        mainPanel.add(helloLabel);

        JButton clickMeButton = new JButton("1.Turn Project to Repo");
        clickMeButton.setSize(200,50);
        clickMeButton.setLocation(50,100);

        JButton push = new JButton("2.Initial Push");
        push.setSize(200,50);
        push.setLocation(50,150);

        clickMeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               
                helloLabel.setText("Goodbye All");
            }
        });
        mainPanel.add(clickMeButton);
        mainPanel.add(push);

        
        frame.setVisible(true);

        
    }
}
