package Main;

public class KeyHandler implements java.awt.event.KeyListener {

    public boolean upPressed, downPressed, leftPressed, rightPressed;
    public boolean undoPressed, restartPressed;
    public boolean escapePressed = false;

    @Override
    public void keyTyped(java.awt.event.KeyEvent e) {
    }

    @Override
    public void keyPressed(java.awt.event.KeyEvent e) {
        int code = e.getKeyCode();

        if (code == java.awt.event.KeyEvent.VK_W)
            upPressed = true;
        if (code == java.awt.event.KeyEvent.VK_A)
            leftPressed = true;
        if (code == java.awt.event.KeyEvent.VK_S)
            downPressed = true;
        if (code == java.awt.event.KeyEvent.VK_D)
            rightPressed = true;
        if (code == java.awt.event.KeyEvent.VK_Z)
            undoPressed = true;
        if (code == java.awt.event.KeyEvent.VK_U)
            restartPressed = true;
        if (code == java.awt.event.KeyEvent.VK_ESCAPE)
            escapePressed = true;
    }

    @Override
    public void keyReleased(java.awt.event.KeyEvent e) {
        int code = e.getKeyCode();

        if (code == java.awt.event.KeyEvent.VK_W)
            upPressed = false;
        if (code == java.awt.event.KeyEvent.VK_A)
            leftPressed = false;
        if (code == java.awt.event.KeyEvent.VK_S)
            downPressed = false;
        if (code == java.awt.event.KeyEvent.VK_D)
            rightPressed = false;
        if (code == java.awt.event.KeyEvent.VK_Z)
            undoPressed = false;
        if (code == java.awt.event.KeyEvent.VK_U)
            restartPressed = false;
        if (code == java.awt.event.KeyEvent.VK_ESCAPE)
            escapePressed = false;
    }
}
