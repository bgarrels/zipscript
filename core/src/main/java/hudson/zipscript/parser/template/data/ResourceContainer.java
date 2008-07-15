package hudson.zipscript.parser.template.data;

import hudson.zipscript.parser.template.element.component.Component;
import hudson.zipscript.parser.template.element.lang.variable.adapter.VariableAdapterFactory;
import hudson.zipscript.resource.macrolib.MacroManager;

import java.util.Map;

public class ResourceContainer {

	private MacroManager macroManager;
	private Component[] components;
	private VariableAdapterFactory variableAdapterFactory;
	private Map initParameters;

	public ResourceContainer (
			MacroManager macroManager, VariableAdapterFactory variableAdapterFactory,
			Component[] components, Map initParameters) {
		this.macroManager = macroManager;
		this.variableAdapterFactory = variableAdapterFactory;
		this.components = components;
		this.initParameters = initParameters;
		macroManager.setResourceContainer(this);
	}

	public MacroManager getMacroManager() {
		return macroManager;
	}
	public Component[] getComponents() {
		return components;
	}
	public VariableAdapterFactory getVariableAdapterFactory() {
		return variableAdapterFactory;
	}
	public Map getInitParameters() {
		return initParameters;
	}
	public void setMacroManager(MacroManager macroManager) {
		this.macroManager = macroManager;
	}
	public void setComponents(Component[] components) {
		this.components = components;
	}
	public void setVariableAdapterFactory(
			VariableAdapterFactory variableAdapterFactory) {
		this.variableAdapterFactory = variableAdapterFactory;
	}
	public void setInitParameters(Map initParameters) {
		this.initParameters = initParameters;
	}
}