;**
;**	Logics for Room 780 -- Under the Boardwalk
;**
;**	LSL5:  Passionate Patti Does A Little Undercover Work
;**				Copyright 1991
;**				by Al Lowe
;**
;**	Programmed by Brian K. Hughes & Oliver Brelsford
;**
;**	Last Update:  September 12, 1991
;**

(script#		780)

(public
	rm780		0
)


(define	pUnderTheBoardwalk			780)
(define	vRoom							780)
	(define	lPierWaves			0-3)
(define	vLarryRoom					781)
	(define	lSwimmingEast		0)
	(define	lSwimmingWest		1)



(instance rm780 of LLRoom
	(properties
		picture		pUnderTheBoardwalk
		north			760
		west			700
		lookStr		"Now you've done it!
						You're swimming along under the beautiful Tramp Boardwalk,
						having a wonderful time.
						The question is:  how far from shore are you?"
	)

	(method (init)
		(ego
			init:			,
			normalize:	vLarryRoom,
			setCycle:	Forward,
			actions:		swimActions,
		)
		(if (!= prevRoomNum 700)
			(HandsOn)
			(ego
				posn:			160 175,
				setHeading:	0,
				edgeHit:		0
			)
		)

		(super 		init:)
		(sky			init:)
		(boardwalk 	init:)
		(post1		init:)
		(post2 		init:)
		(post3 		init:)
		(post4 		init:)
		(wave1
			setCycle:	Forward,
			init:
		)
		(wave2
			setCycle:	Forward,
			init:
		)
		(wave3
			setCycle:	Forward,
			init:
		)
		;**	(curRoom addObstacle:
		;**		((Polygon new:)
		;**			type: PBarredAccess,
		;**			init:	0 0 319 0 319 155 175 155 175 158 115 158 115 155 0 155,
		;**			yourself
		;**		)
		;**		((Polygon new:)
		;**			type: PBarredAccess,
		;**			init:	8 180 8 166 157 166 157 180,
		;**			yourself
		;**		)
		;**		((Polygon new:)
		;**			type: PBarredAccess,
		;**			init:	162 180 162 166 242 166 242 180,
		;**			yourself
		;**		)
		;**		((Polygon new:)
		;**			type: PBarredAccess,
		;**			init:	266 180 266 166 319 166 319 180,
		;**			yourself
		;**		)
		;**		((Polygon new:)
		;**			type: PBarredAccess,
		;**			init:	0 189 0 187 319 187 319 189,
		;**			yourself
		;**		)
		;**	)
		(curRoom addObstacle:
			((Polygon new:)
				type: PBarredAccess,
				init:	0 0 319 0 319 155 175 155 175 159 115 159 115 155 0 155,
				yourself
			)
			((Polygon new:)
				type: PBarredAccess,
				init:	8 180 8 166 157 166 157 180,
				yourself
			)
			((Polygon new:)
				type: PBarredAccess,
				init:	162 180 162 166 242 166 242 180,
				yourself
			)
			((Polygon new:)
				type: PBarredAccess,
				init:	266 180 266 166 319 166 319 180,
				yourself
			)
			((Polygon new:)
				type: PBarredAccess,
				init:	0 189 0 187 319 187 319 189,
				yourself
			)
		)
	)

	(method (doit)
		(super doit:)
		(cond
			(script)
			((StepOn ego cGREEN)
				(self setScript: sScroll NULL EAST)
			)
			((StepOn ego cCYAN)
				(if boardWalkDist
					(self setScript: sScroll NULL WEST)
				)
			)
		)
	)
)


(instance sScroll of Script
	;
	; register - direction, EAST or WEST
	;
	(method (changeState newState)
		(switchto (= state newState)
			(
				(HandsOff)
				(ego setMotion: MoveFwd 40 self)
			)
			(
				(if (== register WEST)
					(-- boardWalkDist)
					(ego x: (+ (ego x?) 320))
					(curRoom drawPic: (curRoom picture?) SCROLLRIGHT)
				else
					(++ boardWalkDist)
					(ego x: (- (ego x?) 320))
					(curRoom drawPic: (curRoom picture?) SCROLLLEFT)
				)
				(= cycles 1)
			)
			(
				(HandsOn)
				(self dispose:)
			)
		)
	)
);sScroll


;ษอออออออออออออออออออป
;บ                   บ
;บ Props					บ
;บ                   บ
;ศอออออออออออออออออออผ

(instance wave1 of Prop
	(properties
		view		 	780
		loop			0
		x				146
		y				145
		signal		(| ignrAct fixPriOn)
		priority		1
		detailLevel 3
		description "the waves"
		lookStr		"It's a little choppy under here."
	)
	(method (doVerb theVerb invItem)
		(super doVerb: theVerb invItem)
	)
)

(instance wave2 of Prop
	(properties
		view		 	780
		loop			1
		x				115
		y				180
		signal		(| ignrAct fixPriOn)
		priority		1
		detailLevel 3
		description "the waves"
		lookStr		"Look! They're doing the wave!"
	)
	(method (doVerb theVerb invItem)
		(wave1 doVerb: theVerb invItem)
	)
)

(instance wave3 of Prop
	(properties
		view		 	780
		loop			2
		x				275
		y				179
		signal		(| ignrAct fixPriOn)
		priority		1
		detailLevel 3
		description "the waves"
		lookStr		"Look! It's the cast of \"Splash!\""
	)
	(method (doVerb theVerb invItem)
		(wave1 doVerb: theVerb invItem)
	)
)

;ษอออออออออออออออออออป
;บ                   บ
;บ Features          บ
;บ                   บ
;ศอออออออออออออออออออผ

(instance sky of Feature
	(properties
		x 				159
		y 				176
		z 				171		;?
		nsLeft 		0
		nsTop 		0
		nsBottom 	11
		nsRight 		319
 		description "the sky"
		lookStr 		"Looking at the sky, you remember a better time,
						just a few minutes ago in fact,
						when you were much closer to the sky
						than you are right now."
		sightAngle 	40
	)
)

(instance boardwalk of Feature
	(properties
		x 				159
		y 				176
		z 				122		;?
		nsLeft 		0
		nsTop 		12
		nsBottom 	96
		nsRight 		319
 		description "the boardwalk"
		lookStr 		"You have a strange sensation--almost
						like you're looking up someone's skirt!"
		sightAngle 	40
	)
)

(instance post1 of Feature
	(properties
		x 				44
		y 				134
		nsLeft 		26
		nsTop 		96
		nsBottom 	173
		nsRight 		62
		description "the post"
		lookStr 		"These posts look much taller than they did from above."
		sightAngle 40
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbDo
				(TPrint "You are unable to climb the slimy,
					slippery surface of the pilings.")
			)
			(else
				(super doVerb: theVerb invItem &rest)
			)
		)
	)
)

(instance post2 of Feature
	(properties
		x 				118
		y 				98
		nsLeft 		104
		nsTop 		21
		nsBottom 	175
		nsRight 		132
		sightAngle 	40
		description "the post"
	)
	(method (doVerb)
		(post1 doVerb: &rest)
	)
)

(instance post3 of Feature
	(properties
		x 				203
		y 				99
		nsLeft 		190
		nsTop 		21
		nsBottom 	177
		nsRight 		216
		sightAngle 	40
		description "the post"
	)
	(method (doVerb)
		(post1 doVerb: &rest)
	)
)

(instance post4 of Feature
	(properties
		x 				303
		y 				101
		nsLeft 		287
		nsTop 		24
		nsBottom 	179
		nsRight 		319
		sightAngle 	40
		description "the post"
	)
	(method (doVerb)
		(post1 doVerb: &rest)
	)
)

(instance swimActions of Actions
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbLook
				(TPrint "You are swimming beneath the boardwalk!")
			)
			(verbDo
				(TPrint "There's no need to dive, Larry.")
			)
			(else
				(return FALSE)
			)
		)
	)
)

