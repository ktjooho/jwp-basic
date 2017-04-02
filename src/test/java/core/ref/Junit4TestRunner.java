package core.ref;

import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Junit4TestRunner {
    @Test
    public void run() throws Exception {
        Class<Junit4Test> clazz = Junit4Test.class;
    }
    @Test
    public void collectorTest() {
        Map<String,Integer> m = new HashMap<String,Integer>();
        m.put("Juho",100);
        m.put("Jaeho",150);

        Map<String,Integer>  m2 = m.entrySet().stream().map((e)->{
            e.setValue(e.getValue()+5);
            return e;
        }).collect(Collectors.toMap(e->e.getKey(),e->e.getValue()));


        Assert.assertEquals(m.size(),2);
        Assert.assertEquals(m.get("Juho").intValue(),105);
        Assert.assertEquals(m.get("Jaeho").intValue(),155);
        //KeyMapper
        //ValueMapper


    }


}
