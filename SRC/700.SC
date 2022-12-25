;**
;**	Logics for Room 700 -- Tramp Casino exterior
;**
;**	LSL5:  Passionate Patti Does A Little Undercover Work
;**				Copyright 1991
;**				by Al Lowe
;**
;**	Programmed by Brian K. Hughes
;**
;**	Last Update:  September 10, 1991
;**

(script#		700)

(public
	rm700		0
)


(define 	vAirportExteriorDoor 251)

(define	pCasinoExterior		700)
(define	vCasinoExterior		700)
	(define	lLimo					0)
;	(define	lFlashyLights		1-7)
	(define	lBirdFlying			8)
;	(define	lFlashyLights		9-15)
(define	vCasinoChangeGirl		702)
	(define	lGivingMoney		1)
(define	vCasinoDoorman			703)
	(define	lHailingLimo		0)
	(define	lLookingAway		1)
	(define	lGettingTip			2)

(define	vChangeGirlTalker		1702)
(define	vDoormanTalker			1703)

(define	mCasino					710)
(define	sElevatorDoor			622)

(local
	seenMsg
	cycleTime
)

(instance rm700 of LLRoom
	(properties
		picture		pCasinoExterior
		north			710
		south			780
		east			760
		lookStr		"Tramp's Casino is a perfect example of the style of
						architecture exemplified by Frank Lloyd Wrong."
	)

	(method (init)
		(LoadMany SOUND
			mCasino
			sElevatorDoor
			sCarPullsUp
			sCarDriveAway
			sCarDoorOpen
			sCarDoorClose
		)
		(ego
			init:			,
			normalize: 	vLarrySmallest
		)

		(switch prevRoomNum
			(750
				(HandsOn)
				(ego
					posn:			117 164,
					setHeading:	180,
					edgeHit:		0
				)
				(Bset fDidLana)
			)
			(north
				(HandsOn)
				(ego
					posn:			117 164,
					setHeading:	180,
					edgeHit:		0
				)
				(theMusic fade: 80 10 10 FALSE)
			)
			(south
				(self setScript: sFromSouth)
			)
			(east
				(ego y: 155)
			)
			(LIMO
				(HandsOff)
				(self setScript: sFromLimo)
			)

			; DEBUG
			(else
				(self setScript: sFromLimo)
			)
		)

		(super init:)

		;init Props

		(doorman 		init:, approachVerbs: verbTalk)
		(if (!= prevRoomNum 750)
			(changeGirl
				init:,
				approachVerbs: verbTalk
			)
		)

		(trampSign 		init:)
		(leftLight		init:)
		(rightLight 	init:)
		(slotSign 		init:)
		(bird 			init:)
		(leftNip 		init:)
		(rightNip 		init:)
		(centerNip		init:)
		(leftRoller
			init:			,
			setCycle:	Forward,
			setScript:  sRoll
		)
		(middleRoller
			init:			,
			setCel:		(Random 1 2),
			setCycle:	Forward
		)
		(rightRoller
			init:			,
			setCel:		3,
			setCycle:	Forward
		)
		(leftNeon		init:)
		(rightNeon		init:)
		(centerNeon		init:)
		(dollars			init:)
		(door				init:, approachVerbs: verbDo)

		;init Features

		(ass1 			init:)
		(ass2 			init:)
		(ass3 			init:)
		(breasts1		init:)
		(breasts2		init:)
		(breasts3		init:)
		(girl1 			init:)
		(girl2 			init:)
		(girl3 			init:)
		(burgerStand	init:)
		(boardwalk		init:)
		(beach			init:)
		(trampSignF		init:)
		(leftLightF		init:)
		(rightLightF	init:)
		(slotSignF		init:)
		(leftNeonF		init:)
		(rightNeonF		init:)
		(centerNeonF	init:)
		(dollarsF		init:)

		(curRoom addObstacle:
			((Polygon new:)
				type: PBarredAccess,
				init:	221 176 231 172 319 172 319 189 298 189,
				yourself
			)
			((Polygon new:)
				type: PBarredAccess,
				init:	98 163 84 163 84 161 3 161 3 169 173 169 197 176 216 189
						0 189 0 0 319 0 319 151 253 151 253 156 228 161 152 161
						148 163 135 163 121 156 121 140 115 140 115 155,
				yourself
			)
		)

		(if
			(== prevRoomNum (curRoom north?))
			(theMusic fade: 80 10 10 FALSE)
		else
			(if
				(!= prevRoomNum 200)
				(theMusic
					number:		mCasino,
					setLoop:		-1,
					flags:		1,
					play:			80,
				)
			)
		)
	)
	(method (doit)
		(super doit: &rest)
		(if
			(and
				(Btst isVga)
				(> (Abs (- gameTime cycleTime)) 6)
			)
			(= cycleTime gameTime)
			(Palette PALCycle 33 48 -1)
			(Palette PALCycle 227 231 -1)

		)
		(cond
			(script)
			(	(> (ego y?) 185)
				(HandsOff)
				(self setScript:	sOcean)
			)
		)
	)
	(method (newRoom n)
		(super newRoom: n)
		(if
			(== n 710)
 			(theMusic fade: 127 10 10 FALSE)
		else
			(theMusic fade:)
		)
		(cond
			((== script sOcean)
				NULL
			)
			(else
				(super newRoom: n)
			)
		)
	)
)


;ษอออออออออออออออออออป
;บ                   บ
;บ Scripts           บ
;บ                   บ
;ศอออออออออออออออออออผ

(instance sRoll of Script
	(method (changeState newState)
		(switchto (= state newState)
			(
				(= seconds 5)
			)
			(
				(leftRoller
					x:			96,
					y:			48,
					setLoop:	7,
				)
				(middleRoller
					hide:
				)
				(rightRoller
					hide:
				)
				(= seconds 5)
			)
			(
				(leftRoller
					x:			99,
					y:			51,
					setLoop:	14,
				)
				(middleRoller
					show:
				)
				(rightRoller
					show:
				)
				(self init:)
			)
		)
	)
)

(instance sFromSouth of Script
	(method (changeState newState)
		(switchto (= state newState)
			(
				(ego
					posn:			320 220,
					setMotion: 	PolyPath 220 180 self
				)
			)
			(
				(HandsOn)
				(self dispose:)
			)
		)
	)
);sFromSouth

(instance sOcean of Script
	(method (changeState newState)
		(switchto (= state newState)
			(
				(ego
					setMotion:	MoveTo 320 220 self
				)
			)
			(
				(curRoom newRoom: 780)
			)
		)
	)
)

(instance sFromLimo of Script
	(method (doit)
		(super doit: &rest)
		(if
			(and
				(== (soundFX prevSignal?) 10)
				(== (soundFX number?) sCarDriveAway)
			)
			(soundFX prevSignal: 5)
			(self cue:)
		)
	)
	(method (changeState newState)
		(switchto (= state newState)
			(
				(limo init:)
				(ego hide:)
				(= ticks 150)
			)
			(
				(soundFX
					number:		sCarDoorOpen,
					play:
				)
				(= ticks 50)	; when sys bug fixed, do play: self instead
			)
			(
				(= ticks 60)
			)
			(
				(ego
					normalize:		vLarrySmallest,
					x:					107,
					y:					177,
					setLoop:		 	3,
					show:				,
					setCel:		  	0,
					setMotion:		MoveTo 107 166 self
				)
			)
			(
				(ego
					setLoop:			RELEASE,		; Grooper won't turn if fixedLoop
					setHeading: 	180 self
				)
			)
			(
				(= ticks 60)
			)
			(
				(soundFX
					number:		sCarDoorClose,
					play:
				)
				(= ticks 100)	; when sys bug fixed, do play: self instead
			)
			(
				(theMusic
					number:		mCasino,
					setLoop:		-1,
					flags:		1,
					play:			80,
				)
				(soundFX
					number:		sCarDriveAway,
					play:
				)
			)
			(;
				(limo
					setStep:  		1 1,
					setMotion:		MoveTo 87 (limo y?) self
				)
			)
			(
				(limo
					setStep:	 		3 3,
					setMotion:		MoveTo 65 (limo y?) self
				)
			)
			(
				(limo
					setStep:	 		4 4,
					setMotion:		MoveTo 50 (limo y?) self
				)
			)
			(
				(limo
					setStep 			5 5,
					setMotion		MoveTo 35 (limo y?) self
				)
			)
			(
				(limo
					setStep 			6 6,
					setMotion		MoveTo -95 (limo y?) self
				)
			)
			(
			 	(limo dispose:)
			 	(ego 	normalize: vLarrySmallest)
			 	(HandsOn)
				(= cycles 1)
			)
			(
				(self dispose:)
				(if
					(Print "Now would be an excellent time for you to save your
						game, Larry."
						#button		{Save} 				1
						#button		{Nah, Why Bother?} 0
						#title		{AL says}
						HIGH
					)
					(theGame save:)
				)
			)
		)
	)
);sFromLimo

(instance sLimo of Script
	(method (doit)
		(super doit: &rest)
		(if
			(and
				(== (soundFX prevSignal?) 10)
				(== (soundFX number?) sCarDriveAway)
			)
			(soundFX prevSignal: 5)
			(self cue:)
		)
	)
	(method (changeState newState &tmp [str 110])
		(switchto (= state newState)
			(
				(ego setHeading: 270)
				(doorman
					setLoop:		lHailingLimo,
					setCel:		0,
					setCycle:	Oscillate FORWARD self
				)
			)
			(
				(doorman
					setLoop: lLookingAway,
					setCel:	0
				)
				(soundFX
					number:			sCarPullsUp,
					play:				,
					hold:				10,
				)
				(limo
					posn: 			-100 178,
					init:				,
					approachVerbs: verbDo,
					setMotion:		MoveTo 70 178 self
				)
			)
			(
				(soundFX hold:	RELEASE)
				(ego setHeading: 90 self)
			)
			(
				(switch silvDollars
					(0
						(TPrint "\"It's certainly too bad that I have no silver
							dollars to tip you,\" you quip to the unamused fellow."
						)
						(doorman
							setLoop:		lLookingAway,
							setCel:		0,
							setCycle:	EndLoop self
						)
					)
					(1
						(TPrint "\"All I have is one silver dollar,\" you tell
							the doorman. \"Normally, I'm quite the generous tipper!\""
						)
						(doorman
							setLoop:		lGettingTip,
							setCel:		0,
							setCycle:	Oscillate FORWARD self
						)
					)
					(else
						(Format @str "Totally out of character for your cheap self,
							you decide to give the doorman a generous tip.
							\"Here you are, my good fellow,\" you quip,
							\"take these %d silver dollars.
							I'll have no use for them where I'm going!\""
							silvDollars
						)
						(TPrint @str)
						(doorman
							setLoop:		lGettingTip,
							setCel:		0,
							setCycle:	Oscillate FORWARD self
						)
					)
				)
			)
			(
				(switch silvDollars
					(0
						(Say doormanTalker "\"Yeah, that's what Mr. Lowe says, 
							too.\""
							#dispose:
							#caller:		self
						)
					)
					(1
						(Say doormanTalker "\"Right.\""
							#dispose:
							#caller:		self
						)
					)
					(else
						(Say doormanTalker "\"WOW! Come back again, Sir,
							and soon!\""
							#dispose:
							#caller: 	self
						)
					)
				)
				(ego put: iSilverDollar 0)
				(= silvDollars 0)
			)
			(
				(ego
					setMotion:		PolyPath (limo approachX?) (limo approachY?) self
				)
			)
			(
				(ego
					setHeading:		180 self
				)
			)
			(
				(= ticks 60)
			)
			(
				(soundFX
					number:			sCarDoorOpen,
					play:
				)
				(= ticks 50)
			)
			(
				(ego
					setLoop:			2,
					setCycle:		0,
					setPri:			(ego priority?),
					setMotion:		MoveTo (ego x?) (+ (ego y?) 6) self
				)
			)
			(
				(ego x: 1000)
				(= ticks 60)

			)
			(
				(soundFX
					number:			sCarDoorClose,
					play:
				)
				(= ticks 50)
			)
			(
				(= ticks 60)
			)
			;this commented out because QA said limo isn't moving when you go to 200
;**				(
;**					(soundFX
;**						number:			sCarDriveAway,
;**						play:
;**					)
;**				)
;**				(
;**					(limo
;**						setStep:	 		3 3,
;**						setMotion:		MoveTo 65 (limo y?) self
;**					)
;**				)
;**				(
;**					(limo
;**						setStep:	 		4 4,
;**						setMotion:		MoveTo 50 (limo y?) self
;**					)
;**				)
;**				(
;**					(limo
;**						setStep:			5 5,
;**						setMotion:		MoveTo 35 (limo y?) self
;**					)
;**				)
;**				(
;**					(limo
;**						setStep:			6 6,
;**						setMotion:		MoveTo -95 (limo y?) self
;**					)
;**			 	)
			(
				(curRoom newRoom: 200)
			)
		)
	)
);sLimo

(instance sGirlTalk of Script
	(method (changeState newState &tmp guess num [str 30] saveX saveY)
		(switchto (= state newState)
			(
			 	(= guess 0)
				(if (not (HaveMouse))
					(= saveX mouseX)
					(= saveY (- mouseY 10))
					(theGame setCursor: theCursor TRUE 500 500)
				)
			 	(while (not (< 0 guess 10000))
			 		(= str 0)
			 		(GetInput @str 6
			 			{\"Please enter a number less than 10000, ok?\"}
						#title: {Cheri}
					)
			 		(= guess (ReadNumber @str))
			 	)
				(if (not (HaveMouse))
					(theGame setCursor: theCursor TRUE saveX saveY)
				)
			 	(if (not (ego has: iSilverDollar))
			 		; We win automatically if we're broke
			 		(Say girlTalker "\"Amazing! What a genius!
						What a coincidence!
						Congratulations, Sir!
						You have correctly guessed the number of which
						I have been thinking of.
						Here's your ten `Tramp Casino' lucky souvenir
						silver dollars.\""
						#dispose:
			 			#caller: self
			 		)
				else
			 		; We lose automatically if we have a silver dollar already
					; Find a number that we DIDN'T guess
			 		(= num guess)
			 		(while (== guess num)
			 			(= num (Random 1 6900))
			 		)
					(Format @str "\"Aw, too bad! I was thinking of %d.\"" num)
					(Say girlTalker @str
						#dispose:
						#caller: self
					)
				)
			)
			(
				(if (ego has: iSilverDollar)	; we lost
					(Say girlTalker "\"But feel free to try again any time!\""
						#dispose:
						#caller:		self
					)
				else
					(Say girlTalker "\"Please don't confuse them with
						REAL money!\""
						#dispose:
					)
					(changeGirl
						setLoop:		lGivingMoney,
						setCel:		0,
						setCycle:	Oscillate FORWARD self
					)
			 		(ego get: iSilverDollar)
			 		(= silvDollars 10)
				)
			)
			(
				(changeGirl setLoop: 0)
				(self dispose:)
			)
		)
	)
);sGirlTalk



;ษอออออออออออออออออออป
;บ                   บ
;บ Objects           บ
;บ                   บ
;ศอออออออออออออออออออผ

(instance bird of Actor
	(properties
		view			vCasinoExterior
		loop			lBirdFlying
		xStep			7
		yStep			5
		illegalBits	$0000
		description "the seagull"
		lookStr		"A lonely seagull flies back and forth,
						searching for a likely target--you!"
		priority		14
		signal		(| fixedLoop ignrAct ignrHrz fixPriOn)
	)
	(method (init)
		(super init:)
		(self
			setCycle:	Forward,
			cue:
		)
	)
	(method (cue &tmp theX theY)
		(= theX (- (Random 0 520) 100))
		(= theY (- (Random 0 150) 50))
		(self setMotion: MoveTo theX theY self)
	)
)

(instance limo of Actor
	(properties
		view			vCasinoExterior
		loop			lLimo
		x				93
		y				180
		approachX	80
		approachY	175
		signal		(| fixedLoop ignrAct)
		description	"the limousine"
		lookStr		"Your limousine has returned.
						Or, perhaps, one suspiciously like it!"
	)
)

(instance door of Door
	(properties
		view				vAirportExteriorDoor
		x					118
		y					155
		openSnd 			sElevatorDoor
		closeSnd 		sElevatorDoor
		entranceTo		710
		locked			FALSE
		state				doorClosed
		forceOpen		FALSE
		moveToX			118	;120
		moveToY			154
		approachX		120	;123
		approachY		157
		enterType		doorWalkEgo
		exitType			doorWalkEgo
 		description 	"the front door of Tramp's Casino"
		lookStr 			"A small bronze plaque over the door reads,
							\"Through these doors pass the greatest people
							in the world--OUR SUCKERS!\""
		sightAngle 		40
		cycleSpeed		10
	)
);door

(instance doorman of Prop
	(properties
		view			vCasinoDoorman
		loop			1
		x				141
		y				162
		signal		ignrAct
		approachX	133
		approachY	165
 		description "Brewster"
		lookStr 		"Tramp's Casino thoughtfully provides a doorman to
						satisfy your every travel need--as long as all you need
						is for him to summon you a limousine!"
		sightAngle 	40
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbDo
				(self doVerb: verbTalk)
			)
			(verbTalk
				(Say doormanTalker "\"May I summon a limousine for you, Sir?\""
					#dispose:
					#caller:		self
				)
			)
			(verbZipper
				(Say doormanTalker "\"Well, you're a little ugly,
					but I suppose.... Oh, okay. I get off at midnight!\""
					#dispose:
				)
			)
			(verbUse
				(Say doormanTalker "\"Thanks for the offer,
					but we employees of Tramp's NEVER accept gratuities!\""
					#dispose:
				)
			)
			(else
				(super doVerb: theVerb invItem &rest)
			)
		)
	)
	(method (cue)
		(if (Print "\"So? Ya wanna limo or not?\""
				#button 	{No, thank you}	0
				#button 	{Yes, please}		1
				#title	{Your Friendly Doorman}
			)
			(HandsOff)
			(Say ego "\"Yes, please... but could you make it back in?\"")
			(Points ptsDoorman pfDoorman)
			(Say doormanTalker "\"No problem,\" says Brewster.
				\"Just watch this!\""
				#dispose:
			)
			(curRoom setScript: sLimo)
		else
			(Say ego "\"No, thank you.
				But I may well come back this way later.\"")
		)
	)
)

