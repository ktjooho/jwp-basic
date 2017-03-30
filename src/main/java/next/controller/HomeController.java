package next.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import next.dao.JdbcQuestionDao;
import next.dao.QuestionDao;
import core.annotation.Controller;
import core.annotation.RequestMapping;
import core.mvc.AbstractController;
import core.mvc.ModelAndView;

@Controller
public class HomeController extends AbstractController {
    private QuestionDao questionDao;

    public HomeController(QuestionDao questionDao) {
        this.questionDao = questionDao;
    }

    @RequestMapping(value="/")
    @Override
    public ModelAndView execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        return jspView("home.jsp").addObject("questions", questionDao.findAll());
    }
}
