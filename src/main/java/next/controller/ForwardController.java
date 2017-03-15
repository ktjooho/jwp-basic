package next.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ForwardController implements Controller {

	private String forwardUrl = null;
	
	public ForwardController(String url) {
		// TODO Auto-generated constructor stub
		this.forwardUrl = forwardUrl;
	}
	
	@Override
	public String execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		// TODO Auto-generated method stub
		return forwardUrl;
	}

}
