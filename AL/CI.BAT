@echo off
:Begin
if exist %1.sc goto GotValidFile
echo  No such file as "%1.sc"
quit
:GotValidFile
pattr ..\src\%1.sc /rN
copy %1.sc ..\src
call vcsdo.bat checkin %1.sc
ptouch ..\src\%1.sc
:DoAnother
shift
if not "%1" == "" goto Begin

