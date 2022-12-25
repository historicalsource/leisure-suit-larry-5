;**
;**	Logics for Room 740 -- Mud Wrestling Pseudo-Arcade Game
;**
;**	LSL5:  Passionate Patti Does A Little Undercover Work
;**				Copyright 1991
;**				by Al Lowe
;**
;**	Programmed by Brian K. Hughes
;**
;**	Last Update:  September 10, 1991
;**


(script#		740)

(public
	rm740		0
)


(define	pWrestlingArcade		740)
(define	vWrestlingArcade		740)
(define		lSingleCels			0)
(define			cBodyParts		0-6)
(define	vLarryWrestling		741)
(define		lRolling				0)
(define		lFallingOutOfRing	1)
(define	vLanaWrestling			742)
;(define		lRolling				0)
;(define		lFallingOutOfRing	1)
(define	vLarryLanaWrestling	743)
(define		lCircling			0)
;(define		lFallingOutOfRing	1)

(define	mMudWrestling			740)
(define		sCrowdRoar			742)
(define		sGirlAh				752)
(define		sGirlOh				753)
(define		sGirlOo				754)
(define		sGirlAh2				755)
(define		sGirlOh2				756)
(define		sGirlOo2				757)

(define	DONE_MOVING				0)
(define	GRABBED_IT				1)

(define	AUDIENCE		 LOW #title: {The Audience} #mode: teJustCenter #dispose:)


(enum
	rightArm
	leftArm
	rightLeg
	leftLeg
	head
	chest
	abdomen
)

(enum
	STARTDELAY
	PRINT1
	GRAPPLE
	WRESTLE
)

(local
	doitCounter
	partCounter
	lSeconds
	thisLSeconds
	lastLSeconds
	missTime = 1	;Random chance of part dissappearing
	grabs				;How many grabs player has made
	wrestleSeconds ;seconds they have been wrestling
)


(instance rm740 of LLRoom
	(properties
		picture		pWrestlingArcade
	)

	(method (init)
		(LoadMany SOUND
			sCrowdRoar
			sCrowdRoar
			sGirlAh
			sGirlOh
			sGirlOo
			sGirlAh?
			sGirlOh?
			sGirlOo?
		)
		(Load SCRIPT WANDER)
		(super init:)
		(HandsOn)
		(User canControl: FALSE)
		(theIconBar disable: walkIcon talkIcon useIcon inventoryIcon)
		(larry init:)
		(theMusic
			number:		mMudWrestling,
			setLoop:		-1,
			flags:		1,
			play:			127,
		)
		(self setScript: sRoom)
	)
	(method (doit)
		(super doit:)
		(if (cast contains: bodyPart)				;They're now wrestling
			(= thisLSeconds (GetTime SYSTIME1))
			(if (!= lastLSeconds thisLSeconds)	;a second has gone by
				(= lastLSeconds thisLSeconds)
				(++ wrestleSeconds)
				(if 
					(< missTime 1000)					;make it easier to click on body part
					(++ missTime)
				)
				(cond
					((== wrestleSeconds 30)
						(TPrint "Hang in there, Larry.
							She looks like she's slowing down!")
					)
					((not (mod wrestleSeconds 8))	;5 seconds have gone by
						(cond
							((not grabs)
								(TPrint "Hint: click the hand on any body part you
									can reach.")
							)
							((and (> wrestleSeconds 19)
									(< grabs 5))
								(TPrint "\"Ha, ha, Larry!\" Lana cries,
									\"I knew I could take you!\"")
							)
						)
					)
				)
			)
		)
	)
	(method (doVerb)
		(return TRUE)	; this keeps us from getting a non-response
	)
	(method (dispose)
		(DisposeScript WANDER)
		(super dispose:)
	)
)

(instance sRoom of Script
	(method (changeState newState)
		(switch (= state newState)
			(STARTDELAY
				(= seconds 2)
			)
			(PRINT1
				(TPrint "Gosh, Larry!
					Are you sure you want to go through with this?
					Why, your leisure suit might even get muddy!")
				(= seconds 2)
			)
			(GRAPPLE
				(larry
					cycleSpeed:			6,
					moveSpeed:			6,
					observeControl:	cGREEN,
					setCycle:			Forward,
					setMotion:			Wander
				)
				(= seconds 3)
			)
			(WRESTLE
				(TPrint "She must be waiting for you to take the offensive.
					You decide to grab whatever parts of her body you can!")
				(TPrint "(Heh, heh, heh!)" LOW)
				(larry
					setMotion:			0
				)
				;to force the cursor to the hand cursor
				(theIconBar curIcon: (theIconBar at: doIcon))
				(theGame setCursor: ((theIconBar curIcon?) cursor?))
				(Animate (cast elements?) FALSE)
				(bodyPart init:, perform: findWhere)
				(curRoom setScript: sRoar)
			)
		)
	)
)

(instance sRoar of Script
	(method (changeState newState)
		(switchto (= state newState)
			(
				(= seconds (Random 1 4))
			)
			(
				(soundFX
					number:		sCrowdRoar,
					play:
				)
			)
			(
				(self init:)
			)
		)
	)
)

(instance sFadeout of Script
	(method (changeState newState)
		(switchto (= state newState)
			(
				(larry
					illegalBits: 0,
					setLoop:		lFallingOutOfRing,
					setCycle:	Forward,
					setMotion:	MoveTo 147 78 self
				)
			)
			(
				(larry
					setMotion:	MoveTo 131 56 self
				)
	 		)
			(
				(theMusic fade: 80 10 10 FALSE)
				(Cls)
				(curRoom newRoom: 750)
			)
		)
	)
)


(instance bodyPart of Actor
	(properties
		view			vWrestlingArcade
		description "her"
		signal		ignrAct
	)
	(method (doVerb theVerb)
		(switch theVerb
			(verbLook
				(switch cel
					(leftArm
						(TPrint "It's her arm! Grab it!")
					)
					(rightArm
						(TPrint "It's her arm! Grab it!")
					)
					(leftLeg
						(TPrint "It's her leg! Grab it!")
					)
					(rightLeg
						(TPrint "It's her leg! Grab it!")
					)
					(head
						(TPrint "It's her head! Grab it!")
					)
					(chest
						(TPrint "It's her... WHOA! Yeah, grab that!")
					)
					(abdomen
						(TPrint "You'd love to grab her there!")
					)
				)
			)
			(verbDo
				; Cue Larry (& lana) and tell them to cirle
				(larry cue: GRABBED_IT)
				(self perform: findWhere)
				(switch cel
					(leftArm
						(TPrint "\"Oh!\"" AUDIENCE)
					)
					(rightArm
						(TPrint "\"Wow!\"" AUDIENCE)
					)
					(leftLeg
						(TPrint "\"Ooh!\"" AUDIENCE)
					)
					(rightLeg
						(TPrint "\"Woah!\"" AUDIENCE)
					)
					(head
						(TPrint "\"Eeee!\"" AUDIENCE)
					)
					(chest
						(TPrint "\"Ahhhhh!\"" AUDIENCE)
					)
					(abdomen
						(TPrint "\"Gasp!\"" AUDIENCE)
					)
				)
			)
			(else
				(super doVerb: theVerb &rest)
			)
		)
	)
	(method (doit)
		(super doit:)

		; If the cursor is on us, we have a 1 in 3 chance to move
		(if (and	(self onMe: (User curEvent?))
					(not (Random 0 missTime))
				)
		  	(= partCounter 1)
		)

		; If we run out of time, move the part
		(if (and
				(not (-- partCounter))
				(!= (curRoom script?) sFadeout)
			)
			(self perform: findWhere)
		)
	)
)

(instance findWhere of Code
	(method (doit who &tmp theX theY)
		; Find somewhere that isn't on blue control
		(while
			(== (OnControl CMAP
				(= theX (Random 10 310)) (= theY (Random 10 170))) cBLUE
			)
		)
		; Now put the part there & start the counter
		(who
			setCel: 	(Random 0 6),
			posn:		theX theY
		)
		(= partCounter (* missTime (Random 5 15)))
	)
)



(instance larry of Actor
	(properties
		view			vLarryLanaWrestling
		x				160
		y				100
		signal		(| ignrAct fixedLoop)
		description "you and Lana"
		lookStr		"That's you and Lana wrestling, but you can't touch
						you from way up here!"
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbDo
				(TPrint "Don't grab you and Lana. Grab those flying body
					parts instead!")
			)
			(else
				(super doVerb: theVerb invItem)
			)
		)
	)
	(method (init)
		(super init:)
		(self setCycle: EndLoop)
	)
	(method (cue cueType &tmp theAng theX theY)
		(switch cueType
			(DONE_MOVING
				(self setCycle: 0)
			)
			(GRABBED_IT
				(if (> grabs 9)
					(HandsOff)
					(curRoom setScript: sFadeout)
				else
					(patFX
						number:		(+ sGirlAh (/ grabs 2)),
						play:
					)
					(++ grabs)
					(= theAng (GetAngle x y 153 84))
					(cond
						((< theAng 90)		; bottom-left of ring
							(= theX (+ x (Random 10 40)))
							(= theY (- y (Random 10 40)))
						)
						((< theAng 180)	; top-left of ring
							(= theX (+ x (Random 10 40)))
							(= theY (+ y (Random 10 40)))
						)
						((< theAng 270)	; top-right of ring
							(= theX (- x (Random 10 40)))
							(= theY (+ y (Random 10 40)))
						)
						(else					; bottom-right of ring
							(= theX (- x (Random 10 40)))
							(= theY (- y (Random 10 40)))
						)
					)
					(self
						setLoop:		lCircling,
						setCycle: 	Forward,
						setMotion:	MoveTo theX theY self
					)
				)
			)
		)
	)
)


;ษอออออออออออออออออออป
;บ                   บ
;บ Sounds				บ
;บ                   บ
;ศอออออออออออออออออออผ


(instance soundFX of Sound)

(instance patFX of Sound
	(properties
		flags			1
	)
)
