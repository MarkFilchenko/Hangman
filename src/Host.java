import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

/**
 * A class that manages what happens after "Host a Game" is clicked.
 */
public class Host {

    public int PORT = 1234;
    public InetAddress host;
    public boolean connected = false;
    public static String phrase;

    public Host(JFrame aFrame) {
        JFrame frame = new JFrame();

        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(30,30,30,30));
        panel.setLayout(new BorderLayout());

        JPanel heading = new JPanel();
        heading.setLayout(new GridLayout(2,1));

        //Prompts host to enter a word or phrase
        JLabel title = new JLabel("Enter a word or phrase:", SwingConstants.CENTER);
        JLabel note = new JLabel("(must be less than 19 characters)", SwingConstants.CENTER);

        heading.add(title);
        heading.add(note);

        JPanel tf = new JPanel(new BorderLayout());
        JTextField text = new JTextField();

        tf.add(text, BorderLayout.NORTH);

        JPanel button = new JPanel();
        JButton start = new JButton("Start Game");
        start.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Checks if word/phrase contains only letters of the alphabet
                if (!isAlpha(text.getText())) {
                    JOptionPane.showMessageDialog(frame, "Your word/phrase should only contain letters!", "Error", JOptionPane.ERROR_MESSAGE);
                }
                //Checks if word/phrase does not start with a space or has extra spaces
                else if (text.getText().startsWith(" ") || text.getText().contains("  ")){
                    JOptionPane.showMessageDialog(frame, "Too many spaces!", "Error", JOptionPane.ERROR_MESSAGE);
                }
                //Checks if word/phrase is empty
                else if (text.getText().length() == 0) {
                    JOptionPane.showMessageDialog(frame, "Please enter a word/phrase.", "Error", JOptionPane.ERROR_MESSAGE);
                }
                //Checks if word/phrase is more than 18 characters
                else if (text.getText().length() > 18) {
                    JOptionPane.showMessageDialog(frame, "Your word/phrase should be less than 19 characters", "Error", JOptionPane.ERROR_MESSAGE);
                }
                else {

                    Host.phrase = text.getText();

                    //Waits for player 2 to connect
                    JDialog dialog = new JDialog();
                    dialog.setTitle("Waiting...");
                    JOptionPane wait = null;
                    try {
                        wait = new JOptionPane("Waiting for player 2 to connect\nIP: " + InetAddress.getLocalHost().getHostAddress(), JOptionPane.INFORMATION_MESSAGE, JOptionPane.DEFAULT_OPTION, null, new Object[]{}, null);
                    } catch (UnknownHostException e1) {
                        e1.printStackTrace();
                    }
                    dialog.setContentPane(wait);
                    dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
                    dialog.pack();
                    dialog.setLocationRelativeTo(frame);
                    dialog.setVisible(true);

                    //New thread is made for the connection to the server so it does not interfere with the GUI thread
                    new Thread() {
                        @Override
                        public void run() {
                            connectToServer();
                        }
                    }.start();

                    //New thread is made for checking if the connection is fully established, it waits half a second between each check
                    //If connection is established, GameHost is started and the current window is closed
                    new Thread() {
                        @Override
                        public void run() {
                            while (!connected) {
                                try {
                                    Thread.sleep(500);
                                } catch (InterruptedException ex) {
                                    ex.printStackTrace();
                                }
                            }
                            dialog.setVisible(false);
                            GameHost g = new GameHost(text.getText(), host);
                            frame.dispose();
                        }
                    }.start();

                }

            }
        });

        button.add(start);

        panel.add(heading, BorderLayout.NORTH);
        panel.add(tf, BorderLayout.CENTER);
        panel.add(button, BorderLayout.SOUTH);

        frame.setLayout(new BorderLayout());
        frame.add(panel, BorderLayout.CENTER);

        frame.setSize(500,200);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(aFrame);
        frame.setVisible(true);
    }

    //Checks if string contains anything other than letters or spaces, if it does then this method returns false
    public static boolean isAlpha(String s) {
        char[]chars = s.toCharArray();
        for (char c : chars) {
            if(!Character.isLetter(c) && !Character.isSpaceChar(c)) {
                return false;
            }
        }
        return true;
    }

    //Connects to server, since this is the host, the server tries to connect to localhost
    public void connectToServer() {
        try {
            host = InetAddress.getLocalHost();
        } catch (UnknownHostException unknownEx) {
            System.out.println("Host ID not found!");
            System.exit(1);
        }

        Socket link = null;


        //Socket, input and output are setup, the first string is sent to the server. After the server responds, the host sends the phrase to be guessed to the server
        //This method then calls waitForSecond(...) after sending the phrase to the server
        try
        {
            link = new Socket(host,PORT);
            Scanner input =	new Scanner(link.getInputStream());
            PrintWriter output = new PrintWriter(link.getOutputStream(), true);

            output.println("1: connecting");
            String response = input.nextLine();
            output.println("SET:" + phrase);

            waitForSecond(link, input, output);

        } catch(IOException ioEx) {
            ioEx.printStackTrace();
        }
    }

    //Waits for the second client to connect to the host so the game can start
    public void waitForSecond(Socket socket, Scanner input, PrintWriter output) {
        String message = input.nextLine();

        connected = true;
    }
}
