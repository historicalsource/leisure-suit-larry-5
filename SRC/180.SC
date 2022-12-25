;**
;**	Logics for Room 180 -- File room, PornProdCorp
;**
;**	LSL5:  Passionate Patti Does A Little Undercover Work
;**				Copyright 1991
;**				by Al Lowe
;**
;**	Programmed by Brian K. Hughes & Carlos Escobar
;**
;**	Last Update:  September 9, 1991
;**


(script#		180)

(public
	rm180		0
)

(define	pFileroom						180)
(define	vRoom							180)
(define		lCreditCard			0)
(define	vLarryRoom					181)
(define		lOpeningDrawer		0)
(define		lTakingFolders		1)
(define		lTakingCard			2)

(define	OPEN_DRAWER		0)
(define	TAKE_FILES		1)

(define 	sFileOpen			181)
(define 	sFileClose			182)


(local
	drawerOpen
)


(instance rm180 of LLRoom
	(properties
		picture		pFileroom
		south			160
		lookStr		"PPC's file room is filled with file cabinets which
						are filled with files."
	)

	(method (init)
		(self setRegions: HOLLYWOODRGN)
		(ego
			init:			,
			normalize:
		)
		(switch prevRoomNum
			(south
				(ego x: 200)
			)
			(else
				(HandsOn)
				(ego
					posn:		160 160,
					edgeHit:	0
				)
			)
		)

		(super init:)

		(imprinter		init:, approachVerbs: verbDo)
		(light			init:)
		(bookcase		init:)
		(nudePainting	init:)
		(desk				init:)
		(lamp				init:)
		(magnifier		init:)
		(drawer			init:, approachVerbs: verbDo)
		(loserDrawer1	init:, approachVerbs: verbDo)
		(loserDrawer2	init:, approachVerbs: verbDo)
		(loserDrawer3	init:, approachVerbs: verbDo)
		(books			init:)
		(fan				init:)
		(chair			init:)
		(fTwins			init:)
		(fRolls			init:)

		(curRoom addObstacle:
			((Polygon new:)
				type: PBarredAccess,
				init:	0 0 319 0 319 189 244 189 284 152 259 154 218 127 220 118
						163 121 96 119 80 136 82 141 54 168 158 168 165 189 0 189,
				yourself
			)
		)
	)
)


;ษอออออออออออออออออออป
;บ                   บ
;บ Objects           บ
;บ                   บ
;ศอออออออออออออออออออผ

(instance imprinter of Prop
	(properties
		view			vRoom
		x				128
		y				1162
		z				1000
		priority		15
		signal		(| fixPriOn ignrAct)
		approachX	137
		approachY	165
		description	"the credit card imprinter"
	)
	(method (init)
		(if (ego has: iGoldCard)
			(self setCel: 1)
		else
			(self setCel: 0)
		)
		(super init:)
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbLook
				(if (ego has: iGoldCard)
					(TPrint "The empty credit card imprinter holds no
						fascination for you.")
				else
					(TPrint "Say!
						An exclusive \"AeroDork Airlines Gold Card\"
						rests in the credit card imprinter.
						Probably just someone racking up a few charges
						on the ol' company dole!")
				)
			)
			(verbDo
				(if (ego has: iGoldCard)
					(TPrint "The imprinter is of no value to you.
						The Gold Card was all you needed.")
				else
					(self setScript: sGetCard)
				)
			)
			(else
				(super doVerb: theVerb invItem &rest)
			)
		)
	)
)

(instance drawer of Actor
	(properties
		view			vRoom
		loop			2
		x				266
		y				125
		signal		fixedLoop
		approachX	240
		approachY	140
 		description "the drawer"
		sightAngle 	40
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbLook
				(cond
					((ego has: iMM'sResume)
						(TPrint "The \"Winners\" drawer is now empty.")
					)
					(drawerOpen
						(TPrint "There are three resume files here.")
					)
					(else
						(TPrint "Why, look! Here it is!! You've found it!
							This drawer is labeled \"Winners!\"")
					)
				)
			)
			(verbDo
				(cond
					((ego has: iMM'sResume)
						(TPrint "There was nothing else of value in that drawer.")
					)
					(drawerOpen
						(self setScript: sDrawer NULL TAKE_FILES)
					)
					(else
						(self setScript: sDrawer NULL OPEN_DRAWER)
					)
				)
			)
			(else
				(super doVerb: theVerb invItem &rest)
			)
		)
	)
)


;ษอออออออออออออออออออป
;บ                   บ
;บ Scripts           บ
;บ                   บ
;ศอออออออออออออออออออผ

(instance sGetCard of Script
	(method (changeState newState)
		(switchto (= state newState)
			(
				(HandsOff)
				(ego
					view:			vLarryRoom,
					setLoop:		lTakingCard,
					setCel:		0,
					posn:			141 157,
					setPri:		15,
					cycleSpeed:	12,
					setCycle:	EndLoop self
				)
			)
			(
			 	(Points ptsGotAeroDorkCard pfGotAeroDorkCard)
				(ego setCycle: BegLoop self)
			)
			(
				(imprinter
					setCel: 			1,
					approachVerbs:	verbNone
				)
			 	(ego
					setLoop:		2,
					posn:			137 165,
					get: 			iGoldCard,
					normalize:
				)
				(= cycles 2)
			)
			(
			 	(TPrint "Assuming that your recent promotion now authorizes such
			 		extravagance, you remove the luxurious \"AeroDork Gold Card\"
					from the credit card imprinter."
				)
				(HandsOn)
				(self dispose:)
			)
		)
	)
);sCard

(instance sDrawer of Script
	;
	; register - either OPEN_DRAWER or TAKE_FILES
	;
	(enum
		reach4Drawer
		openDrawer
		drawerIsOpen
		takeFolders
		hasFolders
		closeDrawer
		lastState
	)
	(method (changeState newState)
		(switch (= state newState)
			(reach4Drawer
				(HandsOff)
				(if (== register TAKE_FILES)
					(self changeState: takeFolders)
				else
					(ego
						view:			vLarryRoom,
						setLoop:		lOpeningDrawer,
						setCel:		0,
						cycleSpeed:	12,
						setCycle:	CycleTo 2 FORWARD self
					)
				)
			)
			(openDrawer
				(ego setCycle: EndLoop)
				(drawer setMotion: MoveTo 255 (drawer y?) self)
				(theMusic2
					number:		sFileOpen,
					setLoop:		1,
					play:
				)
			)
			(drawerIsOpen
				(= drawerOpen TRUE)
				(self changeState: lastState)
			)
			(takeFolders
				(ego
					view:			vLarryRoom,
					setLoop:		lTakingFolders,
					setCel:		0,
					setCycle:	EndLoop self
				)
			)
			(hasFolders
				(Points ptsGotResume pfGotResume)
				(ego get: iMM'sResume iLL'sResume iCC'sResume)
				(drawer setLoop: 1)
				(= cycles 1)
			)
			(closeDrawer
				(ego
					setLoop:		lOpeningDrawer,
					setCel:		LASTCEL,
					setCycle: 	BegLoop self
				)
				(theMusic2
					number:		sFileClose,
					setLoop:		1,
					play:
				)
				(drawer setMotion: MoveTo 263 (drawer y?))
				(= drawerOpen FALSE)
				(drawer approachVerbs: verbNone)
			)
			(lastState
				(if (== register TAKE_FILES)
					(TPrint "You take the three resumes from the drawer.")
				)
				(ego normalize:)
				(HandsOn)
				(self dispose:)
			)
		)
	)
);sDrawer


;ษอออออออออออออออออออป
;บ                   บ
;บ Features          บ
;บ                   บ
;ศอออออออออออออออออออผ

(instance light of Feature
	(properties
		x 				220
		y 				40
		nsLeft 		210
		nsTop 		18
		nsBottom 	62
		nsRight 		231
 		description "the light"
		lookStr 		"Staring into the lamp
						is an enlightening experience for you."
		sightAngle 	40
	)
)

(instance bookcase of Feature
	(properties
		x 				157
		y 				47
		nsLeft 		113
		nsTop 		24
		nsBottom 	61
		nsRight 		201
 		description "the bookcase"
		lookStr 		"This bookcase is filled with everything but books."
		sightAngle 	40
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbDo
				(TPrint "The bookcase contains nothing that is of use to you.
					Perhaps you should look elsewhere.")
			)
			(else
				(super doVerb: theVerb invItem &rest)
			)
		)
	)
)

(instance nudePainting of Feature
	(properties
		x 				60
		y 				57
		nsLeft 		43
		nsTop 		38
		nsBottom 	76
		nsRight 		78
 		description "the nude painting"
		lookStr 		"You don't know much about art,
						but something about this painting touches your fancy!"
		sightAngle 	40
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbDo
				(TPrint "You'd love to fold this up for your wallet,
					but you refuse to create a bulge in your leisure suit.")
			)
			(verbTalk
				(TPrint "\"Were you ever featured in `National  Geographic?'\"
					you wonder.")
			)
			(else
				(super doVerb: theVerb invItem &rest)
			)
		)
	)
)

(instance desk of Feature
	(properties
		x 				89
		y 				165
		nsLeft 		33
		nsTop 		154
		nsBottom 	176
		nsRight 		145
 		description "your desk"
		lookStr 		"Why are there no workers in this place?"
		sightAngle 	40
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbDo
				(TPrint "What? You work?! No way!")
			)
			(else
				(super doVerb: theVerb invItem &rest)
			)
		)
	)
)

