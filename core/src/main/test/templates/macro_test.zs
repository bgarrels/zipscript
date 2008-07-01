[#macro foo abc="def" baz bar]
	abc=${abc}
	baz=$!{baz}
	bar=${bar}
- ${body}
[#set abc="jibjab"/]
- ${body}
[/#macro]

[#macro test doit foo[]]
[#if test1]
	[#foreach foo in foo]
		${foo.body}
	[/#foreach]
[/#if]
[/#macro]

[#macro sectionHeader title]
	The section header title is ${title}
	--
	${body}
	--
[/#macro]

[@test doit=true]
	[@foo]def[/@foo]
	[@foo]ghi[/@foo]
[/@test]

[@foo abc="zyx" baz="jjj" bar="kkk"]
	flip flop ${abc}
[/@foo]