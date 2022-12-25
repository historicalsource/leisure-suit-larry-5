@echo off
:Begin
if exist %vcsdir%%1.sc goto GotValidFile
echo  No such file as "%1.sc"
quit
:GotValidFile
call vcsdo.bat checkout %1.sc
:DoAnother
shift
if not "%1" == "" goto Begin
exit
