<html>
	<head>
		<title>${title}</title>
		[#f null != pageDefinedScript]
			<script language="javascript">
				${pageDefinedScript}
			</script>
		[/#if]
	</head>
	<body>
		${layout_placeholder}
	</body>
</html>