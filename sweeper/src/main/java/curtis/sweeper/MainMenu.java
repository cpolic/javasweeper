package curtis.sweeper;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MainMenu extends JPanel {

    int frame_height, frame_width;

    MainMenu() {
        //frame var setting
        frame_width = 500;
        frame_height = 200;

        //set up the UI frame
        JFrame frame = new JFrame("JavaSweeper");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //set up the main menu panel
        JPanel panel = new JPanel();
        GridLayout layout = new GridLayout(0,3,10,10);
        panel.setLayout(layout);

        //button for easy mode
        JButton easyButton = new JButton("Easy");
        easyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.remove(panel);
                Sweeper sweep = new Sweeper(10,10,10);
                frame.add(sweep);
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
                frame.setSize(sweep.frame_width, sweep.frame_height);
                frame.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosing(WindowEvent e) {
                        System.exit(0);
                    }
                });
            }
        });
        panel.add(easyButton);

        //button for medium mode
        JButton mediumButton = new JButton("Medium");
        mediumButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.remove(panel);
                Sweeper sweep = new Sweeper(16,16,40);
                frame.add(sweep);
                frame.pack();
                frame.setSize(sweep.frame_width, sweep.frame_height);
                frame.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosing(WindowEvent e) {
                        System.exit(0);
                    }
                });
            }
        });
        panel.add(mediumButton);

        //button for hard mode
        JButton hardButton = new JButton("Hard");
        hardButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.remove(panel);
                Sweeper sweep = new Sweeper(16,30,99);
                frame.add(sweep);
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
                frame.setSize(sweep.frame_width, sweep.frame_height);
                frame.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosing(WindowEvent e) {
                        System.exit(0);
                    }
                });
            }
        });
        panel.add(hardButton);

        //other buttons
        panel.add(new JButton("HS (PLACEHOLDER)"));
        panel.add(new JButton("Opt (PLACEHOLDER)"));

        //quit button
        JButton quitButton = new JButton("Quit");
        quitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        panel.add(quitButton);

        //add the panel to the frame
        frame.getContentPane().add(panel, BorderLayout.CENTER);
        frame.setSize(frame_width,frame_height);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

    }

    public static void main(String args[]) {
        MainMenu menu = new MainMenu();
    }
}
