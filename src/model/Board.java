package model;

import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

import static java.util.Objects.nonNull;
import static java.util.Objects.isNull;
import static model.GameStatus.COMPLETE;
import static model.GameStatus.INCOMPLETE;
import static model.GameStatus.NON_STARTED;

public class Board {
    private final List<List<Square>> squares;

    public Board(final List<List<Square>> squares) {
        this.squares = squares;
    }

    public List<List<Square>> getSquares() {
        return squares;
    }

    /**
     * Verifica se o jogo ainda não foi iniciado, se está incompleto ou finalizado.
     * 
     * @return GameStatus
     */
    public GameStatus getStatus() {

        // 1) .flatMap() acessa as listas internas de uma lista pai;
        // 2) Collection::stream retorna todos os itens da lista (square -> square);
        Stream<Square> collection = squares.stream().flatMap(Collection::stream);

        // 3) .noneMatch() percorre toda a coleção e retorna TRUE se TODOS os itens
        // NÃO correspondem à condição: se os valores não são fixos e não-nulos.
        // OBS: O jogo não começou se existem apenas valores nulos e fixos na lista.
        // Valores fixos são os números iniciais do tabuleiro. O restante são nulos.
        if (collection.noneMatch(square -> !square.isFixed() && nonNull(square.getActual()))) {
            return NON_STARTED;
        }
        // A partir daqui temos apenas duas condições: Incompleto ou completo.
        // 4) .anyMatch() percorre toda a coleção e retorna TRUE se ALGUM dos itens
        // correspondem à condição: se algum dos valores ainda é nulo (jogo incompleto).
        // Se todos os itens estiverem preenchidos o jogo está completo.
        return collection.anyMatch(square -> isNull(square.getActual())) ? INCOMPLETE : COMPLETE;
    }

    /**
     * Verifica se o jogo possui algum valor insperado para as "casas" disponíveis.
     * 
     * @return boolean
     */
    public boolean hasAnyError() {

        if (getStatus() == NON_STARTED) {
            return false;
        }

        // .anyMatch() percorre cada item e retorna TRUE se algum valor
        // não-nulo for diferente do valor esperado para a "casa" atual
        return squares.stream().flatMap(Collection::stream)
                .anyMatch(square -> nonNull(square.getActual()) && !square.getActual().equals(square.getExpected()));
    }

    /**
     * Altera o valor de uma "casa" se não existir um valor fixo.
     * 
     * @param col
     * @param row
     * @param value
     * @return boolean
     */
    public boolean changeSquareValue(final int col, final int row, final int value) {
        Square square = squares.get(col).get(row);
        if (square.isFixed())
            return false;

        square.setActual(value);
        return true;
    }

    /**
     * Limpa o valor de uma "casa" se não existir um valor fixo.
     * 
     * @param col
     * @param row
     * @return boolean
     */
    public boolean clearSquareValue(final int col, final int row) {
        Square square = squares.get(col).get(row);
        if (square.isFixed())
            return false;

        square.setActual(null);
        return true;
    }

    /**
     * Limpa todas as "casas" disponíveis para preenchimento no jogo.
     */
    public void resetGame() {
        // Method reference Square::clearSquare é equivalente à seguinte expressão:
        // row -> row.clearSquare()
        squares.forEach(col -> col.forEach(Square::clearSquare));
    }

    /**
     * Verifica se o jogo foi finalizado
     * 
     * @return boolean
     */
    public boolean checkGameDone() {
        return !hasAnyError() && getStatus().equals(COMPLETE);
    }
}
