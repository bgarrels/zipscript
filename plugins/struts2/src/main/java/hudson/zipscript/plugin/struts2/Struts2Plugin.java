package hudson.zipscript.plugin.struts2;

import hudson.zipscript.ZipEngine;
import hudson.zipscript.parser.Constants;
import hudson.zipscript.parser.context.Context;
import hudson.zipscript.parser.context.ExtendedContext;
import hudson.zipscript.parser.exception.InitializationException;
import hudson.zipscript.parser.template.element.component.Component;
import hudson.zipscript.parser.template.element.lang.variable.adapter.VariableAdapterFactory;
import hudson.zipscript.plugin.Plugin;
import hudson.zipscript.plugin.struts2.parser.context.RequestParameterMap;
import hudson.zipscript.resource.WebInfResourceLoader;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.LocaleProvider;
import com.opensymphony.xwork2.TextProviderFactory;

public class Struts2Plugin implements Plugin {

	static final String REQUEST = "Request";
	static final String ACTION = "Action";
	static final String PARAMETERS = "Parameters";
	static final String RESPONSE = "Response";
	
	public Component[] getComponents() {
		return null;
	}

	public VariableAdapterFactory getVariableAdapterFactory() {
		return new hudson.zipscript.plugin.struts2.VariableAdapterFactory();
	}

	public void initialize(ZipEngine zipEngine, Map initParameters)
			throws InitializationException {
		if (null == initParameters.get(Constants.TEMPLATE_RESOURCE_LOADER_CLASS)) {
			initParameters.put(Constants.TEMPLATE_RESOURCE_LOADER_CLASS,
					WebInfResourceLoader.class.getName());
			initParameters.put("templateResourceLoader.pathPrefix", "zs/");
		}
		if (null == initParameters.get(Constants.INCLUDE_RESOURCE_LOADER_CLASS)) {
			initParameters.put(Constants.INCLUDE_RESOURCE_LOADER_CLASS,
					WebInfResourceLoader.class.getName());
			initParameters.put("includeResourceLoader.pathPrefix", "zs/fragments/");
		}
		if (null == initParameters.get(Constants.MACROLIB_RESOURCE_LOADER_CLASS)) {
			initParameters.put(Constants.MACROLIB_RESOURCE_LOADER_CLASS,
					WebInfResourceLoader.class.getName());
			initParameters.put("macroLibResourceLoader.pathPrefix", "zs/macros/");
		}
	}

	public void initialize(ExtendedContext context)
			throws InitializationException {
		ActionContext actionContext = ActionContext.getContext();
		if (null != actionContext) {
			Object action = actionContext.getActionInvocation().getAction();
			if (action instanceof LocaleProvider) {
				context.put(Constants.RESOURCE, new TextProviderFactory().createInstance(
						action.getClass(), (LocaleProvider) action));
				context.setLocale(((LocaleProvider) action).getLocale());
			}
			HttpServletRequest req = ServletActionContext.getRequest();
			context.put(REQUEST, req);
			context.put(RESPONSE, ServletActionContext.getResponse());
			context.put(ACTION, action);
			context.put(PARAMETERS, new RequestParameterMap(req));
		}
	}

	public Context wrapContextObject(Object object) {
		return null;
	}
}