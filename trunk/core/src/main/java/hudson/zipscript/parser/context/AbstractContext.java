package hudson.zipscript.parser.context;

import hudson.zipscript.ResourceContainer;
import hudson.zipscript.parser.template.data.ParsingSession;

public abstract class AbstractContext implements ExtendedContext {

	private ParsingSession parsingSession;
	private ResourceContainer resourceContainer;
	private boolean initialized;

	public ParsingSession getParsingSession() {
		return parsingSession;
	}

	public void setParsingSession(ParsingSession parsingSession) {
		this.parsingSession = parsingSession;
	}

	public ResourceContainer getResourceContainer() {
		return resourceContainer;
	}

	public void setResourceContainer(ResourceContainer resourceContainer) {
		this.resourceContainer = resourceContainer;
	}

	public boolean isInitialized() {
		return initialized;
	}

	public void setInitialized(boolean initialized) {
		this.initialized = initialized;
	}
}