;**
;**	Logics for Room 120 -- Mob Boss's Office
;**
;**	LSL5:  Passionate Patti Does A Little Undercover Work
;**				Copyright 1991
;**				by Al Lowe
;**
;**	Programmed by John Hartin
;**
;**	Last Update:  September 9, 1991
;**

(script#		120)

(public
	rm120		0
)


(define	pMobBossOffice					120)
(define	vSlideShow					120)
	(define	lSlides				0)
		(define	cChart1		0)
		(define	cChart2		1)
		(define	cChart3		2)
;(define	vGoon1Prop			122)
(define	vMobFlunkee1				1122)
;	(define	lTalking				0)
(define	vMobFlunkee2				1123)
;	(define	lTalking				0)
(define	vMobBoss						124)
	(define	lREyebrow			1)	;reversed
	(define	lLEyebrow			0)
	(define	l3rdEyebrow			2)	;??
	(define	lCigar				3)
	(define	lHand					4)

(define mMobOffice	120)
;(define	vRoom							120)
;(define	vRoomEgo			 (+ 1 vRoom))
;(define	vGoon1			122)
;(define	vGoon2			123)
;(define	vBoss				124)
(define  tTalkLoop 3)
(define  tBustLoop 1)
(define  tEyesLoop 2)
(define  tMouthLoop 0)
(define	GOON2LEFT	267)
(define	GOON2TOP		95)
(define	GOON1X	1)
(define	GOON1Y		180)
(define	GOON1LEFT	67)
(define	GOON1TOP		66)
(define	GOON2X	115)
(define	GOON2Y		180)
(define	vBossCartoon	130)

;**	RoomScript state values

(local
	seenMsg
)


(instance rm120 of LLRoom
	(properties
		picture		120
	)

	(method (init)
		(LoadMany VIEW
			vSlideShow
			vMobFlunkee1
			vMobFlunkee2
			vMobBoss
		)
		(LoadMany SOUND
			mMobOffice
		)
		(HandsOff)
		(super init:)
		(goon1 init:, setPri: 10, addToPic:)
		(bossHand init:, cycleSpeed: 6, setCel: (bossHand: lastCel), stopUpd:)
		(bossSmoke init:, cycleSpeed: 24, stopUpd:)
		(bossRtBrow init:, cycleSpeed: 24, stopUpd:)
		(bossLtBrow init:, cycleSpeed: 24, stopUpd:)
		(addToPics doit:)
		(SetFFRoom 155)
		(theMusic
			number:		mMobOffice,
			flags:		TRUE,
			setLoop:		-1,
			play:
		)

		(self setScript: sCartoon)
	)
	(method (dispose)
		(super dispose: &rest)
		(theMusic
			stop:
		)
	)
)


;**(instance slide1 of Prop
;**	(properties
;**		view			vSlideShow
;**		loop			lSlides
;**		cel			cChart2
;**		x				130
;**		y				80
;**	)
;**)

(instance slide of Prop
	(properties
		view			vSlideShow
		loop			lSlides
		cel			cChart1
		x				130
		y				80
	)
)

(instance goon1 of View
	(properties
		name			"Vinnie"
		view			vMobFlunkee1
		loop			tBustLoop
		cel			0
		x				68
		y				65
	)
)

(instance goon1Talker of Talker
	(properties
		name			"Vinnie"
		view			vMobFlunkee1
		loop			tTalkLoop
		nsLeft		GOON1LEFT
		nsTop			GOON1TOP
		x				GOON1X
		y				GOON1Y
		talkWidth	280
	)
	(method (init)
		(= mouth goon1Mouth)
		(super init: &rest)
	)
)

(instance goon1Mouth of Prop
	(properties
		view			vMobFlunkee1
		loop			tMouthLoop
		nsLeft		24
		nsTop			30
		cycleSpeed 	5
	)
)

(instance goon2 of Prop
	(properties
		name			"Bruno"
		view			vMobFlunkee2
		loop			0
		cel			0
		x				283
		y				115
	)
)

(instance goon2Talker of Talker
	(properties
		name			"Bruno"
		view			vMobFlunkee2
		loop			tTalkLoop
		nsLeft		GOON2LEFT
		nsTop			GOON2TOP
		x				GOON2X
		y				GOON2Y
		talkWidth	200
	)
	(method (init)
		(= mouth goon2Mouth)
		(super init: &rest)
	)
)


(instance goon2Mouth of Prop
	(properties
		view			vMobFlunkee2
		loop			tMouthLoop
		nsLeft		0
		nsTop			0
		cycleSpeed 	5
	)
)

(instance bossHand of Prop
	(properties
		view			vMobBoss
		loop			lHand
		cel			0
		x				156
		y				150
	)
)

(instance bossSmoke of Prop
	(properties
		view			vMobBoss
		loop			lCigar
		cel			0
		x				167
		y				91
	)
)

(instance bossRtBrow of Prop
	(properties
		view			vMobBoss
		loop			lREyebrow
		cel			0
		x				227
		y				57
	)
)

(instance bossLtBrow of Prop
	(properties
		view			vMobBoss
		loop			lLEyebrow
		cel			0
		x				213
		y				57
	)
)

;;***********************************************************************
;*
;*		Scripts
;*
;;***********************************************************************

(instance sCartoon of Script
	(method (changeState newState)
		(switchto (= state newState)
			(
				(= cycles 2)
			)
			(
				(Say goon1Talker "\"As you know, ever since LPs came out
					back in the 50s,
					our recording industry subliminal message campaign,
					`Just Do Drugs,' has been a resounding success.\""
					#dispose:
					#caller:		self
				)
			)
			(
				(= ticks 123)
			)
			(
				(slide init:, stopUpd:)
				(= cycles 2)
			)
			(
				(Say goon1Talker "\"Drug sales have never been better,
					and prices remain firm as this slide shows.\""
					#dispose:
					#caller:		self
				)
			)
			(
				(= ticks 60)
			)
			(
				(Say goon1Talker "\"Discounting our disastrous push to
					quadraphonic, results have been most gratifying.\""
					#dispose:
					#caller:		self
				)
			)
			(
				(= ticks 60)
			)
			(
				(goon1Talker talkWidth: 120)
				(Say goon1Talker "\"Now to you, Bruno.\""
					#dispose:
					#caller:		self
				)
			)
			(
				(bossHand
					setCel: (bossHand lastCel:),
					startUpd:,
					cycleSpeed: 18,
					setCycle: Reverse,
				)
				(bossSmoke startUpd:, setCycle: Reverse)
;				(bossRtBrow startUpd:, setCycle: Reverse)
;				(bossLtBrow startUpd:, setCycle: Reverse)
				(= ticks 240)
			)
			(
				(bossHand
					setCel: (bossHand lastCel:),
					stopUpd:,
					setCycle: 0
				)
				(bossSmoke
					stopUpd:,
					setCycle: 0
				)
				(bossRtBrow
					stopUpd:,
					setCycle: 0
				)
				(bossLtBrow
					stopUpd:,
					setCycle: 0
				)
				(= cycles 5)
			)
			(
				(Say goon2Talker "\"Thanks, Vinnie.
					Boss, as you know,
					our pornography division also enjoyed a period
					of explosive growth as this slide shows.\""
					#dispose:
					#caller:		self
				)
			)
			(
				(= ticks 60)
			)
			(
				(slide startUpd:, cel: cChart2)
				(= cycles 2)
			)
			(
				(Say goon2Talker "\"Unfortunately, after a quarter century of
					positive ROI, recent results have been flaccid.\""
					#dispose:
					#caller:		self
				)
			)
			(
				(= ticks 60)
			)
			(
				(Say goon2Talker "\"We attempted to counter this trend
					with a heavy investment in rap music,
					thinking teenagers' continuous exposure to pornographic lyrics
					would help sales of our wider product line.\""
					#dispose:
					#caller:		self
				)
			)
			(
				(= ticks 60)
			)
			(
				(Say goon2Talker "\"But the result is obvious: painfully
					declining penetration!\""
					#dispose:
					#caller:		self
				)
			)
			(
				(= ticks 60)
			)
			(
				(slide cel: cChart3)
				(bossHand
					startUpd:,
					setCel: (bossHand lastCel:),
					setCycle: Reverse,
				)
				(bossSmoke startUpd:, setCycle: Reverse)
;				(bossRtBrow startUpd:, setCycle: Reverse)
;				(bossLtBrow startUpd:, setCycle: Reverse)
				(= ticks 240)
			)
			(
				(bossHand
					setCel: (bossHand lastCel:),
					stopUpd:,
					setCycle: 0
				)
				(bossSmoke
					stopUpd:,
					setCycle: 0
				)
				(bossRtBrow
					stopUpd:,
					setCycle: 0
				)
				(bossLtBrow
					stopUpd:,
					setCycle: 0
				)
				(= cycles 5)
			)
			(
				(Say goon2Talker "\"Our market research failed to foresee the
					widespread growth of cable!\""
					#dispose:
					#caller:		self
				)
			)
			(
				(= ticks 60)
			)
			(
				(Say goon2Talker "\"Even when we forced through rapidly
					reduced VCR prices in the early 80s it just didn't help our
					X-rated movie sales--no one buys them!\""
					#dispose:
					#caller:		self
				)
			)
			(
				(= ticks 60)
			)
			(
				(Say goon2Talker "\"Why should they, when they can use
					their remote controls to flip through channel after channel of almost
					the same stuff in the comfort of their own homes?\""
					#dispose:
					#caller:		self
				)
			)
			(
				(= ticks 60)
			)
			(
				(Say goon2Talker "\"Now even the networks
					are getting into the act!\""
					#dispose:
					#caller:		self
				)
			)
			(
				(= ticks 60)
			)
			(
				(curRoom newRoom: vBossCartoon)
			)
		)
	)
);sCartoon

