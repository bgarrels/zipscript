package hudson.zipscript.resource.macrolib;

import hudson.zipscript.parser.template.element.directive.macrodir.MacroDirective;

public interface MacroProvider {

	public MacroDirective getMacro (String name);

	public String getMacroImportPath (String namespace);
}
