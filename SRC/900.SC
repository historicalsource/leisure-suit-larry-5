;**
;**	Logics for Room 900 -- Doc Pulliam's Dental Hygiene Heaven, exterior
;**
;**	LSL5:  Passionate Patti Does A Little Undercover Work
;**				Copyright 1991
;**				by Al Lowe
;**
;**	Brute-force programming by C. Escobar
;**
;**	Last Update:  September 12, 1991
;**


(script#		900)

(public
	rm900		0
)

(define	ENTER_X 					81)
(define  ENTER_Y					177)

(define	sfxOutside 				251)

(define	vOutSideDoc's			900)

(define	sfxCarDrivesAway 		194)
(define	sfxCarDoorOpen			191)
(define	sfxCarDoorClose		192)


(instance rm900 of LLRoom
	(properties
		picture		900
		north			905
	)

	(method (init)

		(Load SOUND sfxCarDrivesAway)
		(Load SOUND sfxCarDoorOpen)
		(Load SOUND sfxCarDoorClose)

		(self addObstacle:
			((Polygon new:)
				type: PBarredAccess,
				init:	0 0 127 0 127 111 119 116 77 116 77 118 126 118 70 156 55 157
						41 163 9 165 9 169 166 169 310 169 309 166 231 162 55 162
						55 160 72 158 131 118 230 118 231 116 135 116 130 109 130 0
						319 0 319 189 0 189,
				yourself
			)
		)

;**	
;**			(self addObstacle:
;**				((Polygon new:)
;**					type: PBarredAccess,
;**					init:	0 0 127 0 126 116 77 116 77 118 126 118 70 156 55 157 41 163
;**							9 165 9 169 166 169 310 169 309 166 231 162 55 162 55 160
;**							72 158 131 118 230 118 231 116 130 116 130 0 319 0 319 189
;**							0 189,
;**					yourself
;**				)
;**			)

		(ego
			init:,
			normalize:	552, 			;vLarrySmaller,
			setStep:		2 2,
		)

		;set style before super

		(if (== prevRoomNum 920)
			(SetupExit FALSE)
			(Bset fSeenCC) 				;so we can test limo
			(self style IRISOUT)
		)

		(if (not larryLoc)			;for you teleporting-chingadera types
			(= larryLoc Miami)
		)

		(super init:)

		(if (Btst fCalledLimo)
			(limo
				init:,
				approachVerbs: verbDo
			)
		)

		(switch prevRoomNum
			(920		  			;gym routine
				(HandsOn)
				(ego
					posn:				214 163 0,
					setHeading:		180,
					edgeHit:			0
				)
			)
			(north
				(HandsOn)
				(ego
					posn:				123 116 0,
					setHeading:		180,
					setPri:			4,
				)
				(theMusic
					number:			0,
					stop:
				)
				(theMusic2 number: 0)
			)
			(else
				(= larryLoc Miami)
				(Bclr fCalledLimo)
				(limo	init:)
				(curRoom setScript sExitLimo)
			)
		)


		(tooth
			init:,
			cycleSpeed:	6,
			setCycle:	Forward,
		)

		(palmTrees	init:)
		(gymWindow	init:, approachVerbs verbDo verbLook)
		(gymSign		init:)
		(docSign		init:)
		(docDoor 	init:, setPri: 1)
		(theDoor		init:, approachVerbs verbDo)
		(gymDoor		init:, approachVerbs verbDo)
		(building	init:)
		(buildings	init:)
		(sidewalk	init:)
		(fence		init:)
		(stairs		init:)
		(window1		init:, approachVerbs verbDo verbLook)
		(window2		init:, approachVerbs verbDo verbLook)

		(if (== prevRoomNum 920)
			(HandsOff)
			(Delay 3 SECONDS curRoom)
		)

		(theMusic3
			number:				sfxOutside,				;251
			loop:					-1,
			play:
		)

		(SetupExit FALSE)
		(SetFFRoom 0)

	);init

	(method (cue)
		(TPrint "You vow to get in shape!")
		(HandsOn)
	)


	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbLook
				(if (< (ego y?) 130)
					(TPrint "From up here, you can see the Atlantic Ocean.")
					(TPrint "But, looking through the windows, all you can see
						is dental equipment and a lot of people wearing smocks!")
				else
					(TPrint "A closed gymnastics studio is on the ground floor,
						while a dentist's office occupies the second floor.")
				)
			)
			(else
				(super doVerb: theVerb invItem &rest)
			)
		)
	)

	(method (doit)
		(super doit:)
		(cond
			(script)
			((and	(< (ego x?) 88) (< (ego y?) 130))
				NULL
			)
			((and	(!= (ego priority?) 4)
					(< (ego y?) 162)
					(> (ego y?) 120))
				(ego setPri: 4)
			)
			((and (!= (ego priority?) 2)	(< (ego y?) 120))
				(ego setPri: 2)
			)
			((and (== (ego priority?) 4)	(> (ego y?) 161))
				(ego setPri: RELEASE)
			)
		)
	)
)



(instance palmTrees of Feature
	(properties
		x					37
		y					101
		z					0
		heading			0
		nsLeft			20
		nsTop				62
		nsBottom			141
		nsRight			70
 		description		"the palm trees"
		lookStr			"Miami is filled with exotic palm trees just like these."
		sightAngle		40
	)
)

(instance gymWindow of Feature
	(properties
		x					162
		y					140
		z					0
		heading			0
		nsLeft			140
		nsTop				127
		nsBottom			150
		nsRight			185
 		description		"the gymnastics studio"
		sightAngle		40
		approachX		165
		approachY		162
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbLook
				(TPrint "It looks like there is no one inside the gymnastics
					studio, although the mini-blinds severely handicap your view.")
			)
			(verbDo
				(TPrint "You are unable to part the mini-blinds to see inside
					since blinds are commonly placed on the INSIDE of windows!")
			)
			(else
				(super doVerb: theVerb invItem &rest)
			)
		)
	)
)

(instance docSign of Feature
	(properties
		x					182
		y					42
		z					0
		heading			0
		nsLeft			135
		nsTop				24
		nsBottom			61
		nsRight			225
 		description 	"the sign"
		lookStr 			"The sign reads,
							\"Doc Pulliam's Dental Hygiene Heaven.\""
		sightAngle		40
	)
)

(instance gymDoor of Feature
	(properties
		x 					215
		y 					146
		z 					0
		heading 			0
		nsLeft 			195
		nsTop 			132
		nsBottom 		161
		nsRight 			235
 		description 	"the gymnastics studio door"
		sightAngle		40
		approachX		214
		approachY		163
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbLook
				(TPrint "The door leading to the gymnastics studio is locked.")
			)
			(verbDo
				(TPrint "You try the door and find it is locked.")
			)
			(else
				(super doVerb: theVerb invItem &rest)
			)
		)
	)
)

