package hudson.zipscript.parser.context;

import hudson.zipscript.parser.template.data.ParsingSession;
import hudson.zipscript.resource.macrolib.MacroManager;

public abstract class AbstractContext implements ExtendedContext {

	private ParsingSession parsingSession;
	private MacroManager macroManager;
	private boolean initialized;

	public ParsingSession getParsingSession() {
		return parsingSession;
	}

	public void setParsingSession(ParsingSession parsingSession) {
		this.parsingSession = parsingSession;
	}

	public MacroManager getMacroManager() {
		return macroManager;
	}

	public void setMacroManager(MacroManager macroManager) {
		this.macroManager = macroManager;
	}

	public boolean isInitialized() {
		return initialized;
	}

	public void setInitialized(boolean initialized) {
		this.initialized = initialized;
	}
}