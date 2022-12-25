@echo off
pcopy *.sc classes /U /CD:$ /CF:make.lst
type make.lst
C:\system\exe\sc @make.lst > sc.err
del make.lst
