package Animations;

import java.awt.Font;
import java.awt.Graphics;
import Game.Game;
import javax.swing.ImageIcon;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class Distribute extends Animation {
    private int currentPlayer = 0, currentBot = 0, drawnCards = 0, direction = 0, movements = 0, x = 200, y = 175;
    private ArrayList<Integer> toDraw = new ArrayList<>();

    @Override
    public void animate(Graphics g) {
        int num0 = 0, num1 = 0, num2 = 0, num3 = 0;
        for (int i = 0; i < toDraw.size(); i++) {
            switch (toDraw.get(i)) {
                case 0:
                    g.drawImage(new ImageIcon("src/Images/back.jpg").getImage(), 200, 0 - num0, 100, 150, null);
                    num0++;
                    break;
                case 1:
                    g.drawImage(new ImageIcon("src/Images/back.jpg").getImage(), 400, y - num1, 100, 150, null);
                    num1++;
                    break;
                case 2:
                    g.drawImage(new ImageIcon("src/Images/back.jpg").getImage(), 200, 350 - num2, 100, 150, null);
                    num2++;
                    break;
                case 3:
                    g.drawImage(new ImageIcon("src/Images/back.jpg").getImage(), 0, y - num3, 100, 150, null);
                    num3++;
                    break;
            }
        }
        for (int i = 0; i < 51 - drawnCards; i++) {
            g.drawImage(new ImageIcon("src/Images/back.jpg").getImage(), x, y - i, 100, 150, null);
        }
        Game.drawCenteredString(g, num0 + "", 200, 150, 100, 20, new Font(Font.SANS_SERIF, 20, 20));
        Game.drawCenteredString(g, num1 + "", 380, 175, 20, 150, new Font(Font.SANS_SERIF, 20, 20));
        Game.drawCenteredString(g, num2 + "", 200, 330, 100, 20, new Font(Font.SANS_SERIF, 20, 20));
        Game.drawCenteredString(g, num3 + "", 100, 175, 20, 150, new Font(Font.SANS_SERIF, 20, 20));
        if (drawnCards < 52) {
            try{
                TimeUnit.MILLISECONDS.sleep(1);
            } catch(InterruptedException e) {
                System.out.println("Interrupted");
            }
            switch (direction) {
                case 0:
                    g.drawImage(new ImageIcon("src/Images/back.jpg").getImage(), x, y - movements, 100, 150, null);
                    movements += 5;
                    if (movements == 200) {
                        drawnCards++;
                        movements = 0;
                        direction++;
                        toDraw.add(0);
                    }
                    break;
                case 1:
                    g.drawImage(new ImageIcon("src/Images/back.jpg").getImage(), x + movements, y, 100, 150, null);
                    movements += 5;
                    if (movements == 200) {
                        drawnCards++;
                        movements = 0;
                        direction++;
                        toDraw.add(1);
                    }
                    break;
                case 2:
                    g.drawImage(new ImageIcon("src/Images/back.jpg").getImage(), x, y + movements, 100, 150, null);
                    movements += 5;
                    if (movements == 200) {
                        drawnCards++;
                        movements = 0;
                        direction++;
                        toDraw.add(2);
                    }
                    break;
                case 3:
                    g.drawImage(new ImageIcon("src/Images/back.jpg").getImage(), x - movements, y, 100, 150, null);
                    movements += 5;
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
