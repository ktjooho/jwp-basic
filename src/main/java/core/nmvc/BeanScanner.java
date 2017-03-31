package core.nmvc;

import java.lang.annotation.Annotation;
import java.util.Map;
import java.util.Set;

import com.google.common.collect.Sets;
import core.di.factory.BeanFactory;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Maps;

import core.annotation.Controller;

public class BeanScanner {

    private static final Logger log = LoggerFactory.getLogger(BeanScanner.class);

    private Reflections reflections;
    private BeanFactory beanFactory;

    public BeanScanner(Object... basePackage) {
        //beanFactory = new Bea

        reflections = new Reflections(basePackage);
    }
    private Set<Class<?>> getTypesAnnotatedWith(Class<? extends Annotation>... annotations) {
        Set<Class<?>>

    }
    public Set<Class<?>> getPreInstanticateClass() {
        Set<Class<?>> beans  = Sets.newHashSet();

    }

    public Map<Class<?>, Object> getControllers() {
        Set<Class<?>> preInitiatedControllers = reflections.getTypesAnnotatedWith(Controller.class);
        return instantiateControllers(preInitiatedControllers);
    }

    Map<Class<?>, Object> instantiateControllers(Set<Class<?>> preInitiatedControllers) {
        Map<Class<?>, Object> controllers = Maps.newHashMap();
        try {
            for (Class<?> clazz : preInitiatedControllers) {
                controllers.put(clazz, clazz.newInstance());
            }
        } catch (InstantiationException | IllegalAccessException e) {
            log.error(e.getMessage());
        }
        return controllers;
    }




}
