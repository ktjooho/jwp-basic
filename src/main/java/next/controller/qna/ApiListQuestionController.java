package next.controller.qna;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import core.annotation.Controller;
import core.annotation.Inject;
import core.annotation.RequestMapping;
import core.mvc.AbstractController;
import core.mvc.ModelAndView;
import next.dao.QuestionDao;


@Controller
public class ApiListQuestionController extends AbstractController {
    private QuestionDao questionDao;
    @Inject
    public ApiListQuestionController(QuestionDao questionDao) {
        this.questionDao = questionDao;
    }

    @RequestMapping("/api/qna/list")
    public ModelAndView execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        return jsonView().addObject("questions", questionDao.findAll());
    }
}
