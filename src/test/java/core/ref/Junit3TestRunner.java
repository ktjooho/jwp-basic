package core.ref;

import java.lang.reflect.Method;

import org.junit.Test;

public class Junit3TestRunner {
    @Test
    public void run() throws Exception {
        Class<Junit3Test> clazz = Junit3Test.class;
        Method[] methods = clazz.getMethods();
        for(Method m : methods) {
        	if(m.getName().startsWith("test")) {
        		m.invoke(clazz.newInstance());
        		//the object the underlying method is invoked from
        	}
        }
        
    }
}
