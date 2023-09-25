
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
    
    // initializing values        
    final int BUTTON_HEIGHT = 30, BUTTON_WIDTH = 30;
    final int TOPX = 30, TOPY = 55; //topy needs more due to window bar
    final int GAME_HEIGHT = 10, GAME_WIDTH = 10;
    final int MINES = 10;
    
    // button height * buttons + trim
    final int FRAME_HEIGHT = BUTTON_HEIGHT*GAME_HEIGHT+TOPY+30;
    final int FRAME_WIDTH = BUTTON_WIDTH*GAME_WIDTH+TOPX+30;
    
    // setting button amount
    SweeperButton[] mineButtons = new SweeperButton[GAME_HEIGHT*GAME_WIDTH];
    String[] mines = new String[GAME_HEIGHT*GAME_WIDTH];
    
    // will be useful for victory checking that this is non-temporary
    int[] mineIndex = new int[MINES];
    
    // constructor
    Sweeper() {

        // super makes frameText accessible anywhere
        String mine;
        
        // init the mines
        Random rand = new Random();
        for (int i = 0; i<MINES;) {
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
        for (int i =0; i<MINES; i++) {
            int mineSpot = mineIndex[i];
            
            // increment horizontal adjacents
            // left
            if (mineSpot%GAME_WIDTH != 0) {
                if (!"*".equals(mines[mineSpot - 1])) {
                    mines[mineSpot - 1] = Integer.toString(Integer.parseInt(mines[mineSpot - 1]) + 1);
                }
            }
            // right
            if (mineSpot%GAME_WIDTH != GAME_WIDTH - 1) {
                if (!"*".equals(mines[mineSpot + 1])) {
                    mines[mineSpot + 1] = Integer.toString(Integer.parseInt(mines[mineSpot + 1]) + 1);
                }
            }
            
            // increment vertical adjacents
            // up
            if (mineSpot > GAME_WIDTH - 1) {
                if (!"*".equals(mines[mineSpot - GAME_WIDTH])) {
                    mines[mineSpot - GAME_WIDTH] = Integer.toString(Integer.parseInt(mines[mineSpot - GAME_WIDTH]) + 1);
                }
            }
            // down
            if (mineSpot < GAME_WIDTH * (GAME_HEIGHT-1)) {
                if (!"*".equals(mines[mineSpot + GAME_WIDTH])) {
                    mines[mineSpot + GAME_WIDTH] = Integer.toString(Integer.parseInt(mines[mineSpot + GAME_WIDTH]) + 1);
                }
            }
            
            // increment diagonals
            // up and left
            if (mineSpot > GAME_WIDTH - 1 && mineSpot%GAME_WIDTH != 0) {
                if (!"*".equals(mines[mineSpot - GAME_WIDTH - 1])) {
                    mines[mineSpot - GAME_WIDTH - 1] = Integer.toString(Integer.parseInt(mines[mineSpot - GAME_WIDTH - 1]) + 1);
                }
            }
            // up and right
            if (mineSpot > GAME_WIDTH - 1 && mineSpot%GAME_WIDTH != GAME_WIDTH - 1) {
                if (!"*".equals(mines[mineSpot - GAME_WIDTH + 1])) {
                    mines[mineSpot - GAME_WIDTH + 1] = Integer.toString(Integer.parseInt(mines[mineSpot - GAME_WIDTH + 1]) + 1);
                }
            }
             // down and left
            if (mineSpot < GAME_WIDTH * (GAME_HEIGHT-1) && mineSpot%GAME_WIDTH != 0) {
                if (!"*".equals(mines[mineSpot + GAME_WIDTH - 1])) {
                    mines[mineSpot + GAME_WIDTH - 1] = Integer.toString(Integer.parseInt(mines[mineSpot + GAME_WIDTH - 1]) + 1);
                }
            }
            // down and right
            if (mineSpot < GAME_WIDTH * (GAME_HEIGHT-1) && mineSpot%GAME_WIDTH != GAME_WIDTH - 1) {
                if (!"*".equals(mines[mineSpot + GAME_WIDTH + 1])) {
                    mines[mineSpot + GAME_WIDTH + 1] = Integer.toString(Integer.parseInt(mines[mineSpot + GAME_WIDTH + 1]) + 1);
                }
            }
        }
        
        /* populate the numbers
        this was me figuring out minimum distance to mines instead
        happy with it, so it stays
        for (int i = 0; i<MINES; i++) {
            // find x/y of the mine
            int mineSpot = mineIndex[i];
            int mineX = mineSpot%GAME_WIDTH;
            int mineY = (mineSpot - mineX)/GAME_WIDTH;
            int temp;

            // dont look at my triple loop pls
            for (int j = 0; j<GAME_HEIGHT; j++) {
                for (int k = 0; k<GAME_WIDTH; k++) {
                    int index = j * GAME_WIDTH + k;
                    temp = Math.abs(mineX - k) + Math.abs(mineY - j);
                    if (mines[index] != "*") {
                        if (temp < Integer.parseInt(mines[index])) {
                            mines[index] = Integer.toString(temp);
                        }
                    }
                }
            }
        } */
        
        // create the buttons
        int tempX, tempY = TOPY;
        
        for (int i = 0; i<GAME_HEIGHT; i++) {
            tempX = TOPX;
            for (int j = 0; j<GAME_WIDTH; j++) {
                // this passes the button its mine/number
                int index = i * GAME_WIDTH + j;
                mine = mines[index];
                mineButtons[i*GAME_WIDTH+j] = new SweeperButton(tempX,tempY,BUTTON_WIDTH,BUTTON_HEIGHT,"",mine, this, index);
                tempX += BUTTON_WIDTH;
            }
            tempY += BUTTON_HEIGHT;
        }
        
        // panel settings
        setLayout(new GridLayout(10,10));
        setVisible(true);
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
        return check == MINES;
    }
    
    // runtime
    public static void main(String[] args) {
        JFrame frame = new JFrame("JavaSweeper");
        Sweeper sweep = new Sweeper();
        frame.add(sweep);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setSize(sweep.FRAME_WIDTH, sweep.FRAME_HEIGHT);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
    }
}



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
        
        final int GAME_WIDTH = sw.GAME_WIDTH;
        final int GAME_HEIGHT = sw.GAME_HEIGHT;
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
            }
            else {
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