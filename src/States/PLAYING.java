package States;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import javax.swing.ImageIcon;

import java.awt.Graphics;
import java.io.File;

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
    private Bot[] bots = new Bot[0];
    private int mode = 0, currentPlayer = 0;
    private Distribute distribute = new Distribute();
    private boolean showCards = false, showPlis = false, hasPlayed = false, whiteScreen = false;
    private String rule = "Pas De Barbu";
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
            if (mode != 4)
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
                    ArrayList<ArrayList<Card>> assortedList = Card.genRandCards();
                    for (int i = 0; i < 4; i++) {
                        for (int a = 1; a <= 4; a++) {
                            if (players.length > i && players[i].getToString()[2].equals(a + ""))
                                players[i].cards = assortedList.get(a - 1);
                            if (bots.length > i && players[i].getToString()[1].equals(a + ""))
                                bots[i].cards = assortedList.get(a - 1);
                        }
                    }
                    break;
                case 3:
                    objects.add(new Button("Pas De Barbu", 50, 50, 100, 150));
                    selectedObject = objects.get(objects.size() - 1);
                    objects.add(new Button("Pas De Coeur", 200, 50, 100, 150));
                    objects.add(new Button("Pas De Reine", 350, 50, 100, 150));
                    objects.add(new Button("Pas De Plis", 125, 250, 100, 150));
                    objects.add(new Button("Ratatouille", 275, 250, 100, 150));
                    objects.add(new Button("Show Cards", 0, 450, 100, 50));
                    break;
                case 4:
                    objects.add(new Button("Show Plis", 400, 0, 100, 50));
                    objects.add(new Button("NEXT", 0, 0, 50, 50));
                    for (int i = 0; i < players[currentPlayer].cards.size(); i++) {
                        players[currentPlayer].cards.get(i).editData(new int[] { 0 + (38 * i), 350, 38, 150 });
                        objects.add(players[currentPlayer].cards.get(i));
                    }
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
                for (int i = 0; i < objects.size(); i++) {
                    Object tempSelectedObject = objects.get(i);
                    int[] data = tempSelectedObject.getData();
                    if (tempSelectedObject.getToString().length > 2) {
                        Game.drawCenteredString(g,
                                tempSelectedObject.getToString()[1] + tempSelectedObject.getToString()[2],
                                data[0],
                                data[1], data[2], data[3], new Font(Font.SERIF, 25, 25));
                        players[i - 1] = new Player(null, tempSelectedObject.getToString()[2], i + "");
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
                g.fillRect(0, 450, 100, 50);
                g.setColor(Color.magenta);
                Game.drawCenteredString(g, players[currentPlayer].getToString()[1] + " Choosing Rule", 200, 0, 100, 20,
                        new Font(Font.SERIF, 25, 25));
                g.setColor(Color.red);
                Game.drawCenteredString(g, "NEXT", 450, 450, 50, 50, new Font(Font.SERIF, 10, 10));
                for (int i = 1; i < 6; i++) {
                    int[] data = objects.get(i).getData();
                    g.setColor(Color.red);
                    g.fillRect(data[0], data[1], data[2], data[3]);
                    if (objects.get(i) == selectedObject || objects.get(i).getToString()[1].equals(rule)) {
                        rule = objects.get(i).getToString()[1];
                        g.setColor(Color.green);
                        g.drawRect(data[0] - 1, data[1] - 1, data[2] + 2, data[3] + 2);
                        g.drawRect(data[0] - 2, data[1] - 2, data[2] + 4, data[3] + 4);
                        g.drawRect(data[0] - 3, data[1] - 3, data[2] + 6, data[3] + 6);
                    }
                }
                if (showCards) {
                    g.setColor(new Color(255, 255, 255, 220));
                    g.fillRect(0, 0, 500, 500);
                    for (int i = 0; i < players[currentPlayer].cards.size(); i++) {
                        g.drawImage(
                                new ImageIcon("src/Images/" + players[currentPlayer].cards.get(i).getToString()[1]
                                        + players[currentPlayer].cards.get(i).getToString()[2] + ".png").getImage(),
                                0 + (38 * i), 350, 100, 150, null);
                    }
                }
                g.setColor(Color.black);
                g.fillRect(0, 450, 100, 50);
                g.setColor(Color.red);
                Game.drawCenteredString(g, "Show Cards", 0, 450, 100, 50, new Font(Font.SERIF, 15, 15));
                break;
            case 4:
                Game.drawCenteredString(g, players[currentPlayer].getToString()[1] + "'s Turn", 200, 0, 100, 20,
                        new Font(Font.SERIF, 25, 25));
                if (!whiteScreen && !showPlis) {
                    g.setColor(Color.black);
                    g.fillRect(400, 0, 100, 50);
                    g.fillRect(0, 0, 50, 50);
                    g.setColor(Color.red);
                    Game.drawCenteredString(g, "Show Plis", 400, 0, 100, 50, new Font(Font.SERIF, 20, 20));
                    Game.drawCenteredString(g, "NEXT", 0, 0, 50, 50, new Font(Font.SERIF, 10, 10));
                    for (int i = 0; i < Card.plis.size(); i++) {
                        g.drawImage(
                                new ImageIcon("src/Images/" + Card.plis.get(i).getToString()[1]
                                        + Card.plis.get(i).getToString()[2] + ".png").getImage(),
                                200 + (i * 50), 175, 100, 150, null);
                    }
                    for (int i = 0; i < players[currentPlayer].cards.size(); i++) {
                        g.drawImage(
                                new ImageIcon("src/Images/" + players[currentPlayer].cards.get(i).getToString()[1]
                                        + players[currentPlayer].cards.get(i).getToString()[2] + ".png").getImage(),
                                0 + (38 * i), 350, 100, 150, null);
                    }
                } else if (!showPlis) {
                    g.setColor(Color.black);
                    g.fillRect(0, 0, 50, 50);
                    g.setColor(Color.red);
                    Game.drawCenteredString(g, "NEXT", 0, 0, 50, 50, new Font(Font.SERIF, 10, 10));
                    Game.drawCenteredString(g, "PASSING TO NEXT PLAYER...", 0, 0, 500, 500,
                            new Font(Font.SERIF, 25, 25));
                }
                if (showPlis) {
                    for (int i = 0; i < players[currentPlayer].plis.size(); i++) {
                        g.setColor(Color.pink);
                        g.fillRect(((i % 4) * 125) - 5, ((int) (i / 4) * 150), 5, 150);
                        g.fillRect(((i % 4) * 125) - 5, ((int) (i / 4) * 150) - 5, 105, 5);
                        ArrayList<Card> plis = players[currentPlayer].plis.get(i);
                        for (int a = 0; a < plis.size(); a++) {
                            g.drawImage(
                                    new ImageIcon("src/Images/" + plis.get(a).getToString()[1]
                                            + plis.get(a).getToString()[2] + ".png").getImage(),
                                    ((i % 4) * 125) + (a * 25), 0 + ((int) (i / 4) * 150), 100, 150, null);
                        }
                    }
                    g.setColor(Color.black);
                    g.fillRect(400, 0, 100, 50);
                    g.setColor(Color.red);
                    Game.drawCenteredString(g, "Show Plis", 400, 0, 100, 50, new Font(Font.SERIF, 20, 20));
                }
                break;
            case 5:
                
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
        if (object != null && object.getToString().length > 1)
            System.out.println(object.getToString()[1]);
        switch (action) {
            case "clicked":
                selectedObject = object;
                switch (object.getToString()[0]) {
                    case "Button":
                        switch (object.getToString()[1]) {
                            case "NEXT":
                                if (!showCards && mode != 4) {
                                    mode++;
                                    selectedObject = null;
                                    objects.clear();
                                } else if (mode == 4 && (hasPlayed || whiteScreen) && !showPlis) {
                                    if (!whiteScreen) {
                                        whiteScreen = true;
                                        hasPlayed = false;
                                        if (currentPlayer < players.length - 1)
                                            currentPlayer++;
                                        else
                                            currentPlayer = 0;
                                        objects.clear();
                                        objects.add(new Button("Show Plis", 400, 0, 100, 50));
                                        objects.add(new Button("NEXT", 0, 0, 50, 50));
                                        for (int i = 0; i < players[currentPlayer].cards.size(); i++) {
                                            players[currentPlayer].cards.get(i)
                                                    .editData(new int[] { 0 + (38 * i), 350, 38, 150 });
                                            objects.add(players[currentPlayer].cards.get(i));
                                        }
                                    } else {
                                        whiteScreen = false;
                                    }
                                }
                                break;
                            case "Show Cards":
                                if (showCards)
                                    showCards = false;
                                else
                                    showCards = true;
                                break;
                            case "Show Plis":
                                if (showPlis)
                                    showPlis = false;
                                else
                                    showPlis = true;
                                break;
                        }
                        if (object.getToString()[1].length() > 4) {
                            switch (object.getToString()[1].substring(object.getToString()[1].length() - 4)) {
                                case "Bots":
                                    players = new Player[strToNum(object.getToString()[1].substring(0, 1))];
                                    bots = new Bot[4 - players.length];
                                    break;
                            }
                        }
                        break;
                    case "Card":
                        if (!hasPlayed && Card.plis.size() < 4) {
                            boolean validCard = false;
                            if (Card.plis.size() > 0) {
                                if (((Card) object).getToString()[1]
                                        .equals(Card.plis.get(0).getToString()[1]))
                                    validCard = true;
                                boolean containsType = false;
                                for (int i = 0; i < players[currentPlayer].cards.size() && !containsType
                                        && !validCard; i++) {
                                    if (players[currentPlayer].cards.get(i).getToString()[1]
                                            .equals(Card.plis.get(0).getToString()[1]))
                                        containsType = true;
                                }
                                if (!validCard && !containsType)
                                    validCard = true;
                            } else
                                validCard = true;
                            if (validCard) {
                                Card.plis.add((Card) object);
                                objects.remove((Card) object);
                                int cardIndex = players[currentPlayer].cards.indexOf((Card) object);
                                players[currentPlayer].cards.remove((Card) object);
                                for (int i = 0; i < players[currentPlayer].cards.size(); i++) {
                                    if (i >= cardIndex)
                                        players[currentPlayer].cards.get(i).editData(new int[] {
                                                players[currentPlayer].cards.get(i).getData()[0] - 38, -1, -1, -1 });
                                    if (i == players[currentPlayer].cards.size() - 1)
                                        players[currentPlayer].cards.get(i).editData(new int[] { -1, -1, 100, -1 });
                                }
                                hasPlayed = true;
                            }
                        }
                        if (Card.plis.size() == 4) {
                            hasPlayed = false;
                            Card highest = Card.plis.get(0);
                            for (int i = 0; i < 4; i++) {
                                if (compareCards(Card.plis.get(i), highest) > 0
                                        && Card.plis.get(0).getToString()[1].equals(Card.plis.get(i).getToString()[1]))
                                    highest = Card.plis.get(i);
                            }
                            players[(currentPlayer + Card.plis.indexOf(highest) + 1) % 4].plis
                                    .add((ArrayList<Card>) Card.plis.clone());
                            currentPlayer = (currentPlayer + Card.plis.indexOf(highest)) % 4;
                            Card.plis.clear();
                            if (!whiteScreen) {
                                whiteScreen = true;
                                hasPlayed = false;
                                if (currentPlayer < players.length - 1)
                                    currentPlayer++;
                                else
                                    currentPlayer = 0;
                                objects.clear();
                                objects.add(new Button("Show Plis", 400, 0, 100, 50));
                                objects.add(new Button("NEXT", 0, 0, 50, 50));
                                for (int i = 0; i < players[currentPlayer].cards.size(); i++) {
                                    players[currentPlayer].cards.get(i)
                                            .editData(new int[] { 0 + (38 * i), 350, 38, 150 });
                                    objects.add(players[currentPlayer].cards.get(i));
                                }
                            } else {
                                whiteScreen = false;
                            }
                            if (players[currentPlayer].cards.size() == 0) {
                                switch (rule) {
                                    case "Pas De Barbu":
                                        for (int i = 0; i < players.length; i++) {
                                            for (int a = 0; a < players[i].plis.size(); a++) {
                                                for (int b = 0; b < players[i].plis.get(a).size(); b++) {
                                                    if ((players[i].plis.get(a).get(b).getToString()[1]
                                                            + players[i].plis.get(a).get(b).getToString()[2])
                                                            .equals("HeartKing"))
                                                        players[i].updateScore(-80);
                                                }
                                            }
                                        }
                                        break;
                                    case "Pas De Plis":
                                        for (int i = 0; i < players.length; i++) {
                                            for (int a = 0; a < players[i].plis.size(); a++) {
                                                players[i].updateScore(-40);
                                            }
                                        }
                                        break;
                                    case "Pas De Reine":
                                        for (int i = 0; i < players.length; i++) {
                                            for (int a = 0; a < players[i].plis.size(); a++) {
                                                for (int b = 0; b < players[i].plis.get(a).size(); b++) {
                                                    if ((players[i].plis.get(a).get(b).getToString()[2])
                                                            .equals("Queen"))
                                                        players[i].updateScore(-20);
                                                }
                                            }
                                        }
                                        break;
                                    case "Pas De Coeur":
                                        for (int i = 0; i < players.length; i++) {
                                            for (int a = 0; a < players[i].plis.size(); a++) {
                                                for (int b = 0; b < players[i].plis.get(b).size(); b++) {
                                                    if ((players[i].plis.get(a).get(b).getToString()[1])
                                                            .equals("Heart"))
                                                        players[i].updateScore(-10);
                                                }
                                            }
                                        }
                                        break;
                                    case "Ratatouille":
                                        for (int i = 0; i < players.length; i++) {
                                            for (int a = 0; a < players[i].plis.size(); a++) {
                                                for (int b = 0; b < players[i].plis.get(a).size(); b++) {
                                                    if ((players[i].plis.get(a).get(b).getToString()[1]
                                                            + players[i].plis.get(a).get(b).getToString()[2])
                                                            .equals("HeartKing"))
                                                        players[i].updateScore(-80);
                                                }
                                            }
                                        }
                                        for (int i = 0; i < players.length; i++) {
                                            for (int a = 0; a < players[i].plis.size(); a++) {
                                                players[i].updateScore(-40);
                                            }
                                        }
                                        for (int i = 0; i < players.length; i++) {
                                            for (int a = 0; a < players[i].plis.size(); a++) {
                                                for (int b = 0; b < players[i].plis.get(a).size(); b++) {
                                                    if ((players[i].plis.get(a).get(b).getToString()[2])
                                                            .equals("Queen"))
                                                        players[i].updateScore(-20);
                                                }
                                            }
                                        }
                                        for (int i = 0; i < players.length; i++) {
                                            for (int a = 0; a < players[i].plis.size(); a++) {
                                                for (int b = 0; b < players[i].plis.get(a).size(); b++) {
                                                    if ((players[i].plis.get(a).get(b).getToString()[1])
                                                            .equals("Heart"))
                                                        players[i].updateScore(-10);
                                                }
                                            }
                                        }
                                        break;
                                }
                                mode++;
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

    private int compareCards(Card one, Card two) {
        int valueOne = 0, valueTwo = 0;
        switch (one.getToString()[2]) {
            case "Two":
                valueOne = 2;
                break;
            case "Three":
                valueOne = 3;
                break;
            case "Four":
                valueOne = 4;
                break;
            case "Five":
                valueOne = 5;
                break;
            case "Six":
                valueOne = 6;
                break;
            case "Seven":
                valueOne = 7;
                break;
            case "Eight":
                valueOne = 8;
                break;
            case "Nine":
                valueOne = 9;
                break;
            case "Ten":
                valueOne = 10;
                break;
            case "Jack":
                valueOne = 11;
                break;
            case "Queen":
                valueOne = 12;
                break;
            case "King":
                valueOne = 13;
                break;
            case "Ace":
                valueOne = 14;
                break;
        }
        switch (two.getToString()[2]) {
            case "Two":
                valueTwo = 2;
                break;
            case "Three":
                valueTwo = 3;
                break;
            case "Four":
                valueTwo = 4;
                break;
            case "Five":
                valueTwo = 5;
                break;
            case "Six":
                valueTwo = 6;
                break;
            case "Seven":
                valueTwo = 7;
                break;
            case "Eight":
                valueTwo = 8;
                break;
            case "Nine":
                valueTwo = 9;
                break;
            case "Ten":
                valueTwo = 10;
                break;
            case "Jack":
                valueTwo = 11;
                break;
            case "Queen":
                valueTwo = 12;
                break;
            case "King":
                valueTwo = 13;
                break;
            case "Ace":
                valueTwo = 14;
                break;
        }
        if (valueOne > valueTwo) {
            return 1;
        }
        if (valueOne < valueTwo) {
            return -1;
        }
        return 0;
    }
}
