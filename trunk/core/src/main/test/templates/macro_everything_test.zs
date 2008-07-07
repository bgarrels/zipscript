[#macro simpleMacro]
	The body content of simple macro is: ${body}
[/#macro]

[@grid entries=people title="Interesting People"]
[[
        #** The header can be referenced in the macro definition as ${header} **#
        This is the header
]]

        #**
                - template-defined parameters (only string attributes should be enclosed in quotes)
                - the "entry" context value is added by the macro definition
                        (see ...foreach entry in entries... in the macro definition below)
        **#
        [@column title="id" hidden=true] ${entry.id} [/@column]
		[@commonColumns/]

        #** template-defined parameters can be inside other directives **#
        [#if showAge]
                [@column title="Age"] ${entry.age} [/@column]
        [/#if]

        #**
                - template-defined parameters can be externalized for powerful functionality
                - columnList (see below) would be added to the context before merging
        **#
        [#foreach columnInfo in columnList]
                #** properties can be access using [] (see below) when dealing with dynamic property names **#
                [@column title=columnInfo.title size=columnInfo.size] ${entry[columnInfo.propertyName]} [/@column]
        [/#foreach]

        Any non template-defined parameter content inside the macro reference is the macro body and can
        be referenced as ${body}
        
[[
        #** The footer can be referenced in the macro definition as ${footer} **#
        [@simpleMacro]
        	You can use macros/directives in the header/footer as well
        [/@simpleMacro]
]]
[/@grid]


#**
        the macro definition below has the name of "grid" and would be in the "data" macro library as you can see by the reference to "data.grid" above
**#

[#macro grid *title *entries *column[*title width=100 hidden=false cssClass]]
        The title is ${title}
        <table>
                <tr>
                        [#foreach column in column]
                                [#set colSpan=colSpan+1/]
                                <th [#if column.cssClass!=null]class="${column.cssClass}"[/#if]>${column.title}</th>
                        [/#foreach]
                </tr>
                [#if header!=null]
                        <tr><td colspan="${colSpan}">${header}</td></tr>
                [/#if]
                [#foreach entry in entries]
                        <tr>
                        [#foreach column in column]
                                <td style="[#if column.hidden]display: none;[/#if] width: ${column.width}px"> ${column.body} </td>
                        [/#foreach]
                        </tr>
                [/#foreach]
                [#if footer!=null]
                        <tr><td colspan="${colSpan}">${footer}</td></tr>
                [/#if]          
        </table>
[/#macro]

[#macro commonColumns]
        [.@column title="First Name" width=120] ${entry.firstName} [/.@column]
        [@moreCommonColumns/]
[/#macro]

[#macro moreCommonColumns]
	[.@column title="Last Name" width=140] ${entry.lastName} [/.@column]
[/#macro]