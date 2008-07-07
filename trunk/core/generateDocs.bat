set CURRDIR=%~dp0
xsltproc --stringparam html.stylesheet ../css/docbook.css --output "%CURRDIR%..\ZipScript Docs\target" "%CURRDIR%..\ZipScript Docs\docbook\html\chunk.xsl" "%CURRDIR%..\ZipScript Docs\src\docs\docbook.xml"
xsltproc --stringparam generate.toc false --stringparam html.stylesheet ../css/docbook.css --output "%CURRDIR%..\ZipScript Docs\target_index" "%CURRDIR%..\ZipScript Docs\docbook\html\chunk.xsl" "%CURRDIR%..\ZipScript Docs\src\docs\docbook.xml"
copy "%CURRDIR%..\ZipScript Docs\target_index\index.html" "%CURRDIR%..\ZipScript Docs\target\index_only.html"
REM del /F "%CURRDIR%..\ZipScript Docs\target_index\*"
REM rmdir "%CURRDIR%..\ZipScript Docs\target_index"
rd /S /Q "%CURRDIR%..\ZipScript Docs\target_index"
pause