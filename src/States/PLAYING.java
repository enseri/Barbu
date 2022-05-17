package States;

import java.util.ArrayList;
import java.awt.Graphics;
import Objects.Object;
import Objects.*;
import java.awt.Color;
import java.awt.Font;

public class PLAYING extends States {
    ArrayList<Object> objects = new ArrayList<>();
    int[] players = new int[4];
    private int mode = 0;
    private Object selectedObject;

    @Override
    public void render(Graphics g) {
        g.setColor(Color.magenta);
        if (objects.size() == 0) {
            objects.add(new Button("NEXT", 450, 450, 50, 50));
            switch (mode) {
                case 0:
                    objects.add(new Button("1 Players; 3 Bots", 200, 50, 100, 20));
                    objects.add(new Button("2 Players; 2 Bots", 200, 100, 100, 20));
                    objects.add(new Button("3 Players; 1 Bots", 200, 150, 100, 20));
                    objects.add(new Button("4 Players; 0 Bots", 200, 200, 100, 20));
                    selectedObject = objects.get(objects.size() - 1);
                    break;
                case 1:
                    
                    break;
                case 2:
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
                g.drawString("NEXT", 461, 475);
                for (int i = 0; i < objects.size(); i++) {
                    if (objects.get(i).getToString().length > 1 && !objects.get(i).getToString()[1].equals("NEXT")) {
                        int[] data = objects.get(i).getData();
                        g.setColor(Color.black);
                        g.fillRect(data[0], data[1], data[2], data[3]);
                        g.setColor(Color.red);
                        g.setFont(new Font(Font.SERIF, 10, 10));
                        g.drawString(objects.get(i).getToString()[1],
                                (int) (data[0] + data[2] - ((objects.get(i).getToString()[1].length() * 11) / 2)),
                                data[1] + (data[3] / 2));
                        if (selectedObject != null && selectedObject == objects.get(i)) {
                            g.setColor(Color.green);
                            g.drawRect(data[0], data[1], data[2], data[3]);
                            g.drawRect(data[0] - 1, data[1] - 1, data[2] + 2, data[3] + 2);
                            g.drawRect(data[0] - 2, data[1] - 2, data[2] + 4, data[3] + 4);
                        }
                    }

                }
                break;
            case 2:
                g.setColor(Color.black);
                g.fillRect(450, 450, 50, 50);
                g.setColor(Color.red);
                g.drawString("NEXT", 461, 475);
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

    public void interactionWithObject(Object object) {
        System.out.println(object.getToString()[1]);
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
                            players = new int[strToNum(object.getToString()[1].substring(0, 1))];
                            break;
                    }
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
