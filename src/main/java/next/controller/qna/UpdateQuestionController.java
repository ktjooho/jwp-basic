package next.controller.qna;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import core.mvc.AbstractController;
import core.mvc.ModelAndView;
import next.controller.UserSessionUtils;
import next.dao.QuestionDao;
import next.model.Question;
import next.model.User;

public class UpdateQuestionController extends AbstractController {
	 private static final Logger log = LoggerFactory.getLogger(UpdateQuestionController.class);
	 private QuestionDao questionDao = new QuestionDao();
	 private Question question;
	 
	@Override
	public ModelAndView execute(HttpServletRequest req, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		
		HttpSession session = req.getSession();
		if(!UserSessionUtils.isLogined(session)) {
			return jspView("redirect:/users/loginForm");
		}
		
		Long questionId = Long.parseLong(req.getParameter("questionId"));
		question = questionDao.findById(questionId);
	
		User user = UserSessionUtils.getUserFromSession(session);
		String writer = question.getWriter();
		
		if(!user.isSameUser(writer)) {
			throw new IllegalStateException("다른 사용자가 글을 쓸 수 없습니다.");
		}
		
		Question newQuestion = new Question(req.getParameter("writer"),req.getParameter("title"),req.getParameter("contents"));
		question.update(newQuestion);
		questionDao.update(question);
		
		return jspView("redirect:/");
	}

}
