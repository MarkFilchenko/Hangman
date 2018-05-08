import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Game {

    public Game() {
        JFrame frame = new JFrame();
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(50,50,50,50));
        panel.setLayout(new BorderLayout());

        JLabel title = new JLabel("GAME BOARD", SwingConstants.CENTER);
        Font f = new Font("helvetica", Font.BOLD, 20);
        title.setFont(f);
        title.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));

        JPanel buttons = new JPanel();

        JButton joinGame = new JButton("TEST");
        joinGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        buttons.add(joinGame);

        panel.add(title, BorderLayout.NORTH);
        panel.add(buttons, BorderLayout.CENTER);

        frame.setLayout(new BorderLayout());
        frame.add(panel, BorderLayout.CENTER);

        frame.setSize(500,200);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
