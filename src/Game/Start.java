package Game;
import java.awt.*;

import javax.swing.JFrame;
public class Start extends JFrame{
    public Start() {
        add(new Game());

        setResizable(false);
        pack();

        setTitle("Barbu");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
    public static void main(String[] args) {
        new Start();
    }
}