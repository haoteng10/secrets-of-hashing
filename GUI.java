import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.File;

/*
GUI Class

This class creates a graphical user interface for the program.
 */

public class GUI {

    private static JLabel statusLabel; // A Java Swing Label to indicate current status
    private static File selectedFile; // Storing the imported file
    private static JTextArea inputArea; // Java Swing's Text Area for Input
    private static JTextArea outputArea; // Java Swing's Text Area for Output

    private static final String PLACEHOLDER_MESSAGE = "Write your message here"; // Default Message

    // Renders the graphical user interface
    public GUI() {

        // ===== Heading Panel =====
        JPanel headingPanel = new JPanel();

        // Title
        JLabel title = new JLabel("SHA1 Hashing", SwingConstants.CENTER);

        headingPanel.add(title);

        // ===== Text Panel =====
        JPanel textPanel = new JPanel();
        textPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 30, 20));

        // Input Text Area
        // Adapted from Oracle Documentation
        // https://docs.oracle.com/javase/tutorial/uiswing/components/textarea.html
        inputArea = new JTextArea(PLACEHOLDER_MESSAGE);
        inputArea.setLineWrap(true);
        inputArea.setWrapStyleWord(true);
        inputArea.addFocusListener(new FocusListener() {

            public void focusGained(FocusEvent e) {
                if (inputArea.getText().equals(PLACEHOLDER_MESSAGE))
                    inputArea.setText("");
            }

            public void focusLost(FocusEvent e) {
                if (inputArea.getText().isEmpty())
                    inputArea.setText(PLACEHOLDER_MESSAGE);
            }
        });

        JScrollPane inputAreaScrollPane = new JScrollPane(inputArea);
        inputAreaScrollPane.setVerticalScrollBarPolicy(
                ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        inputAreaScrollPane.setPreferredSize(new Dimension(250, 250));

        // Output Text Area
        // Adapted from Oracle Documentation
        // https://docs.oracle.com/javase/tutorial/uiswing/components/textarea.html
        outputArea = new JTextArea("Result will be shown here");
        outputArea.setLineWrap(true);
        outputArea.setEditable(false);
        outputArea.setFont(new Font("serif", Font.BOLD, 12));

        JScrollPane outputAreaScrollPane = new JScrollPane(outputArea);
        outputAreaScrollPane.setVerticalScrollBarPolicy(
                ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        outputAreaScrollPane.setPreferredSize(new Dimension(250, 250));

        textPanel.add(inputAreaScrollPane);
        textPanel.add(outputAreaScrollPane);

        // ===== Result Panel =====
        JPanel resultPanel = new JPanel();

        statusLabel = new JLabel();

        resultPanel.add(statusLabel);

        // ===== Button Panel =====
        JPanel btnPanel = new JPanel();

        // Hash Button
        HashButton hashButton = new HashButton("Hash", inputArea, outputArea);
        // Import Button
        ImportButton importButton = new ImportButton("File");
        // Unselect File Button
        ClearButton clearButton = new ClearButton("Clear");

        btnPanel.add(hashButton.getButton());
        btnPanel.add(importButton.getButton());
        btnPanel.add(clearButton.getButton());

        // ===== Frame =====
        JFrame frame = new JFrame();
        frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));
        frame.add(headingPanel);
        frame.add(textPanel);
        frame.add(resultPanel);
        frame.add(btnPanel);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setTitle("Secrets of Hashing");
        frame.pack();
        frame.setVisible(true);

    }

    // Changes the text of the status label
    // (input: current status string; output: none--manipulates the label)
    public static void setStatus(String status) {
        statusLabel.setText(status);
    }

    // A getter for selectedFile
    // (input: none; output: the reference to the file)
    public static File getSelectedFile() {
        return selectedFile;
    }

    // A setter for selectedFile
    // (input: File; output none)
    public static void setSelectedFile(File file) {
        selectedFile = file;
    }

    // Resets the file
    public static void clear() {
        // Clear selectedFile
        setSelectedFile(null);
        setStatus("");

        // Clear textAreas
        inputArea.setText(PLACEHOLDER_MESSAGE);
        outputArea.setText("");
    }

    // Main method to run
    public static void main(String[] args) {

        // Renders the graphical user interface
        new GUI();

        // Making sure all Swing components are initialized
        if (statusLabel == null) StdOut.println("Status Label not initialized.");
        if (inputArea == null) StdOut.println("Input TextArea not initialized.");
        if (outputArea == null) StdOut.println("Output TextArea not intialized.");

        // Making sure all setters will manipulate correct instance variables
        setSelectedFile(new File("test.txt"));
        if (selectedFile == null) StdOut.println(
                "Setter for selectedFile not modifying the correct instance variable.");

        // Making sure all getters return correct values
        if (selectedFile != getSelectedFile()) StdOut.println(
                "Getter for selectedFile not returning correct value.");

        // Making sure setStatus works
        String TEST_STRING = "test 123";
        setStatus(TEST_STRING);
        if (!statusLabel.getText().equals(TEST_STRING))
            StdOut.println("Not setting the status correctly");

        // Making sure the clear function works
        clear();
        if (getSelectedFile() != null) StdOut.println("Error in clearing the inputs");
    }
}
