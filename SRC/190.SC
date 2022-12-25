;**
;**	Logics for Room 190 -- Exterior, PornProdCorp
;**
;**	LSL5:  Passionate Patti Does A Little Undercover Work
;**				Copyright 1991
;**				by Al Lowe
;**
;**	Programmed by Brian K. Hughes
;**
;**	Last Update:  August 14, 1991
;**


(script#		190)

(public
	rm190		0
)


(define	pPornProdCorpExterior		190)
;(define	vPPCExterior				190)
;(define		lLimoParked			0)
(define		lDoorATP				1)
(define	vFountain					192)
(define		lFountain			0)
(define		lWaves				1)
(define		lDoor					2)

(define	sfxCarDoorOpen			191)
(define	sfxCarDoorClose		192)
(define	sFountain				193)


(instance rm190 of LLRoom
	(properties
		picture		pPornProdCorpExterior
		north			160
		lookStr		"You are outside PPC's general headquarters,
						nestled in the smog beneath the Hollywood hills.
						Gawd, how you love being in show biz!"
	)

	(method (init)
		(self setRegions: HOLLYWOODRGN)
		(ego
			init:			,
			normalize:	vLarrySmaller,
			setStep:		2 1
		)
		(HandsOn)
		(super init:)

		; Limo doesn't show up until Larry has found the all he needs
		; He can leave, however, without the battery charger and without
		;	degaussing the tapes
		(if (and	(ego has: iMM'sResume)
					(ego has: iLL'sResume)
					(ego has: iCC'sResume)
					(ego has: iGoldCard)
				)
			(limo
				init:				,
				approachVerbs: verbDo
			)
		)

		(theMusic2
			number:		sFountain,
			setLoop:		-1,
			play:
		)
		(fountain
			setCycle: 		Forward,
			init:				,
			approachVerbs:	verbDo verbZipper
		)
		(waves
			setCycle:		Forward,
			init:				,
			approachVerbs:	verbDo
		)

		(statue			init:)
		(logo				init:)
		(sign				init:)
		(tree				init:)
		(buildings1		init:)
		(buildings2		init:)
		(windows1		init:)
		(windows2		init:)
		(doorATP			init:)
		(debugFeature	init:)
		(addToPics		doit:)

		(curRoom addObstacle:
			(if (cast contains: limo)
				((Polygon new:)
					type: PBarredAccess,
					init:	0 0 179 0 179 132 76 132 64 125 5 125 5 133 61 133 76 136
							65 143 24 145 4 150 4 158 147 158 202 175 218 187 309 187
							309 167 276 163 273 156 315 146 315 122 253 136 243 143
							225 143 216 134 192 134 192 0 319 0 319 189 0 189,
					yourself
				)
			else
				((Polygon new:)
					type: PBarredAccess,
					init:	0 0 179 0 179 133 71 133 64 125 5 125 5 140 23 140 23 146
							5 150 5 158 151 158 202 175 218 187 313 187 309 167 276 163
							273 156 315 146 315 122 253 137 243 143 225 143 216 134
							192 134 192 0 319 0 319 189 0 189,
					yourself
				)
			)
		)
		(Delay 2 CYCLES self)	; so door happens after picture draws
	)
	(method (newRoom n)
		(if (== n 200)
			(theMusic fade:)
		else
			(theMusic fade: 127 25 10 FALSE)	; don't dispose
		)
		(theMusic2 fade:)
		(super newRoom: n)
	)
	(method (cue)
		; So sound of door isn't early - we get here from Delay in init:
		(door
			init:				,
			approachVerbs: verbDo
		)
	)
)


;ษอออออออออป
;บ         บ
;บ Scripts บ
;บ         บ
;ศอออออออออผ

; Ok, Ok!  I stole this code from Mr. Carlos Escobar,
;	programmer extraordinaire, who thunk it up all himself!

(instance sEnterLimo of Script
	(method (changeState newState &tmp var1 var2)
		(switchto (= state newState)
			(
				(HandsOff)
				(ego setHeading: 180 self)
			)
			(
				(ego
					moveSpeed:		10,
					setMotion:		MoveTo (ego x?) 137 self,
				)
				(= ticks 10)
			)
			(; we get here from ticks
				(ego hide:)
				(theMusic2
					number:			sfxCarDoorOpen,
					setLoop:			1,
					play:
				)
				(= ticks 50)		;in place of queueing with sound
			)
			(; we get cued by MoveTo in state 1
			)
			(
				(theMusic2
					number:			sfxCarDoorClose,
					setLoop:			1,
					play:
				)
				(= ticks 100)		;in place of queueing with sound
			)
			(
				(= ticks 20)
			)
			(
				(curRoom newRoom 200)
			)
		)
	)
)


;ษอออออออออออออออออออป
;บ                   บ
;บ Objects           บ
;บ                   บ
;ศอออออออออออออออออออผ

(instance limo of View
	(properties
		view			vPPCExterior
		loop			lLimoParked
		x				10
		y				1138
		z				1000	; so it gets events before the fountain
		priority		9
		signal		fixPriOn
		approachX	29
		approachY	136
		description	"your limousine"
		lookStr		"At last! You finally get to ride in a real limo."
	)
	(method (doVerb theVerb)
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

(instance door of Door
	(properties
		view				vFountain
		loop				lDoor
		x					175
		y					130
		z					36
		entranceTo		160
		moveToX			187
		moveToY			128
		enterType		doorWalkEgo;doorPlaceEgo
		exitType			doorWalkEgo
		heading			180
		approachX		187
		approachY		138
 		description 	"the front door of PornProdCorp"
		lookStr 			"This door leads back into the PPC lobby."
	)
)

(instance doorATP of PicView
	(properties
		view				vPPCExterior
		loop				lDoorATP
		x					185
		y					129
		priority 		5
		signal			fixPriOn
	)
)

(instance fountain of Prop
	(properties
		view			vFountain
		x				92
		y				160
		priority		15
		signal		fixPriOn
		detailLevel	2
 		description "the fountain"
		lookStr 		"Passing the tinkling water makes you want to pass
						tinkling water."
		sightAngle 	40
	)
	(method (doVerb theVerb invItem &tmp [str 30])
		(switch theVerb
			(verbDo
				(TPrint "Carefully checking to make sure that no one
					can see you,
					you take full advantage of the PPC Memorial Fountain.")
			)
			(verbUse
				(Format @str "Now %s is wet!"
					((Inventory at: invItem) description?)
				)
				(TPrint @str)
			)
			(verbZipper
				(TPrint "Carefully checking to make sure that no one
					can see you,
					you take full advantage of the PPC Memorial Fountain.")
			)
			(else
				(super doVerb: theVerb invItem &rest)
			)
		)
	)
)

(instance waves of Prop
	(properties
		view			vFountain
		loop			1
		x 				89
		y 				189
		priority		15
		detailLevel	3
		signal		fixPriOn
		sightAngle 	40
	)
	(method (doVerb theVerb invItem)
		(fountain doVerb: theVerb invItem &rest)
	)
)


;ษอออออออออออออออออออป
;บ                   บ
;บ Features          บ
;บ                   บ
;ศอออออออออออออออออออผ

(instance statue of Feature
	(properties
		x 				95
		y 				171
		z 				75
		nsLeft 		73
		nsTop 		64
		nsBottom 	129
		nsRight 		117
 		description "Chesty"
		sightAngle 	40
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbLook
				(Points ptsSawChesty pfSawChesty)
				(TPrint "You remember her!
					She's PornProdCorp's founding mother: Chesty Turbo!"
				)
			)
			(verbDo
				(TPrint "You can't really do what you're thinking!")
			)
			(verbTalk
				(TPrint "\"I'm so lonely,\" you moan to the statue.")
			)
			(verbZipper
				(TPrint "Larry! She's not that good!")
			)
			(else
				(super doVerb: theVerb invItem &rest)
			)
		)
	)
)

(instance logo of Feature
	(properties
		x 				193
		y 				20
		nsLeft 		180
		nsTop 		3
		nsBottom 	37
		nsRight 		207
 		description "that PornProdCorp logo"
		lookStr 		"You presume PPC chose that logo because the tongue
						is the universal symbol of good taste."
		sightAngle 	40
	)
)

(instance tree of Feature
	(properties
		x 				263
		y 				169
		z				100
		nsLeft 		235
		nsTop 		4
		nsBottom 	134
		nsRight 		292
 		description "the palm tree"
		sightAngle 	40
		onMeCheck	cYELLOW
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbLook
				(TPrint "For just a moment, you have the feeling that tree is
					the only thing in Hollywood that's straight!"
				)
				(TPrint "(Then you notice it's leaning!)" LOW)
			)
			(verbDo
				(TPrint "You love the feel of palms, don't you, Larry?")
			)
			(verbTalk
				(TPrint "\"I Talk to the Trees,\" you sing.")
			)
			(else
				(super doVerb: theVerb invItem &rest)
			)
		)
	)
)

(instance HollywoodSign of Feature
	(properties
		x 				92
		y 				80
		nsLeft 		0
		nsTop 		0
		nsBottom 	46
		nsRight 		61
 		description "the Hollywood sign"
		lookStr 		"You love working here because everyone in this city tries
						so hard to treat you fairly."
		sightAngle 	40
	)
)

(instance buildings1 of Feature
	(properties
		x 				28
		y 				92
		nsLeft 		0
		nsTop 		71
		nsBottom 	113
		nsRight 		57
 		description "the buildings"
		lookStr 		"You wonder how many disco parties are going on in those
						office buildings over there!"
		sightAngle 	40
	)
)

(instance buildings2 of Feature
	(properties
		x 				299
		y 				87
		nsLeft 		279
		nsTop 		69
		nsBottom 	105
		nsRight 		319
 		description "the buildings"
		lookStr 		"You wonder how many disco parties are going on in those
						office buildings over there!"
		sightAngle 	40
	)
)

(instance windows1 of Feature
	(properties
		x 				105
		y 				106
		nsLeft 		81
		nsTop 		97
		nsBottom 	116
		nsRight 		129
 		description "the windows"
		lookStr 		"You just love voyeurism, don't you?"
		sightAngle 	40
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbDo
				(TPrint "Keep your hands off the glass.")
			)
			(verbTalk
				(TPrint "\"Hello!  Is anybody in there?\" you shout through the
					glass.")
				(TPrint "(There is no response.)" LOW)
			)
			(else
				(super doVerb: theVerb invItem &rest)
			)
		)
	)
)

(instance windows2 of Feature
	(properties
		x 				238
		y 				100
		nsLeft 		216
		nsTop 		89
		nsBottom 	112
		nsRight 		260
 		description "the windows"
		lookStr 		"You just love voyeurism, don't you?"
		sightAngle 	40
	)
	(method (doVerb theVerb invItem)
		(windows1 doVerb: theVerb invItem &rest)
	)
)

(instance debugFeature of Feature
	(properties
		x 				29
		y 				33
		nsLeft 		27
		nsTop 		31
		nsBottom 	35
		nsRight 		31
		sightAngle 	40
	)
	(method (init)
		(mouseDownHandler addToEnd: self)
		(super init:)
	)
	(method (dispose)
		(mouseDownHandler delete: self)
		(super dispose:)
	)
	(method (handleEvent event)
		(if (and (== (event type?) mouseDown)
					(== theCursor EYE_CURSOR)
					(== (event modifiers?) 12)			; ctrl-alt-down
				)
			(= debugging TRUE)
		else
			(curRoom handleEvent: event)
		)
	)
)
