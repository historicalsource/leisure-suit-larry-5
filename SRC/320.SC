;**
;**	Logics for Room 320 -- Larry dreaming on the plane
;**
;**	LSL5:  Passionate Patti Does A Little Undercover Work
;**				Copyright 1991
;**				by Al Lowe Associates Inc.
;**
;**	Forced to work by C. Escobar
;**
;**	Last Update:  Wednesday, September 25, 1991 
;**


(script#		320)

(public
	rm320		0
)

(procedure
	RetControl
)

(define	MY_BIG	#at: -1  17 #width: 280)

;sounds

(define	sZipper				347)
(define	sfxLand				312)
(define	mDreamLand			320)
(define	sPlaneSnd			321)
(define	sSqueezeNuts		322)

(define	mMozart				330)
(define	sApplause			331)

(define	mSexySax				345)
(define	mMusicEnds			346)

(define	mGondola				350)
(define	sPoleToing			351)
(define	sSpringLeak			352)

(define	mJazzPop				370)

(define	mOboeSnake			375)

(define	mAsTimeGoesBy		380)
(define	sDC3Engines			381)


(define	pSittingIn1stClass		320)

(define	vAirplaneProps 			320)
	(define	lMagazine			0)
	(define	lCloud				1)
	(define	lArmRest 			2)

(define	vLarryInAirplane 			321)
	(define	lBody					0)
	(define	lFallingAsleep		1)
	(define	lArmCels				2)
		(define	cRightArm 	0)
		(define	cLeftArm		1)

(define	vLarrySqueezingNutBags	323)
	(define	lRightArm 			0)
	(define	lLeftArm				1)

(define	vLarrySpillingDrink 		324)
	(define	lSpillCells			0)
		(define	cFace		0)
		(define	cArm1		1)
		(define	cArm2		2)

(define	vLarryPushingUpTray		325)
	(define	lSittingUp			0)
	(define	lLookingAround		1)
	(define	lRArm					2)
	(define	lLArm					3)
	(define	lTrayCels			4)
		(define	cTrayDown	0)
		(define	cTrayUp		1)

(define vVenice						351)
	(define lPattiInBoat			3)

(define	vDreamBubbles				322)

(define	vLarryBogieTalker			1381)
(define	vPattiIngridTalker		1382)
(define  magazineHere (and (not (Btst fDumpedMagazine)) (not (ego has: iMagazine))))



(local
	cueCounter			;just another name for WORK var (agi R.I.P)
	dreaming				;TRUE or FALSE for palCycling...
	palNumber			;palCycle from this number
							;so that 330.p56 and 335.p56 work
	choice
)


(instance rm320 of LLRoom
	(properties
		picture		pSittingIn1stClass
		style			IRISOUT
	)

	(method (init)
		(if (== prevRoomNum 101)
			(= choice
				(PrintD {Enter dream to debug:}
					#new
					#button {Dream of Athens}				1
					#new
					#button {Wake up from Athens}			5
					#new
					#button {Dream of Venice}				2
					#new
					#button {Wake up from Venice}			6
					#new
					#button {Dream of Taj Mahal}			3
					#new
					#button {Wake up from Taj Mahal} 	7
					#new
					#button {Dream of Casablanca}			4
				)
			)
			(switch choice
				(1
					(= REMState Athens)
				)
				(2
					(= REMState Venice)
				)
				(3
					(= REMState TajMahal)
				)
				(4
					(= REMState Casablanca)
				)
				(5
					(= prevRoomNum 200)
					(= REMState Athens)
				)
				(6
					(= prevRoomNum 200)
					(= REMState Venice)
				)
				(7
					(= prevRoomNum 200)
					(= REMState TajMahal)
				)
			)
		)


      (chair init:)
      (floor init:)
		
      (super init:)

		(if (!= prevRoomNum 200)
			(theMusic
				number:				sPlaneSnd,
				loop:					-1,
				play:
			)
		)
	  	(cloud
	  		init:,
	  		setStep:				1 1,
	  		posn:					244 (Random 30 44),
	  		setMotion:			MoveTo 63 (cloud y?) cloud,
	  	)
		(switch prevRoomNum
			(200
				(Load SOUND sfxLand) 		;312
				(= dreaming TRUE)
				(switch REMState
					(Athens
	  					(larry
							init:,
							setCel:	1,
							stopUpd,
						)
						(if (== pattiDest toBalt)
							(= REMState Venice)
						else
							(= REMState TajMahal)
						)
						(Load SOUND sZipper)
						(self setScript: sWakeUpAthens)	;Athens
					)
					(Venice
	  					(larry
							init:,
							setCel:	1,
							stopUpd,
						)
						(if (and	(Btst fBeenInPhilly)
						 			(Btst fBeenInBaltimore))
							(= REMState Casablanca)
						else
							(= REMState TajMahal)
						)
						(self setScript: sWakeUpVenice)	;Venice
					)
					(TajMahal
						(if (and	(Btst fBeenInPhilly)
						 			(Btst fBeenInBaltimore))
							(= REMState Casablanca)
						else
							(= REMState Venice)
						)
						(self setScript: sWakeUpTaj)	;TajMahal
					)
				)
			)
			(else
				(Load VIEW vDreamBubbles)	;322
	  			(larry 			init:, stopUpd)
	  			(body 			init:, stopUpd)
	  			(arm				init:, stopUpd)
	  			(tray				init:, stopUpd)
	  			(arm2 			init:, stopUpd)

				(if magazineHere 
					(theMagazine init:)
					(magazine init:)
				)
				(self setScript sFlyingCoach)
			)
		)
		(HandsOff)

	);init

	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbLook
				(TPrint "For the first game in your life,
					you are traveling First Class.
					You feel certain you could easily grow accustomed to such
					luxury.
					You kick back in the leather seat, relax, and find yourself
					growing rather sleepy.")

				(if magazineHere 
					(TPrint "In fact, you feel too sleepy to even read the
						stupid \"AeroDork In-Flight Magazine\" in the seatback
						pocket in front of you.")
				)
            (return: TRUE)
			)
			(verbTalk
				(TPrint "You think about ordering a drink,
					but instead you find yourself growing sleepy.")
			)
			(verbDo
				(TPrint "There's nothing to do here in First Class--they have
					people to do things for you!")
			)
			(else
				(super doVerb: theVerb invItem &rest)
			)
		)
	)

	(method (doit)
		(super doit:)
		(if dreaming
			(Palette PALCycle palNumber (+ palNumber 7) 6) ;fromWhat toWhat direction
		)
	)
)

(instance sFlyingCoach of Script
	(method (changeState newState)
		(switchto (= state newState)
			(
				(= cycles 3)
			)
			(
				(User canInput: TRUE)
				(Load SOUND mDreamLand)		;320
				(switch REMState
					(Athens
						(SetFFRoom		400)
						(Load VIEW		330)
						(Load VIEW		335)
						(Load VIEW		341)
						(Load PICTURE	330)
						(Load PICTURE	335)
						(Load PICTURE	340)
						(Load SOUND		mMozart)			;330
					)
					(Venice
						(SetFFRoom 600)
						(Load VIEW 350)
						(Load VIEW 351)
						(Load VIEW 321)
						(Load VIEW 356)
						(Load PICTURE 350)
						(Load PICTURE 355)
					)
					(TajMahal
						(SetFFRoom 800)
						(Load VIEW 370)
						(Load VIEW 371)
						(Load PICTURE 370)
						(Load SOUND 370)
					)
					(Casablanca
						(SetFFRoom 385)
						(Load VIEW 380)
						(Load PICTURE 380)
					)
				)

				;this is where you can now take the magazine, look around, etc...
				(RetControl)
				(= seconds (Random 10 20))
			)
			(
				(HandsOff)
				(larry setCel:	1)
				(theMusic
					number:				sPlaneSnd,
					fade:					0 15 10 STOP_MUSIC,
				)
				(theMusic2
					number:				mDreamLand,		;320
					loop:					-1,
					play:					10,
					fade:		  			127 15 5 DONT_STOP_MUSIC,
				)
				(= seconds 3)
			)
			(
				;falling asleep...
				(larry
					addToPic:,
					dispose:
				)
				(= seconds 2)			;at least 2 cycles...
			)
			(
				(dreamProp1
					init,
					view:			vDreamBubbles,
					setCel:		1,
					posn:			146 37,
				)
				(= ticks 40)
			)
			(
				(dreamProp1
					setCel:		2,
					posn:			171 51,
				)
				(= ticks 40)
			)
			(
				(dreamProp1
					setCel:		3,
					posn:			195 78,
				)
				(theMusic2			;fade away larry's dreaming music
					fade 0 15 5 STOP_MUSIC
				)
				(= ticks 60)
			)
			(
				;fade in music
				(switch REMState
					(Athens
						(theMusic
							number:		mMozart,			;330
							loop:			ONCE,
							play:			30,
							fade:		  	127 15 10 DONT_STOP_MUSIC,
						)
					)
					(Venice
						(theMusic
							number:		mGondola,
							loop:	 		-1,
							play:			30,
							fade:			127 15 10 DONT_STOP_MUSIC,
						)
					)
					(TajMahal
						(theMusic2
							number:		mJazzPop,			;370
							loop:			-1,
							play:			30,
							fade:			127 15 10 DONT_STOP_MUSIC,
						)
					)
					(Casablanca
						(theMusic
							number:		sDC3Engines,	;381
							loop:			-1,
							play:			30,
							fade:			127 15 10 DONT_STOP_MUSIC,
						)
					)
				)
				(= ticks 40)
			)
			(
				(dreamProp1
					setCel:		4,
					posn:			201 122,
				)
				(= ticks 30)
			)
			(
				(= dreaming TRUE)
				(switch REMState
					(Athens
						(self setScript sDreamAthens)
					)
					(Venice
						(self setScript sDreamVenice)
					)
					(TajMahal
						(self setScript sDreamTaj)
					)
					(Casablanca
						(self setScript sDreamCasa)
					)
				)
			)
		)
	)
);sFlyingCoach

(instance larry of Actor			;larry's head
	(properties
		view					321
		loop					lFallingAsleep	;1
		x						134
		y						70
		priority 			4
		description			"your head"
		signal				(| fixedLoop fixPriOn ignrAct)
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbLook
				(TPrint "You've hated airplane travel ever since
					\"Leisure Suit Larry 2: Looking for Love
					(in Several Wrong Places).\"")
				(TPrint "(Another fine Al Lowe game.)" LOW)
			)
			(verbTalk
				(TPrint "You briefly consider talking to yourself but then
					realize you already know what you were going to say!")
			)
			(verbDo
				(TPrint "You tighten your seat belt and wish this flight were
					over.")
				(TPrint "(Perhaps on this trip,
					you'll be able to get a little shut-eye.)" LOW)
			)
			(else
				(super doVerb: theVerb invItem &rest)
			)
		)
	)
)

(instance tray of Prop
	(properties
		view					325
		loop					lTrayCels	;4
		cel					cTrayDown	;0
		x						202
		y						204
		z						100
		priority 			4
		description			"the tray"
		signal				(| fixedLoop fixPriOn ignrAct)
	)
	(method (onMe theObj)
		(return (InRect 164 80 199 96 theObj))		;left top right bottom
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbLook
				(TPrint "This tray sometimes holds your food.")
			)
			(verbTalk
				(TPrint "You consider eating some of that delicious airline
					food, but realize you are getting sleepy.")
			)
			(verbDo
				(TPrint "You could fold up the tray table but then you'd have
					no place to rest your stomach!")
			)
			(else
				(super doVerb: theVerb invItem &rest)
			)
		)
	)
)

(instance arm of Prop			;must be prop
	(properties
		view					321
		loop					lArmCels		;2
		cel					cRightArm	;0
		x						126
		y						79
		priority 			5
		description 		"your arm"
		lookStr 				"You've always liked this arm."
		signal				(| fixedLoop fixPriOn ignrAct)
	)
)
(instance arm2 of Prop
	(properties
		view					vLarryInAirplane	;321
		loop					lArmCels
		cel					cLeftArm
		x						173
		y						84
		priority 			5
		description 		"your arm"
		lookStr				"You've always liked this arm."
		signal				(| fixedLoop fixPriOn ignrAct)
	)
)
(instance body of Prop
	(properties
		view					vLarryInAirplane	;321
		loop					lBody
		x						165
		y						125
		priority 			2
		description			"your body"
		signal				(| fixedLoop fixPriOn ignrAct)
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbLook
				(TPrint "What a body!")
				(TPrint "(You need!)" LOW)
			)
			(verbTalk
				(larry doVerb theVerb invItem)
			)
			(else
				(super doVerb: theVerb invItem &rest)
			)
		)
	)
)
(instance cloud of Actor
	(properties
		view					320
		loop					lCloud	 	;1
		x						244
		y						44
		priority 			0
		description			"the cloud"
		signal				(| fixedLoop fixPriOn ignrAct)
	)
	(method (cue)
		(switch (++ cueCounter)
			(1
				(Delay (Random 2 5) SECONDS self)
			)
			(2
				(= cueCounter 0)
				(cloud
					posn:				244 (Random 30 44),
					setMotion:		MoveTo 63 (cloud y?) self,
				)
			)
		)
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbLook
				(TPrint "Clouds drift lazily by outside your window,
					at about 650 miles per hour!")
			)
			(verbDo
				(TPrint "You reach over and leave your fingerprints on the window,
					thus ruining the next amateur photographer's chances of
					getting a clear picture of the ground.")
			)
			(else
				(super doVerb: theVerb invItem &rest)
			)
		)
	)
)

(instance patti of Prop
	(properties
		view					320
		priority 			14
		signal				(| fixedLoop fixPriOn ignrAct)
	)
)
(instance dreamProp1 of Prop
	(properties
		view					320
		priority 			14
		signal				(| fixedLoop fixPriOn ignrAct)
	)
)
(instance dreamProp2 of Prop
	(properties
		view					320
		priority 			14
		signal				(| fixedLoop fixPriOn ignrAct)
	)
)
(instance dreamProp3 of Prop
	(properties
		view					320
		priority 			14
		signal				(| fixedLoop fixPriOn ignrAct)
	)
)
(instance dreamProp4 of Prop
	(properties
		view					320
		priority 			14
		signal				(| fixedLoop fixPriOn ignrAct)
	)
)
(instance dreamProp5 of Prop
	(properties
		view					320
		priority 			14
		signal				(| fixedLoop fixPriOn ignrAct)
	)
)

;this is the stuff the Patti crowd throws

(instance conf of Prop
	(properties
		view					340
		priority 			14
		x						181
		y						74
		loop					0
		signal				(| fixedLoop fixPriOn ignrAct)
	)
	(method (cue)
		(super cue:)
		(switch (Random 1 10)
			(1
				(self
					show,
					setLoop:		0,
					posn: 		181 74,
					setCel:		0,
					setCycle:	EndLoop self,
				)
			)
			(2
				(self
					show,
					setLoop:		0,
					posn: 		219 77,
					setCel:		0,
					setCycle:	EndLoop self,
				)
			)
			(3
				(self
					show,
					setLoop:		0,
					posn: 		246 73,
					setCel:		0,
					setCycle:	EndLoop self,
				)
			)
			(4
				(self
					show,
					setLoop:		1,
					posn: 		169 74,
					setCel:		0,
					setCycle:	EndLoop self,
				)
			)
			(5
				(self
					show,
					setLoop:		1,
					posn: 		212 78,
					setCel:		0,
					setCycle:	EndLoop self,
				)
			)
			(6
				(self
					show,
					setLoop:		1,
					posn: 		227 65,
					setCel:		0,
					setCycle:	EndLoop self,
				)
			)
			(else
				(self hide)
				(Delay (Random 2 6) CYCLES self)
			)
		)
	)
)

(instance sSqeezeNuts of Script
	(method (changeState newState)
		(switchto (= state newState)
			(
				(theMusic3
					number:			sSqueezeNuts,
					loop:				1,
					play:				self,
				)
				(arm
					setCycle: 		EndLoop,
				)
				(arm2
					setCycle: 		EndLoop,
				)
			)
			(
				(arm setCel: 0)
				(arm2 setCel: 0)
				(= ticks 30)
			)
			(
				(self init:)
			)
		)
	)
)

(instance magazine of View
	(properties
		view					320
		loop					lMagazine	;0
		x						208
		y						114
		priority 			1
		description 		"the airline magazine"
		signal				(| fixedLoop fixPriOn ignrAct)
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbLook
				(TPrint "There's a copy of AeroDork Airlines' complimentary
					magazine, \"Fly the Dorky Skies.\"")
			)
			(verbDo
				(ego get: iMagazine)
				(theMagazine dispose:)
				(Points ptsTookMagazine)
				(TPrint "You take the magazine because you never know when
					you might be having so much fun that you need something
					boring to read just to calm down.")
				(magazine dispose:)
				(self dispose:)
			)
			(else
				(super doVerb: theVerb invItem &rest)
			)
		)
	)
)

(instance MyForward of Forward
	(method (cycleDone)
		(client cycleSpeed: (Random 6 10))
		(super cycleDone &rest)
	)
)
(procedure  (RetControl)
	(Cls)
	(HandsOn)
	(User canControl: FALSE)
	(theIconBar disable: walkIcon)
)



;**	Here are the four dream sequences, presented in this order:
;**			Dream of Athens
;**			Wake up from Athens
;**			Dream of Venice
;**			Wake up from Venice
;**			Dream of TajMahal
;**			Wake up from TajMahal
;**			Dream of Casablanca


(instance sDreamAthens of Script
	(method (doit)
		(super doit:)
		(if (not state)
			(switch (theMusic prevSignal?)
				(10
					(theMusic prevSignal: 0)
					(patti setCycle: 0)
				)
				(-1
					(theMusic prevSignal: 0)
					(self cue:)
				)
			)
		)
	)
	(method (changeState newState &tmp var1 var2)
		(switchto (= state newState)
			(
				(if magazineHere
					(magazine dispose)
				)
				(dreamProp1 dispose:)
				(body 		dispose:)
				(arm			dispose:)
				(tray 		dispose:)
				(cloud 		dispose:)
				(arm2 		dispose:)
				(patti
					init:,
					view:				330,
					posn:				172 125,
					cycleSpeed:		10,
					setCycle:		RandCycle,
				)
				(Graph GFillRect 0 0 192 320 PMAP 0 0)
				(= palNumber 40)
				(curRoom overlay: 330)
				;we will get our cue when music finishes
			)
			(
				;patti on stage far shot playing piano
				(= dreaming FALSE)
				(curRoom drawPic (curRoom picture?))
				(Graph GFillRect 0 0 192 320 PMAP 0 0)
				(larry
					init,
					addToPic:,
					dispose:
				)
				(= palNumber 40)
				(curRoom overlay: 335)
				(patti
					view:				335,
					posn:				213 88,
					setCycle:		FALSE,
				)
				(theMusic2
					number:				sApplause,	;331
					loop:					-1,
					play:
				)
				(= ticks 10)
			)
			(
				(= dreaming TRUE)
				(= seconds 8)
			)
			(
				;patti on stage bowing and crowd throwing roses
				(= dreaming FALSE)
				(curRoom drawPic (curRoom picture?))
				(Graph GFillRect 0 0 192 320 PMAP 0 0)
				(larry
					init,
					addToPic:,
					dispose:
				)
				(= palNumber 56)
				(curRoom overlay: 340)
				(patti
					view:				341,
					setLoop:			0,
					posn:				197 89,
					setCel:			0,
					setCycle:		EndLoop,
				)
				(conf init:, setCycle: EndLoop conf)
				(= ticks 10)
			)
			(
				(= dreaming TRUE)
				(dreamProp2		  ;rose
					init,
					view:				341,
					setLoop:			3,
					setCel:			0,
					posn:				252 67,
				)
				(= ticks 2)
			)
			(
				(dreamProp2		  ;rose
					setCel:			1,
					posn:				252 67,
				)
				(= ticks 2)
			)
			(
				(dreamProp2		  ;rose
					setCel:			2,
					posn:				224 87,
				)
				(= ticks 2)
			)
			(
				(dreamProp2		  ;rose
					setCel:			3,
					posn:				213 102,
					addToPic:,
					dispose
				)
				(= seconds 3)
			)
			(
				;another rose!
				(patti
					setCycle:		BegLoop,
				)
				(dreamProp3		  ;rose
					init,
					view:				341,
					setLoop:			4,
					setCel:			3,
					posn:				163 63,
				)
				(= ticks 2)
			)
			(
				(dreamProp3		  ;rose
					setCel:			4,
					posn:				169 70,
				)
				(= ticks 2)
			)
			(
				(dreamProp3		  ;rose
					setCel:			5,
					posn:				177 78,
				)
				(= ticks 2)
			)
			(
				(dreamProp3		  ;rose
					setCel:			6,
					posn:				184 88,
					addToPic:,
					dispose:
				)
				(= seconds 3)
			)
			(
				;third rose...
				(patti
					setLoop:			1,
					setCel:			0,
					setCycle:		EndLoop,
				)
				(dreamProp4		  ;rose
					init,
					view:				341,
					setLoop:			5,
					setCel:			3,
					posn:				205 69,
				)
				(= ticks 2)
			)
			(
				(dreamProp4		  ;rose
					setCel:			4,
					posn:				215 78,
				)
				(= ticks 2)
			)
			(
				(dreamProp4		  ;rose
					setCel:			5,
					posn:				230 91,
				)
				(= ticks 2)
			)
			(
				(dreamProp4		  ;rose
					setCel:			6,
					posn:				236 103,
				)
				(= seconds 2)
			)
			(
				(patti
					setCycle:		BegLoop,
				)
				(conf dispose:)
				(= dreaming FALSE)
				(theMusic
					fade:		  			0 15 10 STOP_MUSIC,
				)
				(theMusic2
					fade:		  			0 15 10 STOP_MUSIC,
				)
				(= seconds 2)
			)
			(
				;goto John's piano pit...
				(curRoom newRoom: 400)
			)
		)
	)
);sDreamAthens

(instance sWakeUpAthens of Script			;Athens
	(method (changeState newState)
		(switchto (= state newState)
			(
				(SetFFRoom 310)				;airport landing
				(cloud hide)
				(curRoom drawPic (curRoom picture?))
				(Graph GFillRect 0 0 192 320 PMAP 0 0)
				(= palNumber 56)
				(curRoom overlay: 345)
				(theMusic2
					number:				mSexySax,			;345
					loop:					ONCE,
					play:					,
				)
				(dreamProp2			;larry stand in
					init,
					view:				345,
					posn:				168 118,
					setPri:			13,
					setLoop:			0,
					signal			(| fixedLoop fixPriOn ignrAct),
				)
				(dreamProp4
					init,
					view				345,
					setLoop:			1,
					posn: 			168 118,
					cycleSpeed		10,
					setPri:			14,
					setCel:			-1,
					signal			(| fixedLoop fixPriOn ignrAct),
				)
				(patti
					init,
					view				346,
					posn:				209 140,
					cycleSpeed		8,
					setPri:			13,
					setLoop:			0,
					setCel:			-1,
				)
				(dreamProp1
					init,
					view				346,
					setLoop			2,
					posn: 			209 140,
					setPri:			13,
					setCel:			-1,
					signal			(| fixedLoop fixPriOn ignrAct),
				)
				(= ticks 60)
			)
			(
				(TPrint "During our absence,
					your dream of Passionate Patti's Parthenon performance
					progressed.
					The concert, a huge success, is now over;
					the critics have acclaimed her technique, interpretation,
					and mature musicianship;
					her promoter is arranging a one year tour;
					and the two of you take this opportunity to celebrate...."
					WIDELOW
				)
				(dreamProp4
					setLoop:			1,
					cycleSpeed:		10,
					setCycle: 		Forward,
				)
				(= seconds 2)
			)
			(
				(= seconds 2)
			)
			(
				(dreamProp2
					cycleSpeed:		10,
					setCycle: 		EndLoop,
				)
				(dreamProp4
					setLoop:			2,
					cycleSpeed:		10,
					setCycle: 		EndLoop,
				)
				(dreamProp3		  				;patti's arm
					init,
					view				346,
					setLoop			1,
					posn:				208 104,
					setPri:			14,
					setCel:			0,
					signal			(| fixedLoop fixPriOn ignrAct),
				)
				(dreamProp1
					cycleSpeed:		10,
					setPri:			13,
					setCycle: 		EndLoop self,
				)
				(zipperSound play)
			)
			(
				(dreamProp4
					setLoop:			3,
					cycleSpeed:		10,
					setCycle: 		EndLoop
				)
				(patti setCycle: EndLoop)
				(dreamProp1 hide)
				(dreamProp3
					setPri:			14,				;over larry's arm
					cycleSpeed:		10,
					setCycle:		EndLoop self,
				)
			)
			(
				(dreamProp1
					view				346,
					setLoop			3,
					posn:				209 140,
					setCel:			-1,
					setCycle:		Forward,
					setPri:			14,
					show,
				)
				(= cycles 30)
			)

			;------- WAKE UP -----------------

			;Crack nuts...

			(
				(Cls)
				(= dreaming FALSE)
				(curRoom drawPic (curRoom picture?))
				(body 		init:, stopUpd)
				(tray			init:, stopUpd)

				(if magazineHere
					(magazine	init:)
				)

				(patti 		dispose:)
				(dreamProp1 dispose:)
				(dreamProp2 dispose:)
				(dreamProp3 dispose:)
				(dreamProp4	dispose:)

				(theMusic
					number:				sPlaneSnd,
					loop:					-1,
					play:
				)
				(theMusic2
					number:				mMusicEnds,			;346
					loop:					ONCE,
					play:					60,
					fade:					127 15 10 DONT_STOP_MUSIC,
				)
				(arm
					init,
					view:				vLarrySqueezingNutBags,		;	323
					setLoop:			lLeftArm,
					posn: 			122 86,
					setPri:			14,
					ignoreActors:	TRUE,
					cycleSpeed:		10,
					setScript:		sSqeezeNuts,
				)
				(arm2
					init,
					view:				vLarrySqueezingNutBags,		;	323
					setLoop:			lRightArm,
					posn: 			138 78,
					setPri:			5,
					cycleSpeed:		10,
					ignoreActors:	TRUE,
				)
				(larry
					init,
					view:				321,
					loop:				lFallingAsleep,	;1
					setCel:			1,
					posn:				135 72,
					setPri:			4,
					signal			(| fixedLoop fixPriOn ignrAct)
				)
				(= seconds 3)
			)
			(
				(TPrint "You awaken with a start to hear the stewardess say..."
					MY_BIG
				)
				(larry
					view:				325,
					setLoop:			1,
				)
				(TPrint "\"Why, Mr. Laffer! Is there a problem with your nuts?\""
					LOW
					#title		{The Stewardess}
					#dispose:	self
				)
				(arm setScript: 0)		;turn off nut squeezing shit
			)
			(
				(larry
					setCel: 			1,
					cycleSpeed:		10,
					setCycle:		CycleTo 7 FORWARD self,
				)
			)
			(
				(= seconds 7)
			)
			(
				(arm
					view:				vLarryInAirplane,		;321
					setLoop:			lArmCels,				;2
					setCel:			cRightArm,				;0
					posn:				126 79,
					setPri:			5,
				)
				(arm2
					view:				vLarryInAirplane,		;321
					setLoop:			lArmCels,
					setCel:			cLeftArm,
					posn:				173 84,
					setPri:			5,
				)
				(theMusic2 stop)
				(= seconds 2)
			)
			(
				(TPrint "A voice crackles through the intercom speaker
					above your head,
					\"Please tighten your seatbelts and return your tray tables
					to their fully upright and locked position.
					We'll be landing momentarily.\""
					WIDELOW
				)
				(theMusic
					fade:		  			0 15 10 STOP_MUSIC,
				)
				(theMusic2
					fade:		  			0 15 10 STOP_MUSIC,
				)
				(curRoom newRoom 310)	;land plane normal
			)
		)
	)
);sWakeUpAthens

(instance sWakeUpVenice  of Script			;Venice
	(method (changeState newState)
		(switchto (= state newState)
			(
				(SetFFRoom 310)				;airport landing
				(cloud hide)
				(curRoom drawPic (curRoom picture?))
				(Graph GFillRect 0 0 192 320 PMAP 0 0)
				(= palNumber 56)
				(curRoom overlay: 355)
				(dreamProp2							;still in boat
					init,
					view:				356,
					setLoop:			0,
					posn:				187 126,
					cycleSpeed:		10,
					setCel:			LASTCEL,
					setPri:			14,
					setCycle:		BegLoop self,
				)
				(theMusic
					number:			mGondola,
					loop:	 			-1,
					play:				60,
					fade:				127 15 10 DONT_STOP_MUSIC,
				)
				(HandsOff)
			)
			(
				(= ticks 60)
			)
			(
				(TPrint "During our absence,
					your dream of you and Passionate Patti has progressed.
					You snuggle up together,
					drifting dreamily through the moonlit canals of Venice,
					the songs of other gondoliers wafting softly across the
					waters.
					Suddenly your gondola springs a most inopportune leak...."
					MY_BIG
					#dispose: self
				)
			)
			(
				(theMusic2
					number:			 sSpringLeak,
					loop:				 -1,
					play:				 60,
					fade:				 127 15 15 DONT_STOP_MUSIC,
				)
				(= ticks 10)
			)
			(
			 	(dreamProp1 		;leak
					init:,
					view:				355,
					setLoop:			0,
					setCycle:		Forward,
					setPri:			14,
					posn:				205 137,
					signal			(| fixedLoop fixPriOn ignrAct)
				)
				(= ticks 123)
			)
			(
				(theMusic2 fade 0 15 5 STOP_MUSIC self)
			)
			(

				;--------- WAKE UP ------------

				(Cls)						;- added 9/24/91 BKH
				(= dreaming FALSE)
				(curRoom drawPic (curRoom picture?))
				(if magazineHere 
					(magazine init)
				)
	  			(cloud show)
				(dreamProp1 dispose:)
				(dreamProp2 dispose:)

				(theMusic
					number:				sPlaneSnd,
					loop:					-1,
					play:
				)
				(theMusic2
					number:				mMusicEnds,			;346
					loop:					ONCE,
					play:					60,
					fade:					127 15 10 DONT_STOP_MUSIC,
				)
				(body								;body
					init:,
					view:					325,
					loop:					0,
					setCel:				0,
					posn:					165 124,
					setPri:				4,
					stopUpd,
				)
				(arm								;rightArm
					init,
					view:					vLarrySpillingDrink,		;324
					setLoop:				0,
					setCel:				2,
					posn:					147 105,
					setPri:				8,
				)
				(arm2								;leftArm
					init,
					view:					324,
					setLoop:				1,
					setCel:				0,
					posn:					169 82,
					setPri:				6,
				)
				(larry
					setPri:				5,
					startUpd,
				)
				(= ticks 10)
			)
;**				(
;**					(larry
;**						setCel:				0,
;**					)
;**					(= ticks 10)
;**				)
			(
				(arm2
					cycleSpeed:			10,
					setCycle:			EndLoop self,
				)
			)
			(
				(larry
					view:					324,
					setLoop:				0,
					setCel:				0,
					posn:					139 72,
				)
				(= seconds 3)
			)
			(
				(larry
					view:					325,
					loop:					1,
					setCel:				5,
					posn:					134 70,
					cycleSpeed:			17,
					setCycle:			CycleTo 7 FORWARD self,
				)
			)
			(
				(= seconds 3)
			)
			(
				(TPrint "Awakening from your dream,
					you realize you just filled your lap with
					complimentary beverage!"
					MY_BIG
					#dispose: self
				)
			)
			(
				(= ticks 30)
			)
			(
				(TPrint "A voice crackles through the intercom speaker
					above your head,
					\"Please tighten your seatbelts and return your tray tables
					to their fully upright and locked position.
					We'll be landing momentarily.\""
					WIDELOW
				)
				(theMusic
					fade:		  			0 15 10 STOP_MUSIC,
				)
				(theMusic2
					fade:		  			0 15 10 STOP_MUSIC,
				)
				(= seconds 2)
			)
			(
				(curRoom newRoom: 310)		;land plane normal
			)
		)
	)
);sWakeUpVenice


(instance sDreamTaj of Script
	(method (changeState newState &tmp var1 var2)
		(switchto (= state newState)
			(
				;control should already be off
				(if magazineHere
					(magazine dispose)
				)
				(body 		dispose:)
				(arm			dispose:)
				(tray 		dispose:)
				(cloud 		dispose:)
				(arm2 		dispose:)

				(dreamProp1 		;left speaker
					view:				370,
					setLoop:			0,
					setCycle:		RandCycle,
					posn:				72 122,
					cycleSpeed:		8,
					signal			(| fixedLoop fixPriOn ignrAct)
				)
				(dreamProp2			;drummer
					init:,
					view:				370,
					setLoop:			2,
					posn:				155 80,
					cycleSpeed:		3,
					setCycle:		RandCycle,
				)
				(dreamProp3			;right speaker
					init:,
					view:				370,
					setLoop:			1,
					setCycle:		RandCycle,
					cycleSpeed:		8,
					posn:				236 112,
				)
				(dreamProp4			;sax guy
					init:,
					view:				370,
					setLoop:			3,
					setCycle:		RandCycle,
					cycleSpeed:		6,
					posn:				126 88,
				)
				(dreamProp5			;guitarist
					init:,
					view:				370,
					setLoop:			4,
					setCycle:		RandCycle,
					cycleSpeed:		6,
					posn:				193 94,
				)
				(patti
					init:,
					view:				371,
					posn:				166 151,
					setLoop:			0,
					setCycle:		Forward,
					cycleSpeed:		8,
					setScript:		sPlayGuitar,
				)
				(Graph GFillRect 0 0 192 320 PMAP 0 0)
				(= palNumber 56)
				(curRoom overlay: 370)
				(= seconds 3)
			)
			(
				(TPrint "In your dream, you are watching Passionate Patti perform
					a rock concert before the reflecting pool of the Taj Mahal!
					What a great musician!
					What a romantic setting!
					What a gorgeous babe!!"
					#dispose: self
					MY_BIG
				)
			)
			(
				(= ticks 90)
			)
			(
				(theMusic
					fade:		  			0 15 10 STOP_MUSIC,
				)
				(theMusic2
					fade:		  			0 15 10 STOP_MUSIC,
				)
				(= seconds 3)
			)
			(
				(= dreaming FALSE)
				(= cycles 2)
			)
			(
				;K-RAP Radio exterior
				(curRoom newRoom: 800)
			)
		)
	)
);sDreamTaj

(instance sWakeUpTaj of Script						;TajMahal
	(method (changeState newState &tmp var1 var2)
		(switchto (= state newState)
			(
				(SetFFRoom 310)				;airport landing
				(cloud hide)
				(curRoom drawPic (curRoom picture?))
				(Graph GFillRect 0 0 192 320 PMAP 0 0)
				(= palNumber 56)
				(curRoom overlay: 375)

				(dreamProp1 		;arm
					init,
					view:				376,
				  	setLoop:			0,
					setCel:			0,
					posn:				170 137,
					setPri:			14,
					cycleSpeed:		10,
				)
				(larry 				;body
					init,
					view:				376,
					setLoop:			2,
					posn:				170 137,
					cycleSpeed:		10,
					setPri:			13,
					stopUpd,
				)
				(dreamProp2			;snake
					init,
					view:				375,
					setLoop:			0,
					posn:				197 141,
					setPri:			15,
				)
				(patti
				 	init,
					view:				377,
					setLoop:			0,
					posn:				226 135,
					setPri:			14,
					cycleSpeed:		10,
				)
				(= ticks 10)
			)
			(
				(HandsOff)
				(TPrint "During our absence, your dream of Passionate Patti's
					Taj Mahal concert has progressed.
					Late at night, the gig over,
					the amps and drums packed in the road cases,
					it's now time for just the two of you,
					relaxing together on a couple of overstuffed cushions,
					emotionally drained.
					To calm herself,
					and prepare you for the evening ahead,
					Patti plays a little selection on her oboe...."
					MY_BIG
				)
				(theMusic2
					number:			mOboeSnake,		;375
					loop:				-1,
					play:
				)
				(patti
					cycleSpeed:		10,
					setCycle:		Forward,
				)
				(dreamProp2			;snake
					cycleSpeed:		10,
					setCel:			0,
					setCycle:		EndLoop self,
				)
			)
			(
				(dreamProp2								;snake
					setLoop:			1,
					setCycle:		MyForward,
				)
				(= seconds 3)
			)
			(
				(dreamProp1			  					;larry's arm
					setCel:			0,
					setCycle:		EndLoop self,
				)
			)
			(
				(dreamProp1								;larry's arm
					setLoop:			1,
					setCycle:		Forward,
				)
				(= seconds 3)
			)
			(
				(TPrint "You soon realize the snake is not the only thing Patti
					has charmed!"
					MY_BIG
               #dispose
				)
				(theMusic2 fade 0 15 12 TRUE)
				(= seconds 4)
			)

			(
				;---------- WAKE UP --------------

				(Cls)						;- added 9/24/91 BKH
				(= dreaming FALSE)
				(curRoom drawPic (curRoom picture?))
				(body 		init, stopUpd)
				(arm			init)
				(tray			init)
				(if magazineHere
					(magazine init:)
				)

				(dreamProp1 dispose:)
				(dreamProp2 dispose:)
				(patti 		dispose:)

				;wake up, tray flys!
				(theMusic
					number:				sPlaneSnd,
					loop:					-1,
					play:
				)
				(theMusic2
					number:				mMusicEnds,			;346
					loop:					ONCE,
					play:					60,
					fade:					127 15 10 DONT_STOP_MUSIC,
				)
				(cloud		show,)
				(tray
					init,
					cycleSpeed:	10,
					setCycle:	EndLoop:	self,
				)
				(larry
					init,
					view:			vLarryPushingUpTray,		;325
					posn:			136 68,
					setLoop:		lLookingAround,			;1
					setCel:		0,
				)
				(body
					init,
					view:			vLarryPushingUpTray,		;325
					setLoop: 	lSittingUp,					;0
					setCel:		0,
					stopUpd,
				)
				(arm
					init,
					view:			vLarryPushingUpTray,	 	;325
					setLoop:		lRArm,						;2
					setCel:		1,
					posn:			132 76,
					setPri:		6,
				)
				(arm2
					init,
					view:			vLarryPushingUpTray,	 	;325
					setLoop:		lLArm,						;3
					setCel:		1,
					setPri:		6,
					posn:			183 56,
				)
			)
			(
				(larry
					setCel: 		1,
					cycleSpeed:	10,
					setCycle:	CycleTo 5 FORWARD self,
				)
			)
			(
				(larry
					setCel:		6,
					posn:			144 66,
				)
				(body
					setCel:		1,
				)
				(arm
					setCel:		2,
					posn:			129 78,
				)
				(arm2
					setCel:		2,
					posn:			170 60,
				)
				(= ticks 30)
			)
			(
				(arm
					setCel:		3,
					posn:			129 75
				)
				(larry
					setCel:		7,
					posn:			152 61,
				)
				(body
					setCel:		2,
				)
				(= seconds 3)
			)
			(
				(TPrint "A voice crackles through the intercom speaker
					above your head,
					\"Please tighten your seatbelts and return your tray tables
					to their fully upright and locked position.
					We'll be landing momentarily.\""
					WIDELOW
				)
				(TPrint "You hope none of the other passengers notices that
					your tray table is already up!"
					WIDELOW
				)
				(theMusic
					fade:		  			0 15 10 STOP_MUSIC,
				)
				(theMusic2
					fade:		  			0 15 10 STOP_MUSIC,
				)
				(= seconds 3)
			)
			(
				(curRoom newRoom 310) ;land plane normal...
			)
		)
	)
);sWakeUpTaj


(instance sDreamVenice of Script
	(method (changeState newState &tmp var1 var2)
		(switchto (= state newState)
			(
				;control should already be off
				(if magazineHere 
					(magazine dispose)
				)
				(body 		dispose:)
				(arm			dispose:)
				(tray 		dispose:)
				(cloud 		dispose:)
				(arm2 		dispose:)

				(dreamProp1 		;upper Waves
					view:				350,
					setLoop:			0,
					posn:				216 93,
					setPri:			12,
					setCycle:		Forward,
					cycleSpeed:		10,
					signal			(| fixedLoop fixPriOn ignrAct)
				)
				(dreamProp2 					;lower waves
					init,
					view:				350,
					setLoop:			1,
					posn:				209 135,
					setCycle:		Forward,
					cycleSpeed:		10,
					setPri:			12,
					signal			(| fixedLoop fixPriOn ignrAct)
				)
				(larry				;larry small in boat
					init,
					view:				351,
					setLoop:			0,
					setCel:			0,
					posn:				297 77,
					setPri:			13,
					signal			(| fixedLoop fixPriOn ignrAct),
				)
				(Graph GFillRect 0 0 192 320 PMAP 0 0)
				(= palNumber 56)
				(curRoom overlay: 350)
				(= ticks 30)
			)
			(
				(TPrint "Ah, this is the life!
					In your dream, you are the handsome gondolier,
					poling through the canals of Venice with
					your lone, lovely female customer: Passionate Patti!
					Everything is going well until..."
					WIDELOW
				)
				(larry
					cycleSpeed:    8,
					moveSpeed:		12,
					setCycle:		Forward,
					setMotion:		MoveTo 285 83 self,
				)
			)
			(
				(larry
					setMotion:		MoveTo 266 90 self,
				)
			)
			(
				(larry
					setMotion:		MoveTo 255 96 self,
				)
			)
			(
				(larry setCel: 0)
				(= cycles 2)
			)
			(
				(TPrint "...you stick it in too deeply." HIGH)
				(TPrint "(An experience wholly unfamiliar to you!)" LOW)
				(larry
					setLoop:			1,
					setCel:			0,
				)
				(= ticks 30)
			)
			(
				(theMusic2
					number:			sPoleToing,
					loop:				1,
					play:
				)
				(larry
					setCel:			1,
				)
				(dreamProp3		;pole
					init:,
					view:				351,
					setPri:			14,
					setLoop:			2,
					posn:				241 98,
				)
				(= cycles 2)
			)
			(
				(larry
					cycleSpeed:		20,
					setCycle:		EndLoop,
					setMotion:		MoveTo 145 143 self,
				)
			)
			(
				(Cls)
				(dreamProp1 dispose:)
				(dreamProp2 dispose:)
				(patti dispose:)
				(curRoom drawPic (curRoom picture?))
				(Graph GFillRect 0 0 192 320 PMAP 0 0)
				(= palNumber 56)
				(larry
					setMotion:		FALSE,
					view				321,
					setLoop			lFallingAsleep,	;1
					setCel:			1,
					posn:				134 70,
					setPri:			4,
					addToPic:,
					dispose:
				)
				(curRoom overlay: 355)	;355
				(dreamProp3
					init:,
					view:				356,
					setLoop:			0,
					posn:				187 126,
					cycleSpeed:		10,
					setCel:			0,
					setCycle:		EndLoop self,
				)
			)
			(
				(TPrint "Deciding to seize the moment,
					you wriggle down beside your Patti,
					relying on the subtle canal currents to propel you
					through your night of love..."
					MY_BIG
				)
				(= ticks 30)
			)
			(
				(= dreaming FALSE)
				(theMusic
					fade:		  			0 15 10 STOP_MUSIC,
				)
				(theMusic2
					fade:		  			0 15 10 STOP_MUSIC,
				)
				(= seconds 2)
			)
			(
				;Backseat in limo outside Steve's Shill building
				(curRoom newRoom: 600)
			)
		)
	)
);sDreamVenice


(instance theMagazine of Feature
	(properties
		x 					206
		y 					202
		z 					100
		heading 			0
		nsLeft 			196
		nsTop 			92
		nsBottom 		112
		nsRight 			216
 		description 	"the magazine"
		sightAngle 		40
	)
	(method (doVerb)
		(magazine doVerb &rest)
	)
)


(instance sDreamCasa of Script
	(method (changeState newState &tmp var1 var2)
		(switchto (= state newState)
			(
				;control should already be OFF
				(if magazineHere
					(magazine dispose)
				)
				(body 		dispose:)
				(arm			dispose:)
				(tray 		dispose:)
				(cloud 		dispose:)
				(arm2 		dispose:)

				(theMusic2
					number:			mAsTimeGoesBy,			;380
					loop:				FOREVER,
					play:				,
				)
				(dreamProp1 		;propeller
					init:,
					view:				380,
					setLoop:			0,
					posn:				228 47,
					setPri:			14,
					show,
					addToPic:
				)
				(dreamProp2			;propeller
					init:,
					view:				380,
					setLoop:			1,
					posn:				267 49,
					setPri:			14,
					show,
					addToPic:
				)
				(Graph GFillRect 0 0 192 320 PMAP 0 0)
				(= palNumber 56)
				(curRoom overlay: 380)
				(= seconds 4)
			)
			(
				(Say hisTalker "\"It's not hard to see, Patti,
					the problems of two little people
					don't amount to a hill of beans
					in this crazy mixed-up world...\""
					#dispose:
					#caller: self
				)
			)
			(
				(= seconds 3)
			)
			(
				(Say herTalker "\"Larry, in all the scenes,
					in all the games, in all the world...
					you had to walk into mine!\""
					#dispose:
					#caller: self
				)
			)
			(
				(= seconds 3)
			)
			(
				(Say herTalker "\"Remember Larry:
					we'll always have Nontoonyt!\""
					#dispose:
					#caller: self
				)
			)
			(
				(= seconds 3)
			)
			(
				(Say hisTalker "\"Here's looking at you, kid!\""
					#dispose:
					#caller: self
				)
			)
			(
				;**	AL says: fade out the airplane motor noise...
				(theMusic
					fade:		  			0 15 10 STOP_MUSIC,
				)
				;**	AL says: just let music continue into 380...
;**					(theMusic2
;**						fade:		  			0 15 10 STOP_MUSIC,
;**					)
				(= dreaming FALSE)
				(= ticks 10)
			)
			(
				(curRoom newRoom 380)
			)
		)
	)
);sDreamCasa

(instance hisTalker of Talker
	(properties
		view				vLarryBogieTalker			;1381
		loop				1
		x					1						;where to print text
		y 					180
		nsLeft			125 					;where to place bust
		nsTop				83						;**	was 84
		talkWidth		180
		name				"Leisure Suit Bogie"
	)
	(method (init)
		(= mouth hisMouth)
		(super init:)
	)
)
(instance hisMouth of Prop
	(properties
		view			vLarryBogieTalker
		loop			0
		nsLeft		0
		nsTop			0
	)
)

(instance herTalker of Talker
	(properties
		view				vPattiIngridTalker		;1382
		loop				1
		x					140						;where to print text
		y 					180
		nsLeft			169  					;**	was 166
		nsTop				82
		talkWidth		180
		name				"Ingrid Patti"
	)
	(method (init)
		(= mouth herMouth)
		(super init:)
	)
)
(instance herMouth of Prop
	(properties
		view			vPattiIngridTalker		;1382
		loop			0
		nsLeft		0
		nsTop			0
	)
)

(instance theMusic3 of Sound
	(properties
		number	sSqueezeNuts
	)
)
(instance zipperSound of Sound
	(properties
		number	sZipper
	)
)

(instance sPlayGuitar of Script
	(method (changeState newState)
		(switchto (= state newState)
			(
				(dreamProp1
					cycleSpeed: (Random 3 8),
				)
				(dreamProp2
					cycleSpeed: (Random 3 8),
				)
				(dreamProp3
					cycleSpeed: (Random 3 8),
				)
				(dreamProp4
					cycleSpeed: (Random 3 8),
				)
				(dreamProp5
					cycleSpeed: (Random 3 8),
				)
				(patti
					setLoop:		0,
					cycleSpeed: (Random 3 8),
					setCycle:	Forward,
				)
				(= ticks (Random 20 40))
			)
			(
				(patti
					setLoop:		1,
					cycleSpeed: (Random 3 8),
					setCycle:	EndLoop self,
				)
			)
			(
				(patti
					setLoop:		2,
					cycleSpeed: (Random 3 8),
					setCycle:	Forward,
				)
				(= ticks (Random 20 40))
			)
			(
				(patti
					setLoop:		1,
					cycleSpeed: (Random 3 8),
					setCycle:	BegLoop self,
				)
			)
			(
				(self init:)
			)
		)
	)
)



(instance chair of Feature
	(properties
		x           129
		y           295
		z           100
		heading     0
		nsLeft      65
		nsTop       51
		nsBottom    140
		nsRight     194
 		description "the seat"
		sightAngle  40
      onMeCheck   cBLUE
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbLook
				(TPrint "You've hated airplane travel ever since
					\"Leisure Suit Larry 2: Looking for Love
					(in Several Wrong Places).\"")
				(TPrint "(Another fine Al Lowe game.)" LOW)
			)
			(else
				(super doVerb: theVerb invItem &rest)
			)
		)
	)
)

(instance floor of Feature
	(properties
		x           176
		y           245
		z           100
		heading     0
		nsLeft      108
		nsTop       107
		nsBottom    183
		nsRight     245
 		description "the floor"
		sightAngle  40
      onMeCheck   cGREEN
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbLook
				(TPrint "You've hated airplane travel ever since
					\"Leisure Suit Larry 2: Looking for Love
					(in Several Wrong Places).\"")
				(TPrint "(Another fine Al Lowe game.)" LOW)
			)
			(else
				(super doVerb: theVerb invItem &rest)
			)
		)
	)
)

