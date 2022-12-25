;**
;**	Logics for Room 440 -- Commander Twit's workshop, east CARTOON
;**
;**	LSL5:  Passionate Patti Does A Little Undercover Work
;**				Copyright 1991
;**				by Al Lowe
;**
;**	Programmed by Oliver Brelsford
;**
;**	Last Update:  September 9, 1991
;**

(script#		440)

(public
	rm440		0
)

(define		sLabSounds2			441)
(define		sKeyboardType		442)
(define		sLongFart			443)
(define		sBodyBulge1			444)
(define		sBodyBulge2			445)
(define		sBodyBulge3			446)
(define 		sBoing1				447)
(define 		sBoing2				448)
(define 		sBoing3				449)

(define	vRoom						440)
(define vPeltTalker				1440)
(define vTwitTalker				1430)

;(define	vTwit						432)
;	(define	lWalking				0-2)
;	(define	lGesturing			3-4)
;	(define	lTalking				5)

(define	pWorkshopEast			440)
(define	vWorkshopEast			440)
	(define	lSingleCells		0)
		(define	cDataMan			0)
		(define	cDataPak1		1)
		(define	cDataPak2		2)
	(define	lOfficeDoor			1)
	(define	lComputer			2)
(define	vPattiWorkshopEast	441)
	(define	lTakingDataMan		0)
(define	vTwitUsingComputer	442)
	(define	lFacingNorth 		0)
(define	vFartMan					444)
	(define	lPouringRedFluid	0)
	(define	lDrinkingBeaker	1)
	(define	lFacialTake			2)
	(define	lStomachExpansion	3)
	(define	lIgnition		  	4)
	(define	lLift-offShooting	5)

(define TWIT_X 133)

(instance rm440 of LLRoom
	(properties
		picture		440
		east			450
		west			430
		style			SCROLLLEFT
		lookStr		"The F.B.I. laboratory is filled with interesting
						equipment, most of which baffles you."
	)

	(method (init)
		(SetFFRoom 450)
		(LoadMany SOUND
			sDoorOpen
			sKeyboardType
		)
		(LoadMany VIEW
			vRoom
			vTwit
			vWorkshopEast
			vTwitUsingComputer
			vFartMan
			vTwitTalker
			vPeltTalker
		)
		(Load SCRIPT RANDCYC)
		(curRoom addObstacle:
			((Polygon new:)
				type: PBarredAccess,
				init:	0 189 0 140 192 140 231 130 251 138 305 125 293 113 271 102
						0 102 0 0 319 0 319 189,
				yourself
			)
		)
		(ego
			init:			,
			normalize:	,
			x:				20,
		)
		(if
			(not (Btst fFartMan))
			(Load SOUND
				sLongFart
				sBodyBulge1
				sBodyBulge2
				sBodyBulge3
				sBoing1
				sBoing2
				sBoing3
			)
			(fartman
				init:,
				setScript: sMix
			)
		)
		(HandsOff)
		(self setScript: sRoom)
		(super init:)
		(if
			(!= prevRoomNum 430)		;Teleporting
			(ego
				y:				118,
				view:			vPatti,
			)
		)
		(door
			init:,
			stopUpd:
		)
		(computer
			init:,
			stopUpd:
		)
		(duck
			setCycle:	Forward,
			init:			,
		)
		(machine init:)
		(contraption init:)
		(books init:)
		(desk init:)
		(southBank init:)
	)
	(method (dispose)
		(theMusic2 fade:)
		(super dispose:)
	)
)

;ษอออออออออออออออออออป
;บ                   บ
;บ Scripts				บ
;บ                   บ
;ศอออออออออออออออออออผ

(instance sRoom of Script
	(method (changeState newState)
		(switchto (= state newState)
			(
				(ego
					setMotion:	MoveTo 20 (ego y?) self
				)
			)
			(
				(twit
					init:,
					setCycle:  	StopWalk SAMEVIEW,
					setMotion:	MoveTo 40 100 self
				)
			)
			(
				(if (Btst fHasFBINumber)
					(HandsOn)
					(self dispose:)
				else
					(Bset fHasFBINumber)
					(= seconds 2)
				)
			)
			(
				(Say twitTalker
					"\"Patti, please step over here to the computer for a moment,\"
					says Twit.
					\"I need to take care of your indoctrination paperwork.\""
					#dispose:
				)
				(= seconds 3)
			)
			(
				(twit
					setMotion:		MoveTo TWIT_X 100 self
				)
				(ego
					cycleSpeed:		6,
					moveSpeed:		6,
					setMotion:		PolyPath 114 105
				)
			)
			(
				(= ticks 60)
			)
			(
				(twit
					view:				vTwitUsingComputer,
					setLoop:			0,
					setCycle:		RandCycle
				)
				(= ticks 60)
			)
			(
				(soundFX
					number:			sKeyboardType,
					loop:				-1,
					flags:			0,
					play:
				)
				(computer
					startUpd:,
					setCycle: 		RandCycle
				)
				(= seconds 4)
			)
			(
				(Say twitTalker
					"\"First, let's get you a contact telephone number.\""
					#dispose:
					#caller:		self
				)
			)
			(
				(= seconds 4)
			)
			(
				(Points ptsGotFBINum)
				(Say twitTalker
					"\"Ah, here's one:\n
					556-2779.\"____\n
					_______________\n
					_______________\n
					_______________\n
					_______________\n
					_______________\n
					_______________\n
					_______________\n"
					#dispose:
				)
				(= seconds 3)
			)
			(
				(Say twitTalker
					"\"Now let's issue you a computer account number.\""
					#dispose:
				)
				(= seconds 2)
			)
			(
				(Say twitTalker
					"\"Here we go.  How about BB-30,7.
					Now for your E-mail ID number.\""
					#dispose:
				)
				(= seconds 3)
			)
			(
				(Say twitTalker
					"\"65493756.
					And your official password is...\""
					#dispose:
				)
				(= seconds 2)
			)
			(
				(Say twitTalker
					"\"...MAKING_WOOPY. How appropriate.
					Now I'll apply for security clearance for you....\""
					#dispose:
				)
				(= seconds 2)
			)
			(
				(Say twitTalker
					"\"Granted.
					And you need some sort of codename;
					everybody who's anybody around here has a codename.\""
					#dispose:
				)
				(= seconds 2)
			)
			(
				(Say ego
					"\"How about `Agent 88'?\" you suggest."
					LOW
				)
				(= ticks 90)
			)
			(
				(Say twitTalker
					"\"Okay, let's see if that's taken.\""
					#dispose:
				)
				(= seconds 3)
			)
			(
				(Say twitTalker
					"\"Well, it appears it was used once,
					but that was years ago!\""
					#dispose:
					#caller:		self
				)
			)
			(
				(soundFX
					loop:				1,
					flags:			1,
					stop:
				)
				(computer
					setCycle:		0,
					stopUpd:
				)
				(twit
					view:				vTwit,
					setCycle:		StopWalk SAMEVIEW,
					setHeading:		180 self
				)
			)
			(
				(Say twitTalker
					"\"And now all you have left is your Official Departmental
					Medical Checkup and then we'll be ready to fit you with
					your `Safety First Field Locator Device.'\""
					#dispose:
				)
				(SetFFRoom 0)
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

(instance sToDoc of Script
	(method (doit)
		(super doit: &rest)
		(if
			(and
				state
				(not register)
				(< (ego x?) 231)
				(< (ego x?) (- (twit x?) 10))
			)
			(= register TRUE)
			(ego
				cycleSpeed:		6,
				moveSpeed:		6,
				setMotion:		PolyPath 231 108
			)
		)
	)
	(method (changeState newState)
		(switchto (= state newState)
			(
				(Say twitTalker
					"\"That's the door to Dr. Phil Hopian's office.
					Let's go in there now.
					He'll fit you with your `Field Locator Device.'\""
					#dispose:
					#caller:		self
				)
			)
			(;must be state 1
				(twit
					setLoop:			RELEASE,
					setCycle:  		StopWalk SAMEVIEW,
					setMotion:		MoveTo 260 100 self
				)
			)
			(
				(door setCycle:	EndLoop self)
				(soundFX
					number:			sDoorOpen,
					setLoop:			1,
					play:
				)
			)
			(
				(twit setCycle: 0)
				(Say twitTalker
					"\"Agent, uh, 88, I'd like you to meet our staff physician,
					Dr. Phil Hopian.
					Dr. Hopian, this is Agent 88!
					Don't worry about a thing, Patti:
					you're in good hands with Dr. Hopian!\""
					#dispose:
					#caller:		self
				)
			)
			(
				(Say peltTalker
					"\"Nice to meet you, Patti.
					Come inside and take off all your clothes!\""
					#dispose:
					#caller:		self
				)
			)
			(
				(ego
					setMotion:		MoveTo 286 106 self
				)
			)
			(
				(Say ego
					"\"Wow. Fast worker!\" you think."
					LOW
				)
				(= cycles 10)
			)
			(
				(ego setMotion:	MoveTo 330 (ego y?) self)
			)
			(
				(Say peltTalker
					"\"Assume the position!\" he barks."
					#dispose:
					#caller:		self
				)
			)
			(
				(= seconds 3)
			)
			(
				(Say peltTalker
					"\"Oops. Sorry. I used to be a field agent!
					Old habits, you know.\""
					#dispose:
					#caller:		self
				)
			)
			(
				(door
					setCycle:		BegLoop self
				)
				(soundFX
					number:			sDoorClose,
					setLoop:			1,
					play:
				)
			)
			(
				(TPrint "You enter his office,
					slip behind a screen and remove your clothes,
					replacing them with one of those wonderful hospital
					gowns that leave nothing to the imagination.")
				(TPrint "Oh, well. Things could be worse.
					At least the good doctor is also a good looker!")
				(theMusic2 fade:)
				(curRoom newRoom: 450)
			)
		)
	)
)

(instance sFart of Script
	(method (changeState newState)
		(switchto (= state newState)
			(
				(theGame setCursor: theCursor TRUE 500 500)
				(Bset fFartMan)
				(Say twitTalker
					"\"Ah, yes, Patti.
					I see you're interested in one of my pet projects,
					which I find fascinating.
					This man has developed a high-yield, space-age polymer,
					macrobiotic, flatulence-inducing powder.\""
					#dispose:
					#caller:		self
				)
			)
			(
				(Face ego fartman)
				(= seconds 2)
			)
			(
				(Say twitTalker
					"\"We feel it will be excellent for those times we find it
					necessary to destabilize a public leader by humiliating
					him during a major public appearance or state function!\""
					#dispose:
					#caller:		self
				)
			)
			(
				(= seconds 3)
			)
			(
				(fartman
					setScript:	0,
					setCycle: 	EndLoop self
				)
			)
			(
				(fartman
					setLoop:			lDrinkingBeaker,
					setCel:			0,
					cycleSpeed:		24,
					setCycle:		EndLoop self
				)
			)
			(
				(= seconds 3)
			)
			(
				(soundFX
					number:			sBodyBulge1,
					setLoop:			1,
					play:
				)
				(fartman
					setLoop:			lFacialTake,
					setCel:			0,
					setCycle:		EndLoop self
				)
			)
			(
				(= seconds 3)
			)
			(
				(soundFX
					number:			sBodyBulge2,
					play:
				)
				(fartman
					setLoop:			lStomachExpansion,
					setCel:			0,
					setCycle:		CycleTo 2 1 self
				)
			)
			(
				(= seconds 2)
			)
			(
				(fartman
					setCycle:		EndLoop,
					setPri:			pLCYAN,
					setMotion:		MoveTo 235 32 self
				)
				(soundFX
					number:			sBodyBulge3,
					play:
				)
			)
			(
				(soundFX
					number:			sBoing1,
					play:
				)
				(fartman
					setCel:			8,
					setMotion:		MoveTo 293 71 self
				)
			)
			(
				(soundFX
					number:			sBoing2,
					play:
				)
				(fartman
					setCel:			8,
					setMotion:		MoveTo 252 135 self
				)
			)
			(
				(soundFX
					number:			sBoing3,
					play:
				)
				(fartman
					setMotion:		MoveTo 159 14 self
				)
			)
			(
				(soundFX
					number:			sBoing2,
					play:
				)
				(fartman
					setMotion:		MoveTo 112 149 self
				)
			)
			(
				(soundFX
					number:			sBoing3,
					play:
				)
				(fartman
					setMotion:		MoveTo 114 17 self
				)
			)
			(
				(soundFX
					number:			sBoing1,
					play:
				)
				(fartman
					setMotion:		MoveTo 169 138 self
				)
			)
			(
				(soundFX
					number:			sBoing2,
					play:
				)
				(fartman
					setMotion:		MoveTo 240 11 self
				)
			)
			(
				(soundFX
					number:			sBoing3,
					play:
				)
				(fartman
					setMotion:		MoveTo 288 68 self
				)
			)
			(
				(soundFX
					number:			sBoing2,
					play:
				)
				(fartman
					setMotion:		MoveTo 252 135 self
				)
			)
			(
				(soundFX
					number:			sLongFart,
					setLoop:			-1,
					play:
				)
				(fartman
					setCel:			8,
					setLoop:			lIgnition,
					setMotion:		MoveTo 183 76 self
				)
			)
			(
				(fartman
					setLoop:			lLift-offShooting,
					setCycle:		Forward,
					setStep:			12 2,
					cycleSpeed:		6,
					setMotion:		MoveTo -50 (fartman y?) self
				)
			)
			(
				(fartman dispose:)
				(soundFX fade:)
				(= seconds 3)
			)
			(
				(Say twitTalker
					"\"Well, well. That seems to be progressing nicely!
					However, a lighter dosage may well be preferable!\""
					#dispose:
					#caller:		self
				)
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
		view			432
		loop			0
		cel			2
		x				-15
		y				100
		cycleSpeed 	6
		moveSpeed	6
		signal		ignrAct
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
				(< x TWIT_X)
				(> (- (ego x?) (self x?)) 20)
			)
			(self
				setMotion:	MoveTo (Min 133 (+ x 10)) y
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
					((not (Btst fFartMan))
						(HandsOff)
						(curRoom setScript: sFart)
					)
					(else
						(Say twitTalker
							"\"Whenever you're through exploring this area, Patti,
							please feel free to enter Dr. Hopian's office.\""
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

(instance door of Prop
	(properties
		view			440
		loop			1
		cel			0
		x				282
		y				99
		cycleSpeed  6
		description "Doctor Hopian's door"
		lookStr		"Through this door lies the offices of Dr. Phil Hopian,
						F.B.I. gynecologist."
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbDo
				(HandsOff)
				(curRoom setScript: sToDoc)
			)
			(else
				(super doVerb: theVerb invItem &rest)
			)
		)
	)
)

(instance computer of Prop
	(properties
		view			440
		loop			2
		cel			0
		x				133
		y				61
		signal		ignrAct
		cycleSpeed	6
		description "the computer"
		lookStr 		"If only you had paid attention when Larry chattered
						on and on about his computer.
						You know nothing about this computer and
						are sure you'll be unable to do anything with it."
		sightAngle 40
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbDo
				(TPrint "You are afraid to touch any computer keyboard that
					doesn't have MIDI built in!")
			)
			(else
				(super doVerb: theVerb invItem &rest)
			)
		)
	)
)

(instance fartman of Actor
	(properties
		view			444
		loop			0
		cel			0
		x				173
		y				157
		xStep			12
		yStep			12
		illegalBits 0
		signal		(| ignrAct ignrHrz)
		cycleSpeed	24
		moveSpeed	6
	)
	(method (doVerb theVerb invItem)
		(Points ptsFartman pfFartman)
		(HandsOff)
		(curRoom setScript: sFart)
	)
)

(instance sMix of Script
	(method (changeState newState)
		(switchto (= state newState)
			(
				(client
					setCycle:	CycleTo 1 1 self
				)
			)
			(
				(= seconds (Random 2 4))
			)
			(
				(client
					setCycle:	EndLoop self
				)
			)
			(
				(self init:)
			)
		)
	)
)

(instance duck of Prop
	(properties
		view			vRoom
		loop			3
		x				45
		y				167
		z				7
		priority		15
		signal		(| fixPriOn ignrAct)
		detailLevel	3
 		description "the duck"
		lookStr 		"If it drinks like a duck, and it quacks like a duck,
						it must be an experiment."
		sightAngle 40
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
		nsLeft		40
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
		cycleSpeed	30
	)
)

(instance twitMouth of Prop
	(properties
		view			vTwitTalker
		loop			0
		nsLeft		11
		nsTop			47
		cycleSpeed	8
	)
)

(instance peltTalker of Talker
	(properties
		name			"Dr. Phil Hopian"
		view			vPeltTalker
		loop			3
		nsLeft		50
		nsTop			15
		viewInPrint	TRUE
	)
	(method (init)
		(= bust 	peltBust)
		(= eyes 	peltEyes)
		(= mouth peltMouth)
		(super init: &rest)
	)
)

(instance peltBust of Prop
	(properties
		view			vPeltTalker
		loop			1
		nsLeft		0
		nsTop			0
	)
)

(instance peltEyes of Prop
	(properties
		view			vPeltTalker
		loop			2
		nsLeft		13
		nsTop			18
		cycleSpeed	30
	)
)

(instance peltMouth of Prop
	(properties
		view			vPeltTalker
		loop			0
		nsLeft		14
		nsTop			21
		cycleSpeed	8
	)
)

;ษอออออออออออออออออออป
;บ                   บ
;บ Features				บ
;บ                   บ
;ศอออออออออออออออออออผ


(instance machine of Feature
	(properties
		x 215
		y 47
		z 0
		heading 0
		nsLeft 170
		nsTop -1
		nsBottom 95
		nsRight 261
 		description "the machine"
		lookStr 		"This machine does something, you feel quite certain."
		sightAngle 40
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbDo
				(TPrint "You have not a clue as to what this machine may do!")
			)
			(else
				(super doVerb: theVerb invItem &rest)
			)
		)
	)
)
(instance contraption of Feature
	(properties
		x 114
		y 160
		z 34
		heading 0
		nsLeft 94
		nsTop 115
		nsBottom 137
		nsRight 134
 		description "the chemistry set"
		lookStr 		"This must be the source of the flatulence powder."
		sightAngle 40
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbDo
				(TPrint "You'll never be able to duplicate the F.B.I.'s
					\"secret formula\" of eleven natural herbs and spices.")
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
		y 166
		z 0
		heading 0
		nsLeft 0
		nsTop 144
		nsBottom 189
		nsRight 319
 		description "the workbench"
		lookStr 		"Lots of chemicals, beakers, and junk food wrappers
						fill this workbench."
		sightAngle 40
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbDo
				(TPrint "After seeing the results of Commander Twit's earlier
					experiments, you think it best not to touch anything
					here unless truly necessary.")
			)
			(else
				(super doVerb: theVerb invItem &rest)
			)
		)
	)
)

(instance books of Feature
	(properties
		x 41
		y 88
		z 30
		heading 0
		nsLeft 28
		nsTop 49
		nsBottom 68
		nsRight 55
 		description "the books"
		lookStr 		"These books are filled with Latin phrases.
						The only Latin phrases you know
						would get a sailor thrown out of a bar in Tijuana!"
		sightAngle 40
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbDo
				(TPrint "You don't have time to read a book now, Patti!")
			)
			(else
				(super doVerb: theVerb invItem &rest)
			)
		)
	)
)

(instance desk of Feature
	(properties
		x 81
		y 87
		z 17
		heading 0
		nsLeft 0
		nsTop 54
		nsBottom 88
		nsRight 162
 		description "the desk"
		lookStr 		"This desk doesn't contain any secret documents;
						they all were burned in the big fire last week!"
		sightAngle 40
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbDo
				(TPrint "You open the drawer and look inside.
					There's nothing in there but a few charred slips of paper.")
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

(instance soundFX of Sound)

