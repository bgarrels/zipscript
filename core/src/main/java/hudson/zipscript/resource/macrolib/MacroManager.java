package hudson.zipscript.resource.macrolib;

import hudson.zipscript.ZipEngine;
import hudson.zipscript.parser.ExpressionParser;
import hudson.zipscript.parser.exception.ParseException;
import hudson.zipscript.parser.template.data.ParsingResult;
import hudson.zipscript.parser.template.element.Element;
import hudson.zipscript.parser.template.element.directive.macrodir.MacroDirective;
import hudson.zipscript.parser.template.element.lang.TextDefaultElementFactory;
import hudson.zipscript.parser.util.IOUtil;
import hudson.zipscript.resource.Resource;
import hudson.zipscript.resource.ResourceLoader;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class MacroManager {

	public Map macroLibraries = new HashMap();

	public void addMacroLibrary (
			String namespace, String resourcePath, ResourceLoader resourceLoader)
	throws ParseException {
		if (null == macroLibraries) macroLibraries = new HashMap();
		Resource resource = resourceLoader.getResource(resourcePath);
		String contents = IOUtil.toString(resource.getInputStream());
		ParsingResult pr = ExpressionParser.getInstance().parse(
				contents, ZipEngine.TEMPLATE_COMPONENTS,
				TextDefaultElementFactory.INSTANCE, 0, this);
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
}