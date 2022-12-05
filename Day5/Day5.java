import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

class Day5 {

    public static void main(String[] args) throws IOException {
        String input = Files.readString(Paths.get("", "Day5.txt"));
        var cratesAndMoves = input.split("\n\n");

        System.out.println(
                "crates on top of each stack reverse order: " + part1(parseCrates(cratesAndMoves[0]), parseMoves(cratesAndMoves[1])) + "\n" +
                "crates on top of each stack normal order: " + part2(parseCrates(cratesAndMoves[0]), parseMoves(cratesAndMoves[1]))
        );
    }

    static String part1(Map<Integer, List<Character>> crates, List<int[]> moves) {

        for (var move : moves) {
            var from = crates.get(move[1]);
            var to = crates.get(move[2]);
            var cratesToMove = new ArrayList<>(from.subList(0, move[0]));

            from = new ArrayList<>(from.subList(move[0], from.size()));

            var sortedCrates = new ArrayList<Character>();
            for (int x = cratesToMove.size() - 1; x >= 0; x--)
                sortedCrates.add(cratesToMove.get(x));

            // cratesToMove.sort((c1,c2) -> cratesToMove.indexOf(c1) > cratesToMove.indexOf(c2)? -1:0);
            // took two hours to find this bug, if a list has identical chars it picks the first index!!

            to.addAll(0, sortedCrates);

            crates.put(move[1], from);
            crates.put(move[2], to);
        }

        return crates.values()
                .stream()
                .map(list -> list.get(0).toString())
                .collect(Collectors.joining());
    }

    static String part2(Map<Integer, List<Character>> crates, List<int[]> moves) {

        for (var move : moves) {
            var from = crates.get(move[1]);
            var to = crates.get(move[2]);
            var cratesToMove = new ArrayList<>(from.subList(0, move[0]));

            from = new ArrayList<>(from.subList(move[0], from.size()));

            to.addAll(0, cratesToMove);

            crates.put(move[1], from);
            crates.put(move[2], to);
        }

        return crates.values()
                .stream()
                .map(list -> list.get(0).toString())
                .collect(Collectors.joining());
    }

    static List<int[]> parseMoves(String moves) {
        var pattern = Pattern.compile("[0-9]+");
        return moves.lines()
                .map(line -> {
                    var matcher = pattern.matcher(line);
                    int[] parsedMoves = new int[3];

                    for (int x = 0; matcher.find(); x++)
                        parsedMoves[x] = Integer.parseInt(matcher.group());

                    return parsedMoves;
                })
                .collect(Collectors.toList());
    }

    static Map<Integer, List<Character>> parseCrates(String crates) {
        var map = new HashMap<Integer, List<Character>>();
        int length = crates.split("\n")[0].length();

        crates.lines().forEach(line -> {
            for (int x = 1; x < length; x += 4) {
                int index = ((x - 1) / 4) + 1;
                Character c = line.charAt(x);

                if (!map.containsKey(index))
                    map.put(index, new ArrayList<>());
                if (Character.isDigit(c))
                    break;
                if (c != ' ')
                    map.get(index).add(c);
            }
        });

        return map;
    }
}
