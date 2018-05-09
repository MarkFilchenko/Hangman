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

        JLabel title = new JLabel("Enter IP Address:", SwingConstants.CENTER);

        JPanel tf = new JPanel(new BorderLayout());
        JTextField text = new JTextField();

        tf.add(text, BorderLayout.NORTH);

        JPanel button = new JPanel();
        JButton join = new JButton("Join Game");
        join.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                connectToServer(text.getText());
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException iEx) {
                    iEx.printStackTrace();
                }

//              FOR TESTING PURPOSES ONLY
//                try {
//                    JOptionPane.showMessageDialog(frame, "Joining game\nIP: " + InetAddress.getLocalHost().getHostAddress(), "Joining...", JOptionPane.INFORMATION_MESSAGE);
//                } catch (UnknownHostException e1) {
//                    e1.printStackTrace();
//                }


//              ADD CHECKS HERE: IF IP ADDRESS IS VALID
//
//              DISPOSE DIALOG AFTER CONNECTION ESTABLISHED
                JDialog dialog = new JDialog();
                dialog.setTitle("Joining...");
                JOptionPane wait = new JOptionPane("Joining game\nIP: " + Join.host.getHostAddress(), JOptionPane.INFORMATION_MESSAGE, JOptionPane.DEFAULT_OPTION, null, new Object[]{}, null);

                dialog.setContentPane(wait);
                dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
                dialog.pack();
                dialog.setLocationRelativeTo(frame);
                dialog.setVisible(true);

                new Thread() {
                    @Override
                    public void run() {
                        connectToServer(text.getText());
                    }
                }.start();

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
                        Game g = new Game(false, Join.phrase, Join.host);
                        frame.dispose();
                    }
                }.start();
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

    public void connectToServer(String aHost) {
        try {
            Join.host = InetAddress.getByName(aHost);
        } catch (UnknownHostException uEx) {
            System.out.println("Host does not exist or is not running a game!");
            uEx.printStackTrace();
        }
        Socket link;
        try
        {
            link = new Socket(Join.host,PORT);
            Scanner input =	new Scanner(link.getInputStream());
            PrintWriter output = new PrintWriter(link.getOutputStream(), true);

            output.println("2: connecting");

            String response = input.nextLine();

            String response2 = input.nextLine();
            System.out.println("\n" + response);
            System.out.println("\n" + response2);

            connected = true;



        }
        catch(IOException ioEx)
        {
            ioEx.printStackTrace();
        }
        finally
        {
            System.out.println("\n* Waiting");
        }
    }

    public String getPhrase() {
        String phrase = null;
        Socket link;
        try
        {
            link = new Socket(Join.host,PORT);
            Scanner input =	new Scanner(link.getInputStream());
            PrintWriter output = new PrintWriter(link.getOutputStream(), true);

            output.println("Retrieve");

            phrase = input.nextLine();

            System.out.println("\nWOAHAOAH" + phrase);
        }
        catch(IOException ioEx)
        {
            ioEx.printStackTrace();
        }
        finally
        {
            System.out.println("\n* Waiting");
        }
        gotString = true;
        return phrase;
    }
}
