;**
;**	Logics for Room 205 -- Limousine Interior Dream Sequences & Meanwhiles
;**
;**	LSL5:  Passionate Patti Does A Little Undercover Work
;**				Copyright 1991
;**				by Al Lowe
;**
;**	Started 	by C. Escobar
;**	Finished by BKH
;**
;**	Last Update:  September 9, 1991
;**


(script#		205)

(public
	rm205		0
)

(define 	sfxCarSnd				204)

(define	mMrBiggTheme		 	210)
(define	mScruemallTheme 		215)
(define	mJimBaker			 	220)

(define	lTalkerMouth	0)
(define	lTalkerBust		1)
(define	lTalkerEyes		2)
(define	lTalkerFrame	3)

(define	pMobBossOffice					120)
(define	vMobBoss						124)
(define	vMobFlunky1Talker			1122)
(define	vMobFlunky2Talker			1123)

(define	pMeanwhileScruemallOnPhone	215)
(define	vScruemallTalker			1215)

(define	pMeanwhileCANEgetsAgrant	220)
(define	vCANEPresidentTalker		1220)
(define	vMobFlunky3Talker			1221)

(define	pMeanwhileCANEcongress		225)
(define	vCaneFlunkee1withDough	225)
(define	vSmilingCongressman		227)
(define	vCANEFlunkyTalker			1225)


(enum 1				 	;**	enums for meanWhiles
	MobWantsCane
	ScruemallGetsACall
	CaneGetsBigGrant
	CaneWorksCongress
)


(local
	textUp		; ID of script with display text on screen
)


(instance rm205 of LLRoom
	(method (init &tmp num)
		(super init:)
		(self setScript sCartoon)
	);init
);rm205



;ษอออออออออป
;บ         บ
;บ Scripts บ
;บ         บ
;ศอออออออออผ

(instance sCartoon of Script
	(method (init)
		(keyDownHandler addToFront: self)
		(mouseDownHandler addToFront: self)
		(SetFFRoom SAMEROOM self)
		(theIconBar disable: computerIcon)
		(super init: &rest)
	)
	(method (dispose)
		(keyDownHandler delete: self)
		(mouseDownHandler delete: self)
		(theIconBar enable: computerIcon)
		(super dispose:)
	)
	(method (handleEvent event)
		(if (OneOf (event type?) mouseDown keyDown)
			(if textUp
				(textUp cue:)
			)
		else
			(super handleEvent: event)
		)
	)
	(method (changeState newState)
		(switchto (= state newState)
			(
				(HandsOff)
				(DrawPic pBlackPic SCROLLLEFT)
				(theGame setCursor: theCursor TRUE 300 180)
				(switch (++ meanWhiles)
					(MobWantsCane
						(curRoom
							picture: pMobBossOffice,
							curPic: 	pMobBossOffice
						)
						(theMusic2
							number:	mMrBiggTheme,
							setLoop:	FOREVER,
							play:		127
						)
						(self setScript sMobWantsCane self)
					)
					(ScruemallGetsACall
						(curRoom
							picture: pMeanwhileScruemallOnPhone,
							curPic: 	pMeanwhileScruemallOnPhone
						)
						(theMusic2
							number:	mScruemallTheme,
							setLoop:	FOREVER,
							play:		127
						)
						(self setScript sScruemallGetsCall self)
					)
					(CaneGetsBigGrant
						(curRoom
							picture: pMeanwhileCANEgetsAgrant,
							curPic: 	pMeanwhileCANEgetsAgrant
						)
						(theMusic2
							number:	mScruemallTheme,
							setLoop:	FOREVER,
							play:		127
						)
						(self setScript sCaneGetsBigGrant self)
					)
					(CaneWorksCongress
						(curRoom
							picture: pMeanwhileCANEcongress,
							curPic:	pMeanwhileCANEcongress
						)
						(theMusic2
							number: 	mScruemallTheme,
							setLoop:	FOREVER,
							play:		127
						)
						(self setScript sCaneWorksCongress self)
					)
				)
			)
			(
				(if script
					(script
						caller:	0,
						dispose:
					)
				)
				(Cls)
				(= textUp self)
				(DrawPic pBlackPic SCROLLRIGHT)
				(CenterDisplay 2 colWhite
					"Meanwhile,\n
					 back in your limo..."
				)
				(theMusic2 fade:	0 15 5 STOP_MUSIC)
				(theMusic
					number:			sfxCarSnd, 		;204
					loop:				FOREVER,			;-1
					play:				0,
					fade:				127 15 5 DONT_STOP_MUSIC,
				)
				(= seconds 3)
			)
			(
				(curRoom newRoom 200)
			)
		)
	)
);sCartoon

(instance sMobWantsCane of Script
	(method (changeState newState)
		(switchto (= state newState)
			(
				(= textUp self)
				(CenterDisplay 4 colWhite
				  "Meanwhile,\n
					another East Coast\n
					\1cBoard Meeting\"\n
					takes place..."
				)
				(= seconds 5)
			)
			(
				(= textUp FALSE)
				(DrawPic pMobBossOffice SCROLLLEFT)
				(leftEyebrow init:)
				(rightEyebrow init:)
				(cigar init:)
				(hand init:)
				(= ticks 30)
			)
			(
				(cigar setCycle: RandCycle)
				(leftEyebrow setCycle: RandCycle)
				(rightEyebrow setCycle: RandCycle)
				(TPrint "\"So, tell me, boys,\" says Mr. Bigg,
					\"is everything proceeding according to our plan?\""
					#title: {Mr. Bigg}
					#dispose: self
					LOW
				)
			)
			(
				(cigar setCycle: 0)
				(hand setCycle: Forward)
				(leftEyebrow setCycle: BegLoop)
				(rightEyebrow setCycle: BegLoop)
				(Say mobFlunky1Talker "\"Yes sir, Mr. Bigg!
					Our friends in Hollywood have the industry in an uproar.
					Their `America's Sexiest Home Videos' show is the talk of
					show biz!\""
					#dispose:
					#caller: self
				)
			)
			(
				(= ticks 45)
			)
			(
				(Say mobFlunky1Talker "\"There's every indication it will be the
					smash hit of the fall season.\""
					#dispose:
					#caller:	self
				)
			)
			(
				(= ticks 45)
			)
			(
				(Say mobFlunky2Talker "\"And we've found the perfect way
					to attack our little straw dog:
					I've discovered a new, hyper-active,
					political action committee called
					`Conservatives Against Nearly Everything.'\""
					#dispose:
					#caller:	self
				)
			)
			(
				(= ticks 45)
			)
			(
				(Say mobFlunky2Talker "\"I'm sure they would be willing to become
					the focus of a  national campaign to clean up the airwaves.\""
					#dispose:
					#caller:	self
				)
			)
			(
				(= ticks 45)
			)
			(
				(Say mobFlunky2Talker "\"I'd like to propose to you, Boss,
					that we make a `little investment' in the health and
					well-being of C.A.N.E.
					You know, a `little something' to show how much we support
					their cause.'\""
					#dispose:
					#caller:	self
				)
			)
			(
				(TPrint "The room is filled with suppressed laughter.")
				(= ticks 45)
			)
			(
				(leftEyebrow setCycle: RandCycle)
				(rightEyebrow setCycle: RandCycle)
				(hand setCycle: EndLoop)
				(cigar setCycle: RandCycle)
				(TPrint "\"Well, boys, I think you know I'm always interested in
					`raising C.A.N.E.!'\""
					#title: {Mr. Bigg}
					#dispose: self
					LOW
				)
			)
			(
				(leftEyebrow setCycle: BegLoop)
				(rightEyebrow setCycle: BegLoop)
				(hand setCycle: BegLoop)
				(TPrint "The laughter becomes unsuppressed!")
				(= ticks 30)
			)
			(
				(cigar setCycle: Forward)
				(TPrint "\"This bunch sounds like the perfect group
					to be the vanguard of our little protest against
					the filth of the airwaves!
					Why don't you make an anonymous contribution to our
					simple little friends at C.A.N.E., eh, boys?\""
					#title: {Mr. Bigg}
					#dispose: self
					LOW
				)
			)
			(
				(self dispose:)
			)
		)
	)
);sMobWantsCane

(instance sScruemallGetsCall of Script
	(method (changeState newState)
		(switchto (= state newState)
			(
				(= textUp self)
				(CenterDisplay 3 colWhite
				  "Meanwhile,\n
					your boss receives an\n
					angry telephone call..."
				)
				(= seconds 5)
			)
			(
				(= textUp FALSE)
				(DrawPic pMeanwhileScruemallOnPhone SCROLLLEFT)
				(= cycles 2)
			)
			(
				(TPrint "Silas Scruemall listens intently and deferentially
					to the powerful voice on the other end of the line."
					WIDELOW
				)
				(= ticks 45)
			)
			(
				(Say silasTalker "\"Let me assure you, Julius:
					everything IS proceeding nicely.
					No, sir.
					I will NOT let you down.\""
					#dispose:
					#caller: self
				)
			)
			(
				(= ticks 45)
			)
			(
				(Say silasTalker "\"Yes, sir.
					That's also being taken care of.
					No, it's handled.
					I have a man on it right now.
					All right; whatever you say, Julius!\""
					#dispose:
					#caller: self
				)
			)
			(
				(= ticks 30)
			)
			(
				(self dispose:)
			)
		)
	)
);sScruemallGetsCall

(instance sCaneGetsBigGrant of Script
	(method (changeState newState)
		(switchto (= state newState)
			(
				(= textUp self)
				(CenterDisplay 4 colWhite
				  "Meanwhile,\n
					a conservative political action\n
					committee is pleased to receive\n
					a sizable contribution..."
				)
				(= seconds 5)
			)
			(
				(= textUp FALSE)
				(DrawPic pMeanwhileCANEgetsAgrant SCROLLLEFT)
				(= cycles 2)
			)
			(
				(Say presTalker "\"Well, thank you, gentlemen.
					I promise your CONSIDERABLE contribution will not be wasted!\""
					#dispose:
					#caller: self
				)
			)
			(
				(= ticks 45)
			)
			(
				(Say presTalker "\"I daresay this one donation will enable us to
					now succeed where we've been unable to before.
					You just don't know how grateful we are!\""
					#dispose:
					#caller: self
				)
			)
			(
				(= ticks 45)
			)
			(
				(Say flunkyTalker "\"You're welcome.
					But, just remember:
					our `benefactor' insists that his contribution remain
					completely anonymous.\""
					#dispose:
					#caller: self
				)
			)
			(
				(= ticks 45)
			)
			(
				(Say flunkyTalker "\"There are no strings attached;
					do with it as you will.
					But--he expects to see positive benefits immediately.
					Is that acceptable to you?\""
					#dispose:
					#caller: self
				)
			)
			(
				(= ticks 45)
			)
			(
				(Say presTalker "\"Why, sure.
					In fact, I know just where this money would do the most good.
					Tell your boss he's made the right decision going with us!
					C.A.N.E. will not let him down!\""
					#dispose:
					#caller: self
				)
			)
			(
				(= ticks 45)
			)
			(
				(Say flunkyTalker "\"Oh, let's just say he fully intends to see
					that you live up to your end of this bargain
					(if you get my drift).\""
					#dispose:
					#caller: self
				)
			)
			(
				(= ticks 30)
			)
			(
				(self dispose:)
			)
		)
	)
);sCaneGetsBigGrant

(instance sCaneWorksCongress of Script
	(method (changeState newState)
		(switchto (= state newState)
			(
				(= textUp self)
				(CenterDisplay 4 colWhite
				  "Meanwhile,\n
					a conservative political action\n
					committee peddles a little\n
					more than just influence..."
				)
				(= seconds 7)
			)
			(
				(= textUp FALSE)
				(DrawPic pMeanwhileCANEcongress SCROLLLEFT)
				(= cycles 2)
			)
			(
				(face init:)
				(arm
					init:			,
					setCycle:	EndLoop self
				)
			)
			(
				(TPrint "\"And as you know
					(blah blah blah)
					interested in traditional American values,
					(blah blah blah)
					my campaign promises
					(blah blah blah)
					good and virtue
					(blah blah blah)\""
					#title: 	{Your Elected Official}
					#at:		100 185
					#width:	220
				)
				(= ticks 45)
			)
			(
				(TPrint "\"And furthermore
					(blah blah blah)
					if re-elected, I will
					(blah blah blah)
					pornography, blatant exploitation
					(blah blah blah)
					minors, miners
					(blah blah blah)\""
					#title: 	{Your Elected Official}
					#at:		100 185
					#width:	220
				)
				(= ticks 45)
			)
			(
				(TPrint "\"And in conclusion,
					(blah blah blah)
					(blah blah blah)\""
					#title: 	{Your Elected Official}
					#at:		100 185
					#width:	220
				)
				(= ticks 45)
			)
			(
				(Say congressTalker "\"Yeah, okay, Senator, we've got it.
					Here's the deal: there's plenty more where this comes from,
					as long as we see some progress on that bill to tighten
					regulation of the airwaves.\""
					#dispose:
					#caller: self
				)
			)
			(
				(= ticks 45)
			)
			(
				(Say congressTalker "\"We want that smut off the air--and we're
					willing to back up our beliefs in the `traditional' American
					way. So? Do you want it or don't you?\""
					#dispose:
					#caller: self
				)
			)
			(
				(= ticks 45)
			)
			(
				(TPrint "\"Gimme that suitcase, boy.
					I got legislation to write!\" he grins."
					#title: 	{Your Elected Official}
					#at:		100 185
					#width:	220
				)
				(= ticks 30)
			)
			(
				(self dispose:)
			)
		)
	)
);sCaneWorksCongress



;ษอออออออออป
;บ         บ
;บ Objects บ
;บ         บ
;ศอออออออออผ

(instance leftEyebrow of Prop
	(properties
		view			vMobBoss
		loop		 	0
		x				213
		y				57
		priority		14
		signal		(| fixPriOn ignrAct)
	)
)

(instance rightEyebrow of Prop
	(properties
		view			vMobBoss
		loop 			1
		x				227
		y				57
		priority		14
		signal		(| fixPriOn ignrAct)
	)
)

(instance cigar of Prop
	(properties
		view			vMobBoss
		loop			3
		x				168
		y				87
		priority		14
		signal		(| fixPriOn ignrAct)
	)
)

(instance hand of Prop
	(properties
		view			vMobBoss
		loop		 	4
		x				154
		y				149
		priority	  	14
		signal	  	(| fixPriOn ignrAct)
	)
)

(instance face of Prop
	(properties
		view			vSmilingCongressman
		loop		 	0
		x				221
		y				114
		priority		14
		signal		(| fixPriOn ignrAct)
	)
)

(instance arm of Prop
	(properties
		view			vCaneFlunkee1withDough
		loop	 		0
		x				155
		y				110
		priority		14
		signal		(| fixPriOn ignrAct)
	)
)


;ษอออออออออป
;บ         บ
;บ Talkers บ
;บ         บ
;ศอออออออออผ

; Mob Flunky #1 (Vinnie)

(instance mobFlunky1Talker of Talker
	(properties
		name			"Vinnie"
		view			vMobFlunky1Talker
		loop			lTalkerFrame
		nsLeft		67
		nsTop			66
		x				1
		y				180
		talkWidth	250
	)
	(method (init)
		(= mouth mobFlunky1Mouth)
		(super init: &rest)
	)
)
(instance mobFlunky1Mouth of Prop
	(properties
		view		vMobFlunky1Talker
		loop		lTalkerMouth
		nsLeft	23
		nsTop		31
	)
)


; Mob Flunky #2 (Bruno)

(instance mobFlunky2Talker of Talker
	(properties
		name			"Bruno"
		view			vMobFlunky2Talker
		loop			lTalkerFrame
		nsLeft		267
		nsTop			95
		x				100
		y				180
	)
	(method (init)
		(= mouth mobFlunky2Mouth)
		(super init: &rest)
	)
)
(instance mobFlunky2Mouth of Prop
	(properties
		view		vMobFlunky2Talker
		loop		lTalkerMouth
		nsLeft	0
		nsTop		0
	)
)



; Scruemall

(instance silasTalker of Talker
	(properties
		name			"Silas Scruemall"
		view			vScruemallTalker
		loop			lTalkerFrame
		nsLeft		173
		nsTop			48
		x				80
		y				150
		talkWidth	160
	)
	(method (init)
		(= bust  silasBust)
		(= eyes  silasEyes)
		(= mouth silasMouth)
		(super init: &rest)
	)
)
(instance silasBust of View
	(properties
		view			vScruemallTalker
		loop			lTalkerBust
	)
)
(instance silasEyes of Prop
	(properties
		view			vScruemallTalker
		loop			lTalkerEyes
		nsLeft		-2
		nsTop			1
	)
)
(instance silasMouth of Prop
	(properties
		view			vScruemallTalker
		loop			lTalkerMouth
		nsLeft		0
		nsTop			20
	)
)



; CANE President

(instance presTalker of Talker
	(properties
		name			"President of C.A.N.E."
		view			vCANEPresidentTalker
		loop			lTalkerFrame
		nsLeft		192
		nsTop			55
		x				120
		y				160
	)
	(method (init)
		(= bust  presBust)
		(= eyes  presEyes)
		(= mouth presMouth)
		(super init: &rest)
	)
)
(instance presBust of View
	(properties
		view			vCANEPresidentTalker
		loop			lTalkerBust
	)
)
(instance presEyes of Prop
	(properties
		view			vCANEPresidentTalker
		loop			lTalkerEyes
		nsLeft		17
		nsTop			18
	)
)
(instance presMouth of Prop
	(properties
		view			vCANEPresidentTalker
		loop			lTalkerMouth
		nsLeft		0
		nsTop			35
	)
)

; Mob Flunky with CANE president (Luiggi)

(instance flunkyTalker of Talker
	(properties
		name			"Luigi"
		view			vMobFlunky3Talker
		loop			lTalkerFrame
		nsLeft		95
		nsTop			77
		x				1
		y				160
		talkWidth	200
	)
	(method (init)
		(= eyes  flunkyEyes)
		(= mouth flunkyMouth)
		(super init: &rest)
	)
)
(instance flunkyEyes of Prop
	(properties
		view			vMobFlunky3Talker
		loop			lTalkerEyes
		nsLeft		1
		nsTop			-7
	)
)
(instance flunkyMouth of Prop
	(properties
		view			vMobFlunky3Talker
		loop			lTalkerMouth
		nsLeft		0
		nsTop			0
	)
)



; CANE Flunky with senator (Mortie)

(instance congressTalker of Talker
	(properties
		name			"A C.A.N.E.-er"
		view			vCANEFlunkyTalker
		loop			lTalkerFrame
		nsLeft		144
		nsTop			95
		x				1
		y				140
		talkWidth	250
	)
	(method (init)
		(= eyes  congressEyes)
		(= mouth congressMouth)
		(super init: &rest)
	)
)
(instance congressEyes of Prop			; actually belongs to guy on left
	(properties
		view			vCANEFlunkyTalker
		loop			lTalkerEyes
		nsLeft		-61
		nsTop			-10
	)
)
(instance congressMouth of Prop
	(properties
		view			vCANEFlunkyTalker
		loop			lTalkerMouth
		nsLeft		0
		nsTop			0
	)
)