(instance lamp of Feature
	(properties
		x 				46
		y 				158
		nsLeft 		35
		nsTop 		145
		nsBottom 	171
		nsRight 		58
 		description "the lamp"
		lookStr 		"You admire anyone who uses incandescent lighting."
		sightAngle 	40
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbDo
				(TPrint "You have no use for the lamp.")
			)
			(verbTalk
				(Say ego "\"Testing 1, 2, 3!\" you say forcefully, thinking that
					desk lamp is a hidden microphone.")
			)
			(else
				(super doVerb: theVerb invItem &rest)
			)
		)
	)
)

(instance magnifier of Feature
	(properties
		x 				95
		y 				159
		nsLeft 		73
		nsTop 		144
		nsBottom 	174
		nsRight 		117
 		description "the magnifier"
		sightAngle 	40
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbLook
				(TPrint "One of your favorite pastimes here at PornProdCorp is to
					stand here holding the magnifier up to various parts of
					your anatomy.")
				(TPrint "Like your fingerprints, Larry!")
				(TPrint "(Shame on you for thinking thoughts like that!)" LOW)
			)
			(verbDo
				(TPrint "You move the magnifier slightly,
					then restore it to exactly the same spot again,
					all in the twinkling of a video scan!")
			)
			(else
				(super doVerb: theVerb invItem &rest)
			)
		)
	)
)

