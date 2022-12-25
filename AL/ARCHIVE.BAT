@echo off
pkzip tmp.zip todo *.fea *.txt *.s?
sd ..\doco\*.zip /F/2
uniqname tmp.zip -D:MD -P..\doco
sd ..\doco\*.zip /F/2/e
echo		Ready to move *.FEA & *.TXT to net...
pause
xcopy *.fea L:\games\ll5\al
xcopy *.txt L:\games\ll5\al
del *.fea
del *.txt
echo		Ready to checkin *.S? files...
pause		
for    %%f    in    (      *.s?      ) do call vcsdo.bat checkin %%f

