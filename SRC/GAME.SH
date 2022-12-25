;**
;**	game.sh -- Global Header
;**
;**	LSL5:  Passionate Patti Does A Little Undercover Work
;**				Copyright 1991
;**				by Al Lowe
;**
;**	Last Update:	September 11, 1991
;**


(include graphics.sh)
(include points.sh)
(include sound.sh)


; Some general defines
(define 	FORWARD 			1)
(define 	BACKWARD 		-1)
(define	RELEASE			-1)
(define	SAMEVIEW			-1)
(define	SAMEROOM			1000)
(define	LASTCEL			255)
(define	SECONDS			0)				;- for Delay procedure
(define	CYCLES			1)				;/
(define	REALSECONDS		2)				;/
(define	DONE 				-1)

;music stuff

(define  DONT_STOP_MUSIC		0)
(define  STOP_MUSIC				1)
(define  ONCE 						1)
(define	FOREVER				  -1)


; userEvent
(enum 100
	DRAW_ICON_BAR
)

(enum
	walkIcon
	lookIcon
	doIcon
	talkIcon
	zipperIcon
	ffIcon
	useIcon
	inventoryIcon
	computerIcon
	aboutIcon
)
(define	verbZipper			10)

;Membership states
(enum
	tNone
	tLarry
	tSuper
	tHyatt
	tDone
)

;Videotape states
(enum
	tapeBlank
	tapeDegaussed
	tapeRecordedMM
	tapeRecordedLL
	tapeRecordedCC
)

;Dataman states
(enum
	dmEmpty
	dmPak1
	dmPak2
)

;Door states
(enum
	doorClosed
	doorOpening
	doorOpen
	doorClosing
	doorWaitToClose
	doorWaitToOpen
)

;Door enter (->) and exit (<-) types (from "game.sh")
(enum
	doorWalkEgo		; ->: 	ego will walk in through the door
						; <-:		ego will walk out the door to new room
	doorPlaceEgo	; ->: 	ego will appear in front of the door
						; <-:		ego will walk out the door & door will close
	doorLeaveEgo	; both: 	the door does not affect ego
)

(define	egoAction		userEvent)
(define	WALK				travelIcon)
(define	LOOK_AT			lookIcon)
(define	DO_IT				doIcon)
(define	USE_IT			useIcon)
(define	TALK_TO			talkIcon)
(define	SAVE_RESTORE	computerIcon)
(define	STOP				computerIcon)
(define	CONTROL_PANEL	computerIcon)
(define	ABOUT_HELP		aboutIcon)

; Game specific cursor IDs
(define	TRAVEL_CURSOR		0)
(define	EYE_CURSOR			1)
(define	DO_CURSOR			2)
(define	TALK_CURSOR			3)
(define	ZIP_CURSOR			5)
(define	EXIT_CURSOR			6)
(define	HELP_CURSOR			9)
(define	BLANK_CURSOR		69)

; ******* Regions & Locales *****************************************
(define	HOLLYWOODRGN	040)
(define	INTRO				100 110 120 130 140 150)
(define	PASSWORD			155)
(define  HOLLYWOOD		160 170 180 190)
(define	LIMO				200)
(define	LIMODREAMS		205)
(define	AIRPORT			250 258 260 270 280 290 295)
(define	AIRPLANE			310 320 385 390)
(define	FBI				400 405 410 415 420 425 430 440 450)
(define	WASHINGTONDC	460 480)
(define	NEWYORK			500 510 520 525 530 535)
(define	BALTIMORE		600 610 620 640 660 680)
(define	ATLANTICCITY	700 710 720 730 740 750 760 780 790)
(define	PHILIDELPHIA	800 820 840 850 860 870 880 890)
(define	MIAMI				900 905 910 915 920)

(define	NODEBUGROOMS	200 460)
(define	NO_SAMPLE_ROOMS	NODEBUGROOMS 700 760 660 390 535 320 )

(define	LARRYROOMS		
	HOLLYWOOD INTRO PASSWORD
	AIRPORT AIRPLANE LIMODREAMS
	NEWYORK ATLANTICCITY MIAMI
)
(define	PATTIROOMS
	FBI BALTIMORE PHILIDELPHIA WASHINGTONDC
)

