;**
;**	Logics for Room 620 -- Shill Building lobby
;**
;**	LSL5:  Passionate Patti Does A Little Undercover Work
;**				Copyright 1991
;**				by Al Lowe
;**
;**	Programmed by Steve Conrad
;**
;**	Last Update:  September 17, 1991
;**


(script#		620)

(public
	rm620		0
)


(define	pShillBuildingLobby			620)
(define	vShillBuildingLobby		620)
	(define	lElevatorDoor		0)
	(define	lElevatorDoorL		1)
(define	vGuard						622)
	(define	lFallingAsleep		0)
	(define	lDozing				1)
	(define	lTalkingGuard		2)
	(define	lRaisingTelephone	3)
	(define	lOnTelephone		4)
	(define	lWorkingPanel 		5)
(define vGuardTalker					1622)

(define	reverseState			1)
(define	hammerState				2)

(define	mShillMusak				620)
(define		sElevatorDing	621)
(define		sElevatorDoor	622)


(local
	guardStatus
		(enum
			gSleeping
			gAwake
		)
	datamanSolution
	faxSolution
	wrongDataman
	wrongFax
)


(instance rm620 of LLRoom
	(properties
		picture		pShillBuildingLobby
		south			600	;outside Shill
		north			640	;waiting room
	)

	(method (init)
		(switch prevRoomNum
			(south
				(ego
					init:			,
					normalize:	vPatti,
					x:				92
				)
				(theMusic
					number:		mShillMusak,
					flags:		TRUE,
					setLoop:		-1,
					play:
				)
			)
			(north
				(ego
					init:			,
					normalize:	vPatti,
					setPri:		8,
					posn:			57 141
				)
				(Bset fBeenInBaltimore)
				(curRoom setScript: sExitElevator)
			)
			(else
				(ego
					init:			,
					normalize:	vPatti,
					posn:			160 160,
					edgeHit:		0
				)
				(theMusic
					number:		mShillMusak,
					flags:		TRUE,
					setLoop:		-1,
					play:
				)
			)
		)
		(LoadMany PICTURE 630)
		(LoadMany SOUND
			sElevatorDing
			sElevatorDoor
		)
		(super init:)
		(board
			init:,
			approachVerbs:	verbLook verbDo verbUse
		)
		(guard
			init:				,
			approachVerbs:	verbTalk verbUse verbDo
		)
		(elevator
			init:,
			approachVerbs: verbDo verbUse
		)
		(elevatorLeft
			init:,
			approachVerbs: verbDo verbUse
		)
		(plant		init:)
		(mural	 	init:)
		(fStairs		init:)
		(fLamp1 		init:)
		(fLamp2 		init:)
		(fAshtray 	init:	,
			approachVerbs:	verbLook verbDo
		)
		(sconceA		init:)
		(sconceB		init:)
		(clock		init:)
		(breasts 	init:)
		(curRoom addObstacle:
			((Polygon new:)
				type: PBarredAccess,
				init:	0 0 319 0 319 189 123 189 123 187 313 187 313 159 224 159
						203 155 198 145 197 137 209 130 313 130 313 116 136 116
						132 119 122 120 114 120 61 150 61 151 62 158 58 163 51 164
						16 189 0 189,
				yourself
			)
		)
		(HandsOn)
	)

	(method (doit)
		(if (and (ego edgeHit?)
					(== guardStatus gAwake))
			(= guardStatus gSleeping)
			(guard
				setLoop:		0,
				setCel:		0,
				cycleSpeed: 30,
				setCycle:	EndLoop self
			)
		)
		(super doit:)
	)

	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbWalk
				(if (== curPic 630)
					(curRoom setScript: sReturnsFromBoard)
					TRUE
				)
			)
			(verbLook
				(if (== curPic 630)
					(TPrint "You are staring at the Shill Building's lobby
						directory. Evidently, the building's management ran
						short of those little push-in letters.")
				else
					(TPrint "The lobby of the Shill Building is very elegant.
						A wonderful mural covers the vast rear wall.
						A sleepy guard \"works\" at a security counter.")
				)
			)
			(else
				(super doVerb: theVerb invItem &rest)
			)
		)
	)

	(method (newRoom roomNumber)
		(super newRoom: roomNumber)
		(if (not (== roomNumber 640))
			(theMusic fade: 0 15 12 TRUE)
		)
	)
)


