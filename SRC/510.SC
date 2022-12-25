;**
;**	Logics for Room 510 -- Hard Disk Cafe lobby
;**
;**	LSL5:  Passionate Patti Does A Little Undercover Work
;**				Copyright 1991
;**				by Al Lowe
;**
;**	Programmed by Oliver Brelsford
;**
;**	Last Update:  September 10, 1991
;**

;NOTE:	tapeReader's y has to be greater than larry's
;			when larry is standing in front of same

(script#		510)

(public
	rm510		0
)

(define MUSICPRINT #at:	100 10)

(define t_approachX 			139)
(define t_approachY 			77)

(define vMaitreDWalk 		515)
(define vMaitreD 				512)
(define vWomanOnPhone      513)
(define vMusicBox				514)

(define vHardDiskLobby 		510)
(define vMusicBoxInset		511)
	(define lUsingPhone 		2)

(define	mMusicBox			510)
(define	sMachineFX			511)
(define  sPaperRip			512)
(define	mHardRock1			500)
(define	mHardRock2			501)
(define	mHardRock3			502)
(define	sKeyboardType		442)


(define	vMaitreDTalker		1512)

;maitreD loops:

(define lMDTalking	 	0)
(define lUsingMachine 	1)

(define MAKETAPE 			20)

(local
	talkCounter
	tippedHost     	;for testing
	tapeState
	forceAtest			;it's ONE way!
	okToMakeTape
	tapeHere
)

(instance rm510 of LLRoom
	(properties
		picture		510
		north			525
		south			500
		lookStr 		"The lobby of \"The Hard Disk Cafe\" is filled with a
						tremendously exciting (to nerds) display of ancient
						computer memorabilia.
						Your pulse quickens just looking around the place."
	)

	(method (init)

		(LoadMany VIEW
			vMaitreDWalk 		 	;515
			vMaitreD 				;512
			vWomanOnPhone        ;513
			vMusicBox				;514
			vMusicBoxInset			;511
			vHardDiskLobby 		;510
		)
		(Load SOUND
			mMusicBox
		)

		(LDoor
			init:,
			approachVerbs: verbDo
		)

		(switch prevRoomNum
			(north
				(ego
					edgeHit:		0,
					init:			,
					normalize:	,
				)
			)

			(535
				(HandsOn)

				;**	(Bset fSeenMM)		;we saw Michelle walk by in room 525
				(Bset fMCloseUp)	;close up of Michelle Milken

				((Inventory at: iMembershipTape) state: tDone)		;important!

				(ego
					x:					(door approachX?),
					y:					(door approachY?),
					init:				,
					normalize:		,
					edgeHit: 		0,
				)
			)

			(530	;in case they fast forward, this code needs to be identical to 535's.
				(HandsOn)

				;**	(Bset fSeenMM)		;we saw Michelle walk by in room 525
				(Bset fMCloseUp)	;close up of Michelle Milken

				((Inventory at: iMembershipTape) state: tDone)		;important!

				(ego
					x:					(door approachX?),
					y:					(door approachY?),
					init:				,
					normalize:		,
					edgeHit: 		0,
				)
			)

			(520
				(ego
					init,
					normalize:,
					posn:			137 77,
				)
				(if (ego has:iMembershipTape)
					(HandsOff)
					(curRoom setScript: sExitNorth)
				else
					(HandsOn)
				)
			)

			(500
				(ego
					init,
					normalize,
					posn: 334 219
				)
				(theMusic fade: 127 10 10 FALSE)
				(self setScript: sEnterSouth)
			)

			(else		;tp
				(= larryLoc NewYork)
				(if (not prevRoomNum)
					(WrapMusic firstSound: mHardRock1, lastSound: mHardRock3)
					(theMusic number: mHardRock1, play: WrapMusic, flags: TRUE, setLoop: 1)
				)
				(ego
					init,
					normalize,
					posn: 334 219
				)
				(self setScript: sEnterSouth)
			)
		)

		(door	init:)

		(super init:)

		(if
			(and
				(!= prevRoomNum 535)
				(not (Btst fMCloseUp))
			)
			(self addObstacle:
				((Polygon new:)
					type: PBarredAccess,
					init:	0 0 319 0 319 114 270 99 258 104 245 100 251 90 238 87 218 94
							208 82 218 71 215 70 210 75 172 65 169 58 146 59 147 70
							106 82 81 96 92 100 98 108 84 114 62 113 50 108 29 108 4 125
							6 186 270 187 272 189 0 189,
					yourself
				)
			)

			(woman
				init:,
				addToPic:
			)
			(womansMouth
				init:,
				ignoreActors:	TRUE,
				setScript:		sMouth
			)
			(phone
				init:,
				setCel:	1,
				stopUpd:
			)
		else
			(if (== prevRoomNum 535)
				(WrapMusic firstSound: mHardRock1, lastSound: mHardRock3)
				(theMusic number: mHardRock1, play: WrapMusic, flags: TRUE, setLoop: 1)
			)

			(self addObstacle:
				((Polygon new:)
					type: PBarredAccess,
					init:	0 0 319 0 319 114 270 99 258 104 245 100 251 90 238 87 218 94
							208 82 218 71 215 70 210 75 172 65 169 58 146 59 147 70
							109 83 81 96 92 100 98 108 84 114 62 113 50 108 29 108 4 125
							4 187 270 187 272 189 0 189,
					yourself
				)
			)
			(phone
				init:,
				setCel:	0,
				approachVerbs: verbDo verbTalk,
				stopUpd:
			)
		)

		(if (== ((Inventory at: iMembershipTape) owner?) 510)
			(tape
				init:,
				setCel: LASTCEL,
			)
		)
		(if
			(== ((Inventory at: iMembershipTape) state?) tNone)
			(Load SOUND
				sPaperRip
				sKeyboardType
			)
		)


		(self addObstacle:
			((Polygon new:)
				type: PBarredAccess,
				init:	50 168 44 161 52 156 149 157 157 163 145 169 77 169,
				yourself
			)
			((Polygon new:)
				type: PBarredAccess,
				init:	69 129 80 124 131 124 141 130 130 135 79 135,
				yourself
			)
			((Polygon new:)
				type: PBarredAccess,
				init:	120 110 129 103 173 103 186 109 179 114 173 117 126 117,
				yourself
			)
			((Polygon new:)
				type: PBarredAccess,
				init:	142 147 140 141 148 135 201 135 213 143 204 150 188 152
						159 152,
				yourself
			)
		)

		(musicBox
			init,
			approachVerbs: verbDo verbUse,
			stopUpd:
		)

		;init features

		(maitreD
			init,
			approachVerbs: verbTalk verbDo verbUse
		)

		(tapeReader
			init:,
			approachVerbs: verbDo verbUse
		)

		(diskDrive 			init:)
		(pet 					init:)
		(appleII 			init:)
		(cashRegister 		init:)
		(monitor   			init:)
		(atari400  			init:)
		(tapeDrive 			init:)
		(cactus1   			init:)
		(cactus2   			init:)
		(pianoRoll     	init:)
		(punchCardMachine init:)
		(podium				init:)
		(desk					init:)
		(bullBoard			init:)
		(chair				init:)

	);init


	(method (notify what)
		(switch what
			(-1
				(TPrint "You change your mind and hang up the phone.")
			)
			(4668
				(if (not (Btst fCalledLimo))
					(Bset fCalledLimo)
					(Points ptsCalledChecker pfCalledChecker)
					(TPrint "\"Hello,\" says the voice on the telephone,
						\"You've reached the `Fabulous Checker Limo Company.'
						I hope you're having a nice day!\"")
					(Say ego "\"Hello! This is Larry; Larry Laffer,\" you say.
						\"Please send my limo to The Hard Disk Cafe immediately!")
					(TPrint "\"Yes sir! It'll be there damn near instantly.\"")
				else
					(TPrint "That line is busy.")
				)
			)
		)
	)

	(method (newRoom n)
		;in case music box isn't finished playing yet.
		(theMusic fade: 127 10 10 FALSE)
		(super newRoom: n)
	)

	(method (doit)
		(super doit:)
		(if
			(ego mover?)
			(Cls)
		)
		(cond
			(script)
			((StepOn ego cRED)
				(HandsOff)
				(self setScript sExitSouth)
			)
		)
	)
)

;	SCRIPTS

(instance sExitNorth of Script
	(method (changeState newState)
		(switchto (= state newState)
			(
				(ego
					setMotion:		PolyPath (door approachX?) (door approachY?) self
				)
			)
			(
				(door
					locked:		FALSE,
					exitType:	doorWalkEgo,	;walk ego out
					open:
				)
			)
		)
	)
)


(instance sEnterSouth of Script
	(method (changeState newState &tmp var1 var2)
		(switchto (= state newState)
			(
				; entering from outside 500
				; needed so that ego can walk in diag.

				(HandsOff)
				(ego
					setMotion:	MoveTo 293 183 self
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

(instance sBribe of Script
	(method (changeState newState &tmp bribe [str 30])
		(switchto (= state newState)
			(
				(Say ego "\"Say, good fellow,\" you say to the maitre d',
					\"would you be willing to accept a little bribe?\""
				)
				(= ticks 60)
			)
			(
				(cond
					((== ((Inventory at: iMembershipTape) state?) tNone)
						(Say maitredTalker "\"But, of course, My Sewer,\" says the
							maitre d' snootily.
							\"It worked in Larry 3, didn't it?\""
							#dispose:
							#caller:		self
						)
					)
					((== ((Inventory at: iMembershipTape) state?) tDone)
						(Say maitredTalker "\"I suggest you find transportation to another
							location, Mr. (heh, heh) Laffer!\" he smirks."
							#dispose:
						)
						(HandsOn)
						(self dispose:)
					)
					(else
						(Say maitredTalker "\"You have your tape, Mr. Laffer.
							Stop bothering me!\""
							#dispose:
						)
						(HandsOn)
						(self dispose:)
					)
				)
			)
			(
				(= ticks 67)
			)
			(

				;**	GetInput how many bucks Larry wants to bribe
				;**	maitre d'.	If < 100, print:

				(= bribe 0)
				(while (< bribe 1)
					(= str 0)
					(GetInput @str 6
						{How much do you wish to bribe the Maitre d'.}
						#title		{Grease That Palm}
					)
					(= bribe (ReadNumber @str))
				)

				(if (> bribe 99)
					(HandsOff)
					(Points ptsGotLarryTape pfGotLarryTape)
					(ego put: iMoney)
					(Say maitredTalker "\"Please, follow me, sir,\"
						says the maitre d', walking to the punch tape reader.
						\"Allow me to prepare you for membership
						in our exclusive club.\""
						#dispose
					)
					(= okToMakeTape TRUE)
					((Inventory at: iMembershipTape) state: tLarry)
					(curRoom setScript: sMD)
				else
					(Say maitredTalker "\"Thank you very much, sir,\" says the
		  				maitre d' greedily. \"Have a nice day!\""
						#dispose:
					)
					(HandsOn)
					(self dispose:)
				)
			)
		)
	)
)


(instance sMouth of Script
	(method (changeState newState &tmp var1 var2)
		(switchto (= state newState)
			(
				(womansMouth
					setCycle: 		Forward,
					cycleSpeed:		(Random 6 12),
				)
				(= cycles (Random 48 90))
			)

			(
				(womansMouth setCel: 0)
				(= cycles (Random 30 60))
			)

			(
				(self init:)
			)
		)
	)
)

(instance sMusicBox of Script
	(method (changeState newState &tmp var1 var2)
		(switchto (= state newState)
			(
				(HandsOff)
				(musicBox setCycle: Forward)
				(musicBoxCloseUp
					init:,
					setCycle: 		Forward
				)
				(= seconds 3)
			)
			(
				(Points ptsGotHyattTape pfGotHyattTape)
				(TPrint "Larry! How brilliant you are!
					Carefully wrapping your membership tape around the music
					box's cylinder,
					you punch a new set of holes in your membership tape,
					altering it forever.
					But now, will you be able to enter the cafe?"
					MUSICPRINT
				)
				(= seconds 3)
			)
			(
				(musicBoxCloseUp dispose:)
				(musicBox
					setCel: 		0,
					stopUpd:
				)
				(HandsOn)
				(ego normalize:)
				(self dispose:)
			)
		)
	)
)


(instance sPhone of Script
	(method (changeState newState &tmp var1 var2)
		(switchto (= state newState)
			(
				(HandsOff)
				(ego
					view:  			vMusicBoxInset,				;511
					posn:				(- (ego x?) 10) (ego y?),
					setLoop: 		lUsingPhone,					;2
					setCel:        0,
					setCycle: 		CycleTo 4 FORWARD self,
				)
			)
			(
				(phone setCel:	1)
				(ego setCycle: EndLoop self)
			)

			(
				(User canInput: TRUE)
				(DialPhone self)
			)

			(
				(ego setCycle: CycleTo 4 BACKWARD self)
			)

			(
				(phone
					setCel: 		0,
					stopUpd:
				)
				(ego setCycle: BegLoop self)
			)

			(
				(ego
					normalize: vLarry,
					posn: (+ (ego x) 10) (ego y?),
				)
				(HandsOn)
				(self dispose:)
			)
		)
	)
)


(instance sExitSouth of Script
	(method (changeState newState &tmp var1 var2)
		(switchto (= state newState)
			(
				(ego
					setMotion:		MoveTo (+ (ego x?) 40) (+ (ego y?) 40) self
				)
			)

			(
				(curRoom newRoom 500)
			)
		)
	)
)

(instance sMD of Script
	(method (changeState newState)
		(switch (= state newState)
			(0
				(HandsOff)
				(ego setHeading: 270 self)
			)

			(1
				(if okToMakeTape
					(= state (- MAKETAPE 1))
				)
				(= cycles 2)
			)

			(2

				;**	any changes made to these conditions must also be
				;**	made to those in state 3

				(cond
					((== ((Inventory at: iMembershipTape) state?) tNone)
						(switch (++ talkCounter)
							(1
								(Say ego "\"Hello,\" you say to the maitre d',
									\"do you have a table for a swinging guy like
									me?\"")
							)
							(2
								(Say ego "\"But sir,\" you protest,
									\"perhaps you've heard of me...
									my name is Larry; Larry Laffer.\"")
							)
							(3
								(Say ego "\"I'd really like to get something to eat,\"
									you say to the maitre d'.
									\"May I have a table now?\"")
							)
							(4
								(Say ego "\"I'd really like to get something to
									drink,\" you say to the maitre d'.
									\"May I please have a table now?\"")
							)
							(5
								(Say ego "\"You know, it's amazing that a place like
									this could even stay in business,\" you say,
									beginning to lose your temper.
									\"I've never been to a place where the customer
									is always WRONG!\"")
							)
							(6
								(Say ego "\"Let me into that cafe, you ninny,\"
									you shout at the maitre d'.
									\"You better let me in, I think!\"")
							)
							(7
								(Say ego "\"So when can I get in, eh?\" you whine,
									\"I've got to meet somebody here!\"")
							)
							(8
								(Say ego "\"She's really quite lovely,\" you offer,
									\"in fact, I bet you know her.\"")
							)
							(9
								(Say ego "\"Say, since I'm just going to stand here
									anyway, why don't I tell you a little story?
									A story about how a unassuming, traveling
									software salesman, born near a log cabin in
									Gumbo, Missouri, made it into the big time...\"")
							)
							(else
								(Say ego "\"Want to hear a little more about me?\"
									you offer."
								)
							)
						)
					)
					((== ((Inventory at: iMembershipTape) owner?) 510)
						(Say ego "\"Thanks for making that tape for me.
							Could you give it to me now?\""
						)
					)
					((== ((Inventory at: iMembershipTape) state?) tDone)
						(Say ego "\"Uhh, I seemed to have misplaced my membership
							tape,\" you tell the maitre d'.
							\"Could you cut me a new one?\""
						)
					)
					((Btst fSeenMM)
						(Say ego "\"Excuse me,\" you say to the maitre d',
							\"How do I get into that inner door?\""
						)
					)
					((== ((Inventory at: iMembershipTape) state?) tLarry)
						(Say ego "\"Thanks for the membership tape!\" you say to
							the maitre d'.
							\"But where do I go?\""
						)
					)
				)
				(= cycles 3)
			)
			(3
				(cond
					((== ((Inventory at: iMembershipTape) state?) tNone)
						(switch talkCounter
							(1
								(Say maitredTalker "\"No.\"" #dispose)
							)
							(2
								(Say maitredTalker "\"No.\"" #dispose)
							)
							(3
								(Say maitredTalker "\"No.\"" #dispose)
							)
							(4
								(Say maitredTalker "\"No.\"" #dispose)
							)
							(5
								(Say maitredTalker "\"No?\"" #dispose)
							)
							(6
								(Say maitredTalker "\"Know.\"" #dispose)
							)
							(7
								(Say maitredTalker "\"Ho.\"" #dispose)
							)
							(8
								(Say maitredTalker "\"Who?\"" #dispose)
							)
							(9
								(Say maitredTalker "\"OK! STOP!! I'll get you a damned ticket!\"
									he shouts."
									#dispose
									#caller: self
								)
								(= okToMakeTape TRUE)
								((Inventory at: iMembershipTape) state: tLarry)
							)
							(else
								(Say maitredTalker "\"I'm sorry, sir, but my limit is
									one bore per customer!\""
									#dispose)
							)
						)
					)
					((== ((Inventory at: iMembershipTape) owner?) 510)
						(Say maitredTalker "\"You fool! Eeet's hanging there in plain sight!\""
							#dispose
						)
					)
					((== ((Inventory at: iMembershipTape) state?) tDone)
						(Say maitredTalker "\"I suggest you find transportation to another
							location, Mr. (heh, heh) Laffer!\" he smirks."
							#dispose
						)
					)
					((Btst fSeenMM)
						(curRoom setScript: sDoubleTalk)
					)
					((== ((Inventory at: iMembershipTape) state?) tLarry)
						(Say maitredTalker "\"Just find a place to sit down.
							You have an assigned table. Find it!\""
							#dispose
						)
					)
				)
				(if (not okToMakeTape)
					(HandsOn)
					(self dispose:)
				)
			)

			(4
				(= state (- MAKETAPE 1))
				(= cycles 2)
				(Points ptsGotLarryTape pfGotLarryTape)
				(TPrint "(Nice going, Larry!)" LOW)
			)

			(MAKETAPE
				(maitreD
					init,
					view: 			vMaitreDWalk,
					setCycle: 		Walk,
					setLoop:       RELEASE,
					ignoreActors:  TRUE,
					illegalBits:	0,
					setMotion:		MoveTo 133 86 self,
				)
				(ego
					setMotion:		MoveTo 153 (ego y?)
				)
				(= okToMakeTape FALSE)
			)

			(21			  						;types on machine
				(typingFX play:)
				(maitreD
					view:          vMaitreD,
					setLoop:			lUsingMachine,
					setCycle:		Forward,
				)
				(screen 
					init:,
					setPri:			(- (maitreD priority?) 1)
				)
				(ego
					setMotion:		0,
					setHeading:		315
				)
				(= seconds 3)
			)

			(22
				(typingFX stop:)
				(maitreD setCel: 0)
				(screen setCycle: Forward)
				(= seconds 3)
			)

			(23
				(typingFX play:)
				(maitreD
					setCycle:		Forward,
				)
				(screen setCel: 0)
				(= seconds 3)
			)

			(24
				(typingFX stop:)
				(maitreD setCel: 0)
				(tape
					init,
					setCycle:      EndLoop self,
				)
				(screen dispose:)
			)

			(25
				(Say maitredTalker "\"There you are, Sir!\" barks the maitre d'.
					\"Enjoy your visit here at the lovely Hard Disk Cafe!\""
					#dispose:
					#caller:		self
				)
			)

			(26
				(maitreD
					view:				vMaitreDWalk,
					setCycle:		Walk,
					setLoop:			RELEASE,
					setMotion:		MoveTo 70 90 self,
				)
			)

			(27
				(maitreD
					view:				vMaitreD,
					setLoop:			lMDTalking,
				)
				((Inventory at: iMembershipTape) owner: 510)
				(HandsOn)
				(self dispose:)
			)
		)
	)
)

(instance sDoubleTalk of Script
	(method (changeState newState)
		(switchto (= state newState)
			(
				(Say maitredTalker "\"I'm sorry, sir,\" he replies,
					\"but admittance to the exclusive `Herman Hollerith Room'
					is limited to only our very finest customers.\""
					#dispose:
					#caller:		self
				)
			)
			(
				(Say maitredTalker "\"(...which you are not!)\"
					he concludes under his breath."
					#dispose
				)
				(HandsOn)
				(self dispose:)
			)
		)
	)
)

(instance sGetTape of Script
	(method (changeState newState)
		(switchto (= state newState)
			(
				(ego
					view:		 		vMusicBoxInset,
					setCel:			0,
					setLoop:  		0,
					cycleSpeed:		6,
					setCycle: 		CycleTo 6 1 self,
					get:      		iMembershipTape,
				)
			)
			(
				(paperRip play:)
				(ego
					setCycle:		EndLoop self
				)
				(tape dispose:)
			)
			(
				(ego
					setLoop:			1,
					normalize:		,
				)
				(Points ptsGotTape pfGotTape)
				(TPrint "You grab your tape from the tape reader.")
				(HandsOn)
				(self dispose:)
			)
		)
	)
)

;	PROPS


(instance phone of View
	(properties
	   view          	vHardDiskLobby 	;510
		loop          	4
		x 				  	59
		y 				  	105
		z				  	20
		nsLeft 		  	50
		nsTop 		  	71
		nsBottom 	  	85
		nsRight 		  	72
 		description   	"the telephone"
		sightAngle 	  	90
		priority       5
		signal		  	(| fixedCel fixedLoop fixPriOn ignrAct )
		approachX 		81
		approachY		96
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbLook
				(if (Btst fMCloseUp)
					(TPrint "A telephone rests near the maitre d's podium--and
						it's finally available for your use!")
				else
					(TPrint "That woman has tied up the telephone for as long
						as you've been here.")
				)
			)
			(verbTalk
				(self doVerb: verbDo &rest)
			)
			(verbDo
				(cond
					((Btst fCalledLimo)
						(TPrint "There's no need to use the telephone again,
							since you've already contacted the limo company.
							Shouldn't you walk outside?
							Your limo may be waiting for you.")
					)
					((Btst fMCloseUp)
						(curRoom setScript: sPhone)
					)
					(else
						(Say ego "\"Get off that phone,\" you shout.
							\"There are others here that want to make a call.\"")
						(TPrint "She ignores you completely.")
					)
				)
			)
			(else
				(super doVerb: theVerb invItem &rest)
			)
		)
	)
)


(instance screen of Prop
	(properties
	   view				vHardDiskLobby 	;510
		loop				2
		x					123
		y					46
		nsLeft			110
		nsTop				40
		nsBottom			49
		nsRight			130
 		description		"the computer terminal"
		sightAngle		90
		signal			(| fixedCel fixedLoop ignrAct)
	)
)

(instance woman of View
	(properties
		view				vWomanOnPhone
		loop				0
		x					80
		y					106
		description		"Bertha"
		priority			7
		signal			(| fixedCel fixedLoop fixPriOn ignrAct)
		lookStr			"A large aggressive woman has been talking on the
							telephone for as long as you've been here."
	)
	(method (doVerb theVerb)
		(switch theVerb
			(verbDo
				(Say ego "\"Get off that phone,\" you shout.
					\"There are others here that want to make a call.\"")
				(TPrint "Bertha ignores you completely.")
			)
			(verbTalk
				(Say ego "\"Hey, I'd like to make a call someday, myself,\"
					you say to the large woman.")
				(TPrint "Bertha ignores you completely.")
			)
			(else
				(super doVerb: theVerb &rest)
			)
		)
	)
)

(instance womansMouth of Prop
	(properties
		view				vWomanOnPhone
		loop				1
		x					74
		y					75
		description		"Bertha's head"
		priority			7
		signal			(| fixedCel fixedLoop fixPriOn ignrAct )
	)
)

(instance musicBox of Prop
	(properties
		view				vMusicBox
		loop				0
		x					39
		y					189
		nsLeft			19
		nsTop				173
		nsBottom			189
		nsRight			72
		description		"the music box"
		priority    	15
		approachX   	58
		approachY   	186
		signal		   (| fixedLoop fixPriOn ignrAct )
	)
	(method (cue)
		(super cue:)
		(theMusic fade: 127 10 10 FALSE)
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbLook
				(TPrint "Music boxes show an early use of the principle that
					made the punchcard possible.
					It gave precisely aligned pins meaning as musical pitches.")
				(if (ego has: iMembershipTape)
					(TPrint "(How interesting:  the music box's cylinder
						appears to be the same width as your membership tape.)" LOW)
				else
					(TPrint "Its cylinder is exactly 35 millimeters wide.")
				)
			)
			(verbDo
				(musicBoxFX play: self)
				(theMusic fade: 80 10 10 FALSE)
				(TPrint "The music box is either very old and very tired,
					or some practical joker has altered the pin placement
					on its cylinder. It sounds terrible!"
					MUSICPRINT
				)
			)
			(verbTalk
				(TPrint "It's a music box, not an Edison cylinder recorder.")
			)
			(verbUse
				(switch invItem
					(iMembershipTape
						(if (not (> ((Inventory at: iMembershipTape) state?) tLarry))
							(theMusic fade: 80 10 10 FALSE)
							(musicBoxFX play: self)
							((Inventory at: iMembershipTape) state: tHyatt)
							(TPrint "You wonder if this will ruin your only means
								of admission to the Cafe!")
							(curRoom setScript: sMusicBox)
						else
							(TPrint "If you punch any more holes in that piece of
								tape, there will be nothing left!")
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

(instance musicBoxCloseUp of Prop
	(properties
		view				vMusicBoxInset
		loop				1
		x					71
		y					174
		priority    	15
		signal		   (| fixedLoop fixPriOn ignrAct )
	)
)

(instance tape of Prop
   (properties
	   view				vHardDiskLobby 	;510
      loop				3
      x					112
      y					59
      description		"your membership tape"
      sightAngle		90
		priority			3
		signal			(| ignrAct fixPriOn)
		lookStr			"The membership tape you wanted so badly now hangs
							limply from the side of the paper tape reader,
							twisting slowly, slowly, in the wind."
		approachX		117
		approachY		82
   )
	(method (init)
		(super init:)
		(self approachVerbs: verbDo verbUse)
		(tapeReader
			approachX:	approachX,
			approachY:	approachY,
		)
	)
	(method (dispose:)
		(super dispose:)
		(tapeReader
			approachX: 	t_approachX,
			approachY: 	t_approachY,
		)
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbDo
				(HandsOff)
				(curRoom setScript: sGetTape)
			)
			(else
				(super doVerb: theVerb invItem &rest)
			)
		)
	)
)

(instance maitreD of Actor
	(properties
		view 			vMaitreD
		x 				70
		y 				90
		loop        lMDTalking
		priority    4
 		description	"the maitre d'"
		lookStr     "You are quite envious of those that can look
						stupid and snooty at the same time."
	   approachX   103
		approachY   89
		signal		(| fixedLoop fixPriOn ignrAct )
	)
	(method (cue)
		(super cue:)
		(TPrint "(You wonder if his French accent could
			be fake?)" LOW
		)
	)

	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbDo
				(Say ego "\"Hello,\" you say to the maitre d',
					\"do you have a table for a guy like me?\""
				)
				(if (ego has iMembershipTape)
					(Say maitredTalker "\"Just use the membership tape I created for you,\" he replies." #dispose)
				else
					(Say maitredTalker "\"Please keep your hands off me,\" says the
						maitre d'. \"I'm for the use of members only!\""
						#dispose)
				)
			)

			(verbTalk
				(curRoom setScript: sMD)
			)

			(verbUse
				(if (== ((Inventory at: iMembershipTape) owner?) 510)
					(Say maitredTalker "\"Why don't you get your tape out of
						the machine?\""
						#dispose
					)
				else
					(switch invItem
						(iMoney
							(HandsOff)
							(curRoom setScript: sBribe)
						)

						(iCreditCards
							(if (== ((Inventory at: iMembershipTape) state?) tLarry)
								(Say ego "\"You know, I'd really love to be rid of
									all these troublesome credit cards,\"
									you tell the maitre d'.
									\"Would you be willing to trade them for a table
									inside?\"")
								(Say maitredTalker "\"But, of course, Sir!\" he replies,
									snapping to attention.
									\"I always have a soft spot for a fine
									fellow like you!\""
									#dispose
								)
								(Points ptsGotSuperTape pfGotSuperTape)
								((Inventory at: iMembershipTape) state: tSuper)
								(ego put: iCreditCards)
								(= okToMakeTape TRUE)
								(curRoom setScript: sMD)
							else
								(Say maitredTalker "\"You might want to save these,
									Sir,\" he replies. \"You may need them later."
									#dispose
								)
							)
						)
						(iHDCNapkin
							(Say ego "\"Would you mind throwing this away for me,\"
								you say, \"Now that I know how to get here
								I don't need it any more.\""
							)
							(Say maitredTalker "\"Wee, wee, moisture!\" he replies."
								#dispose
								#caller: self
							)
							(ego put: iHDCNapkin curRoomNum)
							(theIconBar
								disable: 	useIcon,
								curIcon: 	(theIconBar at: walkIcon)
							)
							(theGame setCursor: ((theIconBar curIcon?) cursor?))
						)
						(iDayTrotter
							(Say ego "\"Would you like to see my appointment
								calendar for the next year?\" you ask the maitre d'.")
							(Say maitredTalker "\"Hardly,\" he sniffs."
								#dispose
							)
						)
						(else
							(super doVerb: theVerb invItem &rest)
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


(instance LDoor of Prop
	(properties
	   view				vHardDiskLobby 	;510
		loop				0
		x					139
		y					63
		z					-3
 		description		"the swinging doors"
		approachX		165
		approachY		70
		priority			2
		signal			(| fixedLoop fixPriOn)
	)
	(method (doVerb theVerb invItem)
		(door doVerb: theVerb invItem)
	)
)

(instance door of Door
	(properties
	   view				vHardDiskLobby 	;510
		nsLeft			141
		nsTop				8
		nsBottom			63
		nsRight			177
		loop				1
		x					179
		y					63
		priority			2
 		description		"the swinging doors"
		approachX		165
		approachY		70

		entranceTo 		525
		locked 			TRUE
		moveToX			156
		moveToY			60
		heading			180
		lookStr			"You presume these doors lead to the cafe proper."
		signal			fixPriOn
		enterType:		doorWalkEgo		;place ego in front of door
		exitType:		doorWalkEgo			;walk ego out
	)


	(method (init)
		(= doubleDoor LDoor)
		(super init:)
		(self approachVerbs: verbDo)
	)

	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbDo
				(TPrint "These doors operate by means of a Membership
					Tape inserted and read by that machine to their left.")
			)
			(verbUse
				(switch invItem
					(iMembershipTape
						(TPrint "Insert your tape into the machine just
							to the left of these doors.")
					)
					(iGoldCard
						(TPrint "You might try using that on the maitre d'.")
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


(instance maitredTalker of Talker
	(properties
		name			"the maitre d'"
		view			vMaitreDTalker
		loop			3
		nsLeft		22
		nsTop			33
		viewInPrint	TRUE
	)
	(method (init)
		(= bust  talkerBust)
		(= eyes  talkerEyes)
		(= mouth talkerMouth)
		(super init:)
	)
)

(instance talkerBust of View
	(properties
		view		vMaitreDTalker
		loop		1
		nsLeft	0
		nsTop		0
	)
)

(instance talkerEyes of Prop
	(properties
		view			vMaitreDTalker
		loop			2
		nsLeft		26
		nsTop			11
		cycleSpeed  30
	)
)

(instance talkerMouth of Prop
	(properties
		view			vMaitreDTalker
		loop			0
		nsLeft		12
		nsTop			24
	)
)


;	FEATURES

(instance tapeReader of Feature
	(properties
		x 					112 	;135
		y 					65                 ;must be greater than Larry's Y
		z 					0
		heading 			0
		nsLeft 			101
		nsTop 			38
		nsBottom 		73
		nsRight 			139
 		description 	"the paper tape machine"
		sightAngle 		180
		approachX      t_approachX
		approachY      t_approachY
	)

	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbLook
				(TPrint "An ancient mainframe computer's paper tape reader
					sits against the far wall near the door.")
				(if (== ((Inventory at: iMembershipTape) owner?) 510)
					(TPrint "Your membership tape is still hanging from the side
						of the machine.")
				)
			)
			(verbDo
				(if (== ((Inventory at: iMembershipTape) owner?) 510)
					(tape doVerb: verbDo)
				else
					(curRoom newRoom: 520)
				)
			)
			(verbTalk
				(TPrint "This paper tape reader grants access to the
					\"Hard Disk Cafe\" proper,
					which is located behind those closed doors.
					To enter, just insert your membership tape in the slot.")
			)
			(verbUse
				(switch invItem
					(iMembershipTape
						(Bset fUsedTape)
						(curRoom newRoom: 520)
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


(instance diskDrive of Feature
	(properties
		x 					301
		y 					82
		z 					0
		heading 			0
		nsLeft 			283
		nsTop 			56
		nsBottom 		108
		nsRight 			319
 		description 	"the antique disk drive"
		sightAngle 		90
		lookStr			"This early disk drive was the size of a washing
							machine and held an enormous amount of data--over
							one megabyte."
	)
	(method (doVerb theVerb invItem)
		(if
			(!= theVerb verbLook)
			(TPrint "This disk drive is for display purposes only.")
		else
			(super doVerb: theVerb invItem)
		)
	)
)
(instance pet of Feature
	(properties
		x					108
		y					102
		z					0
		heading			0
		nsLeft			89
		nsTop				92
		nsBottom			113
		nsRight			128
		sightAngle		90
		onMeCheck		cBLUE
 		description		"the Pet Computer"
		lookStr			"You haven't seen a Pet Computer since
							the early days of \"InfoWorld!\""
	)
	(method (doVerb theVerb invItem)
		(if
			(!= theVerb verbLook)
			(TPrint "This computer is for display purposes only.")
		else
			(super doVerb: theVerb invItem)
		)
	)
)
(instance appleII of Feature
	(properties
		x					173
		y					112
		z					0
		heading			0
		nsLeft			154
		nsTop				101
		nsBottom			124
		nsRight			192
		onMeCheck		cBLUE
 		description		"the Apple II computer"
		sightAngle		90
		lookStr			"You cut your first computer teeth on an old integer
							BASIC Apple II.  It wasn't even a Plus!"
	)
	(method (doVerb theVerb invItem)
		(if
			(!= theVerb verbLook)
			(TPrint "This computer is for display purposes only.")
		else
			(super doVerb: theVerb invItem)
		)
	)
)
(instance cashRegister of Feature
	(properties
		x					148
		y					89
		z					0
		heading			0
		nsLeft			128
		nsTop				78
		nsBottom			100
		nsRight			169
		onMeCheck		cBLUE
 		description		"the cash register"
		sightAngle		90
		lookStr			"This NCR baby was one of the first
							microprocessor-controlled point-of-sale cash registers."
	)
	(method (doVerb theVerb invItem)
		(if
			(!= theVerb verbLook)
			(TPrint "This computer is for display purposes only.")
		else
			(super doVerb: theVerb invItem)
		)
	)
)
(instance monitor of Feature
	(properties
		x					124
		y					135
		z					0
		heading			0
		nsLeft			111
		nsTop				126
		nsBottom			144
		nsRight			138
		sightAngle		90
		onMeCheck		cBLUE
 		description		"the green screen monitor"
		lookStr			"This ancient device could display text in any color
							you wanted, as long as it was green on black."
	)
	(method (doVerb theVerb invItem)
		(if
			(!= theVerb verbLook)
			(TPrint "This computer is for display purposes only.")
		else
			(super doVerb: theVerb invItem)
		)
	)
)
(instance atari400 of Feature
	(properties
		x					78
		y					144
		z					0
		heading			0
		nsLeft			58
		nsTop				137
		nsBottom			152
		nsRight			99
		onMeCheck		cBLUE
 		description		"the Atari 400 computer"
		sightAngle		90
		lookStr			"Boy, does that Atari 400 bring back fond memories of
							your misspent youth, playing \"Frogger\" when your
							classmates were dating!"
	)
	(method (doVerb theVerb invItem)
		(if
			(!= theVerb verbLook)
			(TPrint "This computer is for display purposes only.")
		else
			(super doVerb: theVerb invItem)
		)
	)
)
(instance tapeDrive of Feature
	(properties
		x					16
		y					71
		z					0
		heading			0
		nsLeft			0
		nsTop				40
		nsBottom			103
		nsRight			32
		sightAngle		90
 		description		"the antique tape drive"
		lookStr			"This is one of the world's first tape drives,
							revolutionary in its day, it seems incredibly
							quaint today.  Why it couldn't even hold
							\"Space Quest\" let alone a \"King's Quest!\""
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbUse
				(switch invItem
					(iMembershipTape
						(TPrint "This machine once used magnetic tape,
							not paper tape.")
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

(instance pianoRoll of Feature
	(properties
		x 					243
		y 					17
		heading 			0
		nsLeft 			220
		nsTop 			9
		nsBottom 		26
		nsRight 			266
 		description 	"the piano roll"
		sightAngle 		90
		lookStr        "The player piano was an early use of punched holes to
							control a mechanical device.
							A vacuum was applied to 88 holes in a metal bar,
							with each hole indicating a different musical pitch.
							Where holes occurred in the moving paper,
							the corresponding notes were played."
	)
	(method (doVerb theVerb invItem)
		(TPrint "The historic old piano roll is hermetically sealed
			in that glass case.")
	)
)
(instance punchCardMachine of Feature
	(properties
		x 					253
		y 					61
		heading 			0
		nsLeft 			222
		nsTop 			34
		nsBottom 		88
		nsRight 			284
 		description 	"the punch card machine"
		sightAngle 		40
		lookStr        "This punch card reader dates from the 1890 U. S. Census.
							It could handle up to 80 characters or columns of data,
							a number which remains to this day as the width of most
							computer video display devices."
	)
	(method (doVerb theVerb invItem)
		(TPrint "A sign on the punch card reader requests
			that you keep your hands off.")
	)
)
(instance chair of Feature
	(properties
		x					39
		y					84
		z					0
		heading			0
		nsLeft			28
		nsTop				65
		nsBottom			103
		nsRight			51
 		description		"the chair"
		sightAngle		40
		lookStr			"This chair is for the exclusive use of the maitre d'."
	)
)
(instance bullBoard of Feature
	(properties
		x					25
		y					31
		z					0
		heading			0
		nsLeft			0
		nsTop				6
		nsBottom			56
		nsRight			51
 		description		"the bulletin board"
		sightAngle		40
		lookStr			"The bulletin board is filled with photographs of
							famous nerds who frequent this place!"
	)
)
(instance podium of Feature
	(properties
		x					78
		y					95
		z					0
		heading			0
		nsLeft			55
		nsTop				50
		nsBottom			95
		nsRight			102
 		description		"the maitre d's podium"
		sightAngle		40
		onMeCheck		cBLUE
		lookStr			"The maitre d's podium is just the right size for him
							to hide behind when the crowds here start throwing
							things!"
	)
)
(instance desk of Feature
	(properties
		x					61
		y					99
		z					0
		heading			0
		nsLeft			46
		nsTop				79
		nsBottom			106
		nsRight			76
 		description		"the desk"
		sightAngle		40
		lookStr			"You wish you had such a nice desk in your crummy
							office."
	)
)
(instance cactus1 of Feature
	(properties
		x					203
		y					40
		z					0
		heading			0
		nsLeft			190
		nsTop				16
		nsBottom			65
		nsRight			217
 		description		"the cactus"
		onMeCheck		cGREEN
		sightAngle		40
		lookStr			"The cactus is covered with needle-sharp needles.
							(Hence, the name \"Needlenose Cacti.\")"
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbDo
				(Say ego "\"OUCH!\"")
			)
			(verbTalk
				(TPrint "Who do you think you are, Snoopy's Uncle Spike?")
			)
			(verbUse
				(switch invItem
					(iMembershipTape
						(TPrint "Punch holes in your membership tape?
							A good idea, but this is not the way to do it.")
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
(instance cactus2 of Feature
	(properties
		x 					116
		y 					157
		z 					0
		heading 			0
		nsLeft 			79
		nsTop 			125
		nsBottom 		189
		nsRight 			154
 		description 	"the cactus"
		sightAngle 		40
		onMeCheck 	 	cGREEN
	)
	(method (doVerb)
		(cactus1 doVerb &rest)
	)
)

;	SOUNDS

(instance musicBoxFX of Sound
	(properties
		number		mMusicBox
		loop			1
		flags			TRUE
	)
)

(instance typingFX of Sound
	(properties
		number		sKeyboardType
		flags			1
		loop			-1
	)
)

(instance paperRip of Sound
	(properties
		number		sPaperRip
		flags			1
	)
)
