@echo off

if "%1" == "16" 		 goto Usage
if "%1" == "256" 	    goto Usage
if "%1" == "?" 	    goto Usage
if "%1" == "-h"	    goto Usage
if "%1" == "/h"		 goto Usage

cd ..\src

if "%1" == "" 			 goto NOLOAD
if "%1" == "noload"	 goto NOLOAD
if "%1" == "mt" 		 goto MT
if "%1" == "mt32" 	 goto MT
if "%1" == "blast"	 goto BLAST
if "%1" == "std"		 goto STD
if "%1" == "mtblast"	 goto MTBLAST
if "%1" == "mtpro"	 goto MTPRO
if "%1" == "pro"		 goto PRO
if "%1" == "proaudio" goto PRO

goto Usage

:NOLOAD
	if "%2" == "16" goto NOLOAD16
	C:\system\exe\scidh -d C:\LL5\al\noload.256
	goto Done

:NOLOAD16
	C:\system\exe\scid  -d C:\LL5\al\noload.16
	goto Done


:MT
	if "%2" == "16" goto MT16
	C:\system\exe\scidh -d C:\LL5\al\mt32.256
	goto Done

:MT16
	C:\system\exe\scid  -d C:\LL5\al\mt32.16
	goto Done


:MTBLAST
	if "%2" == "16"	goto MTBLAST16
	C:\system\exe\scidh -d C:\LL5\al\mtblast.256
	goto Done

:MTBLAST16
	C:\system\exe\scid  -d C:\LL5\al\mtblast.16
	goto Done


:MTPRO
	if "%2" == "16"	goto MTPRO16
	C:\system\exe\scidh -d C:\LL5\al\mtpro.256
	goto Done

:MTPRO16
	C:\system\exe\scid  -d C:\LL5\al\mtpro.16
	goto Done


:BLAST
	if "%2" == "16"	goto BLAST16
	C:\system\exe\scidh -d C:\LL5\al\blast.256
	goto Done

:BLAST16
	C:\system\exe\scid  -d C:\LL5\al\blast.16
	goto Done


:PRO
	if "%2" == "16"	goto PRO16
	C:\system\exe\scidh -d C:\LL5\al\proaudio.256
	goto Done

:PRO16
	C:\system\exe\scid  -d C:\LL5\al\proaudio.16
	goto Done


:STD
	if "%2" == "16" goto STD16
	C:\system\exe\scidh -d C:\LL5\al\std.256
	goto Done

:STD16
	C:\system\exe\scid  -d C:\LL5\al\std.16
	goto Done


:Usage
echo Oops. Instead of what you typed, use this format instead:
echo r [mt32, mtblast, proaudio, mtpro, std, noload] [16, 256] 
echo .
echo i.e., to run mt32 sound with 256 colors type:  R mt32 256
echo .
echo The default is "noload" sound in "256" colors.
echo .

:Done
cd C:\LL5\al

