import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;

class Day6{

    public static void main(String[] args) throws IOException{
        String input = Files.readString(Paths.get("", "Day6.txt"));

        System.out.println(
            "number of characters before start of packet: "+part1(input)+"\n"+
            "number of characters before start of message: "+part2(input)
        );
    }

    static int part1(String input){
        for(int x=0;x<input.length()-3;x++){

            char[] chars = { input.charAt(x), input.charAt(x+1), input.charAt(x+2), input.charAt(x+3) };

            //brute force :P
            if(chars[0]!= chars[1] && chars[0]!= chars[2] && chars[0]!= chars[3]
               && chars[1]!= chars[2] && chars[1]!= chars[3] && chars[2]!= chars[3])
                return x+4;
        }
        return 0;
    }

    static int part2(String input){
        Set set = new HashSet<Character>();

        for(int x=0;x<input.length()-14;x++){

            for(int y=x;y<14+x;y++)
                set.add(input.charAt(y));

            if(set.size() == 14) return x+14;

            set.clear();
        }

        return 0;
    }
}
