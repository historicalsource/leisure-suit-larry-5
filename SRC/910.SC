;**
;**	Logics for Room 910 -- Chi Chi's cubicle Larry on chair
;**
;**	LSL5:  Passionate Patti Does A Little Undercover Work
;**				Copyright 1991
;**				by Al Lowe
;**
;**	Forced to work by C. Escobar
;**
;**	Last Update:  September 10, 1991
;**


(script#		910)

(public
	rm910		0
)


(define 	sfxSuction 			912)
(define	vChiChiCubicle		910)

(define	vLarryInCubicle	911)
	(define cSit&Look		0)
	(define cLookOver		1)
	(define cLeanBack		2)
	(define cOpenMouth	3)
	(define cOpenWide 	4)

(define	vChiChi 				912)
(define	vChiChiActions		913)
(define	vChiChiTalker		1910)
(define	vChiChiTalker2		1915)

(define 	pChiChiCloseUp		915)
(define 	vChiChiCloseUp		915)
(define 	vChiChiButton		916)

(define 	FAUCET_X 			225)
(define 	FAUCET_Y				135)

(define  rWaitingRoom		905)
(define  mSalsa				910)


(enum
	1
	WORKING
	DANCING
)

(local
	blouseOpen
	closeUpSecs
	secsDrilled		;amount of time drilled on
	oldTime			;scratchVar
	allDone
	scored
	saidJoke
)


(instance rm910 of LLRoom
	(properties
		picture		910
		east			905
	)

	(method (init)

		;not wearing doily anymore
		((Inventory at: iDoily) state: 0)

		(Load VIEW vChiChiCubicle)
		(Load VIEW vLarryInCubicle)
		(Load VIEW vChiChi)
		(Load VIEW vChiChiActions)

		(super init:)

		(ego
			init:,
			normalize:,
			view:			vLarryInCubicle,
			setLoop:		0,
			setCycle:	0,
			posn:			152 100 -5,
			setCel:		0,
			setPri:		7,
		)
		(faucet
			init:,
			cycleSpeed:	28,
			setCycle: 	Forward,
		)
		;features
		(drill			init:)
		(light			init:)
		(degree			init:)
		(theWindow 		init:)
		(chair			init:)
		(dentalTools	init:)
		(sink 			init:)
		(mouthPic		init:)

		(SetupExit TRUE)
		(HandsOn)

		(Bset fSeenCC)

		(chiChi init:)
		(switch prevRoomNum
			(915
				(chiChi
					view:				vChiChiActions,
					posn:				185 105,
					setLoop:			5,
					setCel:			0,
					cycleSpeed:		8,
				)
				(if (Btst fScoredCC)
					(ego setCel: cLookOver)
					(curRoom setScript sScored)
				else
					(ego
						setCel:		cOpenWide,
					)
					(= secsDrilled 25)
					(curRoom setScript sWorkOnTeeth)
				)
			)
			(else
				(HandsOff)
				(theMusic
					number:		mSalsa,						;910
					loop:			FOREVER,						;-1
					play:
				)
				(curRoom setScript: sBringInChiChi)
			)
		)
	)

	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbLook
				(TPrint "Chi Chi Lambada's Dental Hygiene Heaven cubicle
					is far from typical.
					Instead of Muzak, hot salsa music plays over a powerful
					stereo system.
					And how strange: the center of the carpet shows signs
					of extensive wear."
				)
			)
			(verbUse
				(switch invItem
					(iGreenCard
						(TPrint "Try establishing eye contact first.")
					)
					(else
						(TPrint "She's not interested in that, Larry.
							Talk to her; use your charm."
						)
					)
				)
			)
			(verbTalk
				(TPrint "Try establishing eye contact first.")
			)
			(verbZipper
				(TPrint "Is that the only thing on your mind?")
				(TPrint "(Don't answer that!)" LOW)
			)
			(verbWalk
				(HandsOff)
				(Say ego "\"I can't take any more of this, Ms. Lambada,\"
					you cry, \"I hate the taste of latex gloves!\""
				)
				(theMusic fade)
				(curRoom newRoom 905)
			)
			(verbDo
				(Say ego "\"You know, Ms. Lambada,\"
					you say as she inserts her hands into your mouth,
					\"I wmwpafz fqpzvi uht cpwexf kpazxccxwq!\""
				)
			)
			(else
				(TPrint "You can't do that while lying in a dentist's chair.")
			)
		)
	)

	(method (doit)
		(super doit:)
		(if (!= oldTime (GetTime SYSTIME1))
			(= oldTime (GetTime SYSTIME1))
			(++ secsDrilled)
		)
	)
)

