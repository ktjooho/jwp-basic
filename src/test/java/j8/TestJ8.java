package j8;

import com.google.common.collect.Lists;
import org.junit.Test;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by nokdu on 2017-04-04.
 */
public class TestJ8 {

    public List<Apple> prettyPrintApple(List<Apple> inventory, ApplePredicate pred) {
        List<Apple> result = Lists.newArrayList();
        for(Apple apple : inventory) {
            if(pred.test(apple)) {
                result.add(apple);
            }
        }
        return result;
    }
    public <T> List<T> filtering(List<T> list, Predicate<T> pred) {

        List<T> results = Lists.newArrayList();

        for(T item : list) {
            if(pred.test(item)) {
                results.add(item);
            }
        }
        return results;
    }

    @Test
    public void paramTest() throws InterruptedException {
        Thread t = new Thread(()->System.out.println("Hi"));
        t.run();

        List<Apple> apples = Lists.newArrayList();
        apples.add(new Apple("red"));
        apples.add(new Apple("blue"));
        apples.add(new Apple("green"));
        apples.add(new Apple("yellow"));

       List<Apple> greenApples = prettyPrintApple(apples,apple -> apple.getColor().equals("green") );


        apples.stream().map( (apple) -> {
           System.out.println(apple.getColor());
           return apple;
       } );
        apples = filtering(apples, apple ->!apple.getColor().equals("green"));
        for(Apple apple : apples)
            System.out.println(apple.getColor());


        //apples.stream().filter(a->a.getColor().compareTo("apple")).collect(Collectors.toList());



        t.join();

    }


}