(instance changeGirl of Prop
	(properties
		view			vCasinoChangeGirl
		x				96
		y				162
		approachX	103
		approachY	164
		signal		ignrAct
 		description "the casino greeter"
		lookStr 		"Tramp's Casino employs a beautiful young woman
						to greet all its distinguished visitors."
		sightAngle 	40
	)

	(method (init)
		(super init:)
		(self approachVerbs: verbNone)
	)

	(method (doVerb theVerb invItem &tmp guess num [str 30])
		(switch theVerb
			(verbDo
				(Say girlTalker "\"Welcome to Tramp's Casino, young man!
					I'm here to welcome you because I'm the welcomer,
					so `Welcome!'\""
					#dispose:
				)
			)
			(verbTalk
				(if (not seenMsg)
				 	(Say girlTalker "\"Hey fella! Wanna get lucky?
						Guess the number of which I'm thinking of.
						You might just win 10 `silver dollars!'\""
						#dispose:
					)
					(= seenMsg TRUE)
					(self approachVerbs: verbTalk)
				else
					(Points ptsChangeGirl pfChangeGirl)
					(self setScript: sGirlTalk)
				)
			)
			(verbZipper
				(Say girlTalker "\"Hey! I may be standing here on this street
					corner, but I'm not that kind of working girl!
					Ya want I havta call my boyfriend the bouncer?\""
					#dispose:
				)
			)
			(else
				(super doVerb: theVerb invItem &rest)
			)
		)
	)
)

