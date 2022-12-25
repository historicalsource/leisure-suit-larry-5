;**
;**	Logics for Room 905 -- Doc Pulliam's waiting room
;**
;**	LSL5:  Passionate Patti Does A Little Undercover Work
;**				Copyright 1991
;**				by Al Lowe
;**
;**	Thrown together by C. Escobar
;**
;**	Last Update:  Friday, September 20, 1991 
;**


(script#		905)

(public
	rm905		0
)

(procedure
	NormalEgo
)

(define	GET_DOILY_X					287)
(define	GET_DOILY_Y					159)

(define 	WINDOW_AX 					134)
(define 	WINDOW_AY					109)

(define	FACING_NORTH				3)
(define	FACING_NORTHEAST			6)
(define	FACING_EAST					0)
(define	FACING_SOUTHEAST			4)
(define	FACING_SOUTH				2)
(define	FACING_SOUTHWEST			5)
(define	FACING_WEST					1)
(define	FACING_NORTHWEST			7)


(define  sfxKnockGlass				911)
(define 	sfxGlassDoor 	  			171)

(define	mDentistMusak				905)

(define	pDentistWaitingRoom		905)
(define	vDentistWaitingRoom		905)
(define	vLarryInWaitingRoom		906)
;(define 	vLarryW/Doily			907)	;defined in graphics.sh?
;(define	vLarryWearingDoily 		908)
(define	vReceptionist				909)
(define	vReceptionistTalker		1909)

(define	ON		1)
(define	OFF	0)

;dialer returns:
;-1 		no number
;0 		wrong prefix
;number 	(might be wrong but correct prefix)

(define	NO_NUMBER 	-1)
(define	WRONG_NUMBER  0)

(enum
	NO_ONE
	CORRECT_LIMO
	SECRETARY
	WRONG_LIMO
)

(define	lSit							1)
(define	lFidgeting 					2)
(define	lUsingPhone					3)

(define	wearingDoily
	(== ((Inventory at: iDoily) state?) ON)
)

(local
	seenDoilyMsg
	talkedToGirl
	talkCounter
	usePhone
	cueCounter
	whoToTalkTo
	onPhone
	gotEvent
	returningFrom910
	windowTalkCount
	satDown
)


(instance rm905 of LLRoom
	(properties
		picture		pDentistWaitingRoom
		south			900
		east			910
		lookStr		"You are inside the waiting room of Doc Pulliam's Dental
						Hygiene Heaven.
						You love the carpet motif;
						in fact, you feel sure you seen something a lot like
						it before... somewhere."
	)

	(method (init)
		(SetupExit FALSE)
		(SetFFRoom 0)

		(Load VIEW vLarryW/Doily)
		(Load VIEW vLarryWearingDoily)
		(Load VIEW vLarryInWaitingRoom)

		(ego
			init,
			normalize,
		)

		(HandsOn)

		(switch prevRoomNum
			(900
				;this is needed because ego is able to walk
				;on ceiling
				(self setScript sEnterFromOutside)
			)
			(910
				(ego
					edgeHit: 0,			;needed
				)
			)
			(915
				(door entranceTo: 915)
				(ego
					edgeHit: 0,			;needed
				)
			)
			(else
				(= larryLoc Miami)				;DEBUG CODE
				(ego
					posn: 		172 160,
				)
			)
		)

		(super init:)

		(self addObstacle:
			((Polygon new:)
				type: PBarredAccess,
				init:	319 52 205 117 199 113 180 119 173 108 151 106 69 116 62 120
						63 125 54 135 63 140 59 153 48 169 35 174 38 187 310 187
						273 165 282 160 264 158 247 144 236 144 211 129 231 116
						319 114 319 189 0 189 0 0 319 0,
				yourself
			)
		)

		(nurseWindow
			init:,
			approachVerbs: verbLook verbTalk verbDo,
		)
		(glass
			init:,
			approachVerbs: verbLook verbTalk verbDo,
			stopUpd,
		)
		(if (not (Btst fSeenCC))
			(girl init:)
		)
		(phone
			init:,
			approachVerbs verbDo
		)

		;init features
		(toothTable init:, approachVerbs: verbDo verbLook)
		(toothTable2 init:, approachVerbs: verbDo verbLook)
		(degrees		init:)
		(rTable
			init:,
			approachVerbs verbDo
		)
		(lTable
			init:,
			approachVerbs verbDo
		)
		(lChair
			init:,
			approachVerbs: verbDo
		)
		(rChair		init:)
		(rChair2		init:)
		(pic2			init:)
		(palm			init:, approachVerbs: verbDo verbLook)
		(palm2		init:, approachVerbs: verbDo verbLook)
		(lipPic		init:)
		(magazines	init:, approachVerbs: verbDo verbLook verbUse)
		(thePhone	init:, approachVerbs: verbDo)

		(if (and (not (Btst fUsedDoily))
					(not (ego has:iDoily)))
			(laceDoily
				init:,
				stopUpd,
				approachVerbs verbDo
			)
		)
		(door
			init:,
		)
		(theMusic
			number:			mDentistMusak,				;910
			setLoop:			FOREVER,						;-1
			play:
		)
	);init

	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbDo
				(if (OneOf (ego view?) vLarryInWaitingRoom vLarryW/Doily)
					(TPrint "There is nothing you need to do there.")
				else
					(super doVerb: theVerb invItem &rest)
				)
			)
			(else
				(super doVerb: theVerb invItem &rest)
			)
		)
	)

	(method (cue)
;		(super cue:)

		(if returningFrom910
			(TPrint "Your teeth feel so clean,
				you won't need to floss for a month!")
			(= returningFrom910 FALSE)
			(HandsOn)
		else
			;putting on taking off doily
			(NormalEgo FACING_SOUTH)
		)
	)

	(method (doit)
		(super doit:)
		(cond
			(script
			)
			((StepOn ego cBLUE)
				(if (== (ego view?) vLarry)
					(self setScript sExitToOutside)
				else
					(if (not seenDoilyMsg)
						(= seenDoilyMsg TRUE)
						(TPrint "You would be too embarrassed to walk outside
							with that silly doily wrapped around your head.")
					)
				)
			)
			((StepOn ego cCYAN)
				(= seenDoilyMsg FALSE)
			)
		)
	)

	(method (notify what)
		(= whoToTalkTo what)		;send it to script below
	)
)


;**	features

(instance magazines of Feature
	(properties
		x					54
		y					81
		z					0
		heading			0
		nsLeft			41
		nsTop				60
		nsBottom			103
		nsRight			67
 		description		"the magazines"
		sightAngle		40
		approachX		62
		approachY		120
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbUse
				(switch invItem
					(iMagazine
						(TPrint "Being totally bored with the AeroDork In-Flight
							Magazine, you shove it into Doc's magazine rack,
							where it is sure to delight and amaze his primarily
							Spanish-speaking customers.")
						(ego put: iMagazine)
						(Bset fDumpedMagazine)
					)
					(else
						(super doVerb: theVerb invItem &rest)
					)
				)
			)
			(verbLook
				(TPrint "After glancing at a few of the magazines,
							you decide you have no interest in learning
							what the stock market will do in 1969!"
				)
			)
			(verbDo
				(TPrint "You have no desire to read magazines that are older than you.")
			)
			(else
				(super doVerb: theVerb invItem &rest)
			)
		)
	)
)


