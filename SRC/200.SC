;**
;**	Logics for Room 200 -- Limousine interior
;**
;**	LSL5:  Passionate Patti Does A Little Undercover Work
;**				Copyright 1991
;**				by Al Lowe
;**
;**	Programmed mostly by C. Escobar
;**
;**	Last Update:  Monday, September 23, 1991 
;**

(script#		200)

(public
	rm200		0
)

(include limo.sh)


(define	exitActive	(== ((theIconBar at: walkIcon) cursor?) EXIT_CURSOR))

(define  LARRY_X               67)
(define  LARRY_Y              188)
(define  ARRIVE               30)
(define	sPickUpPhone 			36)
(define 	sHangUpPhone			37)		;not used, too big
(define	NO_NUMBER				-1)
(define  vLimoWindows		  208)
(define  PATTI_X 					65)
(define 	PATTI_Y					88)
(define	lTorso				    6)

(local
   faxHere              ;psudo flag
	limoCueCount
	missedDayTrotter		;**	a forced (ego get:) cause player didn't see it
	lastSec					;**	used temporarily in sDrive
	tvOn
	windowOpen
	tDrive 			= 5
	onPhone
	driving
	dreaming
	phonedDesmond				;**	TRUE or FALSE
	whichFax					
	lookedAtDT				;**	looked at the daytrotter

	faxPaperCue
	driverCue
	armCue
	okToExit
	dreamPts =		  		;**	dream bubble points
	[	0 1 117 120
		0 2 148 98
		0 3 195 115
		0 4 199 124
		PATHEND
	]
	dreamDone   :psudo flag
)

(instance rm200 of LLRoom
	(properties
		picture		200
		lookStr		"You've always wondered what it would be like to
						travel by limousine."
	)

	(method (init &tmp num)
		(if (!= prevRoomNum 205)
			(theMusic  number:	0, client: FALSE, stop:)
			(theMusic2 number:	0, client: FALSE, stop:)
		)
		(LoadMany VIEW
				vLimoInterior			;200
				vAquarium				;206
		)
        
		(HandsOn)
		(SetupExit TRUE)
		(User canControl: FALSE)

      (windowL init, stopUpd)

		(switch prevRoomNum
		 	(500
				(Bset fBeenInNewYork)
			)
			(600
				(= pattiLoc Baltimore)
;				(Bset fBeenInBaltimore)		;**	to enable exit/enter limo
			)
			(690
				(= pattiLoc Baltimore)
				(Bset fBeenInBaltimore)
			)
			(700
				(Bset fBeenInAtlanticCity)
			)
			(800
				(= pattiLoc Philly)
;				(Bset fBeenInPhilly)			;**	to enable exit/enter limo
			)
			(890
				(= pattiLoc Philly)
				(Bset fBeenInPhilly)
			)
			(900
				(Bset fBeenInMiami)
			)
		)

		(if egoIsLarry

			(LoadMany VIEW
				vDriverFemale			;204
				vLarry'sLimo			;201
			)

			(ego
				init,
				view:			vLarry'sLimo,			;201
				posn:			LARRY_X LARRY_Y 0,
				setPri:		14,
				setCycle:	0,
				setLoop:		lGetPhone,
				cycleSpeed:	6,
				setCel:		0,
				show,
				forceUpd,
            edgeHit:    0,
			)
		   (faxMachine 		init:, y: 113, z: 0)

			(if (!= prevRoomNum 205)
				(HandsOff)
				(Delay 2 REALSECONDS curRoom)
			)

			(driver
				init:,
				view:			vDriverFemale,				;204
				setLoop:		2,
				stopUpd,
			)
			(driverBody
				init:,
				view:			vDriverFemale,				;204
				setCel:		1,
				setLoop:		0,
			)

			(driverEyes
				init:,
				view:			vDriverFemale,				;204
			)
			(if (!= prevRoomNum 250)
				(theIconBar disable: walkIcon)
			else
				(= okToExit TRUE)
			)
			(driversWindow
				init:,
 				description 	"Bobbi"
			)
			(tv
				init:,
				hide:
			)


		else  ;------------------------------------------------

			;patti is riding

			(LoadMany VIEW
				vDriverMale
				vPatti'sLimo
			)

;**		PushButton			;preload class & ttdialer module

			(if (OneOf prevRoomNum 800 600 425)
				(theIconBar enable: walkIcon)
				(= okToExit TRUE)
			else
				(theIconBar disable: walkIcon)
			)

			(driver
				init:,
				view:			vDriverMale,	;203
				setLoop:		2,
			)
			(driverBody
				init:,
				view:			vDriverMale,	;203
				setCel:		1,
				setLoop:		0,
			)

			(driverEyes
				init:,
				view:			vDriverMale,	;203
			)


			;patti

			(ego
				init,
				view:				vPatti'sLimo,				;202
				setLoop:			lTorso,						;6
				posn:				PATTI_X PATTI_Y -100,
				setPri:			14,
;				setLoop:			lGetPhone,
				setCel:			0,
				setCycle:		0,
				ignoreActors:	TRUE,
				show,
				cycleSpeed:		6,
				forceUpd,
            edgeHit:       0,
			)
			(legs
				init:,
				setScript: sLegs,
			)

			(if (not (Btst fTookBottle))
				(bottle
					init:,
					stopUpd,
				)
			)

			(if (and (Btst fBeenInPhilly) (Btst fBeenInBaltimore))
				(Delay 20 CYCLES curRoom)
				(HandsOff)
				(= pattiDest toDC)
			)
			(driversWindow
				init:,
 				description 	"Bobby"
			)
		   (faxMachine 		init:)
		)

		;props and such

		(phone				init:, stopUpd)
		(glass				init:, stopUpd)

		;features

		(theTv 				init:)
		(rightDoor			init:)
		(stereo				init:)
		(bottles&Glasses	init:)
		(fishTank			init:)
		(tapes				init:)
		(vcr					init:)

		(fish
			init:,
			setCycle:		Forward,
			posn:				(Random 241 304) (Random 87 106),
			setScript 		sFishScript,
		)

		;you should not be able to get the daytrotter
		;coming back from outside HDC

		(if (and (== larryLoc NewYork)
					(not (Btst fTookDayTrotter))
					(== prevRoomNum 250))

			(dayTrotter init:)
		)

		(Load PICTURE	pBlackPic)			;999
		(Load SCRIPT	RANDCYC)

		(super init:)

		(switch prevRoomNum
			(205
				(HandsOff)
				(= driving TRUE)
				(curRoom
					drawPic: 			pLimousineInterior SCROLLRIGHT,
					setScript:			sDrive,
				)
			)
			(890
				(curRoom drawPic: pLimousineInterior IRISOUT)
			)
			(else
				(curRoom drawPic: pLimousineInterior)
			)
		)

	);init

	(method (newRoom n)

		(if (== n 205)
			(theMusic
				fade:	0 15 10 STOP_MUSIC,
			)
		else
			(theMusic	stop:)
			(= destination 0)
		)
		(theMusic2 stop:)
		(tvSnd stop, dispose:)
		(SetupExit FALSE)

		;hoky way to avoid frags

		(theTimer dispose:, delete:)
		(super newRoom n)
	)

	(method (doit)
		(super doit:)
		(if dreaming
			(Palette PALCycle 56 63 2) ;fromWhat toWhat ticks
		)
	)

	(method (cue)
;		(super cue:)

		(if egoIsLarry
			(switch (++ limoCueCount)
				(1
					(if (OneOf prevRoomNum 190 250)
						(Delay 2 CYCLES self)
					else
						(Say ego "\"Geez,\" you think,
							\"no matter where I go,
							no matter which limousine rental company I use,
							no matter which driver I get...
							all these limos look alike!\""
							#dispose: self
						)
					)
				)

				(2
					(if (not goingToAirport)
						(= limoCueCount 0)
						(HandsOn)
						(User canControl: FALSE)  		
					else
						(Delay 2 CYCLES self)
					)
				)

				(3
					(Bset fBeenToTown)
               (ego stopUpd)
					(driver doVerb: verbUse iGoldCard)
				)
			)

		else
			(HandsOff)
		 	(Say ego "\"Home, Bobby!\"
				you signal to your driver, and he nods in ready agreement.")
			(= destination 425)
			(curRoom setScript sDream)
		)
	)

	(method (notify what)
		(= phonedDesmond FALSE)
		(switch what
			(2779
				(= phonedDesmond TRUE)
			)
			(NO_NUMBER
				(TPrint "You change your mind and hang up the telephone.")
			)
			(else
				(TPrint "Hmmm. Wrong number.")
			)
		)
	)

	(method (doVerb theVerb)
		(switch theVerb
			(verbWalk
				(if (and
						 exitActive
						 (not driving))

         		(SetupExit FALSE)
				 	(curRoom newRoom: prevRoomNum)
				)
			)
			(else
				(super doVerb: theVerb &rest)
			)
		)
	)

);rm200

(instance sFishScript of Script
	(method (changeState newState &tmp var1 var2)
		(switch (= state newState)
			(0
				(cond
					((< meanWhiles 3)
						(fish setLoop: lSwimRight)			;1
					)
					(else 						;**	(== meanWhiles 3)
						(fish setLoop: lSwimSickRight)	;3
					)
;**						(else
;**							(fish setLoop: lFishDead)			;4
;**						)
				)

				(if (not egoIsLarry)
					(self changeState 10)
				else
					(= cycles 1)
				)
			)

			(1
				(= start state)
				(= cycles 1)
			)

			(2
				(if (< (fish x?) 262)
					(cond
						((< meanWhiles 3)
							(fish setLoop: lSwimRight)			;1
						)
						(else						;**	(== meanWhiles 3)
							(fish setLoop: lSwimSickRight)	;3
						)
;**							(else
;**								(fish setLoop: lFishDead)			;4
;**							)
					)
					(fish
						setMotion:		MoveTo (Random 270 304) (Random 87 106) self
					)
				else
					(cond
						((< meanWhiles 3)
							(fish setLoop: lSwimLeft)			;0
						)
						(else 					;**	(== meanWhiles 3)
							(fish setLoop: lSwimSickLeft)		;2
						)
;**							(else
;**								(fish setLoop: lFishDead)			;4
;**							)
					)
					(fish
						setMotion:		MoveTo (Random 241 250) (Random 87 106) self
					)
				)
			)

			(3
				(= seconds 2)
			)

			(4
				(self init:)
			)

			(10
				(= start state)
				(= cycles 1)
			)
			(11
				(if (< (fish x?) 262)
					(= register 1)
					(fish
						setLoop:			lSwimRight,
						setMotion:		MoveTo (Random 270 304) (Random 87 106) self
					)
				else
					(= register 0)
					(fish
						setLoop:			lSwimLeft,
						setMotion:		MoveTo (Random 241 250) (Random 87 106) self
					)
				)
			)
			(12
				(if (not (Random 0 2))
					(if register
						(fish setLoop:	lSwimSickRight)
					else
						(fish setLoop: lSwimSickLeft)
					)
					(= seconds (Random 1 3))
				else
					(= seconds 1)
				)
			)

			(13
				(self init:)
			)
		)
	)
)



(instance fish of Actor
	(properties
		view:			vAquarium			;206
		x 				245
		y 				100
 		description "the fish"
		lookStr		"There's a carsick goldfish in the limousine's aquarium."
		sightAngle 	40
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbDo
			 	(TPrint "As much as you would love to pet the little fishie,
					he would prefer to be left alone.")
			)
			(verbTalk
				(TPrint "Do you think talking to fish will make them grow?")
			)
			(verbZipper
				(TPrint "Geez, you are the little sick-o, aren't you!")
			)
			(else
				(super doVerb: theVerb invItem &rest)
			)
		)
	)
)

(instance glass of Actor
	(properties
		view				vLimoInterior			;200
		loop				lLimoInterior			;0
		cel				cGlass					;3
		x 					156
		y 					38
		z 					0
		heading 			0
 		description 	"the privacy window"
		lookStr 			"A piece of soundproof glass blocks conversation
							between you and your driver."
		sightAngle 		40
		priority			3
		xStep				4
		yStep				4
		illegalBits		$0000
		signal			(| fixedLoop fixPriOn ignrAct ignrHrz)
	)

	(method (cue)
		(super cue:)
		(if (not windowOpen)
			(= windowOpen TRUE)
		else
			(= windowOpen FALSE)
		)
		(self stopUpd)
		(HandsOn)
		(User canControl: FALSE)
		(if (or	driving
					(not okToExit)
					(not exitActive))
			(theIconBar disable: walkIcon)
		)
	)
)





(instance dayTrotter of View
	(properties
		view				vLimoInterior			;200
		cel				cDayTrotter
		loop				lLimoInterior			;0
		x					8
		y					187
		priority			4
		description		"the DayTrotter\05"
		signal			(| fixedCel fixedLoop fixPriOn ignrAct)
	)

	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbLook
				(TPrint "Look! Some poor unfortunate traveler forgot his
					appointment calendar.")
				(= lookedAtDT TRUE)
			)
			(verbDo
				(HandsOff)
            (ego stopUpd:)
				(arm
					init,
					view:			vLarry'sLimo,				;201
					setLoop:		lGettingDayTrotter,		;2
					setCel:		0,
					posn:			67 164,
					setCycle:	CycleTo 2 FORWARD arm
				)
			)
			(else
				(super doVerb: theVerb invItem &rest)
			)
		)
	)
)


