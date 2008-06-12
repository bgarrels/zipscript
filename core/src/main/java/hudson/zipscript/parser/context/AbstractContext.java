package hudson.zipscript.parser.context;

import hudson.zipscript.parser.template.data.ParsingSession;

public abstract class AbstractContext implements ZSContext {

	private ParsingSession parsingSession;

	public ParsingSession getParsingSession() {
		return parsingSession;
	}

	public void setParsingSession(ParsingSession parsingSession) {
		this.parsingSession = parsingSession;
	}
}
