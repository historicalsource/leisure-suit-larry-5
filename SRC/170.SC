;**
;**	Logics for Room 170 -- Larry's workroom, PornProdCorp
;**
;**	LSL5:  Passionate Patti Does A Little Undercover Work
;**				Copyright 1991
;**				by Al Lowe
;**
;**	Programmed by Brian K. Hughes
;**
;**	Last Update:  September 10, 1991
;**

(script#		170)

(public
	rm170		0
)


(define	pLarryWorkroom					170)
(define	vWorkroomATPs				170)
(define		lSingleCells		0)
(define			cThreeTapes 0)
(define			cCharger		1)
(define			c8Track		2)
(define	vWorkroom					171)
(define		lOpeningDrawer		0)
(define		lTakingTapes 		1)
(define		lLarryBody			2)
(define		lWorkroomDoor		3)


(define	drawerLeft				1)
(define	drawerCenter			2)
(define	drawerRight				3)

(define	mPPCMusak						160)
(define	sDrawer		171)
(define	sDegausser	172)


(local
	page
)


(instance rm170 of LLRoom
	(properties
		picture		pLarryWorkroom
		east			160
		lookStr		"You are so proud of this, your humble workshop."
	)

	(method (init)
		(self setRegions: HOLLYWOODRGN)
		(ego
			init:				,
			normalize:		,
			illegalBits:	cCYAN
		)
		(switch prevRoomNum
			(east
				NULL
			)
			(else				;DEBUG - teleporting
				(HandsOn)
				(ego
					posn:		160 160,
					edgeHit:	0
				)
			)
		)
		(super init:)

		(if (not
				(or	(ego has: iVideotape1)
						(ego has: iVideotape2)
						(ego has: iVideotape3)
				)
			)
			(camcorderTapes init:, approachVerbs: verbDo)
		)
		(door init:, approachVerbs: verbDo)

		(degausser		init:, approachVerbs: verbUse)
		(drawer1			init:, approachVerbs: verbDo)
		(drawer2			init:, approachVerbs: verbDo)
		(drawer3			init:, approachVerbs: verbDo)
		(cabinet			init:)
		(sterileBarrel	init:, approachVerbs: verbDo verbUse)
		(videoMonitor1	init:)
		(videoMonitor2	init:)
		(drain			init:)
		(boxes			init:)
		(videotapes1	init:)
		(videotapes2	init:)
		(videotapes3	init:)
		(videotapes4	init:)
		(videotapes5	init:)
		(videotapes6	init:)
		(videotapes7	init:)
		(lightWest		init:)
		(lightEast		init:)
		(dice				init:)
		(eightTrack		init:, approachVerbs: verbDo)

		(curRoom addObstacle:
			((Polygon new:)
				type: PContainedAccess,
				init:	319 116 216 116 204 113 204 108 209 97 198 97 190 101 36 101
						11 120 114 120 109 125 60 133 14 129 3 137 3 186 156 164
						239 158 242 175 235 189 315 165 315 145 284 143 255 128
						283 119 319 119,
				yourself
			)
		)
	)
)


;ษอออออออออออออออออออป
;บ                   บ
;บ Scripts           บ
;บ                   บ
;ศอออออออออออออออออออผ

(instance sOpenDrawer of Script
	;
	; register - drawerLeft, drawerCenter, drawerRight
	;
	(method (changeState newState)
		(switchto (= state newState)
			(
				(HandsOff)
				(theMusic2
					number: 	sDrawer,
					setLoop:	1,
					play:
				)
				(ego
					view:			vWorkroom,
					setLoop:		lOpeningDrawer,
					setCel:		0,
					cycleSpeed:	10,
					setCycle:	EndLoop self
				)
			)
			(
				(= cycles 3)
			)
			(
				(switch register
					(drawerRight
						(TPrint "Nope, nothing of interest in this drawer!"
							#at: -1 150
						)
					)
					(drawerCenter
						(TPrint "This drawer only contains a few old donut crumbs."
							#at: -1 150
						)
					)
					(drawerLeft
						(if (Btst pfGotCharger)
							(TPrint "Without the battery recharger, this drawer
								seems so empty!"
								#at: -1 150
							)
						else
							(TPrint "Well, well! What's this? So that's where
								you left your battery recharger!"
								#at: -1 150
							)
							(Points ptsGotCharger pfGotCharger)
							(TPrint "You take it from the drawer and pop it into
								your pocket."
								#at: -1 150
							)
							(ego get: iBatteryCharger)
						)
					)
				)
				(= cycles 3)
			)
			(
				(theMusic2
					number: 	sDrawer,
					setLoop:	1,
					play:
				)
				(ego setCycle: BegLoop self)
			)
			(
				(ego
					loop:				3,
					normalize:		,
					illegalBits:	cCYAN

				)
				(HandsOn)
				(self dispose:)
			)
		)
	)
);sOpenDrawer

(instance sTapes of Script
	(method (changeState newState)
		(switchto (= state newState)
			(
				(HandsOff)
				(ego
					view:			vWorkroom,
					setLoop:		lTakingTapes,
					setCel:		0,
					posn:			237 158,
					setCycle:	CycleTo 3 FORWARD self
				)
				(egoBody init:)
			)
			(
				(camcorderTapes dispose:)
				(ego setCycle: EndLoop self)
			)
			(
				(= cycles 5)
			)
			(
				(Points ptsGotVideotapes pfGotVideotapes)
				(ego get: iVideotape1 iVideotape2 iVideotape3)
				(= cycles 5)
			)
			(
				(ego
					setLoop:	2,
					posn: 	236 184
				)
				(TPrint "You always wondered what these tiny little tapes were.
					These miniature videotapes are a perfect fit for your new
					camcorder.")
				(TPrint "(And you thought they were DAT tapes!)" LOW)
				(egoBody dispose:)
				(ego
					normalize:		,
					illegalBits:	cCYAN
				)
				(HandsOn)
				(self dispose:)
			)
		)
	)
);sTapes

(instance sDegaussTape of Script
	;
	; register - enum value of tape or NULL
	;
	(method (changeState newState)
		(switchto (= state newState)
			(
				(HandsOff)
				(= cycles 1)
			)
			(
				(if (not register)
					(TPrint "The tape has already been degaussed.")
					(self changeState: 3)
				else
					(theMusic2
						number: 	sDegausser,
						setLoop:	-1,
						play:
					)
					(Points ptsDegaussedTapes)
					(if (not tapesDegaussed)
						(TPrint "Sliding the switch to send rapidly alternating,
							pulsating currents through your degausser,
							you sensually slide the girl's dedicated magnetic
							particles in slow concentric circles over the degausser,
							gradually increasing in speed and intensity,
							until they (and you) are randomly aligned.
							A shudder passes through your body!!")
						(TPrint "(By gawd, that's degaussing!)" LOW)
						(= cycles 1)
					else
						(= ticks 120)
					)
					(if (== (++ tapesDegaussed) 3)
						(Bset pfDegaussedTapes)
					)
				)
			)
			(
				(theMusic2 stop:)
				(RecordTape tapeDegaussed register)
				(= cycles 1)
			)
			(
				(ego setMotion: MoveTo (- (ego x?) 15) (+ (ego y?) 5) self)
			)
			(
				(ego
					setLoop:			2,
					normalize:		,
					illegalBits:	cCYAN
				)
				(HandsOn)
				(self dispose:)
			)
		)
	)
);sDegaussTape


;ษอออออออออออออออออออป
;บ                   บ
;บ Objects           บ
;บ                   บ
;ศอออออออออออออออออออผ

(instance camcorderTapes of View
	(properties
		view			vWorkroomATPs
		x 				249
		y 				181
		priority		15
		signal		(| fixPriOn ignrAct)
		approachX	236
		approachY	184
 		description "the camcorder tapes"
		lookStr 		"You've found them! These are the tapes you need
						for your new Pocket Protector Camcorder."
		sightAngle 	40
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbDo
				(curRoom setScript sTapes)
			)
			(verbUse
				(switch invItem
					(iCamcorder
						(TPrint "You try in vain to insert your camcorder into
							those tiny tapes.")
					)
					(else
						(super doVerb: theVerb invItem &rest)
					)
				)
			)
			(else
				(super doVerb: theVerb invItem &rest)
			)
		)
	)
)

(instance egoBody of View
	(properties
		view			vWorkroom
		loop			lLarryBody
		x				237
		y				185
		priority		10
		signal		fixPriOn
	)
)

(instance door of Door
	(properties
		view				vWorkroom
		loop				lWorkroomDoor
		x					292
		y					116
		entranceTo		160
		moveToX			285
		moveToY			116
		enterType		doorWalkEgo
		exitType			doorWalkEgo
		heading			135
		approachX		240
		approachY		117
		description		"the lobby door"
		lookStr			"The door to the lobby seems oh, so familiar to you.
							Probably because you entered this room through it!
							Your \"The Girls of Videotape Technician's Magazine\"
							calendar hangs on the back, open to Miss October,
							who (in your opinion) has the best \"reels\" of the
							bunch!"
	)
	(method (init)
		(ego ignoreControl: cCYAN)
		(super init: &rest)
		(self
			startUpd:	,
			setPri:		9
		)
	)
	(method (open)
		(self setPri: 9)
		(ego illegalBits: 0)
		(super open:)
	)
	(method (close)
		(super close:)
		(self setPri: 8)
		(ego observeControl: cCYAN cWHITE)
	)
)


;ษอออออออออออออออออออป
;บ                   บ
;บ Features          บ
;บ                   บ
;ศอออออออออออออออออออผ

(instance degausser of Feature
	(properties
		x 				162
		y 				74
		nsLeft 		151
		nsTop 		65
		nsBottom 	84
		nsRight 		173
 		description "the degausser"
		lookStr 		"Your degausser, which you use to degauss tapes,
						rests on the workbench."
		sightAngle 	40
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbDo
				(TPrint "Degaussers work on tapes, not hands.
					They return the magnetic particles to a random distribution.")
				(TPrint "(They erase tapes!)" LOW)
			)
			(verbUse
				(if (OneOf invItem iVideotape1 iVideotape2 iVideotape3)
					(if ((Inventory at: invItem) state?)
						(curRoom setScript: sDegaussTape NULL NULL)
					else
						(curRoom setScript: sDegaussTape NULL invItem)
					)
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

(instance drawer1 of Feature
	(properties
		x 				52
		y 				88
		nsLeft 		37
		nsTop 		84
		nsBottom 	93
		nsRight 		68
		approachX	57
		approachY	109
 		description "the drawer"
		lookStr 		"It looks like a closed drawer."
		sightAngle 	40
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbDo
				(curRoom setScript: sOpenDrawer NULL drawerLeft)
			)
			(else
				(super doVerb: theVerb invItem &rest)
			)
		)
	)
)

(instance drawer2 of Feature
	(properties
		x 				99
		y 				88
		nsLeft 		82
		nsTop 		84
		nsBottom 	93
		nsRight 		117
		approachX	104
		approachY	110
 		description "the drawer"
		lookStr 		"It looks like a closed drawer."
		sightAngle 	40
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbDo
				(curRoom setScript: sOpenDrawer NULL drawerCenter)
			)
			(else
				(super doVerb: theVerb invItem &rest)
			)
		)
	)
)

(instance drawer3 of Feature
	(properties
		x 				145
		y 				88
		nsLeft 		129
		nsTop 		84
		nsBottom 	93
		nsRight 		162
		approachX	151
		approachY	110
 		description "the drawer"
		lookStr 		"It looks like a closed drawer."
		sightAngle 	40
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbDo
				(curRoom setScript: sOpenDrawer NULL drawerRight)
			)
			(else
				(super doVerb: theVerb invItem &rest)
			)
		)
	)
)

