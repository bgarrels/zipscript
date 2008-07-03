set CURRDIR=%~dp0
xsltproc --stringparam html.stylesheet ../css/docbook.css --output "%CURRDIR%target/" "%CURRDIR%docbook/html/chunk.xsl" "%CURRDIR%src/docs/docbook.xml"
xsltproc --stringparam generate.toc false --stringparam html.stylesheet http://www.hibernate.org/hib_docs/v3/reference/en/shared/css/html.css --output "%CURRDIR%target_index/" "%CURRDIR%docbook/html/chunk.xsl" "%CURRDIR%src/docs/docbook.xml"
copy "%CURRDIR%target_index\index.html" "%CURRDIR%target\index_only.html"
REM del /F "%CURRDIR%target_index\*"
REM rmdir "%CURRDIR%target_index"
rd /S /Q "%CURRDIR%target_index"
pause