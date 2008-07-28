[#initialize]
	[#set title="Joe Rocks!"/]
[/#initialize]
[#import "tab.zsm" as tab/]

[@tab.pane]
	[%page label="Page 1"]
		Page 1 contents
	[/%page]
	[%page label="Page 2"]
		Page 2 contents
	[/%page]
[/@tab.pane]

This is a test
<br/>
$!{message}
<br/>
${foo}
</br>

[#while i<5]
	${i}
[/#while]

$Resource("key.text")