package core.di.factory.example;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import core.annotation.Controller;
import core.annotation.Inject;
import core.annotation.RequestMapping;
import core.mvc.ModelAndView;
import core.nmvc.AbstractNewController;

@Controller
public class QnaController extends AbstractNewController {
    private MyQnaService qnaService;

    //Inject 어노테이션은 하나만 존재해야할 것.
    //Controller, Service, Repo
    @Inject
    public QnaController(MyQnaService qnaService) {
        this.qnaService = qnaService;
    }

    public MyQnaService getQnaService() {
        return qnaService;
    }

    @RequestMapping("/questions")
    public ModelAndView list(HttpServletRequest request, HttpServletResponse response) throws Exception {
        return jspView("/qna/list.jsp");
    }
}
