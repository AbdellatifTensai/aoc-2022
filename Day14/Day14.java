import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

class Day14{

    private static final int AIR = 0,     ROCK = 1,       SAND = 2,         Y = 1
                              ,X = 0, SOURCE_X = 500, SOURCE_Y = -1,    RIGHT = 1
                           ,LEFT = -1, FORWARD = 1,      WIDTH = 1024, HEIGHT = 256;

    public static void main(String[] args) throws IOException{
        var input = Files.lines(Paths.get("", "Day14.txt")).toList();

        System.out.println(
            "number of units of sand: "+part1(input)+"\n"+
            "number of untis of sand to rest: "+ part2(input)
        );

    }
    static int part1(List<String> input){
        return simulateSand(generateGrid(input));
    }

    static int part2(List<String> input){
        var floor = getMaxY(input) + 2;
        var list = new ArrayList<>(input);
        list.add(0+","+floor+" -> "+(WIDTH-1)+","+floor);
        return simulateSand(generateGrid(list));
    }

    static int simulateSand(int[][] grid){
        int units = 0;
        int x = SOURCE_X;
        for(int y = SOURCE_Y; y<grid.length-1; y++){

            if(grid[y+FORWARD][x] == AIR) continue;

            if(grid[y+FORWARD][x + LEFT] == AIR){
                x += LEFT;
                continue;
            }
            else if(grid[y+FORWARD][x + RIGHT] == AIR){
                x += RIGHT;
                continue;
            }
            else if(y == SOURCE_Y+1 && x == SOURCE_X){
                units++;
                break;
            }
            else{
                grid[y][x] = SAND;
                x = SOURCE_X;
                y = SOURCE_Y;
            }
            units++;
        }

        return units;
    }

    static int[][] generateGrid(List<String> input){
        int[][] grid = new int[HEIGHT][WIDTH]; //large enough?

        input.stream().map(line -> Arrays.stream(line.split(" -> ")))
            .map(line -> line.map(coord -> coord.split(",")))
            .forEach(line -> 
                line.reduce((from, to)->{

                    int fromX = Integer.parseInt(from[X]);
                    int toX = Integer.parseInt(to[X]);
                    int temp = fromX;
                    fromX = fromX <= toX? fromX: toX;
                    toX = temp<= toX? toX: temp;

                    int fromY = Integer.parseInt(from[Y]);
                    int toY = Integer.parseInt(to[Y]);
                    temp = fromY;
                    fromY = fromY <= toY? fromY: toY;
                    toY = temp<= toY? toY: temp;

                    for(int y=fromY;y<=toY;y++)
                        for(int x=fromX;x<=toX;x++)
                            grid[y][x] = ROCK;

                    return to;
                })
        );

        return grid;
    }

    static int getMaxY(List<String> input){
        String str = input.stream().collect(Collectors.joining(" "));
        var matcher = Pattern.compile("[0-9]+,[0-9]+").matcher(str);
        var listOfY = new ArrayList<Integer>();
        while(matcher.find()){
            var numsString = matcher.group().split(",");
            listOfY.add(Integer.parseInt(numsString[1])); 
        }
        return listOfY.stream().mapToInt(x->x).max().getAsInt();
    }

}
