package afengine.part.uiinput;

import afengine.core.util.Debug;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class InputAdapter implements KeyListener,MouseListener,MouseMotionListener,MouseWheelListener,WindowListener{

    @Override
    public void keyTyped(KeyEvent e) {
        Debug.log("key typed "+e.getKeyChar());
    }

    @Override
    public void keyPressed(KeyEvent e) {
        Debug.log("key pressed "+e.paramString());
    }

    @Override
    public void keyReleased(KeyEvent e) {
        Debug.log("key released "+e.paramString());
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        Debug.log("mouse clicked "+e.paramString());
    }

    @Override
    public void mousePressed(MouseEvent e) {
        Debug.log("mouse pressed "+e.paramString());
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        Debug.log("mouse released "+e.paramString());
    }

    //do not 
    @Override
    public void mouseEntered(MouseEvent e){
        Debug.log("mouse enter windows");
    }

    @Override
    public void mouseExited(MouseEvent e) {
        Debug.log("mouse exit windows");
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        Debug.log("mouse dragged to ["+e.getX()+","+e.getY()+"]") ;
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        Debug.log("mouse moved to ["+e.getX()+","+e.getY()+"]") ;
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        Debug.log("mouse wheel ["+e.getWheelRotation()+"]") ;
    }

    @Override
    public void windowOpened(WindowEvent e) {
        Debug.log("window opened") ;
    }

    @Override
    public void windowClosing(WindowEvent e) {
        Debug.log("window closing") ;
    }

    @Override
    public void windowClosed(WindowEvent e) {
        Debug.log("window closed") ;
    }

    @Override
    public void windowIconified(WindowEvent e) {
        Debug.log("window iconified") ;
    }

    @Override
    public void windowDeiconified(WindowEvent e) {
        Debug.log("window deiconified") ;
    }

    @Override
    public void windowActivated(WindowEvent e) {
        Debug.log("window actived") ;
    }

    @Override
    public void windowDeactivated(WindowEvent e) {
        Debug.log("window deactived") ;
    }    
}
