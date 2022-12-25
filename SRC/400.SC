;**
;**	Logics for Room 400 -- The Piano Pit
;**
;**	LSL5:  Passionate Patti Does A Little Undercover Work
;**				Copyright 1991
;**				by Al Lowe
;**
;**	Programmed by John Hartin
;**
;**	Last Update:  September 9, 1991
;**

(script#		400)

(public
	rm400		0
)


;(define	vRoom							400)
(define	pPianoPit						400)
(define	vPianoPit					400)
	(define	lPatrons					0)
(define vPattiBowing	401)
	(define lPattiBows		0)

(define	pClubOwnersOffice			405)
(define	pDarkAlleyExterior			410)
(define	pDesmondsOffice				420)
(define	pWorkshopWest					430)


(define	sOneHandClap	401)
(define		sPFootfall			402)
(define		sPFootCreak			403)
(define		sPFootfall2			404)			;TBA


(local
	seenMsg
	didPlay
	clapCnt
)


(instance rm400 of LLRoom
	(properties
		picture		pPianoPit
		east			pClubOwnersOffice
		west			pDarkAlleyExterior
	)

	(method (init)
		(LoadMany VIEW
			vPianoPit
			vPattiBowing
		)
		(LoadMany SOUND
			sOneHandClap
			sPFootfall
			sPFootCreak
			sPFootfall2
		)
		(ego
			init:			,
		)

		(HandsOff)
		(switch prevRoomNum
			(east
				(ego
					posn:			285 100,
					normalize:	vPatti
				)
				(ego setScript: sFromManager)
			)
			(else
				(theMusic stop:)
				(theMusic2 stop:)
	 			(self style: PIXELDISSOLVE)
				(ego
					loop:			facingNorth,
					cel:			2,
					posn:			195 96,
					normalize:	vPattiBowing,
				)
				(ego setScript: sToManager)
			)
		)

		(super init:)
		(SetFFRoom pWorkshopWest)
	)
;**
;*	The following code borrowed from room 260 (airport). Modified for
;* our needs.
;**
	(method (doit)
		(super doit:)

		(if (ego mover?)

			(switch (ego cel?)
				(0
					(if (== (stepSound number?) sPFootfall2)
						(stepSound
							number:	sPFootfall,
							play:
						)
					else
						(stepSound
							number:	(+ (stepSound number?) 1),
							play:
						)
					)
				)
				(4
					(if (== (stepSound number?) sPFootfall2)
						(stepSound
							number:	sPFootfall,
							play:
						)
					else
						(stepSound
							number:	(+ (stepSound number?) 1),
							play:
						)
					)
				)
			)
		)
	)
	(method (dispose)
		(theMusic
			fade:	0 15 12 TRUE
		)
		(super dispose: &rest)
	)
)

(instance patrons of Prop
	(properties
		view			vPianoPit
		loop			lPatrons
		cel			0
		x				183
		y				81
		cycleSpeed  18
	)
	(method (doit)
		(if (< clapCnt 4)
			(if (== (self cel?) 1)
				(if (not didPlay)
					(theMusic
						number:		sOneHandClap,
						flags:		TRUE,
						loop:			0,
						play:
					)
					(++ clapCnt)
					(= didPlay TRUE)
				)
		 	else
				(= didPlay FALSE)
			)
		else
			(if (== clapCnt 4)
				(self
					setCycle: 0,
					stopUpd:
				)
			)
		)
		(super doit:)
	)
)

(instance stepSound of Sound
	(properties
		number	sPFootfall2
	)
)

(instance sToManager of Script
	(method (changeState newState)
		(switchto (= state newState)
			(
				(patrons
					init:,
					setCycle: Forward,
				)
				(ego
					setLoop: lPattiBows,
					setCycle: EndLoop self,
					cycleSpeed: 12
				)
			)
			(
				(ego setCycle: BegLoop self)
			)
			(
				(ego
					view:	vPatti ,
					setLoop:8,
					setCel: facingNorthEast,
				)
				(= seconds 4)
			)
			(
				(Say ego "\"Oh, great,\" you think, \"another lousy crowd.
					And on a Saturday night, too.\"" BIG)
				(= ticks 30)
			)
			(
				(Say ego "\"Too many drinkers and talkers; too few listeners,\"
					you think.  \"Are there no good places left to play?\"" BIG)
				(= ticks 30)
			)
			(
				(Say ego "\"Another grimy week on a grimy stage before
					another grimy scene in a grimy office with another
					grimy little night club manager!\"" BIG)
				(= seconds 3)
			)
			(
				(TPrint "(Just what did you expect from a club called
					\"The Piano Pit,\" Patti?)" LOW)
				(= seconds 3)
			)
			(
				(Say ego "Finishing your bows, you head for the manager's office.
					\"Now I suppose I'll have to endure another scene with
					that cheap club manager.
					The perfect way to top off a perfect week,\"
					you think sarcastically." BIG)
				(ego
					normalize: ,
					cycleSpeed	6,
					moveSpeed	6,
					setMotion:	MoveTo 270 95 self,
				)
			)
			(
				(Say ego "\"Oh, well,\" you sigh,
					\"Chin up! Positive attitude!\"")
				(TPrint "(Right. You feel positive he'll be a jerk!)" LOW)
				(curRoom newRoom: (curRoom east?))
			)
		)
	)
);sToManager

(instance sFromManager of Script
	(method (changeState newState)
		(switchto (= state newState)
			(
				(ego
					cycleSpeed	6,
					moveSpeed	6,
					setMotion:	MoveTo 160 100 self,
				)
			)
			(
				(Say ego "\"That pig!\" you think disgustedly.
					\"Gawd, I wish there was some way to get even with the rotten
					crooks that run this stinking business!\"")
				(ego
					setMotion:	MoveTo 32 100 self,
				)
			)
			(
				(TPrint "Poor Patti.
					Once again, you're \"too hip for the room!\"")
				(curRoom newRoom: (curRoom west?))
			)
		)
	)
);sFromManager
