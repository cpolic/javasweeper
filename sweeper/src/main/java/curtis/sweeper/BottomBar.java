package curtis.sweeper;

import javax.swing.*;
import java.awt.*;

public class BottomBar extends JPanel {

    int panel_height, panel_width;

    // constructor
    BottomBar(int win_width) {
        // set size for bottom bar
        panel_width = win_width;
        panel_height = 60;

        setLayout(new GridLayout(1, 4));
    }
}
