package next.service.qna;

import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import core.mvc.Service;
import next.controller.UserSessionUtils;
import next.dao.AnswerDao;
import next.dao.QuestionDao;
import next.model.Answer;
import next.model.Question;
import next.model.Result;
import next.model.User;

public class QnAService implements Service {

	public final String RETURN_MSG_SUCCESS = "SUCCESS:";
	public final String RETURN_MSG_FAIL = "FAIL:";
	
	QuestionDao questionDao = new QuestionDao();
	AnswerDao answerDao = new AnswerDao();
	
	@Override
	public String service(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		// TODO Auto-generated method stub
		HttpSession session = req.getSession();
		
		if(!UserSessionUtils.isLogined(session)) {
			return RETURN_MSG_FAIL + "NOT LOGIN";
		}
		
		User user = UserSessionUtils.getUserFromSession(session);
		long questionId = Long.parseLong(req.getParameter("questionId"));
		Question question = questionDao.findById(questionId) ;
		String writer = question.getWriter();
		
		if(!user.getName().equals(writer)) {
			return RETURN_MSG_FAIL + "다른 사용자의 질문을 삭제할 수 없습니다.";
		}
		
		List<Answer> list = answerDao.findAllByQuestionId(questionId);
		Iterator<Answer> iter = list.iterator();
		
		while(iter.hasNext()) {
			Answer answer = iter.next();
			if(!answer.getWriter().equals(user.getName())) {
				return RETURN_MSG_FAIL + "다른 사용자가 댓글을 달은 질문은 삭제할 수 없습니다.";
			}
		}
		
		questionDao.delete(question);
		return RETURN_MSG_SUCCESS;
	}
}
