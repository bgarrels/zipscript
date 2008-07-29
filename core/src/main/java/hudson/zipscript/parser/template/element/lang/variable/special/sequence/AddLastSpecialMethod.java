package hudson.zipscript.parser.template.element.lang.variable.special.sequence;

import hudson.zipscript.parser.context.ExtendedContext;
import hudson.zipscript.parser.template.element.Element;
import hudson.zipscript.parser.template.element.lang.variable.adapter.RetrievalContext;
import hudson.zipscript.parser.template.element.lang.variable.adapter.SequenceAdapter;

public class AddLastSpecialMethod extends SequenceSpecialMethod {

	private Element element;

	public AddLastSpecialMethod (Element[] vars) {
		this.element = vars[0];
	}

	public Object execute(
			Object source, SequenceAdapter sequenceAdapter, RetrievalContext retrievalContext, ExtendedContext context) {
		int length = sequenceAdapter.getLength(source);
		Object value = element.objectValue(context);
		sequenceAdapter.addItemAt(length, value, source);
		return null;
	}
}
