import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class Join {

    public Join() {
        JFrame frame = new JFrame();

        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(30,30,30,30));
        panel.setLayout(new BorderLayout());

        JLabel title = new JLabel("Enter IP Address:", SwingConstants.CENTER);

        JPanel tf = new JPanel(new BorderLayout());
        JTextField text = new JTextField();

        tf.add(text, BorderLayout.NORTH);

        JPanel button = new JPanel();
        JButton join = new JButton("Join Game");
//        start.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                if (text.getText().length() == 0) {
//                    JOptionPane.showMessageDialog(frame, "Please enter a word/phrase.", "Error", JOptionPane.ERROR_MESSAGE);
//                }
//                else if (text.getText().length() > 18) {
//                    JOptionPane.showMessageDialog(frame, "Your word/phrase should be less than 19 characters", "Error", JOptionPane.ERROR_MESSAGE);
//                }
//                else {
//                    JDialog dialog = new JDialog();
//                    dialog.setTitle("Waiting...");
//                    JOptionPane wait = null;
//                    try {
//                        wait = new JOptionPane("Waiting for player 2 to connect\nIP: " + InetAddress.getLocalHost().getHostAddress(), JOptionPane.INFORMATION_MESSAGE, JOptionPane.DEFAULT_OPTION, null, new Object[]{}, null);
//                    } catch (UnknownHostException e1) {
//                        e1.printStackTrace();
//                    }
//                    dialog.setContentPane(wait);
//                    dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
//                    dialog.pack();
//                    dialog.setLocationRelativeTo(frame);
//                    dialog.setVisible(true);
//                }
//
//            }
//        });

        button.add(join);

        panel.add(title, BorderLayout.NORTH);
        panel.add(tf, BorderLayout.CENTER);
        panel.add(button, BorderLayout.SOUTH);

        frame.setLayout(new BorderLayout());
        frame.add(panel, BorderLayout.CENTER);

        frame.setSize(500,200);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