(instance legs of Prop
	(properties
		view				vPatti'sLimo			;202
		loop				lPattiLegs				;3
		x					73
		y					181
		priority			13
		description		"your leg"
		lookStr			"You've always felt your legs were one of your
							better assets."
		signal			(| fixedLoop fixPriOn ignrAct)
	)
	; Because the legs have a large nsRect but the leg itself is small,
	;	some control on the limo seat helps distinguish between the leg
	;	and the skip color of the leg view
	(method (onMe evt)
		(if (!= (OnControl CMAP (evt x?) (evt y?)) cCYAN)
			(super onMe: evt)
		)
	)
)


(instance theTv of Feature
	(properties
		x 					161
		y 					91
		z 					0
		heading 			0
		nsLeft 			117
		nsTop 			64
		nsBottom 		118
		nsRight 			205
 		description 	"the television set"
		sightAngle 		40
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbLook
				(if tvOn
					(TPrint "This is a good show, but one you've seen before!")
				else
					(TPrint "This show is SO boring.")
					(TPrint "(Oh, wait. The television isn't on!)" LOW)
				)
			)
			(verbDo
				(if egoIsLarry
					(tvSnd
						number:		sfxTVClickOn,			;201
						loop:			ONCE,						;1
						play:
					)
					(if tvOn
						(= tvOn FALSE)
						(tv hide:)
						(TPrint "You turn the television set off.")
					else
						(= tvOn TRUE)
						(tv show:)
						(TPrint "You turn the television set on.")
					)
				else
					(TPrint "It's broken.")
				)

			)
			(else
				(super doVerb: theVerb invItem &rest)
			)
		)
	)
)

