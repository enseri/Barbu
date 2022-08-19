package Objects;

import States.State;
import States.PLAYING;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Array;
import java.util.ArrayList;
import java.util.Scanner;

public class Bot extends Object{
    private ArrayList<int[]> indexSave = new ArrayList<>();
    /*
    Actually proud of myself for finding this delay fix,
    Originally caluclating the brain to play it would take around 100 ms
    I found a way to cut it down to 100 once then 1 ms for every other time essentially speeding up the AI testing
    phase.
     */
    private ArrayList<double[]> inputNodes = new ArrayList<>();
    private ArrayList<int[]> combineNodes = new ArrayList<>();
    private ArrayList<double[]> outputNodes = new ArrayList<>();
    public ArrayList<double[]> IToC = new ArrayList<>();
    public ArrayList<double[]> CToO = new ArrayList<>();
    public ArrayList<int[]> ends = new ArrayList<>();
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

    public int play() {
        long start = System.currentTimeMillis();
        ArrayList<double[]> outputsAndPower = new ArrayList<>();
        ArrayList<double[]> combineAndPower = new ArrayList<>();
        for (double[] ITOC : IToC) {
            combineAndPower.add(new double[]{ITOC[2], State.currentState.botRequest((int) inputNodes.get((int) ITOC[0])[0]) * (ITOC[1] - 4)});
        }
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        start = System.currentTimeMillis();
        int tempIndex1 = 0, tempIndex2 = 0;
        if(indexSave.size() == 0) {
            for (double[] COMBINEANDPOWER : combineAndPower) {
                for (double[] CTOO : CToO) {
                    if (COMBINEANDPOWER[0] == CTOO[0]) {
                        indexSave.add(new int[]{tempIndex1, tempIndex2});
                        int temp = 1;
                        if (combineNodes.get((int) CTOO[0])[0] == 0)
                            temp = -1;
                        outputsAndPower.add(new double[]{outputNodes.get((int) CTOO[2])[0], outputNodes.get((int) CTOO[2])[1], COMBINEANDPOWER[1] * (CTOO[1] - 4)});
                    }
                    tempIndex2++;
                }
                tempIndex2 = 0;
                tempIndex1++;
            }
            //System.out.println("Long: " + (System.currentTimeMillis() - start));
        } else {
            for(int i = 0; i < indexSave.size(); i++) {
                double[] CTOO = CToO.get(indexSave.get(i)[1]);
                double[] COMBINEANDPOWER = combineAndPower.get(indexSave.get(i)[0]);
                int temp = 1;
                if (combineNodes.get((int) CTOO[0])[0] == 0)
                    temp = -1;
                outputsAndPower.add(new double[]{outputNodes.get((int) CTOO[2])[0], outputNodes.get((int) CTOO[2])[1], COMBINEANDPOWER[1] * (CTOO[1] - 4)});
            }
            //System.out.println("Short: " + (System.currentTimeMillis() - start));
        }
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        int[] strongestImpulseAndIndex = new int[2];
        for (int i = 0; i < outputsAndPower.size(); i++) {
            if (outputsAndPower.get(i)[2] > strongestImpulseAndIndex[0] && outputsAndPower.get(i)[2] > outputsAndPower.get(i)[1])
                strongestImpulseAndIndex = new int[]{(int) outputsAndPower.get(i)[2], i};
        }
        if (outputsAndPower.size() > 0)
            return (int) outputsAndPower.get(strongestImpulseAndIndex[1])[0];
        return 1;
    } // Trigger Brain To Play

    public int pickRule() {
        return (int) (Math.random() * 5);
    } // Trigger Brain To Pick Rule

