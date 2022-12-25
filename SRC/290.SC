;**
;**	Logics for Room 290 -- VIP lounge
;**
;**	LSL5:  Passionate Patti Does A Little Undercover Work
;**				Copyright 1991
;**				by Al Lowe
;**
;**	Code of minor importance by C. Escobar
;**
;**	Last Update:  September 9, 1991
;**


(script#		290)

(public
	rm290		0
)

(define	sAnnouncement			297)
(define	vLarryLounge			291)

	(define	lEnterLounge		5)

(define	sTrickChair			291)
(define	sBoing 				295)
(define	sCooCoo 				296)
(define	sSuckPass			292)
(define	sSlidingDoor		293)
(define	sCrowd				294)

(define	pVIPlounge						290)

(define 	vLarry290				291)
(define 	vVipRoom'sProps		290)
(define 	vLarryJetWay 		550)
	(define	lSingleCells			0)
		(define	cFoldingChair 	0)
	(define	lDoorToJetway		1)
	(define	lNowBoarding		2)

	(define	lSitsDown			0)			;**	endLoop at cycleSpeed 1
	(define	lCooCoo				1)			;**	forward for a while at 0
	(define	lStandingUp			2)			;**	endLoop at cycleSpeed 1
	(define	lInsertingPass		3)			;**	endLoop at cycleSpeed 1
	(define	lIntoJetway			4)			;**	repo to below x/y, endloop at 0
	(define	lOutOfJetway		5)

(define	atDoorY				127)
(define	disappearY			101)
(define	disappearX			150)

(define	pVIPloungeFull					295)

(local
	[str 50]
	[locStr 50]
)

(instance rm290 of LLRoom
	(properties
		picture		290
		north			310
		south       270
		lookStr		"All your life you assumed those snooty, private
						airport waiting rooms were filled with luxurious
						appointments."
	)

	(method (init)
		(Load	VIEW vLarry290)
		(Load	VIEW vLarry)
		(Load	VIEW vLarryLounge)
		(ego
			init:,
			normalize:
		)

		(door init:, stopUpd)
		(switch prevRoomNum
			(north
				(ego
					view:          vLarryLounge,
					setLoop:       lEnterLounge,
					setCycle:		0,
					posn:          152 93,
				)
				(door
					posn: 111 118
				)
				(SetupExit FALSE)
				(Bclr fOkToBoard)
				(self setScript sReturning)
			)
			(south
				(ego	posn: 159 225)
				(self	setScript sEnter270)
			)
			(else
				(HandsOn)
				(ego
					posn:			160 160,
					edgeHit:		0
				)
			)
		)

		(super init:)

		(cond
			((not prevRoomNum)				;debugging
				(Delay 90 SECONDS curRoom)
			)
			((Btst fOkToBoard)
				(boardingSign init:)
			)
			((and (ego has: iBoardingPass)
					(Btst fBeenToTown))
				(Delay 90 SECONDS curRoom)
			)
		)

		(self addObstacle:
			((Polygon new:)
				type: PBarredAccess,
				init:	91 126 115 126 111 137 96 145 74 144 67 150 63 167 109 189
						0 189 0 0 319 0 319 189 202 189 232 169 261 150 210 123
						102 123,
				yourself
			)
			((Polygon new:)
				type: PBarredAccess,
				init:	201 132 213 137 217 144 198 150 180 148 172 145 170 137,
				yourself
			)
		)

		(chair
			ignoreActors: 	TRUE,
			init:				,
			stopUpd:			,
			approachVerbs:	verbDo
		)
		(ABM init:)
		(pot
			init:,
			approachVerbs: verbDo
		)
		(cabinet 		init:)
		(light 			init:)
		(leftPainting 	init:)
		(rightPainting init:)
	);init

	(method (newRoom n)
		(theTimer dispose:, delete:)
		(super newRoom n)
	)

	(method (doVerb theVerb &tmp [tmpStr 150])
		(switch theVerb
			(verbLook
				(Format @tmpStr
					"All your life you assumed those snooty, private
					airport waiting rooms were filled with luxurious appointments.
					Once again, you were wrong!
					In fact, you'd swear that AeroDork installs that same folding
					chair in every terminal in every city they serve!"
					(switch larryLoc
						(LosAngeles
							{Los Angeles}
						)
						(NewYork
							{New York}
						)
						(AtlanticCity
							{Atlantic City}
						)
						(Miami
							{Miami}
						)
					)
				)
				(TPrint @tmpStr)
			)
			(else
				(super doVerb: theVerb &rest)
			)
		)
	)

	(method (cue)
		(if (self script?)								; the chair's running maybe
			(Delay 5 REALSECONDS self)
		else
			(self setScript: sDoAnnouncement)
		)
	)

	(method (doit)
		(super doit:)
		(cond
			(script)
			((StepOn ego cBLUE)
				(HandsOff)
				(curRoom setScript: sExit270)
			)
		)
	)
)

(instance chair of Prop
	(properties
		view			   vVipRoom'sProps
		loop			   0
		x				   190
		y				   144
		approachX		180
		approachY		143
 		description    "the chair"
		sightAngle     90
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbLook
				(TPrint "How thoughtful!  AeroDork has provided a
					comfortable metal folding chair for your
					sitting convenience!")
			)
			(verbDo
				(if (not (curRoom script?))
					(curRoom setScript: sChair)
				)
			)
			(else
				(super doVerb: theVerb invItem &rest)
			)
		)
	)
)

(instance door of Actor
	(properties
		view			   vVipRoom'sProps
		loop			   1
		x				   152
		y				   118
		illegalBits    0
		priority       3
 		description    "the jetway door"
      signal		   (| fixedLoop fixPriOn ignrHrz ignrAct)
		sightAngle     90
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbLook
			 	(TPrint "The door to the jetway is firmly locked,
					and without protrusions of any kind.")
			)
			(verbDo
				(TPrint "You can only open this door by using the
					ABM to the right.")
			)
			(else
				(super doVerb: theVerb invItem &rest)
			)
		)
	)
)

(instance boardingSign of Prop
	(properties
		view			   vVipRoom'sProps
		loop			   2
		x				   153
		y				   62
 		description    "the boarding sign"
		sightAngle     90
		priority			5
		signal			(| fixedLoop fixPriOn ignrAct)
	)
	(method (init)
		(super init:)
		(self
			cycleSpeed		8,
			setCycle:		Forward,
		)
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbLook
				(if (Btst fOkToBoard)
					(TPrint "The Boarding Pass sign is currently flashing.
			      	Are you going to miss the plane?")
				else
					(TPrint "It appears to be a sign.")
				)
			)
			(else
				(super doVerb: theVerb invItem &rest)
			)
		)
	)
)

