import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {
    enum Numbers {
        ONE(1, "one"),
        TWO(2, "two"),
        THREE(3, "three"),
        FOUR(4, "four"),
        FIVE(5, "five"),
        SIX(6, "six"),
        SEVEN(7, "seven"),
        EIGHT(8, "eight"),
        NINE(9, "nine");

        private final int id;
        private final String description;

        Numbers(int id, String description) {
            this.id = id;
            this.description = description;
        }
    }

    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();
        String path ="Day_1/resource/adventofcode_day1_input.txt";

        try(var reader = new BufferedReader(new FileReader(path))){
            String line;
            List<String> allNumbers = new ArrayList<>();
            while((line = reader.readLine()) != null) {
                int[] numbers = new int[line.length()];
                for(Numbers n : Numbers.values()) {
                    int index = 0;
                    while(index < line.length()){
                        char c = line.charAt(index);
                        if(Character.isDigit(c)){
                            numbers[index] = Character.getNumericValue(c);
                        } else {
                            int ptrnLength = n.description.length();
                            if((index + ptrnLength) <= line.length() && n.description.equals(line.substring(index, index+ptrnLength))) {
                                numbers[index] = n.id;
                            }
                        }
                        index++;
                    }
                }
                numbers = Arrays.stream(numbers)
                        .filter(i -> i > 0)
                        .toArray();
                allNumbers.add(String.valueOf(numbers[0]) + numbers[numbers.length-1]);
            }
            int sumCalibrationValues =  allNumbers.stream().map(Integer::valueOf).reduce(0, Integer::sum);
            System.out.println(sumCalibrationValues);
        }

        long finish = System.currentTimeMillis();
        long timeElapsed = finish - start;
        System.out.println(timeElapsed + " ms");
    }


    /*
        This is the code snippet used as a solution for Part 1 of the
        advent of code Day 1 puzzle
     */
    public static void PartOne() throws IOException {
        Path path = Path.of("Day_1/resource/adventofcode_day1_input.txt");

        try (Stream<String> lines = Files.lines(path)) {
            var sumCalibrationValues = lines.map(line -> {
                var sb = new StringBuilder(line);
                for (Numbers n : Numbers.values()) {
                    while (sb.toString().contains(n.description)) {
                        int startIndex = sb.indexOf(n.description);
                        int endIndex = startIndex + n.description.length();
                        sb.replace(startIndex, endIndex, String.valueOf(n.id));
                    }
                }
                return sb.toString();
            })
            .map(String::chars)
            .map(i -> i.mapToObj(r -> (char) r)
                    .filter(Character::isDigit)
                    .map(String::valueOf)
                    .collect(Collectors.joining()))
            .map(n -> {
                var i = n.length();
                return String.valueOf(n.charAt(0)) + n.charAt(i - 1);
            })
            .peek(System.out::println)
            .map(Integer::valueOf).reduce(0, Integer::sum);

            System.out.println(sumCalibrationValues);
        }
    }
}