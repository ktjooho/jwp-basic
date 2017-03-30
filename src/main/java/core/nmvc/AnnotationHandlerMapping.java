package core.nmvc;

import java.lang.reflect.Method;
import java.lang.reflect.TypeVariable;
import java.util.Map;
import java.util.Set;
import java.util.logging.Handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.reflections.ReflectionUtils;
import org.reflections.Reflections;

import com.google.common.collect.Maps;

import core.annotation.Controller;
import core.annotation.RequestMapping;
import core.annotation.RequestMethod;
import core.mvc.HandlerMapping;
import core.mvc.ModelAndView;

public class AnnotationHandlerMapping implements HandlerMapping {
    private Object[] basePackage;

    private Map<HandlerKey, HandlerExecution> handlerExecutions = Maps.newHashMap();

    public AnnotationHandlerMapping(Object... basePackage) {
        this.basePackage = basePackage;
    }

	private Set<Class<?>> getControllerClasses(Object... basePackage) {
		Reflections reflections = new Reflections(basePackage);
        Set<Class<?>> annotated = reflections.getTypesAnnotatedWith(Controller.class);
		return annotated;
	}
	
	private void createHandlerExecutions(Class<?> cls, Method m) {
		RequestMapping rm = m.getAnnotation(RequestMapping.class);
 		
 		if(rm == null)
 			return ;

 		HandlerKey handlerKey = new HandlerKey(rm.value(), rm.method());
 		
 		//결국엔 execute 메소드를 호출하는 형태이지만,Controller를 상속받아서 쓰게 되면, 뭔가 아구가 안맞는다?
 		//Handler Mapping인데... 
 		
 		
 		//Target이 되는 메소드가 Controller의 메소드가 되는 기이 현상이 발생한다..?
 		
 		
 		HandlerExecution handlerExecution =  (HttpServletRequest req, HttpServletResponse resp)->{
 			return (ModelAndView)m.invoke(cls.newInstance(),req, resp);
 		};
 		
 		handlerExecutions.put(handlerKey, handlerExecution);
	}
	
    public void initialize() {
    	Set<Class<?>> controllerClasses = getControllerClasses(basePackage);
      //   ReflectionUtils.getAllMethods(arg0, arg1)
    //	ReflectionUtils.w
        for(Class<?> cls : controllerClasses) {
         	Method[] methods = cls.getMethods();
         	for(Method m : methods) {
         		createHandlerExecutions(cls, m);
         	}
         }
    }
    @Override
    public HandlerExecution getHandler(HttpServletRequest request) {
        String requestUri = request.getRequestURI();
        RequestMethod rm = RequestMethod.valueOf(request.getMethod().toUpperCase());
        return handlerExecutions.get(new HandlerKey(requestUri, rm));
    }
}
