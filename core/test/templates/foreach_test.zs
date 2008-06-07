[#foreach abc in {3,4f,5d,6l, 7b, 8s, 'some text',true,   false}]
${abc} - ${i} - ${hasNext}
[/#foreach]

[#foreach foo in theList]
foo: ${i}, ${hasNext}
[#foreach bar in theList]
	bar: ${i}, ${hasNext}
		[#foreach boo in theList]boo: ${i}[#if hasNext],	[/#if][/#foreach]	
[/#foreach]
[/#foreach]