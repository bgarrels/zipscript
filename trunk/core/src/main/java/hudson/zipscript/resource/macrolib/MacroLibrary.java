package hudson.zipscript.resource.macrolib;

import hudson.zipscript.parser.template.element.directive.macrodir.MacroDirective;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class MacroLibrary implements MacroProvider {

	private String namespace;
	private Map macroDefinitions = new HashMap();

	public MacroLibrary (String namespace) {
		this.namespace = namespace;
	}

	public Set getMacroNames () {
		return macroDefinitions.keySet();
	}

	public void addMacroDefinition (MacroDirective macro) {
		macro.setMacroLibrary(this);
		macroDefinitions.put(macro.getName(), macro);
	}
	
	public MacroDirective getMacro  (String name) {
		return (MacroDirective) macroDefinitions.get(name);
	}

	public String getNamespace() {
		return namespace;
	}
}