(instance palm of Feature
	(properties
		x 					17
		y					232
		z					100
		heading			0
		nsLeft			0
		nsTop				75
		nsBottom			189
		nsRight			34
 		description		"the palm tree"
		lookStr			"You wonder if that palm tree will ever require a
							\"root canal?\""
		approachX		40
		approachY		184
		onMeCheck		cGREEN
		sightAngle		40
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbDo
			 	(TPrint "The palm tree feels rough to your touch.")
			)
			(else
				(super doVerb: theVerb invItem &rest)
			)
		)
	)
)
(instance palm2 of Feature
	(properties
		x					184
		y					74
		z					0
		heading			0
		nsLeft			163
		nsTop				34
		nsBottom			115
		nsRight			205
		sightAngle		40
		onMeCheck		cGREEN
		approachX		177
		approachY		117
	)
	(method (doVerb)
		(palm doVerb &rest)
	)
)
(instance lipPic of Feature
	(properties
		x					14
		y					67
		z					0
		heading			0
		nsLeft			0
		nsTop				41
		nsBottom			93
		nsRight			28
 		description		"the painting"
		lookStr			"You've always been a big fan of the
							\"Happy Tooth School\" of art."
		sightAngle		40
	)
)
(instance pic2 of Feature
	(properties
		x					277
		y					77
		z					0
		heading			0
		nsLeft			257
		nsTop				49
		nsBottom			106
		nsRight			298
 		description		"the painting"
		lookStr			"You've always been a big fan of the
							\"Happy Tooth School\" of art."
		sightAngle 		40
	)
)
(instance degrees of Feature
	(properties
		x					314
		y					84
		z					0
		heading			0
		nsLeft			310
		nsTop				59
		nsBottom			110
		nsRight			319
 		description		"Doc's diplomas"
		lookStr			"Funny. You've never heard of a dentist specializing in
							\"String and Doorknob\" dentistry.
							And where exactly is the \"School of Hard Knockers?\""
		sightAngle		40
	)
)
(instance toothTable of Feature
	(properties
		x 					190
		y					97
		z					0
		heading			0
		nsLeft			177
		nsTop				82
		nsBottom			113
		nsRight			203
 		description		"the table"
		lookStr			"You can tell that Doc's wife decorated this place.
							She's suffering from terminal cuteness!"
		sightAngle		 40
		approachX		177
		approachY		117
	)
)
(instance lChair of Feature
	(properties
		x					43
		y					137		;must be greater than Larry's y
		z					0
		heading			0
		nsLeft			28
		nsTop				100
		nsBottom			134
		nsRight			59
 		description		"the chair"
		lookStr			"This chair looks just right!"
		sightAngle		40
		approachX		62
		approachY		127
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbDo
				(ego setScript: sChair)
			)
			(else
				(super doVerb: theVerb invItem &rest)
			)
		)
	)
)
(instance rChair of Feature
	(properties
		x					282
		y					135
		z					0
		heading			0
		nsLeft			265
		nsTop				112
		nsBottom			159
		nsRight			300
 		description		"the chair"
		lookStr			"This chair looks too hard."
		sightAngle		40
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbDo
				(TPrint "You briefly consider sitting there,
					but decide that chair will be much too hard!")
			)
			(else
				(super doVerb: theVerb invItem &rest)
			)
		)
	)
)
(instance rChair2 of Feature
	(properties
		x					246
		y					123
		z					0
		heading			0
		nsLeft			225
		nsTop				103
		nsBottom 		143
		nsRight			268
 		description 	"the chair"
		lookStr			"This chair looks too soft."
		sightAngle		40
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbDo
				(TPrint "You briefly consider sitting there,
					but decide that chair will be much too soft!")
			)
			(else
				(super doVerb: theVerb invItem &rest)
			)
		)
	)
)
(instance lTable of Feature
	(properties
		x					32
		y					147
		z					0
		heading			0
		nsLeft			15
		nsTop				125
		nsBottom			170
		nsRight			50
 		description		"the table"
		lookStr			"This table contains a telephone,
							placed here for the convenience of Doc's customers."
		sightAngle		40
		approachX		59
		approachY		129
	)
	(method (doVerb)
		(phone doVerb &rest)
	)
)
(instance rTable of Feature
	(properties
		x					301
		y					159
		z					0
		heading			0
		nsLeft			284
		nsTop				141
		nsBottom			177
		nsRight			319
 		description		"the table"
		sightAngle		40
		approachX		GET_DOILY_X
		approachY		GET_DOILY_Y
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbLook
				(if (ego has: iDoily)
					(TPrint "There's nothing left on this table
						since you took Doc's doily!")
				else
					(TPrint "The only thing on this table is a long, lacy doily.
						It reminds you of those long runners your Grandmother
						had on her table.")
				)
			)
			(verbDo
				(cond
					((ego has: iDoily)
						(TPrint "You've already taken the doily.
							You have no use for the table.")
					)
					((OneOf (ego view?)	vLarryInWaitingRoom vLarryW/Doily)
						(ego setScript: sChair NULL self)
					)
					((IsObject laceDoily)
						(laceDoily doVerb theVerb)
					)
				)
			)
			(verbUse
				(if (== invItem iDoily)
					(TPrint "Something inside tells you that you need the doily
						more than the table does.")
				else
					(super doVerb: theVerb invItem)
				)
			)
		)
	)
)


;**	props

(instance glass of Prop
	(properties
		view				vDentistWaitingRoom		;905
		loop				0
		priority			6
		x					115
		y					84
		approachX		WINDOW_AX
		approachY		WINDOW_AY
		signal			(| fixedLoop fixPriOn ignrAct)
	)
	(method (doVerb)
		(nurseWindow doVerb &rest)
	)
)

(instance girl of Prop
	(properties
		view				vReceptionist 	;909
		loop				0
		x					138
		y					75
		description		"the receptionist"
		lookStr			"You thought this might be the babe you've
							been searching for. Obviously, she's not.
							Not even you could consider this one sexy
							(and you know what low standards YOU have!)."
	)
)


(instance phone of Prop
	(properties
		view				vDentistWaitingRoom		;905
		loop				2
		x 					32
		y 					156
		z					20
		nsLeft 			24
		nsTop 			130
		nsBottom 		142
		nsRight 			41
 		description 	"the waiting room telephone"
		lookStr			"The long table contains a telephone,
							placed here for the convenience of Doc's customers."
		sightAngle 		40
		approachX		59
		approachY		129
      priority       11
		signal			(| fixedLoop fixPriOn ignrAct)
	)

	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbDo
				(cond
					((OneOf (ego view:) vLarryInWaitingRoom vLarryW/Doily)
						(if (== (ego loop?) lUsingPhone)	;3
							(sChair setScript: sHangUp)
						else
							(sChair cue:)
						)
					)
					((curRoom script?)
						(super doVerb: theVerb invItem &rest)
					)
					(else
						(HandsOff)
						(= usePhone TRUE)
						(ego view: vLarry)
						(ego setScript: sChair)
					)
				)
			)
			(else
				(super doVerb: theVerb invItem &rest)
			)
		)
	)
)


