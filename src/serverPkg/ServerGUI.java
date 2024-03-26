package serverPkg;

import javax.swing.*;

public class ServerGUI extends JFrame {

    private JPanel mainPanel;
    private JTextArea textArea1;
    private JTextArea textArea2;
    private JTextArea textArea3;
    private JTextArea textArea4;

    public ServerGUI() {
        setSize(500, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setTitle("Server");
        setLayout(null);
        setContentPane(mainPanel);

        setVisible(true);
    }

    public static void main(String[] args) {
        new ServerGUI();
    }
}
