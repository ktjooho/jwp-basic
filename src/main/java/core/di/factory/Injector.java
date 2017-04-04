package core.di.factory;

/**
 * Created by nokdu on 2017-04-03.
 */
public interface Injector {
    public Object inject(Class<?> clazz);
    public boolean isValid(Class<?> clazz);
}

