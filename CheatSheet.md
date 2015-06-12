# Things You Should Know #
  * No dependencies are required so all you have to do is add zipscript-core.jar to your classpath.
  * The default resource loading behavior is shown below can be modified: [click here for details](http://www.zipscript.org/layout/frameset.html?http://www.zipscript.org/docs/current/bk02ch02.html)
  * The [ZipEngine](http://www.zipscript.org/layout/frameset.html?http://www.zipscript.org/docs/current/javadoc/hudson/zipscript/ZipEngine.html) is used to retrieve templates or evaluators

If you're looking for syntax examples: SyntaxExamples

# Zipscript Usages #

## Evaluating config entries as booleans ##
_the default boolean evaluation resource loader assumes the expression is passed to the getEvaluator method_
```
String expression = "foo != null && bar > 10"; // get the expression to evaluate
Map context = new HashMap();
// add business data to the context
try {
    Evaluator e = ZipEngine.createInstance().getEvaluator(expression);
    boolean val = e.booleanValue(context);
}
catch (ParseException e) {
// the expression was invalid
}
```

## Retrieving runtime objects using config entries ##
_the default object evaluation resource loader assumes the expression is passed to the getEvaluator method_
```
String expression = "{foo, bar, baz}"; // get the expression to evaluate - example represents a list
Map context = new HashMap();
// add business data to the context
try {
    Evaluator e = ZipEngine.createInstance().getEvaluator(expression);
    Object obj = e.objectValue(context);
    // in this example obj will be a java.util.List
}
catch (ParseException e) {
// the expression was invalid
}
```

## Merging templates with runtime data ##
_the default template resource loader will retrieve the template resource from the classpath_
```
String templateName = ... 
Map context = new HashMap();
// add business data to the context
try {
    Template t = ZipEngine.createInstance().getTemplate(templateName);
    // there are other merging options as well
    String result = t.merge(context);
}
catch (ParseException e) {
// the expression was invalid
}
```

## Now What? ##
There are many other features and functionality not listed here.  To learn more, why not check out the UserGuide and DevelopersGuide.