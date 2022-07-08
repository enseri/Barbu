package Objects;

import java.sql.Array;
import java.util.ArrayList;

public class Bot extends Object{
    public ArrayList<ArrayList<ArrayList<Integer>>> brainConnections = new ArrayList<>();
    public ArrayList<Card> cards = new ArrayList<>();
    public ArrayList<ArrayList<Card>> plis = new ArrayList<>();
    public int score = 0;
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

    public void generateBrain() {
        /*
        * 0 Input
        * 1 Impulse Per Data
        * 2 Move
        * 3 Impulse Required
         */
        while((int) (Math.random() * 50) != 0) {
            brainConnections.add(new ArrayList<>());
            int index = brainConnections.size() - 1;
            brainConnections.get(index).add(new ArrayList<>());
            brainConnections.get(index).add(new ArrayList<>());
            brainConnections.get(index).add(new ArrayList<>());
            brainConnections.get(index).add(new ArrayList<>());
            while((int) (Math.random() * 10) != 0) {
                brainConnections.get(index).get(0).add((int) (Math.random() * 32));
                brainConnections.get(index).get(1).add((int) (Math.random() * 10) + 1);
            }
            while((int) (Math.random() * 10) != 0) {
                brainConnections.get(index).get(2).add((int) (Math.random() * 13));
                brainConnections.get(index).get(3).add((int) (Math.random() * 8) + 1);
            }
        }
    }
}