(instance trampSign of Prop
	(properties
		view			vCasinoExterior
		loop			1
		x				120
		y				100
		detailLevel	1
		cycleSpeed	3
		signal		ignrAct
		description "the Tramp sign"
		lookStr		"It says you're in front of Tramp's Casino."
	)
	(method (init)
		(self setCycle: Forward)
		(super init:)
	)
)

(instance slotSign of Prop
	(properties
		view			vCasinoExterior
		loop			4
		x				74
		y				38
		detailLevel	2
		cycleSpeed	5
		signal		ignrAct
		description	"the slot machine sign"
		lookStr		"You presume there must be some form of mechanized gambling
						within."
	)
	(method (init)
		(self setCycle: Forward)
		(super init:)
	)
)

(instance leftLight of Prop
	(properties
		view			vCasinoExterior
		loop			2
		cel			0
		x				67
		y				123
		detailLevel	2
		cycleSpeed	2
		signal		ignrAct
		description	"the light"
		lookStr		"Ain't neon grand!"
	)
	(method (init)
		(self setCycle: Forward)
		(super init:)
	)
)

(instance rightLight of Prop
	(properties
		view			vCasinoExterior
		loop			3
		cel			1
		x				173
		y				124
		detailLevel	2
		cycleSpeed	4
		signal		ignrAct
		description	"the light"
		lookStr		"Ain't neon grand!"
	)
	(method (init)
		(self setCycle: Forward)
		(super init:)
	)
)

