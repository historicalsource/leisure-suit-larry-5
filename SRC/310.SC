;**
;**	Logics for Room 310 -- Takeoff & Landing (far shot of airport)
;**
;**	LSL5:  Passionate Patti Does A Little Undercover Work
;**				Copyright 1991
;**				by Al Lowe
;**
;**	Code forced to work by C. Escobar
;**
;**	Last Update:  September 7, 1991
;**


(script#		310)

(public
	rm310		0
)

(define	vAirport				310)
(define	vPlane				311)
(define	lTakeOff				0)

; sounds and music for outside airport

(define	sfxTakeOff				311)
(define	sfxLand					312)
(define	sfxBounceLanding		313)
(define	sfxBrakes				314)
(define	sfxPullOut 				392)
(define	sfxPullIn 				396)
(define	sfxPlaneDive			386)

(define	mTraveling				315)
(define	mSavePlane				391)

(define	sPlaneSnd				321)

(procedure
	SetUpRoom
)

(local
	birdX
	birdY
	birdFlying
	cueCounter

	crash1Pts =									;come in from left 1st time
	[	1 1 58 71
		1 2 80 99
		1 3 98 121
		1 4 118 141
		1 5 131 149
		1 6 145 156
		PATHEND
	]

	crash1aPts =
	[	1 7 171 150
		1 8 195 133
		PATHEND
	]

	crash2Pts =									;come in from right
	[	0 4 199 100
		0 5 188 102
		0 6 174 101
		0 7 165 101
		PATHEND
	]

	crash3Pts =									;come in from left 2nd time
	[	1 1 41 35
		1 2 54 53
		1 3 63 68
		1 4 77 90
		1 5 96 111
		PATHEND
	]

	crash3aPts =								;come in from left 2nd time
	[	1 6 112 114
		1 7 115 117
		PATHEND
	]

	crash4Pts =									;final landing
	[	2 0 274 148
		2 1 239 153
	2 2 202 157
		2 3 167 156
		2 4 145 150
		2 5 134 146
		2 6 134 146
		PATHEND
	]
)


(instance rm310 of LLRoom
	(properties
;		picture		310
		north			160
		south			160
		east			160
		west			160
		lookStr		"To you, all airports look alike (although the
						landscaping does vary slightly)."
	)

	(method (init)
		(super init:)
		(HandsOff)

		;init features
		(sky 		init:)
		(aTree	init:)
		(aTree2	init:)
		(aTree3	init:)
		(aPlane	init:)
		(aPlane2 init:)
		(tower	init:)

		(SetUpRoom)
		(switch prevRoomNum
			(320
				(Load SOUND sfxBrakes)		;314
				(Load SOUND sfxLand) 		;312

            ;in case we fast-forwarded from 320 dreams 

   			(theMusic2 number:	0, client: FALSE, stop:)
				(self setScript: sLand)
			)
			(390
				(theMusic3
					init,
					number:			sfxPlaneDive,
					flags:			mNOPAUSE,
					play:,
					setLoop:			-1,
				)
				(self setScript: sCrash)
			)
			(else
	 			(if (== REMState Casablanca)
					(self setScript sFlyGumbo)
	 			else
	 				(self setScript: sFly)
	 			)
			)
		)
		(addToPics: doit)
	);init

	(method (newRoom n)
		(if (== prevRoomNum 390)
			(theMusic stop)
			(theMusic2 stop)
		)
		(super newRoom n)
	)

	(method (cue:)
		(if birdFlying
			(switch (++ cueCounter)
				(1
					(bird
						show,
						setStep:			4 4,
						setCycle:		Forward,
						setMotion		MoveTo  birdX birdY self,
					)
				)
				(2
					(= birdFlying FALSE)
					(bird dispose:)
				)
			)
		)
	)
);rm310

(instance plane of Actor
	(properties
		view			vPlane	;311
		priority		13							;bird=15 sock=14
		cel:			0
		signal		(| fixedCel fixedLoop fixPriOn ignrAct ignrHrz)
	)
)

