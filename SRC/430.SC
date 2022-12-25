;**
;**	Logics for Room 430 -- Commander Twit's workshop, west CARTOON
;**
;**	LSL5:  Passionate Patti Does A Little Undercover Work
;**				Copyright 1991
;**				by Al Lowe
;**
;**	Programmed by Oliver Brelsford
;**
;**	Last Update:  September 3, 1991
;**

(script#		430)

(public
	rm430		0
)

(define		twitArmZ				26)

(define		sVibratorOn			431)
(define		sVibratorShoots	432)
(define		sVibratorHits		433)
(define		sShootingBra		434)
(define		sRachetBra			435)
;(define		sLabSounds1			436)

(define vDesmondTalker			1431)
(define vTwitTalker				1430)

(define	vRoom						430)
(define	vRoomEgo			 		(+ 1 vRoom))


(define	pWorkshopWest			430)
(define	vWorkshopWest			430)
	(define	lOfficeDoor			0)
	(define	lDoorExterior		1)
	(define	lLadder				2)
;(define	vTwit						432)
;	(define	lWalkingR			0)
;	(define	lWalkingL			1)
;	(define	lWalkingD			2)
;	(define	lGesturing			3)
;	(define	lPointing			4)
;	(define	lTalking				5)
(define	vVibratorMan			434)
	(define	lWorkingAtBench	0)
	(define	lManipulating1		1)
	(define	lManipulating2		2)
	(define	lLiftoff				3)
	(define	lVibrating			4)
(define	vBraMan					436)
	(define	lStandingBra		0)
	(define	lBeingAdjusted		1)
	(define	lStartScratching	2)
	(define	lScratchingHead	3)
	(define	lExplosion			4)
	(define	lEmbarassed			5)
	(define	lShrugging			6)
	(define	lGrisleyRemains	7)
(define	vTechMan					437)
	(define	lStandingTech		0)
	(define	lAdjustingBra		1)
	(define	lListeningToTwit	2)
	(define	lGettingShot		3)
	(define	lAshes				4)
	(define	lWorkingAtBenchT	5)
	(define	lHammerToWrench	6)
(define	vDesmondInLab			438)
	(define	lWalkingEast		0)
	(define	lWalkingWest		1)

(local
	braPts = [175 112 246 112 246 120 175 120]
	braPts2 = [212 112 254 112 254 120 212 120]
)


(instance rm430 of LLRoom
	(properties
		picture		430
		west			420
		east			440
		lookStr		"Although you haven't really thought about, 
						this isn't what you would have pictured an F.B.I.
						lab to be."
	)
	(method (init)
		(braPolygon
			points:		@braPts,
			size:			4,
		)
		(braPolygon2
			points:		@braPts2,
			size:			4,
		)
		(curRoom addObstacle:
			((Polygon new:)
				type: PBarredAccess,
				init:	0 0 319 0 319 103 133 103 133 80 89 80 9 131 107 131 111 128
						190 128 190 136 283 136 294 147 319 147 319 189 0 189,
				yourself
			)
		)
		(LoadMany VIEW
			vRoom
			vWorkshopWest
			vTwit
			vTwitTalker
		)
		(Load SOUND
			sLabSounds1
		)
		(Load SCRIPT JUMP)
		(ego
			init:,
			edgeHit:		0,
			normalize:,
		)
		(desmondDoor init:)
		(if
			(== prevRoomNum 440)
			(ego x: 300)
			(self
				style:		SCROLLRIGHT,
				setScript: 	sEnter
			)
			(desmondDoor cue:)
		else
			(LoadMany VIEW
				vDesmondInLab
				vDesmondTalker
			)
			(LoadMany SOUND
				sDoorOpen
				sDoorClose
			)
			(ego
				x:				30,		;38,
				y:				105,
				cycleSpeed:	6,
				moveSpeed:	6,
			)
			(twit
				init:			,
				posn:			108 104,
				setCycle:	StopWalk SAMEVIEW,
			)
			(HandsOff)
			(self setScript: sRoom)
		)
		(if
			(not (Btst fVibratorMan))
			(LoadMany SOUND
				sVibratorOn
				sVibratorShoots
				sVibratorHits
			)
			(vibMan
				init:,
				setScript:	sWork
			)
		)
		(if
			(not (Btst fBraMan))
			(LoadMany SOUND
				sShootingBra
				sRachetBra
			)
			(curRoom addObstacle: braPolygon)
			(braMan
				init:,
				stopUpd:
			)
			(techMan
				init:,
			)
		)
		(northDoor
			init:,
			stopUpd:
		)
		(ladder
			setCycle:		Forward,
			init:,
		)
		(dartboard init:)
		(monitors init:)
		(plug init:)
		(outlet init:)
		(chair init:)
		(joystick init)
		(circuitboard init:)
		(northBank init:)
		(southBank init:)
		(super init:)
	)
	(method (dispose)
		(DisposeScript JUMP)
		(super dispose:)
	)
)


;ษอออออออออออออออออออป
;บ                   บ
;บ Scripts				บ
;บ                   บ
;ศอออออออออออออออออออผ

(instance sEnter of Script
	(method (changeState newState)
		(switchto (= state newState)
			(
				(twit
					init:,
					x:				400,
					y:				101,
					setCycle:  	StopWalk SAMEVIEW,
					setMotion:	MoveTo 280 101 self
				)
			)
			(
				(HandsOn)
				(self dispose:)
			)
		)
	)
)

(instance sWork of Script
	(method (changeState newState)
		(switchto (= state newState)
			(
				(client
					setCycle:	EndLoop
				)
				(= seconds (Random 2 3))
			)
			(
				(self init:)
			)
		)
	)
)

(instance sRoom of Script
	(method (changeState newState)
		(switchto (= state newState)
			(
				(twit setHeading: 270)
				(= cycles 1)
			)
			(
				(soundFX
					number:		sDoorOpen,
					play:
				)
				(desmondDoor
					setCycle:	EndLoop self
				)
			)
			(
				(theMusic2
					number:		sLabSounds1,
					setLoop:		-1,
					play:
				)
				(desmondDoor stopUpd:)
				(desmond
					init:			,
					x:				45,
					y:				101,
					setCycle:	Walk,
					setMotion:	MoveTo 95 (desmond y?)
				)
				(ego
					setMotion:	MoveTo 96 (+ (ego y?) 4) self
				)
			)
			(
				(= ticks 60)
			)
			(
				(Say desmondTalker
					"\"Welcome to our Technical Laboratory.
					This is the head of our Office of Agent Field Equipment,
					Commander Twit.
					Commander Twit, meet Passionate Patti.\""
					#dispose:
					#caller:		self
				)
			)
			(
				(= seconds 3)
			)
			(
				(Say twitTalker
					"\"Good morning, Patti,\" says Commander Twit.
					\"It's so very nice to meet you.\""
					#dispose:
					#caller:		self
				)
			)
			(
				(= seconds 3)
			)
			(
				(Say ego
					"\"Likewise, Commander,\" you reply, thinking that is
					the first time you've ever said THAT phrase!
					\"What is all this wonderful equipment you have here?
					I've always been very interested in research--and
					researchers!\""
					LOW
				)
				(= ticks 60)
			)
			(
				(Say twitTalker
					"\"You have?\" Twit asks suspiciously.
					\"Well, allow me to show you about for a moment
					before we begin your indoctrination.\""
					#dispose:
					#caller:		self
				)
			)
			(
				(= ticks 60)
			)
			(
				(Say ego
					"\"Oh, I'd just love a tour,\" you say,
					flashing your best smile."
					LOW
				)
				(= ticks 60)
			)
			(
				(Say twitTalker
					"\"Fine,\" Twit says, obviously charmed.
					\"Feel free to explore the lab a bit, Patti; when you're ready, 
					I'll give you some information you'll need.\""
					#dispose:
					#caller:		self
				)
			)
			(
				(= ticks 60)
			)
			(
				(Say desmondTalker
					"\"Enjoy your indoctrination, Patti,\"
					says Desmond, turning to leave,
					\"and especially your physical!\""
					#dispose:
					#caller:		self
				)
			)
			(
				(desmond
					setMotion:	MoveTo (- (desmond x?) 75) (desmond y?) self
				)
			)
			(
				(desmond dispose:)
				(desmondDoor
					setCycle:		BegLoop desmondDoor
				)
				(soundFX
					number:		sDoorClose,
					play:
				)
				(twit
					setMotion:	MoveTo 154 (- (twit y?) 3) self
				)
			)
			(
				(Face twit ego self)
			)
			(
				(ego
					moveSpeed:		(theGame egoMoveSpeed?),
					cycleSpeed:		(theGame egoMoveSpeed?)
				)
				(HandsOn)
				(self dispose:)
			)
		)
	)
)
(instance sVibrator of Script
	(method (changeState newState)
		(switchto (= state newState)
			(
				(theGame setCursor: theCursor TRUE 500 500)
				(Face ego braMan self)
			)
			(
				(Say ego "\"Excuse me, Commander Twit,\" you say.
					\"I'm particularly interested in this man's research!
					What is he up to?\"")
				(= ticks 60)
			)
			(
				(Bset fVibratorMan)
				(Say twitTalker
				 "\"I suspected you might be intrigued.
					Dr. Rolfing is working on refining our new
					`infrared, heat-seeking, surface-to-air vibrator.'
					We feel it will be of considerable value as a
					protective device against certain types of enemy agents.\""
					#dispose:
					#caller:		self
				)
			)
			(
				(soundFX
					number:		sVibratorOn,
					setLoop:		-1,
					play:
				)
				(Face ego vibMan self)
			)
			(
				(vibMan
					setScript:	0,
					setLoop:		lManipulating1,
					cycleSpeed:	24,
					setCycle:	Forward
				)
				(= ticks 180)
			)
			(
				(vibMan
					setCel:		0,
					setLoop:		lManipulating2,
					cycleSpeed:	18,
					setCycle:	EndLoop self
				)
			)
			(
				(soundFX
					number:		sVibratorShoots,
					play:
				)
				(vibMan
					setCel:		0,
					setLoop:		lLiftoff,
					setCycle:	EndLoop self,
				)
			)
			(
				(soundFX
					number:		sVibratorHits,
					play:
				)
				(vibMan
					x:				(- (vibMan x?) 6),
					y:				(- (vibMan y?) 12),
					xStep:		8,
					yStep:		8,
				)
				(= cycles 1)
			)
			(
				(vibMan
					setLoop:		lVibrating,
					cycleSpeed:	4,
					moveSpeed:	8,
					setStep:		1 1,
					setCycle:	Forward,
					setMotion:	JumpTo -30 230 self
				)
			)
			(
				(soundFX fade:)
				(vibMan dispose:)
				(= ticks 180)
			)
			(
				(Say twitTalker
				 "\"Of course, it works much better when you remember
				 	to turn it on and then RELEASE it!\""
					#dispose:
				)
				(HandsOn)
				(self dispose:)
			)
		)
	)
)

(instance sBra of Script
	(method (changeState newState)
		(switchto (= state newState)
			(
				(theGame setCursor: theCursor TRUE 500 500)
				(Bset fBraMan)
				(techMan setScript: sWork)
				(Face ego braMan self)
			)
			(
				(Say ego "\"Oh, Commander Twit,\" you say.
					\"What are these two men doing?\"")
				(= ticks 60)
			)
			(
				(Say twitTalker
				 "\"Ah, now this is an experiment that I think should be of
					great interest to you, Patti.
					These two technicians are working on one of our latest\n
					Title 9-funded developments: the hair-trigger,
					double-barreled, armor-piercing brassiere cannon.\""
					#dispose:
					#caller:		self
				)
			)
			(
				(techMan
					setScript:	0,
					setCycle:	EndLoop self
				)
			)
			(
				(techMan
					setLoop:		lHammerToWrench,
					setCel:		0,
					setCycle:	EndLoop self
				)
				(braMan
					setLoop:		lBeingAdjusted,
					setCel:		0
				)
			)
			(
				(soundFX
					number:		sRachetBra,
					setLoop:		1,
					play:
				)
				(techMan
					setLoop:		lAdjustingBra,
					setCel:		0,
					setCycle:	Forward,
				)
				(braMan
					setCel:		1,
				)
				(= ticks 120)
			)
			(
				(Say ego
					"\"How interesting, Commander!\" you say.
					\"But how could you ever fire such a device?\""
					LOW
				)
				(= ticks 60)
			)
			(
				(Face twit ego self)
			)
			(
				(Say twitTalker
					"\"Heh, heh, that's the beauty of this baby,\" he exclaims,
					his voice rising with pride.
					\"Just touch your elbows together behind your back!\""
					#dispose:
					#caller:		self
				)
			)
			(
				(braMan
					setLoop:		lStartScratching,
					setCel:		0,
					cycleSpeed:	24,
					setCycle:	EndLoop self
				)
			)
			(
				(braMan
					setLoop:		lScratchingHead,
					setCycle:	Forward
				)
				(techMan
					setLoop:		lListeningToTwit,
					setCel:		255,
					cycleSpeed:	24,
					setCycle:	BegLoop self
				)
			)
			(
				(TPrint "The technician mistakenly believes Commander Twit is
					speaking to him!")
				(braMan
					setLoop:		lExplosion,
					setCel:		0,
					setCycle:	EndLoop self
				)
			)
			(
				(techMan
					setLoop:		lGettingShot,
					setMotion:	MoveTo (- (techMan x?) 30) (- (techMan y?) 10) techMan
				)
				(soundFX
					number:		sShootingBra,
					setLoop:		1,
					play:
				)
				(braMan
					setLoop:		lExplosion,
					setCel:		0,
					cycleSpeed:	5,
					setCycle:	EndLoop self
				)
			)
			(
				(soundFX
					number:		sShootingBra,
					setLoop:		1,
					play:
				)
				(Face twit braMan self)
			)
			(
				(braMan
					setLoop:		lEmbarassed,
					setCel:		0,
					cycleSpeed:	18,
				)
				(= ticks 66)
			)
			(
				(Say twitTalker
					"\"You idiot!\" Twit shouts at the bra-slinging technician,
					\"I was talking to Patti!\""
					#dispose:
					#caller:		self
				)
			)
			(
				(braMan
					setCycle:	EndLoop self
				)
			)
			(
				(= ticks 123)
			)
			(
				(braMan
					setLoop:		lShrugging,
					setCel:		0,
					setCycle:	EndLoop self
				)
			)
			(
				(= ticks 123)
			)
			(
				(braMan
					setLoop:		lEmbarassed,
					setCel:		0,
					setCycle:	EndLoop
				)
				(= seconds 3)
			)
			(
				(Say twitTalker
					"\"Obviously, our turnover rate is rather high here!\""
					#dispose:
				)
				(braMan
					setCycle:	BegLoop self
				)
				((curRoom obstacles?) delete: braPolygon)
				(curRoom addObstacle: braPolygon2)
				(HandsOn)
				(self dispose:)
			)
		)
	)
)


;ษอออออออออออออออออออป
;บ                   บ
;บ Props					บ
;บ                   บ
;ศอออออออออออออออออออผ

(instance twit of Actor
	(properties
		view			vTwit
		loop			lWalkingR
		cel			2
		x				30
		y				104
		signal		ignrAct
		cycleSpeed 	6
		moveSpeed 	6
		description "Commander Twit"
		lookStr		"He's the perfect image of a man more interested in
						science than in his own self-image!"
	)
	(method (doit)
		(super doit: &rest)
		(if
			(and
				(not (self mover?))
				(not (curRoom script?))
				(> (- (ego x?) (self x?)) 20)
			)
			(self
				setMotion:	MoveTo (+ x 10) y
			)
		)
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbDo
				(TPrint "There's nothing you can do to Commander Twit that
					he hasn't done already.")
			)
			(verbTalk
				(cond
					((not (Btst fVibratorMan))
						(HandsOff)
						(curRoom setScript: sVibrator)
					)
					((not (Btst fBraMan))
						(HandsOff)
						(curRoom setScript: sBra)
					)
					(else
						(Say twitTalker
							"\"Whenever you're through exploring this area,
							we'll set you up with the mission-required
							information you're going to need.\""
							#dispose:
						)
					)
				)
			)
			(verbZipper
				(TPrint "Perish the thought!")
			)
			(else
				(super doVerb: theVerb invItem)
			)
		)
	)
)

(instance desmond of Actor
	(properties
		view			vDesmondInLab
		x				25
		y				104
		signal		ignrAct
		cycleSpeed 	6
		moveSpeed 	6
	)
)

(instance desmondDoor of Prop
	(properties
		view			430
		loop			1
		x				15
		y				48
		priority		14
		signal		(| ignrAct fixPriOn)
		cycleSpeed  6
 		description "Desmond's door"
		lookStr 		"This door leads to Inspector Desmond's office.
						It is presently locked."
	)
	(method (cue)
		(super cue:)
		(self
			setPri:		pLGREY,
			stopUpd:
		)
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbDo
				(TPrint "You decide that it wouldn't be a very good idea to
					try to open your new boss' private office--especially
					when he's locked the door.")
			)
			(verbTalk
				(Say ego "\"Hey, Desmond!\" you cry. \"Are you in there?\"")
				(TPrint "(There is no response.)" LOW)
			)
			(else
				(super doVerb: theVerb invItem &rest)
			)
		)
	)
)

(instance northDoor of View
	(properties
		view			430
		loop			0
		cel			0
		x				106
		y				77
		description "the exterior door"
		lookStr		"This door leads outside."
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbDo
				(Say twitTalker
					"\"Whenever you are finished with your indoctrination,\"
					says Twit,
					\"I'll have a limousine waiting for you outside that door.\""
					#dispose:
				)
			)
			(else
				(super doVerb: theVerb invItem &rest)
			)
		)
	)
)

(instance vibMan of Actor
	(properties
		view			434
		loop			0
		cel			0
		x				137
		y				161
		priority		15
		signal		(| ignrAct fixPriOn)
		cycleSpeed  6
		description "the vibrator technician"
	)
	(method (doVerb theVerb invItem)
		(Points ptsVibman pfVibman)
		(HandsOff)
		(curRoom setScript: sVibrator)
	)
)

(instance braMan of Prop
	(properties
		view			436
		loop			0
		cel			0
		x				225		;274
		y				114		;98
		cycleSpeed  6
		description "the bra-slinging technician"
		priority		pGREY
		signal		(| ignrAct fixPriOn)
	)
	(method (doVerb theVerb invItem)
		(if (not (Btst fBraMan))
			(Points ptsBraman pfBraman)
			(HandsOff)
			(curRoom setScript: sBra)
		else
			(super doVerb: theVerb invItem)
		)
	)
)

(instance techMan of Actor
	(properties
		view			437
		loop			5
		cel			3
		x				197		;246
		y				116		;100
		signal		ignrAct
		xStep			15
		yStep			5
		cycleSpeed  10
		description "the bra-tender"
	)
	(method (doVerb theVerb invItem)
		(if (Btst fBraMan)
			(TPrint "Knowing what excellent medical coverage the government
				offers key employees like these, you feel certain this man
				will make a full and complete recovery!")
		else
			(braMan doVerb: theVerb invItem)
		)
	)
	(method (doit)
		(super doit: &rest)
		(if (and (== cel 1)
					(== loop 1)
					(== (soundFX prevSignal?) -1))
			(soundFX play:)
		)
	)
	(method (cue)
		(super cue:)
		(self
			setLoop:		lAshes,
			setCel:		0,
			cycleSpeed:	12,
			setCycle:	EndLoop,
			setMotion:	MoveTo x (+ y 10)
		)
	)
)

(instance ladder of Prop
	(properties
		view		 	vWorkshopWest
		loop			lLadder
		x				159
		y				193
		priority		15
		detailLevel	3
		signal		(| ignrAct fixPriOn)
		description "the Jacob's ladder"
		lookStr		"To think: all your life you assumed there was no real
						purpose for Jacob's ladders!"
	)
)


;ษอออออออออออออออออออป
;บ                   บ
;บ Features				บ
;บ                   บ
;ศอออออออออออออออออออผ

(instance dartboard of Feature
	(properties
		x 64
		y 91
		z 40
		heading 0
		nsLeft 54
		nsTop 37
		nsBottom 65
		nsRight 75
 		description "the dart board"
		sightAngle 40
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbLook
				(TPrint "It is easy to see that F.B.I. employees
					don't always score!")
			)
			(verbDo
				(TPrint "This dart board is here only for the amusement of the
					full-time, salaried employees.")
				(TPrint "(Besides, you've never played darts in your life,
					and you don't intend to begin now!)" WIDELOW)
			)
			(else
				(super doVerb: theVerb invItem &rest)
			)
		)
	)
)
(instance monitors of Feature
	(properties
		x 190
		y 90
		z 49
		heading 0
		nsLeft 164
		nsTop 24
		nsBottom 59
		nsRight 216
 		description "the monitors"
		lookStr 		"These monitors are presently disconnected.
						When they were heavily used many years ago,
						it was only to play \"Jawbreaker!\""
		sightAngle 40
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbDo
				(TPrint "You try to turn on the monitor,
					but are unable to do so.")
			)
			(else
				(super doVerb: theVerb invItem &rest)
			)
		)
	)
)
(instance plug of Feature
	(properties
		x 83
		y 118
		z 100
		heading 0
		nsLeft 71
		nsTop 3
		nsBottom 33
		nsRight 96
 		description "the giant plug"
		lookStr 		"You wonder exactly what that giant plug plugs into."
		sightAngle 40
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbDo
				(TPrint "What? And get electrocuted?!")
			)
			(else
				(super doVerb: theVerb invItem &rest)
			)
		)
	)
)
(instance outlet of Feature
	(properties
		x 147
		y 77
		z 27
		heading 0
		nsLeft 142
		nsTop 43
		nsBottom 57
		nsRight 152
 		description "the intercom"
		lookStr 		"The lab technicians use this intercom to communicate
						with the front office and Commander Twit."
		sightAngle 40
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbDo
				(TPrint "You attempt to communicate through the intercom,
					but your clearance is insufficient.")
			)
			(else
				(super doVerb: theVerb invItem &rest)
			)
		)
	)
)
(instance chair of Feature
	(properties
		x 169
		y 130
		z 0
		heading 0
		nsLeft 152
		nsTop 117
		nsBottom 144
		nsRight 186
 		description "the chair"
		lookStr 		"This office chair prevents you from accessing the
						intricate equipment below it."
		sightAngle 40
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbDo
				(TPrint "Don't move it.
					You don't need anything that's past the chair.")
			)
			(else
				(super doVerb: theVerb invItem &rest)
			)
		)
	)
)
(instance joystick of Feature
	(properties
		x 121
		y 160
		z 34
		heading 0
		nsLeft 108
		nsTop 126
		nsBottom 145
		nsRight 135
 		description "the joystick"
		lookStr 		"Wow! What a joystick!"
		sightAngle 40
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbDo
				(TPrint "You briefly picture yourself playing \"Zeliard.\"")
			)
			(else
				(super doVerb: theVerb invItem &rest)
			)
		)
	)
)
(instance circuitboard of Feature
	(properties
		x 68
		y 189
		z 9
		heading 0
		nsLeft 46
		nsTop 172
		nsBottom 189
		nsRight 91
 		description "the circuit board"
		lookStr 		"This looks like a giant circuit board."
		sightAngle 40
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbDo
				(TPrint "Be careful. You'll get shocked!")
			)
			(else
				(super doVerb: theVerb invItem &rest)
			)
		)
	)
)

(instance northBank of Feature
	(properties
		x 234
		y 89
		z 55
		heading 0
		nsLeft 155
		nsTop 1
		nsBottom 77
		nsRight 319
 		description "the laboratory tables"
		lookStr 		"These tables are filled with highly-expensive
						scientific equipment, all paid for with your tax dollars!"
		sightAngle 40
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbDo
				(TPrint "You really shouldn't mess around with the F.B.I.'s
					equipment.")
			)
			(else
				(super doVerb: theVerb invItem &rest)
			)
		)
	)
)

(instance southBank of Feature
	(properties
		x 159
		y 125
		heading 0
		nsLeft 0
		nsTop 118
		nsBottom 189
		nsRight 319
 		description "the laboratory tables"
		lookStr 		"These tables are filled with highly-expensive
						scientific equipment, all paid for with your tax dollars!"
		sightAngle 40
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbDo
				(TPrint "You really shouldn't mess around with the F.B.I.'s
					equipment.")
			)
			(else
				(super doVerb: theVerb invItem &rest)
			)
		)
	)
)


;ษอออออออออออออออออออป
;บ                   บ
;บ Sounds				บ
;บ                   บ
;ศอออออออออออออออออออผ

(instance soundFX of Sound
	(properties
		flags			1
	)
)


;ษอออออออออออออออออออป
;บ                   บ
;บ Talkers           บ
;บ                   บ
;ศอออออออออออออออออออผ

(instance twitTalker of Talker
	(properties
		name			"Commander Twit"
		view			vTwitTalker
		loop			3
		nsLeft		20
		nsTop			15
		viewInPrint	TRUE
	)
	(method (init)
		(= bust 	twitBust)
		(= eyes 	twitEyes)
		(= mouth twitMouth)
		(super init: &rest)
	)
)

(instance twitBust of Prop
	(properties
		view			vTwitTalker
		loop			1
		nsLeft		0
		nsTop			0
	)
)

(instance twitEyes of Prop
	(properties
		view			vTwitTalker
		loop			2
		nsLeft		0
		nsTop			0
		cycleSpeed  15
	)
)

(instance twitMouth of Prop
	(properties
		view			vTwitTalker
		loop			0
		nsLeft		11
		nsTop			47
	)
)

(instance desmondTalker of Talker
	(properties
		name			"Inspector Desmond"
		view			vDesmondTalker
		loop			3
		nsLeft		15
		nsTop			15
		viewInPrint	TRUE
	)
	(method (init)
		(= bust 	desmondBust)
		(= mouth desmondMouth)
		(= eyes desmondEyes)
		(super init: &rest)
	)
)
(instance desmondBust of Prop
	(properties
		view			vDesmondTalker
		loop			1
		nsLeft		0
		nsTop			0
	)
)

(instance desmondEyes of Prop
	(properties
		view			vDesmondTalker
		loop			2
		nsLeft		31
		nsTop			27
		cycleSpeed  50
	)
)

(instance desmondMouth of Prop
	(properties
		view			vDesmondTalker
		loop			0
		nsLeft		22
		nsTop			36
	)
)

(instance braPolygon of Polygon
	(properties
		type 		PBarredAccess
	)
)

(instance braPolygon2 of Polygon
	(properties
		type 		PBarredAccess
	)
)

