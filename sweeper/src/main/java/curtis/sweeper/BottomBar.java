package curtis.sweeper;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class BottomBar extends JPanel {

    int panel_height, panel_width;
    long start_time;
    JLabel clock_value_label;
    Timer timer;

    // constructor
    BottomBar(int win_width) {
        // set size for bottom bar
        panel_width = win_width;
        panel_height = 60;

        // prep the labels
        JLabel clock_label = new JLabel("Time: ");
        JLabel user_label = new JLabel("User: ");

        // timer
        start_time = System.currentTimeMillis();
        ActionListener timer_listener = e -> updateClock();
        timer = new Timer(25, timer_listener);
        clock_value_label = new JLabel("0");

        // all into the bb panel
        add(clock_label);
        add(clock_value_label);
        add(user_label);
        // this is just for filler
        add(new JLabel("PLACEHOLDER"));

        setLayout(new GridLayout(1, 4));
        timer.start();
    }

    private void updateClock() {
        clock_value_label.setText(String.valueOf(System.currentTimeMillis() - start_time));
    }

    public void stopClock() {
        timer.stop();
    }
}