(instance faucet of Prop
   (properties
      view         	vChiChiCubicle
		loop				1
      x            	FAUCET_X
      y            	FAUCET_Y
      description  	"the faucet"
      lookStr      	"The faucet is drip-, drip-, dripping,
							right in time to the music that's playing!"
      sightAngle   	90
		priority			8
		signal			(| fixedLoop fixPriOn ignrAct)
   )
)

(instance mouthPic of Feature
	(properties
		x					198
		y					48
		z					0
		heading			0
		nsLeft			176
		nsTop 			33
		nsBottom 		63
		nsRight 			221
 		description		"the happy tooth poster"
		lookStr			"If you maintained a regular schedule of oral hygiene
							with Chi Chi Lambada, your teeth would be happy too!"
		sightAngle		40
	)
)
(instance drill of Feature
	(properties
		x					149
		y					33
		z 					0
		heading 			0
		nsLeft 			123
		nsTop 			19
		nsBottom 		47
		nsRight 			175
 		description 	"the drill"
		lookStr 			"Whenever you see a high-speed dental drill,
							you make a silent vow to floss more often!"
		sightAngle 		40
	)
)
(instance sink of Feature
	(properties
		x					217
		y 					132
		z 					0
		heading 			0
		nsLeft 			193
		nsTop 			118
		nsBottom 		146
		nsRight 			242
 		description 	"the sink"
		lookStr 			"You love it when a woman orders you to \"Spit!\""
		sightAngle 		40
	)
)
(instance dentalTools of Feature
	(properties
		x 					124
		y 					110
		z 					0
		heading 			0
		nsLeft 			80
		nsTop 			80
		nsBottom 		141
		nsRight 			169
 		description 	"the tray of dental tools"
		lookStr 			"You've always believed dental professionals have
							latent masochistic tendencies."
		sightAngle 		40
	)
)
(instance chair of Feature
	(properties
		x					160
		y 					93
		z 					0
		heading 			0
		nsLeft 			119
		nsTop 			60
		nsBottom 		127
		nsRight 			202
 		description 	"the dentist's chair"
		lookStr 			"You just love electric furniture!"
		sightAngle 		40
	)
)
(instance theWindow of Feature
	(properties
		x					93
		y 					48
		z 					0
		heading 			0
		nsLeft 			81
		nsTop 			21
		nsBottom 		76
		nsRight 			106
 		description 	"the window"
		lookStr 			"Through the window you see children laughing, skipping,
							running, playing; all oblivious to their oral hygiene!"
		sightAngle 		40
	)
)
(instance degree of Feature
	(properties
		x 					123
		y 					51
		z 					0
		heading 			0
		nsLeft 			112
		nsTop 			41
		nsBottom 		61
		nsRight 			134
 		description 	"Chi Chi's degree"
		lookStr 			"Interesting. You never knew the Columbia School of
							Broadcasting even HAD a dental college!"
		sightAngle 		40
	)
)
(instance light of Feature
	(properties
		x 					155
		y 					50
		z 					0
		heading 			0
		nsLeft 			147
		nsTop 			43
		nsBottom 		58
		nsRight 			163
 		description 	"the spotlight"
		lookStr 			"You love to peer at the reflection of your mouth
							on the surface of the dental spotlight."
		sightAngle 		40
	)
)

