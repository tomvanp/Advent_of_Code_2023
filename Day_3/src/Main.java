
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
    https://adventofcode.com/2023/day/3
    This is some not so pretty code written as a solution for the Advent of Code, Day 3 puzzle.
    The idea is that iterating over de String grid and when a special character is encountered, validate
    the cells around it for digits. If a digit is found, validate the cells left and right of it for other digits until none is found.
    Change all valid digits in the grid to dots, as to not count duplicates.
    The total number parts is: 535235
 */
public class Main {

    static String numberRegex = "\\d";

    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();

        Day3Part1();

        long finish = System.currentTimeMillis();
        long timeElapsed = finish - start;
        System.out.println("Part One time elapse: " + timeElapsed + " ms");
    }

    static void Day3Part1() throws IOException {

        //Path path = Path.of("Day_3/resource/test_input.txt");
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

        List<String[]> allNumbers = new ArrayList<>();

        for(int i = 0; i < rows ; i++){
            for(int j = 0; j < cols ; j++) {
                String character = matrix[i][j];
                boolean specialCharacter = character.matches("(?![\\.\\n])\\W");
                if(specialCharacter) {
                    boolean topCenter = matrix[i-1][j].matches(numberRegex);
                    if (topCenter) {

                        boolean left = matrix[i-1][j-1].matches(numberRegex);
                        boolean farLeft = matrix[i-1][j-2].matches(numberRegex);

                        if ( left && farLeft ) {
                            String[] topNumber = new String[3];
                            topNumber[0] = matrix[i-1][j-2];
                            topNumber[1] = matrix[i-1][j-1];
                            topNumber[2] = matrix[i-1][j];

                            matrix[i-1][j] = ".";
                            matrix[i-1][j-1] = ".";
                            matrix[i-1][j-2] = ".";

                            allNumbers.add(topNumber);

                        }
                        else if (left) {
                            String[] topNumber = new String[3];
                            topNumber[1] = matrix[i-1][j];
                            topNumber[0] = matrix[i-1][j-1];
                            topNumber[2] = matrix[i-1][j+1].matches(numberRegex) ? matrix[i-1][j+1] : "";

                            matrix[i-1][j] = ".";
                            matrix[i-1][j-1] = ".";
                            matrix[i-1][j+1] = matrix[i-1][j+1].matches(numberRegex) ? "." : matrix[i-1][j+1];

                            allNumbers.add(topNumber);

                        }
                        else {
                            boolean right = matrix[i-1][j+1].matches(numberRegex);
                            boolean farRight= matrix[i-1][j+2].matches(numberRegex);

                            if ( right && farRight) {
                                String[] topNumber = new String[3];
                                topNumber[0] = matrix[i-1][j];
                                topNumber[1] = matrix[i-1][j+1];
                                topNumber[2] = matrix[i-1][j+2];

                                matrix[i-1][j] = ".";
                                matrix[i-1][j+1] = ".";
                                matrix[i-1][j+2] = matrix[i-1][j+2].matches(numberRegex) ? "." : matrix[i-1][j+2];

                                allNumbers.add(topNumber);

                            } else if (right) {
                                String[] topNumber = new String[3];
                                topNumber[1] = matrix[i-1][j];
                                topNumber[0] = matrix[i-1][j-1].matches(numberRegex) ? matrix[i-1][j-1] : "";
                                topNumber[2] = matrix[i-1][j+1];

                                matrix[i-1][j] = ".";
                                matrix[i-1][j-1] = matrix[i-1][j-1].matches(numberRegex) ? "." : matrix[i-1][j-1];
                                matrix[i-1][j+1] = ".";

                                allNumbers.add(topNumber);

                            } else {
                                String[] topNumber = new String[3];
                                topNumber[0] = matrix[i-1][j];
                                matrix[i-1][j] = ".";

                                allNumbers.add(topNumber);
                            }
                        }
                    }
                    else {

                        boolean topLeft = matrix[i-1][j-1].matches(numberRegex);
                        if(topLeft) {
                            String[] topNumber = new String[3];

                            topNumber[2] = matrix[i-1][j-1];
                            topNumber[1] = matrix[i-1][j-2].matches(numberRegex) ? matrix[i-1][j-2] : "";
                            topNumber[0] = matrix[i-1][j-3].matches(numberRegex) ? matrix[i-1][j-3] : "";
                            allNumbers.add(topNumber);

                            matrix[i-1][j-1] = ".";
                            matrix[i-1][j-2] = matrix[i-1][j-2].matches(numberRegex) ? "." : matrix[i-1][j-2];
                            matrix[i-1][j-3] = matrix[i-1][j-3].matches(numberRegex) ? "." : matrix[i-1][j-3];


                        }

                        boolean topRight = matrix[i-1][j+1].matches(numberRegex);
                        if(topRight) {
                            String[] topNumber = new String[3];

                            topNumber[0] = matrix[i-1][j+1];
                            matrix[i-1][j+1] = ".";

                            if (matrix[i-1][j+2].matches(numberRegex)) {
                                topNumber[1] = matrix[i-1][j+2];
                                matrix[i-1][j+2] = ".";
                            }
                            if (matrix[i-1][j+3].matches(numberRegex)) {
                                topNumber[2] = matrix[i-1][j+3];
                                matrix[i-1][j+3] = ".";
                            }

                            allNumbers.add(topNumber);
                        }
                    }

                    boolean bottomCenter = matrix[i+1][j].matches(numberRegex);
                    if (bottomCenter) {

                        boolean left = matrix[i+1][j-1].matches(numberRegex);
                        boolean farLeft = matrix[i+1][j-2].matches(numberRegex);

                        if ( left && farLeft ) {
                            String[] bottomNumber = new String[3];
                            bottomNumber[0] = matrix[i+1][j-2];
                            bottomNumber[1] = matrix[i+1][j-1];
                            bottomNumber[2] = matrix[i+1][j];
                            allNumbers.add(bottomNumber);

                            matrix[i+1][j-2] = ".";
                            matrix[i+1][j-1] = ".";
                            matrix[i+1][j] = ".";

                        } else if (left) {
                            String[] bottomNumber = new String[3];
                            bottomNumber[1] = matrix[i+1][j];
                            matrix[i+1][j] = ".";

                            bottomNumber[0] = matrix[i+1][j-1];
                            matrix[i+1][j-1] = ".";

                            if (matrix[i+1][j+1].matches(numberRegex)) {
                                bottomNumber[2] = matrix[i+1][j+1];
                                matrix[i+1][j+1] = ".";
                            }
                            allNumbers.add(bottomNumber);

                        } else {
                            boolean right = matrix[i+1][j+1].matches(numberRegex);
                            boolean farRight= matrix[i+1][j+2].matches(numberRegex);

                            if ( right && farRight) {
                                String[] bottomNumber = new String[3];
                                bottomNumber[0] = matrix[i+1][j];
                                bottomNumber[1] = matrix[i+1][j+1];
                                bottomNumber[2] = matrix[i+1][j+2];

                                matrix[i+1][j+2] = ".";
                                matrix[i+1][j+1] = ".";
                                matrix[i+1][j] = ".";
                                allNumbers.add(bottomNumber);
                            } else if (right) {
                                String[] bottomNumber = new String[3];
                                bottomNumber[1] = matrix[i+1][j];
                                matrix[i+1][j] = ".";

                                if(matrix[i+1][j-1].matches(numberRegex)) {
                                    bottomNumber[0] = matrix[i+1][j-1];
                                    matrix[i+1][j-1] = ".";
                                }

                                bottomNumber[2] = matrix[i+1][j+1];
                                matrix[i+1][j+1] = ".";
                                allNumbers.add(bottomNumber);
                            } else {
                                String[] bottomNumber = new String[3];
                                bottomNumber[0] = matrix[i+1][j];
                                matrix[i+1][j] = ".";
                                allNumbers.add(bottomNumber);
                            }
                        }
                    }
                    else {

                        boolean bottomLeft = matrix[i+1][j-1].matches(numberRegex);
                        if(bottomLeft) {
                            String[] bottomNumber = new String[3];
                            bottomNumber[2] = matrix[i+1][j-1];
                            matrix[i+1][j-1] = ".";

                            if(matrix[i+1][j-2].matches(numberRegex)) {
                                bottomNumber[1] = matrix[i+1][j-2];
                                matrix[i+1][j-2] = ".";
                            }

                            if(matrix[i+1][j-3].matches(numberRegex)) {
                                bottomNumber[0] = matrix[i+1][j-3];
                                matrix[i+1][j-3] = ".";
                            }
                            allNumbers.add(bottomNumber);
                        }

                        boolean bottomRight = matrix[i+1][j+1].matches(numberRegex);
                        if(bottomRight) {
                            String[] bottomNumber = new String[3];
                            bottomNumber[0] = matrix[i+1][j+1];
                            matrix[i+1][j+1] = ".";

                            if (matrix[i+1][j+2].matches(numberRegex)) {
                                bottomNumber[1] = matrix[i+1][j+2];
                                matrix[i+1][j+2] = ".";
                            }

                            if(matrix[i+1][j+3].matches(numberRegex)) {
                                bottomNumber[2] = matrix[i+1][j+3];
                                matrix[i+1][j+3] = ".";
                            }

                            allNumbers.add(bottomNumber);
                        }
                    }

                    boolean left = matrix[i][j-1].matches(numberRegex);
                    if ( left ) {
                        String[] leftNumber = new String[3];
                        leftNumber[2] = matrix[i][j-1];
                        matrix[i][j-1] = ".";

                        boolean isLeftNumber = matrix[i][j-2].matches(numberRegex);
                        if (isLeftNumber) {
                            leftNumber[1] = matrix[i][j-2];
                            matrix[i][j-2] = ".";
                        }

                        if (isLeftNumber && matrix[i][j-3].matches(numberRegex)) {
                            leftNumber[0] = matrix[i][j-3];
                            matrix[i][j-3] = ".";
                        }

                        allNumbers.add(leftNumber);
                    }

                    boolean right = matrix[i][j+1].matches(numberRegex);
                    if ( right ) {
                        String[] rightNumber = new String[3];
                        rightNumber[0] = matrix[i][j+1];
                        matrix[i][j+1]= ".";

                        boolean isRightNumber = matrix[i][j+2].matches(numberRegex);
                        if(isRightNumber) {
                            rightNumber[1] = matrix[i][j+2];
                            matrix[i][j+2]= ".";
                        }
                        if(isRightNumber && matrix[i][j+3].matches(numberRegex)) {
                            rightNumber[2] = matrix[i][j+3];
                            matrix[i][j+3]= ".";
                        }

                        allNumbers.add(rightNumber);
                    }
                }
            }
        }

/*        for(int i = 0; i < rows ; i++) {
            System.out.println(Arrays.toString(matrix[i]));
        }*/

        int total = allNumbers.stream()
                .map(i ->
                        Arrays.stream(i).filter(Objects::nonNull).map(j -> String.join("", j).trim()).collect(Collectors.joining())
                )
                .map(Integer::valueOf)
                .reduce(0, Integer::sum);

        System.out.println("The total number parts is: " + total);
    }
}