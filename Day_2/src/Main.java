import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) throws IOException {
        final int redCubes = 12;
        final int greenCubes = 13;
        final int blueCubes = 14;

        Path path = Path.of("Day_2/resource/advent_of_code_day_2_input.txt");

        final List<String> validGamesList = new ArrayList<>();
        try(Stream<String> lines = Files.lines(path)){
            lines.forEach(game -> {
                String[] cubeSets = game.split("[:;,]");
                boolean validGame = false;
                for(var i : cubeSets) {
                    i = i.trim();
                    if (i.contains("green")) {
                        String[] set = i.split("\\s+");
                        validGame = Integer.parseInt(set[0]) <= greenCubes;
                    }
                    else if(i.contains("red")) {
                        String[] set = i.split("\\s+");
                        validGame = Integer.parseInt(set[0]) <= redCubes;
                    }
                    else if(i.contains("blue")) {
                        String[] set = i.split("\\s+");
                        validGame = Integer.parseInt(set[0]) <= blueCubes;
                    }
                    else {
                        continue;
                    }

                    if(!validGame) {
                        break;
                    }
                }
                if (validGame) {
                    validGamesList.add(cubeSets[0]);
                }
            });
        }

        var sumGames = validGamesList.stream().map(i -> {
            String[] set = i.split("\\s+");
            //System.out.println("Number of valid game: " + set[1]);
            return set[1];
        }).map(Integer::parseInt).reduce(0, Integer::sum);

        System.out.println("sum of all valid games: " + sumGames);
    }
}