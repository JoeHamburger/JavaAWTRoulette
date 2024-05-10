import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MouseAction implements MouseListener {
    private int mouseX, mouseY;
    private boolean buttonPressed = false;

    @Override
    public void mouseClicked(MouseEvent e) {
        // Set buttonPressed to true when the mouse is pressed
        // System.out.println("Mouse pressed at: " + mouseX + ", " + mouseY);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        mouseX = e.getX();
        mouseY = e.getY();
        buttonPressed = true; // Set buttonPressed to true when the mouse is pressed
        // System.out.println("Mouse pressed at: " + mouseX + ", " + mouseY);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        buttonPressed = false; // Set buttonPressed to false when the mouse is released
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // Not needed for this implementation
    }

    @Override
    public void mouseExited(MouseEvent e) {
        // Not needed for this implementation
    }

    public int getMouseX() {
        return mouseX;
    }

    public int getMouseY() {
        return mouseY;
    }

    public boolean isButtonPressed() {
        return buttonPressed; // Return the value of buttonPressed
    }
}