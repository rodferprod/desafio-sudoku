package ui.custom.button;

import java.awt.event.ActionListener;

import javax.swing.JButton;

public class ButtonResetGame extends JButton {
    public ButtonResetGame(final ActionListener actionListener) {
        this.setText("Reiniciar");
        this.addActionListener(actionListener);
    }
}
