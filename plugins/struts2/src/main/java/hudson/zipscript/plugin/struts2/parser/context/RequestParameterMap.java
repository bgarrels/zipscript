package hudson.zipscript.plugin.struts2.parser.context;

import javax.servlet.http.HttpServletRequest;

public class RequestParameterMap {

	HttpServletRequest request;

	public RequestParameterMap (HttpServletRequest request) {
		this.request = request;
	}

	public String get (String key) {
		return request.getParameter(key);
	}

	public String[] values(String key) {
		return request.getParameterValues(key);
	}
}
