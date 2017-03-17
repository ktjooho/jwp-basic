package next.controller.qna;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.Logger;
import core.mvc.Controller;
import next.dao.AnswerDao;

public class DeleteAnswerController implements Controller {

	private static final Logger log = (Logger) LoggerFactory.getLogger(AddAnswerController.class);

	@Override
	public String execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		// TODO Auto-generated method stub
		
		AnswerDao answerDao = new AnswerDao();
		int answerId = Integer.parseInt(req.getParameter("answerId"));
		//answerDao.
		
		return null;
	}

}
