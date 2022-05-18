package States;
import java.awt.Graphics;
import java.util.ArrayList;
import Objects.Object;

public abstract class States {
    public abstract String toString();
    public abstract void render(Graphics g);
    public abstract ArrayList<Object> getObjects();
    public abstract void interactionWithObject(Object object, String action);
}
