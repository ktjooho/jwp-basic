package core.ref;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import org.junit.Test;

public class Junit4TestRunner {
    @Test
    public void run() throws Exception {
      //  Class<Junit4Test> clazz = Junit4Test.class;
    	
    	Class<Junit4Test> clazz = Junit4Test.class;
    	Annotation[] annos = clazz.getAnnotations();
    	Method[] methods = clazz.getMethods();
    	
    	
    	for(Annotation a : annos) {
    		if(a.annotationType().equals(MyTest.class)) {
    		
    			
    		}
    	}
    	
    	for(Method m : methods) {
    		if(m.isAnnotationPresent(MyTest.class)){
    			m.invoke(clazz.newInstance());
    		}
    		
    	}
    	
    	
    	
    }
}
