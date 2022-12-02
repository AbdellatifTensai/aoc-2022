import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

class Day2{

    public static void main(String[] args) throws IOException{
        String input = Files.readString(Paths.get("", "Day2.txt"));
        System.out.println(
            "sum of all results " + part1(input) +"\n"+
            "sum of all other results " + part2(input)
        );
    }

    static int part2(String input){
        return input.lines()
            .map(line -> line.split(" "))
            .map(line -> new int[]{line[0].charAt(0)-'A'+1, line[1].charAt(0)-'X'+1})
            .mapToInt(nums -> {
                    if(nums[1] == 1)
                        return (nums[0]-1) == 0? 3: nums[0]-1;

                    else if(nums[1] == 2)
                        return nums[0] + 3;

                    else if(nums[1] == 3)
                        return (nums[0] + 1) == 4? 7: nums[0]+7;
                    else return 0;
                })
            .sum();
    }

    static int part1(String input){
        return input.lines()
            .map(line -> line.split(" "))
            .map(line -> new int[]{line[0].charAt(0)-'A'+1, line[1].charAt(0)-'X'+1})
            .mapToInt(nums -> {
                    if(nums[1] == 1 && nums [0] == 3)
                        return nums[1] +6;
                    else if(nums[1] == 3 && nums [0] == 1)
                        return nums[1];
                    else if(nums[1] > nums[0])
                        return nums[1] + 6;
                    else if(nums[1] == nums[0])
                        return nums[1] + 3;
                    else return nums[1];
                })
            .sum();
    }
}
