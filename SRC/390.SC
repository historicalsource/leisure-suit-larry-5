;**
;**	Logics for Room 390 -- Flight Stimulator
;**
;**	LSL5:  Passionate Patti Does A Little Undercover Work
;**				Copyright 1991
;**				by Al Lowe
;**
;**	Art Implementer: C. Escobar
;**	Code by: Everybody and his Brother
;**
;**	Last Update:  September 10, 1991
;**


(script#		390)

(public
	rm390		0
)


(define	sfxPlaneDive		386)
(define	sfxScreams			387)
(define	mFalling				390)
(define	sfxPullOut			392)
(define	sfxGaugeFX1			393)
(define	sfxGaugeFX2			394)
(define	sfxGaugeFX3			395)

(enum
	dummy
	roomScript0
	kettle
	kettle1
	waitingForPlayer
	reallyWaiting
	headlong
	headlong2
	rise2leave
	rise2leave2
	lastState
)

(local
	cloudX
	cloud2X
	clickCounter
	outOfTime
	larryStop
)


(instance rm390 of LLRoom
	(properties
		picture		390
	)
	(method (init)
		(super init:)
		(LoadMany FONT niceFont giantFont)
		(larry
			init:,
			setCycle:	EndLoop larry
		)
		(larryHead
			init:			,
			setCycle:	Forward
		)
		(plane
			init:,
			setScript:	sPlane
		)

		(panel1 init:, setCycle:	Forward)
		(panel2 init:, setCycle:	Forward)
		(panel3 init:, setCycle:	Forward)
		(panel4 init:, setCycle:	Forward)
		(panel5 init:, setCycle:	Forward)
		(panel6 init:, setCycle:	Forward)
		(panel7 init:, setCycle:	Forward)
		(panel8 init:, setCycle:	Forward)

		(cloud
			init,
			setScript: sCloud
		)
		(cloud2
			init:,
			setScript: sCloud2
		)
		(frontCloud
			init,
			setScript: sFrontCloud
		)
		(frontCloud2
			init,
			setScript: sFrontCloud2
		)
		(wheel  init, setCycle: Forward)
		(wheel2 init, setCycle: Forward)
		(windows  init:)
		(console  init:)
		(throttle init:)
		(bigLarry init:)
		(thePlane init:)

		(self setScript: sPlaneCartoon)
		(SetFFRoom		310)
	);init

	(method (newRoom n)
		(theMusic  stop:)
		(theMusic2 stop:)
		(super newRoom: n)
	)

	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbLook
				(TPrint "No matter where you look, nothing looks familiar!
					Whatever made you think those hours you killed playing
					\"Red Baron\" when you should have been working could possibly
					prepare you to handle a modern passenger jet?")
			)
			(verbTalk
				(TPrint "\"AAAAEEEEEEEE!\" you scream."
					#font:	giantFont
					#title:	{You}
				)
			)
			(verbUse
				(TPrint "Oh, right!
					Like that's gonna help you land this thing!")
			)
			(verbZipper
				(TPrint "You're too late.
					You drained your bladder shortly after entering the cockpit!")
			)
			(verbDo
				(switch (++ clickCounter)
					(1
						(TPrint "You turn on the radio.")
						(TPrint "(It's tuned to a nice, soft-rock, easy-listening,
							MOR, album station.)" LOW)
					)
					(2
						(TPrint "The wing surface deicers go to work,
							quickly removing any trace of ice from the hot
							summer sun-baked flight surfaces.")
					)
					(3
						(TPrint "Good idea!
							Change to the auxiliary fuel tanks!!")
					)
					(4
						(TPrint "You change back to the main fuel tanks!")
					)
					(5
						(TPrint "You flip the switch that signals the stewardess
							to bring you a fresh cocktail!")
					)
					(else
						(if (== (sPlaneCartoon state?) reallyWaiting)
							(curRoom setScript: sAutoPilot)
						else
							(TPrint "How you wish you had acted earlier!")
						)
					)
				)
			)
			(else
				(super doVerb: theVerb invItem &rest)
			)
		)
	)
)

(instance sPlane of Script
	(method (changeState newState)
		(switchto (= state newState)
			(
				(plane
					setLoop:		1,
					cycleSpeed:	1,
					setCycle:	Forward,
				)
				(= cycles (Random 20 30))
			)
			(
				(plane
					setLoop:		2,
					setCel:		0,
					cycleSpeed:	0,
					setCycle:	EndLoop self,
				)
			)
			(
				(plane
					setLoop:		1,
					cycleSpeed:	1,
					setCycle:	Forward,
				)
				(= cycles (Random 20 30))
			)
			(
				(plane
					setLoop:		3,
					setCel:		0,
					cycleSpeed:	0,
					setCycle:	EndLoop self,
				)
			)
			(
				(self init)
			)
		)
	)
)

(instance panel1 of Prop
	(properties
		view			390
		loop			0
		cel			0
		x				14
		y				18
		detailLevel	1
		priority		4
		signal		(| fixedLoop fixPriOn ignrAct)
		description	"the panel"
	)
	(method (doVerb theVerb invItem)
		(curRoom doVerb: theVerb invItem &rest)
	)
)
(instance panel2 of Prop
	(properties
		view			390
		loop			1
		cel			2
		x				147
		y				18
		detailLevel	1
		priority		4
		signal		(| fixedLoop fixPriOn ignrAct)
		description	"the panel"
	)
	(method (doVerb theVerb invItem)
		(curRoom doVerb: theVerb invItem &rest)
	)
)
(instance panel3 of Prop
	(properties
		view			390
		loop			2
		cel			1
		x				98
		y				18
		detailLevel	2
		priority		4
		signal		(| fixedLoop fixPriOn ignrAct)
		description	"the panel"
	)
	(method (doVerb theVerb invItem)
		(curRoom doVerb: theVerb invItem &rest)
	)
)
(instance panel4 of Prop
	(properties
		view			390
		loop			3
		cel			4
		x				76
		y				92
		detailLevel	2
		priority		4
		signal		(| fixedLoop fixPriOn ignrAct)
		description	"the panel"
	)
	(method (doVerb theVerb invItem)
		(curRoom doVerb: theVerb invItem &rest)
	)
)
(instance panel5 of Prop
	(properties
		view			390
		loop			4
		cel			2
		x				192
		y				95
		detailLevel	3
		priority		4
		signal		(| fixedLoop fixPriOn ignrAct)
		description	"the panel"
	)
	(method (doVerb theVerb invItem)
		(curRoom doVerb: theVerb invItem &rest)
	)
)
(instance panel6 of Prop
	(properties
		view			390
		loop			5
		cel			5
		x				192
		y				18
		detailLevel	3
		priority		4
		signal		(| fixedLoop fixPriOn ignrAct)
		description	"the panel"
	)
	(method (doVerb theVerb invItem)
		(curRoom doVerb: theVerb invItem &rest)
	)
)
(instance panel7 of Prop
	(properties
		view			390
		loop			6
		cel			2
		x				283
		y				13
		detailLevel	4
		priority		4
		signal		(| fixedLoop fixPriOn ignrAct)
		description	"the panel"
	)
	(method (doVerb theVerb invItem)
		(curRoom doVerb: theVerb invItem &rest)
	)
)
(instance panel8 of Prop
	(properties
		view			390
		loop			7
		cel			1
		x				280
		y				83
		detailLevel	4
		priority		4
		signal		(| fixedLoop fixPriOn ignrAct)
		description	"the panel"
	)
	(method (doVerb theVerb invItem)
		(curRoom doVerb: theVerb invItem &rest)
	)
)
(instance wheel of Prop
	(properties
		view			390
		loop			8
		priority		6
		cycleSpeed  1
		x				92
		y				66
		signal		(| fixedLoop fixPriOn ignrAct)
		description	"the yoke"
	)
	(method (doVerb theVerb invItem)
		(curRoom doVerb: theVerb invItem &rest)
	)
)
(instance wheel2 of Prop
	(properties
		view			390
		loop			8
		priority	   6
		cycleSpeed  1
		x				223
		y				65
		signal		(| fixedLoop fixPriOn ignrAct)
		description	"the yoke"
	)
	(method (doVerb theVerb invItem)
		(curRoom doVerb: theVerb invItem &rest)
	)
)

(instance cloud of Actor
	(properties
		view			392
		loop			0
		priority		2
		signal		(| fixedLoop fixPriOn ignrAct)
		description	"the cloud"
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbLook
				(TPrint "Your jetliner plummets through those clouds like
					a hot knife through a buttery-flavored, artifical, non-dairy,
					lard-like, food product substance.")
			)
			(verbDo
				(TPrint "As much as you'd like to grab onto the clouds,
					you just can't do it from inside the cockpit.")
				(TPrint "(However, you soon may be sitting on one, strumming
					your harp!)" LOW)
			)
			(else
				(super doVerb: theVerb invItem &rest)
			)
		)
	)
)
(instance cloud2 of Actor
	(properties
		view			392
		loop			0
		priority		2
		signal		(| fixedLoop fixPriOn ignrAct)
		description	"the cloud"
	)
	(method (doVerb theVerb invItem)
		(cloud doVerb: theVerb invItem &rest)
	)
)
(instance frontCloud of Actor
	(properties
		view			392
		loop			0
		cel 			3
		priority		2
		xStep			25
		yStep			25
		signal		(| fixedCel fixedLoop fixPriOn ignrAct)
		description	"the cloud"
	)
	(method (doVerb theVerb invItem)
		(cloud doVerb: theVerb invItem &rest)
	)
)
(instance frontCloud2 of Actor
	(properties
		view			392
		loop			0
		cel 			3
		priority		2
		xStep			25
		yStep			25
		signal		(| fixedCel fixedLoop fixPriOn ignrAct)
		description	"the cloud"
	)
	(method (doVerb theVerb invItem)
		(cloud doVerb: theVerb invItem &rest)
	)
)

(instance sFrontCloud of Script
	(method (changeState newState)
		(switchto (= state newState)
			(
				(frontCloud hide)
				(= cycles (Random 2 4))
			)
			(
				(frontCloud
					show,
					setCel:					(Random 0 3),
					setStep 					(+ (frontCloud xStep?) 2) (+ (frontCloud yStep?) 2),
				)
				(switch (Random 1 4)
					(1
						(frontCloud
						  	posn: 			144 19,
							setMotion		MoveTo 0 67 self
						)
					)
					(2
						(frontCloud
						  	posn: 			141 44,
							setMotion		MoveTo 0 11 self
						)
					)
					(3
						(frontCloud
						  	posn: 			144 19,
							setMotion		MoveTo -24 40 self
						)
					)
					(4
						(frontCloud
						  	posn: 			141 44,
							setMotion		MoveTo -24 40 self
						)
					)
				)
			)
			(
				(self init:)
			)
		)
	)
)
(instance sFrontCloud2 of Script
	(method (changeState newState)
		(switchto (= state newState)
			(
				(frontCloud2 hide)
				(= cycles (Random 2 4))
			)
			(
				(frontCloud2
					show,
					setCel:					(Random 0 3),
					setStep 					(+ (frontCloud2 xStep?) 2) (+ (frontCloud2 yStep?) 2),
				)
				(switch (Random 1 4)
					(1
						(frontCloud2
						  	posn: 			177 20,
							setMotion		MoveTo 319 67 self
						)
					)
					(2
						(frontCloud2
						  	posn: 			177 20,
							setMotion		MoveTo 334 42 self
						)
					)
					(3
						(frontCloud2
						  	posn: 			182 43,
							setMotion		MoveTo 319 14 self
						)
					)
					(4
						(frontCloud2
						  	posn: 			182 43,
							setMotion		MoveTo 334 42 self
						)
					)
				)
			)
			(
				(self init:)
			)
		)
	)
)
(instance sCloud of Script
	(method (changeState newState)
		(switchto (= state newState)
			(
				(= cloudX (Random 138 227))
				(cloud
					setCel:			(Random 0 3),
					posn:				cloudX (Random 190 200),
					setStep:			10 (Random 10 15),
					setMotion		MoveTo  cloudX (Random 85 95) self,
				)
			)
			(
				(self init:)
			)
		)
	)
)
(instance sCloud2 of Script
	(method (changeState newState)
		(switchto (= state newState)
			(
				(= cloud2X (Random 249 340))
				(cloud2
					setCel:			(Random 0 3),
					posn:				cloud2X (Random 190 200),
					setStep:			10 (Random 10 15),
					setMotion		MoveTo  cloud2X (Random 85 95) self,
				)
			)
			(
				(self init:)
			)
		)
	)
)

(instance theMusic3 of Sound)

(instance console of Feature
	(properties
		x 					162
		y 					55
		z 					0
		heading 			0
		nsLeft 			70
		nsTop 			35
		nsBottom 		75
		nsRight 			255
 		description 	"the console"
		sightAngle 		40
	)
	(method (doVerb theVerb invItem)
		(curRoom doVerb: theVerb invItem &rest)
	)
)
(instance throttle of Feature
	(properties
		x 					158
		y 					86
		z 					0
		heading 			0
		nsLeft 			131
		nsTop 			76
		nsBottom 		96
		nsRight 			186
 		description 	"the throttle"
		sightAngle 		40
	)
	(method (doVerb theVerb invItem)
		(curRoom doVerb: theVerb invItem &rest)
	)
)
(instance windows of Feature
	(properties
		x 					157
		y 					34
		z 					0
		heading 			0
		nsLeft 			3
		nsTop 			0
		nsBottom 		69
		nsRight 			312
 		description 	"the window"
		sightAngle 		40
		onControl		cBLUE
	)
	(method (doVerb theVerb invItem)
		(curRoom doVerb: theVerb invItem &rest)
	)
)

(instance sPlaneCartoon of Script
	(method (changeState newState)
		(switch (= state newState)
			(dummy
				(= cycles 2)
			)
			(roomScript0
				(HandsOn)
				(User canControl: FALSE)
				(theIconBar disable: walkIcon)
				(= seconds 4)
			)
			(kettle
				(HandsOff)
				(TPrint "Oh, Larry!
					Here's another fine mess you've gotten yourself into!"
					#dispose: self
				)
			)
			(kettle1
				(RestoreIB)
				(= ticks 30)
			)
			(waitingForPlayer
				(HandsOff)
				(TPrint "Whatever possessed you to volunteer to land a major
					aircraft? This is far different from your futile attempt
					a few years ago when you tried to sell flight simulator
					software for a major computer game company!"
					#dispose: self
				)
			)

			(reallyWaiting
				(RestoreIB)
				(= seconds 30)
			)

			;**	if we get here, it means the timer elapsed, he didn't
			;**	try to save himself, and we'll take over.  No points.

			(headlong
				(HandsOff)
				(TPrint "As the ground rushes headlong toward you,
					you decide to make a run for it!
					Maybe there are some parachutes tucked away under the
					stewardesses' seats.
					Maybe the other passengers won't notice you heading
					for the exit with a parachute tucked under your leisure suit.
					Maybe you'll be able to open the door.
					Maybe you'll be able to fly!"
					#dispose: self
				)
			)
			(headlong2
				(= ticks 30)
			)
			(rise2leave
				(TPrint "As you rise to leave,
					you struggle against the severe G-forces pummelling your body,
					inadvertently catching the cuff of your leisure suit
					on an inconspicuous switch labeled\n
					\"Auto Pilot.\""
					#dispose: self
				)
			)
			(rise2leave2
				(= ticks 60)
			)
			(lastState
				(= outOfTime TRUE)
				(self setScript: sAutoPilot)
			)
		)
	)
);sPlaneCartoon

(instance sAutoPilot	of Script
	(method (changeState newState)
		(switchto (= state newState)

			;**	he clicked around on the dashboard.
			;**	Al's easy; that's good enough for a dumbshit fake arcade game
			(
				(if outOfTime		;Don't print this because we already said it
					(self cue:)
				else
					(HandsOff)
					(TPrint "Grasping at anything and everything on the dashboard,
						you brilliantly (and totally accidentally)
						hit something called an \"Auto Pilot.\""
						#dispose: self
					)
				)
			)
			(
				(= ticks 60)
			)
			(
				(if (not outOfTime)
					(Points ptsSavedPlane)
				)
				;straighten-out plane
				(plane
					setLoop:		1,
					setCel: 		0,
					setScript:	0
				)
				(wheel setCycle: BegLoop)
				(wheel2 setCycle: BegLoop)

				(sCloud dispose:)					;stop clouds
				(sCloud2	dispose:)
				(sFrontCloud dispose:)
				(sFrontCloud2 dispose:)
				(= cycles 8)
			)
			(
				(frontCloud
					show				,
					setCel:			1,
					posn: 			56 39
				)
				(frontCloud2
					show				,
					setCel:			2,
					posn: 			249 44
				)

				(= larryStop TRUE)					;Larry's head quits moving
				(larryHead setCycle: BegLoop)

				(TPrint "The airplane's computer takes over,
					stopping its spin, leveling out the plane,
					and preventing certain disaster!"
					#dispose: self
				)
			)
			(
				(cloud
					show:				,
					setCel:			0,
					posn:				245 138,
					xStep:			2,
					moveSpeed:		10,
					setMotion:		MoveTo 120 138
				)
				(cloud2
					show:				,
					setCel:			3,
					posn:				310 173,
					xStep:			2,
					moveSpeed:		10,
					setMotion		MoveTo 180 173
				)
				;fly plane off screen
				(plane
					setLoop:		4,
					setCel:		0,
					setCycle:	EndLoop self
				)
			)
;SRC			(
;SRC				(plane
;SRC					moveSpeed:	3,
;SRC					xStep:		6,
;SRC					yStep:		6,
;SRC					setMotion:	MoveTo 350 (plane y?) self
;SRC				)
;SRC			)
			(
			 	(= seconds 4)
			)
			(
				(TPrint "\nYOU DID IT, LARRY!\n\n"
					#font:		niceFont
					#width:		200
					#mode:		teJustCenter
					#dispose: 	self
				)
			)
			(
				(= ticks 120)
			)
			(
				(TPrint "But there's a slight problem...
					now you've got to land this sucker!"
					#dispose: self
				)
			)
			(
				(curRoom newRoom: 310)
			)
		)
	)
);sAutoPilot

(instance plane of Actor
	(properties
		view			392
		loop			1
		cel			0
		x				249
		y				137
		description "your plane"
		priority		3
		signal		(| fixedLoop fixPriOn ignrAct)
	)
	(method (doVerb theVerb invItem)
		(thePlane doVerb: theVerb &rest)
	)
)

(instance larryHead of Prop
	(properties
		view			391
		loop			2
		cel			0
		x				65
		y				113;63
		z				50
		priority		7
 		description "yourself"
		signal		(| fixPriOn ignrAct)
	)
	(method (doVerb theVerb invItem)
		(bigLarry doVerb: theVerb &rest)
	)
)

(instance larry of Prop
	(properties
		view			391
		loop			0
		cel			0
		x				70
		y				189
		priority		6
 		description "yourself"
		signal		(| fixedLoop fixPriOn ignrAct)
	)
	(method (cue)
		(if larryStop
			(self setCel: 2)
		else
			(self setCel: (Random 0 3))
			(Delay (Random 2 4) CYCLES larry)
		)
	)
	(method (doVerb theVerb invItem)
		(bigLarry doVerb: theVerb &rest)
	)
)

(instance bigLarry of Feature
	(properties
		x 					79
		y 					149
		z 					7
		heading 			0
		nsLeft 			0
		nsTop 			96
		nsBottom 		189
		nsRight 			158
 		description 	"you"
		sightAngle 		40
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbLook
				(TPrint "You look like you're gonna die!")
			)
			(verbTalk
				(TPrint "You begin to talk to yourself then realize,
					\"I've gotta fly this sucker!\"")
			)
			(verbDo
				(TPrint "You scratch your ear instead of saving this plane!")
			)
			(verbUse
				(TPrint "That won't help! Do something!")
			)
			(else
				(super doVerb: theVerb invItem &rest)
			)
		)
	)
)

(instance  thePlane of Feature
	(properties
		x 					238
		y 					151
		z 					9
		heading 			0
		nsLeft 			157
		nsTop 			95
		nsBottom 		189
		nsRight 			319
 		description 	"the plane"
		sightAngle 		40
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbLook
				(TPrint "This is your plane, plummeting to Earth,
					soon to crash, killing all aboard.")
			)
			(verbTalk
				(TPrint "\"MAYDAY! MAYDAY!!\" you scream,
					into no apparent microphone!")
			)
			(verbUse
				(TPrint "That won't help a falling airplane!")
			)
			(verbDo
				(TPrint "How you wish it were that simple--to just reach
					outside and grab your airplane,
					pull it back to safety.
					But you've learned long ago: you're no Superman!")
			)
			(else
				(super doVerb: theVerb invItem &rest)
			)
		)
	)
)

