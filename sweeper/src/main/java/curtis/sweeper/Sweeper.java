package curtis.sweeper;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

/**
 *
 * @author Curtis Polic
 */
public class Sweeper extends JPanel {

    String[] mines;
    SweeperButton[] mineButtons;
    MainMenu parent;
    int[] mineIndex;
    int frame_height, frame_width, mine_amount, game_width, game_height;

    // constructor
    Sweeper(int squares_high, int squares_across, int mine_num, MainMenu mm) {
        // initializing values
        final int BUTTON_HEIGHT = 30, BUTTON_WIDTH = 30;
        final int TOPX = 30, TOPY = 55; //topy needs more due to window bar
        parent = mm;
        game_height = squares_high;
        game_width = squares_across;
        mine_amount = mine_num;

        // button height * buttons + trim
        frame_height = BUTTON_HEIGHT*game_height+30;
        frame_width = BUTTON_WIDTH*game_width+30;

        // setting button amount
        mineButtons = new SweeperButton[game_height*game_width];
        mines = new String[game_height*game_width];

        // will be useful for victory checking that this is non-temporary
        mineIndex = new int[mine_amount];

        String mine;
        
        // init the mines
        Random rand = new Random();
        for (int i = 0; i<mine_amount;) {
            int index = rand.nextInt(mines.length);
            if (!"*".equals(mines[index])) {
                mines[index] = "*";
                mineIndex[i] = index;
                i++;
            }          
        }
        
        // all other squares to 0
        for (int i = 0; i<mines.length; i++) {
            if (!"*".equals(mines[i])) {
                mines[i] = "0";
            }
        }
        
        //populate the numbers
        for (int i =0; i<mine_amount; i++) {
            int mineSpot = mineIndex[i];
            
            // increment horizontal adjacents
            // left
            if (mineSpot%game_width != 0) {
                if (!"*".equals(mines[mineSpot - 1])) {
                    mines[mineSpot - 1] = Integer.toString(Integer.parseInt(mines[mineSpot - 1]) + 1);
                }
            }
            // right
            if (mineSpot%game_width != game_width - 1) {
                if (!"*".equals(mines[mineSpot + 1])) {
                    mines[mineSpot + 1] = Integer.toString(Integer.parseInt(mines[mineSpot + 1]) + 1);
                }
            }
            
            // increment vertical adjacents
            // up
            if (mineSpot > game_width - 1) {
                if (!"*".equals(mines[mineSpot - game_width])) {
                    mines[mineSpot - game_width] = Integer.toString(Integer.parseInt(mines[mineSpot - game_width]) + 1);
                }
            }
            // down
            if (mineSpot < game_width * (game_height-1)) {
                if (!"*".equals(mines[mineSpot + game_width])) {
                    mines[mineSpot + game_width] = Integer.toString(Integer.parseInt(mines[mineSpot + game_width]) + 1);
                }
            }
            
            // increment diagonals
            // up and left
            if (mineSpot > game_width - 1 && mineSpot%game_width != 0) {
                if (!"*".equals(mines[mineSpot - game_width - 1])) {
                    mines[mineSpot - game_width - 1] = Integer.toString(Integer.parseInt(mines[mineSpot - game_width - 1]) + 1);
                }
            }
            // up and right
            if (mineSpot > game_width - 1 && mineSpot%game_width != game_width - 1) {
                if (!"*".equals(mines[mineSpot - game_width + 1])) {
                    mines[mineSpot - game_width + 1] = Integer.toString(Integer.parseInt(mines[mineSpot - game_width + 1]) + 1);
                }
            }
             // down and left
            if (mineSpot < game_width * (game_height-1) && mineSpot%game_width != 0) {
                if (!"*".equals(mines[mineSpot + game_width - 1])) {
                    mines[mineSpot + game_width - 1] = Integer.toString(Integer.parseInt(mines[mineSpot + game_width - 1]) + 1);
                }
            }
            // down and right
            if (mineSpot < game_width * (game_height-1) && mineSpot%game_width != game_width - 1) {
                if (!"*".equals(mines[mineSpot + game_width + 1])) {
                    mines[mineSpot + game_width + 1] = Integer.toString(Integer.parseInt(mines[mineSpot + game_width + 1]) + 1);
                }
            }
        }
        
        // create the buttons
        int tempX, tempY = TOPY;
        
        for (int i = 0; i<game_height; i++) {
            tempX = TOPX;
            for (int j = 0; j<game_width; j++) {
                // this passes the button its mine/number
                int index = i * game_width + j;
                mine = mines[index];
                mineButtons[i*game_width+j] = new SweeperButton(tempX,tempY,BUTTON_WIDTH,BUTTON_HEIGHT,"",mine, this, index);
                tempX += BUTTON_WIDTH;
            }
            tempY += BUTTON_HEIGHT;
        }
        
        // panel settings
        setLayout(new GridLayout(game_height,game_width));
        setVisible(true);
    }

    public void lose() {
        JOptionPane.showMessageDialog(this, "You hit a mine :(");
        parent.frame.remove(this);
        parent.resetGUI();
    }
    
    public boolean checkVictory() {
        // return false for a not flagged mine
        for (int index : mineIndex) {
            if (!mineButtons[index].getText().equals("|>")) {
                return false;
            }
        }
        // return true if flag count matches mine count else false
        int check = 0;
        for (SweeperButton mineButton : mineButtons) {
            if (mineButton.getText().equals("|>")) {
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
        frame.setSize(sweep.frame_width, sweep.frame_height);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
    }
}