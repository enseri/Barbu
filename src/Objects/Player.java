package Objects;

import java.util.ArrayList;

public class Player extends Object{
    public ArrayList<Card> cards = new ArrayList<>();
    public ArrayList<ArrayList<Card>> plis = new ArrayList<>();
    public int score = 0;
    String name;
    String position;

    public Player(ArrayList<Card> cards, String name, String position) {
        this.cards = cards;
        this.name = name;
        this.position = position;
    }

    @Override
    public String[] getToString() {
        return new String[]{"Player", name, position};
    }

    @Override
    public int[] getData() {
        return new int[]{0, 0, 0, 0};
    }

    @Override
    public void editToString(String[] edits) {
        if (edits[1] != null)
            name = edits[1];
        if (edits[2] != null)
            position = edits[2];
    }

    @Override
    public void editData(int[] edits) {
        // TODO Auto-generated method stub
        
    }

    public void updateScore(int change) {
        score += change;
    }
}
