import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class GameHost {
    ArrayList<String> phraseArr = new ArrayList<>();
    String word;
    ArrayList<String> currentArr = new ArrayList<>();
    String currentWord;
    Boolean solved = false;
    int numOfErrors = 0;
    boolean disabled = false;
    public int PORT = 1234;
    public InetAddress host;

    public GameHost(String phrase, InetAddress aHost) {
        host = aHost;
        this.disabled = disabled;
        JFrame frame = new JFrame();
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
        panel.setLayout(new BorderLayout());

        JPanel phrasePanel = new JPanel();
        word = phrase;

        //Fill phraseArr with letters from word
        for (int i = 0; i < word.length(); i++) {
            phraseArr.add(word.substring(i,i+1));
        }

        JTextField phraseTextField = new JTextField(23);
        phraseTextField.setHorizontalAlignment(JTextField.CENTER);
        phraseTextField.setEditable(false);

        //Fill currentArr with blanks
        for (int i = 0; i < phraseArr.size(); i++) {
            if (isAlpha(phraseArr.get(i))) {
                currentArr.add("_ ");
            }
            else {
                currentArr.add("  ");
            }
        }

        currentWord = String.join("", currentArr);
        phraseTextField.setText(currentWord);

        phrasePanel.add(phraseTextField);

        StickFigure s = new StickFigure();

        JPanel buttons = new JPanel();
        buttons.setBorder(new EmptyBorder(30,30,0,0));
        buttons.setLayout(new GridLayout(5, 6));
        for (char alphabet = 'A'; alphabet <= 'Z'; alphabet++) {
            JButton button = new JButton(Character.toString(alphabet));
            buttons.add(button);
            button.setEnabled(false);
        }

        panel.add(phrasePanel, BorderLayout.NORTH);
        panel.add(s, BorderLayout.CENTER);
        panel.add(buttons, BorderLayout.EAST);

        frame.setLayout(new BorderLayout());
        frame.add(panel, BorderLayout.CENTER);

        frame.setSize(700,400);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);

        new Thread() {
            Socket link = null;
            Scanner input = null;
            PrintWriter output = null;

            @Override
            public void run() {
                try {
                    link = new Socket(host,PORT);
                    input =	new Scanner(link.getInputStream());
                    output = new PrintWriter(link.getOutputStream(), true);
                } catch (IOException ioEx) {
                    ioEx.printStackTrace();
                }

                while (true) {
                    String message[] = input.nextLine().split(" ");

                    if (!solved) {
                        if (message[0].equals("YES")) {
                            for (int i = 0; i < phraseArr.size(); i++) {
                                if (phraseArr.get(i).equals(message[1].toLowerCase())) {
                                    currentArr.set(i,phraseArr.get(i));
                                }
                            }
                            currentWord = String.join("", currentArr);
                            phraseTextField.setText(currentWord);
                            if (!currentArr.contains("_ ")) {
                                solved = true;
                                JDialog dialog = new JDialog();
                                dialog.setTitle("Game Over");
                                JOptionPane d = new JOptionPane("YOU LOSE!", JOptionPane.INFORMATION_MESSAGE, JOptionPane.DEFAULT_OPTION, null, new Object[]{}, null);
                                dialog.setContentPane(d);
                                dialog.pack();
                                dialog.setLocationRelativeTo(frame);
                                dialog.setVisible(true);

                                dialog.addWindowListener(new WindowAdapter() {
                                    @Override
                                    public void windowClosing(WindowEvent e) {
                                        System.exit(0);
                                    }
                                });
                            }
                        }
                        else {
                            numOfErrors++;
                            if (numOfErrors == 1) {
                                s.setHead();
                                s.repaint();
                            }
                            else if (numOfErrors == 2) {
                                s.setBody();
                                s.repaint();
                            }
                            else if (numOfErrors == 3) {
                                s.setLeftLeg();
                                s.repaint();
                            }
                            else if (numOfErrors == 4) {
                                s.setRightLeg();
                                s.repaint();
                            }
                            else if (numOfErrors == 5) {
                                s.setLeftArm();
                                s.repaint();
                            }
                            else if (numOfErrors == 6) {
                                s.setRightArm();
                                s.repaint();

                                JDialog dialog = new JDialog();
                                dialog.setTitle("Game Over");
                                JOptionPane d = new JOptionPane("YOU WIN!", JOptionPane.INFORMATION_MESSAGE, JOptionPane.DEFAULT_OPTION, null, new Object[]{}, null);
                                dialog.setContentPane(d);
                                dialog.pack();
                                dialog.setLocationRelativeTo(frame);
                                dialog.setVisible(true);

                                dialog.addWindowListener(new WindowAdapter() {
                                    @Override
                                    public void windowClosing(WindowEvent e) {
                                        System.exit(0);
                                    }
                                });
                            }
                        }
                    }
                }
            }
        }.start();

    }


    public static boolean isAlpha(String s) {
        char[]chars = s.toCharArray();
        for (char c : chars) {
            if(!Character.isLetter(c)) {
                return false;
            }
        }
        return true;
    }
}
