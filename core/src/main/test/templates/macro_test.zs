[#macro test doIt foo]
[#if test1]
	[#foreach foo in foo]
		${foo.nested}
	[/#foreach]
[/#if]
[/#macro]

[#macro sectionHeader title]
	The section header title is ${title}
	--
	${nested}
	--
[/#macro]

[#macro foo abc="def" baz bar]
	abc=${abc}
	baz=$!{baz}
	bar=${bar}
- ${nested}
[#set abc="jibjab"/]
- ${nested}
[/#macro]

[#macro tab id title page callMacro]
--- Tab ---
	Id: ${id}
	Title: ${title}
[#if callMacro]
	[#foreach page in page]
		[#call commonPageMacro with page/]	
	[/#foreach]
[#else]
[#foreach page in page]
	--- Page ---
		Id: ${page.id}
		Title: ${page.title}
		Selected: ${page.selected}
[/#foreach]
[/#if]
[/#macro]

[#macro commonPageMacro id title selected]
	--- Common Page ---
		Id: ${id}
		Title: ${title}
		Selected: ${selected}
[/#macro]

[@test doit=true]
	[@foo]def[/@foo]
	[@foo]ghi[/@foo]
[/@test]

[@foo abc="zyx" baz="jjj" bar="kkk"]
	flip flop ${abc}
[/@foo]


[@tab id="myId" title="Tab Title 1"]
	[@page id="page1" title="Page 1" selected=true]
		This is the page 1 contents
	[/@page]
	[@page id="page2" title="Page 2" selected=false]
		[@sectionHeader title=title]
			section body
		[/@sectionHeader]
		This is the page 2 contents
	[/@page]
[/@tab]

[@tab id="myId" title="Tab Title 2" callMacro=true]
	[@page id="page1" title="Page 1" selected=true]
		This is the page 1 contents
	[/@page]
	[@page id="page2" title="Page 2" selected=false]
		[@sectionHeader title=title]
			section body
		[/@sectionHeader]
		This is the page 2 contents
	[/@page]
[/@tab]