;**
;**	Logics for Room 840 -- John Krapper's office
;**
;**	LSL5:  Passionate Patti Does A Little Undercover Work
;**				Copyright 1991
;**				by Al Lowe
;**
;**	Programmed by John Hartin
;**
;**	Last Update:  September 12, 1991
;**


(script#		840)

(public
	rm840		0
)

(define	pJohnKrapper'sOffice			840)
(define	vJohnKrapper'sOffice		840)
	(define	lSingleCells		0)
		(define	cLetterOpener 0)
		(define	cGlintingKey  1)
	(define	lDoorToBath			1)
	(define	lDoorToLobby		2)
	(define	lCopierRunning		3)
	(define	lCopierExploding	4)
	(define	lPaperRunning		5)
;	(define	lCopierJamming		4)
(define	vPattiInOffice				841)
	(define	lOpeningDrawer		0)
	(define	lTakingPapers		1)
	(define	lTakingKey			2)
	(define	lPuttingPapers		3)
	(define	lPressingButton	4)
	(define	lOpeningCopier		5)
	(define	lBlackOpeningDrawer	6)
	(define	lBlackTakingKey	7)
	(define	lBlackTakingPapers		8)
(define  vPattiBlackFace	842)

(define	sCopyMachine	841)
(define	sCopyExplodes	842)
(define	sPickLock	843)
(define	mKRAPRap1	800)
(define	mKRAPRap2	801)
(define	mKRAPRap3	802)

(local
	haveSeenBothThings
)


(instance rm840 of LLRoom
	(properties
		picture		pJohnKrapper'sOffice
		east			820
		west			850
 		description "Mr. Krapper's office"
		lookStr 		"John Krapper's Office is beautifully decorated in
						the latest high-tech look.
						Numerous halogen narrow-beam spotlights bathe the otherwise
						dark room with small brilliantly-white pools of light."
	)

	(method (init)
		(LoadMany VIEW
			vJohnKrapper'sOffice
			vPattiInOffice
			vPattiBlackFace
		)
		(LoadMany SOUND
			sCopyMachine
			sPickLock
			mKRAPRap1
			mKRAPRap2
			mKRAPRap3
		)
		(LoadMany SCRIPT
			REVERSE
		)
		(HandsOn)
		(ego
			init:			,
			normalize:	(if (Btst fPattiBlackface) vPattiBlackFace else vPatti),
		)
		(if (Btst fPattiBlackface)
			(ego actions: ActionsKRAP)
		)
		(switch prevRoomNum
			(east
				(lobbyDoor setPri: pLRED)
				(ego
					posn:			268 152,
					setHeading:	270,
					edgeHit:		0,
					observeControl: cBLUE cGREEN
				)
				(Points ptsGotByReceptionist pfGotByReceptionist)
			)
			(west
				(ego
					posn:			35 155,
					setHeading: 90,
					edgeHit:		0,
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
		(johnDoor init:, approachVerbs: verbDo verbUse)
		(copier init:, approachVerbs: verbDo verbUse verbLook, stopUpd:)
		(opener init:, approachVerbs: verbDo)
;		(myKey init:, approachVerbs: verbDo)

		;init features
		(table init:)
		(sofa init:)
		(chair init:)
		(lobbyDoor init:, approachVerbs: verbDo verbUse, caller: rm840)
		(telephone init:, approachVerbs: verbLook verbDo)
		(computer init:, approachVerbs: verbLook verbDo)
		(stuff init:)
		(lamp1 init:)
		(lamp2 init:)
		(lamp3 init:)
		(lamp4 init:)
		(desk init:, approachVerbs: verbDo verbLook verbUse)
		(plant init:, approachVerbs: verbDo verbUse)
		(myWindow init:)

		; Polygon Editor 1.11
		; Dynamic Obstacles : Picture 840
		(curRoom addObstacle:
			((Polygon new:)
				type: PBarredAccess,
				init:	0 0 319 0 319 142 268 146 267 139 255 122 235 122 226 113
						92 113 79 113 96 119 96 133 70 139 49 149 33 147 0 139,
				yourself
			)
			((Polygon new:)
				type: PBarredAccess,
				init:	0 155 33 155 18 187 316 187 316 180 282 156 319 156 319 189
						0 189,
				yourself
			)
			((Polygon new:)
				type: PBarredAccess,
				init:	101 116 220 116 225 127 108 127,
				yourself
			)
			((Polygon new:)
				type: PBarredAccess,
				init:	71 142 148 129 176 134 176 141 124 154 147 164 147 173 84 184
						74 165,
				yourself
			)
			((Polygon new:)
				type: PBarredAccess,
				init:	148 155 160 149 175 149 208 149 218 151 239 158 237 165
						228 170 161 168 148 161,
				yourself
			)
			((Polygon new:)
				type: PBarredAccess,
				init:	211 141 245 135 262 144 268 155 238 155 218 148,
				yourself
			)
		)
		(if (== prevRoomNum east)
			(TPrint "Whew!  You made it!")
		)
		(mouseDownHandler add: self)
		(keyDownHandler	add: self)
	)
	(method (cue)
		(lobbyDoor ignoreActors:)
		(lobbyDoor caller: 0)
	)
	(method (dispose)
		(LoadMany FALSE
			REVERSE
		)
		(mouseDownHandler delete: self)
		(keyDownHandler	delete: self)
		(ActionsKRAP dispose:)
		(super dispose:)
	)
)

(instance desk of Feature
	(properties
		x 150
		y 115
		approachX 150
		approachY 112
		heading 0
		nsLeft 113
		nsTop 90
		nsBottom 126
		nsRight 214
 		description "the desk"
		sightAngle 40
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbLook
				(cond
					((not (Btst fDrawerOpen))
						(TPrint "John Krapper's granite and stainless steel
							desk is spotless.
							It holds only a computer and a telephone...")
						(if (not (ego has: iLetterOpener))
							(TPrint "...and an imitation gold-plated needle-sharp
								letter opener.")
						else
							(TPrint "...and a locked file drawer.")
						)
					)
					((not (Btst fHasNumber))
						(Bset fHasNumber)
						(ego setScript: lookDeskScript)
					)
					((not (ego has: iFolderOfEvidence))
						(ego get: iFolderOfEvidence)
						(ego setScript: lookPapersScript)
					)
					(else
						(ego setScript: sLookDesk2ndTime)
					)
				)
			)
			(verbDo
				(cond
					((and (not (ego has: iLetterOpener))
							(not (Btst fDrawerOpen)))
						(Points ptsGotLetterOpener pfGotLetterOpener)
						(TPrint "Never knowing when you might get some mail,
							you decide to take Krapper's
							letter opener from his desk.")
						(opener hide:)
						(ego get: iLetterOpener)
					)
					((not (Btst fDrawerOpen))
						(TPrint "Trying the drawers of Krapper's desk,
							you find them securely locked.")
					)
					((not (Btst fHasNumber))
						(Bset fHasNumber)
						(ego setScript: openDeskScript)
					)
					((not (ego has: iFolderOfEvidence))
						(ego get: iFolderOfEvidence)
						(ego setScript: getPapersScript)
					)
					(else
						(ego setScript: sOpenDesk2ndTime)
					)
				)
			)
			(verbUse
				(switch invItem
					(iLetterOpener
						(cond
							;((ego has: iPhotocopies)
							;	(TPrint "The drawer is already open.")
							;)
							((Btst fDrawerOpen)
								(TPrint "You carefully return the letter opener
									to the exact same location on Krapper's desk and
									make sure the drawer is securely locked.
									He'll never even know you touched it.")
								(ego put: iLetterOpener)
								(opener show:)
								(Bclr fDrawerOpen)
							)
							(else
								(Bset fDrawerOpen)
								(ego setScript: pickLockScript)
							)
						)
					)
					(iDeskKey
						(if (Btst fDrawerOpen)
							(Bclr fDrawerOpen)
							(ego setScript: lockDeskScript)
						else
							(Bset fDrawerOpen)
							(ego setScript: unlockDeskScript)
						)
					)
					(iFolderOfEvidence
						(if (Btst fDrawerOpen)
							(ego put: iFolderOfEvidence)
							;(Bclr fDrawerOpen)
							(ego setScript: putPapersScript)
						else
							(TPrint "You would return the folder to the drawer,
								but the desk is locked.")
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

(instance plant of Feature
	(properties
		x 248
		y 81
		heading 0
		nsLeft 208
		nsTop 40
		nsBottom 122
		nsRight 290
		approachX		231
		approachY		120
 		description 	"the potted plant"
		sightAngle 40
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbLook
				(cond
					((ego has: iDeskKey)
						(TPrint "Hmm. This tree could use a good watering.")
					)
					((Btst fSeenGlint)
						(TPrint "Look!
							There's a key almost hidden in the plant's humus.")
					)
					(else
						(TPrint "You see a glint of metal coming from the humus
							in the pot at the base of the tree.")
						(Bset fSeenGlint)
					)
				)
			)
			(verbDo
				(if (not (ego has: iDeskKey))
					(if (Btst fSeenGlint)
						(ego setScript: getKeyScript)
					else
						(ego setScript: sPawThruPlant)
					)
				else
					(super doVerb: theVerb invItem &rest)
				)
			)
			(verbUse
				(if (== invItem iDeskKey)
					(ego put: iDeskKey)
					(ego setScript: replaceKeyScript)
				else
					(super doVerb: theVerb invItem &rest)
				)
			)
			(else
				(super doVerb: theVerb invItem &rest)
			)
		)
	)
)

(instance copier of Prop
	(properties
		view			vJohnKrapper'sOffice
		loop			3
		cel			0
		x				58
		y				138
		z				20
		priority		pLGREEN
		signal		fixPriOn
		cycleSpeed	18
 		description "the copier"
		lookStr 		"It's a genuine ZeroZ 9000, the latest in personal copiers."
		approachX		69
		approachY		140
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbDo
				(if (ego has: iPhotocopies)
					(TPrint "The copier is now broken!
						You should know; you broke it!")
				else
					(TPrint "\"To use the `Zeroz 9000,' simply insert the pages
						you wish to copy.\"")
				)
			)
			(verbLook
				(if (ego has: iPhotocopies)
					(TPrint "It WAS a genuine ZeroZ 9000;
						now it's a piece of trash!")
				else
					(TPrint "It's a genuine ZeroZ 9000, the latest in personal copiers.")
				)
			)
			(verbUse
				(switch invItem
					(iFolderOfEvidence
						(if (ego has: iPhotocopies)
							(TPrint "The copier is now broken!
								You should know; you broke it!")
						else
							(ego setScript: runCopierScript)
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

(instance paper of Prop
	(properties
		view			vJohnKrapper'sOffice
		loop			5
		cel			0
		x				58
		y				112
		priority		10
		signal		fixPriOn
		cycleSpeed	12
 		description "the copier"
		lookStr 		"It's a genuine ZeroZ 9000, the latest in personal	copiers."
		approachX		68
		approachY		138
	)
)

(instance johnDoor of Door
	(properties
		view			vJohnKrapper'sOffice
		loop			1
		cel			0
		x				11
		y				153
		entranceTo	850
		exitType		doorWalkEgo
		enterType	doorWalkEgo
 		description "the bathroom door"
		lookStr 		"A sign on the door says \"Krapper's Crapper.\"
						A smaller sign below it says \"John's John\"."
		approachX	44
		approachY	152
		moveToX		0
		moveToY		141
		signal		ignrAct
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbDo
				(cond
					((ego has: iFolderOfEvidence)
						(TPrint "Mr. Krapper will surely miss his file folder,
							Patti! You'd better return it.")
					)
					((ego has: iDeskKey)
						(TPrint "Mr. Krapper will surely miss his desk key, Patti!
							You'd better return it.")
					)
					((ego has: iLetterOpener)
						(TPrint "Mr. Krapper will surely miss his letter opener,
							Patti! You'd better return it.")
					)
					(else
						(ego ignoreControl: cGREEN)
						(self setPri: 15)
						(super doVerb: theVerb invItem &rest)
					)
				)
			)
			(else
				(super doVerb: theVerb invItem &rest)
			)
		)
	)
	(method (close)
		(ego observeControl: cGREEN cBLUE)
		(super close:)
	)
)

(instance lobbyDoor of Door
	(properties
		view			vJohnKrapper'sOffice
		loop			2
		cel			0
		x				302
		y				153
		entranceTo	820
		enterType	doorWalkEgo
 		description "the lobby door"
		lookStr 		"This is the door through which you entered.
						If you walked out it, you would return to the lobby."
		approachX	269
		approachY	159
		moveToX		280
		moveToY		150
		signal		ignrAct
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbDo
				(ego setScript: peekScript)
			)
			(else
				(super doVerb: theVerb invItem &rest)
			)
		)
	)
	(method (close)
		(self setPri: RELEASE)
		(super close:)
	)
)

(instance table of Feature
	(properties
		x 192
		y 157
		z 0
		heading 0
		nsLeft 152
		nsTop 149
		nsBottom 165
		nsRight 232
 		description "the table"
		lookStr 		"Krapper has a beautiful glass coffee table."
		sightAngle 40
	)
)
(instance chair of Feature
	(properties
		x 237
		y 139
		z 0
		heading 0
		nsLeft 222
		nsTop 120
		nsBottom 152
		nsRight 256
 		description "the chair"
		lookStr 		"This is the kind of furniture you would buy if you
						ever settled down."
		sightAngle 40
	)
)
(instance sofa of Feature
	(properties
		x 112
		y 153
		z 0
		heading 0
		nsLeft 83
		nsTop 126
		nsBottom 178
		nsRight 141
 		description "the sofa"
		lookStr 		"What an unusual sofa! You so love modern furniture."
		sightAngle 40
	)
)
(instance telephone of Feature
	(properties
		x 194
		y 116
		z 20
		heading 0
		nsLeft 187
		nsTop 96
		nsBottom 106
		nsRight 202
		approachX 200
		approachY 112
 		description "the telephone"
		lookStr 		"John Krapper's telephone has very few buttons.
						He must be REALLY important!"
		sightAngle 40
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbDo
				(TPrint "You'd better not use this telephone, Patti.
					You might get caught by the switchboard operator.")
			)
			(else
				(super doVerb: theVerb invItem &rest)
			)
		)
	)
)

(instance computer of Feature
	(properties
		x 137
		y 116
		z 20
		heading 0
		nsLeft 127
		nsTop 88
		nsBottom 105
		nsRight 148
		approachX 140
		approachY 112
 		description "the desk computer"
		lookStr 		"Look at the dust on that keyboard!
						He must never use his computer!"
		sightAngle 40
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbDo
				(TPrint "You briefly consider booting up John Krapper's computer
					in an attempt to contact Inspector Desmond,
					but realize there's no way his computer would be
					connected to FBI-Net!")
			)
			(else
				(super doVerb: theVerb invItem &rest)
			)
		)
	)
)

(instance myWindow of Feature
	(properties
		x 161
		y 73
		z 0
		heading 0
		nsLeft 66
		nsTop 39
		nsBottom 107
		nsRight 256
 		description "the window"
		lookStr 		"Krapper's office has a giant window looking out over
						beautiful downtown Philadelphia."
		sightAngle 40
	)
)

(instance stuff of Feature
	(properties
		x 53
		y 72
		z 0
		heading 0
		nsLeft 33
		nsTop 43
		nsBottom 102
		nsRight 73
 		description "the shelves"
		lookStr 		"Krapper's bookcase is filled with items,
						all of them useless to your mission."
		sightAngle 40
	)
)
(instance lamp1 of Feature
	(properties
		x 59
		y 23
		z 0
		heading 0
		nsLeft 53
		nsTop 17
		nsBottom 29
		nsRight 65
 		description "the lamp"
		lookStr 		"The small halogen spotlights cast a pure white light."
		sightAngle 40
	)
)
(instance lamp2 of Feature
	(properties
		x 99
		y 6
		z 0
		heading 0
		nsLeft 93
		nsTop 0
		nsBottom 13
		nsRight 106
 		description "the lamp"
		lookStr 		"The small halogen spotlights cast a pure white light."
		sightAngle 40
	)
)
(instance lamp3 of Feature
	(properties
		x 163
		y 22
		z 0
		heading 0
		nsLeft 158
		nsTop 18
		nsBottom 26
		nsRight 169
 		description "the lamp"
		lookStr 		"The small halogen spotlights cast a pure white light."
		sightAngle 40
	)
)
(instance lamp4 of Feature
	(properties
		x 260
		y 24
		z 0
		heading 0
		nsLeft 253
		nsTop 20
		nsBottom 29
		nsRight 267
 		description "the lamp"
		lookStr 		"The small halogen spotlights cast a pure white light."
		sightAngle 40
	)
)

(instance opener of View
	(properties
		view			vJohnKrapper'sOffice
		loop			lSingleCells
		cel			cLetterOpener
		x				155
		y				105
		approachX	187
		approachY	118
		priority		pLBLUE
		signal		fixPriOn
 		description "the letter opener"
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbLook
				(TPrint "There's a needle-sharp letter opener lying on
					Krapper's desk.")
			)
			(verbDo
				(TPrint "Never knowing when you might get some mail,
					you decide to take Krapper's letter opener from his desk.")
				(opener hide:)
				(ego get: iLetterOpener)
			)
			(else
				(super doVerb: theVerb invItem &rest)
			)
		)
	)
)

(instance copyMachineSound of Sound
	(properties
		number	sCopyMachine
		flags		TRUE
		priority	11
	)
)
(instance copyExplodesSound of Sound
	(properties
		number	sCopyExplodes
		flags		TRUE
		priority	11
	)
)
(instance pickLockSound of Sound
	(properties
		number	sPickLock
		flags		TRUE
		priority	11
	)
)

(instance peekScript of Script
	(method (changeState newState)
		(switchto (= state newState)
			(
				(HandsOff)
				(lobbyDoor setCel: 1)
				(ego setMotion: MoveTo 272 150 self)
			)
			(
				(ego setLoop: 8, setCel: 0)
				(= seconds 3)
			)
			(
				(TPrint "Opening the doorway to carefully peek outside,
					you notice the receptionist has returned.
					You decide you cannot exit the room that way!"
				)
				(= seconds 3)
			)
			(
				(lobbyDoor setCel: 0)
				(ego
					normalize: (if (Btst fPattiBlackface) vPattiBlackFace else vPatti),
					observeControl: cBLUE cGREEN,
					setScript: 0
				)
				(HandsOn)
			)
		)
	)
)

(instance getKeyScript of Script
	(method (changeState newState)
		(switchto (= state newState)
			(
				(HandsOff)
				(ego
					view: vPattiInOffice,
					loop:	(if (Btst fPattiBlackface) lBlackTakingKey else lTakingKey),
					setCel: 0,
					cycleSpeed: 18,
					setCycle: EndLoop	self
				)
			)
			(
				(ego
					normalize: (if (Btst fPattiBlackface) vPattiBlackFace else vPatti),
					observeControl: cBLUE cGREEN,
					get:	iDeskKey,
					loop:			facingEast,
				)
				(= ticks 30)
			)
			(
				(Points ptsGotKey pfGotKey)
				(TPrint "Bending over at the base of the tree,
					you dig into the humus and find the metallic object
					that you saw earlier.
					It looks like a desk key." HIGH)
				(HandsOn)
				(ego setScript: 0)
			)
		)
	)
)

(instance sPawThruPlant of Script
	(method (changeState newState)
		(switchto (= state newState)
			(
				(HandsOff)
				(ego
					view: vPattiInOffice,
					loop:	(if (Btst fPattiBlackface) lBlackTakingKey else lTakingKey),
					setCel: 0,
					cycleSpeed: 18,
					setCycle: CycleTo 5 FORWARD self
				)
			)
			(
				(ego
					setCycle: CycleTo 0 BACKWARD self
				)
			)
			(
				(ego
					normalize: (if (Btst fPattiBlackface) vPattiBlackFace else vPatti),
					observeControl: cBLUE cGREEN,
					loop:			facingEast,
				)
				(= ticks 30)
			)
			(
				(TPrint "Pawing through the humus in the tree's pot,
					you see a glint of metal.")
				(Bset fSeenGlint)
				(HandsOn)
				(self dispose:)
			)
		)
	)
)

(instance replaceKeyScript of Script
	(method (changeState newState)
		(switchto (= state newState)
			(
				(HandsOff)
				(ego
					view:vPattiInOffice,
					loop:	(if (Btst fPattiBlackface) lBlackTakingKey else lTakingKey),
					setCel:	LASTCEL,
					cycleSpeed: 18,
					setCycle: BegLoop	self
				)
			)
			(
				(ego
					normalize: (if (Btst fPattiBlackface) vPattiBlackFace else vPatti),
					observeControl: cBLUE cGREEN,
					loop:			facingEast,
				)
				(= ticks 30)
			)
			(
				(TPrint "You carefully return the key to its hiding place
					in the humus." HIGH)
				(HandsOn)
				(ego setScript: 0)
			)
		)
	)
)

(instance openDeskScript of Script
	(method (changeState newState &tmp [str 100])
		(switchto (= state newState)
			(
				(HandsOff)
				(ego
					view:vPattiInOffice,
					loop:	(if (Btst fPattiBlackface) lBlackOpeningDrawer else lOpeningDrawer),
					setCel: 0,
					cycleSpeed: 18,
					setCycle: EndLoop	self
				)
			)
			(
				(= ticks 123)
			)
			(
				(ego
					setCel:	LASTCEL,
					setCycle: BegLoop	self
				)
			)
			(
				(= ticks 123)
			)
			(
				(Format @str "Peering through John Krapper's personal
					(formerly-locked) file drawer, you notice a number
					written on a small blue stickum!
					Why in the world would he want to remember %u?"
					(= roomBNumber (Random 10000 65000))
				)
				(Points ptsOpenDesk pfOpenDesk)
				(TPrint @str)
				(ego
					normalize: 			(if (Btst fPattiBlackface) vPattiBlackFace else vPatti),
					observeControl:	cBLUE cGREEN,
					loop:			facingSouth,
					setScript:			FALSE,
				)
				(HandsOn)
			)
		)
	)
)

(instance sOpenDesk2ndTime of Script
	(method (changeState newState &tmp [str 100])
		(switchto (= state newState)
			(
				(HandsOff)
				(ego
					view:vPattiInOffice,
					loop:	(if (Btst fPattiBlackface) lBlackOpeningDrawer else lOpeningDrawer),
					setCel: 0,
					cycleSpeed: 18,
					setCycle: EndLoop	self
				)
			)
			(
				(= ticks 123)
			)
			(
				(ego
					setCel:	LASTCEL,
					setCycle: BegLoop	self
				)
			)
			(
				(= ticks 123)
			)
			(
				(Format @str "Where's that little blue stickum?
					Oh, here it is! %u" roomBNumber)
				(TPrint @str)
				(ego
					normalize:			(if (Btst fPattiBlackface) vPattiBlackFace else vPatti),
					observeControl:	cBLUE cGREEN,
					loop:			facingSouth,
					setScript:			FALSE,
				)
				(HandsOn)
			)
		)
	)
)

(instance lookDeskScript of Script
	(method (changeState newState &tmp [str 100])
		(switchto (= state newState)
			(
				(HandsOff)
				(ego
					view:vPattiInOffice,
					loop:	(if (Btst fPattiBlackface) lBlackOpeningDrawer else lOpeningDrawer),
					cycleSpeed: 18,
					setCel: 0,
					setCycle: EndLoop	self
				)
			)
			(
				(= ticks 123)
			)
			(
				(ego
					setCel:	LASTCEL,
					setCycle: BegLoop	self
				)
			)
			(
				(= ticks 123)
			)
			(
				(Format @str "Peering through John Krapper's personal
					(formerly-locked) file drawer, you notice a number
					written on a small blue stickum!
					Why in the world would he want to remember %u?"
					(= roomBNumber (Random 10000 65000))
				)
				(Points ptsLookDesk pfOpenDesk)
				(TPrint @str)
				(ego
					normalize: (if (Btst fPattiBlackface) vPattiBlackFace else vPatti),
					observeControl: cBLUE cGREEN,
					loop:			facingSouth,
					setScript: 0
				)
				(HandsOn)
			)
		)
	)
)

(instance sLookDesk2ndTime of Script
	(method (changeState newState &tmp [str 100])
		(switchto (= state newState)
			(
				(HandsOff)
				(ego
					view:vPattiInOffice,
					loop:	(if (Btst fPattiBlackface) lBlackOpeningDrawer else lOpeningDrawer),
					setCel: 0,
					cycleSpeed: 18,
					setCycle: EndLoop	self
				)
			)
			(
				(= ticks 123)
			)
			(
				(ego
					setCel:	LASTCEL,
					setCycle: BegLoop	self
				)
			)
			(
				(= ticks 123)
			)
			(
				(Format @str "Where's that little blue stickum?
					Oh, here it is! %u" roomBNumber)
				(TPrint @str)
				(ego
					normalize: (if (Btst fPattiBlackface) vPattiBlackFace else vPatti),
					observeControl: cBLUE cGREEN,
					loop:			facingSouth,
					setScript: 0
				)
				(HandsOn)
			)
		)
	)
)

(instance pickLockScript of Script
	(method (changeState newState)
		(switchto (= state newState)
			(
				(HandsOff)
				(ego
					view:vPattiInOffice,
					loop:	(if (Btst fPattiBlackface) lBlackOpeningDrawer else lOpeningDrawer),
					setCel: 0,
					cycleSpeed: 18,
					setCycle: EndLoop	self
				)
			)
			(
				(pickLockSound play:)
				(ego
					setCel:	LASTCEL,
					setCycle: BegLoop	self
				)
			)
			(
				(Points ptsPickLock pfUnlockDesk)
				(TPrint "Prying away at the desk's inefficient lock with
					Krapper's letter opener, you find a weak spot and
					carefully pry the drawer open.")
				(ego
					normalize: (if (Btst fPattiBlackface) vPattiBlackFace else vPatti),
					observeControl: cBLUE cGREEN,
					loop:			facingSouth,
					setScript: 0
				)
				(HandsOn)
			)
		)
	)
)

(instance unlockDeskScript of Script
	(method (changeState newState)
		(switchto (= state newState)
			(
				(HandsOff)
				(ego
					view:vPattiInOffice,
					loop:	(if (Btst fPattiBlackface) lBlackOpeningDrawer else lOpeningDrawer),
					setCel: 0,
					cycleSpeed: 18,
					setCycle: EndLoop	self
				)
			)
			(
				(pickLockSound play:)
				(ego
					setCel:	LASTCEL,
					setCycle: BegLoop	self
				)
			)
			(
				(Points ptsUnlockDesk pfUnlockDesk)
				(TPrint "Trying the key you found buried in the tree's pot,
					you insert into the desk and turn it carefully. It opens.")
				(ego
					normalize: (if (Btst fPattiBlackface) vPattiBlackFace else vPatti),
					observeControl: cBLUE cGREEN,
					loop:			facingSouth,
					setScript: 0
				)
				(HandsOn)
			)
		)
	)
)

(instance lockDeskScript of Script
	(method (changeState newState)
		(switchto (= state newState)
			(
				(HandsOff)
				(ego
					view:vPattiInOffice,
					loop:	(if (Btst fPattiBlackface) lBlackOpeningDrawer else lOpeningDrawer),
					setCel: 0,
					cycleSpeed: 18,
					setCycle: EndLoop	self
				)
			)
			(
				(pickLockSound play:)
				(ego
					setCel:	LASTCEL,
					setCycle: BegLoop	self
				)
			)
			(
				(TPrint "You lock the desk.")
				(ego
					normalize: (if (Btst fPattiBlackface) vPattiBlackFace else vPatti),
					observeControl: cBLUE cGREEN,
					loop:			facingSouth,
					setScript: 0
				)
				(HandsOn)
			)
		)
	)
)

(instance getPapersScript of Script
	(method (changeState newState)
		(switchto (= state newState)
			(
				(HandsOff)
				(ego
					view:vPattiInOffice,
					loop:	(if (Btst fPattiBlackface) lBlackOpeningDrawer else lOpeningDrawer),
					;loop: lOpeningDrawer,
					setCel: 0,
					cycleSpeed: 18,
					setCycle: EndLoop	self
				)
			)
			(
				(ego
					view:vPattiInOffice,
					loop:	(if (Btst fPattiBlackface) lBlackTakingPapers else lTakingPapers),
					;loop: lTakingPapers,
					setCel: 0,
					cycleSpeed: 18,
					setCycle: EndLoop	self
				)
			)
			(
				(= ticks 123)
			)
			(
				(Points ptsGetFolder pfGetFolder)
				(TPrint "Searching the desk drawer,
					you find the folders marked \"Personal.\"")
				(ego
					normalize: (if (Btst fPattiBlackface) vPattiBlackFace else vPatti),
					observeControl: cBLUE cGREEN,
					loop:			facingSouth,
					setScript: 0
				)
				(HandsOn)
			)
		)
	)
)

(instance lookPapersScript of Script
	(method (changeState newState)
		(switchto (= state newState)
			(
				(HandsOff)
				(ego
					view:vPattiInOffice,
					loop:	(if (Btst fPattiBlackface) lBlackOpeningDrawer else lOpeningDrawer),
					;loop: lOpeningDrawer,
					setCel: 0,
					cycleSpeed: 18,
					setCycle: EndLoop	self
				)
			)
			(
				(TPrint "Carefully searching through the many drawers
					of Krapper's desk,
					you find a folder marked \"Personal.\"")
				(= ticks 60)
			)
			(
				(TPrint "For a moment, you wonder if you should take it.")
				(= ticks 60)
			)
			(
				(ego
					view:vPattiInOffice,
					loop:	(if (Btst fPattiBlackface) lBlackTakingPapers else lTakingPapers),
					;loop: lTakingPapers,
					setCel: 0,
					cycleSpeed: 18,
					setCycle: EndLoop	self
				)
			)
			(
				(Points ptsLookFolder pfGetFolder)
				(TPrint "But of course you do!")
				(= ticks 123)
			)
			(
				(ego
					normalize: (if (Btst fPattiBlackface) vPattiBlackFace else vPatti),
					observeControl: cBLUE cGREEN,
					loop:			facingSouth,
					setScript: 0
				)
				(HandsOn)
			)
		)
	)
)

(instance putPapersScript of Script
	(method (changeState newState)
		(switchto (= state newState)
			(
				(HandsOff)
				(ego
					view:			vPattiInOffice,
					setLoop:		(if (Btst fPattiBlackface) lBlackTakingPapers else lTakingPapers),
					setCel: 		LASTCEL,
					cycleSpeed: 18,
					setCycle: 	BegLoop self
				)
			)
			(
				(= seconds 3)
			)
			(
				(ego
					loop:			(if (Btst fPattiBlackface) lBlackOpeningDrawer else lOpeningDrawer),
					setCel: 		LASTCEL,
					cycleSpeed: 18,
					setCycle: 	BegLoop self
				)
			)
			(
				(= ticks 60)
			)
			(
				(TPrint "You return the folder of evidence to Krapper's desk,
					and close his desk drawer,
					being careful to leave everything exactly as you found it.")
				(ego
					normalize: (if (Btst fPattiBlackface) vPattiBlackFace else vPatti),
					observeControl: cBLUE cGREEN,
					loop:			facingSouth,
					setScript: 0
				)
				(HandsOn)
			)
		)
	)
)

(instance runCopierScript of Script
	(method (changeState newState)
		(switchto (= state newState)
			(
				(HandsOff)
				(ego
					view: vPattiInOffice,
					loop: lPuttingPapers,
					cycleSpeed: 18,
					x				69,
					y				140,
					setCycle: EndLoop self
				)
			)
			(
				(ego
					loop: lPressingButton,
					setCel: LASTCEL,
					setCycle: BegLoop self
				)
			)
			(
				(Points ptsMakeCopies pfMakeCopies)
				(TPrint "You place John Krapper's personal papers in his copier,
					and press the \"Start\" button.")
				(= ticks 123)
			)
			(
				(ego
					loop: lPressingButton,
					setCycle: EndLoop self
				)
			)
			(
				(copyMachineSound play:, setLoop: -1)
				(copier
					setCel:	0,
					setCycle: EndLoop self,	;copier running
					setPri: 9,
				)
			)
			(
				(copier	stopUpd:)
				(= cycles 1)
			)
			(
				(paper
					init:,
					setCel:	0,
					setPri:	9,
					setCycle: ForwardCounter 5 self,
				)
			)
			(
				(copyMachineSound stop:)
				(= ticks 200)
			)
			(
				(Say ego "\"Hey, what's the matter with this thing?\" you think.
					\"Why did it stop working?
					What did I do, jam it?\"")
				(= seconds 3)
			)
			(
				(ego
					loop: lPuttingPapers,
					setCel: LASTCEL,
					setCycle: BegLoop self
				)
			)
			(
				(paper dispose:)
				(copier
					setCel:	0,
				)
				(Say ego "\"Oh well, at least I got the most important evidence
					copied before it jammed,\" you think,
					grabbing your copies from the machine.")
				(= seconds 3)
			)
			(
				(ego
					loop: lPressingButton,
					setCel: 0,
					setCycle: EndLoop self
				)
			)
			(
				(Say ego "\"What does this switch here do?\" you wonder,
					pulling on something that doesn't belong to you.")
				(= ticks 123)
			)
			(
				(TPrint "(An experience not wholly unfamiliar to you!)" LOW)
				(= ticks 60)
			)
			(
				(ego
					loop: lPressingButton,
					setCel: LASTCEL,
					setCycle: BegLoop self
				)
			)
			(
				(ego
					normalize: vPatti,
					loop:			facingWest,
				)
				(= ticks 60)
			)
			(
				(copyExplodesSound play:)
				(copier
					view:	vJohnKrapper'sOffice,
					startUpd:,
					cycleSpeed: 12,
					setCel: 0,
					setLoop: lCopierExploding,	;copier jamming
					setCycle: CycleTo 1 FORWARD self
				)
			)
			(
				(ego
					normalize: vPattiBlackFace,
					observeControl: cBLUE cGREEN,
					loop:			facingWest,
				)
				(copier
					view:	vJohnKrapper'sOffice,
					setPri: 11,
					setCel: 2,
					setCycle: CycleTo 4 FORWARD self
				)
			)
			(
				(copier
					view:	vJohnKrapper'sOffice,
					setPri: 9,
					setCycle: EndLoop self
				)
			)
			(
				(= seconds 3)
			)
			(
				(Say ego "\"GREAT!\" you think.
					\"Now just look at me!
					I'm covered with toner from head to toe!\""
				)
				(copier stopUpd:)
				(Bset fPattiBlackface)
				(ego
					get: iPhotocopies,
					actions: ActionsKRAP,
					setScript: 0
				)
				(HandsOn)
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
				(if (== (ego view?) vPattiBlackFace)
					(TPrint "\"Oh no,\" you think,
						\"how am I ever going to get this stuff off?\"")
					(return TRUE)
  				else
					(TPrint "You look yourself over.
						You like what you see.
						\"Although, I could stand to lose a few pounds,\"
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


