;**
;**	Logics for Room 530 -- Herman Hollerith room
;**
;**	LSL5:  Passionate Patti Does A Little Undercover Work
;**				Copyright 1991
;**				by Al Lowe
;**
;**	Programmed by Carlos Escobar
;**
;**	Last Update:  September 10, 1991
;**

(script#		530)

(public
	rm530		0
)


(define vRoom			 530)
(define vLarrySitting 531)
(define rBoothCloseUp 535)
(define lPeople		 6)
(define vMichelleTalker			1530)

(define	mMichelle				535)
(define	mMichelle2				538)

(local
	michelleCount
	talkedToTable1
	talkedToTable2
	talkedToTable3
	cueCounter
	cueCounter0
	cueCounter2
)


(instance rm530 of LLRoom
	(properties
		picture		530
		south			525
		lookStr		"Now you understand why this is such an exclusive room.
						It's quiet, lovely and private."
	)

	(method (init)
		(LoadMany VIEW
			vLarrySitting
			vMichelleTalker
		)
		(super init:)
		(self addObstacle:
			((Polygon new:)
				type: PBarredAccess,
				init:	0 0 319 0 319 189 239 189 255 148 208 123 183 120 166 106
						92 106 53 138 55 159 128 170 129 189 0 189,
				yourself
			)
			((Polygon new:)
				type: PBarredAccess,
				init:	175 141 203 137 226 141 249 154 236 163 220 166,
				yourself
			)
			((Polygon new:)
				type: PBarredAccess,
				init:	202 160 187 168 161 163 150 153 187 153,
				yourself
			)
		)

		;props
		(drape1
			init:,
			stopUpd:,
		)
		(drape2
			init:,
			stopUpd:,
		)
		(drape3
			init:,
			stopUpd:,
		)

		;features
		(cactus		init:)
		(cart			init:)
		(light		init:)
		(theTable	init:)
		(chair		init:)
		(iceCream	init:)
		(if (!= prevRoomNum 535)	;not coming from close-up
			(table1 	init:)
			(table2	init:)
			(table3	init:)
			(extra1 init:)
			(extra2 init:)
			(extra3 init:)
			(extra4 init:)
			(extra5 init:)
			(ego
				init,
				normalize,
				posn:				151 234,
				edgeHit:			0,
			)
			(michelle
				init:,
				setScript: sMichelleEating,
			)
			(drape4 init:, stopUpd:)
			(self setScript sEnter)
		else
			(michelle init:)
			(ego
				init:,
				view:				vLarrySitting,
				setLoop:			1,
				posn:				260 143,
            setCel:        LASTCEL,                ;sitting
				setCycle:		0,
			)
			(if (not (Btst fMCloseUp))	;if gave her the dayTrotter...
				(Bset fMCloseUp)
				(drape4
					init:,
					setCel: 0,
					stopUpd:,
				)
				(self setScript sCloseCurtain)
			else
				(drape4
					init:,
					setCel: LASTCEL,
					stopUpd:,
				)
				(self setScript sOpenCurtain)
			)
		)
		(addToPics doit:)
		(if
			(!= (theMusic number?) mMichelle)
			(theMusic
				number:		mMichelle,
				setLoop:		-1,
				flags:		1,
				play:,
			)
		)
	);init
)

;	SCRIPTS

(instance sTalk of Script
	(method (changeState newState)
		(switchto (= state newState)
			(
				(switch register
					(0
						(Say theTalker "\"Nearly as many times as I'm here,\"
							she responds."
							#dispose:
							#caller:		self
						)
					)
					(1
						(Say theTalker "\"That may be,
							but I look even better out of it!\""
							#dispose:
							#caller:		self
						)
					)
					(2
						(Say theTalker "\"Well, you may sit down,
							but it may be for dessert!\""
							#dispose:
							#caller:		self
						)
					)
				)
			)
			(
				(switch register
					(0
						(TPrint "(Now what did THAT mean?)" LOW)
						(self dispose:)
					)
					(1
						(TPrint "(Say, this may turn out all right yet!)" LOW)
						(self dispose:)
					)
					(2
						(TPrint "(A better offer you've never had, Larry!
							Go for it!!)" LOW)
						(curRoom setScript: sDoMichelle)
					)
				)
			)
		)
	)
)

(instance sEnter of Script
	(method (changeState newState &tmp var1 var2)
		(switchto (= state newState)
			(
				(HandsOff)
				(ego
					setMotion:		PolyPath 199 155 self
				)
			)
			(
				(ego
					view:				vLarrySitting,
					x:					193,
					y:					151,
					setLoop:			0,
					setCel:			0,
					setCycle:		0
				)
				(= cycles 1)
			)
			(
				; sit larry down
				(ego
					setLoop:			0,
					setCel:			1,
					setPri:			14,
				)
				(= cycles 1)
			)
			(
				(ego
					setLoop:			0,
					setCycle:		EndLoop self,
				)
			)
			(
				(HandsOn)
				(User canControl: FALSE)
				(theIconBar disable: walkIcon)
				(self dispose:)
			)
		)
	)
)

(instance sCloseCurtain of Script
	(method (doit)
		(super doit: &rest)
		(if
			(and
				(== (theMusic number?) mMichelle)
				(== (theMusic prevSignal?) 10)
			)
			(theMusic
				number:	mMichelle2,
				play:
			)
		)
	)
	(method (changeState newState &tmp var1 var2)
		(switchto (= state newState)
			(
				(HandsOff)
				(= seconds 2)
			)
			(
				(drape4 setCycle EndLoop self)
			)
			(
				(= seconds 4)
			)
			(
				(curRoom newRoom 535)
			)
		)
	)
)

(instance sDoMichelle of Script
	(method (changeState newState &tmp var1 var2)
		(switchto (= state newState)
			(
				(HandsOff)
				(ego 
					setCycle:	BegLoop self
				)
			)
			(
				(ego
					normalize:,
					posn:				197 154,
					setPri:			14,
					setMotion:		PolyPath 260 143 self,
				)
			)
			(
				(ego
					setPri:			RELEASE,
					view:				vLarrySitting,
					setLoop:			1,
					setCel:			0,
					setCycle:		EndLoop self,
				)
			)
			(
				(ego
					setCel:			(ego cel?),
					setMotion:		MoveTo 263 142 self
				)
			)
			(
				(= seconds 5)
			)
			(
				(curRoom newRoom rBoothCloseUp)
			)
		)
	)
)

(instance sMichelleEating of Script
	(method (changeState newState &tmp var1 var2)
		(switch (= state newState)
			(0
				(switch (Random 1 4)
					(1
						(= state  1)
						(= cycles 1)
					)
					(2
						(= seconds 4)
					)
					(else
						(= state  6)
						(= cycles 1)
					)
				)
			)
			(1
				(self init:)
			)
			(2
				(michelle
					setLoop:  		4,
					setCel:			0,
					setCycle: 		EndLoop self,
				)
			)
			(3
				(= seconds (Random 2 4))
			)
			(4
				(michelle
					setCycle: 		BegLoop self,
				)
			)
			(5
				(= seconds 2)
			)
			(6
				(self init:)
			)
			(7
				(michelle
					setLoop:		5,
					setCel:		0,
					setCycle:	EndLoop self,
				)
			)
			(8
				(= seconds 2)
			)
			(9
				(self init:)
			)
		)
	)
)

(instance sOpenCurtain of Script
	(method (changeState newState &tmp var1 var2)
		(switchto (= state newState)
			(
				(= cycles 2)
			)
			(
				(Say theTalker "\"Allow me to make our booth just a little more
					private,\" says Michelle, standing up."
					#dispose:)
				(drape4 setCycle: BegLoop self)
			)
			(
				(HandsOn)
				(ego normalize)
			)
		)
	)
)

;	PROPS

(instance michelle of Prop
	(properties
		view			 	vRoom
		loop			 	5
		x				 	237
		y				 	143
		z					40
		priority			1
 		description  	"her"
		signal			(| fixedLoop fixPriOn)
	)
	(method (doVerb theVerb invItem)
		(theTable doVerb: theVerb invItem)
	)
)

(instance drape1 of Prop
	(properties
		view			 	vRoom
		loop			 	1
		x				 	89
		y				 	36
		priority			7
 		description  	"the curtain"
		signal			(| fixPriOn ignrAct)
	)

	(method (cue)
		(switch (++ cueCounter0)
			(1
				(super cue:)
				(self setCycle EndLoop self)
				(table1 dispose:)
				(extra1 dispose:)
				(extra2 dispose:)
				(= talkedToTable1 TRUE)
				(HandsOn)
				(User canControl: FALSE)
				(theIconBar disable: walkIcon)
			)
			(2
				(self stopUpd:)
			)
		)
	)

	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbLook
				(TPrint "Every booth has curtains to assure your total privacy
					while dining.")
			)
			(verbDo
				(TPrint "There's nothing you can do with the curtains.")
			)
			(verbTalk
				(if talkedToTable1
					(switch (Random 1 3)
						(1
							(TPrint "They don't want to talk to you, Larry!")
						)
						(2
							(TPrint "Just leave them alone!")
						)
						(3
							(TPrint "Don't you get the hint?
								They don't want to talk to you!")
						)
					)
				else
					(table1 doVerb: verbTalk)
				)
			)
			(else
				(super doVerb: theVerb invItem &rest)
			)
		)
	)
)
(instance drape2 of Prop
	(properties
		view			 	vRoom
		loop			 	3
		x				 	134
		y				 	101
 		description  	"the curtain"
		signal			ignrAct
	)

	(method (cue)
		(super cue:)
		(switch (++ cueCounter)
			(1
				(TPrint "\"DEAR!\" interrupts his wife, \"that's not necessary!
					This is a family restaurant!\""
					#dispose: self
				)
			)
			(2
				(self setCycle: EndLoop self)
				(table2 dispose:)
				(extra4 dispose:)
				(extra5 dispose:)
				(= talkedToTable2 TRUE)
				(HandsOn)
				(User canControl: FALSE)
				(theIconBar disable: walkIcon)
			)
			(3
				(self stopUpd:)
			)
		)
	)

	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbDo
				(TPrint "There's nothing you can do with the curtains.")
			)
			(verbLook
				(TPrint "Every booth has curtains to assure your total privacy
					while dining.")
			)
			(verbTalk
				(if talkedToTable2
					(drape1 doVerb: verbTalk)
				else
					(table2 doVerb: verbTalk)
				)
			)
			(else
				(super doVerb: theVerb invItem &rest)
			)
		)
	)
)

