import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;  // Import the File class
import java.io.IOException;  // Import the IOException class to handle errors
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

        JTextField  gitUserName; // Text Field for GitHub Username
        gitUserName= new JTextField("");
        gitUserName.setBounds(300,60, 250,50);
        mainPanel.add(gitUserName);  
        mainPanel.setLayout(null);  
        mainPanel.setVisible(true); 

        JTextField  filePath; //Text Field for Directory file path
        filePath= new JTextField(""); 
        filePath.setBounds(400,200, 250,50);
        mainPanel.add(filePath);  
        mainPanel.setLayout(null);  
        mainPanel.setVisible(true); 
    
        // Text next to file path input 
        JLabel helloLabel = new JLabel("Paste the path of the Project you want to turn into a Repo");
        helloLabel.setSize(325,100);
        helloLabel.setLocation(50,180);
        mainPanel.add(helloLabel);
        helloLabel.setVisible(true);

        JLabel repoName = new JLabel("What do you want to name your Repo");
        repoName.setSize(325,100); // Label for input box for repo name
        repoName.setLocation(50,110);
        mainPanel.add(repoName);
        repoName.setVisible(true);

        JTextField repoNameInput = new JTextField("");
        repoNameInput.setSize(250,50); //make a input box next to text asking for repo name
        repoNameInput.setLocation(300,125);
        mainPanel.add(repoNameInput);
        repoNameInput.setVisible(true);



        JButton ignoreButton = new JButton("2.Git Ignore and Readme");
        ignoreButton.setSize(200,50);
        ignoreButton.setLocation(50,350);
        ignoreButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{  

                    String directoryPath = filePath.getText();
                    File readMe = new File(directoryPath + "\\README.md");
                    if (readMe.createNewFile()){
                        System.out.println("README file Created" + readMe.getName());
    
                    } else {
                        System.out.println("File Already exists.");
                    }
                } catch (IOException t){
                    System.out.println("An error occured.");
                    t.printStackTrace();
                }

                try{
                    String directoryPath = filePath.getText();
                    File gitIgnore = new File(directoryPath + "\\.gitignore");

                    if (gitIgnore.createNewFile()){
                        System.out.println("Ignore file Created" + gitIgnore.getName());
    
                    } else {
                        System.out.println("File Already exists.");
                    }

                } catch (IOException f) {
                    System.out.println("An error occured.");
                    f.printStackTrace();
                }
            }          
        });

        mainPanel.add(ignoreButton);
        frame.setVisible(true);
            
        JLabel gitUserNameLabel = new JLabel("Input your GitHub UserName");
        gitUserNameLabel.setSize(325,100);
        gitUserNameLabel.setLocation(70,45);
        mainPanel.add(gitUserNameLabel);
        gitUserNameLabel.setVisible(true);

        JButton output = new JButton("4.Output Repo Url");
        output.setSize(200,50);
        output.setLocation(50,450);
        mainPanel.add(output);
        output.setVisible(true);

        JButton clickMeButton = new JButton("1.Turn Project to Repo");
        clickMeButton.setSize(200,50);
        clickMeButton.setLocation(50,300);

        JButton push = new JButton("3.Initial Push");
        push.setSize(200,50);
        push.setLocation(50,400);
        clickMeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
        
                String repoPath = filePath.getText(); // reads the path the user inputed
                GitSubprocessClient gitSubprocessClient = new GitSubprocessClient(repoPath);

                String gitInit = gitSubprocessClient.gitInit(); // git init command 
                
                String gitRemoteAdd = gitSubprocessClient.gitRemoteAdd("origin", "https://github.com/"+ gitUserName.getText());// ask for username
                
                //need to create error handling 

                //So We know it worked
                System.out.println("Success in creating the repo on the computer");
            }

        });

        mainPanel.add(clickMeButton);mainPanel.add(push);
        frame.setVisible(true);
    }
}










