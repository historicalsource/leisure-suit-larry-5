;**																								  9
;**	Logics for Room 150 -- Scruemall gets scalded by Larry and hires Larry
;**
;**	LSL5:  Passionate Patti Does A Little Undercover Work
;**				Copyright 1991
;**				by Al Lowe
;**
;**	Programmed by W. John Hartin
;**
;**	Last Update:  September 10, 1991
;**

(script#		150)

(public
	rm150		0
)


(define	pScruemallScalded				145)
(define	vScruemallScreams			145)
	(define	lYells					0)
	(define	lWaitAminuteTop		1)
	(define	lLightBulb				2)
	(define	lWaitAminuteBottom		3)
(define	vLarryCracksPot			146)
	(define	lScared				0)
	(define	lPotBreaking		1)
;	(define	lPotBroken			2)
(define	vCamCorder			150)

(define	pScruemallLarry2shot			150)
;(define	vScruemall						150)
;	(define	lTalking				0)
(define	vLarryW/Scruemall			1151)
;	(define	lTalking				0)
(define	pPassWordRoom			155)
(define	pPornProdCorpLobby			160)
(define sBreakPot	146)
(define sYeow!	147)
(define		sIdea					148)
(define mPlan	150)
(define mPornProdOffice	140)
(define vSmallScruemallTalker		1145)

(define	vScruemallIntroTalker	1150)
(define	vScruemallTalker			1215)
;(define	vRoom							120)
;(define	vRoomEgo			 (+ 1 vRoom))
(define  tTalkLoop 3)
(define  tBustLoop 1)
(define  tEyesLoop 2)
(define  tMouthLoop 0)
(define	LARRYLEFT	64)
(define	LARRYTOP		88)
(define	LARRYX	0)
(define	LARRYY		0)
(define	ScruemallLEFT	 154)
(define	ScruemallTOP	 21)
(define	ScruemallX	-1)
(define	ScruemallY		185)
(define	BOSSLEFT	167)
(define	BOSSTOP		27)
(define	BOSSX	-1)	;40)
(define	BOSSY		185)

;**	RoomScript state values

(local
	seenMsg
)


(instance rm150 of LLRoom
	(properties
		picture		pScruemallScalded
	)

	(method (init)
		(LoadMany VIEW
			vScruemallScreams
			vLarryCracksPot
			vLarryW/Scruemall
			vSmallScruemallTalker
			vScruemallIntroTalker
			vScruemallTalker
		)
		(LoadMany SOUND
			mPornProdOffice
			mPlan
			sBreakPot
			sYeow!
		)
		(HandsOff)
		(fScruemall init:)
		(pArm init:)
		(pLarry init:)
		(super init:)
		(SetFFRoom pPassWordRoom)
;wjh		(theMusic
;wjh			number:		mPornProdOffice,
;wjh			flags:		TRUE,
;wjh			setLoop:		-1,
;wjh			play:
;wjh		)

		(self setScript: sCartoon)
	)
	(method (dispose)
		(super dispose: &rest)
		(theMusic
			fade:	0 15 12 TRUE
		)
	)
)

(instance fScruemall of Prop
	(properties
		view			vScruemallScreams
		loop			lYells
		x				153
		y				139
		priority		10
		cycleSpeed	6
		signal		fixPriOn
	)
)

(instance fScruemallTopHalf of Prop
	(properties
		view			vScruemallScreams
		loop			lWaitAminuteTop
		x				179
		y				95
		priority		10
		cycleSpeed	6
		signal		fixPriOn
	)
)

(instance pLarry of Prop
	(properties
		view			vLarryCracksPot
		loop			lScared
		x				96
		y				107
		priority		9
		cycleSpeed	12
		signal		fixPriOn
	)
)

(instance pArm of Prop
	(properties
		view			vLarryCracksPot
		loop			lPotBreaking
		cel			0
		x				96
		y				109
		priority		15
		cycleSpeed	12
		signal		fixPriOn
	)
)

(instance light of Prop
	(properties
		view			vScruemallScreams
		loop			lLightBulb
		cel			0
		x				200
		y				26
		priority		15
		signal		fixPriOn
	)
)

(instance larryCam of View
	(properties
		view			vCamCorder
		loop			0
		cel			0
		x				147
		y				170
	)
)

