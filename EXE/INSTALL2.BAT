rem $Rev$
rem **    Corporate/Network PVCS installation batch file
echo * 
copy a:vdiff.exe %1 > nul
copy a:vdel.exe %1 > nul
copy a:vmrg.exe %1 > nul
copy a:regen.exe %1 > nul
copy a:vjournal.exe %1 > nul
copy a:chmod.exe %1 > nul
copy a:filt.exe %1 > nul
copy a:printenv.exe %1 > nul
copy a:rse.exe %1 > nul
echo * 
echo *  PVCS installation complete
echo * 