;**	talkers

(instance theTalker of Talker
	(properties
		view			vReceptionistTalker	;1909
		loop			3
		nsLeft		22
		nsTop			33
		viewInPrint	TRUE
		name			"Alberta"
	)
	(method (init)
		(= bust  talkerBust)
		(= eyes  talkerEyes)
		(= mouth talkerMouth)
		(super init:)
	)
)
(instance talkerBust of View
	(properties
		view		vReceptionistTalker
		loop		1
		nsLeft	0
		nsTop		0
	)
)
(instance talkerEyes of Prop
	(properties
		view			vReceptionistTalker
		loop			2
		nsLeft		34
		nsTop			35
	)
)
(instance talkerMouth of Prop
	(properties
		view			vReceptionistTalker
		loop			0
		nsLeft		33
		nsTop			44
	)
)


;**	scripts

(instance sEnterFromOutside of Script
	(method (changeState newState &tmp var1 var2)
		(switchto (= state newState)
			(
				(HandsOff)
				(ego
					posn:				163 237,
					setHeading:		0,
					setMotion:		MoveTo 163 179 self,
				)
			)
			(
				(HandsOn)
				(self dispose:)
			)
		)
	)
)
(instance sExitToOutside of Script
	(method (changeState newState &tmp var1 var2)
		(switch (= state newState)
			(0
				(HandsOff)
				(theMusic fade)
				(ego
					setMotion:		MoveTo (ego x?) (+ (ego y?) 50) self
				)
			)
			(1
				(curRoom newRoom 900)
			)
		)
	)
)

