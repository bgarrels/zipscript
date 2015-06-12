### Iterator Directive ###
**FM Implementation**
`[#list myList as entry] ... [/#list]`
  * ${entry\_index} is special token to reference index number
  * ${entry\_hasNext) is special token to reference if there are more entries
**ZS Implementation**
`[#foreach entry in myList] ... [/#foreach]`
  * ${i} is a special token to reference index number
  * ${hasNext} is special token to reference if there are more entries
  * scoped context (so ${i} will always relate to the appropriate loop index)

## While Directive ##
**FM Implementation**
  * FM has no implementation
**ZS Implementation**
`[#while boolean expression ex: i<10] ... [/#while]`
  * ${i} is a special token to reference index number
  * scoped context (so ${i} will always relate to the appropriate loop index)

## Variables ##
**FM Implementation**
  * You must start properties with lowercase eg: foo.bar will reference getBar() on foo
  * FM wraps objects in context with FM implementations
  * Formatting variables is accomplished by using ?string.something

**ZS Implementation**
  * Properties can start with either uppercase or lowercase characters
  * ${foo.bar} will either reference foo.getBar(), foo.isBar(), foo.bar(), or context value retrieved with key 'foo.bar' in that order
  * ZS does no wrapping of objects in the context - what is there is what you put there
  * Special token for formatting '|'  ex:
  1. ${myDate|short} -> 12/25/2008
  1. ${myDate|t:short} -> 10:37 AM
  1. ${myDate|"MMM dd, yyyy"} -> Dec 21, 2008
  1. ${myNumber|currency} -> $23,254.98
  1. ${myNumber|"#,###.00} -> 23,254.98

## Special Methods ##
**FM Implementation**
  * FM hides common property/methods and exposes them through the special object methods with '?'
  * FM has many useful transformation/escaping and other utility special methods
**ZS Implementation**
  * ZS will let you call any property on the object class if it exists
  * ZS has the other special methods that are not handled by normal object property methods

## JSP Support ##
FM has ZS beat here because I'm not sure that I plan to support JSP

## Macros ##
**FM Implementation**
  * Can have ordinal/named attribute parameters
  * Can reference nested content in macro definition

**ZS Implementation**
  * Can have ordinal/named attribute parameters
  * Can reference nested content in macro definition
  * Can create macro data structures which can be evaluated in the macro definition for true object oriented macros
```
[@grid id="myGrid" entries=people]
	[@column title="First Name"]${entry.firstName?upperFirst}[/@column]
	[@column title="Last Name"]${entry.lastName!"Unknown Last Name"}[/@column]
	[#if doShowBirthday]
		[@column title="Birthday"]${entry.birthday|short}[/@column]
	[/#if]
[/@grid]

[#macro grid id entries column]
	The grid id is ${id}
	<table>
		<tr>
			[#foreach column in column]
			 <th>${column.title}</th>
			[/#foreach]
		</tr>
		[#foreach entry in entries]
		<tr>
			[#foreach column in column]
				<td>${column.body}</td>
			[/#foreach]
		</tr>
		[/#foreach]
	</table>
[/#macro]
```
Will output:
```
	The grid id is myGrid
	<table>
		<tr>
						 <th>First Name</th>
						 <th>Last Name</th>
					</tr>
				<tr>
							<td>John</td>
							<td>Smith</td>
					</tr>
				<tr>
							<td>Jimmy</td>
							<td>Carter</td>
					</tr>
				<tr>
							<td>Jerry</td>
							<td>Unknown Last Name</td>
					</tr>
			</table>
```