;**
;**	Logics for Room 425 -- FBI Headquarters exterior
;**
;**	LSL5:  Passionate Patti Does A Little Undercover Work
;**				Copyright 1991
;**				by Al Lowe
;**
;**	Programmed by Oliver Brelsford
;**
;**	Last Update:  September 9, 1991
;**

(script#		425)

(public
	rm425		0
)

;(define		sCarDoorOpen		191)
;(define		sCarDoorClose		192)


(instance rm425 of LLRoom
	(properties
		picture		425
		north			435
		lookStr		"You are outside the F.B.I.'s office building,
						which is cleverly disguised as a block of normal
						storefront businesses.
						Your limousine is waiting for you at the curb,
						exactly as Desmond promised."
	)
	(method (init)
		(LoadMany SOUND
			sCarDoorOpen
			sCarDoorClose
		)
		(ego
			init:,
			normalize:	vPattiSmallest,
			edgeHit:		0,
		)
		(switch prevRoomNum
			(north
				NULL
			)
			(200
				(ego
					x: 		(limo approachX?),
					y:			(+ (limo approachY?) 10),
					hide:		,
				)
				(HandsOff)
				(self setScript: sFromLimo)
			)
			(else
				(ego
					posn:		160 160,
				)
				(HandsOn)
			)
		)
		(super init:)
		(door init:)
		(limo
			init:				,
			approachVerbs:	verbDo
		)
		(steinSign init:)
		(steinLabs init:)
		(gynoSign init:)
		(gynoBuilding init:)
		(woodwindSign init:)
		(woodwindBuilding init:)
;**			(curRoom addObstacle:
;**				((Polygon new:)
;**					type: PBarredAccess,
;**					init:	0 0 184 0 185 153 96 153 96 149 3 149 3 156 313 156 313 153
;**							195 153 195 0 319 0 319 189 0 189,
;**					yourself
;**				)
;**			)
		(curRoom addObstacle:
			((Polygon new:)
				type: PBarredAccess,
				init:	0 0 184 0 185 153 12 153 12 156 305 156 305 153 195 153 195 0
						319 0 319 189 0 189,
				yourself
			)
		)
	)
)


;ษอออออออออออออออออออป
;บ                   บ
;บ Scripts				บ
;บ                   บ
;ศอออออออออออออออออออผ

(instance sLimo of Script
	(method (changeState newState)
		(switchto (= state newState)
			(
				(= ticks 150)
			)
			(
				(soundFX
					number:		sCarDoorOpen,
					play:
				)
				(= ticks 50)	; when sys bug fixed, do play: self instead
			)
			(
				(= ticks 60)
			)
			(
				(ego
					setCycle:	0,
					setCel:		(ego cel?),
					setLoop:		(ego loop?),
					setMotion:	MoveTo (ego x?) (+ (ego y?) 10) self
				)
			)
			(
				(ego hide:)
				(= ticks 60)
			)
			(
				(soundFX
					number:		sCarDoorClose,
					play:
				)
				(= ticks 100)	; when sys bug fixed, do play: self instead
			)
			(
				(curRoom newRoom: 200)
			)
		)
	)
)

(instance sFromLimo of Script
	(method (changeState newState)
		(switchto (= state newState)
			(
				(= cycles 3)
			)
			(
				(soundFX
					number:		sCarDoorOpen,
					play:			; when sys bug fixed, do play: self instead
				)
				(= ticks 50)
			)
			(
				(= ticks 60)
			)
			(
				(ego
					show:			,
					setLoop:		3,
					setCel:		(ego cel?),
					setMotion:	MoveTo (ego x?) (- (ego y?) 10) self
				)
			)
			(
				(ego
					normalize: 	vPattiSmallest,
					setHeading:	180 self
				)
			)
			(
				(= ticks 60)
			)
			(
				(soundFX
					number:		sCarDoorClose,
					play:
				)
				(= ticks 100)	; when sys bug fixed, do play: self instead
			)
			(
				(HandsOn)
				(self dispose:)
			)
		)
	)
)


;ษอออออออออออออออออออป
;บ                   บ
;บ Props					บ
;บ                   บ
;ศอออออออออออออออออออผ

(instance door of Door
	(properties
		view			425
		loop			0
		cel			0
		x				193
		y				151
		description "the door"
		entranceTo  435
		enterType	doorWalkEgo
		exitType		doorWalkEgo
		approachX	188	;195
		approachY	156
		moveToX		188	;195
		moveToY		150
		lookStr		"This door leads back into the F.B... oops!
						This door leads to \"Stein Labs!\""
	)
)


;ษอออออออออออออออออออป
;บ                   บ
;บ Features				บ
;บ                   บ
;ศอออออออออออออออออออผ

(instance limo of Feature
	(properties
		x 				72
		y 				158
		z 				0
		heading 		0
		nsLeft 		3
		nsTop 		146
		nsBottom 	171
		nsRight 		161
		description	"your limousine"
		lookStr		"Your limousine awaits you."
		sightAngle 	40
		approachX	72
		approachY	156
	)
	(method (doVerb theVerb)
		(switch theVerb
			(verbDo
				(HandsOff)
				(curRoom setScript: sLimo)
			)
			(else
				(super doVerb: theVerb &rest)
			)
		)
	)
)

(instance steinSign of Feature
	(properties
		x 133
		y 148
		z 27
		heading 0
		nsLeft 101
		nsTop 108
		nsBottom 134
		nsRight 166
 		description "the Stein Labs sign"
		lookStr 		"The large sign identifies the F.B.I. labs as
						\"Stein Labs--We'll Be Frank With You.\""
		sightAngle 40
	)
)
(instance steinLabs of Feature
	(properties
		x 165
		y 93
		z 0
		heading 0
		nsLeft 92
		nsTop 37
		nsBottom 150
		nsRight 238
 		description "Stein Laboratories"
		lookStr 		"Don't you just love an F.B.I. with a sense of humor?"
		sightAngle 40
	)
)
(instance gynoSign of Feature
	(properties
		x 63
		y 146
		z 47
		heading 0
		nsLeft 55
		nsTop 76
		nsBottom 123
		nsRight 71
 		description		"the GynoMan sign"
		lookStr 			"Dr. Phil Hopian's office is cleverly
							disguised as \"GynoMan.\""
		sightAngle 40
	)
)
(instance gynoBuilding of Feature
	(properties
		x 45
		y 102
		z 0
		heading 0
		nsLeft 0
		nsTop 58
		nsBottom 146
		nsRight 91
 		description 	"the GynoMan building"
		lookStr 			"You wonder what sort of offices are on the upper floors
							of this building, above the office of Dr. Phil Hopian."
		sightAngle 40
	)
)
(instance woodwindBuilding of Feature
	(properties
		x 278
		y 98
		z 0
		heading 0
		nsLeft 238
		nsTop 45
		nsBottom 151
		nsRight 319
 		description "the Woodwind Repair Shop building"
		lookStr 		"The Woodwind Repair Shop is an unusual cover for an
						F.B.I. office."
		sightAngle 40
	)
)
(instance woodwindSign of Feature
	(properties
		x 286
		y 151
		z 73
		heading 0
		nsLeft 254
		nsTop 66
		nsBottom 91
		nsRight 319
 		description "the Woodwind Repair Shop sign"
		lookStr 		"The Woodwind Repair Shop has an attractive sign
						out front."
		sightAngle 40
	)
)

;ษอออออออออออออออออออป
;บ                   บ
;บ Sounds				บ
;บ                   บ
;ศอออออออออออออออออออผ

(instance soundFX of Sound)

