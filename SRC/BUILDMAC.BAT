@echo off

call setsys
call setmac
copy s:classdef
copy s:selector

del mac\*.scr
del mac\*.tex
del mac\*.voc

rem In case a non-approved compile was done
del *.scr
del *.tex
del *.voc

x:sc @build.lst -DCD=0 -DWINDOWS=0 -DAMIGA=0 -DFOREIGN=0 -DMAC=1 -aOomac %1 %2 %3 %4 %5 %6 %7 %8 %9 > build.err

call btmac.bat

x:sc -DCD=0 -DWINDOWS=0 -DAMIGA=0 -DFOREIGN=0 -DMAC=1 -aOomac 000.sc	>> build.err