;*********** Scripts *************

(instance sExitElevator of Script
	(method (changeState newState)
		(switchto (= state newState)
			(
				(HandsOff)
				(= seconds 2)
			)
			(
				(elevatorDing play:)
				(= cycles 10)
			)
			(
				(elevatorDoor play:)
				(elevatorLeft setCycle: EndLoop)
				(elevator setCycle: EndLoop self)
			)
			(
				(elevatorDoor stop:)
				(ego
					setMotion:	MoveTo 89 150 self
				)
			)
			(
				(= seconds 2)
			)
			(
				(ego setPri: -1)
				(elevatorDoor play:)
				(elevatorLeft setCycle: BegLoop)
				(elevator setCycle: BegLoop self)
			)
			(
				(elevatorDoor stop:)
				(elevator stopUpd: TRUE)
				(HandsOn)
				(self dispose:)
			)
		)
	)
);sExitElevator

(instance sGuardWakes of Script
	(method (changeState newState)
		(switchto (= state newState)
			(
				(HandsOff)
				(guard
					setLoop:		lFallingAsleep,
					setCel:		(guard lastCel?),
					cycleSpeed:	12,
					setCycle:	BegLoop self
				)
			)
			(
				(= guardStatus gAwake)
				(Say guardTalker "\"Yes?\" mumbles the sleepy guard,
					\"You need somethin'?\""
					#dispose:
				)
				(HandsOn)
				(Delay 6 REALSECONDS self)
			)
			(
				(HandsOff)
				(guard						  				;guard goes back to sleep
					setLoop:		lFallingAsleep,		;if not talked to further
					setCel:		0,
					setCycle:	EndLoop self
				)
			)
			(
				(HandsOn)
				(guard setScript: sSleeping)
				(= guardStatus gSleeping)
				(self dispose:)
			)
		)
	)
);sGuardWakes

(instance sGuardSleeps of Script
	(method (changeState newState)
		(switchto (= state newState)
			(
				(HandsOff)
				(= guardStatus gSleeping)
			 	(TPrint "As you begin to speak,
					you realize you don't know the room number of
					Reverse Biaz's office."
				)
				(Say ego "\"Nice outfit you're wearing, Mister!\"")
				(self cue:)
			)
			(
				(Say guardTalker "\"Honey, if you don't know where you're going,
					how am I supposed to know?\""
					#dispose:
				)
				(guard
					setLoop:		lFallingAsleep,
					setCel:		0,
					setCycle:	EndLoop self
				)
			)
			(
				(guard setScript: sSleeping)
				(= guardStatus gSleeping)
				(HandsOn)
				(self dispose:)
			)
		)
	)
);sGuardSleeps

