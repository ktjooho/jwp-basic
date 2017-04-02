package next.controller.qna;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import core.annotation.Controller;
import core.annotation.Inject;
import core.annotation.RequestMapping;
import core.mvc.AbstractController;
import core.mvc.ModelAndView;
import next.CannotDeleteException;
import next.controller.UserSessionUtils;
import next.model.Result;
import next.service.QnaService;

@Controller
public class ApiDeleteQuestionController extends AbstractController {
    private QnaService qnaService;

    @Inject
    public ApiDeleteQuestionController(QnaService qnaService) {
        this.qnaService = qnaService;
    }

    @RequestMapping("/api/qna/deleteAnswer")
    public ModelAndView execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        if (!UserSessionUtils.isLogined(req.getSession())) {
            return jsonView().addObject("result", Result.fail("Login is required"));
        }

        long questionId = Long.parseLong(req.getParameter("questionId"));
        try {
            qnaService.deleteQuestion(questionId, UserSessionUtils.getUserFromSession(req.getSession()));
            return jsonView().addObject("result", Result.ok());
        } catch (CannotDeleteException e) {
            return jsonView().addObject("result", Result.fail(e.getMessage()));
        }
    }
}