(instance loserDrawer1 of Feature
	(properties
		x 				257
		y 				124
		nsLeft 		233
		nsTop 		97
		nsBottom 	152
		nsRight 		281
		approachX	226
		approachY	135
 		description "the file drawer"
		lookStr 		"This file drawer is labeled \"Losers.\"
						You're looking for the files of the three finalists.
						This is no time to try to fatten your address book, Larry!"
		sightAngle 	40
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbDo
				(TPrint "As you start to open the drawer, you read the label,
					and decide this is not the drawer for you.")
			)
			(else
				(super doVerb: theVerb invItem &rest)
			)
		)
	)
)

(instance loserDrawer2 of Feature
	(properties
		x 				155
		y 				93
		nsLeft 		100
		nsTop 		71
		nsBottom 	116
		nsRight 		210
		sightAngle 	40
 		description "the file drawer"
		lookStr 		"This file drawer is labeled \"Losers.\"
						You're looking for the files of the three finalists.
						This is no time to try to fatten your address book, Larry!"
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbDo
				(TPrint "As you start to open the drawer, you read the label,
					and decide this is not the drawer for you.")
			)
			(else
				(super doVerb: theVerb invItem &rest)
			)
		)
	)
)

(instance loserDrawer3 of Feature
	(properties
		x 				63
		y 				123
		nsLeft 		40
		nsTop 		101
		nsBottom 	149
		nsRight 		87
		sightAngle 	40
 		description "the file drawer"
		lookStr 		"This file drawer is labeled \"Losers.\"
						You're looking for the files of the three finalists.
						This is no time to try to fatten your address book, Larry!"
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbDo
				(TPrint "As you start to open the drawer, you read the label,
					and decide this is not the drawer for you.")
			)
			(else
				(super doVerb: theVerb invItem &rest)
			)
		)
	)
)

(instance books of Feature
	(properties
		x 				57
		y 				93
		nsLeft 		46
		nsTop 		87
		nsBottom 	99
		nsRight 		69
 		description "some books"
		lookStr 		"Why would you want to look at books, Larry?
						You can barely read!"
		sightAngle 	40
	)
)

(instance fan of Feature
	(properties
		x 				263
		y 				87
		nsLeft 		254
		nsTop 		75
		nsBottom 	100
		nsRight 		273
 		description "the fan"
		lookStr 		"This is your biggest fan!"
		sightAngle 	40
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbDo
				(TPrint "You are afraid to turn on that fan without
					protective garments.")
				(TPrint "(And it, you!)" LOW)
			)
			(else
				(super doVerb: theVerb invItem &rest)
			)
		)
	)
)

(instance chair of Feature
	(properties
		x 				222
		y 				104
		nsLeft 		212
		nsTop 		96
		nsBottom 	113
		nsRight 		232
 		description "your chair"
		lookStr 		"This chair is your favorite work-place sleeper."
		sightAngle 	40
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbDo
				(TPrint "Remember: the Sexiest Woman in America.
					THE Sexiest Woman in America!
					You're not going to find her sitting around in here!")
			)
			(else
				(super doVerb: theVerb invItem &rest)
			)
		)
	)
)

(instance fTwins of Feature
	(properties
		x 				100
		y 				111
		z 				66
		heading 		0
		nsLeft 		93
		nsTop 		31
		nsBottom 	60
		nsRight 		108
 		description "the twins poster"
		lookStr 		"This poster is your personal favorite.
						You've always had a soft spot for twins."
		sightAngle 	40
	)
	(method (doVerb theVerb &tmp [str 100])
		(if (== theVerb verbLook)
			(Format @str "This poster is your personal favorite.
				You've always had a soft spot for %s-haired twins."
				(if (Btst isVga) {red} else {blonde})
			)
			(TPrint @str)
		else
			(super doVerb: theVerb &rest)
		)
	)
)

(instance fRolls of Feature
	(properties
		x 				91
		y 				103
		z 				0
		heading 		0
		nsLeft 		82
		nsTop 		93
		nsBottom 	114
		nsRight 		101
 		description "the rolls of paper"
		lookStr 		"There are some unimportant rolls of paper lying there."
		sightAngle 	40
	)
)