(instance sGuardApproves of Script
	(method (changeState newState)
		(switchto (= state newState)
			(
				(HandsOff)
				(cond
					(datamanSolution
						(Say ego "\"Excuse me,\" you say to the sleepy guard,
							\"Would you please direct me to the offices
							shown on this LCD readout?\"")
					)
					(faxSolution
						(Say ego "\"Excuse me, Sir,\" you say to the nappy guard,
							\"I can see you're quite busy,
							but could you help me find the office shown
							on the fax?\"")
					)
					(wrongDataman
						(Say ego "\"Excuse me,\" you say to the narcoleptic guard,
							\"Would you please direct me to the office
							shown on this LCD readout?\"")
					)
					(wrongFax
						(Say ego "\"Excuse me, Sir,\" you say to the worn-out guard,
							\"I can see you're quite busy,
							but could you help me find the office shown
							on this fax?\"")
					)
					(else
						(Say ego "\"Excuse me,\" you say to the well-rested guard.
							\"I'm here to see Mr. Biaz in Room 900.\"")
					)
				)
				(self cue:)
			)
			(
				(cond
					((Btst fGuardKnows)
						(Say guardTalker "\"Lady, I already told you:
							Reverse Biaz says,
							`you blew the gig when you split'--whatever
 							that means!\""
							#dispose:
						)
						(HandsOn)
						(guard setScript: sBackToSleep)
						(self dispose:)
					)
					(datamanSolution
						(Say guardTalker "\"Hey, pretty cool!
							I was thinkin' about gettin' one of these for
							the little missus!
							Now, let's see... `Biaz?' `Suite 900?'
							Sure, no problem.
							I'll summon the elevator for you right now!\""
							#dispose:
						)
 						(= datamanSolution FALSE)
;SRC						;Make total points the same,
;SRC						;whether user has seen board or not
;SRC						(if (Btst pfSeeOffice)
;SRC						  	(Points (- ptsDatamanSol ptsSeeOffice) pfDatamanSol)
;SRC						else
						(Points ptsDatamanSol pfDatamanSol)
;SRC						)
					)
					(wrongDataman
						(Say guardTalker "\"Hey, pretty cool!
							I was thinkin' about gettin' one of these for
							the little missus!
							Now, let's see... `P. C. Hammer?'
							I'm sorry,
							but there's nobody by that name in this building.\""
							#dispose:
						)
						(= wrongDataman FALSE)
						(HandsOn)
						(guard setScript: sBackToSleep)
						(self dispose:)
					)

					(faxSolution
						(Say guardTalker "\"Kinda flimsy paper, ain't it?
							Well, let's see here...
							You know, it's hard to read this stuff;
							the text is all fuzzy-looking.
							Oh, here it is... `Biaz?'
							Why, he's in Suite 900.
							Sure, no problem.
							I'll summon the elevator for you right now!\""
							#dispose:
						)
						(= faxSolution FALSE)
;SRC						;Make total points the same,
;SRC						;whether user has seen board or not
;SRC						(if (Btst pfSeeOffice)
;SRC						  	(Points (- ptsFaxSol ptsSeeOffice) pfFaxSol)
;SRC						else
						(Points ptsFaxSol pfFaxSol)
;SRC						)
					)
					(wrongFax
						(Say guardTalker "\"Kinda flimsy paper, ain't it?
							Well, let's see here...
							You know, it's hard to read this stuff;
							the text is all fuzzy-looking.
							Hmm... `P. C. Hammer?'
							Nah, they only let us use screwdrivers and pliers
							on the computers here!\""
							#dispose:
						)
						(= wrongFax FALSE)
						(HandsOn)
						(guard setScript: sBackToSleep)
						(self dispose:)
					)
					(else
						(Say guardTalker "\"Huh? Oh, right.
							Give me just a second while I make sure he's in,\"
							sighs the overworked guard."
							#dispose:
						)
						(Bset fDirectorySol)
					)
				)
				(self cue:)
			)
			(
				(guard
					cycleSpeed:	12,
					setLoop:		lWorkingPanel,
					setCel:		0,
					setCycle:	EndLoop self
				)
			)
			(
				(guard
					setLoop:		lRaisingTelephone,
					setCel:		0,
					setCycle:	EndLoop self
				)
			)
			(
				(guard
					setLoop:		lOnTelephone,
					setCel:		0,
					setCycle:	ForwardCounter 2 self
				)
			)
			(
				(= seconds 2)
			)
			(
				(guard
					setLoop:		lRaisingTelephone,
					setCel:		(guard lastCel?),
					setCycle:	BegLoop self
				)
			)
			(
				(guard
					setLoop:		lWorkingPanel,
					setCycle:	EndLoop self
				)
			)
			(
				(if (Btst fWarned)
					(Say guardTalker "\"I'm sorry,
						but he says he's found someone else.
						Take a hike!\""
						#dispose:
					)
					(Bset fGuardKnows)
					(HandsOn)
					(guard setScript: sBackToSleep)
					(self dispose:)
				else
					(Say guardTalker "\"Okay, honey.
						He's waiting for you upstairs.
						I'm calling for the elevator for you right now.\""
						#dispose:
					)
					(guard
						cycleSpeed:	18,
						setLoop:		lFallingAsleep,
						setCel:		0,
						setCycle:	EndLoop self
					)
				)
			)
			(
				(guard setScript: sSleeping)
				(= seconds 3)
			)
			(
				(elevatorDing play:)
				(= cycles 10)
			)
			(
				(elevator setCycle: EndLoop self)
				(elevatorLeft setCycle: EndLoop)
				(elevatorDoor play:)
			)
			(
				(elevatorDoor stop:)
				(ego
					setMotion: 	MoveTo 96 146 self
				)
			)
			(
				(ego
					setPri:		9,
					setMotion:	MoveTo 58 136 self
				)
			)
			(
				(ego setHeading: 180 self)
			)
			(
				(elevatorDoor play:)
				(elevator
					setCycle: BegLoop	self
				)
				(elevatorLeft
					setCycle: BegLoop
				)
			)
			(
				(elevatorDoor stop:)
				(curRoom newRoom: 640)
				(self dispose:)
			)
		)
	)
);sGuardApproves

