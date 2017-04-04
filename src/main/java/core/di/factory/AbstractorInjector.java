package core.di.factory;

import java.util.Set;

/**
 * Created by nokdu on 2017-04-03.
 */
//추상 클래스를 통한, 중복의 최소화.
public abstract class AbstractorInjector implements Injector {


    protected BeanFactory factory;

    protected AbstractorInjector(BeanFactory factory) {
        this.factory = factory;
    }

    private AbstractorInjector() {}

    @Override
    public boolean isValid(Class<?> clazz) {
        if(getInjectedElements(clazz).isEmpty()) {
            return false;
        }
        return true;
    }
    protected Object instantiateClass(Class<?> clazz) {
        return factory.instantiateClass(clazz);
    }

    @Override
    public Object inject(Class<?> clazz) {
        return inject(clazz,getInjectedElements(clazz),factory);
    }

    protected abstract Set<?> getInjectedElements(Class<?> clazz);
    protected abstract Object inject(Class<?> clazz, Set<?>elements, BeanFactory factory);

}
