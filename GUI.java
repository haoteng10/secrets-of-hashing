import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
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
import java.io.File;
import java.io.IOException;

public class GUI implements ActionListener {

    private String inputText;
    private JTextArea inputArea;
    private JTextArea outputArea;

    private File selectedFile;

    private JLabel status;

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
        // Adapted from https://docs.oracle.com/javase/tutorial/uiswing/components/textarea.html
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

        // ===== Result Panel =====
        JPanel resultPanel = new JPanel();

        status = new JLabel();

        resultPanel.add(status);

        // ===== Button Panel =====
        JPanel btnPanel = new JPanel();

        // Hash Button
        JButton button = new JButton("Hash");
        button.addActionListener(this);

        // Import File Button
        JButton importButton = new JButton("File");
        importButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser fc = new JFileChooser();
                int statusCode = fc.showOpenDialog(null);
                if (statusCode == JFileChooser.APPROVE_OPTION) {
                    selectedFile = fc.getSelectedFile();
                    setStatus("Selected: " + selectedFile.getName());
                }
            }
        });

        // Unselect File Button
        JButton unSelectButton = new JButton("Unselect");
        unSelectButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                selectedFile = null;
                setStatus("");
            }
        });

        btnPanel.add(button);
        btnPanel.add(importButton);
        btnPanel.add(unSelectButton);

        // ===== Frame =====
        JFrame frame = new JFrame();
        frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));
        frame.add(headingPanel);
        frame.add(textPanel);
        frame.add(resultPanel);
        frame.add(btnPanel);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setTitle("GUI");
        frame.pack();
        frame.setVisible(true);

    }

    private void setStatus(String status) {
        this.status.setText(status);
    }

    public static void main(String[] args) {
        new GUI();
    }

    public void actionPerformed(ActionEvent e) {
        inputText = inputArea.getText();

        SHA1 hash;

        if (selectedFile == null) {
            hash = new SHA1(inputText);
            outputArea.setText(hash.hexResult());
        }
        else {
            try {
                hash = new SHA1(selectedFile);
                outputArea.setText(hash.hexResult());
            }
            catch (IOException ex) {
                setStatus("Invalid file.");
                // throw new RuntimeException(ex);
            }
        }

    }
}
