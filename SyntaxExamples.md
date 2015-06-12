This is more of a quick reference.  If you would like to see syntax examples and resulting output with unit tests: [click here](SyntaxExamplesWithOutput.md)

```
[## This is a comment ##]
\[## This is not a comment because it is escaped ##]
```

# A Few Variable Examples #
```
   ${person.firstName}  ---------------------  standard variable reference
   ${person.age + 10}  ----------------------  math can be performed inside 
   ${child.fullName!"No Children"}  ---------  variable defaults using '!'
   ${person.birthDate|short}  ---------------  variable formatting using '|'
   ${person.birthDate|"yyyy MM dd"}  --------  custom formatting can be used as well
   ${person.firstName?upperCase}  -----------  variable special methods using '?'

informal variable references
    $person.firstName
    $person.birthDate|short
    $person.firstName?upperCase
```
  * [See all variable special methods](http://www.zipscript.org/layout/frameset.html?http://www.zipscript.org/docs/core/bk01ch02s06.html)
  * [See all variable formatting options](http://www.zipscript.org/layout/frameset.html?http://www.zipscript.org/docs/core/bk01ch02s05.html)

# A Few Directive Examples #
```
[#import "macros/myMacroLib.zsw as mm/] <-- import the macro library "macros/myMacroLib.zsw" to be referenced with the "mm" namespace

[#include "includes/someCommonTemplate.zs"/] <-- include an external zipscript template

[##----- if / elseif / else directives -----##]
[#if foo > bar && abc != "def"]
   ...
[#elseif someClassName?split('.')?length > 3]
   ...
[#else]
   ...
[/#if]


[##------ while directive -----##]
[#while i<5]
    this will print 5 times ${i} is the 0-based index
[/#while]


[##----- foreach directive -----##]
[#foreach entry in {foo, bar, baz}]
   this will loop through the list containing foo, bar and baz
   ${i} is the index; ${hasNext} shows if there are more elements in the list
   [#while someCondition]
       ${super.i} is the index of the parent foreach list
       ${i} is the index of the while list
   [/#while]
[/#foreach]


[##----- escape/noescape directive -----##]
[#escape html]
    ${foo}  <-- This will automatically be HTML-escaped
    [#noescape]
        ${bar}  <-- This will not automatically be escaped
    [/#noescape]
[/#escape]


[##----- macros -----##]
[##-- macro reference from imported library --##]
[@mm:someFlatMacro abc=def ghi="jkl" mno=3/]

[##-- macro reference from inline definition (see below) --##]
[@myInlineMacro myRequiredParam="something" myDefaultedParam=47]

    [## below is a template-defined parameter for "myInlineMacro" ##]
    [%myTemplateDefinedParam tdp1="some required parameter"]
        template defined parameter body content
    [/%myTemplateDefinedParameter]

   This is the macro body content

[/@myInlineMacro]

[##-- macro definition --##]
[#macro myIinlineMacro | myTemplateDefinedParam[tdp1 tdp2=true] myRequiredParam myOptionalParam=null myDefaultedParam=6]

    [## access macro parameters ##]
    ${myRequiredParam}
    ${myOptionalParam!"Null Optional Param"}
    ${myDefaultedParam}

    [## access template-defined parameters ##]
    [#foreach param in myTemplateDefinedParam]
        ${param.tdp1} ${param.tdp2} ${param.body}
    [/#foreach]

    [## access macro body content ##]
    ${body}
[/#macro]
```

# Macro Libraries #
See Yahoo! UI macros [YUIMacros](YUIMacros.md)