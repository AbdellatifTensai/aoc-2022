import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.regex.Pattern;

class Day11{

    public static void main(String[] args) throws IOException{
        var input = Files.readString(Paths.get("", "Day11.txt"));

        long t = System.nanoTime();
        System.out.println(
            "the level of monkey business after 20 rounds: "+part1(input)+"\n"+
            "the level of monkey business after 10000 rounds: "+part2(input)
        );
        long d = System.nanoTime() - t;
        System.out.println("time took: "+ d*1E-9+ "s");
    }
    
    static BigInteger part2(String input){
        return monkeyLevel(input, 10000, 1);
    }

    static BigInteger part1(String input){
        return monkeyLevel(input, 20, 3);
    }

    static BigInteger monkeyLevel(final String input, final int ROUNDS, final int worryReduction){

        var monkeys = Arrays.stream(input.split("\n\n")).map(Monkey::new).toList();

        for(int round=0;round<ROUNDS;round++){
            for(var monkey: monkeys){
                for(var item: monkey.items){
                    var newItem = monkey.operation.apply(item).divide(BigInteger.valueOf(worryReduction)).mod(N);
                    if(monkey.condition.test(newItem)){
                        monkeys.get(monkey.ifTrue).items.add(newItem);
                    }else{
                        monkeys.get(monkey.ifFalse).items.add(newItem);
                    }
                    monkey.itemsToBeRemoved.add(item);
                    monkey.itemsInspected = monkey.itemsInspected.add(BigInteger.ONE);
                }
                monkey.items.removeAll(monkey.itemsToBeRemoved);
            }
        }
        return monkeys.stream()
                    .map(Monkey::inspected)
                    .sorted(Collections.reverseOrder())
                    .limit(2)
                    .reduce((m1,m2)->m1.multiply(m2))
                    .get();
    }

    static BigInteger N = BigInteger.valueOf(1);
    static class Monkey{
        int id;
        List<BigInteger> items;
        List<BigInteger> itemsToBeRemoved; 
        Function<BigInteger, BigInteger> operation;
        Predicate<BigInteger> condition;
        int ifTrue;
        int ifFalse;
        BigInteger itemsInspected;

        Monkey(String monkey){
            items = new ArrayList<>();
            itemsToBeRemoved = new ArrayList<>();
            itemsInspected = BigInteger.valueOf(0);

            var traits = monkey.lines().toList();
            var pattern = Pattern.compile("[0-9]+");

            var matcher = pattern.matcher(traits.get(0));
            while(matcher.find()) id = Integer.parseInt(matcher.group());

            matcher = pattern.matcher(traits.get(1));
            while(matcher.find()) items.add(BigInteger.valueOf(Long.parseLong(matcher.group())));

            var arr = traits.get(2).split("\\* ");
            if(arr.length>1){
                var substr = arr[1];
                operation = num -> substr.contains("old")? num.multiply(num): num.multiply(BigInteger.valueOf(Long.parseLong(substr)));
            }else{
                var substr = traits.get(2).split("\\+ ")[1];
                operation = num -> substr.contains("old")? num.add(num): num.add(BigInteger.valueOf(Long.parseLong(substr)));
            }

            matcher = pattern.matcher(traits.get(3));
            while(matcher.find()){
                var str = matcher.group();
                var val = BigInteger.valueOf(Integer.parseInt(str));
                N = N.multiply(val);
                condition = num -> num.mod(val).equals(BigInteger.ZERO);
            }

            matcher = pattern.matcher(traits.get(4));
            while(matcher.find()) ifTrue = Integer.parseInt(matcher.group());

            matcher = pattern.matcher(traits.get(5));
            while(matcher.find()) ifFalse = Integer.parseInt(matcher.group());
        }

        @Override public String toString(){ return id+" inspected: "+itemsInspected+"\n:\t"+items+"\n"; }
        public BigInteger inspected(){ return itemsInspected; }
    }
}