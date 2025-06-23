import java.io.*;

public class TicTacToeLoader {
    public static int[] loadGameState(String filename) throws IOException {
        int[] field = new int[9];

        try (DataInputStream dis = new DataInputStream(new FileInputStream(filename))) {
            // Читаем 3 байта
            int byte1 = dis.read() & 0xFF;
            int byte2 = dis.read() & 0xFF;
            int byte3 = dis.read() & 0xFF;

            // Объединяем в одно целое число
            int packed = (byte1 << 16) | (byte2 << 8) | byte3;

            // Распаковываем значения
            for (int i = 0; i < 9; i++) {
                field[i] = (packed >> (i * 2)) & 0b11;
            }
        }

        return field;
    }

    public static void main(String[] args) {
        try {
            int[] field = loadGameState("tictactoe.dat");
            System.out.println("Loaded game state:");

            // Выводим поле 3x3
            for (int i = 0; i < 9; i++) {
                char c = switch (field[i]) {
                    case 0 -> '.';
                    case 1 -> 'X';
                    case 2 -> 'O';
                    default -> '?';
                };
                System.out.print(c + " ");
                if ((i + 1) % 3 == 0)
                    System.out.println();
            }
        } catch (IOException e) {
            System.err.println("Error loading game state: " + e.getMessage());
        }
    }
}