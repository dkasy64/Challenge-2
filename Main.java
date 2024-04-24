import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import git.tools.client.GitSubprocessClient;


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

        JTextField  box1;
        box1= new JTextField("");  // box where user inputs his file path
        box1.setBounds(400,20, 250,50);
        frame.add(box1);  
        frame.setLayout(null);  
        frame.setVisible(true); 

        JTextField  box2;
        box2= new JTextField("");  // box where user inputs gitHub Username
        box2.setBounds(400,150, 250,50);
        frame.add(box2);  
        frame.setLayout(null);  
        frame.setVisible(true); 
    
         
        JLabel helloLabel = new JLabel("Paste the path of the Project you want to turn into a Repo");
        helloLabel.setSize(325,100);
        helloLabel.setLocation(120,160);
        mainPanel.add(helloLabel);

        JLabel gitUserName = new JLabel("Input your GitHub UserName");
        gitUserName.setSize(325,100);
        gitUserName.setLocation(70,45);
        mainPanel.add(gitUserName);

        
 
        JButton clickMeButton = new JButton("1.Turn Project to Repo");
        clickMeButton.setSize(200,50);
        clickMeButton.setLocation(50,300);

        JButton push = new JButton("2.Initial Push");
        push.setSize(200,50);
        push.setLocation(50,350);

        clickMeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String repoPath = box1.getText(); //reads the path the user inputed 
                GitSubprocessClient gitSubprocessClient = new GitSubprocessClient(repoPath);
                String gitInit = gitSubprocessClient.gitInit(); // git init command 
                String gitRemoteAdd = gitSubprocessClient.gitRemoteAdd("origin", "https://github.com/");// ask for username
                
            }
        });
        
        mainPanel.add(clickMeButton);
        mainPanel.add(push);

        
        frame.setVisible(true);

        
    }
}
