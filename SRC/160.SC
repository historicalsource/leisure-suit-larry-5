;**
;**	Logics for Room 160 -- Lobby, PornProdCorp
;**
;**	LSL5:  Passionate Patti Does A Little Undercover Work
;**				Copyright 1991
;**				by Al Lowe
;**
;**	Programmed by Brian K. Hughes
;**
;**	Last Update:  September 11, 1991
;**

(script#		160)

(public
	rm160		0
)


(define	vLarryW/Coffee				141)
(define		lTakingPot			0)
(define		lWalkingW/Pot		1)
(define		lTurningSouth		2)

(define	pPornProdCorpLobby			160)
(define	vPPCLobby					160)
(define		lSingleCells		0)
(define			cCoffeePot	0)
(define		lDoorPresident		1)
(define		lDoorFileroom		2)
(define		lWaterCooler		3)
(define		lBelch				4)
(define		lLarrySheepish		5)

(define	vScruemall				150)

(define	vCoffee						162)
(define		lCoffee				0)


(define	mPPCMusak						160)
(define		sCoffee!				161)
(define		sPlugIn				162)
(define		sWaterCooler		163)


(define	startingX					95)
(define	startingY					65)


(local
	oldDoVerbCode
	usedEyeball
	usedHand
	lookedAtPot
	belched
)


(instance rm160 of LLRoom
	(properties
		picture		pPornProdCorpLobby
		east			190
		north			180
		west			170
		lookStr		"Once PornProdCorp was heavily involved in X-rated movies,
						but since their recent I.P.O. and entry into soft-R
						television, they've totally changed their image.
						Now they are struggling their way into respectability."
	)

	(method (init)
		(self setRegions: HOLLYWOODRGN)
		(ego
			init:				,
			normalize:		,
			illegalBits:	(| cCYAN cWHITE)
		)
		(switch prevRoomNum
			(140
				(HandsOn)
				(theMusic
					number:	mPPCMusak,
					setLoop:	-1,
					play:
				)
				(= style IRISOUT)
				(ego
					posn:			138 121,
					setHeading:	180,
					edgeHit:		0,
				)
				(theIconBar disable: computerIcon)
			)
			(PASSWORD		; never actually comes from 150 anymore
				(Bset fBeenIn150)			; prophylactic coding!
				(= restartRoom 160)
				(SetFFRoom NULL)
				(ego get: iCamcorder)
				(= score (+ ptsTookPot ptsGotCamcorder))
				(if (Btst pfDrankWater)
					(+= score ptsDrankWater)
				)
				(if (Btst pfSawTitty)
					(+= score ptsSawTitty)
				)
				(theMusic
					number:	mPPCMusak,
					setLoop:	-1,
					play:
				)
				(HandsOn)
			)
			(west
				(self setScript: sFromWest)
			)
			(north
				(HandsOn)
				(ego
					posn:				260 77,
					setHeading: 	180,
					edgeHit:			0,
					illegalBits:	$0000
				)
			)
			(east
				(ego y: 160)
			)
			(else
		 		(if (!= prevRoomNum 150)	; We either picked PPC off the main
		 			(Bset fBeenIn150)			;	menu or we're restarting, so don't
					(= score (+ ptsTookPot ptsGotCamcorder))		;	do intro stuff
					(if (Btst pfDrankWater)
						(+= score ptsDrankWater)
					)
					(if (Btst pfSawTitty)
						(+= score ptsSawTitty)
					)
					(ego get: iCamcorder)
		 		)
				(ego
					posn: 	(presDoor approachX?) (presDoor approachY?),	;startingX startingY,
					edgeHit:	0
				)
				(theMusic
					number:	mPPCMusak,
					setLoop:	-1,
					play:
				)
				(HandsOn)
			)
		)

		(super init:)

		(fileDoor init:, approachVerbs: verbDo)
		(presDoor init:)
		(if (not (Btst fBeenIn150))
			(coffee
				init:				,
				approachVerbs:	verbDo,
				setScript:		sCoffee
			)
		)
		(coffeeMaker	init:)
		(leftDoor		init:, approachVerbs: verbDo)
		(bigSign			init:)
		(portrait		init:, approachVerbs: verbDo verbLook)
		(poster			init:, approachVerbs: verbDo verbLook)
		(pictures		init:, approachVerbs: verbDo verbLook)
		(onePicture		init:, approachVerbs: verbDo verbLook)
		(certificate	init:, approachVerbs: verbDo verbLook)
		(plant			init:, approachVerbs: verbDo)
		(smallPlant		init:, approachVerbs: verbDo)
		(largePlant		init:, approachVerbs: verbDo)
		(bronzeAward	init:, approachVerbs: verbDo verbLook)
		(waterCooler	init:, approachVerbs: verbDo)
		(chair			init:)
		(desk				init:, approachVerbs: verbDo)
		(cabinetDoors	init:, approachVerbs: verbDo verbLook)
		(outlet			init:, approachVerbs: verbDo verbUse)
		(hallway			init:)

		(curRoom addObstacle:
			((Polygon new:)
				type: PBarredAccess,
				init:	0 0 319 0 319 79 296 79 286 71 273 71 273 44 250 44 250 71
						233 71 219 86 143 86 137 74 118 74 104 59 0 59,
				yourself
			)
			((Polygon new:)
				type: PBarredAccess,
				init:	0 73 79 73 99 76 98 92 66 108 58 113 12 113 11 119 60 119
						48 131 22 135 2 148 2 187 185 187 190 181 216 181 220 187
						314 187 319 157 319 189 0 189,
				yourself
			)
		)
		(if (not (Btst fBeenIn150))
			(helpTimer set: helpTimer 45)
		)
		(= oldDoVerbCode doVerbCode)		; Save our default doVerbCode cuz...
		(= doVerbCode doVerb160Code)		; we're using different code in this room
	)

	(method (newRoom n)
		(if (== n 190)	; we're going outside
			(theMusic fade: 80 25 10 FALSE)	; don't dispose
		)
		(super newRoom: n)
	)

	(method (dispose)
		(helpTimer dispose:, delete:)		; Timers must be deleted manually
		(coffeeTimer dispose:, delete:)
		(= doVerbCode oldDoVerbCode)		; Restore our original doVerbCode
		(super dispose:)
	)

 	(method (doit)
 		(super doit:)
		(cond
			(script)
			((StepOn ego cBLUE)
				(if (not (Btst fBeenIn150))
					(self setScript: sMoveOffControl NULL 10)
				else
	 				(self newRoom: west)
				)
			)
			((and	(StepOn ego cGREEN)
					(not (Btst fBeenIn150))
				)
				(self setScript: sMoveOffControl NULL -10)
			)
			((and	(not belched)
					(== (ego loop?) 3)	; facing north
					(< 30 (ego distanceTo: waterCooler) 40)
				)
				(self setScript: sBelch)
			)
			((and	belched
					(> (ego distanceTo: waterCooler) 60)
				)
				(= belched FALSE)
			)
 		)
 	)
)


;ษอออออออออออออออออออป
;บ                   บ
;บ Scripts           บ
;บ                   บ
;ศอออออออออออออออออออผ

(instance sMoveOffControl of Script
	;
	; register - X offset to move
	;
	(method (changeState newState)
		(switchto (= state newState)
			(
				(HandsOff)
				(ego setMotion: MoveTo (+ (ego x?) register) (ego y?) self)
			)
			(
				(TPrint "Don't leave now, when they're crying for coffee
					in there!")
				(HandsOn)
				(theIconBar disable: computerIcon)
				(self dispose:)
			)
		)
	)
);sMoveOffControl

(instance sCoffee of Script
	(method (changeState newState)
		(switchto (= state newState)
			(
				(coffeeTimer cycleCnt: 0)		; suspend the timer
				(coffeeYell
					init:			,
					setCel:		0,
					posn:			(coffeeYell x?) (Random 53 104),
					cycleSpeed:	(Random 5 10),
					setCycle:	EndLoop self,
				)
			)
			(
				(coffeeSound
					setLoop:		0,
					play:
				)
				(coffeeYell setCycle: CycleTo 3 BACKWARD self)
			)
			(
 				(coffeeYell setCycle: EndLoop)
				(= ticks (Random 30 90))
			)
			(
				(coffeeYell	dispose:)
				(coffeeTimer setReal: coffeeTimer (Random 6 12))
				(self dispose:)
 			)
 		)
	)
);sCoffee

(instance sFromWest of Script
	(method (changeState newState)
		(switchto (= state newState)
			(
				(ego
					posn:			65 70,
					setMotion:	MoveTo 95 70 self
				)
			)
			(
				(HandsOn)
				(self dispose:)
			)
		)
	)
);sFromWest


(instance sBelch of Script
	(method (changeState newState)
		(switchto (= state newState)
			(
				(HandsOff)
				(waterSound
					setLoop:	0,
					play:
				)
				(waterCooler
					setLoop:		lBelch,
					setCycle:	EndLoop self
				)
			)
			(
				(waterCooler
					setLoop:		lWaterCooler,
					setCel:		0
				)
				(ego
					view:			vPPCLobby,
					loop:			5,
					cel:			0,
					cycleSpeed:	1,
					setCycle:	EndLoop self
				)
			)
			(
				(Say ego "\"Excuse me!\" you say, assuming responsibility!" LOW)
				(ego
					setLoop:			3,
					normalize:		,
					illegalBits:	(| cCYAN cWHITE)
				)
				(HandsOn)
				(= belched TRUE)
				(self dispose:)
			)
		)
	)
);sBelch


(instance sDeliver of Script
	(method (changeState newState &tmp theX theY)
		(switchto (= state newState)
			(
				(HandsOff)
				(coffeeTimer dispose:, delete:)
				(helpTimer dispose:, delete:)
				(ego
					view:			vLarryW/Coffee,
					setLoop:		0,
					setCycle:	CycleTo 1 FORWARD self
				)
			)
			(
				(coffee dispose:)
				(ego setCycle: EndLoop self)
			)
			(
				(coffee dispose:)
				(if (cast contains: coffeeYell)
					(coffeeYell dispose:)
				)
				(Points ptsTookPot pfTookPot)
				(TPrint "You grab the pot from the warmer and head for Mr.
					Scruemall's office--eager to once again kiss
					up to the boss!")
				(= theX (presDoor approachX?))
				(= theY (presDoor approachY?))
				(ego
					normalize:	vLarryW/Coffee,
					setLoop:		lWalkingW/Pot,
					setCycle:	Walk,
					setMotion:	PolyPath theX theY self
				)
			)
			(
				(presDoor
					entranceTo:	140,
					doVerb: 		verbDo
				)
			)
		)
	)
);sDeliver


;ษอออออออออออออออออออป
;บ                   บ
;บ Objects           บ
;บ                   บ
;ศอออออออออออออออออออผ

(instance fileDoor of Door
	(properties
		view				vPPCLobby
		loop				lDoorFileroom
		x					245
		y					67
		entranceTo		180
		moveToX			264
		moveToY			61
		enterType		doorWalkEgo
		exitType			doorWalkEgo
		approachX		264
		approachY		76
		description		"the file room door"
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbLook
				(TPrint "This door leads to the filing room where PPC stores
					its voluminous correspondence.  Some of your favorite
					moments have been spent at this exact location, watching
					secretaries fill those lower drawers."
				)
				(= usedEyeball TRUE)
			)
			(else
				(super doVerb: theVerb invItem &rest)
			)
		)
	)
	(method (open)
		(if (not (Btst fBeenIn150))
			(TPrint "Don't leave now, when they're crying for coffee in there!")
			(= usedHand TRUE)
		else
			(ego illegalBits: $0000)
			(super open: &rest)
		)
	)
	(method (close)
		(ego illegalBits: (| cCYAN cWHITE))
		(super close:)
	)
)

(instance presDoor of Door
	(properties
		view				vPPCLobby
		loop				lDoorPresident
		x					47
		y					110
		entranceTo		155
		moveToX			27
		moveToY			115
		approachX		60
		approachY		116
		enterType		doorPlaceEgo
		exitType			doorWalkEgo
		heading			90
		description		"Silas Scruemall's office door"
	)

	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbDo
				(if (Btst fBeenIn150)
				  	(TPrint "Remembering your boss' \"open door\" policy,
						you decide to just follow your orders.
						Besides, that's what you do best!")
				else
					(super doVerb: theVerb invItem &rest)
				)
				(= usedHand TRUE)
			)
			(verbLook
				(TPrint "This is the entrance to the office of
					Silas Scruemall, your boss.
					Mr. S is famous for his \"open door\" policy:
					open his door and you're fired!")
				(TPrint "A tasteful, wood-grained brass plate on his door reads,
					\"President, C. E. O., C. F. O., C. T. O., C. O. O.,
					Chairman of the Board, Executive Producer\"")
				(= usedEyeball TRUE)
			)
			(verbTalk
				(Say ego "\"Hey, Scruemall!\" you whisper softly,
					\"I'm not afraid of you!\"")
				(TPrint "(\"Only your power,\" you think.)" LOW)
			)
			(else
				(super doVerb: theVerb invItem &rest)
			)
		)
	)
	(method (open)
		(if (and	(cast contains: coffee)
					(not (Btst fBeenIn150))
				)
			(coffee setScript: sCoffee)
		else
			(super open: &rest)
		)
	)
)

