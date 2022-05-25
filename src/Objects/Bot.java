package Objects;

import java.util.ArrayList;

public class Bot extends Object{
    public ArrayList<Card> cards = new ArrayList<>();
    String position;

    public Bot(ArrayList<Card> cards, String position) {
        this.cards = cards;
        this.position = position;
    }

    @Override
    public String[] getToString() {
        return new String[]{"Bot", position};
    }

    @Override
    public int[] getData() {
        return new int[]{0, 0, 0, 0};
    }

    @Override
    public void editToString(String[] edits) {
        if (edits[1] != null)
            position = edits[1];
    }

    @Override
    public void editData(int[] edits) {
        // TODO Auto-generated method stub
        
    }
}
