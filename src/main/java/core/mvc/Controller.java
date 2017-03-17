package core.mvc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public interface Controller {
	public static final Logger logger = LoggerFactory.getLogger(DispatcherServlet.class);
    String execute(HttpServletRequest req, HttpServletResponse resp) throws Exception;
}
