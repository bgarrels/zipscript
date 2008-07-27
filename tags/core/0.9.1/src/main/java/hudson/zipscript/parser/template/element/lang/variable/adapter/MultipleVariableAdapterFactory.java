package hudson.zipscript.parser.template.element.lang.variable.adapter;

import hudson.zipscript.parser.context.ExtendedContext;
import hudson.zipscript.parser.template.data.ParsingSession;
import hudson.zipscript.parser.template.element.Element;
import hudson.zipscript.parser.template.element.lang.variable.special.SpecialMethod;

public class MultipleVariableAdapterFactory implements VariableAdapterFactory {

	private VariableAdapterFactory[] factories;

	public MultipleVariableAdapterFactory (VariableAdapterFactory[] factories) {
		this.factories = factories;
	}

	public MapAdapter getMapAdapter(Object map) {
		MapAdapter mapAdapter = null;
		for (int i=0; i<factories.length; i++) {
			mapAdapter = factories[i].getMapAdapter(map);
			if (null != mapAdapter) return mapAdapter;
		}
		return null;
	}

	public ObjectAdapter getObjectAdapter(Object object) {
		ObjectAdapter objectAdapter = null;
		for (int i=0; i<factories.length; i++) {
			objectAdapter = factories[i].getObjectAdapter(object);
			if (null != objectAdapter) return objectAdapter;
		}
		return null;
	}

	public SequenceAdapter getSequenceAdapter(Object sequence) {
		SequenceAdapter sequenceAdapter = null;
		for (int i=0; i<factories.length; i++) {
			sequenceAdapter = factories[i].getSequenceAdapter(sequence);
			if (null != sequenceAdapter) return sequenceAdapter;
		}
		return null;
	}

	public SpecialMethod getSpecialMethod(String name, Element[] parameters,
			Object object, ExtendedContext context, Element element) {
		SpecialMethod specialMethod = null;
		for (int i=0; i<factories.length; i++) {
			specialMethod = factories[i].getSpecialMethod(
					name, parameters, object, context, element);
			if (null != specialMethod) return specialMethod;
		}
		return null;
	}

	public SpecialMethod getStringEscapingStringMethod(String method,
			ParsingSession session) {
		SpecialMethod specialMethod = null;
		for (int i=0; i<factories.length; i++) {
			specialMethod = factories[i].getStringEscapingStringMethod(
					method, session);
			if (null != specialMethod) return specialMethod;
		}
		return null;
	}
}