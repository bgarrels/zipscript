package test.hudson.zipscript;

public class TestSuite extends junit.framework.TestSuite {

	public static junit.framework.TestSuite suite () {
		TestSuite suite = new TestSuite();
		suite.addTestSuite(BooleanTestCase.class);
		suite.addTestSuite(DirectiveTestCase.class);
		suite.addTestSuite(LogicTestCase.class);
		suite.addTestSuite(MathTestCase.class);
		suite.addTestSuite(VariableTestCase.class);
		suite.addTestSuite(VariableDefaultsTestCase.class);
		suite.addTestSuite(ListTestCase.class);
		suite.addTestSuite(PerformanceTestCase.class);
		suite.addTestSuite(VariableFormattingTestCase.class);
		suite.addTestSuite(SpecialMethodsTestCase.class);
		return suite;
	}
}
