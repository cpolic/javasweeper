package curtis.sweeper;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MainMenu extends JPanel {

    int frame_height, frame_width;
    JFrame frame;

    MainMenu() {
        //frame var setting
        MainMenu mm = this;
        frame_width = 500;
        frame_height = 200;

        //set up the UI frame
        frame = new JFrame("JavaSweeper");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //set up the main menu panel
        JPanel panel = this;
        GridLayout layout = new GridLayout(0,3,10,10);
        this.setLayout(layout);

        //button for easy mode
        JButton easyButton = new JButton("Easy");
        easyButton.addActionListener(e -> {
            frame.remove(panel);
            Sweeper sweep = new Sweeper(10,10,10, mm);
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
        });
        this.add(easyButton);

        //button for medium mode
        JButton mediumButton = new JButton("Medium");
        mediumButton.addActionListener(e -> {
            frame.remove(panel);
            Sweeper sweep = new Sweeper(16,16,40, mm);
            frame.add(sweep);
            frame.pack();
            frame.setSize(sweep.frame_width, sweep.frame_height);
            frame.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    System.exit(0);
                }
            });
        });
        this.add(mediumButton);

        //button for hard mode
        JButton hardButton = new JButton("Hard");
        hardButton.addActionListener(e -> {
            frame.remove(panel);
            Sweeper sweep = new Sweeper(16,30,99, mm);
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
        });
        this.add(hardButton);

        //other buttons
        this.add(new JButton("HS (PLACEHOLDER)"));
        this.add(new JButton("Opt (PLACEHOLDER)"));

        //quit button
        JButton quitButton = new JButton("Quit");
        quitButton.addActionListener(e -> System.exit(0));
        this.add(quitButton);

        //add the panel to the frame
        frame.getContentPane().add(this, BorderLayout.CENTER);
        frame.setSize(frame_width,frame_height);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

    }

    public void resetGUI() {
        frame.getContentPane().add(this, BorderLayout.CENTER);
        frame.setSize(frame_width,frame_height);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    public static void main(String[] args) {
        new MainMenu();
    }
}
