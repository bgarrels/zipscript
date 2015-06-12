A key feature of using a Zipscript macro over other solutions is the ability to provide template-defined attributes that control overall layout.  Have you ever wanted to provide script content referenced actually in the head node?  It's easy.  See the example below:

## Example Template Code ##
_Note: this following template code references a macro demonstrated at the bottom of this page which would be located in a macro library referenced with 'layout'
```
[@layout.simple title="My Page Title"]

    [%style]
        body {
            background-color: blue;
        }
    [/%style]

    [%script]
        function doSomething () {
            return true;
        }
    [/%script]

    This is the body content... blah blah blah
    <script language="javascript">
        doSomething();
    </script>

[/@layout.simple]
```_

## Template Output ##
_Note: whitespace has been changed for readability purposes_
```
<html>
	<head>
		<title>My Page Title</title>
		<script language="javascript">
			function doSomething () {
				return true;
			}
		</script>
		<style type="text/css">
			body {
				background-color: blue;
			}
		</style>
	</head>
	<body >
		<div class="header">
			Header Stuff
		</div>
		<div class="menu">
			Menu Stuff
		</div>
		<div class="screen">
			This is the body content... blah blah blah
		    <script language="java">
		        doSomething();
		    </script>
		</div>
		<div class="footer">
			Footer Stuff
		</div>
	</body>
</html>
```

## Simple HTML Layout Macro Definition Example (layout.simple) ##
```
[#macro simple | script[src=null] style[src=null media="screen"] title="Default Title" onLoad=null]
<html>
	<head>
		<title>${title}</title>
		[#if script != null]
			[#foreach script in script]
				[#if script.src == null]
					<script language="javascript">
						${script.body}
					</script>
				[#else]
					<script language="javascript" src="${script.src}></script>
				[/#if]
			[/#foreach]
		[/#if]
		[#if style != null]
			[#foreach style in style]
				[#if style.src == null]
					<style type="text/css">
						${style.body}
					</style>
				[#else]
					<link rel="stylesheet" type="text/css" href="${style.src}" media="${style.media}"></link>				
				[/#if]
			[/#foreach]
		[/#if]
	</head>
	<body [#if onLoad!=null]onLoad="${onLoad}"[/#if]>
		<div class="header">
			Header Stuff
		</div>
		<div class="menu">
			Menu Stuff
		</div>
		<div class="screen">
			${body}
		</div>
		<div class="footer">
			Footer Stuff
		</div>
	</body>
</html>
[/#macro]
```