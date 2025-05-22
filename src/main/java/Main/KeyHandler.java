package Main;


// Classe che gestisce l'input da tastiera
public class KeyHandler implements java.awt.event.KeyListener {

    // Variabili booleane per tracciare lo stato dei tasti premuti
    public boolean upPressed, downPressed, leftPressed, rightPressed;
    public boolean undoPressed, restartPressed;
    public boolean escapePressed = false;

    @Override
    public void keyTyped(java.awt.event.KeyEvent e) {
    }

    // Metodo chiamato quando un tasto viene premuto
    @Override
    public void keyPressed(java.awt.event.KeyEvent e) {
        int code = e.getKeyCode();

        // Verifica quale tasto è stato premuto e imposta la variabile corrispondente a true
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

    // Metodo chiamato quando un tasto viene rilasciato
    @Override
    public void keyReleased(java.awt.event.KeyEvent e) {
        int code = e.getKeyCode();

        // Verifica quale tasto è stato rilasciato e imposta la variabile corrispondente a false
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
