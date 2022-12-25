echo off
echo * 
echo **    PVCS Screen Interface installation batch file
echo * 
if trick%1==trick goto curdir
echo *  Files will be copied to directory %1
goto docopy
:curdir
echo *  Files will be copied to the current directory
:docopy
echo *  Make sure PVCS Screen Interface distribution disk is in drive A
echo * 
pause
copy a:pvcs.exe %1 > nul
copy a:pvcs.hlp %1 > nul
