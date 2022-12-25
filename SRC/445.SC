;**
;**	Logics for Room 445 -- Commander Twit's workshop, east
;**
;**	LSL5:  Passionate Patti Does A Little Undercover Work
;**				Copyright 1991
;**				by Al Lowe
;**
;**	Programmed by Brian K. Hughes & Carlos Escobar
;**
;**	Last Update:  September 9, 1991
;**


(script#		445)

(public
	rm445		0
)

(enum		;set to sGetData register
	MAN
	PAK1
	PAK2
)

(define	vRoom							445)
(define	vRoomEgo			 (+ 1 vRoom))
(define	pWorkshopEast					440)
(define	vWorkshopEast				440)
	(define	lSingleCells		0)
		(define	cDataMan		0)
		(define	cDataPak1	1)
		(define	cDataPak2	2)
	(define	lOfficeDoor			1)
	(define	lComputer			2)
(define	vPattiWorkshopEast		441)

;(define		sLabSounds1			436)


(define  dataX	82)
(define  dataY 99)

;**	RoomScript state values

(instance rm445 of LLRoom
	(properties
		picture		440
		east			450
		west			435
		lookStr		"The F.B.I. laboratory is completely deserted.
						Perhaps they had more experiments than experimenters?"
	)

	(method (init)
		(Load VIEW vPattiWorkshopEast)
		(ego
			init:			,
			normalize:	,
		)
		(if
			(== ((Inventory at: iDataMan) owner?) 445)
			(theDataMan
				init:,
				stopUpd:,
				approachVerbs:	verbDo,
			)
		)
		(if
			(== ((Inventory at: iDataPak1Biaz) owner?) 445)
			(dataPak
				init:,
				stopUpd:,
				approachVerbs:	verbDo,
			)
		)
		(if
			(== ((Inventory at: iDataPak2Hammer) owner?) 445)
			(dataPak2
				init:,
				stopUpd:,
				approachVerbs:	verbDo,
			)
		)
;**	AL did this September 9, 1991:
;**			(dataHolder
;**				init:,
;**				stopUpd:
;**			)
		(machine
			init:,
		)
		(desk
			init:,
		)
		(computer
			init:,
		)
		(contraption
			init:,
		)
		(southBank
			init:,
		)
		(door
			init:,
		)
		(books
			init:,
		)
		(duckF init:)
		(switch prevRoomNum
			(west
				(HandsOn)
				(self style: SCROLLLEFT)
			)
			(else
				(HandsOn)
				(ego
					posn:			278 104,
					normalize:	,
					edgeHit:		0,
					setHeading:	270
				)
			)
		)
		(super init:)
		(HandsOn)
		(curRoom addObstacle:
			((Polygon new:)
				type: PBarredAccess,
				init:	0 189 0 140 136 140 161 150 190 150 231 130 251 138 305 125
						293 113 263 99 0 99 0 0 319 0 319 189,
				yourself
			)
		)
		(theMusic2
			number:		sLabSounds1,
			setLoop:		-1,
			play:
		)
	)
)

;ษอออออออออออออออออออป
;บ                   บ
;บ Scripts				บ
;บ                   บ
;ศอออออออออออออออออออผ

(instance sGetData of Script
	(method (changeState newState)
		(switchto (= state newState)
			(
				(ego
					view:			vPattiWorkshopEast,
					setLoop:		(if register 1 else 0),
					setCel:		0,
					cycleSpeed:	12,
					moveSpeed:	12,
					setCycle:	CycleTo 2 1 self
				)
			)
			(
				(switch register
					(MAN
						(ego get: iDataMan)
						(Points ptsGotDataman pfGotDataman)
						(theDataMan dispose:)
					)
					(PAK1
						(ego get: iDataPak1Biaz)
						(Points ptsGotInfoRB pfGotInfoRB)
						(dataPak dispose:)
					)
					(PAK2
						(ego get: iDataPak2Hammer)
						(Points ptsGotInfoPC pfGotInfoPC)
						(dataPak2 dispose:)
					)
				)
				(ego
					setCycle:	EndLoop self
				)
			)
			(
				(ego normalize:)
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

(instance theDataMan of View
	(properties
		view			440
		loop			0
		cel			0
		x				94
;**			y				175
		y				88
		z				13
		priority		6
		signal		fixPriOn
		approachX	dataX
		approachY	dataY
		description "the DataMan"
		lookStr		"A tiny electronic display device lies on the counter."
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbDo
				(HandsOff)
				(curRoom setScript: sGetData NULL MAN)
			)
			(else
				(super doVerb: theVerb invItem)
			)
		)
	)

)

(instance dataPak of View
	(properties
		view			440
		loop			0
		cel			1
		x				72
;**			y				174
		y				88
		z				14
		priority		6
		signal		fixPriOn
		approachX	dataX
		approachY	dataY
		description "the DataPak"
		lookStr		"What appears to be some sort of ROM cartridge lies
						on the counter."
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbDo
				(HandsOff)
				(curRoom setScript: sGetData NULL PAK1)
			)
			(else
				(super doVerb: theVerb invItem)
			)
		)
	)
)

(instance dataPak2 of View
	(properties
		view			440
		loop			0
		cel			2
		x				72
;**			y				176
		y				88
		z				12
		priority		6
		signal		fixPriOn
		approachX	dataX
		approachY	dataY
		description "the DataPak"
		lookStr		"What appears to be some sort of ROM cartridge lies
						on the counter."
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbDo
				(HandsOff)
				(curRoom setScript: sGetData NULL PAK2)
			)
			(else
				(super doVerb: theVerb invItem)
			)
		)
	)
)