(instance leftNip of Prop
	(properties
		view			vCasinoExterior
		loop			6
		cel			0
		x				43
		y				41
		cycleSpeed	1
		detailLevel	3
		signal		ignrAct
		description	"the neon nipples"
	)
	(method (init)
		(self setCycle: Forward)
		(super init:)
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbLook
				(if
					(Random 0 1)
					(TPrint
						"You really enjoy this part of the game!"
					)
				else
					(TPrint "You haven't seen neon pasties since the late '60's!")
				)
			)
			(verbDo
				(if
					(Random 0 1)
					(TPrint "You are desperate for entertainment!")
				else
					(TPrint "Cheap feel!")
				)
			)
			(verbTalk
				(TPrint "Keep your mouth off those!")
			)
			(else
				(super doVerb: theVerb invItem &rest)
			)
		)
	)
)

(instance rightNip of Prop
	(properties
		view			vCasinoExterior
		loop			5
		cel			1
		x				220
		y				68
		detailLevel	3
		cycleSpeed	1
		signal		ignrAct
		description	"the neon nipples"
	)
	(method (init)
		(self setCycle: Forward)
		(super init:)
	)
	(method (doVerb theVerb invItem)
 		(leftNip doVerb: theVerb invItem &rest)
	)
)

(instance centerNip of Prop
	(properties
		view			vCasinoExterior
		loop			12
		cel			2
		x				159
		y				27
		detailLevel	3
		cycleSpeed	1
		signal		ignrAct
		description	"the neon nipples"
	)
	(method (init)
		(self setCycle: Forward)
		(super init:)
	)
	(method (doVerb theVerb invItem)
 		(leftNip doVerb: theVerb invItem &rest)
	)
)

