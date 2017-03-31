package core.di.factory;

import static org.junit.Assert.assertNotNull;

import java.lang.annotation.Annotation;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.reflections.Reflections;

import com.google.common.collect.Sets;

import core.annotation.Controller;
import core.annotation.Repository;
import core.annotation.Service;
import core.di.factory.example.MyQnaService;
import core.di.factory.example.QnaController;

public class BeanFactoryTest {
    private Reflections reflections;
    private BeanFactory beanFactory;

    /*
        Controller <- Service (Inject)
        Service <- Repo  (Inject)
     */
    @Before
    @SuppressWarnings("unchecked")
    public void setup() {
        reflections = new Reflections("core.di.factory.example");

        //Controller, Service, Repo annotation이 붙은 클래스들을 가져온다.
        // 생성자에 Inject가 있다면.. 생성자 파라미터들을 확인한다.
        // 그 생성자 파라미터에 매핑되는
        Set<Class<?>> preInstanticateClazz = getTypesAnnotatedWith(Controller.class, Service.class, Repository.class);



        beanFactory = new BeanFactory(preInstanticateClazz);
        beanFactory.initialize();
    }

    @Test
    public void di() throws Exception {
        //Set<Class<?>> Controllers;
        //for(Class<?> controller : controllers){
        // Controller c = beanFactory.getBean(controller.class);
        //
        //

        QnaController qnaController = beanFactory.getBean(QnaController.class);
        assertNotNull(qnaController);
        assertNotNull(qnaController.getQnaService());

        MyQnaService qnaService = qnaController.getQnaService();
        assertNotNull(qnaService.getUserRepository());
        assertNotNull(qnaService.getQuestionRepository());
    }

    @SuppressWarnings("unchecked")
    private Set<Class<?>> getTypesAnnotatedWith(Class<? extends Annotation>... annotations) {
        Set<Class<?>> beans = Sets.newHashSet();
        for (Class<? extends Annotation> annotation : annotations) {
            beans.addAll(reflections.getTypesAnnotatedWith(annotation));
        }
        return beans;
    }
}
