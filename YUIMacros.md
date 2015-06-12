# Accordion View #
```
[@accordion:view id="mymenu5" collapsible=false width="250px"]
	[%panel title="Item 1"]
		This is the content of panel 1
	[/%panel]
	[%panel title="Item 2"]
		This is the content of panel 2
	[/%panel]
	[%panel title="Item 3" selected=true]
		This is the content of panel 3
	[/%panel]
	[%panel title="Item 4"]
		This is the content of panel 4
	[/%panel]
	[%panel title="Item 5"]
		This is the content of panel 5
	[/%panel]
[/@accordion:view]
```
  * [View output](http://www.zipscript.org/layout/frameset.html?http://www.zipscript.org/examples/accordion_result.html)
  * [View referenced zipscript macro](http://code.google.com/p/zipscript/source/browse/trunk/macrolibs/YUI/src/main/macros/accordion.zsm)
  * [View unit test producing these results](http://code.google.com/p/zipscript/source/browse/trunk/macrolibs/YUI/src/main/test/WidgetTestCase.java#57)

# Data Table #
```
[@data:table id="myTable" entries=people]
	[%column title="First Name" width=150]${entry.firstName}[/%column]
	[%column title="Last Name" width=150]${entry.lastName}[/%column]
	[%column title="Birthday" format="date"]${entry.birthday?jsDate}[/%column]
	[%column title="# Accounts" format="number"]${entry.numAccounts}[/%column]
	[%column title="Net Worth" format="currency"]${entry.netWorth}[/%column]
	[%column title="Some Hidden Column" hidden=true]It doesn't matter what is here[/%column]
[/@data:table]
```
  * [View output](http://www.zipscript.org/layout/frameset.html?http://www.zipscript.org/examples/datatable_result.html)
  * [View referenced zipscript macro](http://code.google.com/p/zipscript/source/browse/trunk/macrolibs/YUI/src/main/macros/data.zsm)
  * [View unit test producing these results](http://code.google.com/p/zipscript/source/browse/trunk/macrolibs/YUI/src/main/test/WidgetTestCase.java#23)

# Tab Pane #
```
[@tab:pane id="myTab"]
	[%page label="Some Page" active=true]
		These are the contents of some page...
		<p>
			Blah blah blah...
		</p> 
	[/%page]
	[#while i<2]
		[%page label="Page ${i+1}"]
			This is the contents of page ${i+1}
		[/%page]
	[/#while]
	[#foreach item in {"A","B","C"}]
		[%page label='Page "${item}"']
			This is the contents of page "${item}"
		[/%page]
	[/#foreach]
[/@tab:pane]
```
  * [View output](http://www.zipscript.org/layout/frameset.html?http://www.zipscript.org/examples/tab_result.html)
  * [View referenced zipscript macro](http://code.google.com/p/zipscript/source/browse/trunk/macrolibs/YUI/src/main/macros/tab.zsm)
  * [View unit test producing these results](http://code.google.com/p/zipscript/source/browse/trunk/macrolibs/YUI/src/main/test/WidgetTestCase.java#36)

# Tree View #
_Notice the recursive functionality_
```
[@tree:view id="myTree"]
	[%node id="n1" label="Label 1" href="http://www.google.com"]
		[%node id="n1_1" label="Label 1.1"/]
		[%node id="n1_2" label="Label 1.2" tooltip="This is a tooltip"/]
		[%node id="n1_3" label="Label 1.3" tooltip="This is another tooltip"]
			[%node id="n1_3_1" label="Label 1.3.1"/]
		[/%node]
	[/%node]
	[%node id="n2" label="Label 2"]
		[%node id="n2_1" label="Label 2.1" href="http://www.google.com"/]
	[/%node]
	[%node id="n3" label="Label 3" href="http://www.google.com"/]
[/@tree:view]
```
  * [View output](http://www.zipscript.org/layout/frameset.html?http://www.zipscript.org/examples/tree_result.html)
  * [View referenced zipscript macro](http://code.google.com/p/zipscript/source/browse/trunk/macrolibs/YUI/src/main/macros/tree.zsm)
  * [View unit test producing these results](http://code.google.com/p/zipscript/source/browse/trunk/macrolibs/YUI/src/main/test/WidgetTestCase.java#43)