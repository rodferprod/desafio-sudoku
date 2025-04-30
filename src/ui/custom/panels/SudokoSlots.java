package ui.custom.panels;

import java.awt.Dimension;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import ui.custom.input.NumberText;

import static java.awt.Color.black;

public class SudokoSlots extends JPanel {

    public SudokoSlots(final List<NumberText> numberFields) {
        Dimension dimensions = new Dimension(170, 170);
        this.setSize(dimensions);
        this.setPreferredSize(dimensions);
        this.setBorder(new LineBorder(black, 2, true));
        this.setVisible(true);
        numberFields.forEach(this::add);
    }
}
