@echo off

call setsys
copy s:classdef
copy s:selector

del ..\script\*.scr
del ..\script\*.tex
del ..\script\*.voc

rem In case a non-approved compile was done
del *.scr
del *.tex
del *.voc

x:sc @build.lst	-aOo..\script %1 %2 %3 %4 %5 %6 %7 %8 %9 > build.err

call buildtxt.bat

x:sc -aOo..\script 000.sc											 >> build.err