    public void generateBrain() { // Create A Brain For Bot
        indexSave.clear();
        inputNodes.clear();
        combineNodes.clear();
        outputNodes.clear();
        IToC.clear();
        CToO.clear();
        ends.clear();
        /*
        NOTE: ADD ALL THE POTENTIAL INPUTS
        NOTE: TEMP INDICES ARE THE INDEX OF THE OBJECT BEFORE THE NEW NODE
        NOTE: GENERAL INDICES ARE THE INDEX OF THE FIRST OBJECT IN THE NEXT NODE
        NOTE: PERCENT IS THE CHANCE THAT A NODE WILL STOP ADDING THAT TYPE
         */
        int ISPercent = 20, CSPercent = 20, OSPercent = 20, NSPercent = 10;
        int inputIndex = 0, combineIndex = 0, outputIndex = 0;
        while((int) (Math.random() * NSPercent) != 0) { // Loop Until Brain Randomly Stops Making Nodes
            int tempIIndex = inputIndex, tempCIndex = combineIndex, tempOIndex = outputIndex;
            while((int) (Math.random() * ISPercent) != 0) { // Allow Brain To Loop Until By Chance Reached A Number Of Nodes
                inputIndex++;
                inputNodes.add(new double[]{(int) (Math.random() * 142)});
            }
            while((int) (Math.random() * CSPercent) != 0) {
                combineIndex++;
                combineNodes.add(new int[]{(int) (Math.random() * 2)});
            }
            while((int) (Math.random() * OSPercent) != 0) {
                outputIndex++;
                outputNodes.add(new double[]{(int) (Math.random() * 14) + 1, Math.random() * 100});
            }
            for(int I = tempIIndex + 1; I < inputIndex; I++) { // Create Connections Based On Counts
                for(int C = tempCIndex + 1; C < combineIndex; C++) {
                   IToC.add(new double[]{I, Math.random() * 9, C});
                }
            }
            for(int O = tempOIndex + 1; O < outputIndex; O++) {
                for(int C = tempCIndex + 1; C < combineIndex; C++) {
                    CToO.add(new double[]{C, Math.random() * 9, O});
                }
            }
            ends.add(new int[]{tempIIndex, tempCIndex, tempOIndex, inputIndex, combineIndex, outputIndex}); // Note Where Nodes End
        }
    }

    public void updateScore(int change) {
        score += change;
    }
    
    public void mutate(Bot parent) { // FOR AI TESTING NOT REACHABLE
        indexSave = (ArrayList<int[]>) parent.indexSave.clone();
        inputNodes = (ArrayList<double[]>) parent.inputNodes.clone();
        outputNodes = (ArrayList<double[]>) parent.outputNodes.clone();
        combineNodes = (ArrayList<int[]>) parent.combineNodes.clone();
        IToC = (ArrayList<double[]>) parent.IToC.clone();
        CToO = (ArrayList<double[]>) parent.CToO.clone();
        ends = (ArrayList<int[]>) parent.ends.clone();
        for(int i = 0; i < inputNodes.size(); i++) {
            if((int) (Math.random() * 5) == 0)
                inputNodes.set(i, (new double[]{(int) (Math.random() * 142)}));
        }
        for(int i = 0; i < outputNodes.size(); i++) {
            if((int) (Math.random() * 5) == 0)
                outputNodes.set(i, new double[]{(int) (Math.random() * 14) + 1, Math.random() * 100});
        }
        for(int i = 0; i < combineNodes.size(); i++) {
            if((int) (Math.random() * 5) == 0)
                combineNodes.set(i, new int[]{(int) (Math.random() * 2)});
        }
    }

