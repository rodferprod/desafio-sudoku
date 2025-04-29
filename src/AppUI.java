import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;

import ui.custom.frame.MainFrame;
import ui.custom.panels.SudokoTable;

public class AppUI {
    public static void main(String[] args) {
        Dimension dimension = new Dimension(600, 600);
        JPanel sudokoTable = new SudokoTable(dimension);
        JFrame mainFrame = new MainFrame(dimension, sudokoTable);
        mainFrame.revalidate();
        mainFrame.repaint();
    }
}
