;**
;**	Logics for Room 660 -- des Rever recording studio
;**
;**	LSL5:  Passionate Patti Does A Little Undercover Work
;**				Copyright 1991
;**				by Al Lowe
;**
;**	Programmed by Steve Conrad
;**
;**	Last Update:  September 10, 1991
;**


(script#		660)

(procedure
	FindKey
	FindKeyboardKey
)

(public
	rm660		0
)

(local
	takes
	realTakes = 1
	didSession									;**	recorded all 3 takes
	didFF
	talkCounter
	champagneTalkCounter
	didZipper
	mouseDownKey
	keyDownKey
	noteCount
	whiteKeyNums = [ 0 2 4 5 7 9 11 12 14 16 17 19 21 23 24 26 28 29 ]
)

(define	OFFSET			18)
(define	IN_BETWEEN		3)
(define	OCTAVE_X			126)
(define	WHITE_WIDTH		18)
(define	BLACK_WIDTH		15)
(define	BLACK_TOP		164)
(define	BLACK_BOTTOM	180)

(define	pRecordingStudio				660)
(define	vRecordingStudio			660)
	(define	lcontrolRoomDoor	0)
(define	vReverseBiaz				662)
	(define	lTalkingToPatti	0)

(define	pSynthCloseUp					670)
(define	vSynthCloseUp				670)
(define	vReverseSmall				672)
(define	vSynth						673)
	(define	lBlackKeyDown		0)
	(define	lWhiteKeyLeft		1)
	(define	lWhiteKeyMid		2)
	(define	lWhiteKeyRight		3)

(define	vPattiInControlRoom		681)
	(define	lReverseBiaz		0)
	(define	lPatti				1)
(define	vPattiSexSolution			682)
	(define	lPattiJumpsRB		0)
	(define	lPattiPullsRBDown	1)
	(define	lPattiLeg			2)
	(define	lHeadphones			3)
	(define	lMicrophone			4)
	(define	lTapeReel			5)
	(define	lShoe					6)
	(define	lPanties				7)
	(define	lRBHandingTape		8)
(define vPattiChampagneSolution	683)
	(define	lPattiPouring		0)
	(define	lRBDrinking			1)
	(define	lRBSitting			2)
(define	vReverseBiazTalker		1672)


; Music defines

(define	mPopTune			 			660)
(define		sRewind				661)
(define		sStop					662)
(define	mGettingSome				663)
(define		sGlassesClink		664)
(define		sZing					665)


(instance rm660 of LLRoom
	(properties
		picture		pRecordingStudio
		south			640
	)

	(method (init)
		(ego
			init:,
			normalize:	vPatti
		)
		(HandsOn)
		(switch prevRoomNum
			(south
				(ego posn: 292 250)
				(if (Btst fBeenIn660)
					(curRoom setScript: sWalkIn)
				else
					(curRoom setScript: sEntersRoom)
				)
			)
			(else
				(ego
					posn:		160 160,
					edgeHit:	0
				)
			)
		)
		(LoadMany PICTURE
			pSynthCloseUp
			pBlackPic
		)
		(LoadMany VIEW
			vSynthCloseUp
			vReverseSmall
			vSynth
			vPattiInControlRoom
			vPattiSexSolution
			vPattiChampagneSolution
			vReverseBiazTalker
		)
		(LoadMany SOUND
			mPopTune
			sRewind
			sStop
			mGettingSome
			sGlassesClink
			sZing
		)
		(super init:)
		(door init:)
		(reverseBiaz init:)
		(synth
			init:,
			approachVerbs: verbDo
		)
		(musicStands init:)
		(recordingBooth init:)
		(overhangingMike init:)
		(drums init:)

;**	an ugly trick to test "after recording"
;**	(= didSession TRUE)
;**	(ego get: iChampagne)
;**	(self setScript: sEnterBooth)

		(curRoom addObstacle:
			((Polygon new:)
				type: PBarredAccess,
				init:	0 0 319 0 319 189 301 189 301 187 314 187 314 170 292 155
						301 144 290 143 269 144 244 138 237 137 227 117 233 104
						248 104 250 99 230 93 196 108 132 93 123 83 81 83 3 154 3 176
						30 176 52 166 86 175 190 187 205 187 247 172 274 187 274 189
						0 189,
				yourself
			)
			((Polygon new:)
				type: PBarredAccess,
				init:	77 107 103 106 112 112 154 112 154 119 114 122 113 138 65 139,
				yourself
			)
			((Polygon new:)
				type: PBarredAccess,
				init:	154 140 167 131 223 130 233 142 294 165 268 177 218 158
						206 161 191 159 180 164 165 156,
				yourself
			)
			((Polygon new:)
				type: PBarredAccess,
				init:	159 116 196 116 188 130 149 130,
				yourself
			)
		)
	)

	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbLook
				(if (== curPic pSynthCloseUp)
					(if didSession
						(TPrint "At last you are alone with Reverse Biaz.
							Now what are you going to do to obtain Desmond's
							\"hard evidence?\"")
					else
						(TPrint "You've wanted a chance to play a Farelight 2000
							ever since you first heard one. Now's your chance!")
					)
				else
					(TPrint "des Rever's recording studio contains a myriad of
						recording equipment.
						There are microphones, boom stands, road cases, and
						cables everywhere!")
				)
			)
			(verbDo
				(if (and (== curPic pSynthCloseUp)
							(not didSession))
					(TPrint "You're here as a keyboard player, Patti.
						So play!")
				else
					(super doVerb: theVerb invItem &rest)
				)
			)
			(else
				(super doVerb: theVerb invItem &rest)
			)
		)
	)

	(method (dispose)
		(if (not (keyboard isEmpty:))
			(keyboard dispose:)
		)
		(theMusic2
			fade:	0 15 12 TRUE
		)
		(super dispose: &rest)
	)
)


;************ Scripts **************

(instance sEntersRoom of Script
	(method (changeState newState)
		(switchto (= state newState)
			(
				(HandsOff)
				(Bset fBeenIn660)
				(= cycles 1)
			)
			(
				(ego
					setMotion: 	MoveTo 292 182 self
				)
			)
			(
				(= seconds 2)
			)
			(
				(TPrint "des Rever Records' recording studio reminds you
					of your last studio session,
					a big two-CD recording of background music for a
					computer game named \"Leisure Suit Larry 4.\"
					And of that big contract you signed with a
					mysterious someone known only as \"Julius,\"
					who never paid you one red cent for any of your work!")
				(TPrint "You grimace and silently hope this session goes better.")
				(= seconds 2)
			)
			(
				(Say reverseTalker "\"Why, hello there!
					Patti, I'm Reverse Biaz,
					Chief Engineer here at des Rever Records.
					I'm not sure exactly what happened to my regular synthist,
					but I sure appreciate you jumping in here to help us out
					like this.
					I'm sure you'll have no trouble with these tunes.\""
					#dispose:
					#caller: self
				)
			)
			(
				(HandsOn)
				(= seconds 3)
			)
			(
				(HandsOff)
				(Say reverseTalker "\"The synth is programmed, the charts
					are on the music stand, the levels are checked and I'm ready
					when you	are, baby!\""
					#dispose:
					#caller: self
				)
			)
			(
				(HandsOn)
				(ego
 					setLoop:		RELEASE,
				)
				(reverseBiaz
					setCel:			0,
					setCycle:		EndLoop self
				)
			)
			(
				(reverseBiaz setCel: 0)
				(ego setScript: sReverseReminds)
				(self dispose:)
			)
		)
	)
);sEntersRoom

(instance sWalkIn of Script
	(method (changeState newState)
		(switchto (= state newState)
			(
				(HandsOff)
				(= cycles 1)
			)
			(
				(ego
					setMotion: 	MoveTo 292 182 self
				)
			)
			(
				(HandsOn)
				(ego setScript: sReverseReminds)
				(self dispose:)
			)
		)
	)
);sWalkIn

(instance sReverseReminds of Script
	(method (changeState newState)
		(switchto (= state newState)
			(
				(= seconds 30)
			)
			(
				(-= state 2)
				(Say reverseTalker "\"Go ahead, Patti.
					The synthesizer is on that stand. It's all yours.\""
					#dispose:
					#caller: self
				)
			)
			(
				(self dispose:)
			)
		)
	)
);sReverseReminds

(instance sUsesSynth of Script
	(method (changeState newState)
		(switchto (= state newState)
			(
				(HandsOff)
				(ego setHeading: 90)
				(= seconds 2)
			)
			(
				(cast eachElementDo: #hide:)
				(curRoom drawPic: pSynthCloseUp)
				(theIconBar disable:	walkIcon)
				(reverseBiaz
					view:			vReverseSmall,
					setLoop:		0,
					setCel:		0,
					x:				203,
					y:				72,
					setPri:		2,
					show:
				)

				(reelOne init:)
				(reelTwo	init:)
				(reelThree init:)
				(reelFour init:)
				(vuMeter init:)
				(keyboard init:)
				(music init:)
				(synthPanel init:)
				(panelExtender init:)
				(controlPanel init:)
				(controlPanel2 init:)

				(synth dispose:)
				(musicStands dispose:)
				(recordingBooth dispose:)
				(overhangingMike dispose:)
				(drums dispose:)
				(= cycles 2)
			)
			(
				(ego setScript: sSession)
				(self dispose:)
			)
		)
	)
);sUsesSynth

(instance sReturnFromSynth of Script
	(method (changeState newState)
		(switchto (= state newState)
			(
				(curRoom drawPic: (curRoom picture?))
				(cast eachElementDo: #show:)
				(reverseBiaz hide:)
				(vuMeter hide:)
				(reelOne hide:)
				(reelTwo hide:)
				(reelThree hide:)
				(reelFour hide:)

				(keyboard dispose:)
				(music dispose:)
				(synthPanel dispose:)
				(panelExtender hide:)

				(door show:)
				(ego
					normalize: 	vPatti
				)
				(curRoom setScript: sEnterBooth)
				(self dispose:)
			)
		)
	)
);sReturnFromSynth

(instance sSession of Script
	(method (changeState newState &tmp [str 20])
		(switchto (= state newState)
			(
				(HandsOff)
				(SetFFRoom SAMEROOM reverseBiaz)
				(= seconds 2)
			)
			(
;**					(if takes
;**						(Printf "NoteCount = %d" noteCount)
;**					)
				(switch takes
					(0
						(Say reverseTalker "\"Okay, Patti.
							Just read the chart there on the stand.
							We'll take it from a few bars before your entrance.
							Listen for the clicks, then cut in your solo.\""
							#dispose:
							#caller: self
						)
					)
					(1
						(if noteCount
							(= noteCount 0)
							(Say reverseTalker "\"That's not quite what the boss is
								looking for, Patti, baby.
								I'm gonna roll again from the top.
								Let's have a little more feeling, okay?\""
								#dispose:
								#caller: self
							)
						else
							(-- takes)
							(= noteCount 0)
							(Say reverseTalker "\"Well, Patti, I suppose you
								just wanted to hear it that time, eh?
								Well, now you've heard it,
								so this time really let me have your best shot!\""
								#dispose:
								#caller: self
							)
						)
					)
					(else
						(if ( < noteCount 10)
							(-- takes)
							(= noteCount 0)
							(Say reverseTalker "\"That's a little TOO sparse,
								Patti-cakes, babydoll.
								Let's take it at the upper-left corner, okay?
								And this time, how about a little more intensity,
								okay, kid?\""
								#dispose:
								#caller: self
							)
						else
							(Say reverseTalker "\"Well, that was fairly outside!
								Let's give it one more shot, babe. Here we go!\""
								#dispose:
								#caller: self
							)
						)
					)
				)
			)
			(
				;Print keyboard directions the first time.
				(if (and (== realTakes 1)
							(not (HaveMouse)))
					(TPrint "Play the white synthesizer keys by typing the\n
						keys on the A S D F row.
						Play the black synthesizer keys by typing the
						corresponding keys on the W E T Y U row.
						Holding down the Shift key plays higher."
						BIG
						#title		{A Tip from AL}
					)
				)
				(if (> realTakes 1)
					(rewind play: self)
					(reelOne
						cycleSpeed:	5,
						setCycle: 	Reverse
					)
					(reelTwo
						cycleSpeed:	5,
						setCycle: 	Forward
					)
					(reelThree
						cycleSpeed:	5,
						setCycle: 	Reverse
					)
					(reelFour
						cycleSpeed:	5,
						setCycle: 	Forward
					)
				else
					(self cue:)
				)
			)
			(
				(if (> realTakes 1)			;Rewind music, so we can play again
					(musicStop play: self)
					(reelOne
						cycleSpeed:	10
					)
					(reelTwo
						cycleSpeed:	10
					)
					(reelThree
						cycleSpeed:	10
					)
					(reelFour
						cycleSpeed:	10
					)
				else
					(self cue:)
				)
			)
			(
				(if (> realTakes 1)
					(reelOne setCycle: 0)
					(reelTwo	setCycle: 0)
					(reelThree setCycle: 0)
					(reelFour setCycle: 0)
					(= seconds 2)
				else
					(self cue:)
				)
			)
			(
				(Format @str "\"Take %d!\"" realTakes)
				(Say reverseTalker @str #dispose: #caller: self)
			)
			(
				(reverseBiaz
					setLoop:		2,
					setCel:		0,
					setCycle:	EndLoop self
				)
			)
			(
				(reverseBiaz
					setCycle:	BegLoop self
				)
			)
			(
				(reverseBiaz
					setLoop:		0,
					setCycle:	EndLoop self
				)
			)
			(
				(reverseBiaz
					setCycle:	BegLoop self
				)
			)
			(
				(reverseBiaz
					setLoop: 	1,
					setCel:		0,
					setCycle:	EndLoop self
				)
			)
			(
				(reverseBiaz
					setCycle:	BegLoop self
				)
			)
			(
				(reverseBiaz
					setLoop:		0,
					setCel: 		0
				)
				(= cycles 1)
			)
			(
				(reelOne
					cycleSpeed:	17,
					setCycle: 	Forward
				)
				(reelTwo
					cycleSpeed:	15,
					setCycle: 	Reverse
				)
				(reelThree
					cycleSpeed:	17,
					setCycle: 	Forward
				)
				(reelFour
					cycleSpeed:	15,
					setCycle: 	Reverse
				)
				(vuMeter
					view:			670,
					setLoop:		1,
					setCel:		0,
					setCycle: Forward
				)
				(if (<= takes 1)
					(mouseDownHandler addToFront: keyboard)
					(keyDownHandler addToFront: keyboard)
					(DoSound Suspend TRUE)
					(theMusic
						number:		mPopTune,
						flags:		TRUE,
						setLoop:		1,
						play:			,
						send:			9 mMUTE TRUE 		;Mute channel 9
					)
					(DoSound Suspend FALSE)
				else
					(DoSound Suspend TRUE)
					(theMusic
						number:		mPopTune,
						flags:		TRUE,
						setLoop:		1,
						play:			sAutoKeys,
						send:			8 mMUTE TRUE,
						send:			9 mMUTE FALSE 		;without channel 9 muted
					)
					(DoSound Suspend FALSE)
				)
				(reverseBiaz setScript: sMonitorRecording)
				(HandsOn)
				(User canControl: FALSE)
				(theIconBar disable:
					walkIcon
					talkIcon
					zipperIcon
					useIcon
					inventoryIcon
				)
				(if (> takes 1)
					(curRoom setScript: sAutoKeys)
					(self dispose:)
				)
				(self cue:)
			)
			(
				(Delay 50 REALSECONDS self)
			)
			(
				(HandsOff)
				(if mouseDownKey
					(mouseDownKey release:)
				)
				(= mouseDownKey 0)
				(= keyDownKey 0)
				(mouseDownHandler delete: keyboard)
				(keyDownHandler delete: keyboard)
				(= seconds 3)
			)
			(
				(HandsOn)
				(theIconBar disable:	walkIcon)
				(sMonitorRecording dispose:)
			)
			(
				(self dispose:)
			)
		)
	)
);sSession

(instance sMonitorRecording of Script
	(method (dispose)
		(reelOne setCycle: 0)
		(reelTwo	setCycle: 0)
		(reelThree setCycle: 0)
		(reelFour setCycle: 0)
		(vuMeter
			setCycle: 	0,
			setLoop:		0
		)
		(theMusic stop:)
		(reverseBiaz
			setLoop:		0,
			setCycle:	BegLoop self
		)
		(if (> takes 1)
			(curRoom setScript: sEndSession)
		else
			(++ takes)
			(++ realTakes)
			(ego setScript: sSession)
		)
		(super dispose:)
	)
	(method (changeState newState)
		;
		; We loop until the player uses the hand on the keyboard
		;
		(switchto (= state newState)
			(
				(= seconds (Random 3 6))
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

(instance sAutoKeys of Script
	;
	; register - ID of last key hit
	;
	(method (changeState newState &tmp theKeyNum theCue)
		(switchto (= state newState)
			(
				(User canInput: FALSE)
				(theIconBar
					disable:
						walkIcon
						lookIcon
						talkIcon
						zipperIcon
						useIcon
						inventoryIcon
				)
				(theIconBar curIcon: (theIconBar at: doIcon))
				;We stick in this state until cued by music
			)
			(
				(= theCue (theMusic prevSignal?))
				(if (== theCue -1)
					; the music is done so release & get out of here
					(register release:)
					(= cycles 1)
				else
					(= theKeyNum (- 30 (- theCue 60)))

					; If we have a key being pressed already, release it
					(if register
						(register release:)
					)

					; set register to new key pressed
					(= register (keyboard at: theKeyNum))
					(theGame
						setCursor: 	theCursor TRUE
										(if (register white?)
									  		(+ (register x?) 5)
										else
											(register x?)
										)
										(if (register white?)
									  		(- (register y?) 10)
										else
											(- (register y?) 12)
										)
					)
					(register depress: FALSE)
					(self changeState: 0)
				)
			)
			(
				(sMonitorRecording dispose:)
				(self dispose:)
			)
		)
	)
);sAutoKeys

(instance sEndSession of Script
	(method (changeState newState)
		(switchto (= state newState)
			(
				(SetFFRoom NULL)
				(= seconds 3)
			)
			(
				(= didSession TRUE)
				(if didFF
					(Say reverseTalker
						"\"Why don't you come into the control room now
						and listen to the playback with me?\""
						#dispose:
						#caller: self
					)
				else
					(Points ptsDidSession pfDidSession)
					(Say reverseTalker "\"Great take, Patti!
						Why don't you come into the control room now
						and listen to the playback with me?\""
						#dispose:
						#caller: self
					)
				)
			)
			(
				(curRoom setScript: sReturnFromSynth)
				(self dispose:)
			)
		)
	)
);sEndSession

(instance sEnterBooth of Script
	(method (changeState newState)
		(switchto (= state newState)
			(
				(HandsOff)
				(User canControl:	FALSE)
				(User canInput: FALSE)
				(ego
					setPri:		4,
					setMotion:	PolyPath 229 101 self
				)
			)
			(
				(theMusic
					number:		sDoorOpen,
					setVol:		127,
					setLoop:		1,
					play
				)
				(door
					ignoreActors:	TRUE,
					setPri:			3,
					setCycle:		EndLoop self
				)
			)
			(
				(ego
					setMotion:	MoveTo 279 74 self		;251 96
				)
			)
			(
				(door dispose:)
				(curRoom drawPic: 670)

				(reverseBiaz show:)
				(ego
					view:			vPattiInControlRoom,
					setLoop:		lPatti,
					setCel:		0,
					x:				241,
					y:				71,
					show:
				)
				(reelOne show:)
				(reelTwo	show:)
				(reelThree show:)
				(reelFour show:)
				(vuMeter show:)

				(keyboard init:)
				(music init:)
				(synthPanel init:)
				(panelExtender show:)
				(theIconBar disable:	walkIcon)
				(= seconds 3)
			)
			(
				(Say reverseTalker "\"Here you go, babe. Check this out!\""
					#dispose:
					#caller: self
				)
			)
			(
				(reverseBiaz
					setCycle:	EndLoop self
				)
			)
			(
				(reverseBiaz
					setCycle:	BegLoop self
				)
			)
			(
				(reverseBiaz
					setLoop: 	1,
					setCel:		0,
					setCycle:	EndLoop self
				)
			)
			(
				(reverseBiaz
					setCycle:	BegLoop self
				)
			)
			(
				(reverseBiaz
					setLoop:		0
				)
				(= cycles 1)
			)
			(
				(reverseBiaz
					view:			vPattiInControlRoom,
					setLoop:		lReverseBiaz,
					setCel:		0,
					x:				203,
					y:				71
				)
				(= cycles 1)
			)
			(
				(reelOne setCycle: Forward)
				(reelTwo	setCycle: Reverse)
				(self cue:)
			)
			(
				(DoSound Suspend TRUE)
				(theMusic
					number:		mPopTune,
					flags:		TRUE,
					setLoop:		1,
					play:			sStopMusic,
					send:			8 mMUTE TRUE
				)
				(DoSound Suspend FALSE)
				(reverseBiaz
					setScript:	sStopMusic
				)
				(self cue:)
			)
			(
				(= seconds 10)
			)
			(
				(theMusic
					fade:	80 15 12 FALSE
				)
				(Say reverseTalker "\"Nice playing, Patti,\" he smiles,
					\"I bet you have wonderful technique!\""
					#dispose:
				)
				(HandsOn)
				(User canControl:	FALSE)
				(User canInput:	TRUE)
				(theIconBar disable:	walkIcon)
				(Delay 300 SECONDS self)
			)

			;**	Now the user has 5 minutes to finish the booth scene

			(
				(HandsOff)
				(Say reverseTalker "\"Well, Patti,\" Reverse says,
					\"I've got another session starting soon.
					You're going to have to go now; I've got to get to work!\""
					#dispose:
					#caller: self
				)
			)
			(
				(Cls)
				(keyboard dispose:)
				(curRoom drawPic: pBlackPic IRISIN)
				(= ticks 120)
			)
			(
				(TPrint "After failing in your attempt to get evidence from
					Reverse Biaz, you return to your limo.")
				(= ticks 120)
			)
			(
				(Bset fBeenInBaltimore)
				(curRoom newRoom: 200 IRISOUT)	;go back to limo
				(self dispose:)
			)
		)
	)
);sEnterBooth

(instance sStopMusic of Script
	(method (changeState newState)
		(switchto (= state newState)
			(
				;We stay in this state until cued by theMusic
			)
			(
				(if (== (theMusic prevSignal?) -1)
					; the music is done so stop the reels & get out of here
					(reelOne setCycle: 0)
					(reelTwo	setCycle: 0)
					(= cycles 1)
				else
					(self changeState: 0)
				)
			)
			(
				(self dispose:)
			)
		)
	)
);sStopMusic

(instance sDrunkReverse of Script
	(method (changeState newState)
		(switchto (= state newState)
			(
				(HandsOff)
				(theTimer dispose:, delete:)
				(sEnterBooth dispose:)
				(= ticks 123)
			)
			(
				(Say ego "\"What would you say if I proposed a toast,
					Mr. Biaz?\""
				)
				(= ticks 60)
			)
			(
				(Say reverseTalker "\"Why, Patti!\" he replies with a grin,
					\"I'm always interested in sharing a glass of champagne
					with a beautiful woman!\""
					#dispose:
					#caller: self
				)
			)
			(
				(= ticks 60)
			)
			(
				(Say reverseTalker "\"To you, Patti!\" he proposes a toast.
					\"And to many more successful sessions together!\""
					#dispose:
					#caller: self
				)
			)
			(
				(= ticks 60)
			)
			(
				(ego hide:)
				(reverseBiaz
					view:			vPattiChampagneSolution,
					setLoop:		lPattiPouring,
					setCel:		0,
					x:				231,
					y:				71,
					cycleSpeed:	13,
					setCycle:	CycleTo 1 FORWARD self
				)
			)
			(
				(= ticks 60)					;**	pour his
			)
			(
				(reverseBiaz
					setCycle:	CycleTo 3 FORWARD self
				)
			)
			(
				(= ticks 60)					;**	pour hers
			)
			(
				(reverseBiaz
					setCycle:	EndLoop self
				)
			)
			(
				(glassesClink play:)			;**	glasses touch
				(= ticks 123)
			)
			(
				(reverseBiaz
					setLoop:		lRBDrinking,
					setCel:		0,
					setCycle:	EndLoop self
				)
			)
			(
				(= ticks 123)
			)
			(
				(Say reverseTalker "Ahh! A pleasant vintage,\" he sighs,
					\"but it's a little warm, isn't it?\""
					#dispose:
					#caller: self
				)
			)
			(
				(= ticks 60)
			)
			(
				(Say ego "\"Well, possibly so,\" you say in its defense,
					\"but then, you don't know where it's been!\"")
				(= ticks 123)
			)
			(
				(Say ego "\"Here, Mr. Biaz,\" you say, \"have a little more.\"")
				(reverseBiaz
					setLoop:		lPattiPouring,
					setCel:		2,
					setCycle:	CycleTo 3 FORWARD self
				)
			)
			(
				(= ticks 60)					;**	pour his
			)
			(
				(reverseBiaz
					setLoop:		lRBDrinking,
					setCel:		0,
					setCycle:	EndLoop self
				)
			)
			(
				(= ticks 60)					;**	pour his
			)

			(										;**	0
				(switch champagneTalkCounter
					(0
						(Say ego "\"One more won't hurt, Mr. Biaz,\" you say,
							using a technique on him that's worked more than
							a few times on you.")
					)
					(1
						(Say ego "\"No, but I'd love to hear!\"")
					)
					(2
						(Say ego "\"I've shown you my work;
							will you show me yours?\"")
					)
					(3
						(Say ego "\"Oh, really? How could that be, Reverse?\"")
					)
				)
				(= ticks 90)
			)
			(										;**	1
				(reverseBiaz
					setLoop:		lPattiPouring,
					setCel:		2,
					setCycle:	CycleTo 3 FORWARD self
				)
			)
			(										;**	2
				(= ticks 60)					;**	pour his
			)
			(										;**	3
				(reverseBiaz
					setLoop:		lRBDrinking,
					setCel:		0,
					setCycle:	EndLoop self
				)
			)
			(										;**	4
				(= ticks 60)
			)
			(										;**	5
				(switch champagneTalkCounter
					(0
						(Say reverseTalker "\"Please, call me Reverse!\"
							slurs Reverse, \"So, Patticakes,
							have I ever told you what an important guy I am?\""
							#dispose:
							#caller: self
						)
					)
					(1
						(Say reverseTalker "\"Sshaay, thissh issh tasshty!\""
							#dispose:
							#caller: self
						)
					)
					(2
						(Say reverseTalker "\"Why, ssshweetie, my ssshtuff's on
							more recordsssh than anybody'ssh!\""
							#dispose:
							#caller: self
						)
					)
					(3
						(Say reverseTalker "\"Eassshy, ssshugar!\" slurs Reverse.
							\"Lemme find you a sshample!\""
							#dispose:
							#caller: self
						)
					)
				)
			)
			(										;**	6
				(= ticks 90)
				(if ( < (++ champagneTalkCounter) 4)
					(-= state 7)
				)
			)

			(
				(ego show:)
				(zing play:)
				(reverseBiaz
					view:			vPattiChampagneSolution,
					setLoop:		lRBSitting,
					setCel:		0,
					x:				241,
					y:				71,
					setCycle:	EndLoop self
				)
			)
			(
				(= seconds 3)
			)
			(
				(giveTape init:)
				(Points ptsGotTapeRB pfGotTapeRB)
				(ego get: iCassetteTape)
				(= seconds 3)
			)
			(
				(Points ptsChampagne pfChampagne)
				(Say reverseTalker "\"Wait'll ya hear thissh,\" slobbers Reverse.
					\"Every ssshong hasssh ssshomething ssshublimal!\""
					#dispose:
					#caller: self
				)
			)
			(
				(= seconds 3)
			)
			(
				(TPrint "Good job, Patti!
					The boys back at the F.B.I. Labs will have a wonderful time
					extracting those backwards subliminal messages from
					Biaz's tape!")
				(= cycles 3)
			)
			(
				(Bset fChampagneSolution)
				(curRoom newRoom: 690)
			)
		)
	)
);sDrunkReverse

(instance sTalkScript of Script
	(method (changeState newState)
		(switchto (= state newState)
			(
				(HandsOff)
				(switch talkCounter
					(0
						(Say ego "\"So, Mr. Biaz, I understand you're quite a
							successful recording engineer,\" you say.")
					)
					(1
						(Say ego "\"I just love studio work,\" you say with a
							knowing smile.")
					)
					(2
						(Say ego "\"Why, thank you, Mr. Biaz,\" you say,
							fluttering your lashes,
							\"I think I was especially inspired today--by you!\"")
					)
					(3
						(Say ego "\"Oh, that would be wonderful, Mr. Biaz.\"")
					)
					(4
						(Say ego "\"And please, call me Patti!\" you say,
							ignoring the fact that he has been calling you Patti.")
					)
					(5
						(Say ego "\"I'll only be in town a short time, Reverse,\"
							you say, your voice filled with warmth.
							\"I'd certainly like to have a little something to
							remember you by.\"")
					)
					(6
						(theTimer dispose:, delete:)
						(sEnterBooth dispose:)
						(Say ego "\"Is there a chance I could get some samples
							of your work?\" you ask, with a knowing glance.
							\"In fact, I'd love to `add your work' to my
							collection.\"")
					)
				)
				(= ticks 60)
			)
			(
				(switch talkCounter
					(0
						(Say reverseTalker "\"Yeah, I can't complain,\" he replies.
							\"I have several `clients' who keep me quite busy.\""
							#dispose:
							#caller: self
						)
					)
					(1
						(Say reverseTalker "\"Oh?\" answers Reverse.
							\"Well, you did do a nice job on this cut.\""
							#dispose:
							#caller: self
						)
					)
					(2
						(Say reverseTalker "Warming noticeably, he continues,
							\"Well, Patti, I do know a lot of very important people
							in the recording business.
							I suppose I could drop your name in a few right places.\""
							#dispose:
							#caller: self
						)
					)
					(3
						(Say reverseTalker "\"Please... call me Reverse!\""
							#dispose:
							#caller: self
						)
					)
					(4
						(= cycles 2)
					)
					(5
						(Say reverseTalker "\"Why, I think that could be arranged,\"
							he says.
							\"What is it you're thinking of?\""
							#dispose:
							#caller: self
						)
					)
					(6
						(Say reverseTalker "\"I'd be glad to give you some samples
							of my work, Patti,\" he says with a wink,
							\"if I can have a sample of YOURS!\""
							#dispose:
							#caller: self
						)
					)
				)
			)
			(
				(++ talkCounter)
				(RestoreIB)
				(if (> talkCounter 6)
					(self setScript: sDoSex)
				else
					(self dispose:)
				)
			)
		)
	)
);sTalkScript

(instance sDoSex of Script
	(method (changeState newState)
		(switchto (= state newState)
			(
				(HandsOff)
				(TPrint "More than willing to \"serve your country\"
					(especially with such a hunk),
					you throw yourself enthusiastically into your mission.")
				(theMusic
					fade:	0 15 12 TRUE
				)
				(= ticks 60)
			)
			(
				(theMusic2
					number:	mGettingSome,
					setLoop:	-1,
					flags:	TRUE,
					play:
				)
				(ego hide:)
				(reverseBiaz					;And now for the sex animation
					view:			vPattiSexSolution,
					setLoop:		lPattiJumpsRB,
					setCel:		0,
					x:				222,
					y:				71,
					cycleSpeed:	10,
					setCycle:	EndLoop self
				)
			)
			(
				(= ticks 180)
			)
			(
				(reverseBiaz
					setLoop:		lPattiPullsRBDown,
					setCel:		0,
					setCycle:	EndLoop self
				)
			)
			(
				(= ticks 240)
			)
			(
				(reverseBiaz
					setLoop:		lPattiLeg,
					setCel:		0,
					x:				235,
					y:				71,
					cycleSpeed:	10,
					setCycle:	EndLoop self
				)
			)
			(
				(= ticks 120)
			)
			(
				(reverseBiaz
					setLoop:		lHeadphones,
					setCel:		0,
					x:				263,
					y:				71,
					cycleSpeed:	10,
					setCycle:	EndLoop self
				)
			)
			(
				(= ticks 120)
			)
			(
				(reverseBiaz
					setLoop:		lPattiLeg,
					setCel:		0,
					x:				235,
					y:				71,
					cycleSpeed:	10,
					setCycle:	CycleTo 5 FORWARD self
				)
			)
			(
				(reelThree setCycle: Forward)
				(reelFour setCycle: Reverse)
				(vuMeter
					view:			670,
					setLoop:		1,
					setCel:		0,
					setCycle: Forward
				)
				(= ticks 180)
			)
			(
				(reverseBiaz
					setLoop:		lPattiLeg,
					x:				235,
					y:				71,
					cycleSpeed:	10,
					setCycle:	EndLoop self
				)
			)
			(
				(= ticks 120)
			)
			(
				(reverseBiaz
					setLoop:		lMicrophone,
					setCel:		0,
					x:				255,
					y:				73,
					setCycle:	EndLoop self
				)
			)
			(
				(reverseBiaz
					setLoop:		lTapeReel,
					setCel:		0,
					x:				213,
					y:				72,
					setCycle:	EndLoop self
				)
			)
			(
				(= ticks 120)
			)
			(
				(reverseBiaz
					setLoop:		lPattiLeg,
					setCel:		0,
					x:				235,
					y:				71,
					cycleSpeed:	10,
					setCycle:	EndLoop self
				)
			)
			(
				(= ticks 120)
			)
			(
				(reverseBiaz
					setLoop:		lShoe,
					setCel:		0,
					x:				230,
					y:				72,
					setCycle:	EndLoop self
				)
			)
			(
				(reverseBiaz
					setLoop:		lTapeReel,
					setCel:		0,
					x:				213,
					y:				72,
					setCycle:	EndLoop self
				)
			)
			(
				(= ticks 240)
			)
			(
				(reverseBiaz
					setLoop:		lPanties,
					setCel:		0,
					x:				213,
					y:				73,
					setCycle:	EndLoop self
				)
			)
			(
				(= ticks 180)
			)
			(
				(if didZipper
					(Points ptsZipperSex pfZipperSex)
				else
					(Points ptsTalkSex pfTalkSex)
				)
				(keyboard dispose:)
				(curRoom drawPic: pBlackPic IRISIN)
				(= ticks 120)
			)
			(
				(TPrint "Eventually, through one sacrifice after another,
					you eventually obtain a small cassette tape from Reverse Biaz
					containing some of his best work.
					It is exactly the evidence you've been seeking.
					The boys back at the F.B.I. lab will have a heyday
					playing with this one."
				)
				(Points ptsGotTapeRB pfGotTapeRB)
				(ego get: iCassetteTape)
				(self cue:)
			)
			(
				(= ticks 120)
			)
			(
				(TPrint "Unbeknownst to you,
					when you bumped your leg on the recording console
					in the excitement of the moment,
					you made a complete soundtrack of your love-making session.")
				(TPrint "Reverse later added a drum machine and synthesized bass
					line, and created another hit record.")
				(TPrint "Ironically, you eventually got that Number One record
					you've always wanted!
					But, you knew nothing about it.
					At least once in your life, Patti,
					you hit the top of the charts!")
				(curRoom newRoom: 690)
			)
		)
	)
);sDoSex


;************* Objects ***************

(instance door of Prop
	(properties
		view			vRecordingStudio
		loop			lcontrolRoomDoor
		cel			0
		x				249
		y				96
		description "the door"
		lookStr 		"This door leads to the control booth--and Reverse Biaz."
		cycleSpeed 10
		signal (| stopUpdOn ignrAct)
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbDo
				(TPrint "You would like to see the control room,
					and talk with Mr. Biaz,
					but first you want to complete this recording session.")
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
		loop			lTalkingToPatti
		cel			0
		x				286
		y				64
		description "Reverse Biaz"
		lookStr 		"Reverse Biaz is the man the F.B.I. linked to
						backwards subliminal message recording at this studio.
						But he does looks cute."
		cycleSpeed 10
		priority 5
		signal (| stopUpdOn ignrAct fixPriOn)
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbDo
				(if didSession
					(Say reverseTalker "\"Oooh! Now that feels nice, Patti.\""
						#dispose:
					)
				else
					(TPrint "You are unable to touch Reverse Biaz through
						that double-paned soundproof glass.")
				)
			)
			(verbTalk
				(if didSession
					(curRoom setScript: sTalkScript)
				else
					(Say reverseTalker "\"Patti, I can see your lips moving,
						but I don't have an open mike out there.
						Just go ahead and play, and we can talk later, okay?\""
						#dispose:
					)
				)
			)
			(verbUse
				(switch invItem
					(iChampagne
						(if didSession
							(ego put: iChampagne)
							(curRoom setScript: sDrunkReverse)
						else
							(TPrint "You don't pour THROUGH glass, Patti,
								you pour INTO glass!"
							)
						)
					)
					(else
						(Say reverseTalker
							"\"I don't know why you're offering me that, Patti.
							Did you come to play or come to bargain?\""
							#dispose:
						)
					)
				)
			)
			(verbZipper
				(if didSession
					(= didZipper TRUE)
					(curRoom setScript: sDoSex)
				else
					(TPrint "You have no way to do that through that window!")
				)
			)
			(else
				(super doVerb: theVerb invItem &rest)
			)
		)
	)
	(method (doit)
		(if (and cycler (not (IsObject cycler?)))
			(= cycler 0)	
		)
		(super doit:)
	)
	(method (cue)
		(= takes 2)
		(= didFF TRUE)
		(sSession dispose:)
		(sMonitorRecording dispose:)
	)
)

(instance giveTape of View
	(properties
		view			vPattiChampagneSolution
		loop			3
		x				241
		y				73
		priority		4
		signal		fixPriOn
	)
)
(instance panelExtender of View
	(properties
		view			vSynthCloseUp
		loop			6
		x				183
		y				83
		priority		3
		signal		(| stopUpdOn fixPriOn)
 		description "the control room mixer"
		sightAngle 40
	)
	(method (doVerb theVerb invItem)
		(controlPanel doVerb theVerb invItem)
	)
)
(instance reelOne of Prop
	(properties
		x 214
		y 26
		view vSynthCloseUp
		loop 2
		cel 0
		detailLevel	2
 		description "the tape reel"
		sightAngle 40
		cycleSpeed 15
		signal stopUpdOn
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbLook
				(TPrint "You assumed all modern studios recorded directly to
					hard disks!")
			)
			(verbDo
				(if didSession
					(TPrint "Reverse would prefer you to leave his reels alone!")
				else
					(TPrint "Those tapes are inaccessible to you--for now!")
				)
			)
			(else
				(super doVerb: theVerb invItem &rest)
			)
		)
	)
)
(instance reelTwo of Prop
	(properties
		x 236
		y 26
		view vSynthCloseUp
		loop 3
		cel 0
		detailLevel	2
 		description "the tape reel"
		sightAngle 40
		cycleSpeed 20
		signal stopUpdOn
	)
	(method (doVerb theVerb invItem)
		(reelOne doVerb theVerb invItem)
	)
)
(instance reelThree of Prop
	(properties
		x 261;263
		y 27
		view vSynthCloseUp
		loop 4
		cel 0
		detailLevel	3
 		description "the tape reel"
		sightAngle 40
		cycleSpeed 15
		signal stopUpdOn
	)
	(method (doVerb theVerb invItem)
		(reelOne doVerb theVerb invItem)
	)
)
(instance reelFour of Prop
	(properties
		x 283;285
		y 27
		view vSynthCloseUp
		loop 5
		cel 0
		detailLevel	3
 		description "the tape reel"
		sightAngle 40
		cycleSpeed 20
		signal stopUpdOn
	)
	(method (doVerb theVerb invItem)
		(reelOne doVerb theVerb invItem)
	)
)
(instance vuMeter of Prop
	(properties
		x 169
		y 29
		view vSynthCloseUp
		loop 0
		detailLevel	4
 		description "the VU meter"
		lookStr 		"This meter measures dynamic levels in decibels,
						each of which is about 1/10th of a bel."
		sightAngle 40
		cycleSpeed 10
		signal stopUpdOn
	)
)
(class synthKey kindof Prop
	(properties
		view			673
		frequency	0
		white			FALSE
		detailLevel	1
		description	"the key"
	)
	(methods
		depress		; this shows the key pressed down & plays a note
		release		; this shows the key up again & stops a note
	)
	(method (depress playNote)
		(if keyDownKey
			(keyDownKey release:)
			(= keyDownKey 0)
		)
		(self	setCel: 1)
		(if playNote
			(++ noteCount)
			(theMusic send: 8 mNOTEON frequency 100)
		)
	)
	(method (release)
		(self	setCel: 0)
		(theMusic send: 8 mNOTEOFF frequency 0)
	)
	(method (doit &tmp evt newKey)
		(super doit:)

		;If mouse is on a key other than the one on which the mouse was
		;originally pressed, release the original key.  Also, if the mouse
		;is down on the current key, play this key.
		(= evt (User curEvent))
		(if (and (== mouseDownKey self)
					(!= self (= newKey (FindKey evt)))
				)
			(self release:)
			(= mouseDownKey newKey)
			(if newKey
				(newKey depress:)
			)
		)
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbLook
 				(TPrint "Wow! It's a Farelight 2000, the highest-tech high-tech
					synthesizer keyboard.
					You've always wanted one of these for its wonderful
					Boesendorfer patch!")
				(TPrint "Gurn Blanston plays one with \"Rex Famine and the
					Limestone Bug Legs.\"")
			)
			(verbDo
				(if didSession
					(TPrint "You can't play the synthesizer keyboard from inside
						the control room!")
				else
					(super doVerb: theVerb invItem &rest)
				)
			)
			(else
				(super doVerb: theVerb invItem &rest)
			)
		)
	)
	(method (cue)				;used for keyboard releases
		(self release:)
	)
)

(instance keyboard of EventHandler
	(method (init &tmp keyNum octave theKey keyIsWhite
						i theX theY theView theLoop theFreq)
		(super init: &rest)

		(for 	((= i 0))
				(< i 31)
				((++ i))
			(= keyNum i)

			; First find out if we're black or white
			(= octave (/ keyNum 12))
			(-= keyNum (* octave 12))		; yields 0-11
			(= keyIsWhite (mod keyNum 2))	; determine if black or white
			(if (< keyNum 5)
				(-- keyIsWhite)
			)

			; Create a new key & find out its view, loop, and cel
			(= theKey (synthKey new:))
			(= theView (theKey view?))
			(= theLoop
				(if keyIsWhite
					(switch keyNum
						(0		lWhiteKeyLeft)
						(2 	lWhiteKeyMid)
						(4 	lWhiteKeyRight)
						(5 	lWhiteKeyLeft)
						(7 	lWhiteKeyMid)
						(9 	lWhiteKeyMid)
						(11 	lWhiteKeyRight)
					)
				else
					0
				)
			)

			; Calculate the X coordinate, depending on black/white & which
			(if keyIsWhite
				(if (> keyNum 4)
					(++ keyNum)
				)
				(= theX (* (/ keyNum 2) WHITE_WIDTH))
				(+= theX (* octave OCTAVE_X))
				(= theY 189)
			else
				(= theX (* (/ keyNum 2) (+ BLACK_WIDTH IN_BETWEEN)))
				(= theX (+ theX OFFSET (* octave OCTAVE_X)))
				(= theY 180)
			)

			; Calculate the frequency this key will play
			(= theFreq (+ i 60))

			; Stuff values into our new key's properties
			(theKey
				setLoop:			theLoop,
				x:					theX,
				y:					theY,
				frequency:		theFreq,
				white:			keyIsWhite,
				ignoreActors:	,
				setPri:			(if keyIsWhite RELEASE else 15),
				stopUpd:			TRUE,
				init:
			)

			; Finally, add our new key to the lists
			(self addToFront: theKey)
		)
	)
	(method (dispose)
		(mouseDownHandler delete: self)
		(keyDownHandler delete: self)
		(super dispose:)
	)
	(method (handleEvent event &tmp evt theKey)
		(cond
			(mouseDownKey
				(if (== (event	type?) mouseUp)
					(mouseDownKey release:)
					(= mouseDownKey FALSE)
				)
				(event claimed: TRUE)
			)
			((== (event type?) mouseDown)
				(if (and (= theKey (FindKey event))
							(!= ((theIconBar curIcon?) cursor?) EYE_CURSOR)
					)
					(theKey depress: TRUE)
					(= mouseDownKey theKey)
					(event claimed: TRUE)
				else
					(super handleEvent: event &rest)
				)
			)
			((== (event type?) keyDown)
				(= theKey (FindKeyboardKey event))
				(if (!= theKey (keyboard at: -1))
					(theKey depress: TRUE)
					(= keyDownKey theKey)
					((Timer new:) setCycle: theKey 5)
				else
					(super handleEvent: event &rest)
				)
				(event claimed: TRUE)
			)
		)
	)
)

(procedure (FindKeyboardKey event)
	(return
		(keyboard at:
			(switch (event message?)
				(`a 	30) 	;Low C
				(`w	29)
				(`s	28)
				(`e	27)
				(`d	26)
				(`f	25)
				(`t	24)
				(`g	23)
				(`y	22)
				(`h	21)
				(`u	20)
				(`j	19)
				(`k	18)	;Middle C
				(`o	17)
				(`l	16)
				(`p	15)	;**							;
				(59	14)	;**	semi-colon			;keys overlap in the middle
				(39	13)	;**	single-quote		;
				(93	12)	;**	right-brace			;

				(`A 	18)	;w/ shift key
				(`W	17)
				(`S	16)
				(`E	15)
				(`D	14)
				(`F	13)
				(`T	12)
				(`G	11)
				(`Y	10)
				(`H	 9)
				(`U	 8)
				(`J	 7)
				(`K	 6)	;High C
				(`O	 5)
				(`L	 4)
				(`P	 3)
				(`:	 2)
				(`"	 1)
				(`}	 0)	;High Gb
				(else	-1)
			)
		)
	)
)

(procedure (FindKey event &tmp keyNum theKey)

	; Find the absolute white key that claims the X (0 - 30)
	(= keyNum (- 30 [whiteKeyNums (/ (event x?) WHITE_WIDTH)]))

	(cond
		((> (event y?) BLACK_BOTTOM)
			(return (keyboard at: keyNum))
		)
		((and	(< keyNum 30)
				(not ((= theKey (keyboard at: (+ keyNum 1))) white?))
				(theKey onMe: event)
			)
			(return theKey)
		)
		((and	keyNum
				(not ((= theKey (keyboard at: (- keyNum 1))) white?))
				(theKey onMe: event)
			)
			(return theKey)
		)
		((> (event y?) BLACK_TOP)
			(return (keyboard at: keyNum))
		)
		(else
			(return FALSE)
		)
	)
)

(instance synth of Feature
	(properties
		x 				87
		y 				102
		nsLeft 		77
		nsTop 		88
		nsBottom 	115
		nsRight 		100
		approachX   67
		approachY   131
 		description "your synthesizer"
		lookStr		"This is the synthesizer Reverse Biaz wants you to play."
		sightAngle 	40
	)
	(method (doVerb theVerb invItem)
	 	(if (== theVerb verbDo)
			(ego setScript: sUsesSynth)
		else
			(super doVerb: theVerb invItem &rest)
		)
	)
)
(instance musicStands of Feature
	(properties
		x 55
		y 167
		heading 0
		nsLeft 31
		nsTop 147
		nsBottom 187
		nsRight 80
 		description "the music stands"
		lookStr 		"You see a bunch of music stands.
						Or is it a flock of music stands?
						Or is it a bevy of music stands?
						You always forget."
		sightAngle 40
	)
)
(instance recordingBooth of Feature
	(properties
		x 280
		y 52
		heading 0
		nsLeft 256
		nsTop 22
		nsBottom 83
		nsRight 304
 		description "the control room"
		lookStr 		"From his control room, Reverse can see the entire studio.
						He can speak to you through the soundproof glass via
						a talkback loudspeaker."
		sightAngle 40
	)
)
(instance overhangingMike of Feature
	(properties
		x 100
		y 63
		heading 0
		nsLeft 94
		nsTop 56
		nsBottom 70
		nsRight 107
 		description "the overhead mike"
		lookStr 		"You wonder how many evil recordings were done with
						this microphone."
		sightAngle 40
	)
)
(instance drums of Feature
	(properties
		x 178
		y 73
		z 0
		heading 0
		nsLeft 135
		nsTop 51
		nsBottom 96
		nsRight 222
 		description "the drum set"
		lookStr 		"As in most studios, the drum set is behind glass to
						enhance the separation of his sound from the other
						musicians."
		sightAngle 40
	)
)


;******* room 670 features *******

(instance music of Feature
	(properties
		x 96
		y 59
		z 0
		heading 0
		nsLeft 48
		nsTop 17
		nsBottom 102
		nsRight 144
 		description "the music"
		sightAngle 40
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbLook
				(if didSession
					(TPrint "This is the song you recorded for Reverse.")
				else
					(TPrint "This is the song you are supposed to perform.
						You DO remember how to play keyboards, don't you, Patti?")
				)
			)
			(verbDo
				(if didSession
					(TPrint "You can't reach the music from inside the control
						room.")
				else
					(TPrint "There's no need for you to turn the page, Patti!")
				)
			)
			(else
				(super doVerb: theVerb invItem &rest)
			)
		)
	)
)
(instance synthPanel of Feature
	(properties
		x 159
		y 130
		z 0
		heading 0
		nsLeft 0
		nsTop 111
		nsBottom 149
		nsRight 319
 		description "the synthesizer control panel"
		lookStr 		"des Rever Records spared no expense programming this
						synthesizer for this session."
		sightAngle 40
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbDo
				(if didSession
					(TPrint "You can't reach the synthesizer from inside the
						booth!")
				else
					(TPrint "As much as you hate to,
						you just can't resist tweaking a patch here and there!")
				)
			)
			(else
				(super doVerb: theVerb invItem &rest)
			)
		)
	)
)
(instance controlPanel of Feature
	(properties
		x 238
		y 76
		z 0
		heading 0
		nsLeft 194
		nsTop 73
		nsBottom 80
		nsRight 283
 		description "the control room mixer"
		sightAngle 40
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbLook
				(TPrint "The control room mixer is covered with a
					complicated array of sliders, knobs, buttons,
					patch bays and XLR connectors.")
			)
			(verbDo
				(TPrint "You'd love to get your hands on those potentiometers,
					but assume Reverse is as particular as most engineers!")
			)
			(else
				(super doVerb: theVerb invItem &rest)
			)
		)
	)
)
(instance controlPanel2 of Feature
	(properties
		x 232
		y 58
		z 0
		heading 0
		nsLeft 168
		nsTop 50
		nsBottom 67
		nsRight 296
 		description "the studio control panel"
		lookStr 		"This panel contains many digital reverb and
						special effects units, power amplifiers, patch bays
						and a copy of last month's Playboy."
		sightAngle 40
	)
)


;************ Sounds **************

(instance rewind of Sound
	(properties
		number	sRewind
	)
)
(instance musicStop of Sound
	(properties
		number	sStop
	)
)
(instance glassesClink of Sound
	(properties
		number	sGlassesClink
		priority 15
	)
)
(instance zing of Sound
	(properties
		number	sZing
	)
)


;*************** Talker ****************

(instance reverseTalker of Talker
	(properties
		name			"Reverse Biaz"
		view			vReverseBiazTalker
		loop			3
		nsLeft		30
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
		view			vReverseBiazTalker
		loop			1
		nsLeft		0
		nsTop			0
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

