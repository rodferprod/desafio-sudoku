package ui.custom.panels;

import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import static java.awt.Color.black;

public class SudokoSlots extends JPanel {
    public SudokoSlots() {
        Dimension dimensions = new Dimension(170, 170);
        this.setSize(dimensions);
        this.setPreferredSize(dimensions);
        this.setBorder(new LineBorder(black, 2, true));
        this.setVisible(true);
    }
}