(instance theTalker of Talker
	(properties
		view				vChiChiTalker
		loop				3
		nsLeft			22
		nsTop				33
      name 				"Chi Chi Lambada"
		viewInPrint 	TRUE
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
		view			vChiChiTalker
		loop			1
		nsLeft		0
		nsTop			0
	)
)
(instance talkerEyes of Prop
	(properties
		view			vChiChiTalker
		loop			2
		nsLeft		34
		nsTop			25
		cycleSpeed  18
	)
)
(instance talkerMouth of Prop
	(properties
		view			vChiChiTalker
		loop			0
		nsLeft		27
		nsTop			33
	)
)


(instance sCloseUp of Script
	(method (changeState newState)
		(switchto (= state newState)
			(
				(HandsOff)
				(= cycles 2)
			)

			(
				(chiChi
					setLoop:			5,
					setCel:			LASTCEL,
					setCycle:		BegLoop self,
				)
			)

			(
				(curRoom newRoom 915)
			)
		)
	)
);sCloseUp



(instance sBringInChiChi of Script
	(method (changeState newState &tmp var1 var2)
		(switchto (= state newState)
			(
				(HandsOff)
				(= ticks 60)
			)

			(
				(Say ego "You think to yourself,
					\"Where else do you pay someone lots of money to insert metal
					objects into your mouth in order to inflict lots of pain?\""
					#dispose: self
				)
			)
			(
				(= ticks 60)
			)
			(
				(ego
					setCel:			cLookOver,			;1
					cycleSpeed:		6,
				)
				(= ticks 30)
			)
			(
				(TPrint "You hear someone coming."
					#dispose: self
				)
			)
			(
				(= seconds 3)
			)
			(
				(chiChi
					view:				vChiChi,
					setLoop:			1,
					setCel:			-1,
					setCycle:		Forward,
					cycleSpeed:		6,
					setStep:			3 2,
					moveSpeed:		6,
					ignoreActors:	TRUE,
					illegalBits:	0,
					setMotion:		MoveTo 185 105 self,
				)
			)
			(
				(chiChi
					view:				vChiChiActions,
					setLoop:			1,
					setCel			0,
					setCycle:		0,
					setMotion:		0,
			 	)
				(= ticks 30)
			)
			(
				(Say theTalker "\"Well, Mr. Laffer, shall we get started?\""
					#dispose
					#caller: 		self
				)
			)
			(
				(ego
					setCel:			cSit&Look,			;0
					stopUpd:
				)
				(= ticks 30)
			)
			(
				(Say ego "\"Oh, I don't know,\" you protest.
						\"I hate dental work so much,
						I think I'd rather have a baby!\"" LOW
				)
				(= ticks 60)
			)
			(
				(Say theTalker "\"Well, make up your mind,\" says Chi Chi.
					\"I've gotta adjust the chair!\""
					#dispose
					#caller: 		self
				)
				(ego
					setCel:			cLeanBack,			;2
					stopUpd:
				)
			)
			(
				(= ticks 60)
			)
			(
				(chiChi
					view:				vChiChiActions,
					setLoop:			5,
					setCel:			0,
					cycleSpeed:		8,
					setCycle:		EndLoop self,
				)
				(ego
					setCel			cOpenWide,
					stopUpd:
				)
			)

			(
;				(TPrint "Something about the sound of that suction hose
;						excites you!"
;				)
;				(theMusic2
;					number:		sfxSuction, 			;912
;					loop:			FOREVER,					;-1
;					play:
;				)
				(= seconds 4)
			)
			(
				(HandsOn)
				(User canControl: FALSE)
				(theMusic2 stop:)
				(= secsDrilled 0)
				(= seconds 4)
			)

			(
				(Say theTalker "\"Say, Larry.
					Have you ever considered dental floss as an aphrodisiac?\""
					#dispose
					#caller: self
				)
			)

			(
				(curRoom setScript sWorkOnTeeth)
			)
		)
	)
)

