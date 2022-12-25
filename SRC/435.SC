;**
;**	Logics for Room 435 -- Commander Twit's workshop, west
;**
;**	LSL5:  Passionate Patti Does A Little Undercover Work
;**				Copyright 1991
;**				by Al Lowe
;**
;**	Programmed by Oliver Brelsford
;**
;**	Last Update:  September 4, 1991
;**

(script#		435)

(public
	rm435		0
)


(define	vRoom							435)
(define	vRoomEgo			 (+ 1 vRoom))
(define	vWorkshopWest			430)
	(define	lOfficeDoor			0)
	(define	lDoorExterior		1)
	(define	lLadder				2)
	(define	lShooter				3)
(define	vPattiWorkshopEast		441)

;(define		sLabSounds1			436)

(instance rm435 of LLRoom
	(properties
		picture		430
		north			425
		east			445
		lookStr		"Although you haven't really thought about, 
						this isn't what you would have pictured an F.B.I.
						lab to be."
	)

	(method (init)
		(ego
			init:,
			normalize:		vPatti,
		)
		(switch prevRoomNum
			(north
				(ego
					edgeHit:		0
				)
				(theMusic2
					number:		sLabSounds1,
					loop:			-1,
					play:
				)
			)
			(east
				(HandsOn)
				(self style: SCROLLRIGHT)
			)
			(west
				(ego
					edgeHit:		0,
				)
			)
			(else
				(HandsOn)
				(ego
					posn: 		160 160,
					edgeHit:		0
				)
			)
		)
		(northDoor
			init:,
			stopUpd:
		)
		(if (not (ego has: iBra))
			(hooterShooter
				init:,
				stopUpd:,
				approachVerbs: verbDo
			)
		)
		(door				init:,)
		(dartboard		init:,)
		(northBank 		init:,)
		(southBank		init:,)
		(monitors		init:,)
		(plug				init:,)
		(outlet			init:,)
		(chair			init:,)
		(joystick		init:,)
		(circuitboard	init:,)
		(super 			init:)
		(curRoom addObstacle:
			((Polygon new:)
				type: PBarredAccess,
				init:	0 0 116 0 116 69 116 80 89 80 9 131 107 131 111 128 190 128
						190 136 283 136 294 147 319 147 319 189 0 189,
				yourself
			)
			((Polygon new:)
				type: PBarredAccess,
				init:	124 0 319 0 319 99 138 99 138 80 124 80,
				yourself
			)
		)
	)
	(method (newRoom n)
		(if
			(== n (curRoom north?))
			(theMusic2 fade:)
		)
		(super newRoom: n)
	)
)


;ษอออออออออออออออออออป
;บ                   บ
;บ Scripts				บ
;บ                   บ
;ศอออออออออออออออออออผ


(instance sGetBra of Script
	(method (changeState newState)
		(switchto (= state newState)
			(
				(ego
					view:			vPattiWorkshopEast,
					setLoop:		0,
					setCel:		0,
					cycleSpeed:	8,
					moveSpeed:	8,
					setCycle:	CycleTo 2 1 self
				)
			)
			(
				(Points ptsGotBrassiere pfGotBrassiere)
				(hooterShooter dispose:)
				(ego
					get:			iBra,
					setCycle:	EndLoop self
				)
			)
			(
				(ego normalize:)
				(= ticks 67)
			)
			(
				(TPrint "You take the double-barreled brassiere from the workbench.
					It smells strongly of fresh gunpowder.")
				(= seconds 3)
			)
			(
				(TPrint "(You hope they lowered the size of the charge!)" LOW)
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

(instance northDoor of Door
	(properties
		view			430
		loop			0
		cel			0
		x				106
		y				77
		description "the exterior door"
		lookStr		"This door leads to the street outside."
		entranceTo  425
		enterType	doorWalkEgo
		exitType		doorWalkEgo
		approachX	126
		approachY	85
		moveToX		120
		moveToY		75
	)
)


(instance hooterShooter of View
	(properties
		view			vWorkshopWest
		loop			lShooter
		x				271
		y				96
		z				22
		signal		ignrAct
		approachX	261
		approachY	99
		description "the brassiere cannon"
		sightAngle	40
		lookStr		"It appears the F.B.I. technician left his
						bra lying on the workbench!"

	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbDo
				(HandsOff)
				(curRoom setScript: sGetBra)
			)
			(else
				(super doVerb: theVerb invItem)
			)
		)
	)
)

;ษอออออออออออออออออออป
;บ                   บ
;บ Features				บ
;บ                   บ
;ศอออออออออออออออออออผ

(instance door of Feature
	(properties
		x 23
		y 80
		z 0
		heading 0
		nsLeft 5
		nsTop 47
		nsBottom 114
		nsRight 42
 		description "Desmond's door"
		lookStr 		"This door leads to Inspector Desmond's office.
						It is presently locked."
		sightAngle 40
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbDo
				(TPrint "You decide that it wouldn't be a very good idea to
					try to open your new boss' private office--especially
					when he's locked the door.")
			)
			(verbTalk
				(TPrint "\"Hey, Desmond!\" you cry. \"Are you in there?\"")
				(TPrint "(Evidently not!)" LOW)
			)
			(else
				(super doVerb: theVerb invItem &rest)
			)
		)
	)
)
(instance dartboard of Feature
	(properties
		x 64
		y 91
		z 40
		heading 0
		nsLeft 54
		nsTop 37
		nsBottom 65
		nsRight 75
 		description "the dart board"
		sightAngle 40
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbLook
				(TPrint "It is easy to see that F.B.I. employees
					don't always score!")
			)
			(verbDo
				(TPrint "This dart board is here only for the amusement of the
					full-time, salaried employees.")
				(TPrint "(Besides, you've never played darts in your life,
					and you don't intend to begin now!)" WIDELOW)
			)
			(else
				(super doVerb: theVerb invItem &rest)
			)
		)
	)
)
(instance northBank of Feature
	(properties
		x 234
		y 89
		z 55
		heading 0
		nsLeft 155
		nsTop 1
		nsBottom 77
		nsRight 319
 		description "the laboratory tables"
		lookStr 		"These tables are filled with highly-expensive
						scientific equipment, all paid for with your tax dollars!"
		sightAngle 40
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbDo
				(TPrint "You really shouldn't mess around with the F.B.I.'s
					equipment.")
			)
			(else
				(super doVerb: theVerb invItem &rest)
			)
		)
	)
)
(instance southBank of Feature
	(properties
		x 159
		y 125
		heading 0
		nsLeft 0
		nsTop 118
		nsBottom 189
		nsRight 319
 		description "the laboratory tables"
		lookStr 		"These tables are filled with highly-expensive
						scientific equipment, all paid for with your tax dollars!"
		sightAngle 40
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbDo
				(TPrint "You really shouldn't mess around with the F.B.I.'s
					equipment.")
			)
			(else
				(super doVerb: theVerb invItem &rest)
			)
		)
	)
)
(instance monitors of Feature
	(properties
		x 190
		y 90
		z 49
		heading 0
		nsLeft 164
		nsTop 24
		nsBottom 59
		nsRight 216
 		description "the monitors"
		lookStr 		"These monitors are presently disconnected.
						When they were heavily used many years ago,
						it was only to play \"Jawbreaker!\""
		sightAngle 40
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbDo
				(TPrint "You try to turn on the monitor,
					but are unable to do so.")
			)
			(else
				(super doVerb: theVerb invItem &rest)
			)
		)
	)
)
(instance plug of Feature
	(properties
		x 83
		y 118
		z 100
		heading 0
		nsLeft 71
		nsTop 3
		nsBottom 33
		nsRight 96
 		description "the giant plug"
		lookStr 		"You wonder exactly what that giant plug plugs into."
		sightAngle 40
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbDo
				(TPrint "What? And get electrocuted?!")
			)
			(else
				(super doVerb: theVerb invItem &rest)
			)
		)
	)
)
(instance outlet of Feature
	(properties
		x 147
		y 77
		z 27
		heading 0
		nsLeft 142
		nsTop 43
		nsBottom 57
		nsRight 152
 		description "the intercom"
		lookStr 		"The lab technicians use this intercom to communicate
						with the front office and Commander Twit."
		sightAngle 40
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbDo
				(TPrint "You attempt to communicate through the intercom,
					but you find you're suffering from insufficient clearance.")
			)
			(else
				(super doVerb: theVerb invItem &rest)
			)
		)
	)
)
(instance chair of Feature
	(properties
		x 169
		y 130
		z 0
		heading 0
		nsLeft 152
		nsTop 117
		nsBottom 144
		nsRight 186
 		description "the chair"
		lookStr 		"This office chair prevents you from accessing the
						intricate equipment below it."
		sightAngle 40
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbDo
				(TPrint "Don't move it.
					You don't need anything that's past the chair.")
			)
			(else
				(super doVerb: theVerb invItem &rest)
			)
		)
	)
)
(instance joystick of Feature
	(properties
		x 121
		y 169
		z 34
		heading 0
		nsLeft 108
		nsTop 126
		nsBottom 145
		nsRight 135
 		description "the joystick"
		lookStr 		"Wow! What a joystick!"
		sightAngle 40
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbDo
				(TPrint "You briefly picture yourself playing \"Zeliard.\"")
			)
			(else
				(super doVerb: theVerb invItem &rest)
			)
		)
	)
)
(instance circuitboard of Feature
	(properties
		x 68
		y 189
		z 9
		heading 0
		nsLeft 46
		nsTop 172
		nsBottom 189
		nsRight 91
 		description "the circuit board"
		lookStr 		"This looks like a giant circuit board."
		sightAngle 40
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbDo
				(TPrint "Be careful. You'll get shocked!")
			)
			(else
				(super doVerb: theVerb invItem &rest)
			)
		)
	)
)

