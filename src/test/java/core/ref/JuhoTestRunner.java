package core.ref;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import core.mvc.Controller;
import junit.framework.Assert;

public class JuhoTestRunner {
	
	private static final Logger logger = LoggerFactory.getLogger(JuhoTestRunner.class);
	
	@Test
	public void testChildClass() {
		Class<Controller> clazz = Controller.class;
		
		Type[] concreteClass = clazz.getGenericInterfaces();
		
		//Class<?> components = clazz.getComponentType();
		//logger.debug(components.getName());
		
		Class<?>[] cls = clazz.getClasses();
		for(Class<?> c : cls) {
			logger.debug("cls: " + c.getName());
		}
	
		
		
		Type su =  clazz.getGenericSuperclass();
		logger.debug("super :" + su.getTypeName());
		
		for(Type t : concreteClass) {
			logger.debug("Type : " + t.getTypeName());
			
		}
		
		
	}
	
	@Test
	public void annotationTest() {
		Class<JuhoTest> clazz = JuhoTest.class;
		
		Method[] methods = clazz.getMethods();
		Field[] fileds = clazz.getDeclaredFields();
		
		for(Field f : fileds) {
			Juho j = f.getAnnotation(Juho.class);
			logger.debug("x num:" + j.number());
			logger.debug("x string : " + j.text());
		}
		
		for(Method m : methods) {
			Juho j = m.getAnnotation(Juho.class);
			logger.debug("num:" + j.number());
			logger.debug("string : " + j.text());
		}
		
	}
	@SuppressWarnings("deprecation")
	@Test
	public void testMethodIdentitiy() throws Exception {
		Class<JuhoTest> clazz = JuhoTest.class;
		JuhoTest jh = null;
		
		
		Method m1 = clazz.getMethod("method1", int.class, String.class);
		Method m2 = clazz.getMethod("method2", int.class, String.class);
		Assert.assertNotNull(m1);
		Assert.assertNotNull(m2);
		
		JuhoInterface juho = new JuhoInterface() {
			public int foo(int a, String b) throws Exception {
				try {
					Method m = m1;
					return (int) m.invoke(clazz.newInstance(), a, b);
				} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return -500;
				}
			}
		};
	
		Assert.assertEquals(100, juho.foo(5, "ffff"));
		//Assert.assertTrue(m1.equals(m2));
		
	}
	
	
}