(instance bird of Actor
	(properties
		view			vAirport ;310
		priority		15
		signal		(| fixedLoop fixPriOn ignrAct ignrHrz)
	)
)

(instance traffic of Prop
	(properties
		view			vAirport ;310
		loop			0
		x				261
		y				55
		cycleSpeed 	6
		priority		2
		signal		(| fixedLoop fixPriOn ignrAct)
	)
)

(instance sock of Prop
	(properties
		view			vAirport ;310
		loop			2
		priority		14
		cycleSpeed 	6
		signal		(| fixedLoop fixPriOn ignrAct)
	)
)

(instance flag of Prop
	(properties
		view			vAirport ;310
		priority		4
		cycleSpeed 	6
		signal		(| fixedLoop fixPriOn ignrAct)
	)
)

(procedure (SetUpRoom)
	(curRoom drawPic: 310)
	(traffic
		init:,
		setCycle:	Forward,
	)
	(switch larryLoc
		(LosAngeles
			(sock
				init:,
				posn:				260 164,
				setCycle:		Forward,
			)
			(flag
				init:,
				setLoop:			1,
				posn:				29 40,
				setCycle:		Forward,
			)
			(tree1 init:)
			(tree2 init:)
			(tree3 init:)
			(tree4 init:)
			(tree5 init:)
			(water1 init:)
			(water2 init:)
			(mnt1 init:)
			(mnt2 init:)
			(cloud init:)
		)
		(NewYork
			(sock
				init:,
				posn:				185 116,
				setCycle:		Forward,
			)
			(flag
				init:,
				setLoop:			3,
				posn:				34 44,
				setCycle:		Forward,
			)
		)
		(Miami
			(sock
				init:,
				posn:				260 164,
				setCycle:		Forward,
			)
			(flag
				init:,
				setLoop:			3,
				posn:				10 44,
				setCycle:		Forward,
			)
		)
		(AtlanticCity
			(sock
				init:,
				posn:				260 164,
				setCycle:		Forward,
			)
			(flag
				init:,
				setLoop:			3,
				posn:				10 44,
				setCycle:		Forward,
			)
		)
	)

	(if (!= prevRoomNum 390)

		(switch (Random 1 10)
			(1
				(= birdX -12)
				(= birdY 133)
				(= birdFlying TRUE)
				(bird
					init,
					setLoop:				4,
					posn:					327 47,
					hide,
				)
			)
			(2
				(= birdX 73)
				(= birdY 189)
				(= birdFlying TRUE)
				(bird
					init,
					setLoop:				4,
					posn:					319 98,
					hide,
				)
			)
			(3
				(= birdX 0)
				(= birdY 103)
				(= birdFlying TRUE)
				(bird
					init,
					setLoop:				4,
					posn:					319 138,
					hide,
				)
			)
			(4
				(= birdX 0)
				(= birdY 38)
				(= birdFlying TRUE)
				(bird
					init,
					setLoop:				4,
					posn:					319 36,
					hide,
				)
			)
			(5
				(= birdX 319)
				(= birdY 63)
				(= birdFlying TRUE)
				(bird
					init,
					setLoop:				5,
					posn:					0 123,
					hide,
				)
			)
			(6
				(= birdX 319)
				(= birdY 120)
				(= birdFlying TRUE)
				(bird
					init,
					setLoop:				5,
					posn:					0 40,
					hide,
				)
			)
			(7
				(= birdX 319)
				(= birdY 120)
				(= birdFlying TRUE)
				(bird
					init,
					setLoop:				5,
					posn:					0 40,
					hide,
				)
			)
		)
	)
)

(instance arrow of View
	(properties
		view			315
	)
)
(instance dash  of View
	(properties
		view			315
		signal		(| fixedCel fixedLoop fixPriOn ignrAct)
	)
)

