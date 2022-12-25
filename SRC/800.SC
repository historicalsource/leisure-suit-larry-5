;**
;**	Logics for Room 800 -- K-RAP Radio exterior
;**
;**	LSL5:  Passionate Patti Does A Little Undercover Work
;**				Copyright 1991
;**				by Al Lowe
;**
;**	Programmed by John Hartin
;**
;**	Last Update:  September 12, 1991
;**

(script#		800)

(public
	rm800		0
)


(define	vRoom							800)
(define	vRoomEgo			 (+ 1 vRoom))

(define	pK-RAPRadioLobby				820)
(define	pInsideControlRoomB			890)
(define	pK-RAPRadioExterior			800)

(define	vK-RAPRadioExterior		800)
	(define	lK-RAPSign			0)
	(define	lDoor					1)
	(define	lLSpeaker			2)
	(define	lRSpeaker			3)

(define	pK-RAPRadioReverse			810)
(define	vK-RAPRadioReverse		810)
	(define	lKRAPsign			0)
	(define	lAirplane			1)
	(define	lLightsOfTheCity	2-3)

(define	mKRAPRap1	800)
(define	mKRAPRap2	801)
(define	mKRAPRap3	802)
(define	sfxCarDoorOpen 		191)
(define	sfxCarDoorClose		192)

;**	RoomScript state values

(local
	seenMsg
)

(instance rm800 of LLRoom
	(properties
		picture		800
		north			820
		lookStr 		"You are outside the studios of `K-Rap Radio.'
						It appears to be an amazingly small building for a
						station of such importance."
	)

	(method (init)
		(LoadMany VIEW
			vK-RAPRadioExterior
			vK-RAPRadioReverse
			vPattiSmaller
		)
		(LoadMany SOUND
			mKRAPRap1
			mKRAPRap2
			mKRAPRap3
			sfxCarDoorOpen
			sfxCarDoorClose
		)

		(super init:)
		(switch prevRoomNum
			(pK-RAPRadioLobby
	 			(self style: PIXELDISSOLVE)
				(ego
					init:	,
					normalize: vPattiSmaller,
					posn:			153 143,
					setHeading:	180,
					edgeHit:		0	,
				)
				(theMusic setVol: lastVolume)
				(HandsOn)
			)
			(200
				(theMusic stop:)
				(WrapMusic
					firstSound: mKRAPRap1,
					lastSound: 	mKRAPRap3,
					init:
				)
	 			(self style: IRISOUT)
				(self setScript: sExitLimo)
			)
			(else
				(theMusic stop:)
				(WrapMusic
					firstSound: mKRAPRap1,
					lastSound: 	mKRAPRap3,
					init:
				)
				(= lastVolume (theMusic vol?))
				(if (== numColors	256)
	 				(self style: PIXELDISSOLVE)
				else
	 				(self style: DISSOLVE)
				)
				(ego
					init:	,
					normalize: vPattiSmaller,
					posn:			155 153,
					setHeading:	180,
					edgeHit:		0
				)
				(HandsOn)
				(self setScript: exitDreamScript)
			)
		)
		(bush1 init:)
		(bush2 init:)
		(bush3 init:)
		(bush4 init:)
		(drumpicture init:)
		(tower init:)
		(tree1 init:)
		(limo init:, approachVerbs: verbDo verbUse)
		(door init:, openScript: sIntoBuilding)
		(neonSign init:)
		(cone1 init:)
		(cone2 init:)
		(cast eachElementDo: #checkDetail)
		(curRoom addObstacle:
			((Polygon new:)
				type: PBarredAccess,
				init:	0 0 139 0 139 144 5 147 5 153 10 156 78 156 103 153 129 152
						143 162 184 162 184 154 200 151 282 149 294 148 314 144
						253 143 167 143 167 0 319 0 319 189 0 189,
				yourself
			)
		)
	)

	(method (newRoom n)
		(if (== n 820)
			(ego edgeHit: NORTH)
		)
		(Cls)
		(super newRoom: n)
	)
)


(instance limo of Feature
	(properties
		x 62
		y 161
		approachX		26
		approachY		152
		heading 0
		nsLeft 0
		nsTop 148
		nsBottom 174
		nsRight 125
		description	"your limousine"
		lookStr		"Your limousine awaits your return."
	)
	(method (doVerb theVerb)
		(switch theVerb
			(verbDo
				(rm800 setScript: sEnterLimo)
			)
			(else
				(super doVerb: theVerb &rest)
			)
		)
	)
)


(instance neonSign of Prop
	(properties
		view			vK-RAPRadioExterior
		loop			lK-RAPSign
		cel			8
		x				156
		y				27
		description	"the K-RAP sign"
		lookStr		"High above your head, K-RAP's neon sign illuminates
						the Baltimore night."
		detailLevel	3
	)
	(method (init)
		(super init:)
		(self
			setCycle: Forward,
			checkDetail:
		)
	)
)

(instance door of Door
	(properties
		view			vK-RAPRadioExterior
		loop			1
		cel			0
		x				153
		y				138
		moveToX		153
		moveToY		140
		approachX	153
		approachY	144
		exitType		doorWalkEgo
		enterType	doorWalkEgo
		entranceTo	820
		description	"the door"
		lookStr		"K-RAP's doors are always open."
		priority		pLBLUE
		signal		fixPriOn
	)
)

(instance cone1 of Prop
	(properties
		view			vK-RAPRadioExterior
		loop			2
		cel			0
		x				71
		y				118
		description	"the left speaker"
		lookStr		"K-RAP's loudspeakers broadcast the station's current
						programming."
		detailLevel	5
	)
	(method (init)
		(super init:)
		(self
			setCycle: RandCycle,
			checkDetail:
		)
	)
)

(instance cone2 of Prop
	(properties
		view			800
		loop			3
		cel			0
		x				235
		y				114
		description	"the right speaker"
		lookStr		"K-RAP's loudspeakers broadcast the station's current
						programming."
		detailLevel	5
	)
	(method (init)
		(super init:)
		(self
			setCycle: RandCycle,
			checkDetail:
		)
	)
)

(instance bush1 of Feature
	(properties
		x 50
		y 136
		z 0
		heading 0
		nsLeft 36
		nsTop 125
		nsBottom 147
		nsRight 64
 		description "the bush"
		lookStr 		"Someone planted these bushes right in the middle of
						this busy sidewalk!"
		sightAngle 40
	)
)
(instance bush2 of Feature
	(properties
		x 107
		y 135
		z 0
		heading 0
		nsLeft 89
		nsTop 123
		nsBottom 148
		nsRight 125
		sightAngle 40
 		description "the bush"
		lookStr 		"Someone planted these bushes right in the middle of
						this busy sidewalk!"
	)
)
(instance bush3 of Feature
	(properties
		x 210
		y 134
		z 0
		heading 0
		nsLeft 196
		nsTop 117
		nsBottom 151
		nsRight 224
		sightAngle 40
 		description "the bush"
		lookStr 		"Someone planted these bushes right in the middle of
						this busy sidewalk!"
	)
)
(instance bush4 of Feature
	(properties
		x 283
		y 123
		z 0
		heading 0
		nsLeft 268
		nsTop 95
		nsBottom 151
		nsRight 299
		sightAngle 40
 		description "the bush"
		lookStr 		"Someone planted these bushes right in the middle of
						this busy sidewalk!"
	)
)
(instance drumpicture of Feature
	(properties
		x 161
		y 81
		z 0
		heading 0
		nsLeft 112
		nsTop 65
		nsBottom 98
		nsRight 210
 		description "the mural"
		lookStr 		"K-RAP radio has a mural of musicians on their wall."
		sightAngle 40
	)
)
(instance tower of Feature
	(properties
		x 262
		y 36
		z 0
		heading 0
		nsLeft 225
		nsTop 0
		nsBottom 90
		nsRight 303
 		description "the tower"
		lookStr 		"The tower holds the antennas that broadcast K-RAP's
						stereo multiplexed signal to millions of people
						(some of whom listen!)."
		sightAngle 40
	)
)
(instance tree1 of Feature
	(properties
		x 24
		y 92
		z 0
		heading 0
		nsLeft 0
		nsTop 44
		nsBottom 141
		nsRight 49
 		description "the trees"
		lookStr 		"A clump of white birch trees grows just outside K-RAP."
		sightAngle 40
	)
)

;animations for pic 810.  just set them up and let'm cycle
(instance logo of Prop
	(properties
		view			810
		loop			0
		cel			12
		x				164
		y				37
		cycleSpeed	12
		detailLevel	3
	)
)
(instance city1 of Prop
	(properties
		view			810
		loop			2
		cel			0
		x				38
		y				59
		cycleSpeed	24
		detailLevel	5
	)
)
(instance city2 of Prop
	(properties
		view			810
		loop			3
		cel			0
		x				193
		y				53
		cycleSpeed	24
		detailLevel	5
	)
)
(instance plane of Actor
	(properties
		view			810
		loop			1
		cel			0
		x				96
		y				13
		cycleSpeed	6
		moveSpeed	22
	)
)

(instance sIntoBuilding of Script
	(method (changeState newState)
		(switchto (= state newState)
			(										;**	0
				(ego hide:)
				(HandsOff)
				(cast
					eachElementDo: #hide:,
				)
				(SetFFRoom 820)
				(if (not (Btst fNotFirstTimeInKRAP))
	 				(rm800 style: IRISOUT)
					(curRoom
						drawPic: 810,
						picture: 810,
						curPic: 810
					)
					(logo init:, setCycle Forward)
					(city1 init:, setCycle Forward)
					(city2 init:, setCycle Forward)
					(plane
						init:			,
						setCycle:	Forward,
						setMotion:	MoveTo -20 5,
						setStep: 	1 1,
					)
					(cast eachElementDo: #checkDetail)
					(Bset fNotFirstTimeInKRAP)
					(= seconds 5)
				else
 					(curRoom newRoom: 820)
				)
			)
			(										;**	1
				(TPrint "Although you can't see it,
					the K-RAP building is quite impressive when viewed
					from the opposite side."
					WIDELOW
					#dispose: self)
			)
			(
				(= ticks 180)
			)
			(										;**	3
				(TPrint "From the tiny top floor you just entered,
					it stretches hundreds of feet down the hillside,
					in a series of cascading terraces, gardens, and patios."
					WIDELOW
					#dispose: self)
			)
			(
				(= ticks 180)
			)
			(										;**	5
				(TPrint "Obviously, there's more to this place than
					meets the eye!"
					WIDELOW
					#dispose: self)
			)
			(
				(= ticks 180)
			)
			(										;**	7
 				(curRoom newRoom: 820)
			)
		)
	)
);sIntoBuilding

(instance exitDreamScript of Script
	(method (changeState newState)
		(switchto (= state newState)
			(
				(= seconds 6)
			)
			(
				(self dispose:)
				(if (Print "Now would be an excellent time for you to save your
					game, Patti."
					#button		{Save} 				1
					#button		{Nah, Why Bother?} 0
					#title		{AL says}
					HIGH
					)
				 	(theGame save:)
				)
			)
		)
	)
)

(instance sExitLimo of Script
	(method (changeState newState &tmp var1 var2)
		(switchto (= state newState)
			(
				;give game time enough for pic to draw...

				(= cycles 2)
			)
			(
				(HandsOff)
				(theMusic2
					number:			sfxCarDoorOpen,
					setLoop:			1,
					play:
				)
				(= ticks	50)
			)
			(
				(ego
					init:	,
					normalize: vPattiSmaller,
					posn:			26 174,
					setHeading:	0,
					edgeHit:		0
				)
				(= ticks	123)
			)
			(
				(theMusic2
					number:			sfxCarDoorClose,
					setLoop:			1,
					play:
				)
				(ego
					setMotion		MoveTo 26 152 self,
				)
			)
			(
				(HandsOn)
				(ego
					normalize: vPattiSmaller,
					setLoop:			RELEASE,
					setHeading: 	0,
				)
				(self dispose:)
			)
		)
	)
)

(instance sEnterLimo of Script
	(method (changeState newState &tmp var1 var2)
		(switchto (= state newState)
			(
				(HandsOff)
				(ego
					setLoop:		RELEASE,
					setHeading: 180 self
				)
			)
			(
				(ego
					setMotion:		MoveTo 26 174 self
				)
			)
			(
				(theMusic2
					number:			sfxCarDoorOpen,
					setLoop:			1,
					play:
				)
				(= ticks 50)
			)
			(
				(ego hide:)
				(theMusic2
					number:			sfxCarDoorClose,
					setLoop:			1,
					play:
				)
				(= ticks 100)
			)

			(
				(theMusic
					fade:	0 15 12 TRUE
				)
				(curRoom newRoom 200)
			)
		)
	)
)

