import javax.swing.JButton;
import javax.swing.JFileChooser;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

/*
ImportButton Class

This class creates a button that allows user to import a local file.
 */

public class ImportButton implements ActionListener {

    private JButton button; // Java Swing Button

    public ImportButton(String name) {
        button = new JButton(name);
        button.addActionListener(this);
    }

    // Method called after clicking "File" button
    // (input: the buttonClick event; output: none--directly manipulates the GUI )
    // Adapted from Oracle Documentation
    // https://docs.oracle.com/javase/tutorial/uiswing/components/filechooser.html
    public void actionPerformed(ActionEvent e) {
        JFileChooser fc = new JFileChooser();
        int statusCode = fc.showOpenDialog(null);
        if (statusCode == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fc.getSelectedFile();
            GUI.setSelectedFile(selectedFile);
            GUI.setStatus("Selected: " + selectedFile.getName());
        }
    }

    // A getter for the button
    // (input: none; output: the configured button)
    public JButton getButton() {
        return button;
    }

    public static void main(String[] args) {
        ImportButton importButton = new ImportButton("Test");

        // Making sure the hash button is set up correctly.
        if (importButton.getButton().getText().equals("Test")) StdOut.println("Can fetch button");
    }
}
