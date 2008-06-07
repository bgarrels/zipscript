[#foreach foo in theList]
foo: ${i}, ${hasNext}
[#foreach bar in theList]
	bar: ${i}, ${hasNext}
		[#foreach boo in theList]boo: ${i}[#if hasNext],	[/#if][/#foreach]	
[/#foreach]
[/#foreach]