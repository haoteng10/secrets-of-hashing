import javax.swing.JButton;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ClearButton implements ActionListener {

    private JButton button; // Java Swing Button

    // Initialize the clear button with the provided name
    public ClearButton(String name) {
        button = new JButton(name);
        button.addActionListener(this);
    }

    // A getter for the button
    // (input: none; output: the configured button)
    public JButton getButton() {
        return button;
    }

    // Method called after clicking the "clear" button
    // (input: buttonClick event; output: none--calls clear() method)
    public void actionPerformed(ActionEvent e) {
        GUI.clear();
    }

    // Main method for testing
    public static void main(String[] args) {
        ClearButton button = new ClearButton("Test");
        // Making sure the button is set up correctly
        if (button.getButton().getText().equals("Test")) StdOut.println("Can fetch button.");
    }
}
