;**
;**	Logics for Room 880 -- K-RAP Radio control rooms
;**
;**	LSL5:  Passionate Patti Does A Little Undercover Work
;**				Copyright 1991
;**				by Al Lowe
;**
;**	Programmed by John Hartin
;**
;**	Last Update:  September 10, 1991
;**


(script#		880)

(public
	rm880		0
)


(define	pK-RAPRadioStudios			870)
(define	pK-RAPRadioControlRoom		880)
(define	pInsideControlRoomB			890)
(define	vPattiHammerpants			872)
(define	vK-RAPRadioControlRoom	880)
	(define	lSingleCells		0)
		(define	cMikeStand	0)
		(define	cBlockingDoor 1)
	(define	lDoorToA				1)
	(define	lDoorToB				2)
	(define	lSign					3)
		(define	cOff				0)
		(define	cOn				1)
	(define	lASign					4)
(define	vPCHammer					882)
	(define	lWalking 			0-1)
(define	vHammerActions				883)
	(define	lUsingMixer			0)
	(define	lLookingAtPatti	1)
	(define	lBlockingDoor		2)
	(define	lUsingMixerLarge	3)

(define	mKRAPRap1	800)
(define	mKRAPRap2	801)
(define	mKRAPRap3	802)

(define DEBUGCODE -2)
(local
	enteredCode
	correctDoor
)


(instance rm880 of LLRoom
	(properties
		picture		pK-RAPRadioControlRoom
		west			pK-RAPRadioStudios
	   east			890
		lookStr 		"The studio floor of the K-RAP building is far underground,
						filled with soundproof studios and playback speakers.
						A large fountain, complete with giant bronze sculptures,
						fills the center of the room."
	)

	(method (init)
		(LoadMany VIEW
			vPattiHammerpants
			vK-RAPRadioControlRoom
			vPCHammer
			vHammerActions
		)
		(LoadMany SOUND
			mKRAPRap1
			mKRAPRap2
			mKRAPRap3
		)
		(ego
			init:				,
			normalize:		vPattiHammerpants,
			actions: ActionsKRAP,
			illegalBits:	cBLUE
		)
		(switch prevRoomNum
			(west
				(HandsOn)
				(= style SCROLLLEFT)
			)
			(east
				(ego
					posn:			250 127,
					setHeading:	270,
					edgeHit:		0
				)
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
		(doorA init:, approachVerbs: verbDo verbLook)
		(doorB init:, approachVerbs: verbDo verbLook)
		(doorC init:, approachVerbs: verbDo verbLook)
		(windowA init:)
		(windowB init:)
		(windowC init:)
		(keypadA init:, approachVerbs: verbDo verbLook)
		(keypadB init:, approachVerbs: verbDo verbLook)
		(keypadC init:, approachVerbs: verbDo verbLook)
		(PChammer init:, setCycle: RandCycle, cycleSpeed: 12)
		(movingSignA init:, setCycle: Forward, cycleSpeed:	12)
		(cast eachElementDo: #checkDetail)
		(mikeStand init:)
		(statue	init:)
		(recSignA init:)
		(recSignB init:)
		(addToPics doit:)
		(self addObstacle:
			((Polygon new:)
				type: PBarredAccess,
				init:	0 0 319 0 319 189 0 189 0 133 85 138 141 148 188 166 216 187
						316 187 316 171 268 132 251 120 238 116 210 116 207 124
						163 124 175 109 139 76 68 76 65 72 54 82 15 116 0 116,
				yourself
			)
			((Polygon new:)
				type: PBarredAccess,
				init:	91 109 97 105 120 105 124 112 117 120 97 120,
				yourself
			)
		)
		(mouseDownHandler add: self)
		(keyDownHandler	add: self)
	)

	(method (dispose)
		(mouseDownHandler delete: self)
		(keyDownHandler	delete: self)
		(ActionsKRAP dispose:)
		(super dispose:)
	)

	(method (doit)
		(super doit:)
		(cond
			(script)
			((StepOn ego cBLUE)
				(if enteredCode
					(ego edgeHit: NORTH)
					(curRoom newRoom: (curRoom east?))
				else
					(TPrint "It's locked.")
				)
			)
		)
	)

	(method (notify codeNumber)
		(if (and
				(OneOf codeNumber roomBNumber DEBUGCODE)
				(== correctDoor TRUE)
			 )
			(doorB locked: FALSE)
			(= correctDoor FALSE)
			(TPrint "You hear the door's lock click.")
		else
			(TPrint "You hear no sound from the door.
				That must not have been the correct number.")
		)
		(HandsOn)
	)
)

(instance doorA of Door
	(properties
		view			vK-RAPRadioControlRoom
		loop			lDoorToA
		cel			0
		x				47
		y				77
		locked		TRUE
 		description "the door to Control Room A"
		lookStr 		"This door leads to Control Room A.
						You can see a man working inside."
		sightAngle 	40
		approachX	64
		approachY	80
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbDo
				(TPrint "You really don't want to open this door.
					Your mission is to NOT get caught!")
			)
			(else
				(super doVerb: theVerb invItem &rest)
			)
		)
	)
)
(instance doorB of Door
	(properties
		entranceTo	pInsideControlRoomB
		view			vK-RAPRadioControlRoom
		loop			lDoorToB
		cel			0
		x				200
		y				111
		exitType		doorWalkEgo
		enterType	doorWalkEgo
		locked		TRUE
 		description "the Control Room B door"
		lookStr 		"This door leads to Control Room B,
						which appears to be empty."
		sightAngle 	40
		approachX	218
		approachY	130
		moveToX		220
		moveToY		116
	)
)
(instance doorC of Feature
	(properties
		x 				265
		y 				94
		nsLeft 		257
		nsTop 		57
		nsBottom 	131
		nsRight 		274
 		description "the Control Room C door"
		lookStr 		"This door leads to Control Room C.
						It appears to be unoccupied at the moment."
		sightAngle 	40
		approachX	260
		approachY	100
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbDo
				(TPrint "You really don't want to open this door.
					Your mission is to NOT get caught!")
			)
			(else
				(super doVerb: theVerb invItem &rest)
			)
		)
	)
)

(instance keypadA of Feature
	(properties
		x 60
		y 39
		approachX	64
		approachY	80
		nsLeft 55
		nsTop 31
		nsBottom 47
		nsRight 65
 		description "the keypad"
		lookStr 		"Each of the doors has a keypad to limit access to
						only those with authorization.
						This keypad controls access to Control Room A."
		sightAngle 40
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbDo
				(UseKeypad rm880)
			)
			(else
				(super doVerb: theVerb invItem &rest)
			)
		)
	)
)
(instance keypadB of Feature
	(properties
		x 239
		y 81
		nsLeft 232
		nsTop 74
		nsBottom 89
		nsRight 246
 		description "the keypad"
		lookStr 		"Each of the doors has a keypad to limit access to
						only those with authorization.
						This keypad controls access to Control Room B."
		sightAngle 40
		approachX	238
		approachY	115
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbDo
				(= correctDoor TRUE)
				(UseKeypad rm880)
			)
			(else
				(super doVerb: theVerb invItem &rest)
			)
		)
	)
)
(instance keypadC of Feature
	(properties
		x 254
		y 85
		z 0
		heading 0
		nsLeft 248
		nsTop 74
		nsBottom 95
		nsRight 257
 		description "the keypad"
		lookStr 		"Each of the doors has a keypad to limit access to
						only those with authorization.
						This keypad controls access to Control Room C."
		sightAngle 40
		approachX	260
		approachY	100
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbDo
				(UseKeypad rm880)
			)
			(else
				(super doVerb: theVerb invItem &rest)
			)
		)
	)
)
(instance windowA of Feature
	(properties
		x 122
		y 41
		z 0
		heading 0
		nsLeft 116
		nsTop 29
		nsBottom 53
		nsRight 128
 		description "the window of Control Room A"
		lookStr 		"Through this window you can see Control Room A."
		sightAngle 40
	)
)
(instance windowB of Feature
	(properties
		x 161
		y 59
		z 0
		heading 0
		nsLeft 146
		nsTop 39
		nsBottom 79
		nsRight 176
 		description "the window of Control Room B"
		lookStr 		"Through this window you can see Control Room B.
						It appears to be currently unoccupied."
		sightAngle 40
	)
)
(instance windowC of Feature
	(properties
		x 303
		y 111
		nsLeft 287
		nsTop 74
		nsBottom 148
		nsRight 319
 		description "the window of Control Room C"
		lookStr 		"Through this window you can see Control Room C.
						It appears to be currently unoccupied."
		sightAngle 40
	)
)

