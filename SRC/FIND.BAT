Del %tmp%grep

:loop
fgrep -flwx %1 *.sc >> %tmp%grep 
shift
if "%1" == "" goto done
goto loop

:done
xced al
b grep 
xced src
