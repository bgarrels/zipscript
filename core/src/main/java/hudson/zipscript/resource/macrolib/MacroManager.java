package hudson.zipscript.resource.macrolib;

import hudson.zipscript.parser.ExpressionParser;
import hudson.zipscript.parser.exception.ParseException;
import hudson.zipscript.parser.template.data.ParsingResult;
import hudson.zipscript.parser.template.data.ResourceContainer;
import hudson.zipscript.parser.template.element.Element;
import hudson.zipscript.parser.template.element.directive.macrodir.MacroDirective;
import hudson.zipscript.parser.template.element.lang.TextDefaultElementFactory;
import hudson.zipscript.parser.template.element.lang.variable.adapter.VariableAdapterFactory;
import hudson.zipscript.parser.util.IOUtil;
import hudson.zipscript.resource.Resource;
import hudson.zipscript.resource.ResourceLoader;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class MacroManager {

	private Map macroLibraries = new HashMap();
	private ResourceContainer resourceContainer;

	public void addMacroLibrary (
			String namespace, String resourcePath, ResourceLoader resourceLoader,
			VariableAdapterFactory variableAdapterFactory)
	throws ParseException {
		if (null == macroLibraries) macroLibraries = new HashMap();
		Resource resource = resourceLoader.getResource(resourcePath);
		String contents = IOUtil.toString(resource.getInputStream());
		ParsingResult pr = ExpressionParser.getInstance().parse(
				contents, resourceContainer.getComponents(),
				TextDefaultElementFactory.INSTANCE, 0, resourceContainer);
		List l = pr.getElements();
		MacroLibrary macroLibrary = new MacroLibrary(namespace);
		for (Iterator i=l.iterator(); i.hasNext(); ) {
			Element e = (Element) i.next();
			if (e instanceof MacroDirective) {
				macroLibrary.addMacroDefinition((MacroDirective) e); 
			}
		}
		if (macroLibrary.getMacroNames().size() > 0) {
			macroLibraries.put(namespace, macroLibrary);
		}
	}

	public MacroDirective getMacro (String name, String namespace, MacroProvider defaultMacroProvider) {
		if (null == namespace) {
			if (null != defaultMacroProvider) return defaultMacroProvider.getMacro(name);
			else return null;
		}
		else {
			MacroLibrary lib = (MacroLibrary) macroLibraries.get(namespace);
			if (null != lib) return lib.getMacro(name);
			else return null;
		}
	}

	public ResourceContainer getResourceContainer() {
		return resourceContainer;
	}

	public void setResourceContainer(ResourceContainer resourceContainer) {
		this.resourceContainer = resourceContainer;
	}
}