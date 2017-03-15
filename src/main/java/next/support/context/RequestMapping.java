package next.support.context;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServlet;

import next.controller.Controller;
import next.controller.ForwardController;



public class RequestMapping {

	private Map<String, Controller> requestMap = new HashMap<String, Controller>();
	
	public RequestMapping() {
		// TODO Auto-generated constructor stub
	}
	
	public Controller getController(String url) {
		return requestMap.get(url);
	}
	
	public void initMapping() {
		requestMap.put("/", new ForwardController("index.jsp"));
		
		
	}
	
	
	
	
	
}
