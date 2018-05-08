import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainMenu {

    public MainMenu() {
        JFrame frame = new JFrame();
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(50,50,50,50));
        panel.setLayout(new BorderLayout());

        JLabel title = new JLabel("HANGMAN", SwingConstants.CENTER);
        Font f = new Font("helvetica", Font.BOLD, 20);
        title.setFont(f);
        title.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));

        JPanel buttons = new JPanel();

        JButton hostAGame = new JButton("Host a Game");
        hostAGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Host h = new Host();
                frame.dispose();
            }
        });

        JButton joinGame = new JButton("Join a Game");
        joinGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Join j = new Join();
                frame.dispose();
            }
        });

        buttons.add(hostAGame);
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

    public static void main(String[] args) {
        MainMenu m = new MainMenu();
    }
}
