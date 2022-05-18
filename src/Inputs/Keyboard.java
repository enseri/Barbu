package Inputs;
import java.awt.event.*;

import States.State;
public class Keyboard implements KeyListener{
    public static String lastKeyStr;
    public static int lastKeyNum;
    @Override
    public void keyPressed(KeyEvent e) {
        // TODO Auto-generated method stub
    }

    @Override
    public void keyReleased(KeyEvent e) {
        lastKeyNum = e.getKeyCode();
        lastKeyStr = e.getKeyChar() + "";
        State.currentState.interactionWithObject(null, "typed");
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }
    
}
