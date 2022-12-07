import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

class Day7{

    public static void main(String[] args) throws IOException{
        var input = Files.lines(Paths.get("","Day7.txt")).skip(1).toList();
        var fileSystem = parseFileSystem(input);

        System.out.println(
            "sum of total sizes of dirs: "+part1(fileSystem)+"\n"+
            "size of smallest dir to be deleted: "+part2(fileSystem, new ArrayList<>(), 30_000_000 - (70_000_000 - fileSystem.size))
        );
    }

    static int part2(Node fileSystem, List<Node> toBeDeleted, int requiredSpace){
        for(var child: fileSystem.childs){
            if(child.size>requiredSpace){
                toBeDeleted.add(child);
                part2(child, toBeDeleted, requiredSpace);
            }
        }
            
        return toBeDeleted.stream().mapToInt(child -> child.size).min().getAsInt();
    }

    static int total =0;
    static int part1(Node fileSystem){
        for(var child: fileSystem.childs){
            if(child.size <= 100_000){
                part1(child);
                total += child.size;
            }
            else{
                part1(child);
            }
        }
        return total;
    }

    static Node parseFileSystem(List<String> input){
        Node node = new Node(null, new ArrayList<>(), new ArrayList<>(), "/");
        
        for(var line: input){

            if(line.contains("$ cd ")){
                var str = line.split(" ")[2];

                if(str.equals("..")){
                    node = node.parent;
                }
                else{
                    int index=-1;
                    for(var child: node.childs){
                        if(child.name.equals(str)){
                            index = node.childs.indexOf(child);
                            break;
                        }
                    }
                    if(index == -1) throw new RuntimeException("could not find child, fix your code!");
                    node = node.childs.get(index);
                }
            }
            else if(line.contains("dir ")){
                node.childs.add(new Node(node, new ArrayList<>(), new ArrayList<>(), line.split(" ")[1]));
            }
            else if(line.contains("$ ls")){
                continue;
            }
            else{
                node.files.add(line);
            }
        }

        while(!node.name.equals("/")) node = node.parent;

        tallySize(node);

        return node;
    }

    static int tallySize(Node node){
        node.size += node.files.stream().mapToInt(file -> Integer.parseInt(file.split(" ")[0])).sum();
        node.size += node.childs.stream().mapToInt(child -> tallySize(child)).sum();
        return node.size;
    }

    static void printNode(Node node, String appender){
        StringBuilder nodeFormat = new StringBuilder();

        nodeFormat.append(appender + "-" + node.name + " : "+ node.size +"\n");
        node.files.forEach(file -> nodeFormat.append(appender).append(file).append("\n"));
        System.out.println(nodeFormat.toString());

        node.childs.forEach(child -> printNode(child, appender+appender));
    }

    static class Node{
        Node parent;
        List<Node> childs;
        List<String> files;
        String name;
        int size;
        Node(Node parent, List<Node> childs, List<String> files, String name) {
            this.parent = parent;
            this.childs = childs;
            this.files = files;
            this.name = name;
        }
        @Override public String toString(){ return name+" : "+size; }
        Node size(int s){ size = s; return this;}
    }

}
