;**
;**	Logics for Room 110 -- Copy protection screen
;**
;**	LSL5:  Passionate Patti Does A Little Undercover Work
;**				Copyright 1991
;**				by Al Lowe
;**
;**	Programmed by Steve Conrad
;**
;**	Last Update:  August 28, 1991
;**

(script#		110)

(public
	rm110		0
)

(procedure
	ShadowPrint
)

(define	pSatinBackground				115)
(define	vPattiPoster				115)
(define	vLarryPoster				116)

;SRC(define	mRepriseTheme					110)

(define	colPurple							61)

(instance rm110 of LLRoom
	(properties
		picture		pSatinBackground
	)

	(method (init)
		(larryPic init:)
		(self
			style: 	(if (Btst isVga)
							PIXELDISSOLVE
						else
							DISSOLVE
						)
		)
		(super init:)
		(LoadMany PICTURE pBlackPic)
		(LoadMany VIEW
			vPattiPoster
			vLarryPoster
		)
		(keyDownHandler addToFront: self)
		(mouseDownHandler addToFront: self)
		(SetFFRoom 120)
		(self setScript: sCartoon)
	)

	(method (handleEvent event)
		(if (and									 ;DEBUG - Don't need and for SV
				(event type?)
				(!= (event message?) ESC)
				(!= (event message?) `@a)	 ;DEBUG - Come out for shipping version
				(!= (event message?) `@r)	 ;DEBUG
				(!= (event message?) `@t)	 ;DEBUG
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
		(theMusic
			fade: 0 15 12 TRUE
		)
	)
)

;********** Scripts ***********

(instance sCartoon of Script
	(method (changeState newState &tmp [str 100])
		(switchto (= state newState)
			(
				(= cycles 2)
			)
			(
				(curRoom drawPic: (curRoom picture?))
				(ShadowPrint
					{Executive Producer\n\n
					Ken Williams}
					160
				)
				(= seconds 4)
			)
			(
				(curRoom drawPic: (curRoom picture?))
				(ShadowPrint
					{Creative Director\n\n
					Bill Davis}
					160
				)
				(= seconds 4)
			)
			(
				(curRoom drawPic: (curRoom picture?))
				(ShadowPrint
					{Director\n\n
					Al Lowe}
					160
				)
				(= seconds 4)
			)
			(
				(curRoom drawPic: (curRoom picture?))
				(ShadowPrint
					{Producer\n\n
					Guruka Singh Khalsa}
					160
				)
				(= seconds 3)
			)
			(
				(curRoom drawPic: (curRoom picture?)) 		;Picture change
				(larryPic dispose:)
				(pattiPic init:)
				(= ticks 120)
			)
			(
				(curRoom drawPic: (curRoom picture?))
				(ShadowPrint
					{Game Designer\n\n
					Al Lowe}
					0
				)
				(= seconds 4)
			)
			(
				(curRoom drawPic: (curRoom picture?))
				(ShadowPrint
					{Production Designer\n\n
					Andy Hoyos}
					0
				)
				(= seconds 4)
			)
			(
				(curRoom drawPic: (curRoom picture?))
				(ShadowPrint
					{Art Designer\n\n
					Jane Cardinal}
					0
				)
				(= seconds 4)
			)
			(
				(curRoom drawPic: (curRoom picture?))
				(ShadowPrint
					{Lead Programmer\n\n
					Brian K. Hughes}
					0
				)
				(= seconds 4)
			)
			(
				(curRoom drawPic: (curRoom picture?))
				(ShadowPrint
					{Composer\n\n
					Craig Safan}
					0
				)
				(= seconds 4)
			)
			(
				(theMusic
					fade: 0 15 12 TRUE
				)
				(curRoom drawPic: pBlackPic IRISIN)
				(= seconds 3)
			)
			(
				(TPrint "An East Coast meeting is not going too well....")
				(theIconBar enable:)
				(curRoom newRoom: 120)
				(self dispose:)
			)
		)
	)
);sCartoon

;********** Objects ***********

(instance pattiPic of View
	(properties
		view			vPattiPoster
		loop			0
		cel			0
		x				170
		y				177
		priority		0
		signal		(| stopUpdOn fixPriOn)
	)
)

(instance larryPic of View
	(properties
		view			vLarryPoster
		loop			0
		cel			0
		x				173;181
		y				179
		priority		0
		signal		(| stopUpdOn fixPriOn)
	)
)

;********** Procedure ***********

(procedure (ShadowPrint	theText atX)
	(Display
 		theText
		#p_at: 		(+ atX 1) (- 64 (/ (* 14 3) 2))
		#p_width:	158
		#p_mode:		teJustCenter
		#p_color:	colBlack
		#p_font:		niceFont
	)
	(Display
 		theText
		#p_at: 		atX (- 63 (/ (* 14 3) 2))
		#p_width:	158
		#p_mode:		teJustCenter
		#p_color:	colPurple
		#p_font:		niceFont
	)
)
