package core.ref;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import next.model.Question;
import next.model.User;

public class ReflectionTest {
    private static final Logger logger = LoggerFactory.getLogger(ReflectionTest.class);

    @Test
    public void showClass() {
    	
    	Class<Person> personClazz = Person.class;
    	Field[] personFields = personClazz.getDeclaredFields();
    	for(Field field : personFields) {
        	logger.debug("Persone FieldName:" + field.getName() + ", Type :" + field.getType().getName());
        	
        }
    	
    	
        Class<Question> clazz = Question.class;
        logger.debug("Test : " + clazz.getSimpleName());
        
        //clazz.get
        Field[] fields = clazz.getDeclaredFields();
        logger.debug("Field size : " + fields.length);
        for(Field field : fields) {
        	logger.debug("FieldName:" + field.getName() + ", Type :" + field.getType().getName());
        	
        }
        Method[] methods = clazz.getMethods();
        for(Method method : methods) {
        	logger.debug("Method Name : " + method.getName());
        }
        Constructor<Question>[] constructors =  (Constructor<Question>[]) clazz.getConstructors();
        
        for(Constructor<Question>c : constructors) {
        	logger.debug("Constructor:" + c.getName() + "Params Count : " + c.getParameterCount()); 
        }
        
        
        
        
    }
    
    @Test
    public void newInstanceWithConstructorArgs() {
        Class<User> clazz = User.class;
        logger.debug(clazz.getName());
    }
    
    @Test
    public void privateFieldAccess() {
        Class<Student> clazz = Student.class;
        logger.debug(clazz.getName());
    }
    
    @Test
    public void annotationCheck() {
    	Class<Student> clazz = Student.class;
    	logger.debug(clazz.getAnnotations().toString());
    }
}
