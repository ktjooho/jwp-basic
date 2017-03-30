package core.nmvc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import core.mvc.Controller;
import core.mvc.HandlerAdapter;
import core.mvc.ModelAndView;

public class ControllerHandlerAdapter implements HandlerAdapter {

	@Override
	public ModelAndView handle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// TODO Auto-generated method stub
		return ((Controller)handler).execute(request, response);
	}
	@Override
	public boolean isValid(Object handler) {
		// TODO Auto-generated method stub
		return ( handler instanceof Controller); 
	}
}
