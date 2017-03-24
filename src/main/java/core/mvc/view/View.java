package core.mvc.view;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface View {
	public void render(Object target, HttpServletRequest req, HttpServletResponse resp)  throws IOException ;
}