(instance tv of Prop
	(properties
		view				vTVpicture			;205
		x					tvX
		y					tvY
		z 					0
		loop				0			;only loop in the view
		priority			7
		heading 			0
		nsLeft 116
		nsTop 63
		nsBottom 118
		nsRight 			206
 		description 	"the television set"
		sightAngle 		40
	)
	(method (doit)
		(super doit:)
		(if tvOn
			(tv setCel: (Random 0 3))
		)
	)

;**		(method (onMe theObj)
;**			(return (InRect  117 64 205 118 theObj))		;left top right bottom
;**		)

	(method (doVerb)
		(theTv doVerb &rest)
	)
)


(instance phone of Prop
	(properties
		view				vLimoInterior			;200
		loop				lPhoneCel
		x 					161
		y 					271
		z 					100
		heading 			0
		nsLeft 			143
		nsTop 			162
		nsBottom 		174
		nsRight 			186
 		description 	"the telephone"
		sightAngle 		40
		priority			10
		signal			(| fixedLoop fixPriOn ignrAct)
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbLook
				(TPrint "A cellular phone rests conveniently beside you.")
			)
			(verbDo
				(self setScript: sPhone)
			)
			(else
				(super doVerb: theVerb invItem &rest)
			)
		)
	)
)
(instance dreamProp1 of Prop
	(properties
		view				vBoatCouple		;231
		priority			14
		signal			(| fixPriOn ignrAct)
	)
)
(instance dreamProp2 of Prop
	(properties
		view				vBoatCouple		;231
		priority			14
		signal			(| fixPriOn ignrAct)
	)
)
(instance dreamProp3 of Prop
	(properties
		view				vBoatCouple		;231
		priority			14
		signal			(| fixPriOn ignrAct)
	)
)


(instance arm of Prop
	(properties
		view				vLarry'sLimo				;201
		priority			14
		signal			(| fixPriOn ignrAct)
	)

	(method (cue)
		(super cue:)
		(switch (++ armCue)
			(1
				(if (not missedDayTrotter)			;**	he's getting it automatically
			 		(Points ptsTookDaytrotter)
				)
				(ego get: iDayTrotter)
				(Bset fTookDayTrotter) ;**	can take it or be given it on leaving
				(dayTrotter dispose:)
				(Delay 2 CYCLES self)
			)
			(2
				(self setCycle BegLoop self)
			)
			(3
				(= armCue 0)
				(if (not lookedAtDT)
					(TPrint "Look! Some poor unfortunate traveler forgot his
						appointment calendar.")
				)
				(TPrint "Yeah, too bad for the other guy. He'll never know.
					Besides, he would probably want you to have it!")
				(if (not missedDayTrotter)
					(HandsOn)
					(User canControl: FALSE)
					(if (or
							 driving
				 			 (not okToExit)
				 			 (not exitActive))
						(theIconBar disable: walkIcon)
						(self dispose:)
					)
				else
					(curRoom newRoom destination)
				)

			)
		)
	)
)

(instance bottle of View
	(properties
		view			vLimoInterior 			;200
		cel			cBottle 					;0
		loop			lLimoInterior			;0
		x 				41
		y 				111
 		description "the champagne bottle"
		sightAngle 	40
		priority		5
		signal		(| fixedLoop fixPriOn)
	)

	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbLook
				(TPrint "A chilled bottle of champagne awaits your pleasure.")
			)
			(verbDo
				(HandsOff)
				(Bset fTookBottle)
				(curRoom setScript sGetBottle)
			)
			(else
				(super doVerb: theVerb invItem &rest)
			)
		)
	)
)




(instance vcr of Feature
	(properties
		x 					162
		y 					152
		z 					100
		heading 			0
		nsLeft 			114
		nsTop 			45
		nsBottom 		60
		nsRight 			211
 		description 	"the video recorder"
		lookStr 			"A professional quality videotape player rests above
							the television set."
		sightAngle 		40
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbDo
				(TPrint "Too bad it's broken.")
			)
			(else
				(super doVerb: theVerb invItem &rest)
			)
		)
	)
)
(instance tapes of Feature
	(properties
		x 					245
		y 					257
		z 					200
		heading 			0
		nsLeft 			211
		nsTop 			45
		nsBottom 		70
		nsRight 			279
 		description 	"the video tapes"
		lookStr 			"There are many videotapes in that rack that you
							would love to see.  Unfortunately, the VCR is broken!"
		sightAngle 		40
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbDo
				(TPrint "You have no way to play those tapes since the
					limo's videotape player is broken.")
			)
			(else
				(super doVerb: theVerb invItem &rest)
			)
		)
	)
)
(instance fishTank of Feature
	(properties
		x 					266
		y 					94
		z 					0
		heading 			0
		nsLeft 			217
		nsTop 			71
		nsBottom 		118
		nsRight 			316
 		description 	"the aquarium"
		lookStr 			"How bizarre! A limousine with an aquarium!"
		sightAngle 		40
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbDo
				(if (not (Random 0 10))
					(TPrint "You would love to grab the little fishy and pet him
						and make him your friend and take him home and the two
						of you grow old together and spend his final years resting
						in a small bowl of briny water on your nightstand.")
				else
			 		(TPrint "As much as you would love to pet the little fishie,
						he would prefer to be left alone.")
				)
			)
			(else
				(super doVerb: theVerb invItem &rest)
			)
		)
	)
)

(instance dreamBubble of Prop
	(properties
		view				vDreamBubble		;207
		loop				0						;only loop in view
		priority			15
		signal			(| fixedLoop fixPriOn ignrAct)
	)
)

(instance bottles&Glasses of Feature
	(properties
		x 					73
		y 					178
		z 					100
		heading 			0
		nsLeft 			31
		nsTop 			46
		nsBottom 		111
		nsRight 			115
 		description 	"the bottles and glasses"
		lookStr 			"There are enough bottles of liquor and glasses
							to throw a party.
							All you need now is some friends!"
		sightAngle 		40
		onMeCheck		cGREEN
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbDo
				(if (cast contains: bottle)
					(bottle doVerb: verbDo)
				else
					(TPrint "Leave the bottles and glasses alone.")
				)
			)
			(else
				(super doVerb: theVerb invItem &rest)
			)
		)
	)
)