(instance leftRoller of Prop
	(properties
		view			vCasinoExterior
		loop			14
		x				99
		y				51
		cycleSpeed	7
		detailLevel	1
		signal		ignrAct
		description	"the slot machine sign"
		detailLevel	3
	)
	(method (init)
		(self setCycle: Forward)
		(super init:)
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbLook
				(if (Random 0 1)
					(TPrint "That slot machine NEVER stops on a winner!")
				else
					(TPrint "That slot machine NEVER stops on a loser!")
				)
			)
			(verbDo
				(TPrint "If it's handles you want to pull,
					why not go inside?")
			)
			(else
				(super doVerb: theVerb invItem &rest)
			)
		)
	)
)

(instance middleRoller of Prop
	(properties
		view			vCasinoExterior
		loop			14
		x				110
		y				51
		signal		ignrAct
		description	"the slot machine sign"
		detailLevel	3
	)
	(method (doVerb theVerb invItem)
		(leftRoller doVerb: theVerb invItem)
	)
)

(instance rightRoller of Prop
	(properties
		view			vCasinoExterior
		loop			14
		cel			3
		x				122
		y				51
		signal		ignrAct
		description	"the slot machine sign"
		detailLevel	3
	)
	(method (doVerb theVerb invItem)
		(leftRoller doVerb: theVerb invItem)
	)
)

