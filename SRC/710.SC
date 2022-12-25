;**
;**	Logics for Room 710 -- "Tramp Casino" Interior
;**
;**	LSL5:  Passionate Patti Does A Little Undercover Work
;**				Copyright 1991
;**				by Al Lowe
;**
;**	Programmed by Brian K. Hughes
;**
;**	Last Update:  September 10, 1991
;**

(script#		710)

(public
	rm710		0
)


(define	pCasinoInterior			710)
(define	vCasinoInterior			710)
(define		lGambler0				0)
(define		lGambler1				1)
(define		lGambler2				2)
(define		lGambler3				3)
(define		lGambler4				4)
(define		lGambler5				5)
(define	vLarryInCasino				711)
;(define		lWalking					0-3)

(define	mCasino						710)
(define	sCasinoFX					711)

(define northXLeft		115)
(define northXRight		205)
(define northY				41)

(instance rm710 of LLRoom
	(properties
		picture		pCasinoInterior
		north			730
		south			700
		lookStr		"Tramp's Casino has spared no expense to create the
						ultimate in tasteless luxuriant opulent detritus.
						Video poker machines surround you to such an extent that
						you are unsure how to walk around them."
	)

	(method (init)
		(ego
			init:			,
			normalize:	vLarryInCasino
		)
		(switch prevRoomNum
			(720								; Video poker - ego's X, Y, Loop should
				(ego edgeHit:	0)			;	remain the same since ego's global
				(theIconBar disable: useIcon)
			)
			(north
				(HandsOff)
				(ego
					setHeading:	180,
					edgeHit:		0
				)
				(if (Btst fCasinoSide)
					(self setScript: sFromNorth NULL TRUE)
				else
					(self setScript: sFromNorth NULL FALSE)
				)
			)
			(south
				(ego x: 144)
			)
			(else
				(HandsOn)
				(ego
					posn: 	160 160,
					edgeHit:	0
				)
			)
		)
		(super init:)
		(girlPic1 		init:)
		(girlPic2 		init:)
		(rouletteArea 	init:, approachVerbs: verbTalk)
		(blackjackArea init:, approachVerbs: verbTalk)
		(chandelier 	init:)
		(chandelier2 	init:)
		(ballroom 		init:)
		(statue1 		init:, approachVerbs: verbDo verbUse)
		(statue2 		init:, approachVerbs: verbDo verbUse)
		(statue3 		init:, approachVerbs: verbDo verbUse)
		(statue4 		init:, approachVerbs: verbDo verbUse)
		(statue5 		init:, approachVerbs: verbDo verbUse)
		(poker1 			init:, approachVerbs: verbDo verbUse)
		(poker3 			init:, approachVerbs: verbDo verbUse)
		(poker5 			init:, approachVerbs: verbDo verbUse)
		(poker6 			init:, approachVerbs: verbDo verbUse)
		(poker7 			init:, approachVerbs: verbDo verbUse)
		(poker8 			init:, approachVerbs: verbDo verbUse)
		(poker9 			init:, approachVerbs: verbDo verbUse)
		(poker10 		init:, approachVerbs: verbDo verbUse)
		(poker11 		init:, approachVerbs: verbDo verbUse)
		(poker12 		init:, approachVerbs: verbDo verbUse)
		(poker15 		init:, approachVerbs: verbDo verbUse)
		(poker16 		init:, approachVerbs: verbDo verbUse)
		(poker17 		init:, approachVerbs: verbDo verbUse)
		(poker18 		init:, approachVerbs: verbDo verbUse)
		(poker19 		init:, approachVerbs: verbDo verbUse)
		(poker20 		init:, approachVerbs: verbDo verbUse)
		(poker21 		init:, approachVerbs: verbDo verbUse)
		(poker22 		init:, approachVerbs: verbDo verbUse)
;**			(curRoom addObstacle:
;**				((Polygon new:)
;**					type: PBarredAccess,
;**					init:	0 0 99 0 126 46 126 62 136 62 136 72 118 88 108 88 100 76
;**							72 76 55 94 113 94 108 126 121 126 123 88 151 88 151 138
;**							103 138 103 146 41 146 50 134 50 129 48 129 46 135 29 135
;**							15 160 121 160 128 176 128 189 0 189,
;**					yourself
;**				)
;**				((Polygon new:)
;**					type: PBarredAccess,
;**					init:	319 0 319 189 154 189 165 184 223 184 246 174 273 174 273 154
;**							239 154 239 150 197 150 197 136 307 136 294 100 285 100
;**							275 112 257 112 243 100 220 100 227 115 263 115 263 124
;**							161 124 161 114 197 114 194 101 161 101 159 90 229 90 224 73
;**							188 73 188 63 222 63 219 52 201 52 190 46 215 0,
;**					yourself
;**				)
;**				((Polygon new:)
;**					type: PBarredAccess,
;**					init:	136 154 194 154 191 165 138 165,
;**					yourself
;**				)
;**				((Polygon new:)
;**					type: PBarredAccess,
;**					init:	161 0 190 40 182 46 136 46 129 41,
;**					yourself
;**				)
		(curRoom addObstacle:
			((Polygon new:)
				type: PBarredAccess,
				init:	0 0 99 0 126 46 126 62 136 62 136 72 118 88 108 88 100 76
						72 76 55 94 113 94 108 126 121 126 123 88 151 88 151 138
						105 138 105 146 41 146 50 134 50 129 48 129 46 135 29 135
						15 160 121 160 128 176 128 189 0 189,
				yourself
			)
			((Polygon new:)
				type: PBarredAccess,
				init:	319 0 319 189 154 189 165 184 223 184 246 174 273 174 273 154
						239 154 239 150 197 150 197 136 307 136 294 100 285 100
						275 112 257 112 243 100 220 100 227 115 263 115 263 124
						161 124 161 114 197 114 194 101 161 101 159 90 229 90 224 73
						188 73 188 63 222 63 219 52 201 52 190 46 215 0,
				yourself
			)
			((Polygon new:)
				type: PBarredAccess,
				init:	128 154 194 154 191 165 133 165,
				yourself
			)
			((Polygon new:)
				type: PBarredAccess,
				init:	161 0 190 40 182 46 136 46 129 41,
				yourself
			)
		)
		(if
			(== prevRoomNum 700)
			(theMusic fade: 127 10 10 FALSE)
		else
			(theMusic
				number:		mCasino,
				setLoop:		-1,
				flags:		1,
				play:			127,
			)
		)
		(theMusic2
			number:		sCasinoFX,
			setLoop:		-1,
			flags:		1,
			play:			127,
		)
		(person0 init:)
		(person1 init:)
		(person2 init:)
		(person3 init:)
		(person4 init:)
		(person5 init:)
		(if (Btst fDidLana)
			(rope1
				init:,
				stopUpd:
			)
			(rope2
				init:,
				stopUpd:
			)
		)
	)
	(method (doit)
		(super doit:)
		(cond
			(script)
			((and
				(Btst fDidLana)
				(StepOn ego (| cBLUE cGREEN))
			 )
				(HandsOff)
				(curRoom setScript: sClosed)
			)
			((StepOn ego cBLUE)
				(ego edgeHit: NORTH)
				(HandsOff)
				(self setScript: sToNorth NULL TRUE)
			)
			((StepOn ego cGREEN)
				(ego edgeHit: NORTH)
				(HandsOff)
				(self setScript: sToNorth NULL FALSE)
			)
		)
	)
	(method (newRoom n)
		(if
			(!= n 700)
			(theMusic fade:)
		)
		(theMusic2 fade:)
		(super newRoom: n)
	)
)


;ษอออออออออออออออออออป
;บ                   บ
;บ Scripts				บ
;บ                   บ
;ศอออออออออออออออออออผ

(instance sClosed of Script
	(method (changeState newState)
		(switchto (= state newState)
			(
				(TPrint "The Tramp Casino Ballroom is presently closed.")
				(ego
					setMotion:	PolyPath (ego x?) (+ (ego y?) 4) self
				)
			)
			(
				(HandsOn)
				(self dispose:)
			)
		)
	)
)


(instance sToNorth of Script
	(method (changeState newState)
		(switchto (= state newState)
			(
				(ego
					setPri:		0,
					setMotion:	MoveTo (if register northXLeft else northXRight) northY self
				)
			)
			(
				(ego
					setPri:		RELEASE
				)
				(if register (Bset fCasinoSide) else (Bclr fCasinoSide))
				(curRoom newRoom: (curRoom north?))
			)
		)
	)
)

(instance sFromNorth of Script
	(method (changeState newState)
		(switchto (= state newState)
			(
				(ego
					setPri:		0,
					x:				(if register northXLeft else northXRight),
					y:				northY,
					setMotion:	MoveTo (if register 136 else 184) 48 self
				)
			)
			(
				(ego
					setPri:		RELEASE,
					setHeading: 180 self
				)
			)
			(
				(HandsOn)
				(self dispose:)
			)
		)
	)
)


;ษอออออออออออออออออออป
;บ                   บ
;บ Props					บ
;บ                   บ
;ศอออออออออออออออออออผ


(instance person0 of Prop
	(properties
		view			710
		loop			0
		x				29
		y				132
		signal		ignrAct
		description "the gambler"
		lookStr		"All of these gamblers look compulsive to you."
	)
	(method (init)
		(super init:)
		(self setCycle: BegLoop person1)
	)
	(method (cue)
		(super cue:)
		(self setCycle: BegLoop person1)
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbDo
				(TPrint "Leave them alone, Larry; they're concentrating
					on losing.")
			)
			(else
				(super doVerb: theVerb invItem)
			)
		)
	)
)
(instance person1 of Prop
	(properties
		view			710
		loop			1
		x				93
		y				143
		signal		ignrAct
	)
	(method (cue)
		(super cue:)
		(self setCycle: BegLoop person2)
	)
	(method (doVerb)
		(person0 doVerb: &rest)
	)
)

(instance person2 of Prop
	(properties
		view			710
		loop			2
		x				194
		y				184
		signal		ignrAct
	)
	(method (cue)
		(super cue:)
		(self setCycle: BegLoop person3)
	)
	(method (doVerb)
		(person0 doVerb: &rest)
	)
)

(instance person3 of Prop
	(properties
		view			710
		loop			3
		x				294
		y				171
		priority		14
		signal		(| ignrAct fixPriOn)
	)
	(method (cue)
		(super cue:)
		(self setCycle: BegLoop person4)
	)
	(method (doVerb)
		(person0 doVerb: &rest)
	)
)

(instance person4 of Prop
	(properties
		view			710
		loop			4
		x				261
		y				152
		signal		ignrAct
	)
	(method (cue)
		(super cue:)
		(self setCycle: BegLoop person5)
	)
	(method (doVerb)
		(person0 doVerb: &rest)
	)
)

(instance person5 of Prop
	(properties
		view			710
		loop			5
		x				73
		y				75
		signal		ignrAct
	)
	(method (cue)
		(super cue:)
		(self setCycle: BegLoop person0)
	)
	(method (doVerb)
		(person0 doVerb: &rest)
	)
)

(instance rope1 of View
	(properties
		view			710
		loop			6
		x				131
		y				36
		priority		1
		signal		(| ignrAct fixPriOn)
	)
)

(instance rope2 of View
	(properties
		view			710
		loop			6
		x				195
		y				37
		priority		1
		signal		(| ignrAct fixPriOn)
	)
)


;ษอออออออออออออออออออป
;บ                   บ
;บ Features          บ
;บ                   บ
;ศอออออออออออออออออออผ

(instance girlPic1 of Feature
	(properties
		x 				56
		y 				21
		nsLeft 		0
		nsTop 		1
		nsBottom 	41
		nsRight 		112
 		description "the painting"
		sightAngle 	40
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbLook
				(TPrint "You find the casino's artwork totally tasteless...")
				(TPrint "...and fascinating!")
			)
			(verbDo
				(TPrint "Don't touch those paintings!")
			)
			(else
				(super doVerb: theVerb invItem &rest)
			)
		)
	)
)

