package hudson.zipscript.parser.template.element.lang.variable.adapter;

public interface VariableAdapterFactory {

	public MapAdapter getMapAdapter (Object map);

	public SequenceAdapter getSequenceAdapter (Object sequence);

	public ObjectAdapter getObjectAdapter (Object object);
}
