;**
;**	Logics for Room 295 -- VIP lounge end of game
;**
;**	LSL5:  Passionate Patti Does A Little Undercover Work
;**				Copyright 1991
;**				by Al Lowe
;**
;**	Programmed by Steve Conrad
;**
;**	Last Update:  September 9, 1991
;**



(script#		295)

(public
	rm295		0
)

(local
	cameraFlasherCueCounter
	kidCueCounter
	videoCueCounter
	cameraManCueCounter
	fatReporterCueCounter
)

(procedure
	Flash
)

(define	pVIPloungeFull					290)

(define	vLarryLounge				291)
	(define	lEnterLounge		5)

(define	vVIPLoungeFull				295)
	(define	lHandingPhone		0)
	(define	lFlashbulbs			1)
	(define	lLarryGrabsPhone	2)
	(define	lLarryOnPhone		3)

(define	vMiscReporters				297)
	(define	lGroupShot			0)
	(define	lFlashPics			1)
	(define	lMovieFilming		2)
	(define	lPhotog				3)
	(define	lTakePicRight		4)
	(define	lMaleReporter		5)
	(define	lKid					6)
	(define	lFemaleReporter	7)
	(define	lLightLeft			8)
	(define	lLightRight			9)

(define	vBush							1295)

(define	sCrowd						294)

(instance rm295 of LLRoom
	(properties
		picture		pVIPloungeFull
	)
	(method (init)

		(ego
			init:				,
			normalize:		vLarryLounge,
			setCycle:		0,
			setLoop:       lEnterLounge,
			setCel:			0,
			posn:          152 94,
			cycleSpeed:    10
		)

		(super init:)

		(curRoom
			addObstacle:
			((Polygon new:)
				type: PBarredAccess,
				init:	94 144 110 145 90 163 75 161 69 167 74 174 109 189 0 189 0 0
						319 0 319 189 202 189 232 169 249 165 249 157 211 141 98 140,
				yourself
			)
			((Polygon new:)
				type: PBarredAccess,
				init:	210 152 221 160 218 166 193 167 176 161 179 155 191 154,
				yourself
			)
		)

		(crowd				init:)
		(phoneArm			init:)
		(takePicRight		init:)
		(girlReporter		init:)
		(fatReporter		init:)
		(videoCameraMan 	init:)
		(takePicRight		init:)
		(lightLeft 			init:)
		(lightRight			init:)
		(cameraFlasher 	init:)
		(cameraMan			init:)
		(kid 					init:)

		(addToPics doit:)
		(Load PICTURE 	pBlackPic)
		(theMusic
			number:		sCrowd,
			flags:		TRUE,
			setLoop:		-1,
			play:			0,
			fade:			127 15 12 FALSE
		)

		(SetFFRoom 460)		;White House scene
		(HandsOff)
		(self setScript sJetWay)
	)
)

;********** Scripts ***********

(instance sJetWay of Script

	(method (doit)
		(super doit:)
		(if (and
				(IsObject flash)
				(not (Random 0 5))
			)
			(Flash)
		)
	)

	(method (changeState newState)
		(switchto (= state newState)
			(
				(= ticks 60)
			)
			(
				(ego
					cycleSpeed:		9,
					setCycle:      EndLoop self,
				)
			)
			(
				;No cue in this state so that the last thing that cues above
				;will	cue the next state.
				(TPrint "\"HIP, HIP,\""
					HIGH
					#dispose:
				)
				(ego
					normalize:,
					posn:				151 121,
					setHeading:		180,
				)
				(= ticks 60)
			)

			(
				(TPrint "\"HURRAY!!\""
					LOW
					#dispose:
				)
				(lightLeft setCycle: EndLoop)
				(lightRight setCycle: EndLoop)
				(kid cue:)
				(flash init:)
				(= ticks 30)
			)
			(
				(cameraMan cue:)
				(fatReporter cue:)
				(videoCameraMan cue:)
				(cameraFlasher cue:)
				(= ticks 70)
			)
			(
				(TPrint "What's this?
					The now all-too-expected Chartreuse Carpet Lounge is filled
					with people, all here to celebrate the country's newest
					hero--Larry; Larry Laffer!"
					WIDELOW
					#dispose: self
				)
			)
			(
				(= ticks 60)
			)
			(
				(TPrint "\"It's him!\""
					HIGH
					#dispose:
				)
				(ego
					normalize:		,
					posn:          152 123,
					loop:        	facingDown,
					cycleSpeed:		7,
					moveSpeed:		7,
					setMotion:		MoveTo 152 131 self
				)
			)
			(
				(TPrint "\"It's Larry!\""
					LOW
					#dispose:
				)
				(ego
					setMotion:		MoveTo 152 140 self
				)
			)
			(
				(= ticks 90)
			)
			(
				(TPrint "\"It's Larry Laffer!\""
					HIGH
					#dispose: self
				)
			)
			(
				(= ticks 60)
			)
			(
				(TPrint "\"HUZZAH!!\""
					HIGH
					#dispose: self
				)
			)
			(
				(= ticks 30)
			)
			(
				(TPrint "\"HUZZAH!!\""
					HIGH
					#dispose: self
				)
			)
			(
				(= ticks 30)
			)
			(
				(TPrint "\"HIP, HIP,\""
					LOW
					#dispose: self
				)
			)
			(
				(= ticks 30)
			)
			(
				(TPrint "\"HURRAY!!\""
					LOW
					#dispose: self
				)
			)
			(
				(girlReporter
					setCycle:      EndLoop self
				)
			)
			(
				(= ticks 90)
			)
			(
				(TPrint "\"So tell us, Mr. Laffer,\" asks a reporter,
					\"how does it feel to save the lives of hundreds of people?\""
					HIGH
					#dispose: self
				)
			)
			(
				(= ticks 30)
			)
			(
				(TPrint "Another reporter chimes in,
					\"Where did you learn to fly a commercial airliner?\""
					LOW
					#dispose: self
				)
			)
			(
				(= ticks 30)
			)
			(
				(TPrint "\"What is the secret of your amazing rescue
					technique?\" asks a third."
					HIGH
					#dispose: self
				)
			)
			(
				(= ticks 30)
			)
			(
				(TPrint "\"And will you earn extra Frequent Flyer credits?\""
					LOW
					#dispose: self
				)
			)
			(
				(= ticks 30)
			)
			(
				(TPrint "Before you can answer any questions,
					you hear a telephone ring."
					HIGH
					#dispose: self
				)
			)
			(
				(= seconds 3)
			)
			(
				(TPrint "\"Mr. Laffer! There's a telephone call for you!\""
					LOW
					#dispose: self
				)
			)
			(
				(= ticks 30)
			)
			(
				(TPrint "\"For me?\" you wonder. \"No one knows I'm here.\""
					LOW
					#dispose: self
				)
			)
			(
				(ego
					setMotion:		MoveTo 160 170 self
				)
			)
			(
				(TPrint "\"Here, take the phone,\"
					says the reporter.
					\"It's the President of the United States!\""
					LOW
					#dispose: self
				)
			)
			(
				(= ticks 30)
			)
			(
				(phoneArm
					setCycle:    	EndLoop self
				)
			)
			(
				(ego
					view:          vVIPLoungeFull,
					setLoop:			lLarryGrabsPhone,
					setCel:        0,
					cycleSpeed:		10,
					setCycle:		CycleTo 2 FORWARD	self
				)
			)
			(
				(phoneArm dispose:)
				(ego
					setCycle: EndLoop self
				)
			)
			(
				(ego
					setLoop: 	lLarryOnPhone,
					cycleSpeed:	6
				)
				(self setScript: sTalk)
			)
			(
				(TPrint "Well, Larry, things DID work out for you!
					Who would have thought that you would get a personal
					invitation from the President of the United States to
					attend a big State Dinner--especially one in your honor!"
					BIG
					#dispose: self
				)
			)
			(
				(= ticks 30)
			)
			(
				(TPrint "(You wonder if they'll have live music.)" LOW)
				(curRoom drawPic: pBlackPic IRISIN)
				(theMusic
					fade:	0 15 12 TRUE
				)
				(= seconds 3)
			)
			(
				(TPrint "And so it ends...")
				(TPrint "The following Tuesday, our heroine patiently
					awaits her turn to perform at a White House dinner,
					while unbeknown to her, our hero approaches the doorway.")
				(curRoom newRoom 460) 		;White House closing
			)
		)
	)
)

(instance sTalk of Script
	(method (changeState newState)
		(switchto (= state newState)
			(
				(ego setCycle: ForwardCounter 2)
				(Say ego "\"Er, hello?\""
					LOW
					#dispose: self
				)
			)
			(
				(= ticks 30)
			)
			(
				(ego setCycle: FALSE)
				(Say bushTalker "\"Hello, who is this?\" asks the voice on
					the phone."
					#dispose:
					#caller: self
				)
			)
			(
				(= ticks 30)
			)
			(
				(ego setCycle: ForwardCounter 5)
				(Say ego "\"My name is Larry; Larry Laffer,\" you reply."
					LOW
					#dispose: self
				)
			)
			(
				(= ticks 30)
			)
			(
				(ego setCycle: FALSE)
				(Say bushTalker "\"Well, Mr. Lafner, this is the President of the
					United States!\""
					#dispose:
					#caller: self
				)
			)
			(
				(= ticks 30)
			)
			(
				(Say bushTalker "\"I just wanted to telephone to express the
					appreciation of this great nation to you,
					Larry Lasner, for the heroic deed you just accomplished!\""
					#dispose:
					#caller: self
				)
			)
			(
				(= ticks 30)
			)
			(
				(Say bushTalker "\"(And, of course, take this opportunity to up
					my approval rating another few points.
					You know, it never hurts to be seen worldwide on CNN
					under these circumstances!)\""
					#dispose:
					#caller: self
				)
			)
			(
				(= ticks 30)
			)
			(
				(Say bushTalker "\"Eh, um, anyway, what I mean is,\"
					he says, getting back on track, \"You probably didn't know,
					but the Vice-President's Mommy was on that airplane you
					just saved.\""
					#dispose:
					#caller: self
				)
			)
			(
				(= ticks 30)
			)
			(
				(Say bushTalker "\"(By the way, he's still gaining the approval of the
					great American people; yes, gaining every month,
					little by little, getting better and better...)\""
					#dispose:
					#caller: self
				)
			)
			(
				(= ticks 30)
			)
			(
				(Say bushTalker "\"Ah, in any case, he's so grateful that he wants me
					to host a big dinner in your honor here at the White House.
					He would have called you himself, but it's long distance.\""
					#dispose:
					#caller: self
				)
			)
			(
				(= ticks 30)
			)
			(
				(Say bushTalker "\"So, Lafter, do you think you could drop by the
					White House, oh, say, Tuesday next week for one of those
					typical Big National Hero Of The Week Dinners?\""
					#dispose:
 					#caller: self
				)
			)
			(
				(= ticks 30)
			)
			(
				(ego setCycle: ForwardCounter 6)
				(Say ego "You are astonished. \"But of course, sir,\"
					you reply, \"I'll be there!\""
					LOW
					#dispose: self
				)
			)
			(
				(= ticks 30)
			)
			(
				(ego setCycle: FALSE)
				(TPrint "\"Do I have to bring a date?\" you wonder."
					LOW
					#dispose: self
				)
			)
			(
				(= ticks 30)
			)
			(
				(ego setCycle: ForwardCounter 6)
				(Say ego "But to the President you say,
					\"I really look forward to meeting you and the First Lady!\""
					LOW
					#dispose: self
				)
			)
			(
				(= ticks 30)
			)
			(
				(ego setCycle: FALSE)
				(Say bushTalker "\"Oh, sorry, Lasser,\" he says,
					\"I'll be too busy fish, er, uh, MEETing with some
					important ambassadors or something, to attend.
					But I promise to send the Vice-President instead.\""
					#dispose:
					#caller: self
				)
			)
			(
				(= ticks 30)
			)
			(
				(Say bushTalker "\"Nice talking to you, Licker,\" he concludes.
					\"Good job!\""
					#dispose:
					#caller: self
				)
			)
			(
				(= ticks 30)
			)
			(
				(Say bushTalker "\"And, good bye.\""
 					#dispose:
					#caller: self
				)
			)
			(
				(= ticks 30)
			)
			(
				(TPrint "\"(Click.)\""
					LOW
					#dispose: self
				)
			)
			(
				(= ticks 30)
			)
			(
				(ego setCycle: ForwardCounter 5)
				(Say ego "\"Good bye to you, Mr. Pres...
					oh, I guess you've hung up.\""
					LOW
					#dispose: self
				)
			)
			(
				(= ticks 30)
			)
			(
				(ego setCycle: FALSE)
				(client cue:)
			)
		)
	)
)

