package Inputs;
import States.*;
import Objects.*;
import Objects.Object;

import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.event.MouseInputListener;
public class Mouse implements MouseInputListener{

    @Override
    public void mouseClicked(MouseEvent e) {
        ArrayList<Object> tempObjects = State.currentState.getObjects();
        for(int i = 0; i < tempObjects.size(); i++) {
            boolean xIntercept = false, yIntercept = false;
            int[] data = tempObjects.get(i).getData();
            for(int x = data[0]; x < data[0] + data[2]; x++) {
                if(e.getX() == x)
                    xIntercept = true;
            }
            for(int y = data[1]; y < data[1] + data[3]; y++) {
                if(e.getY() == y)
                    yIntercept = true;
            }
            if(xIntercept && yIntercept) {
                State.currentState.interactionWithObject(tempObjects.get(i));
                break;
            }
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void mouseExited(MouseEvent e) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void mousePressed(MouseEvent e) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        // TODO Auto-generated method stub
        
    }
    
}
