package hudson.zipscript.parser.template.element.lang.variable.adapter;

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

}