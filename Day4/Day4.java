import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

class Day4{

    public static void main(String[] args) throws IOException{
        String input = Files.readString(Paths.get("", "Day4.txt"));

        System.out.println(
            "sum of pair of one range containing the other: "+part1(input)+"\n"+
            "sum of pair of one range overlaps the other: "+part2(input)
        );
    }

    static int part1(String input){
        return input.lines()
            .map(line -> line.replaceAll(",", " ").replaceAll("-", " "))
            .map(line -> line.split(" "))
            .map(line -> Arrays.stream(line).map(Integer::parseInt).toArray(Integer[]::new))
            .mapToInt(l -> ((l[0]<=l[2] && l[1]>=l[3]) || (l[2]<=l[0] && l[3]>=l[1]))? 1: 0)
            .sum();
    }

    static int part2(String input){
        return input.lines()
            .map(line -> line.replaceAll(",", " ").replaceAll("-", " "))
            .map(line -> line.split(" "))
            .map(line -> Arrays.stream(line).map(Integer::parseInt).toArray(Integer[]::new))
            .mapToInt(l -> ((l[0]>=l[2] && l[0]<=l[3]) || (l[2]>=l[0] && l[2]<=l[1]))? 1: 0)
            .sum();
    }
}
