;**
;**	Logics for Room 405 -- Club owner's office
;**
;**	LSL5:  Passionate Patti Does A Little Undercover Work
;**				Copyright 1991
;**				by Al Lowe
;**
;**	Programmed by John Hartin
;**
;**	Last Update:  September 9, 1991
;**

(script#		405)

(public
	rm405		0
)


(define	PATTI_PRINT			#at 15 15 #width 160)

(define	vRoom							405)
;(define	vRoomEgo			 (+ 1 vRoom))
(define	pClubOwnersOffice			405)
(define	vPattiInOffice				406)
	(define	lSitsOnDesk				0)
	(define	lWalkingEast			1)
	(define	lWalkingWest			2)
(define	vClubOwner 407)
	(define	lSmoking				0)
;	(define	lTalking				1)
(define 	vClubOwnerTalker	1407)
(define	pPianoPit						400)
(define	mStiffed							405)

(define  tTalkLoop 3)
(define  tBustLoop 1)
(define  tEyesLoop 2)
(define  tMouthLoop 0)
(define	OWNERLEFT	10)
(define	OWNERTOP		13)
(define	OWNERX		140)
(define	OWNERY		33)

(local
	seenMsg
)


(instance rm405 of LLRoom
	(properties
		picture		pClubOwnersOffice
		west			pPianoPit
	)

	(method (init)
		(LoadMany VIEW
			vRoom
			vPattiInOffice
			vClubOwner
		 	vClubOwnerTalker
		)
		(HandsOff)
		(ego 	init:, x: 71, y: 121, view:vPattiInOffice)
		(clubOwner init:, setCycle: Forward)

		(ego
			edgeHit:		0,
			setLoop: lWalkingEast
		)

		(ownersDoor	init:, setCel: LASTCEL)
		(theMusic
			number:		mStiffed,
			flags:		TRUE,
			setLoop:		-1,
			play:
		)
		(super init:)
		(self setScript: sCartoon)
	)

;wjh	(method (cue)
;wjh		(if (== (ownersDoor state?) doorClosed)
;wjh			(HandsOff)
;wjh			(self setScript: sCartoon)
;wjh		)
;wjh	)

	(method (dispose)
		(theMusic
			fade:	0 15 12 TRUE self
		)
		(super dispose:)
	)
)

(instance ownersDoor of Prop
	(properties
		view	vRoom
		loop	0
		x		94
		y		114
;wjh		entranceTo	pPianoPit
;wjh		exitType		doorWalkEgo
;wjh		enterType	doorWalkEgo
 		description "the door"
		approachX	109
		approachY	121
;		moveToX		71
;		moveToY		121
		signal		ignrAct
	)
)

(instance clubOwner of Prop
	(properties
		view			vClubOwner
		loop			lSmoking
		cel			0
		x				218
		y				140
	)
)




;ษอออออออออออออออออออป
;บ                   บ
;บ Talkers           บ
;บ                   บ
;ศอออออออออออออออออออผ

(instance ownerTalker of Talker
	(properties
		name			"Andy"
		view			vClubOwnerTalker
		loop			tTalkLoop
		nsLeft		OWNERLEFT
		nsTop			OWNERTOP
		viewInPrint	TRUE
	)
	(method (init)
		(= eyes ownerEyes)
		(= bust ownerBust)
		(= mouth ownerMouth)
		(super init: &rest)
	)
)

(instance ownerBust of Prop
	(properties
		view		vClubOwnerTalker
		loop		tBustLoop
		nsLeft	0
		nsTop		0
	)
)

(instance ownerEyes of Prop
	(properties
		view		vClubOwnerTalker
		loop		tEyesLoop
		nsLeft	31
		nsTop		9
		cycleSpeed 	70
	)
)

(instance ownerMouth of Prop
	(properties
		view			vClubOwnerTalker
		loop			tMouthLoop
		nsLeft		36
		nsTop			15
		cycleSpeed 	5
	)
)



;******************************************
; Scripts
;******************************************

(instance sCartoon of Script
	(method (changeState newState)
		(switchto (= state newState)
			(
				(ego setMotion: MoveTo 109 121 self)
			)
			(
				(ownersDoor setCycle: BegLoop)
				((Sound new:) number: sDoorClose, play:)
				(ego setMotion: MoveTo 165 137 self, setPri: 15)
			)
			(
				(ego
					setLoop:		lSitsOnDesk,
					setCycle:	EndLoop self
				)
			)
			(
				(clubOwner
					setCycle:	0,
					setCel:		0,		;stop smoking, start talking
					stopUpd:
				)
				(Say ego "\"Well, Andy,\" you say to the swarthy club manager,
					\"that about wraps it up for me.
					Looks like you've had another good week!\""
					PATTI_PRINT)
				(Say ownerTalker "\"Funny you'd mention that, Patti.\""
					#dispose:
					#caller:	self
				)
			)
			(
				(= ticks 30)
			)
			(
				(Say ownerTalker "\"I was just getting ready to talk to you.\""
					#dispose:
					#caller:	self
				)
			)
			(
				(= ticks 123)
			)
			(
				(Say ego "\"You DID enjoy the show, didn't you, Andy?\"
					you ask with your best smile."
					PATTI_PRINT)
				(= seconds 3)
			)
			(
				(Say ownerTalker "\"Oh, Patti, it ain't me.
					You know I like you just fine.
					It's just my boss that don't.\""
					#dispose:
					#caller:	self
				)
			)
			(
				(= ticks 60)
			)
			(
				(Say ownerTalker "\"Julius doesn't much care for small audiences.\""
					#dispose:
					#caller:	self
				)
			)
			(
				(= ticks 60)
			)
			(
				(Say ownerTalker "\"He says your fans listen too much and drink too little.\""
					#dispose:
					#caller:	self
				)
			)
			(
				(= ticks 60)
			)
			(
				(TPrint "(How ironic! You were thinking just the opposite!)"
					LOW)
				(Say ownerTalker "\"He can't see how he and his 'backers' can be
					expected to make a profit like that.\""
					#dispose:
					#caller:	self
				)
			)
			(
				(= ticks 30)
			)
			(
				(Say ownerTalker "\"Patti, I'm just going to have to let you go.\""
					#dispose:
					#caller:	self
				)
			)
			(
				(= ticks 123)
			)
			(
				(Say ego "\"But, Andy! How could you?  After all I've done
					for you?\""
					PATTI_PRINT)
				(Say ownerTalker "\"It's not me, Honey,\" he says,
					\"you treated me jus' fine.
					I'll be sorry to see you go.\""
					#dispose:
					#caller:	self
				)
			)
			(
				(= ticks 123)
			)
			(
				(Say ego "\"Well, then, give me my pay,
					and I'll get out of here!\""
					PATTI_PRINT)
				(Say ownerTalker "\"Ah, er,\" he stutters,
					\"I guess that's the other thing:
					I can't do that.\""
					#dispose:
					#caller:	self
				)
			)
			(
				(= ticks 60)
			)
			(
				(Say ownerTalker "\"Julius says your contract was never valid,
					so he doesn't have to pay.\""
					#dispose:
					#caller:	self
				)
			)
			(
				(= ticks 123)
			)
			(
				(Say ego "\"WHAT!\" you explode,
					\"Now you're saying I don't even get paid for the week
					I have worked?!
					I ought to file a grievance with the Musicians' Union!\""
					PATTI_PRINT)
				(Say ownerTalker "Not exactly shaking, he replies,
					\"Do what you have to do, Patti, but if you ever want to work
					anywhere in this town again...\""
					#dispose:
					#caller:	self
				)
			)
			(
				(= ticks 60)
			)
			(
				(Say ego "His meaning is not lost on you.
					\"Goodbye, Andy! Somehow I expected more of you.
					I don't know why!\""
					PATTI_PRINT)
				(Say ownerTalker "\"Patti, take my advice:
					play more commercial.
					Give 'em what they like!
					A broad as good lookin' as you could really pull 'em in,
					if you'd just get smart!\""
					#dispose:
					#caller:	self
				)
			)
			(
				(= ticks 123)
			)
			(
				(ego
					cel: 4,						;last cel in view
					setCycle:	BegLoop self
				)
				(clubOwner
					startUpd:	,
					setCycle: 	Forward,
				)
			)
			(
				(Say ego "\"Andy, when I start taking musical criticism from you,
					I'd appreciate it if someone would just put me
					out of my misery!\" you say, leaving his grimy desk."
					PATTI_PRINT)
				(ego
					setCycle: Forward,
					setLoop: lWalkingWest,
					setMotion:	MoveTo 109 121 self,
				)
			)
			(
				(ego setCycle: 0)
				(ego setPri: RELEASE)
				(= cycles 1)
			)
			(
				(ownersDoor setCycle: EndLoop self)
				((Sound new:) number: sDoorOpen, play:)
			)
			(
				(ego
					setCycle: Forward,
					setPri:		RELEASE,
					setMotion:	MoveTo 71 121 self,
				)
			)
			(
				(curRoom newRoom: (curRoom west?))
			)
		)
	)
);sCartoon
