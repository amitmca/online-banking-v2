@ECHO OFF

set hasErrors=0

REM execute maven command here
if not errorlevel 0 set hasErrors=1

REM more batch command and similar checking ...

if %hasErrors%==1 (
    echo print your error info or do whatever
) else (echo %hasErrors%)

pause