    public void saveBrain(){
        try {
            PrintWriter pw = new PrintWriter(new File("src/Saves/Bot Brain"));
            for(int i = 0; i < indexSave.size(); i++) {
                pw.print(indexSave.get(i)[0] + "|" + indexSave.get(i)[1] + " ");
            }
            pw.println();
            for(int i = 0; i < inputNodes.size(); i++) {
                    pw.print(inputNodes.get(i)[0] + " ");
            }
            pw.println();
            for(int i = 0; i < outputNodes.size(); i++) {
                    pw.print(outputNodes.get(i)[0] + "|" + outputNodes.get(i)[1] + " ");
            }
            pw.println();
            for(int i = 0; i < combineNodes.size(); i++) {
                    pw.print(combineNodes.get(i)[0] + " ");
            }
            pw.println();
            for(int i = 0; i < IToC.size(); i++) {
                pw.print(IToC.get(i)[0] + "|" + IToC.get(i)[1] + "|" + IToC.get(i)[2] + " ");
            }
            pw.println();
            for(int i = 0; i < CToO.size(); i++) {
                pw.print(CToO.get(i)[0] + "|" + CToO.get(i)[1] + "|" + CToO.get(i)[2] + " ");
            }
            pw.println();
            for(int i = 0; i < ends.size(); i++) {
                pw.print(ends.get(i)[0] + "|" + ends.get(i)[1] + "|" + ends.get(i)[2] + "|" + ends.get(i)[3] + "|" + ends.get(i)[4] + "|" + ends.get(i)[5] + " ");
            }
            pw.println();
            pw.close();
        } catch (IOException E) {
            E.printStackTrace();
        }
    }
    /*
    Note: ADD SPACES
     */
    public void readBrain(){
        inputNodes.clear();
        combineNodes.clear();
        outputNodes.clear();
        IToC.clear();
        CToO.clear();
        ends.clear();
        try{
            Scanner s = new Scanner(new File("src/Saves/Bot Brain"));
            for(int z = 0; z < 7; z++) {
                String line = s.nextLine();
                String temp = "";
                int timesEntered = 0;
                switch(z) {
                    case 0:
                        for(int a = 0; a < line.length(); a++) {
                            temp += line.substring(a, a + 1);
                            if(line.substring(a, a + 1).equals("|")) {
                                temp = temp.substring(0, temp.length() - 1);
                                indexSave.add(new int[]{strToNum(temp), 0});
                                temp = "";
                            } else if(line.substring(a, a + 1).equals(" ")) {
                                temp = temp.substring(0, temp.length() - 1);
                                indexSave.get(indexSave.size() - 1)[1] = strToNum(temp);
                                temp = "";
                            }
                        }
                        break;
                    case 1:
                        for(int a = 0; a < line.length(); a++) {
                            temp += line.substring(a, a + 1);
                            if(line.substring(a, a + 1).equals(" ")) {
                                temp = temp.substring(0, temp.length() - 1);
                                inputNodes.add(new double[]{strToDB(temp)});
                                temp = "";
                            }
                        }
                        break;
                    case 2:
                        for(int a = 0; a < line.length(); a++) {
                            temp += line.substring(a, a + 1);
                            if(line.substring(a, a + 1).equals("|")) {
                                temp = temp.substring(0, temp.length() - 1);
                                outputNodes.add(new double[]{strToDB(temp), 0});
                                temp = "";
                            } else if (line.substring(a, a + 1).equals(" ")) {
                                temp = temp.substring(0, temp.length() - 1);
                                outputNodes.get(outputNodes.size() - 1)[1] = strToDB(temp);
                                temp = "";
                            }
                        }
                        break;
                    case 3:
                        for(int a = 0; a < line.length(); a++) {
                            temp += line.substring(a, a + 1);
                            if(line.substring(a, a + 1).equals(" ")) {
                                temp = temp.substring(0, temp.length() - 1);
                                combineNodes.add(new int[]{strToNum(temp)});
                                temp = "";
                            }
                        }
                        break;
                    case 4:
                        for(int a = 0; a < line.length(); a++) {
                            temp += line.substring(a, a + 1);
                            if(line.substring(a, a + 1).equals("|")) {
                                temp = temp.substring(0, temp.length() - 1);
                                if(timesEntered == 0)
                                    IToC.add(new double[]{strToDB(temp), 0, 0});
                                else
                                    IToC.get(IToC.size() - 1)[1] = strToDB(temp);
                                temp = "";
                                timesEntered++;
                            } else if (line.substring(a, a + 1).equals(" ")) {
                                temp = temp.substring(0, temp.length() - 1);
                                IToC.get(IToC.size() - 1)[2] = strToDB(temp);
                                temp = "";
                                timesEntered = 0;
                            }
                        }
                        break;
                    case 5:
                        for(int a = 0; a < line.length(); a++) {
                            temp += line.substring(a, a + 1);
                            if(line.substring(a, a + 1).equals("|")) {
                                temp = temp.substring(0, temp.length() - 1);
                                if(timesEntered == 0)
                                    CToO.add(new double[]{strToDB(temp), 0, 0});
                                else
                                    CToO.get(CToO.size() - 1)[1] = strToDB(temp);
                                temp = "";
                                timesEntered++;
                            } else if (line.substring(a, a + 1).equals(" ")) {
                                temp = temp.substring(0, temp.length() - 1);
                                CToO.get(CToO.size() - 1)[2] = strToDB(temp);
                                temp = "";
                                timesEntered = 0;
                            }
                        }
                        break;
                    case 6:
                        for(int a = 0; a < line.length(); a++) {
                            temp += line.substring(a, a + 1);
                            if(line.substring(a, a + 1).equals("|")) {
                                temp = temp.substring(0, temp.length() - 1);
                                switch(timesEntered) {
                                    case 0:
                                        ends.add(new int[]{strToNum(temp), 0, 0, 0, 0, 0});
                                        break;
                                    case 1:
                                        ends.get(ends.size() - 1)[1] = strToNum(temp);
                                        break;
                                    case 2:
                                        ends.get(ends.size() - 1)[2] = strToNum(temp);
                                        break;
                                    case 3:
                                        ends.get(ends.size() - 1)[3] = strToNum(temp);
                                        break;
                                    case 4:
                                        ends.get(ends.size() - 1)[4] = strToNum(temp);
                                        break;
                                }
                                temp = "";
                                timesEntered++;
                            } else if (line.substring(a, a + 1).equals(" ")) {
                                temp = temp.substring(0, temp.length() - 1);
                                ends.get(ends.size() - 1)[5] = strToNum(temp);
                                temp = "";
                            }
                        }
                        break;
                }
            }
        } catch (IOException E) {
            E.printStackTrace();
        }
    }

    private double strToDB (String str) {
        return Double.parseDouble(str);
    }
    private int strToNum (String str) {
        return Integer.parseInt(str);
    }
}