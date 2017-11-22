package client;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class ClientGUI extends JPanel {

    JTextField userName = new JTextField();
    JButton signInButton = new JButton("Sign in");
    JButton signOutButton = new JButton("Sign out");

    JTextArea chatBox = new JTextArea();
    JTextField messageBox = new JTextField();
    JButton sendButton = new JButton("Send");

    public ClientGUI() {
        setLayout(null);
        setFocusable(true);
        grabFocus();

        JLabel jLabel = new JLabel("Enter your name: ");
        jLabel.setBounds(10,10,100,30);
        add(jLabel);

        userName.setBounds(120,10,270,30);
        add(userName);

        signInButton.setBounds(400,10,100,30);
        signInButton.setEnabled(false);
        add(signInButton);

        signOutButton.setBounds(510,10,100,30);
        signOutButton.setEnabled(false);
        add(signOutButton);

        chatBox.setBounds(10,50,600,300);
        chatBox.setEditable(false);
        chatBox.setVisible(true);
        chatBox.setLineWrap(true);
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

        userName.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER && userName.getText().trim() != "") {
                    signInButton_Click();
                }
            }
        });
        signInButton.addActionListener(e->signInButton_Click());

        signOutButton.addActionListener(e->signOutButton_Click());

        messageBox.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER && userName.getText().trim() != "") {
                    sendButton_Click();
                }
            }
        });
        sendButton.addActionListener(e->sendButton_Click());
    }

    private void signInButton_Click() {

    }

    private void signOutButton_Click() {

    }

    private void sendButton_Click() {

    }
}
