@echo off

if "%1" == "16" 		goto Usage
if "%1" == "256" 		goto Usage
if "%1" == "?" 		goto Usage
if "%1" == "-h"		goto Usage
if "%1" == "/h"		goto Usage

call setsys

if "%1" == "" 			goto NOLOAD
if "%1" == "noload"	goto NOLOAD
if "%1" == "mt" 		goto MT
if "%1" == "mt32" 	goto MT
if "%1" == "mtblast"	goto MTBLAST
if "%1" == "blast"	goto BLAST
if "%1" == "cms"		goto CMS
if "%1" == "std"		goto STD


:NOLOAD
	if "%2" == "16" goto NOLOAD16
	..\system\scidh -d noload.256
	goto Done

:NOLOAD16
	..\system\scid  -d noload.16
	goto Done


:MT
	if "%2" == "16" goto MT16
	..\system\scidh -d mt32.256
	goto Done

:MT16
	..\system\scid  -d mt32.16
	goto Done


:MTBLAST
	if "%2" == "16"	goto MTBLAST16
	...\system\scidh -d mtblast.256
	goto Done

:MTBLAST16
	..\system\scid  -d mtblast.16
	goto Done


:BLAST
	if "%2" == "16"	goto BLAST16
	..\system\scidh -d blast.256
	goto Done

:BLAST16
	..\system\scid  -d blast.16
	goto Done


:CMS
	if "%2" == "16"	goto CMS16
	..\system\scidh -d cms.256
	goto Done

:CMS16
	..\system\scid  -d cms.16
	goto Done


:STD
	if "%2" == "16" goto STD16
	..\system\scidh -d where.256
	goto Done

:STD16
	..\system\scid  -d where.16
	goto Done


:Usage
echo Oops. Instead of what you typed, use this format instead:
echo r [mt32, blast, mtblast, pro, proaudio, mtpro, std, noload] [16, 256] 
echo .
echo i.e., to run mt32 sound with 256 colors type:  R mt32 256
echo .
echo The default is noload 256.
echo .

:Done

