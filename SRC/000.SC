;**
;**	Logics for Room 000 -- Thanks SQ4!
;**
;**	LSL5:  Passionate Patti Does A Little Undercover Work
;**				Copyright 1991
;**				by Al Lowe
;**
;**	Programmed by Brian K. Hughes
;**
;**	Last Update:  September 11, 1991
;**


(script#	0)

(include	menu.sh)

(procedure
	EgoIs
	HandsOff
	HandsOn
	HaveMem
	StepOn
	Btst
	Bset
	Bclr
	RecordTape
	Points
	Face
	NoResponse
	SetFFRoom
	TPrint
	WalkTo
	RestoreIB
	Delay
	Say
	SetupExit
	SaveCurIcon
	LoadCurIcon
	CenterDisplay
)

(public
	LSL5				0
	EgoIs				1
	HandsOff			2
	HandsOn			3
	HaveMem			4
	StepOn			5
	Btst				6
	Bset				7
	Bclr				8
	RecordTape		9
	Points			10
	Face				11
	NoResponse		12
	SetFFRoom		13
	TPrint			14
	WalkTo			15
	RestoreIB		16
	Delay				17
	Say				18
	gcWin				20
	ll5Win			21
	SetupExit		22
	SaveCurIcon		23
	LoadCurIcon		24
	CenterDisplay	25
)


;ษอออออออออออออออออออป
;บ                   บ
;บ Global Sounds     บ
;บ                   บ
;ศอออออออออออออออออออผ

(instance longSong of Sound)
(instance longSong2 of Sound)
(instance hotSound of Sound)

(class WrapMusic of Object
	(properties
		firstSound	0		; sound number with which to start
		lastSound	0		; sound number at which to stop
	)
	(methods
		cue
	)
	(method (init)
		(super init:)
		(theMusic
			number:		firstSound,
			setLoop:		1,
			play:			self
		)
	)
	(method (cue &tmp num oldVol)
		(if (== (theMusic prevSignal?) SND_DONE)	; don't change sounds on fade
			(= num (theMusic number?))
			(= oldVol (theMusic vol?))
			(if (> (++ num) lastSound)
				(= num firstSound)
			)
			(theMusic number: num, play: oldVol self)
		)
	)
)


;ษอออออออออออออออออออป
;บ                   บ
;บ Global Timers     บ
;บ                   บ
;ศอออออออออออออออออออผ

(class ll5Timer kindof Timer
	(properties
		code		0		; object ID of code to perform when cued
	)
	(method (cue)
		(if code
			(code doit:)
			(= code 0)
		)
	)
)

(instance camcorderTimer of Timer
	(properties
		seconds			0
	)
	(method (doit)
		(if camcorderOn
			(super doit:)
		)
	)
	(method (cue &tmp cam)
		(TPrint "You hear a delicate little click from the camcorder
			as its tiny battery is now completely drained.")
		(= cam (Inventory at: iCamcorder))
		(cam state: (- (cam state?) 100))
	)
)


;ษอออออออออออออออออออป
;บ                   บ
;บ Actions Class     บ
;บ                   บ
;ศอออออออออออออออออออผ

(class Actions kindof Code
	(methods
		doVerb
	)
	(method (doVerb)
		(return FALSE)
	)
)


;ษอออออออออออออออออออป
;บ                   บ
;บ Grooper           บ
;บ                   บ
;ศอออออออออออออออออออผ

(instance stopGroop of GradualLooper)


;ษอออออออออออออออออออป
;บ                   บ
;บ Quit Icon         บ
;บ                   บ
;ศอออออออออออออออออออผ

(instance quitIcon of DCIcon
	(method (init)
		((= cycler (Forward new:)) init: self)
	)
)


;ษอออออออออออออออออออป
;บ                   บ
;บ EventHandlers     บ
;บ                   บ
;ศอออออออออออออออออออผ

(instance ll5KDHandler	of EventHandler)	;just gets keyDown events
(instance ll5MDHandler 	of EventHandler)	;just gets mouse events
(instance ll5DirHandler of EventHandler)	;just gets direction events


;ษอออออออออออออออออออป
;บ                   บ
;บ ll5 Game          บ
;บ                   บ
;ศอออออออออออออออออออผ

(instance LSL5 of Game
	(method (init &tmp num [str 5] theEgo cfgHandle panicWin)

		; make our instance of grooper global
		(= sGrooper stopGroop)

		; Preload scripts we're gonna use all the time
		StopWalk
		Timer
		Polygon
		PolyPath
		LLRoom
		IconBar
		Inventory
		(ScriptID SIGHT)

		(super init:)

		((ScriptID INIT 0) init:)
		(UnLoad SCRIPT INIT)

		; set up for the default feature doVerb thing
		(= doVerbCode ll5DoVerbCode)
		(= ftrInitializer ll5FtrInit)

		; initialize handlers
		((= keyDownHandler	ll5KDHandler) 	add:)
		((= mouseDownHandler	ll5MDHandler)	add:)
		((= directionHandler	ll5DirHandler) add:)
		(= pMouse PseudoMouse)

		; set up some global sounds
		((= theMusic longSong)
			owner: self,
			flags: mNOPAUSE,
			init:
		)
		((= theMusic2 longSong2)
			owner: self,
			flags: mNOPAUSE,
			init:
		)

		; set up our global timer
		(= theTimer ll5Timer)
		(= chargeTimer camcorderTimer)

		(if (not (FileIO fileExists {memory.drv}))
			((= panicWin (SysWindow new:))
				color:	colBlack,
				back:		colWhite
			)
			(Print "Can't load MEMORY.DRV"
				#window: panicWin
				#font:	0
			)
			(= quit TRUE)
			(return FALSE)
		)

		; set up & read in the version number & version date
		(= version 				{x.yyy.zzz})
		(= versionDate 		{mm/dd/yy})
		(= versionPhone		{991-999-9999})
		(= versionIntPhone	{992-999-9999})
		(= cfgHandle (FileIO fileOpen {version} fRead))
		(FileIO fileFGets version 				11 cfgHandle)
		(FileIO fileFGets versionDate			20 cfgHandle)
		(FileIO fileFGets versionPhone		20 cfgHandle)
		(FileIO fileFGets versionIntPhone	20 cfgHandle)
		(FileIO fileClose cfgHandle)

		; set up icon bar
		((= theIconBar IconBar)
			add:
				icon0
				icon1
				icon2
				icon3
				icon4
				icon5
				icon6
				icon7
		  		icon8
				icon9,
			eachElementDo: #init:,
			eachElementDo: #highlightColor: 0,
			curIcon:			icon0,
			useIconItem:	icon6,
			helpIconItem:	icon9,
			disable:			useIcon,
			disable:			,
			state:			(| OPENIFONME NOCLICKHELP)
		)
		(icon7 message:
			(if (HaveMouse)
				SHIFTTAB
			 else
			 	TAB
			)
		)

		; set up control panel
		(GameControls
			window:	gcWin,
			add:
				iconOk
				detailSlider
				(volumeSlider
					theObj:	  	self,
					selector:  	#masterVolume,
					yourself?
				)
				(speedSlider
					theObj:	  	self,
					selector:  	#setSpeed,
					yourself?
				)
				textSlider
				(iconSave
					theObj: 		self,
					selector: 	#save,
					yourself?
				)
				(iconRestore
					theObj: 		self,
					selector: 	#restore,
					yourself?
				)
				(iconRestart
					theObj: 		self,
					selector: 	#restart,
					yourself?
				)
				(iconQuit
					theObj:		self,
					selector: 	#quitGame,
					yourself?
				)
				(iconAbout
					theObj:	 	(ScriptID ABOUT 0),
					selector:	#doit,
					yourself?
				)
				iconHelp,

			eachElementDo:	#highlightColor:	0,
			eachElementDo:	#lowlightColor:	colLYellow,
			helpIconItem:	iconHelp,
			curIcon:			iconSave,
			state:			NOCLICKHELP
		)

		; set ego up to be Larry (we don't use (EgoIs LARRY) because we don't
		;	want the inventory to be set up yet).  We use the define, LARRY,
		;	because we don't actually load the module until after we see if
		;	we're going to the limo
		(= theEgo LARRY)

		; can't fast-forward at first
		(SetFFRoom 0)

		(if (GameIsRestarting)
			(MemorySegment MS_RESTORE_TO @restartRoom)
;		)
			else						;DEBUG - Comment to line *****
				(= str 0)
				(= num 0)
				(= num
					(PrintD {Where to, Larry/Patti?}
						#edit	@str 5
						#new
						#button {L Opening}		100
						#button {Hollywood}		160
						#button {Limousine}		200
						#button {Airports}		250
						#new
						#button {Airplane}		310
						#button {P Opening}		400
						#button {K-RAP}			800
						#button {des Rever}		600
						#new
						#button {New York}		500
						#button {Atlantic City}	700
						#button {Miami}			900
						#button {Closing}			460
						#new
						#button {Restore} 		-100 #x 100 #y 10
					)
				)
				(if str
					(= num (ReadNumber @str))
				)

				; debug
				(if (== num -100)
					(theGame restore:)
				)

				(if (< num 100)
					(= num 100)
				)
				(if (== num 200)
					(if (PrintD {sex?}
							#new
							#button {Larry}			0
							#new
							#button {Patti}			1
						)
						(= theEgo PATTI)
					)
				)

				(= restartRoom num)

				; We do this so that PRINTD (which gets disposed in startRoom)
				;	doesn't get loaded under our ego module
				(DisposeScript PRINTD)
				(= debugging TRUE)
			)

			(theIconBar enable:)


			;DEBUG

			; NOW we load the appropriate ego module & setup inventory
			(if (== theEgo LARRY)			; DEBUG - *****
			(= ego Larry)
			(= egoIsLarry TRUE)
			else									; DEBUG - Comment to line *****
				(= ego Patti)
				(= egoIsLarry FALSE)
			)										; DEBUG - *****

 		(User
			alterEgo:		ego,
			verbMessager:	0,
			canControl:		FALSE,
			canInput:		FALSE,
		)

		(Inventory release:)
		(ego setUpInv:)

		(self newRoom:	restartRoom)
	);init

	(method (doit)
		(super doit:)
		(cond
			; Value is 1/20th of 1% (for example, 242 = 12.1%)
			(camcorderCharging
				(= camcorderCharge (Min 2000 (+= camcorderCharge 3)))
			)
			(camcorderOn
				(= camcorderCharge (Max 0 (-- camcorderCharge)))
			)
		)
	);doit

	(method (pragmaFail &tmp theVerb theItem [str 30])
		(if (and	modelessDialog
					(not talkersOnScreen)
				)
			(modelessDialog dispose:)
			(return TRUE)
		)
		(if (User canInput?)
			(= theVerb ((User curEvent?) message?))
			(switch theVerb
				(verbLook
					(TPrint "It's not much to look at.")
				)
				(verbTalk
					(TPrint "To whom were you trying to speak?")
				)
				(verbDo
					(TPrint "There's nothing to do there.")
				)
				(verbZipper
					(cond
						;see if there's a script running?
						((curRoom script?)
							(TPrint "Now?!")
						)
						((and (!= (ego view?) vLarry)
								(!= (ego view?) vPatti))
							(TPrint "Now?!")
						)
						(else
							(if egoIsLarry
								(TPrint "You coyly turn your back and
									relieve yourself.
									You quickly feel much better.
									The hint of a smile crosses your lips.")
							else
								(TPrint "Visions of cucumbers dance through
									your head.")
							)
						)
					)
				)
				(verbUse
					(= theItem (Inventory indexOf: (theIconBar curInvIcon?)))
					(if egoIsLarry
						(switch theItem
							(iCamcorder
								(TPrint "Good thinking! Shoot a little tape of that!")
								(TPrint "(But is your pocket camcorder working?)" LOW)
							)
							(iHDCNapkin
								(TPrint "You wave your napkin in the air. How fun!")
							)
							(iGoldCard
								(TPrint "That's one more place that won't accept
									your AeroDork Gold Card!")
							)
							(iQuarter
								(TPrint "That doesn't look like a vending machine!")
							)
							(iMoney
								(TPrint "Stop flashing your money around, Larry!")
							)
							(iCasinoMatchbook
								(TPrint "That's not something you should set afire!")
							)
							(iDoily
								(TPrint "Your doily would look lovely there,
									but you'd better keep it instead.")
							)
							(else
								(Format @str "Try finding some place else to place %s."
									((Inventory at: theItem) description?)
								)
								(TPrint @str)
							)
						)
					else
						(switch theItem
							(iFax1ReverseBiaz
								(TPrint "You wave the fax in the air. You have fun.")
								(TPrint "(But not much!)" LOW)
							)
							(iChampagne
								(TPrint "That's not a good place to pour out your
									champagne.")
							)
							(iGoldRecord
								(TPrint "Your gold record would get scratched there.")
							)
							(iFax2PCHammer
								(TPrint "You wave the fax in the air. You have fun.")
								(TPrint "(But not much!)" LOW)
							)
							(iBra
								(TPrint "You briefly contemplate firing your bra
									at that, but decide to hold your \"fire.\"")
							)
							(else
								(Format @str "Try finding some place else to place %s."
									((Inventory at: theItem) description?)
								)
								(TPrint @str)
							)
						)
					)
				)
			)
		)
	);pragmaFail

	(method (restart)
		(curRoom
			style:		IRISIN,
			drawPic: 	pBlackPic,
		)
		(cast eachElementDo: #hide:)
		(MemorySegment MS_SAVE_FROM @restartRoom 2)
		(super restart:)
	)

	(method (quitGame &tmp [str1 10] [str2 8] theFile)
		(quitIcon
			view:			vQuitIcon,
			loop:			1,
			cycleSpeed:	9,
		)
		(if
			(Print
			 "\"Fine. Go ahead. Leave! We've got\n
			 	better things to do in here\n
				anyway, don't we, Patti?\"\n
				\n
				\n
				\"(How's this feel, babe?)\"\n
				\n
				\n
			  \"(Ooooh, Larry! Let 'em go play\n
				with their word processors now!)\""
				#title:	{So You're Finally Leaving?}
				#button:	{Okay, you two. Have fun!}	1
				#button:	{Hey! Back to work!}			0
				#icon:	quitIcon 0 0
			)
			(if ((= theFile (File new:)) name: {MEMORY.DRV}, open: fRead)
				(theFile
					readString:	@str1 20,
					close:
				)
			)
			(if (theFile open: fTrunc)
				(Format @str2 "%d" pokerJackpot)
				(theFile
					writeString: @str1,
					writeString: {\n},
					writeString: @str2,
					close:
				)
			)
			(theFile dispose:)		;superstition?
			(super quitGame: TRUE)
		)
	);quitGame

	(method (newRoom)
		;
		; This method is only here so if we're doing a newRoom as a result
		;	of hitting the fast-forward icon, we can do certain things before
		;	ffRoom gets set to 0 again.  If the ffRoom global is > 1000, then
		;	we have poked the fast-forward icon & are about to go to whereever
		;	it leads us.
		;
		(Cls)

		; If the camcorder still has time left, save it so we can get rid
		;	of the timer - will restore the time in the new room
		(= saveCharge 0)
		(if (chargeTimer client?)
			(= saveCharge (chargeTimer seconds?))
			(chargeTimer client: 0)
		)

		(if (and	(IsObject fastCast)
					(fastCast elements?)
				)
			(fastCast eachElementDo: #dispose: TRUE)
		)

		(theIconBar disable:)

		(if (> ffRoom ffInProgress)
			(switch ffRoom
				(1155
					(Bset fBeenIn150)
				)
				(1480
					(Bset fFFto480)
				)
				; Add any other code that needs to be done on FF-ing here!
			)
			(-= ffRoom ffInProgress)
			(super newRoom: &rest)
		else
			(super newRoom: &rest)
		)
	)

	(method (setSpeed what)
		(if argc
			(ego
			  	cycleSpeed:	what,
			  	moveSpeed: 	what
			)
			(self egoMoveSpeed: what)
		)
		(return (ego moveSpeed?))
	)

	(method (startRoom n)

		; Dump load-as-needed code
		((ScriptID DISP) doit: n)

;		; Check for frags
;		(if (and	(!= (- (MemoryInfo FreeHeap) 2)
;					(MemoryInfo LargestPtr))
;					(PrintD {Memory fragmented.}
;						#new
;						#button: {Who cares} 0
;						#button: {Debug} 		1
;					)
;				)
;			(SetDebug)
;		)

		; If we've gotten to the end of a cartoon without fast-forwarding
		(if (OneOf ffRoom n SAMEROOM)
			(SetFFRoom 0)
		)

		; Dump the ego we're not using (if any) and setup inventory
		(cond
			((== n LIMO)
				(Inventory release:)
				(ego setUpInv:)
			)
			((OneOf n LARRYROOMS)
				(if (not egoIsLarry)
					(DisposeScript PATTI)
					(Inventory release:)
					(theIconBar curInvIcon: 0)
					(theIconBar curIcon: (theIconBar at: walkIcon))
					(EgoIs LARRY)
				)
			)
			(else	; PATTIROOMS
				(if egoIsLarry
					(DisposeScript LARRY)
					(Inventory release:)
					(theIconBar curInvIcon: 0)
					(theIconBar curIcon: (theIconBar at: walkIcon))
					(EgoIs PATTI)
				)
			)
		)

		; MEMORY EMPTY!!!	start loading it back up.

		; Load any regions we want to keep
		(if (OneOf n HOLLYWOOD)
			(ScriptID HOLLYWOODRGN)
  		)

	  	; Take out the following lines for shipping
		(if (and	debugging (not (OneOf n NODEBUGROOMS)))
			((ScriptID DEBUG 0) init:)
;			(= debugging FALSE)
		)

		(theIconBar enable:)

		;FINALLY, let the room load
		(super startRoom: n)

		; Check to see if we had time on the camcorder, and if so restore it
		;	and restart the timer
		(if saveCharge
			(chargeTimer setReal: chargeTimer saveCharge)
		)

		(if (and	(ego cycler?)
					(not (ego looper?))
					((ego cycler?) isKindOf: StopWalk)
				)
			(ego setLoop:	stopGroop)
		)
	);startRoom

	(method (handleEvent event)
		; Since mouseUps only go to game (not mouseDownHandler), we do this:
		(if (== (event type?) mouseUp)
			(mouseDownHandler handleEvent: event)
		else
			(super handleEvent: event)
		)
		(if (event claimed?) (return TRUE))
		(switch (event type?)
			(keyDown
				(if (and	(not (OneOf curRoomNum NO_SAMPLE_ROOMS))
;**								( > (>> (MemoryInfo FreeHunk) 6) 27)
							(DoSound NumDACs))
					(switch (event message?)
						(`#1	(hotSound number: 482, play:))
						(`#3	(hotSound number: 483, play:))
						(`#4	(hotSound number: 484, play:))
						(`#6	(hotSound number: 851, play:))
						(`#8	(hotSound number: 526, play:))
						(`#9 	(hotSound number: 161, play:))
						(`#10	(hotSound number: 892, play:))
					)
				)
				(switch (event message?)
					; inventory was invoked from keyboard
					; but only if inventory icon is active
					(TAB							;**	inventory
						(if (not (& ((theIconBar at: inventoryIcon) signal?) DISABLED))
							(if (or fastCast talkersOnScreen)
								(return)
							)
							(ego showInv:)
						)
					)
					(SHIFTTAB					;**	inventory
						(if (not (& ((theIconBar at: inventoryIcon) signal?) DISABLED))
							(if (or fastCast talkersOnScreen)
								(return)
							)
							(ego showInv:)
						)
					)
					(`^q 							; quit
						(theGame quitGame:)
						(event claimed: TRUE)
					)
					(`^c
						(theIconBar hide:)
						(GameControls show:)
					)
					(`#2 							; volume
						(cond
							((theGame masterVolume:)
								(theGame masterVolume: 0)
							)
							((> numVoices 1)
								(theGame masterVolume: 15)
							)
							(else
								(theGame masterVolume: 1)
							)
						)
						(event claimed: TRUE)
					)
					(`#5 							; save
						(if (not (& ((theIconBar at: computerIcon) signal?) DISABLED))
							(if (or fastCast talkersOnScreen)
								(return)
							)
							(theGame save:)
							(event claimed: TRUE)
						)
					)
					(`#7 							; restore
						(if (not (& ((theIconBar at: computerIcon) signal?) DISABLED))
							(if (or fastCast talkersOnScreen)
								(return)
							)
							(theGame restore:)
							(event claimed: TRUE)
						)
					)
					(`+							;**	faster
						(if (User controls?)
							(theGame setSpeed: (Max 0 (- (theGame egoMoveSpeed?) 1)))
						)
					)
					(`-							;**	slower
						(if (User controls?)
							(theGame setSpeed: (+ (theGame egoMoveSpeed?) 1))
						)
					)
					(`=							;**	normal speed
						(if (User controls?)
							(theGame setSpeed: 6)
						)
					)
				)
			)
		)
	);handleEvent
);ll5 instance


;ษอออออออออออออออออออป
;บ                   บ
;บ Procedures        บ
;บ                   บ
;ศอออออออออออออออออออผ

(procedure (EgoIs who)
	(switch who
		(LARRY
			(= ego Larry)
			(= egoIsLarry TRUE)
			(User alterEgo: ego)
			(ego setUpInv:)
		)
		(PATTI
			(= ego Patti)
			(= egoIsLarry FALSE)
			(User alterEgo: ego)
			(ego setUpInv:)
		)
	)
)


(procedure (HandsOff)
	(SaveCurIcon)

 	(User
		canControl:	FALSE,
		canInput:	FALSE
	)
	(ego setMotion: 0)

	; save the state of each icon so we can put the icon bar back the way it was
	(= iconSettings 0)
	(theIconBar eachElementDo: #perform checkIcon)

	; disable some icons so user doesn't screw us up
	(theIconBar disable:
						walkIcon
						lookIcon
						doIcon
						talkIcon
						zipperIcon
						useIcon
						inventoryIcon
	)

	; if no mouse, move the cursor out of the way, but save the initial
	; posn so HandsOn can restore it
	(if (not (HaveMouse))
		(= saveCursorX mouseX)
		(= saveCursorY mouseY)
		(theGame setCursor: waitCursor TRUE 310 185)
	else
		(theGame setCursor: waitCursor TRUE)
	)
)


(procedure (HandsOn)
	(User
		canControl: TRUE,
		canInput:	TRUE
	)

	; re-enable iconbar
	(theIconBar enable:
						walkIcon
						lookIcon
						doIcon
						talkIcon
						zipperIcon
						useIcon
						inventoryIcon
						computerIcon
	)
	(if (not (theIconBar curInvIcon?))
		(theIconBar disable: useIcon)
	)

	(LoadCurIcon)

	; restore cursor xy posn if no mouse
	(if (not (HaveMouse))
		(theGame setCursor:
			((theIconBar curIcon?) cursor?) TRUE saveCursorX saveCursorY
		)
	else
		(theGame setCursor:
			((theIconBar curIcon?) cursor?) TRUE
		)
	)
)


(procedure (HaveMem howMuch)
	(return (u> (MemoryInfo FreeHeap) howMuch))
)


; return true if who's origin is on color
(procedure (StepOn who color)
	(if (& (who onControl: origin) color)
		(return TRUE)
	)
)


(procedure (Btst flagEnum)
	(& [gameFlags (/ flagEnum 16)] (>> $8000 (mod flagEnum 16)))
)


(procedure (Bset flagEnum  &tmp oldState)
	(= oldState (Btst flagEnum))
	(|= [gameFlags (/ flagEnum 16)] (>> $8000 (mod flagEnum 16)))
	oldState
)


(procedure (Bclr flagEnum  &tmp oldState)
	(= oldState (Btst flagEnum))
	(&= [gameFlags (/ flagEnum 16)] (~ (>> $8000 (mod flagEnum 16))))
	oldState
)


(procedure (RecordTape who theTape &tmp whichTape)
	;
	; 'theTape' only gets passed to us if we're degaussing from room 170.
	;	The only reason we even have this is that changing names of global
	;	objects must be done in global code.
	;
	(if (== who tapeDegaussed)
		((Inventory at: theTape)
			state:	who,
			name:		{An Erased Videotape}
		)
	else
		(= whichTape (mod ((Inventory at: iCamcorder) state?) 100))
		((Inventory at: whichTape)
			state:	who,
			name:		(switch who
							(tapeRecordedMM	{Michelle Milken})
							(tapeRecordedLL	{Lana Luscious})
							(tapeRecordedCC	{Chi Chi Lambada})
						)
		)
	)
)


; Give the player points for a puzzle.
; If the second parameter exists, it is assumed to be a bit flag that must
;	be clear to get points, at which time the flag is set

(instance pointsSound of Sound
	(properties
		number		10
		flags			mNOPAUSE
	)
)

(procedure (Points val flag)
	(if (> argc 1)
		(if (Bset flag)	; Bset returns previous state of flag; if set, no pts
			(= val 0)
		)
	)
	(if val
		(theGame changeScore: val)
		(if (> val 0)
 			(pointsSound play:)
		)
	)
)


; force 'who' to face either another object or an xy
; usage:
;	(Face who whoElse)
;	will turn who to face whoElse, cueing nobody

;	(Face who whoElse whoCares)
;	will turn who to face whoElse, cueing whoCares

;	(Face who x y)
;	will turn who to face pixel at x y

;	(Face who x y whoCares)
;	will turn who to face pixel at x y, cueing whoCares

(procedure (Face who theObjOrX theY whoCares
				&tmp theHeading lookX lookY whoToCue)
	(= whoToCue 0)
	(if (IsObject theObjOrX)
		(= lookX (theObjOrX x?))
		(= lookY (theObjOrX y?))
		(if (== argc 3)
			(= whoToCue theY)
		)
	else
		(= lookX theObjOrX)
		(= lookY theY)
		(if (== argc 4)
			(= whoToCue whoCares)
		)
	)

	(= theHeading (GetAngle (who x?) (who y?) lookX lookY))
	(who setHeading: theHeading (if (IsObject whoToCue) whoToCue))
)


; To fast-forward through a sequence contained within one room, use:
;
;		(SetFFRoom SAMEROOM who2cue)
;
(procedure (SetFFRoom theRoom cueObj)
	(if (not theRoom)
		(= ffRoom 0)
		(= ffCueObj 0)
		(theIconBar disable: ffIcon)
	else
		(= ffRoom theRoom)
		(if (and	(> argc 1)
					(== theRoom SAMEROOM)
				)
			(= ffCueObj cueObj)
		)
		(theIconBar enable: ffIcon)
	)
)


(procedure (TPrint arg1 arg2 &tmp [str 300] theTime saveCursor)

	;	The time here is based on the length of the string in characters,
	;	multiplied by the current position of the text speed slider divided
	;	by 120, with a minimum of 3 seconds.

	(Cls)
	(if (not (HaveMouse))
		(= saveCursor (theGame setCursor: 69 TRUE))
	)
	(if (u< arg1 1000)
		(GetFarText arg1 arg2 @str)
		(= theTime (Max 3 (/ (* textSpeed (StrLen @str)) 120)))
		(Print @str #time: theTime &rest)
	else
		; Near string
		(= theTime (Max 3 (/ (* textSpeed (StrLen arg1)) 120)))
		(Print arg1 #time: theTime arg2 &rest)
	)
	(if (not (HaveMouse))
		(theGame setCursor: saveCursor TRUE)
	)
	(return TRUE)
)


; This walks ego to an object and optionally calls the object's doVerb method

(procedure (WalkTo theObj theVerb &tmp evt)
	(if (< argc 2)
		(= theVerb verbWalk)
	)
	(= evt
		((Event new:)
			type:		userEvent,
			message:	theVerb,
			x:			(theObj x?),
			y:			(theObj y?),
		)
	)
	(theObj handleEvent: evt)
	(evt dispose:)
)


; This procedure restores each icon's state (enabled or disabled) to what it
;	was at the time of the last HandsOff

(procedure (RestoreIB &tmp i)
	(HandsOn)
	(for	((= i 0))
			(< i 8)
			((++ i))
		(if (& iconSettings (>> $8000 i))
			(theIconBar disable: i)
		)
	)
)


; This procedure sets a timer that, when finished, cues an object or performs
;	a piece of code
;
; Examples:
;
;		(Delay 5 CYCLES actor)	 			- delays 5 cycles then cues an actor
;		(Delay 4 SECONDS NULL myCode)		- delays 4 seconds then performs myCode
;		(Delay 6 REALSECONDS NULL myCode)- delays 6 real-time seconds, then
;													  performs myCode

(procedure (Delay howLong delayType whoCares theCode)
	(if (or	(< argc 3)
				(== whoCares NULL)
			)
		(= whoCares theTimer)
	)
	(if (> argc 3)
		(theTimer code: theCode)
	)
	(cond
		((== delayType CYCLES)
			(theTimer setCycle: whoCares howLong)
		)
		((== delayType SECONDS)
			(theTimer set: whoCares howLong)
		)
		(else
			(theTimer setReal: whoCares howLong)
		)
	)
)


; This procedure tells a Talker to talk, initing him if necessary.  The
;	dispose and caller selectors can be passed (as in Print).  This procedure
;	assumes that the bust, eyes, and mouth properties of the Talker have
;	already been set (i.e. in its init method).
;
; Examples:
;
;		(Say fred "This is a test" #dispose:)
;		(Say elmo @elmoMsg #caller: self)
;		(Say ego  "My name is Larry; Larry Laffer")

(procedure (Say who arg1 arg2 &tmp disposeOrNot who2Cue i [buffer 200])
	; TEXT parameter may be far (module/message#)
	(cond
		((u< arg1 1000)
			(GetFarText arg1 arg2 @buffer)
			(= i 2)
		)
		(arg1
			(StrCpy @buffer arg1)
			(= i 1)
		)
		(else
			(= buffer 0)
			(= i 0)
		)
	)

	(= disposeOrNot (= who2Cue FALSE))

	; If the who is LARRY or PATTI, just use a print to mimick a talker

	(if (== who ego)
		(if (> i 1)
			(TPrint @buffer #title: {You} &rest)
		else
			(TPrint @buffer #title: {You} arg2 &rest)
		)
	else
		; the rest of the args passed are optional
		(for ((= i i)) (< i argc) ((++ i))
			(switch [arg1 i]
				(dispose
					(= disposeOrNot TRUE)
				)
				(caller
					(++ i)
					(= who2Cue [arg1 i])
				)
			)
		)

		(if (who underBits:)	; talker is still on the screen
			(who say: @buffer NULL NULL disposeOrNot who2Cue)
		else
			(who
				init:	,
				say:	@buffer NULL NULL disposeOrNot who2Cue
			)
		)
	)
)



; DEBUG - This procedure should not ship
;				(or at least should be disconnected)

(procedure (NoResponse obj verb &tmp [str 40] [fileName 10] [vstr 10])
	(StrCpy @vstr
		(switch verb
			(verbWalk 	{Walk})
			(verbLook 	{Look})
			(verbDo		{Do})
			(verbTalk	{Talk})
			(verbZipper {Zipper})
			(else			{UNKNOWN_VERB})
		)
	)
	(Format @fileName "%d.msg" curRoomNum)
	(Format @str ";%s (%s) [%d,%d]: "
  					(obj name?)
					@vstr
					((User curEvent?) x?)
					((User curEvent?) y?)
  	)
	(File
		name: 			@fileName,
		writeString:	@str "\r",
		close:			,
	)
)


(procedure (SetupExit turnOn)
	(if turnOn
		(icon0
			loop:		lExitIcon,
			cursor: 	EXIT_CURSOR,
		)
	else
		(icon0
			loop:		lWalkerIcon,
			cursor:	TRAVEL_CURSOR,
		)
	)
	(if (== (theIconBar curIcon?) icon0)			; we're on walk
		(theGame setCursor (icon0 cursor?))
	)
)


(procedure (SaveCurIcon)
	(if (not theCurIcon)	; don't want to save it twice!
		(= theCurIcon (theIconBar curIcon?))
	)
)

(procedure (LoadCurIcon)
	(if theCurIcon
		(theIconBar curIcon: theCurIcon)
		(theGame setCursor: ((theIconBar curIcon?) cursor?))
		(= theCurIcon 0)
		(if (and	(== (theIconBar curIcon?) (theIconBar at: useIcon))
					(not (theIconBar curInvIcon?))
				)
			(theIconBar advanceCurIcon:)
		)
	)
)

(procedure (CenterDisplay lines theColor &tmp oldPort)
	(Cls)
	(= oldPort (GetPort))
	(SetPort 0)
	(Display &rest
		#p_mode:		teJustCenter
		#p_color: 	theColor
		#p_font:		niceFont
		#p_width:	318
		#p_at: 		1 (- 89 (/ (* 14 lines) 2))
		;           ณ             ณ    ณ    ณ
		;           ณ             ณ    ณ    ภฤ this must be 2
		;           ณ             ณ    ภฤฤฤฤฤฤ make this # of lines in msg
		;           ณ             ภฤฤฤฤฤฤฤฤฤฤฤ make this font height
		;           ภฤฤฤฤฤฤฤฤฤฤฤฤฤฤฤฤฤฤฤฤฤฤฤฤฤ this must be 1
	)
	(SetPort oldPort)
)



;ษอออออออออออออออออออป
;บ                   บ
;บ IconBar icons     บ
;บ                   บ
;ศอออออออออออออออออออผ

(instance icon0 of IconItem				;walk
	(properties
		view			vIcons
		loop			0
		cel			0
		maskView		vIcons
		maskLoop		14
		maskCel		1
		message		verbWalk
		cursor		TRAVEL_CURSOR
		signal		(| RELVERIFY HIDEBAR)
		helpStr		"Use this icon to move your character."
	)
	(method (init)
		(= lowlightColor colLMagenta)
		(super init:)
	)
	(method (select &tmp evt)
		(if (super select: &rest)
			(theIconBar hide:)
			(if (== cursor EXIT_CURSOR)
				((= evt (Event new:))
					type:		mouseDown,
					message:	verbWalk
				)
				(if (not (mouseDownHandler handleEvent: evt))
					(regions handleEvent: evt)
				)
				(evt dispose:)
			)
			(return TRUE)
		else
			(return FALSE)
		)
	)
)

(instance icon1 of IconItem				;look
	(properties
		view			vIcons
		loop			1
		cel			0
		maskView		vIcons
		maskLoop		14
		maskCel		1
		message		verbLook
		cursor		EYE_CURSOR
		signal		(| RELVERIFY HIDEBAR)
		helpStr		"Use this icon to look at things."

	)
	(method (init)
		(= lowlightColor colCyan)
		(super init:)
	)
)

(instance icon2 of IconItem				;do
	(properties
		view			vIcons
		loop			2
		cel			0
		maskView		vIcons
		maskLoop		14
		maskCel		0
		message		verbDo
		cursor		DO_CURSOR
		signal		(| RELVERIFY HIDEBAR)
		helpStr		"Use this icon to do things."
	)
	(method (init)
		(= lowlightColor colLGreen)
		(super init:)
	)
)

(instance icon3 of IconItem				;talk
	(properties
		view			vIcons
		loop			3
		cel			0
		maskView		vIcons
		maskLoop		14
		maskCel		3
		message		verbTalk
		cursor		TALK_CURSOR
		signal		(| RELVERIFY HIDEBAR)
		helpStr		"Use this icon to talk to other characters."
	)
	(method (init)
		(= lowlightColor colLRed)
		(super init:)
	)
)

(instance icon4 of IconItem				;zipper
	(properties
		view			vIcons
		loop			10
		cel			0
		maskView		vIcons
		maskLoop		14
		maskCel		1
		message		verbZipper
		cursor		ZIP_CURSOR
		signal		(| RELVERIFY HIDEBAR)
		helpStr		"Say! Don't you know what your zipper is for?"
	)
	(method (init)
		(= lowlightColor colLYellow)
		(super init:)
	)
)

(instance icon5 of IconItem				;fast forward
	(properties
		view			vIcons
		loop			11
		cel			0
		maskView		vIcons
		maskLoop		14
		maskCel		0
		message		0
		cursor		ARROW_CURSOR
		signal		(| RELVERIFY HIDEBAR IMMEDIATE)
		helpStr		"This icon lets you \"Fast Forward\"
						to the next interactive portion of the game."
	)
	(method (init)
		(= lowlightColor colCyan)
		(super init:)
	)
	(method (select)
		(if (and ffRoom
					(super select: &rest)
				)
				(theIconBar hide:)
				(if
					(Print "Do you really want to skip ahead?"
						#title:	{Fast Forward}
						#button: {Yes}		1
						#button: {Oops}	0
					)
					(if (== ffRoom SAMEROOM)
					  	(if (IsObject ffCueObj)
					  		(ffCueObj cue:)
							(SetFFRoom NULL)
					  	else
					  		(Print "ERROR: Object passed to SetFFRoom -1 ain't.")
					  	)
					else
						(curRoom newRoom: ffRoom)
						(+= ffRoom ffInProgress)
					)
				)
		else
			(return FALSE)
		)
	)
)

(instance icon6 of IconItem				;use it (inventory window - initially diasabled)
	(properties
		view			vIcons
		loop			4
		cel			0
		maskView		vIcons
		maskLoop		14
		maskCel		4
		message		verbUse
		cursor		ARROW_CURSOR
		signal		(| RELVERIFY HIDEBAR)
		helpStr		"Select this icon to use your current inventory object."
	)
	(method (init)
		(= lowlightColor colLGreen)
		(super init:)
	)
	(method (select relVer &tmp event whichCel cii theX theY)
		(return
			(cond
				((& signal DISABLED)
					FALSE
				)
				((and argc relVer (& signal RELVERIFY))
					(if (= cii (theIconBar curInvIcon?))
						(= theX
							(+
								; add offset
								(/
									; half each side
									(-
										; difference in widths
										(- nsRight nsLeft?)
										(CelWide (cii view?) (+ (cii loop?) 1) (cii cel?))
									)
									2
								)
								nsLeft
							)
						)
						(= theY
							(+
								(theIconBar y?)
								; add offset
								(/
									; half each side
									(-
										; difference in heights
										(- nsBottom nsTop)
										(CelHigh (cii view?) (+ (cii loop?) 1) (cii cel?))
									)
									2
								)
								nsTop
							)
						)
					)
					(DrawCel view loop (= whichCel 1) nsLeft nsTop -1)
					(if (= cii (theIconBar curInvIcon?))
						(DrawCel (cii view?) (+ 1 (cii loop?)) (cii cel?) theX theY -1)
					)
					(Graph GShowBits nsTop nsLeft nsBottom nsRight VMAP)
					(while (!= ((= event	(Event new:)) type?)	mouseUp)
						(event localize:)
						(if (self onMe:event)
							(if (not whichCel)
								(DrawCel view loop (= whichCel 1) nsLeft nsTop -1)
								(if (= cii (theIconBar curInvIcon?))
									(DrawCel (cii view?) (+ 1 (cii loop?)) (cii cel?) theX theY -1)
								)
								(Graph GShowBits nsTop nsLeft nsBottom nsRight VMAP)
							)
						else
							(if whichCel
								(DrawCel view loop (= whichCel 0) nsLeft nsTop -1)
								(if (= cii (theIconBar curInvIcon?))
									(DrawCel (cii view?) (+ 1 (cii loop?)) (cii cel?) theX theY -1)
								)
								(Graph GShowBits nsTop nsLeft nsBottom nsRight VMAP)
							)
						)
						(event dispose:)
					)
					(event dispose:)
					(if (== whichCel 1)
						(DrawCel view loop 0 nsLeft nsTop -1)
						(if (= cii (theIconBar curInvIcon?))
							(DrawCel (cii view?) (+ 1 (cii loop?)) (cii cel?) theX theY -1)
						)
						(Graph GShowBits nsTop nsLeft nsBottom nsRight VMAP)
					)
					whichCel
				)
				(else
					TRUE
				)
			)
		)
	)
)

(instance icon7 of IconItem	;inventory
	(properties
		view			vIcons
		loop			5
		cel			0
		maskView		vIcons
		maskLoop		14
		maskCel		2
		type			0
		message		0
		signal		(| RELVERIFY IMMEDIATE HIDEBAR)
		cursor		ARROW_CURSOR
		helpStr		"Use this icon to bring up your inventory window."
	)
	(method (init)
		(= lowlightColor colYellow)
		(super init:)
	)
	(method (select)
		(return
			(if (super select: &rest)
				(theIconBar hide:)
				(ego showInv:)
			)
		)
	)
)

(instance icon8 of IconItem	;sound volume/game speed
	(properties
		view			vIcons
		loop			7
		cel			0
		maskView		vIcons
		maskLoop		14
		maskCel		1
		message		CONTROL_PANEL
		cursor		ARROW_CURSOR
		signal		(| RELVERIFY IMMEDIATE HIDEBAR)
		helpStr		"This icon brings up the control panel."
	)
	(method (init)
		(= lowlightColor colLMagenta)
		(super init:)
	)
	(method (select)
		(return
			(if (super select: &rest)
				(theIconBar hide:)
				(GameControls show:)
			)
		)
	)
)

(instance icon9 of IconItem	;about/help
	(properties
		view			vIcons
		loop			9
		cel			0
		maskView		vIcons
		maskLoop		14
		maskCel		0
		message		verbHelp
		cursor		HELP_CURSOR
		signal		(| RELVERIFY IMMEDIATE)
		helpStr		"To learn about the other icons, first click here,
						then pass the question mark over the other icons."
	)
	(method (init)
		(= lowlightColor colLRed)
		(super init:)
	)
)


(instance checkIcon of Code
	(method (doit theIcon)
		(if (theIcon isKindOf: IconItem)		; It's an icon
			(if (& (theIcon signal?) DISABLED)
				(|= iconSettings (>> $8000 (theIconBar indexOf: theIcon)))
			)
		)
	)
)


;ษอออออออออออออออออออป
;บ                   บ
;บ Dft doVerb code   บ
;บ                   บ
;ศอออออออออออออออออออผ

(instance ll5DoVerbCode of Code
	(method (doit theVerb theObj &tmp objDesc theItem [str 100])
		(= objDesc (theObj description?))
		(switch theVerb
			(verbWalk
				; We do this because features claim walk events even if they
				;	don't use them.
				((User curEvent?) claimed: FALSE)
	 		)
			(verbLook
				(if (theObj lookStr?)
					(TPrint (theObj lookStr?))
				else
					(Format @str "Why, look! It's %s." objDesc)
					(TPrint @str)
				)
			)
			(verbTalk
				(Format @str "You engage in a short, but personally meaningful,
					conversation with %s." objDesc)
				(TPrint @str)
			)
			(verbDo
				(Format @str "That feels exactly like %s." objDesc)
				(TPrint @str)
			)
			(verbUse
				(= theItem ((theIconBar curInvIcon?) description?))
				(Format @str "It seems %s just doesn't work with %s."
					theItem
					objDesc
				)
				(TPrint @str)
			)
			(verbZipper
				(Format @str "Don't do that to %s!" objDesc)
				(TPrint @str)
			)
			(else
				(NoResponse theObj theVerb)
			)
		)
	)
)


;ษอออออออออออออออออออออออออป
;บ                         บ
;บ Dft feature initializer บ
;บ                         บ
;ศอออออออออออออออออออออออออผ

(instance ll5FtrInit of Code				; sets up defaults
	(method (doit theObj)
		; angle used by facingMe
		(if (== (theObj sightAngle?) ftrDefault)
			(theObj sightAngle: 90)
		)
		; instance of Action or EventHandler with Actions
		(if (== (theObj actions?) ftrDefault)
			(theObj actions: 0)
		)
		; set approachX & approachY if not already set
		(if (and	(not (theObj approachX?))
					(not (theObj approachY?))
				)
			(theObj
				approachX:	(theObj x?),
				approachY:	(theObj y?)
			)
		)
	)
)


;ษอออออออออออออออออออป
;บ                   บ
;บ System window     บ
;บ                   บ
;ศอออออออออออออออออออผ

(instance ll5Win of SysWindow)	;BorderWindow)



;ษอออออออออออออออออออออป
;บ                     บ
;บ GameControls window บ
;บ                     บ
;ศอออออออออออออออออออออผ

; the 1st set are all used as property values, so they
; can't use stuff such as CelHigh, etc.

(define	GAP			3)
(define	SIDEGAP		4)
(define	BIGBOXTOP	GAP)
(define	BIGBOXLEFT	SIDEGAP)
(define	BUT1LEFT		(+ BIGBOXLEFT 4))
(define	BUT2LEFT		(+ BIGBOXLEFT 30))
(define	SAVETOP		(+ BIGBOXTOP 3))
(define	RESTOTOP		(+ BIGBOXTOP 23))
(define	RESTATOP		(+ BIGBOXTOP 43))
(define	QUITTOP		(+ BIGBOXTOP 63))
(define	ABOUTTOP		(+ BIGBOXTOP 83))
(define	PLAYTOP		(+ BIGBOXTOP 103))
(define	SLIDERTOP	37);;15)
(define	DETAILLEFT	67)
(define	SLIDDIST		40)
(define	VOLUMELEFT	(+ DETAILLEFT SLIDDIST))
(define	SPEEDLEFT	(+ VOLUMELEFT SLIDDIST))
(define	TEXTLEFT		(+ SPEEDLEFT  SLIDDIST))

; these guys are only used for kernel calls

(define	TITLETOP		(* GAP 2))
(define	TITLEWIDE	(CelWide vGameControls 0 6))
(define	BIGBOXHIGH	(CelHigh	vGameControls 1 1))
(define	BIGBOXWIDE	(CelWide	vGameControls 1 1))
(define	ICONHIGH		(CelHigh vGameControls 0 4))
(define	SLIDWIDE		(CelWide vGameControls 0 1))
(define	SLIDHIGH		(CelHigh vGameControls 0 1))
(define	WINWIDE		(+ DETAILLEFT SLIDWIDE (* SLIDDIST 3) SIDEGAP))
(define	WINHIGH		(+ BIGBOXHIGH (* GAP 2)));;(+ TITLEHIGH BIGBOXHIGH (* GAP 5)))
(define	WINTOP		(/ (- 200 WINHIGH) 2))
(define	WINLEFT		(/ (- 320 WINWIDE) 2))
(define	WINBOTTOM	(+ WINHIGH WINTOP))
(define	WINRIGHT		(+ WINWIDE WINLEFT))
(define	TITLELEFT
	(+ (/ (- (- WINWIDE (+ BIGBOXLEFT BIGBOXWIDE)) TITLEWIDE) 2) (+ BIGBOXLEFT BIGBOXWIDE))
)
(define	ARROWTOP		(+ SLIDERTOP 1))
(define	ARROW1LEFT	(+ DETAILLEFT 27))
(define	ARROW2LEFT	(+ VOLUMELEFT 28))
(define	ARROW3LEFT	(+ SPEEDLEFT 28))
(define	SLIDTITTOP	(- SLIDERTOP (+ ICONHIGH GAP)))
(define	DETAILTLEFT	(- DETAILLEFT 4))
(define	VOLUMETLEFT	(- VOLUMELEFT 6))
(define	SPEEDTLEFT	(- SPEEDLEFT 1))
(define	TEXTTLEFT	(- TEXTLEFT 1))

; for score 'window'
(define	SWINHIGH		13);;30)
(define	SWINLEFT		(+ 6 BIGBOXLEFT BIGBOXWIDE))
(define	SWINWIDE		(- WINWIDE (+ SWINLEFT SIDEGAP 2)))
(define	SWINTOP		(+ 9 SLIDERTOP SLIDHIGH))
(define	TEXTTOP		(+ SWINTOP 3))
(define	THEFONT		999)

(instance gcWin of SysWindow		;BorderWindow
	(method (open
					&tmp savePort theBevelWid t l r b theColor theMaps
					topColor bottomColor rightColor leftColor	thePri i
					[str 15]	[rectPt 4]
			  )
			(= thePri -1)
		(self
			top:			WINTOP,
			left:			WINLEFT,
			bottom:		WINBOTTOM,
			right:		WINRIGHT,
			priority:	thePri,
		)
		(super open:)

		;*******************************
		; title
		(DrawCel vGameControls 0 6 TITLELEFT TITLETOP thePri)
		; big box around buttons
		(DrawCel vGameControls 1 1 BIGBOXLEFT BIGBOXTOP thePri)
		; 1st arrow between sliders
		(DrawCel vGameControls 1 0 ARROW1LEFT ARROWTOP thePri)
		; 2nd arrow between sliders
		(DrawCel vGameControls 1 0 ARROW2LEFT ARROWTOP thePri)
		; 3rd arrow between sliders
		(DrawCel vGameControls 1 0 ARROW3LEFT ARROWTOP thePri)
		; icon over detail
		(DrawCel vGameControls 0 4 DETAILTLEFT SLIDTITTOP thePri)
		; icon over volume
		(DrawCel vGameControls 0 3 VOLUMETLEFT SLIDTITTOP thePri)
		; icon over speed
		(DrawCel vGameControls 0 2 SPEEDTLEFT SLIDTITTOP thePri)
		; icon over text
		(DrawCel vGameControls 0 5 TEXTTLEFT SLIDTITTOP thePri)

		;*******************************
		; a window below the sliders for version & score
		(= t SWINTOP)
		(= b (+ t SWINHIGH))
		(= l SWINLEFT)
		(= r (+ l SWINWIDE))
		(= theColor		0)
		(= theMaps VMAP)

		(Graph GFillRect t l (+ b 1) (+ r 1) theMaps theColor thePri)
		(Graph GShowBits  t l (+ b 1) (+ r 1) VMAP)

		; print score centered in score window
		(Format @str "Score: %d of %d" score possibleScore)
		(TextSize @rectPt @str THEFONT 0)
		(Display @str
			#p_font:		THEFONT
			#p_color:	colGray5
			#p_at:		(+ SWINLEFT (/ (- SWINWIDE [rectPt 3]) 2)) TEXTTOP
		)
	)
)


;ษอออออออออออออออออออป
;บ                   บ
;บ GameControl items บ
;บ                   บ
;ศอออออออออออออออออออผ

(instance detailSlider	of Slider
	(properties
		view			vGameControls
		loop			0
		cel 			1
		nsLeft		DETAILLEFT
		nsTop			SLIDERTOP
		sliderView	vGameControls
		sliderLoop	0
		sliderCel	0
		signal		FIXED_POSN
		helpStr		"Raise this to increase the amount of background animation.
						Lower it if game play seems sluggish."
		topValue		5
		bottomValue	1
	)
	(method (doit num)
		(if argc
			(theGame detailLevel: num)
		)
		(return (theGame detailLevel?))
	)
)

(instance volumeSlider	of Slider
	(properties
		view			vGameControls
		loop			0
		cel 			1
		nsLeft		VOLUMELEFT
		nsTop			SLIDERTOP
		sliderView	vGameControls
		sliderLoop	0
		sliderCel	0
		signal		FIXED_POSN
		helpStr		"This adjusts the volume on some sound boards and
						synthesizers."
		topValue		15
		bottomValue	0
	)
)

(instance speedSlider of Slider
	 (properties
		view			vGameControls
		loop			0
		cel 			1
		nsLeft		SPEEDLEFT
		nsTop			SLIDERTOP
		sliderView	vGameControls
		sliderLoop	0
		sliderCel	0
		signal		FIXED_POSN
		helpStr		"This adjusts Larry and Patti's speed,
						within the limits of your computer's capabilities."
		topValue		0
		bottomValue	15
	)
	(method (show)
		(if (not (User controls?))
			(= signal (| FIXED_POSN DISABLED))
			(= sliderLoop	9)
		else
			(= sliderLoop	0)
			(= signal FIXED_POSN)
		)
		(super show: &rest)
	)
	(method (move)
		(if (User controls?)
			(super move: &rest)
		)
	)
	(method (mask)
	)
)

(instance textSlider of Slider
	 (properties
		view			vGameControls
		loop			0
		cel 			1
		nsLeft		TEXTLEFT
		nsTop			SLIDERTOP
		sliderView	vGameControls
		sliderLoop	0
		sliderCel	0
		signal		FIXED_POSN
		helpStr		"Lower this slide to make text remain on the screen longer.
						Raise it if you are a fast reader."
		topValue		1
		bottomValue	24
	)
	(method (doit num)
		(if argc
			(= textSpeed num)
		)
		(return textSpeed)
	)
)

(instance iconSave of ControlIcon
	(properties
		view			vGameControls
		loop			2
		cel			0
		nsLeft		BUT1LEFT
		nsTop			SAVETOP
		message		ABOUT_HELP
		signal		(| RELVERIFY IMMEDIATE FIXED_POSN VICON HIDEBAR)
		helpStr		"Use this to save the current state of your game.
						When you later select Restore,
						everything will be exactly as it is now."
	)
)

(instance iconRestore of ControlIcon
	(properties
		view			vGameControls
		loop			3
		cel			0
		nsLeft		BUT1LEFT
		nsTop			RESTOTOP
		message		ABOUT_HELP
		signal		(| RELVERIFY IMMEDIATE FIXED_POSN VICON HIDEBAR)
		helpStr		"This restores a game you saved earlier."
	)
	(method (select)
		(if (< curRoomNum 160)
			(curRoom newRoom: PASSWORD)
		else
			(super select: &rest)
		)
	)
)

(instance iconRestart of ControlIcon
	(properties
		view			vGameControls
		loop			4
		cel			0
		nsLeft		BUT1LEFT
		nsTop			RESTATOP
		message		ABOUT_HELP
		signal		(| RELVERIFY IMMEDIATE FIXED_POSN VICON HIDEBAR)
		helpStr		"Use this to restart the game from the very beginning."
	)
)

(instance iconQuit of ControlIcon
	(properties
		view			vGameControls
		loop			5
		cel			0
		nsLeft		BUT1LEFT
		nsTop			QUITTOP
		message		ABOUT_HELP
		signal		(| RELVERIFY IMMEDIATE FIXED_POSN VICON HIDEBAR)
		helpStr		"Use this to leave the game."
	)
)

(instance iconAbout of ControlIcon		; halfdome
	(properties
		view			vGameControls
		loop			6
		cel			0
		nsLeft		BUT1LEFT
		nsTop			ABOUTTOP
		message		ABOUT_HELP
		signal		(| RELVERIFY IMMEDIATE FIXED_POSN VICON HIDEBAR)
		helpStr		"Here's where you learn more than you care to know
						about the creators of this game."
	)
)

(instance iconHelp of IconItem			;
	(properties
		view			vGameControls
		loop			7
		cel			0
		nsLeft		BUT2LEFT
		nsTop			ABOUTTOP
		signal		(| RELVERIFY IMMEDIATE FIXED_POSN VICON)
		type			userEvent
		message		verbHelp
		cursor		HELP_CURSOR
		helpStr		"To learn about the other items in this window,
						first click here, then pass the question mark
						over the other items."
	)
)

(instance iconOk	of IconItem				; actually sez 'Play'
	(properties
		view			vGameControls
		loop			8
		cel			0
		nsLeft		BUT1LEFT
		nsTop			PLAYTOP
		message		ABOUT_HELP
		cursor		HELP_CURSOR
		signal		(| RELVERIFY IMMEDIATE FIXED_POSN VICON HIDEBAR)
		helpStr		"Use this to exit this menu and resume game play."
	)
)
