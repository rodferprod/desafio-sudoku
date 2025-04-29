package ui.custom.button;

import java.awt.event.ActionListener;

import javax.swing.JButton;

public class ButtonFinishGame extends JButton {
    public ButtonFinishGame(final ActionListener actionListener) {
        this.setText("Concluir");
        this.addActionListener(actionListener);
    }
}
