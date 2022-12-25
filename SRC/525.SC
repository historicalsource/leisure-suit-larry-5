;**
;**	Logics for Room 525 -- Hard Disk Cafe dining room
;**
;**	LSL5:  Passionate Patti Does A Little Undercover Work
;**				Copyright 1991
;**				by Al Lowe
;**
;**	Programmed by Carlos Escobar
;**
;**	Last Update:  September 10, 1991
;**


(script#		525)

(define 	vRoom525 		525)
(define  vMichelle      527)
(define  vScreen			510)
(define  vLarrySitting  526)

(define	sFatSlob			526)

(public
	rm525		0
)


(local
	clickX
	clickY
)

(instance rm525 of LLRoom
	(properties
		picture		525
		south			510
		east			530
	)

	(method (init)

		(LoadMany VIEW
			vRoom525 		;525
			vMichelle      ;527
			vScreen			;510
			vLarrySitting  ;526
		)
		(Load SOUND
			sFatSlob
		)

		(ego normalize:)

		(daDoor
			init:,
			approachVerbs: verbDo
		)
		(door init:)
		(curRoom addObstacle:
			((Polygon new:)
				type: PBarredAccess,
				init:	0 0 319 0 319 189 235 189 252 166 229 155 193 166 166 155
						122 170 114 164 96 177 64 189 0 189,
				yourself
			)
		)

		(switch prevRoomNum
			(520
				(ego
					posn:				210 161,
					init:,
				)
       		(if (> ((Inventory at: iMembershipTape) state?) 1)
					(HandsOff)
					(curRoom setScript: sToHollerith)
				else
					(HandsOn)
				)
			)
			(east
				(HandsOn)
				(ego
					posn:				238 162,
					setHeading:		270,
					edgeHit:			0
				)
			)
			(else
				(= larryLoc NewYork)
				(ego
					posn:				146 237,
					setHeading:		45,
					get:				iMembershipTape,
					init:,
				)
				(curRoom setScript: sEnter)
			)
		)

		(super init:)

		(fatLady
			init:,
			setScript sFatLadyEating,
		)

		;init features

		(tapeReader
			init:,
			approachVerbs: verbDo verbUse
		)
		(cactus 		init:)
		(tables     init:)
		(SprattFamilyTable  init:)
		(larryTable
			init:,
			approachVerbs: verbDo
		)

		;init addToPics...

		(baldMan	init:)
		(man1		init:)
		(man2		init:)
		(man3		init:)
		(woman1	init:)

		(addToPics doit:)

	);init
	(method (newRoom n)
		(super newRoom: n)
		(if
			(== n 530)
			(theMusic fade:)
		)
	)
	(method (doit)
		(super doit: &rest)
		(cond
			(script)
			(	(and
					(== (ego view?) vLarrySitting)
					(ego mover?)
					(User canControl?)
					(User canInput?)
				)
				(= clickX ((User curEvent?) x?))
				(= clickY ((User curEvent?) y?))
				(HandsOff)
				(curRoom setScript: sStandUp)
			)
			((StepOn ego cGREEN)
				(HandsOff)
				(self setScript: sExitSouth)
			)
		)
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbLook
				(if (!= (ego view) vLarrySitting)
					(TPrint "At last you are inside the \"Hard Disk Cafe's\" cafe.
						But where in the world is YOUR seat?")
				else
					(TPrint "You could sit like this and watch people all day.
						In fact, you may have to, as there seems to be an
						extreme shortage of waiters in this place.
						No wonder there's no crowd!")
				)
			)
			(else
				(super doVerb: theVerb invItem)
			)
		)
	)
)

;SCRIPTS

(instance sToHollerith of Script
	(method (changeState newState)
		(switchto (= state newState)
			(
				(= ticks 123)
			)
			(
				(ego
					setMotion:	PolyPath (door approachX?) (door approachY?) self
				)
			)
			(
				((curRoom obstacles?) dispose:)
				(= cycles 2)
			)
			(
				(curRoom addObstacle:
					((Polygon new:)
						type: PBarredAccess,
						init:	117 167 117 179 98 179 98 167,
						yourself
					)
				)
				(door
					locked:		FALSE,
					doVerb: 		verbDo,
				)
				(self dispose:)
			)
		)
	)
)

(instance sEnter of Script
	(method (changeState newState &tmp var1 var2)
		(switchto (= state newState)
			(
				(HandsOff)
				(ego
					setMotion:		MoveTo 196 185 self
				)
			)

			(
				(HandsOn)
				(self dispose:)
			)
		)
	)
)

(instance sMichelle of Script
	(method (changeState newState &tmp var1 var2)
		(switchto (= state newState)
			(
				(michelle
					setCycle:      Forward,
					setMotion:		MoveTo 190 191 self
				)
			)
			(
				(Points ptsSeesMichelle)
				(TPrint "Look, Larry!
					There she is!!
					It's your \"target\" for New York City:
					Michelle Milken.")
				(michelle
					setMotion:		MoveTo 216 163 self
				)
			)
			(
				(michelle
					setLoop:       1,
					posn:          207 162,
					setCel:			0,
					setCycle:      EndLoop self,
				)
			)
			(
				(screen
					init,
					setCycle: 		Forward,
				)
				(= seconds (Random 3 6))
			)
			(
				(michelle
					setCycle:      BegLoop self,
					illegalBits:	0,
					ignoreActors:	TRUE,
				)
			)
			(
				(screen dispose:)
				(michelle
					setLoop:       0,
					setCycle:      Walk,
					setMotion:		MoveTo 242 164 self
				)
			)
			(
				(door
					setCycle:		EndLoop self,
				)
			)
			(
				(michelle
					setMotion:		MoveTo 308 144 self
				)
			)
			(
				(door
					setCycle:		BegLoop self,
				)
			)
			(
				(door stopUpd:)
				(michelle dispose:)
				(HandsOn)
				(self dispose:)
			)
		)
	)
)


(instance sFatLadyEating of Script
	(method (changeState newState &tmp var1 var2)
		(switchto (= state newState)
			(
				(fatLady
					setCel:    0,
				)
				(if (not (Random 0 2))
					(= seconds (Random 2 6))
				else
					(= cycles 1)
				)
			)
			(
				(fatLady setCycle: EndLoop self)
			)
			(
				(slobSound play:)
				(self init:)
			)
		)
	)
)

(instance sSitDown of Script
	(method (changeState newState &tmp var1 var2)
		(switchto (= state newState)
			(
				(ego
					view: 			vLarrySitting,
					setLoop:			0,
					setCycle: 		EndLoop self,
				)
			)
			(
				(if (Btst fSeenMM)
					(HandsOn)
				else
					(Bset fSeenMM)
					(User canInput: TRUE)
					(theIconBar enable:
						lookIcon
						talkIcon
					)
					(theIconBar curIcon: (theIconBar at: lookIcon))
					(theGame setCursor: ((theIconBar curIcon?) cursor?))
					(michelle
						init:,
						setScript:		sMichelle,
					)
				)
				(self dispose:)
			)
		)
	)
)

(instance sStandUp of Script
	(method (changeState newState)
		(switchto (= state newState)
			(
				(ego
					setCycle:			BegLoop self
				)
			)
			(
				(HandsOn)
				(ego normalize:)
				(if
					(!= (CueObj client?) larryTable)
					(if
						(and
							((CueObj client?) approachX?)
							(!= ((theIconBar curIcon?) cursor?) TRAVEL_CURSOR)
						)
						(ego setMotion: PolyPath
						((CueObj client?) approachX?)
						(+ (ego z?) ((CueObj client?) approachY))
						CueObj)
					else
						(ego setMotion: PolyPath clickX clickY)
					)
				)
				(self dispose:)
			)
		)
	)
)

(instance sExitSouth of Script
	(method (changeState newState &tmp var1 var2)
		(switchto (= state newState)
			(
				(ego
					setMotion:		MoveTo (- (ego x?) 80) (+ (ego y?) 50) self
				)
			)

			(
				(curRoom newRoom 510)
			)
		)
	)
)


;PROPS

(instance door of Door
   (properties
      view				vRoom525
      loop				0
      x					264
      y					165
      description		"the door"
      sightAngle		90
		priority			3
		entranceTo 		530
		locked 			TRUE
		approachX		231
		approachY		164
		moveToX			292
		moveToY			154
		exitType			doorWalkEgo
		signal			(| ignrAct fixPriOn)
   )

	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbLook
				(if (Btst fSeenMM)
					(TPrint "You know it's possible to get in there, but
						how will you?  Michelle used that paper tape reader.")
				else
					(TPrint "You wonder what lies behind \"The Magenta Door.\"")
				)
			)
			(verbDo
				(if locked
					(TPrint "This door operates by means of a Membership
						Tape inserted and read by that machine to the left.")
				else
					(super doVerb: verbDo)
				)
			)
			(verbTalk
				(Say ego "\"Hello. Is there anyone inside?\"")
			)
			(verbUse
				(switch invItem
					(iMembershipTape
						(TPrint "Insert your tape into the machine just
							to the left of this door.")
					)
					(iGoldCard
						(TPrint "You might try using that on the maitre d'.")
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


(instance michelle of Actor
	(properties
		view				vMichelle
		loop				0
		x					128
		y					247
		lookStr			"It's her, Larry!
							Watch her carefully; perhaps you could follow her!"
		description		"Michelle Milken"
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbTalk
				(Say ego "\"Yoo hoo, Michelle!\" you cry,
					fumbling to find the words that would stop a knockout like
					this one.
					\"Wait! I've just got to meet you!\"")
			)
			(else
				(super doVerb: theVerb invItem &rest)
			)
		)
	)
)

(instance fatLady of Prop
	(properties
		view 				vRoom525
		x 					39
		y 					146
		z 					0
		heading 			0
		loop 				7
 		description 	"the fat lady"
		lookStr 			"You presume she's the one who sings when it's over."
		sightAngle 		40
		priority			1
		signal			(| fixedLoop fixPriOn ignrAct)
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbDo
				(TPrint "\"Getchur hans off'n my grub!\" screams the
					woman.")
			)
			(verbTalk
				(Say ego "\"Excuse me, madam,\" you say, \"do you
					have any idea how to enter the other room?\"")
				(TPrint "\"Glub, glub, glub,\" is her only sound.")
			)
			(else
				(super doVerb: theVerb invItem &rest)
			)
		)
	)
)

(instance screen of Prop
	(properties
	   view				vScreen
		loop				2
		x					192
		y					132
 		description		"the monitor"
		sightAngle		40
		priority			15
		signal			(| fixedLoop fixPriOn ignrAct)
	)
)

(instance man1 of PicView
	(properties
		view				vRoom525
		loop				5
		x					159
		y					70
 		description		"the bald man"
		lookStr			"That's one of the few people in this huge cafe."
		sightAngle		90
		priority			4
		signal			(| fixedLoop fixPriOn ignrAct ignrHrz)
	)
)
(instance man2 of PicView
	(properties
		view				vRoom525
		loop				5
		x					73
		y					70
 		description		"the lonely man"
		lookStr			"That's one of the few people in this huge cafe."
		sightAngle		90
		priority			4
		cel				1   ;can't setCel picViews
		signal			(| fixedLoop fixPriOn ignrAct ignrHrz)
	)
)
(instance man3 of PicView
	(properties
		view			  	vRoom525
		loop				6
		x					116
		y					86
 		description		"the lonely man"
		lookStr			"That's one of the few people in this huge cafe."
		sightAngle		90
		priority			4
		signal			(| fixedLoop fixPriOn ignrAct ignrHrz)
	)
)
(instance woman1 of PicView
	(properties
		view				vRoom525
		loop				6
		x					21
		y					92
 		description		"the lonely woman"
		sightAngle		90
		lookStr			"That's one of the few people in this huge cafe."
		priority			5
		cel				1
		signal			(| fixedLoop fixPriOn ignrAct ignrHrz)
	)
)

(instance baldMan of PicView
	(properties
		view				vRoom525
		loop				2
		x					62
		y					161
 		description		"Jack"
		sightAngle		90
		priority			4
		signal			(| fixedLoop fixPriOn ignrAct ignrHrz)
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbLook
				(TPrint "An extremely skinny man is eating with an extremely
					not-very-skinny woman!")
			)
			(else
				(super doVerb: theVerb invItem &rest)
			)
		)
	)
)

(instance SprattFamilyTable of Feature
	(properties
		x					52
		y					160
		z					0
		heading			0
		nsLeft			14
		nsTop				143
		nsBottom			177
		nsRight			90
 		description		"their table"
		lookStr			"This table seems to be occupied. Extremely occupied!"
		sightAngle		40
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbDo
				(TPrint "You can't sit there.
					You weren't assigned to that table.")
			)
			(else
				(super doVerb: theVerb invItem &rest)
			)
		)
	)
)

(instance larryTable of Feature
	(properties
		x					133
		y					148
		z					0
		heading			0
		nsLeft 			90
		nsTop 			124
		nsBottom 		162
		nsRight 			170
 		description		"your table"
		sightAngle		40
		approachX		114
		approachY		163

	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbLook
       		(switch ((Inventory at: iMembershipTape) state?)
					(tLarry
						(TPrint "Look! How convenient!
							A small sign on the table reads,
							\"Track 45, Sector 4F.\"
							This is your table, Larry!")
					)
					(else
						(TPrint "You no longer have to sit at this table, Larry.
							You are now a \"SuperUser!\"")
						(TPrint "(Whatever that means!)" LOW)
					)
				)
			)
			(verbDo
				(if
					(and
						(User canInput?)
						(User canControl?)
					)
					(HandsOff)
					(curRoom setScript: sSitDown)
				)
			)
			(else
				(super doVerb: theVerb invItem &rest)
			)
		)
	)
)

(instance tables of Feature
	(properties
		x					102
		y					57
		z					0
		heading			0
		nsLeft			0
		nsTop				0
		nsBottom			115
		nsRight			204
 		description		"the tables"
		lookStr			"What a huge cafe to have so few customers."
		sightAngle		40
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbDo
				(TPrint "You can't get to those tables.")
			)
			(verbTalk
				(TPrint "You voice echoes throughout the nearly empty
					\"Hard Disk Cafe.\"")
			)
			(else
				(super doVerb: theVerb invItem &rest)
			)
		)
	)
)