(instance statue of Feature
	(properties
		x 99
		y 106
		z 0
		heading 0
		nsLeft 0
		nsTop 24
		nsBottom 189
		nsRight 198
 		description "the statue"
		lookStr 		"How impressive! K-RAP has a fountain filled with a
				huge statue of the Queen of Rock 'n' Roll--Connie Stevens!"
		sightAngle 40
		onMeCheck	cYELLOW
	)
)

(instance recSignA of Feature
	(properties
		x 100
		y 26
		z 0
		heading 0
		nsLeft 73
		nsTop 24
		nsBottom 29
		nsRight 128
 		description "the recording sign"
		lookStr 		"This sign is illuminated when Control Room A is recording."
		sightAngle 40
	)
)

(instance recSignB of Feature
	(properties
		x 162
		y 36
		z 0
		heading 0
		nsLeft 144
		nsTop 24
		nsBottom 48
		nsRight 181
 		description "the recording sign"
		lookStr 		"This sign is illuminated when Control Room B is recording."
		sightAngle 40
	)
)

(instance movingSignA of Prop
	(properties
		view			vK-RAPRadioControlRoom
		loop			lASign
		cel			0
		x				75
		y				28
		priority		15
		signal		fixPriOn
 		description "the recording sign"
		lookStr 		"Control Room A's recording indicator lamp is presently on."
	)
)

