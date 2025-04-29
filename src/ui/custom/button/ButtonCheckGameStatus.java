package ui.custom.button;

import javax.swing.JButton;
import java.awt.event.ActionListener;

public class ButtonCheckGameStatus extends JButton {
    public ButtonCheckGameStatus(final ActionListener actionListener) {
        this.setText("Verificar status");
        this.addActionListener(actionListener);
    }
}
