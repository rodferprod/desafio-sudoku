package ui.custom.button;

import java.awt.event.ActionListener;

import javax.swing.JButton;

public class ButtonRestartGame extends JButton {
    public ButtonRestartGame(final ActionListener actionListener) {
        this.setText("Reiniciar");
        this.addActionListener(actionListener);
    }
}