;;***************************************************
;*
;*		TALKERS
;*
;;***************************************************

(instance larryTalker of Talker
	(properties
		name			"You"
		view			vLarryW/Scruemall
		loop			tTalkLoop
		nsLeft		LARRYLEFT
		nsTop			LARRYTOP
		x				LARRYX
		y				LARRYY
		talkWidth	314
	)
	(method (init)
		(= mouth larryMouth)
		(super init: &rest)
	)
)

(instance larryMouth of Prop
	(properties
		name			"You"
		view			vLarryW/Scruemall
		loop			tMouthLoop
		nsLeft		0
		nsTop			0
		cycleSpeed 	5
	)
)

(instance silasTalker of Talker
	(properties
		name			"Silas Scruemall"
		view			vScruemallIntroTalker
		loop			tTalkLoop
		nsLeft		ScruemallLEFT
		nsTop			ScruemallTOP
		x				ScruemallX
		y				ScruemallY
		talkWidth	240
	)
	(method (init)
		(= mouth bossmallMouth)
		(= bust bossmallBust)
		(= eyes bossmallEyes)
		(super init: &rest)
	)
)



(instance bossmallBust of Prop
	(properties
		view		vScruemallIntroTalker
		loop		tBustLoop
		nsLeft	0
		nsTop		0
	)
)

(instance bossmallEyes of Prop
	(properties
		view			vScruemallIntroTalker
		loop			tEyesLoop
		nsLeft		16
		nsTop			31
		cycleSpeed 	70
	)
)

(instance bossmallMouth of Prop
	(properties
		name			"Silas Scruemall"
		view			vScruemallIntroTalker
		loop			tMouthLoop
		nsLeft		6
		nsTop			55
		cycleSpeed 	5
	)
)

(instance ScruemallTalker of Talker
	(properties
		name			"Silas Scruemall"
		view			vSmallScruemallTalker
		loop			tTalkLoop
		nsLeft		BOSSLEFT
		nsTop			BOSSTOP
		x				BOSSX
		y				BOSSY
		talkWidth	280
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
		view		vSmallScruemallTalker
		loop		tBustLoop
		nsLeft	0
		nsTop		0
	)
)

(instance ScruemallEyes of Prop
	(properties
		view			vSmallScruemallTalker
		loop			tEyesLoop
		nsLeft		10
		nsTop			15
		cycleSpeed 	70
	)
)

(instance ScruemallMouth of Prop
	(properties
		view			vSmallScruemallTalker
		loop			tMouthLoop
		nsLeft		5
		nsTop			0
		cycleSpeed 	5
	)
)
(instance breakPot of Sound
	(properties
		number	sBreakPot
		flags: TRUE
	)
)

(instance yeow of Sound
	(properties
		number	sYeow!
		flags: TRUE
	)
)

(instance plink of Sound
	(properties
		number	sIdea
		flags: TRUE
	)
)

;;**************************************************************
;*
;*		SCRIPTS
;*
;;**************************************************************

