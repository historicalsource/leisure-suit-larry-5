;**
;**	Logics for Room 500 -- Hard Disk Cafe exterior
;**
;**	LSL5:  Passionate Patti Does A Little Undercover Work
;**				Copyright 1991
;**				by Al Lowe
;**
;**	Originally programmed by C. Escobar
;**
;**	Last Update:  September 9, 1991
;**

;**	NOTE: no lava lamps if in 16 color


(script#		500)

(public
	rm500		0
)

(local
	cycleTime
)


(define	sfxCarDrivesAway 		194)
(define	sfxCarDoorOpen 		191)
(define	sfxCarDoorClose		192)
(define	mHardRock1				500)
(define	mHardRock2				501)
(define	mHardRock3				502)

(define	vRoom500 				500)


(instance rm500 of LLRoom
	(properties
		picture		500
		north			510
	)
	(method (init)
		;DEBUG CODE
;		(Bset fCalledLimo)
		(switch prevRoomNum
			(north
				(theMusic fade: 80 10 10 FALSE)
				(ego
					init:,
					normalize:,
					edgeHit: 0,	  	;needed
				)
			)
			(else
				(theMusic
					number: 		  	0,
					stop:
				)
				(ego
					init:,
					normalize:,
					posn: 			24 177 0,
					setLoop:			RELEASE,
					setHeading:		180,
					edgeHit:			0
				)
				(limo init:)
				(= larryLoc NewYork)
				(curRoom setScript: sExitLimo)
			)
		)
		(super init:)
		(door
			init:,
			approachVerbs: verbDo
		)
		(doorR
			init:,
			approachVerbs: verbDo
		)

		;if you've called the limo or are exiting limo...
		(if (or 	(== prevRoomNum 200) (Btst fCalledLimo))
			(limo
				init:,
				approachVerbs: verbDo
			)
		)
		(curRoom addObstacle:
			((Polygon new:)
				type: PBarredAccess,
				init:	145 143 124 157 32 157 32 147 72 147 73 145 3 145 3 165
						315 165 315 145 261 145 260 147 295 147 295 156 205 156
						190 143 158 143 151 0 319 0 319 189 0 189 0 0 148 0,
				yourself
			)
		)
		(lavaLamp1 init:)
		(lavaLamp2 init:)
		(plants1 init:)
		(plants2 init:)
	);init

	(method (doit)
		(super doit:)
		(++ cycleTime)
		(if (not (mod cycleTime 10))
			(Palette PALCycle 239 254 -1) ;fromWhat toWhat direction
		)
		(cond
			(script)
			((and (StepOn ego cBLUE)
					(not (Btst pfPissed)))
				(HandsOff)
				(curRoom setScript: sPiss)
			)
		)
	)

	(method (doVerb theVerb)
		(switch theVerb
			(verbLook
				(TPrint "The exterior of \"The Hard Disk Cafe\" is in keeping
					with their computer memorabilia motif.")
				(return TRUE)
			)
			(verbDo
				(cond
					((== prevRoomNum 200)
						(TPrint "Your limo is gone, Larry. Why not walk inside?")
					)
					((Btst fCalledLimo)
						(TPrint "The limousine you ordered has arrived.
							Why not get in?")
					)
					(else
						(TPrint "You try in vain to find a means of transportation,
							but to no avail.
							How will you ever be able to telephone that limo
							company?")
					)
				)
				(return TRUE)
			)
			(else
				(super doVerb: theVerb &rest)
			)
		)
	)
)

;	SCRIPTS


(instance sPiss of Script
	(method (changeState newState)
		(switchto (= state newState)
			(
				(Points ptsPissed pfPissed)
				(= seconds 3)
			)
			(
				(Say ego "\"Ahhh!\"")
				(HandsOn)
				(self dispose:)
			)
		)
	)
)

(instance sExitLimo of Script
	(method (changeState newState &tmp var1 var2)
		(switchto (= state newState)
			(
				;give game time enough for pic to draw...
				(= cycles 2)
			)
			(
				(HandsOff)
				(theMusic2
					number:			sfxCarDoorOpen,
					setLoop:			1,
					play:
				)
				(= ticks 50)
			)
			(
				(ego
					setLoop:		 	3,
					setCel:		  	0,
					setMotion		MoveTo 24 163 self,
				)
			)
			(
				(ego
					setLoop:			RELEASE,
					setHeading: 	180,
				)
				(= ticks 10)
			)

			(
				(theMusic2
					number:			sfxCarDoorClose,
					setLoop:			1,
					play:
				)
				(= ticks 110)
			)

			(
				(WrapMusic
					firstSound: 	mHardRock1,
					lastSound: 		mHardRock3
				)
				(theMusic
					number: 			mHardRock1,
					play: 			WrapMusic,
					setVol:			80,
					flags: 			TRUE,
					setLoop: 		1,
				)
				(self setScript: sLimoLeaves self)
			)

			(
				(HandsOn)
				(limo dispose:)
				(= cycles 1)
			)
			(
				(ego 	normalize:)
				(self dispose:)
				(if
					(Print "Now would be an excellent time for you to save your
						game, Larry."
						#button		{Save} 				1
						#button		{Nah, Why Bother?} 0
						#title		{AL says}
						HIGH
					)
				 	(theGame save:)
				)
			)
		)
	)
)

(instance sEnterLimo of Script
	(method (changeState newState &tmp var1 var2)
		(switchto (= state newState)
			(
				(HandsOff)
				(ego
					setLoop:		RELEASE,
					setHeading: 180 self
				)
			)
			(
				(theMusic2
					number:			sfxCarDoorOpen,
					setLoop:			1,
					play:
				)
				(= ticks 50)
			)
			(
				(ego
					setMotion:		MoveTo 23 183 self
				)
			)

			(
				(ego hide:)
				(= ticks 10)
			)

			(
				(theMusic2
					number:			sfxCarDoorClose,
					setLoop:			1,
					play:
				)
				(= ticks 110)
			)

; took this out 9/22/91 to make Q.A happy  (CE)
;
;			(
;				(self setScript sLimoLeaves self)
;			)

			(
				(curRoom newRoom: 200)
			)
		)
	)
)

(instance sLimoLeaves of Script
	(method (doit)
		(super doit:)
		(if (== (theMusic2 prevSignal?) 10)
			(theMusic2 prevSignal: 0)
			(self cue:)
		)
	)
	(method (changeState newState &tmp var1 var2)
		(switchto (= state newState)
			(
				(theMusic2
					number:			sfxCarDrivesAway,
					setLoop:			1,
					play:
				)
			)
			(
				(limo
					setStep:  		2 2,
					setMotion:		MoveTo 67 (limo y?) self,
				)
			)
			(
				(limo
					setStep:	 		4 4,
					setMotion:		MoveTo 45 (limo y?) self,
				)
			)
			(
				(limo
					setStep:	 		6 6,
					setMotion:		MoveTo 15 (limo y?) self,
				)
			)
			(
				(limo
					setStep 			9 9,
					setMotion		MoveTo -25 (limo y?) self,
				)
			)
			(
				(limo
					setStep 			12 12,
					setMotion		MoveTo -95 (limo y?) self,
				)
			)
			(
				(self dispose:)
			)
		)
	)
)

;	ACTORS


(instance limo of Actor
	(properties
		view				vRoom500   ;500
		loop				0
		x					75
		y					189
		description		"your limousine"
		lookStr			"Why do all these limos look alike?"
		priority	 		14
		signal			(| fixedLoop fixPriOn ignrAct)
		approachX		23
		approachY		163
	)
	(method (doVerb theVerb)
		(switch theVerb
			(verbDo
				(HandsOff)
				(theMusic fade:)
				(curRoom setScript sEnterLimo)
			)
			(else
				(super doVerb: theVerb &rest)
			)
		)
	)
)

(instance doorR of Feature
	(properties
		x 188
		y 98
		z 0
		heading 0
		nsLeft 168
		nsTop 56
		nsBottom 141
		nsRight 209
 		description 	"the front door"
		lookStr 			"If you could figure out how to open this door,
							you might enter \"The Hard Disk Cafe.\""
		sightAngle 40
		approachX		154
		approachY		144
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbDo
				(door open)
			)
			(else
				(super doVerb: theVerb invItem &rest)
			)
		)
	)
)

(instance door of Door
	(properties
		view				vRoom500
		loop				2
		cel				0
		x					146
		y					141
		description		"the front door"
		sightAngle 		40
		entranceTo 		510				; room # beyond the door
		locked 			FALSE
		moveToX			156
		moveToY			135				; where to move to when going to door
		heading			0					; heading in which ego should exit
		lookStr 			"If you could figure out how to open this door,
							you might enter \"The Hard Disk Cafe.\""
		approachX		154
		approachY		144
		exitType			doorWalkEgo
		enterType		doorWalkEgo
	)
)

(instance lavaLamp1 of Feature
	(properties
		x 76
		y 97
		z 0
		heading 0
		nsLeft 41
		nsTop 41
		nsBottom 153
		nsRight 111
 		description		"the lava lamp"
		lookStr			"Wow! A giant lava lamp!
							(You've always loved those things!)"
		sightAngle		40
		onMeCheck		cCYAN
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbDo
				(TPrint "It feels so warm to the touch.")
			)
			(else
				(super doVerb: theVerb invItem &rest)
			)
		)
	)
)
(instance lavaLamp2 of Feature
	(properties
		x 256
		y 93
		z 0
		heading 0
		nsLeft 220
		nsTop 33
		nsBottom 154
		nsRight 292
 		description 	"the lava lamp"
		lookStr			"Wow! A giant lava lamp!
							(You've always loved those things!)"
		sightAngle		40
		onMeCheck		cCYAN
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbDo
				(TPrint "It feels so warm to the touch.")
			)
			(else
				(super doVerb: theVerb invItem &rest)
			)
		)
	)
)

(instance plants1 of Feature
	(properties
		x 76
		y 97
		z 0
		heading 0
		nsLeft 41
		nsTop 41
		nsBottom 153
		nsRight 111
 		description		"the plant"
		lookStr			"These plants grow here in spite of the atmosphere."
		sightAngle		40
		onMeCheck		cGREEN
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(else
				(super doVerb: theVerb invItem &rest)
			)
		)
	)
)

(instance plants2 of Feature
	(properties
		x 256
		y 93
		z 0
		heading 0
		nsLeft 220
		nsTop 33
		nsBottom 154
		nsRight 292
 		description		"the plant"
		lookStr			"These plants grow here in spite of the atmosphere."
		sightAngle		40
		onMeCheck		cGREEN
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(else
				(super doVerb: theVerb invItem &rest)
			)
		)
	)
)

