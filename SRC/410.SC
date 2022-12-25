;**
;**	Logics for Room 410 -- Alley exterior
;**
;**	LSL5:  Passionate Patti Does A Little Undercover Work
;**				Copyright 1991
;**				by Al Lowe
;**
;**	Programmed by John Hartin
;**
;**	Last Update:  June 30, 1991
;**

(script#		410)

(public
	rm410		0
)


(define	DESMOND_PRINT		#at  15 15 #width 160)
(define	PATTI_PRINT			#at 145 15 #width 160)

(define	vRoom							410)
(define	vRoomEgo			 (+ 1 vRoom))
(define	pDarkAlleyExterior			410)
(define	vDarkAlleyExterior		410)
	(define	lSteam				0)
	(define	lArrow				1)
(define	vPattiInAlley				411)
	(define	lWalkingWest		0)
	(define	lWalkingEast		1)
	(define	lReflectWest		2)
	(define	lStanding			3)
	(define	lReflectStanding	4)
(define  vDesmond		412)
(define	lDesmondEast	0)
(define	lDesmondWest	1)
(define	lDesmondLeaning	2)
(define	lDesmondTrans	3)
(define	lDesmondStandEast	4)
(define	pPianoPit			400)
(define	pDesmondsOffice	420)
(define	pDarkAlley2Shot	415)

(define	mDesmondTheme	410)
(define	mDesmond2	420)

(local
	seenMsg
)


(instance rm410 of LLRoom
	(properties
		picture		410
		east			pPianoPit
		west			pDesmondsOffice
		south			pDarkAlley2Shot
	)

	(method (init)
		(ego 	
			init:,
			normalize: vRoomEgo
		)

		(LoadMany	VIEW		vDarkAlleyExterior vPattiInAlley vDesmond)
		(LoadMany	SOUND		mDesmondTheme mDesmond2)

		(switch prevRoomNum
			(south
				(ego
					x:	157,
					y:	125,
					setLoop: lStanding,		;standing 
					setCel: 0,		;west
					setCycle: 0
				)
				(reflection
					init:,
					x:	157,
					y:	125,
					setLoop: lReflectStanding,		;standing
					setCel: 0,		;west
					setCycle: 0
				)
				(desmond
					init:,
					x: 130,
					y: 125,
					setLoop: 	lDesmondStandEast,
					setCel: 		0,
					setCycle: 	0,
					signal:	ignrAct
				)
				(self setScript: sCartoon2)
			)
			(else
				(desmond init:, setCycle: Forward)
				(self setScript: sCartoon)
			)
		)

		(super init:)
		(steam init:, setCycle: Forward)
		(arrow init:, setCycle: Forward)
	)

	(method (newRoom n)
		(if (!= n 415)		
			(theMusic
				fade:	0 15 12 TRUE self
			)
		)
		(Cls)
		(super newRoom: n)
	)
)

(instance steam of Prop
	(properties
		view			vDarkAlleyExterior
		loop			lSteam
		cel			4
		x				133
		y				162
		cycleSpeed	12
		detailLevel	3
	)
)

(instance desmond of Actor
	(properties
		view			vDesmond
		loop			lDesmondLeaning
		cel			0
		x				31
		y				121
		cycleSpeed	6
		moveSpeed	6
	)
)

(instance arrow of Prop
	(properties
		view			vDarkAlleyExterior
		loop			lArrow
		cel			1
		x				286
		y				75
		priority		15
		signal		fixPriOn
		detailLevel	4
	)
)

(instance reflection of Actor
	(properties
		view			vPattiInAlley
		loop			lReflectWest
		priority		1
		signal		fixPriOn
		cycleSpeed	6
		moveSpeed	6
	)

	(method (doit)
		(= x (ego x?))
		(= y (+ 125 (- 125 (ego y?))))
		(= cel (ego cel?))
	)
)

(instance sCartoon of Script
	(method (changeState newState)
		(switchto (= state newState)
			(
				(ego
					x:				262,
					y: 			111,
					cycleSpeed	6,
					moveSpeed	6,
					setLoop: 	0,
					setMotion: 	MoveTo 226 125 self
				)
				(reflection:
					init:,
				)
				(theMusic
					number:		mDesmondTheme,
					flags:		TRUE,
					setLoop:		-1,
					play:
				)
			)
			(
				(TPrint "Entering the brisk night air, you notice a dark
					figure leaning against the far wall." 
					BIG)
				(ego 
					setLoop: 	0,
					setMotion: 	MoveTo 157 125 self
				)
				(desmond setScript: sDesmondCartoon)
				(= cycles 20)
			)
			(
				(TPrint "Evidently, he has noticed you, too." 
					HIGH)
				(TPrint "You aren't afraid.
					You've handled rough situations before.
					After all, think of all you went through back in 
					\"Leisure Suit Larry 3!\"" 
					BIG)
			)
			(
				(ego
					setLoop: lStanding,		;standing 
					setCel: 0,		;west
					setCycle: 0
				)
				(reflection
					setLoop: lReflectStanding,		;standing
					setCel: 0,		;west
					setCycle: 0
				)
				(self dispose:)
			)
		)
	)
);sCartoon


(instance sDesmondCartoon of Script
	(method (changeState newState)
		(switchto (= state newState)
			(
				(desmond
					setLoop: 	lDesmondTrans,
					setCel: 		0,
					setCycle: 	EndLoop self
				)
			)
			(
				(desmond
					setLoop: 	lDesmondEast,
					setCel: 		0,
					setCycle: 	Walk,
					setMotion: 	MoveTo 130 125 self
				)
			)
			(
				(desmond
					setLoop: 	lDesmondStandEast,
					setCel: 		0,
					setCycle: 	0
				)
				(= seconds 3)
			)
			(
				(curRoom newRoom: pDarkAlley2Shot)
			)
		)
	)
)

(instance sCartoon2 of Script
	(method (changeState newState)
		(switchto (= state newState)
			(
				(theMusic
					number:		mDesmond2,
					flags:		TRUE,
					setLoop:		-1,
					play:
				)
				(= ticks 123)
				;**	walk both of them off to the west until they're off screen.
				;**	Do an iris in to black.  Pause 3 seconds.  Iris out from black
				;**	on room 420
			)
			(
				(reflection loop: lReflectWest)
				(ego
					cycleSpeed	6,
					moveSpeed	6,
					setLoop: lWalkingWest,		;walking west 
					setCycle: Walk,
					setMotion: 	MoveTo 139 125 self
				)
				(desmond
					setLoop: 	lDesmondWest,
					setCel: 		0,
					cycleSpeed:	(ego cycleSpeed?),
					moveSpeed:	(ego moveSpeed?),
					setCycle: 	Walk,
					setMotion: 	MoveTo -10 138	;0 135
				)
			)
			(
				(reflection dispose:)	;no more water to walk over
				(ego
					setMotion: 	MoveTo 0 145 self
				)
			)
			(
				(steam dispose:)
				(arrow dispose:)
				(desmond dispose:)
				(ego dispose:)
				(DrawPic 1 (+ IRISOUT BLACKOUT))
				(CenterDisplay 4 colWhite
					"The following morning,\n
					in a much nicer area of the city,\n
					Patti meets Desmond at \n
					F.B.I. Headquarters..."
				)
				(= seconds 7)
			)
			(
;wjh				(theMusic
;wjh					fade:	0 15 12 TRUE self
;wjh				)
				(curRoom newRoom: mDesmond2)
			)
		)
	)
)
