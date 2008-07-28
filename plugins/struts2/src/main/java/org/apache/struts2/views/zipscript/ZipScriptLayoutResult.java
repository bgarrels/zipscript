package org.apache.struts2.views.zipscript;

import hudson.zipscript.ZipEngine;
import hudson.zipscript.parser.context.Context;
import hudson.zipscript.template.Template;

import java.io.Writer;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.util.ValueStack;

public class ZipScriptLayoutResult extends ZipScriptBodyResult {
	private static final long serialVersionUID = -3624317866978957286L;

	@Override
	protected void writeOutput(Object context, ValueStack stack,
			ZipEngine zipEngine, ActionInvocation invocation, String location,
			ServletContext servletContext, HttpServletRequest request,
			HttpServletResponse response, Writer writer) throws Exception {

		// get the body content
		Template body = getBodyTemplate(
				stack, zipEngine, invocation, location, servletContext);
		Context initializedCtx = body.initialize(context);
		initializedCtx.put(getScreenPlaceholderToken(), body);

		// get the layout content
		Template layout = getLayoutTemplate(
				stack, zipEngine, invocation, location, servletContext);
		layout.initialize(initializedCtx);
		initializedCtx.put(getLayoutPlaceholderToken(), layout);

		// get the page content
		Template page = getPageTemplate(
				stack, zipEngine, invocation, location, servletContext);
		page.merge(initializedCtx, writer);
	}

	protected Template getLayoutTemplate(ValueStack stack, ZipEngine zipEngine,
			ActionInvocation invocation, String location, ServletContext servletContext)
			throws Exception {
		return zipEngine.getTemplate(getLayoutPath(), servletContext);
	}

	private static final String SCREEN_PLACEHOLDER = "screen_placeholder";
	protected String getScreenPlaceholderToken () {
		return SCREEN_PLACEHOLDER;
	}

	private static final String LAYOUT_PATH = "layouts/layout.zs";
	protected String getLayoutPath () {
		return LAYOUT_PATH;
	}
}