(instance sCartoon of Script
	(method (changeState newState)
		(switchto (= state newState)
			(
				(= seconds 3)
			)
			(
				(fScruemall setCycle: CycleTo 1 FORWARD self)
			)
			(
				(pLarry setCycle: EndLoop)
				(pArm setCycle: CycleTo 1 FORWARD self)
				(fScruemall setCycle: CycleTo 2 FORWARD)
			)
			(
				(breakPot play:)
				(pArm setCel: 2, setCycle: EndLoop)
				(fScruemall setCel: 3, setCycle: EndLoop self)
			)
			(
				(yeow play:)
				(TPrint "\"Yooooooowwwwwww!!!!!!!!\"" LOW)
				(= seconds 3)
			)
			(
				(fScruemall
					setCel: 0,
					setLoop: lWaitAminuteBottom,
					stopUpd:
				)
				(fScruemallTopHalf
					init: ,
					stopUpd: ,
					;setCycle: EndLoop self
				)
				(= ticks 10)
			)
			(
				(Say ScruemallTalker "\"You clumsy idiot!  What a dolt!\""
			  		#dispose:
					#caller:		self
				)
			)
			(
				(= ticks 123)
			)
			(
				(Say ScruemallTalker "\"Who hired this fool?
					Sexy women?
					Why even the sexiest woman in America wouldn't...\""
			  		#dispose:
					#caller:		self
				)
			)
			(
				(= ticks 123)
			)
			(
				(fScruemallTopHalf
					startUpd:,
					cycleSpeed:	12,
					setCycle: EndLoop self
				)
				;(pLarry setCel: 0)
				(plink play:)
				(light init:, cycleSpeed: 24, setCycle: EndLoop)
			)
			(
				(= ticks 60)
			)
			(
				(TPrint "\"Hey, wait a minute!
					Wait just a minute....\""
					#title: {Silas Scruemall}
					LOW
			  		#dispose: self
				)
			)
			(
				(= ticks 123)
			)
			(
				(pLarry
					dispose:
				)
				(fScruemall
					dispose:
				)
				(fScruemallTopHalf
					dispose:
				)
				(pArm dispose:)
				(light dispose:)
				(curRoom
					style:		IRISOUT,
					drawPic:		pScruemallLarry2shot,
					picture:		pScruemallLarry2shot,
					curPic:		pScruemallLarry2shot,
				)
				(theMusic
					number:		mPlan,
					flags:		TRUE,
					setLoop:		-1,
					play:
				)

 				(Say silasTalker "\"Mmmm,\" says Silas, thinking fast and furiously.
					\"Uh, say buddy, er, nice leisure suit.\""
			  		#dispose:
					#caller:		self
				)
			)
			(
				(= ticks 30)
			)
			(
				(Say silasTalker "\"I'm sure I know you, uh, but what exactly is
					your name?\""
			  		#dispose:
					#caller:		self
				)
			)
			(
				(= ticks 123)
			)
			(
				(Say larryTalker "\"Why, my name is Larry; Larry Laffer,\" you reply."
			  		#dispose:
					#caller:		self
				)
			)
			(
				(= ticks 123)
			)
			(
				(Say silasTalker "\"How quaint,\" he grumbles,
					\"your first and middle names are exactly the same.
					Well, never mind about that.
					I have a proposal I'd like to make to you, Daffer.
					Now, here's the deal.\""
			  		#dispose:
					#caller:		self
				)
			)
			(
				(= ticks 30)
			)
			(
				(Say silasTalker "\"You see, Luffner,
					I've got to find a hostess for our new television show.\""
			  		#dispose:
					#caller:		self
				)
			)
			(
				(= ticks 123)
			)
			(
				(Say larryTalker "\"Yes, I know,\" you offer proudly,
					\"I'm the Chief Tape Rewinder and Sterilizer
					on this project!\""
			  		#dispose:
					#caller:		self
				)
			)
			(
				(= ticks 30)
			)
			(
				(TPrint "(You conveniently neglect to mention the
					\"Betamax Division\" portion of your job description!)" LOW)
				(= seconds 3)
			)
			(
				(Say silasTalker "\"And a fine job you're doing (I suppose).
					Well, Larcher, we've used those audition tapes to
					narrow the field down to three finalists,
					but we're, uh, um,\" he stumbles,
					\"too ah, busy to hold their auditions here!\""
			  		#dispose:
					#caller:		self
				)
			)
			(
				(= ticks 30)
			)
			(
				(Say silasTalker "\"So I want you to visit them in their,
					ah, `home environments;' see them in their `natural habitat,'
					so to speak; catch them `au naturale' if you can.\""
			  		#dispose:
					#caller:		self
				)
			)
			(
				(= ticks 30)
			)
			(
				(TPrint "(You understand many of the words in that last
					sentence.)" LOW)
				(= seconds 3)
			)
			(
				(Say silasTalker "\"You do know how to audition girls,
					don't you, Loafer?\""
			  		#dispose:
					#caller:		self
				)
			)
			(
				(= ticks 123)
			)
			(
				(Say larryTalker "\"What? Audition three women?
					No problem, boss, I'm your guy!\""
			  		#dispose:
					#caller:		self
				)
			)
			(
				(= ticks 30)
			)
			(
				(TPrint "Why, it's like a dream come true!
					In fact, it's like several of your dreams come true!
					When those babes find out why you're visiting,
					they'll fall all over you!
					Not that you mind, of course.
					Anything for your company.")
				(= seconds 3)
			)
			(
				(Say silasTalker "\"But, Loaner,
					if you tell them who you are,
					or of your powerful position here at PPC,
					it could easily invalidate the data.
					We want to see their reactions to a nice,
					normal guy like you, Lenny.\""
			  		#dispose:
					#caller:		self
				)
			)
			(
				(= ticks 30)
			)
			(
				(Say silasTalker "\"I must be certain you don't
					`take advantage' of the situation.
					So, take this.\""
			  		#dispose:
					#caller:		self
				)
			)
			(
				(larryCam init:)
				(Points ptsGotCamcorder pfGotCamcorder)
				(= ticks 123)
			)
			(
				(Say larryTalker "\"Wow, Mr. Scruemall,\" you say,
					unable to contain your excitement,
					\"I can always use another pocket protector.
					Mine's getting really vintage!\""
			  		#dispose:
					#caller:		self
				)
			)
			(
				(= seconds 3)
			)
			(
				(Say silasTalker "\"Lipper, you fool!
					This is no ordinary pocket protector!
					It's a brand new, imported, miniaturized,
					.5 lux, Rob Lowe Model 'Complete Video Studio'
					merely disguised as some dork's Pocket Protector!\""
			  		#dispose:
					#caller:		self
				)
			)
			(
				(= ticks 123)
			)
			(
				(larryTalker talkWidth: 160)
				(Say larryTalker "\"I knew that,\" you pretend."
			  		#dispose:
					#caller:		self
				)
			)
			(
				(= seconds 3)
			)
			(
				(Say silasTalker "\"You must never let a `client'
					suspect you are filming them or that you represent
					the show!
					They must each think of you only as plain
					Larry, Larry Limper!\""
			  		#dispose:
					#caller:		self
				)
			)
			(
				(= ticks 123)
			)
			(
				(larryCam dispose:)
				(larryTalker talkWidth: 240)
				(Say larryTalker "\"I can handle it, Sir,\" you say excitedly."
			  		#dispose:
					#caller:		self
				)
			)
			(
				(= seconds 3)
			)
			(
				(Say silasTalker "\"I hope so.
					If you can pull this off, Lipper,
					I promise you a really big promotion:
					perhaps even to Assistant Associate Director!
					What do you think of that?"
			  		#dispose:
					#caller:		self
				)
			)
			(
				(= ticks 30)
			)
			(
				(TPrint "You think, \"Wow!
					This could be the big break I've been hoping for!\"" HIGH)
				(larryTalker talkWidth: 240)
				(Say larryTalker "\"I promise to give it everything I've got!\""
					#dispose:
					#caller:		self
				)
			)
			(
				(= ticks 123)
			)
			(
				(Say silasTalker "\"Good.
					I really want you to take charge, Larry.
					Take whatever you need from here in the office;
					you're really on your own.
					I'll look forward to personally viewing those audition tapes
					when you return.\""
			  		#dispose:
					#caller:		self
				)
			)
			(
				(= ticks 30)
			)
			(
				(Say silasTalker "\"But remember: because this little guy is
					such a new model and because it's so tiny,
					its recording time and battery life are severely limited!
					Be sure you recharge after every girl!\""
			  		#dispose:
					#caller:		self
				)
			)
			(
				(= ticks 123)
			)
			(
				(TPrint "\"Heh, heh.
					Always a good idea,\" you think.\""
					HIGH
				)
				(Say silasTalker "\"Okay, Wopner, you've got one,
					maybe two days max!
					You'd better grab the girls' bios and get going!\""
			  		#dispose:
					#caller:		self
				)
				(Bset fBeenIn150)
			)
			(
				(= ticks 123)
			)
			(
				;**	fade to black here
				(DrawPic pBlackPic (+ IRISIN BLACKOUT))
				(= seconds 3)
			)
			(
				(TPrint "What a dream assignment!
					Travel across the United States,
					visiting three of the sexiest women in America,
					and you get paid for doing it!!
					Larry, it looks like things are really beginning to
					go your way.")
				(= seconds 3)
			)
			(
				(TPrint "(Of course, something makes you think you've
					had that thought before!)" LOW)
				(curRoom newRoom: pPassWordRoom)
			)
		)
	)
)