(instance sHangUp of Script
	(method (changeState newState &tmp var1 var2)
		(switchto (= state newState)
			(
				(HandsOff)
				(ego
					setLoop:		lUsingPhone,		;3
					setCel:		LASTCEL,
					cycleSpeed:	6,
					setCycle: 	BegLoop self,
				)
			)

			(
				(ego
					setLoop:		1,
					setCel:		LASTCEL,
				)
				(HandsOn)
				(user canControl: FALSE)
			 	(self dispose:)
			)
		)
	)
)

(instance sChair of Script
	(method (doit)
		(super doit:)
		(if (and	(== state 2)
				 	((User curEvent) type?)
					((User curEvent) message?)
				 	(== (theIconBar curIcon?) (theIconBar at: walkIcon))
			    	(or	(== ((User curEvent) message?) verbWalk)
				   		(& ((User curEvent) type?) direction)))
			(= gotEvent TRUE)
;			(Printf "message= %d" ((User curEvent) message?))
			(self init:)
		)
	)

	(method (changeState newState &tmp var1 var2)
		(switch (= state newState)
			(0
				(HandsOff)
				(if (OneOf (ego view?) vLarryInWaitingRoom vLarryW/Doily)
					;larry's already sitting
					(ego
						setLoop:			lSit,
						cycleSpeed:		6,
						setCycle:		BegLoop self
					)
				else
					;SIT DOWN
					(ego sitting: TRUE)
					(= state 1)
					(if wearingDoily
						(ego view: vLarryW/Doily)
					else
						(ego view: vLarryInWaitingRoom)
					)
					(ego
						heading:			90,			;for normalEgo to work
						setLoop:			lSit,
						cycleSpeed:		6,
						setCycle: 		EndLoop self,
					)
					(palm			 approachVerbs: verbNone)
					(palm2		 approachVerbs: verbNone)
					(magazines	 approachVerbs: verbNone)
					(nurseWindow approachVerbs: verbNone)
					(glass		 approachVerbs: verbNone)
					(laceDoily 	 approachVerbs: verbNone)
					(rTable 		 approachVerbs: verbNone)
					(phone 		 approachVerbs: verbNone)
					(girl 		 approachVerbs: verbNone)
					(door 		 approachVerbs: verbNone)
				)
			)

			(1
				;STAND UP
				(palm			 	approachVerbs: verbDo verbLook verbUse)
				(palm2		 	approachVerbs: verbDo verbLook verbUse)
				(magazines	 	approachVerbs: verbDo verbLook verbUse)
				(nurseWindow 	approachVerbs: verbDo verbTalk)
				(glass 		 	approachVerbs: verbDo verbTalk)
				(laceDoily 		approachVerbs: verbDo)
				(rTable 			approachVerbs: verbDo)
				(phone 			approachVerbs: verbDo)
				(girl 			approachVerbs: verbDo)
				(door 			approachVerbs: verbDo verbLook verbUse)

				(NormalEgo FACING_EAST)

				;If the register is an object, then we're standing up
				; to go do something
				(HandsOn)
				(if (IsObject register)
					(WalkTo register verbDo) ;hands have to be on!
				)
				(if gotEvent
					(= gotEvent FALSE)
					(ego setMotion: PolyPath ((User curEvent?) x?) ((User curEvent) y?))
				)
				(= register 0)
				(ego sitting: FALSE)
				(self dispose:)
			)

			(2
				;finished sitting down
				(HandsOn)
				(user canControl: FALSE)
				(= register 0)
			  	(if usePhone
					(self setScript sPhone)
            else
					(if (not satDown)
						(TPrint "Ah! Good idea. This chair looks just right!"
							HIGH
							#dispose
						)
					)
				)
				(= satDown TRUE)
			)

			(3
				(= state 1)
				(self setScript sPhone)
				(= cycles 1)
			)
		)
	)
)


