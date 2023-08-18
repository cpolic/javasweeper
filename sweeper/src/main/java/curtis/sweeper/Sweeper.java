
package curtis.sweeper;

import java.awt.*;
import java.awt.event.*;
import java.util.Random;

/**
 *
 * @author Curtis
 */
public class Sweeper extends Frame {
    
    // initializing values        
    final int BUTTON_HEIGHT = 30, BUTTON_WIDTH = 30;
    final int TOPX = 30, TOPY = 55; //topy needs more due to window bar
    final int GAME_HEIGHT = 10, GAME_WIDTH = 10;
    final int MINES = 10;
    
    // button height * buttons + trim
    final int FRAME_HEIGHT = BUTTON_HEIGHT*GAME_HEIGHT+TOPY+30;
    final int FRAME_WIDTH = BUTTON_WIDTH*GAME_WIDTH+TOPX+30;
    
    // setting button amount
    SweeperButton mineButtons[] = new SweeperButton[GAME_HEIGHT*GAME_WIDTH];
    String mines[] = new String[GAME_HEIGHT*GAME_WIDTH];
    
    // constructor
    Sweeper(String frameText) {
        
        // super makes frameText accessible anywhere
        super(frameText);
        String mine = "1";
        
        // init the mines
        Random rand = new Random();
        int mineIndex[] = new int[MINES];
        for (int i = 0; i<MINES;) {
            int index = rand.nextInt(mines.length);
            if (mines[index] != "*") {
                mines[index] = "*";
                mineIndex[i] = index;
                i++;
            }          
        }
        
        // all other squares to 0
        for (int i = 0; i<mines.length; i++) {
            if (mines[i] != "*") {
                mines[i] = "0";
            }
        }
        
        //populate the numbers
        for (int i =0; i<MINES; i++) {
            int mineSpot = mineIndex[i];
            
            // increment horizontal adjacents
            // left
            if (mineSpot%GAME_WIDTH != 0) {
                if (mines[mineSpot - 1] != "*") {
                    mines[mineSpot - 1] = Integer.toString(Integer.parseInt(mines[mineSpot - 1]) + 1);
                }
            }
            // right
            if (mineSpot%GAME_WIDTH != GAME_WIDTH - 1) {
                if (mines[mineSpot + 1] != "*") {
                    mines[mineSpot + 1] = Integer.toString(Integer.parseInt(mines[mineSpot + 1]) + 1);
                }
            }
            
            // increment vertical adjacents
            // up
            if (mineSpot > GAME_WIDTH - 1) {
                if (mines[mineSpot - GAME_WIDTH] != "*") {
                    mines[mineSpot - GAME_WIDTH] = Integer.toString(Integer.parseInt(mines[mineSpot - GAME_WIDTH]) + 1);
                }
            }
            // down
            if (mineSpot < GAME_WIDTH * (GAME_HEIGHT-1)) {
                if (mines[mineSpot + GAME_WIDTH] != "*") {
                    mines[mineSpot + GAME_WIDTH] = Integer.toString(Integer.parseInt(mines[mineSpot + GAME_WIDTH]) + 1);
                }
            }
            
            // increment diagonals
            // up and left
            if (mineSpot > GAME_WIDTH - 1 && mineSpot%GAME_WIDTH != 0) {
                if (mines[mineSpot - GAME_WIDTH - 1] != "*") {
                    mines[mineSpot - GAME_WIDTH - 1] = Integer.toString(Integer.parseInt(mines[mineSpot - GAME_WIDTH - 1]) + 1);
                }
            }
            // up and right
            if (mineSpot > GAME_WIDTH - 1 && mineSpot%GAME_WIDTH != GAME_WIDTH - 1) {
                if (mines[mineSpot - GAME_WIDTH + 1] != "*") {
                    mines[mineSpot - GAME_WIDTH + 1] = Integer.toString(Integer.parseInt(mines[mineSpot - GAME_WIDTH + 1]) + 1);
                }
            }
             // down and left
            if (mineSpot < GAME_WIDTH * (GAME_HEIGHT-1) && mineSpot%GAME_WIDTH != 0) {
                if (mines[mineSpot + GAME_WIDTH - 1] != "*") {
                    mines[mineSpot + GAME_WIDTH - 1] = Integer.toString(Integer.parseInt(mines[mineSpot + GAME_WIDTH - 1]) + 1);
                }
            }
            // down and right
            if (mineSpot < GAME_WIDTH * (GAME_HEIGHT-1) && mineSpot%GAME_WIDTH != GAME_WIDTH - 1) {
                if (mines[mineSpot + GAME_WIDTH + 1] != "*") {
                    mines[mineSpot + GAME_WIDTH + 1] = Integer.toString(Integer.parseInt(mines[mineSpot + GAME_WIDTH + 1]) + 1);
                }
            }
        }
        
//        // populate the numbers
//        // this was me figuring out minimum distance to mines instead
//        // happy with it so it stays
//        for (int i = 0; i<MINES; i++) {
//            // find x/y of the mine
//            int mineSpot = mineIndex[i];
//            int mineX = mineSpot%GAME_WIDTH;
//            int mineY = (mineSpot - mineX)/GAME_WIDTH;
//            int temp;
//            
//            // dont look at my triple loop pls
//            for (int j = 0; j<GAME_HEIGHT; j++) {
//                for (int k = 0; k<GAME_WIDTH; k++) {
//                    int index = j * GAME_WIDTH + k;
//                    temp = Math.abs(mineX - k) + Math.abs(mineY - j);
//                    if (mines[index] != "*") {
//                        if (temp < Integer.parseInt(mines[index])) {
//                            mines[index] = Integer.toString(temp);
//                        }
//                    }
//                }
//            }
//        }
        
        // create the buttons
        int tempX, tempY = TOPY;
        
        for (int i = 0; i<GAME_HEIGHT; i++) {
            tempX = TOPX;
            for (int j = 0; j<GAME_WIDTH; j++) {
                // this passes the button its mine/number
                int index = i * GAME_WIDTH + j;
                mine = mines[index];
                mineButtons[i*GAME_WIDTH+j] = new SweeperButton(tempX,tempY,BUTTON_WIDTH,BUTTON_HEIGHT,"",mine, this);
                tempX += BUTTON_WIDTH;
            }
            tempY += BUTTON_HEIGHT;
        }
        
        // closing window button
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        
        // frame settings
        setLayout(null);
        setSize(FRAME_WIDTH, FRAME_HEIGHT);
        setVisible(true);
    }
    
    // runtime
    public static void main(String[] args) {
        new Sweeper("Javasweeper");
    }
}



class SweeperButton extends Button implements MouseListener {
    Sweeper sw;
    String mine;
    
    // constructor
    SweeperButton(int x, int y, int width, int height, String cap, String min, Sweeper sws) {
        super(cap);
        setBounds(x,y,width,height);
        this.sw = sws;
        this.sw.add(this);
        this.mine = min;
        addMouseListener(this);
    }
    
    @Override
    public void mouseClicked(MouseEvent e) {
        // button == 3 is rightclick
        if (e.getButton() == 3 && this.getLabel() == "") {
            // flag button
            this.setLabel("|>");
        } else if (e.getButton() == 3 && this.getLabel() == "|>") {
            //unflag
            this.setLabel("");
        } else {
            // left click
            this.setLabel(mine);
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