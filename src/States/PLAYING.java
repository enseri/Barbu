package States;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import javax.swing.*;

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
    private ArrayList<Object> objects = new ArrayList<>(); // Storage Of Objects
    private ArrayList<Card> playedCards = new ArrayList<>(); // Storage Of Played Cards
    private Player[] players = new Player[4]; // Storage Of Players
    private Bot[] bots = new Bot[0]; // Storage Of Bots
    private int mode = 0, currentPlayer = 0, starter = 0, gamesPlayed = 0, generation = 0; // Data For Playing
    private long generationStart = 0;
    private Distribute distribute = new Distribute(); // Animation
    private boolean showCards = false, showPlis = false, hasPlayed = false, whiteScreen = false, autoSkip = false, infiniteGames = false;
    private String rule = "Pas De Barbu"; // Rules
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
                objects.add(new Button("NEXT", 450, 450, 50, 50)); // Universal Next Button Creation
            switch (mode) {
                case 0: // Player To Bot Ratio Select Screen Button Creation
                    objects.add(new Button("1 Players; 3 Bots", 100, 50, 300, 50));
                    objects.add(new Button("2 Players; 2 Bots", 100, 150, 300, 50));
                    objects.add(new Button("3 Players; 1 Bots", 100, 250, 300, 50));
                    objects.add(new Button("4 Players; 0 Bots", 100, 350, 300, 50));
                    selectedObject = objects.get(objects.size() - 1);
                    break;
                case 1: // Text Box Creation
                    for (int i = 0; i < players.length; i++) {
                        objects.add(new TextBox("Player " + (i + 1) + ": ", 100, 50 + (i * 100), 300, 50));
                    }
                    break;
                case 2: // During Animation Scene Randomize Cards And Assign To Players Animation Is Useless
                    ArrayList<ArrayList<Card>> assortedList = Card.genRandCards();
                    for (int i = 0; i < players.length; i++) {
                        players[i].plis.clear();
                        players[i].cards = assortedList.get(i);
                    }
                    for (int i = 0; i < bots.length; i++) {
                        if (bots[i] == null) {
                            bots[i] = new Bot(null, i + "");
                            bots[i].readBrain();
                        }
                        bots[i].plis.clear();
                        bots[i].cards = assortedList.get(i + players.length);
                    }
                    break;
                case 3: // Rule Selected Screen Buttons
                    objects.add(new Button("Pas De Barbu", 50, 50, 100, 150));
                    objects.add(new Button("Pas De Coeur", 200, 50, 100, 150));
                    objects.add(new Button("Pas De Reine", 350, 50, 100, 150));
                    objects.add(new Button("Pas De Plis", 125, 250, 100, 150));
                    objects.add(new Button("Ratatouille", 275, 250, 100, 150)); // when player is last currentplayer is off
                    if (currentPlayer < players.length)
                        selectedObject = objects.get(1);
                    else { // Allow Bots To Pick Rules
                        selectedObject = objects.get(bots[currentPlayer - players.length].pickRule() + 1);
                        rule = selectedObject.getToString()[1];
                    }
                    objects.add(new Button("Show Cards", 0, 450, 100, 50));
                    break;
                case 4:
                    if (currentPlayer < players.length) { // Create Objects For All Cards
                        objects.add(new Button("Show Plis", 400, 0, 100, 50));
                        objects.add(new Button("NEXT", 0, 0, 50, 50));
                        for (int i = 0; i < players[currentPlayer].cards.size(); i++) {
                            players[currentPlayer].cards.get(i).editData(new int[]{0 + (38 * i), 350, 38, 150});
                            objects.add(players[currentPlayer].cards.get(i));
                        }
                    }
                    break;
                case 5:
                    gamesPlayed++;
                    break;
            }
        }
        switch (mode) {
            case 0:
                g.setColor(Color.black);
                g.fillRect(450, 450, 50, 50);
                g.setColor(Color.red);
                Game.drawCenteredString(g, "NEXT", 450, 450, 50, 50, new Font(Font.SERIF, 10, 10));
                for (int i = 0; i < objects.size(); i++) { // Display Boxes For Player To Bot Ratio Picking
                    if (objects.get(i).getToString().length > 1 && !objects.get(i).getToString()[1].equals("NEXT")) {
                        int[] data = objects.get(i).getData();
                        g.setColor(Color.black);
                        g.fillRect(data[0], data[1], data[2], data[3]);
                        g.setColor(Color.red);
                        Game.drawCenteredString(g, objects.get(i).getToString()[1], data[0], data[1], data[2], data[3],
                                new Font(Font.SERIF, 25, 25));
                        if (selectedObject != null && selectedObject == objects.get(i)) { // Display Border For Selected Option
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
                for (int i = 0; i < players.length; i++) { // For Each Player Display Name Box
                    g.fillRect(100, 50 + (i * 100), 300, 50);
                }
                g.setColor(Color.red);
                for (int i = 0; i < objects.size(); i++) { // For Each Player Display Name Box Changes
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
                if (autoSkip) // For AI TESTING NOT REACHABLE
                    mode++;
                else {
                    distribute.animate(g); // Animation For Card Distributing Literally Completely Useless
                    g.setColor(Color.black);
                    g.fillRect(450, 450, 50, 50);
                    g.setColor(Color.red);
                    Game.drawCenteredString(g, "NEXT", 450, 450, 50, 50, new Font(Font.SERIF, 10, 10));
                }
                break;
            case 3:
                if (players.length == 0) { // For AI TESTING NOT REACHABLE
                    mode++;
                } else { // Display Player Picking Rule
                    g.setColor(Color.black);
                    g.fillRect(450, 450, 50, 50);
                    g.fillRect(0, 450, 100, 50);
                    g.setColor(Color.magenta);
                    if (currentPlayer < players.length)
                        Game.drawCenteredString(g, players[currentPlayer].getToString()[1] + " Choosing Rule", 200, 0, 100, 20,
                                new Font(Font.SERIF, 25, 25));
                    else
                        Game.drawCenteredString(g, "Bot " + bots[currentPlayer - players.length].getToString()[1] + " Choosing Rule", 200, 0, 100, 20,
                                new Font(Font.SERIF, 25, 25));
                    g.setColor(Color.red);
                    Game.drawCenteredString(g, "NEXT", 450, 450, 50, 50, new Font(Font.SERIF, 10, 10));
                    for (int i = 1; i < 6; i++) { // Display All Rule Options With Selected Rule Bordered
                        int[] data = objects.get(i).getData();
                        g.setColor(Color.red);
                        g.fillRect(data[0], data[1], data[2], data[3]);
                        g.drawImage(new ImageIcon("src/Images/" + objects.get(i).getToString()[1] + ".png").getImage(),
                                data[0], data[1], data[2], data[3], null);
                        if (objects.get(i) == selectedObject || objects.get(i).getToString()[1].equals(rule)) {
                            rule = objects.get(i).getToString()[1];
                            g.setColor(Color.green);
                            g.drawRect(data[0] - 1, data[1] - 1, data[2] + 2, data[3] + 2);
                            g.drawRect(data[0] - 2, data[1] - 2, data[2] + 4, data[3] + 4);
                            g.drawRect(data[0] - 3, data[1] - 3, data[2] + 6, data[3] + 6);
                        }
                    }
                    if (showCards) { // Allow For Card Showing During Rule Picking
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
                }
                break;
            case 4:
                if (currentPlayer >= players.length) {  // For AI TESTING NOT REACHABLE
                    hasPlayed = true;
                    interactionWithObject(new Button("NEXT", 0, 0, 0, 0), "clicked");
                }
                if (currentPlayer < players.length) { //Display Player Playing
                    Game.drawCenteredString(g, players[currentPlayer].getToString()[1] + "'s Turn", 200, 0, 100, 20,
                            new Font(Font.SERIF, 25, 25));
                    if (!whiteScreen && !showPlis) { // During Game Show Cards And Button
                        g.setColor(Color.black);
                        g.fillRect(400, 0, 100, 50);
                        g.fillRect(0, 0, 50, 50);
                        g.setColor(Color.red);
                        Game.drawCenteredString(g, "Show Plis", 400, 0, 100, 50, new Font(Font.SERIF, 20, 20));
                        Game.drawCenteredString(g, "NEXT", 0, 0, 50, 50, new Font(Font.SERIF, 10, 10));
                        for (int i = 0; i < Card.plis.size(); i++) { // For All Cards In Plis Display Card's Image
                            g.drawImage(
                                    new ImageIcon("src/Images/" + Card.plis.get(i).getToString()[1]
                                            + Card.plis.get(i).getToString()[2] + ".png").getImage(),
                                    200 + (i * 50), 175, 100, 150, null);
                        }
                        for (int i = 0; i < players[currentPlayer].cards.size(); i++) { // For All Cards In Deck Display Card's Image
                            g.drawImage(
                                    new ImageIcon("src/Images/" + players[currentPlayer].cards.get(i).getToString()[1]
                                            + players[currentPlayer].cards.get(i).getToString()[2] + ".png").getImage(),
                                    0 + (38 * i), 350, 100, 150, null);
                        }
                    } else if (!showPlis) { // Allow Player To Pick Next If Not Showing Plis For Anti-Accidental Click
                        g.setColor(Color.black);
                        g.fillRect(0, 0, 50, 50);
                        g.setColor(Color.red);
                        Game.drawCenteredString(g, "NEXT", 0, 0, 50, 50, new Font(Font.SERIF, 10, 10));
                        Game.drawCenteredString(g, "PASSING TO NEXT PLAYER...", 0, 0, 500, 500,
                                new Font(Font.SERIF, 25, 25));
                    }
                    if (showPlis) {
                        for (int i = 0; i < players[currentPlayer].plis.size(); i++) { // For Each Card In Each Plis In Plis display Card's Image
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
                }
                break;
            case 5: // LeaderBoard
                playedCards.clear();
                if (autoSkip) { // For AI TESTING NOT REACHABLE
                    if(gamesPlayed < 5 || infiniteGames) {
                        if (starter < 3)
                            starter++;
                        else
                            starter = 0;
                        currentPlayer = starter;
                        objects.clear();
                        mode = 2;
                    } else{
                        generation++;
                        System.out.println("Generation: " + generation + "||| Time Taken: " + (System.currentTimeMillis() - generationStart));
                        generationStart = System.currentTimeMillis();
                        int bestScore = 0;
                        for(int i = 0; i < bots.length; i++) {
                            if(bots[i].score > bots[bestScore].score)
                                bestScore = i;
                        }
                        for(int i = 0; i < bots.length; i++) {
                            bots[i].score = 0;
                            if(i != bestScore) {
                                bots[i].mutate(bots[bestScore]);
                            }
                        }
                        boolean crtdRandom = false;
                        while(!crtdRandom) {
                            int temp = (int) (Math.random() * 4);
                            if(temp != bestScore) {
                                bots[temp].generateBrain();
                                crtdRandom = true;
                            }
                        }
                        gamesPlayed = 0;
                        starter = 0;
                        currentPlayer = starter;
                        objects.clear();
                        mode = 2;
                    }
                } else {
                    ArrayList<Integer> scores = new ArrayList<>(); // Add Scores To Big Array For Sorting
                    for (Player temp : players)
                        scores.add(temp.score);
                    for (Bot temp : bots)
                        scores.add(temp.score);
                    boolean isSorted = false;
                    while (!isSorted) { // Sort Scores
                        for (int i = 0; i < scores.size(); i++) {
                            for (int b = 0; b < scores.size(); b++) {
                                if (scores.get(i) > scores.get(b)) {
                                    int temp = scores.get(i);
                                    scores.set(i, scores.get(b));
                                    scores.set(b, temp);
                                }
                            }
                        }
                        isSorted = true; // Check If Sorted
                        for (int i = 0; i < scores.size(); i++) {
                            if (i > 0)
                                if (scores.get(i) > scores.get(i - 1))
                                    isSorted = false;
                        }
                    } // Display Boxes
                    g.setColor(Color.gray);
                    g.fillRect(50, 0, 400, 500);
                    g.setColor(Color.black);
                    Game.drawCenteredString(g, "Games Played: " + gamesPlayed + " Generation: " + generation, 0, 0, 500, 50, new Font(Font.SERIF, 20, 20));
                    g.setColor(Color.red);
                    for (Player temp : players) { // For Every Player Display Box And Color Based On Score Position
                        if (temp.score == scores.get(3))
                            g.setColor(Color.red);
                        if (temp.score == scores.get(2))
                            g.setColor(new Color(200, 100, 0));
                        if (temp.score == scores.get(1))
                            g.setColor(Color.yellow);
                        if (temp.score == scores.get(0))
                            g.setColor(Color.green);
                        g.fillRect(50, 50 + (100 * (strToNum(temp.getToString()[2]) - 1)), 400, 50);
                        g.setColor(Color.black);
                        Game.drawCenteredString(g,
                                temp.getToString()[1] + ":                                                     "
                                        + temp.score,
                                50, 50 + (100 * (strToNum(temp.getToString()[2]) - 1)), 400, 50,
                                new Font(Font.SERIF, 20, 20)); // Name Algorithm
                    }
                    for (Bot temp : bots) { // For Every Bot Display Box And Color Based On Score Position
                        if (temp.score == scores.get(3))
                            g.setColor(Color.red);
                        if (temp.score == scores.get(2))
                            g.setColor(new Color(200, 100, 0));
                        if (temp.score == scores.get(1))
                            g.setColor(Color.yellow);
                        if (temp.score == scores.get(0))
                            g.setColor(Color.green);
                        g.fillRect(50, 50 + (100 * (strToNum(temp.getToString()[1]) + players.length)), 400, 50);
                        g.setColor(Color.black);
                        Game.drawCenteredString(g,
                                "Bot " + temp.getToString()[1] + ":                                                     "
                                        + temp.score,
                                50, 50 + (100 * (strToNum(temp.getToString()[1]) + players.length)), 400, 50,
                                new Font(Font.SERIF, 20, 20));
                    }
                    g.setColor(Color.black);
                    g.fillRect(450, 450, 50, 50);
                    g.setColor(Color.red);
                    Game.drawCenteredString(g, "NEXT", 450, 450, 50, 50, new Font(Font.SERIF, 10, 10));
                }
                break;
        }
    }

    public String toString() {
        return "PLAYING";
    }

    public ArrayList<Object> getObjects() { // Get Objects For Mouse
        return objects;
    }

    public void interactionWithObject(Object object, String action) {
        switch (action) {
            case "clicked":
                if (currentPlayer < players.length) // Select Rule If Player Pick Turn
                    selectedObject = object;
                switch (object.getToString()[0]) {
                    case "Button":
                        switch (object.getToString()[1]) {
                            case "NEXT":
                                if (!showCards && mode != 4 && mode != 5) { // Iterate Mode If Not In Special Modes
                                    mode++;
                                    selectedObject = null;
                                    objects.clear();
                                } else if (mode == 4 && (hasPlayed || whiteScreen) && !showPlis) { // Playing Mode
                                    if (!whiteScreen) { // If Not Hiding Player Cards
                                        whiteScreen = true;
                                        hasPlayed = false;
                                        boolean gameOverByBot = false; // Condition If Bot Ends Game
                                        if (currentPlayer < players.length - 1) // Move Forward In Players
                                            currentPlayer++;
                                        else { // Else If Current Player Is A Bot Play Bots
                                            currentPlayer++;
                                            while (currentPlayer >= players.length && !gameOverByBot) { // Loop Until Player's Turn Or Game End By Bot
                                                for (; currentPlayer < 4 && !gameOverByBot; currentPlayer++) { // Iterate Through All Bot Positions
                                                    Bot tempB = bots[currentPlayer - players.length];
                                                    boolean validCard = false;
                                                    int botInd = tempB.play() - 1;
                                                    Card botPlayed = null;
                                                    if (botInd < tempB.cards.size())
                                                        botPlayed = tempB.cards.get(botInd);
                                                    if (botPlayed != null) { // If Bot Didnt Play Impossible Card Check Validity ALL EXPLAINED FARTHER BELOW
                                                        if (Card.plis.size() == 0)
                                                            validCard = true;
                                                        else {
                                                            if (botPlayed.getToString()[1]
                                                                    .equals(Card.plis.get(0).getToString()[1]))
                                                                validCard = true;
                                                            boolean containsType = false;
                                                            for (int i = 0; i < bots[currentPlayer - players.length].cards.size() && !containsType
                                                                    && !validCard; i++) {
                                                                if (bots[currentPlayer - players.length].cards.get(i).getToString()[1]
                                                                        .equals(Card.plis.get(0).getToString()[1]))
                                                                    containsType = true;
                                                            }
                                                            if (!validCard && !containsType)
                                                                validCard = true;
                                                        }
                                                    }
                                                    if (validCard) {
                                                        playedCards.add(botPlayed);
                                                        Card.plis.add(botPlayed);
                                                        tempB.cards.remove(botPlayed);
                                                    } else { // If Card Still Not valid Meaning Bot Played Incorrect Card Pick A Random Correct Card
                                                        System.out.println("random");
                                                        if(players.length == 0)
                                                            bots[currentPlayer].score -= 1000;
                                                        while (!validCard) {
                                                            if (bots[currentPlayer - players.length].cards.size() != 0)
                                                                botPlayed = bots[currentPlayer - players.length].cards.get((int) (Math.random() * bots[currentPlayer - players.length].cards.size()));
                                                            else {
                                                                gameOverByBot = true;
                                                                break;
                                                            }
                                                            if (Card.plis.size() == 0) {
                                                                validCard = true;
                                                            } else {
                                                                if (botPlayed.getToString()[1]
                                                                        .equals(Card.plis.get(0).getToString()[1]))
                                                                    validCard = true;
                                                                boolean containsType = false;
                                                                for (int i = 0; i < bots[currentPlayer - players.length].cards.size() && !containsType
                                                                        && !validCard; i++) {
                                                                    if (bots[currentPlayer - players.length].cards.get(i).getToString()[1]
                                                                            .equals(Card.plis.get(0).getToString()[1]))
                                                                        containsType = true;
                                                                }
                                                                if (!validCard && !containsType)
                                                                    validCard = true;
                                                            }
                                                        }
                                                        if (validCard) {
                                                            playedCards.add(botPlayed);
                                                            Card.plis.add(botPlayed);
                                                            tempB.cards.remove(botPlayed);
                                                        }
                                                    }
                                                    if (Card.plis.size() == 4) {
                                                        boolean containsBarbu = false;
                                                        if (rule.equals("Pas De Barbu")) {
                                                            for (Card temp : Card.plis)
                                                                if ((temp.getToString()[1] + temp.getToString()[2]).equals("HeartKing"))
                                                                    containsBarbu = true;
                                                        }
                                                        int highestCard = 0;
                                                        for (Card tempC : Card.plis) {
                                                            if (compareCards(tempC, Card.plis.get(highestCard)) > 0 && Card.plis.get(0).getToString()[1].equals(tempC.getToString()[1]))
                                                                highestCard = Card.plis.indexOf(tempC);
                                                        }
                                                        currentPlayer = ((currentPlayer + highestCard + 1) % 4);
                                                        if (currentPlayer < players.length)
                                                            players[currentPlayer].plis.add((ArrayList<Card>) Card.plis.clone());
                                                        else
                                                            bots[currentPlayer - players.length].plis.add((ArrayList<Card>) Card.plis.clone());
                                                        Card.plis.clear();
                                                        if ((currentPlayer < players.length && players[currentPlayer].cards.size() == 0) || (currentPlayer >= players.length && bots[currentPlayer - players.length].cards.size() == 0) || containsBarbu) {
                                                            updateAllScores();
                                                            hasPlayed = false;
                                                            whiteScreen = false;
                                                            objects.clear();
                                                            mode++;
                                                            gameOverByBot = true;
                                                        }
                                                        break;
                                                    }
                                                }
                                                if (currentPlayer == 4 && !gameOverByBot) // Rollback
                                                    currentPlayer = 0;
                                            }
                                        }
                                        if (!gameOverByBot) { // If Bot Has Not Ended Game Go Back To Player UI
                                            objects.clear();
                                            objects.add(new Button("Show Plis", 400, 0, 100, 50));
                                            objects.add(new Button("NEXT", 0, 0, 50, 50));
                                            for (int i = 0; i < players[currentPlayer].cards.size(); i++) {
                                                players[currentPlayer].cards.get(i)
                                                        .editData(new int[]{0 + (38 * i), 350, 38, 150});
                                                objects.add(players[currentPlayer].cards.get(i));
                                            }
                                        }
                                    } else {
                                        whiteScreen = false;
                                    }
                                } else if (mode == 5) { // LeaderBoard Mode
                                    if (starter < 3) // Who Picks Rule
                                        starter++;
                                    else
                                        starter = 0;
                                    currentPlayer = starter;
                                    objects.clear();
                                    mode = 2;
                                }
                                break;
                            case "Show Cards": // Show Players Card On Screen During Rule Picking
                                if (showCards)
                                    showCards = false;
                                else
                                    showCards = true;
                                break;
                            case "Show Plis": // Show Players Taken Plis
                                if (showPlis)
                                    showPlis = false;
                                else
                                    showPlis = true;
                                break;
                        }
                        if (object.getToString()[1].length() > 4) { // Change Number Of Players To Bots
                            switch (object.getToString()[1].substring(object.getToString()[1].length() - 4)) {
                                case "Bots":
                                    players = new Player[strToNum(object.getToString()[1].substring(0, 1))];
                                    bots = new Bot[4 - players.length];
                                    break;
                            }
                        }
                        break;
                    case "Card":
                        if (!hasPlayed && Card.plis.size() < 4) { // Check If Player Has Played
                            boolean validCard = false;
                            if (Card.plis.size() > 0) { // If No cards Played No Need To Validate
                                if (((Card) object).getToString()[1]
                                        .equals(Card.plis.get(0).getToString()[1])) // Check If Same Type If So Valid
                                    validCard = true;
                                boolean containsType = false;
                                for (int i = 0; i < players[currentPlayer].cards.size() && !containsType
                                        && !validCard; i++) { // If Not Same Type Check If Same Type Available Else Valid Card
                                    if (players[currentPlayer].cards.get(i).getToString()[1]
                                            .equals(Card.plis.get(0).getToString()[1]))
                                        containsType = true;
                                }
                                if (!validCard && !containsType) // Explained
                                    validCard = true;
                            } else
                                validCard = true;
                            if (validCard) { // If Valid Add Card To Plis And Remove From Player
                                playedCards.add((Card) object);
                                Card.plis.add((Card) object);
                                objects.remove((Card) object);
                                int cardIndex = players[currentPlayer].cards.indexOf((Card) object);
                                players[currentPlayer].cards.remove((Card) object);
                                for (int i = 0; i < players[currentPlayer].cards.size(); i++) {
                                    if (i >= cardIndex)
                                        players[currentPlayer].cards.get(i).editData(new int[]{
                                                players[currentPlayer].cards.get(i).getData()[0] - 38, -1, -1, -1});
                                    if (i == players[currentPlayer].cards.size() - 1)
                                        players[currentPlayer].cards.get(i).editData(new int[]{-1, -1, 100, -1});
                                }
                                hasPlayed = true;
                            }
                        }
                        if (Card.plis.size() == 4) { // If Full Plis; Who Takes Plis
                            boolean containsBarbu = false;
                            if (rule.equals("Pas De Barbu")) { // Check If King Is In Deck
                                for (Card temp : Card.plis)
                                    if ((temp.getToString()[1] + temp.getToString()[2]).equals("HeartKing"))
                                        containsBarbu = true;
                            }
                            hasPlayed = false;
                            Card highest = Card.plis.get(0);
                            for (int i = 0; i < 4; i++) { // Find Highest Card
                                if (compareCards(Card.plis.get(i), highest) > 0
                                        && Card.plis.get(0).getToString()[1].equals(Card.plis.get(i).getToString()[1]))
                                    highest = Card.plis.get(i);
                            }
                            if ((currentPlayer + Card.plis.indexOf(highest) + 1) % 4 < players.length) // Add Plis To Player
                                players[((currentPlayer + Card.plis.indexOf(highest) + 1) % 4)].plis
                                        .add((ArrayList<Card>) Card.plis.clone());
                            else // Add Plis To Bot
                                bots[((currentPlayer + Card.plis.indexOf(highest) + 1) % 4) - players.length].plis
                                        .add((ArrayList<Card>) Card.plis.clone());
                            currentPlayer = ((currentPlayer + Card.plis.indexOf(highest)) % 4); // Move Current Player To Plis Taker
                            Card.plis.clear();
                            if (!containsBarbu) { // If No Early End
                                if (!whiteScreen) { // Reset UI
                                    whiteScreen = true;
                                    hasPlayed = false;
                                    if (currentPlayer < players.length - 1)
                                        currentPlayer++;
                                    else
                                        currentPlayer = 0;
                                    objects.clear();
                                    objects.add(new Button("Show Plis", 400, 0, 100, 50));
                                    objects.add(new Button("NEXT", 0, 0, 50, 50));
                                    for (int i = 0; i < players[currentPlayer].cards.size(); i++) { // Show Player Cards
                                        players[currentPlayer].cards.get(i)
                                                .editData(new int[]{0 + (38 * i), 350, 38, 150});
                                        objects.add(players[currentPlayer].cards.get(i));
                                    }
                                } else {
                                    whiteScreen = false;
                                }
                            }
                            if ((currentPlayer < players.length && players[currentPlayer].cards.size() == 0) || (currentPlayer > players.length && bots[currentPlayer - players.length].cards.size() == 0) || containsBarbu) { // Check All Game End Conditions
                                updateAllScores();
                                whiteScreen = false;
                                objects.clear();
                                mode++;
                                // Game Reset
                            }
                        }
                        break;
                }
                break;
            case "typed":
                if (selectedObject != null && selectedObject.getToString()[0] == "TextBox"
                        && (!Keyboard.lastKeyStr.toLowerCase().equals(Keyboard.lastKeyStr.toUpperCase())
                        || Keyboard.lastKeyNum == 32)) {
                    selectedObject.editToString(new String[]{null, null,
                            selectedObject.getToString()[2] + Keyboard.lastKeyStr.toUpperCase()});
                } else if (selectedObject != null && selectedObject.getToString()[0] == "TextBox"
                        && Keyboard.lastKeyNum == 8 && selectedObject.getToString()[2].length() > 0) {
                    selectedObject.editToString(new String[]{null, null, selectedObject.getToString()[2].substring(0,
                            selectedObject.getToString()[2].length() - 1)});
                } else {
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
//                        case "f":
//                            Game.zoomWidth += 5;
//                            Game.zoomHeight += 5;
//                            break;
//                        case "d":
//                            Game.zoomWidth -= 5;
//                            Game.zoomHeight -= 5;
//                            break;
//                        case "r":
//                            if (Game.currentNetwork + 1 < Game.bot.ends.size())
//                                Game.currentNetwork++;
//                            break;
//                        case "t":
//                            if (Game.currentNetwork > 0)
//                                Game.currentNetwork--;
//                            break;
//                        case "s":
//                            Game.showBrains = !Game.showBrains;
//                            break;
//                        case "g":
//                            Game.goCrazy = !Game.goCrazy;
//                            break;
//                        case "p":
//                            autoSkip = !autoSkip;
//                            break;
//                        case "m":
//                            System.out.println("----------------------------------");
//                            for (int i = 0; i < players.length; i++) {
//                                for (Card temp : players[i].cards) {
//                                    System.out.println(temp.getToString()[1] + ":" + temp.getToString()[2]);
//                                }
//                                System.out.println("----------------------------------");
//                            }
//                            for (int i = 0; i < bots.length; i++) {
//                                for (Card temp : bots[i].cards) {
//                                    System.out.println(temp.getToString()[1] + ":" + temp.getToString()[2]);
//                                }
//                                System.out.println("----------------------------------");
//                            }
//                            break;
//                        case "n":
//                            for (int i = 0; i < players.length; i++) {
//                                for (ArrayList<Card> tempA : players[i].plis) {
//                                    for (Card temp : tempA) {
//                                        System.out.println(temp.getToString()[1] + ":" + temp.getToString()[2]);
//                                    }
//                                    System.out.println("****************************** PLIS: " + players[i].plis.indexOf(tempA));
//                                }
//                                System.out.println("---------------------------------- PLAYER: " + players[i].getToString()[2]);
//                            }
//                            for (int i = 0; i < bots.length; i++) {
//                                for (ArrayList<Card> tempA : bots[i].plis) {
//                                    for (Card temp : tempA) {
//                                        System.out.println(temp.getToString()[1] + ":" + temp.getToString()[2]);
//                                    }
//                                    System.out.println("****************************** PLIS: " + bots[i].plis.indexOf(tempA));
//                                }
//                                System.out.println("---------------------------------- BOT: " + bots[i].getToString()[1]);
//                            }
//                            break;
//                        case "k":
//                            int bestScore = 0;
//                            for(int i = 0; i < bots.length; i++) {
//                                if(bots[i].score > bots[bestScore].score)
//                                    bestScore = i;
//                            }
//                            bots[bestScore].saveBrain();
//                            break;
//                        case "i":
//                            infiniteGames = !infiniteGames;
//                            break;
                    }
                }
                break;
        }
    }

    private int strToNum(String str) { // Used to turn String To Numbers For Easy Universal Number Storage
        int x = -1000;
        while (x < 1000 && !(x + "").equals(str)) {
            x++;
        }
        if (x == 1000)
            x = 0;
        return x;
    }

    private int compareCards(Card one, Card two) { // Calculate The Values Of All Cards Then Compare
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

    public void updateAllScores() {
        switch (rule) {
            case "Pas De Barbu":
                for (int i = 0; i < players.length; i++) { //Calculate Score For Players For Rules No Barbu
                    for (int a = 0; a < players[i].plis.size(); a++) {
                        for (int b = 0; b < players[i].plis.get(a).size(); b++) {
                            if ((players[i].plis.get(a).get(b).getToString()[1]
                                    + players[i].plis.get(a).get(b).getToString()[2])
                                    .equals("HeartKing"))
                                players[i].updateScore(-80);
                        }
                    }
                }
                for (int i = 0; i < bots.length; i++) { //Calculate Score For Bots For Rules No Barbu
                    for (int a = 0; a < bots[i].plis.size(); a++) {
                        for (int b = 0; b < bots[i].plis.get(a).size(); b++) {
                            if ((bots[i].plis.get(a).get(b).getToString()[1]
                                    + bots[i].plis.get(a).get(b).getToString()[2])
                                    .equals("HeartKing"))
                                bots[i].updateScore(-80);
                        }
                    }
                }
                break;
            case "Pas De Plis":
                for (int i = 0; i < players.length; i++) { //Calculate Score For Players For Rules No Plis
                    for (int a = 0; a < players[i].plis.size(); a++) {
                        players[i].updateScore(-40);
                    }
                }
                for (int i = 0; i < bots.length; i++) { //Calculate Score For Bots For Rules No Plis
                    for (int a = 0; a < bots[i].plis.size(); a++) {
                        bots[i].updateScore(-40);
                    }
                }
                break;
            case "Pas De Reine":
                for (int i = 0; i < players.length; i++) { //Calculate Score For Players For Rules No Queens
                    for (int a = 0; a < players[i].plis.size(); a++) {
                        for (int b = 0; b < players[i].plis.get(a).size(); b++) {
                            if ((players[i].plis.get(a).get(b).getToString()[2])
                                    .equals("Queen"))
                                players[i].updateScore(-20);
                        }
                    }
                }
                for (int i = 0; i < bots.length; i++) { //Calculate Score For Bots For Rules No Queens
                    for (int a = 0; a < bots[i].plis.size(); a++) {
                        for (int b = 0; b < bots[i].plis.get(a).size(); b++) {
                            if ((bots[i].plis.get(a).get(b).getToString()[2])
                                    .equals("Queen"))
                                bots[i].updateScore(-20);
                        }
                    }
                }
                break;
            case "Pas De Coeur":
                for (int i = 0; i < players.length; i++) { //Calculate Score For Players For Rules No Hearts
                    for (int a = 0; a < players[i].plis.size(); a++) {
                        for (int b = 0; b < players[i].plis.get(a).size(); b++) {
                            if ((players[i].plis.get(a).get(b).getToString()[1])
                                    .equals("Heart"))
                                players[i].updateScore(-10);
                        }
                    }
                }
                for (int i = 0; i < bots.length; i++) { //Calculate Score For Bots For Rules No Hearts
                    for (int a = 0; a < bots[i].plis.size(); a++) {
                        for (int b = 0; b < bots[i].plis.get(a).size(); b++) {
                            if ((bots[i].plis.get(a).get(b).getToString()[1])
                                    .equals("Heart"))
                                bots[i].updateScore(-10);
                        }
                    }
                }
                break;
            case "Ratatouille":
                for (int i = 0; i < players.length; i++) { //Calculate Score For Players For Rules No Barbu
                    for (int a = 0; a < players[i].plis.size(); a++) {
                        for (int b = 0; b < players[i].plis.get(a).size(); b++) {
                            if ((players[i].plis.get(a).get(b).getToString()[1]
                                    + players[i].plis.get(a).get(b).getToString()[2])
                                    .equals("HeartKing"))
                                players[i].updateScore(-80);
                        }
                    }
                }
                for (int i = 0; i < players.length; i++) { //Calculate Score For Players For Rules No Plis
                    for (int a = 0; a < players[i].plis.size(); a++) {
                        players[i].updateScore(-40);
                    }
                }
                for (int i = 0; i < players.length; i++) { //Calculate Score For Players For Rules No Queen
                    for (int a = 0; a < players[i].plis.size(); a++) {
                        for (int b = 0; b < players[i].plis.get(a).size(); b++) {
                            if ((players[i].plis.get(a).get(b).getToString()[2])
                                    .equals("Queen"))
                                players[i].updateScore(-20);
                        }
                    }
                }
                for (int i = 0; i < players.length; i++) { //Calculate Score For Players For Rules No Hearts
                    for (int a = 0; a < players[i].plis.size(); a++) {
                        for (int b = 0; b < players[i].plis.get(a).size(); b++) {
                            if ((players[i].plis.get(a).get(b).getToString()[1])
                                    .equals("Heart"))
                                players[i].updateScore(-10);
                        }
                    }
                }
                for (int i = 0; i < bots.length; i++) { //Calculate Score For Bots For Rules No Barbu
                    for (int a = 0; a < bots[i].plis.size(); a++) {
                        for (int b = 0; b < bots[i].plis.get(a).size(); b++) {
                            if ((bots[i].plis.get(a).get(b).getToString()[1]
                                    + bots[i].plis.get(a).get(b).getToString()[2])
                                    .equals("HeartKing"))
                                bots[i].updateScore(-80);
                        }
                    }
                }
                for (int i = 0; i < bots.length; i++) { //Calculate Score For Bots For Rules No Plis
                    for (int a = 0; a < bots[i].plis.size(); a++) {
                        bots[i].updateScore(-40);
                    }
                }
                for (int i = 0; i < bots.length; i++) { //Calculate Score For Bots For Rules No Queens
                    for (int a = 0; a < bots[i].plis.size(); a++) {
                        for (int b = 0; b < bots[i].plis.get(a).size(); b++) {
                            if ((bots[i].plis.get(a).get(b).getToString()[2])
                                    .equals("Queen"))
                                bots[i].updateScore(-20);
                        }
                    }
                }
                for (int i = 0; i < bots.length; i++) { //Calculate Score For Bots For Rules No Hearts
                    for (int a = 0; a < bots[i].plis.size(); a++) {
                        for (int b = 0; b < bots[i].plis.get(a).size(); b++) {
                            if ((bots[i].plis.get(a).get(b).getToString()[1])
                                    .equals("Heart"))
                                bots[i].updateScore(-10);
                        }
                    }
                }
                break;
        }
    }

    public int botRequest(int request) { // Return A Value For Each Question
        switch (request) {
            case 0: // Rule
                switch (rule) {
                    case "Pas De Barbu":
                        return 1;
                    case "Pas De Coeur":
                        return 2;
                    case "Pas De Reine":
                        return 3;
                    case "Pas De Plis":
                        return 4;
                    case "Pas De Ratatouille":
                        return 5;
                }
                break;
            case 1: // Num Cards Played
                return (13 - bots[currentPlayer - players.length].cards.size() * 4) + Card.plis.size();
            case 2: // Position
                return Card.plis.size() + 1;
            case 3: // Num Cards In Deck
                return bots[currentPlayer - players.length].cards.size() + 1;
            // Split ************************************************************
            case 4: // Card 1 In Deck Type
                if (bots[currentPlayer - players.length].cards.size() > 0)
                    return Card.getCardRank(bots[currentPlayer - players.length].cards.get(0));
                break;
            case 5: // Card 2 In Deck Type
                if (bots[currentPlayer - players.length].cards.size() > 1)
                    return Card.getCardRank(bots[currentPlayer - players.length].cards.get(1));
                break;
            case 6: // Card 3 In Deck Type
                if (bots[currentPlayer - players.length].cards.size() > 2)
                    return Card.getCardRank(bots[currentPlayer - players.length].cards.get(2));
                break;
            case 7: // Card 4 In Deck Type
                if (bots[currentPlayer - players.length].cards.size() > 3)
                    return Card.getCardRank(bots[currentPlayer - players.length].cards.get(3));
                break;
            case 8: // Card 5 In Deck Type
                if (bots[currentPlayer - players.length].cards.size() > 4)
                    return Card.getCardRank(bots[currentPlayer - players.length].cards.get(4));
                break;
            case 9: // Card 6 In Deck Type
                if (bots[currentPlayer - players.length].cards.size() > 5)
                    return Card.getCardRank(bots[currentPlayer - players.length].cards.get(5));
                break;
            case 10: // Card 7 In Deck Type
                if (bots[currentPlayer - players.length].cards.size() > 6)
                    return Card.getCardRank(bots[currentPlayer - players.length].cards.get(6));
                break;
            case 11: // Card 8 In Deck Type
                if (bots[currentPlayer - players.length].cards.size() > 7)
                    return Card.getCardRank(bots[currentPlayer - players.length].cards.get(7));
                break;
            case 12: // Card 9 In Deck Type
                if (bots[currentPlayer - players.length].cards.size() > 8)
                    return Card.getCardRank(bots[currentPlayer - players.length].cards.get(8));
                break;
            case 13: // Card 10 In Deck Type
                if (bots[currentPlayer - players.length].cards.size() > 9)
                    return Card.getCardRank(bots[currentPlayer - players.length].cards.get(9));
                break;
            case 14: // Card 11 In Deck Type
                if (bots[currentPlayer - players.length].cards.size() > 10)
                    return Card.getCardRank(bots[currentPlayer - players.length].cards.get(10));
                break;
            case 15: // Card 12 In Deck Type
                if (bots[currentPlayer - players.length].cards.size() > 11)
                    return Card.getCardRank(bots[currentPlayer - players.length].cards.get(11));
                break;
            case 16: // Card 13 In Deck Type
                if (bots[currentPlayer - players.length].cards.size() > 12)
                    return Card.getCardRank(bots[currentPlayer - players.length].cards.get(12));
                break;
            // Split ************************************************************
            case 17: // Card 1 In Deck Level
                if (bots[currentPlayer - players.length].cards.size() > 0)
                    return Card.getCardValue(bots[currentPlayer - players.length].cards.get(0));
                break;
            case 18: // Card 2 In Deck Level
                if (bots[currentPlayer - players.length].cards.size() > 1)
                    return Card.getCardValue(bots[currentPlayer - players.length].cards.get(1));
                break;
            case 19: // Card 3 In Deck Level
                if (bots[currentPlayer - players.length].cards.size() > 2)
                    return Card.getCardValue(bots[currentPlayer - players.length].cards.get(2));
                break;
            case 20: // Card 4 In Deck Level
                if (bots[currentPlayer - players.length].cards.size() > 3)
                    return Card.getCardValue(bots[currentPlayer - players.length].cards.get(3));
                break;
            case 21: // Card 5 In Deck Level
                if (bots[currentPlayer - players.length].cards.size() > 4)
                    return Card.getCardValue(bots[currentPlayer - players.length].cards.get(4));
                break;
            case 22: // Card 6 In Deck Level
                if (bots[currentPlayer - players.length].cards.size() > 5)
                    return Card.getCardValue(bots[currentPlayer - players.length].cards.get(5));
                break;
            case 23: // Card 7 In Deck Level
                if (bots[currentPlayer - players.length].cards.size() > 6)
                    return Card.getCardValue(bots[currentPlayer - players.length].cards.get(6));
                break;
            case 24: // Card 8 In Deck Level
                if (bots[currentPlayer - players.length].cards.size() > 7)
                    return Card.getCardValue(bots[currentPlayer - players.length].cards.get(7));
                break;
            case 25: // Card 9 In Deck Level
                if (bots[currentPlayer - players.length].cards.size() > 8)
                    return Card.getCardValue(bots[currentPlayer - players.length].cards.get(8));
                break;
            case 26: // Card 10 In Deck Level
                if (bots[currentPlayer - players.length].cards.size() > 9)
                    return Card.getCardValue(bots[currentPlayer - players.length].cards.get(9));
                break;
            case 27: // Card 11 In Deck Level
                if (bots[currentPlayer - players.length].cards.size() > 10)
                    return Card.getCardValue(bots[currentPlayer - players.length].cards.get(10));
                break;
            case 28: // Card 12 In Deck Level
                if (bots[currentPlayer - players.length].cards.size() > 11)
                    return Card.getCardValue(bots[currentPlayer - players.length].cards.get(11));
                break;
            case 29: // Card 13 In Deck Level
                if (bots[currentPlayer - players.length].cards.size() > 12)
                    return Card.getCardValue(bots[currentPlayer - players.length].cards.get(12));
                break;
            // Split ************************************************************
            case 30: // Card 1 In Played Cards Level
                if (playedCards.size() > 0)
                    return Card.getCardValue(playedCards.get(0));
                break;
            case 31: // Card 2 In Played Cards Level
                if (playedCards.size() > 1)
                    return Card.getCardValue(playedCards.get(1));
                break;
            case 32: // Card 3 In Played Cards Level
                if (playedCards.size() > 2)
                    return Card.getCardValue(playedCards.get(2));
                break;
            case 33: // Card 4 In Played Cards Level
                if (playedCards.size() > 3)
                    return Card.getCardValue(playedCards.get(3));
                break;
            case 34: // Card 5 In Played Cards Level
                if (playedCards.size() > 4)
                    return Card.getCardValue(playedCards.get(4));
                break;
            case 35: // Card 6 In Played Cards Level
                if (playedCards.size() > 5)
                    return Card.getCardValue(playedCards.get(5));
                break;
            case 36: // Card 7 In Played Cards Level
                if (playedCards.size() > 6)
                    return Card.getCardValue(playedCards.get(6));
                break;
            case 37: // Card 8 In Played Cards Level
                if (playedCards.size() > 7)
                    return Card.getCardValue(playedCards.get(7));
                break;
            case 38: // Card 9 In Played Cards Level
                if (playedCards.size() > 8)
                    return Card.getCardValue(playedCards.get(8));
                break;
            case 39: // Card 10 In Played Cards Level
                if (playedCards.size() > 9)
                    return Card.getCardValue(playedCards.get(9));
                break;
            case 40: // Card 11 In Played Cards Level
                if (playedCards.size() > 10)
                    return Card.getCardValue(playedCards.get(10));
                break;
            case 41: // Card 12 In Played Cards Level
                if (playedCards.size() > 11)
                    return Card.getCardValue(playedCards.get(11));
                break;
            case 42: // Card 13 In Played Cards Level
                if (playedCards.size() > 12)
                    return Card.getCardValue(playedCards.get(12));
                break;
            case 43: // Card 14 In Played Cards Level
                if (playedCards.size() > 13)
                    return Card.getCardValue(playedCards.get(13));
                break;
            case 44: // Card 15 In Played Cards Level
                if (playedCards.size() > 14)
                    return Card.getCardValue(playedCards.get(14));
                break;
            case 45: // Card 16 In Played Cards Level
                if (playedCards.size() > 15)
                    return Card.getCardValue(playedCards.get(15));
                break;
            case 46: // Card 17 In Played Cards Level
                if (playedCards.size() > 16)
                    return Card.getCardValue(playedCards.get(16));
                break;
            case 47: // Card 18 In Played Cards Level
                if (playedCards.size() > 17)
                    return Card.getCardValue(playedCards.get(17));
                break;
            case 48: // Card 19 In Played Cards Level
                if (playedCards.size() > 18)
                    return Card.getCardValue(playedCards.get(18));
                break;
            case 49: // Card 20 In Played Cards Level
                if (playedCards.size() > 19)
                    return Card.getCardValue(playedCards.get(19));
                break;
            case 50: // Card 21 In Played Cards Level
                if (playedCards.size() > 20)
                    return Card.getCardValue(playedCards.get(20));
                break;
            case 51: // Card 22 In Played Cards Level
                if (playedCards.size() > 21)
                    return Card.getCardValue(playedCards.get(21));
                break;
            case 52: // Card 23 In Played Cards Level
                if (playedCards.size() > 22)
                    return Card.getCardValue(playedCards.get(22));
                break;
            case 53: // Card 24 In Played Cards Level
                if (playedCards.size() > 23)
                    return Card.getCardValue(playedCards.get(23));
                break;
            case 54: // Card 25 In Played Cards Level
                if (playedCards.size() > 24)
                    return Card.getCardValue(playedCards.get(24));
                break;
            case 55: // Card 26 In Played Cards Level
                if (playedCards.size() > 25)
                    return Card.getCardValue(playedCards.get(25));
                break;
            case 56: // Card 27 In Played Cards Level
                if (playedCards.size() > 26)
                    return Card.getCardValue(playedCards.get(26));
                break;
            case 57: // Card 28 In Played Cards Level
                if (playedCards.size() > 27)
                    return Card.getCardValue(playedCards.get(27));
                break;
            case 58: // Card 29 In Played Cards Level
                if (playedCards.size() > 28)
                    return Card.getCardValue(playedCards.get(28));
                break;
            case 59: // Card 30 In Played Cards Level
                if (playedCards.size() > 29)
                    return Card.getCardValue(playedCards.get(29));
                break;
            case 60: // Card 31 In Played Cards Level
                if (playedCards.size() > 30)
                    return Card.getCardValue(playedCards.get(30));
                break;
            case 61: // Card 32 In Played Cards Level
                if (playedCards.size() > 31)
                    return Card.getCardValue(playedCards.get(31));
                break;
            case 62: // Card 33 In Played Cards Level
                if (playedCards.size() > 32)
                    return Card.getCardValue(playedCards.get(32));
                break;
            case 63: // Card 34 In Played Cards Level
                if (playedCards.size() > 33)
                    return Card.getCardValue(playedCards.get(33));
                break;
            case 64: // Card 35 In Played Cards Level
                if (playedCards.size() > 34)
                    return Card.getCardValue(playedCards.get(34));
                break;
            case 65: // Card 36 In Played Cards Level
                if (playedCards.size() > 35)
                    return Card.getCardValue(playedCards.get(35));
                break;
            case 66: // Card 37 In Played Cards Level
                if (playedCards.size() > 36)
                    return Card.getCardValue(playedCards.get(36));
                break;
            case 67: // Card 38 In Played Cards Level
                if (playedCards.size() > 37)
                    return Card.getCardValue(playedCards.get(37));
                break;
            case 68: // Card 39 In Played Cards Level
                if (playedCards.size() > 38)
                    return Card.getCardValue(playedCards.get(38));
                break;
            case 69: // Card 40 In Played Cards Level
                if (playedCards.size() > 39)
                    return Card.getCardValue(playedCards.get(39));
                break;
            case 70: // Card 41 In Played Cards Level
                if (playedCards.size() > 40)
                    return Card.getCardValue(playedCards.get(40));
                break;
            case 71: // Card 42 In Played Cards Level
                if (playedCards.size() > 41)
                    return Card.getCardValue(playedCards.get(41));
                break;
            case 72: // Card 43 In Played Cards Level
                if (playedCards.size() > 42)
                    return Card.getCardValue(playedCards.get(42));
                break;
            case 73: // Card 44 In Played Cards Level
                if (playedCards.size() > 43)
                    return Card.getCardValue(playedCards.get(43));
                break;
            case 74: // Card 45 In Played Cards Level
                if (playedCards.size() > 44)
                    return Card.getCardValue(playedCards.get(44));
                break;
            case 75: // Card 46 In Played Cards Level
                if (playedCards.size() > 45)
                    return Card.getCardValue(playedCards.get(45));
                break;
            case 76: // Card 47 In Played Cards Level
                if (playedCards.size() > 46)
                    return Card.getCardValue(playedCards.get(46));
                break;
            case 77: // Card 48 In Played Cards Level
                if (playedCards.size() > 47)
                    return Card.getCardValue(playedCards.get(47));
                break;
            case 78: // Card 49 In Played Cards Level
                if (playedCards.size() > 48)
                    return Card.getCardValue(playedCards.get(48));
                break;
            case 79: // Card 50 In Played Cards Level
                if (playedCards.size() > 49)
                    return Card.getCardValue(playedCards.get(49));
                break;
            case 80: // Card 51 In Played Cards Level
                if (playedCards.size() > 50)
                    return Card.getCardValue(playedCards.get(50));
                break;
            case 81: // Card 52 In Played Cards Level
                if (playedCards.size() > 51)
                    return Card.getCardValue(playedCards.get(51));
                break;
            // Split ************************************************************
            case 82: // Card 1 In Played Cards Type
                if (playedCards.size() > 0)
                    return Card.getCardRank(playedCards.get(0));
                break;
            case 83: // Card 2 In Played Cards Type
                if (playedCards.size() > 1)
                    return Card.getCardRank(playedCards.get(1));
                break;
            case 84: // Card 3 In Played Cards Type
                if (playedCards.size() > 2)
                    return Card.getCardRank(playedCards.get(2));
                break;
            case 85: // Card 4 In Played Cards Type
                if (playedCards.size() > 3)
                    return Card.getCardRank(playedCards.get(3));
                break;
            case 86: // Card 5 In Played Cards Type
                if (playedCards.size() > 4)
                    return Card.getCardRank(playedCards.get(4));
                break;
            case 87: // Card 6 In Played Cards Type
                if (playedCards.size() > 5)
                    return Card.getCardRank(playedCards.get(5));
                break;
            case 88: // Card 7 In Played Cards Type
                if (playedCards.size() > 6)
                    return Card.getCardRank(playedCards.get(6));
                break;
            case 89: // Card 8 In Played Cards Type
                if (playedCards.size() > 7)
                    return Card.getCardRank(playedCards.get(7));
                break;
            case 90: // Card 9 In Played Cards Type
                if (playedCards.size() > 8)
                    return Card.getCardRank(playedCards.get(8));
                break;
            case 91: // Card 10 In Played Cards Type
                if (playedCards.size() > 9)
                    return Card.getCardRank(playedCards.get(9));
                break;
            case 92: // Card 11 In Played Cards Type
                if (playedCards.size() > 10)
                    return Card.getCardRank(playedCards.get(10));
                break;
            case 93: // Card 12 In Played Cards Type
                if (playedCards.size() > 11)
                    return Card.getCardRank(playedCards.get(11));
                break;
            case 94: // Card 13 In Played Cards Type
                if (playedCards.size() > 12)
                    return Card.getCardRank(playedCards.get(12));
                break;
            case 95: // Card 14 In Played Cards Type
                if (playedCards.size() > 13)
                    return Card.getCardRank(playedCards.get(13));
                break;
            case 96: // Card 15 In Played Cards Type
                if (playedCards.size() > 14)
                    return Card.getCardRank(playedCards.get(14));
                break;
            case 97: // Card 16 In Played Cards Type
                if (playedCards.size() > 15)
                    return Card.getCardRank(playedCards.get(15));
                break;
            case 98: // Card 17 In Played Cards Type
                if (playedCards.size() > 16)
                    return Card.getCardRank(playedCards.get(16));
                break;
            case 99: // Card 18 In Played Cards Type
                if (playedCards.size() > 17)
                    return Card.getCardRank(playedCards.get(17));
                break;
            case 100: // Card 19 In Played Cards Type
                if (playedCards.size() > 18)
                    return Card.getCardRank(playedCards.get(18));
                break;
            case 101: // Card 20 In Played Cards Type
                if (playedCards.size() > 19)
                    return Card.getCardRank(playedCards.get(19));
                break;
            case 102: // Card 21 In Played Cards Type
                if (playedCards.size() > 20)
                    return Card.getCardRank(playedCards.get(20));
                break;
            case 103: // Card 22 In Played Cards Type
                if (playedCards.size() > 21)
                    return Card.getCardRank(playedCards.get(21));
                break;
            case 104: // Card 23 In Played Cards Type
                if (playedCards.size() > 22)
                    return Card.getCardRank(playedCards.get(22));
                break;
            case 105: // Card 24 In Played Cards Type
                if (playedCards.size() > 23)
                    return Card.getCardRank(playedCards.get(23));
                break;
            case 106: // Card 25 In Played Cards Type
                if (playedCards.size() > 24)
                    return Card.getCardRank(playedCards.get(24));
                break;
            case 107: // Card 26 In Played Cards Type
                if (playedCards.size() > 25)
                    return Card.getCardRank(playedCards.get(25));
                break;
            case 108: // Card 27 In Played Cards Type
                if (playedCards.size() > 26)
                    return Card.getCardRank(playedCards.get(26));
                break;
            case 109: // Card 28 In Played Cards Type
                if (playedCards.size() > 27)
                    return Card.getCardRank(playedCards.get(27));
                break;
            case 110: // Card 29 In Played Cards Type
                if (playedCards.size() > 28)
                    return Card.getCardRank(playedCards.get(28));
                break;
            case 111: // Card 30 In Played Cards Type
                if (playedCards.size() > 29)
                    return Card.getCardRank(playedCards.get(29));
                break;
            case 112: // Card 31 In Played Cards Type
                if (playedCards.size() > 30)
                    return Card.getCardRank(playedCards.get(30));
                break;
            case 113: // Card 32 In Played Cards Type
                if (playedCards.size() > 31)
                    return Card.getCardRank(playedCards.get(31));
                break;
            case 114: // Card 33 In Played Cards Type
                if (playedCards.size() > 32)
                    return Card.getCardRank(playedCards.get(32));
                break;
            case 115: // Card 34 In Played Cards Type
                if (playedCards.size() > 33)
                    return Card.getCardRank(playedCards.get(33));
                break;
            case 116: // Card 35 In Played Cards Type
                if (playedCards.size() > 34)
                    return Card.getCardRank(playedCards.get(34));
                break;
            case 117: // Card 36 In Played Cards Type
                if (playedCards.size() > 35)
                    return Card.getCardRank(playedCards.get(35));
                break;
            case 118: // Card 37 In Played Cards Type
                if (playedCards.size() > 36)
                    return Card.getCardRank(playedCards.get(36));
                break;
            case 119: // Card 38 In Played Cards Type
                if (playedCards.size() > 37)
                    return Card.getCardRank(playedCards.get(37));
                break;
            case 120: // Card 39 In Played Cards Type
                if (playedCards.size() > 38)
                    return Card.getCardRank(playedCards.get(38));
                break;
            case 121: // Card 40 In Played Cards Type
                if (playedCards.size() > 39)
                    return Card.getCardRank(playedCards.get(39));
                break;
            case 122: // Card 41 In Played Cards Type
                if (playedCards.size() > 40)
                    return Card.getCardRank(playedCards.get(40));
                break;
            case 123: // Card 42 In Played Cards Type
                if (playedCards.size() > 41)
                    return Card.getCardRank(playedCards.get(41));
                break;
            case 124: // Card 43 In Played Cards Type
                if (playedCards.size() > 42)
                    return Card.getCardRank(playedCards.get(42));
                break;
            case 125: // Card 44 In Played Cards Type
                if (playedCards.size() > 43)
                    return Card.getCardRank(playedCards.get(43));
                break;
            case 126: // Card 45 In Played Cards Type
                if (playedCards.size() > 44)
                    return Card.getCardRank(playedCards.get(44));
                break;
            case 127: // Card 46 In Played Cards Type
                if (playedCards.size() > 45)
                    return Card.getCardRank(playedCards.get(45));
                break;
            case 128: // Card 47 In Played Cards Type
                if (playedCards.size() > 46)
                    return Card.getCardRank(playedCards.get(46));
                break;
            case 129: // Card 48 In Played Cards Type
                if (playedCards.size() > 47)
                    return Card.getCardRank(playedCards.get(47));
                break;
            case 130: // Card 49 In Played Cards Type
                if (playedCards.size() > 48)
                    return Card.getCardRank(playedCards.get(48));
                break;
            case 131: // Card 50 In Played Cards Type
                if (playedCards.size() > 49)
                    return Card.getCardRank(playedCards.get(49));
                break;
            case 132: // Card 51 In Played Cards Type
                if (playedCards.size() > 50)
                    return Card.getCardRank(playedCards.get(50));
                break;
            case 133: // Card 52 In Played Cards Type
                if (playedCards.size() > 51)
                    return Card.getCardRank(playedCards.get(51));
                break;
            // Split ************************************************************
            case 134: // Card 1 In Plis Level
                if(Card.plis.size() > 0)
                    return Card.getCardValue(Card.plis.get(0));
                break;
            case 135: // Card 2 In Plis Level
                if(Card.plis.size() > 1)
                    return Card.getCardValue(Card.plis.get(1));
                break;
            case 136: // Card 3 In Plis Level
                if(Card.plis.size() > 2)
                    return Card.getCardValue(Card.plis.get(2));
                break;
            case 137: // Card 4 In Plis Level
                if(Card.plis.size() > 3)
                    return Card.getCardValue(Card.plis.get(3));
                // Split ************************************************************
            case 138: // Card 1 In Plis Type
                if(Card.plis.size() > 0)
                    return Card.getCardRank(Card.plis.get(0));
                break;
            case 139: // Card 2 In Plis Type
                if(Card.plis.size() > 1)
                    return Card.getCardRank(Card.plis.get(1));
                break;
            case 140: // Card 3 In Plis Type
                if(Card.plis.size() > 2)
                    return Card.getCardRank(Card.plis.get(2));
                break;
            case 141: // Card 4 In Plis Type
                if(Card.plis.size() > 3)
                    return Card.getCardRank(Card.plis.get(3));
                break;
        }
        return 0;
    }
}
