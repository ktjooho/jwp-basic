package next.controller.qna;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.annotation.JsonView;

import core.mvc.AbstractController;
import core.mvc.ModelAndView;
import next.controller.UserSessionUtils;
import next.dao.QuestionDao;
import next.model.Question;
import next.model.Result;
import next.model.User;

public class UpdateFormQuestionController extends AbstractController {

	 private Question question;
	 private QuestionDao questionDao = new QuestionDao();
	 
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
			//jsonView().addObject("question", Result.fail("다른 사용자가 글을 쓸수 없습니다."));
			throw new IllegalStateException("다른 사용자가 글을 쓸 수 없습니다.");
		}
		
		
		return jspView("/qna/update.jsp").addObject("question", question);
	}

}
