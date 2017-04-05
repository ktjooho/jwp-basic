package core.di.factory;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Maps;

import core.annotation.Controller;
import org.springframework.beans.BeanUtils;


public class BeanFactory implements  BeanDefinitionRegistry {
    private static final Logger logger = LoggerFactory.getLogger(BeanFactory.class);

    private Map<Class<?>, Object> beans = Maps.newHashMap(); //bean 상태. (인스턴스화된 bean)
    private Map<Class<?>, BeanDefinition> beanDefinitions = Maps.newHashMap();

    private Class<?> findConcreteClass(Class<?> clazz) {
        Set<Class<?>> beanClasses = getBeanClasses();
        Class<?> concreteClazz = BeanFactoryUtils.findConcreteClass(clazz,beanClasses);

        if(!beanClasses.contains(concreteClazz)) {
            throw  new IllegalStateException(clazz + "는 Bean이 아니다.");
        }

        return concreteClazz;
    }
    public Set<Class<?>> getBeanClasses() {
        return beanDefinitions.keySet();
    }


    @SuppressWarnings("unchecked")
    public <T> T getBean(Class<T> clazz) {
        Object bean = beans.get(clazz);
        if(bean != null) {
            return (T) bean;
        }
        Class<?> concreteClass = findConcreteClass(clazz);
        BeanDefinition beanDefinition = beanDefinitions.get(concreteClass);
        bean = inject(beanDefinition);
        beans.put(clazz,bean);
        return (T) beans.get(clazz);
    }

    public void initialize() {
        for (Class<?> clazz : getBeanClasses()) {
            getBean(clazz);
        }
    }


    private Object inject(BeanDefinition beanDefinition) {
        if(beanDefinition.getResolvedInjectMode() == InjectType.INJECT_CONSTRUCTOR) {
            return injectConstructor(beanDefinition);
        } else if(beanDefinition.getResolvedInjectMode() == InjectType.INJECT_FIELD) {
            return injectFields(beanDefinition);
        }

        return BeanUtils.instantiate(beanDefinition.getBeanClass());
    }

    private Object injectConstructor(BeanDefinition beanDefinition) {
        Constructor<?> constructor = beanDefinition.getInjectConstructor();
        List<Object> args = Lists.newArrayList();
        for(Class<?> clazz : constructor.getParameterTypes()) {
            args.add(getBean(clazz));
        }
        return BeanUtils.instantiateClass(constructor,args);
    }
    private Object injectFields(BeanDefinition beanDefinition) {
        Set<Field> fields = beanDefinition.getInjectFields();
        Object bean = BeanUtils.instantiate(beanDefinition.getBeanClass());
        for(Field field : fields) {
            injectField(bean,field);
        }
        return bean;
    }
    private void injectField(Object bean, Field field) {
        field.setAccessible(true);
        try {
            field.set(bean,getBean(field.getType()));
        } catch (IllegalAccessException e) {
            logger.error(e.getMessage());
        }
    }


    void clear() {
       beanDefinitions.clear();
        beans.clear();
    }

    @Override
    public void registerBeanDefinition(Class<?> clazz, BeanDefinition beanDefinition) {
        beanDefinitions.put(clazz,beanDefinition);
    }
}
