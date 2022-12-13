import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

class Day13{

    public static void main(String[] args) throws IOException{
        String input = Files.readString(Paths.get("","Day13.txt"));
        String test = Files.readString(Paths.get("","test.txt"));

        System.out.println(
            "sum of indicies with the right order: "+part1(test)
        );
    }

    static int part1(String input){

        var pairs = Arrays.stream(input.split("\n\n"))
                        .map(pair -> Arrays.stream(pair.split("\n")))
                        .map(pair -> pair.map(line -> line.chars().mapToObj(x->(char)x).iterator()))
                        .map(pair -> pair.peek(it -> it.next()))
                        .map(pair -> pair.map(Day13::parseInput).toList())
                        .toList();

        int indices =0;
        for(int x=0;x<pairs.size();x++){
            var pair = pairs.get(x);
            var first = pair.get(0);
            var second = pair.get(1);
            if(compare(first, second) == -1){
                indices += (x+1);            
            }
        }

        return indices;
    }

    static int compare(Node first, Node second){
        if(first.hasValue() && second.hasValue()){
            if(first.value < second.value)
                return -1;
            else if(first.value == second.value)   
                return 0;
            else 
                return 1;
        }
        else if(!first.hasValue() && second.hasValue()){
            second.childs = new ArrayList<>();
            second.childs.add(new Node(second.value));
        }
        else if(first.hasValue() && !second.hasValue()){
            first.childs = new ArrayList<>();
            first.childs.add(new Node(first.value));
        }
        
        int firstLength = first.childs.size();
        int secondLength = second.childs.size();
        int length = firstLength<=secondLength? firstLength: secondLength;
        int r = 0;

        for(int x=0;x<length;x++){
            r = compare(first.childs.get(x), second.childs.get(x));
            if(r!= 0) return r;
        }

        if(firstLength < secondLength) return -1;
        else if(firstLength == secondLength) return 0;
        else return 1;
        
    }

    static Node parseInput(Iterator<Character> input){
        Node node = new Node();
        while(input.hasNext()){
            var c = input.next();
            switch(c){
            case '[': node.childs.add(parseInput(input)); break;
            case ']': return node;
            case ',': continue;
            default : node.childs.add(new Node(Integer.parseInt(String.valueOf(c))));
            }
        }
        return node;
    }

    static class Node{
        List<Node> childs;
        int value;

        Node(){
            childs = new ArrayList<>();
            value = 0;
        }
        Node(int value){
            this.value = value;
            childs = null;
        }
        boolean hasValue(){
            return childs == null;
        }

        @Override public String toString() {
            return childs!=null? childs.toString(): value+"";
        }
    }
}