(instance leftNeon of Prop
	(properties
		view			vCasinoExterior
		loop			9
		x				52
		y				0
		z				-56
		detailLevel	5
		cycleSpeed	5
		signal		ignrAct
		description	"the neon sign"
		lookStr		"How you love gaudy things!"
	)
	(method (init)
		(self setCycle: Forward)
		(super init:)
	)
)

(instance rightNeon of Prop
	(properties
		view			vCasinoExterior
		loop			10
		x				195
		y				0
		z				-70
		detailLevel	5
		cycleSpeed	4
		signal		ignrAct
		description	"the neon sign"
		lookStr		"How you love gaudy things!"
	)
	(method (init)
		(self setCycle: Forward)
		(super init:)
	)
)

(instance centerNeon of Prop
	(properties
		view			vCasinoExterior
		loop			11
		x				125
		y				38
		detailLevel	4
		cycleSpeed	3
		signal		ignrAct
		description	"the neon sign"
		lookStr		"How you love gaudy things!"
	)
	(method (init)
		(self setCycle: Forward)
		(super init:)
	)
)

(instance dollars of Prop
	(properties
		view			vCasinoExterior
		loop			13
		x				121
		y				115
		detailLevel	3
		cycleSpeed	9
		signal		ignrAct
		description	"the dollar sign"
		lookStr		"The implication here is that a fella could win some
						money inside this place!"
	)
	(method (init)
		(self setCycle: Forward)
		(super init:)
	)
)


;ษอออออออออออออออออออป
;บ                   บ
;บ Features          บ
;บ                   บ
;ศอออออออออออออออออออผ

(instance burgerStand of Feature
	(properties
		x 				280
		y 				130
		nsLeft 		241
		nsTop 		111
		nsBottom 	149
		nsRight 		319
 		description "the burger stand"
		lookStr 		"How exciting!  There's a restaurant along the
						Tramp Boardwalk that serves dead cow!"
		sightAngle 	40
	)
	(method (init)
		(super init:)
		(self approachVerbs: verbNone)
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbDo
				(TPrint "You wouldn't eat there, even if there was a door!")
			)
			(else
				(super doVerb: theVerb invItem &rest)
			)
		)
	)
)

