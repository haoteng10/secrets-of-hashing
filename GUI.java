import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class GUI implements ActionListener {

    private String inputText;
    private JTextArea inputArea;
    private JTextArea outputArea;

    public GUI() {

        // ===== Heading Panel =====
        JPanel headingPanel = new JPanel();

        // Title
        JLabel title = new JLabel("Secrets of Hashing", SwingConstants.CENTER);

        headingPanel.add(title);

        // ===== Text Panel =====
        JPanel textPanel = new JPanel();
        textPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 30, 20));

        // Input Text Area
        String placeholderMessage = "Write your message here";
        inputArea = new JTextArea(placeholderMessage);
        inputArea.setLineWrap(true);
        inputArea.setWrapStyleWord(true);
        inputArea.addFocusListener(new FocusListener() {

            public void focusGained(FocusEvent e) {
                inputArea.setText("");
            }

            public void focusLost(FocusEvent e) {
                if (inputArea.getText().isEmpty()) {
                    inputArea.setText(placeholderMessage);
                }
            }
        });

        JScrollPane inputAreaScrollPane = new JScrollPane(inputArea);
        inputAreaScrollPane.setVerticalScrollBarPolicy(
                ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        inputAreaScrollPane.setPreferredSize(new Dimension(250, 250));

        // Output Text Area
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

        // ===== Button Panel =====
        JPanel btnPanel = new JPanel();

        // Button
        JButton button = new JButton();
        button.setText("Hash");
        button.addActionListener(this);

        btnPanel.add(button);

        // ===== Frame =====
        JFrame frame = new JFrame();
        frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));
        frame.add(headingPanel);
        frame.add(textPanel);
        frame.add(btnPanel);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setTitle("GUI");
        frame.pack();
        frame.setVisible(true);

    }

    public static void main(String[] args) {
        new GUI();
    }

    public void actionPerformed(ActionEvent e) {
        inputText = inputArea.getText();

        SHA1 hash = new SHA1(inputText);
        outputArea.setText(hash.hexResult());
    }
}
