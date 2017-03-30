package core.nmvc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import core.mvc.ModelAndView;

@FunctionalInterface
public interface HandlerExecution {
    public ModelAndView handle(HttpServletRequest request, HttpServletResponse response) throws Exception;
}
