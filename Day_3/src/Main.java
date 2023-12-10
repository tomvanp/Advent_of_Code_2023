import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();

        Path path = Path.of("Day_3/resource/advent_of_code_day_3_input.txt");

        List<String> input= new ArrayList<>();

        try (Stream<String> lines = Files.lines(path)) {
            lines.forEach(input::add);
        }

        int cols = input.get(0).length();
        int rows = input.size();

        String[][] matrix = new String[rows][cols];

        for(int i = 0; i < rows ; i++) {
            char[] chars = input.get(i).toCharArray();
            for(int j =0 ; j < cols ; j++) {
                matrix[i][j] = String.valueOf(chars[j]);
            }
        }
        Pattern pattern = Pattern.compile("(?![\\.\\n])\\W");

        for(int i = 0; i < rows ; i++){
            for(int j = 0; j < cols ; j++) {
                String character = matrix[i][j];
                boolean specialCharacter = character.matches("(?![\\.\\n])\\W");
                if(specialCharacter) {
                    //System.out.println("Found: " + character + " at index: " + i + ", " + j);

                    boolean topLeft = validateTopLeft(matrix, i, j);
                    if (topLeft) {
                        System.out.println(matrix[i-1][j-1]);
                    }
                }
            }
        }

        long finish = System.currentTimeMillis();
        long timeElapsed = finish - start;
        System.out.println("Part One time elapse: " + timeElapsed + " ms");
    }

    static boolean validateTopLeft(String[][] matrix, int i, int j) {
        String topLeft = matrix[i-1][j-1];
        return topLeft.matches("\\d");
    }
}

/*
    int[][] array = new int[rows][columns];

    int value = 1;
        for (int i = 0; i < rows; i++) {
        for (int j = 0; j < columns; j++) {
        array[i][j] = value;
        value++;
        }
        }*/

    /*String[][] array =
            IntStream.range(0, 3)
                    .mapToObj(x -> IntStream.range(0, 3)
                            .mapToObj(y -> String.format("%c%c", letter(x), letter(y)))
                            .toArray(String[]::new))
                    .toArray(String[][]::new);*/
