package next.controller.user;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import core.mvc.Controller;
import next.dao.QuestionDao;
import next.dao.UserDao;
import next.model.Question;

public class HomeController implements Controller {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
    	
    	QuestionDao questionDao = new QuestionDao();
    	
    	UserDao userDao = new UserDao();
        
    	req.setAttribute("users", userDao.findAll());
    	List<Question> list = questionDao.findAll();
        req.setAttribute("questions", list);
    	
        return "home.jsp";
    }
}
