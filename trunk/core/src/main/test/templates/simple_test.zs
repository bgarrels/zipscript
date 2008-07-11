[#macro macroName *requiredParam1 *requiredParam2 optionalParam1 optionalParamWithDefault=true *templateDefinedParameter[*tdpRequiredParam tdpOptionalParam]]
	Required parameters will always exist ${requiredParam1} - ${requiredParam2}
	Optional parameters with no defaults should be referenced carefully ${optionalParam1!"some default"} or $!{optionalParam1}
	Optional parameters with defaults will always exist ${optionalParamWithDefault}

	Template-defined parameters can be accessed in a sequence
	[#foreach entry in templateDefinedParameter]
		Parameters of template defined parameters can be accessed using '.': ${entry.tdpRequiredParam} - ${entry.tdpOptionalParam!"foo"}
		Template defined parameter body content can be accessed using  'body': ${entry.body}
	[/#foreach]

	The template-defined parameter can only be accessed directly without looping if there is one
	Otherwise, use the sequence index syntax
	${templateDefinedParameter[0].tdpRequiredParam} - ${templateDefinedParameter[0].tdpOptionalParam!"bar"}

	Macro body content can be accessed using ${body}
[/#macro]

[@macroName requiredParam1="abc" requiredParam2="def"]
	MAIN MACRO BODY
	
	[@templateDefinedParameter tdpRequiredParam="TDP 1"] TDP 1 BODY [/@templateDefinedParameter]
	[@templateDefinedParameter tdpRequiredParam="TDP 2"] TDP 2 BODY [/@templateDefinedParameter]
[/@macroName]
