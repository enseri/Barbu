package Game;
import java.awt.Dimension;

import javax.swing.JFrame;
public class Start extends JFrame{
    public Start() {
        Game game = new Game();
        setPreferredSize(new Dimension(500, 500));
        add(game);
        addKeyListener(game.keyboard);
        addMouseListener(game.mouse);
        setResizable(false);
        pack();
        setTitle("Barbu");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setFocusable(true);
    }
    public static void main(String[] args) {
        new Start();
    }
}