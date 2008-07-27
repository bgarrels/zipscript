package hudson.zipscript.parser.context;

import hudson.zipscript.ResourceContainer;
import hudson.zipscript.parser.template.data.ParsingSession;
import hudson.zipscript.parser.template.element.directive.macrodir.MacroDirective;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractContext implements ExtendedContext {

	private ParsingSession parsingSession;
	private ResourceContainer resourceContainer;
	private Map importDefinitions;
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

	public MacroDirective getMacro (String name) {
		return getParsingSession().getMacro(name);
	}

	public String getMacroImportPath (String namespace) {
		if (null == importDefinitions) return null;
		else return (String) importDefinitions.get(namespace);
	}

	public void addMacroImport(String namespace, String macroPath) {
		if (null == importDefinitions)
			importDefinitions = new HashMap();
		importDefinitions.put(namespace, macroPath);
	}
}