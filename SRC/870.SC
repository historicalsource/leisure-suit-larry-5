;**
;**	Logics for Room 870 -- K-RAP Radio studios
;**
;**	LSL5:  Passionate Patti Does A Little Undercover Work
;**				Copyright 1991
;**				by Al Lowe
;**
;**	Programmed by John Hartin
;**
;**	Last Update:  September 10, 1991
;**


(script#		870)

(public
	rm870		0
)


(define	pK-RAPRadioStudios			870)
(define	vK-RAPRadioStudios		870)
	(define	lSingleCells		0)
		(define	cShowerW/OPatti 0)
		(define	cShowerSide		1)
		(define	cClothesOnHook	2)
		(define	cClothesRack	3)
	(define	lShowerDoor			1)
	(define	lGroupsTalking1		2)
	(define	lGroupsTalking2		3)
	(define	lGroupsTalking3		4)
	(define	lElevatorComingDown	5)
(define	vPattiInStudios			871)
	(define	lRobing				0)
(define	vPattiHammerpants			872)
(define vPattiNaked					873)
(define	pK-RAPRadioControlRoom		880)

(define	sElevator	853)
(define	mKRAPRap1	800)
(define	mKRAPRap2	801)
(define	mKRAPRap3	802)

(instance rm870 of LLRoom
	(properties
		picture		pK-RAPRadioStudios
		east			pK-RAPRadioControlRoom
		lookStr 		"The studio floor of the K-RAP building is far underground,
						filled with soundproof studios and playback speakers.
						A large fountain, complete with giant bronze sculptures,
						fills the center of the room."
	)

	(method (init)
		(LoadMany VIEW
			vK-RAPRadioStudios
			vPattiInStudios
			vPattiHammerpants
			vPattiNaked
		)
		(LoadMany SOUND
			sElevator
			mKRAPRap1
			mKRAPRap2
			mKRAPRap3
		)
		(switch prevRoomNum
			(east
				(HandsOn)
				(= style SCROLLRIGHT)
				(ego
					init:			,
					actions: ActionsKRAP,
					edgeHit:	WEST,
					normalize:	vPattiHammerpants
				)
				(elevator
					init:,
					setPri: -1,
					addToPic:
				)
				(eDoor
					init:,
					setPri: -1,
					addToPic:
				)
				(elevatorSide
					init:,
					setPri: -1,
					addToPic:
				)
				(addToPics doit:)
				(door init:, approachVerbs: verbDo)
				(keypad init:, approachVerbs: verbDo verbLook)
				(studioARoom init:)
				(studioBRoom init:)
				(studioCRoom init:)
				(elevatorKeypad init:, approachVerbs: verbDo verbLook)
			)
			(else
				(HandsOn)
				(SetFFRoom 0)
				(ego
					init:,
					normalize:	vPattiNaked,
					hide:
				)
				(door init:)
				(keypad init:)
				(studioARoom init:)
				(studioBRoom init:)
				(studioCRoom init:)
				(elevatorKeypad init:)
				(self setScript: goingDownScript)
			)
		)
		(statue	init:)
		(dude1 init:, setCycle: RandCycle)
		(dude2 init:, setCycle: RandCycle)
		(dude3 init:, setCycle: RandCycle)
		(super init:)
		(clothesRack init:)
		(addToPics doit:)
		(self addObstacle:
			((Polygon new:)
				type: PBarredAccess,
				init:	0 0 319 0 319 100 293 100 256 63 201 63 173 100 124 101
						104 134 92 133 76 134 64 134 54 146 90 146 73 168 44 179
						24 174 11 187 135 187 151 176 186 162 235 153 319 153 319 189
						0 189,
				yourself
			)
			((Polygon new:)
				type: PBarredAccess,
				init:	197 116 204 110 228 110 234 114 225 122 204 122,
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

	(method (notify codeNumber)
		(TPrint "You hear no sound from the door.
			That must not have been the correct number.")
		(HandsOn)
	)
)

(instance clothesRack of PicView
	(properties
		view			vK-RAPRadioStudios
		loop			lSingleCells
		cel			cClothesRack
		x				68
		y				158
 		description "the clothes rack"
		lookStr		"What a break! A clothing rack stands right beside your
						elevator."
		signal		ignrAct
	)
)

(instance keypad of Feature
	(properties
		x 231
		y 35
		z 0
		heading 0
		nsLeft 227
		nsTop 29
		nsBottom 41
		nsRight 235
 		description "the keypad"
		lookStr		"This keypad is similar to those upstairs.
						Unfortunately, you don't know the combination to this one."
		sightAngle 40
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbDo
				(if (!= (ego view?) vPattiNaked)
					(UseKeypad rm870)
				)
			)
			(else
				(super doVerb: theVerb invItem &rest)
			)
		)
	)
)

(instance studioARoom of Feature
	(properties
		x 				180
		y 				46
		nsLeft 		171
		nsTop 		28
		nsBottom 	64
		nsRight 		190
 		description "Studio A"
		sightAngle 	40
	)
	(method (doVerb)
		(dude1 doVerb: &rest)
	)
)

(instance door of Feature
	(properties
		x 				245
		y 				42
		nsLeft 		235
		nsTop 		24
		nsBottom 	60
		nsRight 		255
 		description "the studio door"
		lookStr		"This door leads to K-RAP's Studio B."
		sightAngle 	40
	)
)

(instance studioBRoom of Feature
	(properties
		x 				203
		y 				37
		nsLeft 		198
		nsTop 		24
		nsBottom 	51
		nsRight 		208
 		description "Studio B"
		lookStr		"This is K-RAP's Studio B." 
		sightAngle 	40
	)
)

(instance studioCRoom of Feature
	(properties
		x 				278
		y 				42
		nsLeft 		268
		nsTop 		23
		nsBottom 	62
		nsRight 		288
 		description "Studio C"
		lookStr 		"This is K-RAP's Studio C."
		sightAngle 	40
	)
)

(instance statue of Feature
	(properties
		x 217
		y 1
		z 0
		heading 0
		nsLeft 116
		nsTop 20
		nsBottom 189
		nsRight 319
 		description "the statue"
		lookStr "How impressive! K-RAP has a fountain filled with a
			huge statue of the King of Rock 'n' Roll--Barry Manilow!"
		sightAngle 40
		onMeCheck	cYELLOW
	)
)

(instance elevatorKeypad of Feature
	(properties
		x 9
		y 157
		z 0
		approachX	20
		approachY	180
		heading 0
		nsLeft 4
		nsTop 138
		nsBottom 157
		nsRight 15
 		description "the elevator keypad"
		lookStr		"This keypad is similar to those upstairs.
						Unfortunately, you don't know the combination to this one."
		sightAngle 40
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbDo
				(TPrint	"Since you have no way of knowing John Krapper's
					personal elevator's access code,
					it appears you are trapped down here forever!")
			)
			(else
				(super doVerb: theVerb invItem &rest)
			)
		)
	)
)

(instance clothes of View
	(properties
		view			vK-RAPRadioStudios
		loop			lSingleCells
		cel			cClothesOnHook
		x				68
		y				179
		z 				20
		approachX	56
		approachY	162
 		description "the clothes"
		lookStr		"A set of brightly-colored, over-sized rapper's clothes
						hangs on the clothes rack, conveniently placed near the
						elevator."
		sightAngle 	40
		signal		ignrAct
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbDo
				(if (== (ego view?) vPattiNaked)
					(ego setScript: getClothesScript)
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

(instance dude1 of Prop
	(properties
		view			vK-RAPRadioStudios
		loop			lGroupsTalking1
		x				142
		y				71
		cycleSpeed: 12
 		description "that \"2 Live 2 Screw\" dude"
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbLook
				(TPrint "Inside Studio A, the members of \"2 Live 2 Screw\"
					are carrying on a lively discussion.
					If only you could hear their conversation.")
			)
			(verbDo
				(TPrint "You are unable to hear those dudes through the
					soundproof glass.")
			)
			(verbZipper
				(TPrint "Patti! You have no interest whatsoever in these guys!")
			)
			(verbUse
				(TPrint "They can't hear your offer, and wouldn't be
					willing to deal with you anyway!")
			)
			(else
				(TPrint "If only you could find some way to overhear their
					conversation.")
			)
		)
	)
)
(instance dude2 of Prop
	(properties
		view			vK-RAPRadioStudios
		loop			lGroupsTalking2
		x				134
		y				68
		cycleSpeed: 10
 		description "that \"2 Live 2 Screw\" dude"
	)
	(method (doVerb theVerb invItem)
		(dude1 doVerb: theVerb invItem)
	)
)
(instance dude3 of Prop
	(properties
		view			vK-RAPRadioStudios
		loop			lGroupsTalking3
		x				150
		y				63
		cycleSpeed: 8
 		description "that \"2 Live 2 Screw\" dude"
	)
	(method (doVerb theVerb invItem)
		(dude1 doVerb: theVerb invItem)
	)
)

(instance elevator of View
	(properties
		view			vK-RAPRadioStudios
		loop			lSingleCells
		cel			cShowerW/OPatti
		x				44
		y				171
		priority		8
		signal		ignrAct
 		description "the showervator"
	)
	(method (doVerb theVerb invItem)		;**	only hits after she's dressed
		(switch theVerb
			(verbLook
				(TPrint "You are far too familiar with the \"showervator\"
					already!
					You hope that somehow, some way, you'll be able to
					find your way back up to Krapper's office and
					to your own clothes!")
			)
			(verbDo
				(TPrint "From down here, Mr. Krapper's private elevator
					requires his personal access number through the keypad
					to the left.")
			)
			(verbZipper
				(TPrint "While you might want to undress and re-enter the
					shower, you can't, as the elevator door is now firmly locked.")
			)
			(verbUse
				(TPrint "That doesn't seem to unlock the shower door.")
			)
			(else
				(TPrint "The showervator is off-limits to you at the moment.")
			)
		)
	)
)
(instance eDoor of Prop					 ;**	elevator door
	(properties
		view			vK-RAPRadioStudios
		loop			lShowerDoor
		cel			0
		x				44
		y				171
		priority		15
		signal		ignrAct
 		description "the showervator"
	)
	(method (doVerb theVerb invItem)
		(elevator doVerb theVerb invItem)
	)
)
(instance elevatorSide of View
	(properties
		view			vK-RAPRadioStudios
		loop			lSingleCells
		cel			cShowerSide
		x				44
		y				171
		priority		15
		signal		ignrAct
 		description "the showervator"
	)
	(method (doVerb theVerb invItem)
		(elevator doVerb theVerb invItem)
	)
)

(instance elevatorWithPatti of Actor
	(properties
		view			vK-RAPRadioStudios
		loop			lElevatorComingDown
		cel			0
		x				44
		y				5
		signal		ignrAct
		description "your naked body"
		lookStr		"Down you come!"
	)
)

(instance goingDownScript of Script
	(method (changeState newState)
		(switchto (= state newState)
			(
				(HandsOff)
				(theMusic setVol: lastVolume)
				(clothes init:, setPri: pLRED)	;can only happen after elevator
				(elevatorWithPatti
					init:			,
					setLoop:		lElevatorComingDown,
					setCel: 		0,
					moveSpeed: 	3,
					setMotion: 	MoveTo 44 171 self
				)
			)
			(
				(theMusic2
					release:
				)
				(= ticks 30)
			)
			(
				(elevatorWithPatti dispose:)
				(elevator
					init:			,
					setPri: 		pLRED,
					stopUpd:	,
				)
				(eDoor
					init:			,
					setPri: 		-1,
					stopUpd:,
				)
				(elevatorSide
					init:			,
					setPri: 		-1,
					stopUpd:
				)
				(ego
					show:			,
					x:				37,
					y: 			183,
					z:				20,
					setLoop:			4,
					setCel: 			0,
					setPri: 		pLRED,
					;setHeading:	90,
					actions: 	ActionsKRAP,
				)
				(HandsOn)
				(theIconBar disable:
					walkIcon
				)
				(self dispose:)
			)
		)
	)
)

(instance getClothesScript of Script
	(method (changeState newState)
		(switchto (= state newState)
			(
				(HandsOff)
				(eDoor
					startUpd:,
					setCel: 0
				)
				(= seconds 3)
			)
			(
				(TPrint "Checking as much of the downstairs as you can see,
					you make sure that no one is coming.
					Cautiously leaning out of the showervator,
					you grab the set of clothes conveniently hanging on
					the nearby clothes rack.")
				(= seconds 3)
			)
			(
				(eDoor
					setCycle: EndLoop self,
				)
			)
			(
				(ego
					x:				43,
					y: 			166,
					z:				0,
					setLoop:			RELEASE,
					setCel: 			RELEASE,
					setMotion: MoveTo 56 162 self
				)
			)
			(
				(Points ptsWoreHammerpants pfWoreHammerpants)
				(clothes hide:)
				(ego
					view: 		871,
					setLoop: 	0,
					setCel:		0,
					x: 			69,
					y: 			160,
					cycleSpeed: 12,
					setCycle: 	EndLoop self
				)
			)
			(
				(ego
					normalize:	vPattiHammerpants,
					x: 			56,
					y: 			162,
					loop:			facingEast,
					setMotion: 	MoveTo 78 166 self
				)
			)
			(
				(TPrint "Wow! Look at you! It's the all-new,
					all-what's-happenin' \"Rappin' Patti P!!\"")
				(TPrint "With that outfit and your impromptu copier make-up job,
					you're all set to blend right in down here!")
				(eDoor
					setCycle:	BegLoop self,
				)
			)
			(
				(eDoor
					setPri: 		-1,
					stopUpd:	,
				)
				(theIconBar enable:	walkIcon)
				(door approachVerbs: verbDo)
				(keypad approachVerbs: verbDo verbLook)
				(elevatorKeypad approachVerbs: verbDo verbLook)
				(HandsOn)
				(self dispose:)
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
			(verbDo
				(if (== (ego view?) vPattiNaked)
					(TPrint "You briefly consider doing what you often do when
						you're in the shower,
						but soon decide this is neither the time nor the place.")
					(return TRUE)
  				else
					(TPrint "You attempt to hike up those Hammerpants you're wearing,
						but it does no good!")
					(return TRUE)
				)
			)
			(verbZipper
				(if (== (ego view?) vPattiNaked)
					(TPrint "You briefly consider doing what you often do when
						you're in the shower,
						but soon decide this is neither the time nor the place.")
					(return TRUE)
  				else
					(TPrint "Maintaining your cool,
						you surreptitiously check the zipper on your giant pants.
						It's fine.")
					(return TRUE)
				)
			)
			(else
				(return FALSE)
			)
		)
	)
)

