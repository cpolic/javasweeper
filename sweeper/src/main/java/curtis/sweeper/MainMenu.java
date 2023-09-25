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

            }
        });
        panel.add(easyButton);

        //other buttons
        panel.add(new JButton("Medium"));
        panel.add(new JButton("Hard"));
        panel.add(new JButton("High Scores"));
        panel.add(new JButton("Quit"));

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