; ******* pseudo system stuff ***************************************
(define	DEBUG			010)
(define	DISP			011)
(define	COLOR			012)
(define	ABOUT			015)
(define	INIT			016)
(define	LLROOM		018)
(define	INVENTORY	019)
(define	TTDIALER		020)
(define	DOOR			021)
(define	CHARGER		022)
(define	LARRY			023)
(define	PATTI			024)

; LARRY Inventory objects
; this enum MUST be in same order as inventory add: in INVENTRY.SC!
(enum
	iCamcorder				; Pocket Protector camcorder
	iBatteryCharger		; Battery charger for camcorder
	iVideotape1				; Michelle Milken's blank/recorded tape
	iVideotape2				; Lana Luscious's blank/recorded tape
	iVideotape3		 		; Chi Chi Lambada's blank/recorded tape
	iMM'sResume				; Michelle Milken's resume
	iHDCNapkin				; Napkin from Hard Disk Cafe
	iGoldCard				; Aerodork gold card
	iBoardingPass			; Aerodork boarding pass
	iMagazine				; Aerodork in-flight magazine
	iQuarter					; Quarters for phone
	iDayTrotter				; Organizer (?)
	iMoney					; Money
	iCreditCards			; Credit card collection found in DayTrotter
	iMembershipTape		; Membership paper tape
	iLL'sResume				; Lana Luscious's resume
	iCasinoMatchbook		; Tramp casino matchbook
	iSilverDollar			; Silver dollars from Tramp casino
	iRollerblades			; Rented on Tramp boardwalk
	iCC'sResume		 		; Chi Chi Lambada's resume
	iDocPulliam'sCard		; Doc Pulliam's business card
	iGreenCard				; Green card from Miami airport
	iDoily					; Doily found in dentist waiting room
)

; PATTI Inventory objects
; this enum MUST be in same order as inventory add: in INVENTRY.SC!
(enum
	iDataMan					; Electronic notepad (?)
	iDataPak1Biaz			; Reverse Biaz data cartridge for Data Man
	iDataPak2Hammer		; P.C. Hammer data cartridge for Data Man
	iFax1ReverseBiaz		; Info about Reverse Biaz
	iChampagne				; Bottle of champagne
	iGoldRecord				; Incriminating evidence #1 from studio
	iCassetteTape			; Incriminating evidence #2 from control room
	iFax2PCHammer			; Info about P.C. Hammer
	iLetterOpener			; Letter opener found in K-RAP office
	iDeskKey					; Desk key found in K-RAP office
	iFolderOfEvidence		; Incriminating evidence #3 from K-RAP office
	iPhotocopies			; Incriminating evidence #4 from K-RAP office
	iReelToReelTape		; Blank/recorded reel-to-reel tape
	iBra						; 30-cal armor-piercing brasiere
)

