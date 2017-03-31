package core.nmvc;

import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * Created by nokdu on 2017-03-31.
 */
public class PersonTest {

    @Test
    public void reflectionTest() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Class<Person> clazz = Person.class;
        Constructor<Person> constructor = clazz.getConstructor(String.class,Integer.TYPE);
        Assert.assertNotNull(constructor);

        Person p = constructor.newInstance("Hell",5);

        Assert.assertNotNull(p);
        Assert.assertEquals("Hell",p.getName());
        Assert.assertEquals(5,p.getAge());
    }
}