(instance sExit270 of Script
	(method (changeState newState &tmp var1 var2)
		(switchto (= state newState)
			(
				(ego
					setMotion:		MoveTo (ego x?) 225 self,
				)
			)
			(
				(curRoom newRoom: (curRoom south?))  ;270
			)
		)
	)
)

(instance sEnter270 of Script
	(method (changeState newState &tmp var1 var2)
		(switchto (= state newState)
			(
				(HandsOff)
				(ego
					setMotion:		MoveTo 159 160 self,
				)
			)
			(
				(HandsOn)
				(ego normalize:)
				(self dispose:)
			)
		)
	)
)

(instance ABM of Feature
	(properties
		x 					185
		y 					98
		z 					0
		heading 			0
		nsLeft 			176
		nsTop 			92
		nsBottom 		105
		nsRight 			194
		sightAngle		90
 		description		"the Automatic Boarding Pass Machine"
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbUse
				(switch invItem
					(iGoldCard
						(TPrint "Your AeroDork Gold Card will only work
							in the ATM outside the airport.")
					)
					(iBoardingPass
						(if (Btst fOkToBoard)
							;**	walk to slot, show him insert pass,
							;**	swing door open and walk him up the jetway
							(ego put: iBoardingPass 0)
							(curRoom setScript sJetWay)
						else
							(TPrint "You're a little early.
								That flight isn't due to board for another few
								minutes.")
						)
					)
					(else
						(super doVerb: theVerb invItem &rest)
					)
				)
			)
			(verbLook
				(TPrint "Near the far door is one of AeroDork Airline's
					exclusive \"ABMs.\"")
				(TPrint "ABM is an acronym for \"Automatic Boarding Pass Machine.\"
					So far, AeroDork is the only airline to have them.")
				(TPrint "(You expect it to remain that way!)" LOW)
			)
			(else
				(super doVerb: theVerb invItem &rest)
			)
		)
	)
)

(instance pot of Feature
	(properties
		x 					91
		y 					211
		z 					100
		heading 			0
		nsLeft 			85
		nsTop 			107
		nsBottom 		116
		nsRight 			97
 		description 	"the coffee pot"
		sightAngle 		40
		approachX 		104
		approachY 		136
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbLook
				(TPrint 	"It's just another example of AeroDork's
					extravagant attitude towards its customers.
					Freshly brewed coffee just for you!")
			)
			(verbDo
				(TPrint "YUCK! The pot has boiled dry!  Nothing remains
					but the stench of burned coffee.")
			)
			(else
				(super doVerb: theVerb invItem &rest)
			)
		)
	)
)

(instance cabinet of Feature
	(properties
		x 					85
		y 					119
		z 					0
		heading 			0
		nsLeft 			68
		nsTop 			101
		nsBottom 		137
		nsRight 			102
 		description 	"the coffee cabinet"
		sightAngle 		40
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbLook
				(TPrint "The cabinet holds up the coffee maker.")
			)
			(verbDo
				(TPrint "There's nothing in the cabinet that you need.")
			)
			(else
				(super doVerb: theVerb invItem &rest)
			)
		)
	)
)

