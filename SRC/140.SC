;**
;**	Logics for Room 140 -- President's office, PornProdCorp
;**
;**	LSL5:  Passionate Patti Does A Little Undercover Work
;**				Copyright 1991
;**				by Al Lowe
;**
;**	Programmed by John Hartin
;**
;**	Last Update:  September 9, 1991
;**


(script#		140)

(public
	rm140		0
)


(define	pPresidentPornProdCorp		140)
(define	vFlunkiesATPs				140)
(define	vLarryW/Coffee				141)
	(define	lTakingPot			0)
	(define	lWalkingW/Pot		1)
	(define	lTurningSouth		2)
(define	vPPCflunkee1Speaks		142)
;	(define	lTalking				0)
(define	vPPCflunkee2Speaks		143)
;	(define	lTalking				0)
(define	vSilasScruemallSpeaks		144)
	(define	lLookingAround		0)
		(define	cLookingAround		1)
		(define	cTalkingR			0)
		(define	cTalkingL			2)
(define	pPassWordRoom			155)
(define	pPornProdCorpLobby			160)
(define	pScruemallLarry2shot			150)
(define mPornProdOffice	140)

(define	vPPCFlunky1Talker		1142)
(define	vPPCFlunky2Talker		1143)
(define	vSilasScruemallTalker		1140)
(define  tTalkLoop 3)		;order taken from view 1622.v16; Guard at Shill
(define  tBustLoop 1)
(define  tEyesLoop 2)
(define  tMouthLoop 0)
(define	GEEK1LEFT	3)
(define	GEEK1TOP		113)
(define	GEEK1X	70)
(define	GEEK1Y		140)
(define	GEEK2LEFT	48)
(define	GEEK2TOP		113)
(define	GEEK2X	113)
(define	GEEK2Y		120)
(define	BOSSLEFT	40)
(define	BOSSTOP		106)
(define	BOSSX	85)
(define	BOSSY		146)

(define RAND0	#at: 68 91)
(define RAND1	#at: 105 137)
(define RAND2	#at: 180 113)
(define RAND3	#at: 180 147)
(define RAND4	#at: 0 147)

;(define RANDPOS
;	(switch (Random 0 3)
;		(0
;		  RAND0
;		)
;		(1
;		  RAND1
;		)
;		(2
;		  RAND2
;		)
;		(3
;		  RAND3
;		)
;	)
;)

;**	RoomScript state values

(local
	seenMsg
)


(instance rm140 of LLRoom
	(properties
		picture		140
	)

	(method (init)
		(LoadMany VIEW
			vFlunkiesATPs
			vLarryW/Coffee
			vPPCFlunky1Talker
			vPPCFlunky2Talker
			vSilasScruemallTalker
		)
		(LoadMany SOUND
			mPornProdOffice
		)
		(HandsOff)
		(super init:)
		(fScruemall init:, setPri: pCYAN)
		(pornGeek1 init:)
		(pornGeek2 init:)
		(geek3 init:)
		(geek4 init:)
		(geek4a init:)
		(geek5 init:)
		(geek6 init:)
		(geek6a init:)
		(geek7 init:)
		(addToPics doit:)
		(SetFFRoom 155)
		(theMusic
			number:		mPornProdOffice,
			flags:		TRUE,
			setLoop:		-1,
			play:
		)

		(if (== prevRoomNum 130)
			(self setScript: sCartoon)
		else
			(= style SCROLLRIGHT)
			(self setScript: sLarryCartoon)
		)
	)
	(method (newRoom n)
		(if (== n 160)
			(theMusic
				fade:	0 15 12 TRUE
			)
		)
		(super newRoom: n)
	)
)


(instance fScruemall of Prop
	(properties
		view			vSilasScruemallSpeaks
		loop			lLookingAround
		cel			cTalkingL
		x				158
		y				55
	)
)

(instance pornGeek1 of PicView
	(properties
		view			vPPCflunkee1Speaks
		loop			0
		cel			0
		x				63
		y				81
		priority		7
		signal		fixPriOn
	)
)

(instance pornGeek2 of PicView
	(properties
		view			vPPCflunkee2Speaks
		loop			0
		cel			0
		x				249
		y				75
		priority		pLGREY
		signal		fixPriOn
	)
)

(instance geek3 of PicView
	(properties
		view			vFlunkiesATPs
		loop			0
		cel			0
		x				53
		y				110
		priority		pGREY
	)
)

(instance geek5 of PicView
	(properties
		view			vFlunkiesATPs
		loop			1
		cel			0
		x				267
		y				114
		priority		pLBLUE
	)
)

(instance geek4 of PicView
	(properties
		view			vFlunkiesATPs
		loop			2
		cel			1
		x				221
		y				159
	)
)

(instance geek4a of PicView
	(properties
		view			vFlunkiesATPs
		loop			2
		cel			0
		x				236
		y				128
		priority		14
		signal		fixPriOn
	)
)

(instance geek6 of PicView
	(properties
		view			vFlunkiesATPs
		loop			3
		cel			1
		x				103
		y				154
		priority		13
		signal		fixPriOn
	)
)

(instance geek6a of PicView
	(properties
		view			vFlunkiesATPs
		loop			3
		cel			0
		x				89
		y				126
		priority		14
		signal		fixPriOn
	)
)

(instance geek7 of PicView
	(properties
		view			vFlunkiesATPs
		loop			4
		cel			0
		x				157
		y				128
	)
)



;ษอออออออออออออออออออป
;บ                   บ
;บ Talkers           บ
;บ                   บ
;ศอออออออออออออออออออผ

(instance geek1Talker of Talker
	(properties
		name			"Biffie"
		view			vPPCFlunky1Talker
		loop			tTalkLoop
		nsLeft		GEEK1LEFT
		nsTop			GEEK1TOP
		viewInPrint TRUE
	)
	(method (init)
		(= bust 	geek1Bust)
		(= eyes 	geek1Eyes)
		(= mouth geek1Mouth)
		(super init: &rest)
	)
)

(instance geek1Bust of Prop
	(properties
		view		vPPCFlunky1Talker
		loop		tBustLoop
		nsLeft	0
		nsTop		0
	)
)

(instance geek1Eyes of Prop
	(properties
		view			vPPCFlunky1Talker
		loop			tEyesLoop
		nsLeft		10
		nsTop			15
		cycleSpeed 	70
	)
)

(instance geek1Mouth of Prop
	(properties
		view			vPPCFlunky1Talker
		loop			tMouthLoop
		nsLeft		26
		nsTop			36
		cycleSpeed 	5
	)
)


(instance geek2Talker of Talker
	(properties
		name			"Scooter"
		view			vPPCFlunky2Talker
		loop			tTalkLoop
		nsLeft		GEEK2LEFT
		nsTop			GEEK2TOP
		viewInPrint	TRUE
	)
	(method (init)
		(= bust 	geek2Bust)
		(= eyes 	geek2Eyes)
		(= mouth geek2Mouth)
		(super init: &rest)
	)
)

(instance geek2Bust of Prop
	(properties
		view		vPPCFlunky2Talker
		loop		tBustLoop
		nsLeft	0
		nsTop		0
	)
)

(instance geek2Eyes of Prop
	(properties
		view			vPPCFlunky2Talker
		loop			tEyesLoop
		nsLeft		23
		nsTop			34
		cycleSpeed 	70
	)
)

(instance geek2Mouth of Prop
	(properties
		view			vPPCFlunky2Talker
		loop			tMouthLoop
		nsLeft		16
		nsTop			40
		cycleSpeed 	5
	)
)

(instance ScruemallTalker of Talker
	(properties
		name			"Silas Scruemall"
		view			vSilasScruemallTalker
		loop			tTalkLoop
		nsLeft		BOSSLEFT
		nsTop			BOSSTOP
		viewInPrint	TRUE
	)
	(method (init)
		(= bust 	ScruemallBust)
		(= eyes 	ScruemallEyes)
		(= mouth ScruemallMouth)
		(super init: &rest)
	)
)

(instance ScruemallBust of Prop
	(properties
		view		vSilasScruemallTalker
		loop		tBustLoop
		nsLeft	0
		nsTop		0
	)
)

(instance ScruemallEyes of Prop
	(properties
		view			vSilasScruemallTalker
		loop			tEyesLoop
		nsLeft		13
		nsTop			31
		cycleSpeed 	70
	)
)

(instance ScruemallMouth of Prop
	(properties
		view			vSilasScruemallTalker
		loop			tMouthLoop
		nsLeft		8
		nsTop			53
		cycleSpeed 	5
	)
)

;;*************************************
;*
;*		Scripts
;*
;;*************************************

(instance sCartoon of Script
	(method (changeState newState)
		(switchto (= state newState)
			(
				(Say geek1Talker "\"And so,\" a corporate yes man continues,
					\"I'm pleased to say our contest to find
					`The Sexiest Woman in America' to serve as the hostess on
					`America's Sexiest Home Videos'
					has been a resounding success!\""
					#dispose:
					#caller:		self
				)
			)
			(
				(= ticks 60)
			)
			(
				(Say geek1Talker "\"The character portrayed by our hostess,
					whom we've decided to name `Vanna Black,'
					will be one exceptional woman!\""
					#dispose:
					#caller:		self
				)
			)
			(
				(= ticks 60)
			)
			(
				(Say geek1Talker "\"We've searched for a woman, beautiful,
					yet with those All-American, girl-next-door looks;
					a woman virginal and untouchable;
					and yet, a woman who is sexy and slutty.\""
					#dispose:
					#caller:		self
				)
			)
			(
				(= ticks 60)
			)
			(
				(Say geek1Talker "\"We've received thousands of submissions,
					and (thanks to hard work by me and my colleagues)
					narrowed the field down to three finalists.\""
					#dispose:
					#caller:		self
				)
			)
			(
				(= ticks 60)
			)
			(
				(Say geek1Talker "\"I intend to have these women flown here,
					first class, wined and dined in regal style,
					then outfitted with the finest fashions Rodeo Drive
					has to offer.\""
					#dispose:
					#caller:		self
				)
			)
			(
				(= ticks 60)
			)
			(
				(Say geek1Talker "\"They'll want for nothing!
					And, on the morning of the auditions,
					champagne baths for all!
					I've arranged national press coverage, of course.\""
					#dispose:
					#caller:		self
				)
			)
			(
				(= ticks 60)
			)
			(
				(Say geek1Talker "\"In short, everything is proceeding
					according to schedule.\""
					#dispose:
					#caller:		self
				)
			)
			(
				(= ticks 60)
			)
			(
				(fScruemall setCel: cLookingAround)
				(= seconds 3)
			)
			(
				(TPrint "There is a polite smattering of applause from the
					the other lackeys around the conference table.")
				(= seconds 3)
			)
			(
				(fScruemall setCel: cTalkingL)
				(= ticks 100)
			)
			(
				(Say ScruemallTalker "\"But wait, Biffie.
					We all know that if we bring these women to Hollywood to
					audition under those circumstances, they'll all be sexy.\""
					#dispose:
					#caller:		self
				)
			)
			(
				(= ticks 60)
			)
			(
				(fScruemall setCel: cLookingAround)
				(TPrint "\"Yes sir, Mr. Scruemall!\"" RAND0 #title {A Yes Man})
				(TPrint "\"Isn't that what we wanted, Mr. Scruemall?\"" RAND1 #title {A Yes Man})
				(TPrint "\"Bravo, Mr. Scruemall!\"" RAND2 #title {A Yes Man})
				(= seconds 3)
			)
			(
				(Say ScruemallTalker "\"But, we're looking for a woman
					so over-sexed she'd drop (what's she's doing) for any man,
					any time, any place, and for any loser, any dork,
					no matter how lame!"
					#dispose:
					#caller:		self
				)
			)
			(
				(= ticks 60)
			)
			(
				(TPrint "\"Yes sir, Mr. Scruemall!\""  RAND3 #title {A Yes Man})
				(TPrint "\"Isn't that what we wanted, Mr. Scruemall?\"" RAND4 #title {A Yes Man})
				(TPrint "\"Bravo, Mr. Scruemall!\"" RAND0 #title {A Yes Man})
				(= seconds 3)
			)
			(
				(Say ScruemallTalker "\"I've changed my mind!
					Now I think we should hold a blind audition,
					where the women don't know they're being auditioned!\""
					#dispose:
					#caller:		self
				)
			)
			(
				(= ticks 60)
			)
			(
				(fScruemall setCel: cTalkingR)
				(TPrint "\"Yes sir, Mr. Scruemall!\"" RAND1 #title {A Yes Man})
				(TPrint "\"Isn't that what we wanted, Mr. Scruemall?\"" RAND2 #title {A Yes Man})
				(TPrint "\"Bravo, Mr. Scruemall!\"" RAND3 #title {A Yes Man})
				(= seconds 3)
			)
			(
				(Say geek2Talker "\"But (with all due respects, Mr. Scruemall)
					that's impossible.
					We need videotapes of their auditions for our own,
					uh, personal review purposes.
					There's no way we could follow the guy around without
					her seeing us!\""
					#dispose:
					#caller:		self
				)
			)
			(
				(fScruemall setCel: cLookingAround)
				(= seconds 3)
			)
			(
				(Say ScruemallTalker "\"Well, yes,
					we do need those tapes to review.
					But what if we could find an auditioner so inept, so sexless,
					so lame, so unappealing, that it would take the sexiest
					woman in America to find him attractive?\""
					#dispose:
					#caller:		self
				)
			)
			(
				(= ticks 60)
			)
			(
				(TPrint "\"Great idea, Mr. Scruemall!\"" RAND4 #title {A Yes Man})
				(TPrint "\"Of course, Mr. Scruemall!\"" RAND0 #title {A Yes Man})
				(TPrint "\"Masterful stroke, Mr. Scruemall!\"" RAND1 #title {A Yes Man})
				(fScruemall setCel: 0)
				(= seconds 4)
			)
			(
				(Say geek2Talker "\"But, Sil--, er, Mr. Scruemall!
					How will we ever find somebody so lame?\""
					#dispose:
					#caller:		self
				)
			)
			(
				(fScruemall setCel: cTalkingR)
				(= ticks 150)
			)
			(
				;**	fade to black here
				(theIconBar disable:)
				(DrawPic pBlackPic (+ IRISIN BLACKOUT))
				(= seconds 3)
			)
			(
				(theIconBar enable:)
				(curRoom newRoom: pPornProdCorpLobby)
			)
		)
	)
);sCartoon

(instance sLarryCartoon of Script
	(method (changeState newState)
		(switchto (= state newState)
			(
				(fScruemall setCel: cLookingAround)
				(ego
					init:,
					x: 319,
					y: 69,
					view:	vLarryW/Coffee,
					setCycle: Walk,
					loop: lWalkingW/Pot,
					setMotion: MoveTo 217 56 self		;295 66 self
				)
			)
			(
				(Say ego "\"Hi, guys!\" you say cheerfully.
					\"So, who wanted java?\"")
				(= cycles 10)
			)
			(
				(Say ScruemallTalker "\"You may begin with me, boy.\""
					#dispose:
					#caller:		self
				)
			)
			(
				(TPrint "(So you decide to do exactly that!)" LOW)
				(ego
					setMotion: MoveTo 190 53 self
				)
			)
			(
				(ego
					setMotion: MoveTo 125 53 self
				)
			)
			(
				(fScruemall setCel: cTalkingL)
				(ego
					setCel: 0,
					y: 55,
					setLoop: lTurningSouth,
					setCycle: EndLoop self
				)
			)
			(
				(Say ego "\"Here you go, Mr. Scruemall,\" you say in your
					best suck-up tone of voice.
					\"May I freshen that for you?\"")
				(curRoom newRoom: pScruemallLarry2shot)
			)
		)
	)
)