(instance drape3 of Prop
	(properties
		view			 	vRoom
		loop			 	2
		x				 	211
		y				 	35
		priority			7
 		description  	"the curtain"
		signal			(| fixPriOn ignrAct)
	)

	(method (cue)
		(switch (++ cueCounter2)
			(1
				(super cue:)
				(TPrint "\"Why doesn't that come as a surprise?\"
					the woman replies snootily."
				)
				(self setCycle: EndLoop self)
				(table3 dispose:)
				(extra3 dispose:)
				(= talkedToTable3 TRUE)
				(HandsOn)
				(User canControl: FALSE)
				(theIconBar disable: walkIcon)
			)
			(2
				(self stopUpd:)
			)
		)
	)

	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbDo
				(TPrint "There's nothing you can do with the curtains.")
			)
			(verbLook
				(TPrint "Every booth has curtains to assure your total privacy
					while dining."
				)
			)
			(verbTalk
				(if talkedToTable3
					(drape1 doVerb: verbTalk)
				else
					(table3 doVerb: verbTalk)
				)
			)
			(else
				(super doVerb: theVerb invItem &rest)
			)
		)
	)
)
(instance drape4 of Prop
	(properties
		view			 	vRoom
		loop			 	0
		x				 	279
		y				 	55
		priority			14
 		description  	"the curtain"
		signal			(| fixPriOn ignrAct)
	)
	(method (doVerb theVerb invItem)
		(theTable doVerb: theVerb invItem)
	)
)

