package Objects;

import java.util.ArrayList;

public abstract class Object {
    public abstract String[] getToString(); // Get The Object Info
    public abstract int[] getData(); // Return Objects Data
    public abstract void editToString(String[] edits); // Edit To String For Positioning
    public abstract void editData(int[] edits); // Edit Objects Data
}