(instance light of Feature
	(properties
		x 					150
		y 					35
		z 					0
		heading 			0
		nsLeft 			140
		nsTop 			26
		nsBottom 		45
		nsRight 			161
 		description 	"the light fixture"
		sightAngle 		40
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbLook
				(TPrint "Wouldn't you think the local transportation
					authorities would worry about exposed wiring?")
			)
			(verbDo
				(TPrint "Right!  Of course you can reach the ceiling in here!")
			)
			(else
				(super doVerb: theVerb invItem &rest)
			)
		)
	)
)

(instance leftPainting of Feature
	(properties
		x 					76
		y 					81
		z 					0
		heading 			0
		nsLeft 			64
		nsTop 			67
		nsBottom 		96
		nsRight 			89
 		description 	"the lovely painting"
		lookStr 			"You've always admired paintings on velvet!"
		sightAngle 		40
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbDo
				(TPrint "There's nothing hidden behind the painting.")
			)
			(else
				(super doVerb: theVerb invItem &rest)
			)
		)
	)
)

(instance rightPainting of Feature
	(properties
		x 					229
		y 					94
		z 					0
		heading 			0
		nsLeft 			213
		nsTop 			84
		nsBottom 		105
		nsRight 			246
 		description 	"the beautiful painting"
		lookStr 			"The last time you saw art this nice, you
							were in Tijuana."
		sightAngle 		40
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbDo
				(TPrint "There's nothing hidden behind the painting.")
			)
			(else
				(super doVerb: theVerb invItem &rest)
			)
		)
	)
)

(instance sDoAnnouncement of Script
	(method (changeState newState)
		(switchto (= state newState)
			(
				(HandsOff)
				(Bset fOkToBoard)
				(boardingSign init:)
				(= ticks 180)
			)
			(
				(if (chair script?)
					(-- state)
				)
				(= ticks 60)
			)
			(
				(theMusic
					number:			sAnnouncement,
					loop:				1,
					play:
				)
				(= ticks 300)			 ;Need to wait long enough
			)
			(
				(TPrint "A voice comes over the public address system,
					\"AeroDork Airlines is proud to announce the departure
					of its Chartreuse Service to msdffft, wqpaffw,
					and paovnnq.\"")
				(TPrint "Hmm. That speaker is none too clear.")
				(= ticks 120)
			)
			(
				(RestoreIB)
				(self dispose:)
			)
		)
	)
);sDoAnnouncement


