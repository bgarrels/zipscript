<html>
	<head>
		<link rel="stylesheet" type="text/css" href="../yui_resources/build/fonts/fonts-min.css" />
[#foreach key in cssIncludes?keys]
		<link rel="stylesheet" type="text/css" href="../yui_resources/build/${key}" />
[/#foreach]

		<script type="text/javascript" src="../yui_resources/build/yahoo-dom-event/yahoo-dom-event.js"></script>
		<script type="text/javascript" src="../yui_resources/build/dragdrop/dragdrop-min.js"></script>
		<script type="text/javascript" src="../yui_resources/build/element/element-beta-min.js"></script>
[#foreach key in scriptIncludes?keys]
		<script type="text/javascript" src="../yui_resources/build/${key}"></script>
[/#foreach]
	</head>
	<body class=" yui-skin-sam">
${screen_placeholder}
	</body>
</html>