;*********** Objects *************

(instance videoCameraMan of Prop
	(properties
		view			   vMiscReporters
		loop			   lMovieFilming
		cel				8
		x				   116
		y				   144
		cycleSpeed		13
		priority       13
      signal		   (| fixPriOn ignrAct)
	)
	(method (cue)
		(switch (++ videoCueCounter)
			(1
				(self
					setCycle: 	BegLoop self
				)
			)
			(2
				(WaitSeconds 5)
			)
			(3
				(self
					setCycle: 	EndLoop self
				)
			)
			(4
				(= videoCueCounter 0)
				(WaitSeconds (Random 5 7))
			)
		)
	)
)

(instance cameraFlasher of Prop
	(properties
		view			   vMiscReporters
		loop			   lFlashPics
		x				   184
		y				   129
		cycleSpeed		6
		priority       13
      signal		   (| fixPriOn ignrAct)
	)
	(method (cue)
		(switch (++ cameraFlasherCueCounter)
			(1
				(self
					setCycle: 	EndLoop self
				)
			)
			(2
				(self	setCel: 0)
				(self cue:)
			)
			(3
				(= cameraFlasherCueCounter 0)
				(WaitSeconds (Random 3 5))
			)
		)
	)
)

(instance cameraMan of Prop
	(properties
		view			   vMiscReporters
		loop			   lPhotog
		x				   117
		y				   135
		cycleSpeed		10
		priority       13
      signal		   (| fixPriOn ignrAct)
	)
	(method (cue)
		(switch (++ cameraManCueCounter)
			(1
				(self
					setCycle: 	EndLoop self
				)
			)
			(2
				(WaitSeconds 2)
			)
			(3
				(self
					setCycle: 	BegLoop self
				)
			)
			(4
				(= cameraManCueCounter 0)
				(WaitSeconds (Random 2 3))
			)
		)
	)
)

