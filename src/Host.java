import jdk.nashorn.internal.scripts.JO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class Host {

    public Host() {
        JFrame frame = new JFrame();

        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(30,30,30,30));
        panel.setLayout(new BorderLayout());

        JPanel heading = new JPanel();
        heading.setLayout(new GridLayout(2,1));
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
                if (!isAlpha(text.getText())) {
                    JOptionPane.showMessageDialog(frame, "Your word/phrase should only contain letters!", "Error", JOptionPane.ERROR_MESSAGE);
                }
                else if (text.getText().length() == 0) {
                    JOptionPane.showMessageDialog(frame, "Please enter a word/phrase.", "Error", JOptionPane.ERROR_MESSAGE);
                }
                else if (text.getText().length() > 18) {
                    JOptionPane.showMessageDialog(frame, "Your word/phrase should be less than 19 characters", "Error", JOptionPane.ERROR_MESSAGE);
                }
                else {
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
        frame.setVisible(true);
    }

    public static boolean isAlpha(String s) {
        char[]chars = s.toCharArray();
        for (char c : chars) {
            if(!Character.isLetter(c) && !Character.isSpaceChar(c)) {
                return false;
            }
        }
        return true;
    }

}