(define	HIGH	#at: -1  28)
(define	LOW	#at: -1 185)
(define	BIG	#at: -1  15 #width: 280)
(define	WIDE	#at: 15  -1 #width: 280)
(define	WIDELOW	#at: -1	185 #width: 280)

(enum												;**	Standard loops
	facingRight
	facingLeft
	facingDown
	facingUp

	facingSouthEast
	facingSouthWest
	facingNorthEast
	facingNorthWest
)

(extern
	StillDown		255 5
	EgoIs				0	1
	HandsOff			0 	2
	HandsOn			0 	3
	HaveMem			0 	4
	StepOn			0 	5
	Btst				0 	6
	Bset				0 	7
	Bclr				0 	8
	RecordTape		0 	9
	Points			0 	10
	Face				0 	11
	NoResponse		0 	12
	SetFFRoom		0 	13
	TPrint			0 	14
	WalkTo			0 	15
	RestoreIB		0	16
	Delay				0	17
	Say				0	18
	SetupExit		0	22
	SaveCurIcon		0	23
	LoadCurIcon		0	24
	CenterDisplay	0	25
	ColorInit		COLOR 0
	aboutCode		ABOUT 0
)

(global
	sGrooper				100
	gameCode				101 = 1234	; Larry made me do this!
	theMusic				102
	theMusic2			103
	cursorType			104
	numColors			105	; number of colors available
	numVoices			106
	restartRoom			107 = 100
	skateAbility		108	; how well Larry can skate
	saveCursorX			109	; -position of cursor when HandsOff is fired
	saveCursorY			110	; /
	debugging			111	; Al's personal favorite
	versionIntPhone	112	; International number read from version file
	tapesDegaussed		113	; # of tapes degaussed
;	nextVar				114
;	nextVar				115
;	nextVar				116
;	nextVar				117
;	nextVar				118
;	nextVar				119
;	nextVar				120
;	nextVar				121
	colBlack				122	; some colors we want
	colGray1				123
	colGray2				124
	colGray3				125
	colGray4				126
	colGray5				127
	colWhite				128
	colDRed				129
	colLRed				130
	colVLRed				131
	colDYellow			132
	colYellow			133
	colLYellow			134
	colVDGreen			135
	colDGreen			136
	colLGreen			137
	colVLGreen			138
	colDBlue				139
	colBlue				140
	colLBlue				141
	colVLBlue			142
	colMagenta			143
	colLMagenta			144
	colCyan				145
	colLCyan				146
	colWindow			147

	camcorderCharge	150	; % of battery power left in camcorder
	ffRoom				151	; room number to go to if fast-forward is hit
	larryDollars		152	; how many bucks we have
	silvDollars			153	; how many SDs we have
	TPed					154	; set to last room Teleported to
	textSpeed			155	; current speed of message time
	REMState				156	; Larry's dream
	(enum
		Athens
		Venice
		TajMahal
		Casablanca
	)
	pattiDest			157	; Patti's destination
	(enum
		atFBI
		toBalt
		toPhilly
		toDC
	)
	ffCueObj				158	; who the fast-forward will cue if ffRoom is -1
	boardWalkDist		159	; number of "screens" away from casino
	larryLoc				160	; where Larry is at any given time
	(enum
		LosAngeles
		NewYork
		AtlanticCity
		Miami
		Gumbo
	)
	pattiLoc				161	; where Patti is at any given time
	(enum
		Washington
		Baltimore
		Philly
	)
	pattiDream			162	; which dream is next for Patti
	(enum 1
		Tramp
		Gates
		McDuck
	)
	pokerJackpot		163	; progressive jackpot won by royal flush
	blondeX  			164
	blondeLoop   		165
	redHeadX 			166
	redHeadLoop			167
	quarters				168	; # quarters Larry has found & used (-1 if done)
	iconSettings		169	; bit-mask - each icon enabled or disabled
	theTimer				170	; pointer to global timer
	roomBNumber			171	; keypad number for control room B in Pillie.
	meanWhiles			172	; larry meanwhile... in limo
	monoFont				173	; mono-spaced font (typing)
	giantFont			174
	niceFont				175
	destination			176	; used in 200 and 205
	airplaneSeat		177	; seat # for boarding pass
	dentistState		178	; what r905's receptionist remembers about Larry
	egoIsLarry			179	; TRUE if ego is Larry, FALSE if ego is Patti
	talkersOnScreen  	180	; Kludge! Number of talkers on screen
	versionDate			181	; version date in format mm/dd/yy
	versionPhone		182	; version phone in format 999-999-9999
	chargeTimer			183	; pointer to global camcorder charge timer
	saveCharge			184	; saves the camcorder charge over room change
	theCurIcon			185	; used by Save/LoadCurIcon procedures
	gameFlags			186	; 64 bit flags start here...
	pointFlags			190	; ...128 point flags start here...
	endGameFlags		198	; ...and all flags end here, giving us 184 flags

	(enum							;	GLOBAL FLAGS
		isVga						;
		fCalledLimo				;	Larry has called for the limo from HDC
		fSeenMM					;	Larry has seen Michelle go into Hollerith room
		fSeenLL					;	Larry has met Lana on the boardwalk
		fSeenCC					;	Larry has met Chi Chi
		fMudWrestled			;	Larry has mud wrestled with Lana
		fSkated					;	Larry left the skate shop & skated at least once
		fBeenIn150				;	Larry has done the coffee bit
		fBeenToTown				;	Determines if Larry is arriving or departing
		fBeenInNewYork			;	Larry's already been to NY
		fBeenInAtlanticCity	;	Larry's already been to AC
		fBeenInMiami			; 	Larry's already been to Miami
		fCalledGreenCard		;  Called greenCard co.
		fGotQuarterNY        ;
		fUsedTape            ;  Used tape in 510
		fMMadeTape           ;  Maitre'd made tape

		fMCloseUp				;	Michelle's closeup in 530
		fSeenRBOffice			;	Patti has seen Reverse Biaz's office number
		fCalledDoc				;	Called doc
		fBeenIn660				;	Patti has been here before, don't give initial messages
		fChampagneSolution	;	670
		fWarned					;	Reverse warned Patti not to leave 640
		fRecordOnTurntable			;	Record is on turntable in 640
		fSeenGlint				;	noticed key in plant pot, 840
		fDrawerOpen				;	desk draw in rm 840 is open
		fHasNumber				;	Has seen keypad # for studio B
		fNotFirstTimeInKRAP	;	So that we only show the picture of the K-Rap building once
		fPattiBlackface		;	after copier blows up, rm 840
		fBeenInPhilly			;  K.R.A.P.
		fBeenInBaltimore		;  shill
		fTookBottle				;  patti took bottle out of limo
		fTookDayTrotter		;  larry took dayTrotter out of limo

		fOkToBoard				;  needed in room 290
		fUsedDoily				;  wore doily to get into back of dentist's office
		fTookQuarters260		;  took quarters out of cigarette machine
		fWearingBra				;	Patti is wearing the bra
		fPulledSlots			;  Used slots in 260
		fCasinoSide				; 	which door larry exited, TRUE left FALSE right
		fLimoParked				;  parked and you can get back inside
		fWorkedOutWithChiChi	;  worked out with chi chi in 920
		fCherryGone				;  did michelle lick off her cherry before blowjob?
		fVibratorMan			;	vibrator man did his thing, rm430
		fBraMan					;  bra man did his thing. rm430
		fFartMan					;  fart man did his thing, rm440
		fHasFBINumber			;	twit signed her up in FBI lab (rm440)
		fGuardKnows				;  Guard now knows that RB got someone else
		fPrintedPass			;	Printed boarding pass - it's hanging in ATM
		fFFto480					;  FFed from 460 to 480

		fDirectorySol			;  Used directory to get past Guard
		fDidLana					;	mudwrestling is over
		fBrokeSlots				;	slot machine in Alantic City airport
		fScoredCC 				;  Gave Green Card to CC OR talked enough (rm915 & rm910)
		fDumpedMagazine		;  Dumped magazine in 905
		fDegaussedTape			;	Larry has deguassed at least one tape
		fNextFlag				;	END OF FLAGS  (presently can't be more than 64)
	)
	;**	Note:  points flags are specified in POINTS.SH, running from 64-160

	lastVar				199
)

; ******* Pseudo-procedures with few or no arguments *******************

(define Cls if modelessDialog (modelessDialog dispose:))

; Returns detailLevel so coders don't have to type so much stuff
(define Detail theGame detailLevel?)

; Use in scripts
(define	delay	= seconds)

; For fast-forward icon
(define	ffInProgress			1000)

; Charger & camcorder defines
(define	chargerPlugged
	(== ((Inventory at: iBatteryCharger) owner?) curRoom)
)
(define	camcorderCharging
	(== ((Inventory at: iCamcorder) owner?) (Inventory at: iBatteryCharger))
)
(define	camcorderOn		(>= ((Inventory at: iCamcorder) state?) 100))
(define	PlugCharger		curRoom setScript: (ScriptID CHARGER 0))
(define	UnplugCharger 	curRoom setScript: (ScriptID CHARGER 1))
(define	PlugFinger		curRoom setScript: (ScriptID CHARGER 2) NULL TRUE)
(define	theCharger		(ScriptID CHARGER 3))


; Keypad defines
(define	DialPhone		(ScriptID TTDIALER 0) init: TRUE)
(define	UseKeypad		(ScriptID TTDIALER 0) init: FALSE)

; Timer defines - pass time value, as in (WaitSeconds 5)
(define	WaitSeconds		(Timer new:) setReal: self)
(define	WaitCycles		(Timer new:) setCycle: self)

