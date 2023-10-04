package curtis.sweeper;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author Curtis Polic
 */
public class Sweeper extends JPanel {

    String[] mines;
    SweeperButton[] mine_buttons;
    MainMenu parent;
    int[] mine_index;
    int panel_height, panel_width, mine_amount, game_width, game_height;
    boolean first_click;

    // constructor
    Sweeper(int squares_high, int squares_across, int mine_num, MainMenu mm) {
        // initializing values
        final int BUTTON_HEIGHT = 30, BUTTON_WIDTH = 30;
        final int TOPX = 30, TOPY = 55; //topy needs more due to window bar
        parent = mm;
        first_click = true;
        game_height = squares_high;
        game_width = squares_across;
        mine_amount = mine_num;

        // button height * buttons + trim
        panel_height = BUTTON_HEIGHT*game_height+30;
        panel_width = BUTTON_WIDTH*game_width+30;

        // setting button amount
        mine_buttons = new SweeperButton[game_height*game_width];
        mines = new String[game_height*game_width];

        // will be useful for victory checking that this is non-temporary
        mine_index = new int[mine_amount];
        
        // create the buttons
        int tempX, tempY = TOPY;
        
        for (int i = 0; i<game_height; i++) {
            tempX = TOPX;
            for (int j = 0; j<game_width; j++) {
                int index = game_width*i + j;
                mine_buttons[i*game_width+j] = new SweeperButton(tempX,tempY,BUTTON_WIDTH,BUTTON_HEIGHT,"", this, index);
                tempX += BUTTON_WIDTH;
            }
            tempY += BUTTON_HEIGHT;
        }
        
        // panel settings
        setLayout(new GridLayout(game_height,game_width));
        setVisible(true);
    }

    public void lose(int loss_index) {
        // "click" the whole board, so you can see how you went
        for (SweeperButton but : mine_buttons) {
            // ignore correct flags
            if (!(but.getText().equals("|>") && but.mine.equals("*"))) {
                // "click"
                but.resetColor();
                // keep the zeroes as blank
                if (but.mine.equals("0")) {
                    but.setText("");
                } else {
                    but.setText(but.mine);
                }
                but.setBackground(Color.WHITE);
            }
        }
        mine_buttons[loss_index].setBackground(Color.RED);
        JOptionPane.showMessageDialog(this, "You hit a mine :(");
        parent.resetGUI();
    }

    public void victory() {
        JOptionPane.showMessageDialog(this, "Victory!");
        parent.frame.remove(this);
        parent.resetGUI();
    }

    public void generate(int first_click_x, int first_click_y) {
        ArrayList<Integer> forced_not_mine = getSafeSquares(first_click_x, first_click_y);

        // init the mines
        Random rand = new Random();
        for (int i = 0; i<mine_amount;) {
            int index = rand.nextInt(mines.length);
            if (!"*".equals(mines[index]) & !forced_not_mine.contains(index)) {
                mines[index] = "*";
                mine_index[i] = index;
                i++;
            }
        }

        // all other squares to 0
        for (int i = 0; i<mines.length; i++) {
            if (!"*".equals(mines[i])) {
                mines[i] = "0";
            }
        }

        // populate the numbers
        for (int i =0; i<mine_amount; i++) {
            int mine_spot = mine_index[i];

            // increment horizontal adjacents
            // left
            if (mine_spot%game_width != 0) {
                if (!"*".equals(mines[mine_spot - 1])) {
                    mines[mine_spot - 1] = Integer.toString(Integer.parseInt(mines[mine_spot - 1]) + 1);
                }
            }
            // right
            if (mine_spot%game_width != game_width - 1) {
                if (!"*".equals(mines[mine_spot + 1])) {
                    mines[mine_spot + 1] = Integer.toString(Integer.parseInt(mines[mine_spot + 1]) + 1);
                }
            }

            // increment vertical adjacents
            // up
            if (mine_spot > game_width - 1) {
                if (!"*".equals(mines[mine_spot - game_width])) {
                    mines[mine_spot - game_width] = Integer.toString(Integer.parseInt(mines[mine_spot - game_width]) + 1);
                }
            }
            // down
            if (mine_spot < game_width * (game_height-1)) {
                if (!"*".equals(mines[mine_spot + game_width])) {
                    mines[mine_spot + game_width] = Integer.toString(Integer.parseInt(mines[mine_spot + game_width]) + 1);
                }
            }

            // increment diagonals
            // up and left
            if (mine_spot > game_width - 1 && mine_spot%game_width != 0) {
                if (!"*".equals(mines[mine_spot - game_width - 1])) {
                    mines[mine_spot - game_width - 1] = Integer.toString(Integer.parseInt(mines[mine_spot - game_width - 1]) + 1);
                }
            }
            // up and right
            if (mine_spot > game_width - 1 && mine_spot%game_width != game_width - 1) {
                if (!"*".equals(mines[mine_spot - game_width + 1])) {
                    mines[mine_spot - game_width + 1] = Integer.toString(Integer.parseInt(mines[mine_spot - game_width + 1]) + 1);
                }
            }
            // down and left
            if (mine_spot < game_width * (game_height-1) && mine_spot%game_width != 0) {
                if (!"*".equals(mines[mine_spot + game_width - 1])) {
                    mines[mine_spot + game_width - 1] = Integer.toString(Integer.parseInt(mines[mine_spot + game_width - 1]) + 1);
                }
            }
            // down and right
            if (mine_spot < game_width * (game_height-1) && mine_spot%game_width != game_width - 1) {
                if (!"*".equals(mines[mine_spot + game_width + 1])) {
                    mines[mine_spot + game_width + 1] = Integer.toString(Integer.parseInt(mines[mine_spot + game_width + 1]) + 1);
                }
            }
        }

        // give the buttons their number
        for (int i =0; i<mines.length; i++) {
            mine_buttons[i].mine = mines[i];
        }
    }

