package curtis.sweeper;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.Instant;

public class BottomBar extends JPanel {

    int panel_height, panel_width;
    long start_time;
    JLabel clock_value_label, flags_value_label;
    Timer timer;
    DateTimeFormatter date = DateTimeFormatter.ofPattern("HH:mm:ss").withZone(ZoneId.of("Z"));

    // constructor
    BottomBar(int win_width) {
        // set size for bottom bar
        panel_width = win_width;
        panel_height = 60;

        // prep the labels
        JLabel clock_label = new JLabel("Time: ");
        JLabel user_label = new JLabel("User: ");
        JLabel flags_label = new JLabel("Flags Remaining: ");
        flags_value_label = new JLabel();

        // timer
        start_time = System.currentTimeMillis();
        ActionListener timer_listener = e -> updateClock();
        timer = new Timer(25, timer_listener);
        clock_value_label = new JLabel("0");

        // all into the bb panel
        add(clock_label);
        add(clock_value_label);
        add(flags_label);
        add(flags_value_label);
        add(user_label);
        // this is just for filler
        add(new JLabel("PLACEHOLDER"));

        setLayout(new GridLayout(1, 6));
        timer.start();
    }

    private void updateClock() {
        Instant ins = Instant.ofEpochMilli(System.currentTimeMillis() - start_time);
        clock_value_label.setText(date.format(ins));
    }

    public void stopClock() {
        timer.stop();
    }
}
