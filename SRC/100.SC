;**
;**	Logics for Room 100 -- Title screen
;**
;**	LSL5:  Passionate Patti Does A Little Undercover Work
;**				Copyright 1991
;**				by Al Lowe
;**
;**	Programmed by Steve Conrad with typewriter stuff stolen from Brian's demo
;**
;**	Last Update:  August 29, 1991
;**


(script#		100)

(procedure
	TypeIt
)

(public
	rm100		0
)

(define	pTitleSierra					100)
(define	pTitleLSLarryIn				105)
(define	vLarryOpen1					105)
	(define	lSingleCels			0)
		(define 	cBucket		0)
		(define 	cBrush		1)
	(define	lGetGlue				1)
	(define	lSpreadGlue			2)
	(define	lSetDownBrush		3)
	(define	lPutUp5				4)
(define	vLarryOpen2					106)
	(define	lStruggleW/Bucket	0)
	(define	lLeaningOnWall		1)
	(define	lFootDown			2)
	(define	lHandStuck			3)
	(define	lStepInBucket		4)
(define	vLarryOpen3					107)
	(define	lStruggleW/Hand	0)
	(define	lPullBack			1)
	(define	lPullAgain			2)
(define	vLarryOpen4					108)
	(define	lStepAway			0)
	(define	lStepBack			1)
	(define	lAdmire				2)
	(define	lBlankPanel			3)
	(define	lTake					4)
(define 	vLargePatti					109)

; Music defines
(define	mMainTheme						100)
(define		sFootInBucket		101)
(define		sTypewriter			102)
(define		sBroomUp				103)
(define		sGlueStretch		104)
(define		sBackSpace			105)

(local
	letter
	letterX
	[string 50]
	underBs = [ 0 0 0 0 0 0 0 ]
	sizeArray = [ 0 0 0 0 ]
	lightCounter
	cycleThemColors
)

(instance rm100 of LLRoom
	(properties
		name "rm100"
		picture pTitleSierra
		style FADEOUT
	)

	(method (init)
		(LoadMany PICTURE pTitleLSLarryIn)
		(LoadMany VIEW
			vLarryOpen1
			vLarryOpen2
			vLarryOpen3
			vLarryOpen4
			vLargePatti
		)
		(LoadMany SOUND
			sFootInBucket
			sTypewriter
			sBroomUp
			sGlueStretch
			sBackSpace
		)
		(theMusic
			number:		mMainTheme,
			setLoop:		-1,
			flags:		TRUE,
			play:
		)
		(StrCpy @string {Passionate Patti Does PittsbuA Little Undercover Work})
		(super init:)
		(HandsOff)
		(keyDownHandler addToFront: self)
		(mouseDownHandler addToFront: self)
		(SetFFRoom 120)
		(self setScript: sCartoon)
	)

	(method (handleEvent event)
		(if (and									;DEBUG - Don't need and for SV
				(event type?)
				(!= (event message?) ESC)
				(!= (event message?) `@a)	;DEBUG - Come out for shipping version
				(!= (event message?) `@r)	;DEBUG
				(!= (event message?) `@t)	;DEBUG
			)
			(if
				(Print "Throughout this game, you can \"Zap\" ahead
					by clicking this Fast Forward button,
					which is now hidden at the top of your screen."
					#title:	{Fast Forward}
					#icon:	vIcons 11 0
					#new:
					#button:	{Really? Show Me!} 1
					#button:	{Oops}				 0
				)
				(theIconBar
					curIcon:		(theIconBar at: ffIcon),
					handleEvent:
						(event
							type:		keyDown,
							message:	ESC,
							yourself
						)
				)
			)
			(event claimed: TRUE)
		else
			(super handleEvent: event &rest)
		)
	)

	(method (dispose)
		(super dispose: &rest)
		(keyDownHandler delete: self)
		(mouseDownHandler delete: self)
	)
)

;************ Scripts ************

(instance sCartoon of Script
	(method (doit)
		(super doit:)
		(if cycleThemColors
			(Palette PALCycle 24 31 -1)
			(Palette PALCycle 240 254 -1)
		)
	)
	(method (changeState newState)
		(switchto (= state newState)
			(
				(= seconds 4)
			)
			(
				(larry init:)
				(patti init:)
				(blankPanel init:)
				(glue init:)
				(curRoom drawPic: pTitleLSLarryIn FADEOUT)
				(= seconds 3)			;give viewer time to adjust
			)
			(
				(larry
					setCycle:		EndLoop self
				)
			)
			(
				(broomUp play:)
				(larry
					setLoop:			lSpreadGlue,
					setCel:			0,
					setCycle:		EndLoop self
				)
			)
			(
				(broomUp play:)
				(larry
					setCel:			0,
					setCycle:		EndLoop self
				)
			)
			(
				(glue setPri: 0)
				(larry
					setLoop:			lSetDownBrush,
					setCycle:		EndLoop self
				)
			)
			(
				(= seconds 3)			;give viewer time to adjust
			)
			(
				(brush init:)
				(larry
					setLoop:			lPutUp5,
					setCel:			0,
					setCycle:		EndLoop self
				)
			)
			(
				(blankPanel dispose:)
				(larry
					view:			vLarryOpen4,
					setLoop:		lStepAway,
					setCel:		0,
					setCycle:	EndLoop self
				)
			)
			(
				(larry
					setLoop:		lStepBack,
					setCel:		0,
					setCycle:	EndLoop self
				)
			)
			(
				(larry								  		;step back again
					setLoop:		lStepBack,
					setCel:		0,
					x:				(+ (larry x?) 20),
					setCycle:	EndLoop self
				)
			)
			(
				(larry
					setLoop:		lAdmire,
					setCel:		0,
					setCycle:	EndLoop self
				)
			)
			(
				(= cycleThemColors TRUE)
				(= seconds 5)
			)
			(
				(patti
					setCycle:	Walk,
					setMotion: 	MoveTo 15 140 self
				)
			)
			(
				(patti
					setMotion: 	MoveTo 335 140
				)
				(= letter 0)	 				;Start the typing
				(= letterX 3)
				(larry setScript: sLarryCartoon)
				(= cycles 1)
			)
			;**	loop through this state 28 times
			(;Print first string (string with mistake).
				(TypeIt)
				(typewriter play:)
				(if (< (++ letter) 29)
					; We're not done with the string yet
					(= cycles (Random 3 5))
					(-- state)
				else
					; We're done, go on to next part
					(= cycles 10)
				)
			)

			;**	loop through this state 7 times
			(;erase the Pitts letters
				(Display {} #p_restore [underBs (- (-- letter) 22)])
				(backSpace play:)
				(if (> letter 22)
					; We're not done correcting yet
					(= cycles 4)
					(-- state)
				else
					; We're done correcting, change strings & go on to next part
					(= letterX 153)
					(= letter 29)
					(= cycles 10)
				)
			)

			;**	loop through this state a buncha times too
			(;Finish correctly
				(TypeIt)
				(typewriter play:)
				(if (< (++ letter) (StrLen @string))
					; We're not done with the string yet
					(-- state)
					(= cycles (Random 3 5))
				else
					; We're done, go on to next part
					(= cycles 10);5
				)
			)
			(
				(= ticks 270)
			)
			(
				(curRoom newRoom: 110)
				(self dispose:)
			)
		)
	)
);sCartoon

(instance sLarryCartoon of Script
	(method (changeState newState)
		(switchto (= state newState)
			(
				(larry
					setLoop:		lTake,
					setCel:		0,
					setCycle:	ForwardCounter 4 self
				)
			)
			(
				(larry
					view:			vLarryOpen2,
					setLoop:		lStepInBucket,
					setCel:		0,
					posn:			268 133,
					setCycle:	CycleTo 3 1 self,
					setMotion:	MoveTo 263 133 self,
				)
			)
			(
				;Dummy state - so that the last to cue above cues next state
			)
			(
				(glue dispose:)
				(larry
					setCycle:	CycleTo 5 1 self
				)
			)
			(
				(footInBucket play:)
				(larry
					setCycle:	EndLoop self
				)
			)
			(
				(larry
					setLoop:		lStruggleW/Bucket,
					setCel:		0,
					x:				(- (larry x?) 5),
					setCycle:	CycleTo 4 FORWARD self
				)
			)
			(
				(glueStretch play:)
				(larry
					setCycle:	EndLoop self
				)
			)
			(
				(larry
					setCycle:	BegLoop self
				)
			)
			(
				(larry
					setLoop:		lLeaningOnWall,
					setCel:		0,
					setCycle:	ForwardCounter 2 self
				)
			)
			(
				(larry
					setLoop:		lFootDown,
					setCel:		0,
					setCycle:	ForwardCounter 2 self
				)
			)
			(
				(larry
					setLoop:		lHandStuck,
					setCel:		0,
					setCycle:	EndLoop self
				)
			)
			(
				(larry
					view:			vLarryOpen3,
					setLoop:		lStruggleW/Hand,
					setCel:		0,
					setCycle:	ForwardCounter 2 self
				)
			)
			(
				(glueStretch play:)
				(larry
					setLoop:		lPullBack,
					setCel:		0,
					setCycle:	EndLoop self
				)
			)
			(
				(self dispose:)
			)
		)
	)
);sLarryCartoon

;************ Objects ************

(instance larry of Actor
	(properties
		view			vLarryOpen1
		loop			lGetGlue
		cel			0
		x				230
		y				132
		cycleSpeed	10
		moveSpeed	10
		signal		ignrAct
	)
)

(instance patti of Actor
	(properties
		view		  	vLargePatti
		loop			0
		cel			0
		x				-16
		y				140
		xStep			5
		yStep			2
		moveSpeed	10
		cycleSpeed	10
		signal		ignrAct
	)
)

(instance blankPanel of View
	(properties
		view			vLarryOpen4
		loop			lBlankPanel
		cel			0
		x				223
		y				108
		signal		(| stopUpdOn ignrAct)
	)
)

(instance glue of View
	(properties
		view			vLarryOpen1
		loop			lSingleCels
		cel			cBucket
		x				251
		y				132
		signal		(| stopUpdOn ignrAct)
	)
)

(instance brush of View
	(properties
		view			vLarryOpen1
		loop			lSingleCels
		cel			cBrush
		x				256
		y				132
		signal		(| stopUpdOn ignrAct)
	)
)

;********** Sounds ***********

(instance footInBucket of Sound
	(properties
		number	sFootInBucket
	)
)

(instance typewriter of Sound
	(properties
		number	sTypewriter
	)
)

(instance broomUp of Sound
	(properties
		number	sBroomUp
	)
)

(instance glueStretch of Sound
	(properties
		number	sGlueStretch
	)
)

(instance backSpace of Sound
	(properties
		number	sBackSpace
	)
)

;********** Procedure ***********

(procedure (TypeIt &tmp [ltr 2] char theUnders)
	(= char (StrAt @string letter))
	(Format @ltr {%c} char)
	(= theUnders
		(Display @ltr
			#p_at:		letterX 160
			#p_color:	colWhite
			#p_width:	7						;**	was 20
		 	#p_mode:		teJustLeft
			#p_font:		monoFont				;**	2407
			#p_save:
		)
	)

	; If we're in the "Pittsbu" part, save the underbits for each character
	;	into the array above so we can erase it later
	(if (< 21 letter 29)
		(= [underBs (- letter 22)] theUnders)
	else
		(UnLoad MEMORY theUnders)
	)
	(+= letterX 7)
	(if (== 32 char)							;**	because " " is 5 wide, not 7.
		(-= letterX 2)
	)
)

