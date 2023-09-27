package curtis.sweeper;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

class SweeperButton extends JButton implements MouseListener {
    Sweeper sw;
    String mine;
    int index;

    // constructor
    SweeperButton(int x, int y, int width, int height, String cap, String min, Sweeper sws, int index) {
        super(cap);
        setBounds(x,y,width,height);
        this.index = index;
        this.sw = sws;
        this.sw.add(this);
        this.mine = min;
        resetColor();
        setOpaque(true);
        setBackground(Color.LIGHT_GRAY);
        setBorder(BorderFactory.createBevelBorder(0));
        setBorderPainted(true);
        setFocusPainted(false);
        addMouseListener(this);
    }

    public void clickAdjacent() {
        // method clicks all adjacent squares
        // used for clicking on zeroes
        MouseEvent e = new MouseEvent(this, 1, 1, 1, 1, 1, 1, false);

        final int GAME_WIDTH = sw.game_width;
        final int GAME_HEIGHT = sw.game_height;
        int mineSpot = index;


        // the below is reused from initilizing the numbers so the var names are recycled
        // left
        if (mineSpot%GAME_WIDTH != 0) {
            sw.mineButtons[mineSpot - 1].mouseClicked(e);
        }
        // right
        if (mineSpot%GAME_WIDTH != GAME_WIDTH - 1) {
            sw.mineButtons[mineSpot + 1].mouseClicked(e);
        }
        // up
        if (mineSpot > GAME_WIDTH - 1) {
            sw.mineButtons[mineSpot - GAME_WIDTH].mouseClicked(e);
        }
        // down
        if (mineSpot < GAME_WIDTH * (GAME_HEIGHT-1)) {
            sw.mineButtons[mineSpot + GAME_WIDTH].mouseClicked(e);
        }

        // up and left
        if (mineSpot > GAME_WIDTH - 1 && mineSpot%GAME_WIDTH != 0) {
            sw.mineButtons[mineSpot - GAME_WIDTH - 1].mouseClicked(e);
        }
        // up and right
        if (mineSpot > GAME_WIDTH - 1 && mineSpot%GAME_WIDTH != GAME_WIDTH - 1) {
            sw.mineButtons[mineSpot - GAME_WIDTH + 1].mouseClicked(e);
        }
        // down and left
        if (mineSpot < GAME_WIDTH * (GAME_HEIGHT-1) && mineSpot%GAME_WIDTH != 0) {
            sw.mineButtons[mineSpot + GAME_WIDTH - 1].mouseClicked(e);
        }
        // down and right
        if (mineSpot < GAME_WIDTH * (GAME_HEIGHT-1) && mineSpot%GAME_WIDTH != GAME_WIDTH - 1) {
            sw.mineButtons[mineSpot + GAME_WIDTH + 1].mouseClicked(e);
        }
    }

    public void resetColor() {
        // set red for mine, blue for zero, yellow otherwise
        // made a method since this is called more than once
        switch (this.mine) {
            case "0":
                setForeground(Color.BLACK);
                break;
            case "1":
                setForeground(Color.BLUE);
                break;
            case "2":
                setForeground(Color.GREEN.darker());
                break;
            case "3":
                setForeground(Color.RED);
                break;
            case "4":
                setForeground(Color.RED.darker());
                break;
            case "*":
                setForeground(Color.RED);
                break;
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        // button == 3 is rightclick
        if (e.getButton() == 3 && "".equals(getText())) {
            // flag button
            setForeground(Color.RED);
            setBackground(Color.WHITE);
            setText("|>");
            if (sw.checkVictory()) {
                JOptionPane.showMessageDialog(sw, "Victory!");
            }
        } else if (e.getButton() == 3 && "|>".equals(getText())) {
            //unflag
            resetColor();
            setBackground(Color.LIGHT_GRAY);
            setText("");
        } else {
            // left click
            resetColor();
            if (mine.equals("0") && getText().isEmpty()) {
                setText(" ");
                setBackground(Color.BLACK);
                clickAdjacent();
            } else if (mine.equals("0")) {
                setText(" ");
                setBackground(Color.BLACK);
            } else if (mine.equals("*")) {
                setText(mine);
                setBackground(Color.WHITE);
                sw.lose();
            } else {
                setText(mine);
                setBackground(Color.WHITE);
            }
        }
    }

    // overriding the other methods from MouseListener
    @Override
    public void mouseExited(MouseEvent e){}

    @Override
    public void mouseEntered(MouseEvent e){}

    @Override
    public void mouseReleased(MouseEvent e){}

    @Override
    public void mousePressed(MouseEvent e){}
}