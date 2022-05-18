package Objects;

public class Card extends Object {
    public static Card[] deck = new Card[] {
            new Card("Clover", "Ace", 0, 0, 100, 150),
            new Card("Clover", "One", 0, 0, 100, 150),
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
            new Card("Spade", "One", 0, 0, 100, 150),
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
            new Card("Diamond", "One", 0, 0, 100, 150),
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
            new Card("Heart", "One", 0, 0, 100, 150),
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
}
