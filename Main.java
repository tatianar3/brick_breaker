package brick_breaker;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        JFrame obj = new JFrame(); //outer window where the game will show up
        Gameplay gamePlay = new Gameplay();
        obj.setBounds(10,10, 700, 600); // how big frame is
        obj.setTitle("Brick Breaker");
        obj.setResizable(false); // can't change size of window
        obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // exits program when window is closed
        obj.add(gamePlay);
        obj.setVisible(true); // is visible
    }
}