(instance docDoor of Door
	(properties
		view				vOutSideDoc's			;900
		loop				1
		x 					114
		y 					104		;extra 100 y
		z 					-10
		sightAngle 		40
		priority			1
 		description 	"the dentist's office door"
		lookStr 			"The door leading to Doc Pulliam's Dental Hygiene Heaven
							is unlocked."
		approachX		128
		approachY      118
		moveToX			128
		moveToY			115				; where to move to when going to door

		entranceTo 		905				; room # beyond the door
		locked 			FALSE
;		heading			360					; heading in which ego should exit

		exitType			doorWalkEgo
		enterType		doorWalkEgo
	)
)


(instance building of Feature
	(properties
		x 					157
		y 					91
		z 					-20
		heading 			0
		nsLeft 			65
		nsTop 			60
		nsBottom 		162
		nsRight 			239
 		description 	"the building"
		lookStr 			"You are outside the building where Chi Chi Lambada
							is employed: \"Doc Pulliam's Dental Hygiene Heaven.\"
							Immediately below it is a closed gymnastics studio,
							\"The Gym-Dandy Gymnastics Center.\""
		sightAngle 		40
	)
)

(instance sidewalk of Feature
	(properties
		x 					169
		y 					170
		z 					0
		heading 			0
		nsLeft 			0
		nsTop 			162
		nsBottom 		178
		nsRight 			319
 		description 	"the sidewalk"
		lookStr 			"Carefully inspecting the sidewalk,
							you conclude Doc Pulliam doesn't spend much on
							filling cavities out here!"
		sightAngle 		40
	)
)

