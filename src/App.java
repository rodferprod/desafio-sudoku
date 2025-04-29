import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

import static util.BoardTemplate.BOARD_TEMPLATE;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import model.Board;
import model.Square;

public class App {

    private final static Scanner scanner = new Scanner(System.in);
    private static Board board;
    private final static int BOARD_LIMIT = 9;
    private static int coluna, linha, numero;

    public static void main(String[] args) throws Exception {

        // Os arqumentos chegam com a seguinte estrutura: "0,0;4,false"
        // .collect() funciona como uma função .reduce() que aceita uma função que
        // recebe uma coleção de dados e os retorna modificados da forma desejada:
        // Converte em um mapa em que as chaves são as coordenadas (col=0, row=0)
        // com seus respectivos valores (valor=4, fixed=false)
        final Map<String, String> positions = Stream.of(args)
                .collect(
                        Collectors.toMap(
                                key -> key.split(";")[0],
                                value -> value.split(";")[1]));

        int option = -1;

        while (option != 0) {
            System.out.println("Selecione a opção desejada:");
            System.out.println("1 - Iniciar um novo jogo");
            System.out.println("2 - Inserir um número");
            System.out.println("3 - Remover um número");
            System.out.println("4 - Visualizar jogo");
            System.out.println("5 - Verificar status");
            System.out.println("6 - Reiniciar jogo");
            System.out.println("7 - Finalizar jogo");
            System.out.println("0 - Sair");

            option = scanner.nextInt();

            switch (option) {
                case 1:
                    startGame(positions);
                    break;
                case 2:
                    insertNumber();
                    break;
                case 3:
                    removeNumber();
                    break;
                case 4:
                    viewGame();
                    break;
                case 5:
                    viewStatus();
                    break;
                case 6:
                    restartGame();
                    break;
                case 7:
                    endGame();
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Opção inválida!");
                    break;
            }
        }
    }

    /*******************************************************************
     * Recebe a configuração inicial do jogo e preenche uma matriz com
     * as coordenadas das posições do tabuleiro e seus valores
     * 
     * @param positions
     *******************************************************************/
    private static void startGame(Map<String, String> positions) {
        if (nonNull(board)) {
            System.out.println("O jogo já foi iniciado anteriormente.");
            return;
        }

        // Criando o List externo
        List<List<Square>> squares = new ArrayList<>();

        // Percorre a lista externa, que contem índices das linhas
        for (int i = 0; i < BOARD_LIMIT; i++) {

            // Criando o List interno
            squares.add(new ArrayList<>());

            // Percorre a lista interna, que contem índices das colunas
            for (int j = 0; j < BOARD_LIMIT; j++) {

                // Percorrendo as chaves e pegando os valores das posições
                String positionValue = positions.get("%s,%s".formatted(i, j));

                // Convertendo os valores capturados para seus devidos tipos
                int expectedPositionValue = Integer.parseInt(positionValue.split(",")[0]);
                boolean fixedValue = Boolean.parseBoolean(positionValue.split(",")[1]);

                // Instanciando a posição atual do tabuleiro
                Square currentSquare = new Square(expectedPositionValue, fixedValue);

                // Pegando o item da lista correspondente à casa do tabuleiro para
                // configurar os valores da posição corrente
                squares.get(i).add(currentSquare);
            }
        }
        // Configurando o tabuleiro com todas as posições configuradas
        board = new Board(squares);
        System.out.println("O tabuleiro foi configurado! Pronto para iniciar o jogo?");

    }

    /*******************************************************************
     * Insere um número no tabuleiro
     *******************************************************************/
    private static void insertNumber() {
        if (checkGameNotConfigured())
            return;

        getPositionConfig("insert");

        if (!board.changeSquareValue(coluna, linha, numero)) {
            System.out.println("A posição informada possui um número fixo, que não pode ser alterado.");
        }
    }