(instance coffee of Prop
	(properties
		view			vPPCLobby
		x				73
		y				75
		approachX	98
		approachY	97
		priority		6
		signal		(| ignrAct fixPriOn)
		description	"the coffee pot"
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbLook
				(TPrint "You notice a fresh pot of \"Discount Coffee Warehouse's\"
					finest Kona-like blend waiting on the warmer. You wonder
					if anyone nearby would like a cup."
				)
				(= lookedAtPot TRUE)
				(= usedEyeball TRUE)
			)
			(verbDo
				(curRoom setScript: sDeliver)
			)
			(else
				(super doVerb: theVerb invItem &rest)
			)
		)
	)

)

(instance coffeeYell of Prop
	(properties
		view			vCoffee
		x				32
		y				81
		priority		14
		signal		(| ignrAct fixPriOn)
		description	"the sound of coffee drinkers"
		lookStr		"It looks like someone wants some coffee!"
	)
)

(instance coffeeSound of Sound
	(properties
		number		sCoffee!
	)
)

(instance waterCooler of Prop
	(properties
		view			vPPCLobby
		loop			lWaterCooler
		x				204
		y				182
		approachX	201
		approachY	179
		priority		15
		signal		(| fixPriOn ignrAct)
		description	"the water cooler"
		lookStr		"The water cooler is the office equipment with which
						you're most familiar."
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbDo
				(waterSound play:)
				(self setCycle: EndLoop self)
				(= usedHand TRUE)
				(TPrint "You get a refreshing drink of bottled tap water.")
			)
			(else
				(super doVerb: theVerb invItem &rest)
			)
		)
	)
	(method (cue)
		(waterCooler setCel: 0)
		(Points ptsDrankWater pfDrankWater)
	)
)