    private ArrayList<Integer> getSafeSquares(int first_click_x, int first_click_y) {
        ArrayList<Integer> forced_not_mine = new ArrayList<>();

        // first click must be a zero
        // make the first click not a mine
        forced_not_mine.add(first_click_y *game_width+ first_click_x);

        // to the left
        if (first_click_x > 0) {
            forced_not_mine.add(first_click_y *game_width+ first_click_x - 1);
        }

        // to the right
        if (first_click_x < game_width-1) {
            forced_not_mine.add(first_click_y *game_width+ first_click_x + 1);
        }

        // above
        if (first_click_y > 0) {
            forced_not_mine.add((first_click_y -1)*game_width+ first_click_x);
        }

        // below
        if (first_click_y < game_height-1) {
            forced_not_mine.add((first_click_y +1)*game_width+ first_click_x);
        }

        // top left
        if (first_click_x > 0 & first_click_y > 0){
            forced_not_mine.add((first_click_y -1)*game_width+ first_click_x - 1);
        }

        // top right
        if (first_click_x < game_width-1 & first_click_y > 0) {
            forced_not_mine.add((first_click_y -1)*game_width+ first_click_x + 1);
        }

        // bottom left
        if (first_click_x > 0 & first_click_y < game_height-1) {
            forced_not_mine.add((first_click_y +1)*game_width+ first_click_x - 1);
        }

        // bottom right
        if (first_click_x < game_width-1 & first_click_y < game_height-1) {
            forced_not_mine.add((first_click_y +1)*game_width+ first_click_x + 1);
        }
        return forced_not_mine;
    }

    public boolean checkVictory() {
        // return false for a not flagged mine
        for (int index : mine_index) {
            if (!mine_buttons[index].getText().equals("|>")) {
                return false;
            }
        }
        // return true if flag count matches mine count else false
        int check = 0;
        for (SweeperButton but : mine_buttons) {
            if (but.getText().equals("|>")) {
                check++;
            }
        }

        /*   old version of above utilizing for loops rather than for-each. learning!
        for (int i = 0; i<mineIndex.length; i++) {
            if (!mineButtons[mineIndex[i]].getText().equals("|>")) {
                return false;
            }
        }
        // return true if flag count matches mine count else false
        int check = 0;
        for (int i = 0; i<mineButtons.length; i++) {
            if (mineButtons[i].getText().equals("|>")) {
                check++;
            }
        }*/
        return check == mine_amount;
    }
    
    // runtime
    public static void main(String[] args) {
        JFrame frame = new JFrame("JavaSweeper");
        Sweeper sweep = new Sweeper(10,10,10, null);
        frame.add(sweep);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setSize(sweep.panel_width, sweep.panel_height);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
    }
}