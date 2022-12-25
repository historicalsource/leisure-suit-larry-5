;**
;**	Logics for TTDIALER -- Touch Tone Dialer
;**
;**	LSL5:  Passionate Patti Does A Little Undercover Work
;**				Copyright 1991
;**				by Al Lowe
;**
;**	Programmed by Brian K. Hughes
;**
;**	Last Update:  September 10, 1991
;**


;** 	This module is used both for a phone pad and a door entry keypad.
;** If accessed with:
;**
;**		(DialPhone whoCares)
;**
;** the room's notify method will be passed a four-digit number that
;** represents the last four numbers the user dialed.
;**
;** If accessed with:
;**
;**		(UseKeypad whoCares)
;**
;** the room's notify method will be passed a five-digit number that
;** represents the number keyed by the user.


(script#	TTDIALER)

(public
	TTDialer		0
)


(define	vCopyProtButtons	011)
(define	usingPhone			(== function TRUE))
(define	usingKeypad			(== function FALSE))
(define	lHangUpButton		13)

(define	sPhoneKey1			021)
(define	sPhoneKey2			022)
(define	sPhoneKey3			023)
(define	sPhoneKey4			024)
(define	sPhoneKey5			025)
(define	sPhoneKey6			026)
(define	sPhoneKey7			027)
(define	sPhoneKey8			028)
(define	sPhoneKey9			029)
(define	sPhoneKey0			030)
(define	sPhoneKey#			031)
(define	sPhoneKey*			032)
(define	sDialTone			033)
(define	sWrongNumber		035)

(define	ctrlAltDown			12)

(define	DO_EXIT				254)
(define	EXIT_DIALER			255)

(define	sATMFX				256)


(local
	[str 100]
	buttons
	buttonX 	= [ 0 1 -16 1 19 -16 1 19 -16 1 19 -16 19 -1 ]
	buttonY 	= [ 0 -15 -69 -69 -69 -52 -52 -52 -34 -34 -34 -15 -15 -3 ]
	prefix 	= [551 552 553 554 555 556]
	ATMLoops = [ 0 0 0 0 0 0 0 0 0 0 ]
	thePrefix
	theLine
	function
	who2Cue
	userInput
	vol1
	vol2
	oldSettings
	walkType
	notifyVal
	cheatVal
)


(instance TTDialer of Prop
	(properties
		view		vPushbuttons
		x			275
		y			89
		priority	14
		signal	fixPriOn
	)
	(method (init which who)
		(switch curRoomNum
			(258					; ATM machine, lower left corner
				(= x 41)
				(= y 189)
			)
			(870					; K-RAP, upper left corner
				(= x 41)
				(= y 89)
			)
			(else					; everywhere else, upper right corner
				(= x 275)
				(= y 89)
			)
		)
		(= function which)
		(if (> argc 1)
			(= who2Cue who)
		else
			(= who2Cue 0)
		)
		(= str 0)
		(= buttons 0)
		(= thePrefix 0)
		(= theLine 0)

		; Save off the current condition of User & the current icon
		(= userInput (User canInput?))
		(= oldSettings iconSettings)
		(= walkType ((theIconBar at: walkIcon) cursor?))

		; Set up our control situation
		(if (!= walkType EXIT_CURSOR)
			(SetupExit TRUE)
		)
		(HandsOff)		; saves the state of each icon
		(User
			canInput: 		TRUE,
			mapKeyToDir:	FALSE
		)
		(theIconBar
			enable:	walkIcon doIcon,
			curIcon:	(theIconBar at: doIcon)
		)
		(theGame setCursor: DO_CURSOR)

		; If there's music playing, turn it down
		(if (theMusic handle?)
			(= vol1 (theMusic vol?))
			(theMusic setVol: (/ vol1 2))
		)
		(if (theMusic2 handle?)
			(= vol2 (theMusic2 vol?))
			(theMusic2 setVol: (/ vol2 2))
		)

		(if usingPhone
			(dialToneSound
				number:	sDialTone,
				loop:		-1,
				flags:	TRUE,
				play:		,
				hold:		10
			)
		)

		(super 		init:, stopUpd:)
		(btn0 		init:, stopUpd:)
		(btn1 		init:, stopUpd:)
		(btn2 		init:, stopUpd:)
		(btn3 		init:, stopUpd:)
		(btn4 		init:, stopUpd:)
		(btn5 		init:, stopUpd:)
		(btn6 		init:, stopUpd:)
		(btn7 		init:, stopUpd:)
		(btn8 		init:, stopUpd:)
		(btn9 		init:, stopUpd:)
		(if usingPhone
			(btnStar 	init:, stopUpd:)
			(btnCross 	init:, stopUpd:)
			(hangUp		init:, stopUpd:)
		)
		(mouseDownHandler addToEnd: self)
		(keyDownHandler addToEnd: self)
	)
	(method (dispose)
		(theIconBar
			disable:
;			curIcon:	(theIconBar at: computerIcon),
;			disable: walkIcon doIcon
		)
		(User
			canInput:	FALSE,
			canControl:	FALSE
		)
		(btn0 		dispose:)
		(btn1 		dispose:)
		(btn2 		dispose:)
		(btn3 		dispose:)
		(btn4 		dispose:)
		(btn5 		dispose:)
		(btn6 		dispose:)
		(btn7 		dispose:)
		(btn8 		dispose:)
		(btn9 		dispose:)
		(if usingPhone
			(btnStar 	dispose:)
			(btnCross 	dispose:)
			(hangUp		dispose:)
		)

		(self hide:)
		(= buttons DO_EXIT)

		; Wait for a bit, then do the real dispose
		(hangUpTimer setReal: self 2)
	)

	(method (handleEvent event)
		(cond
			((not (User canInput?)))
			((event modifiers?)	; we want shift- and control- events to go thru
				(super handleEvent: event)
			)
			((and	(== (event type?) keyDown)
					(== (event message?) ESC)
				)
				(event claimed: TRUE)
			)
			((or	(and	(OneOf (event type?) mouseDown keyDown)
							(== ((theIconBar at: walkIcon) cursor?) EXIT_CURSOR)
							(== (theIconBar curIcon?) (theIconBar at: walkIcon))
				  	)
					(== (event message?) verbWalk)
				)
				(event claimed: TRUE)
				(= notifyVal -1)
				(self dispose:)
			)
			(else
				(event claimed: TRUE)
			)
		)
	)

	(method (cue &tmp i)
		(switch (++ buttons)
			(1
				(if (and	usingPhone
							(not (ReadNumber @str))
						)
					(self setScript: sInformation)
				)
			)
			(3
				(if usingPhone
					(= thePrefix (ReadNumber @str))
					(= str 0)
					(if (OneOf thePrefix 411 911)
						(self setScript: sInformation)
					)
				)
			)
			(5
				(if usingKeypad
					(if (not (StrCmp @str {!!!!!}))	; we cheated
						(= notifyVal -2)
					else
						(= notifyVal (ReadNumber @str))
					)
					(self dispose:)
				)
			)
			(7
				(User canInput: FALSE)
				(= theLine (ReadNumber @str))
				(if (and	usingPhone
							(== thePrefix 555)
							(== theLine 1212)
						)
					(self setScript: sInformation)
				else
					(User canInput: FALSE)
					(self setScript: sMessage self)
				)
			)
			(8	; not really a button cue, we get cued from sMessage
				(if theLine
					(= notifyVal theLine)
					(self dispose:)
				else
					(= str 0)
					(= buttons 0)
					(= thePrefix 0)
					(= theLine 0)
				)
			)
			(EXIT_DIALER		; we've waited 40 cycles, now leave
				(for	((= i 0))
						(< i 10)
						((++ i))
					(= [ATMLoops i] 0)
				)
				; Restore state of user & iconbar
				(User
					canInput: 		userInput,
					mapKeyToDir:	TRUE
				)
				(if (!= walkType EXIT_CURSOR)
					(SetupExit FALSE)
				)
				(RestoreIB)
				(= iconSettings oldSettings)

				; Reset sound volumes
				(if (theMusic handle?)
					(theMusic setVol: vol1)
				)
				(if (theMusic2 handle?)
					(theMusic2 setVol: vol2)
				)
				(mouseDownHandler delete: self)
				(keyDownHandler delete: self)
				(curRoom notify: notifyVal)
				(if who2Cue
					(who2Cue cue:)
				)
				(dialToneSound stop:)
				(hangUpTimer dispose:, delete:)
				(theIconBar
					enable:		,
					curIcon: 	(theIconBar at: doIcon)
				)
				(theGame setCursor: ((theIconBar curIcon?) cursor?))
				(super dispose:)
			)
		)
	)
);TTDialer


(instance sMessage of Script
	;
	(define PREFIX_OK  1)
	;
	(method (changeState newState)
		(switchto (= state newState)
			(
				(if (or	(and	(!= curRoomNum LIMO)
									(== thePrefix [prefix larryLoc])
								)
							(and	(== curRoomNum LIMO)
									(== thePrefix 556)
								)
						)
					(= register PREFIX_OK)
				)
				(if (!= register PREFIX_OK)
					(phoneSound number: sWrongNumber, play: self)
				else
					(= cycles 1)
				)
			)
			(
				(if (!= register PREFIX_OK)
				  	(TPrint "\"We're sorry,
						your call cannot be completed as dialed.
				  		Please hang up and try your call again.
						If you are having trouble,
						please hang up and dial your operator.\"")
					(TPrint "\"This is a recording.\"")
					(TPrint "(As if you didn't know!)" LOW)
				  	(= theLine 0)
				else
				  	(TPrint "\"Thank you for using U. S. Sprint!\"")
				)
				(User canInput: TRUE)
				(= notifyVal theLine)
				(client dispose:)
			)
		)
	)
);sMessage

(instance sInformation of Script
	(method (changeState newState &tmp [string 222])
		(switchto (= state newState)
			(
				(= ticks 120)
			)
			(
				(Format @string "\"You have reached Directory Assistance for the
					Greater %s Area.
					We're sorry, but we're unable to come to the phone
					right now.
					We're presently busy (yeah, busy %s!).
					Let us assure you that your call IS important;
					it's just not important to us!\""
					(if (== curRoom 200)
						{Washington, D. C.}
					else
						(switch larryLoc
							(NewYork
								{New York City Metropolitan}
							)
							(AtlanticCity
								{Atlantic City Non-Tramp Infested}
							)
							(Miami
								{Dade County}
							)
							(else
								{`Enter Your City Here'}
							)
						)
					)
					(if (== curRoom 200)
						{floating down the Potomac}
					else
						(switch larryLoc
							(NewYork
								{getting mugged}
							)
							(AtlanticCity
								{feeding the slots}
							)
							(Miami
								{lying on the beach}
							)
							(else
								{sitting on the can!}
							)
						)
					)
				)
				(TPrint @string)
				(= cycles 1)
			)
			(
				(= str 0)
				(= buttons 0)
				(= thePrefix 0)
				(= theLine 0)
				(= notifyVal -1)
				(client dispose:)
			)
		)
	)
);sInformation


(instance dialToneSound of Sound)
(instance phoneSound of Sound)
(instance hangUpTimer of Timer)

(class PushButton of Prop
	(properties
		priority		14
		signal		fixPriOn
		keyValue		45				; what key triggers us
		scanValue	0				; what scan code triggers us
		keyStr		{x}		  	; what to cat to str
		tone			0			  	; sound to play when pressed
	)
	(method (init &tmp idx)
		(= x (+ (TTDialer x?) [buttonX loop]))
		(= y (+ (TTDialer y?) [buttonY loop] 1000))
		(if (== curRoomNum 258)
			(= view vCopyProtButtons)
			; Assign a random loop, until they're all used
			(= idx (Random 1 10))
			(while [ATMLoops idx]
				(= idx (Random 1 10))
			)
			(= [ATMLoops idx] 1)
			(= loop idx)
		else
			(= view vPushbuttons)
		)
		(self setCel: 0)
		(super init:)
		(mouseDownHandler addToFront: self)
		(keyDownHandler addToFront: self)
	)
	(method (dispose)
		(mouseDownHandler delete: self)
		(keyDownHandler delete: self)
		(super dispose:)
	)
	(method (handleEvent event &tmp [tempStr 5] [str1 5] eType eMsg theTone)
		;
		; We should do something if
		;		a) we were clicked on
		;		b) we were ENTERed on
		;		c) the corresponding key was pressed
		;
		(= eType (event type?))
		(= eMsg	(event message?))
		(cond
			((and	(== eType keyDown)
					(== eMsg ESC)
				)
				(event claimed: TRUE)
			)
			((or
					(and
						(== eType mouseDown)
						(self onMe: event)
					)
					(and
						(== eType keyDown)
						(== eMsg ENTER)
						(self onMe: event)
					)
					(and
						(== eType keyDown)
						(OneOf eMsg keyValue scanValue)
					)
				)
				(event claimed: TRUE)
				(dialToneSound stop:)
				(if (== loop lHangUpButton)
					(= notifyVal -1)
					(TTDialer dispose:)
				else
					(if (and (== (event modifiers?) ctrlAltDown)
								(== loop 1)
							)
						(= keyStr {!})
					else
						(switch loop
							(11
								(= keyStr {#})
							)
							(12
								(= keyStr {*})
							)
							(else
								(Format keyStr "%d" (- loop 1))
							)
						)
					)
					(= theTone (if (== curRoomNum 258) sATMFX else tone))
					(self setScript: (sButton new:) NULL theTone)
				)
			)
		)
	)
)

(instance sButton of Script
	;
	; register - tone to play
	;
	(method (changeState newState)
		(switchto (= state newState)
			(
				(User canInput: FALSE)
				(client setCycle: EndLoop self)
			)
			(
				(if register
					(phoneSound
						number:	register,
						play:
					)
				)
				(= cycles 1)
			)
			(
				(StrCat @str (client keyStr?))
				(client setCel: 0)
				(= cycles 1)
			)
			(
				(TTDialer cue:)
				(= cycles 10)
			)
			(
				(User canInput: TRUE)
				(self dispose:)
			)
		)
	)
);sButton

(instance btn0 of PushButton
	(properties
		loop			1
		z				1000
		keyValue		48
		scanValue	20992
		tone			sPhoneKey0
	)
)
(instance btn1 of PushButton
	(properties
		loop			2
		z				1000
		keyValue		49
		scanValue	20224
		tone			sPhoneKey1
	)
	(method (init)
		(= keyValue (if (== curRoomNum 258) 55 else 49))
		(= scanValue (if (== curRoomNum 258) 18176 else 20224))
		(super init:)
	)
)
(instance btn2 of PushButton
	(properties
		loop			3
		z				1000
		keyValue		50
		scanValue	20480
		tone			sPhoneKey2
	)
	(method (init)
		(= keyValue (if (== curRoomNum 258) 56 else 50))
		(= scanValue (if (== curRoomNum 258) 18432 else 20480))
		(super init:)
	)
)
(instance btn3 of PushButton
	(properties
		loop			4
		z				1000
		keyValue		51
		scanValue	20736
		tone			sPhoneKey3
	)
	(method (init)
		(= keyValue (if (== curRoomNum 258) 57 else 51))
		(= scanValue (if (== curRoomNum 258) 18688 else 20736))
		(super init:)
	)
)
(instance btn4 of PushButton
	(properties
		loop			5
		z				1000
		keyValue		52
		scanValue	19200
		tone			sPhoneKey4

	)
)
(instance btn5 of PushButton
	(properties
		loop			6
		z				1000
		keyValue		53
		scanValue	19456
		tone			sPhoneKey5
	)
)
(instance btn6 of PushButton
	(properties
		loop			7
		z				1000
		keyValue		54
		scanValue	19712
		tone			sPhoneKey6
	)
)
(instance btn7 of PushButton
	(properties
		loop			8
		z				1000
		keyValue		55
		scanValue	18176
		tone			sPhoneKey7
	)
	(method (init)
		(= keyValue (if (== curRoomNum 258) 49 else 55))
		(= scanValue (if (== curRoomNum 258) 20224 else 18176))
		(super init:)
	)
)
(instance btn8 of PushButton
	(properties
		loop			9
		z				1000
		keyValue		56
		scanValue	18432
		tone			sPhoneKey8
	)
	(method (init)
		(= keyValue (if (== curRoomNum 258) 50 else 56))
		(= scanValue (if (== curRoomNum 258) 20480 else 18432))
		(super init:)
	)
)
(instance btn9 of PushButton
	(properties
		loop			10
		z				1000
		keyValue		57
		scanValue	18688
		tone			sPhoneKey9
	)
	(method (init)
		(= keyValue (if (== curRoomNum 258) 51 else 57))
		(= scanValue (if (== curRoomNum 258) 20736 else 18688))
		(super init:)
	)
)
(instance btnCross of PushButton
	(properties
		loop			11
		z				1000
		keyValue		35
		tone			sPhoneKey#
	)
)
(instance btnStar of PushButton
	(properties
		loop			12
		z				1000
		keyValue		`*
		tone			sPhoneKey*
	)
)
(instance hangUp of PushButton
	(properties
		loop			13
		z				1000
		keyValue		32
	)
)
