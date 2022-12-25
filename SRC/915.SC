;**
;**	Logics for Room 915 -- Chi Chi closeUp
;**
;**	LSL5:  Passionate Patti Does A Little Undercover Work
;**				Copyright 1991
;**				by Al Lowe
;**
;**	Almost made to work by C. Escobar
;**
;**	Last Update:  September 11, 1991
;**


(script#		915)

(public
	rm915		0
)

(define	vChiChiTalker2		1915)

(define 	vChiChiCloseUp		915)
	(define lSmile 2)
	(define lFrown 3)

(define 	vChiChiButton		916)
(define  mSalsa				910)

(local
	blouseOpen
	oldTime			;scratchVar
	closeUpSecs
	talkCounter
	buttonCounter
)


(instance rm915 of LLRoom
	(properties
		picture		915
	)

	(method (init)
		(Load VIEW vChiChiTalker2)		;1915
		(Load VIEW vChiChiCloseUp)		;915
		(Load VIEW vChiChiButton)		;916
		(super init:)
		(chichis   init:)
		(herEye	  init:)
		(herMouth  init:, hide)
		(herButton init:)
		(boobs 	  init:, hide:)
		(HandsOn)
		(SetupExit TRUE)
		(User canControl: FALSE)
		(= closeUpSecs 30)
		(if (!= prevRoomNum 910)
			(theMusic
				number:		mSalsa,						;910
				loop:			FOREVER,						;-1
				play:
			)
			(ego get: iGreenCard)
		)
	)

	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbLook
				(switch (Random 1 2)
					(1
						(TPrint "Wow!
							You can now readily understand how she got that name!
							Take a look at those Chi Chi's!")
					)
					(2
						(TPrint "Wow, what a babe!
							Chi Chi Lambada is the perfect name
							for this Latin beauty!")
					)
				)
			)
			(verbUse
				(switch invItem
					(iGreenCard
						(HandsOff)
						(Points ptsUsedGreenCard)
						(curRoom setScript: sGiveGreen)
					)
					(else
						(TPrint "She's not interested in that, Larry.
							Talk to her; use your charm.")
					)
				)
			)
			(verbTalk
				(HandsOff)
				(curRoom setScript sTalk)
			)
			(verbZipper
				(switch (Random 1 2)
					(1
						(Say theTalker "\"Don't rush me, Big Guy!\" says Chi Chi.
							\"You've still got a little plaque on those bicuspids.\""
							#dispose
						)
					)
					(2
						(Say theTalker "\"Perhaps... if you'll just hold still
							a little while.\""
							#dispose
						)
					)
				)
			)
			(verbDo
				(TPrint "Wow!
					You can now readily understand how she got that name!
					Take a look at those Chi Chi's!")
			)
			(verbWalk
				(HandsOff)
				(Say ego "\"I can't take any more of this, Ms. Lambada,\"
					you cry, \"I hate the taste of latex gloves!\""
				)
				(theMusic fade)
				(Bset fSeenCC)				;force a test
				(curRoom newRoom 905)
			)
			(else
				(super doVerb: theVerb invItem &rest)
			)
		)
	)

	(method (doit)
		(super doit:)
		(if (!= oldTime (GetTime SYSTIME1))
			(= oldTime (GetTime SYSTIME1))
			(-- closeUpSecs)
			(if (not closeUpSecs)
				(HandsOff)
				(curRoom setScript sBackToWork)
			)
		)
	)
)

(instance sTalk of Script
	(enum 1
		TALKED1TIME
		TALKED2TIMES
		TALKED3TIMES
		TALKED4TIMES
		TALKED5TIMES
	)

	(define TALK 		10)
	(define ALL_DONE	30)

	(method (changeState newState)
		(switchto (= state newState)
			(
				(HandsOff)
				(= closeUpSecs 1000)
				(= cycles 2)
			)
			(
				(switch (++ talkCounter)
					(TALKED1TIME
						(Say ego "\"Oh, Chi Chi,\" you say,
							\"has anyone ever told you you're beautiful?\""
							#dispose: self
						)
					)
					(TALKED2TIMES
						(Say ego "\"Does the name `Bucky Beaver' mean anything
							to you?\" you ask Chi Chi."
							#dispose: self
						)
					)
					(TALKED3TIMES
						(Say theTalker "\"Oh, I do hope illegal actions don't
							bother you, Larry!\" says Chi Chi."
							#dispose:
							#caller: self
						)
					)
					(TALKED4TIMES
						(TPrint "Waiting until your mouth is full of equipment,
							Chi Chi asks you a question."
							LOW
							#dispose: self
						)
					)
					(TALKED5TIMES
						(Say theTalker "\"I hope it doesn't bother you if I
							occasionally rub up against your elbow... like this.
							Sometimes when I hear that salsa music,
							I get so excited I just can't control myself!\""
							#dispose:
							#caller: self
						)
					)
				)
			)
			(
				(= ticks 30)
			)
			(
				(switch talkCounter
					(TALKED1TIME
						(Say theTalker "\"Yes.\""
							#dispose
						)
						(= ticks 123)
					)
					(TALKED2TIMES
						(Say theTalker "\"Well, I suppose I've heard worse!\"
							she responds."
							#dispose
						)
						(= ticks 123)
					)
					(TALKED3TIMES
						(Say ego "\"Well, I dunno,\" you respond."
							#dispose: self
						)
					)
					(TALKED4TIMES
						(Say theTalker "\"Have you ever considered dental floss
							an aphrodisiac?\""
							#dispose
						)
						(= ticks 123)
					)
					(TALKED5TIMES
						(Say ego "\"I know what you mean,\" you say."
							#dispose: self
						)
					)
				)
			)
			(
				(switch talkCounter
					(TALKED1TIME
						(TPrint "(You're going to have to do better than that line,
							Larry!)" LOW
							#dispose: self
						)
					)
					(TALKED2TIMES
						(HandsOn)
						(User canControl: FALSE)
						(= closeUpSecs 30)
					)
					(TALKED3TIMES
						(Say theTalker "\"Oh, nothing much, I suppose.
							I really meant to jump in on that
							amnesty deal a few years ago,
							but I just forgot, I guess.\""
							#dispose
						)
						(= ticks 123)
					)
					(TALKED4TIMES
						(Say ego "\"Owefggh wfqqafd,\" you reply."
							#dispose: self
						)
					)
					(TALKED5TIMES
						(Bset fScoredCC)
						(Points ptsTalkedCCIntoIt)
						(curRoom newRoom 910)
					)
				)
			)
			(
				(switch talkCounter
					(TALKED1TIME
			 			(HandsOn)
						(User canControl: FALSE)
						(= closeUpSecs 30)
					)
					(TALKED3TIMES
						(Say ego "\"Amnesty? Chi Chi?\" you interject.
							\"Are you an `undocumented worker?'\""
							#dispose: self
						)
					)
					(TALKED4TIMES
				  		(HandsOn)
						(User canControl: FALSE)
						(= closeUpSecs 30)
					)
				)
			)
			(
				(Say theTalker "\"Yes, I guess I am,\" she replies.
					\"Although I'd do anything for the man who could help
					me become a citizen.\""
					#dispose:
					#caller:		self
				)
			)
			(
				(= ticks 60)
			)
			(
				(Say theTalker "\"Say,\" she pauses and looks deep into your eyes,
					\"you wouldn't have any ideas about this, would you?\""
					#dispose:
					#caller:		self
				)
			)
			(
				(= ticks 60)
			)
			(
				(Say ego "\"Uh, no, I can't think of anything right now,\"
					you say with a grin.
					\"But if something pops up, I feel certain I'll think of you!\""
					#dispose: self
				)
			)
			(
				(HandsOn)
				(User canControl: FALSE)
				(= closeUpSecs 30)
			)
		)
	)
);sCloseUp

(instance chichis of Feature
	(properties
		x 					163
		y 					255
		z 					100
		heading 			0
		nsLeft 			113
		nsTop 			137
		nsBottom 		173
		nsRight 			213
 		description 	"her chi chis"
		sightAngle 		40
	)
	(method (doVerb)
		(curRoom doVerb &rest)
	)
)
(instance herButton of Feature
	(properties
		x 					158
		y 					338
		z 					200
		heading 			0
		nsLeft 			149
		nsTop 			131
		nsBottom 		146
		nsRight 			167
 		description 	"her button"
		sightAngle 		40
	)
	(method (doVerb theVerb)
		(cond
			((== theVerb verbDo)
				(if (not blouseOpen)
					(if ( < (++ buttonCounter) 6)
						(HandsOff)
						(curRoom setScript sStopThat)
					else
						(HandsOff)
						(theIconBar disable:)
						(TPrint "\"Now, Larry! Stop that!!\"")
						(Points ptsSawCCtits pfSawCCtits)
						(boobs show)
						(= blouseOpen TRUE)
						(Delay 3 REALSECONDS boobs)
					)
				)
			)
			((== theVerb verbLook)
				(TPrint "As much as you try to prevent it,
					your eyes are attracted to the vicinity of her button;
					is that thread straining?"
				)
			)
			(else
				(curRoom doVerb theVerb &rest)
			)
		)
	)
)
(instance boobs of Prop
   (properties
      view         	vChiChiButton ;916
		loop				0
      x            	160
      y            	172
      description  	"her chi chis"
      sightAngle   	90
		priority			14
		signal			(| fixedLoop fixPriOn ignrAct)
   )
	(method (cue)
		(curRoom setScript sHideBoobs)
	)
	(method (doVerb)
		(curRoom doVerb &rest)
	)
)

(instance theTalker of Talker
	(properties
		view				vChiChiTalker2
		loop				3
		x					50		;where text goes
		y 					140
		nsLeft			151	;where bust goes
		nsTop				68
		talkWidth		220
		name				"Chi Chi Lambada"
	)
	(method (init)
		(= mouth talkerMouth)
		(super init:)
	)
)
(instance talkerMouth of Prop
	(properties
		view			vChiChiTalker2
		loop			0
		nsLeft		0
		nsTop			0
	)
)


(instance herEye of Prop
   (properties
      view         	vChiChiCloseUp
		loop				0
      x            	170
      y            	55
      description  	"her eyes"
      sightAngle   	90
		priority			14
		signal			(| fixedLoop fixPriOn ignrAct)
   )
	(method (doVerb theVerb invItem)
		(curRoom doVerb theVerb &rest)
	)
)
(instance herMouth of Prop
   (properties
      view         	vChiChiCloseUp
		loop				2
      x            	157
      y            	169
		z					100
      description  	"her mouth"
      sightAngle   	90
		priority			14
		signal			(| fixedLoop fixPriOn ignrAct)
   )
)

(instance sBackToWork of Script
	(method (changeState newState)
		(switchto (= state newState)
			(
				(HandsOff)						;condom coding
				(= closeUpSecs 1000)
				(= cycles 2)
			)
			(
				(Say theTalker "\"If you've stared at me long enough,
					Mr. Laffer, it's time for me to get back into your mouth!\""
					#dispose
					#caller: self
				)
			)
			(
				(= ticks 30)
			)
			(
				(herEye setCycle: EndLoop self)
			)
			(
				(= ticks 60)
			)
			(
				(herEye setCycle: BegLoop self)
			)
			(
				(= ticks 30)
			)
			(
				(curRoom newRoom 910)
			)
		)
	)
)

(instance sHideBoobs of Script
	(method (changeState newState)
		(switchto (= state newState)
			(
				(HandsOff)
				(= closeUpSecs 1000)
			 	(= buttonCounter 0)			;allow to open again
				(= blouseOpen FALSE)
				(= cycles 2)
			)
			(
				(Say theTalker "\"Hey! Just a minute here!
					If anybody's going to undo my button, it's me!\""
					#dispose:
					#caller: self
				)
			)
			(
				(boobs hide:)
				(theIconBar enable:)
				(= ticks 90)
			)
			(
				(= seconds 2)
			)

			(
				(herEye setCycle: EndLoop self)
			)
			(
				(= ticks 60)
			)
			(
				(herEye setCycle: BegLoop self)
			)
			(
				(= ticks 30)
			)
			(
				(HandsOn)
				(= closeUpSecs 20)
				(self dispose:)
			)
		)
	)
)

(instance sStopThat of Script
	(method (changeState newState)
		(switchto (= state newState)
			(
				(HandsOff)						;condom coding
				(= closeUpSecs 1000)
				(= cycles 2)
			)
			(
				(Say theTalker "\"Hey! What do you think you're doing?!\""
					#dispose
					#caller: self
				)
			)
			(
				(= ticks 20)
			)
			(
				(herMouth
					show:,
					setLoop:		lSmile,
					setCel:		0,
					setCycle:	EndLoop self,
				)
			)
			(
				(= ticks 60)
			)
			(
				(herMouth setCycle: BegLoop self)
			)
			(
				(herMouth hide)
				(= closeUpSecs 20)
				(HandsOn)
				(self dispose:)
			)
		)
	)
)


(instance sGiveGreen of Script
	(method (changeState newState)
		(switchto (= state newState)
			(
				(HandsOff)						;condom coding
				(= closeUpSecs 1000)
				(= cycles 2)
			)
			(
				(Say ego "\"You know, Chi Chi,
					I bet a girl like you could use something like this,\"
					you say, offering her the green card you \"found\"
					at the airport."
					#dispose: self
				)
			)

			(
				(= ticks 60)
			)

			(
				(Say theTalker "\"Could I?\" says Chi Chi.
					\"Oh, Larry!
					You've made me the happiest woman in Miami.
					Now I'll be able to move to Central California!\""
					#dispose
					#caller: self
				)
			)
			(
				(herMouth 	dispose:)
				(herEye		dispose:)
				(= ticks 60)
			)
			(
				(ego put: iGreenCard)
				(Bset fScoredCC)
				(curRoom newRoom 910)
			)
		)
	)
)