(instance waterSound of Sound
	(properties
		number	sWaterCooler
	)
)

(instance helpTimer of Timer
	(method (cue)
		(cond
			((not usedEyeball)
				(TPrint "Here are some suggestions if this is the first
					time you've played a Sierra adventure with the
					\"no-typing\" interface.")
				(TPrint "When windows like this one appear,
					press ENTER or click your mouse to clear them away immediately.
					Or, you can just wait and they'll clear automatically.
					Use the control panel to adjust the Text Speed to
					your reading speed.")
				(TPrint "When there is no text here, you can press ESC or move
					your mouse to the very top of your screen.
					The \"Icon Bar\" will appear.
					Click on the Question Mark,
					then move your cursor across the other icons.
					They'll each tell you what they do.")
				(TPrint "Click on those icons to change your game cursor.
					For example, choose the Eyeball icon and click it on
					various things around this room to \"Look\" at them.")
				(self set: self 45)
			)
			((and	(== startingX (ego x?))
					(== startingY (ego y?))
				)
				(TPrint "Choose the Walking Man icon from the icon bar and
					click its feet where you would like to \"Walk.\"")
				(TPrint "Your feet will stop as close as possible to
					where you clicked.")
				(self set: self 45)
			)
			((not usedHand)
				(TPrint "Choose the Hand icon from the icon bar and
					click its index finger where you would like to do some
					\"Action.\"")
				(self set: self 45)
			)
			((not lookedAtPot)
				(TPrint "This would be a good time to select the Eyeball \"Look\"
					icon from the icon bar and click it on the coffee pot in
					the upper left corner of this room.")
				(self set: self 45)
			)
			(else
				(TPrint "Select the Hand \"Action\" icon and click it on the
					coffee pot to use the coffee.")
				(TPrint "If you haven't already,
					now would be a good time to read the
					documentation that came with this game.")
			)
		)
	)
)

(instance coffeeTimer of Timer
	(method (cue)
		(coffee setScript: sCoffee)
	)
)


;ษอออออออออออออออออออป
;บ                   บ
;บ Features          บ
;บ                   บ
;ศอออออออออออออออออออผ

(instance coffeeMaker of Feature
	(properties
		x 				72
		y 				69
		nsLeft 		62
		nsTop 		61
		nsBottom 	78
		nsRight 		82
 		description "the coffee maker"
		sightAngle 	40
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbLook
				(if (cast contains: coffee)
					(TPrint "The coffee maker has just finished brewing a
						fresh batch of Kahuii Koala Mocha-Homo Blend.")
				else
					(TPrint "There's no pot here. Remember? You broke it!")
				)
			)
			(verbDo
				(if (cast contains: coffee)
					(TPrint "You'd love to brew a fresh pot,
						but there's already a whole pot of coffee on the
						warmer."
					)
				else
			  		(TPrint "You'd love to brew a fresh pot,
			  			but there's no pot left to brew in!"
					)
				)
			)
			(else
				(super doVerb: theVerb invItem &rest)
			)
		)
	)
)

(instance leftDoor of Feature
	(properties
		x 				22
		y 				87
		nsLeft 		12
		nsTop 		51
		nsBottom 	123
		nsRight 		32
		approachX	60
		approachY	116
		description	"Silas Scruemall's office door"
		sightAngle 	40
	)
	(method (doVerb)
		(presDoor doVerb: &rest)
	)
)

(instance bigSign of Feature
	(properties
		x 				176
		y 				26
		nsLeft 		153
		nsBottom 	52
		nsRight 		199
 		description "the \"America's Sexiest Home Videos\" sign"
		lookStr 		"Evidently this company is the home of
						\"America's Sexiest Home Videos.\"
						You've heard of that show!
						Especially since you work here!"
		sightAngle 	40
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbDo
				(TPrint "Yes, of course you think the sign is lovely,
					but you really have no use for a big, ugly sign.")
				(= usedHand TRUE)
			)
			(verbTalk
				(TPrint "If that thing talks back, you vow to leave the
					building immediately!")
			)
			(else
				(super doVerb: theVerb invItem &rest)
			)
		)
	)
)

(instance portrait of Feature
	(properties
		x 				62
		y 				40
		nsLeft 		53
		nsTop 		24
		nsBottom 	57
		nsRight 		71
		approachX	94
		approachY	95
 		description "the portrait of Silas Scruemall"
		lookStr 		"\"Our founder, Silas Scruemall!\""
		sightAngle 	40
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbDo
				(TPrint "Thinking yourself incredibly smart,
					you attempt to remove the painting from the wall in order
					to crack the safe which you feel certain lies behind it.")
				(TPrint "You fail.")
				(TPrint "(But not because you can't crack a good one!)" LOW)
				(= usedHand TRUE)
			)
			(verbTalk
				(TPrint "Try talking to people instead.")
			)
			(else
				(super doVerb: theVerb invItem &rest)
			)
		)
	)
)

(instance poster of Feature
	(properties
		x 				93
		y 				27
		nsLeft 		83
		nsTop 		11
		nsBottom 	43
		nsRight 		103
 		description "the poster"
		lookStr 		"Art is one thing, but this is something else!"
		sightAngle 	40
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbDo
				(TPrint "Take your hand off her!")
				(TPrint "(You're smudging that fine cardboard surface!)" LOW)
				(= usedHand TRUE)
			)
			(verbTalk
				(TPrint "You really ARE lonely, aren't you, Larry?")
			)
			(else
				(super doVerb: theVerb invItem &rest)
			)
		)
	)
)

(instance pictures of Feature
	(properties
		x 				128
		y 				29
		nsLeft 		111
		nsTop 		11
		nsBottom 	48
		nsRight 		146
		approachX	122
		approachY	75
 		description "the photographs"
		lookStr 		"The pictures are of past stars of PornProdCorp's many
						films, television shows and artsy documentaries.
						There are a total of zero pictures of
						Chief Video Tape Rewinders like you.
						You have no reason to assume you'll be the first!"
		sightAngle 	40
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbDo
				(TPrint "You really enjoy playing with your hands, eh, Larry?")
				(= usedHand TRUE)
			)
			(else
				(super doVerb: theVerb invItem &rest)
			)
		)
	)
)

