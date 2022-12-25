;**
;**	Logics for Room 610 -- Outside Shill Building
;**
;**	LSL5:  Passionate Patti Does A Little Undercover Work
;**				Copyright 1991
;**				by Al Lowe
;**
;**	Programmed by Steve Conrad
;**
;**	Last Update:  August 6, 1991
;**

(script#		610)

(public
	rm610		0
)

(define	pShillBuildingExterior		610)
(define	vShillBuildingExterior	610)
	(define	lFlagLeft			0)
	(define	lFlagRight			1)
	(define	lDoorIn				2)
	(define	lDoorOut				3)
(define	pShillBuildingLobby		620)

(define 	sRevolvingDoor			611)

(instance rm610 of LLRoom
	(properties
		picture		pShillBuildingExterior
		north			pShillBuildingLobby
		lookStr		"The Shill Building is one of Baltimore's finest office
						buildings.  
						Its granite, brass and stainless steel facade 
						shines in the sunlight.
						A revolving door promises access to its interior."
	)

	(method (init)
		(ego
			init:,
			normalize:	vPattiSmallest,
			setStep: 2 1
		)

		(HandsOn)

		(switch prevRoomNum
			(north
				(ego
					posn:			156 177,
					setHeading:	180,
					edgeHit:		0
				)
			)
			(else
				(curRoom setScript: sExitLimo)
			)
		)

		(LoadMany SOUND sRevolvingDoor)

		(super init:)

		(flagOne 
			init:,
			cycleSpeed:	18,
			setCycle:	Forward
		)
		(flagTwo
			init:,
			cycleSpeed:	24,
			setCycle:	Forward
		)
		(revolvingDoor
			init:			,
			cycleSpeed:	10
		)
		(limo
			init:,
			approachVerbs: verbDo
		)
		(hydrant init:)
		(car init:)
		(shillSign init:)
		(fReflection init:)

		(curRoom addObstacle:
			((Polygon new:)
				type: PBarredAccess,
				init:	0 189 0 0 156 0 156 180 52 180 52 182 264 182 264 180 174 180
						174 137 174 0 319 0 319 189,
				yourself
			)
		)
	)
)

;************ Scripts *************

(instance sExitLimo of Script
	(method (changeState newState)
		(switchto (= state newState)
			(
				(ego
					posn:			119 182,
					setHeading:	90,
 					setMotion:	MoveTo 119 180
				)
				(= seconds 4)
			)
			(
				(if (Print "Now would be an excellent time for you to save your
					game, Patti." 
					#button		{Save} 				1
					#button		{Nah, Why Bother?} 0
					#title		{AL says}
					HIGH
					)
				 	(theGame save:)
				)
				(self dispose:)
			)
		)
	)
);sExitLimo

;*********** Objects ************

(instance flagOne of Prop
	(properties
		view			vShillBuildingExterior
		loop			lFlagLeft
		x				98
		y				161
 		description "the flag"
		lookStr 		"The Shill Building's management is so patriotic!"
	)
)

(instance flagTwo of Prop
	(properties
		view			vShillBuildingExterior
		loop			lFlagRight
		x				220
		y				159
 		description "the flag"
		lookStr 		"The Shill Building's management is so patriotic!"
	)
)

(instance revolvingDoor of Door
	(properties
		entranceTo	620
		moveToX		165
		moveToY		179
		approachX	172
		approachY	179
		openSnd		sRevolvingDoor
		closeSnd		sRevolvingDoor
		view			vShillBuildingExterior
		loop			lDoorIn
		x				163
		y				178
 		description "the revolving door"
		lookStr 		"This revolving door is a highlight of the entrance 
						to the Shill building."
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbDo
				(self
					setPri:	14,
					setLoop:	lDoorIn
				)
				(super doVerb: theVerb &rest)
			)
			(else
				(super doVerb: theVerb &rest)
			)
		)
	)
	(method (init)
		(if (== prevRoomNum (curRoom north?))
			(self setLoop:	lDoorOut)
		)
		(super init: &rest)
	)
	(method (open)
		(ego
			hide:	,
			posn:	moveToX moveToY
		)
		(super open:)
	)
	(method (cue)
		(super cue:)
		(if (== state doorClosed)
			(ego
				posn:		(- approachX 14) (- approachY 1),
				show:
			)
		)
	)
)

;*********** Features ************

(instance limo of Feature
	(properties
		x 109
		y 176
		approachX 119
		approachY 180
		nsLeft 53
		nsTop 169
		nsBottom 183
		nsRight 166
 		description "the limousine"
		lookStr 		"Your limousine waits for your return.
						Inside, your driver is still smarting from your arrival!
						You feel sure he has learned his lesson."
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbDo
				(curRoom newRoom: 200)
			)
			(else
				(super doVerb: theVerb &rest)
			)
		)
	)
)

(instance hydrant of Feature
	(properties
		x 21
		y 178
		nsLeft 1
		nsTop 168
		nsBottom 189
		nsRight 41
 		description "the fire hydrant"
		lookStr 		"How do dogs spell relief? \"H-Y-D-R-A-N-T\""
	)
)
(instance car of Feature
	(properties
		x 272
		y 168
		nsLeft 225
		nsTop 147
		nsBottom 189
		nsRight 319
 		description "the car"
		lookStr 		"You wonder which Shill Building office drone drives 
						such a junky car?"
	)
)

(instance shillSign of Feature
	(properties
		x 160
		y 133
		nsLeft 121
		nsTop 128
		nsBottom 138
		nsRight 199
 		description "the Shill sign"
		lookStr 		"Patti, it says \"SHILL.\""
		sightAngle 40
	)
)

(instance fReflection of Feature
	(properties
		x 175
		y 176
		z 107
		heading 0
		nsLeft 160
		nsTop 57
		nsBottom 82
		nsRight 190
 		description "the reflection"
		lookStr 		"Don't stare at the sun, Patti!  You'll go blind!"
		sightAngle 40
	)
)
