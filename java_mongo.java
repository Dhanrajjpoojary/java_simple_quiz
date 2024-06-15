//package mongo_java;

import static com.mongodb.client.model.Filters.*;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

import org.bson.Document;
import org.bson.conversions.Bson;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Projections;




public class java_mongo implements ActionListener {
    String username = "dhanrajjpoojary";
    String password = "MongodbAtlas@123";
    MongoCollection<Document> collection;
    JFrame jfrm;
    JTextField Txtname;
    JTextField Txtpwrd;
    JLabel info;

    public java_mongo() {
        try {
            String encodedUsername = URLEncoder.encode(username, "UTF-8");
            String encodedPassword = URLEncoder.encode(password, "UTF-8");
        
            // Construct the connection string with encoded username and password
            String connectionString = "mongodb+srv://" + encodedUsername + ":" + encodedPassword + "@cluster0.3n5wx82.mongodb.net/?retryWrites=true&w=majority&appName=Cluster0";
        
            // Creating a Mongo client
            MongoClient mongoClient = MongoClients.create(connectionString);
        
            // Accessing the database
            MongoDatabase database = mongoClient.getDatabase("JAVA_MONGO");
            collection = database.getCollection("Quiz");
        
            // Now you can perform operations on the collection...
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            // Handle the exception appropriately
        }

        // Create a new JFrame container.
        jfrm = new JFrame("Swing Application");
        
        // Give the frame an initial size.
        jfrm.setSize(500, 500);

        JLabel label = new JLabel("LOGIN PAGE");
        info = new JLabel("");
        // Terminate the program when the user closes the application.
        jfrm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Txtname = new JTextField();
        Txtpwrd = new JTextField();  


        JLabel lname = new JLabel("Name");  
        JLabel lpwrd = new JLabel("Password");  
        JButton submit = new JButton("SUBMIT"); 
        submit.addActionListener(this);  

        label.setBounds(130,60, 250,20); 
        lname.setBounds(130,80, 250,20); 
        Txtname.setBounds(130,100, 150,20); 
        lpwrd.setBounds(130,120, 250,20); 
        Txtpwrd.setBounds(130,140, 150,20); 
        submit.setBounds(130,160,95,30);  
        
        jfrm.add(label); 
        jfrm.add(lname);jfrm.add(Txtname); 
        jfrm.add(lpwrd);jfrm.add(Txtpwrd);jfrm.add(submit);    
        
        // Add the label to the content pane.
        jfrm.add(info);
        // Display the frame.
        jfrm.setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {  
        try {  
            String name = Txtname.getText(); 
            String password = Txtpwrd.getText();  

            Bson projectionFields = Projections.fields(Projections.include("name", "password"));

            Document doc = collection.find(
                    and(
                        eq("name", name),
                        eq("password", password)
                    )
                ).projection(projectionFields)
                .first();
    
            if (doc == null) {
                info.setText("            Invalid Login Credentials");
            } else {
                jfrm.dispose();
                new Quiz_JFrame(name);
            }
        } catch(Exception ex) {
            System.out.println(ex);
        }  
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new java_mongo();
            }
        });
    }
}
class Quiz_JFrame implements ActionListener{
    String username = "dhanrajjpoojary";
    String password = "MongodbAtlas@123";
    MongoClient mongoClient;
    MongoCollection<Document> collection;

    
    //Creating objects
    JFrame frame;
    JScrollPane scrollPane;
    JPanel panel;

    JLabel Title_label;
    JLabel[] lblQuestions;
    JRadioButton[][] radioButtons;
    ButtonGroup[] buttonGroups;
    JButton submit;
    String name;

