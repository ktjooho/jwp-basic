package core.mvc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface Service {
	
	//////////////////////
	// C-R-U-D로 묶어야되나./
	/////////////////////
	
	//
	//
	//Return Type 고민.
	// - Return 되는 부분이 4곳이다. 이것을 하나의 리턴으로 묶을 수 있어야함. 
	// - Return을 String으로 하면
	
	// Fail:[msg]
	// Success :[msg]
	// 
	String service(HttpServletRequest req, HttpServletResponse resp) throws Exception;
	
}
