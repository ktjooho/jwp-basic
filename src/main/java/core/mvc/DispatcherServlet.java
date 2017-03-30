package core.mvc;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Lists;

import core.nmvc.AnnotationHandlerMapping;
import core.nmvc.ControllerHandlerAdapter;
import core.nmvc.HandlerExecution;
import core.nmvc.HandlerExecutionAdapter;

@WebServlet(name = "dispatcher", urlPatterns = "/", loadOnStartup = 1)
public class DispatcherServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger logger = LoggerFactory.getLogger(DispatcherServlet.class);

    //위 아래의 맵핑들을 하나의 인터페이스로 묶을 수 있어야한다.
    // Mapping하는 방식.
    // Controller 형태가 다르다.? 
    
    
    private AnnotationHandlerMapping ahm;
    private List<HandlerMapping> handlerMappings = Lists.newArrayList();
    private List<HandlerAdapter> handlerAdapters = Lists.newArrayList();

    @Override
    public void init() throws ServletException {
    	LegacyHandlerMapping lhm = new LegacyHandlerMapping();
        lhm.initMapping();
        
        AnnotationHandlerMapping ahm = new AnnotationHandlerMapping("next.controller");
        ahm.initialize();
        
        handlerMappings.add(lhm);
        handlerMappings.add(ahm);
        
        handlerAdapters.add(new ControllerHandlerAdapter());
        handlerAdapters.add(new HandlerExecutionAdapter());
    }
    
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String requestUri = req.getRequestURI();
        
        logger.debug("Method : {}, Request URI : {}", req.getMethod(), requestUri);

        for(HandlerMapping handlerMapping : handlerMappings) {

        	Object handler = handlerMapping.getHandler(req);
        	
        	if(handler == null)
        		continue;
        	
        	try {
				render(req,resp,execute(req, resp, handler));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        
        }
    }
    private ModelAndView execute(HttpServletRequest req, HttpServletResponse resp, Object handler) throws ServletException {
    	for(HandlerAdapter adapter : handlerAdapters) {
    		if(adapter.isValid(handler)) {
    			try{
    			return adapter.handle(req, resp, handler);
    			} catch (Throwable e) {
    				logger.error("Exception : {}",e);
    				throw new ServletException(e.getMessage());
    			}
    		}
    	}
    	return null;
    }
    private void render(HttpServletRequest req, HttpServletResponse resp, ModelAndView mav) throws Exception {
    	View view = mav.getView();
    	view.render(mav.getModel(), req, resp);
    }
}
