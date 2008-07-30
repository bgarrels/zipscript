${firstName} ${lastName} ${car}

${company.name}

[#foreach employee in company.employee]
	${employee.firstName} ${employee.lastName}
[/#foreach]

[#foreach child in children]
	${child.firstName}
[/#foreach]