(instance sChair of Script
	(method (changeState newState &tmp var1 var2)
		(switchto (= state newState)
			(
				(HandsOff)
				(Load SOUND sTrickChair)	;291
				(Load SOUND sBoing)			;295
				(Load SOUND sCooCoo)			;296
				(Load VIEW 	vLarry290)
				(Load VIEW  vLarryLounge)
				(= cycles 1)
			)
			(
				(ego setHeading 180 self)
			)
			(
				(chair hide)
				(ego
					setPri:        14,
					view:          vLarry290,
					setLoop:       0,
					posn:          (+ (chair x?) 1) (chair y?),
					cycleSpeed:		6,
					setCel:			0,
					setCycle:      CycleTo 5 FORWARD self,
				)
			)
			(
				(theMusic2
					number:			sTrickChair,		;291
					loop:				1,
					play:
				)
				(ego
					setCycle:      CycleTo 9 FORWARD self,
				)
			)
			(
				(theMusic2
					number:			sBoing,
					play:
				)
				(ego setCycle: 	EndLoop self)
			)
			(
				(chair show)
				(theMusic2
					number:			sCooCoo,
					loop:				1,
					play:
				)
				(ego
					posn:				(+ (chair x?) 44) (+ (chair y?) 3),
					setLoop:       1,
					cycleSpeed:		4,
					setCycle:      Forward,
				)
				(= ticks 60)
			)
			(
				(ego
					setLoop:       2,
					cycleSpeed:    6,
					setCel:			0,
					setCycle:      EndLoop self,
				)
			)
			(
				(ego
					posn:				(- (ego x?) 12) (ego y?),
					normalize:,
				)
				(RestoreIB)
				(self dispose:)
			)
		)
	)
)


(instance sReturning of Script
	(method (changeState newState &tmp var1 var2)
		(switchto (= state newState)
			(
				(HandsOff)
				(= ticks 60)
			)
			(
            (ego
					setCel:			0,
					cycleSpeed:		12,
					moveSpeed:		5,
               setCycle:      EndLoop self,
;              setMotion:     MoveTo 151 98 self
            )
			)
			(
            (ego
               normalize:,
               posn:          152 123,
               loop:          facingDown,
               cycleSpeed:    7,
               moveSpeed:     7,
               setMotion:     MoveTo 152 131 self
            )
			)
			(
				(ego normalize:)
				(theMusic2
					number:				sSlidingDoor, 	;293
					loop:					-1,
					play:
				)
				(door
					setMotion:		MoveTo 152 118 self
				)
			)

			(
				(theMusic2 stop)
				(= cycles 2)
			)

			(
				(Format @str "Welcome to %s!"
					(switch larryLoc
						(LosAngeles
							{Los Angeles, cheater}
						)
						(NewYork
							{New York}
						)
						(AtlanticCity
							{Atlantic City}
						)
						(Miami
							{Miami}
						)
					)
				)
				(TPrint @str)
				(= ticks 60)
			)
			(
				(cond
					((Btst fBeenInNewYork)
						(Format @locStr {New York})
					)
					((Btst fBeenInMiami)
						(Format @locStr {Miami})
					)
					((Btst fBeenInAtlanticCity)
						(Format @locStr {Atlantic City})
					)
					(else
						(Format @locStr {Los Angeles})
					)
				)
				(Format @str "This airport looks just like the one in %s!"
					@locStr)
				(TPrint @str)
				(= ticks 60)
			)
			(
				(HandsOn)
				(self dispose:)
			)
		)
	)
)

(instance sJetWay of Script
	(method (changeState newState &tmp var1 var2)
		(switchto (= state newState)
			(
				(HandsOff)
				(ego setMotion:		PolyPath 179 124 self)
			)
			(
				(ego
					view:          	vLarry290,
					setLoop:       	3,					;facing north
					cycleSpeed:    	6,
					setCel:				0,
					setCycle:      	EndLoop self,
				)
			)
			(
				(= ticks 30)
			)
			(
				(Points ptsUsedBoardingPass pfUsedBoardingPass)

				(if (== larryLoc LosAngeles)
					(TPrint "Your boarding pass is sucked...")
					(TPrint "...into the slot of the ABM!")
				)
				(theMusic2
					number:				sSuckPass, ;292
					loop:					1,
					play:
				)
				(= ticks 30)
			)
			(
				(ego
					cycleSpeed:			8,
					setCycle:			BegLoop,
				)
				(theMusic2
					number:				sSlidingDoor, 	;293
					loop:					-1,
					play:
				)
				(door
					setMotion:			MoveTo 110 (door y?) self,
				)
			)
			(
				(theMusic2 stop)
				(ego
					normalize:,
					setMotion:			PolyPath 154 124 self
				)
			)
			(
				(ego
					view:          	vLarry290,
					setLoop:       	4,
					cycleSpeed:    	8,
					setCel:				0,
					posn:					152 94,
					setCycle:      	EndLoop self,
				)
			)
			(
				(= quarters 0)
				(curRoom newRoom: 310)
			)
		)
	)
)