(instance sPhone of Script
	(method (changeState newState &tmp var1 var2)
		(switchto (= state newState)
			(
				(HandsOff)
				(= onPhone TRUE)
				(if wearingDoily
					(ego view:			vLarryW/Doily)
				else
					(ego view:			vLarryInWaitingRoom)
				)
				(ego
					setLoop:		lUsingPhone,
					setCel:		0,
					cycleSpeed:	6,
					setCycle:	EndLoop self,
				)
			)
			(
            (Cls)
				(DialPhone self)
			)
			(
				(= cycles 2)	;needed for dialer to work right (Say)
			)
			(
				(HandsOff)
				(switch whoToTalkTo
					(8544
						(TPrint "\"Hello, North Beach Limousine Service.\"")
						(Say ego "\"This is Mr. Laffer,\" you say.
							\"Would you please send my limousine over to
							Doc Pulliam's right away?\""
						)
						(cond
							((not (Btst fSeenCC))
								(TPrint "\"No, I'm sorry, Mr. Laffer.
									All of our drivers are on break right now.
									Could you call back again in a few minutes?\""
								)
							)
							((not (Btst fCalledLimo))
								(Bset fCalledLimo)
								(TPrint "\"Why, yes sir, Mr. Laffer.
									In fact, one of our drivers is in that vicinity
									right now.
									Just look outside.
									I bet you'll see her!\""
								)
							)
							(else
								(TPrint "\"You again?
									Why don't you just go outside and get it?!
									She's waiting for you!\""
								)
							)
						)
					)
					(3627
						(if (not (Bset fCalledDoc))
							(Bset fCalledDoc)
							(if (Btst fSeenCC)
								(Say theTalker "\"Good afternoon.
									You've reached Dr. Pulliam's exchange.
									I'm sorry, but everyone in their office has
									gone home for the evening.
									But please feel free to call back tomorrow.
									This has been a recording.\""
									#dispose:

									;NO CALLER CUE!
								)
							else
								(TPrint "\"Good afternoon.
									Dr. Pulliam's office.\""
								)
								(Say ego "\"Hello?
									This is, uh, umm, ah, Dr. `Payne.'
									I'm going to have to send over one of my
									emergency patients over for Doc Pulliam.
									Something's come up and I won't be able to see
									him this afternoon.
									Could you please see him right away?
									Thanks ever so much.
									Gotta go; it's tee time!\""
								)
							)
						else
							(TPrint "You already did that.")
						)
					)
					(NO_NUMBER
						(TPrint "You change your mind and hang up the phone.")
					)
					(else
						(TPrint "Hmmm. Wrong number.")
						(Say ego "\"Now where did I put that telephone number?\"
							you ask yourself.")
					)
				)
				(self setScript sHangUp self)
			)

			(
				(= onPhone FALSE)
				(= usePhone FALSE)
				(HandsOn)
				(user canControl: FALSE)
				(self dispose:)
			)
		)
	)
);sPhone

(instance nurseWindow of Feature
	(properties
		x 					112
		y 					63
		nsLeft 			71
		nsTop 			45
		nsBottom 		82
		nsRight 			153
 		description 	"the sliding glass window"
		sightAngle 		40
		approachX		WINDOW_AX
		approachY		WINDOW_AY
	)
	(method (doVerb theVerb invItem)
		(if (OneOf theVerb verbDo verbTalk)
			(cond
				((OneOf (ego view?) vLarryInWaitingRoom vLarryW/Doily)
					(if (== (ego loop?) lUsingPhone)
						(TPrint "First, hang up the phone.")
					else
						(ego setScript: sChair NULL self)
					)
				)
				((Btst fSeenCC)
					(TPrint "There is no answer from beyond the sliding
						glass windows.
						Either they're avoiding you,
						or they've all gone home."
					)
				)
				((and
						(not (Btst pfFinishedSurvey))
						(not talkedToGirl))
					(curRoom setScript sWindow)
				)
				(wearingDoily
					(curRoom setScript sWindow)
				)
				(else
					(TPrint "\"Go away,\" a voice cries through the glass.
						\"You already have an appointment.
						We'll see you then!\""
					)
				)
			)
		else
			(cond
				((== theVerb verbLook)
					(TPrint "Ghostly shapes seem to float on the other side of a
						set of frosted sliding glass windows.
 						There must be someone working back there.
						On the window a small Dymo label reads,
						\"Knock on window for service.\"")
				)
				((and (== theVerb verbUse)
						(== invItem iDocPulliam'sCard))
					(TPrint "Doc's office is already full of Doc's business cards.")
				)
				(else
					(super doVerb: theVerb invItem &rest)
				)
			)
		)
	)
)

(instance door of Door
	(properties
		view				vDentistWaitingRoom	;905
		x					234
		y					115
		loop				3
 		description 	"the door"
		lookStr			"This door leads to the inner offices of Doc Pulliam,
							including (you hope) the cubicle of Chi Chi Lambada,
							the woman you traveled all this way to \"interview.\""
		sightAngle		40
		entranceTo 		910				; room # beyond the door
		locked 			TRUE
		openSnd 			0
		closeSnd 		0
		moveToX			250
		moveToY			108				; where to move to when going to door
		heading			0					; heading in which ego should exit
		approachX		194
		approachY		120
		exitType			doorWalkEgo
;		enterType		doorPlaceEgo		;place ego in front of door
		enterType		doorWalkEgo
	)
	(method (close)
		(super close:)
		(= returningFrom910 TRUE)
		(Delay 3 REALSECONDS curRoom)
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbDo
				(if (OneOf (ego view?) vLarryInWaitingRoom vLarryW/Doily)
			 		(ego setScript: sChair NULL self)
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

(instance sWindow of Script
	(enum
		knockGlass
		knockTwice
		knockThreeTimes
		openGlass
		glassIsOpen
		girlTalks
		introduceLarry
		yeahMaybe
		noWay1
		noWay2
		grabPaper
		okaySweetie
		doQuestionnaire
		answeredAll
		closeGlass
		questionnaireDone
		gonnaGetIn
		fadeMusic
		closeWindow
	)

	(method (changeState newState &tmp var1 var2 [str 60])
		(switch (= state newState)
			(knockGlass
				(HandsOff)
				(if wearingDoily
					(ego
						view 					vLarryW/Doily,		;907
						setLoop:				0,
						cycleSpeed:			6,
						setCycle:			EndLoop self,
					)
				else
					(ego
						view 					vLarryInWaitingRoom,		;906
						setLoop:				0,
						cycleSpeed:			6,
						setCycle:			EndLoop self,
					)
				)
				(= cycles 2)
			)

			(knockTwice
				(theMusic2
					number:					sfxKnockGlass,
					setLoop:					ONCE,
					play:						,
				)
			)

			(knockThreeTimes
				(NormalEgo FACING_NORTH)
				(TPrint "You rap your knuckles gently on the textured
					sliding glass window."
					#dispose:
				)
				(= ticks 90)
			)

			(openGlass
				(theMusic2
					number:				sfxGlassDoor,
					setLoop:				FOREVER,
					play:
				)
				(glass
					cycleSpeed:	 		12,
					setCycle:	 		EndLoop self
				)
			)
			(glassIsOpen
				(theMusic2 stop)
				(girl setCycle: Forward)
				(= ticks 123)
			)
			(girlTalks
				(switch (++ windowTalkCount)
					(1
						(Say theTalker "\"Oh,\" she growls, \"and
							just what do we think we're doing here,
							eh, little man?\""
							#dispose
							#caller: self
						)
					)
					(else
						(Say theTalker "\"Yeah, what is it?\""
							#dispose
							#caller: self
						)
					)
				)

				(if (or	(Btst fCalledDoc)
							(== (ego view?) vLarryWearingDoily))
					(= state (- gonnaGetIn 1))
				)
			)

			(introduceLarry
				(switch windowTalkCount
					(1
						(Say ego "\"Hello,\" you say in your most pleasant tone,
							\"my name is Larry; Larry Laffer.
							I wonder if I might get an appointment with one of your
							dental hygienists?\""
						)
					)
					(else
						(Say ego "\"Hello,\" you say to the receptionist.
							\"I'd like an appointment to see one of your
							dental hygienists.
							I think I'm due for a little oral cavity work
							(if you know what I mean).\""
						)
					)
				)
				(= ticks 123)
			)

			(yeahMaybe
				(switch windowTalkCount
					(1
						(Say theTalker "\"No!\" she says, closing the window."
							#dispose
							#caller: self
						)
					)
					(else
						(= state noWay2)
						(= talkCounter 0)
						(Say theTalker "\"Yeah, maybe,\" she replies,
							\"but first, let's do your paperwork.\""
							#dispose
							#caller: self
						)
					)
				)
			)

			(noWay1
				(theMusic2
					number:				sfxGlassDoor,
					setLoop:				FOREVER,
					play:
				)
				(glass setCycle: BegLoop self)
			)

			(noWay2
				(theMusic2 stop)
				(Say ego "\"Wait!\" you cry, but it's too late.")
				(= register 0)
				(= start 0)
				(HandsOn)
				(NormalEgo FACING_NORTH)
				(self dispose:)
			)

			(grabPaper
				(Cls)
				(TPrint "And she grabs a large piece of paper,
					covered with tiny type.")
				(= ticks 123)
			)
			(okaySweetie
				(Say theTalker "\"Okay, sweetie,
					let's fill out this lil' ol' admission form together,
					now shall we?\""
					#dispose
					#caller: self
				)
			)
			(doQuestionnaire
				(girl setCel: 0)
				(Format @str "Question #%d" (++ talkCounter))
				(switchto talkCounter
					(
						NULL
					)
					(
						(= register
							(Print "\"Do you have dental insurance?\""
								#button	{Yes}	0
								#button	{No}	1
								#title	@str
							)
						)
					)
					(
						(= register
							(Print "\"Do you have LOTS of dental insurance?\""
								#button	{Yes}	0
								#button	{No}	1
								#title	@str
							)
						)
					)
					(
						(= register
							(Print "\"Are your teeth overly sensitive?\""
								#button	{Yes}	1
								#button	{No}	0
								#title	@str
							)
						)
					)
					(
						(= register
							(Print "\"Are you allergic to pain?\""
								#button	{Yes}	1
								#button	{No}	0
								#title	@str
							)
						)
					)
					(
						(= register
							(Print "\"Do you ENJOY pain?\""
								#button	{Yes}	0
								#button	{No}	1
								#title	@str
							)
						)
					)
					(
						(= register
							(Print "\"Do you enjoy sharing your pain with others?\""
								#button	{Yes}	0
								#button	{No}	1
								#title	@str
							)
						)
					)
					(
						(= register
							(Print "\"Do you enjoy mechanical objects in your mouth?\""
								#button	{Yes}	0
								#button	{No}	1
								#title	@str
							)
						)
					)
					(
						(= register
							(Print "\"Do you enjoy other people's hands
								in your mouth?\""
								#button	{Yes}	0
								#button	{No}	1
								#title	@str
							)
						)
					)
					(
						(= register
							(Print "\"Do you enjoy rubber in your mouth?\""
								#button	{Yes}	0
								#button	{No}	1
								#title	@str
							)
						)
					)
					(
						(= register
							(Print "\"Do you swallow?\""
								#button	{Yes}	0
								#button	{No}	1
								#title	@str
							)
						)
					)
					(
						(= register
							(Print "\"Have you ever even heard of AIDS?\""
								#button	{Yes}	1
								#button	{No}	0
								#title	@str
							)
						)
					)
					(
						(= register
							(Print "\"Do you know anyone who has ever hired a lawyer?\""
								#button	{Yes}	1
								#button	{No}	0
								#title	@str
							)
						)
					)
					(
						(= register
							(Print "\"Do you know the definition of `malpractice?'\""
								#button	{Yes}	1
								#button	{No}	0
								#title	@str
							)
						)
					)
					(
						;close window, come back later?

						(Points ptsFinishedSurvey pfFinishedSurvey)
						(= state (- closeWindow 1))
						(= talkedToGirl TRUE)
						(Say theTalker "\"Since this is not an emergency,
							I'll go ahead and set you up with an appointment for,
							uh, let's see... how about nine months from today?
							Ten o'clock is good for me!\""
							#dispose
							#caller: self
						)
					)
				)

				(if register
					(girl
						setCycle Forward,
					)
				else
					(if (< talkCounter 14)
						(-- state)
					)
				)
				(= ticks 123)
			)

			(answeredAll
				(Say theTalker "\"Oops! Well, gosh, I'm sorry,
					but I just realized we'll be unable to accept you as a client.
					I just remembered...
					we have no more openings this century!\""
					#dispose:
					#caller: self
				)
			)

			(closeGlass
				(glass
					cycleSpeed:			4,
					setCycle BegLoop 	self,
				)
				(theMusic2
					number:				sfxGlassDoor,
					setLoop:				FOREVER,
					play:
				)
			)

			(questionnaireDone
				(theMusic2 stop)
				(TPrint "\"Wait!\" you cry, but it's too late.")
				(= register 0)
				(= start 0)
				(HandsOn)
				(NormalEgo FACING_NORTH)
				(self dispose:)
			)

			(gonnaGetIn
				(girl setCel:0)
				(cond
					((== (ego view?) vLarryWearingDoily)
						(Say ego "\"Hepowafww, mmapwffoj,\" you mumble with
							your jaw tied shut,
							while you pretend to be unable to speak,
							\"maywppf asqd waf ffvppf?\"")
						(Points ptsUsedDoily pfUsedDoily)
						(Bset fUsedDoily)
						(Say theTalker "\"Oh, you poor dear!
							You look like you're in terrible pain!
							Why, just go right on in.
							I'll have one of our technicians attend to you
							right away!\""
							#dispose
							#caller: self
						)
					)
					((Btst fCalledDoc)
						(Say ego "\"Excuse me, ma'am,\" you say, hoping she
							doesn't recognize your voice.
							\"My name is Larry; Larry Laffer.\"")
						(Points ptsCalledDoc pfCalledDoc)
						(Say theTalker "\"Well, Larry, that was a quick trip!
							Please, just walk through the door to the right!
							I'll have one of our technicians attend to you
							right away!\""
							#dispose
							#caller: self
						)
					)
				)
			)

			(fadeMusic
				(Bset fSeenCC) 				;very important
				(theMusic
					fade				0 5 1 STOP_MUSIC,
				)
				(NormalEgo FACING_NORTH)
				(door
					setPri:	8,
					locked: 	FALSE,
					open
				)
			)

			(closeWindow
				(theMusic2
					number:				sfxGlassDoor,
					setLoop:				ONCE,
					play:
				)
				(glass
					cycleSpeed:	12,
					setCycle: 	BegLoop,
				)
				(HandsOn)
				(NormalEgo FACING_NORTH)
				(self dispose:)
			)
		)
	)
);sWindow

(instance laceDoily of Prop
	(properties
		view				vDentistWaitingRoom			;905
		loop				1
		x					307
		y					160
		nsLeft 			20
		nsTop 			144
		nsBottom 		157
		nsRight 			37
 		description 	"the doily"
		sightAngle 		40
		priority			13
		signal			(| fixedLoop fixPriOn ignrAct ignrHrz)
		approachX		GET_DOILY_X
		approachY		GET_DOILY_Y
	)

	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbLook
				(TPrint "The only thing on this table is a long, lacy doily.
					It reminds you of those long runners your Grandmother
					had on her table.")
			)
			(verbDo
				(cond
					((OneOf (ego view?)	vLarryInWaitingRoom vLarryW/Doily)
						(ego setScript: sChair NULL self)
					)
					((not (ego has:iDoily))
						(HandsOff)
						(curRoom setScript sGetDoily)
					)
					(else
						(TPrint "There is nothing here.")
					)
				)
			)
			(else
				(super doVerb: theVerb invItem &rest)
			)
		)
	)
)



