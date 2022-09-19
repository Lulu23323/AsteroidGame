package si.display;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class PlayerListener implements KeyListener {
    private boolean left;
    private boolean right;
    private boolean forwards;
    private boolean backwards;
    private boolean fire;
    private boolean pause;
    private boolean invincible;

    public boolean isPressingLeft() {
        return left;
    }

    public boolean isPressingRight() {
        return right;
    }

    public boolean hasPressedForwards() {
        return forwards;
    }

    public boolean hasPressedBackwards() {return backwards;}

    public boolean hasPressedFire() {return fire;}

    public boolean hasPressedPause() {
        return pause;
    }


    @Override
    public void keyTyped(KeyEvent e) {
        if (e.getKeyChar() == 'P' || e.getKeyChar() == 'p') {
            pause = true;
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            left = true;
        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            right = true;
        } else if (e.getKeyCode() == KeyEvent.VK_UP) {
            forwards = true;
        }else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            backwards = true;
        } else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            fire = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            left = false;
        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            right = false;
        }else if (e.getKeyCode() == KeyEvent.VK_UP) {
            forwards = false;
        }else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            backwards = false;
        }
    }

    public void resetPause() {
        pause = false;
    }

    public void resetUp() {
        forwards = false;
    }

    public void resetFire() {
        fire = false;
    }

    public void resetBackwards() {
        backwards = false;
    }
}
