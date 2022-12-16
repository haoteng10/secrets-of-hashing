import javax.swing.JButton;
import javax.swing.JTextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

/*
HashButton Class

This class creates a button that starts the process of hashing and
changing the contents of Swing elements accordingly.
 */

public class HashButton implements ActionListener {

    private JButton button; // Java Swing Button

    private JTextArea inputArea; // TextArea that has the user's input
    private JTextArea outputArea; // TextArea that displays the result

    // Initializes the HashButton with provided name, input textArea, and output textArea
    public HashButton(String name, JTextArea inputArea, JTextArea outputArea) {
        this.inputArea = inputArea;
        this.outputArea = outputArea;

        button = new JButton(name);
        button.addActionListener(this);
    }

    // Method called after clicking the "hash" button
    // (input: the buttonClick event; output: none--directly manipulates the GUI )
    public void actionPerformed(ActionEvent e) {
        String inputText = inputArea.getText();

        SHA1 hash;

        // Hash inputArea's content if no file is selected
        if (GUI.getSelectedFile() == null) {
            hash = new SHA1(inputText);
            outputArea.setText(hash.hexResult());
        }
        else {
            try {
                hash = new SHA1(GUI.getSelectedFile());
                outputArea.setText(hash.hexResult());
            }
            catch (IOException ex) {
                GUI.setStatus("Invalid file.");
                throw new RuntimeException(ex);
            }
        }

    }

    // A getter for the button
    // (input: none; output: the configured button)
    public JButton getButton() {
        return button;
    }

    // Main method for testing
    public static void main(String[] args) {
        HashButton hashButton = new HashButton(
                "Test", new JTextArea("Test 1"), new JTextArea("Test 2"));

        // Making sure the hash button is set up correctly.
        if (hashButton.getButton().getText().equals("Test")) StdOut.println("Can fetch button");

    }
}
