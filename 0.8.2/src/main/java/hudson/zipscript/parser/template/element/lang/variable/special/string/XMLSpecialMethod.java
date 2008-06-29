package hudson.zipscript.parser.template.element.lang.variable.special.string;


public class XMLSpecialMethod extends AbstractReplacementMethod {

	public static final XMLSpecialMethod INSTANCE = new XMLSpecialMethod();

	protected char[] getCharsToReplace() {
		return new char[] {'<', '>', '&', '\"', '&'};
	}

	protected String[] getReplacementStrings() {
		return new String[] {
			"&lt;", "&gt;", "&amp;", "&quot;", "&apos;"	};
	}
}