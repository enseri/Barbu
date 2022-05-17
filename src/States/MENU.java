package States;

import java.awt.Graphics;
import java.util.ArrayList;

import java.awt.Color;
import java.awt.Font;
import Objects.Object;
import Objects.*;

public class MENU extends States{
    ArrayList<Object> objects = new ArrayList<>();
    @Override
    public void render(Graphics g) {
        if(objects.size() == 0)
        objects.add(new Button("PLAY", 125, 50, 250, 50));
        g.setColor(Color.black);
        g.fillRect(125, 50, 250, 50);
        g.setColor(Color.magenta);
        g.setFont(new Font("serif", 50, 50));
        g.drawString("PLAY", 175, 100);
        g.setColor(Color.red);
    }
    public String toString() {
        return "MENU";
    }
    public ArrayList<Object> getObjects() {
        return objects;
    }
    public void interactionWithObject(Object object) {
        switch(object.getToString()[0]) {
            case "Button":
                switch(object.getToString()[1]) {
                    case "PLAY":
                        State.currentState = new PLAYING();
                        break;
                }
            break;
        }
    }
}