(instance boardwalk of Feature
	(properties
		x 				280
		y 				161
		nsLeft 		241
		nsTop 		150
		nsBottom 	173
		nsRight 		319
 		description "the boardwalk"
		lookStr 		"The boardwalk leads off to the east,
						stretching nearly as far as the eye can see."
		sightAngle 	40
	)
)

(instance beach of Feature
	(properties
		x 				183
		y 				109
		nsLeft 		234
		nsTop 		176
		nsBottom 	190
		nsRight 		320
 		description "the beach"
		lookStr 		"That little strip of sand leads to a precipitous drop,
						then straight into the Atlantic! Care for a swim?"
		sightAngle	40
	)
)

(instance breasts1 of Feature
	(properties
		x 				42
		y 				55
		nsLeft 		31
		nsTop 		33
		nsBottom 	42
		nsRight 		53
 		description "the lights"
		sightAngle 	40
	)
	(method (doVerb theVerb invItem)
		(leftNip doVerb: theVerb invItem)
	)
)

(instance breasts2 of Feature
	(properties
		x 				156
		y 				20
		nsLeft 		148
		nsTop 		11
		nsBottom 	29
		nsRight 		165
		sightAngle 	40
		description "the lights"
	)
	(method (doVerb)
		(leftNip doVerb: &rest)
	)
)

(instance breasts3 of Feature
	(properties
		x 				214
		y 				74
		nsLeft 		205
		nsTop 		60
		nsBottom 	72
		nsRight 		224
		sightAngle 	40
		description "the rear"
	)
	(method (doVerb)
		(leftNip doVerb: &rest)
	)
)

(instance ass1 of Feature
	(properties
		x 				33
		y 				62
		nsLeft 		26
		nsTop 		55
		nsBottom 	70
		nsRight 		43
		description "the rear"
		lookStr 		"Tight butts drive you nuts!"
		sightAngle 	40
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbDo
				(TPrint "It's only a sign, you know!")
			)
			(verbTalk
				(TPrint "\"Have you ever heard of `yodeling up the canyon?'\"
					you ask the sign.")
			)
			(else
				(super doVerb: theVerb invItem &rest)
			)
		)
	)
)

(instance ass2 of Feature
	(properties
		x 				120
		y 				15
		nsLeft 		108
		nsTop 		0
		nsBottom 	16
		nsRight 		133
		sightAngle 	40
	)
	(method (doVerb)
		(ass1 doVerb: &rest)
	)
)

(instance ass3 of Feature
	(properties
		x 				213
		y 				87
		nsLeft 		204
		nsTop 		80
		nsBottom 	95
		nsRight 		223
		sightAngle 	40
		description "the rear"
	)
	(method (doVerb)
		(ass1 doVerb: &rest)
	)
)

(instance girl1 of Feature
	(properties
		x 				34
		y 				54
		nsLeft 		8
		nsTop 		20
		nsBottom 	88
		nsRight 		61
 		description "the girl on the sign"
		lookStr 		"Sure she's only made of plywood, but doesn't she
						have a great set of lights?"
		sightAngle 	40
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbDo
				(TPrint "Touch all you want. She's only a sign!")
			)
			(verbTalk
				(TPrint "\"I'm so horny, I'd talk to a sign,\"
					you say to yourself.")
			)
			(else
				(super doVerb: theVerb invItem &rest)
			)
		)
	)
)

(instance girl2 of Feature
	(properties
		x 				138
		y 				14
		nsLeft 		92
		nsTop 		0
		nsBottom 	28
		nsRight 		185
 		description "the girl"
		lookStr 		"She's in one of your favorite positions... for a sign!"
		sightAngle 	40
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbDo
				(TPrint "\"I'd love to click my cursor on you, Baby,\"
					you think.")
			)
			(verbTalk
				(TPrint "\"Wanna get together somewhere after the game?\"
					you ask the sign.")
			)
			(else
				(super doVerb: theVerb invItem &rest)
			)
		)
	)
)

(instance girl3 of Feature
	(properties
		x 				206
		y 				72
		nsLeft 		180
		nsTop 		39
		nsBottom 	105
		nsRight 		232
 		description "the girl"
		lookStr 		"And you were so afraid you were wasting your
						money buying this game!"
		sightAngle 	40
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbDo
				(TPrint "\"I'll bet she really likes this,\" you think.")
			)
			(verbTalk
				(TPrint "\"I'm available tonight,\" you tell the sign.
					\"At least, I think I am!\"")
			)
			(else
				(super doVerb: theVerb invItem &rest)
			)
		)
	)
)


