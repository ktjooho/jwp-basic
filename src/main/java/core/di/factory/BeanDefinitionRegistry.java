package core.di.factory;


/**
 * Created by nokdu on 2017-04-04.
 */
public interface BeanDefinitionRegistry {
    void registerBeanDefinition(Class<?> clazz, BeanDefinition beanDefinition);
}
