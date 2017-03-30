package core.mvc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface HandlerAdapter {
	public ModelAndView handle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception;
	public boolean isValid(Object handler);
}