(instance onePicture of Feature
	(properties
		x 				220
		y 				22
		nsLeft 		213
		nsTop 		11
		nsBottom 	33
		nsRight 		228
		approachX	231
		approachY	78
 		description "the hydrant photograph"
		lookStr 		"It's either the star of \"Manhunter,\" or a fire hydrant,
						you're not sure which."
		sightAngle 	40
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbDo
				(TPrint "You have so much fun feeling things!")
				(= usedHand TRUE)
			)
			(verbTalk
				(TPrint "You attempt to throw your voice into the picture.
					You fail.")
			)
			(else
				(super doVerb: theVerb invItem &rest)
			)
		)
	)
)

(instance certificate of Feature
	(properties
		x 				295
		y 				24
		nsLeft 		295
		nsTop 		15
		nsBottom 	29
		nsRight 		309
		approachX	288
		approachY	76
 		description "the certificate"
		sightAngle 	40
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbLook
				(TPrint "\"Class B Establishment\" granted by the Board of Health.
					(Contested by the Board of Good Taste!)"
					#mode		teJustCenter
				)
			)
			(verbDo
				(TPrint "This is in keeping with your opinion of this place.")
				(= usedHand TRUE)
			)
			(else
				(super doVerb: theVerb invItem &rest)
			)
		)
	)
)

