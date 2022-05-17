import Inputs.Keyboard;
import Inputs.Mouse;
import java.util.Random;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Color;
public class Game extends JPanel{
    Keyboard keyboard;
    Mouse mouse;
    public Game() {
        keyboard = new Keyboard();
        mouse = new Mouse();
        init();
    }
    public void init() {
        setBackground(Color.white);
        setFocusable(true);
        setPreferredSize(new Dimension(500, 500));
        repaint();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        doDrawing(g);
    }

    public void doDrawing(Graphics g) {
        Render.render(g);
        repaint();
    }
}
