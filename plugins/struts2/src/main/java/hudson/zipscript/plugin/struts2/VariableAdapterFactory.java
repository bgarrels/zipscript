package hudson.zipscript.plugin.struts2;

import hudson.zipscript.parser.context.ExtendedContext;
import hudson.zipscript.parser.template.data.ParsingSession;
import hudson.zipscript.parser.template.element.Element;
import hudson.zipscript.parser.template.element.lang.variable.adapter.MapAdapter;
import hudson.zipscript.parser.template.element.lang.variable.adapter.ObjectAdapter;
import hudson.zipscript.parser.template.element.lang.variable.adapter.SequenceAdapter;
import hudson.zipscript.parser.template.element.lang.variable.special.SpecialMethod;

import javax.servlet.http.HttpServletRequest;

import com.opensymphony.xwork2.TextProvider;

public class VariableAdapterFactory
		implements
		hudson.zipscript.parser.template.element.lang.variable.adapter.VariableAdapterFactory {

	public String getDefaultGetterMethod(Object obj) {
		if (obj instanceof TextProvider) {
			return "getText";
		}
		else if (obj instanceof HttpServletRequest) {
			return "getParameter";
		}
		else return null;
	}

	public MapAdapter getMapAdapter(Object map) {
		return null;
	}

	public ObjectAdapter getObjectAdapter(Object object) {
		return null;
	}

	public String[] getReservedContextAttributes() {
		return new String[] {
				"Request",
				"Action",
				"Response",
				"Parameter"
		};
	}

	public SequenceAdapter getSequenceAdapter(Object sequence) {
		return null;
	}

	public SpecialMethod getSpecialMethod(String name, Element[] parameters,
			Object object, ExtendedContext context, Element element) {
		return null;
	}

	public SpecialMethod getStringEscapingStringMethod(String method,
			ParsingSession session) {
		return null;
	}
}