package core.di.factory;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import core.annotation.Controller;

public class BeanFactory {
    private static final Logger logger = LoggerFactory.getLogger(BeanFactory.class);

    private Set<Class<?>> preInstanticateBeans;

    private Map<Class<?>, Object> beans = Maps.newHashMap();

    private List<AbstractorInjector> injectors = Lists.newArrayList();

    public BeanFactory(Set<Class<?>> preInstanticateBeans) {
        this.preInstanticateBeans = preInstanticateBeans;
    }

    public Set<Class<?>> getPreInstanticateBeans() {return preInstanticateBeans;}
    @SuppressWarnings("unchecked")
    public <T> T getBean(Class<T> requiredType) {
        return (T) beans.get(requiredType);
    }
    public void putBean(Class<?> bean, Object object ){beans.put(bean, object);}

    public void initialize() {
        injectors.add(new FieldInjector(this));
        for (Class<?> clazz : preInstanticateBeans) {
            if (beans.get(clazz) == null) {
                logger.debug("instantiated Class : {}", clazz);
                instantiateClass(clazz);
            }
        }
    }
    //이 메소드는 Injector와 연관을 가지지않도록 바꾼다..
    //
    public Object instantiateClass(Class<?> clazz) {
        Object bean = beans.get(clazz);

        if (bean != null) {
            return bean;
        }

        Injector injector = null;

        for(Injector in : injectors) {
            if(in.isValid(clazz)) {
                injector = in;
                break;
            }
        }

        if(injector == null) {
            bean = BeanUtils.instantiate(clazz);
            putBean(clazz,bean);
            return bean;
        }

        bean = injector.inject(clazz);
        beans.put(clazz, bean);
        return bean;

        /*
           Injector di = DIFactory.create(clazz);
           bean = di.inject(clazz);

           beans.put(clazz,bean);
           return bean;
         */
       /// Annotation an;
       /// an.annotationType()
        //DI 방식간의 영향을 최소화하는것이 목적.

        //Injection 과  객체 생성은 좀 다르다.
        //


        //DependencyInjectionHelper
        //or DependencyInjectionAdaptor
        //-- GetElement
        //--
        //Injector
        //-ConstructorDI
        //-SetterDI
        //-FieldDI

        // Constructor는 주어진 Class를 직접 만들기 위해서 쓰이는 요소.
        // @Inject붙거나 안붙거나..
        // 꼭 써야하는 부분이다.

        // DependencyInjector가 직접 객체를 생성해버리면, 다른 구체클래스(Sibling)에 대한 정보까지 알아야된다.
        // 객체(Bean) 생성은 BeanFactory해주는게 맞다.
        // 추상 계층(인터페이스)을 통해서, 객체 생성시 다양한 방법으로 의존성을 주입해준다.
        //

        // @Inject부분은 객체를 생성해야된다는 부분이 아니고, 그 부분에 뭔가를 만들어서 넣어줘야
        // 하는 것이다.

        // @Inject something;
        //  you have to make instance of class<something> and assign it to variable something.
        //


        /*
            0. Field 일 때.
            bean = BeanUtils.instanite(clazz);
            fields = BeanFactoryUtils.getInjectedFields(clazz);
            for(field : fields) {
                field.set(bean, instatinatedClass(field.class));
            }
            bans.put(clazz,bean);
            return bean;
         */
        /*
            1.Method 일 때.
            bean = BeanUtils.instanite(clazz);
            methods = BeanFactoryUtils.getInjectedMethods(clazz);
            for(method : methods) {
                   method.invoke(bean, instatinatedParams(method));
            }
         */

        Constructor<?> injectedConstructor = BeanFactoryUtils.getInjectedConstructor(clazz);

        if (injectedConstructor == null) {
            bean = BeanUtils.instantiate(clazz);
            beans.put(clazz, bean);
            return bean;
        }

        logger.debug("Constructor : {}", injectedConstructor);
        bean = instantiateConstructor(injectedConstructor);
        beans.put(clazz, bean);
        return bean;
    }
    private Injector createDependencyInjector(Class<?> clazz) {
        if(BeanFactoryUtils.getInjectedConstructor(clazz) != null)
            return new ConstructorInjector();
        return null;
    }
    private Object instantiateSetter(Method m) {
        Parameter pType = m.getParameters()[0];
        return null;
    }
    private Object instantiateField(Field field) {
        Class<?> pType = field.getType();
        Class<?> concreteClazz = BeanFactoryUtils.findConcreteClass(pType,preInstanticateBeans);

        if (!preInstanticateBeans.contains(concreteClazz)) {
            throw new IllegalStateException(pType + "는 Bean이 아니다.");
        }

        Object bean = beans.get(concreteClazz);

        if(bean == null){

        }

        //field.set(bean,BeanUtils.instantiateClass(pType));

        return null;
    }
    private Object instantiateConstructor(Constructor<?> constructor) {
        Class<?>[] pTypes = constructor.getParameterTypes();
        List<Object> args = Lists.newArrayList();
        for (Class<?> clazz : pTypes) {
            Class<?> concreteClazz = BeanFactoryUtils.findConcreteClass(clazz, preInstanticateBeans);
            if (!preInstanticateBeans.contains(concreteClazz)) {
                throw new IllegalStateException(clazz + "는 Bean이 아니다.");
            }

            Object bean = beans.get(concreteClazz);
            if (bean == null) {
                bean = instantiateClass(concreteClazz);
            }
            args.add(bean);
        }
        return BeanUtils.instantiateClass(constructor, args.toArray());
    }

    public Map<Class<?>, Object> getControllers() {
        Map<Class<?>, Object> controllers = Maps.newHashMap();
        for (Class<?> clazz : preInstanticateBeans) {
            if (clazz.isAnnotationPresent(Controller.class)) {
                controllers.put(clazz, beans.get(clazz));
            }
        }
        return controllers;
    }
}
