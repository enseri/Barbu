package Game;

import Inputs.Keyboard;
import Inputs.Mouse;
import Objects.Bot;

import java.util.Random;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.*;

public class Game extends JPanel {
    Keyboard keyboard;
    Mouse mouse;
    Bot bot = new Bot(null, "1");

    public Game() {
        keyboard = new Keyboard();
        mouse = new Mouse();
        init();
    }

    public void init() {
        addMouseListener(mouse);
        addKeyListener(keyboard);
        setBackground(Color.white);
        setFocusable(true);
        setPreferredSize(new Dimension(500, 500));
        setVisible(true);
        new Threads(this).start();
        bot.generateBrain();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        doDrawing(g);
        for(int a = 0; a < bot.brainConnections.size(); a++) {
            for(int b = 0; b < bot.brainConnections.get(a).size(); b++) {
                for (int c = 0; c < bot.brainConnections.get(a).get(b).size(); c++) {
                    switch(b) {
                        case 0:
                            g.setColor(Color.green);
                            break;
                        case 1:
                            g.setColor(Color.yellow);
                            break;
                        case 2:
                            g.setColor(Color.orange);
                            break;
                        case 3:
                            g.setColor(Color.red);
                            break;
                    }
                    g.fillOval(0 + (a * 100) + (c * 15), 0 + (b * 25), 15, 15);
                    g.setFont(new Font(Font.SERIF, 10, 10));
                    g.setColor(Color.black);
                    g.drawString(bot.brainConnections.get(a).get(b).get(c) + "",0 + (a * 100) + (c * 15) + 7, 0 + (b * 25) + 7);
                }
            }
        }
    }

    public void doDrawing(Graphics g) {
        Render.render(g);
    }

    public static void drawCenteredString(Graphics g, String text, int x, int y, int width, int height, Font font) {
        FontMetrics metrics = g.getFontMetrics(font);
        int tempX = x + (width - metrics.stringWidth(text)) / 2;
        int tempY = y + ((height - metrics.getHeight()) / 2) + metrics.getAscent();
        g.setFont(font);
        g.drawString(text, tempX, tempY);
    }
}

class Threads extends Thread {
    double timePerFrame = 1000 / 60;
    double lastFrame;
    Game game;

    public Threads(Game game) {
        this.game = game;
    }

    public void run() {
        while (true) {
            if (System.currentTimeMillis() - lastFrame >= timePerFrame) {
                lastFrame = System.currentTimeMillis();
                game.repaint();
            }
        }
    }
}