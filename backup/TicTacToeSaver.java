import java.io.*;

public class TicTacToeSaver {
    public static void saveGameState(int[] field, String filename) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(new FileOutputStream(filename))) {
            // Упаковываем 9 значений (каждое 2 бита) в 3 байта
            int packed = 0;
            for (int i = 0; i < 9; i++) {
                packed |= (field[i] & 0b11) << (i * 2);
            }

            // Записываем 3 байта
            dos.write((packed >> 16) & 0xFF);
            dos.write((packed >> 8) & 0xFF);
            dos.write(packed & 0xFF);
        }
    }

    public static void main(String[] args) {
        int[] field = { 1, 0, 2, 0, 1, 2, 2, 0, 1 }; // Пример состояния поля

        try {
            saveGameState(field, "tictactoe.dat");
            System.out.println("Game state saved successfully");
        } catch (IOException e) {
            System.err.println("Error saving game state: " + e.getMessage());
        }
    }
}