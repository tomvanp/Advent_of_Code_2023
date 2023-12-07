import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) throws IOException
    {
        long start = System.currentTimeMillis();
        Path path = Path.of("./Day_1/resource/adventofcode_day1_input.txt");

        try (Stream<String> s = Files.lines(path)) {
            var t = s.map(String::chars);
            var f = t.map(i -> i.mapToObj(r -> (char) r)
                    .filter(Character::isDigit)
                    .map(String::valueOf)
                    .collect(Collectors.joining()));

            var a = f.map(n -> {
                var i = n.length();
                return String.valueOf(n.charAt(0)) + n.charAt(i - 1);
            }).map(Integer::valueOf).reduce(0, Integer::sum);
            System.out.println(a);

            long finish = System.currentTimeMillis();
            long timeElapsed = finish - start;
            System.out.println(timeElapsed + " ms");
        }

    }
}