(procedure (NormalEgo theLoop)
	(if (not theLoop)
		(= theLoop -1)
	)

	(if wearingDoily
	  	(ego
			setLoop:		theLoop,
			normalize:  vLarryWearingDoily ;908
		)

	else
		(ego
			setLoop:		theLoop,
			normalize: 	vLarry
		)
	)
)


(instance legs of View
	(properties
		view				vLarryInWaitingRoom
		loop				4
		cel				0
		priority			12
		signal			(| fixedLoop fixPriOn ignrAct)
	)
)



(instance thePhone of Feature
	(properties
		x 					31
		y 					331
		z 					200
		heading 			0
		nsLeft 			25
		nsTop 			126
		nsBottom 		137
		nsRight 			38
 		description 	"the phone"
		sightAngle 		40
		approachX		62
		approachY		127
	)
	(method (doVerb)
		(phone doVerb &rest)
	)
)


(instance toothTable2 of Feature
	(properties
		x 					19
		y 					379
		z 					200			  		
		heading 			0
		nsLeft 			0
		nsTop 			170
		nsBottom 		189
		nsRight 			39
 		description		"the table"
		lookStr			"You can tell that Doc's wife decorated this place.
							She's suffering from terminal cuteness!"
		sightAngle 		40
		approachX		47
		approachY		183
	)
)

(instance sGetDoily of Script
	(method (changeState newState)
		(switchto (= state newState)
			(
				(HandsOff)
				(ego setHeading: 125 self)
			)

			(

				(legs
					init,
					posn:			GET_DOILY_X GET_DOILY_Y,

				)
				(ego
					view: 		vLarryInWaitingRoom,			;906
					setLoop: 	4,
					setCel:		1,
					cycleSpeed:	8,
					posn:			(- GET_DOILY_X 1) GET_DOILY_Y,
					setPri:		13,
					setCycle:	EndLoop self,
				)
			)

			(
				(laceDoily dispose:)
				(= cycles 2)
			)

			(
				(rTable approachVerbs: verbNone)
				(Points ptsTookDoily pfTookDoily)
				(legs dispose:)
				(NormalEgo FACING_SOUTHEAST)
				(ego 
					setPri: 		RELEASE,
					get:			iDoily,
				)
				(= ticks 10)
			)
			(
				(TPrint "You take the lace doily from the waiting room table,
					but you really don't know why." 
					#dispose:self
				)
			)

			(
				(HandsOn)
				(self dispose:)
			)


		)
	)
)

