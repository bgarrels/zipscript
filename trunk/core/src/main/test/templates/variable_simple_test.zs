${myObject.testMap['anotherObj'].list1[0].getText().length()}
${myObject.testMap['anotherObj'].list1[0].getText()}
${myObject.testMap['anotherObj'].list1[0].Text}
${myObject.testMap['anotherObj'].list1[0].text}
[#foreach obj in myObject.testMap['anotherObj'].list1]
	${obj.text}
[/#foreach]
->$!{myObjectNoExist}<-

${myString} is ${myString.length} characters long

${obj.${dynamicPath}} <-- we can access object paths dynamically


== Lazy Variables ==

$myObject.testMap['anotherObj'].list1[0].getText().length()
$myObject.testMap['anotherObj'].list1[0].getText()
$myObject.testMap['anotherObj'].list1[0].Text
$myObject.testMap['anotherObj'].list1[0].text
-> $!myObjectNoExist <-
->$!myObjectNoExist<-

$myString is $myString.length characters long

== Resources ==
$Resource("1 This is a test")
$Resource("2 This is a test", {"param 1", "param 2"})
${Resource("3 This is a test")}
${Resource("4 This is a test", {"param 1", "param 2"})}