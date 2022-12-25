;**
;**	Logics for Room 850 -- John Krapper's crapper
;**
;**	LSL5:  Passionate Patti Does A Little Undercover Work
;**				Copyright 1991
;**				by Al Lowe
;**
;**	Programmed by John Hartin
;**
;**	Last Update:  September 10, 1991
;**

(script#		850)

(public
	rm850		0
)


(define	vRoom							850)
(define	vRoomEgo			 (+ 1 vRoom))
(define	sFlush	851)
(define	sRunWater	852)
(define	sElevator	853)
(define	sElevatorClose	854)

(define	pK-RAPRadioAtrium				860)
(define	pJohnKrapper'sBathroom		850)
(define	pJohnKrapper'sOffice			840)
(define	vJohnKrapper'sBathroom	850)
	(define	lSingleCells		0)
		(define	cShowerW/oPatti 0)
		(define	cPatti'sClothes 1)
	(define	lDoorToOffice		1)
	(define	lDoorToShower		2)
(define	vPattiInBathroom			851)
	(define	lSittingOnCan		0)
	(define	lWiping				1)
	(define	lDisrobing			2)
	(define	lEnteringShower	3)
(define	vPattiBlackface			842)

(define	mKRAPRap1	800)
(define	mKRAPRap2	801)
(define	mKRAPRap3	802)


;**	RoomScript state values

(local
	seenMsg
)


(instance rm850 of LLRoom
	(properties
		picture		pJohnKrapper'sBathroom
		east			pJohnKrapper'sOffice
 		description "John Krapper's bathroom"
		lookStr 		"John Krapper's bathroom lives up to expectations.
						Gold fixtures are scattered everywhere.
						There are even a pair of gold statuettes on either
						side of the \"crapper.\""
	)

	(method (init)

		(LoadMany VIEW
			vJohnKrapper'sBathroom
			vPattiInBathroom
			vPattiBlackface
		)
		(LoadMany SOUND
			sFlush
			sRunWater
			sElevator
			mKRAPRap1
			mKRAPRap2
			mKRAPRap3
		)

		(HandsOn)
		(ego
			init:	,
			normalize:	(if (Btst fPattiBlackface) vPattiBlackface else vPatti),
		)
		(if (Btst fPattiBlackface)
			(ego actions: ActionsKRAP)
		)

		(ego
			posn:			270 140,
			setHeading:	270,
			edgeHit:		0
		)

		(super init:)
		(officeDoor init:)
		(sDoor
			init:,
			approachVerbs: verbDo,
			ignoreActors:	,
			stopUpd:			,
		)
		(shower
			init:				,
			approachVerbs: verbDo,
			ignoreActors:	,
			stopUpd:
		)
		(showerSide
			init:				,
			approachVerbs: verbDo,
			ignoreActors:	,
			stopUpd:
		)
		(toilet
			init:				,
			approachVerbs: verbDo verbZipper verbLook verbUse,
		)
		(myWindow init:)
		(curtain1 init:)
		(curtain2 init:)
		(sink1 init:)
		(sink2 init:)
		(skylight init:)
		(rightStatue init:)
		(leftStatue init:)
		(curRoom addObstacle:
			((Polygon new:)
				type: PBarredAccess,
				init:	0 0 319 0 319 123 278 129 272 138 247 111 240 111 240 128
						83 128 77 112 63 112 52 121 59 128 59 138 51 145 32 152 6 146
						4 186 54 186 61 177 89 177 112 165 145 165 139 177 173 177
						172 165 207 165 217 177 249 178 256 187 317 187 314 162
						283 136 318 131 319 189 0 189,
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
	(method (cue)
		(if (== (officeDoor state?) doorClosed)
			(officeDoor ignoreActors:, setPri: pLBLUE)
		)
	)
)

(instance shower of Actor
	(properties
		view 			850
		loop			0
		cel			0
		x				25
		y				144
		priority		8
		signal		(| fixPriOn ignrAct)
 		description "the shower"
		lookStr 		"What appears to be a lovely glass shower rests against
						the wall."
		sightAngle 	40
		approachX		73
		approachY		139
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbDo
				(if (Btst fPattiBlackface)
					(ego setScript: TakeAShowerScript)
				else
					(TPrint "You don't need a shower right now, Patti.
						You're not dirty.")
					(TPrint "(That is, you're not soiled!)" LOW)
				)
			)
			(else
				(super doVerb: theVerb invItem &rest)
			)
		)
	)
)

(instance showerSide of Actor
	(properties
		view			850
		loop			0
		cel			1
		x				26
		y				144
 		description "the shower"
		lookStr 		"Why, look! A lovely glass shower stands against the wall."
		sightAngle 	40
		approachX		73
		approachY		139
	)
	(method (doVerb theVerb invItem)
		(shower doVerb: theVerb invItem &rest)
	)
)

(instance officeDoor of Door
	(properties
		entranceTo	840
		view			850
		loop			1
		cel			0
		x				298
		y				136
		approachX	264
		approachY	136
		moveToX		306
		moveToY		127
		exitType		doorWalkEgo
		enterType	doorWalkEgo
 		description "the office door"
		lookStr 		"This door leads back to John Krapper's office."
		priority		pLGREEN
		signal		fixPriOn
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbDo
				(ego ignoreControl: cBLUE)
				(super doVerb: theVerb invItem &rest)
			)
			(else
				(super doVerb: theVerb invItem &rest)
			)
		)
	)
	(method (close)
		(ego observeControl: cBLUE)
		(self caller: rm850)
		(super close:)
	)
	(method (open)
		(self setPri: pLGREEN)
		(super open:)
	)
)


(instance sDoor of Actor
	(properties
		view			850
		loop			2
		cel			0
		x				26
		y				144
 		description "the shower"
		approachX		73
		approachY		139
	)
	(method (doVerb theVerb invItem)
		(shower doVerb: theVerb invItem &rest)
	)
)

(instance toilet of Feature
	(properties
		x 155
		y 183
		heading 0
		nsLeft 134
		nsTop 178
		nsBottom 189
		nsRight 177
 		description "Krapper's crapper"
		lookStr 		"Made of hand-carved, gold-flecked marble with a
						gold-plated handle and ermine-covered seat,
						it's obvious to you this guy really knows his \"K-RAP!\""
		approachX		161
		approachY		180
		sightAngle 40
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbDo
				(TPrint "You reach over and give the handle a solid push.")
				(flushSound play:)
			)
			(verbZipper
				(TPrint "You reach over and give the handle a solid push.")
				(flushSound play:)
			)
			(verbLook
				(super doVerb: theVerb invItem &rest)
			)
			(verbUse
				(switch invItem
					(iPhotocopies
						(TPrint "Wouldn't flushing the photocopies be defeating the
							whole purpose of your mission?")
					)
					(else
						(TPrint "Hey! You might need that!")
					)
				)
			)
			(else
				(super doVerb: theVerb invItem &rest)
			)
		)
	)
)
(instance myWindow of Feature
	(properties
		x 148
		y 80
		heading 0
		nsLeft 78
		nsTop 46
		nsBottom 114
		nsRight 219
 		description "the window"
		lookStr 		"A giant plate-glass window looks out over the lovely
						glow of downtown Philadelphia at night."
		sightAngle 40
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(else
				(super doVerb: theVerb invItem &rest)
			)
		)
	)
)
(instance curtain1 of Feature
	(properties
		x 68
		y 79
		heading 0
		nsLeft 59
		nsTop 48
		nsBottom 111
		nsRight 78
 		description "the curtains"
		lookStr 		"These curtains are only for show; they do not close."
		sightAngle 40
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(else
				(super doVerb: theVerb invItem &rest)
			)
		)
	)
)
(instance curtain2 of Feature
	(properties
		x 231
		y 77
		heading 0
		nsLeft 221
		nsTop 43
		nsBottom 112
		nsRight 242
 		description "the curtains"
		lookStr 		"These curtains are only for show; they do not close."
		sightAngle 40
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(else
				(super doVerb: theVerb invItem &rest)
			)
		)
	)
)
(instance sink1 of Feature
	(properties
		x 117
		y 110
		heading 0
		nsLeft 91
		nsTop 96
		nsBottom 125
		nsRight 143
 		description "the left sink"
		sightAngle 40
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbLook
				(TPrint "Made of hand-carved, gold-flecked marble with
					gold-plated faucets,
					you bet this sink alone is worth more than your grand piano!")
			)
			(verbDo
				(if (Btst fPattiBlackface)
					(TPrint "What you really need is a shower.")
				else
					(TPrint "Your hands aren't dirty.")
				)
			)
			(else
				(super doVerb: theVerb invItem &rest)
			)
		)
	)
)
(instance sink2 of Feature
	(properties
		x 204
		y 110
		heading 0
		nsLeft 176
		nsTop 96
		nsBottom 124
		nsRight 233
 		description "the right sink"
		sightAngle 40
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbLook
				(sink1 doVerb: theVerb invItem &rest)
			)
			(verbDo
				(sink1 doVerb: theVerb invItem &rest)
			)
			(else
				(super doVerb: theVerb invItem &rest)
			)
		)
	)
)
(instance skylight of Feature
	(properties
		x 155
		y 14
		z 0
		heading 0
		nsLeft 91
		nsTop 4
		nsBottom 25
		nsRight 219
 		description "the skylight"
		lookStr 		"The ceiling of John Krapper's bathroom is crowned by
						a lovely skylight."
		sightAngle 40
	)
)

