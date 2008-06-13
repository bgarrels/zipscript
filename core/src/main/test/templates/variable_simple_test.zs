${myObject.testMap['anotherObj'].list1[0].getText().length()}
${myObject.testMap['anotherObj'].list1[0].getText()}
[#foreach obj in myObject.testMap['anotherObj'].list1]
	${obj.text}
[/#foreach]
->$!{myObjectNoExist}<-

${myString} is ${myString.length} characters long

