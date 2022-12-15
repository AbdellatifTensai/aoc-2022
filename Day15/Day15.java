import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

class Day15{

    public static void main(String[] args) throws IOException{
        var input = Files.lines(Paths.get("", "Day15.txt")).toList();
        var test = Files.lines(Paths.get("", "test.txt")).toList();

        long t = System.nanoTime();
        System.out.println(
            // "number of positions that cannot contain a beacon: "+part1(test)+"\n"+
            "tuning frequency of the only possible beacon: "+part2(input)
        );
        System.out.println("Time took: "+ (System.nanoTime() - t)*1E-6 + "ms");
    }

    static long part1(List<String> input){
        final long Y = 2_000_000;
        var records = input.stream().map(InputRecord::new).toList();
        long[] limitsX = getLimitsX(records);

        int noBeacon = 0;
        for(long x = limitsX[0]; x <= limitsX[1]; x++)
            for(var rec: records){
                if(rec.distanceFromSensor(x, Y) <= rec.distance)
                    noBeacon++;
                    break;
                }

        return noBeacon - beaconsAt(Y, records);
    }

    static long part2(List<String> input){
        final int TUNING_FACTOR = 4_000_000;
        final int MAX_DISTANCE = 4_000_000;
        final var records = input.stream().map(InputRecord::new).toList();
        final var length = records.size();
        //it's an array of one element to bypass the "can't mutate variables in a lambda" error thingy
        long[] frequency = new long[1];
        //but you can mutate reference types for some reason, java...

        IntStream.range(0, MAX_DISTANCE+1).parallel().forEach(y->{
            IntStream.range(0, MAX_DISTANCE+1).parallel().forEach(x ->{
                int flag = 0;  //again..
                for(int index=0;index<length;index++){
                    var rec = records.get(index);
                    if(rec.distanceFromSensor(x, y) > rec.distance)
                        flag++;
                }
                
                if(flag == records.size()){
                    frequency[0] =  x*TUNING_FACTOR + y;
                    return;
                }
            });
        });
             
        return frequency[0];
    }

    static long[] getLimitsX(List<InputRecord> input){
        return new long[]{input.stream().mapToLong(rec->rec.sensorX-rec.distance).min().getAsLong()
            ,input.stream().mapToLong(rec->rec.sensorX+rec.distance).max().getAsLong()};
    }

    static int beaconsAt(long y, List<InputRecord> input){
        int flag =0;
        var list = new ArrayList<InputRecord>();
        for(var rec: input) if(rec.beaconY == y) list.add(rec);
        if(list.size() != 0){
            flag++;
            for(int x=0;x<list.size()-1;x++)
                flag += list.get(x).beaconX != list.get(x+1).beaconX? 1: 0;

            return flag;
        }
        return 0;
    }

    static class InputRecord{
        long sensorX;
        long sensorY;
        long beaconX;
        long beaconY;
        long distance;

        InputRecord(String input){
            var matcher = Pattern.compile("-?[0-9]+").matcher(input);
            if(matcher.find()) sensorX = Long.parseLong(matcher.group());
            if(matcher.find()) sensorY = Long.parseLong(matcher.group());
            if(matcher.find()) beaconX = Long.parseLong(matcher.group());
            if(matcher.find()) beaconY = Long.parseLong(matcher.group());
            distance = distanceFromSensor(beaconX, beaconY);
        }

        long distanceFromSensor(long x, long y){
            return Math.abs(sensorX - x) + Math.abs(sensorY - y);
        }

        @Override public String toString() {
            return "["+sensorX+","+sensorY+"] ["+beaconX+","+beaconY+"] : "+distance;
        }
    }

}