;	PICVIEWS

(instance iceCream of PicView
	(properties
		view			 	vRoom
		loop			 	6
		cel				5
		x				 	244
		y				 	109
		sightAngle 	 	90
 		description  	"her ice cream"
		signal			(| fixedCel fixedLoop)
	)
)
(instance extra1 of PicView
	(properties
		view			 	vRoom
		loop			 	6
		cel				1
		x				 	63
		y				 	89
		sightAngle 	 	90
		priority			5
 		description  	"the couple"
		signal			(| fixedCel fixedLoop fixPriOn)
	)
)
(instance extra2 of PicView
	(properties
		view			 	vRoom
		loop			 	6
		cel				2
		x				 	80
		y				 	88
		sightAngle 	 	90
		priority			5
 		description  	"the couple"
		signal			(| fixedCel fixedLoop fixPriOn)
	)
)
(instance extra3 of PicView
	(properties
		view			 	vRoom
		loop			 	6
		cel				0
		x				 	184
		y				 	88
		sightAngle 	 	90
		priority			4
 		description  	"the couple"
		signal			(| fixedCel fixedLoop fixPriOn)
	)
)
(instance extra4 of PicView
	(properties
		view			 	vRoom
		loop			 	6
		cel				3
		x				 	119
		y				 	78
		sightAngle 	 	90
		priority			4
 		description  	"the couple"
		signal			(| fixedCel fixedLoop fixPriOn)
	)
)
(instance extra5 of PicView
	(properties
		view			 	vRoom
		loop			 	6
		cel				4
		x				 	140
		y				 	77
		sightAngle 	 	90
		priority			5
 		description  	"the couple"
		signal			(| fixedCel fixedLoop fixPriOn)
	)
)