(instance takePicRight of PicView
	(properties
		view			vMiscReporters
		loop			lTakePicRight
		x				203
		y				135
		priority		13
		signal		(| stopUpdOn ignrAct fixPriOn)
	)
)

(instance fatReporter of Prop
	(properties
		view			   vMiscReporters
		loop			   lMaleReporter
		x				   197
		y				   137
		cycleSpeed		15
		priority       13
      signal		   (| fixPriOn ignrAct)
	)
	(method (cue)
		(switch (++ fatReporterCueCounter)
			(1
				(self
					setCycle: 	EndLoop self
				)
			)
			(2
				(self
					setCycle: 	BegLoop self
				)
			)
			(3
				(= fatReporterCueCounter 0)
				(WaitSeconds (Random 2 4))
			)
		)
	)
)

(instance kid of Prop
	(properties
		view			   vMiscReporters
		loop			   lKid
		x				   179;125
		y				   133;122
		cycleSpeed		10
		priority       13
      signal		   (| fixPriOn ignrAct)
	)
	(method (cue)
		(switch (++ kidCueCounter)
			(1
				(self
					setCycle: 	EndLoop self
				)
			)
			(2
				(self	setCel: 0)
				(self cue:)
			)
			(3
				(self
					setCycle: 	EndLoop self
				)
			)
			(4
				(self	setCel: 0)
				(self cue:)
			)
			(5
				(= kidCueCounter 0)
				(WaitSeconds (Random 2 4))
			)
		)
	)
)

