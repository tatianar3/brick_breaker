package brick_breaker;

import java.awt.*;

public class MapGenerator {
    public int map[][]; // 2d array with all bricks
    public int brickWidth;
    public int brickHeight;

    public MapGenerator(int row, int col) {
        map = new int[row][col];
        for(int i = 0; i < map.length; i++) {
            for(int j = 0; j < map[0].length; j++){
                map[i][j] = 1; // brick has not been hit with the ball, so is shown
            }
        }
        brickWidth = 540/col;
        brickHeight = 150/row;
    }

    public void draw(Graphics2D g) {
        for(int i = 0; i < map.length; i++) {
            for(int j = 0; j < map[0].length; j++){
                if(map[i][j] > 0) { // brick has not been hit with the ball, so is shown
                    g.setColor(Color.PINK);
                    g.fillRect(j * brickWidth + 80, i * brickHeight + 50, brickWidth, brickHeight);

                    // makes bricks separate
                    g.setStroke(new BasicStroke(3));
                    g.setColor(Color.black);
                    g.drawRect(j * brickWidth + 80, i * brickHeight + 50, brickWidth, brickHeight);
                }
            }
        }
    }

    public void setBrickValue(int value, int row, int col) {
        map[row][col] = value;
    }
}
