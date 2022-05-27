package Objects;

import java.util.ArrayList;
import java.util.Arrays;

public class Card extends Object {
    public static ArrayList<Card> plis = new ArrayList<>();
    public static Card[] deck = new Card[] {
            new Card("Clover", "Ace", 0, 0, 100, 150),
            new Card("Clover", "Two", 0, 0, 100, 150),
            new Card("Clover", "Three", 0, 0, 100, 150),
            new Card("Clover", "Four", 0, 0, 100, 150),
            new Card("Clover", "Five", 0, 0, 100, 150),
            new Card("Clover", "Six", 0, 0, 100, 150),
            new Card("Clover", "Seven", 0, 0, 100, 150),
            new Card("Clover", "Eight", 0, 0, 100, 150),
            new Card("Clover", "Nine", 0, 0, 100, 150),
            new Card("Clover", "Ten", 0, 0, 100, 150),
            new Card("Clover", "Jack", 0, 0, 100, 150),
            new Card("Clover", "Queen", 0, 0, 100, 150),
            new Card("Clover", "King", 0, 0, 100, 150),
            new Card("Spade", "Ace", 0, 0, 100, 150),
            new Card("Spade", "Two", 0, 0, 100, 150),
            new Card("Spade", "Three", 0, 0, 100, 150),
            new Card("Spade", "Four", 0, 0, 100, 150),
            new Card("Spade", "Five", 0, 0, 100, 150),
            new Card("Spade", "Six", 0, 0, 100, 150),
            new Card("Spade", "Seven", 0, 0, 100, 150),
            new Card("Spade", "Eight", 0, 0, 100, 150),
            new Card("Spade", "Nine", 0, 0, 100, 150),
            new Card("Spade", "Ten", 0, 0, 100, 150),
            new Card("Spade", "Jack", 0, 0, 100, 150),
            new Card("Spade", "Queen", 0, 0, 100, 150),
            new Card("Spade", "King", 0, 0, 100, 150),
            new Card("Diamond", "Ace", 0, 0, 100, 150),
            new Card("Diamond", "Two", 0, 0, 100, 150),
            new Card("Diamond", "Three", 0, 0, 100, 150),
            new Card("Diamond", "Four", 0, 0, 100, 150),
            new Card("Diamond", "Five", 0, 0, 100, 150),
            new Card("Diamond", "Six", 0, 0, 100, 150),
            new Card("Diamond", "Seven", 0, 0, 100, 150),
            new Card("Diamond", "Eight", 0, 0, 100, 150),
            new Card("Diamond", "Nine", 0, 0, 100, 150),
            new Card("Diamond", "Ten", 0, 0, 100, 150),
            new Card("Diamond", "Jack", 0, 0, 100, 150),
            new Card("Diamond", "Queen", 0, 0, 100, 150),
            new Card("Diamond", "King", 0, 0, 100, 150),
            new Card("Heart", "Ace", 0, 0, 100, 150),
            new Card("Heart", "Two", 0, 0, 100, 150),
            new Card("Heart", "Three", 0, 0, 100, 150),
            new Card("Heart", "Four", 0, 0, 100, 150),
            new Card("Heart", "Five", 0, 0, 100, 150),
            new Card("Heart", "Six", 0, 0, 100, 150),
            new Card("Heart", "Seven", 0, 0, 100, 150),
            new Card("Heart", "Eight", 0, 0, 100, 150),
            new Card("Heart", "Nine", 0, 0, 100, 150),
            new Card("Heart", "Ten", 0, 0, 100, 150),
            new Card("Heart", "Jack", 0, 0, 100, 150),
            new Card("Heart", "Queen", 0, 0, 100, 150),
            new Card("Heart", "King", 0, 0, 100, 150),
    };
    String type, card;
    int x, y, width, height;

