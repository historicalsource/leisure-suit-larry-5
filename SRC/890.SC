;**
;**	Logics for Room 890 -- Control room B interior
;**
;**	LSL5:  Passionate Patti Does A Little Undercover Work
;**				Copyright 1991
;**				by Al Lowe
;**
;**	Programmed by John Hartin
;**
;**	Last Update:  September 10, 1991
;**


(script#		890)

(public
	rm890		0
)


(define	RTRloc		((Inventory at: iReelToReelTape) owner?))
(define EDGEHIT (& (ego onControl?) cLMAGENTA))

(define	pK-RAPRadioControlRoom		880)
(define	pInsideControlRoomB			890)
(define	vPattiHammerpants			872)
(define	vK-RAPRadioControlRoom	880)
	(define	lSingleCells		0)
		(define	cMikeStand	0)
		(define	cBlockingDoor 1)
	(define	lDoorToA				1)
	(define	lDoorToB				2)
	(define	lSign					3)
		(define	cOff				0)
		(define	cOn				1)
(define	vPCHammer					882)
	(define	lWalking 			0-1)
(define	vHammerActions				883)
	(define	lUsingMixer			0)
	(define	lLookingAtPatti	1)
	(define	lBlockingDoor		2)
	(define	lUsingMixerLarge	3)
(define	vInsideControlRoomB		890)
	(define	lRecorderReels0		0)
	(define	lRecorderReels1		1)
	(define	lBreakingWindow	2)
	(define	lMeterLights 		3-4)
	(define	lControlRoomASign 		6)
(define	vPattiInControlRoomB		891)
	(define	lGettingTape		0)
	(define	lUsingRecorder		1)
	(define	lUsingBoard			2)
	(define	lSingingIntoMike	3)
	(define	lClimbingOutWindow1 4)
	(define	lClimbingOutWindow2 5)
	(define	lPattiLeavingWindow 7)

(define		sUseMike				891)
(define sBreakPot	146)
(define	mKRAPRap1	800)
(define	mKRAPRap2	801)
(define	mKRAPRap3	802)

(local
	seenMsg
	recording
	recorded
	jammed
	usedMixer
	egoMove							;so ego can recover from edgehit on jammed door
	mixerLookCounter
	mixerDoCounter
	volumeUp
	rewoundTape
	displayVar
)

(enum
	onShelf
	withLarry		; place holder, would never happen
	withPatti
	onRecorder
)

(instance rm890 of LLRoom
	(properties
		picture		pInsideControlRoomB
		south			pK-RAPRadioControlRoom
		lookStr 		"You are inside K-RAP radio's Control Room B.
						For such a large room, it certainly looked smaller
						from the outside.
						The walls are filled with a myriad of recording equipment,
						electronic gear, mixers, microphones, recording tape,
						etc. etc."
	)

	(method (init)
		(LoadMany VIEW
			vPattiHammerpants
			vK-RAPRadioControlRoom
			vPCHammer
			vHammerActions
			vInsideControlRoomB
			vPattiInControlRoomB
		)
		(LoadMany SOUND
			sUseMike
			sBreakPot
			mKRAPRap1
			mKRAPRap2
			mKRAPRap3
		)
		(LoadMany SCRIPT
			REVERSE
		)
		(HandsOn)
		(SetFFRoom 0)
		(ego
			init:			,
			actions: ActionsKRAP,
			normalize:	vPattiHammerpants,
			x:				70,
			y:		185
		)
		(super init:)
		(fElectronics1 init:, approachVerbs: verbDo verbUse verbLook)
		(fElectronics2 init:, approachVerbs: verbDo verbUse verbLook)
		(fElectronics3 init:, approachVerbs: verbDo verbUse verbLook)
		(fElectronics4 init:, approachVerbs: verbDo verbUse verbLook)
		(fElectronics5 init:, approachVerbs: verbDo verbUse verbLook)
		(fElectronics6 init:)
		(fElectronics7 init:)
		(speaker1 init:)
		(speaker2 init:)
		(speaker3 init:)
		(tapeShelf init:, approachVerbs: verbDo verbLook)
		(shelf init:, approachVerbs: verbDo verbLook)
		(recorder init:, approachVerbs: verbDo verbUse verbLook)
		(tape init:, approachVerbs: verbDo verbUse verbLook)
		(if (== RTRloc	onRecorder)
			(tapeHole init:, addToPic:)		; Tape isn't on shelf anymore.
			(tape 					; Tape mounted already when Patti enters room.
				setLoop: 0,
				x: 35,
				y: 142,
				stopUpd:
			)
		else
			(tape stopUpd:)		;Tape defualts handle everything
		)
		(mixer init:, approachVerbs: verbDo verbLook)
		(microphone init:, approachVerbs: verbDo verbTalk verbLook)
		(glass init:, stopUpd)
		(lights init:, approachVerbs: verbDo verbLook, stopUpd:)
		(if (Btst pfHeard2Live2Screw)
			(lights setCycle: Forward, startUpd:)
		)
		(addToPics doit:)
		(recSignA init:, setCycle: Forward)
		(hammer init:, setCycle: RandCycle, cycleSpeed: 12)
		(self addObstacle:
			((Polygon new:)
				type: PBarredAccess,
				init:	0 0 319 0 319 189 98 189 103 187 235 187 289 166 284 154
						265 161 189 133 112 133 17 176 29 189 0 189,
				yourself
			)
		)
;wjh		(if (!= prevRoom (curRoom south?))
;wjh			(self setScript: sCartoon)
;wjh		)
	)

	(method (doit)
		(cond
			(script
				(script doit:)
			)
			((ego edgeHit?)
				(curRoom newRoom: pK-RAPRadioControlRoom)
			)
			(EDGEHIT
				(if jammed
					(if (not egoMove)
						(TPrint "Someone has jammed the door shut with a
							large, immovable object!")
						(= egoMove TRUE)
					)
				)
			)
			(egoMove
				(= egoMove FALSE)
			)
			(else
				(super doit:)
			)
		)
	)
	(method (dispose)
		(LoadMany FALSE
			REVERSE
		)
		(super dispose:)
	)
)

(instance sCartoon of Script
	(method (changeState newState)
		(switchto (= state newState)
			(
				(ego hide:)
				(tape hide:)
				(glass hide:)
				(lights hide:)
				(recSignA dispose:)

				; Since we're changing pics temporarily and moving PC about,
				;	we dispose of the current polygons & init the one around
				;	the fountain in the other room (easier than doing newRoom)
				((curRoom obstacles?) dispose:)
				(theIconBar disable:)
				(curRoom
					drawPic: 		pK-RAPRadioControlRoom,
					picture:			pK-RAPRadioControlRoom,
					curPic:			pK-RAPRadioControlRoom,
				)
				(theIconBar enable:)
				(doorA init:, close:)
				(doorB init:)
				(mikestand init:, stopUpd:)
				(recSign init:, setCycle: Forward)
				(addToPics doit:)
				(hammer
					setLoop:	-1,
					view:	vPCHammer,
					x:		65,
					y:		86,
					xStep:	8,
					yStep:	4,
					cycleSpeed:	12,
					moveSpeed:	12,
					setCycle:	Walk,
					setMotion:	MoveTo 156 123 self
				)
			)
			(
				(TPrint "Oh, no! P. C. Hammer has noticed the \"Recording\"
					sign over the window of Control Room B,
					realized you are not an engineer,
					and is heading for your room!")
				(hammer
					view: 		vHammerActions,
					xStep:	3,
					yStep:	2,
					cycleSpeed:	6,
					moveSpeed:	6,
					setLoop: 	2,
					setMotion: 	MoveTo 186 123 self
				)
				(mikestand
					startUpd:,
					setMotion: MoveTo	205 123
				)
			)
			(
				(= jammed TRUE)
				(mikestand stopUpd:)
				(hammer
					setLoop:		-1,
					view:			vPCHammer,
					xStep:	8,
					yStep:	4,
					cycleSpeed:	12,
					moveSpeed:	12,
					setCycle:	Walk,
					setMotion:	MoveTo 30 124 self
				)
			)
			(
				(TPrint "Blocking the only way out of the control room with
					that heavy microphone stand,
					he heads for the elevator!")
				(TPrint "You're in trouble now, Patti!")
				(hammer
					setMotion:	MoveTo -30 124 self
				)
			)
			(
				; Now redraw the original pic & re-estab. original polygons
				(doorA dispose:)
				(doorB dispose:)
				(mikestand dispose:)
				(hammer dispose:)
				(recSign dispose:)

				(theIconBar disable:)
				(curRoom
					drawPic: 		890,
					picture:			890,
					curPic:			890,
					addObstacle:
						((Polygon new:)
							type: PBarredAccess,
							init:	0 0 319 0 319 189 98 189 103 187 235 187 289 166 284 154
									265 161 189 133 112 133 17 176 29 189 0 189,
							yourself
						)
				)
				(theIconBar enable:)
				(ego show:, observeControl: cYELLOW)
				(tape show:)
				(tapeHole init:, addToPic:)		; Tape isn't on shelf anymore.
				(glass show:, stopUpd:)
				(lights show:)
				(= seconds 3)
			)
			(
				(TPrint "Wait! What's that!!")
				(TPrint "By carefully staring across the lobby through the
					control room window,
					you notice P. C. Hammer punching an access code into
					the keypad of the shower elevator.
					From the angle of his finger and the movement of his hand,
					you learn the secret access code.
					Knowing that code,
					you could escape this area and return upstairs!")
				(TPrint "Now if you could only find a way out of this room!")
				(HandsOn)
				(self dispose:)
			)
		)
	)
);sCartoon

(instance fElectronics1 of Feature
	(properties
		x 150
		y 199
		z 0
		;heading 0
		approachX	151
		approachY	187
		nsLeft 117
		nsTop 165
		nsBottom 189
		nsRight 184
 		description "the giant speaker"
		lookStr 		"You could swear these are genuine Altec
						\"Voice of the Theatre\" speakers.
						You haven't seen these babies in years!"
		sightAngle 40
	)
)
(instance fElectronics2 of Feature
	(properties
		x 283
		y 187
		z 0
		;heading 0
		approachX	235
		approachY	187
		nsLeft 247
		nsTop 163
		nsBottom 189
		nsRight 319
 		description "the electronic equipment"
		lookStr 		"Everywhere you look, you see buttons, switches,
						lights, reels of tape, and tape drives."
		sightAngle 40
	)
)
(instance fElectronics3 of Feature
	(properties
		x 301
		y 169
		z 0
		;heading 0
		approachX	281
		approachY	169
		nsLeft 283
		nsTop 129
		nsBottom 163
		nsRight 319
 		description "the electronic equipment"
		lookStr 		"Everywhere you look, you see buttons, switches,
						lights, reels of tape, and tape drives."
		sightAngle 40
	)
)
(instance fElectronics4 of Feature
	(properties
		x 277
		y 151
		z 0
		;heading 0
		approachX	237
		approachY	151
		nsLeft 235
		nsTop 106
		nsBottom 135
		nsRight 319
 		description "the left large-hub reel-to-reel recorder"
		sightAngle 40
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbDo
				(TPrint "This recorder will not operate without a reel of tape.")
			)
			(verbLook
				(TPrint "You haven't seen a 2\", 24-track, 3\"-hub reel-to-reel
					recorder in years.
					What antiquated equipment!
					Where's K-RAP's digital equipment?")
			)
			(verbUse
				(switch invItem
					(iReelToReelTape
						(TPrint "You are unable to mount your reel's tiny hole
							onto this recorder's massive spindle!")
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
(instance fElectronics5 of Feature
	(properties
		x 215
		y 139
		z 0
		;heading 0
		approachX	205
		approachY	139
		nsLeft 196
		nsTop 106
		nsBottom 125
		nsRight 235
 		description "the right large-hub reel-to-reel recorder"
		sightAngle 40
	)
	(method (doVerb theVerb invItem)
		(fElectronics4 doVerb: theVerb invItem &rest)
	)
)
(instance fElectronics6 of Feature
	(properties
		x 265
		y 63
		z 0
		;heading 0
		nsLeft 274
		nsTop 17
		nsBottom 109
		nsRight 319
 		description "the electronic equipment"
		lookStr 		"Everywhere you look, you see buttons, switches,
						lights, reels of tape, and tape drives.
						Unfortunately, they are much too high for you to reach."
		sightAngle 40
	)
)

(instance fElectronics7 of Feature
	(properties
		x 265
		y 63
		z 0
		;heading 0
		nsLeft 212
		nsTop 30
		nsBottom 109
		nsRight 274
 		description "the electronic equipment"
		lookStr 		"Everywhere you look, you see buttons, switches,
						lights, reels of tape, and tape drives.  Unfortunately,
						they are much too high for you to reach."
		sightAngle 40
	)
)


(instance speaker1 of Feature
	(properties
		x 100
		y 60
		z 0
		;heading 0
		nsLeft 86
		nsTop 43
		nsBottom 78
		nsRight 114
 		description "the monitor speaker"
		lookStr 		"You could swear these are genuine Altec \"Voice of the
						Theatre\" speakers.
						You haven't seen these babies in years!"
		sightAngle 40
	)
)
(instance speaker2 of Feature
	(properties
		x 199
		y 57
		z 0
		;heading 0
		nsLeft 186
		nsTop 42
		nsBottom 72
		nsRight 212
 		description "the monitor speaker"
		lookStr 		"You could swear these are genuine Altec \"Voice of the
						Theatre\" speakers.
						You haven't seen these babies in years!"
		sightAngle 40
	)
)

(instance speaker3 of Feature
	(properties
		x 10
		y 24
		z 0
		;heading 0
		nsLeft 0
		nsTop 0
		nsBottom 49
		nsRight 21
 		description "the monitor speaker"
		lookStr 		"You could swear these are genuine Altec \"Voice of the
						Theatre\" speakers.
						You haven't seen these babies in years!"
		sightAngle 40
	)
)
(instance shelf of Feature
	(properties
		x 154
		y 87
		z 0
		approachX	155
		approachY	139
		nsLeft 		120
		nsTop 		63
		nsBottom 	131
		nsRight 		189
 		description "the equipment rack"
		lookStr 		"This shelf contains a lot of electronic equipment,
						most of it old, plus a shelf filled with blank
						reel-to-reel tapes."
		sightAngle 	40
	)
)

(instance tapeShelf of Feature
	(properties
		x 154
		y 87
		z 0
		approachX	155
		approachY	139
		nsLeft 138
		nsTop 79
		nsBottom 102
		nsRight 169
 		description "the shelf of tapes"
		lookStr 		"Why, here's what you need!
						A shelf filled with blank, reel-to-reel recording tapes."
		sightAngle 40
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbDo
				(if (!= RTRloc onShelf)
					(TPrint "You have no use for another tape.
						Use the one you have!")
				else
					(ego setScript: GetTape)
				)
			)
			(else
				(super doVerb: theVerb invItem &rest)
			)
		)
	)
)

(instance mixer of Feature
	(properties
		x 70
		y 144
		z 0
		approachX	90
		approachY	140
		;heading 270
		nsLeft 66
		nsTop 107
		nsBottom 126
		nsRight 104
 		description "the microphone mixer"
		sightAngle 40
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbLook
				(switch (++ mixerLookCounter)
					(1
						(TPrint "Before you lies an elaborate microphone mixer and
							studio control panel console.
							The names of the various downstairs studios are
							engraved on the potentiometers.")
					)
					(2
						(TPrint "This volume control says, \"Control Room C.\"")
					)
					(3
						(TPrint "This potentiometer says, \"Studio C.\"")
					)
					(4
						(TPrint "This slider says, \"Control Room B.\"")
					)
					(5
						(TPrint "This pot is labeled, \"Studio B.\"")
					)
					(6
						(TPrint "This controls the volume coming from \"Control
							Room A.\"")
					)
					(7
						(TPrint "This fader is labeled, \"Studio A.\"")
					)
					(else
						(TPrint "The rest of these are labeled \"Carts (A-F),\"
							\"Tables (A-C),\" \"CDs (A-R).\"")
					)
				)
			)
			(verbDo
				(if jammed
					(ego setScript: useMixer)
				else	
					(if (== (++ mixerDoCounter) 1)
						(TPrint "The console contains many knobs, sliders,
							meters and buttons. Which one will you choose?")
					else
						(ego setScript: useMixer)
					)
				)
			)
			(else
				(super doVerb: theVerb invItem &rest)
			)
		)
	)
)

(instance microphone of Feature
	(properties
		x 59
		y 145
		z 0
		approachX	89 						;**	was 70
		approachY	141						;**	was 151
		;heading 270
		nsLeft 50
		nsTop 102
		nsBottom 120
		nsRight 68
 		description "the microphone"
		lookStr 		"A professional-quality, high-fidelity, studio ribbon
						microphone hangs from a boom over the studio console."
		sightAngle 40
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbDo
				(TPrint "You carefully aim the microphone so it's pointing
					just below your mouth.")
			)
			(verbTalk
				(if jammed
					(if volumeUp
						(ego setScript: useMikeScript)
					else
						(TPrint "You hear your voice echo through the room's
							monitor speakers.
							The speakers are amazingly loud;
							loud enough to break glass.")
					)
				else
					(TPrint "You carefully check the level of your voice on the
						mixer. It's okay.")
				)
			)
			(else
				(super doVerb: theVerb invItem &rest)
			)
		)
	)
)

(instance recorder of Feature
	(properties
		x 26
		y 163
		approachX	42
		approachY	163
		;heading 0
		nsLeft 0
		nsTop 115
		nsBottom 146
		nsRight 52
 		description "the reel-to-reel tape recorder"
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbDo
				(cond
					(recording
						(ego setScript: turnRecorderOff)
					)
					((== RTRloc onRecorder)
						(cond
							(rewoundTape
								(ego setScript: getRecordedTape)
							)
							(recorded
								(ego setScript: rewindTape)
							)
							((Btst pfHeard2Live2Screw)
								(ego setScript: turnRecorderOn)
							)
							(else
								(TPrint "What's the use of recording silence?
									There no signal currently being fed to this deck.")
							)
						)
					)
					(else
						(TPrint "The recorder's automatic shut-off prevents it
							from operating without a properly mounted tape.")
					)
				)
			)
			(verbLook
				(cond
					(recording
						(TPrint "The recorder is capturing every nuance of
							\"2 Live 2 Screw's\" conversation.")
					)
					((== RTRloc onRecorder)
						(cond
							(rewoundTape
								(TPrint "The recorder contains a tape full of
									incriminating evidence.")
							)
							(recorded
								(TPrint "This tape is half on the take-up reel.
									It needs to be rewound before you can remove it
									from the recorder.")
							)
							(else
								(TPrint "There is a blank tape on the recorder.")
							)
						)
					)
					(else
						(TPrint "A large Studer console recorder is all
							warmed up and ready to record--except there's
							no tape upon which to record!")
					)
				)
			)
			(verbUse
				(switch invItem
					(iReelToReelTape
						(cond
							(recorded
								(TPrint "Your tape is already recorded.")
							)
							(else
								(ego setScript: mountTapeScript)
							)
						)
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
(instance tape of Prop
	(properties
		view			vInsideControlRoomB
		loop			lRecorderReels1
		cel			0
		x				42
		y				130
		approachX	42
		approachY	163
 		description "the tape"
		lookStr 		"A large Studer console recorder is all warmed up and
						ready to record--except there's no tape upon which
						to record!"
	)
	(method (doVerb theVerb invItem)
		(recorder doVerb theVerb invItem)
	)
)
(instance tapeHole of View
	(properties
		view			vInsideControlRoomB
		loop			7
		cel			0
		x				158
		y				85
	)
	(method (doVerb theVerb invItem)
		(tapeShelf doVerb theVerb invItem)
	)
)
(instance hammer of Actor
	(properties
		view			vHammerActions
		loop			lUsingMixerLarge
		cel			0
		x				38
		y				120
 		description "P. C. Hammer"
		lookStr 		"P. C. Hammer is working in the next Control Room."
	)
)

(instance glass of Prop
	(properties
		view			vInsideControlRoomB
		loop			lBreakingWindow
		cel			1
		x				41
		y				116
		priority		9
		signal		fixPriOn
 		description "the glass window"
		lookStr 		"A heavy, soundproof, plate-glass window separates you
						from the outside--and your freedom!"
	)
)

(instance lights of Prop
	(properties
		view			vInsideControlRoomB
		loop			lSingingIntoMike
		cel			0
		x				53
		y				122
		approachX	90
		approachY	144
		priority		9
		signal		fixPriOn
 		description "the microphone mixer"
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbDo
				(mixer doVerb: theVerb invItem &rest)
			)
			(verbLook
				(mixer doVerb: theVerb invItem &rest)
			)
			(else
				(super doVerb: theVerb invItem &rest)
			)
		)
	)
)
(instance recSignA of Prop
	(properties
		view			vInsideControlRoomB
		loop			lControlRoomASign
		cel			cOn
		x				17
		y				51
		cycleSpeed	12
 		description "the recording sign"
		lookStr 		"This sign is illuminated whenever anyone is recording
						inside control room A."
	)
)

(instance useMikeSound of Sound
	(properties
		flags		1
		number	sUseMike
	)
)

(instance breakGlassSound of Sound
	(properties
		number	sBreakPot
		priority	11
	)
)

(instance useMikeScript of Script
	(method (changeState newState &tmp i invItem)
		(switchto (= state newState)
			(
				(HandsOff)
				(ego
					setMotion:	MoveTo 89 141 self
				)
			)
			(
				(ego
					view: 		vPattiInControlRoomB,
					setLoop		lUsingBoard,
					setCel: 		0,
					cycleSpeed: 12,
					setCycle: 	EndLoop self
				)
			)
			(
				(useMikeSound play:)
				(ego
					setLoop		lSingingIntoMike,
					setCel: 		0,
					setCycle:	EndLoop self
				)
			)
			(
				(TPrint "Taking your biggest possible diaphragmatic breath,
					you lean back and belt out your most powerful high C,
					directly into the microphone, through the powerful amplifiers
					and out through the control room monitor speakers!")
				(glass startUpd:, setCel: 2)
				(= ticks 123)
			)
			(
				(breakGlassSound play:)
				(glass
					setCycle: EndLoop self
				)
			)
			(
				(= seconds 3)
			)
			(
				(TPrint "Is it Patti...")
				(TPrint "...or is it Memorex?")
				(= seconds 4)
			)
			(
				(ego
					setLoop		lClimbingOutWindow1,
					setPri:		9,
					setCel:		0,
					cycleSpeed: 10,
					setCycle:	EndLoop self
				)
			)
			(
				(ego
					actions: 	0,
					setLoop		lClimbingOutWindow2,
					setCel: 		0,
					setCycle:	EndLoop self
				)
			)
			(
				(Points ptsShatteredGlass pfShatteredGlass)
				(= ticks 123)
			)
			(
				(ego
					view:			vPattiInControlRoomB,
					posn:			18 114,
					actions: 	0,
					setCel: 		0,
					setLoop		lPattiLeavingWindow,
					setCycle:	0,
				)
				(= ticks 123)
			)
			(
				(ego
					setMotion: MoveTo 1 114 self
				)
			)
			(
				(= seconds 3)
			)
			(
				(theMusic
					fade:	0 15 12 TRUE
				)
				(theIconBar disable:)
				(DrawPic pBlackPic (+ IRISIN BLACKOUT))
				(= ticks 30)
			)
			(
				(theIconBar enable:)
				(TPrint "Punching in the access code you learned by watching
					P. C. Hammer, you are able to ride the showervator back
					to John Krapper's bathroom.
					Luckily, no one noticed your clothes lying on the
					floor there.")
				(= seconds 3)
			)
			(
				(TPrint "Putting on your dress and gathering your possessions,
					you sneak back to the receptionist's lobby door and wait
					for her to take a break before making for the safety of
					your patiently waiting limo.")
				(= seconds 3)
			)
			(
				(for	((= i 0))
						(< i (Inventory size?))
						((++ i))
					(= invItem (Inventory at: i))
					(if (invItem ownedBy: 1000)
						(invItem owner: PATTI)
					)
				)
				(curRoom newRoom: 200)
			)
		)
	)
)
(instance GetTape of Script
	(method (changeState newState)
		(switchto (= state newState)
			(
				(HandsOff)
				(ego
					view: 		vPattiInControlRoomB,
					setLoop		lGettingTape,
					setCel:		0,
					cycleSpeed:	12,
					setCycle: 	EndLoop self,
				)
			)
			(
				(= ticks 30)
			)
			(
				(tapeHole init:, addToPic:)
				(ego
					setCel:		LASTCEL,
					setCycle: 	BegLoop self,
				)
			)
			(
				(ego
					get: 			iReelToReelTape,
					normalize: 	vPattiHammerpants,
					setHeading: 0,
				)
				(= ticks	30)
			)
			(
				(Points ptsGetBlankTape pfGetBlankTape)
				(TPrint "You grab a blank reel of tape from the shelf.")
				(HandsOn)
				(self dispose:)
			)
		)
	)
)
(instance turnRecorderOn of Script
	(method (changeState newState)
		(switchto (= state newState)
			(
				(HandsOff)
				(ego
					view: 		vPattiInControlRoomB,
					setLoop		lUsingBoard,
					setCel:		0,
					cycleSpeed:	12,
					setCycle: 	EndLoop self ,
				)
			)
			(
				(tape setCycle: Reverse, startUpd:)
				(ego
					normalize: 	vPattiHammerpants,
					loop:	4,
					cel:	1,
					setHeading: 270,
				)
				(= ticks 30)
			)
			(
				(Points ptsActivatedRecorder pfActivatedRecorder)
				(TPrint "You turn on the reel-to-reel tape recorder.")
  				(= recording TRUE)
				(= seconds 7)
			)
			(
				(TPrint "As you record \"2 Live 2 Screw,\"
					you giggle with delight as they thoroughly
					incriminate themselves.")
				(= seconds 3)
			)
			(
				(hammer
					setLoop:		1,
					setCycle:	EndLoop self,
				)
			)
			(
				(TPrint "What's that? What's P. C. Hammer doing?
					He looks like he's seen your \"Recording\" sign!
					And he doesn't look too happy about it.")
				(= seconds 3)
			)
			(
				(hammer
					cel:		0,
					setCycle: 0,
					setMotion: MoveTo 10 120 self
				)
			)
			(
				(TPrint "Oh, oh! Where did Hammer go?")
				(glass startUpd:)	  			;to stop carryover to next pic
				(= cycles 5)
			)
			(
				(self dispose:)
				(curRoom setScript: sCartoon)
			)
		)
	)
)
(instance turnRecorderOff of Script
	(method (changeState newState)
		(switchto (= state newState)
			(
				(HandsOff)
				(ego
					view: 		vPattiInControlRoomB,
					setLoop:		lUsingBoard,
					setCel:		0,
					cycleSpeed:	12,
					setCycle:	EndLoop self
				)
			)
			(
				(tape
					setCycle: 0,
					stopUpd:
				)
				(= ticks 30)
			)
			(
				(= recording FALSE)
				(= recorded TRUE)
				(ego
					normalize: 	vPattiHammerpants,
					loop:	4,
					cel:	1,
					setHeading: 270,
					observeControl: cYELLOW,
				)
				(= ticks 30)
			)
			(
				(TPrint "You turn off the recorder.")
				(HandsOn)
				(self dispose:)
			)
		)
	)
)
(instance rewindTape of Script
	(method (changeState newState)
		(switchto (= state newState)
			(
				(HandsOff)
				(ego
					view: 		vPattiInControlRoomB,
					setLoop:		lUsingBoard,
					setCel:		0,
					cycleSpeed:	12,
					setCycle:	EndLoop self
				)
			)
			(
				(= ticks 30)
			)
			(
				(ego
					normalize: 	vPattiHammerpants,
					loop:	4,
					cel:	1,
					setHeading: 270,
					observeControl: cYELLOW,
				)
				(tape
					setCycle: Forward,
					startUpd:,
					cycleSpeed: 0
				)
				(= seconds 7)
			)
			(
				(tape setCycle:0, stopUpd:)
				(= ticks 30)
			)
			(
				(TPrint "The tape is now fully rewound.")
				(= rewoundTape TRUE)
				(HandsOn)
				(self dispose:)
			)
		)
	)
)
(instance getRecordedTape of Script
	(method (changeState newState)
		(switchto (= state newState)
			(
				(HandsOff)
				(ego
					view: 		vPattiInControlRoomB,
					setLoop		lUsingRecorder,
					setCel:		3,
					cycleSpeed: 12,
					setCycle: 	CycleTo 2 BACKWARD self
				)
			)
			(
				(tape
					setLoop: 	1,
					setCel:		0,
					x:				42,
					y:				130,
					stopUpd:
				)
				(ego
					cycleSpeed: 12,
					setCycle: 	BegLoop self
				)
			)
			(
				(ego get: iReelToReelTape)
				((Inventory at: iReelToReelTape) state: TRUE)
				(ego
					normalize: 	vPattiHammerpants,
					loop:	4,
					cel:	1,
					setHeading: 270,
					observeControl: cYELLOW,
				)
				(= ticks 30)
			)
			(
				(Points ptsGetRecordedTape pfGetRecordedTape)
				(TPrint "You remove the reel-to-reel tape.")
				(= ticks 30)
			)
			(
				(HandsOn)
				(self dispose:)
			)
		)
	)
)
(instance useMixer of Script
	(method (changeState newState)
		(switchto (= state newState)
			(
				(HandsOff)
				(ego
					view: 		vPattiInControlRoomB,
					setLoop:		lUsingBoard,
					cycleSpeed: 12,
					setCel: 		0,
					setCycle: 	Oscillate 1 self
				)
			)
			(
				(ego
					loop:			4,
					cel:			1,
					setHeading: 270,
					normalize: 	vPattiHammerpants
				)
				(if jammed
					(ego observeControl: cYELLOW)
				)
				(= ticks 30)
			)
			(
				(if jammed
					(if volumeUp
						(TPrint "Since you've cranked it up to the point
							of feedback, you can't go any louder!
							Besides, it's almost loud enough to
							break glass.")
					else
						(TPrint "You crank up the volume on your control
							room's monitor speakers to an
							ear-piercing level.")
						(= volumeUp TRUE)
					)
				else
					(switch mixerDoCounter
						(1
							;**	can't get here from there
						)
						(2
							(TPrint "You bring up the volume control labeled
								\"Control Room C.\"")
							(Wait 60)
							(TPrint "You hear nothing but hiss.")
						)
						(3
							(TPrint "You bring up the potentiometer labeled
								\"Studio C.\"")
							(Wait 60)
							(TPrint "You hear nothing but hiss and a little
								air conditioner bleed-through.")
						)
						(4
							(TPrint "You pull up the slider labeled \"Control Room B\"
							 	and hear what sounds like a female breathing.")
							(Wait 60)
							(TPrint "(Oh, wait! That's you!
								You're in Control Room B!)" LOW)
						)
						(5
							(TPrint "You raise the volume on \"Studio B,\"")
							(Wait 60)
							(TPrint "You hear nothing.")
						)
						(6
							(TPrint "You carefully open the slider on \"Control Room
								A,\" as if P. C. Hammer could hear you doing so.")
							(Wait 60)
							(TPrint "He's recording his \"live drive-time\" show for
								tomorrow morning.
								Who cares?")
						)
						(else
							(if (not (Btst pfHeard2Live2Screw))
								(ego setScript: sHearingScrew)
							else
								(TPrint "You are eavesdropping on the conversations
									from Studio A and to you it sounds just like
									\"2 Live 2 Screw!\"")
							)
						)
					)
				)
				(= ticks 30)
			)
			(
				(HandsOn)
				(self dispose:)
			)
		)
	)
)

(instance sHearingScrew of Script
	(method (changeState newState)
		(switchto (= state newState)
			;**	the following only occurs when she's heard the voices

			(
				(TPrint "You fade up the volume on \"Studio A\" and...
					listen to that!")
				(lights setCycle: Forward, startUpd:)
				(= seconds 3)
			)
			(
				(TPrint "Why, that's \"2 Live 2 Screw!\" Patti! You did it!
					Now you can overhear their conversation!")
			 	(= seconds 3)
			)
			(
				(Points ptsHeard2Live2Screw pfHeard2Live2Screw)
				(TPrint "But how can you get some hard evidence to take back to
					Inspector Desmond to prove you really are hearing this!?")
				(= usedMixer TRUE)
				(HandsOn)
				(self dispose:)
			)
		)
	)
)
(instance mountTapeScript of Script
	(method (changeState newState)
		(switchto (= state newState)
			(
				(HandsOff)
				(Patti put: iReelToReelTape onRecorder)
				(ego
					view: vPattiInControlRoomB,
					setLoop	1,
					cycleSpeed: 12,
					setCel: 0,
					setCycle: CycleTo 2 FORWARD self
				)
			)
			(
				(tape
					setLoop: 0,
					x: 35,
					y: 142
				)
				(ego
					setCycle: EndLoop self
				)
			)
			(
				(ego
					normalize: vPattiHammerpants,
					loop:	4,
					cel:	1,
					setHeading: 270,
				)
				(= ticks 30)
			)
			(
				(TPrint "You place your tape on the recorder,
					carefully threading it around the tension arms,
					over the heads and past the capstan,
					finishing by threading the leader onto the takeup reel
					with a professional flourish.")
				(HandsOn)
				(self dispose:)
			)
		)
	)
)

;*************************************************************
;*																				 *
;*		Necessary things for PC Hammer cartoon in pic 880		 *
;*																				 *
;*************************************************************
(instance doorA of Door
	(properties
		view			vK-RAPRadioControlRoom
		loop			lDoorToA
		cel			0
		x				47
		y				77
		locked		TRUE
 		description "the Control Room A door"
		lookStr 		"Be careful, Patti! Inside that door stands the man
						you've come here to investigate:  P. C. Hammer!"
		sightAngle 	40
		approachX	64
		approachY	80
		signal		ignrAct
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbDo
				(TPrint "You don't want to try that door, Patti!
					The direct approach won't work with P. C. Hammer.")
			)
			(else
				(super doVerb: theVerb invItem &rest)
			)
		)
	)
)

(instance doorB of Door
	(properties
		entranceTo	890
		view			vK-RAPRadioControlRoom
		loop			lDoorToB
		cel			0
		x				199
		y				111
		exitType		doorWalkEgo
		enterType	doorWalkEgo
		locked		TRUE
 		description "the Control Room B door"
		lookStr 		"A sign on the door reads \"Control Room B.\"
						It is currently unoccupied."
		sightAngle 	40
		approachX	218
		approachY	116
		moveToX		220
		moveToY		116
	)
)

(instance hammer of Actor
	(properties
		view			vHammerActions
		loop			lUsingMixer
		cel			0
		x				82
		y				79
 		description "P. C. Hammer"
		lookStr 		"Through the control room window,
						you see P. C. Hammer mixing his \"live\" radio broadcast
						for tomorrow morning's drive time show."
	)
)

(instance mikestand of Actor
	(properties
		view			vK-RAPRadioControlRoom
		loop			lSingleCells
		cel			cMikeStand
		x				175
		y				121
 		description "the microphone stand"
		lookStr 		"A large boom microphone stand sits just outside
						the door to Control Room B."
 		signal		ignrAct
	)
)
(instance recSign of Prop
	(properties
		view			vK-RAPRadioControlRoom
		loop			lSign
		cel			cOn
		x				177
		y				42
		cycleSpeed	12
 		description "the recording sign"
		lookStr 		"This sign is illuminated whenever anyone is recording
						inside this control room."
	)
)
(instance ActionsKRAP of Actions
	(method (doit)
		(return FALSE)					;fixes a bug caused by (Patti doVerb)
	)										;in CueObj (features.sc)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbDo
				(TPrint "You attempt to hike up those Hammerpants you're wearing,
					but it does no good!")
				(return TRUE)
			)
			(verbZipper
				(TPrint "Maintaining your cool,
					you surreptitiously check the zipper on your giant baggy pants.
					It's fine.")
				(return TRUE)
			)
			(else
				(return FALSE)
			)
		)
	)
)

