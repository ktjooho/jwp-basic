package next.controller.qna;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;

import ch.qos.logback.classic.Logger;
import core.mvc.Controller;
import next.dao.AnswerDao;
import next.model.Answer;
import next.model.Result;

public class DeleteAnswerController implements Controller {

	private static final Logger log = (Logger) LoggerFactory.getLogger(AddAnswerController.class);

	@Override
	public String execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		// TODO Auto-generated method stub
		
		int answerId = Integer.parseInt(req.getParameter("answerId"));
		AnswerDao answerDao = new AnswerDao();
		Answer answer = answerDao.findById(answerId);
		
		boolean isSuccess = answerDao.delete(answer.getAnswerId());
		Result result = Result.ok();
		/*
		if(!isSuccess)
			result = Result.fail("Fail to remove answer");
		*/
		ObjectMapper mapper = new ObjectMapper();
		resp.setContentType("application/json;charset=UTF-8");
		PrintWriter out = resp.getWriter();
		out.print(mapper.writeValueAsString(result));
		
		return null;
	}
}