    public Card(String type, String card, int x, int y, int width, int height) {
        this.type = type;
        this.card = card;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public String[] getToString() {
        return new String[] { "Card", type, card };
    }

    public int[] getData() {
        return new int[] { x, y, width, height };
    }

    @Override
    public void editToString(String[] edits) {
        if (edits[1] != null)
            type = edits[1];
        if (edits[2] != null)
            card = edits[2];
    }

    public static ArrayList<ArrayList<Card>> genRandCards() {
        ArrayList<Card> arr1 = new ArrayList<>();
        ArrayList<Card> arr2 = new ArrayList<>();
        ArrayList<Card> arr3 = new ArrayList<>();
        ArrayList<Card> arr4 = new ArrayList<>();
        ArrayList<Card> excluded = new ArrayList<>();
        for (int i = 0; i < deck.length; i++) {
            int randNum = (int) (Math.random() * deck.length);
            while (excluded.contains(deck[randNum])) {
                randNum = (int) (Math.random() * deck.length);
            }
            boolean added = false;
            if (arr1.size() < 13 && !added) {
                added = true;
                arr1.add(deck[randNum]);
                excluded.add(deck[randNum]);
            }
            if (arr2.size() < 13 && !added) {
                added = true;
                arr2.add(deck[randNum]);
                excluded.add(deck[randNum]);
            }
            if (arr3.size() < 13 && !added) {
                added = true;
                arr3.add(deck[randNum]);
                excluded.add(deck[randNum]);
            }
            if (arr4.size() < 13 && !added) {
                added = true;
                arr4.add(deck[randNum]);
                excluded.add(deck[randNum]);
            }
        }
        for(int a = 0; a < 13; a++) {
            for (int b = 0; b < 13; b++) {
                if (getCardRank(arr1.get(a)) < getCardRank(arr1.get(b))) {
                    arr1.add(b, arr1.get(a));
                    if(a - b > 0)
                        arr1.remove(a + 1);
                    else
                        arr1.remove(a);
                }
                if (getCardRank(arr2.get(a)) < getCardRank(arr2.get(b))) {
                    arr2.add(b, arr2.get(a));
                    if(a - b > 0)
                        arr2.remove(a + 1);
                    else
                        arr2.remove(a);
                }
                if (getCardRank(arr3.get(a)) < getCardRank(arr3.get(b))) {
                    arr3.add(b, arr3.get(a));
                    if(a - b > 0)
                        arr3.remove(a + 1);
                    else
                        arr3.remove(a);
                }
                if (getCardRank(arr4.get(a)) < getCardRank(arr4.get(b))) {
                    arr4.add(b, arr4.get(a));
                    if(a - b > 0)
                        arr4.remove(a + 1);
                    else
                        arr4.remove(a);
                }
            }
        }
        for (int a = 0; a < 13; a++) {
            for (int b = 0; b < 13; b++) {
                if (getCardValue(arr1.get(a)) > getCardValue(arr1.get(b)) && arr1.get(b).getToString()[1].equals(arr1.get(a).getToString()[1])) {
                    arr1.add(b, arr1.get(a));
                    if(a - b > 0)
                        arr1.remove(a + 1);
                    else
                        arr1.remove(a);
                }
                if (getCardValue(arr2.get(a)) > getCardValue(arr2.get(b)) && arr2.get(b).getToString()[1].equals(arr2.get(a).getToString()[1])) {
                    arr2.add(b, arr2.get(a));
                    if(a - b > 0)
                        arr2.remove(a + 1);
                    else
                        arr2.remove(a);
                }
                if (getCardValue(arr3.get(a)) > getCardValue(arr3.get(b)) && arr3.get(b).getToString()[1].equals(arr3.get(a).getToString()[1])) {
                    arr3.add(b, arr3.get(a));
                    if(a - b > 0)
                        arr3.remove(a + 1);
                    else
                        arr3.remove(a);
                }
                if (getCardValue(arr4.get(a)) > getCardValue(arr4.get(b)) && arr4.get(b).getToString()[1].equals(arr4.get(a).getToString()[1])) {
                    arr4.add(b, arr4.get(a));
                    if(a - b > 0)
                        arr4.remove(a + 1);
                    else
                        arr4.remove(a);
                }
            }
        }
        return new ArrayList<ArrayList<Card>>(Arrays.asList(arr1, arr2, arr3, arr4));
    }

    @Override
    public void editData(int[] edits) {
        if (edits[0] != -1)
            x = edits[0];
        if (edits[1] != -1)
            y = edits[1];
        if (edits[2] != -1)
            width = edits[2];
        if (edits[3] != -1)
            height = edits[3];
    }

    private static int getCardValue(Card card) {
        switch (card.getToString()[2]) {
            case "Two":
                return 2;

            case "Three":
                return 3;

            case "Four":
                return 4;

            case "Five":
                return 5;

            case "Six":
                return 6;

            case "Seven":
                return 7;

            case "Eight":
                return 8;

            case "Nine":
                return 9;

            case "Ten":
                return 10;

            case "Jack":
                return 11;

            case "Queen":
                return 12;

            case "King":
                return 13;

            case "Ace":
                return 14;

        }
        return -1;
    }

    public static int getCardRank(Card card) {
        switch(card.getToString()[1]) {
            case "Heart":
                return 1;
            case "Diamond":
                return 2;
            case "Spade":
                return 3;
            case "Clover":
                return 4;
        }
        return -1;
    }
}
