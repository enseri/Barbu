package States;

import java.util.ArrayList;
import java.awt.Graphics;
import Objects.Object;
import Objects.*;
import java.awt.Color;
import java.awt.Font;
import Animations.*;
import Inputs.Keyboard;
import Game.Game;

public class PLAYING extends States {
    private ArrayList<Object> objects = new ArrayList<>();
    private Player[] players = new Player[4];
    private int mode = 0;
    private Distribute distribute = new Distribute();
    /*
     * Mode 0: Num Players to Bots
     * Mode 1: Name Players
     * Mode 2: Distribute Cards
     * Mode 3: Player Picks Rule // Show Player Cards
     * Mode 4: Play
     */
    public Object selectedObject;

    @Override
    public void render(Graphics g) {
        g.setColor(Color.magenta);
        if (objects.size() == 0) {
            objects.add(new Button("NEXT", 450, 450, 50, 50));
            switch (mode) {
                case 0:
                    objects.add(new Button("1 Players; 3 Bots", 100, 50, 300, 50));
                    objects.add(new Button("2 Players; 2 Bots", 100, 150, 300, 50));
                    objects.add(new Button("3 Players; 1 Bots", 100, 250, 300, 50));
                    objects.add(new Button("4 Players; 0 Bots", 100, 350, 300, 50));
                    selectedObject = objects.get(objects.size() - 1);
                    break;
                case 1:
                    for (int i = 0; i < players.length; i++) {
                        objects.add(new TextBox("Player " + (i + 1) + ": ", 100, 50 + (i * 100), 300, 50));
                    }
                    break;
                case 2:

                    break;
                case 3:
                    objects.add(new Button("Pas De Barbu", 50, 50, 100, 150));
                    objects.add(new Button("Pas De Coeur", 200, 50, 100, 150));
                    objects.add(new Button("Pas De Reine", 350, 50, 100, 150));
                    objects.add(new Button("Pas De Plis", 125, 250, 100, 150));
                    objects.add(new Button("Ratatouille", 275, 250, 100, 150));
                    break;
            }
        }
        switch (mode) {
            case 0:
                g.setColor(Color.black);
                g.fillRect(450, 450, 50, 50);
                g.setColor(Color.red);
                Game.drawCenteredString(g, "NEXT", 450, 450, 50, 50, new Font(Font.SERIF, 10, 10));
                for (int i = 0; i < objects.size(); i++) {
                    if (objects.get(i).getToString().length > 1 && !objects.get(i).getToString()[1].equals("NEXT")) {
                        int[] data = objects.get(i).getData();
                        g.setColor(Color.black);
                        g.fillRect(data[0], data[1], data[2], data[3]);
                        g.setColor(Color.red);
                        Game.drawCenteredString(g, objects.get(i).getToString()[1], data[0], data[1], data[2], data[3],
                                new Font(Font.SERIF, 25, 25));
                        if (selectedObject != null && selectedObject == objects.get(i)) {
                            g.setColor(Color.green);
                            g.drawRect(data[0], data[1], data[2], data[3]);
                            g.drawRect(data[0] - 1, data[1] - 1, data[2] + 2, data[3] + 2);
                            g.drawRect(data[0] - 2, data[1] - 2, data[2] + 4, data[3] + 4);
                        }
                    }

                }
                break;
            case 1:
                g.setColor(Color.black);
                for (int i = 0; i < players.length; i++) {
                    g.fillRect(100, 50 + (i * 100), 300, 50);
                }
                g.setColor(Color.red);
;                for (int i = 0; i < objects.size(); i++) {
                    Object tempSelectedObject = objects.get(i);
                    int[] data = tempSelectedObject.getData();
                    if (tempSelectedObject.getToString().length > 2) {
                        Game.drawCenteredString(g, tempSelectedObject.getToString()[1] + tempSelectedObject.getToString()[2],
                                data[0],
                                data[1],data[2], data[3], new Font(Font.SERIF, 25, 25));
                    }
                }
                g.setColor(Color.black);
                g.fillRect(450, 450, 50, 50);
                g.setColor(Color.red);
                Game.drawCenteredString(g, "NEXT", 450, 450, 50, 50, new Font(Font.SERIF, 10, 10));
                break;
            case 2:
                distribute.animate(g);
                g.setColor(Color.black);
                g.fillRect(450, 450, 50, 50);
                g.setColor(Color.red);
                Game.drawCenteredString(g, "NEXT", 450, 450, 50, 50, new Font(Font.SERIF, 10, 10));
                break;
            case 3:
                g.setColor(Color.black);
                g.fillRect(450, 450, 50, 50);
                g.setColor(Color.red);
                Game.drawCenteredString(g, "", 450, 450, 50, 50, new Font(Font.SERIF, 10, 10));
                Game.drawCenteredString(g, "NEXT", 450, 450, 50, 50, new Font(Font.SERIF, 10, 10));
                g.fillRect(50, 50, 100, 150);
                g.fillRect(200, 50, 100, 150);
                g.fillRect(350, 50, 100, 150);
                g.fillRect(125, 250, 100, 150);
                g.fillRect(275, 250, 100, 150);
                break;
        }
    }

    public String toString() {
        return "PLAYING";
    }

    public ArrayList<Object> getObjects() {
        return objects;
    }

    public void interactionWithObject(Object object, String action) {
        switch (action) {
            case "clicked":
                selectedObject = object;
                switch (object.getToString()[0]) {
                    case "Button":
                        switch (object.getToString()[1]) {
                            case "NEXT":
                                mode++;
                                selectedObject = null;
                                objects.clear();
                                break;
                        }
                        if (object.getToString()[1].length() > 4) {
                            switch (object.getToString()[1].substring(object.getToString()[1].length() - 4)) {
                                case "Bots":
                                    players = new Player[strToNum(object.getToString()[1].substring(0, 1))];
                                    break;
                            }
                        }
                        break;
                }
                break;
            case "typed":
                if (selectedObject != null && selectedObject.getToString()[0] == "TextBox"
                        && (!Keyboard.lastKeyStr.toLowerCase().equals(Keyboard.lastKeyStr.toUpperCase())
                                || Keyboard.lastKeyNum == 32)) {
                    selectedObject.editToString(new String[] { null, null,
                            selectedObject.getToString()[2] + Keyboard.lastKeyStr.toUpperCase() });
                } else if (selectedObject != null && selectedObject.getToString()[0] == "TextBox"
                        && Keyboard.lastKeyNum == 8 && selectedObject.getToString()[2].length() > 0) {
                    selectedObject.editToString(new String[] { null, null, selectedObject.getToString()[2].substring(0,
                            selectedObject.getToString()[2].length() - 1) });
                }
                break;
        }
    }

    private int strToNum(String str) {
        int x = -1000;
        while (x < 1000 && !(x + "").equals(str)) {
            x++;
        }
        if (x == 1000)
            x = 0;
        return x;
    }
}
