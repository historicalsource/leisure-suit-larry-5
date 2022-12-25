;**
;**	Logics for Room 760 -- Tramp Boardwalk
;**
;**	LSL5:  Passionate Patti Does A Little Undercover Work
;**				Copyright 1991
;**				by Al Lowe
;**
;**	Programmed by Brian K. Hughes
;**
;**	Last Update:  September 10, 1991
;**

(script#		760)


(public
	rm760		0
	bench		1
)


(define	pTrampBoardwalk				760)
(define	vBuildingATPs				760)
(define	vLarryWalkingSmallest	761)
(define		lEast					0)
(define		lWest					1)
(define		lSouthEOLonly		2)
(define		lNorthStandingOnly 3)
(define	vLarryWalkingSmaller		762)
(define	vLarryWalkingSmall		763)
(define	vLarryWalkingNormal		764)

(define	vLarrySkatingSmallest	765)	;**	loops all same as walkers above
(define	vLarrySkatingSmaller		766)
(define	vLarrySkatingSmall		767)
(define	vLarrySkatingNormal		768)

(define	vLanaSkating				769)
(define		lSkatingEast		0)
(define		lSkatingWest		1)
(define		lSpinning			2)
(define		lSlowing				3)
(define		lGliding				4)
(define		lSitting				5)
(define		lTurnsToLarry		6)

(define  vLarryFalling				770)
(define		lFallingTake		0)
(define		lZipOutOfSight		1)
(define		lSplash				2)

(define	vMiscSkater1				771)	;**		These are each sized for
(define	vMiscSkater2				772)	;**	their particular Y-coordinate.
(define	vMiscSkater3				773)
(define	vMiscSkater4				774)
(define	vMiscSkater5				775)
(define	vMiscSkater6				776)
(define	vMiscSkater7				777)
(define	vMiscSkater8				778)
(define	vMiscSkater9				779)

(define	vLarryInSkateShop			791)
(define		lSittingShoes		0)
(define		lChangingShoes		1)
(define		lSittingSkates		2)
;(define		lSlowing				3)
(define		lSittingForward	4)
(define		lTurnsToLana		5)

(define	vLanaCloseUp				795)
(define		lREye					0)
(define		lNose					1)
(define		lLEye					2)
(define		lMouth				3)
(define		lMouthPuckered		4)

(define	vLanaTalker					1731)
(define	vLanaClose-upTalker		1795)

(define	skatingEast					(== loop lEast))
(define	skatingWest					(== loop lWest))

(define	mBoardWalk		  	700)
(define 	sOcean				701)


(define	SKATESHOP		3)
(define	GETAWAY_TIME	18)


(local
	seenMsg
	seenWarning
	movingOffControl
	egosPrevDir

	caughtLana
	smallView		; Larry's smallest view (either 761 or 765)
	largeView		; Larry's largest view (either 764 or 768)
	talkCount

	; sMoveLarry script
	eX
	eY
	obj2Cue
	verb2Use
	shrinkY 		= [ 0 82 103 124 ]
	growY 		= [ 103 124 145 0 ]
	gonnaFall

	; Buildings
	building		= [ 0 0 0 0 0 0 0 ]
	buildWidth 	= [ 53 53 53 53 53 53 53 53 53 53 53 53 53 106 106 53 159 ]

	; For Skating ability
	learnCounter	; how long til next upgrade
		(enum
			bad
			ok
			better
			good
		)
)


(instance rm760 of LLRoom
	(properties
		picture		pTrampBoardwalk
		north			790
		south			780
		west			700
		east			760
		lookStr		"The commercialization of the lovely Tramp Boardwalk
						seems complete.
						Businesses line every inch of the far side of the boardwalk.
						At least the near side has been kept open for
						fishermen and skaters."
	)

	(method (init &tmp theView)
		(LoadMany	VIEW
			vLarryWalkingSmall
			vLarryWalkingSmaller
			vLarryWalkingSmallest
			vLarryWalkingNormal

			vMiscSkater1
			vMiscSkater2
			vMiscSkater3
			vMiscSkater4
			vMiscSkater5
			vMiscSkater6
			vMiscSkater7
			vMiscSkater8
			vMiscSkater9

			vLanaSkating
			vLarryFalling
			vLarryInSkateShop

			vLanaTalker
			vLanaClose-upTalker
		)
		(theMusic
			number:	mBoardWalk,
			loop:		-1,
			play:
		)
		(if (ego skating?)
			(= smallView vLarrySkatingSmallest)
			(= largeView vLarrySkatingNormal)
		else
			(= smallView vLarryWalkingSmallest)
			(= largeView vLarryWalkingNormal)
		)
		(ego
			init:			,
			normalize:	largeView,
			setStep:		3 3
		)

		(switch prevRoomNum
			(north
				(HandsOn)
				(ego
					view:			smallView,
					posn:			204 85,
					setHeading:	180,
					edgeHit:		0
				)
			)
			(west
				(self setScript: sFromWest)
			)
			(else
				(HandsOn)
				(= smallView vLarrySkatingSmallest)
				(= largeView vLarrySkatingNormal)
				(ego
					posn:			160 145,
					edgeHit:		0,
					skating:		TRUE,
					normalize:	largeView
				)
				(= skateAbility better)
				(= learnCounter 99)
			)
		)

		(super 	init:)
		(skater1 init:)
		(skater2 init:)
		(skater3 init:)
		(skater4 init:)
		(skater5 init:)
		(skater6 init:)
		(skater7 init:)
		(skater8 init:)
		(skater9 init:)

		(bench	init:)
		(post1	init:)
		(post2	init:)
		(post3	init:)
		(post4	init:)

		(curRoom addObstacle:
			((Polygon new:)
				type: PBarredAccess,
				init:	90 145 154 145 154 160 90 160,
				yourself
			)
		)

		(directionHandler addToFront: self)
		(mouseDownHandler	addToFront: self)
		(keyDownHandler	addToFront: self)

	);init

	(method (dispose)
		(directionHandler delete: self)
		(mouseDownHandler delete: self)
		(keyDownHandler 	delete: self)
		(theMusic fade:)
		(super dispose:)
	);dispose

	(method (doit &tmp [str 80])
		(super doit:)
		(cond
			((not (OneOf script NULL sMoveLarry)))
			((StepOn ego cGREEN)
				(self setScript: sScroll NULL EAST)
			)
			((StepOn ego cCYAN)
				(cond
					(boardWalkDist
						(self setScript: sScroll NULL WEST)
					)
					((and	(ego skating?)
							(not movingOffControl)
						)
						(if (not seenWarning)
							(TPrint "Be careful, Larry!
								You nearly skated right off the boardwalk.")
							(= seenWarning TRUE)
						)
						(HandsOff)
						(ego setMotion: MoveTo (+ (ego x?) 10) (ego y?) self)
						(= movingOffControl TRUE)
					)
				)
			)
		)
		(if (and (ego skating?)
					(ego mover?)
					(not (cast contains: lana))
				)
			(if (== (++ learnCounter) 100)			; only improve every 100 cycles
				(= learnCounter 0)
				(if (and	(>= (++ skateAbility) good)
							(not (Btst fSeenLL))
						)
					(lana init:)
				)
			)
		)
	);doit

	(method (cue)
		(if movingOffControl
			; Cued by mover when ego is moving off CYAN control
			(= movingOffControl FALSE)
			(HandsOn)
		else
			(ego edgeHit: NORTH)
		 	(curRoom newRoom: (curRoom north?))
		)
	)

	(method (handleEvent event &tmp eType eMsg)
		(= eType (event type?))
		(= eMsg	(event message?))
		(cond

			; Hit the 5 key - cycle the icon
			((and	(& eType direction)
					(== eMsg dirStop)
				)
				(event claimed: FALSE)
			)

			((and	(== eType keyDown)
					(OneOf eMsg TAB ESC)
				)
				NULL
			)

			; Poked the exit icon or clicked/hit ENTER with exit icon
			((or	(and	(OneOf eType mouseDown keyDown)
							(not (event modifiers?))
							(== theCursor EXIT_CURSOR)
				  	)
					(and	(== eType userEvent)
							(== eMsg verbWalk)
				  	)
				)
				(sMeetLana cue:)
			)

			; The Lana close-up script is still running, so let events go through
			((== (ego script?) sMeetLana)
				NULL
			)

			; Don't have control
			((not (User canControl?))
				(event claimed: TRUE)
			)

			((& eType direction)
				(if (not (ego sitting?))
					(if (and	(ego mover?)
								(== eMsg egosPrevDir)
							)
						(ego setMotion: 0)
						(if (== (curRoom script?) sMoveLarry)
							(sMoveLarry dispose:)
						)
						(= egosPrevDir 0)
					else
						(switch eMsg
							(dirN
								(event x: (ego x?), y: (- (ego y?) 20))
							)
							(dirNE
								(event x: (+ (ego x?) 100), y: (- (ego y?) 20))
							)
							(dirE
								(event x: 1000, y: (ego y?))
							)
							(dirSE
								(event x: (+ (ego x?) 20), y: (+ (ego y?) 20))
							)
							(dirS
								(event x: (ego x?), y: (+ (ego y?) 20))
							)
							(dirSW
								(event x: (- (ego x?) 20), y: (+ (ego y?) 20))
							)
							(dirW
								(event x: 0, y: (ego y?))
							)
							(dirNW
								(event x: (- (ego x?) 20), y: (- (ego y?) 20))
							)
							(dirStop
								(theIconBar advanceCurIcon:)
								(event claimed: TRUE)
							)
						)
						(if (!= eMsg dirStop)
							(= eX		(event x?))
							(= eY		(event y?))
							(if (OneOf (curRoom script?) NULL sMoveLarry)
					  			(curRoom setScript: sMoveLarry)
							)
						)
					)
					(= egosPrevDir eMsg)
				else
					(curRoom setScript: sStandUp NULL FALSE)
				)
				(event claimed: TRUE)
			)
			((or	(and (== eType mouseDown)
							(not (event modifiers?))
							(or 	(== (theIconBar curIcon?) (theIconBar at: walkIcon))
									(== eMsg verbWalk)
							)
					)
					(and	(== eType keyDown)
							(== eMsg ENTER)
							(== theCursor ((theIconBar at: walkIcon) cursor?))
					)
				)
				(cond
					((ego sitting?)
						(event claimed: TRUE)
						(curRoom setScript: sStandUp NULL FALSE)
					)
					((OneOf (curRoom script?) NULL sMoveLarry)
						(event claimed: TRUE)
						(= eX	(event x?))
						(= eY	(event y?))
						(if (> eY 158)
							(cond
								((StepOn ego cYELLOW)
									NULL
								)
								((not (ego skating?))
									(curRoom setScript: sFall)
								)
								(else
									(= eY 145)
									(curRoom setScript: sMoveLarry)
								)
							)
						else
							(if (> eY 145)
								(= eY 145)
							)
							(curRoom setScript: sMoveLarry)
						)
					)
				)
			)
		)
	);handleEvent

	(method (drawPic thePic &tmp i val theLoop theCel nextX theX appX)
		(if (!= thePic 795)
			(cast eachElementDo: #perform showSkaterCode)
			(addToPics dispose:)
			(super drawPic: thePic &rest)
			(= nextX 1)
			(= i 0)

			(while (< nextX 318)

				; Generate a pseudo-random building, based on X and boardwalk #
				(= theCel (* (+ boardWalkDist 1) (* i 37)))
				(if (& 1 (>> theCel i))			; find out if the bit is on
					(/= theCel 17)					; another prime
				)

				; See if it is supposed to be Ivana's Skate shop
				(if (and	(== boardWalkDist SKATESHOP)	; 4th boardwalk, building #4
							(== i SKATESHOP)
						)
					(= theLoop 2)
					(= theCel 0)					; Ivana Skate
					(= theX 106)
				else
					(= theCel (mod theCel 17))
					(= theX [buildWidth theCel])
					(= theLoop 0)
					(if (> theCel 8)
						(-= theCel 9)
						(= theLoop 1)
					)
				)
				(= appX (/ (CelWide (theBuilding view?) theLoop theCel) 2))

				; Create the building
				((theBuilding new:)
					x:					nextX,
					loop:				theLoop,
					cel:				theCel,
					approachX:		(Min 300 (+ nextX appX)),
					approachY:		85,
					init:
				)
				(+= nextX theX)
				(++ i)
			)
			(addToPics doit:)
		else
			(super drawPic: thePic &rest)
		)
	);drawPic
)



;ษอออออออออออออออออออป
;บ                   บ
;บ Objects           บ
;บ                   บ
;ศอออออออออออออออออออผ

(class Skater kindof Actor
	(properties
		room			0		; boardWalk number currently in
		freq			1		; how often we change direction (1 chance in freq)
		nearLarry	0		; # cycles to get away from Larry
		cycleSpeed	12
		signal		ignrAct
		paused		0		; TRUE if skaters are paused - detail level
	)
	(methods
		turn					; turn around & skate the other way
	)
	(method (init)
		(self setCycle: Forward)	; we do this here for detail level
		(super init:)
		(= x 160)
		(self cue:)						; reset skater
	)
	(method (doit)
		(super doit:)
		(cond
			(nearLarry
				(-- nearLarry)
			)
			((and	(== room boardWalkDist)
			 		(< (ego distanceTo: self) 30)
			 	)
				(= nearLarry GETAWAY_TIME)
			)
			((and	(not mover)			; This is some real kludgey prophylactic
					(OneOf x 0 320)	;	coding to prevent skaters from getting
				)							;	stuck between rooms.  Why?  Only the
				(self cue:)				;	shadow & his attorney know!
			)
		)
		(cond
			((<= freq 0))		; either don't turn or just changing rooms
			(skatingEast
				(cond
					((and	(== nearLarry GETAWAY_TIME)
							(self isNotHidden:)
						)
						(self turn: WEST)
					)
					((< 300 x 312)
						(cond
							; Time to change direction
							((or	(and 	(> freq 0)
											(== (Random 1 freq) 1)
									)
									(== room 20)
								)
								(self turn: WEST)
							)

							; We're coming in from another screen
							((& signal actorHidden)
								(++ room)
								(= x (- x 320))
								(self setMotion: 	MoveTo 320 y)
								(if (== room boardWalkDist)
									(self show:)
								)
							)

							; Else, we're leaving the current room
							(else
								(self
									freq:			(* freq -1),
									setMotion: 	MoveFwd 40 self
								)
							)
						)
					)
				)
			)
			(skatingWest
				(cond
					((and	(== nearLarry GETAWAY_TIME)
							(self isNotHidden:)
						)
						(self turn: EAST)
					)
					((< 13 x 25)
						(cond
							; Time to change direction
							((or	(and	(> freq 0)
											(== (Random 1 freq) 1)
									)
									(== room 0)
								)
								(self turn: EAST)
							)

							; We're coming in from another screen
							((& signal actorHidden)
								(-- room)
								(= x (+ x 320))
								(self setMotion: 	MoveTo 0 y)
								(if (== room boardWalkDist)
									(self show:)
								)
							)

							; Else, we're leaving the current room
							(else
								(self
									freq:			(* freq -1),
									setMotion: 	MoveFwd 40 self
								)
							)
						)
					)
				)
			)
		)
	)
	(method (turn theDir)
		(switch theDir
			(WEST
				(self
					setLoop:		lWest,
					setMotion:	MoveTo 0 y
				)
			)
			(EAST
				(self
					setLoop:		lEast,
					setMotion:	MoveTo 320 y
				)
			)
		)
	)
	(method (cue)
		; We only get here if leaving the current room
		(if skatingWest
			(-- room)
			(self
				x:				(+ x 320),
				hide:			,
				setMotion:	MoveTo 0 y,
				freq:			(if (< freq 0) (* freq -1) else freq),
				nearLarry:	0
			)
		else
			(++ room)
			(self
				x:				(- x 320),
				hide:			,
				setMotion:	MoveTo 320 y,
				freq:			(if (< freq 0) (* freq -1) else freq),
				nearLarry:	0
			)
		)
	)
)

(instance lana of Skater
	(properties
		view			vLanaSkating
		freq			99
		y				145
		xStep			6
		cycleSpeed	10
		description "Lana Luscious"
	)
	(method (init &tmp roomLo roomHi)
		(super init:)
		; Find a room that Larry's not in, within 4 either direction of us
		(= roomLo (Max 0 	(- boardWalkDist 4)))
		(= roomHi (Min 20 (+ boardWalkDist 4)))
		(while (== (= room (Random roomLo roomHi)) boardWalkDist))
		(if (> room boardWalkDist)
			(self setLoop: lWest)
		else
			(self setLoop: lEast)
		)
	)
	(method (dispose)
		(HandsOn)			; cuz Larry's waiting for Lana to leave
		(super dispose:)
	)
	(method (doit)
		(super doit:)
		(if (and (== room boardWalkDist)
					(not (& signal (| actorHidden hideActor)))
					(not seenMsg)
					(< 20 x 300)
				)
			(TPrint "There she is! It's Lana Luscious!!
				She looks just like that photo you saw in her resume!")
			(= seenMsg TRUE)
			(= freq 6)			; change direction more often now
		)
		(if (and seenMsg
					(not caughtLana)
					(or	(== (ego y?) y)
							(ego sitting?)
					)
					(< (ego distanceTo: self) 40)
					(self isNotHidden:)
				)
			(= caughtLana TRUE)
			(ego setScript: sMeetLana)
		)
	)
	(method (turn)
		(if nearLarry
			(= nearLarry 0)
		else
			(super turn: &rest)
		)
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbLook
			(TPrint "There she is! It's Lana Luscious!!
				She looks just like that photo you saw in her resume!")
			)
			(verbTalk
				(if (or	(< (lana x?) 25)
							(> (lana x?) 300)
						)
					(TPrint "She must not have heard you, Larry.  Perhaps you
						should try to get closer next time."
					)
				else
					(= caughtLana TRUE)
					(ego setScript: sMeetLana)
				)
			)
			(else
				(super doVerb: theVerb invItem &rest)
			)
		)
	)
	(method (cue)
		(cond
			((not freq)
				(self dispose:)
			)
			(skatingWest
				(-- room)
				(self
					x:				(+ x 320),
					hide:			,
					setMotion:	MoveTo 0 y,
					freq:			(if (< freq 0) (* freq -1) else freq)
				)
			)
			(else
				(++ room)
				(self
					x:				(- x 320),
					hide:			,
					setMotion:	MoveTo 320 y,
					freq:			(if (< freq 0) (* freq -1) else freq)
				)
			)
		)
	)
)

(instance skater1 of Skater
	(properties
		view			vMiscSkater1
		room			3
		freq			4
		y				140
		xStep			8
		description "Mister Z"
		lookStr		"Isn't he the cute one?"
	)
	(method (doit)
		(if (not paused)
			(super doit:)
		)
	)
	(method (checkDetail)
		(if (> (theGame detailLevel?) 1)
			(if (>= z 1000)
				(self perform: zCode)
			)
			(= paused FALSE)
		else
			(= paused TRUE)
			(if (< z 1000)
			 	(self perform: zCode)
			)
		)
	)
)

(instance skater2 of Skater
	(properties
		view			vMiscSkater2
		room			3
		freq			4
		y				121
		xStep			8
		description "the old man"
		lookStr		"He's a little old for a rollerskater, eh?"
	)
	(method (doit)
		(if (not paused)
			(super doit:)
		)
	)
	(method (checkDetail)
		(if (> (theGame detailLevel?) 1)
			(if (>= z 1000)
				(self perform: zCode)
			)
			(= paused FALSE)
		else
			(= paused TRUE)
			(if (< z 1000)
			 	(self perform: zCode)
			)
		)
	)
)

(instance skater3 of Skater
	(properties
		view			vMiscSkater3
		room			1
		freq			3
		y				118
		z				10			; cuz origin's in his waist
		xStep			8
		description "Hot Wheels"
		lookStr		"This kid's a real showoff!"
	)
	(method (doit)
		(if (not paused)
			(super doit:)
		)
	)
	(method (checkDetail)
		(if (> (theGame detailLevel?) 2)
			(if (>= z 1000)
				(self perform: zCode)
			)
			(= paused FALSE)
		else
			(= paused TRUE)
			(if (< z 1000)
			 	(self perform: zCode)
			)
		)
	)
)

(instance skater4 of Skater
	(properties
		view			vMiscSkater4
		room			2
		freq			2
		y				116
		xStep			6
		description "Rockin' Roller"
		lookStr		"What's he listening to, anyway?"
	)
	(method (doit)
		(if (not paused)
			(super doit:)
		)
	)
	(method (checkDetail)
		(if (> (theGame detailLevel?) 2)
			(if (>= z 1000)
				(self perform: zCode)
			)
			(= paused FALSE)
		else
			(= paused TRUE)
			(if (< z 1000)
			 	(self perform: zCode)
			)
		)
	)
)

(instance skater5 of Skater
	(properties
		view			vMiscSkater5
		room			2
		freq			2
		y				142
		xStep			8
		description "Roller Pig"
		lookStr		"What talent! He can eat AND skate at the same time!"
	)
	(method (doit)
		(if (not paused)
			(super doit:)
		)
	)
	(method (checkDetail)
		(if (> (theGame detailLevel?) 3)
			(if (>= z 1000)
				(self perform: zCode)
			)
			(= paused FALSE)
		else
			(= paused TRUE)
			(if (< z 1000)
			 	(self perform: zCode)
			)
		)
	)
)

(instance skater6 of Skater
	(properties
		view			vMiscSkater6
		room			2
		freq			2
		y				117
		xStep			6
		description "Bill the Suit"
		lookStr		"He's way overdressed for THIS boardwalk!"
	)
	(method (doit)
		(if (not paused)
			(super doit:)
		)
	)
	(method (checkDetail)
		(if (> (theGame detailLevel?) 3)
			(if (>= z 1000)
				(self perform: zCode)
			)
			(= paused FALSE)
		else
			(= paused TRUE)
			(if (< z 1000)
			 	(self perform: zCode)
			)
		)
	)
)

(instance skater7 of Skater
	(properties
		view			vMiscSkater7
		room			2
		freq			2
		y				131
		xStep			8
		description "Kid Dork"
		lookStr		"What a dorky lookin' little guy!
						You immediately take a liking to him!"
	)
	(method (doit)
		(if (not paused)
			(super doit:)
		)
	)
	(method (checkDetail)
		(if (> (theGame detailLevel?) 4)
			(if (>= z 1000)
				(self perform: zCode)
			)
			(= paused FALSE)
		else
			(= paused TRUE)
			(if (< z 1000)
			 	(self perform: zCode)
			)
		)
	)
)

(instance skater8 of Skater
	(properties
		view			vMiscSkater8
		room			2
		freq			2
		y				110
		xStep			6
		description "the skater"
		lookStr		"You had no idea the boardwalk would have so many skaters!"
	)
	(method (doit)
		(if (not paused)
			(super doit:)
		)
	)
	(method (checkDetail)
		(if (> (theGame detailLevel?) 4)
			(if (>= z 1000)
				(self perform: zCode)
			)
			(= paused FALSE)
		else
			(= paused TRUE)
			(if (< z 1000)
			 	(self perform: zCode)
			)
		)
	)
)

(instance skater9 of Skater
	(properties
		view			vMiscSkater9
		room			2
		freq			2
		y				91
		xStep			6
		description "the skater"
		lookStr		"Never straying far from the shops on the other side
						of the Boardwalk, a skater finds true happiness,
						blading his little heart out!"
	)
	(method (doit)
		(if (not paused)
			(super doit:)
		)
	)
	(method (checkDetail)
		(if (> (theGame detailLevel?) 4)
			(if (>= z 1000)
				(self perform: zCode)
			)
			(= paused FALSE)
		else
			(= paused TRUE)
			(if (< z 1000)
			 	(self perform: zCode)
			)
		)
	)
)


(instance zCode of Code
	(method (doit theObj)
		(if (and (== (theObj z?) 1000)
					(!= (curRoom curPic?) 795)
				)
			(theObj z: 0)
		else
			(theObj z: 1000)
		)
	)
)

(instance showSkaterCode of Code
	(method (doit obj)
		(if (obj respondsTo: #freq)				; it's a skater
			(if (== (obj room?) boardWalkDist)	; show if in the room...
				(obj show:)
			else											; hide if not
				(obj hide:)
			)
		)
	)
)


(instance theBuilding of PicView
	(properties
		view			vBuildingATPs
		y				80
		description	"the building"
	)
	(method (doVerb theVerb &tmp evt)
		(switch theVerb
			(verbLook
				(switch loop
					(0
						(switch cel
							(0
								(TPrint "This shanty reminds you of \"The Shack,\"
									your favorite collegiate hangout!")
							)
							(1
								(TPrint "What a strange location for a
									rocket fuel manufacturing plant.")
							)
							(2
								(TPrint "You haven't seen a slot-car raceway in
									years!")
							)
							(3
								(TPrint "The \"Salty Dog Saloon\" is built upon the
									very same location as the old \"Pepper Dog Saloon,\"
									which burnt down years ago in the Great Tramp
									Boardwalk Fire of '06."
								)
							)
							(4
								(TPrint "Wow! In this place you can watch movies
									for only 25 cents! What a bargain!")
							)
							(5
								(TPrint "This is one of the last of the juke joints!")
							)
							(6
								(TPrint "You wonder what they sell inside the
									\"Love Shack.\"")
							)
							(7
								(TPrint "This house was built here many years ago,
									before there even was a Tramp Boardwalk!")
							)
							(8
								(TPrint "Recliners are a very popular item among
									any Atlantic City tourists that can still walk!")
							)
						)
					)
					(1
						(switch cel
							(0
								(TPrint "A sign on the front door reads,
									\"Shirts? No shoes. No service.\"")
							)
							(1
								(TPrint "Carlos Escobar has been running this taco
									stand ever since he wrecked his van staring at
									sidewalk scenery!")
							)
							(2
								(TPrint "You wonder if they have your favorite
									poster--the extra-large-size, full-color,
									\"Wayne Newton Perspiring!\"")
							)
							(3
								(TPrint "The owners of the \"Live Girl\" shop are
									faring much better financially since they changed
									their name from the \"Dead Girl\" shop!")
							)
							(4
								(TPrint "If you wanna have fun,
									this is the place to do it.")
							)
							(5
								(TPrint "Contrary to popular rumor,
									this is NOT a jukebox recycling center.")
							)
							(6
								(TPrint "If you want a CD real bad, this is the place!
									They've got some CDs that are real bad!")
							)
							(7
								(TPrint "No boardwalk is complete without the
									requisite salt-water taffy store.")
							)
						)
					)
					(2
						(TPrint "Look! It's the skate shop of the
							supermarket-famous Ivana Tramp!
							And--she's open!!")
					)
				)
			)
			(verbDo
				(if	(and	(== (ego view?) smallView)
								(== (ego x?) approachX)
								(== (ego y?) approachY)
							)
					(if (== loop 2)	; Ivana Skate
						(if (ego skating?)
							(TPrint "Sorry, Larry.
								A sign on the door clearly reads,
								\"No shirt, no shoes, no service.\"
								You're going to have to remove your skates
								before entering.")
						else
							; We're standing outside the door
							(ego setHeading: 0 curRoom)
						)
					else
						(TPrint "A small sign near the front door of the shop reads,
							\"Closed in Honor of The Donald's Birthday!\"")
						(TPrint "(You had no idea Donald Tramp's birthday was
							a local holiday!)" LOW)
					)
				else
					; We're way away
					(curRoom handleEvent:
						(= evt
							((Event new:)
								type:			mouseDown,
								message:		verbWalk,
								x:				approachX,
								y:				approachY,
							)
						)
					)
					(= obj2Cue self)
					(= verb2Use verbDo)
					(evt dispose:)
				)
			)
			(else
				(super doVerb: theVerb &rest)
			)
		)
	)
)

(instance sSplash of Sound
	(properties
		number		761
	)
)

(instance splash of Prop
	(properties
		view		vLarryFalling
		loop		lSplash
		y			189
	)
)

(instance rightEye of Prop
	(properties
		view			vLanaCloseUp
		loop			lREye
		x				165
		y				180
		z				119
		description	"Lana's right eye"
	)
	(method (doVerb)
		(lanaFtr doVerb: &rest)
	)
)

(instance leftEye of Prop
	(properties
		view			vLanaCloseUp
		loop			lLEye
		x				140
		y				180
		z				123
		description	"Lana's left eye"
	)
	(method (doVerb)
		(lanaFtr doVerb: &rest)
	)
)

(instance nose of Prop
	(properties
		view			vLanaCloseUp
		loop			lNose
		x				149
		y				180
		z				108
		cycleSpeed	18
		description	"Lana's nose"
	)
	(method (init)
		(super init:)
		(self setCycle: Forward)
	)
	(method (doVerb)
		(lanaFtr doVerb: &rest)
	)
)

(instance lanaMouth of Prop
	(properties
		view			vLanaCloseUp
		loop			lMouth
		x				149
		y				180
		z				101
		description	"Lana's mouth"
	)
	(method (doVerb)
		(lanaFtr doVerb: &rest)
	)
)


;ษอออออออออป
;บ         บ
;บ Talkers บ
;บ         บ
;ศอออออออออผ

; Lana's close-up talker

(instance lanaCTalker of Talker
	(properties
		name			"Lana Luscious"
		view			vLanaClose-upTalker
		loop			1
		nsLeft		140
		nsTop			70
		x				140
		y				160
	)
	(method (init)
		(= mouth lanaCMouth)
		(super init: &rest)
	)
)

(instance lanaCMouth of Prop
	(properties
		view		vLanaClose-upTalker
		loop		0
		nsLeft	0
		nsTop		0
	)
)


; Lana's far away talker

(instance lanaFTalker of Talker
	(properties
		name			"Lana Luscious"
		view			vLanaTalker
		loop			3
		nsLeft		5
		nsTop			100
		viewInPrint	TRUE
	)
	(method (init)
		(= bust 	lanaFBust)
		(= mouth	lanaFMouth)
		(= eyes	lanaFEyes)
		(super init: &rest)
	)
)

(instance lanaFBust of View
	(properties
		view		vLanaTalker
		loop		1
		nsLeft	0
		nsTop		0
	)
)

(instance lanaFMouth of Prop
	(properties
		view		vLanaTalker
		loop		0
		nsLeft	23
		nsTop		40
	)
)

(instance lanaFEyes of Prop
	(properties
		view			vLanaTalker
		loop			2
		nsLeft		29
		nsTop			31
		cycleSpeed	12
	)
)


;ษอออออออออป
;บ         บ
;บ Scripts บ
;บ         บ
;ศอออออออออผ

(instance sFromWest of Script
	(method (changeState newState)
		(switchto (= state newState)
			(
				(ego
					view:			vLarryWalkingNormal,
					posn:			-10 145,
					setMotion: 	MoveFwd 40 self
				)
			)
			(
				(HandsOn)
				((ego looper?) dispose:)
				(self dispose:)
			)
		)
	)
);sFromWest


(instance sFall of Script
	(method (doit)
		(super doit:)
		(if (StepOn ego cYELLOW)
			(ego setMotion: MoveTo (ego x?) 145)
			(HandsOn)
			(if (== (curRoom script?) sMoveLarry)
				(sMoveLarry dispose:)
			)
			(self dispose:)
		)
	)
	(method (changeState newState)
		(switchto (= state newState)
			(
				(if (< (ego y?) 145)	; needs to move to front edge first
					(= eY 145)
					(= obj2Cue (= verb2Use 0))
					(= gonnaFall TRUE)
					(client setScript: sMoveLarry)
				else
					(ego
						setLoop:		2,
						setCycle:	StopWalk SAMEVIEW,
						setMotion:	PolyPath (ego x?) 158 self
					)
				)
			)
			(
				(HandsOff)
				(ego
					view:			vLarryFalling,
					setLoop:		lFallingTake,
					setCel:		0,
					cycleSpeed:	10,
					setCycle:	EndLoop
				)
				(= cycles 10)
			)
			(
				(ego
					setLoop: 	lZipOutOfSight,
					setCel:		0,
					setCycle:	EndLoop
				)
				(= ticks 90)
			)
			(
				(sSplash play:)
				(splash
					init:			,
					x:				(ego x?),
					setCycle:	EndLoop self
				)
			)
			(
				(curRoom newRoom: 780)
			)
		)
	)
);sFall


(instance sScroll of Script
	;
	; register - direction, EAST or WEST
	;
	(method (changeState newState)
		(switchto (= state newState)
			(
				(HandsOff)
				(ego setMotion: MoveFwd 40 self)
			)
			(
				(if (== register WEST)
					(-- boardWalkDist)
					(ego x: (+ (ego x?) 320))
					(curRoom drawPic: (curRoom picture?) SCROLLRIGHT)
				else
					(++ boardWalkDist)
					(ego x: (- (ego x?) 320))
					(curRoom drawPic: (curRoom picture?) SCROLLLEFT)
				)
				(= cycles 1)
			)
			(
				(= eX 0)
				(= eY 0)
				(= obj2Cue 0)
				(= verb2Use 0)
				(HandsOn)
				(self dispose:)
			)
		)
	)
);sScroll

(instance sMeetLana of Script
	;
	; Larry has to catch Lana for this to happen.  With real-time cyclers,
	;	the player can speed Larry up so that he skates faster than Lana.  If
	;	he does not, she will eventually return to his boardwalk segment.
	;
	(method (changeState newState)
		(switchto (= state newState)
			(
				; If we're on a higher band than Lana, move to her band first
				(if (< (ego y?) 145)
					(= eY 145)
					(= obj2Cue self)
					(curRoom setScript: sMoveLarry)
				else
					(= cycles 1)
				)
			)
			(
				(HandsOff)

				; We do this because HandsOff does an (ego setMotion: 0), which
				;	does not get rid of it's script
				(if (== (curRoom script?) sMoveLarry)
					(sMoveLarry dispose:)
				)

				(Points ptsLarryMeetsLana pfLarryMeetsLana)
				(if (and	(ego sitting?)
							(!= (ego loop?) lSittingForward)
						)
					(self setScript: sStandUp self FALSE)
				else
					(= cycles 2)
				)
			)
			(
				(lana setMotion: 0)
				(Say ego "\"Oh, Lana,\" you say in your friendliest voice,
					\"Lana Luscious!\"")
				(Say lanaFTalker "\"Yes,\" she replies, hesitantly.
					\"Who are you?\""
					#dispose:
				)
				(= ticks 30)
			)
			(
				(Say ego "\"My name is Larry; Larry Laffer,\" you purr.
					\"I've come a long way to meet you!\"")
				(Say lanaFTalker "\"And meet me you have, Larry,\" says Lana.
					\"Whaddaya say we sit down on that bench over there?
					These skates are killing me!\""
					#dispose:
				)
				(= ticks 30)
			)
			(
				(Say ego "\"It would be my pleasure.\"")
				(= ticks 30)
			)
			(
				(ego
					setLoop:		RELEASE,
					setCycle:	StopWalk SAMEVIEW,
					setMotion: 	MoveTo 145 145 self
				)
				(lana setScript: sLanaSits self)
			)
			(;we get cued by lana's script
				(ego
					view:			vLarryInSkateShop,
					setLoop:		lSittingForward,
					setCel:		0,
					setCycle:	EndLoop
				)
			)
			(;we get cued by lana's script
				(ego
					setLoop:		lTurnsToLana,
					setCel:		0,
					setCycle:	EndLoop self,
					sitting:		TRUE
				)
			)

			(
				(curRoom drawPic: 795 PLAIN)
				(= cycles 1)
			)
			(;we get cued by clicking the exit icon
				(cast eachElementDo: #perform zCode)
				(SetupExit TRUE)
				(HandsOn)
				(User canControl: FALSE)
				(rightEye	init:)
				(leftEye		init:)
				(nose			init:)
				(lanaMouth	init:)
				(features	addToFront: lanaFtr)
			)
			(
				(HandsOff)
				(SetupExit FALSE)
				(curRoom drawPic: 760 PLAIN)
				(rightEye	dispose:)
				(leftEye		dispose:)
				(nose			dispose:)
				(lanaMouth	dispose:)
				(lanaFtr		dispose:)
				(addToPics doit:)
				(cast eachElementDo: #perform zCode)
				(skater1 cue:)		; reset the skaters
				(skater2 cue:)
				(skater3 cue:)
				(skater4 cue:)
				(skater5 cue:)
				(skater6 cue:)
				(skater7 cue:)
				(skater8 cue:)
				(skater9 cue:)
				(= cycles 3)
			)
			(
				(lana
					freq:			0,			; dispose instead of turning
					setLoop:		lSitting,
					setCel:		LASTCEL,
					posn:			107 145,
					setCycle: 	BegLoop self
				)
			)
			(
				(ego setCycle:	BegLoop self)
			)
			(
				(ego
					setLoop: lSittingForward,
					setCel:	LASTCEL
				)
				(lana
					setLoop:		lSkatingWest,
					setCycle:	Forward,
					setMotion: 	MoveTo -20 145 lana
				)
				(skater5 setPri: 10)
				(Bset fSeenLL)
				(self dispose:)
				; We'll get a HandsOn from Lana's dispose method; we're stuck
				;	on the bench until she leaves the room
			)
		)
	)
);sMeetLana

(instance sLanaSits of Script
	(enum
		moveLeft
		spin
		moveToBench
		glideToStop
		sitDown
		lastState
	)
	(method (changeState newState)
		(switch (= state newState)
			(moveLeft
				(lana setPri: 9)
				(if (< (lana x?) 51)
					(self changeState: moveToBench)
				else
					(lana
						setLoop:		lSkatingWest,
						setMotion: 	MoveTo 50 145 self
					)
				)
			)
			(spin
				(lana
					setLoop: 	lSpinning,
					setCycle:	ForwardCounter 2 self
				)
			)
			(moveToBench
				(lana
					setLoop:		lSlowing,
					setCycle:	EndLoop,
					setMotion:	MoveTo 93 145 self
				)
			)
			(glideToStop
				(lana
					setLoop:		lGliding,
					setCycle:	Forward,
					setMotion:	MoveTo 107 145 self
				)
			)
			(sitDown
				(skater5 setPri: 9)
				(lana
					posn:			(lana x?) 148,
					setLoop:		lSitting,
					setCycle:	EndLoop self
				)
			)
			(lastState
				(self dispose:)
			)
		)
	)
);sLanaSits

(instance sSitDown of Script
	;
	; register - TRUE if changing shoes, FALSE if just sitting
	;
	(method (changeState newState)
		(switchto (= state newState)
			(
				(HandsOff)
				(= eX (if register 90 else 142))
				(= eY 145)
				(= obj2Cue self)
				(= verb2Use 0)
				(curRoom setScript: sMoveLarry)
			)
			(
				(if register
					(ego setMotion: MoveTo 90 149 self)
				else
					(= cycles 1)
				)
			)
			(
				(if register
					(ego
						view:			vLarryInSkateShop,
						setLoop:		(if (ego skating?)
											lSittingSkates
										else
											lSittingShoes),
						setCel:		0,
						setCycle:	EndLoop self,
						sitting:		TRUE
					)
				else
					(ego
						view:			vLarryInSkateShop,
						setLoop:		lSittingForward,
						setCel:		0,
						setCycle:	EndLoop self,
						sitting:		TRUE
					)
				)
			)
			(
				(HandsOn)
				(self dispose:)
			)
		)
	)
);sSitDown

(instance sDoSkates of Script
	(method (changeState newState)
		(switchto (= state newState)
			(
				(if (ego skating?)
					(= smallView vLarryWalkingSmallest)
					(= largeView vLarryWalkingNormal)
					(ego
						setLoop:		lChangingShoes,
						setCel:		LASTCEL,
						setPri:		10,
						setCycle:	BegLoop self
					)
				else
					(Points ptsPutOnSkates pfPutOnSkates)
					(= smallView vLarrySkatingSmallest)
					(= largeView vLarrySkatingNormal)
					(ego
						setLoop:		lChangingShoes,
						setCel:		0,
						setPri:		10,
						setCycle:	EndLoop self
					)
				)
			)
			(
				(if (ego skating?)
					(ego skating: FALSE)
					((Inventory at: iRollerblades) state: 0)
					(ego
						setLoop:	lSittingShoes,
						setCel:	LASTCEL
					)
					(LoadMany VIEW
						vLarryWalkingSmall
						vLarryWalkingSmaller
						vLarryWalkingSmallest
						vLarryWalkingNormal
					)
				else
					(ego skating: TRUE)
					((Inventory at: iRollerblades) state: 1)
 					(ego
						setLoop:	lSittingSkates,
						setCel:	LASTCEL
					)
					(LoadMany VIEW
						vLarrySkatingSmall
						vLarrySkatingSmaller
						vLarrySkatingSmallest
						vLarrySkatingNormal
					)
				)
				(self dispose:)
			)
		)
	)
);sDoSkates

(instance sStandUp of Script
	;
	; register - TRUE if we should sit down again, FALSE otherwise
	;
	(method (changeState newState)
		(switchto (= state newState)
			(
			 	(ego setCycle: BegLoop self)
			)
			(
				(ego
					sitting:		FALSE,
					setLoop:		3,
					normalize:	largeView,
					setStep:		3 3,
					setHeading:	0,
					posn:			(ego x?) 145
				)
				(if register
				 	(bench doVerb: verbUse iRollerblades)
				)
				(self dispose:)
			)
		)
	)
);sStandUp

(instance sMoveLarry of Script
	;
	; register - which way we're going
	;
	(enum
		startMoving
		moveOnce
		changeViews
		stopMoving
	)
	(define LATERAL	0)
	(define SHRINKING 1)
	(define GROWING	2)

	(method (changeState newState &tmp idx)
		(switch (= state newState)
			(startMoving
				(ego
					normalize: 	(ego view?),
					setStep:		3 3
				)
				(cond
					((< eY (- (ego y?) 5))
						(= register SHRINKING)
					)
					((> eY (+ (ego y?) 5))
						(= register GROWING)
					)
					(else
						(= register LATERAL)
					)
				)
				(= cycles 1)
			)
			(moveOnce
				(switch register
					(LATERAL
						; Move along the x axis, no y movement allowed
						(if (not gonnaFall)
							(User canControl: TRUE)
						)
						(++ state)
						(ego
							setCycle:	StopWalk SAMEVIEW,
							setMotion: 	PolyPath eX (Min 145 (ego y?)) self
						)
					)
					(SHRINKING
						; Move along the y axis, no x movement allowed
						(User canControl: FALSE)
						(if (== (ego view?) smallView)	; already min size
							(= register LATERAL)
							(= cycles 1)
 						else
							(= idx (- (ego view?) smallView))	; yields 0-3
							(ego
								setLoop:		3,
								setCel:		0,
								setCycle:	EndLoop,
								setMotion:	PolyPath (ego x?) [shrinkY idx] self
							)
						)
					)
					(GROWING
						; Move along the y axis, no x movement allowed
						(User canControl: FALSE)
						(if (== (ego view?) largeView)	; already max size
							(= register LATERAL)
							(= cycles 1)
						else
							(= idx (- (ego view?) smallView))	; yields 0-3
							(ego
								setLoop:		2,
								setCel:		0,
								setCycle:	EndLoop,
								setMotion:	PolyPath (ego x?) [growY idx] self
							)
						)
					)
				)
			)
			(changeViews
				(switch register
					(SHRINKING
						(if (< (ego view?) 769)
							(ego
								view: 		(- (ego view?) 1),
								setLoop: 	RELEASE,
								setCel:		LASTCEL,
							)
						)
						(if (< (- (ego y?) eY) 6)	; we're in the right band
							(= register LATERAL)
						)
					)
					(GROWING
						(if (< (ego view?) 769)
							(ego
								view: 		(+ (ego view?) 1),
								setLoop:		RELEASE,
								setCel:		0,
							)
						)
						(if (< (- eY (ego y?)) 6)	; we're in the right band
							(= register LATERAL)
						)
					)
				)
				(self changeState: 1)
			)
			(stopMoving
				(cond
					(verb2Use
						(obj2Cue: doVerb verb2Use)
					)
					(obj2Cue
						(obj2Cue cue:)
					)
				)
				(= obj2Cue (= verb2Use 0))
				(if gonnaFall
					(client setScript: sFall)
				else
					(User canControl: TRUE)
					(self dispose:)
				)
			)
		)
	)
);sMoveLarry

(instance sLanaTalks of Script
	(method (changeState newState)
		(switchto (= state newState)
			(
				(Say ego "\"Skate here often?\" you ask.")
				(= ticks 60)
			)
			(
				(Say lanaCTalker "\"Only when I'm trying to find a man,\"
					she says with a smile."
					#dispose:
				)
				(= ticks 60)
			)
			(; cued by talking
			)


			(
				(Say ego "\"So, does that happen often?\" you ask.")
				(= ticks 60)
			)
			(
				(Say lanaCTalker "\"Only when I'm here skating,\"
					she says with a smile."
					#dispose:
				)
				(= ticks 60)
			)
			(; cued by talking
			)


			(
				(Say ego "\"I must tell you how much I love your skating
					technique,\" you tell Lana.")
				(= ticks 60)
			)
			(
				(Say lanaCTalker "\"If you like that, you should drop by the
					`Tramp This! Mud Wrestling' Show.
					I go on in just a few minutes!\"
					she says with a wink."
					#dispose:
				)
				(= ticks 60)
			)
			(; cued by talking
			)


			(
				(Say ego "\"Where is the mud wrestling held?\"
					you ask."
				)
				(= ticks 60)
			)
			(
				(Say lanaCTalker "\"Why, it's in the Tramp Ballroom.
					The entrance is at the rear of the casino!\" she says.
					\"And if you bring a few hundred dollars with you, I'll
					make sure it's an experience you'll never forget!\""
					#dispose:
				)
				(= ticks 60)
			)
			(
				(TPrint "(You now understand why Lana was one of the top three
					finalists back at PornProdCorp!)" LOW)
				(= ticks 60)
			)
			(; cued by talking
			)


			(
				(Points ptsTalkedToLana pfTalkedToLana)
				(Say ego "\"I'll be there, Lana!\" you exclaim.
					\"See you there!\""
				)
				(= ticks 60)
			)
			(
				(Say lanaCTalker "\"Okay, Larry; I'll look forward to it!\"
					she concludes, standing up.
					\"Gotta go get out of my makeup!\""
					#dispose:
				)
				(= ticks 60)
			)
			(
				(TPrint "(Doesn't she mean \"into?\")" LOW)
				(= cycles 1)
			)
			(
				(sMeetLana cue:)		;**	finish old script
				(self dispose:)
			)
		)
	)
);sLanaTalks


;ษอออออออออออออออออออป
;บ                   บ
;บ Features          บ
;บ                   บ
;ศอออออออออออออออออออผ

(instance lanaFtr of Feature
	(properties
		x				160
		y				170
		nsLeft		0
		nsTop			0
		nsBottom		189
		nsRight		319
		description	"Lana Luscious"
	)
	(method (doVerb theVerb)
		(switch theVerb
			(verbLook
				(TPrint "You are sitting on the boardwalk staring directly
					into the eyes of a gorgeous, oversexed blond!
					If only you could think of something to say!")
			)
			(verbTalk
				(if (curRoom script?)
					((curRoom script?) cue:)
				else
					(curRoom setScript: sLanaTalks)
				)
			)
			(verbDo
				(Say lanaCTalker
					"\"Oh, Larry! You know that just drives me crazy!\""
					#dispose:
				)
			)
			(verbZipper
				(Say lanaCTalker "\"Larry! Here?\" says Lana.
					\"Why don't you come by the Casino later when I'm
					mud wrestling?\""
					#dispose:
				)
			)
			(verbUse
				(Say lanaCTalker "\"Oh, I have no use for anything you could
					possibly offer me, Larry,\" says Lana.
					\"Why, when I have total access to this sun-drenched boardwalk,
					the fresh ocean breezes caressing my hair,
					my smell of the salt water
					and ready access to all the mud I can roll in!\""
					#dispose:
				)
			)
			(else
				(super doVerb: theVerb &rest)
			)
		)
	)
)

(instance buildings of Feature
	(properties
		x 				159
		y 				29
		nsLeft 		0
		nsTop 		0
		nsBottom 	59
		nsRight 		319
 		description "the boardwalk buildings"
		lookStr 		"The far side of the Tramp Boardwalk is lined
						with tawdry, tacky tourist traps.
						A bit of blue sky peeks through the buildings."
		sightAngle 	40
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbDo
				(TPrint "Almost every building on the boardwalk is closed today
					in honor of a long celebrated local holiday--Donald Tramp's
					birthday.")
			)
			(else
				(super doVerb: theVerb invItem &rest)
			)
		)
	)
)

(instance bench of Feature
	(properties
		x 				126
		y 				140
		nsLeft 		98
		nsTop 		129
		nsBottom 	151
		nsRight 		154
		approachX	88
		approachY	152
 		description "the bench"
		lookStr 		"A long row of park benches await your sitting pleasure
						along the ocean side of the boardwalk."
		sightAngle 40
	)
	(method (doVerb theVerb invItem &tmp who2Cue)
		(switch theVerb
			; Yeah, I know this is a real convoluted mess.  But that's what I
			;	get for trying to make the bench handle every possible combination,
			;	and then not having the art to make it easy! - BKH
			(verbDo
				(cond
					((not (ego sitting?))
					 	(ego setScript:
							sSitDown NULL (if (sSitDown next?) TRUE else FALSE)
						)
					)
					((== (ego loop?) lSittingForward)
						(ego setScript: sStandUp NULL (ego has: iRollerblades))
					)
					((ego has: iRollerblades)
						(ego setScript: sDoSkates)
					)
					(else
						(super doVerb: theVerb invItem &rest)
					)
				)
			)
			(verbUse
				(if (== invItem iRollerblades)
					(sSitDown next: sDoSkates)
					(self doVerb: verbDo)
				else
					(super doVerb: theVerb invItem &rest)
				)
			)
			(else
				(super doVerb: theVerb invItem &rest)
			)
		)
	)
)

(instance post1 of Feature
	(properties
		x 				61
		y 				160
		nsLeft 		51
		nsTop 		131
		nsBottom 	189
		nsRight 		72
 		description "the post"
		lookStr 		"These posts hold up the boardwalk."
		sightAngle 	40
	)
)

(instance post2 of Feature
	(properties
		x 				137
		y 				163
		z 				0
		heading 		0
		nsLeft 		125
		nsTop 		138
		nsBottom 	189
		nsRight 		149
 		description "the post"
		lookStr 		"These posts hold up the boardwalk."
		sightAngle 	40
	)
)

(instance post3 of Feature
	(properties
		x 				205
		y 				164
		nsLeft 		193
		nsTop 		139
		nsBottom 	189
		nsRight 		221
 		description "the post"
		lookStr 		"These posts hold up the boardwalk."
		sightAngle 	40
	)
)

(instance post4 of Feature
	(properties
		x 				295
		y 				165
		nsLeft 		283
		nsTop 		141
		nsBottom 	189
		nsRight 		309
 		description "the post"
		lookStr 		"These posts hold up the boardwalk."
		sightAngle 	40
	)
)
