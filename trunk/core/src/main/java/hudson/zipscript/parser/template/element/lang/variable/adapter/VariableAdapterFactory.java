package hudson.zipscript.parser.template.element.lang.variable.adapter;

import hudson.zipscript.parser.template.data.ParsingSession;
import hudson.zipscript.parser.template.element.Element;
import hudson.zipscript.parser.template.element.lang.variable.special.SpecialMethod;

public interface VariableAdapterFactory {

	public MapAdapter getMapAdapter (Object map);

	public SequenceAdapter getSequenceAdapter (Object sequence);

	public ObjectAdapter getObjectAdapter (Object object);

	public SpecialMethod getSpecialMethod (
			String name, Element[] parameters, ParsingSession session, Element element);

	public SpecialMethod getStringEscapingStringMethod (
			String method, ParsingSession session);

	public String getDefaultGetterMethod(Object obj);

	public String[] getReservedContextAttributes();
}
