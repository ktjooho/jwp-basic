package next.controller.qna;



import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import core.mvc.AbstractController;
import core.mvc.ModelAndView;
import next.dao.QuestionDao;
import next.model.Question;

public class ListQuestionController extends AbstractController {

	
	private QuestionDao questionDao = new QuestionDao();
	
	@Override
	public ModelAndView execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		List<Question> questions = questionDao.findAll();
		return jsonView().addObject("questions", questions);
	}

}
