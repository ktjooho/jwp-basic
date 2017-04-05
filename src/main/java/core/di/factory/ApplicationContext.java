package core.di.factory;

import java.util.Set;

/**
 * Created by nokdu on 2017-04-05.
 */
public class ApplicationContext {

    private BeanFactory beanFactory;

    public ApplicationContext(Object... basePackage) {
        beanFactory = new BeanFactory();
        ClasspathBeanDefinitionScanner scanner = new ClasspathBeanDefinitionScanner(beanFactory);
        scanner.doScan(basePackage);
        beanFactory.initialize();
    }

    public <T> T getBean(Class<T> clazz) {
        return beanFactory.getBean(clazz);
    }

    public Set<Class<?>> getBeanClasses(){
        //return beanFactory.get
        return beanFactory.getBeanClasses();
    }




}
