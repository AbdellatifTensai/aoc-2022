import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

class Day12{

    public static void main(String[] args) throws IOException{
        var input = Files.lines(Paths.get("", "Day12.txt")).map(line -> line.chars().mapToObj(x->(char)x).toList()).toList();

        System.out.println(
            "number of steps : "+part1(input)+"\n"+
            "number of steps from closest a : "+part2(input)
        );
    }

    static int part1(List<List<Character>> input){
        var grid = new ArrayList<List<Node>>();
        Node start = null;
        for(int y=0;y<input.size();y++){
            var row = new ArrayList<Node>();
            var line = input.get(y);
            for(int x=0;x<line.size();x++){
                var c = line.get(x);
                if(c.equals('E')) c= 'z'+1;
                var node = new Node(c ,null, false, y, x); 
                if(c.equals('S')) start = node;
                row.add(node);
            }
            grid.add(row);
        }
        start.isVisited = true;

        return BFS(grid, start); 
    }

    static int part2(List<List<Character>> input){
        var starts = new ArrayList<Node>();
        var grid = new ArrayList<List<Node>>();
        var steps = new ArrayList<Integer>();
        for(int y=0;y<input.size();y++){
            var row = new ArrayList<Node>();
            var line = input.get(y);
            for(int x=0;x<line.size();x++){
                var c = line.get(x);
                if(c.equals('E')) c= 'z'+1;
                var node = new Node(c ,null, false, y, x); 
                if(c.equals('S') || c.equals('a')) starts.add(node);
                row.add(node);
            }
            grid.add(row);
        }

        for(var start: starts){
            start.isVisited = true;
            steps.add(BFS(grid, start));
            grid.forEach(row -> row.forEach(node -> node.isVisited = false));
        }

        return steps.stream().mapToInt(x->x).filter(x -> x!=0).min().getAsInt();
    }

    static int BFS(List<List<Node>> grid, Node start){
        var queue = new LinkedList<Node>() ;
        queue.add(start);

        Node currentNode = null;
        while(!queue.isEmpty()){

            currentNode = queue.poll();
            if(currentNode.name == 'z'+1) break;

            var neighbors = neighbors(grid, currentNode);
            for(var node: neighbors){
                if(currentNode.name >= node.name || currentNode.name == (node.name -1) || currentNode.name.equals('S')){
                    node.isVisited = true;
                    node.parent = currentNode;
                    queue.addLast(node);
                }
            }
        }

        if(currentNode.name != 'z'+1) return 0;
        int steps =0;
        while(!currentNode.equals(start)){
            currentNode = currentNode.parent;
            steps++;
        }

        return steps-2;
    }

    static List<Node> neighbors(List<List<Node>> grid, Node currentNode){
        var neighbors = new ArrayList<Node>();
        int rowLength = grid.get(0).size();
        int colLength = grid.size();

        int[][] coords = {{currentNode.y,currentNode.x+1}
                        ,{currentNode.y,currentNode.x-1}
                        ,{currentNode.y+1,currentNode.x}
                        ,{currentNode.y-1,currentNode.x}};

        for(var yx: coords){
            if((yx[0] < colLength && yx[0] >= 0) && (yx[1] < rowLength && yx[1] >= 0)){
                Node node = grid.get(yx[0]).get(yx[1]);
                if(!node.isVisited){
                    neighbors.add(node);
                }
            }
        }

        return neighbors;
    }

    static class Node{
        Character name;
        Node parent;
        boolean isVisited;
        int x,y;
        Node(Character name, Node parent, boolean isVisited, int y, int x) {
            this.name = name;
            this.parent = parent;
            this.isVisited = isVisited;
            this.y = y;
            this.x = x;
        }
        @Override public String toString(){ return name+" : "+y+" , "+x; }
        @Override
        public boolean equals(Object obj) {
            if(!(obj instanceof Node)) return false;
            var other = (Node) obj;
            return this.name.equals(other.name) && this.x == other.x && this.y == other.y;
        }
    }
}