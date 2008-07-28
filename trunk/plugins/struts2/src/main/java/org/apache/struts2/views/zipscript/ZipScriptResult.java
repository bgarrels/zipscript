package org.apache.struts2.views.zipscript;

import hudson.zipscript.ZipEngine;
import hudson.zipscript.template.Template;

import java.io.OutputStreamWriter;
import java.io.Writer;

import javax.servlet.Servlet;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspFactory;
import javax.servlet.jsp.PageContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.StrutsConstants;
import org.apache.struts2.dispatcher.StrutsResultSupport;
import org.apache.struts2.views.JspSupportServlet;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.inject.Inject;
import com.opensymphony.xwork2.util.ValueStack;

public class ZipScriptResult extends StrutsResultSupport {
	private static final long serialVersionUID = -3624317866978957286L;

	private static final Log log = LogFactory.getLog(ZipScriptResult.class);

	String defaultEncoding;
	private ZipScriptManager zipScriptManager;
    @Inject("zipScriptManager")
    public void setZipScriptManager(ZipScriptManager mgr) {
        this.zipScriptManager = mgr;
    }

	public ZipScriptResult() {
		super();
	}

	public ZipScriptResult(String location) {
		super(location);
	}

	@Inject(StrutsConstants.STRUTS_I18N_ENCODING)
	public void setDefaultEncoding(String val) {
		defaultEncoding = val;
	}

	/**
	 * Creates a ZipScript context from the action, loads a ZipScript template and
	 * executes the template. Output is written to the servlet output stream.
	 * 
	 * @param finalLocation
	 *            the location of the ZipScript template
	 * @param invocation
	 *            an encapsulation of the action execution state.
	 * @throws Exception
	 *             if an error occurs when creating the ZipScript context,
	 *             loading or executing the template or writing output to the
	 *             servlet response stream.
	 */
	public void doExecute(String finalLocation, ActionInvocation invocation)
			throws Exception {

		// get working values
		ValueStack stack = ActionContext.getContext().getValueStack();
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		JspFactory jspFactory = null;
		ServletContext servletContext = ServletActionContext
				.getServletContext();
		Servlet servlet = JspSupportServlet.jspSupportServlet;

		ZipEngine zipEngine = zipScriptManager.getZipEngine(servletContext);

		boolean usedJspFactory = false;
		PageContext pageContext = (PageContext) ActionContext.getContext().get(
				ServletActionContext.PAGE_CONTEXT);

		if (pageContext == null && servlet != null) {
			jspFactory = JspFactory.getDefaultFactory();
			pageContext = jspFactory.getPageContext(servlet, request, response,
					null, true, 8192, true);
			ActionContext.getContext().put(ServletActionContext.PAGE_CONTEXT, pageContext);
			usedJspFactory = true;
		}

		try {
			String encoding = getEncoding(finalLocation);
			String contentType = getContentType(finalLocation);

			if (encoding != null) {
				contentType = contentType + ";charset=" + encoding;
			}

			Writer writer = new OutputStreamWriter(response.getOutputStream(), encoding);
			Object context = zipScriptManager.createContext(invocation, request);

			writeOutput(context, stack, zipEngine, invocation, finalLocation,
					servletContext, request, response, writer);

			response.setContentType(contentType);
			writer.flush();
		} catch (Exception e) {
			log.error("Unable to render ZipScript Template, '" + finalLocation + "'", e);
			throw e;
		} catch (Throwable e) {
			log.error("Unable to render ZipScript Template, '" + finalLocation + "'", e);
			throw new Exception(e);
		} finally {
			if (usedJspFactory) {
				jspFactory.releasePageContext(pageContext);
			}
		}
		return;
	}

	protected void writeOutput(Object context, ValueStack stack, ZipEngine zipEngine,
			ActionInvocation invocation, String location, ServletContext servletContext,
			HttpServletRequest request, HttpServletResponse response, Writer writer)
	throws Exception {
		Template t = getPageTemplate(
				stack, zipEngine, invocation, location, servletContext);
		t.merge(context, writer);
	}

	/**
	 * Given a value stack, a ZipEngine, and an action invocation, this
	 * method returns the appropriate ZipScript template to render.
	 * 
	 * @param stack
	 *            the value stack to resolve the location again (when parse
	 *            equals true)
	 * @param velocity
	 *            the velocity engine to process the request against
	 * @param invocation
	 *            an encapsulation of the action execution state.
	 * @param location
	 *            the location of the template
	 * @param encoding
	 *            the charset encoding of the template
	 * @return the template to render
	 * @throws Exception
	 *             when the requested template could not be found
	 */
	protected Template getPageTemplate(ValueStack stack, ZipEngine zipEngine,
			ActionInvocation invocation, String location, ServletContext servletContext)
			throws Exception {
		return zipEngine.getTemplate(getTemplatesPath() + location, servletContext);
	}

	protected String getTemplatesPath () {
		return "templates/";
	}

	/**
	 * Retrieve the content type for this template. <p/> People can override
	 * this method if they want to provide specific content types for specific
	 * templates (eg text/xml).
	 * 
	 * @return The content type associated with this template (default
	 *         "text/html")
	 */
	protected String getContentType(String templateLocation) {
		return "text/html";
	}

	/**
	 * Retrieve the encoding for this template. <p/> People can override this
	 * method if they want to provide specific encodings for specific templates.
	 * 
	 * @return The encoding associated with this template (defaults to the value
	 *         of 'struts.i18n.encoding' property)
	 */
	protected String getEncoding(String templateLocation) {
		String encoding = defaultEncoding;
		if (encoding == null) {
			encoding = System.getProperty("file.encoding");
		}
		if (encoding == null) {
			encoding = "UTF-8";
		}
		return encoding;
	}
}