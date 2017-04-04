package core.di.factory;

import org.springframework.beans.BeanUtils;

import java.lang.reflect.Field;
import java.util.Set;

/**
 * Created by nokdu on 2017-04-03.
 */
public class FieldInjector extends AbstractorInjector {

    protected FieldInjector(BeanFactory factory) {
        super(factory);
    }

    @Override
    protected Set<?> getInjectedElements(Class<?> clazz) {
        return BeanFactoryUtils.getInjectedField(clazz);
    }

    @Override
    protected Object inject(Class<?> clazz, Set<?>elements, BeanFactory factory) {
        Object bean = BeanUtils.instantiate(clazz);
        Field [] fields = (Field[]) elements.toArray();

        for(Field field : fields) {
            try {
                field.set(bean,super.instantiateClass(field.getType()));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return bean;
    }
}
