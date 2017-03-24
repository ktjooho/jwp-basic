package core.mvc;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;

import core.mvc.view.View;

@WebServlet(name = "dispatcher", urlPatterns = "/", loadOnStartup = 1)
public class DispatcherServlet extends HttpServlet {
	
	public class JsonView implements View{
		@Override
		public void render(Object target, HttpServletRequest req, HttpServletResponse resp) throws IOException {
			// TODO Auto-generated method stub
			ObjectMapper mapper = new ObjectMapper();
			resp.setContentType("application/json;charset=UTF-8");
	        PrintWriter out = resp.getWriter();
	        out.print(mapper.writeValueAsString(target));
	        
		}
	}
	public class JspView implements View{
		@Override
		public void render(Object target, HttpServletRequest req, HttpServletResponse resp) throws IOException {
			// TODO Auto-generated method stub
			String viewName = (String) target;
			if (viewName.startsWith(DEFAULT_REDIRECT_PREFIX)) {
	            resp.sendRedirect(viewName.substring(DEFAULT_REDIRECT_PREFIX.length()));
	            return;
	        }

	        RequestDispatcher rd = req.getRequestDispatcher(viewName);
	        try {
				rd.forward(req, resp);
			} catch (ServletException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	
    private static final long serialVersionUID = 1L;
    private static final Logger logger = LoggerFactory.getLogger(DispatcherServlet.class);
    private static final String DEFAULT_REDIRECT_PREFIX = "redirect:";
    
    private RequestMapping rm;

    @Override
    public void init() throws ServletException {
        rm = new RequestMapping();
        rm.initMapping();
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String requestUri = req.getRequestURI();
        logger.debug("Method : {}, Request URI : {}", req.getMethod(), requestUri);
        
        Pair<Controller, View> cv =  rm.findViewAndController(requestUri);
        
        Controller controller = cv.getLeft();
        View view = cv.getRight();
        try {
        	
        	Object target = controller.executes(req, resp);
        	if(target != null) {
        		view.render(target, req, resp);
        	}
        	/*
            if (viewName != null) {
                move(viewName, req, resp);
            }
            */
        } catch (Throwable e) {
            logger.error("Exception : {}", e);
            throw new ServletException(e.getMessage());
        }
    }
    
    //JSP 전용. 
    private void move(String viewName, HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        if (viewName.startsWith(DEFAULT_REDIRECT_PREFIX)) {
            resp.sendRedirect(viewName.substring(DEFAULT_REDIRECT_PREFIX.length()));
            return;
        }

        RequestDispatcher rd = req.getRequestDispatcher(viewName);
        rd.forward(req, resp);
    }
}
