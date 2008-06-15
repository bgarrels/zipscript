package hudson.zipscript.parser.template.element.lang.variable.special.string;


public class XML extends AbstractReplacementMethod {

	public static final XML INSTANCE = new XML();

	protected char[] getCharsToReplace() {
		return new char[] {'<', '>', '&', '\"', '&'};
	}

	protected String[] getReplacementStrings() {
		return new String[] {
			"&lt;", "&gt;", "&amp;", "&quot;", "&apos;"	};
	}
}