(instance sUsesBoard of Script
	(method (changeState newState)
		(switchto (= state newState)
			(
				(User canControl: FALSE)
				(theIconBar disable: computerIcon)
				(cast eachElementDo: #hide:)
				(plant 			dispose:)
				(mural	 		dispose:)
				(fStairs			dispose:)
				(fLamp1 			dispose:)
				(fLamp2 			dispose:)
				(fAshtray 		dispose:)
				(sconceA			dispose:)
				(sconceB			dispose:)
				(clock			dispose:)
				(breasts 		dispose:)

				(businessOne 	init:)
				(businessTwo 	init:)
				(businessThree init:)
				(businessFour 	init:)
				(businessFive 	init:)
				(businessSix 	init:)
				(businessSeven init:)
				(businessEight init:)
				(curRoom drawPic: 630)
				(SetupExit TRUE)

				(Display "__allowe____ Supplies________827"
					#p_at: 86 67
					#p_color: colWhite
					#p_font:	smallFont
				)
				(Display "Hell___ Industries___________301"
					#p_at: 88 79
					#p_color: colWhite
					#p_font:	smallFont
				)
				(Display "__uck You___________________702"
					#p_at: 88 91
					#p_color: colWhite
					#p_font:	smallFont
				)
				(Display "des Rever Records_________900"
					#p_at: 89 103
					#p_color: colWhite
					#p_font:	smallFont
				)
				(Display "Shi___ Heads________________400"
					#p_at: 90 115
					#p_color: colWhite
					#p_font:	smallFont
				)
				(Display "AeroDork Corp._____________501"
					#p_at: 90 127
					#p_color: colWhite
					#p_font:	smallFont
				)
				(Display "Big Butt____s_Inc.__________251"
					#p_at: 92 139
					#p_color: colWhite
					#p_font:	smallFont
				)
				(Display "Hard Di__k Service________601"
					#p_at: 93 151
					#p_color: colWhite
					#p_font:	smallFont
				)
				(board dispose:)
				(self dispose:)
			)
		)
	)
);sUsesBoard

(instance sReturnsFromBoard of Script
	(method (changeState newState)
		(switchto (= state newState)
			(
				(HandsOn)
				(theIconBar enable: computerIcon)
				(curRoom drawPic: (curRoom picture?))
				(cast eachElementDo: #show:)
				(businessOne dispose:)
				(businessTwo dispose:)
				(businessThree dispose:)
				(businessFour dispose:)
				(businessFive dispose:)
				(businessSix dispose:)
				(businessSeven dispose:)
				(businessEight dispose:)
				(plant 		init:)
				(mural	 	init:)
				(fStairs		init:)
				(fLamp1 		init:)
				(fLamp2 		init:)
				(fAshtray 	init:)
				(sconceA		init:)
				(sconceB		init:)
				(clock		init:)
				(breasts 	init:)
				(board
					init:,
					approachVerbs:	verbLook verbDo verbUse
				)
				(SetupExit FALSE)
			)
		)
	)
);sReturnsFromBoard

(instance sBackToSleep of Script
	(method (changeState newState)
		(switchto (= state newState)
			(
				(= guardStatus gSleeping)
				(guard
					setLoop:		lFallingAsleep,
					setCel:		0,
					setCycle:	EndLoop self
				)
			)
			(
				(guard setScript: sSleeping)
			)
			(
				(self dispose:)
			)
		)
	)
);sBackToSleep

(instance sSleeping of Script
	(method (changeState newState)
		(switchto (= state newState)
			(
				(guard
					setLoop:		lDozing,
					setCel:		0,
					cycleSpeed:	30,
					setCycle:	EndLoop self
				)
			)
			(
				(= cycles 20)
			)
			(
				(guard
					setCycle:	BegLoop self
				)
			)
			(
				(= cycles 20)
			)
			(
				(self changeState: 0)
			)
		)
	)
);sSleeping


;************* Objects **************

(instance guard of Prop
	(properties
		view			vGuard
		loop			lDozing
		x				274
		y				124
		priority		11
		signal		(| fixPriOn ignrAct)
		approachX	244
		approachY	166
		description "the guard"
		sightAngle 	40
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbTalk
				(if (== guardStatus gSleeping)
					(guard setScript: sGuardWakes)
				else
					(ll5Timer dispose:, delete:)
					(if (Btst fSeenRBOffice)
						(curRoom setScript: sGuardApproves)
					else
						(guard setScript: sGuardSleeps)
					)
				)
			)
			(verbUse
				(if (== guardStatus gAwake)
					(ll5Timer dispose:, delete:)
					(switch invItem
						(iDataMan
							(cond
								((== ((Inventory at: iDataMan) state?) reverseState)
									(= datamanSolution TRUE)
									(curRoom setScript: sGuardApproves)
								)
								((== ((Inventory at: iDataMan) state?) hammerState)
									(= wrongDataman TRUE)
									(curRoom setScript: sGuardApproves)
								)
								(else
									(Say guardTalker "\"I'm sorry, lady,
										but that don't mean nothing to me.\""
										#dispose:
									)
									(guard setScript: sBackToSleep)
								)
							)
						)
						(iFax1ReverseBiaz
							(= faxSolution TRUE)
							(curRoom setScript: sGuardApproves)
						)
						(iFax2PCHammer
							(= wrongFax TRUE)
							(curRoom setScript: sGuardApproves)
						)
						(else
							(super doVerb: theVerb invItem &rest)
							(guard setScript: sBackToSleep)
						)
					)
				else
					(TPrint "Aren't guards just so precious when they're asleep?")
				)
			)
			(verbLook
				(if (== guardStatus gSleeping)
					(TPrint "The guard is sleeping on the job.")
				else
					(TPrint "The guard would prefer to be sleeping on the job.")
				)
			)
			(verbDo
				(if (== guardStatus gAwake)
					(Say guardTalker "\"Oh, Ma'am,\" sighs the guard,
						\"that feels good!\""
						#dispose:
					)
				else
					(TPrint "The guard grumbles under his breath and goes back to
						sleep.")
				)
			)
			(verbZipper
				(TPrint "Geez, Patti! Try to trim back that attitude, okay?")
			)
			(else
				(super doVerb: theVerb invItem &rest)
			)
		)
	)
	(method (init)
		(super init:)
		(self
			setScript:		sSleeping
		)
	)
)

(instance elevator of Prop
	(properties
		view			vShillBuildingLobby
		loop			lElevatorDoor
		x				61
		y				148
		priority		4
		signal		(| fixPriOn ignrAct stopUpdOn)
		cycleSpeed 	12
		description "the elevator"
		sightAngle 	40
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbLook
				(TPrint "An elevator awaits you.
					But how do you access it?
					There are no buttons on the wall.")
			)
			(verbDo
				(TPrint "You push your hand against the elevator door,
					but it does no good.
					And there are no buttons nearby to push.")
			)
			(else
				(super doVerb: theVerb invItem &rest)
			)
		)
	)
)
(instance elevatorLeft of Prop
	(properties
		view			vShillBuildingLobby
		loop			lElevatorDoorL
		x				45
		y				152
		priority		9
		signal		(| fixPriOn ignrAct stopUpdOn)
		cycleSpeed 	12
		description "the elevator"
		sightAngle 	40
	)
	(method (doVerb theVerb invItem)
		(elevator doVerb: theVerb invItem &rest)
	)
)

(instance elevatorDing of Sound
	(properties
		number	sElevatorDing
	)
)
(instance elevatorDoor of Sound
	(properties
		number	sElevatorDoor
	)
)


;********** Features ***********

(instance board of Feature
	(properties
		x 				10
		y 				131
		nsLeft 		9
		nsTop 		98
		nsBottom 	165
		nsRight 		38
		approachX	61
		approachY	178
 		description "the Shill Building's directory"
		sightAngle 	40
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbLook
				(curRoom setScript: sUsesBoard)
			)
			(verbDo
				(curRoom setScript: sUsesBoard)
			)
			(else
				(super doVerb: theVerb invItem &rest)
			)
		)
	)
)
(instance businessOne of Feature
	(properties
		x 157
		y 71
		nsLeft 93
		nsTop 66
		nsBottom 76
		nsRight 221
 		description "the directory"
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbLook
				(TPrint "Halloween Supplies is Baltimore's largest local supplier
					of Halloween goods and party supplies."
					HIGH
				)
			)
			(verbWalk
				(curRoom setScript: sReturnsFromBoard)
				TRUE
			)
			(else
				(super doVerb: theVerb invItem &rest)
			)
		)
	)
)
(instance businessTwo of Feature
	(properties
		x 156
		y 82
		nsLeft 92
		nsTop 78
		nsBottom 86
		nsRight 221
 		description "the directory"
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbLook
				(TPrint "Hello Industries is one of the world's leading
					producers of greeting cards."
					HIGH
				)
			)
			(verbWalk
				(curRoom setScript: sReturnsFromBoard)
				TRUE
			)
			(else
				(super doVerb: theVerb invItem &rest)
			)
		)
	)
)
(instance businessThree of Feature
	(properties
		x 154
		y 94
		nsLeft 86
		nsTop 90
		nsBottom 99
		nsRight 223
 		description "the directory"
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbLook
				(TPrint "Duck Youth is a wonderful public service organization
					that specializes in familiarizing inner-city youngsters
					with all varieties of barnyard and aquatic fowl."
					HIGH
				)
			)
			(verbWalk
				(curRoom setScript: sReturnsFromBoard)
				TRUE
			)
			(else
				(super doVerb: theVerb invItem &rest)
			)
		)
	)
)
(instance businessFour of Feature
	(properties
		x 153
		y 106
		nsLeft 85
		nsTop 102
		nsBottom 111
		nsRight 222
 		description "the directory"
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbLook
				(Bset fSeenRBOffice)
				(Points ptsSeeOffice pfSeeOffice)
				(TPrint "That's it, Patti!
					\"des Rever Records\" is located in Room 900."
					HIGH
				)
			)
			(verbDo
				(Bset fSeenRBOffice)
				(Points ptsSeeOffice pfSeeOffice)
				(TPrint "That's it, Patti!
					\"des Rever Records\" is located in Room 900."
					HIGH
				)
			)
			(verbWalk
				(curRoom setScript: sReturnsFromBoard)
				TRUE
			)
			(else
				(super doVerb: theVerb invItem &rest)
			)
		)
	)
)
(instance businessFive of Feature
	(properties
		x 155
		y 118
		nsLeft 88
		nsTop 114
		nsBottom 123
		nsRight 222
 		description "the directory"
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbLook
				(TPrint "Ship Heads, Inc. provides the finest commodes available
					for ocean-going vessels."
					HIGH
				)
			)
			(verbWalk
				(curRoom setScript: sReturnsFromBoard)
				TRUE
			)
			(else
				(super doVerb: theVerb invItem &rest)
			)
		)
	)
)
(instance businessSix of Feature
	(properties
		x 155
		y 130
		nsLeft 87
		nsTop 126
		nsBottom 135
		nsRight 224
 		description "the directory"
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbLook
				(TPrint "AeroDork is a very high-class airline.
					So high-class their offices are forbidden to the public."
					HIGH
				)
			)
			(verbWalk
				(curRoom setScript: sReturnsFromBoard)
				TRUE
			)
			(else
				(super doVerb: theVerb invItem &rest)
			)
		)
	)
)
(instance businessSeven of Feature
	(properties
		x 156
		y 142
		nsLeft 89
		nsTop 138
		nsBottom 147
		nsRight 224
 		description "the directory"
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbLook
				(TPrint "Big Buttons, Inc. supplies buttons of all sizes for
					every occasion: political campaigns, birthday parties,
					fund raisers, software company identification, etc."
					HIGH
				)
			)
			(verbWalk
				(curRoom setScript: sReturnsFromBoard)
				TRUE
			)
			(else
				(super doVerb: theVerb invItem &rest)
			)
		)
	)
)
(instance businessEight of Feature
	(properties
		x 156
		y 154
		nsLeft 89
		nsTop 150
		nsBottom 159
		nsRight 223
 		description "the directory"
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbLook
				(TPrint "Hard Disk Services can handle all of your computer hardware
					needs--from correcting corrupted database files
					to undeleting sensitive information,
					to recovering formatted hard drives."
					HIGH
				)
			)
			(verbWalk
				(curRoom setScript: sReturnsFromBoard)
				TRUE
			)
			(else
				(super doVerb: theVerb invItem &rest)
			)
		)
	)
)
(instance plant of Feature
	(properties
		x 120
		y 95
		nsLeft 104
		nsTop 36
		nsBottom 114
		nsRight 137
 		description "the plant"
		lookStr 		"This plant is obviously well-maintained,
						but superfluous to your mission."
	)
)
(instance mural of Feature
	(properties
		x 230
		y 47
		nsLeft 142
		nsTop 0
		nsBottom 95
		nsRight 319
 		description "the mural"
		lookStr 		"It is quite an interesting mural."
	)
)
(instance fStairs of Feature
	(properties
		x 229
		y 164
		z 0
		heading 0
		nsLeft 139
		nsTop 139
		nsBottom 189
		nsRight 319
		onMeCheck cLMAGENTA
 		description "the staircase"
		lookStr 		"A staircase winds its way upwards, but a velvet rope
						across the bottom indicates it's not to be used under
						penalty of law.
						It looks like the elevator is your only hope, Patti."
		sightAngle 40
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbDo
				(TPrint "Since the staircase is presently being renovated,
					you are unable to use it.
					Besides, you know how much you hate climbing stairs in heels!")
			)
			(else
				(super doVerb: theVerb invItem &rest)
			)
		)
	)
)
(instance fLamp1 of Feature
	(properties
		x 131
		y 163
		z 0
		heading 0
		nsLeft 124
		nsTop 139
		nsBottom 188
		nsRight 138
 		description "the lamp"
		lookStr 		"A lovely Art Deco lamp rises above the staircase."
		sightAngle 40
	)
)
(instance fLamp2 of Feature
	(properties
		x 294
		y 118
		z 0
		heading 0
		nsLeft 287
		nsTop 98
		nsBottom 138
		nsRight 302
 		description "the lamp"
		lookStr 		"A lovely Art Deco lamp rises above the staircase."
		sightAngle 40
	)
)
(instance fAshtray of Feature
	(properties
		x 51
		y 150
		z 0
		approachX	70
		approachY	160
		heading 0
		nsLeft 46
		nsTop 141
		nsBottom 160
		nsRight 56
 		description "the ashtray"
		lookStr 		"Carefully examining the ashtray for a disco pass,
						for a brief moment you think you're playing
						\"Leisure Suit Larry 1:  In the Land of the Lounge Lizards.\"
						Of course, you're not."
		sightAngle 40
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbDo
				(TPrint "Carefully examining the ashtray for a disco pass,
					for a brief moment you think you're playing
					\"Leisure Suit Larry 1:  In the Land of the Lounge Lizards.\"
					Of course, you're not.")
			)
			(else
				(super doVerb: theVerb invItem &rest)
			)
		)
	)
)

