package hudson.zipscript.parser.template.element.comparator;



public class LEqExpression extends AbstractComparatorElement {

	protected boolean compare(Object lhs, Object rhs) {
		if (null == lhs || null == rhs) return false;
		if (lhs.equals(rhs)) return true;
		if (!(lhs instanceof Number) || !(rhs instanceof Number)) return false;
		return ((Number) lhs).doubleValue() < ((Number) rhs).doubleValue();
	}

	public int getPriority() {
		return 10;
	}

	public String getComparatorString() {
		return "<=";
	}
}
