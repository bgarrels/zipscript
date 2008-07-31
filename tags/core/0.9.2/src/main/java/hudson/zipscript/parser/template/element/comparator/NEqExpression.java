package hudson.zipscript.parser.template.element.comparator;

import hudson.zipscript.parser.template.element.special.NullElement;

public class NEqExpression extends AbstractComparatorElement {

	protected boolean compare(Object lhs, Object rhs) {
		if (null == lhs || null == rhs) {
			if (null != rhs && getLeftHandSide() instanceof NullElement) {
				return true;
			}
			else if (null != lhs && getRightHandSide() instanceof NullElement) {
				return true;
			}
			else return false;
		}
		else return !lhs.equals(rhs);
	}

	public int getPriority() {
		return 10;
	}

	public String getComparatorString() {
		return "!=";
	}
}