;ษอออออออออออออออออออป
;บ                   บ
;บ Talkers				บ
;บ                   บ
;ศอออออออออออออออออออผ

(instance girlTalker of Talker
	(properties
		name			"Cheri"
		view			vChangeGirlTalker
		loop			3
		nsLeft		12
		nsTop			15
		viewInPrint	TRUE
	)
	(method (init)
		(= bust 	girlBust)
		(= eyes 	girlEyes)
		(= mouth girlMouth)
		(super init: &rest)
	)
)
(instance girlEyes of Prop					;skip color, no eyes
	(properties
		view			vChangeGirlTalker
		loop			2
		nsLeft		8
		nsTop			8
		cycleSpeed	30
	)
)
(instance girlBust of Prop
	(properties
		view			vChangeGirlTalker
		loop			1
		nsLeft		0
		nsTop			0
	)
)
(instance girlMouth of Prop
	(properties
		view			vChangeGirlTalker
		loop			0
		nsLeft		30
		nsTop			33
		cycleSpeed	8
	)
)

(instance doormanTalker of Talker
	(properties
		name			"Doorman"
		view			vDoormanTalker
		loop			3
		nsLeft		50
		nsTop			15
		viewInPrint	TRUE
	)
	(method (init)
		(= bust 	manBust)
		(= eyes 	manEyes)
		(= mouth manMouth)
		(super init: &rest)
	)
)
(instance manBust of Prop
	(properties
		view			vDoormanTalker
		loop			1
		nsLeft		0
		nsTop			0
	)
)
(instance manEyes of Prop
	(properties
		view			vDoormanTalker
		loop			2
		nsLeft		8
		nsTop			8
		cycleSpeed	30
	)
)
(instance manMouth of Prop
	(properties
		view			vDoormanTalker
		loop			0
		nsLeft		11
		nsTop			31
		cycleSpeed	8
	)
)


;new features:

(instance trampSignF of Feature
	(properties
		x 121
		y 152
		z 69
		heading 0
		nsLeft 88
		nsTop 70
		nsBottom 97
		nsRight 154
		sightAngle 40
		description "the Tramp sign"
		lookStr		"It says you're in front of Tramp's Casino."
	)
)
(instance leftLightF of Feature
	(properties
		x 68
		y 157
		z 58
		heading 0
		nsLeft 62
		nsTop 82
		nsBottom 117
		nsRight 75
		description	"the light"
		lookStr		"Ain't neon grand!"
		sightAngle 40
	)
)
(instance rightLightF of Feature
	(properties
		x 172
		y 158
		z 59
		heading 0
		nsLeft 162
		nsTop 81
		nsBottom 118
		nsRight 182
		description	"the light"
		lookStr		"Ain't neon grand!"
		sightAngle 40
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(else
				(super doVerb: theVerb invItem &rest)
			)
		)
	)
)
(instance slotSignF of Feature
	(properties
		x 62
		y 160
		z 103
		heading 0
		nsLeft 53
		nsTop 37
		nsBottom 78
		nsRight 71
		description	"the slot sign"
		lookStr		"You presume there must be some form of mechanized gambling
						within."
		sightAngle 40
	)
)
(instance leftNeonF of Feature
	(properties
		x 28
		y 157
		z 143
		heading 0
		nsLeft 4
		nsTop 1
		nsBottom 28
		nsRight 53
		description	"the neon sign"
		lookStr		"How you love gaudy things!"
		sightAngle 40
	)
)
(instance rightNeonF of Feature
	(properties
		x 206
		y 157
		z 137
		heading 0
		nsLeft 185
		nsTop 0
		nsBottom 41
		nsRight 228
		description	"the neon sign"
		lookStr		"How you love gaudy things!"
		sightAngle 40
	)
)
(instance centerNeonF of Feature
	(properties
		x 124
		y 158
		z 126
		heading 0
		nsLeft 110
		nsTop 25
		nsBottom 40
		nsRight 139
		description	"the neon sign"
		lookStr		"How you love gaudy things!"
		sightAngle 40
	)
)
(instance dollarsF of Feature
	(properties
		x 124
		y 159
		z 50
		heading 0
		nsLeft 90
		nsTop 102
		nsBottom 116
		nsRight 158
		description	"the dollar sign"
		lookStr		"The implication here is that a fella could win some
						money inside this place!"
		sightAngle 40
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(else
				(super doVerb: theVerb invItem &rest)
			)
		)
	)
)

(instance soundFX of Sound
	(properties
		flags		1
	)
)
