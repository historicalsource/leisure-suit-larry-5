;**
;**	Logics for Room 250 -- Airport exterior
;**
;**	LSL5:	Passionate Patti Does A Little Undercover Work
;**				Copyright 1991
;**				by Al Lowe
;**
;**	Code thrown together by C. Escobar 
;**	with help from Steve Conrad
;**
;**	Last Update:	Tuesday, September 17, 1991 
;**


(script#		250)

(public
	rm250		0
)

(define	sfxAirport					251)
(define	sfxAnnouncement1			252)
(define	sfxAnnouncement2			253)
(define	sfxFlyBy						254)
(define	sfxCarPullsUp				255)
(define	sfxCarDoorOpen				191)
(define	sfxCarDoorClose			192)
(define  sfxSlidingDoor				293)

(define	vAirportExterior			250)
(define	vAirportExteriorDoor		251)

(define  atmMachine 					258)

(local
	cardHere
	cueCounter
)


(instance rm250 of LLRoom
	(properties
		picture		250
		north			270
	)

	(method (init)
		(HandsOff)
		(Load VIEW 250)
		(ego
			init:,
			normalize:	vLarrySmallest,
			setStep:		2 1,
		)
		(plane
			init,
			setLoop:		0,
			setCel:		(Random 4 6),
			setScript 	sPlane,
		)
		(door
			init,
			setPri:		4,
		)

		(if (or
				(Btst fLimoParked)
				(Btst fCalledLimo))
			(limo
				init:,
				posn:			277 115,
			)
		)

		(switch prevRoomNum
			(north
				(ego
					edgeHit:		 	0,
				)
				(Delay 5 CYCLES curRoom)
			)
			(atmMachine
				(ego
					posn:				 191 105,
					illegalBits:	$8000,
				)
				(Delay 5 CYCLES curRoom)
			)

			(else 							;200 or teleporting
				(ego
					posn: 1000 1000 0,
				)
				(if (not (Btst fLimoParked))
					(if (not (cast contains: limo))
						(limo init:)
					)
					(limo
						posn:				 400 115
					)
				)
				(curRoom setScript: sLimo)
			)
		)

		(super init:)

		(self addObstacle:
			((Polygon new:)
				type: PBarredAccess,
				init:	0 0 257 0 257 104 182 104 182 106 315 106 316 104 263 104
						263 0 319 0 319 189 0 189,
				yourself
			)
		)

		;init features/addToPics
		(otherDoor init:)
		(driveway	init:)
		(car 			init:)
		(car2 		init:)
		(luggage		init:)
		(luggage2	init:)
		(bush 		init:)
		(tower 		init:)
		(ATM
			init:,
			approachVerbs: verbDo verbUse
		)
		(trashCan
			init:,
			approachVerbs: verbDo
		)

		(if (and
				(Btst fCalledGreenCard)
				(== larryLoc Miami)
				(not (ego has: iGreenCard))
				(not (Btst fBeenInMiami)))
			(envelope
				init,
				approachVerbs: verbDo,
			)
			(= cardHere TRUE)
		)

		(addToPics doit:)

		(cloud
			init,
			setCel: 		8,
			posn:			(Random 90 230) (Random 1 20) -30,
			stopUpd:,
		)
		(theSign
			init:,
			setCel: 	larryLoc,
			setPri:	4,
		)
		(theMusic
			number:		sfxAirport,			;251
			loop:			-1,
			play:
		)
	);init

	(method (newRoom n)
		(if (!= n 258)				;don't stop music
			(theMusic  stop)
			(theMusic2 stop)
			(theMusic3 stop)
		else
			(theMusic2 fade)		;airplane flyBy
		)
		(super newRoom n)
	)

	(method (doVerb theVerb invItem &tmp [str 100])
		(switch theVerb
			(verbLook
				(if cardHere
					(TPrint "Look! There's a plain brown envelope lying on top of
						that trash receptacle near the ATM machine.")
				else
					(Format @str
						"You are outside the %s airport.
						AeroDork Airlines has installed one of their exclusive
						ATMs near the entrance."
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
					(TPrint @str)
				)
			)
			(else
				(super doVerb: theVerb invItem &rest)
			)
		)
	)

	(method (doit)
		(super doit:)
		(cond
			(script
				(script doit:)
			)
		)
	)
	(method (cue)
;		(super cue:)
		(HandsOn)
	)
)

(instance ATM of Feature
	(properties
		y 					1
		nsLeft 			176
		nsTop 			82
		nsBottom 		94
		nsRight 			190
 		description 	"the ATM"
		sightAngle 		40
		approachX		189
		approachY		102
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbLook
				(TPrint "AeroDork was the first airline to feature
					ATMs (\"Automated Ticket Machines\").
					These machines have allowed AeroDork to completely
					eliminate ticket agents."
				)
			)
			(verbDo
				(curRoom newRoom 258)
			)
			(verbTalk
				(TPrint "\"Hello!\" you cry, \"is this machine voice activated?\"")
				(TPrint "The ATM's voice activation circuits may be implemented
					\"Real Soon Now.\""
				)
			)
			(verbUse
				(switch invItem
					(iGoldCard
						(ego put: iGoldCard)
						(curRoom newRoom: 258)
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

(instance limo of Actor
	(properties
		view				vAirportExterior	;250
		xStep			 	3
		yStep			 	2
		loop				3
		cel				0
		description		"your limousine"
		illegalBits:	0
		approachX		288
		approachY		107
		priority			8
		signal			(| fixPriOn fixedLoop fixedCel ignrAct)
	)
	(method (cue)
		(super cue:)
		(switch (++ cueCounter)
			(1
				(Delay 3 REALSECONDS self)
			)
			(2
				(ego hide)
				(Delay 2 REALSECONDS self)
			)
			(3
				(theMusic3
					number:			sfxCarDoorClose,
					loop:				1,
					play:
				)
				(self cue:)
			)

			(4
				(Delay 2 REALSECONDS self)
			)

			(5
				(curRoom newRoom: 200)
			)
		)
	)
	(method (init)
		(self
			approachVerbs:  verbDo
		)
		(super init:)
	)
	(method (doVerb theVerb)
		(switch theVerb
			(verbLook
				(TPrint "A small,
					tasteful bumper sticker on the back of the limo reads,
					\"Honk if you can last more than thirty seconds.\"")
			)
			(verbDo
				(HandsOff)
				(theMusic3
					number:			sfxCarDoorOpen,
					loop:				1,
					play:
				)
				(self cue:)
			)
			(else
				(super doVerb: theVerb &rest)
			)
		)
	)
)

(instance tower of Feature
	(properties
		y 					1
		nsLeft 			256
		nsTop 			20
		nsBottom 		68
		nsRight 			295
 		description 	"the control tower"
		sightAngle 		40
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbLook
				(TPrint "This tower contains all the many new air traffic
					controllers recently hired to keep you totally safe
					during takeoffs and landings.")
			)
			(else
				(super doVerb: theVerb invItem &rest)
			)
		)
	)
)

(instance bush of Feature
	(properties
		y 					1
		nsLeft 			242
		nsTop 			130
		nsBottom 		171
		nsRight 			319
 		description 	"the bushes"
		sightAngle 		40
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbLook
				(TPrint "\"Is your name George?\" you joke to Mister Bush.")
			)
			(verbDo
				(TPrint "There's nothing hidden in the planter.")
			)
			(verbTalk
				(TPrint "Do you think talking to these plants will make
					them grow?")
			)
			(else
				(super doVerb: theVerb invItem &rest)
			)
		)
	)
)
(instance luggage of Feature
	(properties
		y 					1
		nsLeft 			118
		nsTop 			101
		nsBottom 		124
		nsRight 			149
 		description 	"the luggage"
		sightAngle 		40
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbLook
				(TPrint "That piece of luggage belongs to some other jet
					setter, Larry.")
			)
			(else
				(super doVerb: theVerb invItem &rest)
			)
		)
	)
)
(instance luggage2 of Feature
	(properties
		y 					1
		nsLeft 			55
		nsTop 			135
		nsBottom 		150
		nsRight 			82
 		description 	"the luggage"
		sightAngle 		40
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbLook
				(TPrint "That piece of luggage belongs to some other jet
					setter, Larry.")
			)
			(else
				(super doVerb: theVerb invItem &rest)
			)
		)
	)
)
(instance car of Feature
	(properties
		y 					1
		nsLeft 			95
		nsTop 			124
		nsBottom 		161
		nsRight 			160
 		description 	"the car"
		sightAngle 		40
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbLook
				(TPrint "This car has been parked here well over
					the three-minute time limit!")
			)
			(else
				(super doVerb: theVerb invItem &rest)
			)
		)
	)
)
(instance car2 of Feature
	(properties
		y 					1
		nsLeft 			61
		nsTop 			161
		nsBottom 		188
		nsRight 			147
 		description 	"the car"
		sightAngle 		40
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbLook
				(TPrint "This car has been parked here well over
					the 3 minute time limit!")
			)
			(else
				(super doVerb: theVerb invItem &rest)
			)
		)
	)
)
(instance driveway of Feature
	(properties
		y 					1
		nsLeft 			150
		nsTop 			105
		nsBottom 		129
		nsRight 			319
 		description 	"the driveway"
		sightAngle 		40
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbLook
				(TPrint "This is the part of the airport where planes
					are not supposed to drive!")
			)
			(else
				(super doVerb: theVerb invItem &rest)
			)
		)
	)
)

(instance otherDoor of Feature
	(properties
		y 					1
		nsLeft 			46
		nsTop 			67
		nsBottom 		131
		nsRight 			99
 		description 	"the door"
		sightAngle 		40
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbLook
				(TPrint "That door is for those travelers with enough wisdom and
					experience to not choose AeroDork Airlines.")
			)
			(else
				(super doVerb: theVerb invItem &rest)
			)
		)
	)
)

