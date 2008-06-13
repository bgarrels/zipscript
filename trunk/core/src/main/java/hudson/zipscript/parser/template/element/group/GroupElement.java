package hudson.zipscript.parser.template.element.group;

import hudson.zipscript.parser.context.ZSContext;
import hudson.zipscript.parser.exception.ExecutionException;
import hudson.zipscript.parser.template.element.Element;
import hudson.zipscript.parser.template.element.NestableElement;

import java.io.StringWriter;

public class GroupElement extends NestableElement {

	protected boolean isStartElement(hudson.zipscript.parser.template.element.Element e) {
		return (e instanceof GroupElement);
	}

	protected boolean isEndElement(Element e) {
		return (e instanceof EndGroupElement);
	}

	public void merge(ZSContext context, StringWriter sw) {
		sw.write('(');
	}

	public boolean booleanValue(ZSContext context) throws ExecutionException {
		if (getChildren().size() == 1)
			return ((Element) getChildren().get(0)).booleanValue(context);
		else
			throw new ExecutionException("groups can not be evaluated as booleans", this);
	}

	public Object objectValue(ZSContext context) throws ExecutionException {
		if (getChildren().size() == 1)
			return ((Element) getChildren().get(0)).objectValue(context);
		else
			throw new ExecutionException("groups can not be evaluated as objects", this);
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("(");
		if (null != getChildren()) {
			for (int i=0; i<getChildren().size(); i++) {
				if (i > 0) sb.append(' ');
				sb.append(getChildren().get(i));
			}
		}
		sb.append(")");
		return sb.toString();
	}
}
