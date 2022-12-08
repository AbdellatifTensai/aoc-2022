import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

class Day8{

    public static void main(String[] args) throws IOException{
        var input = Files.lines(Paths.get("", "Day8.txt")).toList();

        System.out.println(
            "number of trees visible: "+part1(input)+"\n"+
            "highest scenic score: "+part2(input)
        );

    }

    static int part1(List<String> input){

        int edges = input.get(0).length()*4 - 4;
        int height = input.size();
        int width = input.get(0).length();
        int visibleTrees =0;

        for(int y=1;y<height-1;y++)
            for(int x=1;x<width-1;x++)
                if(checkDirections(input, input.get(y).charAt(x), y, x, width, height))
                    visibleTrees++;

        return visibleTrees + edges;
    }

    static int part2(List<String> input){
        int height = input.size();
        int width = input.get(0).length();
        var treesVisibility = new ArrayList<Integer>();

        for(int y=1;y<height-1;y++)
            for(int x=1;x<width-1;x++)
                treesVisibility.add(scenicScore(input, input.get(y).charAt(x), y, x, width, height));

        return treesVisibility.stream().mapToInt(x->x).max().getAsInt();
    }

    static int scenicScore(List<String> grid, char c, int Yindex, int Xindex, int width, int height){
        int top =0, bottom=0, left=0, right=0; 

        for(int row = Xindex+1; row<width; row++)
            if(grid.get(Yindex).charAt(row) < c){
                right++;
            }else{
                right++; break;
            } 

        for(int row = Xindex-1; row>=0; row--)
            if(grid.get(Yindex).charAt(row) < c){
                left++;
            }else{
                left++; break;
            } 

        for(int col = Yindex+1; col<height; col++)
            if(grid.get(col).charAt(Xindex) < c){
                top++;
            }else{
                top++; break;
            } 

        for(int col = Yindex-1; col>=0; col--)
            if(grid.get(col).charAt(Xindex) < c){
                bottom++;
            }else{
                bottom++; break;
            } 

        return top*bottom*left*right;
    }


    static boolean checkDirections(List<String> grid, char c, int Yindex, int Xindex, int width, int height){
        int directions = 0b1111;

        for(int row = Xindex+1; row<width; row++)
            if(grid.get(Yindex).charAt(row) >= c){
                directions &= 0b1110; break;
            }

        for(int row = Xindex-1; row>=0; row--)
            if(grid.get(Yindex).charAt(row) >= c){
                directions &= 0b1101; break;
            }

        for(int col = Yindex+1; col<height; col++)
            if(grid.get(col).charAt(Xindex) >= c){
                directions &= 0b1011; break;
            }

        for(int col = Yindex-1; col>=0; col--)
            if(grid.get(col).charAt(Xindex) >= c){
                directions &= 0b0111; break;
            }

        return directions != 0;
    }

}
