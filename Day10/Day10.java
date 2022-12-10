import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class Day10{

    public static void main(String[] args) throws IOException{
        var input = Files.lines(Paths.get("", "Day10.txt")).toList();
        var test = Files.lines(Paths.get("", "test.txt")).toList();

        System.out.println(
            "sum of the six signal strengths: "+part1(test)+"\n"+
            "final rendered image: \n"+part2(input)
        );
    }

    static int part1(final List<String> input){
        var marks = Arrays.asList( 20, 60, 100, 140, 180, 220 );
        var signalStrengths = new ArrayList<Integer>();
        int cycles =0;
        int totalValue =1;

        for(var line: input){
            if(line.equals("noop")){
                cycles++;
                if(marks.contains(cycles)) signalStrengths.add(totalValue*cycles);
            }
            else{
                cycles++;
                if(marks.contains(cycles)) signalStrengths.add(totalValue*cycles);
                cycles++;
                if(marks.contains(cycles)) signalStrengths.add(totalValue*cycles);
                totalValue += Integer.parseInt(line.split(" ")[1]);
            }
        }

        return signalStrengths.stream().mapToInt(x->x).sum();
    }

    static String part2(final List<String> input){
        int pos =0;
        int totalValue =1;
        String output = "";

        for(var line: input){
            if(line.equals("noop")){
                output += (pos >= totalValue-1 && pos <= totalValue+1)? "#": ".";
                pos = (pos +1) % 40;
                if(pos == 0) output+= "\n";
            }
            else{
                output += (pos >= totalValue-1 && pos <= totalValue+1)? "#": ".";
                pos = (pos +1) % 40;
                if(pos == 0) output+= "\n";
                output += (pos >= totalValue-1 && pos <= totalValue+1)? "#": ".";
                pos = (pos +1) % 40;
                if(pos == 0) output+= "\n";
                totalValue += Integer.parseInt(line.split(" ")[1]);
            }
        }

        return output;
    }
}
