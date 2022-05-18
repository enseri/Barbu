package Objects;

public class Button extends Object{
    String text;
    int x, y, width, height;
    public Button(String text, int x, int y, int width, int height) {
        this.text = text;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }
    public String[] getToString(){
        return new String[]{"Button", text};
    }
    public int[] getData() {
        return new int[]{x, y, width, height};
    }
    @Override
    public void editToString(String[] edits) {
        if(edits[1] != null)
            text = edits[1];
    }
}
