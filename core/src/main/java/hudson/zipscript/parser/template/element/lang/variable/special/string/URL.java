package hudson.zipscript.parser.template.element.lang.variable.special.string;

import hudson.zipscript.parser.context.ZSContext;
import hudson.zipscript.parser.template.data.ParsingSession;
import hudson.zipscript.parser.template.element.lang.variable.special.SpecialMethod;

import java.net.URLEncoder;


public class URL implements SpecialMethod {

	private String encoding;
	public URL (ParsingSession parsingSession) {
		this.encoding = "UTF-8";
	}

	public Object execute(Object source, ZSContext context) throws Exception {
		return URLEncoder.encode((String) source, encoding);
	}
}