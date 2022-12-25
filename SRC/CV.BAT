@echo off

if "%1" == "" goto Usage
set of = %0.lst
if not "%2" == "" set of = %2

for %f in (l:\games\ll5\prog\view\%1.v16) do gosub CheckView
set n1=
set n2=
set of=
quit


:CheckView
set n1 = %@name[%f]
set n2 = %@name[%f].%@ext[%f]
echo Checking view %n2
echo. >> %of
echo =================================== >> %of
echo VIEW %n2 >> %of
echo. >> %of
l:viewchk l:\games\ll5\prog\view\%n1.v56 l:\games\ll5\prog\view\%n1.v16 >> %of
return


:Usage
echo Usage:   %0 filespec listfile [default = %0.lst]
echo Example: %0 13* my.lst
