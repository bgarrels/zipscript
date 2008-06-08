[#macro foo abc="def" baz bar]
	this is a test
	abc=${abc}
	baz=$!{baz}
	bar=${bar}
- ${nested}
- ${nested}
[/#macro]

[@foo abc="zyx" baz="jjj" bar="kkk"]
	flip flop
[/@foo]