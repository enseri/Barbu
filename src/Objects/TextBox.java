package Objects;

public class TextBox extends Object{
    String iniText = "", currentText = "";
    int x, y, width, height;
    public TextBox(String initialText, int x, int y, int width, int height) {
        this.iniText = initialText;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }
    @Override
    public String[] getToString() {
        return new String[]{"TextBox", iniText, currentText};
    }

    @Override
    public int[] getData() {
        return new int[]{x, y, width, height};
    }
    @Override
    public void editToString(String[] edits) {
        if(edits[1] != null)
            iniText = edits[1];
        if(edits[2] != null)
            currentText = edits[2];
    }
    
}
