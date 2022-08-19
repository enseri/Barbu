package States;

import java.awt.Graphics;
import java.util.ArrayList;

import Game.Game;

import java.awt.Color;
import java.awt.Font;

import Inputs.Keyboard;
import Objects.Object;
import Objects.*;

public class MENU extends States {
    ArrayList<Object> objects = new ArrayList<>();

    @Override
    public void render(Graphics g) { // Home Screen
        if (objects.size() == 0)
            objects.add(new Button("PLAY", 125, 50, 250, 50));
        g.setColor(Color.black);
        g.fillRect(125, 50, 250, 50);
        g.setColor(Color.magenta);
        g.setFont(new Font("serif", 50, 50));
        Game.drawCenteredString(g, "PLAY", 125, 50, 250, 50, new Font(Font.SERIF, 50, 50));
        g.setColor(Color.red);
    }

    public String toString() {
        return "MENU";
    }

    public ArrayList<Object> getObjects() {
        return objects;
    }

    public void interactionWithObject(Object object, String action) { // For AI TESTING NOT REACHABLE
        switch (action) {
            case "clicked":
                switch (object.getToString()[0]) {
                    case "Button":
                        switch (object.getToString()[1]) {
                            case "PLAY":
                                State.currentState = new PLAYING();
                                break;
                        }
                        break;
                }
                break;
            case "typed":
                switch (Keyboard.lastKeyNum) {
                    case 38:
                        Game.y -= 5;
                        break;
                    case 37:
                        Game.x -= 5;
                        break;
                    case 40:
                        Game.y += 5;
                        break;
                    case 39:
                        Game.x += 5;
                        break;
                }
                switch (Keyboard.lastKeyStr) {
                    case "f":
                        Game.zoomWidth += 5;
                        Game.zoomHeight += 5;
                        break;
                    case "g":
                        Game.zoomWidth -= 5;
                        Game.zoomHeight -= 5;
                        break;
                    case "r":
                        if(Game.currentNetwork + 1 < Game.bot.ends.size())
                        Game.currentNetwork++;
                        break;
                    case "t":
                        if(Game.currentNetwork > 0)
                        Game.currentNetwork--;
                        break;
                    case "s":
                        Game.showBrains = !Game.showBrains;
                        break;
                }
        }
    }

    @Override
    public int botRequest(int x) {
        return 0;
    }
}
