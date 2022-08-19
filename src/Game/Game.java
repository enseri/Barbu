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
    public static int x = 0, y = 0, zoomWidth = 20, zoomHeight = 20, currentNetwork = 0; // For AI TESTING NOT REACHABLE
    public static boolean goCrazy = false, showBrains = false; // For AI TESTING NOT REACHABLE
    Keyboard keyboard;
    Mouse mouse;
    public static Bot bot = new Bot(null, "1");

    public Game() {
        keyboard = new Keyboard();
        mouse = new Mouse();
        init();
    }

    public void init() {
        bot.generateBrain(); // For AI TESTING NOT REACHABLE
        addMouseListener(mouse);
        addKeyListener(keyboard);
        setBackground(Color.white);
        setFocusable(true);
        setPreferredSize(new Dimension(500, 500));
        setVisible(true);
        new Threads(this).start();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        doDrawing(g);
    }

    public void doDrawing(Graphics g) {
        Render.render(g);
        if(showBrains) { // For AI TESTING NOT REACHABLE
            for (int i = bot.ends.get(currentNetwork)[0] + 1; i < bot.ends.get(currentNetwork)[3]; i++) {
                g.setColor(Color.red);
                g.fillOval(0 - x - zoomWidth - (zoomWidth / 2), ((i - bot.ends.get(currentNetwork)[0]) * zoomHeight) - y, zoomWidth, zoomHeight);
                for (int a = 0; a < bot.IToC.size(); a++)
                    if (bot.IToC.get(a)[0] == i)
                        g.drawLine(zoomWidth - x - zoomWidth - (zoomWidth / 2), ((i - bot.ends.get(currentNetwork)[0]) * zoomHeight) + (zoomHeight / 2) - y, 50 - x, ((int) (bot.IToC.get(a)[2] - bot.ends.get(currentNetwork)[1]) * zoomHeight) + (zoomHeight / 2) - y);
            } //input
            for (int i = bot.ends.get(currentNetwork)[1] + 1; i < bot.ends.get(currentNetwork)[4]; i++) {
                g.setColor(Color.blue);
                g.fillOval(50 - x, ((i - bot.ends.get(currentNetwork)[1]) * zoomHeight) - y, zoomWidth, zoomHeight);
            } //combine
            for (int i = bot.ends.get(currentNetwork)[2] + 1; i < bot.ends.get(currentNetwork)[5]; i++) {
                g.setColor(Color.green);
                g.fillOval(100 - x + zoomWidth + (zoomWidth / 2), ((i - bot.ends.get(currentNetwork)[2]) * zoomHeight) - y, zoomWidth, zoomHeight);
                for (int a = 0; a < bot.CToO.size(); a++)
                    if (bot.CToO.get(a)[2] == i)
                        g.drawLine(100 - x + zoomWidth + (zoomWidth / 2), ((i - bot.ends.get(currentNetwork)[2]) * zoomHeight) + (zoomHeight / 2) - y, 50 + zoomWidth - x, (((int) bot.CToO.get(a)[0] - bot.ends.get(currentNetwork)[1]) * zoomHeight) + (zoomHeight / 2) - y);
            } //output
        }
    }

    public static void drawCenteredString(Graphics g, String text, int x, int y, int width, int height, Font font) {
        FontMetrics metrics = g.getFontMetrics(font);
        int tempX = x + (width - metrics.stringWidth(text)) / 2;
        int tempY = y + ((height - metrics.getHeight()) / 2) + metrics.getAscent();
        g.setFont(font);
        g.drawString(text, tempX, tempY);
    }
}

class Threads extends Thread { // Frames Per Second
    double timePerFrame = 1000 / 60;
    double lastFrame;
    Game game;

    public Threads(Game game) {
        this.game = game;
    }

    public void run() {
        while (true) {
            if (System.currentTimeMillis() - lastFrame >= timePerFrame || Game.goCrazy) { // Timing
                lastFrame = System.currentTimeMillis();
                game.repaint();
            }
        }
    }
}