(instance fence of Feature
	(properties
		x 					164
		y 					152
		z 					0
		heading 			0
		nsLeft 			0
		nsTop 			139
		nsBottom 		166
		nsRight 			308
 		description 	"the fence"
		lookStr 			"A stone wall decorates an otherwise
							perfunctory building."
		sightAngle 		40
		onMeCheck		cGREEN
	)
)

(instance gymSign of Feature
	(properties
		x					190
		y					119
		z					0
		heading			0
		nsLeft			145
		nsTop				109
		nsBottom			129
		nsRight			230
 		description		"the gymnastic studio sign"
		lookStr			"The sign says, \"Gym-Dandy Gymnastics Center.\""
		sightAngle		40
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbDo
				(TPrint "The \"Gym-Dandy Gymnastics Center's\" sign is made
					of virgin polyvinyl chloride.")
			)
			(else
				(super doVerb: theVerb invItem &rest)
			)
		)
	)
)



(instance window1 of Feature
	(properties
		x					184
		y					190
		z					100
		heading			0
		nsLeft			145
		nsTop				78
		nsBottom			102
		nsRight			225
 		description		"the window"
		lookStr			"You don't need to peek in those windows!"
		sightAngle		40
		approachX		167
		approachY		115
	)
)

(instance window2 of Feature
	(properties
		x					92
		y					190
		z					100
		heading 			0
		nsLeft 			89
		nsTop 			78
		nsBottom 		100
		nsRight 			106
 		description		"the window"
		lookStr			"You don't need to peek in those windows!"
		sightAngle		40
		approachX		95
		approachY		119
	)
)

(instance stairs of Feature
	(properties
		x					101
		y					157
		z					25
		heading			0
		nsLeft			70
		nsTop				106
		nsBottom			158
		nsRight			135
 		description		"the stairway"
		lookStr			"A flight of stairs leads up to Doc Pulliam's office."
		sightAngle		40
		onMeCheck		cCYAN
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbDo
				(if (< (ego y?) 130)
					(ego setMotion:		PolyPath 57 164)
				else
					(ego setMotion:		PolyPath 126 113)
				)
			)
			(else
				(super doVerb: theVerb invItem &rest)
			)
		)
	)
)
(instance tooth of Prop
	(properties
		view			vOutSideDoc's 				;900
		loop			2
		x				115
		y				64
		nsLeft 		101
		nsTop 		27
		nsBottom 	64
		nsRight 		129
		description	"the revolving tooth"
		lookStr		"Take the Ventura Freeway to the Fontainebleau Freeway
						to the Eisenhower Turnpike to the Slaussen Cutoff.
						Get out of your car and cut off your slaussen.
						Get back in your car and continue on until you reach
						the Beach Front Highway.  Take the Beach Front Highway
						to 22nd Street Southwest and stop when you come
						to the giant revolving tooth!
						You're there! Doc Pulliam's Dental Hygiene Heaven!"
		signal		(| fixedLoop fixPriOn ignrAct ignrHrz)
	)
)

