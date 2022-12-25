;**
;**	Logics for Room 730 -- "Tramp Casino" Ballroom
;**
;**	LSL5:  Passionate Patti Does A Little Undercover Work
;**				Copyright 1991
;**				by Al Lowe
;**
;**	Programmed by Oliver Brelsford
;**
;**	Last Update:  September 5, 1991
;**

(script#		730)

(public
	rm730		0
)

(procedure
	InitFeatures
	DisposeFeatures
)

(define	pTrampCasinoBallroom			730)
(define	pT&AClose-up				738)

(define	vRoom							730)
(define		lAudienceMotion	0-2)
(define	vLarryRoom					731)
(define		lSittingInFront	0)
(define		lMountingRing		1)
(define	vBouncer						732)
(define		lBlockingAisle		0)
;(define		lTalking				1)
(define		lAcceptingMoney	2)
(define	vWetT-ShirtContestant	733)
;(define		lWalking				0-1)
(define	vLanaWrestling				734)
;(define		lWalking				0-1)
(define		lTaunting1			2)
(define		lTaunting2			3)
(define	vT&ACloseUp				738)
	(define	lWinking				0)
	(define	lKissing				1)

(define	vBouncerTalker			(+ vBouncer 1000))
(define	vLanaTalker				1731)
;(define	vLarrySmaller			552)	; 8-Loop

(define	lana'sOut	(cast contains: lana))

(define	mNastyBoys				738)
(define		sCatCalls			743)
(define		sMudSquishing		744)

(local
	seenMsg
	justWatching
	mainEvent
	payoff
	paid
	contestantState
)

(enum
	none
	wrestling
	contest
)


(instance rm730 of LLRoom
	(properties
		picture		pTrampCasinoBallroom
		lookStr		"The Ballroom is pulsating with people--all of them men.
							\"Where are the women?\" you wonder."
	)

	(method (init)
		(ego 	init:)
		(HandsOff)
		(switch prevRoomNum
			(710
				(self setScript: sFromSouth)
			)
			(else
				; DEBUG
				(ego get: iSilverDollar)
				(= silvDollars 5000)
				(Bset fSeenLL)
				(HandsOn)
				(self setScript: sFromSouth)
			)
		)

		(super init:)
		(bouncer init:, approachVerbs: verbTalk verbUse)

		(InitFeatures)
		(curRoom addObstacle:
			((Polygon new:)
				type: PBarredAccess,
				init:	0 0 319 0 319 189 16 189 16 183 33 164 0 164,
				yourself
			)
		)
		(theMusic
			number:		mNastyBoys,
			setLoop:		-1,
			flags:		1,
			play:			127,
		)
		(cond
			((Btst fSeenLL)
				(LoadMany VIEW
					vLanaTalker
					vLarrySmaller
				)
				(LoadMany SOUND
					sMudSquishing
					sCatCalls
				)
				(= mainEvent wrestling)
				(ring 
					init:,
					stopUpd:
				)
				(ropes 
					init:,
					stopUpd:
				)
			)
			((Btst fMudWrestled)
				(= mainEvent none)
			)
			(else
				(= mainEvent contest)
				(Load SOUND sCatCalls)
				(LoadMany VIEW
					vWetT-ShirtContestant
					vT&ACloseUp
				)
			)
		)
		(Load VIEW vLarryRoom)
	)
	(method (doVerb theVerb invItem)
		(if (== 	(curRoom curPic?)  pT&AClose-up)
			(cast eachElementDo: #show)
			(curRoom
				drawPic: 	pTrampCasinoBallroom,
				setScript:	0
			)
			(herEyes dispose:)
			(herMouth dispose:)
			(SetupExit FALSE)
			(InitFeatures)
			(sContest start: (sContest state?))
			(contestant setScript: sContest)
			TRUE
		else
			(super doVerb: theVerb invItem &rest)
		)
	)
	(method (doit)
		(super doit:)
		(if (and (ego mover?)
					(== (curRoom curPic?) pT&AClose-up))
			(ego setMotion: 0)
			(self doVerb:)
		)
		(cond
			(script)
			((ego script?))
			((ego edgeHit?)
				(ego edgeHit: 0)
				(HandsOff)
				(curRoom setScript: sLeave)
			)
			(	(and
					(ego sitting?)
					(ego mover?)
			 	)
				(if
					(== (curRoom curPic?) pT&AClose-up)
					(self doVerb:)
				else
		 			(HandsOff)
					(ego setScript: sStandUp)
				)
			)
			(	(and
			 		lana'sOut
					(not justWatching)
					(== (lana script?) sTaunt)
				)
				(ego
					setPri:		14,
					setScript: 	sLarryIntoRing
				)
			)
			(	(and
					(< (ego y?) 137)
					(not (ego sitting?))
				)
				(ego setScript: sSitDown)
			)
		)
	)
	(method (newRoom n)
		(if
			(!= n 740)
			(theMusic fade:)
		)
		(super newRoom: n)
	)
)



;ษอออออออออออออออออออป
;บ                   บ
;บ Scripts           บ
;บ                   บ
;ศอออออออออออออออออออผ


(instance sLeave of Script
	(method (changeState newState)
		(switchto (= state newState)
			(
				(ego
					setMotion:		MoveTo (- (ego x?) 25) (+ (ego y?) 25) self
				)
			)
			(
				(curRoom newRoom: 710)
			)
		)
	)
)

(instance sFromSouth of Script
	(method (changeState newState)
		(switchto (= state newState)
			(
				(ego
					normalize:	,
					posn:			-30 222,
					setMotion: 	PolyPath 8 188 self
				)
			)
			(
				(ego setHeading: 0 self)
			)
			(
				(HandsOn)
				(self dispose:)
			)
		)
	)
);sFromSouth


(instance sSitDown of Script
	(method (changeState newState)
		(switchto (= state newState)
			(
				(HandsOff)
				(ego
					view:			vLarryRoom,
					setLoop:		1,
					setCel:		0,
					setPri:		14,
					setCycle:	EndLoop,
					setMotion: 	MoveTo 111 130 self
				)
			)
			(
				(ego
					x:				111,
					y:				123,
					setLoop:		0,
					setCel:		0,
					setCycle:	EndLoop self
				)
			)
			(
				(ego sitting: TRUE)
				(HandsOn)
				(if (== mainEvent wrestling)
					(if justWatching
						(= paid FALSE)	; we can still pay more
					)
					(if (not (cast contains: lana))
						(lana
							init:			,
							setScript:	sWrestle
						)
					)
				else
					(if (not (cast contains: contestant))
						(contestant
							init:			,
							setScript: 	sContest
						)
					)
				)
				(self dispose:)
			)
		)
	)
);sSitDown


(instance sStandUp of Script
	(method (changeState newState)
		(switchto (= state newState)
			(
				(HandsOff)
				(ego sitting: FALSE)
				(ego setCycle: BegLoop self)
			)
			(
				(ego posn: (ego x?) (+ (ego y?) 7))
				(= cycles 1)
			)
			(
				(ego
					setLoop:		2,
					setCel:		0,
					setCycle:	EndLoop,
					setMotion:	PolyPath 80 137 self
				)
			)
			(
 				(HandsOn)
				(ego normalize:)
				(if
					(and
						((CueObj client?) approachX?)
						(!= ((theIconBar curIcon?) cursor?) TRAVEL_CURSOR)
					)
					(ego setMotion: PolyPath
					((CueObj client?) approachX?)
					(+ (ego z?) ((CueObj client?) approachY))
					CueObj)
				)
				(self dispose:)
			)
		)
	)
);sStandUp


(instance sWrestle of Script
	(method (changeState newState)
		(switchto (= state newState)
			(
				(lana
					setLoop:		0,
					setCycle:	Forward,
					setMotion:	MoveTo 265 82 self
				)
			)
			(
				(lana
					setLoop:		1,
					setCel:		0,
					setCycle:	EndLoop self
				)
			)
			(
				(soundFX
					number:		sCatCalls,
					play:
				)
				(lana
					setLoop:		2,
					setCel:		0,
					setCycle:	CycleTo 7 FORWARD self
				)
			)
			(
				(robe
					init:			,
					setCycle:	EndLoop
				)
				(lana
					setLoop:		4,
					setCycle:	Forward,
					setMotion:	MoveTo 223 82 self
				)
			)
			(
				(lana
					x:				203,
					y:				65,
					setLoop:		5,
					setCel:		0,
					setPri:		7,
					cycleSpeed:	4,
					setCycle:	EndLoop self
				)
			)
			(
				(soundFX
					number:		sMudSquishing,
					play:
				)
				(lana
					setLoop:		4,
					x:				174,
					y:				82,
				)
				(= cycles 2)
			)
			(
				(lana
					cycleSpeed:	6,
					setScript: 	sTaunt
				)
				(self dispose:)
			)
		)
	)
);sWrestle

(instance sContest of Script
	(method (changeState newState)
		(switchto (= state newState)
			(
				(TPrint "The public address system booms through the hall,
					\"Come on, guys, let's give a great big welcome
					to the last contestant in the big
					`Tramp This! Wet T-Shirt Contest,'
					Miss Jennifer Jiggle!\"")
				(= seconds 3)
			)
			(
				(contestant
					setCycle:	Walk,
					setMotion: 	MoveTo 250 82 self,
				)
			)
			(
				(soundFX
					number:		sCatCalls,
					play:
				)
				(contestant
					setCycle:	Walk,
					setMotion:	MoveTo 160 82 self
				)
			)
			(
				(contestant
					setLoop:		2,
					setCel:		0,
					setMotion:	0,
					cycleSpeed: 8,
					moveSpeed:  8,
					setCycle:	EndLoop self,
				)
			)
			(
				(contestant 
					setCycle:	BegLoop self
				)
			)
			(
				(= seconds 2)
			)
			(
				(contestant
					setLoop:		RELEASE,
					setCycle:	Walk,
					setMotion:	MoveTo 135 82 self
				)
			)
			(
				(contestant
					setLoop:		3,
					setCel:		255,
					setCycle:	BegLoop self
				)
			)
			(
				(= seconds 2)
			)
			(
				(contestant
					setLoop:		RELEASE,
					cycleSpeed: 6,
					moveSpeed:  6,
					setCycle:	Walk,
					setMotion:	MoveTo 360 82 self,
				)
			)
			(
				(TPrint "\"And that completes today's matinee, guys!
					Come back later today for the wonderful
					\"Tramp This! Mud Night Madness\"
					mud wrestling contest!\"")
				(theMusic fade: 0 10 5 TRUE)
				(self dispose:)
			)
		)
	)
);sContest

(instance sLarryIntoRing of Script
	(method (changeState newState)
		(switchto (= state newState)
			(
				(HandsOff)
				(if (not (ego sitting?))
					(ego
						setMotion: 	PolyPath 70 132 self
					)
				else
					(= cycles 3)
				)
			)
			(
				(if (not (ego sitting?))
					(ego
						view:			vLarryRoom,
						setLoop:		1,
						setCel:		0,
						setPri:		14,
						setCycle:	EndLoop,
						setMotion: 	MoveTo 101 130 self
					)
				else
					(= cycles 3)
				)
			)
			(
				(ego
					view:			vLarrySmaller,
					sitting:		FALSE,
					setLoop:		RELEASE,
					setCycle:	Walk,
					cycleSpeed:	6,
					moveSpeed:	6,
					setMotion:	MoveTo 90 123 self,
				)
			)
			(
				(ropes ignoreActors: TRUE)
				(ring  ignoreActors: TRUE)
				(ego
					view:			vLarrySmaller,
					setLoop:		3,
					setCycle:	Walk,
					setMotion:	MoveTo 109 102 self
				)
			)
			(
				(ego
					view:			vLarryRoom,
					setCel:		0,
					setCycle:	CycleTo 7 1 self
				)
			)
			(
				(ego
					setPri:		(+ (ring priority?) 1),
					setCycle:	EndLoop self
				)
			)
			(
				(soundFX
					number:		sMudSquishing,
					play:			self
				)
			)
			(
				(curRoom newRoom: 740)
			)
		)
	)
);sLarryIntoRing


(instance sTaunt of Script
	(method (changeState newState)
		(switchto (= state newState)
			(
				(if (== (lana x?) 184)
					(lana
						setLoop:		6,
						setCycle:	Forward,
						setMotion: 	MoveTo 115 82 self
					)
				else
					(lana
						setLoop:		7,
						setCycle:	Forward,
						setMotion: 	MoveTo 184 82 self
					)
				)
			)
			(
				(lana
					setLoop:		8,
					setCel:		0,
					setCycle:	EndLoop self
				)
			)
			(
				(switch (Random 0 5)
					(0
						(Say lanaTalker "\"C'mon, Larry!
							Let's see what you've got!\""
							#dispose:
						)
					)
					(1
						(Say lanaTalker "\"What'sa matter, Larry?
							Are you a wimp or a mouse?\""
							#dispose:
						)
					)
					(2
						(Say lanaTalker "\"What a wussy! Get up here!\""
							#dispose:
						)
					)
					(3
						(Say lanaTalker "\"A REAL man would be up here already!\""
							#dispose:
						)
					)
					(4
						(Say lanaTalker "\"What's wrong, Big Boy?
							Afraid I might win?\""
							#dispose:
						)
					)
					(5
						(Say lanaTalker "\"Geez, Larry!  What a chicken!\""
							#dispose:
						)
					)
				)
				(Delay (Random 10 20) CYCLES self)
			)
			(
				(self changeState: 0)
			)
		)
	)
);sTaunt

(instance sCloseup of Script
	(method (changeState newState)
		(switchto (= state newState)
			(
				(= seconds (Random 2 4))
			)
			(
				(herEyes
					init:			,
					setCel:		0,
					setCycle:	BegLoop self
				)
			)
			(
				(= seconds (Random 2 4))
			)
			(
				(herMouth
					init:			,
					setCel:		0,
					setCycle:	EndLoop self
				)
			)
			(
				(= seconds (Random 1 3))
			)
			(
				(herMouth
					setCycle:	BegLoop self
				)
			)
			(
				(= seconds (Random 2 4))
			)
			(
				(herEyes
					init:			,
					setCel:		0,
					setCycle:	BegLoop self
				)
			)
			(
				(= start (* 2 (Random 0 1)))
				(self init:)
			)
		)
	)
)


;ษอออออออออออออออออออป
;บ                   บ
;บ Objects           บ
;บ                   บ
;ศอออออออออออออออออออผ

(instance contestant of Actor
	(properties
		view			vWetT-ShirtContestant
		x				340
		y				82
		description	"Jennifer Jiggle"
		cycleSpeed	6
		moveSpeed	6
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbLook
				(Points ptsWetTeeShirt pfWetTeeShirt)
				(cast eachElementDo: #hide)
				(curRoom
					drawPic: 	pT&AClose-up,
					setScript:	sCloseup
				)
				(SetupExit TRUE)
				(DisposeFeatures)
				(self
					setCycle:	0,
					setMotion:	0,
				)
			)
			(verbTalk
				(Say ego "\"Hey, Jennifer! Why don't you really wet down that
					T-shirt?\" you shout.")
			)
			(verbUse
				(TPrint "There's nothing you can use on Jennifer that she
					hasn't used on herself already!")
			)
			(verbZipper
				(TPrint "You really wish she'd take off that shirt,
					don't you, Larry?")
			)
			(else
				(super doVerb: theVerb invItem)
			)
		)
	)
)

(instance lana of Actor
	(properties
		view			vLanaWrestling
		x				340
		y				82
		description	"Lana Luscious"
		lookStr		"Lana looks much different, now that she's out of her
						roller skates and into her element!"
		cycleSpeed	6
		moveSpeed	6
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbDo
				(TPrint "You'll have to pay first, Larry!")
			)
			(verbTalk
				(Say ego "\"Oh, yeah, Lana? I'm not afraid of you,\"
					you say quietly.")
			)
			(verbZipper
				(TPrint "She's waiting for you to climb in that ring and
					do exactly that!")
			)
			(else
				(super doVerb: theVerb invItem)
			)
		)
	)
)

(instance robe of Prop
	(properties
		view			vLanaWrestling
		loop			3
		x				300
		y				55
		description	"her robe"
		lookStr		"She threw away her robe!"
	)
)

(instance ring of View
	(properties
		view			vRoom
		x				147
		y				107
		priority		4
		signal		(| fixPriOn ignrAct)
		description	"the mud wrestling ring"
		lookStr		"Welcome to \"Tramp This! Mud Night Madness\"
						mud wrestling ring."
	)
)

(instance ropes of View
	(properties
		view			vRoom
		cel			1
		x				147
		y				107
		priority		8
		signal		(| fixPriOn ignrAct)
		description	"the ropes"
		lookStr		"Welcome to \"Tramp This! Mud Night Madness\"
						mud wrestling ring."
	)
)

(instance bouncer of Actor
	(properties
		view 			vBouncer
		x 				16
		y 				161
		approachX	16
		approachY	168
 		description "the bouncer"
		lookStr 		"You feel certain this bouncer is not nearly as wise
						as you in the ways of women."
		sightAngle 	40
		signal		ignrAct
	)
	(method (doVerb theVerb invItem &tmp [str 100])
		(switch theVerb
			(verbLook
				(if (not payoff)
					(TPrint "The gentleman blocking your path will be glad
						to allow you to pass--for a price.")
				else
					(TPrint "Since you've paid, he now treats you with all
						due respect.")
					(TPrint "(Evidently you have none due!)" LOW)
				)
			)
			(verbZipper
				(TPrint "He'd bounce you a long way if you tried that!")
			)
			(verbTalk
				(switch mainEvent
					(none
						(Say bouncerTalker
							"\"Da ballroom's closed, Mac!  
							Beat it, before I lose my delly-cate sense of humor.\""
							#dispose:
						)
					)
					(contest
						(if (< (ego y?) y)
							(Say bouncerTalker
								"\"There's one good seat down front,
								right at ringside!\""
								#dispose:
							)
						else
							(if (Random 0 1)
								(Say bouncerTalker
									"\"$25.00, that's the cover charge and ya ain' 
									gettin' in fer less!\""
									#dispose:
								)
							else
								(Say bouncerTalker
									"\"Plenty o' good seats lef, Bud! 
									Butcha ain' gettin' past me 
									'lessin' ya pays ma price!\""
									#dispose:
								)
							)
						)
					)
					(wrestling
						(if (< (ego y?) y)
							(Say bouncerTalker
								"\"You wanna chance to play wit' da goils?
								It'll cost ja $500.00, please!
								Up front.
								No refunds when ya get beat!\""
								#dispose:
							)
						else
							(Say bouncerTalker
								"\"If ya wanna watch, I kin letcha in for a measly
								$25.00.  
								If ya wanna participate, it'll cost ya 5 big ones!  
								(Need I mention, it's worth it, too!)\""
								#dispose:
							)
						)
					)
				)
			)
			(verbUse
				(switch invItem
					(iSilverDollar
						(= payoff
							(switch mainEvent
								(none
									0
								)
								(contest
									25
								)
								(wrestling
									(if lana'sOut
										500
									else
										(PrintD {\"What's it gonna be?\"}
											#new
											#title	{The Bouncer}
											#button 	{I'll just watch}	25
											#button 	{Lemme at 'em!}	500
										)
									)
								)
							)
						)
						(cond
							((not payoff))
							(paid
								(if justWatching
									(Say bouncerTalker
										"\"Ya paid yer money. Now sit down!\""
										#dispose:
									)
								else
									(Say bouncerTalker
										"\"Ya paid yer money. Now get in there!\""
										#dispose:
									)
								)
							)
							((ego sitting?)
								(ego setScript: sStandUp)
							)
							((> payoff silvDollars)
								(Format @str
									"\"Excuse me, sir,\" you say to the hopefully
									friendly bouncer. \"Would it be okay if I only
									paid you $%d.00? I seem to be a little short!\""
									silvDollars
								)
								(Say ego @str)
								(TPrint "The bouncer scowls at you.")
								(Say bouncerTalker
									"\"Yeah, and not just in that wimpy little fake
									eelskin wallet, Donkey-doo! 
									Get out of here!\""
									#dispose:
								)
							)
							(else
						 		(if (== payoff 25)
									(Say ego "\"Hello, Mister,\" you say to the bouncer.
										\"I'd like to purchase one of your better seats.
										Here's my $25.00.\""
									)
									(Say bouncerTalker
										"\"Thanks for nuttin'. 
										Ya gets da best seat in da house--which is 
										also da only seat in da house!\""
										#dispose:
									)
									(= justWatching TRUE)
								else
									(Points ptsPaidBouncer pfPaidBouncer)
									(Say ego "\"Hello, Mister,\" you say to the bouncer.
										\"I'd like to participate in the evening's
										next event. Here's my $500.00.\""
									)
									(if lana'sOut
										(Say bouncerTalker
											"\"Thanks, suck... uh, er, Sir!
											Now if you'll kindly step into the ring,
											the beatin... ah, uh, the show will begin.\""
											#dispose:
										)
									else
										(Say bouncerTalker
											"\"Thanks, suck... uh, er, Sir!
											Now if you'll kindly sit down,
											the beatin... ah, uh, the show will begin.\""
											#dispose:
										)
									)
									(= justWatching FALSE)
								)
								(-= silvDollars payoff)
								(if
									(not silvDollars)
									(ego put: iSilverDollar 0)
								)
								(= paid TRUE)
								(if (not lana'sOut)
									(self
										setCycle: 	EndLoop self,
										approachX:	43,
										approachY:	153
									)
								)
							)
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
	(method (cue)
		(self
			setCel:	0,
			posn:		(- x 16) y,
			stopUpd:
		)
		((curRoom obstacles?) dispose:)
		(curRoom addObstacle:
			((Polygon new:)
				type: PBarredAccess,
				init:	0 0 319 0 319 189 18 189 18 181 93 130 116 130 116 121 68 121
						35 138 35 164 0 164,
				yourself
			)
		)
	)
)

(instance herEyes of Prop
	(properties
		view				vT&ACloseUp
		loop				lWinking
		x					150
		y					57
		signal			ignrAct
		cycleSpeed		6
	)
	(method (doVerb theVerb invItem)
		(curRoom doVerb: theVerb)
	)
)

(instance herMouth of Prop
	(properties
		view				vT&ACloseUp
		loop				lKissing
		x					150
		y					57
		signal			ignrAct
		cycleSpeed		6
	)
	(method (doVerb theVerb invItem)
		(curRoom doVerb: theVerb)
	)
)

;ษอออออออออป
;บ         บ
;บ Talkers บ
;บ         บ
;ศอออออออออผ


(instance bouncerTalker of Talker
	(properties
		name			"The Bouncer"
		view			vBouncerTalker
		loop			3
		nsLeft		20
		nsTop			15
		viewInPrint	TRUE
	)
	(method (init)
		(= bust 	bouncerBust)
		(= eyes 	bouncerEyes)
		(= mouth bouncerMouth)
		(super init: &rest)
	)
)

(instance bouncerBust of Prop
	(properties
		view			vBouncerTalker
		loop			1
		nsLeft		0
		nsTop			0
	)
)

(instance bouncerEyes of Prop
	(properties
		view			vBouncerTalker
		loop			2
		nsLeft		0
		nsTop			0
		cycleSpeed	20
	)
)

(instance bouncerMouth of Prop
	(properties
		view			vBouncerTalker
		loop			0
		nsLeft		9
		nsTop			53
		cycleSpeed	8
	)
)


(instance lanaTalker of Talker
	(properties
		name			"Lana"
		view			vLanaTalker
		loop			3
		nsLeft		15
		nsTop			15
		viewInPrint	TRUE
	)
	(method (init)
		(= bust  lanaBust)
		(= eyes 	lanaEyes)
		(= mouth lanaMouth)
		(super init: &rest)
	)
)

(instance lanaBust of Prop
	(properties
		view			vLanaTalker
		loop			1
		nsLeft		0
		nsTop			0
	)
)

(instance lanaEyes of Prop
	(properties
		view			vLanaTalker
		loop			2
		nsLeft		29
		nsTop			31
		cycleSpeed	30
	)
)

(instance lanaMouth of Prop
	(properties
		view			vLanaTalker
		loop			0
		nsLeft		23
		nsTop			40
	)
)


;ษอออออออออออออออออออป
;บ                   บ
;บ Features          บ
;บ                   บ
;ศอออออออออออออออออออผ

(instance stage of Feature
	(properties
		x 				152
		y 				85
		nsLeft 		91
		nsTop 		67
		nsBottom 	103
		nsRight 		214
 		description "the stage"
		lookStr 		"The stage is where all the action takes place."
		sightAngle 	40
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbDo
				(TPrint "Of course, you want to go on stage, Larry,
					but first you must pay the bouncer his due.")
			)
			(else
				(super doVerb: theVerb invItem &rest)
			)
		)
	)
)

(instance runway of Feature
	(properties
		x 				260
		y 				83
		nsLeft 		202
		nsTop 		77
		nsBottom 	89
		nsRight 		319
 		description "the runway"
		lookStr 		"You wonder what sort of babes must lie in
						wait down that runway."
		sightAngle 	40
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbDo
				(TPrint "You can't get on the runway, Larry.
					At least not yet!")
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


;ษอออออออออออออออออออป
;บ                   บ
;บ Procedures			บ
;บ                   บ
;ศอออออออออออออออออออผ


(procedure (InitFeatures)
	(runway init:)
	(stage init:)
)

(procedure (DisposeFeatures)
	(runway dispose:)
	(stage dispose:)
)


