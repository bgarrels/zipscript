package test.hudson.zipscript;

public class TestSuite extends junit.framework.TestSuite {

	public static junit.framework.TestSuite suite () {
		TestSuite suite = new TestSuite();
		suite.addTestSuite(BooleanTestCase.class);
		suite.addTestSuite(DirectiveTestCase.class);
		suite.addTestSuite(LogicTestCase.class);
		suite.addTestSuite(MathTestCase.class);
		suite.addTestSuite(VariableDefaultsTestCase.class);
		return suite;
	}
}
