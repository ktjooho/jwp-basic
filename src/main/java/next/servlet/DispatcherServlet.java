package next.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import next.controller.Controller;
import next.support.context.RequestMapping;

/**
 * 
 * Resolve url to associated controller that will process program logic.
 * Rendering the view that is from controller.
 *
 * 			 Controller 
 * 			     ↑
 * Request → Dispatcher → View(Response)
 * @author nokdu
 *
 */

@WebServlet(name="dispatcher",urlPatterns="/",loadOnStartup=1)
public class DispatcherServlet extends HttpServlet {
	
	private static final String DEFAULT_REDIRECT_PREFIX = "redirect:";
	private static final Logger log = LoggerFactory.getLogger(DispatcherServlet.class);
	private RequestMapping requestMapping = new RequestMapping();

	
	/**
	 * Fill urls.
	 */

	@Override
	public void init() throws ServletException {
		// TODO Auto-generated method stub
		requestMapping = new RequestMapping();
		requestMapping.initMapping();
	}
	
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		log.debug("Req URL:" + req.getRequestURL());
		log.debug("Req Method:" + req.getMethod());
		log.debug("Req ContextPath:" + req.getContextPath());
		log.debug("Req Servlet Path:" + req.getServletPath());
		
		String uri = req.getRequestURI();
		Controller controller = requestMapping.getController(uri);

		try {
			String viewName = controller.execute(req, resp);
			move(viewName, req, resp);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error(e.getMessage());
		}
	}

	private void move(String viewName, HttpServletRequest req, HttpServletResponse resp) throws Exception {
		if(viewName.startsWith(DEFAULT_REDIRECT_PREFIX)) {
			resp.sendRedirect(viewName.substring(DEFAULT_REDIRECT_PREFIX.length()));
			return ;
		}
		req.getRequestDispatcher(viewName).forward(req, resp);
	}
}
