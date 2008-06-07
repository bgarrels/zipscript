package hudson.zipscript.parser.template.data;

import java.util.HashMap;
import java.util.Map;

public class ParsingSession {

	private ParseParameters parameters;
	private Map unknownVariablePatterns;

	public ParsingSession (ParseParameters parameters) {
		this.parameters = parameters;
	}

	public ParseParameters getParameters () {
		return parameters;
	}

	public void setParameters (ParseParameters parameters) {
		this.parameters = parameters;
	}

	public boolean isVariablePatternRecognized (String pattern) {
		if (null == unknownVariablePatterns)
			unknownVariablePatterns = new HashMap();
		return (null != unknownVariablePatterns.get(pattern));
	}

	public void setReferencedVariable (String pattern) {
		if (null == unknownVariablePatterns)
			unknownVariablePatterns = new HashMap();
		unknownVariablePatterns.put(pattern, Boolean.TRUE);
	}
}