(instance stereo of Feature
	(properties
		x 					165
		y 					248
		z 					100
		heading 			0
		nsLeft 110
		nsTop 117
		nsBottom 178
		nsRight 222
 		description 	"the stereo"
		lookStr 			"The limousine's stereo has both a cassette tape player
							and a CD."
		sightAngle 		40
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbDo
				(TPrint "Both the CD player and the cassette deck are broken.")
			)
			(else
				(super doVerb: theVerb invItem &rest)
			)
		)
	)
)
(instance rightDoor of Feature
	(properties
		x 					286
		y 					146
		z 					0
		heading 			0
		nsLeft 			253
		nsTop 			123
		nsBottom 		170
		nsRight 			319
 		description 	"the cabinet door"
		lookStr 			"A small, locked liquor compartment rests beneath the
							right front passenger's seat.
							You wonder when it will be your turn to live high on
							the hog."
		sightAngle 		40
		onMeCheck		cBLUE
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbDo
				(TPrint "The cabinet is locked!")
			)
			(else
				(super doVerb: theVerb invItem &rest)
			)
		)
	)
)


;**	(instance windowF of View
;**		(properties
;**			view				vLimoInterior			;200
;**			x					158
;**			y					13
;**			loop				lLimoInterior			;0
;**			cel				cWindowF					;4
;**			priority			1
;**			signal			(| fixedCel fixedLoop fixPriOn ignrAct ignrHrz)
;**		)
;**	)


(instance sRoll of Script
	(method (changeState newState &tmp var1 var2)
		(switchto (= state newState)
			(
				(dreamProp1
					setLoop:		(Random 0 1),
					setCycle:	EndLoop self,
				)
			)
			(
				(= cycles (Random 4 10))
			)
			(
				(dreamProp1
					setCycle:	BegLoop self,
				)
			)
			(
				(= cycles (Random 4 10))
			)
			(
				(self init:)
			)
		)
	)
)


(instance driverEyes of Prop
	(properties
		view			vDriverFemale			;204
		loop			lEyes						;1
		x				155
		y				6
		priority		1
 		description "the mirror"
		signal		(| fixedLoop fixPriOn ignrAct ignrHrz)
	)
)

(instance sLegs of Script
	(method (changeState newState)
		(switchto (= state newState)
			(
				(= cycles 2)
			)

			(
				(legs
					setCycle: 	Forward,
					cycleSpeed:	(Random 6 10),
				)
				(= cycles (Random 20 40))
			)
			(
				(legs
					setCel:	0,
					stopUpd:
				)
				(= cycles (Random 10 30))
			)

			(
				(self init:)
			)
		)
	)
)



(instance windowL of Prop
	(properties
		view				vLimoWindows		;208
		loop				0
		x					17
		y					46
		priority			14
 		description 	"the window"
		signal			(| fixedLoop fixPriOn ignrAct ignrHrz)
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbDo
				(TPrint "If you roll down the windows,
					you'll spoil the air-conditioned comfort in which you ride.")
			)
			(verbLook
				(if driving
					(TPrint "It looks like you are moving.")
				else
					(TPrint "Bored silly, you find yourself staring out the
						windows!")
				)
			)
			(else
				(super doVerb: theVerb invItem &rest)
			)
		)
	)
)

(instance tvSnd of Sound)


(instance faxMachine of Feature
	(properties
		x 					59
		y 					213							;has to be larger than larry's
		z 					100
		heading 			0
		nsLeft 			31
		nsTop 			101
		nsBottom 		125
		nsRight 			87
 		description 	"the fax machine"
		sightAngle		40
	)


	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbLook
				(TPrint "How wonderful. Your own personal fax machine.")
				(TPrint "(Doesn't everybody travel like this?)" LOW)
			)
			(verbDo
				(if (and
						 whichFax
						 (not phonedDesmond))
					(HandsOff)
					(faxPaper doVerb: verbDo)
				else
					(TPrint "The fax machine has no way to dial out.
						If you want to make a telephone call,
						your cellular phone rests right beside your right hand.")
				)
			)
			(else
				(super doVerb: theVerb invItem &rest)
			)
		)
	)
)





(instance driverBody of Prop
	(properties
		view			   vDriverFemale				;204
		x				   114
		y				   34
		z              0
		priority			1
		description	   "your limo driver"
		signal			(| fixedLoop fixPriOn ignrAct ignrHrz)
	)
)




(instance driversWindow of Feature
	(properties
		x 					160
		y 					119
		z 					100
		heading 			0
		nsLeft			49
		nsTop				-1
		nsBottom			42
		nsRight			269
		lookStr 			"You are sealed off from your driver by that big
							soundproof glass window."
		sightAngle 		40
	)

	(method (doVerb)
		(driver doVerb &rest)
	)

);driversWindow



(instance sGetBottle of Script
	(method (changeState newState)
		(switchto (= state newState)
			(
				(HandsOff)
				(ego
					view:				vPatti'sLimo,		;202
					setLoop:			lTakingFax,			;1
					setCel:			0,
					cycleSpeed:		6,
					setCycle:		CycleTo 3 FORWARD self,
				)
			)

			(
				(ego
					setLoop:			lTakingBottle,				;2
					setCel:			0,
				)
				(= cycles 1)
			)

			(
				(ego
					cycleSpeed:		6,
					setCycle:		CycleTo 3 FORWARD self,
				)
			)

			(
				(bottle dispose:)
				(ego setCycle: EndLoop self)
			)

			(
				(ego
					setLoop:			6,
					setCel:			0,
					posn:				PATTI_X PATTI_Y,
					get:				iChampagne,
				)
				(if (or
						driving
				 		(not okToExit)
				 		(not exitActive))
					(theIconBar disable: walkIcon)
				)
				(Points ptsTookLimoBottle)
			 	(= cycles 2)
			)

			(
				(HandsOn)
				(User canControl: FALSE)
				(if (not okToExit)
					(theIconBar disable: walkIcon)
				)
				(self dispose:)
			)
		)
	)
);sGetBottle


