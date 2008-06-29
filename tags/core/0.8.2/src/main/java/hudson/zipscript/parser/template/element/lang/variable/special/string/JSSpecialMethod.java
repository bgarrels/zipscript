package hudson.zipscript.parser.template.element.lang.variable.special.string;


public class JSSpecialMethod extends AbstractReplacementMethod {

	public static final JSSpecialMethod INSTANCE = new JSSpecialMethod();

	protected char[] getCharsToReplace() {
		return new char[] {'\"', '\'', '>', '\r', '\n'};
	}

	protected String[] getReplacementStrings() {
		return new String[] {
			"\\\"", "\\'", "\\>", "\\r", "\\n"};
	}
}