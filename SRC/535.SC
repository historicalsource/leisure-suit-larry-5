;**
;**	Logics for Room 535 -- Michelle Milken close-up
;**
;**	LSL5:  Passionate Patti Does A Little Undercover Work
;**				Copyright 1991
;**				by Al Lowe
;**
;**	Programmed by Carlos Escobar
;**
;**	Last Update:  September 11, 1991
;**

(script#		535)

(public
	rm535		0
)

(procedure
	BranchIt
	Reset
)

(define	vRoom 535)
(define	pMMCloseUp1				535)
(define	vFacialExpressions	535)
	(define	lBody					0)
	(define	lEyes					1)
	(define	lMouth				2)
	(define	lTongue				3)
	(define	lSingleCels			4)
		(define	cIceCream		0)
		(define	cCherry			1)
(define	vMMAnimation1			536)
	(define	lFingerIntoCream	0)
	(define	lSuckingFinger	  	1)

(define	pMMCloseUp2				540)
(define	vMMAnimation2			540)
	(define	lGrabbingBanana	0)
	(define	lGoingDown			1)
	(define	lSuckingBanana		2)
	(define	lComesUp				3)
	(define	lStartShoulder		4)
	(define	lShoulderWalk		5)
(define	vLarryCloseUp			545)
(define	vLarryFace				545)
	(define	lBeingSurprised	0)
	(define	lNoseWoody			1)

(define	mMichelle2				538)
(define	mMichelle				538)
(define	sBanana					537)
(define	sGetsCherry				536)


(define	MICHELLE_X				150)
(define	MICHELLE_Y				115)
(define	EYE_X						154)
(define	EYE_Y						78)
(define	TONGUE_X					155)
(define	TONGUE_Y					81)
(define 	CREAM_X					151)
(define	CREAM_Y					141)
(define	TALK_X					155)
(define	TALK_Y					81)
(define 	CHERRY_X					148)
(define	CHERRY_Y					116)

(define	FIRST_WARNING			60)
(define  LOSER_TIME				90)

(local
	cueCounter
	oldTime
	secsInRoom
	michelleMsg
	talkCounter
	giveItems
	didSomething
	loserCounter
)

(instance rm535 of LLRoom
	(properties
		picture		535
		lookStr		"You are sitting with the gorgeous Michelle Milken
						in an exclusive private booth
						in the exclusive Herman Hollerith Room
						in the exclusive Hard Disk Cafe
						in exclusive New York City,
						trying hard not to stare through
						her see-through blouse."
	)

	(method (init)
		(super init:)

		(HandsOff)

;		(ego get: iDayTrotter iMoney iCreditCards)

		(michelle
			init:			,
			stopUpd:		,
			setScript:	sConversation
		)

		(if (not (Btst fMCloseUp))		;first time in here?
			(Load SOUND
				sGetsCherry
			)
			(mEyes
				init:			,
				setScript: 	sBlink
			)
			(mMouth
				init:,
				hide:
			)
			(iceCream	init:)
			(cherry		init:)
			(HandsOn)
			(theIconBar disable:	walkIcon)
			(User canControl: FALSE)
		else
			(michelle  view:	540)
			(iceCream	init:)
			(mMouth
				init:,
				hide:
			)
			(LoadMany SOUND
				mMichelle2
				sBanana
			)
			(if
				(not (Btst fCherryGone))
				(cherry init:)
			)
			(self setScript: sGoDown)
		)

		;init features
		(face 		init:)
		(tits			init:)
	);init
	(method (doit)
		(super doit:)
		(if
			(and
				(not script)
				(!= oldTime (GetTime SYSTIME1))
			)
			(= oldTime (GetTime SYSTIME1))
			(if
				(== (++ loserCounter) LOSER_TIME)
				(Reset)
				(HandsOff)
				(self setScript: sLoser)
			)
			(if
				(and
					(not didSomething)
					(== (++ secsInRoom) FIRST_WARNING)
				)
				(TPrint
					"Perhaps you should take the initiative, Larry.
					You've been sitting there for over a minute now.
					Try something!"
				)
			)
		)
	)
)

(instance sLoser of Script
	(method (changeState newState)
		(switchto (= state newState)
			(
				(Say MMtalker "\"You know, Larry,
					it's nice just sitting here with you,
					but I'm going to have to run.
					I've got a big, uh, er, `bored' meeting I'm late for.
					Maybe I'll see you around, eh?\""
					#dispose:
					#caller:		self
				)
			)
			(
				(DrawPic pBlackPic IRISIN)
				(michelle hide:)

				(Say ego "\"Wait, Michelle!\" you cry as she leaves,
					\"I've still got a few things left to say...\""
					LOW
				)
				(= seconds 3)
			)
			(
				(TPrint "Well, Larry, you \"blew\" that one!")
				(= seconds 3)
			)
			(
				(TPrint "Hanging your head in shame,
					you wander back to the lobby.")
				(curRoom newRoom: 510)
			)
		)
	)
)

(instance sGoDown of Script
	(method (doit)
		(super doit: &rest)
		(if
			(and
				(== (theMusic number?) mMichelle)
				(== (theMusic prevSignal?) 10)
			)
			(theMusic
				number:	mMichelle2,
				play:
			)
		)
	)
	(method (changeState newState)
		(switchto (= state newState)
			(
				(= seconds 3)
 ;**					(iceCream y: (+ (iceCream y?) 14))
 ;**	 				(cherry y: (+ (cherry y?) 14))
			)
			(
				(michelle
					view:			540,
					setLoop:		0,
					setCel:		0,
					setCycle:	CycleTo 2 1 self,
				)
			)
			(
				(iceCream hide:)
				(if
					(IsObject cherry)
					(cherry hide:)
				)
				(michelle
					setCycle:	EndLoop self
				)
			)
			(
				(= seconds 2)
			)
			(
				(soundFX
					number:		sBanana,
					play:
				)
				(= seconds 3)
			)
			(
				(soundFX stop:)
				(michelle
					setLoop:		1,
					setCel:		0,
					setCycle:	EndLoop self,
				)
			)
			(
				(= seconds 3)
			)
			(
;**					(michelle
;**						setLoop:		2,
;**						setCel:		0,
;**			  		)
				(mMouth
					view:			540,
					setLoop:		3,
					setCel:		0,
					x:				(- (mMouth x?) 1),
					setPri:		14,
					show:			,
					setCycle:	EndLoop self
				)
			)
			(
				(= seconds 2)
			)

				;from cherry script

			(
				(mMouth hide:)
				(michelle
					view:			541,
					setCel:		0,
					cycleSpeed:	18,
					setCycle:	EndLoop self,
				)
				(iceCream
					setCel:		1,
					x:				(michelle x?),
					y:				(michelle y?),
					show:
				)
			)
			(
				(= seconds 3)
			)
			(
				(michelle 	hide:)
				(iceCream dispose:)
				(cherry dispose:)
				(DrawPic pBlackPic IRISIN)
				(= ticks 34)
			)
			(
				(michelle
					view:		545,
					posn:		192 119,
					setLoop: 0,
					setCel:	0,
					show:		,
				)
				(DrawPic 535 (| HWIPE HMIRROR))
				(= seconds 4)
			)
			(
				(michelle
					setCel:	1,
				)
				(TPrint "Oh, NOW you get her drift!")
				(if camcorderOn
					(RecordTape tapeRecordedMM)
					(Points ptsRecordedMM pfRecordedMM)
					(TPrint "You're almost sorry you're recording all this.
						What will the boys in Hollywood think?")
				else
					(TPrint "What a shame you didn't get this on tape, Larry!")
				)
				(= seconds 4)
			)
			(
				(michelle
					setLoop:	1,
					setCel:	0,
				)
				(= seconds 4)
			)
			(
				(michelle
					setCel:	1,
				)
				(= seconds 4)
			)
			(
				(michelle
					setCel:	2,
				)
				(= seconds 4)
			)
			(
				(DrawPic pBlackPic IRISIN)
				(michelle hide:)
				(= seconds 3)
			)
			(
				(if (ego has: iMoney)
					(ego put: iMoney)
					(TPrint "You more than willingly give all your money to
						Michelle!")
				)
				(if (ego has: iCreditCards)
					(ego put: iCreditCards)
					(TPrint "You think, \"I don't need those credit cards,\"
						so you gladly offer them to Michelle,
						in return for a little more of the same!")
				)
				(if (ego has: iDayTrotter)
					(ego put: iDayTrotter)
					(TPrint "Leafing through your DayTrotter\05 with Michelle,
						she expresses an interest in some of those cryptic numbers
 						listed beside the notation \"Swiss Accounts.\"
						You allow her to keep the book,
						since you have no interest in chocolate.")
				)
				(Points ptsHadSexMM pfHadSexMM)
				(TPrint "After a few more minutes with Michelle,
					you stagger to your feet and attempt to walk back
					to the Hard Disk Cafe lobby.")
				(= cycles 2)
			)
			(
				(curRoom newRoom 510)
			)
		)
	)
);sCartoon

(instance sBlink of Script
	(method (changeState newState &tmp var1 var2)
		(switchto (= state newState)
			(
				(= seconds (Random 1 4))
			)
			(
				(mEyes setCycle: EndLoop self)
			)
			(
				(= cycles (Random 1 3))
			)
			(
				(mEyes setCycle: BegLoop self)
			)
			(
				(self init:)
			)
		)
	)
)

(instance sSuckFinger of Script
	(method (changeState newState)
		(switchto (= state newState)
			(
				(mEyes hide:)
				(michelle
					view: 		vMMAnimation1,
					setCel:		0,
					cycleSpeed:	16,
					setLoop: 	lFingerIntoCream,
				)
				(= ticks 123)
			)
			(
				(michelle
					setCycle:	CycleTo 7 1 self
				)
			)
			(
				(= seconds 5)
			)
			(
				(michelle
					setCycle: 	EndLoop self,
				)
			)
			(
				(michelle
					view:			vFacialExpressions,
					setLoop: 	lBody,
					cycleSpeed:	10,
					stopUpd:		,
				)
				(= seconds 2)
			)
			(
				(Say MMtalker "\"Oh, Larry. Don't you just love stiff cream?\""
					#dispose:
					#caller:			self
				)
				(mEyes show:)
				(HandsOn)
				(theIconBar disable:	walkIcon)
				(self dispose:)
			)
		)
	)
);sCartoon

(instance sCherry of Script
	(method (changeState newState)
		(switchto (= state newState)
			(
				(mEyes hide:)
				(michelle
					view: 		vFacialExpressions,
					setLoop: 	lBody,
					stopUpd:		,
				)
				(mMouth
					show:			,
					setLoop:		5,
					setCel:		0,
					setCycle:	EndLoop self,
				)
			)
			(
				(soundFX
					number:		sGetsCherry,
					loop:			1,
					play:
				)
				(= ticks 30)
			)
			(
				(cherry
					setLoop:		7,
					setCycle:	EndLoop cherry
				)
				(mMouth
					setCycle:	BegLoop self
				)
			)
			(
				(cherry hide:)
				(mMouth hide:)
				(= seconds 2)
			)
			(
				(Bset fCherryGone)
				(Say ego "\"Well, there goes her cherry,\" you think." LOW)
				(mEyes show:)
				(HandsOn)
				(theIconBar disable:	walkIcon)
				(self dispose:)
			)
		)
	)
)

(instance sStartGoDown of Script
	(method (changeState newState)
		(switchto (= state newState)
			(
				(SetFFRoom 510)
				(Say MMtalker "\"Oooooh, Larry!\" says Michelle.
					\"I know a way we can `increase your interest!'\""
					#dispose:
					#caller:			self
				)
			)
			(
				(= seconds 2)
			)
			(
				(TPrint "(What does she mean by that, Larry?)" LOW)
				(= seconds 2)
			)
			(
				(Say MMtalker "\"You're a `software' kind of guy and
					I'm a `hardware' kind of girl,\" she says.
					\"How about if I `download onto your hard disk?'\""
					#dispose:
					#caller:		self
				)
			)
			(
				(= seconds 2)
			)
			(
			 	(Say MMtalker "\"Allow me to make our booth more private.\""
					#dispose:
					#caller:		self
				)
			)
			(
				(curRoom newRoom: 530)
			)
		)
	)
)

(instance sTrotter of Script
	(method (changeState newState)
		(switchto (= state newState)
			(
				(Say ego "\"Would you be interested in this?\" you ask.
					\"It's `my' personal organizer and contains a list of all
					`my' Swiss bank accounts, off-shore deposits, and
					international funds.\"")
				(Say MMtalker "\"Well, I don't know,\" she pretends to hesitate."
					#dispose:
					#caller:		self
				)
			)
			(
				(ego put: iDayTrotter)
				(Points ptsGaveMMdaytrotter pfGaveMMdaytrotter)
				(Say MMtalker "\"Why, yes,\" she says with a big smile,
					\"I'm sure I could `invest' these for you tomorrow!
					Trust me!\""
					#dispose:
					#caller:		self
				)
			)
			(
				(BranchIt)
			)
		)
	)
)

(instance sCreditCards of Script
	(method (changeState newState)
		(switchto (= state newState)
			(
				(Say ego "\"Would you be interested in this?\" you ask.
					\"These credit cards would surely be useful to you
					if you ever need to withdraw funds.
					You know, cash advances, things like that?\"")
				(ego put: iCreditCards)
				(Points ptsGaveMMcards pfGaveMMcards)
				(Say MMtalker "\"Oohh,\" she squeals,
					\"perhaps I haven't mentioned how much stock I put in
					these things!
					And what a smart rubber band you have around them!\""
					#dispose:
					#caller:		self
				)
			)
			(
				(BranchIt)
			)
		)
	)
)

(instance sMoney of Script
	(method (changeState newState)
		(switchto (= state newState)
			(
				(Say ego "\"I also have this little bit of cash on me,\"
					you say, offering it to her.
					\"Could you make some investments for me with this?\"")
				(ego put: iMoney)
				(Points ptsGaveMMcash pfGaveMMcash)
				(Say MMtalker "\"Oh, could I?!\" she cries.
					\"As my friend, Donald Tramp says, `cash is king!'\""
					#dispose:
					#caller:		self
				)
			)
			(
				(BranchIt)
			)
		)
	)
)

(instance sConversation of Script
	(method (changeState newState)
		(switchto (= state newState)
			(
				NULL
			)
			(										;try 1
				(Say ego "\"Did I mention my name?\" you ask.
					\"I didn't think so. My name is Larry; Larry Laffer.\"" LOW)
				(Say MMtalker "\"Good evening, Lawrence,\" she says,
					\"I'm Michelle Milken, program-trading specialist and
					sometime computer hobbyist.\""
					#dispose:
				)
			)


			(										;try 2
			 	(Say ego "\"I'm really into computers,\" you say,
		 			exposing wisdom beyond your years.
		 			\"In fact, I've written a couple of very successful
		 			computer games. At least, I think I have.\""
					LOW
			 	)
			 	(Say MMtalker "\"Oh, really?\" asks Michelle.
			 		\"Does that mean you have lots of money?\""
					#dispose:
					#caller:			self
				)
			)
			(
			 	(Say ego "\"I make a nice living, er, well, that is,
					I think I did!\" you say. \"But now I'm not so sure.
					You see, I've been having a little trouble lately with amnesia.
					So what is it that you do?\""
					LOW
			 	)
			 	(Say MMtalker "\"I laugh all the way to and from the bank!\"
					she says with a grin. \"I'm a junk bond dealer.\""
					#dispose:
				)
			)


			(										;try 3
				(Say ego "\"So, what does a junk bond dealer do?\" you ask."
					LOW
				)
				(Say MMtalker "\"Whatever she can,\" Michelle replies cryptically."
					#dispose:
					#caller:		self
				)
			)
			(
				(Say ego "\"I've been hearing lots of good things recently
					about junk bonds,\" you say.
					\"Would you recommend them as an integral part of my
					portfolio?\""
					LOW
				)
				(Say MMtalker "\"Junk bonds, eh?\" she warms perceptibly.
					\"Why I was just telling a friend about them today.
					Yes, I think they would be perfect for someone like you!\""
					#dispose:
				)
			)

			(										;try 4
				(Say ego "\"Really? I always thought investments like that were
					too risky for an ordinary guy like me!\""
					LOW
				)
				(Say MMtalker "\"Larry, no one I know would ever admit to
					thinking that you are in any way, shape, or form, `ordinary!'\""
					#dispose:
					#caller:		self
				)
			)
			(
				(Say MMtalker "\"So,
					do you have any cash-equivalent funds lying about,
					just gathering whatever conservative interest rate you're
					presently earning?\""
					#dispose:
					#caller:		self
				)
			)
			(
				(Say ego "\"Well, no, Michelle, I don't,\" you say."
					LOW
				)
			)


			(										;try 5
				(Say ego "\"Michelle, I just thought of something!\" you say.
					\"I might just have lots of royalties waiting for me
					from my last job.
					Of course, I can't quite remember if I do or not,
					but it is something to consider.\""
					LOW
				)
				(Say MMtalker "\"And consider it I do, Larry,\" Michelle replies.
					\"In fact, perhaps I could open your account right now.\""
					#dispose:
					#caller:		self
				)
			)
			(
				(Points ptsTalkedMMintoIt pfTalkedMMintoIt)
				(Say ego "\"Huh? Well, oh, I don't... Ohhh!\"
					you say as a big smile crosses your lips.
					\"Well, okay, Michelle, have it your way!\""
					LOW
				)
				(HandsOff)
				(curRoom setScript: sStartGoDown)
			)
		)
	)
)

;michelle's body parts:

(instance michelle of Actor
	(properties
		view			 	vRoom
		loop			 	0
		cel				0
		x				 	150
		y				 	105
		yStep				1
		sightAngle 	 	90
 		description  	"Michelle Milken"
		priority			3
		signal			(| fixedCel fixPriOn ignrAct)
		cycleSpeed		10
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbLook
				(TPrint "You can see why this woman made the finals back at
					good ol' PornProdCorp!
					She has to be one of the sexiest babes YOU'VE ever seen!")
				(TPrint "(But she sure has a funny way of eating ice cream!)" LOW)
			)
			(verbDo
				(TPrint "While you may wish to reach across the table and grab
					her, your professional attitude and loyalty to your employer
					prevent such blatant, crass behavior.")
			)
			(verbZipper
				(Say MMtalker 
					"\"Hmmm,\" says Michelle, with a coy smile,
					\"that's an idea!\""
					#dispose:
				)
			)
			(verbUse
				(switch invItem
					(iMoney
						(HandsOff)
						(Reset)
						(curRoom setScript: sMoney)
					)
					(iCreditCards
						(HandsOff)
						(Reset)
						(curRoom setScript: sCreditCards)
					)
					(iDayTrotter
						(HandsOff)
						(Reset)
						(curRoom setScript: sTrotter)
					)
					(iHDCNapkin
						(TPrint "Yeah, that ought to impress her, Larry.
							Give her a napkin that she could pick up off any
							table in the building!")
					)
					(else
						(Say MMtalker 
							"\"Oh, no thanks, Larry,\" responds Michelle.
							\"I have no idea what I'd do with that.\""
							#dispose:
						)
					)
				)
			)
			(verbTalk
				(= didSomething TRUE)
				(sConversation cue:)
			)
			(else
				(super doVerb: theVerb invItem &rest)
			)
		)
	)
)

(instance mEyes of Prop
	(properties
		view			 	vRoom
		loop			 	2
		x				 	153		;152
		y				 	65			;62
		sightAngle 	 	90
		description		"her eyes"
		lookStr			"Her sultry eyes burn into your soul."
		cycleSpeed		6
		priority			13
		signal			(| ignrAct fixPriOn)
	)
	(method (doVerb theVerb invItem)
		(michelle doVerb: theVerb invItem)
	)
)
(instance mMouth of Prop
	(properties
		view			 	vRoom
		loop			 	4
		x				 	153		;154		;151
		y				 	68			;65
		sightAngle 	 	90
		description		"her mouth"
		lookStr			"Her lips are moist and inviting."
		cycleSpeed		10
		priority			14
		signal			(| ignrAct fixPriOn)
	)
	(method (doVerb theVerb invItem)
		(michelle doVerb: theVerb invItem)
	)
)

;stuff

(instance iceCream of View
	(properties
		view			 	vRoom
		loop			 	6
		x				 	151
		y				 	126
		sightAngle 	 	90
		nsLeft			137
		nsTop				118
		nsBottom			142
		nsRight			165
		lookStr			"You love ice cream. Especially ice cream that's sitting
							where THAT ice cream is!"
 		description  	"the ice cream"
		signal			ignrAct
	)
)
(instance cherry of Prop
	(properties
		view			 	vRoom
		loop			 	9
		cel				1
 		x				 	148
		y				 	101
		sightAngle 	 	90
		description		"her cherry"
		lookStr			"Her cherry looks soft, red, ripe and ready for the
							picking!"
		priority			14
		signal			(| ignrAct fixPriOn)
	)
	(method (cue)
		(super cue:)
		(self dispose:)
	)
)
(instance tits of Feature
	(properties
		x					151
		y					130
		z					0
		heading			0
		nsLeft			130
		nsTop				107
		nsBottom			127
		nsRight			172
 		description		"her breasts"
		sightAngle		40
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbLook
				(Say MMtalker 
					"\"You really enjoy staring at me there,
					don't you, Larry?\""
					#dispose:
				)
			)
			(verbDo
				(TPrint "\"There's plenty of time for that later, Larry!\"")
			)
			(else
				(michelle doVerb: theVerb invItem)
			)
		)
	)
)

(instance face of Feature
	(properties
		x					155
		y					130
		z					0
		heading			0
		nsLeft			132
		nsTop				49
		nsBottom			93
		nsRight			179
 		description		"her face"
		sightAngle		40
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbLook
				(TPrint "This is a face you could dream about, Larry!")
			)
			(verbDo
				(Say MMtalker
					"\"I like it when you stroke my face, Larry!\""
					#dispose:
				)
			)
			(else
				(michelle doVerb: theVerb invItem)
			)
		)
	)
)

;ษอออออออออออออออออออป
;บ                   บ
;บ Talkers           บ
;บ                   บ
;ศอออออออออออออออออออผ
(instance MMtalker of Talker
	(properties
		name			"Michelle Milken"
		view			1535
		loop			1
		nsLeft		142
		nsTop			58
		x				8
		y				5
		talkWidth	300
	)
	(method (init)
 		(= eyes 	MMEyes)
		(= mouth MMMouth)
		(super init: &rest)
	)
)

(instance MMEyes of Prop
	(properties
		view			1535
		loop			2
		nsLeft		1
		nsTop			0
		signal 		ignrAct
		cycleSpeed	25
	)
)

(instance MMMouth of Prop
	(properties
		view			1535
		loop			0
		nsLeft		7
		nsTop			10
		signal 		ignrAct
	)
)


;ษอออออออออออออออออออป
;บ                   บ
;บ Procedures			บ
;บ                   บ
;ศอออออออออออออออออออผ

(procedure (BranchIt)
	(Reset)
 	(mEyes hide:)
	(switch (++ giveItems)
		(1
			(if
				(or
					(ego has: iMoney)
					(ego has: iCreditCards)
					(ego has: iDayTrotter)
				)
				(curRoom setScript: sSuckFinger)
			else
				(curRoom setScript: sStartGoDown)
			)
		)
		(2
			(if
				(or
					(ego has: iMoney)
					(ego has: iCreditCards)
					(ego has: iDayTrotter)
				)
				(curRoom setScript: sCherry)
			else
				(curRoom setScript: sStartGoDown)
			)
		)
		(3
			(curRoom setScript: sStartGoDown)
		)
	)
)

(procedure (Reset)
	(= didSomething TRUE)
	(= loserCounter 0)
)


;ษอออออออออออออออออออป
;บ                   บ
;บ Sounds				บ
;บ                   บ
;ศอออออออออออออออออออผ


(instance soundFX of Sound
	(properties
		flags			1
		loop			-1
	)
)
