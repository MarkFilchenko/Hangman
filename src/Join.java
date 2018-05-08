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
//              FOR TESTING PURPOSES ONLY
                try {
                    JOptionPane.showMessageDialog(frame, "Joining game\nIP: " + InetAddress.getLocalHost().getHostAddress(), "Joining...", JOptionPane.INFORMATION_MESSAGE);
                } catch (UnknownHostException e1) {
                    e1.printStackTrace();
                }
                Game g = new Game();
                frame.dispose();

//              ADD CHECKS HERE: IF IP ADDRESS IS VALID
//
//              DISPOSE DIALOG AFTER CONNECTION ESTABLISHED
//                JDialog dialog = new JDialog();
//                dialog.setTitle("Joining...");
//                JOptionPane wait = null;
//                try {
//                    wait = new JOptionPane("Joining game\nIP: " + InetAddress.getLocalHost().getHostAddress(), JOptionPane.INFORMATION_MESSAGE, JOptionPane.DEFAULT_OPTION, null, new Object[]{}, null);
//                } catch (UnknownHostException e1) {
//                    e1.printStackTrace();
//                }
//                dialog.setContentPane(wait);
//                dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
//                dialog.pack();
//                dialog.setLocationRelativeTo(frame);
//                dialog.setVisible(true);
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
        frame.setVisible(true);
    }
}
