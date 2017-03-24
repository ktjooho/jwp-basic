package core.mvc;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import core.mvc.view.JsonView;
import core.mvc.view.JspView;
import core.mvc.view.View;
import next.controller.HomeController;
import next.controller.qna.AddAnswerController;
import next.controller.qna.DeleteAnswerController;
import next.controller.qna.ShowController;
import next.controller.user.CreateUserController;
import next.controller.user.ListUserController;
import next.controller.user.LoginController;
import next.controller.user.LogoutController;
import next.controller.user.ProfileController;
import next.controller.user.UpdateFormUserController;
import next.controller.user.UpdateUserController;

public class RequestMapping {
    private static final Logger logger = LoggerFactory.getLogger(DispatcherServlet.class);
    //private Map<String, Controller> mappings = new HashMap<>();
    private Map<String, Pair<Controller, View>> mappings = new HashMap<>();
    
    private Pair<Controller,View> makePair(Controller controller, View view)
    {
    	return new ImmutablePair<>(controller,view);
    }
    void initMapping() {
    	
    	Pair<String, String> p = new ImmutablePair<>("a", "b");
    	
    	mappings.put("/",makePair(new HomeController(), new JspView()));
    	
    	
    	//mappings.put("/", new HomeController());
        mappings.put("/users/form", makePair(new ForwardController("/user/form.jsp"),new JspView()));
        mappings.put("/users/loginForm",makePair(new ForwardController("/user/login.jsp"),new JspView()));
        mappings.put("/users", makePair(new ListUserController(),new JspView()));
        mappings.put("/users/login", makePair(new LoginController(),new JspView()));
        mappings.put("/users/profile", makePair(new ProfileController(),new JspView()));
        mappings.put("/users/logout", makePair(new LogoutController(),new JspView()));
        mappings.put("/users/create", makePair(new CreateUserController(), new JspView()));
        mappings.put("/users/updateForm", makePair(new UpdateFormUserController(), new JspView()));
        mappings.put("/users/update", makePair(new UpdateUserController(),new JspView()));
        mappings.put("/qna/show", makePair(new ShowController(),new JspView()));
        mappings.put("/api/qna/addAnswer", makePair(new AddAnswerController(),new JsonView()));
        mappings.put("/api/qna/deleteAnswer", makePair(new DeleteAnswerController(),new JsonView()));

        
        
        
        logger.info("Initialized Request Mapping!");
    }
    Pair<Controller,View> findViewAndController(String url) {
    	return mappings.get(url);
    }
    public Controller findController(String url) {
        return mappings.get(url).getLeft();
    }

    void put(String url, Controller controller) {
       // mappings.put(url, controller);
    }
}
