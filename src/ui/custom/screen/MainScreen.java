package ui.custom.screen;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import model.GameStatus;
import model.Square;
import service.BoardService;
import service.NotifierService;
import ui.custom.frame.MainFrame;
import ui.custom.panels.SudokoSlots;
import ui.custom.panels.SudokoTable;
import ui.custom.button.ButtonCheckGameStatus;
import ui.custom.button.ButtonFinishGame;
import ui.custom.button.ButtonRestartGame;
import ui.custom.input.NumberText;

import static javax.swing.JOptionPane.QUESTION_MESSAGE;
import static javax.swing.JOptionPane.YES_NO_OPTION;
import static javax.swing.JOptionPane.showMessageDialog;
import static service.ReverseEvent.CLEAR_TEXT_FIELD;;

public class MainScreen {

    private final static Dimension dimension = new Dimension(600, 600);

    private final BoardService boardService;
    private final NotifierService notifierService;

    private JButton finishGameButton;
    private JButton checkGameStatusButton;
    private JButton restartGameButton;

    public MainScreen(final Map<String, String> positions) {
        this.boardService = new BoardService(positions);
        this.notifierService = new NotifierService();
    }

    public void buildMainScreen() {
        JPanel mainPanel = new SudokoTable(dimension);
        JFrame mainFrame = new MainFrame(dimension, mainPanel);

        for (int row = 0; row < 9; row += 3) {
            int endRow = row + 2;
            for (int col = 0; col < 9; col += 3) {
                int endCol = col + 2;
                List<Square> squares = getSquares(boardService.getSquares(), col, endCol, row, endRow);
                JPanel slots = generateSlots(squares);
                mainPanel.add(slots);
            }
        }

        addRestartGameButton(mainPanel);
        addCheckGameStatusButton(mainPanel);
        addFinishGameButton(mainPanel);

        mainFrame.revalidate();
        mainFrame.repaint();
    }

    private List<Square> getSquares(final List<List<Square>> squares, final int initCol, final int endCol,
            final int initRow, final int endRow) {

        List<Square> squareSlot = new ArrayList<>();
        for (int row = initRow; row <= endRow; row++) {
            for (int col = initCol; col <= endCol; col++) {
                squareSlot.add(squares.get(col).get(row));
            }
        }

        return squareSlot;
    }

    private JPanel generateSlots(final List<Square> squares) {

        // Criando uma lista de componentes text fields
        List<NumberText> numberFields = new ArrayList<>(squares.stream().map(NumberText::new).toList());

        // Registrando componentes text fields para que eventos
        // ocorridos nos squares sejam refletidos em si mesmos
        numberFields.forEach(field -> notifierService.subscribe(CLEAR_TEXT_FIELD, field));

        return new SudokoSlots(numberFields);
    }

    private void addFinishGameButton(JPanel mainPanel) {
        finishGameButton = new ButtonFinishGame(event -> {
            if (boardService.checkGameDone()) {
                showMessageDialog(null, "Parabéns! Você concluiu o jogo!");
                restartGameButton.setEnabled(false);
                checkGameStatusButton.setEnabled(false);
                finishGameButton.setEnabled(false);
            } else {
                String message = "O jogo contém algum erro. Verifique e tente novamente!";
                showMessageDialog(null, message);
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
            showMessageDialog(null, message);
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
                notifierService.notify(CLEAR_TEXT_FIELD);
            }
        });

        mainPanel.add(restartGameButton);
    }
}
