package core.di.factory;

import com.google.common.collect.Lists;
import org.springframework.beans.BeanUtils;

import java.lang.reflect.Constructor;
import java.util.List;
import java.util.Set;

/**
 * Created by nokdu on 2017-04-03.
 */
public class ConstructorInjector extends AbstractorInjector {

    protected ConstructorInjector(BeanFactory factory) {
        super(factory);
    }

    @Override
    protected Set<?> getInjectedElements(Class<?> clazz) {
        return BeanFactoryUtils.getInjectedConstructors(clazz);
    }

    @Override
    protected Object inject(Class<?> cla, Set<?> elements, BeanFactory factory) {
        Constructor<?> constructor = (Constructor<?>) elements.toArray()[0];
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
}
