[#macro foo abc="def" baz bar]
	this is a test
	abc=${abc}
	baz=$!{baz}
	bar=${bar}
- ${nested}
[#set abc="jibjab"/]
- ${nested}
[/#macro]
[#macro tab id title page]
--- Tab ---
	Id: ${id}
	Title: ${title}
[#foreach page in page]
	--- Page ---
	Id: ${page.id}
	Title: ${page.title}
	Selected: ${page.selected}
[/#foreach]
[/#macro]
[#macro somePage id title selected]
	--- Page ---
		Id: ${page.id}
		Title: ${page.title}
		Selected: ${page.selected}
[/#macro]

[@foo abc="zyx" baz="jjj" bar="kkk"]
	flip flop ${abc}
[/@foo]

[@foo abc="zyx" baz="jjj" bar="kkk"]
	joe test
[/@foo]


[@tab id="myId" title="Tab Title"]
	[@page id="page1" title="Page 1" selected=true]
		This is the page 1 contents
	[/@page]
	[@page id="page2" title="Page 2" selected=false]
		This is the page 2 contents
	[/@page]
[/@tab]