(instance sLand of Script
	(method (changeState newState)
		(switchto (= state newState)
			(
				(theMusic
					number:			sfxLand, 		;312
					loop:				1,
					play:
				)
				(= cycles 2)
			)
			(
				(plane
					init,
					posn:				319 53,
					setLoop:			4,
					setCel:			0,
					setStep:			7 7,
					cycleSpeed:		10,
					moveSpeed:		3,
					setMotion:		MoveTo 194 115 self
				)
			)
			(
				(theMusic2
					number:			sfxBrakes, 			;314
					loop:				1,
					play:
				)
				(plane
					setCycle:		EndLoop,
					setStep:			6 6,
					setMotion:		MoveTo 104 121 self
				)
				(= ticks 16)
			)
			(
				(theMusic2 stop)
			)
			(
				;get cue from plane...
				(plane
					setStep:			4 4,
					setMotion: 		MoveTo -39 127 self
				)
				(= ticks 20)
			)
			(
				(theMusic fade 0 15 12 TRUE)
				;get cue from plane finishing...
			)
			(
				(= seconds 4)
			)
			(
				(= larryLoc ((Inventory at: iBoardingPass) state?))
				(if (!= ((Inventory at: iBatteryCharger) owner?) LARRY)
					((Inventory at: iBatteryCharger) owner: 0)
				)
				(Bclr fBeenToTown)
				(Bclr fOkToBoard)
				(curRoom newRoom 290)
			)
		)
	)
)

(instance tree1 of PicView
	(properties
		view			310
		loop			6
		cel			3
		x				139
		y				122
		priority		14
		signal		(| fixedLoop fixPriOn ignrAct)
	)
)

(instance tree2 of PicView
	(properties
		view			310
		loop			6
		cel			1
		x				14
		y				146
		priority		14
		signal		(| fixedLoop fixPriOn ignrAct)
	)
)

(instance tree3 of PicView
	(properties
		view			310
		loop			6
		cel			2
		x				0
		y				144
		priority		14
		signal		(| fixedLoop fixPriOn ignrAct)
	)
)

(instance tree4 of PicView
	(properties
		view			310
		loop			6
		cel			0
		x				22
		y				179
		priority		14
		signal		(| fixedLoop fixPriOn ignrAct)
	)
)

(instance tree5 of PicView
	(properties
		view			310
		loop			6
		cel			2
		x				305
		y				176
		priority		14
		signal		(| fixedLoop fixPriOn ignrAct)
	)
)

(instance water1 of PicView
	(properties
		view			310
		loop			8
		cel			0
		x				40
		y				45
		priority		1
		signal		(| fixedLoop fixPriOn ignrAct)
	)
)

(instance water2 of PicView
	(properties
		view			310
		loop			8
		cel			1
		x				179
		y				44
		priority		1
		signal		(| fixedLoop fixPriOn ignrAct)
	)
)

(instance mnt1 of PicView
	(properties
		view			310
		loop			7
		cel			0
		x				234
		y				38
		priority		1
		signal		(| fixedLoop fixPriOn ignrAct)
	)
)

(instance mnt2 of PicView
	(properties
		view			310
		loop			7
		cel			0
		x				198
		y				36
		priority		1
		signal		(| fixedLoop fixPriOn ignrAct)
	)
)

(instance cloud of PicView
	(properties
		view			310
		loop			7
		cel			1
		x				221
		y				32
		priority		1
		signal		(| fixedLoop fixPriOn ignrAct)
	)
)

(instance sky of Feature
	(properties
		x 159
		y 20
		z 0
		heading 0
		nsLeft 0
		nsTop -1
		nsBottom 41
		nsRight 319
		description	"the sky"
		lookStr 		"Hills stretch off into the distance."
		sightAngle 40
	)
)
(instance aTree of Feature
	(properties
		x 24
		y 127
		z 0
		heading 0
		nsLeft 0
		nsTop 77
		nsBottom 177
		nsRight 49
		description	"the trees"
		lookStr 		"This airport is rife with trees."
						;**	rife spell-checked August 1, 1991
		sightAngle 40
	)
)
(instance aTree2 of Feature
	(properties
		x 300
		y 142
		z 0
		heading 0
		nsLeft 282
		nsTop 108
		nsBottom 176
		nsRight 319
		description	"the tree"
		lookStr 		"This airport is rife with trees."
		sightAngle 40
	)
)
(instance aTree3 of Feature
	(properties
		x 142
		y 98
		z 0
		heading 0
		nsLeft 122
		nsTop 73
		nsBottom 124
		nsRight 162
		description	"the trees"
		lookStr 		"This airport is rife with trees."
		sightAngle 40
	)
)

