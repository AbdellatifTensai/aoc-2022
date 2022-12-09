import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

class Day9{

    public static void main(String[] args) throws IOException{
        var input = Files.lines(Paths.get("", "Day9.txt")).toList();
        
        System.out.println(
            "number of uniquely visited places: "+part1(input)+"\n"+
            "number of uniquely visited places by tails: "+part2(input)
        );
    }
    
    static int part2(List<String> input){

        String seq = input.stream()
        .map(line -> line.split(" "))
        .map(line -> line[0].repeat(Integer.parseInt(line[1])))
        .collect(Collectors.joining());
        
        var uniqueTailsPositions = new HashSet<List<Integer>>();
        uniqueTailsPositions.add(Arrays.asList(0,0));

        final int x =0, y=1;
        int[][] rope = new int[10][2]; 

        for(var c: seq.toCharArray()){
            switch(c){
            case 'U': rope[0][y]++; break;
            case 'D': rope[0][y]--; break;
            case 'R': rope[0][x]++; break;
            case 'L': rope[0][x]--; break;
            }

            for(int a=0, b=1;a<rope.length-1;a++,b++){
                if((rope[a][x] - rope[b][x]) > 1){
                    rope[b][x]++;
                    rope[b][y] += (rope[a][y] - rope[b][y]) > 0? 1: (rope[a][y] - rope[b][y]) < 0? -1: 0;
                }
                else if(((rope[a][x] - rope[b][x])) < -1){
                    rope[b][x]--;
                    rope[b][y] += (rope[a][y] - rope[b][y]) > 0? 1: (rope[a][y] - rope[b][y]) < 0? -1: 0;
                }

                if(((rope[a][y] - rope[b][y])) > 1){
                    rope[b][y]++;
                    rope[b][x] += (rope[a][x] - rope[b][x]) > 0? 1: (rope[a][x] - rope[b][x]) < 0? -1: 0;
                }
                else if(((rope[a][y] - rope[b][y])) < -1){
                    rope[b][y]--;
                    rope[b][x] += (rope[a][x] - rope[b][x]) > 0? 1: (rope[a][x] - rope[b][x]) < 0? -1: 0;
                }
            }

            uniqueTailsPositions.add(Arrays.asList(rope[9][x],rope[9][y]));
        }

        return uniqueTailsPositions.size();
    }

    static int part1(List<String> input){

        String seq = input.stream()
        .map(line -> line.split(" "))
        .map(line -> line[0].repeat(Integer.parseInt(line[1])))
        .collect(Collectors.joining());
        
        var uniqueTPositions = new HashSet<List<Integer>>();
        uniqueTPositions.add(Arrays.asList(0,0));

        int Ux=0, Uy=0, Tx=0, Ty=0; 
        for(var c: seq.toCharArray()){
            switch(c){
            case 'U': Uy++; break;
            case 'D': Uy--; break;
            case 'R': Ux++; break;
            case 'L': Ux--; break;
            }

            if((Ux - Tx) > 1){
                Tx++;
                Ty += (Uy - Ty) > 0? 1: (Uy - Ty) < 0? -1: 0;
            }
            else if((Ux - Tx) < -1){
                Tx--;
                Ty += (Uy - Ty) > 0? 1: (Uy - Ty) < 0? -1: 0;
            }

            if((Uy - Ty) > 1){
                Ty++;
                Tx += (Ux - Tx) > 0? 1: (Ux - Tx) < 0? -1: 0;
            }
            else if((Uy - Ty) < -1){
                Ty--;
                Tx += (Ux - Tx) > 0? 1: (Ux - Tx) < 0? -1: 0;
            }

            uniqueTPositions.add(Arrays.asList(Tx,Ty));
        }
        
        return uniqueTPositions.size();
    }

}