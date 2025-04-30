package ui.custom.screen;

import java.awt.Dimension;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import model.GameStatus;
import service.BoardService;
import ui.custom.frame.MainFrame;
import ui.custom.panels.SudokoTable;
import ui.custom.button.ButtonCheckGameStatus;
import ui.custom.button.ButtonFinishGame;
import ui.custom.button.ButtonRestartGame;

import static javax.swing.JOptionPane.QUESTION_MESSAGE;
import static javax.swing.JOptionPane.YES_NO_OPTION;

public class MainScreen {

    private final static Dimension dimension = new Dimension(600, 600);

    private final BoardService boardService;

    private JButton finishGameButton;
    private JButton checkGameStatusButton;
    private JButton restartGameButton;

    public MainScreen(final Map<String, String> positions) {
        this.boardService = new BoardService(positions);
    }

    public void buildMainScreen() {
        JPanel mainPanel = new SudokoTable(dimension);
        JFrame mainFrame = new MainFrame(dimension, mainPanel);

        addRestartGameButton(mainPanel);
        addCheckGameStatusButton(mainPanel);
        addFinishGameButton(mainPanel);

        mainFrame.revalidate();
        mainFrame.repaint();
    }

    private void addFinishGameButton(JPanel mainPanel) {
        finishGameButton = new ButtonFinishGame(event -> {
            if (boardService.checkGameDone()) {
                JOptionPane.showMessageDialog(null, "Parabéns! Você concluiu o jogo!");
                restartGameButton.setEnabled(false);
                checkGameStatusButton.setEnabled(false);
                finishGameButton.setEnabled(false);
            } else {
                String message = "O jogo contém algum erro. Verifique e tente novamente!";
                JOptionPane.showMessageDialog(null, message);
            }
        });

        mainPanel.add(finishGameButton);
    }

    private void addCheckGameStatusButton(JPanel mainPanel) {
        checkGameStatusButton = new ButtonCheckGameStatus(event -> {
            boolean hasError = boardService.hasAnyError();
            GameStatus status = boardService.getStatus();
            String message = switch (status) {
                case NON_STARTED -> "O jogo ainda não foi iniciado";
                case INCOMPLETE -> "O jogo está incompleto";
                case COMPLETE -> "O jogo está completo";
            };
            message += hasError ? " e contém algum erro" : " e não contém nehum erro";
            JOptionPane.showMessageDialog(null, message);
        });

        mainPanel.add(checkGameStatusButton);
    }

    private void addRestartGameButton(JPanel mainPanel) {
        restartGameButton = new ButtonRestartGame(event -> {
            int dialogResult = JOptionPane.showConfirmDialog(
                    null,
                    "Deseja realmente reiniciar o jogo?",
                    "Reiniciar jogo",
                    YES_NO_OPTION,
                    QUESTION_MESSAGE);

            if (dialogResult == 0) {
                boardService.resetGame();
            }
        });

        mainPanel.add(restartGameButton);
    }
}
