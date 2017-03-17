package next.controller.qna;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ch.qos.logback.classic.Logger;
import core.mvc.Controller;
import next.dao.AnswerDao;
import next.dao.QuestionDao;
import next.model.Answer;
import next.model.Question;

public class ShowController implements Controller {

	@Override
	public String execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		// TODO Auto-generated method stub
		
		String questionId = req.getParameter("questionId");
		logger.debug("QuestionId :" + questionId);
		
		QuestionDao questionDao = new QuestionDao();
		AnswerDao answerDao = new AnswerDao();
		
		Question question = questionDao.findByQuestionId(questionId);
		List<Answer> answers = answerDao.findByQuestionId(questionId) ;
		
		req.setAttribute("question", question);
		req.setAttribute("answers", answers);
		
		//req.setAttribute("", o);
		
		//req.setAttribute("questions", o);
		
		//return "/qna/show.jsp
		
		// 
		
		return "/qna/show.jsp";
	}

}