(instance plant of Feature
	(properties
		x 				305
		y 				45
		nsLeft 		292
		nsTop 		31
		nsBottom 	60
		nsRight 		319
		approachX	291
		approachY	75
 		description "the plant"
		lookStr 		"You really enjoy the look of fine plastic."
		sightAngle 	40
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbDo
				(TPrint "Ouch! Those plastic leaves are sharp!")
				(= usedHand TRUE)
			)
			(verbTalk
				(TPrint "Does talking to plastic plants help them grow?")
			)
			(else
				(super doVerb: theVerb invItem &rest)
			)
		)
	)
)

(instance smallPlant of Feature
	(properties
		x 				13
		y 				188
		nsLeft 		0
		nsTop 		139
		nsBottom 	183
		nsRight 		26
		approachX	16
		approachY	187
 		description "the plant"
		lookStr 		"You really enjoy the look of fine plastic."
		sightAngle 	40
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbDo
				(TPrint "Ouch! Pricked again!")
				(= usedHand TRUE)
			)
			(verbTalk
				(TPrint "Does talking to plastic plants help them grow?")
			)
			(else
				(super doVerb: theVerb invItem &rest)
			)
		)
	)
)

(instance largePlant of Feature
	(properties
		x 				245
		y 				180
		nsLeft 		224
		nsTop 		112
		nsBottom 	182
		nsRight 		266
		approachX	260
		approachY	179
 		description "the plant"
		lookStr 		"How you love plastic plants! (Especially the smell!)"
		sightAngle 	40
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbDo
				(TPrint "\"SPROING!\" says the leaf.")
				(= usedHand TRUE)
			)
			(verbTalk
				(TPrint "Does talking to plastic plants help them grow?")
			)
			(else
				(super doVerb: theVerb invItem &rest)
			)
		)
	)
)

