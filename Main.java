import java.awt.Color;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;  // Import the File class
import java.io.FileWriter;
import java.io.IOException;  // Import the IOException class to handle errors
import java.net.URI;
import java.net.URISyntaxException;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import git.tools.client.GitSubprocessClient;
import github.tools.client.GitHubApiClient;
import github.tools.client.RequestParams;
import github.tools.responseObjects.CreateRepoResponse;

public class Main {
    public static void main (String[] args) {
    
        JFrame frame = new JFrame("GitHub Application (PROTOTYPE: NOT FOR COMMERCIAL USE)");
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

        JTextField descInput = new JTextField(""); //description for created repo
        descInput.setSize(300, 50);
        descInput.setLocation(300, 290);
        mainPanel.add(descInput);
        descInput.setVisible(true);

        JLabel visLabel = new JLabel("Select Repo Visibility");
        visLabel.setSize(325, 100);
        visLabel.setLocation(70, 330);
        mainPanel.add(visLabel);
        visLabel.setVisible(true);

        ButtonGroup visible = new ButtonGroup(); // to group the radio buttons together so they are easier
        JRadioButton publicRadioButton = new JRadioButton("Public"); 
        JRadioButton privateRadioButton = new JRadioButton("Private");
        publicRadioButton.setBounds(300, 360, 80, 50); 
        privateRadioButton.setBounds(390, 360, 80, 50); 
        visible.add(publicRadioButton);
        visible.add(privateRadioButton);
        mainPanel.add(publicRadioButton);
        mainPanel.add(privateRadioButton);
        publicRadioButton.setSelected(true); //so that there is something selected

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
                        writer.write("## " + descInput.getText());
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
                        FileWriter writer = new FileWriter(gitIgnore);
                        writer.write("## ##############################\r\n" + //
                                                        "## Java\r\n" + //
                                                        "##############################\r\n" + //
                                                        ".mtj.tmp/\r\n" + //
                                                        ".vscode\r\n" + //
                                                        "*.class\r\n" + //
                                                        "*.jar\r\n" + //
                                                        "*.war\r\n" + //
                                                        "*.ear\r\n" + //
                                                        "*.nar\r\n" + //
                                                        "hs_err_pid*\r\n" + //
                                                        "replay_pid*\r\n" + //
                                                        "\r\n" + //
                                                        "##############################\r\n" + //
                                                        "## Maven\r\n" + //
                                                        "##############################\r\n" + //
                                                        "target/\r\n" + //
                                                        "pom.xml.tag\r\n" + //
                                                        "pom.xml.releaseBackup\r\n" + //
                                                        "pom.xml.versionsBackup\r\n" + //
                                                        "pom.xml.next\r\n" + //
                                                        "pom.xml.bak\r\n" + //
                                                        "release.properties\r\n" + //
                                                        "dependency-reduced-pom.xml\r\n" + //
                                                        "buildNumber.properties\r\n" + //
                                                        ".mvn/timing.properties\r\n" + //
                                                        ".mvn/wrapper/maven-wrapper.jar\r\n" + //
                                                        "\r\n" + //
                                                        "##############################\r\n" + //
                                                        "## Gradle\r\n" + //
                                                        "##############################\r\n" + //
                                                        "bin/\r\n" + //
                                                        "build/\r\n" + //
                                                        ".gradle\r\n" + //
                                                        ".gradletasknamecache\r\n" + //
                                                        "gradle-app.setting\r\n" + //
                                                        "!gradle-wrapper.jar\r\n" + //
                                                        "\r\n" + //
                                                        "##############################\r\n" + //
                                                        "## IntelliJ\r\n" + //
                                                        "##############################\r\n" + //
                                                        "out/\r\n" + //
                                                        ".idea/\r\n" + //
                                                        ".idea_modules/\r\n" + //
                                                        "*.iml\r\n" + //
                                                        "*.ipr\r\n" + //
                                                        "*.iws\r\n" + //
                                                        "\r\n" + //
                                                        "##############################\r\n" + //
                                                        "## Eclipse\r\n" + //
                                                        "##############################\r\n" + //
                                                        ".settings/\r\n" + //
                                                        "bin/\r\n" + //
                                                        "tmp/\r\n" + //
                                                        ".metadata\r\n" + //
                                                        ".classpath\r\n" + //
                                                        ".project\r\n" + //
                                                        "*.tmp\r\n" + //
                                                        "*.bak\r\n" + //
                                                        "*.swp\r\n" + //
                                                        "*~.nib\r\n" + //
                                                        "local.properties\r\n" + //
                                                        ".loadpath\r\n" + //
                                                        ".factorypath\r\n" + //
                                                        "\r\n" + //
                                                        "##############################\r\n" + //
                                                        "## NetBeans\r\n" + //
                                                        "##############################\r\n" + //
                                                        "nbproject/private/\r\n" + //
                                                        "build/\r\n" + //
                                                        "nbbuild/\r\n" + //
                                                        "dist/\r\n" + //
                                                        "nbdist/\r\n" + //
                                                        "nbactions.xml\r\n" + //
                                                        "nb-configuration.xml\r\n" + //
                                                        "\r\n" + //
                                                        "##############################\r\n" + //
                                                        "## Visual Studio Code\r\n" + //
                                                        "##############################\r\n" + //
                                                        ".vscode/\r\n" + //
                                                        ".code-workspace\r\n" + //
                                                        "\r\n" + //
                                                        "##############################\r\n" + //
                                                        "## OS X\r\n" + //
                                                        "##############################\r\n" + //
                                                        ".DS_Store\r\n" + //
                                                        "\r\n" + //
                                                        "##############################\r\n" + //
                                                        "## Miscellaneous\r\n" + //
                                                        "##############################\r\n" + //
                                                        "*.log");
                        writer.close();
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

        JLabel url = new JLabel("");
        url.setSize(325, 70);
        url.setLocation(555, 485);
        mainPanel.add(url);
        url.setVisible(false);
        url.setForeground(Color.BLUE.darker());
        url.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        url.addMouseListener(new MouseAdapter() { //manages clicking of the link
 
            @Override
            public void mouseClicked(MouseEvent e) { //opens link in browser
                try {
                    Desktop.getDesktop().browse(new URI("https://github.com/"+ gitUserName.getText() + "/" + repoNameInput.getText()));
                } catch (IOException | URISyntaxException e1) {
                    e1.printStackTrace();
                }
            }

            @Override
            public void mouseExited(MouseEvent e) { // un underlines 
                url.setText("https://github.com/"+ gitUserName.getText() + "/" + repoNameInput.getText());
            }

            @Override
            public void mouseEntered(MouseEvent e) { // underlines
                url.setText("<html><a href=''>" + ("https://github.com/"+ gitUserName.getText() + "/" + repoNameInput.getText()) + "</a></html>");
            }
 
        });

        output.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                url.setVisible(true);
                url.setText("https://github.com/"+ gitUserName.getText() + "/" + repoNameInput.getText());
            }
        });

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
                // NEEDS ERROR HANDLING

                String token = gitTokenInput.getText(); // Gets inputed token
                String user =  gitUserName.getText(); // Gets inputed username
                String desc = descInput.getText();

                Boolean vis;
                if (publicRadioButton.isSelected()) {
                    vis = false;
                } else {
                    vis = true;
                }
                
                GitHubApiClient gitHubApiClient = new GitHubApiClient(user, token); //allows the api client to work

                String repoName = repoNameInput.getText(); // Gets desired repo name

                RequestParams requestParams = new RequestParams();
                requestParams.addParam("name", repoName); // name of repo
                requestParams.addParam("description", desc); // repo description
                requestParams.addParam("private", vis); // if repo is private or not

                CreateRepoResponse createRepo = gitHubApiClient.createRepo(requestParams);

                System.out.println("Success in creating the GitHub repo");
            }
        });

        JButton push = new JButton("4. Initial Commit + Push");
        push.setSize(200,50);
        push.setLocation(50,505);
        push.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // NEEDS ERROR HANDLING

                String repoPath = filePath.getText(); // reads the path the user inputed
                GitSubprocessClient gitSubprocessClient = new GitSubprocessClient(repoPath);

                //adds changes, commits them, and pushes
                String gitAddAll = gitSubprocessClient.gitAddAll();
                String commit = gitSubprocessClient.gitCommit("Initial Commit");
                String push = gitSubprocessClient.gitPush("master");
              
                System.out.println("Successful initial commit + push");
            }
        });
        clickMeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
        
                String repoPath = filePath.getText(); // reads the path the user inputed
                GitSubprocessClient gitSubprocessClient = new GitSubprocessClient(repoPath);

                String gitInit = gitSubprocessClient.gitInit(); // git init command 
                
                String gitRemoteAdd = gitSubprocessClient.gitRemoteAdd("origin", "https://github.com/"+ gitUserName.getText() + "/" + repoNameInput.getText() + ".git");// ask for username
                
                //NEED TO CREATE ERROR HANDLING 

                //So We know it worked
                System.out.println("Success in creating the repo on the computer");
            }

        });

        mainPanel.add(clickMeButton);mainPanel.add(push);
        frame.setVisible(true);
    }
}