import javax.swing.JButton;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ClearButton implements ActionListener {

    private JButton button;

    public ClearButton() {
        button = new JButton("Clear");
        button.addActionListener(this);
    }

    // A setter for the button
    // (input: none; output: the configured button)
    public JButton getButton() {
        return button;
    }

    // Method called after clicking the "clear" button
    public void actionPerformed(ActionEvent e) {
        GUI.clear();
    }

    public static void main(String[] args) {

    }
}
