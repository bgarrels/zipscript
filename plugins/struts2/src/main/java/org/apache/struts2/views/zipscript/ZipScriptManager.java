package org.apache.struts2.views.zipscript;

import hudson.zipscript.ZipEngine;
import hudson.zipscript.parser.Constants;
import hudson.zipscript.plugin.Plugin;
import hudson.zipscript.plugin.struts2.Struts2Plugin;
import hudson.zipscript.plugin.struts2.parser.context.ActionRequestContextWrapper;
import hudson.zipscript.resource.WebInfResourceLoader;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.StrutsConstants;
import org.apache.struts2.dispatcher.Dispatcher;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.config.ConfigurationProvider;
import com.opensymphony.xwork2.inject.Inject;
import com.opensymphony.xwork2.util.location.LocatableProperties;

public class ZipScriptManager {

	private static final String INIT_PARAM_PREFIX = "zipscript.";
	private static final String CONFIG_SERVLET_CONTEXT_KEY = ZipEngine.class.getName();

	private String encoding;

	@Inject(StrutsConstants.STRUTS_I18N_ENCODING)
	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

	public String getEncoding() {
		return encoding;
	}

	public final synchronized ZipEngine getZipEngine (ServletContext servletContext) {
		ZipEngine zipEngine = (ZipEngine) servletContext.getAttribute(CONFIG_SERVLET_CONTEXT_KEY);
		if (null == zipEngine) {
			zipEngine = createNewZipEngine(servletContext);
			servletContext.setAttribute(CONFIG_SERVLET_CONTEXT_KEY, zipEngine);
		}
		return zipEngine;
	}

	protected ZipEngine createNewZipEngine (ServletContext servletContext) {
		Map<String, Object> props = new HashMap<String, Object>();

		// is there a better way to do this?
		LocatableProperties locatableProperties = new LocatableProperties();
		List<ConfigurationProvider> configurationProviders = Dispatcher.getInstance()
				.getConfigurationManager().getConfigurationProviders();
		for (ConfigurationProvider cp : configurationProviders) {
			try {
				cp.register(null, locatableProperties);
			}
			catch (Exception e) {}
		}
		for (Enumeration<Object> e=locatableProperties.keys(); e.hasMoreElements(); ) {
			String key = e.nextElement().toString();
			if (key.startsWith(INIT_PARAM_PREFIX)) {
				props.put(key.substring(INIT_PARAM_PREFIX.length()), locatableProperties.get(key));
			}
		}
		
		props.put(Constants.INCLUDE_RESOURCE_LOADER_PARAMETER, servletContext);
		props.put(Constants.MACROLIB_RESOURCE_LOADER_PARAMETER, servletContext);

		ZipEngine zipEngine = ZipEngine.createInstance(
				props, new Plugin[]{new Struts2Plugin()});

		// set resource loaders
		String rootDirectory = getProperty("rootDirectory", "zs/", props);
		String fragmentsDirectory = getProperty("fragmentsDirectory", "fragments/", props);
		String macrosDirectory = getProperty("macrosDirectory", "macros/", props);

		zipEngine.setTemplateResourceLoader(
				new WebInfResourceLoader(rootDirectory));
		zipEngine.setIncludeResourceLoader(
				new WebInfResourceLoader(rootDirectory + fragmentsDirectory));
		zipEngine.setMacroLibResourceLoader(
				new WebInfResourceLoader(rootDirectory + macrosDirectory));

		return zipEngine;
	}

	private String getProperty (String key, String defaultValue, Map properties) {
		Object obj = properties.get(key);
		if (null == obj) return defaultValue;
		else return obj.toString();
	}

	public Object createContext (
			ActionInvocation actionInvocation, HttpServletRequest request) {
		return new ActionRequestContextWrapper(
				actionInvocation.getAction(), request);
	}
}