(instance cabinet of Feature
	(properties
		x 				98
		y 				44
		nsLeft 		38
		nsTop 		30
		nsBottom 	58
		nsRight 		159
 		description "the cabinet"
		lookStr 		"You have no idea what's inside these cabinets since you
						are too short to see inside."
		sightAngle 	40
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbDo
				(TPrint "You've always wanted to use these upper cabinets,
					but your arms are too short,
					so you've never seen inside them.")
			)
			(else
				(super doVerb: theVerb invItem &rest)
			)
		)
	)
)

(instance sterileBarrel of Feature
	(properties
		x 				222
		y 				44
		nsLeft 		204
		nsTop 		22
		nsBottom 	66
		nsRight 		240
 		description "your barrel of sterilizer"
		lookStr 		"This barrel contains the sterilizing solution that you use
						on all incoming video tapes before rewinding them and
						distributing them to the members of the ASHV Audition Team."
		sightAngle 	40
	)
	(method (doVerb theVerb invItem &tmp [str 100])
		(switch theVerb
			(verbDo
				(Points ptsSterilizedHands pfSterilizedHands)
				(TPrint "You quickly rinse your hands with the sterilizing
					solution. You rather enjoy it, although for the life
					of you, you don't know why.")
			)
			(verbTalk
				(TPrint "\"Hey, Sterilizer!\" you yell into the metal drum.
					\"Every night's your night in the barrel!\"")
			)
			(verbUse
				(Format @str "You rinse %s in a stream of sterilizer,
					effectively removing over 90%c of the bacteria from
					its environmental surfaces."
					((Inventory at: invItem) description?)
					37
				)
				(TPrint @str)
			)
			(else
				(super doVerb: theVerb invItem &rest)
			)
		)
	)
)

(instance videoMonitor1 of Feature
	(properties
		x 					29
		y 					68
		nsLeft 			14
		nsTop 			54
		nsBottom 		82
		nsRight 			44
 		description 	"the video monitor"
		lookStr 			"You use this monitor every day to preview the incoming
							submissions, making sure they're explicit enough to
							pass on to the official members of the Submissions Team."
		sightAngle 		40
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbDo
				(TPrint "You've seen enough of this monitor over the last few
					months. You don't want to see any more.")
			)
			(verbUse
				(if (OneOf invItem iVideotape1 iVideotape2 iVideotape3)
  					(TPrint "These tapes only fit in your Pocket Protector
  						Camcorder.")
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

(instance videoMonitor2 of Feature
	(properties
		x 					286
		y 					168
		nsLeft 			254
		nsTop 			148
		nsBottom 		189
		nsRight 			319
		sightAngle 		40
	)
	(method (doVerb)
		(videoMonitor1 doVerb: &rest)
	)
)


(instance drain of Feature
	(properties
		x 				149
		y 				1124
		z 				1000
		nsLeft 		133
		nsTop 		119
		nsBottom 	130
		nsRight 		166
 		description "the floor drain"
		lookStr 		"You often feel this is where your life is headed."
		sightAngle 	40
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbDo
				(TPrint "Cleverly surmising the humble floor drain is really a
					secret passage to another part of the building,
					you yank and tug on the grate,
					only to find it is firmly screwed.")
				(TPrint "(You can relate to that!)" LOW)
			)
			(verbTalk
				(TPrint "\"Hello!\" you shout down the drain.")
				(TPrint "\"Heeeellllooooo.\"" HIGH)
				(TPrint "\"Heeeellllooooo.\"")
				(TPrint "\"Heeeellllooooo.\"" LOW)
			)
			(else
				(super doVerb: theVerb invItem &rest)
			)
		)
	)
)

(instance boxes of Feature
	(properties
		x 				84
		y 				156
		nsLeft 		49
		nsTop 		123
		nsBottom 	189
		nsRight 		120
 		description "the boxes"
		lookStr 		"You have no use for empty boxes."
		sightAngle 	40
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbDo
				(TPrint "\"Nah,\" you think, \"who needs a box?\"")
			)
			(else
				(super doVerb: theVerb invItem &rest)
			)
		)
	)
)

(instance videotapes1 of Feature
	(properties
		x 				10
		y 				170
		nsLeft 		0
		nsTop 		151
		nsBottom 	189
		nsRight 		21
 		description "some videotapes"
		sightAngle 	40
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbDo
				(TPrint "These tapes are of no use to you.")
			)
			(verbLook
				(TPrint "These are the tapes of the women who didn't make
					the cut. They won't fit in your camcorder.")
				(TPrint "(And neither do their tapes!)" LOW)
			)
			(else
				(super doVerb: theVerb invItem &rest)
			)
		)
	)
)

(instance videotapes2 of Feature
	(properties
		x 				116
		y 				71
		nsLeft 		89
		nsTop 		63
		nsBottom 	79
		nsRight 		143
		sightAngle 	40
	)
	(method (doVerb)
		(videotapes1 doVerb: &rest)
	)
)

(instance videotapes3 of Feature
	(properties
		x 				55
		y 				115
		nsLeft 		6
		nsTop 		101
		nsBottom 	130
		nsRight 		104
		sightAngle 	40
	)
	(method (doVerb)
		(videotapes1 doVerb: &rest)
	)
)

(instance videotapes4 of Feature
	(properties
		x 				169
		y 				153
		nsLeft 		109
		nsTop 		118
		nsBottom 	188
		nsRight 		229
		sightAngle 	40
	)
	(method (doVerb)
		(videotapes1 doVerb: &rest)
	)
)

(instance videotapes5 of Feature
	(properties
		x 				227
		y 				96
		nsLeft 		204
		nsTop 		79
		nsBottom 	113
		nsRight 		250
		sightAngle 	40
	)
	(method (doVerb)
		(videotapes1 doVerb: &rest)
	)
)

(instance videotapes6 of Feature
	(properties
		x 				304
		y 				114
		nsLeft 		289
		nsTop 		82
		nsBottom 	146
		nsRight 		319
		sightAngle 	40
	)
	(method (doVerb)
		(videotapes1 doVerb: &rest)
	)
)

(instance videotapes7 of Feature
	(properties
		x 				103
		y 				16
		nsLeft 		41
		nsTop 		3
		nsBottom 	29
		nsRight 		166
		sightAngle 	40
	)
	(method (doVerb)
		(videotapes1 doVerb: &rest)
	)
)

(instance lightWest of Feature
	(properties
		x 				24
		y 				32
		nsLeft 		8
		nsTop 		23
		nsBottom 	41
		nsRight 		41
 		description "the lamp"
		lookStr 		"It's just hanging around here... rather like you, Larry!"
		sightAngle 	40
	)
	(method (onMe)
		(return (or	(super onMe: &rest)
						(lightEast onMe: &rest)
					)
		)
	)
)

(instance lightEast of Feature
	(properties
		x 				178
		y 				29
		nsLeft 		160
		nsTop 		19
		nsBottom 	39
		nsRight 		196
		sightAngle 	40
	)
)

(instance dice of Feature
	(properties
		x 				190
		y 				127
		z 				52
		heading 		0
		nsLeft 		180
		nsTop 		65
		nsBottom 	85
		nsRight 		200
 		description "the fuzzy dice"
		lookStr 		"Your sole attempt to decorate your work space is one-half
						of a pair of fuzzy dice, left over from your '74 Bug."
		sightAngle 	40
	)
)

(instance eightTrack of Prop
	(properties
		view			vWorkroomATPs
		cel			c8Track
		x 				54
		y 				77
		approachX	54
		approachY	101
 		description "your 8-track player"
		lookStr 		"Your trusty Strombug Carlton 8-track cartridge tape player
						rests on your workbench.
						Although lately you've had trouble finding new cartridges,
						it still really belts out the tunes."
		sightAngle 	40
	)
	(method (init)
		(= page 0)
		(super init:)
	)
	(method (cue)
		(self doVerb: verbDo)
	)
	(method (doVerb theVerb &tmp num)
		(switch theVerb
			(verbDo
				(Points ptsHeard8track pfHeard8track)
				(if (not page)
					(= num
						(PrintD {Play which 8-track cartridge?}
							#new
							#button {__The Larry Song__}		100
							#button {_Record des Rever_}		640
							#new
							#button {__Desmond's Theme__}		410
							#button {__Silas Scruemall__}		150
							#new
							#button {___Closing Theme___}		462
							#button {___Hard Rock Me___}		500
							#new
							#button {___Hard Rock You__}		501
							#button {____Hard Rock It____}		502
							#new
							#button {__Michelle's Song__}		535
							#button {______Shill This______}	620
							#new
							#button {______Saxy Sex______}	345
							#button {On The Boardwalk}			700
							#new
							#button {< Next Screen >} 			-1		#x 70 #y 10
							#new
							#button {< Eject >}					0		#x 95
						)
					)
				else
					(= num
						(PrintD {Play which 8-track cartridge?}
							#new
							#button {_A Studio Session_}		660
							#button {__Gettin' Some Air__} 	663
							#new
							#button {____Nasty Girlz_____}		738
							#button {____Whoz On Top?____}		740
							#new
							#button {__Tramp's Casino__}		710
							#button {____Ivana Skate____}		790
							#new
							#button {_____K-RAP Rap_____}	800
							#button {_____K-RAP Talk_____}	801
							#new
							#button {____K-RAP Rock_____}	802
							#button {___Doc'll Pulliam____}		905
							#new
							#button {__Chi Chi a Me Me__}	910
							#button {____Salsa This_____}		920
							#new
							#button {< Previous Screen >} 	-1		#x 70 #y 10
							#new
							#button {< Eject >}					0		#x 95
						)
					)
				)
				(switch num
					(-1
						(= page (- 1 page))
						(Delay 1 SECONDS self)
					)
					(0
						(theMusic
							number:	mPPCMusak,
							setLoop:	FOREVER,
							play:
						)
					)
					(else
						(theMusic
							number: 	num,
							setLoop:	FOREVER,
							play:
						)
					)
				)
				(if (and	(== (ego x?) approachX)
							(== (ego y?) approachY)
						)
					(ego setMotion: MoveTo (+ (ego x?) 15) (+ (ego y?) 5))
				)
			)
			(else
				(super doVerb: theVerb &rest)
			)
		)
	)
)

(instance funSound of Sound)