(instance  cactus of Feature
	(properties
		x					294
		y					162
		z					0
		heading			0
		nsLeft			271
		nsTop				137
		nsBottom			188
		nsRight			318
 		description		"the cactus"
		lookStr			"Insert your favorite cactus and \"prick\" joke here."
		sightAngle		40
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbDo
				(Say ego "\"OUCH!\"")
			)
			(else
				(super doVerb: theVerb invItem &rest)
			)
		)
	)
)


(instance tapeReader of Feature
	(properties
		x 					195
		y 					142
		nsLeft 			176
		nsTop 			120
		nsBottom 		158
		nsRight 			215
 		description 	"the tape reader"
		approachX   	210
		approachY   	161
		sightAngle		40
	)

	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbLook
				(TPrint "This is identical to the reader you used to
					enter this room.")
			)
			(verbDo
				(if
					(and
						(User canInput?)
						(User canControl?)
					)
					(HandsOff)
					(curRoom newRoom: 520)
				)
			)
			(verbUse
				(switch invItem
					(iMembershipTape
						(self doVerb: verbDo)
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

(instance daDoor of Feature
	(properties
		x 246
		y 125
		heading 0
		nsLeft 227
		nsTop 92
		nsBottom 159
		nsRight 266
 		description		"the door"
		sightAngle		40
		onMeCheck		cBLUE
		approachX		231
		approachY		164
	)

	(method (doVerb theVerb invItem)
		(door doVerb: theVerb invItem)
	)
)

(instance slobSound of Sound
	(properties
		number		sFatSlob
		flags			1
	)
)
