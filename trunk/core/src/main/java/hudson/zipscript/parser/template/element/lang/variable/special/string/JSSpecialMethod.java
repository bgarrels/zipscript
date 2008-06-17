package hudson.zipscript.parser.template.element.lang.variable.special.string;


public class JSSpecialMethod extends AbstractReplacementMethod {

	public static final JSSpecialMethod INSTANCE = new JSSpecialMethod();

	protected char[] getCharsToReplace() {
		return new char[] {'\"', '\'', '>'};
	}

	protected String[] getReplacementStrings() {
		return new String[] {
			"\\\"", "\\'", "\\>"};
	}
}