;**
;**	Logics for Room 820 -- K-RAP Radio lobby
;**
;**	LSL5:  Passionate Patti Does A Little Undercover Work
;**				Copyright 1991
;**				by Al Lowe
;**
;**	Programmed by John Hartin
;**
;**	Last Update:  September 10, 1991
;**

(script#		820)

(public
	rm820		0
)


(define	vRoom							820)
(define	vRoomEgo			 (+ 1 vRoom))
(define	krapperDoorCode	45954)
(define	pK-RAPRadioLobby				820)
(define	vK-RAPRadioLobby			820)
	(define	lDoorToOffice		0)

(define	mKRAPRap1	800)
(define	mKRAPRap2	801)
(define	mKRAPRap3	802)

(define	DEBUGCODE	-2)
;**	RoomScript state values

(local
	seenMsg
)


(instance rm820 of LLRoom
	(properties
		picture		pK-RAPRadioLobby
		south			800
		west			840
		lookStr 		"\"This lobby is quite impressive,\" you think,
						\"especially for such a small building.\""
	)

	(method (init)
		(LoadMany VIEW
			vK-RAPRadioLobby
		)
		(LoadMany SOUND
			mKRAPRap1
			mKRAPRap2
			mKRAPRap3
		)
		(HandsOn)
		(ego
			init:,
			x: 280,
			normalize:
		)

		(theMusic setVol: (* (/ (theMusic vol?) 3) 2))
		(keypad
			init:,
			approachVerbs: verbDo verbLook
		)
		(door init:)
		(krapWindow init:)
		(plant init:)
		(outerdesk init:)
		(tree init:)
		(backcounter init:)
		(forecounter init:)
		(lamp init:)
		(lights init:)
		(chair
			init:					,
			approachVerbs:		verbLook verbDo,
		)
		(super init:)
		(curRoom addObstacle:
			((Polygon new:)
				type: PBarredAccess,
				init:	0 0 319 0 319 189 315 189 315 130 280 129 255 129 187 128
						184 119 306 119 306 117 80 117 73 121 71 120 49 142 56 145
						8 145 8 156 49 156 2 175 2 187 185 186 221 172 269 182
						268 189 0 189,
				yourself
			)
			((Polygon new:)
				type: PBarredAccess,
				init:	62 154 67 143 67 137 116 137 135 125 172 125 169 140 160 150
						107 154,
				yourself
			)
		)
		(ego observeControl: cBLUE)
		(mouseDownHandler add: self)
		(keyDownHandler	add: self)
	)

	(method (notify codeNumber)
		(if	(OneOf codeNumber krapperDoorCode DEBUGCODE)
			(door locked: FALSE)
			(TPrint "You hear the door's lock click.")
		 else
			(TPrint "You hear no sound from the door.
				That must not have been the correct number.")
		)
		(HandsOn)
	)
	(method (dispose)
		(mouseDownHandler delete: self)
		(keyDownHandler	delete: self)
		(super dispose:)
	)
)


(instance door of Door
	(properties
		view			vK-RAPRadioLobby
		loop			lDoorToOffice
		cel			0
		x				18
		y				154
		approachX	58
		approachY	152
		moveToX		20
		moveToY		146
		entranceTo  840
		exitType		doorWalkEgo
		enterType	doorWalkEgo
		locked		TRUE
		description	"the door"
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbDo
				(if (not (self locked))
					(TPrint "You hear footsteps! Hurry, Patti!
						It's the receptionist returning from her break.")
					(ego	ignoreControl: cBLUE)
				)
				(super doVerb: theVerb invItem &rest)
			)
			(verbLook
				(TPrint "The door's tasteful brass plate reads...")
				(TPrint "Mr. John Krapper\n
							Founder\n
							Owner\n
							President\n
							C. E. O."
					#mode			teJustCenter
				)
			)
			(else
				(super doVerb: theVerb invItem &rest)
			)
		)
	)
)

(instance krapWindow of Feature
	(properties
		x 197
		y 73
		z 0
		heading 0
		nsLeft 75
		nsTop 43
		nsBottom 102
		nsRight 315
 		description "the plate glass window"
		lookStr 		"Through the window you see the night skyline of beautiful
						downtown Philadelphia."
		sightAngle 0
	)
)
(instance outerdesk of Feature
	(properties
		x 114
		y 130
		heading 0
		nsLeft 78
		nsTop 119
		nsBottom 142
		nsRight 150
 		description "the receptionist's desk"
		lookStr 		"This is the receptionist's desk.
						Luckily for you, she seems to be absent right now."
		sightAngle 40
	)
)
(instance plant of Feature
	(properties
		x 221
		y 167
		heading 0
		nsLeft 203
		nsTop 157
		nsBottom 178
		nsRight 240
 		description "the plant"
		lookStr 		"A potted plant adds so much to office ambience."
		sightAngle 40
	)
)

(instance keypad of Feature
	(properties
		x 51
		y 108
		z 0
		heading 0
		nsLeft 46
		nsTop 101
		nsBottom 115
		nsRight 57
		approachX	58
		approachY	142
 		description "the keypad"
		lookStr		"This keypad controls access to John Krapper's private
						office door."
		sightAngle 40
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbLook
				(UseKeypad rm820)
			)
			(verbDo
				(UseKeypad rm820)
			)
			(else
				(super doVerb: theVerb invItem &rest)
			)
		)
	)
)
(instance tree of Feature
	(properties
		x 268
		y 74
		z 0
		heading 0
		nsLeft 248
		nsTop 41
		nsBottom 108
		nsRight 289
 		description "the potted tree"
		lookStr 		"There must be more light in here in the daytime."
		sightAngle 40
	)
)
(instance forecounter of Feature
	(properties
		x 200
		y 182
		z 0
		heading 0
		nsLeft 156
		nsTop 176
		nsBottom 189
		nsRight 245
 		description "the planter"
		lookStr 		"This planter is near the front door."
		sightAngle 40
	)
)
(instance backcounter of Feature
	(properties
		x 254
		y 115
		z 0
		heading 0
		nsLeft 189
		nsTop 107
		nsBottom 124
		nsRight 319
 		description "the rear counter"
		lookStr 		"This counter almost looks like a window seat."
		sightAngle 40
	)
)
(instance lamp of Feature
	(properties
		x 142
		y 110
		z 0
		heading 0
		nsLeft 127
		nsTop 103
		nsBottom 118
		nsRight 158
 		description "the lamp"
		lookStr 		"You really like this lamp's style."
		sightAngle 40
	)
)
(instance lights of Feature
	(properties
		x 87
		y 20
		z 0
		heading 0
		nsLeft 54
		nsTop 1
		nsBottom 39
		nsRight 121
 		description "the lights"
		lookStr 		"Subtle wall washer floodlights cast a lovely glow over
						the supergraphics on the lobby wall."
		sightAngle 40
	)
)

(instance chair of Feature
	(properties
		x 104
		y 124
		z 0
		heading 180
		approachX	104
		approachY	120
		nsLeft 97
		nsTop 106
		nsBottom 124
		nsRight 111
 		description "the chair"
		lookStr 		"The receptionist's chair is empty,
						but still warm to your touch.
						She's only been gone a short while,
						and may soon return."
		sightAngle 40
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbDo
				(TPrint lookStr)
			)
			(else
				(super doVerb: theVerb invItem &rest)
			)
		)
	)
)

