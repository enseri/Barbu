package Animations;

import java.awt.Graphics;

import javax.swing.ImageIcon;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class Distribute extends Animation {
    private int currentPlayer = 0, currentBot = 0, drawnCards = 0, direction = 0, movements = 0, x = 200, y = 200;
    private ArrayList<Integer> toDraw = new ArrayList<>();

    @Override
    public void animate(Graphics g) {
        int num0 = 0, num1 = 0, num2 = 0, num3 = 0;
        for (int i = 0; i < toDraw.size(); i++) {
            switch (toDraw.get(i)) {
                case 0:
                    g.drawImage(new ImageIcon("src/Images/back.jpg").getImage(), 200, 20 - num0, 100, 150, null);
                    num0++;
                    break;
                case 1:
                    g.drawImage(new ImageIcon("src/Images/back.jpg").getImage(), 400, 200 - num1, 100, 150, null);
                    num1++;
                    break;
                case 2:
                    g.drawImage(new ImageIcon("src/Images/back.jpg").getImage(), 200, 400 - num2, 100, 150, null);
                    num2++;
                    break;
                case 3:
                    g.drawImage(new ImageIcon("src/Images/back.jpg").getImage(), 0, 200 - num3, 100, 150, null);
                    num3++;
                    break;
            }
        }
        for (int i = 0; i < 52 - drawnCards; i++) {
            g.drawImage(new ImageIcon("src/Images/back.jpg").getImage(), x, y - i, 100, 150, null);
        }
        if (drawnCards < 52) {
            try{
                TimeUnit.NANOSECONDS.sleep(100);
            } catch(InterruptedException e) {

            }
            switch (direction) {
                case 0:
                    g.drawImage(new ImageIcon("src/Images/back.jpg").getImage(), x, y - movements, 100, 150, null);
                    movements++;
                    if (movements == 200) {
                        drawnCards++;
                        movements = 0;
                        direction++;
                        toDraw.add(0);
                    }
                    break;
                case 1:
                    g.drawImage(new ImageIcon("src/Images/back.jpg").getImage(), x + movements, y, 100, 150, null);
                    movements++;
                    if (movements == 200) {
                        drawnCards++;
                        movements = 0;
                        direction++;
                        toDraw.add(1);
                    }
                    break;
                case 2:
                    g.drawImage(new ImageIcon("src/Images/back.jpg").getImage(), x, y + movements, 100, 150, null);
                    movements++;
                    if (movements == 200) {
                        drawnCards++;
                        movements = 0;
                        direction++;
                        toDraw.add(2);
                    }
                    break;
                case 3:
                    g.drawImage(new ImageIcon("src/Images/back.jpg").getImage(), x - movements, y, 100, 150, null);
                    movements++;
                    if (movements == 200) {
                        drawnCards++;
                        movements = 0;
                        direction = 0;
                        toDraw.add(3);
                    }
                    break;
            }
        }
    }
}