(instance sDrive of Script
	;
	; destination - room number to go to after
	; (was register)
	;
	;

	(method (doit &tmp [str 10])
		(super doit:)

		(-- tDrive)
		(if (not tDrive)
			(driverBody setCel: (Random 0 2))
			(= tDrive (Random 5 10))
		)
	)

	(method (changeState newState)
		(switch (= state newState)
			(0
				(= driving TRUE)
				(if egoIsLarry
					(HandsOn)
					(User canControl: FALSE)
			  		(theIconBar disable: walkIcon)
				)
				(theMusic
					number:			sfxCarSnd, 		;204
					loop:				FOREVER,			;-1
					play:				127,
				)
				(if (== prevRoomNum 205)
					(windowL
						cycleSpeed: 3,
						setCycle:   Forward,
					)
					(= state (- ARRIVE 1))
					(= seconds 3)
				else
					(windowL
						cycleSpeed: 6,
						setCycle:   Forward,
					)
					(= seconds 5)
				)
			)
			(1
				(windowL
					cycleSpeed 	3,
				)
				(= seconds 5)
			)

			(2
				(windowL
					cycleSpeed 	0,
				)
				(if egoIsLarry
					(= seconds 4)
				else
					(= seconds 12)
				)
			)


			(3
				(= start state)
				(= cycles 2)
			)

			(4
				(cond
					(onPhone
						(self init:)
					)
					((and (not egoIsLarry)
							(not dreamDone))
						(self init:)
					)
				   ((and egoIsLarry goingToAirport)
						;
						;need to remember what destination is
						;
						(curRoom newRoom 205)  ;meanWhile...
					)
					(else
						(= seconds 3)
					)
				)
			)

			(5
				(windowL
					cycleSpeed: 3
				)
				(= seconds 3)
			)

			(6
				(= start state)
				(= cycles 1)
			)

			(7
				(if onPhone
					(self init)
				else
					(HandsOff)
					(driver 		setCycle: EndLoop)
					(driverEyes setCycle: EndLoop)
					(= state (- ARRIVE 1))
					(= cycles 2)
				)
			)

			(ARRIVE
				(Cls)
				(theMusic fade:	0 15 10 FALSE)
				(if egoIsLarry
					(TPrint "\"Sir, we have arrived at your destination!\"
						says your driver.
						\"I hope your journey was a pleasant one.\""
                  #title {Bobbi}
                  #dispose
               )
				   (driver 		setCycle: EndLoop)
				   (driverEyes setCycle: EndLoop)
               (= ticks 90)
				else
					(++ state)
					(theMusic2
						fade:					0 15 10 STOP_MUSIC,
					)
					(= seconds 2)
				)
			)

			(31 							;larry reaches destination
				(Bclr fCalledLimo)
				(if (!= destination 250)
					(Bclr fLimoParked)
				)
				(if (and (== destination 900)
							(ego has: iQuarter))
					(Say ego "\"Thank you for an excellent journey,
						my good woman,\" you tell your chauffeur.
						\"I'll leave a little something for you on the
						seat back here.\"")
					(ego put: iQuarter)
					(= quarters 0)
				)

				(if (and (== destination 500)
							(not (ego has: iDayTrotter)))
					(TPrint "As you are ready to exit the limousine,
						you notice a small leather binder on the seat beside you.
					 	You feel certain the limo company will make no attempt to
						locate its rightful owner, so you take it,
						vowing to search relentlessly until you find its owner
						and return his missing DayTrotter\05 to him!")
					(= missedDayTrotter TRUE)
					(dayTrotter doVerb: verbDo &rest)
				else
					(if (and (Btst fBeenInNewYork)
						 		(ego has: iDayTrotter))
						(ego
							put:	iDayTrotter,
							put:	iCreditCards,
							put:	iMoney,
						)
						(TPrint "You think, \"Who wants a stupid old DayTrotter\05
							anyway?\" So you leave it and all the things that
							came with it on the seat of your limo.
							\"Let somebody else hassle with that thing!\" you think.
							\"I've got better things to do!\"")
					)
					(curRoom newRoom: destination)
				)
			)

			(32
				(Cls)
				(cast eachElementDo: #hide)
				(curRoom drawPic: pBlackPic FADEOUT)	;999
				(CenterDisplay 2 colWhite
					"Meanwhile...\n
					in a plane far, far away...."
				)
				(= seconds 3)
			)

			(33	 								;patti reaches destination
				(switch destination
					(425
						NULL
					)
					(800
						(= pattiLoc Philly)
					)
					(600
						(= pattiLoc Baltimore)
					)
				)
				(curRoom newRoom: 320)	;back on airplane
			)

		)
	)
);sDrive


(instance driver of Prop
	(properties
		view			   vDriverFemale				;204
		x				   114
		y				   34
		z              0
		priority			2
		description	   "your limo driver"
		signal			(| fixedLoop fixPriOn ignrAct ignrHrz)
	)

	(method (cue)
		(super cue:)
		(switch (++ driverCue)
			(1
				(HandsOff)
				(driver 		setCycle: EndLoop)
				(driverEyes setCycle: EndLoop)
				(Delay 2 SECONDS self)
			)
			(2
				(switch destination
					(250
			  			(TPrint "\"Airport, eh?\" says Bobbi.
							\"It's a great place--if you like flying!\""
							#title {Bobbi}
						)
						(Delay 2 CYCLES self)
					)

					(500
			  			(TPrint "\"Hard Disk Cafe, eh?\" says Bobbi.
							\"It's a great place--if you like computers!\""
							#title {Bobbi}
						)
						(Delay 2 CYCLES self)
					)
					(700
						(TPrint "\"Tramp's Casino, eh?\" says Bobbi.
							\"It's a great place--if you like gambling!\""
							#title {Bobbi}
						)
						(Delay 2 CYCLES self)
					)
					(900
					 	(TPrint "\"Doc Pulliam's, eh?\" says Bobbi.
							\"It's a great place--if you like pain!\""
							#title {Bobbi}
						)
						(Delay 2 CYCLES self)
					)
					(600
						(TPrint "\"The Shill Building, eh?\" says Bobby.
							\"It's a great place--if you like music!\""
							#title {Bobby}
						)
						(if (== prevRoomNum 800)
							(Bset fBeenInPhilly)
						)
						(Delay 2 CYCLES self)
					)
					(800
						(TPrint "\"K-RAP Radio, eh?\" says Bobby.
							\"It's a great place--if you like rap!\""
							#title {Bobby}
						)
						(if (== prevRoomNum 600)
							(Bset fBeenInBaltimore)
						)
						(Delay 2 CYCLES self)
					)
				)
			)
			(3
				(driver 		setCycle: BegLoop)
				(driverEyes setCycle: BegLoop)
				(arm setCycle: BegLoop self)
			)
			(4
				(= driverCue 0)
            (ego startUpd:)
				(arm dispose:)
				(if egoIsLarry
					(curRoom setScript: sDrive)
				else
					(curRoom setScript: sDream)	;required!
				)
			)
		)
	)

	(method (doVerb theVerb invItem &tmp [str 80])
		(switch theVerb
			(verbLook
				(if egoIsLarry
					(switch larryLoc
						(LosAngeles
		  					(TPrint "Bobbi Bang, your limo driver,
								is as cute as a button.")
						)
						(NewYork
		  					(TPrint "Bobbi Bangue, your present limo driver,
								is as cute as a pin.")
						)
						(AtlanticCity
		  					(TPrint "Bobbi Bahng, your latest limo driver,
								is as cute as a baby's bottom.")
						)
						(Miami
		  					(TPrint "Bobbi Biang, your current limo driver,
								is as cute as any limo driver you've ever had.")
						)
					)
				else
  					(TPrint "Bobby Bang, your limo driver for the entire tour,
						is about as cute as guys come.")
				)
			)
			(verbDo
				(cond
					(driving
						(TPrint "Please leave your driver alone!")
					)
					(egoIsLarry
						(TPrint "You tap on the glass, but there is no answer.")
					)
					(else
						(HandsOff)
						(theMusic
							number:		sfxWindowUpDown,			;202
							loop:			ONCE,							;1
							play:
						)
						(if (not windowOpen)
							(glass setMotion: MoveTo (glass x?) 78 glass)
						else
							(glass setMotion: MoveTo (glass x?) 38 glass)
						)
					)
				)
			)
			(verbTalk
				(cond
					(driving
						(Format @str "Please leave the driver alone!
							%se's doing the best %se can!"
							(if egoIsLarry {Sh} else {H})
							(if egoIsLarry {sh} else {h})
							)
						(TPrint @str)
					)
					(egoIsLarry
						(switch (Random 1 2)
							(1
								(TPrint "You try to get the driver's attention,
									but she ignores you.")
							)
							(2
			  					(TPrint "The driver points to her ear and shrugs,
			  						indicating she is unable to hear you through
									the soundproof glass window.")
							)
						)
				  		(TPrint "Perhaps you could show her where you want to go.")
					)
					(windowOpen
				  		(TPrint "\"Hello, Miss.
							Thanks for lowering that window.
							I'm sorry, but I can't hear you anyway--I'm deaf!
							Just show me where you want to go.\""
							#title {Bobby}
						)
					)
					(else
						(TPrint "The intercom from the driver's compartment
							crackles to life, \"I'm sorry,
							but I can't hear you through the soundproof glass!\"")
					)
				)
			)
			(verbZipper
				(cond
					(egoIsLarry
						(Say ego "\"Hey, Baby!
							How about you crawling back here and
							letting me drive for a change?!\"")
						(TPrint "She ignores you completely.")
						(TPrint "(An experience not wholly unfamiliar to you!)" LOW)
					)
               ((== pattiLoc Baltimore)
						(TPrint "\"I thought I already tried that!\""
							#title {Bobby}
						)
               )
					((Btst fBeenInBaltimore)
						(TPrint "\"I thought I tried that when we were in
							Baltimore?!\""
							#title {Bobby}
						)
					)
					(driving
						(TPrint "\"Hey, baby,\" says Bobby Bang,
							\"if you're serious about that,
							maybe I should stop the car?!\""
							#title {Bobby}
						)
					)
					(else
						(TPrint "\"Hey, baby,\" says Bobby Bang,
							\"that's okay with me!
							Let's wait until we get to Baltimore!\""
							#title {Bobby}
						)
					)
				)
			)
			(verbUse
				(if egoIsLarry
					(if destination
						(TPrint "\"Please!\" she shouts through the intercom,
							\"It's not safe to bother me while I'm driving!\""
							#title {Bobbi}
						)
					else
						(switch invItem
							(iGoldCard
								(if (== prevRoomNum 250)
									(TPrint "You're at the airport now!" #title {Bobbi})
								else
                           (ego stopUpd)
									(arm
										init,
										view:			vLarry'sLimo,			;201
										setLoop:		lShowDriverStuff,		;1
										posn:			96 163,
										setCel:		0,
										cycleSpeed:	8,
										setCycle:	EndLoop self,
									)
									(= destination 250)
								)
							)
							(iHDCNapkin
								(cond
									((!= larryLoc NewYork)
										(TPrint "\"Waddayou, crazy?\" shouts Bobbi,
											\"I ain't gonna haul you all the way to
											New York City!\""
											#title {Bobbi}
										)
									)
									(goingToAirport
										(TPrint "\"Waddayou, crazy?\" shouts Bobbi,
											\"you're already there!\""
											#title {Bobbi}
										)
									)
									((Btst fBeenInNewYork)
										(TPrint "You have no need to return to
											New York City."
											#title {Bobbi}
										)
									)
									(else
                              (ego stopUpd)
										(arm
											init,
											view:			vLarry'sLimo,			;201
											setLoop:		lShowDriverStuff,		;1
											posn:			96 163,
											setCel:		0,
											setCycle:	EndLoop self,
										)
										(= destination 500)
									)
								)
							)
							(iCasinoMatchbook
								(cond
									((!= larryLoc AtlanticCity)
										(TPrint "\"Waddayou, crazy?\" shouts Bobbi,
											\"I ain't gonna haul you all the way to
											Atlantic City!\""
											#title {Bobbi}
										)
									)
									(goingToAirport
										(TPrint "\"Waddayou, crazy?\" shouts Bobbi,
											\"you're already there!\""
											#title {Bobbi}
										)
									)
									((Btst fBeenInAtlanticCity)
										(TPrint "You have no need to return to
											Atlantic City.")
									)
									(else
                              (ego stopUpd)
										(arm
											init,
											view:			vLarry'sLimo,				;201
											setLoop:		lShowDriverStuff,			;1
											posn:			96 163,
											setCel:		0,
											setCycle:	EndLoop self,
										)
										(= destination 700)
									)
								)
							)
							(iDocPulliam'sCard
								(cond
									((!= larryLoc Miami)
										(TPrint "\"Waddayou, crazy?\" shouts Bobbi,
											\"I ain't gonna haul you all the way to
											Miami.\""
											#title {Bobbi}
										)
									)
									(goingToAirport
										(TPrint "\"Waddayou, crazy?\" shouts Bobbi,
											\"you're already there!\""
											#title {Bobbi}
										)
									)
									((Btst fBeenInMiami)
										(TPrint "You have no need to return to
											Miami."
										)
									)
									(else
                              (ego stopUpd)
										(arm
											init,
											view:			vLarry'sLimo,			;201
											setLoop:		lShowDriverStuff,		;1
											posn:			96 163,
											setCel:		0,
											setCycle:	EndLoop self,
										)
										(= destination 900)
									)
								)
							)
							(else
								(super doVerb: theVerb invItem &rest)
							)
						)
					)

				else		;----------------------PATTI------------	

					(switch invItem
						(iFax1ReverseBiaz	  ;used in baltimore
							(cond
								((== pattiLoc Baltimore)
									(TPrint "\"Oh, come on, lady!\" says Bobby,
										\"that's where you are now!\""
										#title {Bobby}
									)
								)
								((Btst fBeenInBaltimore)
									(TPrint "\"Oh, come on, lady!\" says Bobby,
										\"I'm not taking you back there!\""
										#title {Bobby}
									)
								)
								(destination
									(TPrint "\"I'm sorry, lady,\" says Bobby,
										\"But I can only handle one destination at
										a time!\""
										#title {Bobby}
									)
								)
								(else
									(Points ptsToldLimoDriver pfToldLimoDriver)
									(arm
										view:			vPatti'sLimo,			;202
										init,
										setLoop:		lShowDriverFax,		;4
										setCel:		0,
										posn:			98 182,
										setCycle:	EndLoop self,
									)

									(+= pattiDest toBalt)
									(= destination 600)
									(= phonedDesmond FALSE)
									(faxPaper setScript: 0)
								)
							)
						)

						(iFax2PCHammer		;used in philly
							(cond
								((== pattiLoc Philly)
									(TPrint "\"Oh, come on, lady!\" says Bobby,
										\"that's where you are now!\""
										#title {Bobby}
									)
								)

								((Btst fBeenInPhilly)
									(TPrint "\"Oh, come on, lady!\" says Bobby,
										\"I'm not taking you back there!\""
										#title {Bobby}
									)
								)
								(destination
									(TPrint "\"I'm sorry, lady,\" says Bobby,
										\"But I can only handle one destination at
										a time!\""
										#title {Bobby}
									)
								)
								(else
									(Points ptsToldLimoDriver pfToldLimoDriver)
									(arm
										view:			vPatti'sLimo,			;202
										init,
										setLoop:		lShowDriverFax,		;4
										setCel:		0,
										posn:			98 182,
										setCycle:	EndLoop self,
									)
									(+= pattiDest toPhilly)
									(= destination 800)
									(= phonedDesmond FALSE)
									(faxPaper setScript: 0)
								)
							)
						)
						(iDataMan
							(switch ((Inventory at: iDataMan) state?)
								(0
									(TPrint "Your driver stares blankly
										at the totally blank DataMan.")
								)

								(1	;BALTIMORE?

									(cond
										((== pattiLoc Baltimore)
											(TPrint "\"Oh, come on, lady!\" says Bobby,
												\"that's where you are now!\""
												#title {Bobby}
											)
										)

										((Btst fBeenInBaltimore)
											(TPrint "\"Oh, come on, lady!\" says Bobby,
												\"I'm not taking you back there!\""
												#title {Bobby}
											)
										)

										(destination
											(TPrint "\"I'm sorry, lady,\" says Bobby,
												\"But I can only handle one destination
												at a time!\""
												#title {Bobby}
											)
										)
										(else
											(Points ptsToldLimoDriver pfToldLimoDriver)
											(arm
												view:			vPatti'sLimo,				;202
												init,
												setLoop:		lShowDriverDMan,			;5
												setCel:		0,
												posn:			96 184,
												setCycle:	EndLoop self,
											)
											(+= pattiDest toBalt)
											(= destination 600)
											(= phonedDesmond FALSE)
											(faxPaper setScript: 0)
										)
									)
								)

								(2
									(cond
										((== pattiLoc Philly)
											(TPrint "\"Oh, come on, lady!\" says Bobby,
												\"that's where you are now!\""
												#title {Bobby}
											)
										)
										((Btst fBeenInPhilly)
											(TPrint "\"Oh, come on, lady!\" says Bobby,
												\"I'm not taking you back there!\""
												#title {Bobby}
											)
										)
										(destination
											(TPrint "\"I'm sorry, lady,\" says Bobby,
												\"But I can only handle one destination
												at a time!\""
												#title {Bobby}
											)
										)
										(else
											(Points ptsToldLimoDriver pfToldLimoDriver)
											(arm
												view:			vPatti'sLimo,				;202
												init,
												setLoop:		lShowDriverDMan,			;5
												setCel:		0,
												posn:			96 184,
												setCycle:	EndLoop self,
											)
											(+= pattiDest toPhilly)
											(= destination 800)
											(= phonedDesmond FALSE)
											(faxPaper setScript: 0)
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
			)
			(else
				(super doVerb: theVerb invItem &rest)
			)
		)
	)
);driver
(instance sDream of Script
	(method (changeState newState)
		(switchto (= state newState)
			(
				(HandsOff)
				(driver setScript sDrive)
				(legs setScript: 0, setCel: 0)
				(= seconds 8)
			)

			(
				(= dreaming TRUE)
				(theMusic
					fade:			0 15 5 FALSE
				)
				(theMusic2 stop:)
				(= seconds 2)
			)

			(
				(theMusic2
					number:				mDreamLand,						;320
					loop:					FOREVER,							;-1
					play:					0,
					fade:					127 15 10 DONT_STOP_MUSIC,
				)
				(TPrint "As you drift off to sleep, you begin to dream."
					LOW
					#dispose
				)
				(tv dispose:)
				(fish
					setScript:		0,
					stopUpd:
				)
				(dreamBubble
					init:,
					setLoop:			0,
					setCel:			1,
					posn:				117 120,
				)
				(= ticks 90)
			)

			(
				(dreamBubble
					setCel:			2,
					posn:				148 98,
				)
				(= ticks 90)
			)

			(
				(dreamBubble
					setCel:			3,
					posn:				195 115,
				)
				(= ticks 90)
			)
			(
				(dreamBubble
					setCel:			4,
					posn:				199 124,
				)
				(= ticks 90)
			)

			(
				(Cls)
				(dreamBubble dispose:)
				(fish hide:)
				(bottle hide:)
				(tv hide:)
;	  			(Graph GFillRect 0 0 192 320 PMAP 0 0)	;crude but effective fix

				(switch (++ pattiDream)
					(Tramp						;**	3 waves and a couple on a boat
						(curRoom overlay: 230)
						(dreamProp1
							init:,
							view:				vBoatCouple,		;231
							setLoop:			lCheriRolling, 	;0
							posn:				205 90,
							setScript:  	sRoll,
						)
					)
					(Gates						;**	tv and arms
						(curRoom overlay: 235)
						(dreamProp1
							init:,
							view:				vGatesTv,		;235
							posn:				123 69,
							cycleSpeed:		6,
							setCycle: 		RandCycle,
						)
						(dreamProp2
							init:,
							view:				vGatesArms,		;236
							posn:				231 87,
							cycleSpeed:		8,
							setCycle: 		RandCycle,
						)
					)
					(McDuck					;** dumping money onto
												;** Patti's naked body!

						(curRoom overlay: 240)
						(theMusic2
							number:				sfxCoinsFall,		;241
							play:
						)
						(dreamProp1
							init,
							view:				vGoldPouring,	;240
							setLoop:			lLooseChange,	;0
							setCel:			0,
							cycleSpeed:		8,
							setCycle 		Forward,
							posn:				258 93,
							setPri:			13,
						)
						(dreamProp2
							init:,
							view:				vShovel,			;241
							setLoop:			lPatti,			;0
							posn:				260 106,
							setPri:			12,
							cycleSpeed: 	10,
							setCycle: 		RandCycle,
						)
						(dreamProp3
							init:,
							view:				vShovel,			;241
							setLoop:			lShovel,			;1
							setCel:			0,
							cycleSpeed:		5,
							setCycle:		EndLoop,
							posn:				203 31,
							setPri:			14,
						)
					)
				)
				(= seconds 3)
			)

			(
				(switch pattiDream
					(Tramp
						(Say ego "\"Oh, Donald!\" you moan in your dream.")
						(TPrint "\"The Donald,\" he corrects you."
							#title {Donald}
						)
						(Say ego "\"MY Donald,\" you correct him!
							\"I so love this little boat you bought me...\""
						)
						(TPrint "\"...I'm going to name it `The Tramp Pianist!'\""
							#title {The Donald}

						)
					)
					(Gates
						(Say ego "\"Oh, Willi,\" you moan in your dream.
							\"Put down that keyboard and practice a little
							touch typing on me!\""
						)
						(TPrint "\"Not now, Patti!\" he replies,
							\"I'm about to finish cutting a deal with IBM!\""
							#title {Willie}
						)
					)
					(McDuck
						(Say ego "\"Oh, OH, OOOOOHHHHHH!\" you shout in your dream!
							\"I just love it when you do that!\""
						)
						(TPrint "\"I just hope my three nephews don't come in
							about now!\" he replies."
							#title {Sir Francis}
						)
					)
				)
				(= dreamDone TRUE)
			)

		)
	)
);sDream


(instance sPhone of Script
	(method (changeState newState &tmp var1 var2)
		(switchto (= state newState)
			(
				(HandsOff)
				(Load SOUND sPickUpPhone)
            (ego startUpd:)
            (= cycles 2)
         )

         (
				(= onPhone TRUE)
				(if egoIsLarry
					(ego
                  setLoop:       0,
                  setCel:        0,
						cycleSpeed:		8,
						setCycle: 		CycleTo 5 FORWARD self,
					)
					(= register 5)
				else
					(ego stopUpd)
					(arm
						init,
						view:				vPatti'sLimo,					;202
						posn:				PATTI_X (+ PATTI_Y 100),
						setLoop:			lGetPhone,
						cycleSpeed:		8,
						setCycle: 		CycleTo 3 FORWARD self,
					)
					(legs
						setScript: 		0,
						setCel:	  		0,
						stopUpd:,
					)
				)
			)

			(
				(phone hide:)
				(theMusic2
					number:			sPickUpPhone,
					loop:				1,
					play:				,
				)
				(if egoIsLarry
					(ego setCycle: EndLoop self)
				else
					(arm setCycle:	EndLoop self)
				)
			)
			(
				(if egoIsLarry
					(TPrint "\"I'm sorry, Sir,\" crackles the driver's voice
						through the intercom,
						\"but my cellular telephone won't work in this area.
						Too much interference or something.\"" #title {Bobbi}
					)
					(TPrint "\"Besides, I'm sure you were not given authorization
						for cellular calls.\"" #title {Bobbi}
					)
					(Say ego "DAMN! And all your life you've wanted to try a cell phone!")
					(++ state)
					(= cycles 2)
				else
					(cond
						((and (Btst pfGotInfoRB)
								(Btst pfGotInfoPC))
							(User canInput: TRUE)
							(DialPhone self)
						)
						(phonedDesmond
							(TPrint "Hang up! You have an incoming fax!
								Don't tie up the line.")
							(++ state)
							(= cycles 2)
						)
						((not whichFax)
							(User canInput: TRUE)
							(DialPhone self)
						)
						(else
							(TPrint "You don't need to use the phone right now.
								But perhaps later.")
							(++ state)
							(= cycles 2)
						)
					)
				)
			)

			(									;**	she doesn't have info on both guys
            (HandsOff)
				(theIconBar curIcon: (theIconBar at: doIcon))
				(theGame setCursor: ((theIconBar curIcon?) cursor?))
				(if phonedDesmond
					(TPrint "You dial Inspector Desmond's office telephone.
						Soon a familiar voice answers.")
					(TPrint "\"Hello, this is Desmond.\"" #title {Desmond})
					(cond
						((and (Btst pfGotInfoRB)
								(Btst pfGotInfoPC))
							(Say ego "\"Hi ya, Boss!\" you say cheerfully.
								\"I just wanted to tell you,
								I've found the information I need on
								Reverse Biaz and P. C. Hammer and I'm on my way.\""
							)
							(TPrint "\"Good job, Patti!\" exclaims Desmond,
								\"I knew you wouldn't let us down!\""
								#title {Desmond}
							)
							(Say ego "\"Thanks, Inspector!\" you say cheerfully.
								\"I hope I'll be talking to you soon!\""
							)
						)

						((and (not (Btst pfGotInfoRB))
						 		(not (Btst pfGotInfoPC)))
							(Say ego "\"Hi ya, Boss!\" you say cheerfully.
								\"I was wondering if I might get some
								information from you.\""
							)
							(TPrint "\"Sure, Patti!\" replies Desmond.
								\"What is it you're looking for?\""
								#title {Desmond}
							)
							(= whichFax
								(switch
									(Print "\"Inspector,
										please fax me some information about...\""
										#button {ReverseBiaz}	RBIAZFAX
										#button {PCHammer}		PCHAMMERFAX
										#title {You}
									)
								)
							)
						)
						((Btst pfGotInfoRB)
							(Say ego "\"Inspector,\" you ask,
								\"could you please fax me some information about
								P. C. Hammer?\""
							)
							(TPrint "\"Sure, Patti!\" replies Desmond.
								\"I'll get someone right on it.
								Bye.\""
								#title {Desmond}
							)
							(= whichFax PCHAMMERFAX)
						)
						((Btst pfGotInfoPC)
							(Say ego "\"Inspector,\" you ask,
								\"could you please fax me some information about
								Reverse Biaz?\"")
							(TPrint "\"Sure, Patti!\" replies Desmond.
								\"I'll get someone right on it.
								Bye.\""
								#title {Desmond}
							)
							(= whichFax RBIAZFAX)
						)
					)

					(if whichFax
						(faxPaper
							init:,
							hide:,
							setScript: sFax,
						)
					)
				)
				(= ticks 50)
			)
			(										;**	HANG UP THE PHONE
				(if egoIsLarry
					(ego setCycle: CycleTo 5 BACKWARD self)
				else
					(arm setCycle:	CycleTo 3 BACKWARD self)
				)
			)

			(
				(phone show)
				(theMusic2
					number:			sPickUpPhone,
					loop:				1,
					play:				,
				)
				(if egoIsLarry
					(ego setCycle: BegLoop self)
				else
					(arm setCycle: BegLoop self)
				)
			)

			(
				(if (not egoIsLarry)
					(legs
						startUpd,
						setScript: sLegs,
					)
					(arm dispose:)
				)
				(= onPhone FALSE)
				(= ticks 10)
			)

			(
            (if (and whichFax (not faxHere))
               (HandsOff)
            else
				   (HandsOn)
				   (User canControl: FALSE)

				   (if (or
						   driving
				 		   (not okToExit)
				 		   (not exitActive))
					   (theIconBar disable: walkIcon)
				   )
            )
				(self dispose:)
			)
		)
	)
);sPhone

(instance sFax of Script
	(method (changeState newState)
		(switchto (= state newState)
			(
            (HandsOff)
				(Load SOUND sfxFax)
				(= seconds 3)
         )

         (
				(theMusic
					number:				sfxFax, 		;203
					loop:					ONCE,			;1
					play:					,
				)
				(TPrint "The fax machine awakens...")
            (= ticks 90)
			)

			(
				(faxPaper
					show,
					ignoreActors:		TRUE,
					moveSpeed:			5,
					posn:					55 225,
					setMotion:			MoveTo 55 215 self
				)
			)
			(
				(= phonedDesmond FALSE)
				(HandsOn)
				(User canControl: FALSE)

				(if (or
						driving
				 		(not okToExit)
				 		(not exitActive))
					(theIconBar disable: walkIcon)
				)
            (= faxHere TRUE)
				(self dispose:)
			)
		)
	)
);sFax

(instance faxPaper of Actor
	(properties
		view				vLimoInterior			;200
		loop				lLimoInterior			;0
		cel				cFaxPaper				;2
		x					55
		y					225
		z					100
		xStep				1
		yStep				1
		priority			6
		description		"the fax"
		signal			(| fixedCel fixedLoop fixPriOn ignrAct)
	)

	(method (cue)
		(super cue:)
		(switch (++ faxPaperCue)
			(1
				(HandsOff)
				(self hide)
				(ego setCycle EndLoop self)
			)
			(2
				(ego
					setLoop:		lTorso,					;6
				)
				(Delay 2 CYCLES self)
			)
			(3
				(switch whichFax
					(RBIAZFAX
						(Points ptsGotInfoRB pfGotInfoRB)
						(TPrint "You grab the fax from the machine.
							The header says \"Reverse Biaz.\"")
						(ego get: iFax1ReverseBiaz)
					)
					(PCHAMMERFAX
						(Points ptsGotInfoPC pfGotInfoPC)
						(TPrint "You grab the fax from the machine.
							The header says \"P. C. Hammer.\"")
						(ego get: iFax2PCHammer)
					)
				)
				(= faxPaperCue 0)
				(= whichFax NONE)
            (= faxHere FALSE)
				(HandsOn)
				(User canControl: FALSE)
				(if (or
						driving
				 		(not okToExit)
				 		(not exitActive))
					(theIconBar disable: walkIcon)
				)
				(self dispose:)
			)
		)
	)

	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbDo
				(HandsOff)	
				(ego
					view:			vPatti'sLimo,				;202
					setLoop:		lTakingFax,					;1
					setCel:		0,
					setCycle:	CycleTo 6 FORWARD self,
				)
			)
			(else
				(super doVerb: theVerb invItem &rest)
			)
		)
	)
);faxPaper 

