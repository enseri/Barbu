package Inputs;
import java.awt.event.*;
import Game.Game;
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
        lastKeyNum = e.getKeyCode();// Save Key Variables
        lastKeyStr = e.getKeyChar() + "";// Save Key Variables
        State.currentState.interactionWithObject(null, "typed"); // For Current State Send Data
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }
    
}
