[#while i < 20]
	i is ${i}
[/#while]

[#while i < 20]
	i is ${i}
[#if i > 10][#break/][/#if]
[/#while]