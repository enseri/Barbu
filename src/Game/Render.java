package Game;
import java.awt.Graphics;
import States.State;
public class Render {
    public static void render(Graphics g) {
        State.currentState.render(g);
    } // Render All States Current Visible
}
