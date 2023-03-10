(script# LOGGER)

(procedure
	Log
)

(public	sysLogger 0)

(local
	logHandle = 0
)

(enum 1
	Txt
	Num
	Uns
	Hex
	Inp
	Tim
	Dat
)

(define MAXCOMMENTS 10)

(procedure (Log how aLabel anArg &tmp [buffer 40] tm retval)
	(Format @buffer "%15s: " aLabel)
	(FileIO fileFPuts logHandle @buffer)
	(= buffer NULL)
	(switch how
		(Txt	(StrCpy @buffer (if anArg anArg else {})))
		(Num	(Format @buffer "%d" anArg))
		(Uns	(Format @buffer "%u" anArg))
		(Hex	(Format @buffer "%x" anArg))
		(Inp
			(if anArg
				(GetInput @buffer 66 anArg)
			)
			(= retval (StrLen @buffer))
		)
		(Tim
			; get system time stamp (minutes after 12:00)
			(= tm (GetTime SYSTIME2))
			(Format 
				@buffer 
				"%02d:%02d:%02d"
				(>> tm 11)
				(& (>> tm 5) %111111)
				(* (& tm %11111) 2)
			)
		)
		(Dat
			; get system date stamp (mm/dd/yy)
			(= tm (GetTime SYSDATE))
			(Format 
				@buffer 
				"%02d/%02d/%02d"
				(& (>> tm 5) %1111)
				(& tm %11111)
				(+ 80 (>> tm 9))
			)
		)
	)
	
	(StrCat @buffer {\r})
	(FileIO fileFPuts logHandle @buffer)
	retval
);procedure Log

(instance sysLogger of Code
	
	(method (doit 
			&tmp	i j l c firstNote theDrv commented saveInfont
			[str 40]
			[cfgPath 30]
			[thePath 30]
			[theToken 30]
			[QAinitials 5]
			[kbdDrvEntry 40]
			[joyDrvEntry 40]
			[videoDrvEntry 40]
			[soundDrvEntry 40]
			[mouseDrvEntry 40]
			[audioDrvEntry 40]
		)
		
		;;	initialize some variables:
		
		(= saveInfont inputFont)
		(= inputFont 999)
		
		(= str 
			(= thePath 
				(= theToken 
					(= kbdDrvEntry 
						(= joyDrvEntry 
							(= videoDrvEntry
								(= soundDrvEntry 
									(= mouseDrvEntry
										(= audioDrvEntry 0)))))))))
		
		(= firstNote (== 0 (StrLen @sysLogPath)))
		
		;; if path argument is NULL, we need some initial info 
		(if firstNote
			(while (not (< 0 (StrLen @thePath) 19))
				(GetInput @thePath 40 
					{Enter drive letter, path and your name\n
					(no extension, max 40 characters)}
				)
			)
			(StrCpy @sysLogPath @thePath 40)
		)
		
		;;access "memory variable" file to seed data
		(Format @thePath "%s.mem" @sysLogPath)
		
		(if (!= -1 (= logHandle (FileIO fileOpen @thePath fRead)))
			(FileIO fileFGets @QAinitials 80 logHandle)
			(FileIO fileFGets @cfgPath 80 logHandle)
			(FileIO fileClose logHandle)
		else
			(= QAinitials 0)
			(StrCpy @cfgPath {resource.cfg})
		)
		
		(if firstNote
			(GetInput @QAinitials 5
				{Enter your initials (up to 3 characters):})
			(StrAt @QAinitials 3 NULL)
		)
		
		;; read configuration file
		(while
			(and 
				(or (not firstNote)
					(GetInput @cfgPath 30 
						{Enter configuration file name (or hit return to skip):})
				)
				(== -1 (= logHandle (FileIO fileOpen @cfgPath fRead)))
				(StrAt @cfgPath 0)
			)
			(StrAt @cfgPath 0 0)
		);while
		
		(if (!= -1 logHandle) ; opened config file
			
			(while (FileIO fileFGets @str 80 logHandle)
				
				; strip leading whitespace
				(for 
					((= i 0)) 
					(and (= c (StrAt @str i)) (OneOf c TAB SPACEBAR)) 
					((++ i))
				);for
				
				(for ((= j 0))
					(and
						(= c (StrAt @str i))
						(not (OneOf c `= `: TAB SPACEBAR))
					) 
					((++ i) (++ j))
					(StrAt @theToken j c)
				)
				
				(StrAt @theToken j NULL)
				
				(= theDrv
					(cond
						((== 0 (StrCmp @theToken {kbdDrv}))		@kbdDrvEntry)
						((== 0 (StrCmp @theToken {joyDrv}))		@joyDrvEntry)
						((== 0 (StrCmp @theToken {videoDrv}))	@videoDrvEntry)
						((== 0 (StrCmp @theToken {soundDrv}))	@soundDrvEntry)
						((== 0 (StrCmp @theToken {mouseDrv}))	@mouseDrvEntry)
						((== 0 (StrCmp @theToken {audioDrv}))	@audioDrvEntry)
					);cond
				);=
				
				(if theDrv
					
					;;skip trailing white space
					(while (and (= c (StrAt @str i)) (OneOf c `= `: TAB SPACEBAR))
						(++ i)
					)
					
					;;find last delimiter and period
					(for ((= j i) (= l 0)) (= c (StrAt @str j)) ((++ j))
						(if (OneOf c `: `\\ `/)
							(= i (+ j 1))
						)
						(if (== c `.)
							(= l (- j i))
						)
					);for
					
					(if (== l 0)
						(= l (- j i))
					)
					
					(StrCpy theDrv (+ @str i) l)
					
				);if theDrv
				
			);while
			
			(FileIO fileClose logHandle)
			
		);if opened config file
		
		;;NOW, open log file!
		(Format @thePath "%s.log" @sysLogPath)
		(if (and
				firstNote
				(or
					;; file doesn't exist so start new one
					(== -1 (= logHandle (FileIO fileOpen @thePath fRead)))
					;; file exists, ask whether to overwrite
					(and (Format @str "Log file \"%s\" exists" @thePath) FALSE)
					(Print @str
						#button: {append to it} FALSE
						#button: {overwrite it} TRUE
					)
				);or
			);and
			
			(FileIO fileClose logHandle)
			(= logHandle (FileIO fileOpen @thePath fTrunc))
		else
			(= logHandle (FileIO fileOpen @thePath fAppend))
		);if-else
		
		(if (== -1 logHandle)
			
			(Printf "error: couldn't open %s" @thePath)
			
		else
			
			;;???????????????????????????????????????
			;;?    Match Fields With Import Items   ?
			;;???????????????????????????????????????
			;;? Number of Items in Import File: 63  ?
			;;???????????????????????????????????????
			;;? Item ? Field Name   ? Type ? Length ?
			;;???????????????????????????????????????
			;;?   1  ? BUG-NUMBER   ?   N  ?     7  ?
			;;?   2  ? BACKWARD     ?   B  ?     7  ?
			;;?   3  ? FORWARD      ?   F  ?     7  ?
			;;?   4  ? GAME         ?   A  ?     6  ?
			
			(Log Txt {GAME} (theGame name?))
			
			;;?   5  ? VERSION      ?   A  ?     7  ?
			
			(Log Txt {VERSION} version)
			
			;;?   6  ? QA-DATE      ?   D  ?     6  ?
			
			(Log Dat {QA-DATE})
			
			;;?   7  ? ANALYST      ?   A  ?     3  ?
			
			(Log Txt {ANALYST} @QAinitials)
			
			;;?   8  ? QA-STATUS    ?   A  ?     1  ?
			;;?   9  ? RE-CHECK     ?   D  ?     6  ?
			;;?  10  ? SEVERITY     ?   A  ?     1  ?
			
			(Log Txt {SEVERITY}
				(Print 
					"Severity of bug..."
					#button: {FATAL} {F}
					#button: {NON-FATAL} {N}
					#button: {SUGGESTION} {S}
				)
			)
			
			;;?  11  ? QA-COMMENT1  ?   A  ?    76  ?
			;;?  12  ? QA-COMMENT2  ?   A  ?    76  ?
			;;?  13  ? QA-COMMENT3  ?   A  ?    76  ?
			;;?  14  ? QA-COMMENT4  ?   A  ?    76  ?
			;;?  15  ? QA-COMMENT5  ?   A  ?    76  ?
			;;?  16  ? QA-COMMENT6  ?   A  ?    76  ?
			;;?  17  ? QA-COMMENT7  ?   A  ?    76  ?
			;;?  18  ? QA-COMMENT8  ?   A  ?    76  ?
			;;?  19  ? QA-COMMENT9  ?   A  ?    76  ?
			;;?  20  ? QA-COMMENT10 ?   A  ?    76  ?
			
			(for 
				((= i 1) (= commented TRUE)) 
				(<= i MAXCOMMENTS)
				((++ i)) 
				
				(Format @theToken "QA-COMMENT%d" i)
				(Format @str "Comment line %d of %d:" i MAXCOMMENTS)
				(if commented
					(= commented (Log Inp @theToken @str))
				else
					(Log Txt @theToken NULL)
				)
			)
			;;?  21  ? DEPARTMENT   ?   A  ?     1  ?
			
			(Log Txt {DEPARTMENT}
				(Print 
					"Who can fix bug..."
					#button: {PROG}   {P}  
					#button: {ART}    {A}  
					#button: {DESIGN} {D}
				)
			)
			
			;;?  22  ? RESPONSE-BY  ?   A  ?     3  ?
			;;?  23  ? RESP-DATE    ?   D  ?     6  ?
			;;?  24  ? ACTION       ?   A  ?     1  ?
			;;?  25  ? RESPONSE-1   ?   A  ?    76  ?
			;;?  26  ? RESPONSE-2   ?   A  ?    76  ?
			;;?  27  ? RESPONSE-3   ?   A  ?    76  ?
			;;?  28  ? RESPONSE-4   ?   A  ?    76  ?
			;;?  29  ? RESPONSE-5   ?   A  ?    76  ?
			
			;;?  30  ? ROOM         ?   N  ?     4  ?
			
			(Log Num {ROOM} curRoomNum)
			
			;;?  31  ? ROOM-SCRIPT  ?   A  ?    15  ?
			;;?  32  ? ROOM-STATE   ?   N  ?     5  ?
			
			(= i (curRoom script?))
			(Log Txt {ROOM-SCRIPT} (if i (i name?)))
			(Log Num {ROOM-STATE} (if i (i state?)))
			
			;;?  33  ? EGO-X        ?   A  ?     3  ?
			;;?  34  ? EGO-Y        ?   A  ?     3  ?
			;;?  35  ? EGO-Z        ?   A  ?     3  ?
			
			(Log Num {EGO-X} (ego x?))
			(Log Num {EGO-Y} (ego y?))
			(Log Num {EGO-Z} (ego z?))
			
			;;?  36  ? EGO-SCRIPT   ?   A  ?    15  ?
			;;?  37  ? EGO-STATE    ?   N  ?     5  ?
			
			(= i (ego script?))
			(Log Txt {EGO-SCRIPT} (if i (i name?)))
			(Log Num {EGO-STATE} (if i (i state?)))
			
			;;?  38  ? EGO-VIEW     ?   A  ?     4  ?
			;;?  39  ? EGO-LOOP     ?   A  ?     2  ?
			;;?  40  ? EGO-CEL      ?   A  ?     2  ?
			;;?  41  ? EGO-PRIORITY ?   A  ?     3  ?
			;;?  42  ? EGO-HEADING  ?   A  ?     3  ?
			
			(Log Num {EGO-VIEW} (ego view?))
			(Log Num {EGO-LOOP} (ego loop?))
			(Log Num {EGO-CEL} (ego cel?))
			(Log Num {EGO-PRIORITY} (ego priority?))
			(Log Num {EGO-HEADING} (ego heading?))
			
			;;?  43  ? EGO-CYCLER   ?   A  ?    15  ?
			
			(Log Txt {CYCLER} (if (ego cycler?) ((ego cycler?) name?)))
			
			;;?  44  ? EGO-MOVER    ?   A  ?    15  ?
			;;?  45  ? MOVER-X      ?   A  ?     3  ?
			;;?  46  ? MOVER-Y      ?   A  ?     3  ?
			;;?  47  ? EGO-MOVESPD  ?   A  ?     4  ?
			
			(= i (ego mover?))
			(Log Txt {EGO-MOVER} (if i (i name?)))
			(Log Num {MOVER-X} 
				(cond 
					((not i) NULL)
					((i isMemberOf: PolyPath) (i: finalX?))
					(else (i x?))
				)
			)
			(Log Num {MOVER-Y} 
				(cond 
					((not i) NULL)
					((i isMemberOf: PolyPath) (i: finalY?))
					(else (i y?))
				)
			)
			
			(Log Num {EGO-MOVESPD} (ego moveSpeed?))
			
			;;?  48  ? SIGNAL-BITS  ?   A  ?     4  ?
			
			(Log Hex {SIGNAL-BITS} (ego signal?))
			
			;;?  49  ? HOWFAST      ?   A  ?     1  ?
			
			(Log Num {HOWFAST} howFast)
			
			;;?  50  ? ICONBAR      ?   A  ?    15  ?
			
			(Log Txt {ICONBAR} (if theIconBar (theIconBar name?)))
			(Log Txt {CUR-ICON} 
				(if (and theIconBar (theIconBar curIcon?))
					((theIconBar curIcon?) name?)
				)
			)
			;;?  51  ? DETAIL-LEVEL ?   A  ?     2  ?
			
			(Log Num {DETAIL-LEVEL} (theGame detailLevel:))
			
			;;?  52  ? CD-AUDIO     ?   A  ?     1  ?
			
			(Log Num {CD-AUDIO} cDAudio)
			
			;;?  53  ? VIDEO-DRV    ?   A  ?     8  ?
			;;?  54  ? SOUND-DRV    ?   A  ?     8  ?
			;;?  55  ? AUDIO-DRV    ?   A  ?     8  ?
			;;?  56  ? KEYBOARD-DRV ?   A  ?     8  ?
			;;?  57  ? JOY-DRV      ?   A  ?     8  ?
			;;?  58  ? MOUSE        ?   A  ?     1  ?
			
			(Log Txt {VIDEO-DRV} @videoDrvEntry)
			(Log Txt {SOUND-DRV} @soundDrvEntry)
			(Log Txt {AUDIO-DRV} @audioDrvEntry)
			(Log Txt {KEYBOARD-DRV} @kbdDrvEntry)
			(Log Txt {JOY-DRV} @joyDrvEntry)
			(Log Txt {MOUSE} @mouseDrvEntry)
			
			
			;;?  59  ? LARGEST-HEAP ?   A  ?     5  ?
			;;?  60  ? FREE-HEAP    ?   A  ?     5  ?
			;;?  61  ? TOTAL-HUNK   ?   A  ?     3  ?
			;;?  62  ? LARGEST-HUNK ?   A  ?     5  ?
			;;?  63  ? FREE-HUNK    ?   A  ?     3  ?
			
			(Log Uns {LARGEST-HEAP} (MemoryInfo LargestPtr))
			(Log Uns {FREE-HEAP} (MemoryInfo FreeHeap))
			(Log Uns {TOTAL-HUNK} (>> (MemoryInfo TotalHunk) 6))
			(Log Uns {LARGEST-HUNK} (MemoryInfo LargestHandle))
			(Log Uns {FREE-HUNK} (>> (MemoryInfo FreeHunk) 6))
			
			;;???????????????????????????????????????
			
			(FileIO fileFPuts logHandle {**********************************\r})
			(FileIO fileClose logHandle)
		);if opened log file
		
		(Format @thePath "%s.mem" @sysLogPath)
		(if (and
				(== -1 (= logHandle (FileIO fileOpen @thePath fTrunc))) ;existing file
				(== -1 (= logHandle (FileIO fileOpen @thePath fAppend))) ;new file
			)
			(Printf "couldn't open memory file %s!" @thePath)
		else
			(FileIO fileFPuts logHandle @QAinitials)
			(FileIO fileFPuts logHandle {\n})
			(FileIO fileFPuts logHandle @cfgPath)
			(FileIO fileFPuts logHandle {\n})
			(FileIO fileClose logHandle)
		)
		
		(= inputFont saveInfont)
		
		;; unload me
		(DisposeScript LOGGER)
	)
)


