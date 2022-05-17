package Objects;

public class Card extends Object{
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
        return new String[]{"Card"};
    }
    public int[] getData() {
        return new int[]{x, y, width, height};
    }
}