(instance girlPic2 of Feature
	(properties
		x 				56
		y 				21
		nsLeft 		203
		nsTop 		1
		nsBottom 	43
		nsRight 		319
 		description "the painting"
		sightAngle 	40
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbLook
				(TPrint "You find the casino's artwork totally tasteless...")
				(TPrint "...and fascinating!")
			)
			(verbDo
				(TPrint "Don't touch those paintings!")
			)
			(else
				(super doVerb: theVerb invItem &rest)
			)
		)
	)
)

(instance rouletteArea of Feature
	(properties
		x 				23
		y 				70
		nsLeft 		0
		nsTop 		45
		nsBottom 	96
		nsRight 		46
 		description "the roulette tables"
		lookStr 		"You don't know how to play roulette."
		sightAngle 	40
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbDo
				(TPrint "You? Play roulette? HA!")
			)
			(verbTalk
				(TPrint "The roulette players are too busy to talk to you.")
			)
			(verbUse
				(switch invItem
					(iSilverDollar
						(TPrint "Save your money, Larry!
							You'll only lose anyway!")
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

(instance blackjackArea of Feature
	(properties
		x 				278
		y 				62
		nsLeft 		237
		nsTop 		42
		nsBottom 	82
		nsRight 		319
 		description "the blackjack tables"
		lookStr 		"Blackjack is just not your game.
						You can remember that much from \"Leisure Suit Larry 1!\""
		sightAngle 	40
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbDo
				(TPrint "If you want to play blackjack,
					buy \"Leisure Suit Larry 1!\"")
				(TPrint "(Another fine Al Lowe game!)" LOW)
			)
			(verbTalk
				(TPrint "The blackjack players are too busy to talk to you.")
			)
			(verbUse
				(switch invItem
					(iSilverDollar
						(TPrint "Save your money, Larry! You'll only lose anyway!")
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

(instance chandelier of Feature
	(properties
		x 				164
		y 				107
		z				73
		nsLeft 		134
		nsTop 		46
		nsBottom 	86
		nsRight 		184
 		description "the chandelier"
		lookStr 		"Isn't it wonderful what they're doing
						with polymers these days?"
		sightAngle 	40
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbDo
				(TPrint "You try your best, but just can't reach the
					chandelier from your current (low) altitude.")
			)
			(else
				(super doVerb: theVerb invItem &rest)
			)
		)
	)
)
(instance chandelier2 of Feature
	(properties
		x 164
		y 107
		z 73
		heading 0
		nsLeft 151
		nsTop 25
		nsBottom 44
		nsRight 177
 		description "the chandelier"
		lookStr 		"Isn't it wonderful what they're doing
						with polymers these days?"
	)
	(method (doVerb theVerb invItem)
		(chandelier doVerb: theVerb invItem)
	)
)

(instance ballroom of Feature
	(properties
		x 				156
		y 				22
		nsLeft 		120
		nsTop 		0
		nsBottom 	45
		nsRight 		199
 		description "the ballroom entrance"
		lookStr 		"It appears a crowd has gathered inside
						the exclusive Tramp Ballroom."
		sightAngle 	40
	)
)

(instance statue1 of Feature
	(properties
		x 				229
		y 				170
		nsLeft 		212
		nsTop 		151
		nsBottom 	189
		nsRight 		246
 		description "the statue"
		lookStr 		"Naked girls holding balloon bouquets have been a favorite
						subject for the world's greatest artists for many
						centuries."
		sightAngle 	40
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbDo
				(TPrint "Larry! She's only a statue!")
			)
			(else
				(super doVerb: theVerb invItem &rest)
			)
		)
	)
)

(instance statue2 of Feature
	(properties
		x 				258
		y 				74
		nsLeft 		248
		nsTop 		45
		nsBottom 	104
		nsRight 		268
		sightAngle 	40
	)
	(method (doVerb)
		(statue1 doVerb: &rest)
	)
)

(instance statue3 of Feature
	(properties
		x 				69
		y 				109
		nsLeft 		55
		nsTop 		86
		nsBottom 	133
		nsRight 		84
		sightAngle 	40
	)
	(method (doVerb)
		(statue1 doVerb: &rest)
	)
)

(instance statue4 of Feature
	(properties
		x 				116
		y 				51
		nsLeft 		108
		nsTop 		28
		nsBottom 	75
		nsRight 		124
		sightAngle 	40
	)
	(method (doVerb)
		(statue1 doVerb: &rest)
	)
)

(instance statue5 of Feature
	(properties
		x 				159
		y 				125
		nsLeft 		148
		nsTop 		96
		nsBottom 	154
		nsRight 		170
 		description "the statue"
		lookStr 		"What a set of balloons she has!"
		sightAngle 	40
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbDo
				(TPrint "You find the faux marble cool and exciting to
					the touch!")
			)
			(else
				(super doVerb: theVerb invItem &rest)
			)
		)
	)
)

(instance poker1 of Feature
	(properties
		x 				48
		y 				82
		nsLeft 		43
		nsTop 		66
		nsBottom 	99
		nsRight 		54
		approachX	70
		approachY	93
		sightAngle 	40
 		description "the video poker machine"
	)
	(method (doVerb theVerb invItem &tmp [str 100])
		(switch theVerb
			(verbLook
				(Format @str "The only machines accessible to you are these
					progressive video poker machines.
					You notice the jackpot is up to $%d!"
					pokerJackpot
				)
				(TPrint @str)
			)
			(verbDo
				(if (ego has: iSilverDollar)
					(Format @str "You insert all %d of your silver dollars
						into the video poker machine."
						silvDollars
					)
					(Points ptsPlayedPoker pfPlayedPoker)
					(TPrint @str)
					(curRoom newRoom: 720)
				else
					(TPrint "You have no silver dollars with which to play this
						machine.")
				)
			)
			(verbUse
				(switch invItem
					(iSilverDollar
						(if (ego has: iSilverDollar)
							(Format @str "You insert all %d of your silver dollars
								into the video poker machine."
								silvDollars
							)
							(TPrint @str)
							(curRoom newRoom: 720)
						else
							(TPrint "You have no silver dollars with which to play
								this machine.")
						)
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

(instance poker3 of Feature
	(properties
		x 				95
		y 				115
		nsLeft 		84
		nsTop 		80
		nsBottom 	135
		nsRight 		107
		approachX	109
		approachY	115
		sightAngle 	40
	)
	(method (doVerb)
		(poker1 doVerb: &rest)
	)
)

(instance poker5 of Feature
	(properties
		x 				127
		y 				127
		nsLeft 		108
		nsTop 		118
		nsBottom 	136
		nsRight 		146
		approachX	148
		approachY	135
		sightAngle 	40
	)
	(method (doVerb)
		(poker1 doVerb: &rest)
	)
)

(instance poker6 of Feature
	(properties
		x 				136
		y 				99
		nsLeft 		127
		nsTop 		82
		nsBottom 	117
		nsRight 		146
		approachX	151
		approachY	119
		sightAngle 	40
	)
	(method (doVerb)
		(poker1 doVerb: &rest)
	)
)

(instance poker7 of Feature
	(properties
		x 				212
		y 				90
		nsLeft 		177
		nsTop 		83
		nsBottom 	98
		nsRight 		247
		approachX	226
		approachY	106
		sightAngle 	40
	)
	(method (doVerb)
		(poker1 doVerb: &rest)
	)
)

(instance poker8 of Feature
	(properties
		x 				207
		y 				113
		nsLeft 		170
		nsTop 		105
		nsBottom 	122
		nsRight 		245
		approachX	212
		approachY	130
		sightAngle 	40
	)
	(method (doVerb)
		(poker1 doVerb: &rest)
	)
)

(instance poker9 of Feature
	(properties
		x 				292
		y 				90
		nsLeft 		270
		nsTop 		82
		nsBottom 	99
		nsRight 		315
		approachX	286
		approachY	107
		sightAngle 	40
	)
	(method (doVerb)
		(poker1 doVerb: &rest)
	)
)

(instance poker10 of Feature
	(properties
		x 				309
		y 				124
		nsLeft 		301
		nsTop 		99
		nsBottom 	149
		nsRight 		318
		approachX	304
		approachY	126
		sightAngle 	40
	)
	(method (doVerb)
		(poker1 doVerb: &rest)
	)
)

(instance poker11 of Feature
	(properties
		x 				238
		y 				138
		nsLeft 		208
		nsTop 		129
		nsBottom 	148
		nsRight 		300
		approachX	238
		approachY	154
		sightAngle 	40
	)
	(method (doVerb)
		(poker1 doVerb: &rest)
	)
)

(instance poker12 of Feature
	(properties
		x 				273
		y 				185
		nsLeft 		246
		nsTop 		164
		nsBottom 	185
		nsRight 		319
		approachX	273
		approachY	174
		sightAngle 	40
	)
	(method (doVerb)
		(poker1 doVerb: &rest)
	)
)

(instance poker15 of Feature
	(properties
		x 				48
		y 				116
		nsLeft 		8
		nsTop 		107
		nsBottom 	125
		nsRight 		55
		approachX	48
		approachY	131
		sightAngle 	40
	)
	(method (doVerb)
		(poker1 doVerb: &rest)
	)
)

(instance poker16 of Feature
	(properties
		x 				10
		y 				146
		nsLeft 		0
		nsTop 		126
		nsBottom 	167
		nsRight 		20
		approachX	25
		approachY	143
		sightAngle 	40
	)
	(method (doVerb)
		(poker1 doVerb: &rest)
	)
)

(instance poker17 of Feature
	(properties
		x 				59
		y 				162
		nsLeft 		21
		nsTop 		149
		nsBottom 	168
		nsRight 		111
		approachX	59
		approachY	160
		sightAngle 	40
	)
	(method (doVerb)
		(poker1 doVerb: &rest)
	)
)

(instance poker18 of Feature
	(properties
		x 				112
		y 				179
		nsLeft 		104
		nsTop 		169
		nsBottom 	189
		nsRight 		121
		approachX	128
		approachY	188
		sightAngle 	40
	)
	(method (doVerb)
		(poker1 doVerb: &rest)
	)
)

(instance poker19 of Feature
	(properties
		x 				80
		y 				63
		nsLeft 		56
		nsTop 		55
		nsBottom 	72
		nsRight 		105
		approachX	93
		approachY	77
		sightAngle 	40
	)
	(method (doVerb)
		(poker1 doVerb: &rest)
	)
)

(instance poker20 of Feature
	(properties
		x 				228
		y 				63
		nsLeft 		221
		nsTop 		44
		nsBottom 	82
		nsRight 		236
		approachX	224
		approachY	80
		sightAngle 	40
	)
	(method (doVerb)
		(poker1 doVerb: &rest)
	)
)

(instance poker21 of Feature
	(properties
		x 				207
		y 				64
		nsLeft 		194
		nsTop 		57
		nsBottom 	72
		nsRight 		220
		approachX	209
		approachY	81
		sightAngle 	40
	)
	(method (doVerb)
		(poker1 doVerb: &rest)
	)
)

(instance poker22 of Feature
	(properties
		x 				100
		y 				128
		nsLeft 		79
		nsTop 		118
		nsBottom 	138
		nsRight 		121
		approachX	111
		approachY	144
		sightAngle 	40
	)
	(method (doVerb)
		(poker1 doVerb: &rest)
	)
)

