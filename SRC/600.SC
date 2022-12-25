;**
;**	Logics for Room 600 -- Backseat In Limo & Outside Shill Building
;**
;**	LSL5:  Passionate Patti Does A Little Undercover Work
;**				Copyright 1991
;**				by Al Lowe
;**
;**	Programmed by Steve Conrad
;**
;**	Last Update:  September 8, 1991
;**

(script#		600)

(public
	rm600		0
)

(define	pBackseatPatti	 				600)
(define	vDriverGroping				600)
	(define	lGropes1				0)
	(define	lGropes2				1)

(define	pShillBuildingExterior		610)
(define	vShillBuildingExterior	610)
	(define	lFlagLeft			0)
	(define	lFlagRight			1)
	(define	lDoorIn				2)
	(define	lDoorOut				3)
	(define	lDoor					4)
(define	pShillBuildingLobby		620)

(define 	mStruggling				600)
(define	sSlap						601)
(define 	sRevolvingDoor			611)
(define	sfxCarDoorOpen			191)
(define	sfxCarDoorClose		192)
(define	sfxStreetNoise			251)

(define 	limoRoom					200)

(instance rm600 of LLRoom
	(properties
		north			pShillBuildingLobby
		south			limoRoom
		lookStr		"The Shill Building is one of Baltimore's finest office
						buildings.
						Its granite, brass and stainless steel facade
						shines in the sunlight.
						A revolving door promises access to its interior."
	)

	(method (init)
		(switch prevRoomNum
			(north					;exit from Shill building to outside Shill
				(self picture: pShillBuildingExterior)
				(ego
					init:			,
					normalize:	vPattiSmallest,
					setStep: 	2 1,
					posn:			156 177,
					setHeading:	180,
					edgeHit:		0
				)
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
				(theMusic2
					number:		sfxStreetNoise,
					setLoop:		-1,
					setVol:		127,
					flags:		TRUE,
					play:
				)
				(HandsOn)
			)
			(south  					;exit directly from limo, no slap
				(self picture: pShillBuildingExterior)
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
				(self setScript: sExitLimo)
			)
			(else						;exit from limo to outside Shill building
            (SetupExit FALSE)
				(self
					picture: 	pBackseatPatti,
					style:		PIXELDISSOLVE
				)
				(ego
					init:			,
					normalize:	vDriverGroping,
					setCel:		0,
					posn:			189 126,
					cycleSpeed: 12
				)
				(LoadMany PICTURE pShillBuildingExterior)
				(LoadMany VIEW vShillBuildingExterior)
				(LoadMany SOUND sSlap)
				(theMusic
					number:		mStruggling,
					flags:		TRUE,
					setLoop:		-1,
					play:
				)
				(self setScript: sCouple)
			)
		)

		(LoadMany SOUND sRevolvingDoor)

		(super init:)

		(curRoom addObstacle:
			((Polygon new:)
				type: PBarredAccess,
				init:	0 189 0 0 156 0 156 180 52 180 52 182 264 182 264 180 174 180
						174 137 174 0 319 0 319 189,
				yourself
			)
		)
	)
	(method (dispose)
		(theMusic
			fade:	0 15 12 TRUE
		)
		(theMusic2
			fade:	0 15 12 TRUE
		)
		(super dispose: &rest)
	)
)

;******** Scripts	*********

(instance sCouple of Script
	(method (changeState newState)
		(switchto (= state newState)
			(
				(HandsOff)
				(ego setCycle: ForwardCounter 2 self)
			)
			(
				(TPrint "\"Get your hands off me!\" you shout.
					\"That's not what's meant by `personal driver!'\""
					#dispose:
					#title:	{You}
					HIGH
				)
				(ego setCycle: Forward)
				(= seconds 5)
			)
			(
				(Cls)
				(ego
					setLoop:		lGropes2,
					setCel:		0,
					setCycle: 	CycleTo 1 FORWARD self,
				)
			)
			(
				(slap play:)
				(ego
					setCycle: 	EndLoop self,
				)
			)
			(
				(TPrint "Squirming across the leather seat,
					you grab the door handle, fling open the door,
					and escape the limousine (and that chauffeur)!"
					HIGH
				)
				(theMusic
					fade:	0 15 12 TRUE
				)
				(ego dispose:)
				(self setScript sExitLimo)
			)
		)
	)
);sCouple

(instance sExitLimo of Script
	(method (changeState newState)
		(switchto (= state newState)
			(
				(if (!= prevRoomNum (curRoom south?))
					(curRoom drawPic: pShillBuildingExterior PLAIN)
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
				)
				(theMusic2
					number:		sfxStreetNoise,
					setLoop:		-1,
					setVol:		127,
					flags:		TRUE,
					play:
				)
				(= ticks 90)
			)
			(
				(theMusic
					number:		sfxCarDoorOpen,
					setLoop:		1,
					play:
				)
				(= ticks 80)
			)
			(
				(ego
					init:			,
					setLoop:		sGrooper,
					normalize:	vPattiSmallest,
					setStep:		2 1,
					posn:			119 182,
					setHeading:	0,
 					setMotion:	MoveTo 119 180	self
				)
			)
			(
				(= ticks 30)
			)
			(
				(theMusic
					number:		sfxCarDoorClose,
					setLoop:		1,
					play:
				)
				(= ticks 100)
			)
			(
				(if (== prevRoomNum (curRoom south?))
					(self dispose:)
				)
				(= seconds 3)
			)
			(
				(HandsOn)
				(self dispose:)
				(if (Print "Now would be an excellent time for you to save your
						game, Patti."
						#button		{Save} 				 1
						#button		{Nah, Why Bother?} 0
						#title		{AL says}
						HIGH
					)
				 	(theGame save:)
				)
			)
		)
	)
);sExitLimo

(instance sEnterLimo of Script
	(method (changeState newState)
		(switchto (= state newState)
			(
				(HandsOff)
				(theMusic
					number:		sfxCarDoorOpen,
					setLoop:		1,
					play:
				)
				(= ticks 80)
			)
			(
				((ego looper?) dispose:)
				(ego
					setCycle:	0,
					setPri:		9,
					setMotion: 	MoveTo (ego x?) (+ (ego y?) 10) self
				)
			)
			(
				(ego dispose:)
				(= ticks 30)
			)
			(
				(theMusic
					number:		sfxCarDoorClose,
					setLoop:		1,
					play:
				)
				(= ticks 100)
			)
			(
				(curRoom newRoom: 200)
				(self dispose:)
			)
		)
	)
);sEnterLimo

;******** Objects ********

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
		loop			lDoor
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
			(self setLoop: lDoor)
			(ego
				posn:		(- approachX 14) (- approachY 1),
				show:
			)
		)
	)
)

;*********** Sound ************

(instance slap of Sound
	(properties
		number	sSlap
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
				(curRoom setScript: sEnterLimo)
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