(instance bronzeAward of Feature
	(properties
		x 				283
		y 				188
		nsLeft 		280
		nsTop 		173
		nsBottom 	189
		nsRight 		313
		approachX	283
		approachY	185
 		description "the \"Titty\" award"
		sightAngle 	40
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbLook
				(TPrint "\"Presented to PornProdCorp for their humanitarian
					efforts in the field of free speech and the commercial
					development of the steel-reinforced videotape.
					\n
					\n
					March 10, 1976\""
					#mode			teJustCenter
					#title		{Genuine Engraved Plastic}
				)
				(Points ptsSawTitty pfSawTitty)
				(TPrint "(Wow! It's a genuine \"Titty!\")" LOW)
				(= usedEyeball TRUE)
			)
			(verbDo
				(TPrint "Is this the best thing you've found to do with your
					hand cursor?")
				(= usedHand TRUE)
			)
			(else
				(super doVerb: theVerb invItem &rest)
			)
		)
	)
)

(instance chair of Feature
	(properties
		x 					117
		y 					173
		nsLeft 			106
		nsTop 			164
		nsBottom 		183
		nsRight 			129
 		description 	"the chair"
		lookStr 			"This chair and desk belong to the person least in favor
							with your boss, Silas Scruemall.
							Currently, they are unoccupied.
							Often, you sit here."
		sightAngle		40
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbDo
				(TPrint "You're afraid to sit down here since your boss may come
					out of his office at any moment.
					Shouldn't you continue with your mission?")
				(= usedHand TRUE)
			)
			(else
				(super doVerb: theVerb invItem &rest)
			)
		)
	)
)

