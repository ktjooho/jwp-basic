package next.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface Controller {
	public static final String DEFAULT_REDIRECT_PREFIX = "redirect:";
	public String execute(HttpServletRequest req, HttpServletResponse resp) throws Exception;
}