;	FEATURES

(instance chair of Feature
	(properties
		x					179
		y					144
		z					0
		heading			0
		nsLeft			162
		nsTop				126
		nsBottom			163
		nsRight 			196
 		description 	"your chair"
		lookStr 			"It's the only place you've got to sit. And you like it!"
		sightAngle 		40
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbDo
				(TPrint "You can't stand up; you have nowhere else to go.")
			)
			(verbWalk
				(TPrint "Why not talk to that beautiful woman instead.
					Go on, Larry; strike up a conversation.")
			)
			(else
				(super doVerb: theVerb invItem &rest)
			)
		)
	)
)
(instance cactus of Feature
	(properties
		x					91
		y					143
		z					0
		heading			0
		nsLeft			43
		nsTop				107
		nsBottom			180
		nsRight			140
 		description		"the cactus"
		lookStr			"Isn't it funny how this cactus blooms
							when none of the others do."
		sightAngle		40
		onMeCheck		cGREEN
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbDo
				(Say ego "\"OUCH!\"")
			)
			(else
				(super doVerb: theVerb invItem &rest)
			)
		)
	)
)
(instance table1 of Feature
	(properties
		x					67
		y					95
		z					0
		heading			0
		nsLeft			45
		nsTop				73
		nsBottom			107
		nsRight			90
 		description		"the table"
		lookStr			"Here's another couple having a wonderful evening
							while you wander around all alone again!"
		sightAngle		40
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbDo
				(TPrint "You can't sit with a strange couple!")
			)
			(verbTalk
				(if (not talkedToTable1)
					(HandsOff)
					(Say ego "\"Excuse me, may I join you?\"
						you ask the couple at the left table."
						LOW
					)
					(TPrint "\"No. I'm sorry, but we're eating.\"
						replies the wit at the table.
						\"And I don't want to lose my appetite.\""
						#dispose: drape1
					)
				else
					(super doVerb: theVerb invItem)
				)
			)
			(else
				(super doVerb: theVerb invItem &rest)
			)
		)
	)
)
(instance table2 of Feature
	(properties
		x 					132
		y					195
		z					0
		heading			0
		nsLeft			109
		nsTop				60
		nsBottom			92
		nsRight			155
		sightAngle		40
 		description		"the table"
		lookStr			"Here's another couple having a wonderful evening
							while you wander around New York City all alone!"
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbDo
				(TPrint "You can't sit with a strange couple!")
			)
			(verbTalk
				(if (not talkedToTable2)
					(HandsOff)
					(Say ego "\"Excuse me,\" you shout to that far table,
						\"but can you explain why I don't talk to that lovely woman
						sitting all alone at that table in the corner?\""
						LOW
					)
					(TPrint "\"Could your head be up your...\""
						#dispose: drape2
					)
				else
					(super doVerb: theVerb invItem)
 				)
			)
			(else
				(super doVerb: theVerb invItem &rest)
			)
		)
	)
)
(instance table3 of Feature
	(properties
		x					187
		y					95
		z					0
		heading			0
		nsLeft			173
		nsTop				67
		nsBottom			104
		nsRight			201
 		description		"the table"
		lookStr			"Here's another couple having a wonderful evening
							while you wander around all alone again!"
		sightAngle		40
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbDo
				(TPrint "You can't sit with a strange couple!")
			)
			(verbTalk
				(if (not talkedToTable3)
					(HandsOff)
					(Say ego "\"Hi, folks; I'm from out of town!\" you yell to
						the diners at the table across the room."
						LOW
						#dispose: drape3
					)
				else
					(super doVerb: theVerb invItem)
				)
			)
			(else
				(super doVerb: theVerb invItem &rest)
			)
		)
	)
)
(instance theTable of Feature
	(properties
		x					246
		y					140
		z					0
		heading			0
		nsLeft			214
		nsTop				75
		nsBottom			132
		nsRight			279
 		description		"the table"
		lookStr			"A breathtakingly beautiful black woman sits alone at a
							booth, playing slowly and sensually with her dessert.
							It's Michelle Milken. You've found her, Larry!"
		sightAngle		40
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbDo
				(TPrint "You know what you want to do, Larry.
					Your problem is figuring out how to do it!")
			)
			(verbUse
				(TPrint "Perhaps you should sit down with her first.")
			)
			(verbZipper
				(TPrint "A little premature, aren't you, Larry?
					Maybe first a little dinner,
					then a show,
					perhaps a little dancing....")
			)
			(verbTalk
				(switch (++ michelleCount)	;**	keep track of conversation count
					(1
						(Say ego "\"Hello, Lovely Lonely Lady,\" you say to the
							gorgeous babe sitting all alone in the booth near you.
							\"Allow me to introduce myself.
							My name is Larry; Larry Laffer.\""
							LOW
						)
						(TPrint "You hope she doesn't just ignore you.")
						(Say theTalker "\"Hi, Larry,\" she says with a friendly smile,
							\"new in town?\""
							#dispose:)
					)
					(2
						(Say ego "Remembering your boss' warning not to reveal
							your identity, you decide to begin with a casual
							opener, \"Do you come here often?\" you ask."
							LOW
						)
						(curRoom setScript: sTalk NULL 0)
					)
					(3
						(Say ego "Perhaps I could mention her \"power clothes,\"
							you think to yourself.
							A compliment on her apparel might be appropriate..."
							LOW
						)
						(Say ego "\"What a lovely dress you're almost wearing,\"
							you tell her. \"And you certainly look good in it!"
							LOW
						)
						(curRoom setScript: sTalk NULL 1)
					)
					(4
						(Say ego "\"I notice you're dining alone,\" you try again."
							LOW
						)
						(Say theTalker "\"Yes, and dining isn't the only thing I
							don't enjoy doing alone!\" she responds with a smile."
							#dispose:)
					)
					(else
						(Say ego "\"I was wondering: may I join you for dinner?\"
							you ask."
							LOW
						)
						(curRoom setScript: sTalk NULL 2)
					)
				;**	we walk him over to her booth, show her stand,
				;**	show him sit, show her close the drapes to their booth
				)
			)
			(else
				(super doVerb: theVerb invItem &rest)
			)
		)
	)
)

(instance cart of Feature
	(properties
		x					206
		y					139
		z					0
		heading			0
		nsLeft			188
		nsTop				125
		nsBottom			154
		nsRight			225
 		description		"the serving cart"
		lookStr			"It looks like this cart is the only table available."
		sightAngle		40
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbDo
				(TPrint "You can't move the cart. Then you'd have no table!")
			)
			(else
				(super doVerb: theVerb invItem &rest)
			)
		)
	)
)
(instance light of Feature
	(properties
		x					144
		y					140
		z					86
		heading			0
		nsLeft			121
		nsTop				40
		nsBottom			65
		nsRight			167
 		description		"the lamp"
		lookStr			"It casts an even illumination."
		sightAngle		40
		onMeCheck		cBLUE
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(else
				(super doVerb: theVerb invItem &rest)
			)
		)
	)
)

;	TALKER

(instance theTalker of Talker
	(properties
		name 			"Michelle Milken"
		view			vMichelleTalker
		loop			3
		nsLeft		30
		nsTop			15
		viewInPrint	TRUE
	)
	(method (init)
		(= bust  talkerBust)
		(= eyes  talkerEyes)
		(= mouth talkerMouth)
		(super init:)
	)
)
(instance talkerBust of View
	(properties
		view		vMichelleTalker
		loop		1
		nsLeft	0
		nsTop		0
	)
)
(instance talkerEyes of Prop
	(properties
		view			vMichelleTalker
		loop			2
		nsLeft		20
		nsTop			27
		cycleSpeed  30
	)
)
(instance talkerMouth of Prop
	(properties
		view			vMichelleTalker
		loop			0
		nsLeft		26
		nsTop			37
	)
)

