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
    SweeperButton(int x, int y, int width, int height, String cap, Sweeper sws, int index) {
        super(cap);
        setBounds(x,y,width,height);
        this.index = index;
        this.sw = sws;
        this.sw.add(this);
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
        //noinspection MagicConstant
        MouseEvent e = new MouseEvent(this, 1, 1, 1, 1, 1, 1, false);

        final int GAME_WIDTH = sw.game_width;
        final int GAME_HEIGHT = sw.game_height;

        // left
        if (index%GAME_WIDTH != 0) {
            if (!sw.mine_buttons[index - 1].getText().equals("|>")) {
                sw.mine_buttons[index - 1].mouseClicked(e);
            }
        }
        // right
        if (index%GAME_WIDTH != GAME_WIDTH - 1) {
            if (!sw.mine_buttons[index + 1].getText().equals("|>")) {
                sw.mine_buttons[index + 1].mouseClicked(e);
            }
        }
        // up
        if (index/GAME_WIDTH != 0) {
            if (!sw.mine_buttons[index - GAME_WIDTH].getText().equals("|>")) {
                sw.mine_buttons[index - GAME_WIDTH].mouseClicked(e);
            }
        }
        // down
        if (index/GAME_WIDTH != GAME_HEIGHT - 1) {
            if (!sw.mine_buttons[index + GAME_WIDTH].getText().equals("|>")) {
                sw.mine_buttons[index + GAME_WIDTH].mouseClicked(e);
            }
        }

        // up and left
        if (index%GAME_WIDTH != 0 && index/GAME_WIDTH != 0) {
            if (!sw.mine_buttons[index - GAME_WIDTH - 1].getText().equals("|>")) {
                sw.mine_buttons[index - GAME_WIDTH - 1].mouseClicked(e);
            }
        }
        // up and right
        if (index%GAME_WIDTH != GAME_WIDTH - 1 && index/GAME_WIDTH != 0) {
            if (!sw.mine_buttons[index - GAME_WIDTH + 1].getText().equals("|>")) {
                sw.mine_buttons[index - GAME_WIDTH + 1].mouseClicked(e);
            }
        }
        // down and left
        if (index%GAME_WIDTH != 0 && index/GAME_WIDTH != GAME_HEIGHT - 1) {
            if (!sw.mine_buttons[index + GAME_WIDTH - 1].getText().equals("|>")) {
                sw.mine_buttons[index + GAME_WIDTH - 1].mouseClicked(e);
            }
        }
        // down and right
        if (index%GAME_WIDTH != GAME_WIDTH - 1 && index/GAME_WIDTH != GAME_HEIGHT - 1) {
            if (!sw.mine_buttons[index + GAME_WIDTH + 1].getText().equals("|>")) {
                sw.mine_buttons[index + GAME_WIDTH + 1].mouseClicked(e);
            }
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
            case "3" + "*":
                setForeground(Color.RED);
                break;
            case "4":
                setForeground(Color.RED.darker());
                break;
        }
    }

    public boolean checkSatisfied() {
        final int GAME_WIDTH = sw.game_width;
        final int GAME_HEIGHT = sw.game_height;
        int adj_flags = 0;

        // false for flags, mines and blanks
        if (getText().equals("|>") || getText().equals("*") || getText().isEmpty() || sw.first_click) {
            return false;
        }

        // left
        if (index%GAME_WIDTH != 0) {
            if (sw.mine_buttons[index - 1].getText().equals("|>")) {
                adj_flags++;
            }
        }
        // right
        if (index%GAME_WIDTH != GAME_WIDTH - 1) {
            if (sw.mine_buttons[index + 1].getText().equals("|>")) {
                adj_flags++;
            }
        }
        // up
        if (index > GAME_WIDTH - 1) {
            if (sw.mine_buttons[index - GAME_WIDTH].getText().equals("|>")) {
                adj_flags++;
            }
        }
        // down
        if (index < GAME_WIDTH * (GAME_HEIGHT-1)) {
            if (sw.mine_buttons[index + GAME_WIDTH].getText().equals("|>")) {
                adj_flags++;
            }
        }

        // up and left
        if (index > GAME_WIDTH - 1 && index%GAME_WIDTH != 0) {
            if (sw.mine_buttons[index - GAME_WIDTH - 1].getText().equals("|>")) {
                adj_flags++;
            }
        }
        // up and right
        if (index > GAME_WIDTH - 1 && index%GAME_WIDTH != GAME_WIDTH - 1) {
            if (sw.mine_buttons[index - GAME_WIDTH + 1].getText().equals("|>")) {
                adj_flags++;
            }
        }
        // down and left
        if (index < GAME_WIDTH * (GAME_HEIGHT-1) && index%GAME_WIDTH != 0) {
            if (sw.mine_buttons[index + GAME_WIDTH - 1].getText().equals("|>")) {
                adj_flags++;
            }
        }
        // down and right
        if (index < GAME_WIDTH * (GAME_HEIGHT-1) && index%GAME_WIDTH != GAME_WIDTH - 1) {
            if (sw.mine_buttons[index + GAME_WIDTH + 1].getText().equals("|>")) {
                adj_flags++;
            }
        }

        System.out.println(adj_flags);

        return adj_flags == Integer.parseInt(mine);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        // button == 3 is right click
        if (e.getButton() == 3 && "".equals(getText())) {
            // flag button
            setForeground(Color.RED);
            setBackground(Color.WHITE);
            setText("|>");
            if (sw.checkVictory()) {
                sw.victory();
            }
        } else if (e.getButton() == 3 && "|>".equals(getText())) {
            //un-flag
            resetColor();
            setBackground(Color.LIGHT_GRAY);
            setText("");
        } else if (e.getButton() == 2) {
            // middle click
            System.out.println("middle");
            if (checkSatisfied()) {
                clickAdjacent();
            }
        } else {
            // left click
            if (sw.first_click){
                // first click generates the mines so first click is always a zero
                sw.generate(index%sw.game_width, index/sw.game_width);
                sw.first_click = false;
            }
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