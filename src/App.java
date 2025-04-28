import static java.util.Objects.*;

import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import model.Board;

public class App {

    private final static Scanner scanner = new Scanner(System.in);
    private static Board board;
    private final static int BOARD_LIMIT = 9;

    public static void main(String[] args) throws Exception {

        // Os arqumentos chegam com a seguinte estrutura: "0,0;4,false"
        // .collect() funciona como uma função .reduce() retornando os argumentos
        // passados modificados da forma desejada: convertendo-os em um mapa em que
        // as chaves são as coordenadas (col=0, row=0) com seus respectivos valores
        // (valor=4, fixed=false)
        final Map<String, String> positions = Stream.of(args).collect(
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
                    finishGame();
                    break;
                default:
                    System.out.println("Opção inválida!");
                    break;
            }
        }
    }

    private static void startGame(Map<String, String> positions) {
        if (nonNull(board)) {
            System.out.println("O jogo já foi iniciado anteriormente.");
            return;
        }
    }

    private static void insertNumber() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'insertNumber'");
    }

    private static void removeNumber() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'removeNumber'");
    }

    private static void viewGame() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'viewGame'");
    }

    private static void viewStatus() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'viewStatus'");
    }

    private static void restartGame() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'restartGame'");
    }

    private static void finishGame() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'finishGame'");
    }

}