(instance aPlane of Feature
	(properties
		x 95
		y 66
		z 0
		heading 0
		nsLeft 71
		nsTop 58
		nsBottom 74
		nsRight 120
		description	"the airplane"
		lookStr 		"You hope you don't have to wait for that airplane to
						take off."
		sightAngle 40
	)
)
(instance aPlane2 of Feature
	(properties
		x 231
		y 65
		z 0
		heading 0
		nsLeft 216
		nsTop 59
		nsBottom 71
		nsRight 246
		description	"the airplane"
		sightAngle 40
	)
	(method (doVerb theVerb invItem)
		(aPlane doVerb: theVerb invItem &rest)
	)
)

(instance tower of Feature
	(properties
		x 265
		y 56
		z 0
		heading 0
		nsLeft 248
		nsTop 34
		nsBottom 79
		nsRight 282
		description	"the tower"
		lookStr 		"The control tower is staffed with non-union air traffic
						controllers."
		sightAngle 40
	)
)

(instance sCrash of Script
	(method (changeState newState)
		(switchto (= state newState)
			(
				(= ticks 30)
			)
			(						 				;**	Attempt #1:  Crash from left
				(TPrint "There's the runway, Larry! Steady now.
						Bring 'er in carefully! You're \"coming in\" too fast!"
						#dispose: self
				)
			)
			(
				(= ticks 30)
			)
			(
				(theMusic2
					number:			sfxPullIn,
					loop:				1,
					play:
				)
				(plane
					init,
					posn: 			39 50,
					setLoop:			1,
					setCel:			0,
					setStep:			10 10,
					moveSpeed:		2,
					cycleSpeed:		9,
					ignoreActors:	TRUE,
					illegalBits:	0,
					setCycle:		MoveCycle @crash1Pts self,
				)
			)
			(
				(theMusic
					number:			sfxBounceLanding,
					loop:				1,
					play:
				)
				(TPrint "\"YEOW!\" you scream!"
					HIGH
					#title:		{You}
					#dispose:
				)
				(= cycles 2)
			)
			(
				(theMusic2
					number:			sfxPullOut,
					loop:				1,
					play:
				)
				(plane
					setCycle:		MoveCycle @crash1aPts self,
				)
			)
			(
				(plane
					setMotion		MoveTo 319 50 self,
				)
			)
			(										;**	done, wait a while
				(plane hide)
				(= seconds 3)
			)
			(
				(Cls)
				(TPrint "Whew! That was close.
					You just barely \"pulled out\" in time!" #dispose: self)
			)
			(
				(= seconds 2)
			)
;**				(
;**					(TPrint "You circle around 180 degrees to try again." #dispose: self)
;**				)
;**				(
;**					(= seconds 2)
;**				)
			(								;**	Attempt #2:  coming in from the right
				(theMusic2
					number:			sfxPullIn,
					loop:				1,
					play:
				)
				(plane
					show,
					setLoop:			0,
					setCel:			0,
					posn:				315 1,
					setMotion:		MoveTo 217 102 self,
				)
			)
			(
				(TPrint "\"OH, NO!!\" you scream!"
					LOW
					#title:			{You}
					#dispose:
				)
				(theMusic
					number:			sfxBounceLanding,
					loop:				1,
					play:
				)
				(plane
					setCycle:		CycleTo 	2 FORWARD self
				)
			)
			(
				(theMusic2
					number:			sfxPullOut,
					loop:				1,
					play:
				)
				(plane
					setCel:			3,
					posn:				210 101,
					setCycle:		MoveCycle @crash2Pts self
				)
			)
			(
				(plane
					setCel:			8,
					setMotion:		MoveTo 46 3 self
				)
			)
			(
				(plane hide)
				(= seconds 3)
			)
			(
				(Cls)
				(TPrint "Larry! You've got to \"keep it up\" longer!" #dispose: self)
			)
			(
				(= seconds 3)
			)
			(
				(TPrint "You circle around 180 degrees to try again." #dispose: self)
			)
			(
				(= seconds 3)
			)
			(										;**	Attempt #3:  from the left
				(theMusic2
					number:			sfxPullIn,
					loop:				1,
					play:
				)
				(plane
					setLoop:			1,
					setCel:			0,
					posn: 			21 -4,
					setCycle:		MoveCycle: @crash3Pts self,
					show,
				)
			)
			(
;**					(TPrint "\"ARRRGGGGGHHH!!\" you scream!"
;**						HIGH
;**						#title:			{You}
;**						#dispose:
;**					)
				(theMusic
					number:			sfxBounceLanding,
					loop:				1,
					play:				,
				)
				(= ticks 2)
			)
			(
				(theMusic2
					number:			sfxPullOut,
					loop:				1,
					play:
				)
				(plane
					setCycle:		MoveCycle: @crash3aPts self,
					show,
				)
			)
			(
				(plane
					setCel:			8,
					setMotion		MoveTo 250 -5 self
				)
			)
			(
				(plane hide)
				(= ticks 20)
			)
			(
;				(Cls)
				(TPrint "Next time, try it with both hands on your \"yoke!\"")
				(= seconds 5)
			)
;**				(
;**					(TPrint "You circle around 180 degrees to try again.")
;**					(= seconds 3)
;**				)
			(										;**	Attempt #4:  final landing
				(TPrint "\"This is it,\" you think,
					\"I've got to make it this time!\""
					HIGH
					#title:			{You}
					#dispose:
				)
				(= seconds 2)
			)
			(
				(plane
					show,
					setLoop:			2,
					setCel:			0,
					posn:				331 136,
					setCycle:		MoveCycle @crash4Pts self,
				)
			)
			(
				(theMusic2
					number:			sfxBrakes, 			;314
					loop:				1,
					play:
				)
				(theMusic  stop:)
				(theMusic3 stop:)
				(plane
					setLoop:			5,
					setCel:			0,
					cycleSpeed:		9,
					setCycle:		EndLoop,
					setMotion:		MoveTo 86 115,
				)
				(= seconds 5)
			)
			(
				(theMusic
					number:			mSavePlane,
					loop:				1,
					play:
				)
				(TPrint "You did it, Larry!
					You saved an entire airliner filled with innocent people.
					Surely, you'll finally gain the respect you deserve."
					BIG
					#dispose: self
				)
			)
			(
				(= seconds 2)
			)
			(
				(TPrint "But how stupid were those airport designers!
					They built a runway 100 feet long...")
				(TPrint "...and TWO MILES wide!")

				(= seconds 3)
			)
			(
				(curRoom newRoom 295)		;end of game
			)
		)
	)
);sCrash

(instance sTakeOff of Script
	(method (changeState newState)
		(switchto (= state newState)
			(
				(theMusic
					number:			sfxTakeOff, 		;311
					loop:				1,
					play:				,
					hold:				10,
				)
				(plane
					init,
					show,
					posn:				-38 127,
					setLoop:			3,
					setCel:			0,
					setStep:			2 2,
					cycleSpeed:		6,
					moveSpeed:		2,
				)
				(= ticks 60)
			)
			(
				(plane
					setMotion:		MoveTo 33 119 self,
				)
			)
			(
				(plane
					setStep:			3 3,
					setMotion:		MoveTo 88 118 self,
				)
			)
			(
				(plane
					setStep:			4 4,
					setMotion:		MoveTo 130 115 self,
				)
			)
			(
				(plane
					setCycle:		EndLoop self,
					setMotion:		MoveTo 190 111,
				)
			)
			(
				(theMusic
					release:,
					fade:				0 15 5 STOP_MUSIC
				)
				(plane
					setStep:			6 6,
					setMotion: 		MoveTo 353 12 self
				)
			)
			(
				(plane dispose:)
				(self dispose:)
			)
		)
	)
)

(instance theMusic3 of Sound)


(instance sFlyGumbo of Script
	(method (changeState newState)
		(switchto (= state newState)
			(
				(self setScript: sTakeOff self)
			)
			(
				(theIconBar disable:)
				(= REMState Casablanca)
				(theMusic2
					number:		mTraveling, 	;312
					loop:			1,
					play:	 		self,
				)
				(Cls)
				(cast eachElementDo: #dispose)
				(curRoom drawPic: 315 FADEOUT)	;globe
				(TPrint "Your visit to the East Coast finished,
					your airplane heads back to Los Angeles."
					HIGH
					#dispose: self
				)
				(arrow
					init:,
					setLoop:			1,
					setPri:			15,
					posn:				190 122,
				)
				(dash
					init,
					setPri:			1,
					setLoop:			2,
					posn:				(+ (arrow x?) 2) (- (arrow y?) 2),
					addToPic:,
				)
			)
			(
				(= ticks 30)
				(= register 8)
			)
			(
				;PLANE FLYS TO GUMBO
				(= start state)
				(arrow
					posn:				(- (arrow x?) 5) (- (arrow y?) 1),
				)
				(= ticks 30)
			)
			(
				(dash			 ;posn under arrow
					init,
					setPri:			1,
					setLoop:			2,
					posn:				(+ (arrow x?) 2) (- (arrow y?) 2),
					addToPic:,
				)
				(= ticks 30)
			)
			(
				(if (not (-- register))
					(theMusic
						number:				sPlaneSnd,
						loop:					-1,
						play:
					)
					(= cycles 2)
				else
					(self init:)
				)
			)
			(
				(theIconBar enable:)
				(curRoom newRoom 320)
			)
		)
	)
);flyGumbo

(instance sFly of Script
	(method (changeState newState)
		(switchto (= state newState)
			(
				(self setScript: sTakeOff self)
			)
			(
				(if (== larryLoc LosAngeles)
					(theIconBar disable:)
					(theMusic2
						number:		mTraveling, 	;312
						loop:			1,
						play:	 		self,
					)
					(Cls)
					(cast eachElementDo: #dispose)
					(curRoom drawPic: 315 FADEOUT)	;globe
					(arrow
						init:,
						setPri:			15,
						posn:				52 103,
						setLoop:			0,
					)
					(dash
						init,
						setPri:			1,
						setLoop:			2,
						posn:				(- (arrow x?) 2) (- (arrow y?) 2),
						addToPic:,
					)
					(= seconds 2)
					(= register 6)
				else
					(curRoom drawPic: pBlackPic FADEOUT)	;999
					(curRoom newRoom 320)
				)
			)
			(
				(= start state)
				(arrow
					posn:				(+ (arrow x?) 5) (- (arrow y?) 2),
				)
				(= ticks 30)
			)
			(
				(dash
					init,
					setPri:			1,
					setLoop:			2,
					posn:				(- (arrow x?) 2) (- (arrow y?) 2),
					addToPic:,
				)
				(= ticks 30)
			)
			(
				(if (not (-- register))
					(= ticks 1)
					(= register 7)
					(arrow
						posn:				(+ (arrow x?) 1) (- (arrow y?) 1),
					)
				else
					(self init:)
				)
			)

			(
				(= start state)
				(arrow
					posn:				(+ (arrow x?) 5) (+ (arrow y?) 1),
				)
				(= ticks 30)
			)
			(
				(dash
					init,
					setPri:			1,
					setLoop:			2,
					posn:				(- (arrow x?) 2) (- (arrow y?) 2),
					addToPic:,
				)
				(= ticks 30)
			)

			(
				(if (not (-- register))
					(theMusic
						number:				sPlaneSnd,
						loop:					-1,
						play:
					)
					(= cycles 2)
				else
					(self init:)
				)
			)
			(
				(theIconBar enable:)
				(curRoom newRoom 320)
			)
		)
	)
)

