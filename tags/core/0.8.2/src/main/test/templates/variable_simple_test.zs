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