    Quiz_JFrame(String name) {
        try {
            String encodedUsername = URLEncoder.encode(username, "UTF-8");
            String encodedPassword = URLEncoder.encode(password, "UTF-8");
        
            // Construct the connection string with encoded username and password
            String connectionString = "mongodb+srv://" + encodedUsername + ":" + encodedPassword + "@cluster0.3n5wx82.mongodb.net/?retryWrites=true&w=majority&appName=Cluster0";
        
            // Creating a Mongo client
            mongoClient = MongoClients.create(connectionString);
        
            // Accessing the database
            MongoDatabase database = mongoClient.getDatabase("JAVA_MONGO");
            collection = database.getCollection("Quiz");
        
            // Now you can perform operations on the collection...
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            // Handle the exception appropriately
        }

        this.name = name;
        frame = new JFrame("Quiz JFrame");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 700);
        frame.setLocationRelativeTo(null);
        frame.getContentPane().setLayout(null);
    
        Title_label = new JLabel("MCQ EXAM BEGINS");
        Title_label.setBounds(170, 10, 330, 30);
        Title_label.setFont(new Font("Ariel", Font.BOLD, 28));
        Title_label.setOpaque(true);
        Title_label.setBorder(new EmptyBorder(0, 10, 0, 0));
    
        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
    
        lblQuestions = new JLabel[5];
        radioButtons = new JRadioButton[5][4]; // Corrected array dimensions
        buttonGroups = new ButtonGroup[5];
        String[] questionTexts = {
                "What does GUI stand for?",
                "Which package contains the Java Swing classes?",
                "Which class is used as the base class for all Swing components?",
                "Which layout manager is used by default for JFrame?",
                "Which class is used to create a button in Java Swing?"
        };
        String[][] options = {
                {"Graphical User Interface", "General User Interface", "Graphics Utility Interface", "Graphical Utility Interface "},
                {"java.lang", "java.io", "java.util", "javax.swing"},
                {"Component", "Container", "JPanel", "JFrame"},
                {"BorderLayout", "FlowLayout", "GridLayout", "CardLayout"},
                {"JButton", "JLabel", "JRadioButton", "JTextArea"}
        };
    
        for (int i = 0; i < 5; i++) {
            lblQuestions[i] = new JLabel((i + 1) + ") " + questionTexts[i]);
            lblQuestions[i].setFont(new Font("Ariel", Font.BOLD, 20));
            lblQuestions[i].setBorder(new EmptyBorder(10, 10, 0, 0));
            panel.add(lblQuestions[i]);
    
            buttonGroups[i] = new ButtonGroup();
    
            for (int j = 0; j < 4; j++) { 
                radioButtons[i][j] = new JRadioButton(options[i][j]);
                buttonGroups[i].add(radioButtons[i][j]);
                panel.add(radioButtons[i][j]);
            }
        }
    
        submit = new JButton("SUBMIT");
        submit.addActionListener(this);
        panel.add(submit);
    
        scrollPane = new JScrollPane(panel);
        scrollPane.setBounds(10, 50, 975, 600);
        frame.add(scrollPane);
    
        frame.add(Title_label);
        frame.setVisible(true);
    }
    
    
    @Override
    public void actionPerformed(ActionEvent e) {
        int totalMarks = 0;
    String[] correctAnswers = 
            {"Graphical User Interface", "javax.swing", "Component", "BorderLayout", "JButton"
             };

    for (int i = 0; i < 5; i++) {
        for (int j = 0; j < 4; j++) {
            
            if (radioButtons[i][j].isSelected() && radioButtons[i][j].getText().equals(correctAnswers[i])) {
                 totalMarks++;
                break; 
            }
        }
    }
    
    frame.dispose();//Disposing the Second JFrame
    // Open a new frame to display total marks
    JFrame resultFrame = new JFrame("Quiz Result");
    resultFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    resultFrame.setSize(300, 200);
    resultFrame.setLocationRelativeTo(null);

    JLabel resultLabel = new JLabel("Total Marks: " + totalMarks);
    resultLabel.setFont(new Font("Ariel", Font.BOLD, 20));
    resultLabel.setHorizontalAlignment(SwingConstants.CENTER);
    resultFrame.add(resultLabel);

    resultFrame.setVisible(true);
        
                java.util.Date currentDate = new java.util.Date();
                // Stroring data to database 
                collection.updateOne(
                    eq("name", name),
                    new Document("$set", new Document("Marks", new Document("Date", currentDate).append("TotalMark", totalMarks)))
                );
                mongoClient.close();
        }
}





