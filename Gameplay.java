package brick_breaker;

import javax.swing.*;
import java.awt.*;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Gameplay extends JPanel implements KeyListener, ActionListener {
    private boolean play = false;
    private int score = 0;

    private int totalBricks = 36;

    private Timer timer;
    private int delay = 6;

    private int playerX = 310; // starting position of slider

    private int ballposX = 200; // ball starting point pos X
    private int ballposY = 350; // ball starting point pos Y
    private int ballXdir = 1;
    private int ballYdir = 2;

    private MapGenerator map;

    public Gameplay() {
        map = new MapGenerator(6, 6);
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        timer = new Timer(delay, this);
        timer.start();
    }

    public void paint(Graphics g) {
        //background
        g.setColor(Color.black);
        g.fillRect(1,1, 692, 592);

        //brick map
        map.draw((Graphics2D) g);

        //borders
        g.setColor(Color.lightGray);
        g.fillRect(0,0,8,592);
        g.fillRect(0,0,692,8);
        g.fillRect(691,0,8,592);

        //score
        g.setColor(Color.white);
        g.setFont(new Font("monospace", Font.BOLD, 25));
        g.drawString("Score: " + score, 530, 34);

        //paddle
        g.setColor(Color.lightGray);
        g.fillRect(playerX, 550, 100, 8);

        //ball
        g.setColor(Color.red);
        g.fillOval(ballposX, ballposY, 20, 20);

        if(totalBricks <= 0) { // all bricks gone
            play = false;
            ballXdir = 0;
            ballYdir = 0;
            g.setColor(Color.red);
            g.setFont(new Font("monospace", Font.BOLD, 32));
            g.drawString("You Won! :)", 250, 260);

            g.setFont(new Font("serif", Font.BOLD, 30));
            g.drawString("Press Enter to restart", 220, 300);
        }

        if(ballposY > 590) { // goes OOB
            play = false;
            ballXdir = 0;
            ballYdir = 0;
            g.setColor(Color.red);
            g.setFont(new Font("monospace", Font.BOLD, 32));
            g.drawString("GAME OVER!",250, 260);

            g.setFont(new Font("monospace", Font.BOLD, 30));
            g.drawString("Score: " + score, 280, 300);

            g.setFont(new Font("monospace", Font.BOLD, 25));
            g.drawString("Press Enter to restart", 220, 340);

        }

        g.dispose();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        timer.start();
        if (play) { // edit so that when ball hits left edge/right edge, it goes in that direction u get me?
            if(new Rectangle(ballposX, ballposY, 20, 20).intersects(new Rectangle(playerX, 550, 100,8))) {
                ballYdir = -ballYdir; //detects ball's intersection with rectangle
            }

            A: for(int i = 0; i < map.map.length; i++) { // map 1 is map in this class, map 2 is map in MapGenerator
                for(int j = 0; j < map.map[0].length; j++) {
                    if(map.map[i][j] > 0) { // detect intersection of brick and ball
                        int brickX = j  * map.brickWidth + 80;
                        int brickY = i * map.brickHeight + 50;
                        int brickWidth = map.brickWidth;
                        int brickHeight = map.brickHeight;

                        Rectangle rect = new Rectangle(brickX, brickY, brickWidth, brickHeight);
                        Rectangle ballRect = new Rectangle(ballposX, ballposY, 20, 20);
                        Rectangle brickRect = rect;

                        if(ballRect.intersects(brickRect)) { // if rect around ball hits brick...
                            map.setBrickValue(0, i, j);
                            totalBricks--;
                            score += 5;

                            if(ballposX + 19 <= brickRect.x || ballposX + 1 >= brickRect.x + brickRect.width) {
                                ballXdir = -ballXdir;
                            } else {
                                ballYdir = -ballYdir;
                            }

                            break A; // when exiting loop, goes to where A is labeled
                        }
                    }
                }
            }

            ballposX += ballXdir;
            ballposY += ballYdir;
            if(ballposX < 0) // bottom
                ballXdir = -ballXdir;
            if(ballposY < 0) // top
                ballYdir = -ballYdir;
            if(ballposX > 670) // right border
                ballXdir = -ballXdir;

        }

        repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {} // don't need, but will give error if not in class

    @Override
    public void keyReleased(KeyEvent e) {} // don't need

    @Override
    public void keyPressed(KeyEvent e) { // key is held
        if(e.getKeyCode() == KeyEvent.VK_RIGHT) { // if right arrow is detected as pressed
            if(playerX >= 600)
                playerX = 600;
            else
                moveRight();
        }
        if (e.getKeyCode() == KeyEvent.VK_LEFT) { // if left arrow is detected as pressed
            if(playerX < 10)
                playerX = 10;
            else
                moveLeft();
        }

        if (e.getKeyCode() == KeyEvent.VK_ENTER) { // if enter is pressed to restart game
            if(!play) {
                play = true;
                ballposX = 200;
                ballposY = 350;
                ballXdir = 1;
                ballYdir = 2;
                playerX = 310;
                score = 0;
                totalBricks = 36;
                map = new MapGenerator(6, 6);
                repaint();
            }
        }
    }

    public void moveRight() {
        play = true;
        playerX += 20;
    }

    public void moveLeft() {
        play = true;
        playerX -= 20;
    }

}
