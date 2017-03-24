package next.controller.qna;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.Logger;
import core.mvc.AbstractController;
import core.mvc.Controller;
import core.mvc.ModelAndView;
import next.controller.UserSessionUtils;
import next.dao.QuestionDao;
import next.model.Question;
import next.model.User;


public class AddQuestionController extends AbstractController  {
	private static final Logger log = (Logger) LoggerFactory.getLogger(AddQuestionController.class);
	
	private QuestionDao questionDao = new QuestionDao();
	
	@Override
	public ModelAndView execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		
		
		Question question = new Question(request.getParameter("writer"), request.getParameter("title"), request.getParameter("contents"));
		questionDao.insert(question);
		return jspView("redirect:/");
		
		
		//HttpSession session = request.getSession();
		/*
		if(UserSessionUtils.isLogined(request.getSession())) {
			 //return jspView("redirect:/user/login");
			//User user = UserSessionUtils.getUserFromSession(request.getSession());
			
			
		}
		
		
		
		
		return null;
		*/
	}

}
