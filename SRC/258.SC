;**
;**	Logics for Room 258  ATM CloseUp
;**
;**	LSL5:  Passionate Patti Does A Little Undercover Work
;**				Copyright 1991
;**				by Al Lowe
;**
;**	Programmed by Brian K. Hughes
;**
;**	Last Update:  September 10, 1991
;**

(script#		258)

(procedure
	FormatTime
)

(public
	rm258		0
)


(define	pATMCloseUp						258)
(define	vATMCloseUp					258)
(define		lCard					0)
(define		lBoardingPass		1)

(define	HUNG_UP			-1)
(define	CHEATED			-2)

(define	sTicket				257)
(define	sCardSlot			258)
(define	sPrintTicket		259)	;dump?
(define	sGetTape				512)


(local
	cueCounter
	wrongNumber
	weCheated
	warnCounter

	cpCode	= [ 0 0 0 0 ]
	cpTime	= [ 0 0 0 0 ]
	dest		= [ 0 0 0 0 ]
	theBar
	[departTime	10]
	cityToState = [ 9 0 3 9 2 1 9 9 ]	; boarding pass state values/city enum

	NYcopyProtCode =
	[
		900	16053
		1010	12415
		1120	34023
		1240	36528
		130	63639
		220	14065
		340	59592
		440	55788
	]
	ACcopyProtCode =
	[
		920	43895
		1030	58188
		1150	23802
		1200	62165
		100	49702
		210	63305
		310	26084
		410	43051
	]
	McopyProtCode =
	[
		950	18734
		1050	56308
		1140	40162
		1230	36998
		120	37687
		240	31525
		330	62572
		430	29519
	]
	LAcopyProtCode =
	[
		930	58212
		1040	46710
		1110	12320
		1250	40616
		140	57664
		230	37669
		320	40088
		400	45095
	]
)

(enum
	I_PA
	LA_CA
	M_FL
	C_MI
	AC_NJ
	NY_NY
	S_WA
	O_CA
)


(instance rm258 of LLRoom
	(properties
		picture		pATMCloseUp
	)

	(method (init)
		(super init:)
		(cond
			((Btst fPrintedPass)
				(card y: 97)
				(boardPass init:)
				(SetupExit TRUE)
				(HandsOn)
			)
			((and	(Btst fBeenToTown)
					(not (ego has: iBoardingPass))
				)
				(SetupExit TRUE)
				(slot init:)
				(dispenser init:)
				(if (not (ego has: iGoldCard))
					(slot doVerb: verbUse iGoldCard)
				)
			)
			(else
				(HandsOff)
				(Display
					{Out of boarding passes at this time.
					Please try again in another city!}
					#p_at:    144 75
					#p_color: colWhite
					#p_width: 110
					#p_font:  smallFont
				)
				(if (not (ego has: iGoldCard))
					(ego get: iGoldCard)
				)
				(Delay 15 REALSECONDS curRoom)
			)
		)
	)
	(method (notify theNumber &tmp cPCode theHour theMins i)
		(switch theNumber
			(HUNG_UP
				(card init:)
			)
			(CHEATED
				(= weCheated TRUE)
				(boardPass init:)
			)
			([cpCode theBar]
				(boardPass init:)
			)
			(else
				(= wrongNumber TRUE)
				(curRoom drawPic: (curRoom picture?))
				(Display
					{You have entered an incorrect destination for this
						airport at this hour.  But we still appreciate your
						selection of AeroDork Airlines.}
					#p_at:    144 75
					#p_color: colWhite
					#p_width: 110
					#p_font:  smallFont
				)
				; Any event will clear this & send out the card
			)
		)
	)
	(method (handleEvent event)
		(if wrongNumber
			(= wrongNumber FALSE)
			(event claimed: TRUE)
			(card init:)
		else
			(super handleEvent: event)
		)
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbLook
				(TPrint "You are standing before one of AeroDork Airline's
					amazing new Automatic Ticket Machines.
					Just insert your exclusive AeroDork Gold Club membership
					card into the slot on the left.")
			)
			(verbUse
				(if (== invItem iGoldCard)
					(TPrint "Insert your exclusive AeroDork Gold Club membership
						card into the slot on the left.")
				else
					(super doVerb: theVerb invItem &rest)
				)
			)
			(verbWalk
				(if (== ((theIconBar at: walkIcon) cursor?) EXIT_CURSOR)
					(cond
						((or	(Btst fPrintedPass)
								(ego has: iGoldCard)
							)
							(curRoom newRoom: 250)
						)
						((cast contains: card)
							(curRoom drawPic: (curRoom picture?))
							(switch (++ warnCounter)
								(1
									(Display
										{Hey!  Take your AeroDork gold card!}
										#p_at:    144 75
										#p_color: colWhite
										#p_width: 110
										#p_font:  smallFont
									)
								)
								(2
									(Display
										{Look, you twit.  If you wander off and leave
										that card here I'll see to it that your luggage
										goes non-stop to Muncie, Indiana!}
										#p_at:    144 75
										#p_color: colWhite
										#p_width: 110
										#p_font:  smallFont
									)
								)
								(else
									(Display
										{Fine.  I'm ignoring you.}
										#p_at:    144 75
										#p_color: colWhite
										#p_width: 110
										#p_font:  smallFont
									)
								)
							)
						)
						(else
							(bar1 dispose:)
							(bar2 dispose:)
							(bar3 dispose:)
							(card init:)
						)
					)
				else
					(return FALSE)
				)
			)
			(else
				(super doVerb: theVerb &rest)
			)
		)
	)
	(method (cue)
		; We only get here from delays
		(self newRoom: 250)
	)
	(method (newRoom)
		(if (cast contains: card)
			(TPrint "Hey! Don't forget your gold card!")
		else
	 		(SetupExit FALSE)
;	 		(theIconBar
;	 			curInvIcon:	(Inventory at: iGoldCard),
;	 			curIcon:
;					((theIconBar useIconItem?)
;						cursor: ((Inventory at: iGoldCard) cursor?),
;						yourself
;					)
;	 		)
;	 		(theGame setCursor: ((theIconBar curInvIcon?) cursor?))
	 		(super newRoom: &rest)
		)
	)
)


;ษอออออออออออออออออออป
;บ                   บ
;บ Procedures        บ
;บ                   บ
;ศอออออออออออออออออออผ

(procedure (FormatTime which theCity &tmp i theTime [str 10])
	(= i (* (Random 0 7) 2))
	(switch theCity
		(LA_CA
			(= [cpTime which] [LAcopyProtCode i])
			(= [cpCode which] [LAcopyProtCode (+ i 1)])
		)
		(M_FL
			(= [cpTime which] [McopyProtCode i])
			(= [cpCode which] [McopyProtCode (+ i 1)])
		)
		(AC_NJ
			(= [cpTime which] [ACcopyProtCode i])
			(= [cpCode which] [ACcopyProtCode (+ i 1)])
		)
		(NY_NY
			(= [cpTime which] [NYcopyProtCode i])
			(= [cpCode which] [NYcopyProtCode (+ i 1)])
		)
	)
	(if (or	(< [cpTime which] 900)
				(>= [cpTime which] 1200)
			)
		(StrCpy @str {PM})
	else
		(StrCpy @str {AM})
	)
	(Format @departTime "%2d:%02d %s"
		(/ [cpTime which] 100)
		(mod [cpTime which] 100)
		@str
	)
)


;ษอออออออออออออออออออป
;บ                   บ
;บ Objects           บ
;บ                   บ
;ศอออออออออออออออออออผ

(instance boardPass of Actor
	(properties
		view			vATMCloseUp
		loop			lBoardingPass
		x				164
		y				158
		description	"the boarding pass"
	)
	(method (init)
		(super init:)
		(HandsOff)
		(curRoom drawPic: (curRoom picture?) PLAIN)
		(if weCheated
			(Display
				{Oh, it's you, Mr. Hughes!
				Of course you may have a boarding pass!
				Right away, Sir!}
				#p_at:    144 75
				#p_color: colWhite
				#p_width: 110
				#p_font:  smallFont
			)
		else
			(Display
				{Please take your boarding pass now.}
				#p_at:    144 75
				#p_color: colWhite
				#p_width: 110
				#p_font:  smallFont
			)
		)
		(if (not (Bset fPrintedPass))	; set returns old value
			(theMusic2
				number:	sTicket,
				setLoop:	0,
				play:
			)
			(self
				setPri:		3,
				setMotion: 	MoveTo x 178 self
			)
		else
			(self
				posn:		164 178,
				setCel: 	LASTCEL,
				setPri:	13
			)
		)
	)
	(method (cue)
		(theMusic2 stop:)
		(if (not cel)
			(self
				setPri:		RELEASE,
				setCycle: 	EndLoop self
			)
		else
		  	((Inventory at: iBoardingPass)
				state: [cityToState [dest theBar]]
			)
			; Generate a random seat, 2A through 10F
			(= airplaneSeat (+ (* (Random 2 10) 100) (Random 65 70)))
			(HandsOn)
		)
	)
	(method (doVerb theVerb)
		(switch theVerb
			(verbLook
				(TPrint "Your newly obtained boarding pass is just hanging
					there, waiting for you to grab it.")
			)
			(verbDo
				(Points ptsGotBoardingPass)
				(ego get: iBoardingPass)
				(card init:)
				(Bclr fPrintedPass)
				(self dispose:)
			)
			(else
				(super doVerb: theVerb &rest)
			)
		)
	)
)

(instance card of Actor
	(properties
		view			vATMCloseUp
		loop			lCard
		x				83
		y				107
		priority		3
		signal		fixPriOn
		moveSpeed	10
		description	"your AeroDork card"
	)
	(method (init)
		(super init:)
		(if (== y 97)
			;
			; Coming out of the slot
			;
			(curRoom drawPic: (curRoom picture?) PLAIN)
			(Display
				{Please take your AeroDork gold card now.}
				#p_at:    144 75
				#p_color: colWhite
				#p_width: 110
				#p_font:  smallFont
			)
			(theMusic2
				number:	sCardSlot,
				setLoop:	0,
				play:
			)
			(self setMotion: MoveTo x 107)
		else
			;
			; Going into the slot - wait for a bit, then slide in
			;
			(Delay 10 CYCLES self)
		)
	)
	(method (doVerb theVerb)
		(switch theVerb
			(verbLook
				(TPrint "Your AeroDork Gold Card is ready to return to your
					pocket.")
			)
			(verbDo
				(HandsOff)
				(curRoom drawPic: (curRoom picture?) PLAIN)
				(Display
					{Thank you for flying AeroDork!}
					#p_at:		144 75
					#p_color:	colWhite
					#p_width:	110
					#p_font:		smallFont
				)
				(ego get: iGoldCard)
				(Delay 5 SECONDS curRoom)
				(self dispose:)
			)
			(else
				(super doVerb: theVerb &rest)
			)
		)
	)
	(method (cue &tmp i)
		(switch (++ cueCounter)
			(1
				;
				; This happens on init, after the initial delay
				;
				(theMusic2
					number:	sCardSlot,
					setLoop:	0,
					play:
				)
				(self setMotion: MoveTo x 97 self)
			)
			(2
				;
				; This only happens when the card has disappeard into the slot
				;
				(User canInput: TRUE)
				(ego put: iGoldCard)
				(= [dest 0] 0)
				(= [dest 1] 0)
				(= [dest 2] 0)

				(Display
					{Welcome, Cliff Taurus!  Where to today?}
					#p_at:    144 75
					#p_color: colWhite
					#p_width: 110
					#p_font:  smallFont
				)
				(= i 0)

				(if (not (Btst fBeenInNewYork))
					(FormatTime i NY_NY)
					(Display
						{New York}
						#p_at:		144 102
						#p_color:	colWhite
						#p_width:	120
						#p_font:		smallFont
					)
					(Display
						@departTime
						#p_at:		215 102
						#p_color:	colWhite
						#p_width:	105
						#p_font:		smallFont
					)
					(= [dest i] NY_NY)
					(++ i)
				)

				(if (not (Btst fBeenInAtlanticCity))
					(FormatTime i AC_NJ)
					(Display
						{Atlantic City}
						#p_at:		144 (+ 102 (* 10 i))
						#p_color:	colWhite
						#p_width:	120
						#p_font:		smallFont
					)
					(Display
						@departTime
						#p_at:		215 (+ 102 (* 10 i))
						#p_color:	colWhite
						#p_width:	105
						#p_font:		smallFont
					)
					(= [dest i] AC_NJ)
					(++ i)
				)

				(if (not (Btst fBeenInMiami))
					(FormatTime i M_FL)
					(Display
						{Miami}
						#p_at:		144 (+ 102 (* 10 i))
						#p_color:	colWhite
						#p_width:	120
						#p_font:		smallFont
					)
					(Display
						@departTime
						#p_at:		215 (+ 102 (* 10 i))
						#p_color:	colWhite
						#p_width:	105
						#p_font:		smallFont
					)
					(= [dest i] M_FL)
					(++ i)
				)

				(if (not i)		; been everywhere
					(FormatTime i LA_CA)
					(Display
						{Los Angeles}
						#p_at:		144 102
						#p_color:	colWhite
						#p_width:	120
						#p_font:		smallFont
					)
					(Display
						@departTime
						#p_at:		210 102
						#p_color:	colWhite
						#p_width:	100
						#p_font:		smallFont
					)
					(= [dest i] LA_CA)
					(++ i)
				)

				; Setup the features for however many cities we got
				(if i
					(bar1 init:)
					(if (> i 1)
						(bar2 init:)
						(if (> i 2)
							(bar3 init:)
						)
					)
				)

				(theIconBar curIcon: (theIconBar at: doIcon))
				(theGame setCursor: DO_CURSOR)
				(self dispose:)	; se we can init & come back out
			)
		)
	)
)


;ษออออออออออป
;บ          บ
;บ Features บ
;บ          บ
;ศออออออออออผ

(instance slot of Feature
	(properties
		x 				80
		y 				19
		z				-100
		nsLeft 		63
		nsTop 		91
		nsBottom 	148
		nsRight 		98
 		description "the card slot"
		sightAngle 	40
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbLook
				(TPrint "Insert your AeroDork Gold card into this slot.")
			)
			(verbUse
				(switch invItem
					(iGoldCard
						(User canInput: FALSE)
						(card init:)
					)
					(else
						(super doVerb: theVerb invItem &rest)
					)
				)
			)
			(else
				(super doVerb: theVerb invItem &rest)
			)
		)
	)
)

(instance dispenser of Feature
	(properties
		x 				162
		y 				178
		nsLeft 		120
		nsTop 		171
		nsBottom 	186
		nsRight 		204
 		description "the boarding pass slot"
		lookStr		"Boarding passes are emitted from this slot."
		sightAngle 	40
	)
)

(instance ATMName of Feature
	(properties
		x 				164
		y 				27
		nsLeft 		57
		nsTop 		15
		nsBottom 	40
		nsRight 		271
 		description "the nameplate"
		lookStr 		"The nameplate says, \"AeroDork Airlines.\""
		sightAngle 	40
	)
)

(instance screen of Feature
	(properties
		x 				194
		y 				110
		nsLeft 		130
		nsTop 		63
		nsBottom 	157
		nsRight 		259
 		description "the screen"
		lookStr 		"The AeroDork ATM has a touch-sensitive screen."
		sightAngle 	40
	)
)

(instance bar1 of Feature
	(properties
		x 				174
		y 				105
		nsLeft 		143
		nsTop 		100
		nsBottom 	110
		nsRight 		205
		sightAngle 	40
		description	"the destination"
	)
	(method (doVerb theVerb invItem &tmp [str 80])
		(switch theVerb
			(verbLook
				(Format @str "Click the hand icon here to select the flight
					to %s."
					(switch [dest 0]
						(LA_CA	{Los Angeles})
						(M_FL		{Miami})
						(AC_NJ	{Atlantic City})
						(NY_NY	{New York})
					)
				)
				(TPrint @str)
			)
			(verbDo
				(= theBar 0)
				(curRoom drawPic: (curRoom picture?) PLAIN)
				(Display "Please enter the five-character Destination Code from
					your AeroDork Airline Travel Brochure."
					#p_at:    144 75
					#p_color: colWhite
					#p_width: 110
					#p_font:  smallFont
				)
				(if (not (HaveMouse))
					(TPrint "You can use your number pad to enter your code.  The
						keys match in position (i.e. the 7 key will press the top
						left button, etc.).  Be sure to use only the numeric key pad,
						not the cursor keys."
						#title {A hint from AL}
					)
				)
				(UseKeypad)
				(bar1 dispose:)
				(bar2 dispose:)
				(bar3 dispose:)
			)
			(else
				(super doVerb: theVerb invItem &rest)
			)
		)
	)
)

(instance bar2 of Feature
	(properties
		x 				174
		y 				115
		nsLeft 		143
		nsTop 		110
		nsBottom 	120
		nsRight 		205
		sightAngle 	40
		description	"the destination"
	)
	(method (doVerb theVerb invItem &tmp [str 80])
		(switch theVerb
			(verbLook
				(Format @str "Click the hand icon here to select the flight
					to %s."
					(switch [dest 1]
						(LA_CA	{Los Angeles})
						(M_FL		{Miami})
						(AC_NJ	{Atlantic City})
						(NY_NY	{New York})
					)
				)
				(TPrint @str)
			)
			(verbDo
				(= theBar 1)
				(curRoom drawPic: (curRoom picture?) PLAIN)
				(Display "Please enter the five-character Destination Code from
					your AeroDork Airline Travel Brochure."
					#p_at:    144 75
					#p_color: colWhite
					#p_width: 110
					#p_font:  smallFont
				)
				(if (not (HaveMouse))
					(TPrint "You can use your number pad to enter your code.  The
						keys match in position (i.e. the 7 key will press the top
						left button, etc.).  Be sure to use only the numeric key pad,
						not the cursor keys."
						#title {A hint from AL}
					)
				)
				(UseKeypad)
				(bar1 dispose:)
				(bar2 dispose:)
				(bar3 dispose:)
			)
			(else
				(super doVerb: theVerb invItem &rest)
			)
		)
	)
)

(instance bar3 of Feature
	(properties
		x 				174
		y 				125
		nsLeft 		143
		nsTop 		120
		nsBottom 	130
		nsRight 		205
		sightAngle 	40
		description	"the destination"
	)
	(method (doVerb theVerb invItem &tmp [str 80])
		(switch theVerb
			(verbLook
				(Format @str "Click the hand icon here to select the flight
					to %s."
					(switch [dest 2]
						(LA_CA	{Los Angeles})
						(M_FL		{Miami})
						(AC_NJ	{Atlantic City})
						(NY_NY	{New York})
					)
				)
				(TPrint @str)
			)
			(verbDo
				(= theBar 2)
				(curRoom drawPic: (curRoom picture?) PLAIN)
				(Display "Please enter the five character Destination Code from
					your AeroDork Airline Travel Brochure."
					#p_at:    144 75
					#p_color: colWhite
					#p_width: 110
					#p_font:  smallFont
				)
				(if (not (HaveMouse))
					(TPrint "You can use your number pad to enter your code.  The
						keys match in position (i.e. the 7 key will press the top
						left button, etc.).  Be sure to use only the numeric key pad,
						not the cursor keys."
						#title {A hint from AL}
					)
				)
				(UseKeypad)
				(bar1 dispose:)
				(bar2 dispose:)
				(bar3 dispose:)
			)
			(else
				(super doVerb: theVerb invItem &rest)
			)
		)
	)
)

