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


public class ZipScriptBodyResult extends ZipScriptResult {
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

		// get the page content
		Template page = getPageTemplate(
				stack, zipEngine, invocation, location, servletContext);
		initializedCtx.put(getLayoutPlaceholderToken(), body);
		page.merge(initializedCtx, writer);
	}

	protected Template getPageTemplate(ValueStack stack, ZipEngine zipEngine,
			ActionInvocation invocation, String location, ServletContext servletContext)
			throws Exception {
		return zipEngine.getTemplate(getPagePath(), servletContext);
	}

	protected Template getBodyTemplate(ValueStack stack, ZipEngine zipEngine,
			ActionInvocation invocation, String location, ServletContext servletContext)
			throws Exception {
		return super.getPageTemplate(
				stack, zipEngine, invocation, location, servletContext);
	}

	private static final String LAYOUT_PLACEHOLDER = "layout_placeholder";
	protected String getLayoutPlaceholderToken () {
		return LAYOUT_PLACEHOLDER;
	}

	private static final String PAGE_PATH = "layouts/page.zs";
	protected String getPagePath () {
		return PAGE_PATH;
	}
}