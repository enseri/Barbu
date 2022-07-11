package Objects;

import java.sql.Array;
import java.util.ArrayList;

public class Bot extends Object{
    public ArrayList<Integer> ends = new ArrayList<>();
    public ArrayList<int[]> IToC = new ArrayList<>();
    public ArrayList<int[]> CToO = new ArrayList<>();
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
        //50
       int[] inputNodes = new int[(int) (Math.random() * 200) + 50];
       double[] combNodes = new double[(int) (Math.random() * 100) + 32];
       int[] outputNodes = new int[(int) (Math.random() * 200) + 50];
       int IPBN = inputNodes.length / 32, CPBN = combNodes.length / 32, OPBN = outputNodes.length / 32;
       for(int i = 0; i < inputNodes.length; i++) {
           inputNodes[i] = (int) (Math.random() * 137) + 1;
       }
        for(int i = 0; i < combNodes.length; i++) {
            combNodes[i] = (Math.random() * 2) + 1;
        }
        for(int i = 0; i < outputNodes.length; i++) {
            outputNodes[i] = (int) (Math.random() * 13) + 1;
        }
        int inputIndex = 0, combIndex = 0, outputIndex = 0;
       for(int i = 0; i < 32; i++) {
           int tempIndex = 0;
           for(int b = 0; b < CPBN; b++) {
               tempIndex = inputIndex;
               for (int a = 0; a < IPBN; a++) {
                   IToC.add(new int[]{tempIndex, combIndex});
                   tempIndex++;
               }
               combIndex++;
           }
           ends.add(tempIndex);
           inputIndex = tempIndex;
           tempIndex = 0;
           for(int b = 0; b < OPBN; b++) {
               tempIndex = combIndex;
               for (int a = 0; a < CPBN; a++) {
                   CToO.add(new int[]{tempIndex, outputIndex});
                   tempIndex++;
               }
               outputIndex++;
           }
           outputIndex = tempIndex;
       }
    }
}
