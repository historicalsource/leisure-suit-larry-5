;**
;**	Logics for Room 920 -- Chi Chi's & Larry's Gym-Nasty
;**
;**	LSL5:  Passionate Patti Does A Little Undercover Work
;**				Copyright 1991
;**				by Al Lowe
;**
;**	Brute-force programming by C. Escobar
;**	Tuned by Al & Cheri
;**
;**	Last Update:  Saturday, September 7, 1991 
;**

(script#		920)

(public
	rm920		0
)


(define	sfxHitWall 					913)
(define	sfxHitBalls					914)
(define	sfxBoing2					448)
(define	sfxSpinBar					915)

(define	mSalsaComic 				920)

(define	pChiChi'sGym				920)
(define	vChiChi'sGym				920)
	(define lBlinds 		   	0)
	(define lWallCrack			1)
	(define lEquipment 	   	2)
	  (define cTrampoline  0)
	  (define cPatti'sBar  1)
	  (define cLarry'sBar  2)

(define	vLarryGymnastics			921)
	(define lLarryWalkBeam	0)
	(define lFallOnNuts		1)
	(define lSpinOnBar		2)
	(define lMountBeam 		3)
(define	vChiChiGymnastics			922)
	(define lWalkBeam			0)
	(define lHandStand		1)
	(define lChiChiSwinging	2)
	(define lJumpOffBeam		3)
	(define lTurnAround		4)

(define	vLarryOnRope				923)
	(define lLarryOnRope 	0)
	(define lSplat				1)
	(define lRope				2)

(define	vLarryRunning				924)
	(define lRun	0)
(define	vChiChiStradlingBars		925)
	(define lFingerHerself 0)
	(define lWatchLarry 	  1)

;shakeScreen
(define UP&DOWN	 1)
(define RIGHT&LEFT 2)

(local
	runJumpPts =
	 [
		0 11 91 86
		0 12 92 89
		0 13 91 91
		0 14 111 98
		0 14 153 84
		0 14 173 80
		PATHEND
	]
)

(instance rm920 of LLRoom
	(properties
		picture		pChiChi'sGym
	)

	(method (init)
		(Load PICTURE pBlackPic)		;999
		(super init:)
		(ego
			init:,
			normalize:,
			view:				vLarryOnRope,
			posn:				137 117,
			setCycle:		0,
			setLoop:	 		lLarryOnRope,		;0
			setCel:			0,
			setPri:			8,
			setStep:			1 1,
			illegalBits: 	0,
			ignoreActors:	TRUE,
			hide:,
		)
		(crack init:, hide:)
		(bars  init:, hide:)
		(rope  init:, hide:)
		(chiChi
			init,
			setCycle:		Forward,
		)
		(blinds
			init:,
			setCel:			LASTCEL,
		)
		(theMusic
			number:			mSalsaComic,				;920
			loop:				FOREVER,						;-1
			play:
		)

		(self setScript sCartoon)
	);init

	(method (newRoom n)
		(theMusic	stop)
		(theMusic2	stop)
		(super newRoom n)
	)
)



(instance sCartoon of Script
	(method (changeState newState)
		(switchto (= state newState)
			(
				(HandsOff)
				(SetFFRoom 900)
				(SetupExit FALSE)
				(= ticks 90)
			)
			(
				(Say ego "\"Chi Chi!\" you cry. \"What are you doing?\""
				)
				(= ticks 30)
			)
			(
				(TPrint "\"Come on, my little Tito Puente,\" she cries,
					\"I am ready and waiting!\""
					#title:		{Chi Chi}
				)
				(= ticks 90)
			)
			(
				(blinds
					setCycle: 		BegLoop self
				)
			)
			(
				(blinds 				stopUpd:)
				(= ticks 90)
			)


			;**	cartoon #1 -- the rope trick

			(
				(if (!= (chiChi cel?) 1)
					(-- state)					;**	try again, soon
				else
					(ego show:)
				)
				(= register (ego cycleSpeed?))
				(= cycles 2)
			)
			(
				(ego
					cycleSpeed:		8,
					setCycle:		EndLoop self
				)
			)

			(
				(theMusic2
					number:			sfxHitWall,
					loop:				ONCE,
					play:
				)

				(ego
					cycleSpeed:		register,
					setLoop:			lSplat,			;1
					setCel:			0,
					posn:				85 82,
				)
				(rope
					show,
					setCycle:		EndLoop,
				)
				(Say ego "\"UGH!\""
					HIGH
					#dispose:
				)
				(ShakeScreen 5 UP&DOWN)				;**	shake 5 times, down & up
				(= ticks 90)
			)
			(
				(ego
;					setStep:			3 8,
					setStep:			3 6,
					setMotion:		MoveTo 85 124 self,
				)
			)
			(
				(= seconds 3)
			)
			(
				(ego
					setCycle:		EndLoop self,
				)
			)
			(
				(= seconds 4)
			)
			(
				(Cls)
				(blinds
					setCycle: 		EndLoop self
				)
			)


			;**	cartoon #2 -- Chi Chi spreads it on the parallel bars

			(
				(Load VIEW vLarryRunning)
				(Load VIEW vChiChiStradlingBars)
				(blinds 				stopUpd)
				(= seconds 4)
			)

			(
				(ego
					hide,
					setMotion:		FALSE,
					view: 			vLarryRunning,				;924
					cycleSpeed		12,
					setStep:			3 3,
					setLoop:			lRun,							;0
					setCel:			0,
					posn:				77 93,
				)
				(chiChi
					view:				vChiChiStradlingBars, 	;925
					setLoop:			lFingerHerself,			;0
					cycleSpeed		18,
					posn:				176 166,
					setCycle:		Forward,
					setPri:			13,
				)
				(bars
					show,
					view:				vChiChi'sGym,				;920
					setLoop:			lEquipment,					;2
					setCel:			cPatti'sBar, 				;1
					posn:				180 159,
					stopUpd:,
				)
				(rope
					view:				vChiChi'sGym,
					setLoop:			lEquipment,					;2
					setCel:			cTrampoline,				;0
					posn:				104 136,
					stopUpd:,
				)
				(blinds
					setCycle:		BegLoop self,
				)
			)
			(
				(blinds 				stopUpd:)
				(= seconds 4)
			)
			(
				(TPrint "\"Oh, Larry!\" cries Chi Chi,
					\"I bet you've never done it THIS way!\"" BIG
					#title:		{Chi Chi}
				)
				(chiChi
					setLoop:			lWatchLarry,		;1
					setCel:			0,
					cycleSpeed		18,
					setCycle:		EndLoop self,
				)
			)
			(
				(chiChi
					setCel:			0,
					setCycle:		EndLoop self,
				)
			)
			(
				(= ticks 120)
			)
			(
				(ego
					show:		,
					setCycle:		CycleTo 4 FORWARD self
				)
			)
			(
				(TPrint "\"Larry! Watch out for my trampoline!\"" HIGH
					#title:		{Chi Chi}
				)
				(ego
					setCycle:		CycleTo 10 FORWARD self
				)
			)
			(
				(theMusic2
					number:			sfxBoing2,
					loop:				ONCE,
					play:
				)
				(ego
					setCel:			11,
					cycleSpeed:		12,
					setCycle:		MoveCycle @runJumpPts self
				)
			)

			(
				(theMusic2
					number:			sfxHitWall,
					loop:				ONCE,
					play:
				)
				(Say ego "\"ARGGHH!\""
					#title: {You}
					HIGH
					#dispose:
				)
				(ego hide)
				(crack
					show,
					view:				vChiChi'sGym,		;920
					setLoop:			lWallCrack,			;1
					setCel:			0,
					cycleSpeed		6,
					posn:				244 59,
					setPri:			15,
					setCycle:		EndLoop self,
				)
			)
			(
				(ShakeScreen 7 RIGHT&LEFT)				;**	shake 5 times, right/left
				(crack addToPic:)
				(= seconds 4)
			)
			(
				(Cls)
				(blinds
					setCycle: 		EndLoop self,
				)
			)

			;**	Cartoon #3 -- the (lack of) balance beam

			(
				(Load VIEW vLarryGymnastics)
				(Load VIEW vChiChiGymnastics)
				(rope dispose:)
				(bars
					setPri:			4,
					posn:				131 161,
					setCel:			cLarry'sBar,		;2
					setLoop:			lEquipment,			;2
					stopUpd:
				)
				(ego
					show,
					view:				vLarryGymnastics,		;921
					posn:				85 124,
					setLoop:			lMountBeam,				;3
					setCel:			0,
				)
				(chiChi
					view:				vChiChiGymnastics,	;922
					posn:				102 123,
					cycleSpeed:		9,
					moveSpeed:		9,
					setLoop:			lWalkBeam,				;0
					setCel:			0,
				)
				(blinds 				stopUpd:)
				(= seconds 4)
			)
			(
				(blinds setCycle: BegLoop self,)
			)
			(
				(blinds 				stopUpd:)
				(= ticks 30)
			)
			(
				(chiChi
					setStep:			2 2,
					setCycle:		Walk,
					setMotion:		MoveTo 150 (chiChi y?) self,	;**	was 140x
				)
			)
			(
				(chiChi											;v922
					setLoop:			lHandStand,				;1
					setCel:			0,
					cycleSpeed		12,
					setCycle:		CycleTo 7 FORWARD self,
				)
			)
			(
				(= ticks 30)					;**	pause with legs together
			)
			(
				(chiChi
					setCycle:		EndLoop self,
				)
			)
			(
				(= ticks 90)
			)
			(
				(TPrint "\"Now it's your turn, Larry!\"
					says Chi Chi, the blood rushing to her brain.
					\"Come and get it, Big Fella!\""
					#title:		{Chi Chi}
					BIG
				)
				(= ticks 90)
			)
			(
				;Larry climbs on beam, current loop still 3
				(ego
					cycleSpeed:		11,
					moveSpeed:		11,
					setCycle:		EndLoop self,
				)
			)

			(
				(= ticks 30)
			)
			(										;**	Larry walks to Chi Chi
				(ego
					setCycle:		Walk,
					setLoop:			lLarryWalkBeam,			;0
					setStep:			1 1,
					setMotion:		MoveTo 116 (ego y?) self,	;**	was 137
				)
			)
			(
				(ego
					setLoop:			lFallOnNuts,				;1
					setCel:			0,
					setCycle:		CycleTo 3 FORWARD self,
				)
			)
			(
				(theMusic2
					number:			sfxHitBalls,
					loop:				ONCE,
					play:
				)

				(Say ego "\"AAWWWH!\""
					#title: {You}
					HIGH
					#dispose:
				)
				(ShakeScreen 5 RIGHT&LEFT)				;**	shake 5 times, right/left
				(= ticks 90)
			)
			(
				(ego
					setCycle:		EndLoop self,
				)
			)
			(
				(= ticks 90)
			)
			(
				(Cls)
				(theMusic2
					number:			sfxSpinBar,
					loop:				ONCE,
					play:
				)

				(ego
					setLoop:			lSpinOnBar,				;2
					setCel:			0,
					cycleSpeed:		12,
					setCycle:		EndLoop self,
				)
			)
			(
				(= seconds 4)
			)
			(
				(blinds
					setCycle: 		EndLoop self,
				)
			)
			(
				(= ticks 180)
			)
			(
				(ego 			hide:)
				(chiChi		dispose:)
				(bars			dispose:)
				(blinds		dispose:)
				(DrawPic 	pBlackPic IRISIN)
				(theMusic	fade)
				(theMusic2	fade)
				(= seconds 3)
			)
			(
				(Points ptsHadSexCC pfHadSexCC)
				(TPrint "What a humiliating experience this has been!
					Finally, your lack of athletic ability has emerged
					to haunt you!")
				(if camcorderOn
					(RecordTape tapeRecordedCC)
					(Points ptsRecordedCC pfRecordedCC)
					(TPrint "Well, at least you recorded Chi Chi on your videotape.
						The boys in the office will be proud of you!")
					(TPrint "(But so much for those fantasies you've
						had about you and gymnasts!)" LOW
					)
				else
					(TPrint "What a shame you didn't get that scene on tape!")
				)
				(curRoom newRoom 900)
			)
		)
	)
);sCartoon


(instance chiChi of Actor
   (properties
      view         	vChiChiGymnastics
      x            	128
      y            	108
		loop				lChiChiSwinging		;2
		priority			7
		cycleSpeed		8
      description  	"Chi Chi Lambada"
      lookStr      	"Chi Chi loves doing sexually-explicit gymnastics
							routines."
      sightAngle   	90
		signal			(| fixedLoop fixPriOn ignrAct)
   )
)

(instance blinds of Prop
   (properties
      view         	vChiChi'sGym
      x            	128
      y            	158
		loop				lBlinds		;0
		cycleSpeed		10
		description  	"the venetian blinds"
      lookStr      	"If it weren't for these venetian blinds,
							we'd all have a perfect view!"
      sightAngle   	90
		priority			14
		signal			(| fixedLoop fixPriOn ignrAct)
   )
)
(instance rope of Prop
   (properties
      view         	vLarryOnRope
      x            	136
      y            	44
		loop				lRope		;2
		cycleSpeed		8
      description  	"the rope"
      lookStr      	"Hang on to this!"
      sightAngle   	90
		signal			(| fixedLoop fixPriOn ignrAct)
   )
)

(instance bars of View
	(properties
		view:				vChiChi'sGym
		signal			(| fixedLoop fixPriOn ignrAct)
	)
)

(instance crack of Prop
	(properties
		view:				vChiChi'sGym
		signal			(| fixedLoop fixPriOn ignrAct)
	)
)

