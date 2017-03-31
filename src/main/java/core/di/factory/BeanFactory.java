package core.di.factory;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.common.collect.Lists;
import core.mvc.Controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Maps;

public class BeanFactory {
    private static final Logger logger = LoggerFactory.getLogger(BeanFactory.class);

    private Set<Class<?>> preInstanticateBeans;

    private Map<Class<?>, Object> beans = Maps.newHashMap();

    public BeanFactory(Set<Class<?>> preInstanticateBeans) {
        this.preInstanticateBeans = preInstanticateBeans;
    }

    @SuppressWarnings("unchecked")
    public <T> T getBean(Class<T> requiredType) {
        return (T) beans.get(requiredType);
    }

    private Object putBeanObject(Constructor<?> constructor, Class<?> bean, Object... args) throws Exception{
       Object obj = constructor.newInstance(args);
       beans.put(bean,obj);
       return obj;
    }
  //  public Controller getController()
    public Object createBeanInstance(Class<?> bean) throws Exception {
        Object obj = beans.get(bean);

        if(obj != null)
            return obj;

        Constructor<?> constructor = BeanFactoryUtils.getInjectedConstructor(bean);

        if(constructor == null) {
            return bean.newInstance();
        }

        Parameter[] params = constructor.getParameters();
        List<Object> args = Lists.newArrayList();

        for(Parameter param : params) {
            Class<?> concreteClazz = BeanFactoryUtils.findConcreteClass(param.getType(), preInstanticateBeans);
            args.add(createBeanInstance(concreteClazz));
        }
        return putBeanObject(constructor,bean,args.toArray());
    }

    public void initialize() {
        //TODO BeanFactory 구현
        for(Class<?> bean : preInstanticateBeans) {
            try {
                createBeanInstance(bean);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