;**	AL did this September 9, 1991:
;**	(instance dataHolder of View
;**		(properties
;**			view			440
;**			loop			0
;**			cel			1
;**			x				74
;**			y				88
;**			z				21
;**			description	"the DataPak holder"
;**			lookStr		"A small rack rests on the counter."
;**		)
;**		(method (doVerb theVerb invItem)
;**			(switch theVerb
;**				(verbDo
;**					(TPrint "You can't take the rack as it is attached to the table.")
;**				)
;**				(else
;**					(super doVerb: theVerb invItem)
;**				)
;**			)
;**		)
;**	)


(instance door of View
	(properties
		view			440
		loop			1
		cel			0
		x				282
		y				99
		description "Doctor Phil Hopian's door"
		lookStr     "This door leads back into Dr. Phil Hopian's office.
						After what you just went through,
						you're in no hurry to go back in there!"
		signal		ignrAct
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbDo
				(TPrint "It's locked, and you're glad that you can't get in!")
			)
			(verbTalk
				(TPrint "You knock on Dr. Phil Hopian's office door.
					There is no answer because the good Doctor is off playing
					golf.")
			)
			(else
				(super doVerb: theVerb invItem &rest)
			)
		)
	)
)

;ษอออออออออออออออออออป
;บ                   บ
;บ Features				บ
;บ                   บ
;ศอออออออออออออออออออผ


(instance machine of Feature
	(properties
		x 215
		y 47
		z 0
		heading 0
		nsLeft 170
		nsTop -1
		nsBottom 95
		nsRight 261
 		description "the machine"
		lookStr 		"This machine does something, you feel quite certain."
		sightAngle 40
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbDo
				(TPrint "You have not a clue as to what this machine may do!")
			)
			(else
				(super doVerb: theVerb invItem &rest)
			)
		)
	)
)
(instance computer of Feature
	(properties
		x 133
		y 91
		z 33
		heading 0
		nsLeft 119
		nsTop 45
		nsBottom 71
		nsRight 148
 		description "the computer"
		lookStr 		"If only you had paid attention when Larry chattered
						on and on about his computer.
						You know nothing about computers and
						are sure you'll be unable to do anything with it."
		sightAngle 40
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbDo
				(TPrint "You are afraid to touch any computer keyboard that
					doesn't have MIDI built in!")
			)
			(else
				(super doVerb: theVerb invItem &rest)
			)
		)
	)
)
(instance contraption of Feature
	(properties
		x 114
		y 160
		z 34
		heading 0
		nsLeft 94
		nsTop 115
		nsBottom 137
		nsRight 134
 		description "the chemistry set"
		lookStr 		"This is where the technician made the flatulence powder.
						(Un)Fortunately, he left his equipment thoroughly clean."
		sightAngle 40
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbDo
				(TPrint "You'll never be able to duplicate the F.B.I.'s
					\"secret formula\" of eleven natural herbs and spices.")
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
		y 166
		z 0
		heading 0
		nsLeft 0
		nsTop 144
		nsBottom 189
		nsRight 319
 		description "the workbench"
		lookStr 		"Lots of chemicals, beakers, and junk food wrappers
						fill this workbench."
		sightAngle 40
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbDo
				(if (or	(Btst fVibratorMan)
							(Btst fBraMan)
							(Btst fFartMan))
					(TPrint "After seeing the results of Commander Twit's earlier
						experiments, you think it best not to touch anything
						here unless truly necessary.")
				else
					(TPrint "There's nothing on that bench that could be
						useful to you in your mission.")
				)
			)
			(else
				(super doVerb: theVerb invItem &rest)
			)
		)
	)
)

(instance books of Feature
	(properties
		x 41
		y 88
		z 30
		heading 0
		nsLeft 28
		nsTop 49
		nsBottom 68
		nsRight 55
 		description "the books"
		lookStr 		"These books are filled with Latin phrases.
						The only Latin phrases you know
						would get a sailor thrown out of a bar in Tijuana!"
		sightAngle 40
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbDo
				(TPrint "You don't have time to read a book now, Patti!")
			)
			(else
				(super doVerb: theVerb invItem &rest)
			)
		)
	)
)

(instance desk of Feature
	(properties
		x 81
		y 87
		z 17
		heading 0
		nsLeft 0
		nsTop 54
		nsBottom 88
		nsRight 162
 		description "the desk"
		lookStr 		"This desk doesn't contain any secret documents;
						they all were burned in the big fire last week!"
		sightAngle 40
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbDo
				(TPrint "You open the drawer and look inside.
					There's nothing in there but a few charred slips of paper.")
			)
			(else
				(super doVerb: theVerb invItem &rest)
			)
		)
	)
)

(instance duckF of Feature
	(properties
		x 43
		y 189
		z 42
		heading 0
		nsLeft 23
		nsTop 134
		nsBottom 160
		nsRight 63
		sightAngle 40
	)
	(method (doVerb theVerb invItem)
		(if (== theVerb verbLook)
			(TPrint "Aw! The technician took his duck with him!")
		else
			(southBank doVerb: theVerb invItem)
		)
	)
)

