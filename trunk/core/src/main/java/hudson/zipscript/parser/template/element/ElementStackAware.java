package hudson.zipscript.parser.template.element;

import java.util.Stack;

/**
 * Elements must implement this if they need to evaluate their position in the document structure
 * 
 * @author Joe Hudson
 */
public interface ElementStackAware {

	/**
	 * Set the nested element stack retrieved from expression resource parsing
	 * @param elementStack the element stack (entries of type hudson.zipscript.parser.template.element.Element)
	 */
	public void setElementStack (Stack elementStack);
}
