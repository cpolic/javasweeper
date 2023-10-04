package curtis.sweeper;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MainMenu extends JPanel {

    int frame_height, frame_width;
    JFrame frame;
    JPanel container;

    MainMenu() {
        //frame var setting
        frame_width = 500;
        frame_height = 200;

        //set up the UI frame
        frame = new JFrame("JavaSweeper");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //set up the main menu panel
        GridLayout layout = new GridLayout(0,3,10,10);
        this.setLayout(layout);

        //button for easy mode
        JButton easyButton = new JButton("Easy");
        easyButton.addActionListener(e -> {
            newGame(10,10,10);
        });
        this.add(easyButton);

        //button for medium mode
        JButton mediumButton = new JButton("Medium");
        mediumButton.addActionListener(e -> {
            newGame(16,16,40);
        });
        this.add(mediumButton);

        //button for hard mode
        JButton hardButton = new JButton("Hard");
        hardButton.addActionListener(e -> {
            newGame(16,30,99);
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

    public void newGame(int across, int high, int mines) {
        //remove self from frame
        frame.remove(this);

        // create new GUI elements
        Sweeper sweep = new Sweeper(across,high,mines, this);
        BottomBar bb = new BottomBar(sweep.panel_width);

        // container for GUI elements
        container = new JPanel();
        GridBagLayout layout = new GridBagLayout();
        GridBagConstraints constraints = new GridBagConstraints();

        // sizing setup
        sweep.setMinimumSize(new Dimension(sweep.panel_width, sweep.panel_height));
        sweep.setMaximumSize(new Dimension(sweep.panel_width, sweep.panel_height));
        sweep.setPreferredSize(new Dimension(sweep.panel_width, sweep.panel_height));

        // sizing setup
        bb.setMinimumSize(new Dimension(bb.panel_width, bb.panel_height));
        bb.setMaximumSize(new Dimension(bb.panel_width, bb.panel_height));
        bb.setPreferredSize(new Dimension(bb.panel_width, bb.panel_height));

        // constraints for GUI elements
        constraints.gridwidth = GridBagConstraints.REMAINDER;
        constraints.weightx = 1;
        constraints.weighty = sweep.game_height;
        layout.setConstraints(sweep, constraints);
        constraints.weighty = 2;
        layout.setConstraints(bb, constraints);

        // add it all to container
        container.setLayout(layout);
        container.add(sweep);
        container.add(bb);

        // pop it all in the frame and set it up
        frame.add(container);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setSize(sweep.panel_width, sweep.panel_height + bb.panel_height);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
    }

    public void resetGUI() {
        frame.remove(container);
        frame.add(this, BorderLayout.CENTER);
        frame.setSize(frame_width,frame_height);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    public static void main(String[] args) {
        new MainMenu();
    }
}
