;**
;**	Logics for Room 790 -- Ivana Tramp's Rollerblade Rental Shop
;**
;**	LSL5:  Passionate Patti Does A Little Undercover Work
;**				Copyright 1991
;**				by Al Lowe
;**
;**	Programmed by Oliver Brelsford
;**
;**	Last Update:  September 12, 1991
;**


(script#		790)

(public
	rm790		0
)

(local
	talked
)

(define RENTAL_CHARGE				250)

(define	pRollerbladeRentalShop		790)
(define	vRoom							790)
	(define	lDoorToBackRoom	0)
	(define	lNeonSign			1)
;(define	vLarryRoom					791)
	(define	lSitting				0)
	(define	lChangingShoes		1-2)
(define	vIvanaTramp					792)
;	(define	lWalking				0-3)
;	(define	lTalking				4)
	(define	lBending				5)

(define	vIvanaTalker				1792)

(define  mSkateShop				790)


(instance rm790 of LLRoom
	(properties
		picture		pRollerbladeRentalShop
		south			760
		lookStr		"You are inside the only open shop along the entire
						boardwalk.
						You wonder why this place doesn't join in the celebration
						of Donald Tramp's birthday."
	)

	(method (init)
		(LoadMany VIEW
			vIvanaTalker
			vIvanaTramp
		)
		(ego
			init:			,
			normalize:	,
			actions:		sitActions,
		)
	 	(switch prevRoomNum
			(south
				(HandsOff)
				(ego x: 160)
				(curRoom setScript: sFromSouth)
			)
			(else
				(HandsOn)
				(ego
					posn:		160 160,
					edgeHit:	0
				)
			)
		)
		(super init:)
		(theMusic
			number:		mSkateShop,
			loop:			-1,
			flags:		1,
			play:,
		)

		(skates	init:)
		(curtain
			init:,
			stopUpd:
		)
		(poster1	init:)
		(poster2	init:)
		(poster3	init:)
		(poster4	init:)
		(poster5	init:)
		(poster6	init:)
		(poster7	init:)
		(box		init:)
		(light	init:)
		(desk		init:)

		(curRoom addObstacle:
			((Polygon new:)
				type: PBarredAccess,
				init:	0 0 319 0 319 189 250 189 250 137 224 120 91 120 63 151
						63 189 0 189,
				yourself
			)
			((Polygon new:)
				type: PBarredAccess,
				init:	179 144 217 144 238 157 198 157,
				yourself
			)
		)
	)
	(method (dispose)
		(if (ego has: iRollerblades)
			(Bset fSkated)
		)
		(theMusic fade:)
		(super dispose:)
	)
	(method (doit)
		(super doit:)
		(cond
			(script)
			((StepOn ego cBLUE)
				(if (not (cast contains: Ivana))
					(HandsOff)
					(Ivana
						init:				,
						approachVerbs:	verbUse verbTalk verbDo,
						setCycle:		Walk,
					)
					(curRoom
						setScript:		sIvanaEnters
					)
				)
			)
		)
	)
)


;ษอออออออออออออออออออป
;บ                   บ
;บ Scripts           บ
;บ                   บ
;ศอออออออออออออออออออผ

(instance sIvanaEnters of Script
	(method (changeState newState)
		(switchto (= state newState)
			(
				(Ivana setMotion: MoveTo 121 107 self)
			)
			(
				(curtain setCycle: EndLoop curtain)
				(Ivana setMotion: MoveTo 121 111 self)
			)
			(
				(Ivana
					setPri:		6,
					setMotion: 	MoveTo 150 111 self
				)
			)
			(
				(Ivana setHeading: 180 self)
			)
			(
				(Say ivanaTalker "\"Hiya, Cutie!\" says the older woman behind the
					counter.
					\"Welcome to `Skates, Etc.'
					I'm your hostess for your visit, Ivana Tramp.\""
					#dispose:
				)
				(Ivana stopUpd:)
				(HandsOn)
				(self dispose:)
			)
		)
	)
);sIvanaEnters

(instance sFromSouth of Script
	(method (changeState newState)
		(switchto (= state newState)
			(
				(ego
					y:				250,
					setMotion:  MoveTo 160 180 self
				)
			)
			(
				(HandsOn)
				(self dispose:)
			)
		)
	)
)

(instance sTalk of Script
	(method (changeState newState &tmp num [str 80])
		(switchto (= state newState)
			(
				(= talked TRUE)
				(Say ego "\"Hello,\" you say. \"I'd like to rent some skates.\""
					LOW
				)
				(Say ivanaTalker "\"Good idea; you've come to the right place.
					My rental fee is quite reasonable,
					but there's no use quibbling over such details now.
					When you return the skates, we'll settle up based
					upon the number of minutes you actually skate.\""
					#dispose:
					#caller:		self
				)
			)
			(
				(Format @str "\"But, first, I'll need a small deposit
					for such valuable equipment,\" she says, sizing you up.
					\"Let's say oh, $%d, okay?\""
					RENTAL_CHARGE
				)
				(Say ivanaTalker @str
					#dispose:
					#caller:		self
				)
			)
			(
				(Say ego "\"I don't have any money,\" you say."
					LOW
				)
				(Say ivanaTalker "\"Oh? Well, I suppose I'll accept some more of
					those damned `silver dollars' from the casino.\""
					#dispose:
					#caller:		self
				)
			)
			(
				(HandsOn)
				(self dispose:)
			)
		)
	)
)

(instance sCamcorder of Script
	(method (changeState newState)
		(switchto (= state newState)
			(
				(Say ego "You have an inspiration.
					\"Instead of money,\" you offer,
					\"would you accept this `auto-focus, auto-level, auto-color
					balance, auto-zoom, auto-pan, .5-lux,
					Complete-Video-Studio-in-a-
					Pocket-Protector?'
					It's a `Rob Lowe Model,' you know!\""
					LOW
				)
				(Say ego "\"It's the very latest model and was very
					expensive,\" you continue.
					\"But you've got to promise to give it
					back when I return your skates.\""
					LOW
				)
				(Points ptsGotSkatesWithCamcorder pfGotSkatesWithCamcorder)
				(Say ivanaTalker "\"Hmmmm,\" she muses,
					\"`The Rob Lowe?'
					Yeah, I suppose that'll do for a deposit.\""
					#dispose:
					#caller:		self
				)
			)
			(
				(TPrint "And she takes it from you.")
				(ego
					put: iCamcorder 		790,
					get: iRollerblades,
				)
				(HandsOn)
				(theIconBar curIcon:	(theIconBar at: walkIcon))
				(theGame setCursor: ((theIconBar curIcon?) cursor?))
				(self dispose:)
			)
		)
	)
)

(instance sRollerblades of Script
	(method (changeState newState &tmp num [str 80])
		(switchto (= state newState)
			(
				(Say ego "\"Here's my skates, ma'am,\" you say,
					handing them across the counter.
					\"May I have my deposit back now?\""
					LOW
				)
				(Say ivanaTalker "\"Well, I dunno,\" she hesitates.
					\"First, lemme inspect 'em for damages.\"
					And she diligently inspects every inch of your skates."
					#dispose:
					#caller:		self
				)
			)
			(
				(Points ptsReturnedSkates pfReturnedSkates)
				(cond
					((Btst pfGotSkatesWithMoney)	
						(= num (Random 100 (- RENTAL_CHARGE 10)))
						(Format @str "\"Hey, looky here!
							Ya scuffed this wheel!\" she shrieks.
							\"I'm gonna haf'ta dock yer refund %d bucks!\""
							num)
						(Say ivanaTalker @str
							#dispose:
						)
						(ego
							put: iRollerblades 0,
							get: iSilverDollar
						)
						(+= silvDollars (- RENTAL_CHARGE num))
					)
					(else
						(Say ivanaTalker "\"Hmmm. Well there's a small nick in this
							one wheel,
							but I guess you can have your camcorder back
							anyway, although I was kinda hopin' you'd jes'
							keep the skates.\""
							#dispose:
						)
						(ego
							put: iRollerblades 0,
							get: iCamcorder
						)
					)
				)
				(HandsOn)
				(theIconBar curIcon:	(theIconBar at: walkIcon))
				(theGame setCursor: ((theIconBar curIcon?) cursor?))
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

(instance Ivana of Actor
	(properties
		view 			vIvanaTramp
		x 				147
		y 				107
		priority		3
		signal		fixPriOn
 		description "Ivana Tramp"
		lookStr 		"Ivana Tramp (known locally as \"Wheels, The Blademaster\")
						has plummeted several social levels since her divorce
						from The Donald."
		sightAngle 	40
		cycleSpeed	6
		moveSpeed	6
		approachX	130
		approachY	120
	)
	(method (doVerb theVerb invItem &tmp num [str 80])
		(switch theVerb
			(verbDo
				(TPrint "There's no need to shove her around, Larry;
					she's been shoved enough already!")
			)
			(verbZipper
				(TPrint "Perish the thought!")
			)
			(verbTalk
				(cond
					((ego has: iRollerblades)
						(if (Btst fSkated)
							(Say ivanaTalker "\"So, didja have a good time on
								them blades, my little `Chicken Fat?'\" says Ivana."
								#dispose:
							)
						else
							(Say ivanaTalker "\"Happy rolling!\" says Ivana.
								\"Remember: `keep your tips up!'\""
								#dispose:
							)
						)
					)
					((Btst fSkated)
						(Say ivanaTalker "\"Come back any time!\" says Ivana."
							#dispose:
						)
					)
					((not talked)
						(HandsOff)
						(curRoom setScript: sTalk)
					)
					(else
						(Say ivanaTalker "\"I've already quoted you a price,
							young man!
							Take it or leave it!\""
							#dispose:
						)
					)
				)
			)
			(verbUse
				(switch invItem
					(iSilverDollar
						(cond
							((and
								(ego has: iRollerblades)
								(not (Btst fSkated))
							 )
								(Say ivanaTalker "\"Oh, no money is necessary yet.
									We'll settle up when you return the skates!\""
									#dispose:
								)
							)
							((Btst fSkated)
								(Say ivanaTalker "\"Thanks for the offer, Cutie-pie,
									but I never accept tips!\""
									#dispose:
								)
							)
							((not talked)
								(HandsOff)
								(curRoom setScript: sTalk)
							)
							(( < silvDollars RENTAL_CHARGE)
								(Format @str "\"Would you be willing to settle for
									$%d.00?\" you ask.
									\"That's all the money I have!\"" silvDollars)
								(Say ego @str
									LOW
								)
								(Say ivanaTalker "\"Hardly,\" she sniffs.
									\"But, do you have anything else of value?\""
									#dispose:
								)
							)
							(else
								(Format @str "\"Here you are,\" you say,
									\"here's %d of those heavy silver dollars.\""
									RENTAL_CHARGE)
								(Say ego @str
									LOW
								)
								(Points ptsGotSkatesWithMoney pfGotSkatesWithMoney)
								(Say ivanaTalker "\"Thank you, sir!\" she replies,
									accepting your money.
									\"And remember: keep your skates as long as
									you wish; enjoy them as you would any other
									fine mechanical object.
									Just don't wear them off the boardwalk,
									for any reason.\""
									#dispose:
								)
								(-= silvDollars RENTAL_CHARGE)
								(if (not silvDollars)
									(ego put: iSilverDollar 0)
								)
								(ego get: iRollerblades)
								(theIconBar curIcon:	(theIconBar at: walkIcon))
								(theGame setCursor: ((theIconBar curIcon?) cursor?))
							)
						)
					)
					(iCamcorder
						(cond
							((Btst fSkated)
								(Say ivanaTalker "\"Oh, no,\" says Ivana Tramp.
									\"that's yours! But, thanks anyway.\""
									#dispose:
								)
							)
							((ego has: iRollerblades)
								(Say ivanaTalker "\"Yes? Isn't that cute?\" says Ivana.
									\"You've got your skates.
									Why are you showing that to me?\""
									#dispose:
								)
							)
							(else
								(HandsOff)
								(curRoom setScript: sCamcorder)
							)
						)
					)
					(iRollerblades
						(if (Btst fSkated)
							(HandsOff)
							(curRoom setScript: sRollerblades)
						else
							(Say ivanaTalker "\"What's the matter, little buddy?\"
								asks Ivana. \"Are you afraid you'll go outside
								on the boardwalk and get `stuck in a crack?'\""
								#dispose:
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
)


;ษอออออออออออออออออออป
;บ                   บ
;บ Features          บ
;บ                   บ
;ศอออออออออออออออออออผ

(instance skates of Feature
	(properties
		x 				177
		y 				69
		nsLeft 		148
		nsTop 		49
		nsBottom 	90
		nsRight 		206
 		description "the skates"
		lookStr 		"From the many pairs of in-line skates on this rack,
						you feel safe in predicting that this store rents skates."
		sightAngle 	40
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbDo
				(TPrint "You can't reach the skates, but even if you
					could, the owner would break your finger if you tried to
					take them without paying.")
			)
			(else
				(super doVerb: theVerb invItem &rest)
			)
		)
	)
)

(instance curtain of Prop
	(properties
		view			vRoom
		x 				123
		y 				94
		signal		ignrAct
 		description "the curtain"
		lookStr 		"A beaded curtain blocks your view of the
						skate shop's back room."
		sightAngle 	40
	)
	(method (cue)
		(super cue:)
		(self stopUpd:)
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbDo
				(TPrint "You can't reach the beaded curtain.")
			)
			(else
				(super doVerb: theVerb invItem &rest)
			)
		)
	)
)

(instance poster1 of Feature
	(properties
		x 				64
		y 				42
		nsLeft 		54
		nsTop 		31
		nsBottom 	54
		nsRight 		75
 		description "the poster"
		lookStr 		"You presume this poster advertises an amusement park
						for insects."
		sightAngle 	40
	)
)

(instance poster2 of Feature
	(properties
		x 				76
		y 				75
		nsLeft 		62
		nsTop 		51
		nsBottom 	100
		nsRight 		90
 		description "the poster"
		lookStr 		"So that's what Ivana looked like when she was a redhead!"
		sightAngle 	40
	)
)

(instance poster3 of Feature
	(properties
		x 				61
		y 				113
		nsLeft 		50
		nsTop 		96
		nsBottom 	131
		nsRight 		73
 		description "the poster"
		lookStr 		"To demonstrate how good these skates are,
						the lady in the poster is eating one."
		sightAngle 	40
	)
)

(instance poster4 of Feature
	(properties
		x 				230
		y 				75
		nsLeft 		221
		nsTop 		44
		nsBottom 	82
		nsRight 		240
 		description "the poster"
		lookStr 		"This poster advertises roller skate odor eaters."
		sightAngle 	40
	)
)

(instance poster5 of Feature
	(properties
		x 				238
		y 				44
		nsLeft 		225
		nsTop 		66
		nsBottom 	103
		nsRight 		252
 		description "the poster"
		lookStr 		"\"Keep your in-line skates in line with the all new
						`Edlin,' from Ed's Skate Aligning Corp.\""
		sightAngle 40
	)
)

(instance poster6 of Feature
	(properties
		x 				253
		y 				37
		nsLeft 		246
		nsTop 		25
		nsBottom 	50
		nsRight 		261
 		description "the poster"
		lookStr 		"\"Better skating, no waiting.\""
		sightAngle 	40
	)
)

(instance poster7 of Feature
	(properties
		x 				250
		y 				106
		nsLeft 		241
		nsTop 		92
		nsBottom 	121
		nsRight 		259
 		description "the poster"
		lookStr 		"\"Nuclear Blades\" for those who want to go REALLY fast!"
		sightAngle 	40
	)
)

(instance light of Feature
	(properties
		x 					165
		y 					37
		nsLeft 			147
		nsTop 			30
		nsBottom 		44
		nsRight 			184
 		description 	"the light"
		lookStr 			"Ivana has thoughtfully replaced the standard 60-watt
							bulb with this new 250-watt halogen."
		sightAngle 		40
	)
)

(instance desk of Feature
	(properties
		x 					158
		y 					105
		nsLeft 			90
		nsTop 			91
		nsBottom 		120
		nsRight 			227
 		description 	"the counter"
		lookStr 			"Across this counter pass the world's sharpest
							`blades!'"
		sightAngle 		40
	)
)

(instance box of Feature
	(properties
		x 				205
		y 				144
		nsLeft 		188
		nsTop 		133
		nsBottom 	156
		nsRight 		223
 		description "the box"
		lookStr 		"A wooden crate resides incongruously in the center of the
						floor."
		sightAngle 	40
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbDo
				(TPrint "Although you find the small wooden crate precious,
					you really have no desire to carry it with you throughout
					the rest of the game.")
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

(instance ivanaTalker of Talker
	(properties
		name			"Ivana"
		view			vIvanaTalker
		loop			3
		nsLeft		20
		nsTop			15
		viewInPrint	TRUE
	)
	(method (init)
		(= bust 	ivanaBust)
		(= eyes 	ivanaEyes)
		(= mouth ivanaMouth)
		(super init: &rest)
	)
)

(instance ivanaBust of Prop
	(properties
		view			vIvanaTalker
		loop			1
		nsLeft		0
		nsTop			0
	)
)

(instance ivanaEyes of Prop
	(properties
		view			vIvanaTalker
		loop			2
		nsLeft		24
		nsTop			41
		cycleSpeed	30
	)
)

(instance ivanaMouth of Prop
	(properties
		view			vIvanaTalker
		loop			0
		nsLeft		24
		nsTop			41
		cycleSpeed	8
	)
)

(instance sitActions of Actions
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbUse
				(switch invItem
					(iRollerblades
						(TPrint "There's no place here to sit down--except
							that dirty box which might very well soil your
							pure white leisure suit.
							Perhaps you could find a place outside?")
					)
					(else
						(return FALSE)
					)
				)
			)
			(else
				(return FALSE)
			)
		)
	)
)