(instance PChammer of Actor
	(properties
		view			vHammerActions
		loop			lUsingMixer
		cel			0
		x				90
		y				67
 		description "P. C. Hammer"
		detailLevel	3
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbLook
				(TPrint "P. C. Hammer is the man Desmond says is linked to
					much of the evil doings here at K-RAP radio!")
				(TPrint "It's a good thing your disguise is working out so well.
					You certainly wouldn't want him to notice you.")
			)
			(verbTalk
				(TPrint "You may wish to talk with P. C. Hammer later,
					but now is not the time.")
			)
			(verbDo
				(TPrint "Stay away from the window, in case P. C. realizes
					you're not the woman you seem to be.")
			)
			(verbZipper
				(TPrint "Patti! Is that all you have on your mind?")
				(TPrint "(Although he IS kind of cute!)" LOW)
			)
			(else
				(super doVerb: theVerb invItem &rest)
			)
		)
	)
)
(instance mikeStand of PicView
	(properties
		view			vK-RAPRadioControlRoom
		loop			cMikeStand
		cel			0
		x				175
		y				121
		signal		ignrAct
 		description "the microphone stand"
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbLook
				(TPrint "A large, heavy microphone stand waits for a gig.")
			)
			(verbDo
				(TPrint "You really have no use for a microphone stand.")
				(TPrint "(Especially one that must weigh a ton!)" LOW)
			)
			(verbZipper
				(TPrint "Patti!")
				(TPrint "(Besides, wouldn't that steel be cold!?)" LOW)
			)
			(else
				(super doVerb: theVerb invItem &rest)
			)
		)
	)
)
(instance ActionsKRAP of Actions
	(method (doit)
		(return FALSE)							;fixes a bug caused by (Patti doVerb)
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbDo
				(TPrint "You attempt to hike up those Hammerpants you're wearing,
					but it does no good!")
				(return TRUE)
			)
			(verbZipper
				(TPrint "Maintaining your cool,
					you surreptitiously check the zipper on your giant baggy pants.
					It's fine.")
				(return TRUE)
			)
			(else
				(return FALSE)
			)
		)
	)
)

