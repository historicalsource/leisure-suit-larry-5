;**
;**	Logics for Room 420 -- Inspector Desmond's office
;**
;**	LSL5:  Passionate Patti Does A Little Undercover Work
;**				Copyright 1991
;**				by Al Lowe
;**
;**	Programmed by Brian K. Hughes & Carlos Escobar
;**
;**	Last Update:  September 12, 1991
;**

(script#		420)

(public
	rm420		0
)


(define	vRoom							420)
(define	vRoomEgo			 (+ 1 vRoom))
(define	vDesmondTalker			1420)
(define	vPattiOfficeTalker	1421)

(define  mDesmond3				425)


(instance rm420 of LLRoom
	(properties
		picture		420
	)

	(method (init)
		(super init:)
		(LoadMany VIEW
			vDesmondTalker
			vPattiOfficeTalker
		)
		(theMusic
			number:		mDesmond3,
			loop:			-1,
			flags:		1,
			play:,
		)
		(self setScript: sRoom)
	)
)


(instance sRoom of Script
	(method (changeState newState)
		(switchto (= state newState)
			(
				(= ticks 123)
			)
			(
				(Say desmondTalker
					"\"All right, Patti, here's the deal:
					we're convinced of organized crime's considerable influence
					in the entertainment industry.
					I want you to use your musical talent to help us obtain
					sufficient evidence to convict the guilty parties.\""
					#dispose:
					#caller:	self
				)
			)
			(
				(= ticks 60)
			)
			(
				(Say pattiTalker
					"\"But, Inspector,\" you say worriedly,
					\"I know nothing of spying.\""
					#dispose:
					#caller: self
				)
			)
			(
				(= ticks 60)
			)
			(
				(Say desmondTalker
					"\"Don't worry, Patti, most of the spies we use are amateurs!
					You won't be `licensed to kill!'
					Just keep your eyes and ears open,
					learn what you can,
					take advantage of any opportunities,
					and try to bring back some hard evidence!\""
					#dispose:
					#caller:	self
				)
			)
			(
				(= ticks 60)
			)
			(
				(Say desmondTalker
					"\"We'll make sure you're in the right place at the right time.
					And you'll be doing good for your country.\""
					#dispose:
					#caller:	self
				)
			)
			(
				(= ticks 60)
			)
			(
				(TPrint "You think, \"Not to mention getting even with those
					bastards for all the times I've been screwed in the past!\"")
				(= ticks 60)
			)
			(
				(Say pattiTalker
					"\"All right, Inspector Desmond,\" you say.
					\"But what places and what times?\""
					#dispose:
					#caller: self
				)
			)
			(
				(= ticks 60)
			)
			(
				(Say desmondTalker
					"\"There are two specific areas of concern:
					one is subliminal messages in popular music.
					Our studies link the recent nationwide rise in crime to
					increased popularity of hard rock music.\""
					#dispose:
					#caller:	self
				)
			)
			(
				(= ticks 60)
			)
			(
				(Say desmondTalker
					"\"`des Rever Records' in Baltimore is heavily involved
					in such subliminal recordings.
					Much of the overdubbing at that studio is handled by a
					young recording engineer named Reverse Biaz.\""
					#dispose:
					#caller:	self
				)
			)
			(
				(= ticks 60)
			)
			(
				(Say desmondTalker
					"\"The second area is the recent flood of obscene
					rap recordings.
					I personally believe records like these are being used by the
					Mob to undermine the moral fiber of our nation's youth.\""
					#dispose:
					#caller:	self
				)
			)
			(
				(= ticks 60)
			)
			(
				(Say desmondTalker
					"\"`K-RAP Radio' in Philadelphia is the headquarters of
					the rap group `2 Live 2 Screw,'
					who have had an unjustified string of hits recently.
					I'm sure that place is a center of Mob involvement.\""
					#dispose:
					#caller:	self
				)
			)
			(
				(= ticks 60)
			)
			(
				(TPrint "\"That's hard to believe,\" you think.")
				(= ticks 60)
			)
			(
				(Say pattiTalker
					"But to Desmond you say, \"Well, all right, Inspector,
					but I'm hardly an expert in subliminal recordings or
					rap music!\""
					#dispose:
					#caller: self
				)
			)
			(
				(= ticks 60)
			)
			(
				(Say desmondTalker
					"\"Oh, don't worry, Patti.
					With your `talents,' you can easily fake your way through the
					simplistic noise these guys pass off as music.
					After all, they're hardly the M.J.Q.!\""
					#dispose:
					#caller:	self
				)
			)
			(
				(= ticks 60)
			)
			(
				(Say desmondTalker
					"\"One more thing: a man we know only as `Julius' has been
					connected to both these businesses.
					I think this `Julius' character may be the key
					to the entire problem.\""
					#dispose:
					#caller:	self
				)
			)
			(
				(= ticks 60)
			)
			(
				(Say desmondTalker
					"\"Anything you can discover about `Julius'
					or K-RAP or des Rever Records would be helpful.
					We'll provide you with background information
					on all the pertinent people, places and groups,
					but it will be up to you to get in and
					get out with whatever hard facts you can.\""
					#dispose:
					#caller:	self
				)
			)
			(
				(= ticks 60)
			)
			(
				(Say pattiTalker
					"\"Okay, boss, I'm on! Now, how do I begin?\""
					#dispose:
					#caller: self
				)
			)
			(
				(= ticks 60)
			)
			(
				(Say desmondTalker
					"\"Earlier this morning I set you up with a gig at
					des Rever Records as a keyboard sub on a recording session.\""
					#dispose:
					#caller:	self
				)
			)
			(
				(= ticks 60)
			)
			(
				(Say desmondTalker
					"\"We've also arranged a short East Coast tour as your
					cover story.
					The government will handle all your travel arrangements.\""
					#dispose:
					#caller:	self
				)
			)
			(
				(= ticks 60)
			)
			(
				(Say pattiTalker
					"\"First class, of course?\""
					#dispose:
					#caller: self
				)
			)
			(
				(= ticks 60)
			)
			(
				(Say desmondTalker
					"\"Yes,\" he laughs, \"first class, of course!
					I'll get you a private limousine and your own personal driver!
					And, Patti, if things go well,
					I'll even set you up with a gig at the White House!
					Would you like that?\""
					#dispose:
					#caller:	self
				)
			)
			(
				(= ticks 60)
			)
			(
				(Say pattiTalker
					"\"Yes, of course!\" you agree,
					afraid now that you're in way over your head."
					#dispose:
					#caller: self
				)
			)
			(
				(= ticks 60)
			)
			(
				(Say desmondTalker
					"\"Come with me, Patti,\" Desmond says, rising to his feet,
					\"we'll get started right now!\""
					#dispose:
					#caller:	self
				)
			)
			(
				(Cls)
				(theMusic fade:)
				(curRoom newRoom: 430)
			)
		)
	)
);sRoom


;ษอออออออออออออออออออป
;บ                   บ
;บ Talkers           บ
;บ                   บ
;ศอออออออออออออออออออผ

(instance desmondTalker of Talker
	(properties
		name			"Inspector Desmond"
		view			vDesmondTalker
		loop			3
		nsLeft		115
		nsTop			29
		x				0
		y				160
		talkWidth   200
	)
	(method (init)
		(= bust 	desmondBust)
		(= mouth desmondMouth)
		(super init: &rest)
	)
)
(instance desmondBust of Prop
	(properties
		view			vDesmondTalker
		loop			1
		nsLeft		0
		nsTop			0
	)
)
(instance desmondMouth of Prop
	(properties
		view			vDesmondTalker
		loop			0
		nsLeft		0
		nsTop			13
		cycleSpeed	8
	)
)

(instance pattiTalker of Talker
	(properties
		name			"You"
		view			vPattiOfficeTalker
		loop			3
		nsLeft		199
		nsTop			59
		x				140
		y				170
	)
	(method (init)
		(= mouth pattiMouth)
		(super init: &rest)
	)
)
(instance pattiMouth of Prop
	(properties
		view			vPattiOfficeTalker
		loop			0
		nsLeft		0
		nsTop			0
		cycleSpeed	8
	)
)