(instance rightStatue of Feature
	(properties
		x 118
		y 145
		z 0
		heading 0
		nsLeft 107
		nsTop 127
		nsBottom 163
		nsRight 129
 		description "the statuette"
		lookStr 		"This statuette was presented to K-RAP Radio for
						excellence in broadcasting!"
	)
)
(instance leftStatue of Feature
	(properties
		x 197
		y 148
		z 0
		heading 0
		nsLeft 187
		nsTop 130
		nsBottom 167
		nsRight 207
 		description "the statuette"
		lookStr 		"This statuette was presented to K-RAP Radio for
						excellence in broadcasting!"
	)
)


(instance clothes of View
	(properties
		view			850
		loop			0
		cel			2
		x				40
		y				141
	)
)

(instance flushSound of Sound
	(properties
		number	sFlush
		priority	11
	)
)
(instance runWaterSound of Sound
	(properties
		number	sRunWater
		priority	11
	)
)

(instance TakeAShowerScript of Script
	(method (changeState newState &tmp i invItem)
		(switchto (= state newState)
			(
				(HandsOff)
				(Points ptsTakeAShower)
				(TPrint "Now, that's a good idea, Patti.
					Use Krapper's shower to wash that toner
					from your hands and face.")
				(sDoor
					setCycle: 	EndLoop self,
				)
			)
			(
				(ego
					setMotion: MoveTo 29 139 self
				)
				(shower 			startUpd:)
				(showerSide 	startUpd:)
			)
			(
				(ego
					cycleSpeed: 12,
					view: 		vPattiInBathroom,
					loop: 		2,
					setCycle: 	EndLoop self
				)
			)
			(
				(sDoor cel: 0)
				(ego
					setLoop: 	3,
					setCycle: 	EndLoop self
				)
				(clothes init:)
			)
			(
				(TPrint "As you turn what appears to be a gold-plated faucet,
					you feel the \"shower\" begin to move!")
				(theMusic2
					number: sElevator,
					play:,
					hold: 10
				)
				(sDoor setPri: pBLUE, setMotion: MoveTo 26 226 self)
				(ego setPri: pBLACK, setMotion: MoveTo 29 226 self, moveSpeed: 6)
				(shower setPri: pBLUE, setMotion: MoveTo 25 226 self)
				(showerSide setPri: pBLUE, setMotion: MoveTo 26 226 self)
			)
			(
				;**	leave all her inventory in room 850
				(for	((= i 0))
						(< i (Inventory size?))
						((++ i))
					(= invItem (Inventory at: i))
					(if (invItem ownedBy: PATTI)
						(invItem owner: 1000)
					)
				)
				(TPrint "Where will you end up on this naked adventure?")
				(TPrint "And without your inventory, too!")
				(curRoom newRoom: pK-RAPRadioAtrium)
			)
		)
	)
)
(instance ActionsKRAP of Actions
	(method (doit)
		(return FALSE)					;fixes a bug caused by (Patti doVerb)
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbLook
				(if (== (ego view?) vPattiBlackface)
					(Say ego "\"Oh no,\" you think.
						\"How will I ever get this stuff off me?\"")
					(return TRUE)
  				else
					(TPrint "You look yourself over.
						You like what you see.
						\"Although I could stand to lose a few pounds,\"
						you think.")
					(return TRUE)
				)
			)
			(else
				(return FALSE)
			)
		)
	)
)



