import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.*;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

/**
 * A class that manages what happens after "Join a Game" is clicked.
 */
public class Join {

    public int PORT = 1234;
    public static InetAddress host;
    public boolean connected = false;
    public static String phrase = null;
    public boolean gotString = false;

    public Join(JFrame aFrame) {
        JFrame frame = new JFrame();

        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        panel.setLayout(new BorderLayout());

        //Prompts player to enter an IP address
        JLabel title = new JLabel("Enter IP Address:", SwingConstants.CENTER);

        JPanel tf = new JPanel(new BorderLayout());
        JTextField text = new JTextField();

        tf.add(text, BorderLayout.NORTH);

        JPanel button = new JPanel();
        JButton join = new JButton("Join Game");
        join.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (text.getText().equals("")) {
                    JOptionPane.showMessageDialog(frame, "The IP address cannot be empty!!", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    //Connects to server, if exception is caught, then JOptionPane is displayed with error message
                    try {
                        connectToServer(text.getText());
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(frame, "The host does not exist or is not running a server!", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    //Sleep to make sure the connect to server fully finished
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException iEx) {
                        iEx.printStackTrace();
                    }

                    JDialog dialog = new JDialog();
                    dialog.setTitle("Joining...");
                    JOptionPane wait = new JOptionPane("Joining game\nIP: " + text.getText(), JOptionPane.INFORMATION_MESSAGE, JOptionPane.DEFAULT_OPTION, null, new Object[]{}, null);

                    dialog.setContentPane(wait);
                    dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
                    dialog.pack();
                    dialog.setLocationRelativeTo(frame);
                    dialog.setVisible(true);


                    //New thread is ran to getphrase and keep waiting until the phrase is received and the connection is established, after it is then game is started
                    //Separate thread to separate GUI and server logic
                    new Thread() {
                        @Override
                        public void run() {
                            Join.phrase = getPhrase();
                            while (!connected && !gotString) {
                                try {
                                    Thread.sleep(500);
                                } catch (InterruptedException ex) {
                                    ex.printStackTrace();
                                }
                            }
                            dialog.setVisible(false);
                            Game g = new Game(Join.phrase, Join.host);
                            frame.dispose();
                        }
                    }.start();
                }
            }
        });

        button.add(join);

        panel.add(title, BorderLayout.NORTH);
        panel.add(tf, BorderLayout.CENTER);
        panel.add(button, BorderLayout.SOUTH);

        frame.setLayout(new BorderLayout());
        frame.add(panel, BorderLayout.CENTER);

        frame.setSize(500, 200);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(aFrame);
        frame.setVisible(true);
    }


    //Method to connect to the server with the IP Address aHost
    public void connectToServer(String aHost) throws Exception {

        //Checks if the host is not something wild like letters or not formatted correctly, if it isnt then an exception is thrown
        try {
            Join.host = InetAddress.getByName(aHost);
        } catch (UnknownHostException uEx) {
            throw new Exception();
        }
        Socket link;
        try
        {
            //Next lines attempt to connect to the server, if connection is refused then an exception is thrown
            Socket sock = new Socket();
            final int timeOut = (int) TimeUnit.SECONDS.toMillis(2); // 5 sec wait period
            try {
                sock.connect(new InetSocketAddress(Join.host, PORT), timeOut);
            } catch (SocketTimeoutException ex) {
                throw new Exception();
            }

            //Socket, input, output are setup and the connection message is sent
            link = new Socket(Join.host,PORT);
            Scanner input =	new Scanner(link.getInputStream());
            PrintWriter output = new PrintWriter(link.getOutputStream(), true);

            output.println("2: connecting");
            String response = input.nextLine();
            String response2 = input.nextLine();

            connected = true;

        } catch(IOException ioEx) {
            ioEx.printStackTrace();
        } catch (Exception e) {
            throw new Exception();
        }
    }

    //Returns the main game phrase from the server
    public String getPhrase() {
        String phrase = null;
        Socket link;
        try
        {

            //Socket,input,output setup and command is sent to server to retrieve phrase, phrase is stored in phrase variable
            link = new Socket(Join.host,PORT);
            Scanner input =	new Scanner(link.getInputStream());
            PrintWriter output = new PrintWriter(link.getOutputStream(), true);

            output.println("Retrieve");

            phrase = input.nextLine();
        }
        catch(IOException ioEx)
        {
            ioEx.printStackTrace();
        }
        gotString = true;
        return phrase;
    }
}