(instance limo of Actor
	(properties
		view				vOutSideDoc's 			;900
		loop				0
		x					65
		y					179
		description		"the limousine"
		lookStr			"It's your limousine, awaiting your entrance."
		priority			14
		signal			(| fixedLoop fixPriOn ignrAct)
		approachX		81
		approachY		167
	)
	(method (doVerb theVerb)
		(switch theVerb
			(verbDo
				(HandsOff)
				(curRoom setScript sEnterLimo)
			)
			(else
				(super doVerb: theVerb &rest)
			)
		)
	)
)
(instance buildings of Feature
	(properties
		x 					159
		y 					81
		z 					0
		heading 			0
		nsLeft 			0
		nsTop 			0
		nsBottom 		162
		nsRight 			319
 		description 	"the building"
		lookStr 			"There are many buildings in a city as large as Miami."
		sightAngle 		40
		onMeCheck 		cBLUE
	)
)


(instance sExitLimo of Script
	(method (changeState newState &tmp var1 var2)
		(switchto (= state newState)
			(
				(HandsOff)
				(ego
					hide,
					setHeading:		0,
					posn:				ENTER_X ENTER_Y 0,
				)
				(= cycles 2)
			)

			(
				(theMusic2
					number:			sfxCarDoorOpen,
					setLoop:			1,
					play:
				)
				(ego
					show,
					setMotion:		MoveTo (limo approachX) (limo approachY)
				)
				(= ticks 50)
			)

			(
				(= ticks 10)
			)

			(
				(theMusic2
					number:			sfxCarDoorClose,
					setLoop:			1,
					play:
				)
				(= ticks 100)
			)

			(
				(ego
					setLoop:			-1,
					setHeading: 	180 self,
				)
			)

			(
				(self setScript sLimoLeaves self)
			)

			(
				(HandsOn)
				(ego
					normalize:	552, 			;vLarrySmaller,
					setStep:		2 2,
				)
				(limo dispose:)
				(= cycles 1)
			)

			(
				(self dispose:)
				(if (Print "Now would be an excellent time for you to save your
						game, Larry."
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
				(= register (theMusic2 flags?))
				(theMusic2
					number:				sfxCarDrivesAway,
					setLoop:				1,
					flags:				0,
					play:
				)
				;we get cue 10 from sound
			)

			(
				(limo
					illegalBits:		0,
					ignoreActors:		TRUE,
					setStep:				3 3,
					setMotion:			MoveTo (- (limo x?) 20) (limo y?) self
				)
			)

			(
				(limo
					setStep:				5 5,
					setMotion:			MoveTo (- (limo x?) 20) (limo y?) self
				)
			)

			(
				(limo
					setStep:				8 8,
					setMotion:			MoveTo -80 (limo y?) self
				)
				(theMusic2
					fade:	0 15 12 STOP_MUSIC,
					flags:				register,

				)
			)

			(
				(limo dispose:)
				(self dispose:)
			)
		)
	)
)

(instance theMusic3 of Sound)

(instance theDoor of Feature
	(properties
		x 						125
		y 						99
		z 						0
		heading 				0
		nsLeft 				112
		nsTop 				82
		nsBottom 			117
		nsRight 				139
 		description 		"the dentist's office door"
		lookStr 				"The door leading to Doc Pulliam's Dental Hygiene Heaven
								is unlocked."
		sightAngle 			40
		approachX			128
		approachY     		116
	)
	(method (doVerb)
		(docDoor doVerb &rest)
	)
)


(instance sEnterLimo of Script
	(method (changeState newState &tmp var1 var2)
		(switchto (= state newState)
			(
				(HandsOff)
				(ego
					setHeading:		180 self,
				)
			)

			(
				(ego
					setMotion:		MoveTo ENTER_X ENTER_Y self,
				)
				(= ticks 10)
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
				NULL
			)

			(
				(ego hide)
				(theMusic2
					number:			sfxCarDoorClose,
					setLoop:			1,
					play:
				)
				(= ticks 120)
			)

;**				(
;**					(self setScript sLimoLeaves self)
;**				)

			(
				(curRoom newRoom 200)
			)
		)
	)
);sEnterLimo

