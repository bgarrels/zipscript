package hudson.zipscript.parser.template.element.lang.variable.adapter;

import hudson.zipscript.parser.context.ExtendedContext;
import hudson.zipscript.parser.template.data.ParsingSession;
import hudson.zipscript.parser.template.element.Element;
import hudson.zipscript.parser.template.element.lang.variable.special.SpecialMethod;

import java.util.ArrayList;
import java.util.List;

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

	public String getDefaultGetterMethod (Object obj) {
		String getterMethod = null;
		for (int i=0; i<factories.length; i++) {
			getterMethod = factories[i].getDefaultGetterMethod(obj);
			if (null != getterMethod) return getterMethod;
		}
		return null;
	}
	private String[] reservedContextAttributes;
	public synchronized String[] getReservedContextAttributes() {
		if (null == reservedContextAttributes) {
			List l = new ArrayList();
			for (int i=0; i<factories.length; i++) {
				String[] sArr = factories[i].getReservedContextAttributes();
				if (null != sArr && sArr.length > 0)
					for (int j=0; j<sArr.length; j++)
						l.add(sArr[j]);
			}
			reservedContextAttributes = (String[]) l.toArray(new String[l.size()]);
		}
		return reservedContextAttributes;
	}
}