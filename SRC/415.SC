;**
;**	Logics for Room 415 -- Alley exterior, 2nd shot
;**
;**	LSL5:  Passionate Patti Does A Little Undercover Work
;**				Copyright 1991
;**				by Al Lowe
;**
;**	Programmed by Brian K. Hughes & Carlos Escobar
;**
;**	Last Update:  September 9, 1991
;**

(script#		415)

(public
	rm415		0
)


;(define	vRoom							415)
;(define	vRoomEgo			 (+ 1 vRoom))
(define	pDarkAlley2Shot				415)
(define	vDesmondFacialAnimation	1415)
(define	vPattiFacialAnimation  	1416)
(define	pDarkAlleyExterior		410)


(define	DESMOND_PRINT		#at  15 15 #width 160)
(define	PATTI_PRINT			#at 145 15 #width 160)

(define	PATTILEFT	168)
(define	PATTITOP		76)
(define	PATTIX	160)
(define	PATTIY	  180)
(define	DESMONDLEFT	120)
(define	DESMONDTOP		73)
(define	DESMONDX	1)
(define	DESMONDY		180)

(define  tTalkLoop 3)
(define  tBustLoop 1)
(define  tEyesLoop 2)
(define  tMouthLoop 0)

;**	RoomScript state values

(local
	seenMsg
)


(instance rm415 of LLRoom
	(properties
		picture		pDarkAlley2Shot
	)

	(method (init)
		(LoadMany VIEW
			vDesmondFacialAnimation
			vPattiFacialAnimation
		)
		(super init:)
		(self setScript: sCartoon)
	)
	(method (dispose)
		(theMusic
			fade:	0 15 12 TRUE self
		)
		(super dispose:)
	)
)


(instance pattiTalker of Talker
	(properties
		name			"You"
		view			vPattiFacialAnimation
		loop			tTalkLoop
		nsLeft		PATTILEFT
		nsTop			PATTITOP
		x				PATTIX
		y				PATTIY
		talkWidth	318
	)
	(method (init)
		(= mouth pattiMouth)
		(super init: &rest)
	)
)

(instance pattiMouth of Prop
	(properties
		view			vPattiFacialAnimation
		loop			tMouthLoop
		nsLeft		1
		nsTop			0
		ticks			10
		cycleSpeed 	5
	)
)

(instance desmondTalker of Talker
	(properties
		name			"Inspector Desmond"
		view			vDesmondFacialAnimation
		loop			tTalkLoop
		nsLeft		DESMONDLEFT
		nsTop			DESMONDTOP
		x				DESMONDX
		y				DESMONDY
		talkWidth	160
	)
	(method (init)
		(= mouth desmondMouth)
		(super init: &rest)
	)
)

(instance desmondMouth of Prop
	(properties
		view			vDesmondFacialAnimation
		loop			tMouthLoop
		nsLeft	0
		nsTop		0
		ticks		10
		cycleSpeed 	5
	)
)


(instance sCartoon of Script
	(method (changeState newState)
		(switchto (= state newState)
			(
				(Say desmondTalker "\"Patti, I'm Inspector Desmond, of your
					Federal Bureau of Investigation.
					If it's all right,
					I'd like to request a few moments of your time.\""
					#dispose:
					#caller:	self
				)
			)
			(
				(= ticks 123)
			)
			(
				(Say pattiTalker "\"But, of course, Inspector,\" you reply.
					\"Nice trench coat!\""
					#dispose:
					#caller:	self
				)
			)
			(
				(= ticks 123)
			)
			(
				(Say desmondTalker "\"You too,\" he smiles, easily recognizing your joke.
					\"I'm here, on behalf of your country, to ask a favor of you.\""
					#dispose:
					#caller:	self
				)
			)
			(
				(= ticks 123)
			)
			(
				(Say pattiTalker "\"Go ahead, Inspector, but I don't usually grant favors
					on a first date!
					Besides, I'm not the private-eye type.\""
					#dispose:
					#caller:	self
				)
			)
			(
				(= ticks 123)
			)
			(
				(Say desmondTalker "He smiles again.
					\"Oh, you won't have to do that, Patti.
					You see, you're exactly the `type' we need!\""
					#dispose:
					#caller:	self
				)
			)
			(
				(= ticks 30)
			)
			(
				(Say desmondTalker "\"Let me explain: we're right in the middle of a
					months-long, nation-wide investigation into the infiltration
					of organized crime into the entertainment industry.\""
					#dispose:
					#caller:	self
				)
			)
			(
				(= ticks 30)
			)
			(
				(Say desmondTalker "\"I'm looking for a special someone,
					someone willing to take some risks,
					someone without a family,
					without many social ties,
					without any extenuating circumstances to get in the way.\""
					#dispose:
					#caller:	self
				)
			)
			(
				(= ticks 30)
			)
			(
				(Say desmondTalker "\"Someone who understands the music business,
					who plays an instrument,
					and who has the looks, brains and talent to be accepted by
					those insiders we're trying to catch.\""
					#dispose:
					#caller:	self
				)
			)
			(
				(= ticks 30)
			)
			(
				(Say desmondTalker "\"I know you've been `stiffed' by these guys--probably
					many times.
					I want these guys, and I bet you do too.
					So, Patti, in short: I need you to go `undercover'!\""
					#dispose:
					#caller:	self
				)
			)
			(
				(= ticks 30)
			)
			(
				(Say desmondTalker "\"What do you say?\""
					#dispose:
					#caller:	self
				)
			)
			(
				(= ticks 30)
			)
			(
				(TPrint "You are taken aback.
					But considering the requirements,
					who could be better for the job than you?
					It would be sweet to get revenge on these guys,
					not just for tonight's embarrassment,
					but for all those others times when you gave your talents
					for a bunch of ingrates."
					WIDELOW)
				(Say pattiTalker "\"But, of course I'll help you, Inspector Desmond!\"
					you hear yourself say,
					\"after all, I recently learned I'm taking next week off.\""
					#dispose:
					#caller:	self
				)
			)
			(
				(= ticks 123)
			)
			(
				(Say pattiTalker "\"So, how do we start?\""
					#dispose:
					#caller:	self
				)
			)
			(
				(= ticks 123)
			)
			(
				(Say desmondTalker "\"Follow me, Patti.
					I have a car and driver waiting for us.\""
					#dispose:
					#caller:	self
				)
			)
			(
;wjh				(theMusic
;wjh					fade:	0 15 12 TRUE self
;wjh				)
				(= ticks 210)
			)
			(
				(curRoom newRoom: pDarkAlleyExterior)
			)
		)
	)
)









