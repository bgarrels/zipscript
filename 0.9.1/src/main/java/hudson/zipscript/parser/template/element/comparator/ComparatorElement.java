package hudson.zipscript.parser.template.element.comparator;

import hudson.zipscript.parser.template.element.Element;

public interface ComparatorElement extends Element {

	public int getPriority();

	public Element getLeftHandSide ();

	public void setLeftHandSide (Element e);

	public Element getRightHandSide ();

	public void setRightHandSide (Element e);
}
