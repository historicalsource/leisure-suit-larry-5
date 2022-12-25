;**
;**	Logics for Room 640 -- des Rever Records waiting room
;**
;**	LSL5:  Passionate Patti Does A Little Undercover Work
;**				Copyright 1991
;**	 	by Al Lowe
;**
;**	Programmed by Steve Conrad
;**
;**	Last Update:  September 10, 1991
;**

(script#		640)

(public
	rm640		0
)

(local
	seenMsg
	askedInToStudio
	moveNeedle
	music2Playing
	getRecordFirstTime = TRUE
	turntableCycler		;0 - none, 1 - Forward, 2 - Reverse
	turntableSpeed	= 18	;6 - 78 rpm, 18 - 33 rpm
	speakerCycler			;0 - none, 1 - MyRandCycle
)

(procedure
	TalkFromBooth
)


(define	pWaitingRoom				640)
(define	vWaitingRoom				640)
	(define	lElevatorDoor		0)
	(define	lStudioAdoor		1)
	(define	lGoldRecord			2)
	(define	lRecordLong			3)
(define	vPattiRoom					641)
	(define	lTakingGoldRecord	0)
(define	vReverseBiaz				642)
	(define	lWorkingPanel		0)

(define	pStereoClose-up			650)
(define	vStereoClose-up			650)
	(define	lRFswitch			0)
	(define	l33switch			1)
	(define	lTurntableWrecord	2)
	(define	lToneArm				3)
	(define	lSpeakerCone		4-5)

(define	vReverseBiazTalker		1672)


; Music defines

(define mShillMusak					620)
(define mSong33Forward				640)
(define mSong78Forward				641)
(define mSong33Backward				642)
(define mSong78Backward				643)
	(define sMessage1				644)
	(define sMessage2				645)
	(define sMessage3				646)
	(define sSwitch				201)
	(define sNeedleDownSpin		648)
	(define sElevatorDing		621)
	(define sElevatorDoor		622)


(instance rm640 of LLRoom
	(properties
		picture		pWaitingRoom
		north			660
	)

	(method (init)
		(ego
			init:,
			normalize: vPatti
		)
		(HandsOn)
		(switch prevRoomNum
			(north
				(ego
					posn:			189 86,
					setHeading:	180,
					edgeHit:		0
				)
				(= askedInToStudio TRUE)
				(if (and
						(not (Btst fRecordOnTurntable))
						(not (ego has: iGoldRecord))
					)
					(record
						init:				,
						approachVerbs: verbLook verbDo
					)
				)
				(if (Btst fRecordOnTurntable)
					(record2
						init:				,
						approachVerbs:	verbLook verbDo
					)
				)
				(theMusic
					number:		mShillMusak,
					setVol:		127,
					flags:		TRUE,
					play:
				)
			)
			(else
				(ego
					posn:			59 139,
					setHeading:	90,
					edgeHit:		0,
					setPri:		9,
					setScript:	sExitElevator
				)
				(record
					init:				,
					approachVerbs: verbLook verbDo
				)
			)
		)
		(LoadMany PICTURE pStereoClose-up)
		(LoadMany VIEW
			vStereoClose-up
			vPattiRoom
			vReverseBiazTalker
		)
		(LoadMany SOUND
 			mSong33Forward
 			mSong78Forward
 			mSong33Backward
 			mSong78Backward
	 		sMessage1
	 		sMessage2
	 		sMessage3
	 		sSwitch
	 		sNeedleDownSpin
	 		sElevatorDing
	 		sElevatorDoor
		)
		(super init:)
		(stereo
			init:				,
			approachVerbs: verbLook verbDo verbUse
		)
		(elevator
			init:				,
			approachVerbs: verbDo
		)
		(door
			init:				,
			approachVerbs:	verbDo
		)
		(reverseBiaz
			init:			,
			setScript:	sMonitorRecording
		)
		(statue init:)
		(speakerA init:)
		(speakerB init:)
		(plant init:)
		(shelves init:)
		(recordJacketsA init:)
		(recordJacketsB init:)
		(fGoldRecords 	 init:)
		(plaque
			init:				,
			approachVerbs: verbLook verbDo verbUse
		)

		(curRoom addObstacle:
			((Polygon new:)
				type: PBarredAccess,
				init:	0 117 99 117 105 126 62 149 77 149 125 118 145 112 186 102
						229 111 232 128 206 141 154 141 130 139 85 162 51 159 62 164
						54 175 28 175 16 170 0 175,
				yourself
			)
			((Polygon new:)
				type: PBarredAccess,
				init:	67 165 105 171 116 160 212 160 224 134 255 129 252 115
						193 100 0 100 0 93 220 93 287 107 286 139 236 148 241 175
						231 181 198 185 142 185 111 183 90 184 66 183 61 174,
				yourself
			)
			((Polygon new:)
				type: PBarredAccess,
				init:	103 83 208 83 201 78 201 52 178 52 178 79 0 79 0 0 319 0
						319 189 245 189 245 166 290 160 301 171 317 161 289 130
						315 130 315 108 279 78 227 78 228 83 256 83 262 86 253 92
						226 92 215 90 100 90,
				yourself
			)
		)
	)

	(method (doit)
		(if (and (StepOn ego cBLUE) (not askedInToStudio))
			(TalkFromBooth)
		)
		(if (StepOn ego cGREEN)
			(if (not askedInToStudio)
				(TalkFromBooth)
			)
		)
		(super doit: &rest)
	)

	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbWalk
				(if (== curPic 650)
					(curRoom setScript: sReturnsFromStereo)
					TRUE
				)
			)
			(verbLook
				(if (== curPic 650)
					(super doVerb: theVerb invItem &rest)
				else
					(TPrint "The waiting room is filled with an impressive
						array of recording memorabilia.")
				)
			)
			(else
				(super doVerb: theVerb invItem &rest)
			)
		)
	)

	(method (newRoom roomNumber)
		(super newRoom: roomNumber)
		(if (not (== roomNumber 620))
			(theMusic fade: 0 15 12 TRUE)
			(theMusic2 fade: 0 15 12 TRUE)
		else
			(if music2Playing
				(theMusic
					setVol:	127,
					play:
				)
				(theMusic2 fade: 0 15 12 TRUE)
			)
		)
	)

