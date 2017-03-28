package next.controller.qna;

import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import core.mvc.AbstractController;
import core.mvc.ModelAndView;
import core.mvc.Service;
import next.controller.UserSessionUtils;
import next.dao.AnswerDao;
import next.dao.QuestionDao;
import next.model.Answer;
import next.model.Question;
import next.model.Result;
import next.model.User;
import next.service.qna.QnAService;

public class DeleteQuestionJsonController extends AbstractController {

	QuestionDao questionDao = new QuestionDao();
	AnswerDao answerDao = new AnswerDao();
	
	@Override
	public ModelAndView execute(HttpServletRequest req, HttpServletResponse response) throws Exception {
		
		Service qnaService = new QnAService();
		//qnaService.service(req, response);
		
		
		HttpSession session = req.getSession();
		
		if(!UserSessionUtils.isLogined(session)) {
			return jspView("redirect:/users/loginForm");
		}
		
		User user = UserSessionUtils.getUserFromSession(session);
		long questionId = Long.parseLong(req.getParameter("questionId"));
		Question question = questionDao.findById(questionId) ;
		String writer = question.getWriter();
		
		if(!user.getName().equals(writer)) {
			return jsonView().addObject("result", Result.fail("다른 사용자의 질문을 삭제할 수 없습니다."));
		}
		
		List<Answer> list = answerDao.findAllByQuestionId(questionId);
		Iterator<Answer> iter = list.iterator();
		
		while(iter.hasNext()) {
			Answer answer = iter.next();
			if(!answer.getWriter().equals(user.getName())) {
				return jsonView().addObject("result", Result.fail("다른 사용자가 댓글을 달은 질문은 삭제할 수 없습니다."));
			}
		}
		
		questionDao.delete(question);
		return jsonView().addObject("result", Result.ok());
	}
}