package actions;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.interceptor.ServletRequestAware;

import com.opensymphony.xwork2.ActionSupport;

public class Test1 extends ActionSupport implements ServletRequestAware {

	private HttpServletRequest request;

	public String getMessage() {
		return "test message";
	}

	public void setServletRequest(HttpServletRequest request) {
		this.request = request;	
	}

	@Override
	public String execute() throws Exception {
		request.setAttribute("foo", "bar");
		return SUCCESS;
	}
}
