import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

class Day3{

    public static void main(String[] args) throws IOException{
        String input = Files.readString(Paths.get("", "Day3.txt"));
        System.out.println(
            "sum of all priorities of items occuring twice : "+ part1(input)+"\n"+
            "sum of all priorities of badges occuring three times: "+ part2(input)
        );
    }

    static int part1(String input){
        return input.lines()
            .map(line -> new String[]{line.substring(0, (line.length()/2)), line.substring(line.length()/2, line.length())})
            .map(line ->{for(char c1: line[0].toCharArray()) for(char c2: line[1].toCharArray()) if(c1 == c2) return c1; return '0';})
            .mapToInt(c -> Character.isLowerCase(c)? c -'a'+1: c-38)
            .sum();
    }

    static int part2(String input){
        var inputList = input.lines().toList();
        var list = new ArrayList<String[]>();
        for(int x=0;x<inputList.size();x+=3)
            list.add(new String[]{inputList.get(x),inputList.get(x+1),inputList.get(x+2)});

        return list.stream()
                   .mapToInt(grp -> {
                        for(char c1: grp[0].toCharArray())
                          for(char c2: grp[1].toCharArray())
                            for(char c3: grp[2].toCharArray())
                              if(c1 == c2 && c1 == c3)
                                return Character.isLowerCase(c1)? c1-'a'+1: c1-38;
                        return 0;
                    })
                   .sum();
    }
}