(instance girlReporter of Prop
	(properties
		view			   vMiscReporters
		loop			   lFemaleReporter
		x				   200
		y				   163
		cycleSpeed		10
		priority       14
      signal		   (| stopUpdOn fixPriOn ignrAct)
	)
)

(instance phoneArm of Prop
	(properties
		view			   vVIPLoungeFull
		loop			   lHandingPhone
		x				   127
		y				   154
		cycleSpeed		10
		priority       12
      signal		   (| stopUpdOn fixPriOn ignrAct)
	)
)

(instance flash of Prop
	(properties
		view			   vVIPLoungeFull
		loop			   lFlashbulbs
		cycleSpeed		6
		priority       14
      signal		   (| fixPriOn ignrAct)
	)
)

(instance crowd of PicView
	(properties
		view			   vMiscReporters
		loop			   lGroupShot
		x				   163
		y				   159
      signal		   (| stopUpdOn ignrAct)
	)
)

(instance lightLeft of Prop
	(properties
		view			vMiscReporters
		loop			lLightLeft
		x				91
		y				77
		cycleSpeed	10
		priority		14
		signal		fixPriOn
	)
)

(instance lightRight of Prop
	(properties
		view			vMiscReporters
		loop			lLightRight
		x				207
		y				76
		cycleSpeed	10
		priority		14
		signal		fixPriOn
	)
)

;*********** Talker ***********

(instance bushTalker of Talker
	(properties
		name			"Mr. President"
		view			vBush
		loop			3
		nsLeft		35
		nsTop			15
		viewInPrint	TRUE
	)
	(method (init)
		(= bust 	bushBust)
		(= eyes 	bushEyes)
		(= mouth bushMouth)
		(super init: &rest)
	)
)

(instance bushBust of Prop
	(properties
		view		vBush
		loop		1
		nsLeft	0
		nsTop		0
	)
)

(instance bushEyes of Prop
	(properties
		view			vBush
		loop			2
		nsLeft		30
		nsTop			28
		cycleSpeed  70
	)
)

(instance bushMouth of Prop
	(properties
		view			vBush
		loop			0
		nsLeft		24
		nsTop			31
		cycleSpeed 	5
	)
)

;*********** Procedure ************

(procedure (Flash)
	(switch (Random 0 1)
		(0
			(flash posn: (Random 73 112) (Random 110 151))
		)
		(1
			(flash posn: (Random 192 248) (Random 95 137))
		)
	)
	(flash
		setCycle: EndLoop
	)
)