    /*******************************************************************
     * Remove um número do tabuleiro
     *******************************************************************/
    private static void removeNumber() {
        if (checkGameNotConfigured())
            return;

        getPositionConfig("remove");

        if (!board.changeSquareValue(coluna, linha, numero)) {
            System.out.println(String.format(
                    "A posição informada [%s,%s] possui um número fixo, que não pode ser alterado.", coluna, linha));
        }
    }

    /*******************************************************************
     * Monta o tabuleiro na tela
     *******************************************************************/
    private static void viewGame() {
        if (checkGameNotConfigured())
            return;

        // Recupera a configuração das casas do tabuleiro
        List<List<Square>> squares = board.getSquares();

        // Criando um array com 9x9 posições em sequência,
        // que vai permitir substituir as variáveis
        // posicionadas no template do tabuleiro
        // pelos valores armazenados (squares)
        var gameSlots = new Object[81];
        var slotPositions = 0;

        // Percorre a lista externa, que contem os índices das linhas
        for (int i = 0; i < BOARD_LIMIT; i++) {

            // Percorre a lista interna, que contém os índices das colunas
            for (List<Square> square : squares) {

                // O primeiro espaço em branco corresponde
                // ao % das variáveis no template
                gameSlots[slotPositions++] = " "
                        + (isNull(square.get(i).getActual()) ? " " : square.get(i).getActual());
            }
        }
        System.out.println("Visualizando o jogo:");
        System.out.printf(BOARD_TEMPLATE + "\n", gameSlots);
    }

    /*******************************************************************
     * Informa o status atual do jogo
     ************************************************************/
    private static void viewStatus() {
        if (checkGameNotConfigured())
            return;

        System.out.println(String.format("O jogo encontra-se com o seguinte status: %s", board.getStatus().getLabel()));

        if (board.hasAnyError()) {
            System.out.println("O jogo contém algum erro de preenchimento.");
        } else {
            System.out.println("O jogo não tem erros.");
        }
    }

    /*******************************************************************
     * Reinicia o jogo limpando as casas não fixas do tabuleiro
     ************************************************************/
    private static void restartGame() {
        if (checkGameNotConfigured())
            return;

        System.out.println("Tem certeza que deseja limpar o tabuleiro e perder as jogadas realizadas?");
        String confirm = scanner.next();
        while (!confirm.equalsIgnoreCase("sim") && !confirm.equalsIgnoreCase("não")) {
            System.out.println("Informe 'sim' ou 'não'");
            confirm = scanner.next();
        }
        if (confirm.equalsIgnoreCase("sim"))
            board.resetGame();
    }

    private static void endGame() {
        if (checkGameNotConfigured())
            return;

        if (board.checkGameDone()) {
            System.out.println("Parabéns! Você concluiu o jogo!");
        } else if (board.hasAnyError()) {
            System.out.println("Seu jogo contém algum erro. Verifique o tubuleiro e faça os devidos ajustes.");
        } else {
            System.out.println(
                    "As casas do tabuleiro ainda não foram completamente preenchidas. Por favor, verifique...");
        }
    }

    private static boolean checkGameNotConfigured() {
        if (isNull(board)) {
            System.out.println("O tabuleiro ainda não foi configurado.");
            return true;
        }
        return false;
    }

    private static void getPositionConfig(String action) {
        System.out.println("Informe a coluna em que o número será inserido:");
        coluna = getValidRangeNumber(0, 8); // 0 e 8 são os índices do tabuleiro
        System.out.println("Informe a linha em que o número será inserido:");
        linha = getValidRangeNumber(0, 8); // 0 e 8 são os índices do tabuleiro
        if (action != "remove") {
            System.out
                    .println(String.format("Informe o número a ser inserido nas coordenadas [%s,%s]:", coluna, linha));
            numero = getValidRangeNumber(1, 9); // 1 e 9 são os números permitidos no jogo
        }
    }

    private static int getValidRangeNumber(final int min, final int max) {
        int position = scanner.nextInt();
        while (position < min || position > max) {
            System.out.println(String.format("Informe um número entre %s e %s", min, max));
            position = scanner.nextInt();
        }
        return position;
    }
}
