import javax.swing.JButton;
import javax.swing.JTextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class HashButton implements ActionListener {

    private JButton button;

    private JTextArea inputArea;
    private JTextArea outputArea;

    public HashButton(JTextArea inputArea, JTextArea outputArea) {
        this.inputArea = inputArea;
        this.outputArea = outputArea;

        button = new JButton("Hash");
        button.addActionListener(this);
    }

    // Method called after clicking the "hash" button
    // (input: the buttonClick event; output: none--directly manipulates the GUI )
    public void actionPerformed(ActionEvent e) {
        String inputText = inputArea.getText();

        SHA1 hash;

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

    public JButton getButton() {
        return button;
    }

    public static void main(String[] args) {

    }
}
