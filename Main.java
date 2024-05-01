import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;  // Import the File class
import java.io.FileWriter;
import java.io.IOException;  // Import the IOException class to handle errors
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import git.tools.client.GitSubprocessClient;
import github.tools.client.GitHubApiClient;
import github.tools.client.RequestParams;
import github.tools.responseObjects.CreateRepoResponse;

public class Main {
    public static void main (String[] args) {
    
        JFrame frame = new JFrame("GitHub Application");
        frame.setSize(800,600);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(null);
        mainPanel.setBackground(Color.pink);
        frame.setContentPane(mainPanel);

        JTextField  gitUserName; // Text Field for GitHub Username
        gitUserName= new JTextField("");
        gitUserName.setBounds(300,10, 250,50);
        mainPanel.add(gitUserName);  
        mainPanel.setLayout(null);  
        mainPanel.setVisible(true); 

        JTextField  filePath; //Text Field for Directory file path
        filePath= new JTextField(""); 
        filePath.setBounds(400,150, 250,50);
        mainPanel.add(filePath);  
        mainPanel.setLayout(null);  
        mainPanel.setVisible(true); 
    
        // Text next to file path input 
        JLabel pathLabel = new JLabel("Paste the path of the Project you want to turn into a Repo");
        pathLabel.setSize(345,100);
        pathLabel.setLocation(50,120);
        mainPanel.add(pathLabel);
        pathLabel.setVisible(true);

        JLabel repoNameLabel = new JLabel("What do you want to name your Repo?");
        repoNameLabel.setSize(325,100); // Label for input box for repo name
        repoNameLabel.setLocation(50,50);
        mainPanel.add(repoNameLabel);
        repoNameLabel.setVisible(true);

        JTextField repoNameInput = new JTextField("");
        repoNameInput.setSize(250,50); //make a input box next to text asking for repo name
        repoNameInput.setLocation(300,75);
        mainPanel.add(repoNameInput);
        repoNameInput.setVisible(true);

        JLabel tokenLabel = new JLabel("Input GitHub Access Token");
        tokenLabel.setSize(325, 100);
        tokenLabel.setLocation(70, 200);
        mainPanel.add(tokenLabel);
        tokenLabel.setVisible(true);

        JTextField gitTokenInput = new JTextField("");
        gitTokenInput.setSize(300, 50);
        gitTokenInput.setLocation(300, 220);
        mainPanel.add(gitTokenInput);
        gitTokenInput.setVisible(true);

        JLabel descLabel = new JLabel("Put Repo Description Here");
        descLabel.setSize(325, 100);
        descLabel.setLocation(70, 260);
        mainPanel.add(descLabel);
        descLabel.setVisible(true);

        JTextField descInput = new JTextField("");
        descInput.setSize(300, 50);
        descInput.setLocation(300, 290);
        mainPanel.add(descInput);
        descInput.setVisible(true);

        JButton ignoreButton = new JButton("2.Git Ignore and Readme"); //creates the gitignore and readMe
        ignoreButton.setSize(200,50);
        ignoreButton.setLocation(300,430);
        ignoreButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{  

                    String directoryPath = filePath.getText();
                    File readMe = new File(directoryPath + "\\README.md");
                    if (readMe.createNewFile()){
                        System.out.println("README file Created" + readMe.getName());
                        // Write to README.md file
                        FileWriter writer = new FileWriter(readMe);
                        writer.write("# " + repoNameInput.getText()); // makes the header whatever the user named the repo
                        writer.close(); // Remember to close the writer
    
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
        gitUserNameLabel.setSize(325,70);
        gitUserNameLabel.setLocation(70,0);
        mainPanel.add(gitUserNameLabel);
        gitUserNameLabel.setVisible(true);

        JButton output = new JButton("5.Output Repo Url");
        output.setSize(200,50);
        output.setLocation(300,505);
        mainPanel.add(output);
        output.setVisible(true);

        JButton clickMeButton = new JButton("1. Initialize Repo on Computer");
        clickMeButton.setSize(200,50);
        clickMeButton.setLocation(50,430);

        JButton createButton = new JButton("3. Create GitHub Repo ");
        createButton.setSize(200,50);
        createButton.setLocation(550,430);
        mainPanel.add(createButton);
        createButton.setVisible(true);

        createButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed (ActionEvent e) {
                //WILL NOT WORK IF REPO ALREADY EXISTS

                String token = gitTokenInput.getText(); // Gets inputed token
                String user =  gitUserName.getText(); // Gets inputed username
                
                GitHubApiClient gitHubApiClient = new GitHubApiClient(user, token); //allows the api client to work

                String repoName = repoNameInput.getText(); // Gets desired repo name

                RequestParams requestParams = new RequestParams();
                requestParams.addParam("name", repoName); // name of repo
                //NEED TO SET UP BOX FOR DESCRIPTION
                requestParams.addParam("description", "this is a new repo"); // repo description
                //NEED TO SET UP PRIVATE OR PUBLIC SELECTION
                requestParams.addParam("private", false); // if repo is private or not

                CreateRepoResponse createRepo = gitHubApiClient.createRepo(requestParams);

                System.out.println("Success in creating the GitHub repo");
            }
        });

        JButton push = new JButton("4.Initial Push");
        push.setSize(200,50);
        push.setLocation(50,505);
        clickMeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
        
                String repoPath = filePath.getText(); // reads the path the user inputed
                GitSubprocessClient gitSubprocessClient = new GitSubprocessClient(repoPath);

                String gitInit = gitSubprocessClient.gitInit(); // git init command 
                
                String gitRemoteAdd = gitSubprocessClient.gitRemoteAdd("origin", "https://github.com/"+ gitUserName.getText());// ask for username
                
                //NEED TO CREATE ERROR HANDLING 

                //So We know it worked
                System.out.println("Success in creating the repo on the computer");
            }

        });

        mainPanel.add(clickMeButton);mainPanel.add(push);
        frame.setVisible(true);
    }
}