(instance theSign of View
	(properties
		view			vAirportExterior ;250
		loop			0
		x				224
		y				67
 		description "the sign"
	)
	(method (doVerb theVerb &tmp [str 40])
		(switch theVerb
			(verbLook
				(Format @str "The sign says you are presently in %s, Larry!"
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
				(TPrint @str)
			)
			(else
				(super doVerb: theVerb &rest)
			)
		)
	)
)

(instance envelope of Prop			;must be prop cause of cue method
	(properties
		view				vAirportExterior ;250
		loop				2
		x					206
		y					99
		priority	 		6
 		description 	"the plain brown envelope"
		approachX		205
		approachY		93
	)
	(method (cue)
		(TPrint "You conveniently \"forget\" to leave any money.
			You feel guilty... but only for a few moments.")
		(self dispose)
	)
	(method (doVerb theVerb)
		(switch theVerb
			(verbLook
				(TPrint "There's an envelope here.")
			)
			(verbDo
				(if (not (ego has: iGreenCard))
					(HandsOff)
					(= cardHere FALSE)
					(ego get: iGreenCard)
					(HandsOn)
					(Points ptsTookGreenCard)
					(TPrint "You take the envelope,
						open it and find your non-personalized,
						\"100% legal,\" surrogate green card inside.")
					(Delay 3 CYCLES self)
				)
			)
			(else
				(super doVerb: theVerb &rest)
			)
		)
	)
)

(instance plane of Actor
	(properties
		view			 vAirportExterior ;250
		loop			 0
		priority     3
 		description  "the airplane"
		signal		 (| fixedLoop fixPriOn ignrAct ignrHrz)
	)
	(method (doVerb theVerb)
		(switch theVerb
			(verbLook
				(TPrint "\"Da plane, boss, da plane!\"")
			)
			(verbDo
				(TPrint "You may think you can touch that airplane...
					but you're wrong!")
			)
			(else
				(super doVerb: theVerb &rest)
			)
		)
	)
)

(instance trashCan of Feature
	(properties
		x 					201
		y 					94
		z 					0
		heading 			0
		nsLeft 			196
		nsTop 			88
		nsBottom 		101
		nsRight 			206
 		description 	"the trash can"
		sightAngle 		90
		approachX		205
		approachY		93
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbLook
				(if cardHere
					(TPrint "There's a plain brown envelope resting
						inconspicuously on the top of the trash can.")
				else
					(TPrint "A small sign on the side of the trash receptacle
						reads, \"Hold it 'til you get to the can!\"")
				)
			)
			(verbDo
				(if cardHere
				 	(envelope doVerb: verbDo)
				else
					(TPrint "There's nothing in the trash can--in THIS
						Leisure Suit Larry game!")
				)
			)
			(else
				(super doVerb: theVerb invItem &rest)
			)
		)
	)
)

(instance cloud of View
	(properties
		view			 vAirportExterior ;250
		loop			 0
		priority     1
		description  "the cloud"
		signal		 (| fixedCel fixedLoop fixPriOn ignrAct ignrHrz)
	)
	(method (doVerb theVerb)
		(switch theVerb
			(verbLook
				(TPrint "A lonely cloud drifts by on V.F.R.")
			)
			(verbDo
				(TPrint "You may think you can touch the clouds...
					but you're wrong!")
			)
			(else
				(super doVerb: theVerb &rest)
			)
		)
	)
)

(instance door of Door
	(properties
		view				vAirportExteriorDoor 	;251
		loop				0
		x					260
		y					101
		description	 	"the door"
		sightAngle		90
		entranceTo 		270					; room # beyond the door
		locked 			FALSE
		openSnd 			sfxSlidingDoor
		closeSnd 		0
		approachX		261
		approachY		104
		moveToX			261
		moveToY			102					;- where to move to when going to door
		illegalBits:	0
		exitType			doorWalkEgo
		enterType		doorWalkEgo
		lookStr			"This door leads to the exclusive AeroDork gates."
	)
)

(instance theMusic3 of Sound)

(instance sPlane of Script
	(method (changeState newState &tmp var1 var2)
		(switchto (= state newState)
			(
				(= seconds (Random 6 11))
			)
			(
				(theMusic2
					number:				sfxFlyBy,
					loop:					1,
					flags:				1,
					play:					127,
				)
				(switch (Random 1 4)
					(1
						(plane
							show:,
							setCel:				4,
							posn:			 		347 31,
							setMotion:			MoveTo 120 55 self,
						)
					)
					(2
						(plane
							show:,
							setCel:				7,
							posn:			 		319 18,
							setMotion:			MoveTo 108 57 self,
						)
					)
					(3
						(plane
							show:,
							setCel:				5,
							posn:			 		319 25,
							setMotion:			MoveTo 124 66 self,
						)
					)
					(4
						(plane
							show:,
							setCel:				6,
							posn:			 		283 -3,
							setMotion:			MoveTo 73 48 self,
						)
					)
				)
			)
			(
				(plane hide:)
				(= ticks 30)
			)
			(
				(theMusic2
					fade:		  			0 10 10 STOP_MUSIC
				)
				(self	 init:)
			)
		)
	)
)


(instance sLimo of Script

	(define GET_OUT  5)

	(method (changeState newState &tmp var1 var2)
		(switch (= state newState)
			(0
            (HandsOff)
				(ego
					hide:,
					posn: 			287 110,
				)
				(= cycles 2)
			)
			(1
				(if (Btst fLimoParked)
					(self changeState GET_OUT)
				else
					(theMusic3
						number:			sfxCarPullsUp,
						loop:				1,
						play:				,
                  flags:         mNOPAUSE,         ;1
						hold:				10,
					)
					(limo
						setMotion:		MoveTo 340 115 self,
					)
				)
			)
			(2
				(limo
					xStep:			2,
					setMotion:		MoveTo 295 115 self,
				)
			)
			(3
				(limo
					xStep:			1,
					setMotion:		MoveTo 277 115 self,
				)
			)
			(4
				(theMusic3 release)
				(limo stopUpd)
            (= ticks 90)
			)

			(GET_OUT
				(theMusic3
					number:			sfxCarDoorOpen,
					loop:				1,
					play:
				)
				(= ticks 100)
			)

			(6
				(= ticks 10)
			)

			(7
				(ego
					show,
					setLoop:       3,
					moveSpeed:		15,
               cycleSpeed:    8,
					setMotion:		MoveTo 287 105 self,
				)
			)
			(8
				(theMusic3
					number:			sfxCarDoorClose,
					loop:				1,
					play:
				)
				(HandsOn)
				(ego
					normalize:		vLarrySmallest,
					setStep:			2 1,
				)
				(Bset fLimoParked)
				(self dispose:)
			)
		)
	)
)

