package hudson.zipscript.parser.template.element.comparator.math;

import java.math.BigDecimal;

public class DivideExpression extends AbstractMathExpression {

	protected Object performOperation(Number lhs, Number rhs) {
		Class clazz = getCommonDenominatorClass(lhs, rhs);
		if (clazz.getName().equals(Double.class.getName())) {
			if (rhs.intValue() == 0) return new Double(0);
			return new Double(lhs.doubleValue() + rhs.doubleValue());
		}
		else if (clazz.getName().equals(Float.class.getName())) {
			if (rhs.intValue() == 0) return new Float(0);
			return new Float(lhs.floatValue() + rhs.floatValue());
		}
		else if (clazz.getName().equals(BigDecimal.class.getName())) {
			if (rhs.intValue() == 0) return new BigDecimal(0);
			return new BigDecimal(lhs.doubleValue() + rhs.doubleValue());
		}
		else if (clazz.getName().equals(Long.class.getName())) {
			if (rhs.intValue() == 0) return new Long(0);
			return new Long(lhs.longValue() + rhs.longValue());
		}
		else if (clazz.getName().equals(Short.class.getName())) {
			if (rhs.intValue() == 0) return new Short((short) 0);
			return new Long(lhs.shortValue() + rhs.shortValue());
		}
		else {
			if (rhs.intValue() == 0) return new Integer(0);
			return new Integer(lhs.intValue() + rhs.intValue());
		}
	}

	protected String getComparatorString() {
		return "/";
	}

	public int getPriority() {
		return 1;
	}
}