;SRC	(method (dispose)
;SRC		(super dispose: &rest)
;SRC		(theMusic
;SRC			fade: 0 15 12 TRUE
;SRC		)
;SRC		(theMusic2
;SRC			fade:	0 15 12 TRUE
;SRC		)
;SRC	)
)


;*********** Scripts ************

(instance sExitElevator of Script
	(method (changeState newState)
		(switchto (= state newState)
			(
				(HandsOff)
				(= seconds 2)
			)
			(
				(elevatorDing play:)
				(= cycles 15)
			)
			(
				(elevatorDoor play:)
				(elevator setCycle: EndLoop self)
			)
			(
				(elevatorDoor stop:)
				(elevator setPri:	9)
				(ego
					setPri:		9,
					setMotion:	MoveTo 73 148
				)
				(= seconds 2)
			)
			(
				(elevatorDoor play:)
				(elevator setCycle: BegLoop self)
			)
			(
				(elevatorDoor stop:)
				(ego setPri: -1)
				(elevator stopUpd: TRUE)
				(HandsOn)
				(self dispose:)
			)
		)
	)
);sExitElevator

(instance sGetRecord of Script
	(method (changeState newState)
		(switchto (= state newState)
			(
				(User canInput: FALSE)
				(ego
					setHeading:	0 self
				)
			)
			(
				(ego
					view: 		vPattiRoom,
					setLoop:		lTakingGoldRecord,
					setCel:		0,
					cycleSpeed:	12,
					setCycle:	EndLoop self
				)
			)
			(
				(record dispose:)
				(ego
					get:			iGoldRecord,
					setCycle:	BegLoop self
				)
			)
			(
				(if getRecordFirstTime
					(Points ptsGetRecord pfGetRecord)
					(= getRecordFirstTime FALSE)
					(TPrint "Wriggling the gold record on its plaque,
						you discover it is loose enough to be removed.
						So you do.")
					(TPrint "You've wanted to have a gold record ever
						since you began playing professionally at age thirteen.")
					(TPrint "(But you never dreamed you would get it like this!)"
						LOW)
				)
				(ego
					normalize: 	vPatti,
					loop:			facingNorth
				)
				(User canInput: TRUE)
				(self dispose:)
			)
		)
	)
);sGetRecord

(instance sMonitorRecording of Script
	(method (changeState newState)
		(switchto (= state newState)
			(
				(= seconds 10)
			)
			(
				(if (Random 0 1)
					(reverseBiaz
						setLoop:		0,
						setCel:		0,
						setCycle:	EndLoop self
					)
				else
					(reverseBiaz
						setLoop:		1,
						setCel:		0,
						setCycle:	EndLoop self
					)
				)
			)
			(
				(reverseBiaz
						setCycle:	BegLoop self
				)
			)
			(
				(self changeState: 0)
			)
		)
	)
);sMonitorRecording

(instance sSpeed78Reverse of Script
	(method (changeState newState)
		(switchto (= state newState)
			(
				(if (needle cel?)
					(= seconds 10)
				else
					(self dispose:)
				)
			)
			(
				(HandsOff)
				(if (not (DoSound NumDACs))
					(TPrint "Amidst the hiss and crackle of this old LP,
						you can just barely make out a husky female voice saying,
						\"Porn is best at a quarter a whack!\""
						HIGH
						#dispose: self
					)
				else
					(theMusic2 setVol: 80)
					(message1 play: self)
				)
			)
			(
				(= ticks 30)
			)
			(
				(theMusic2 setVol: 127)
				(Points ptsHeard78R pfHeard78R)
				(TPrint "Of all the nerve!
					des Rever Records imprints subliminal messages
					encouraging young Americans to frequent adult bookstores!"
					HIGH
					#dispose: self
				)
			)
			(
				(= ticks 30)
			)
			(
				(TPrint "At last! You've found some good solid evidence for
					Inspector Desmond."
					LOW
					#dispose: self
				)
			)
			(
				(HandsOn)
				(User canControl: FALSE)
				(self dispose:)
			)
		)
	)
);sSpeed78Reverse

(instance sSpeed33Reverse of Script
	(method (changeState newState)
		(switchto (= state newState)
			(
				(if (needle cel?)
					(= seconds 10)
				else
					(self dispose:)
				)
			)
			(
				(HandsOff)
				(if (not (DoSound NumDACs))
					(TPrint "Although the music sounds quite garbled backwards,
						you feel certain you can understand the words,\n
						\"Just Say Yes!\""
						HIGH
						#dispose: self
					)
				else
					(theMusic2 setVol: 80)
					(message2 play: self)
				)
			)
			(
				(= ticks 30)
			)
			(
				(theMusic2 setVol: 127)
				(Points ptsHeard33R pfHeard33R)
				(TPrint "\"Just Say Yes!\"
					Why, that's just the opposite of what Nancy Reagan said!
					You now have the incontrovertible evidence Desmond sent you
					to find!
					And you finally understand what evil this place does!"
					HIGH
					#dispose: self
				)
			)
			(
				(= ticks 30)
			)
			(
				(TPrint "How vile! How corrupt!
					des Rever Records records backwards music urging innocent
					teenagers to violate the law!
					You never really believed this before--but you believe it now!"
					HIGH
					#dispose: self
				)
			)
			(
				(= ticks 30)
			)
			(
				(TPrint "If you could only obtain more
					evidence to prove your case."
					HIGH
					#dispose: self
				)
			)
			(
				(HandsOn)
				(User canControl: FALSE)
				(self dispose:)
			)
		)
	)
);sSpeed33Reverse

(instance sSpeed78Forward of Script
	(method (changeState newState)
		(switchto (= state newState)
			(
				(if (needle cel?)
					(= seconds 10)
				else
					(self dispose:)
				)
			)
			(
				(HandsOff)
				(if (not (DoSound NumDACs))
					(TPrint "Although it seems improbable,
						you feel sure you can understand a message among
						the sped-up music:
						\"Digital Audio Tape causes impotence.\"
						How odd."
						HIGH
						#dispose: self
					)
				else
					(theMusic2 setVol: 80)
					(message3 play: self)
				)
			)
			(
				(= ticks 30)
			)
			(
				(theMusic2 setVol: 127)
				(Points ptsHeard78 pfHeard78)
				(TPrint "(Hmm.
					This must be a \"pre-Sony\" Columbia recording!)"
					LOW
					#dispose: self
				)
			)
			(
				(HandsOn)
				(User canControl: FALSE)
				(self dispose:)
			)
		)
	)
);sSpeed78Forward

(instance sSpeed33Forward of Script
	(method (changeState newState)
		(switchto (= state newState)
			(
				(if (needle cel?)
					(= seconds 10)
				else
					(self dispose:)
				)
			)
			(
				(HandsOff)
				(Points ptsHeard33 pfHeard33)
				(TPrint "Well, that's interesting.
					You've always wondered if there was really a record
					underneath all that \"gold.\""
					HIGH
					#dispose: self
				)
			)
			(
				(HandsOn)
				(User canControl: FALSE)
				(self dispose:)
			)
		)
	)
);sSpeed33Forward

(instance sUsesStereo of Script
	(method (changeState newState)
		(switchto (= state newState)
			(
				(User canControl: FALSE)
				(User canInput: TRUE)

				(cast eachElementDo: #hide:)
				(statue dispose:)
				(speakerA dispose:)
				(speakerB dispose:)
				(plant dispose:)
				(shelves dispose:)
				(recordJacketsA dispose:)
				(recordJacketsB dispose:)
				(fGoldRecords dispose:)
				(plaque dispose:)

				(reverseButton
					init:,
					stopUpd:
				)
				(stopButton
					init:,
					stopUpd:
				)
				(forwardButton
					init:,
					stopUpd:
				)
				(button33
					init:,
					stopUpd:
				)
				(button78
					init:,
					stopUpd:
				)
				(needle init:)

				(if (Btst fRecordOnTurntable)
					(turnTableWRecord
						init:				,
						setCycle:		(cond
												((== turntableCycler 0)
													0
												)
												((== turntableCycler 1)
													Forward
												)
												((== turntableCycler 2)
													Reverse
												)
											),
						cycleSpeed:		turntableSpeed
					)
					(record2 dispose:)
				else
					(turntable init:)
				)
				(speakerOne
					init:			,
					setCycle:	(if speakerCycler
										MyRandCycle
									else
										0
									)
				)
				(speakerTwo
					init:			,
					setCycle:	(if speakerCycler
										MyRandCycle
									else
										0
									)
				)

				(curRoom drawPic: 650)
				(SetupExit TRUE)
				(theIconBar curIcon: (theIconBar at: doIcon))
				(theGame setCursor: ((theIconBar curIcon?) cursor?))
				(stereo dispose:)
				(theMusic fade: 0 15 12 FALSE self)
			)
			(
				(theMusic pause: TRUE)
				(self dispose:)
			)
		)
	)
);sUsesStereo

(instance sReturnsFromStereo of Script
	(method (changeState newState)
		(switchto (= state newState)
			(
				(HandsOn)
				(if (curRoom script?)
					((curRoom script?) dispose:)
				)
				(if (not (needle cel?))
					(theMusic
						setVol:	127,
						pause: 	FALSE
					)
				)
				(stopButton		dispose:)
				(forwardButton	dispose:)
				(reverseButton	dispose:)
				(button33		dispose:)
				(button78		dispose:)
				(needle			dispose:)

				(if (Btst fRecordOnTurntable)
					(if (turnTableWRecord cycler?)
						(if ((turnTableWRecord cycler?) isKindOf: Forward)
							(= turntableCycler 1)
						else
							(if ((turnTableWRecord cycler?) isKindOf: Reverse)
						 		(= turntableCycler 2)
							)
						)
						(= music2Playing TRUE)
					else
						(= turntableCycler 0)
						(= music2Playing FALSE)
					)
					(record2 init:)
					(= turntableSpeed (turnTableWRecord cycleSpeed?))
					(turnTableWRecord	dispose:)
				else
					(turntable dispose:)
				)
				(if (speakerOne cycler?)
					(= speakerCycler 1)
				else
					(= speakerCycler 0)
				)
				(speakerOne		dispose:)
				(speakerTwo		dispose:)

				(if (not (ego has: iGoldRecord))
					(record show:)
				)
		 		(stereo
					init:				,
					approachVerbs: verbLook verbDo verbUse
				)
				(curRoom		drawPic: (curRoom picture?))
				(elevator 	show:)
				(ego 			show:)
				(door 		show:)
				(reverseBiaz show:)

				(statue init:)
				(speakerA init:)
				(speakerB init:)
				(plant init:)
				(shelves init:)
				(recordJacketsA init:)
				(recordJacketsB init:)
				(fGoldRecords init:)
				(plaque
					init:				,
					approachVerbs: verbLook verbDo verbUse
				)

				(SetupExit FALSE)
				(User canControl: TRUE)
				(self dispose:)
			)
		)
	)
);sReturnsFromStereo

(instance sStopButtonPressed of Script
	(method (changeState newState)
		(switchto (= state newState)
			(
				(if (needle cel?)
					(HandsOff)
				)
				(stopButton startUpd:)
				(= cycles 1)
			)
			(
				(recordSwitch play:)
				(stopButton
					setPri:		10,
					setCycle:	Oscillate 1 self
				)
			)
			(
				(cond
					((not (turnTableWRecord cycler?))
						(TPrint "The record player is STILL stopped!" LOW)
					)
					((not (Btst fRecordOnTurntable))
						(TPrint "You should see how well it stops when there's a
							record on it!"
							LOW
						)
					)
					(else
						(if (needle cel?)
							(needle doVerb: verbDo)
						)
					)
				)
				(= cycles 1)
			)
			(
				(User canControl: FALSE)
				(= seconds 3)
			)
			(
				(turnTableWRecord setCycle: 0)
				(stopButton
					setPri:	-1,
					stopUpd:
				)
				(self dispose:)
			)
		)
	)
);sStopButtonPressed

(instance sForwardButtonPressed of Script
	(method (changeState newState)
		(switchto (= state newState)
			(
				(forwardButton startUpd:)
				(= cycles 1)
			)
			(
				(recordSwitch play:)
				(forwardButton
					setPri:		10,
					setCycle:	Oscillate 1 self
				)
			)
			(
				(if (turnTableWRecord cycler?)
					(if (not ((turnTableWRecord cycler?) isKindOf: Forward))	;if not already forward
						(turnTableWRecord setCycle: Forward)
						(if (needle cel?)
							(= moveNeedle FALSE)
							(speakerOne cue:)
						)
						(if (== (turnTableWRecord cycleSpeed?) 6)
							(curRoom setScript: sSpeed78Forward)
						else
							(curRoom setScript: sSpeed33Forward)
						)
					)
				else
					(if (Btst fRecordOnTurntable)
						(turnTableWRecord setCycle: Forward)
					else
						(TPrint "This turntable is pressure-sensitive.
							It will not turn without a record present.")
					)
				)
				(forwardButton
					setPri:	-1,
					stopUpd:
				)
				(self dispose:)
			)
		)
	)
);sForwardButtonPressed


(instance sReverseButtonPressed of Script
	(method (changeState newState)
		(switchto (= state newState)
			(
				(reverseButton startUpd:)
				(= cycles 1)
			)
			(
				(recordSwitch play:)
				(reverseButton
					setPri:		10,
					setCycle:	Oscillate 1 self
				)
			)
			(
				(if (turnTableWRecord cycler?)
					(if (not ((turnTableWRecord cycler?) isKindOf: Reverse))	;if not already reverse
						(turnTableWRecord setCycle: Reverse)
						(if (needle cel?)
							(= moveNeedle FALSE)
							(speakerOne cue:)
						)
						(if (== (turnTableWRecord cycleSpeed?) 18)
							(curRoom setScript: sSpeed33Reverse)
						else
							(if (== (turnTableWRecord cycleSpeed?) 6)
								(curRoom setScript: sSpeed78Reverse)
							)
						)
					)
				else
					(if (Btst fRecordOnTurntable)
						(turnTableWRecord setCycle: Reverse)
					else
						(TPrint "This turntable is pressure-sensitive.
							It will not turn without a record present.")
					)
				)
				(reverseButton
					setPri:	-1,
					stopUpd:
				)
				(self dispose:)
			)
		)
	)
);sReverseButtonPressed

(instance sButton33Pressed of Script
	(method (changeState newState)
		(switchto (= state newState)
			(
				(button33 startUpd:)
				(= cycles 1)
			)
			(
				(recordSwitch play:)
				(button33
					setPri:		10,
					setCycle:	Oscillate 1 self
				)
			)
			(
				(if (turnTableWRecord cycler?)
					(if (== (turnTableWRecord cycleSpeed?) 6)		;if speed is 78
						(turnTableWRecord cycleSpeed: 18)
						(if (needle cel?)
							(= moveNeedle FALSE)
							(speakerOne cue:)
						)
						(if ((turnTableWRecord cycler?) isKindOf: Reverse)
							(curRoom setScript: sSpeed33Reverse)
						else
							(if ((turnTableWRecord cycler?) isKindOf: Forward)
								(curRoom setScript: sSpeed33Forward)
							)
						)
					)
				else
					(turnTableWRecord cycleSpeed: 18)
					(TPrint "The turntable is now prepared to revolve thirty-three
						and one-third times per minute.")
					(TPrint "(But only when it's ON!)" LOW)
				)
				(button33
					setPri:	-1,
					stopUpd:
				)
				(self dispose:)
			)
		)
	)
);sButton33Pressed

(instance sButton78Pressed of Script
	(method (changeState newState)
		(switchto (= state newState)
			(
				(button78 startUpd:)
				(= cycles 1)
			)
			(
				(recordSwitch play:)
				(button78
					setPri:		10,
					setCycle:	Oscillate 1 self
				)
			)
			(
				(if (turnTableWRecord cycler?)
					(if (== (turnTableWRecord cycleSpeed?) 18)		;if speed is 33
						(turnTableWRecord cycleSpeed: 6)
						(if (needle cel?)
							(= moveNeedle FALSE)
							(speakerOne cue:)
						)
						(if ((turnTableWRecord cycler?) isKindOf: Reverse)
							(curRoom setScript: sSpeed78Reverse)
						else
							(curRoom setScript: sSpeed78Forward)
						)
					)
				else
					(turnTableWRecord cycleSpeed: 6)
					(TPrint "The turntable is now prepared to revolve
						seventy-eight times per minute.")
					(TPrint "(But only when it's ON!)" LOW)
				)
				(button78
					setPri:	-1,
					stopUpd:
				)
				(self dispose:)
			)
		)
	)
);sButton78Pressed

(instance sEnterElevator of Script
	(method (changeState newState)
		(switchto (= state newState)
			(
				(HandsOff)
				(elevatorDing play:)
				(= cycles 15)
			)
			(
				(elevatorDoor play:)
				(elevator setCycle: EndLoop self)
			)
			(
				(elevatorDoor stop:)
				(elevator setPri: 10)
				(ego
					setPri:		9,
					setMotion: 	MoveTo 59 139 self
				)
			)
			(
				(ego
					setHeading:	90 self
				)
			)
			(
				(Say reverseTalker "\"Patti, I promise you:
					you'll never work in this town again!\""
					#dispose:
					#caller: self
				)
			)
			(
				(elevatorDoor play:)
				(elevator setCycle: BegLoop self)
			)
			(
				(elevatorDoor stop:)
				(curRoom newRoom: 620)				;Back to Shill lobby
				(self dispose:)
			)
		)
	)
);sEnterElevator

(instance sPlayMusic of Script
	(method (changeState newState)
		(switchto (= state newState)
			(
				(if moveNeedle
					(needleDownSpin play: self)
				else
					(= cycles 1)
				)
			)
			(
				(HandsOn)
				(User canControl: FALSE)
				(if ((turnTableWRecord cycler?) isKindOf: Forward)
					(if (== (turnTableWRecord cycleSpeed?) 6)
						(theMusic2
							number:		mSong78Forward,
							flags:		TRUE,
							setLoop:		-1,
							play:
						)
						(curRoom setScript: sSpeed78Forward)
					else
						(theMusic2
							number:		mSong33Forward,
							flags:		TRUE,
							setLoop:		-1,
							play:
						)
						(curRoom setScript: sSpeed33Forward)
					)
				else
					(if ((turnTableWRecord cycler?) isKindOf: Reverse)
						(if (== (turnTableWRecord cycleSpeed?) 18)
							(theMusic2
								number:		mSong33Backward,
								flags:		TRUE,
								setLoop:		-1,
								play:
							)
							(curRoom setScript: sSpeed33Reverse)
						else
							(if (== (turnTableWRecord cycleSpeed?) 6)
								(theMusic2
									number:		mSong78Backward,
									flags:		TRUE,
									setLoop:		-1,
									play:
								)
								(curRoom setScript: sSpeed78Reverse)
							)
						)
					)
				)
				(self dispose:)
			)
		)
	)
);sPlayMusic

;*********** Objects ************

(instance stereo of Feature
	(properties
		x 				174
		y 				163
		z				3
		nsLeft 		151
		nsTop 		154
		nsBottom 	167
		nsRight 		197
		approachX	172
		approachY	185
 		description "the stereo"
		sightAngle 	40
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbLook
				(curRoom setScript: sUsesStereo)
			)
			(verbDo
				(curRoom setScript: sUsesStereo)
			)
			(verbUse
				(if (== invItem iGoldRecord)
					(Bset fRecordOnTurntable)
					(ego put: iGoldRecord)
					(curRoom setScript: sUsesStereo)
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
(instance plaque of Feature
	(properties
		x 167
		y 161
		z 29
		approachX	159
		approachY	183
		heading 0
		nsLeft 153
		nsTop 119
		nsBottom 145
		nsRight 181
 		description "the plaque"
		sightAngle 40
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbLook
				(if (cast contains: record)
					(TPrint "One of des Rever Records' gold records
						is mounted on a simulated solid walnut plaque.")
				else
					(TPrint "This plaque once contained a gold record.")
				)
			)
			(verbUse
				(switch invItem
					(iGoldRecord
						(record init:)
						(ego put: iGoldRecord)
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
(instance record of View
	(properties
		view			vWaitingRoom
		loop			2
		x 				169
		y 				162
		z				33
		approachX	159
		approachY	183
 		description "the gold record"
		sightAngle 	40
		priority		14
		signal		(| fixPriOn ignrAct stopUpdOn)
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbDo
				(ego setScript: sGetRecord)
			)
			(verbLook
				(TPrint "It says,
					\"This gold record is awarded to Reverse Biaz, Chief Engineer,
					`des Rever Records.'\"")
			)
			(else
				(super doVerb: theVerb invItem &rest)
			)
		)
	)
)
(instance record2 of View
	(properties
		view			vWaitingRoom
		loop			lRecordLong
		x 				154
		y 				164
		z				16
		approachX	159
		approachY	183
 		description "the stereo"
		sightAngle 	40
		priority		14
		signal		(| fixPriOn ignrAct stopUpdOn)
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbLook
				(curRoom setScript: sUsesStereo)
			)
			(verbDo
				(curRoom setScript: sUsesStereo)
			)
			(else
				(super doVerb: theVerb invItem &rest)
			)
		)
	)
)
(instance elevator of Prop
	(properties
		view			vWaitingRoom
		x				50
		y				77
		approachX	65
		approachY	152
		cycleSpeed	12
		priority		10
		signal		(| ignrAct fixPriOn stopUpdOn)
		description "the elevator"
		lookStr		"This is the elevator in which you arrived."
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbDo
				(cond
					((Btst fWarned)
						(curRoom setScript: sEnterElevator)
					)
					(askedInToStudio
						(Bset fWarned)
						(Say reverseTalker "\"Where are you going, Patti?
							There are a lot of people in this town who would
							love a chance to blow synth on a session here.
							I was happy to give you the gig.
							But, if you leave me now,
							I promise you I'll be on the phone to the Union
							before you're downstairs!\""
							#dispose:
						)
					)
					(else
						(TalkFromBooth)
					)
				)
			)
			(else
				(super doVerb: theVerb invItem &rest)
			)
		)
	)
)
(instance reverseBiaz of Prop
	(properties
		view			vReverseBiaz
		loop			0
		cel			0
		x				251
		y				56
		cycleSpeed	12
		description "Reverse Biaz"
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbLook
				(TPrint "This is your man: Reverse Biaz,
					chief engineer of `des Rever Records' and
					the man you've been sent to investigate.")
				(if (not askedInToStudio)
					(TalkFromBooth)
				)
			)
			(verbTalk
				(TPrint "The double-paned, soundproof glass prevents
					him from hearing you.")
				(if (not askedInToStudio)
					(TalkFromBooth)
				)
			)
			(verbDo
				(TPrint "It's difficult to do anything through this glass.")
			)
			(verbUse
				(switch invItem
					(iChampagne
						(TPrint "You don't pour THROUGH glass,
							you pour INTO glass!")
					)
					(else
						(TPrint "He's probably not going to break that
							double-paned, soundproof glass to accept it, Patti!")
					)
				)
			)
			(verbZipper
				(TPrint "Gee, Patti, he IS cute--but aren't you here for
					business?")
			)
			(else
				(TPrint "There's nothing you can do with Reverse Biaz from
					out here.")
			)
		)
	)
)
(instance turntable of Feature
	(properties
		x 				174
		y 				160
		nsLeft 		112
		nsTop 		109
		nsBottom 	132
		nsRight 		191
 		description "the turntable"
		sightAngle 	40
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbLook
				(TPrint "What an unusual turntable;
					it still has a 78 RPM setting.")
			)
			(verbDo
				(TPrint "The turntable doesn't require a push start.")
			)
			(verbUse
				(switch invItem
					(iGoldRecord
						(Bset fRecordOnTurntable)
						(ego put: iGoldRecord)
						(turnTableWRecord	init:)
						(theIconBar curIcon: (theIconBar at: doIcon))
						(theGame setCursor: ((theIconBar curIcon?) cursor?))
						(self dispose:)
					)
					(else
						(super doVerb: theVerb invItem &rest)
					)
				)
			)
			(verbWalk
				(curRoom setScript: sReturnsFromStereo)
			)
			(else
				(super doVerb: theVerb invItem &rest)
			)
		)
	)
)
(instance turnTableWRecord of Prop
	(properties
		view	vStereoClose-up
		loop	10
		x		150
		y		133
		cycleSpeed 18
		description "the gold record"
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbLook
				(if (self cycler?)
					(TPrint "Round and round and round it goes.")
				else
					(TPrint "Why don't you take it for a spin?")
				)
			)
			(verbDo
				(if (self cycler?)
					(TPrint "Don't remove the gold record while it's spinning!")
				else
					(Bclr fRecordOnTurntable)
					(ego get: iGoldRecord)
					(turntable init:)
					(self dispose:)
				)
			)
			(verbWalk
				(curRoom setScript: sReturnsFromStereo)
			)
			(else
				(super doVerb: theVerb invItem &rest)
			)
		)
	)
)
(instance needle of Prop
	(properties
		view	vStereoClose-up
		loop	6
		x		191
		y		111
		cycleSpeed 12
		priority 11
		signal (| fixPriOn stopUpdOn)
		description "the tone arm"
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbLook
				(if (self cel?)
					(TPrint "That stylus is really \"getting in the groove.\"")
				else
					(TPrint "The tone arm rests gently in its holder,
						awaiting your next action.")
				)
			)
			(verbDo
				(if (self cel?)
					(-= y 1000)	; restore turntable's ability to get events
					(= z 0)
					(HandsOff)
					(self setCycle: BegLoop speakerOne)
					; Let the stopButton finish stopping the turntable
					(cond
						((== (curRoom script?) sStopButtonPressed)
							((curRoom script?) cue:)
						)
						((curRoom script?)
							((curRoom script?) dispose:)
						)
					)
				else
					(if (Btst fRecordOnTurntable)
						(if (and
								(turnTableWRecord cycler?)
								(not (curRoom script?))
							)
							(+= y 1000)	; so we get events before turntable
							(= z 1000)
							(= moveNeedle TRUE)
							(HandsOff)
							(self setCycle: EndLoop speakerOne)
						else
							(TPrint "Bring the turntable up to speed before applying
								the tonearm and cartridge.")
						)
					else
						(TPrint "Don't do that! You'll wreck the stylus.")
					)
				)
			)
			(verbWalk
				(curRoom setScript: sReturnsFromStereo)
			)
			(else
				(super doVerb: theVerb invItem &rest)
			)
		)
	)
)
(instance speakerOne of Prop
	(properties
		view	vStereoClose-up
		loop	8
		x		243
		y		114
		cycleSpeed 2
		description "the speakers"
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbLook
				(TPrint "A small plaque on the rear of the speaker reads,
					\"Digital Ready.\"")
			)
			(verbWalk
				(curRoom setScript: sReturnsFromStereo)
			)
			(else
				(super doVerb: theVerb invItem &rest)
			)
		)
	)
	(method (cue)
		(needle stopUpd:)
		(if (needle cel?)
			(self setCycle: MyRandCycle)
			(speakerTwo setCycle: MyRandCycle)

			(self setScript sPlayMusic)
		else
			(HandsOn)
			(User canControl: FALSE)
			(theMusic2 stop:)
			(self setCycle: 0)
			(speakerTwo setCycle: 0)
		)
	)
)
(instance speakerTwo of Prop
	(properties
		view	vStereoClose-up
		loop	9
		x		75
		y		117
		cycleSpeed 2
		description "the speakers"
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbLook
				(TPrint "A small plaque on the rear of the speaker reads,
					\"Digital Ready.\"")
			)
			(verbWalk
				(curRoom setScript: sReturnsFromStereo)
			)
			(else
				(super doVerb: theVerb invItem &rest)
			)
		)
	)
)

(class MyRandCycle of Cycle
	(method (doit)
		(++ cycleCnt)
		(if (> cycleCnt (client cycleSpeed?))
			(client setCel: (Random 0 1))
			(= cycleCnt 0)
		)
	)
)

(instance door of Door
	(properties
		entranceTo	660
		view			vWaitingRoom
		loop			1
		x				177
		y				21
		signal		stopUpdOn
		approachX	195
		approachY	83
		moveToX		195
		moveToY		73
		exitType		doorWalkEgo
		enterType	doorWalkEgo
		state			doorClosed
		description "the studio door"
		lookStr		"A small plaque on the door reads, \"Studio A.\""
	)
)

(instance reverseButton of Prop
	(properties
		x 105
		y 159
		view	650
 		description "the Reverse button"
		sightAngle 40
		cycleSpeed 12
		signal stopUpdOn
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbDo
				(curRoom setScript: sReverseButtonPressed)
			)
			(verbLook
				(TPrint "This button is marked \"Reverse.\"")
			)
			(verbWalk
				(curRoom setScript: sReturnsFromStereo)
			)
			(else
				(super doVerb: theVerb invItem &rest)
			)
		)
	)
)
(instance stopButton of Prop
	(properties
		x 120
		y 160
		view 650
		loop 1
 		description "the Stop button"
		sightAngle 40
		cycleSpeed 12
		signal stopUpdOn
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbDo
				(curRoom setScript: sStopButtonPressed)
			)
			(verbLook
				(TPrint "This button is marked \"Stop.\"")
			)
			(verbWalk
				(curRoom setScript: sReturnsFromStereo)
			)
			(else
				(super doVerb: theVerb invItem &rest)
			)
		)
	)
)
(instance forwardButton of Prop
	(properties
		x 134
		y 161
		view 650
		loop 2
 		description "the Forward button"
		sightAngle 40
		cycleSpeed 12
		signal stopUpdOn
	)
 	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbDo
				(curRoom setScript: sForwardButtonPressed)
			)
			(verbLook
				(TPrint "This button is marked \"Forward.\"")
			)
			(verbWalk
				(curRoom setScript: sReturnsFromStereo)
			)
			(else
				(super doVerb: theVerb invItem &rest)
			)
		)
	)
)
(instance button33 of Prop
	(properties
		x 188
		y 161
		view 650
		loop 3
 		description "the 33 button"
		sightAngle 40
		cycleSpeed 12
		signal stopUpdOn
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbDo
				(curRoom setScript: sButton33Pressed)
			)
			(verbLook
				(TPrint "This button is marked \"33.\"")
			)
			(verbWalk
				(curRoom setScript: sReturnsFromStereo)
			)
			(else
				(super doVerb: theVerb invItem &rest)
			)
		)
	)
)
(instance button78 of Prop
	(properties
		x 				217
		y 				159
		view			650
		loop			5
 		description "the 78 button"
		sightAngle 	40
		cycleSpeed 12
		signal stopUpdOn
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbDo
				(curRoom setScript: sButton78Pressed)
			)
			(verbLook
				(TPrint "This button is marked \"78.\"")
			)
			(verbWalk
				(curRoom setScript: sReturnsFromStereo)
			)
			(else
				(super doVerb: theVerb invItem &rest)
			)
		)
	)
)
(instance statue of Feature
	(properties
		x 31
		y 141
		z 0
		heading 0
		nsLeft 24
		nsTop 114
		nsBottom 169
		nsRight 43
 		description "the statue"
		lookStr 		"This sculpture represents the patron saint of recording
						companies--\n St. Needle."
		sightAngle 40
	)
)
(instance speakerA of Feature
	(properties
		x 121
		y 200
		z 50
		nsLeft 112
		nsTop 137
		nsBottom 164
		nsRight 131
 		description "the left speaker"
		lookStr 		"This transducer converts electrical energy into
						rapidly pulsating sound waves."
		sightAngle 40
	)
)
(instance speakerB of Feature
	(properties
		x 215
		y 199
		z 50
		nsLeft 205
		nsTop 137
		nsBottom 161
		nsRight 225
 		description "the right speaker"
		lookStr 		"This transducer converts electrical energy into
						rapidly pulsating sound waves."
		sightAngle 40
	)
)
(instance plant of Feature
	(properties
		x 233
		y 71
		z 0
		heading 0
		nsLeft 220
		nsTop 45
		nsBottom 93
		nsRight 246
 		description "the plant"
		lookStr 		"Upon close examination, you determine this is a plant."
		sightAngle 40
	)
)
(instance shelves of Feature
	(properties
		x 268
		y 110
		z 0
		heading 0
		nsLeft 257
		nsTop 83
		nsBottom 137
		nsRight 279
 		description "the shelves"
		lookStr 		"The office's shelves are filled with records, tapes,
						and books."
		sightAngle 40
	)
)
(instance recordJacketsA of Feature
	(properties
		x 133
		y 15
		z 0
		heading 0
		nsLeft 104
		nsTop 10
		nsBottom 20
		nsRight 162
 		description "the record jackets"
		lookStr 		"Many famous record album jackets adorn the walls of
						the office of des Rever Records."
		sightAngle 40
	)
)
(instance recordJacketsB of Feature
	(properties
		x 268
		y 15
		z 0
		heading 0
		nsLeft 222
		nsTop 11
		nsBottom 20
		nsRight 315
 		description "the record jackets"
		lookStr 		"Many famous record album jackets adorn the walls of
						the office of des Rever Records."
		sightAngle 40
	)
)


;************ Sounds ***************

(instance elevatorDing of Sound
	(properties
		number	sElevatorDing
	)
)
(instance elevatorDoor of Sound
	(properties
		number	sElevatorDoor
	)
)
(instance message1 of Sound
	(properties
		number	sMessage1
	)
)
(instance message2 of Sound
	(properties
		number	sMessage2
	)
)
(instance message3 of Sound
	(properties
		number	sMessage3
	)
)
(instance recordSwitch of Sound
	(properties
		number	sSwitch
	)
)
(instance needleDownSpin of Sound
	(properties
		number	sNeedleDownSpin
	)
)


;************ Talker ***************

(instance reverseTalker of Talker
	(properties
		name			"Reverse Biaz"
		view			vReverseBiazTalker
		loop			3
		nsLeft		35
		nsTop			15
		viewInPrint	TRUE
	)
	(method (init)
		(= bust 	reverseBust)
		(= eyes 	reverseEyes)
		(= mouth reverseMouth)
		(super init: &rest)
	)
)
(instance reverseBust of Prop
	(properties
		view		vReverseBiazTalker
		loop		1
		nsLeft	0
		nsTop		0
	)
)
(instance reverseEyes of Prop
	(properties
		view			vReverseBiazTalker
		loop			2
		nsLeft		22
		nsTop			31
		cycleSpeed  70
	)
)
(instance reverseMouth of Prop
	(properties
		view			vReverseBiazTalker
		loop			0
		nsLeft		15
		nsTop			36
		cycleSpeed 	5
	)
)
(instance fGoldRecords of Feature
	(properties
		x 134
		y 77
		z 37
		heading 0
		nsLeft 108
		nsTop 34
		nsBottom 47
		nsRight 160
 		description "the gold records"
		lookStr 		"These gold records are firmly attached
						to the wall."
		sightAngle 40
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbDo
				(TPrint "You are unable to remove this gold record
					from its plaque.")
			)
			(else
				(super doVerb: theVerb invItem &rest)
			)
		)
	)
)


;************ Procedure ***************

(procedure (TalkFromBooth)
	(= askedInToStudio TRUE)
	(TPrint "A speaker crackles in the background...")
 	(Say reverseTalker "\"Hello, out there!
		You must be Passionate Patti!
		Everything's all ready for your cut-in session.
		Just come on into the studio, I'm waiting for you.\""
		#dispose:
	)
)

