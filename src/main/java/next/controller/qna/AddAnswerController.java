package next.controller.qna;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import core.annotation.Controller;
import core.annotation.Inject;
import core.annotation.RequestMapping;
import core.annotation.RequestMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import core.mvc.AbstractController;
import core.mvc.ModelAndView;
import next.controller.UserSessionUtils;
import next.dao.AnswerDao;
import next.dao.QuestionDao;
import next.model.Answer;
import next.model.Result;
import next.model.User;

@Controller
public class AddAnswerController extends AbstractController {
    private static final Logger log = LoggerFactory.getLogger(AddAnswerController.class);

    private QuestionDao questionDao;
    private AnswerDao answerDao;

    @Inject
    public AddAnswerController(QuestionDao questionDao, AnswerDao answerDao) {
        this.questionDao = questionDao;
        this.answerDao = answerDao;
    }

    @RequestMapping(value = "/api/qna/addAnswer",method = RequestMethod.PUT)
    public ModelAndView execute(HttpServletRequest req, HttpServletResponse response) throws Exception {
        if (!UserSessionUtils.isLogined(req.getSession())) {
            return jsonView().addObject("result", Result.fail("Login is required"));
        }

        User user = UserSessionUtils.getUserFromSession(req.getSession());
        Answer answer = new Answer(user.getUserId(), req.getParameter("contents"),
                Long.parseLong(req.getParameter("questionId")));
        log.debug("answer : {}", answer);

        Answer savedAnswer = answerDao.insert(answer);
        questionDao.updateCountOfAnswer(savedAnswer.getQuestionId());

        return jsonView().addObject("answer", savedAnswer).addObject("result", Result.ok());
    }
}