(instance desk of Feature
	(properties
		x 				132
		y 				186
		nsLeft 		85
		nsTop 		184
		nsBottom 	189
		nsRight 		179
		approachX	151
		approachY	188
 		description "the desk"
		lookStr 		"This chair and desk belong to the person least in favor
						with your boss, Silas Scruemall.
						Currently, they are unoccupied.
						Often, you sit here."
		sightAngle 	40
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbDo
				(TPrint "Rubbing your hand across the desk makes you appreciate
					its fine vinyl woodgrain surface.")
				(= usedHand TRUE)
			)
			(else
				(super doVerb: theVerb invItem &rest)
			)
		)
	)
)

(instance cabinetDoors of Feature
	(properties
		x 				78
		y 				92
		nsLeft 		70
		nsTop 		86
		nsBottom 	99
		nsRight 		87
 		description "the cabinet"
		lookStr 		"The only thing in the cabinet under the coffee pot is a
						box of thoughtfully premeasured, preweakened coffee
						portions, and some empty plastic bags that used to hold
						stacks of Styrofoam coffee cups.
						You have no need for either coffee or filters."
		sightAngle 	40
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbDo
				(TPrint "You have no need for coffee or filters.")
				(= usedHand TRUE)
			)
			(else
				(super doVerb: theVerb invItem &rest)
			)
		)
	)
)



(instance outlet of Feature
	(properties
		x 				51
		y 				97
		nsLeft 		48
		nsTop 		88
		nsBottom 	101
		nsRight 		55
		approachX	77		;82
		approachY	105	;112
 		description "the electrical outlet"
		sightAngle 	40
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbLook
				(if chargerPlugged
					(TPrint "The coffee pot is plugged into the upper electrical
						outlet, and your battery charger is \"currently\"
						occupying the lower outlet.")
				else
					(TPrint "The coffee pot is plugged into the upper electrical
						outlet, but the lower one is \"currently\" available.")
				)
				(= usedEyeball TRUE)
			)
			(verbDo
				(if (not chargerPlugged)
					(PlugFinger)
				else
					(theCharger doVerb: verbDo)
				)
				(= usedHand TRUE)
			)
			(verbUse
				(switch invItem
					(iBatteryCharger
						(TPrint "Good idea! You can never tell when a guy might
							need his pocket protector fully charged!")
						(PlugCharger)
					)
					(iCamcorder
						(TPrint "The camcorder can't be plugged into an outlet.
							You must use its recharger base.")
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

(instance hallway of Feature
	(properties
		x 				99
		y 				64
		nsLeft 		77
		nsTop 		57
		nsBottom 	71
		nsRight 		122
 		description "the hallway"
		lookStr 		"This hallway leads to the Department of Video Tape
						Rewinding and your workroom, where you fulfill your job as
						Chief Rewinder and Sterilizer (Betamax Division)."
		sightAngle 	40
	)
)


;ษอออออออออออออออออออป
;บ                   บ
;บ doVerb code       บ
;บ                   บ
;ศอออออออออออออออออออผ

; This will help us with our "Teaching" system by automatically setting
; the look and hand stuff if we don't have specific verbLook or verbDo
; clauses in the item's doVerb method.

(instance doVerb160Code of Code
	(method (doit theVerb theObj &tmp objDesc theItem [str 100])
		(= objDesc (theObj description?))
		(switch theVerb
			(verbLook
				(if (theObj facingMe:ego) 	; turn ego to look
					(if (theObj lookStr?)
						(TPrint (theObj lookStr?))
					else
						(NoResponse theObj theVerb)
					)
				)
				(= usedEyeball TRUE)
			)
			(verbTalk
				(if (theObj facingMe:ego) 	; turn ego to look
					(Format @str "You engage in a short,
						but personally meaningful, conversation with %s." objDesc)
					(TPrint @str)
				)
			)
			(verbDo
				(Format @str "That feels exactly like %s." objDesc)
				(TPrint @str)
				(= usedHand TRUE)
			)
			(verbUse
				(= theItem ((theIconBar curInvIcon?) description?))
				(Format @str "It seems %s just doesn't work with %s."
					theItem
					objDesc
				)
				(TPrint @str)
			)
			(verbZipper
				(Format @str "Don't do that to %s!" objDesc)
				(TPrint @str)
			)
			(else
				(NoResponse theObj theVerb)
			)
		)
	)
)