(instance sconceA of Feature
	(properties
		x 21
		y 81
		z 0
		heading 0
		nsLeft 5
		nsTop 77
		nsBottom 86
		nsRight 37
 		description "the sconce"
		lookStr 		"My, the Shill Building has tasteful sconces."
		sightAngle 40
	)
)

(instance sconceB of Feature
	(properties
		x 94
		y 71
		z 0
		heading 0
		nsLeft 85
		nsTop 69
		nsBottom 74
		nsRight 103
 		description "the sconce"
		lookStr 		"My, the Shill Building has tasteful sconces."
		sightAngle 40
	)
)

(instance clock of Feature
	(properties
		x 230
		y 112
		z 0
		heading 0
		nsLeft 215
		nsTop 107
		nsBottom 125
		nsRight 245
 		description "the clock"
		lookStr 		"A lovely Deco time clock is built into the end of the
						Security Guard's counter for those employees still
						paid by the hour."
		sightAngle 40
	)
)

(instance breasts of Feature
	(properties
		x 191
		y 199
		z 80
		heading 0
		nsLeft 182
		nsTop 34
		nsBottom 44
		nsRight 201
 		description "the mural"
		lookStr 		"What? Patti!
						When did you develop an interest in other women?"
		sightAngle 40
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbDo
				(TPrint "You would, but you have no ladder!")
			)
			(else
				(super doVerb: theVerb invItem &rest)
			)
		)
	)
)

;*************** Talker ****************

(instance guardTalker of Talker
	(properties
		name			"The Guard"
		view			vGuardTalker
		loop			3
		nsLeft		35
		nsTop			15
		viewInPrint	TRUE
	)
	(method (init)
		(= bust 	guardBust)
		(= eyes 	guardEyes)
		(= mouth guardMouth)
		(super init: &rest)
	)
)
(instance guardBust of Prop
	(properties
		view		vGuardTalker
		loop		1
		nsLeft	0
		nsTop		0
	)
)
(instance guardEyes of Prop
	(properties
		view			vGuardTalker
		loop			2
		nsLeft		21
		nsTop			32
		cycleSpeed  70
	)
)
(instance guardMouth of Prop
	(properties
		view		   vGuardTalker
		loop			0
		nsLeft		15
		nsTop			36
		cycleSpeed 	5
	)
)

