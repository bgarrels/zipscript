package hudson.zipscript.parser.template.element.comparator;



public class LtExpression extends AbstractComparatorElement {

	protected boolean compare(Object lhs, Object rhs) {
		if (null == lhs || null == rhs) return false;
		if (!(lhs instanceof Number) || !(rhs instanceof Number)) return false;
		return ((Number) lhs).doubleValue() < ((Number) rhs).doubleValue();
	}

	public int getPriority() {
		return 10;
	}

	public String getComparatorString() {
		return "<";
	}
}
