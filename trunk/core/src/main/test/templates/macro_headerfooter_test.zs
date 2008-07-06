[#macro someMacro bbb]
	${body) - ${bbb!"No bbb!!!"}
[/#macro]
[#macro test *abc *foo[*bar]]
	The header is '${header}'
	abc is '${abc}'
	The body is '${body!"Undefined"}'
	foo.bar is '${foo.bar}'
	foo header is '${foo.header!"Undefined"}'
	foo body is '${foo.body!"Undefined"}'
	foo footer is '${foo.footer!"Undefined"}'
	The footer is '${footer!"Undefined"}'
[/#macro]

[@test abc="ABC"]
[[
		This is test header
]]
	This is test content
	[@someMacro bbb=abc]flip flop[/@someMacro]
	[@foo bar="baz"]
		[[This is foo header]]
		This is foo body
		[[This is foo footer]]
	[/@foo]
[[
		This is test footer
]]
[/@test]