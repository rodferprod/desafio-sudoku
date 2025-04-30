package service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import model.Square;
import model.Board;
import model.GameStatus;

public class BoardService {

    private final static int BOARD_LIMIT = 9;

    private final Board board;

    public BoardService(Map<String, String> positions) {
        board = new Board(initBoard(positions));
    }

    public List<List<Square>> getSquares() {
        return board.getSquares();
    }

    private static List<List<Square>> initBoard(final Map<String, String> positions) {
        List<List<Square>> squares = new ArrayList<>();
        for (int i = 0; i < BOARD_LIMIT; i++) {
            squares.add(new ArrayList<>());
            for (int j = 0; j < BOARD_LIMIT; j++) {
                String positionValue = positions.get("%s,%s".formatted(i, j));
                int expectedPositionValue = Integer.parseInt(positionValue.split(",")[0]);
                boolean fixedValue = Boolean.parseBoolean(positionValue.split(",")[1]);
                Square currentSquare = new Square(expectedPositionValue, fixedValue);
                squares.get(i).add(currentSquare);
            }
        }
        return squares;
    }

    public GameStatus getStatus() {
        return board.getStatus();
    }

    // Quando a alteração ocorre nos valores armazenados (squares) os text fields
    // correspondentes na interface não são refletidos, sendo necessário criar um
    // serviço de notificação para cumprir essa tarefa.
    public void resetGame() {
        board.resetGame();
    }

    public boolean checkGameDone() {
        return board.checkGameDone();
    }

    public boolean hasAnyError() {
        return board.hasAnyError();
    }

}
