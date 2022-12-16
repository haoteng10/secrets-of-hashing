import javax.swing.JButton;
import javax.swing.JFileChooser;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class ImportButton implements ActionListener {

    private JButton button;

    public ImportButton() {
        button = new JButton("File");
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

    // A setter for the button
    // (input: none; output: the configured button)
    public JButton getButton() {
        return button;
    }

    public static void main(String[] args) {

    }
}
