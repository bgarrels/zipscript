package hudson.zipscript.parser.template.element.lang.variable.special.string;


public class JS extends AbstractReplacementMethod {

	public static final JS INSTANCE = new JS();

	protected char[] getCharsToReplace() {
		return new char[] {'\"', '\'', '>'};
	}

	protected String[] getReplacementStrings() {
		return new String[] {
			"\\\"", "\\'", "\\>"};
	}
}