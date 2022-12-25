xced tl
C:
copy L:%1.txt+%1.txt %tmp%append.$$$
copy %tmp%append.$$$ ..\src\%1.txt
copy %tmp%append.$$$ L:%1.txt
del  %1.txt
del  %tmp%append.$$$

