import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;

class Day1{

    public static void main(String[] args) throws IOException{
        String input = Files.readString(Paths.get("", "Day1.txt"));

        System.out.println(
            "biggest number of calories: " + part1(input)+"\n"+
            "sum of top three number of calories: " + part2(input)
        );
    }

    static int part2(String input){
        return Arrays.stream(input.split("\n\n"))
            .map(s -> Arrays.stream(s.split("\n")))
            .map(s -> s.mapToInt(Integer::parseInt).sum())
            .sorted(Collections.reverseOrder())
            .limit(3)
            .reduce(0, Integer::sum);
    }

    static int part1(String input){
        return Arrays.stream(input.split("\n\n"))
            .map(s -> Arrays.stream(s.split("\n")))
            .mapToInt(s -> s.mapToInt(Integer::parseInt).sum())
            .max()
            .getAsInt();
    }
}
