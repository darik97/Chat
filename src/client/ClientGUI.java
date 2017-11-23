package client;

import javax.swing.*;
import javax.swing.text.DefaultCaret;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.logging.Logger;

class ClientGUI extends JPanel {
    String UserName;
    private ClientObject clientObject;

    private static Logger log = Logger.getLogger(ClientGUI.class.getName());

    private JTextField usernameBox = new JTextField();
    private JButton signInButton = new JButton("Sign in");
    private JButton signOutButton = new JButton("Sign out");

    private JTextArea chatBox = new JTextArea();
    private JTextArea messageBox = new JTextArea();
    private JButton sendButton = new JButton("Send");

    ClientGUI() {
        setLayout(null);
        setFocusable(true);
        grabFocus();

        JLabel jLabel = new JLabel("Enter your name: ");
        jLabel.setBounds(10,10,100,30);
        add(jLabel);

        usernameBox.setBounds(120,10,270,30);
        add(usernameBox);

        signInButton.setBounds(400,10,100,30);
//        signInButton.setEnabled(false);
        add(signInButton);

        signOutButton.setBounds(510,10,100,30);
        signOutButton.setEnabled(false);
        add(signOutButton);

        chatBox.setBounds(10,50,600,300);
        chatBox.setEditable(false);
        chatBox.setVisible(true);
        chatBox.setLineWrap(true);

        DefaultCaret caret = (DefaultCaret)chatBox.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);

        JScrollPane scroll = new JScrollPane(chatBox);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
//        add(scroll);
        add(chatBox);

        messageBox.setBounds(10,360,490,100);
        messageBox.setEnabled(false);
        add(messageBox);

        sendButton.setBounds(510,395,100,30);
        sendButton.setEnabled(false);
        add(sendButton);

        usernameBox.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER && usernameBox.getText().trim().length() > 0) {
                    signInButton_Click();
                }
            }
        });
        signInButton.addActionListener(e->signInButton_Click());

        signOutButton.addActionListener(e->signOutButton_Click());

        messageBox.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER && usernameBox.getText().trim().length() > 0) {
                    sendButton_Click();
                }
                if (usernameBox.getText().trim().length() > 0) {
                    signInButton.setEnabled(true);
                } else {
                    signInButton.setEnabled(false);
                }
            }
        });
        sendButton.addActionListener(e->sendButton_Click());

        log.info("Chat GUI created");
    }

    private void signInButton_Click() {
        log.info("Sign in button pressed");

        if (usernameBox.getText().trim().length() > 0) {
            UserName = usernameBox.getText().trim();

            usernameBox.setEnabled(false);
            signInButton.setEnabled(false);
            signOutButton.setEnabled(true);
            messageBox.setEnabled(true);
            sendButton.setEnabled(true);
            messageBox.requestFocusInWindow();

            clientObject = new ClientObject(this);
            new Thread(clientObject).start();
        } else {
            showErrorMessage("Fill the name!");
            log.info("Field name is empty");
        }
    }

    private void signOutButton_Click() {
        log.info("Sign out button pressed");

        signInButton.setEnabled(true);
        signOutButton.setEnabled(false);
        usernameBox.setEnabled(true);
        messageBox.setText("");
        chatBox.setText("");
        messageBox.setEnabled(false);
        sendButton.setEnabled(false);

        clientObject.disconnect();
    }

    private void sendButton_Click() {
        log.info("Send button pressed");

        if (messageBox.getText().trim().length() > 0) {
            String message = messageBox.getText();
            clientObject.sendMessage(message);

            messageBox.requestFocus();
            messageBox.setText("");
        }
    }

    void printMessage(String message) {
        chatBox.append(message + "\n");
        chatBox.scrollRectToVisible(chatBox.getVisibleRect());
        chatBox.paint(chatBox.getGraphics());

        log.info("Chat history updated");
    }

    void showErrorMessage(String message) {
        JOptionPane.showMessageDialog(null,message);
        log.info(message);
    }
}
