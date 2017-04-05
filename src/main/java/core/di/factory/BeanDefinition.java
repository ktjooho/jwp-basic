package core.di.factory;

import com.google.common.collect.Sets;
import com.sun.xml.internal.bind.v2.runtime.reflect.opt.Const;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Set;

/**
 * Created by nokdu on 2017-04-04.
 */
public class BeanDefinition {
    //Class
    //public Class<?> getInjectedClass();
    //Injection
    //Object bean;

    /*
        내가 가장 고민했던 부분은 비슷한 타입이지만, 다르게 처리해야되는 경우이다.
        Constructor injection 처리, Setter or Field Injection 처리이다.
        한 클래스내에서, 이 두가지 처리방식 중 하나만 가진다.
        한 클래스에서 한가지 처리방식으로 바꾸고 싶었고, 이것을 인터페이스로 분리시킬까 생각을 했었다.
        그런데 공통점을 뽑아낼 수 있는게 없어보였다.

     */

    //0. Injection Parameters.
    //1. Construction Injected Params.
    //2. Field Injected Params.
    //3. InjectType.
    private Class<?> beanClazz;
    private Constructor<?> injectConstructor;
    private Set<Field> injectFields;
    // 먼저 그냥 분기로 처리하는게, 현명하다.

    BeanDefinition(Class<?> beanClazz) {
        this.beanClazz = beanClazz;
        injectConstructor = getInjectConstructor(beanClazz);
        injectFields = getInjectFields(beanClazz, injectConstructor);
    }

    public Constructor<?> getInjectConstructor() {
        return injectConstructor;
    }

    public Set<Field> getInjectFields() {
        return injectFields;
    }
    public Class<?> getBeanClass() {
        return beanClazz;
    }

    private Constructor<?> getInjectConstructor(Class<?> clazz) {
        return BeanFactoryUtils.getInjectedConstructor(clazz);
    }

    private Set<Field> getInjectFields(Class<?> clazz,Constructor<?> constructor) {
        if(constructor != null) {
            return Sets.newHashSet();
        }

        Set<Field> injectFields = Sets.newHashSet();
        Set<Class<?>> injectProperties = getInjectPropetiesType(clazz);
        Field[] fields = clazz.getDeclaredFields();

        for(Field field : fields) {
            if(injectProperties.contains(field.getType())) {
                injectFields.add(field);
            }
        }

        return injectFields;
    }

    private Set<Class<?>> getInjectPropetiesType(Class<?> clazz) {
       Set<Class<?>> injectProperties = Sets.newHashSet();
       Set<Method> injectMethod =
               BeanFactoryUtils.getInjectedMethods(clazz);
        //Setter
       for(Method method : injectMethod) {
           Class<?>[] paramTypes = method.getParameterTypes();
           if(paramTypes.length != 1) {
               throw new IllegalStateException("DI할 메소드는 하나의 인자여야함.");
           }
           injectProperties.add(paramTypes[0]);
       }
       //Field
        Set<Field> injectFields = BeanFactoryUtils.getInjectedFields(clazz);
       for(Field field : injectFields) {
            injectProperties.add(field.getType());
       }
       return injectProperties;
    }

    public InjectType getResolvedInjectMode() {
        if( injectConstructor != null) {
            return InjectType.INJECT_CONSTRUCTOR;
        }

        if( !injectFields.isEmpty() ) {
            return InjectType.INJECT_FIELD;
        }

        return InjectType.INJECT_NO;
    }



    /*
    public Object getBean();
    public void putBean(Object object);
    public boolean hasInjected();
    */

}