(instance sWorkOnTeeth of Script
	(method (changeState newState)
		(switchto (= state newState)
			(
				(chiChi
					cycleSpeed:		(Random 3 6),
					setLoop:			6,
					setCel:			0,
					setCycle:		EndLoop self,
				)
			)
			(
				(if (and (> secsDrilled 30) (not saidJoke))
					(Say theTalker
						"\"Just a few more seconds and I'll have that nasty
						old tooth of yours pu..., er, uh, cleaned!\""
						#dispose
						#caller: self
					)
					(= saidJoke TRUE)
				else
					(= ticks 60)
				)
			)

			(
				(= ticks 30)
			)
			(
				(chiChi
					setCycle:		BegLoop self,
				)
			)
			(
				(= ticks 60)
			)
			(
				(if (> secsDrilled 35)
					(HandsOff)
					(Say theTalker "\"I'm all done, Mr. Laffer!\""
						#dispose
						#caller: self
					)
					(chiChi
						setLoop:			5,
						setCel:			LASTCEL,
						cycleSpeed:		8,
						setCycle:		BegLoop self,
					)
					(theMusic fade)
				else
					;loop back and keep working on his mouth!
					(self init)
				)
			)

			(
				NULL
			)

			(
				(curRoom newRoom rWaitingRoom)		;905
			)
		)
	)
)



(instance sDance of Script
	(method (changeState newState &tmp var1 var2)
		(switchto (= state newState)
			(
				(= register 30) 	;how long to dance for
				(self cue:)
			)
			(
				(= start state)
				(chiChi
					view:				vChiChiActions,
					setLoop:			(Random 2 4),
					setCycle:		Forward,
					cycleSpeed:		(Random 5 12),
				)
				(= ticks (Random 10 30))
			)
			(
				(if (not (-- register))
					(self dispose:)
				else
					(self init:)
				)
			)
		)
	)
)



(instance chiChi of Actor
   (properties
      view         	vChiChi
      x            	259
      y            	105
      description  	"Chi Chi Lambada"
      sightAngle   	90
		priority			8
		signal			(| fixedLoop fixPriOn ignrAct)
   )

	(method (cue)
		(super cue:)
		(curRoom newRoom 915)
	)

	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbDo
				(TPrint "Wow!
					You can now readily understand how she got that name!
					Take a look at those Chi Chi's!")
			)

			(verbLook
				(HandsOff)
				(curRoom setScript: 0)	; get rid of sWorkOnTeeth
				(chiChi
					setLoop:			5,
					setCel:			LASTCEL,
					cycleSpeed:		8,
					setCycle:		BegLoop self,
				)
			)

			(verbTalk
				(TPrint "Try establishing eye contact first.")
			)

			(verbZipper
				(Say theTalker "\"Well, I might be interested, Larry,\"
					says Chi Chi.
					\"But what about a little conversation first?\""
					#dispose
				)
			)
			(verbWalk
				(curRoom doVerb: verbWalk)
			)
			(else
				(TPrint "Try establishing eye contact first.")
			)
		)
	)
);chiChi

(instance sScored of Script
	(method (changeState newState &tmp var1 var2)
		(switchto (= state newState)
			(
				(HandsOff)
				(= cycles 2)
			)

			(
				(ego
					setCel:	cLookOver,			;1
				)
				(self setScript sDance self)
			)

			(
				(chiChi
					setCycle: FALSE,
					stopUpd,
				)

				(Points ptsHadSexCC pfHadSexCC)
				(Say theTalker "\"Why don't we go downstairs to my apartment
					behind the gymnastics studio where I'll be able to express
					my appreciation in a more acceptable manner?\""
					#dispose
					#caller: self
				)
			)

			(
				(= ticks 90)
			)

			(
				(TPrint "(All right, Larry!
					It sounds like you're going to get a home-cooked meal
					for a change!)" WIDELOW
				)
				(= ticks 123)
			)
			(
  				(curRoom drawPic: pBlackPic IRISIN)
				(= seconds 3)
			)
			(
				(theMusic fade)
				(CenterDisplay 3 colWhite
				  "A few minutes later,\n
				   downstairs in the\n
					gymnastics studio..."
				)
				(= seconds 3)
			)
			(
				(curRoom newRoom 920)
			)
		)
	)
)

