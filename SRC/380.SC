;**
;**	Logics for Room 380 -- Alley exterior End of Game
;**
;**	LSL5:  Passionate Patti Does A Little Undercover Work
;**				Copyright 1991
;**				by Al Lowe Ass. Inc
;**
;**	Programmed by Steve Conrad
;**
;**	Last Update:  September 9, 1991
;**


(script#		380)

(public
	rm380		0
)

(define	pDarkAlley2Shot		415)
(define	vDesmondTalker		1415)
(define	vPattiTalker	  	1416)

(define	mAsTimeGoesBy			380)
(define		sDC3Engines		381)

(define	DESMOND_PRINT		#at  15 15 #width 160)
(define	PATTI_PRINT			#at 145 15 #width 160)

(define	PATTILEFT		168)
(define	PATTITOP			76)
(define	PATTIX			140)				;**	where the prints go
(define	PATTIY	  		180)
(define	DESMONDLEFT		120)
(define	DESMONDTOP		73)
(define	DESMONDX			1)					;**	where the prints go
(define	DESMONDY			180)

(define  tTalkLoop 3)
(define  tBustLoop 1)
(define  tEyesLoop 2)
(define  tMouthLoop 0)


(instance rm380 of LLRoom
	(properties
		picture		pDarkAlley2Shot
		style			PIXELDISSOLVE
	)

	(method (init &tmp i)
		(LoadMany VIEW
			vDesmondTalker
			vPattiTalker
		)
		(super init:)
		;**	just let theMusic2 continue to play from 320...
		(SetFFRoom 385)

		(curRoom setScript: sCartoon)
	)
)

(instance sCartoon of Script

(enum												;**	sCartoon state values
	START
	START2
	START3
	START4
	START5
	DEBRIEF
	DEBRIEF2
	DEBRIEF3
	DEBRIEF4
	SHILL_LOBBY
	SHILL_LOBBY2
	SHILL_LOBBY3
	SHILL_LOBBY4
	SHILL_WAIT
	SHILL_WAIT2
	SHILL_WAIT3
	SHILL_WAIT4
	SHILL_WAIT7
	STUDIO
	STUDIO1
	STUDIO2
	STUDIO3
	STUDIO4
	STUDIO5
	STUDIO6
	KRAP
	KRAP1
	KRAP2
	KRAP3
	KRAP4
	KRAP5
	KRAP5a
	KRAP6
	KRAP7
	KRAP8
	KRAP8a
	KRAP9
	KRAP10
	KRAP10a
	END_DEBRIEFING
	WINDING_DOWN
	bigTits
	WINDING_DOWN2
	didJaMissDaBra
	bigBra2
	thanksAsshole
	soonerThanUthink
	WINDING_DOWN3
	MEANWHILE
	END
)

	(method (changeState newState)
		(switch (= state newState)
			(START
				(HandsOff)
				(= seconds 4)
			)
			(START2
				(Say desmondTalker "\"Well, Patti,
					I'm glad to see you made it back safely.
					I told you there was nothing to worry about!\""
					#dispose:
					#caller:		self
				)
			)
			(START3
				(= ticks 30)
			)
			(START4
				(Say pattiTalker "\"You were right, Inspector Desmond.
					But for a while there, I was really stumped!\""
					#dispose:
					#caller: self
				)
			)
			(START5
				(= ticks 30)
			)
			(DEBRIEF
				(Say desmondTalker "\"Oh, you did fine!
					Now let's go over what you discovered through
					your investigations.\""
					#dispose:
					#caller: self
				)
			)
			(DEBRIEF2
				(= ticks 30)
			)
			(DEBRIEF3
				(Say desmondTalker "\"Let's begin with Baltimore and des Rever
					Records.
					What happened?\""
					#dispose:
					#caller: self
				)
			)
			(DEBRIEF4
				(= ticks 30)
			)

			;	Shill Building	Lobby

			(SHILL_LOBBY
				(cond
					((Btst pfDatamanSol)
						(Say pattiTalker "\"It was easy.
							I got past that guard in the Shill Building Lobby
							just by showing him the DataMan\05
							into which I had inserted Reverse Biaz's cartridge.\""
							#dispose:
							#caller: self
						)
					)
					((Btst pfFaxSol)
						(Say pattiTalker "\"It was easy.
							I got past that guard in the Shill Building Lobby
							just by showing him Reverse Biaz's fax.\""
							#dispose:
							#caller: self
						)
					)
					((Btst fDirectorySol)
						(Say pattiTalker "\"It was easy.
							I got past that guard in the Shill Building Lobby
							just by finding Reverse Biaz's office in the building
							directory.\""
							#dispose:
							#caller: self
						)
					)
					(else
						(Say pattiTalker "\"I had real trouble there, Inspector.
							I never could figure out any way to get past that
							guard in the Shill Building Lobby.\""
							#dispose:
							#caller: self
						)
					)
				)
			)
			(SHILL_LOBBY2
				(= ticks 60)
			)
			(SHILL_LOBBY3
				(if (or	(Btst pfDatamanSol)
							(Btst pfFaxSol)
							(Btst	fDirectorySol))
					(Say desmondTalker "\"Good work, Patti.
						But what happened after you were upstairs?
						Did you learn anything about des Rever or Biaz?\""
						#dispose:
						#caller: self
					)
				else
					(= state	(- KRAP 1))
					(Say desmondTalker "\"Oh, no. That's too bad.
						I thought Reverse Biaz was a major player.
						He might have been the key to breaking this case.\""
						#dispose:
						#caller: self
					)
				)
			)
			(SHILL_LOBBY4
				(= ticks 120)
			)

			;	Shill Building	Waiting Room

			(SHILL_WAIT							;At least we hope she'll wait
				(if (ego has: iGoldRecord)
					(Say pattiTalker "\"While upstairs in des Rever's outer office,
						I found a gold record which I think might contain some
						valuable evidence.
						Here, take it!\""
						#dispose:
						#caller: self
					)
					(ego put: iGoldRecord)
				else
					(Say pattiTalker "\"I couldn't find anything interesting
						in des Rever's outer office.\""
						#dispose:
						#caller: self
					)
				)
			)
			(SHILL_WAIT2
				(= ticks 60)
			)
			(SHILL_WAIT3
				(if (Btst pfHeard33)
					(Say pattiTalker "\"I discovered the gold record could
						actually be played on a stereo system there.
 						It sounded fine.\""
						#dispose:
						#caller: self
					)
				else
					(= cycles 2)
				)
			)
			(SHILL_WAIT4
				(if (Btst pfHeard33R)
					(Say pattiTalker "\"But when I played the gold record
						in reverse at 33-1/3, I heard a strange message.
						It should be proof positive of the vicious things
						they've been doing!\""
						#dispose:
						#caller: self
					)
				else
					(= cycles 2)
				)
			)
			(SHILL_WAIT7
				(= ticks 60)
			)

			;	Shill Building	Studio

			(STUDIO
				(Say desmondTalker "\"Good going, Patti.
					Did you make the recording session I booked for you?\""
					#dispose:
					#caller: self
				)
			)
			(STUDIO1
				(if (Btst pfDidSession)
					(Say pattiTalker "\"Actually, the studio session went pretty
						well, Boss.
						After a couple of tries, I started really cookin'!\""
						#dispose:
						#caller: self
					)
				else
					(Say pattiTalker "\"I'm sorry, but I just couldn't,
						Inspector.\""
						#dispose:
						#caller: self
					)
				)
			)
			(STUDIO2
				(= ticks 60)
			)
			(STUDIO3
				(cond
					((Btst pfChampagne)
						(Say pattiTalker "\"You'll love this, Boss!
							I just used an old technique with which I'm quite
							familiar!
							I grabbed some champagne from my limo and
							got Reverse drunk!
							So drunk, in fact, he gave me this cassette tape
							to impress me.\""
							#dispose:
							#caller: self
						)
						(ego put: iCassetteTape)
					)
					((Btst pfZipperSex)
						(Say pattiTalker "\"Gosh, Boss.
							I just used an old technique with which I'm quite
							familiar!
							Here's the tape I, uh, `talked' him out of!\""
							#dispose:
							#caller: self
						)
						(ego put: iCassetteTape)
					)
					((Btst pfTalkSex)
						(Say pattiTalker "\"Here's the tape I, uh,
							`talked' him out of!\""
							#dispose:
							#caller: self
						)
						(ego put: iCassetteTape)
					)
					(else
						(= state (- KRAP 1))
						(Say desmondTalker "\"Too bad, Patti.
							I wish you could have found something there.\""
							#dispose:
							#caller: self
						)
					)
				)
			)
			(STUDIO4
				(= ticks 60)
			)
			(STUDIO5
				(Say desmondTalker "\"You really have a way with men, Patti!\"
					he laughs.
					\"Our agents could have never `obtained'
					evidence like you did.\""
					#dispose:
					#caller: self
				)
			)
			(STUDIO6
				(= ticks 120)
			)

			;	KRAP building room 840

			(KRAP
				(Say desmondTalker "\"So, did you discover
					anything at K-RAP radio?\""
					#dispose:
					#caller: self
				)
			)
			(KRAP1
				(if (Btst pfGotByReceptionist)
				 	(Say pattiTalker "\"Well, I sneaked into John Krapper's
						office just as the receptionist came back from her break.\""
						#dispose:
						#caller: self
					)
				else
				 	(Say pattiTalker "\"Sorry, Boss, but I couldn't even
						get past the K-RAP lobby!
						They really had tight security!\""
						#dispose:
						#caller: self
					)
				)
			)
			(KRAP2
				(= ticks 30)
			)
			(KRAP3
				(if (Btst pfGotByReceptionist)
					(Say desmondTalker "\"Nice job of sleuthing, Patti!\""
						#dispose:
						#caller: self
					)
				else
					(= state (- END_DEBRIEFING	1))
				 	(Say desmondTalker "\"It's too bad you didn't get further
						into the K-RAP building, Patti.
						I think P. C. Hammer might well be crucial to this problem.\""
						#dispose:
						#caller: self
					)
				)
			)
			(KRAP4
				(= ticks 60)
			)
			;If the user got to this point, he would have had to have finished
			;the rest of the area.
			(KRAP5
				(Say pattiTalker "\"I searched John Krapper's desk and
					discovered a folder filled with what looked like
					incriminating evidence to me!\""
					#dispose:
					#caller: self
				)
			)
			(KRAP5a
				(Say pattiTalker "\"I assumed he'd miss the folder if I stole it,
					so I used his photocopier to make copies of it.
					Here they are!\""
					#dispose:
					#caller: self
				)
				(ego put: iPhotocopies)
			)
			(KRAP6
				(Say desmondTalker "\"Way to go, Patti. Nice work.\""
					#dispose:
					#caller: self
				)
			)
			(KRAP7
				(= ticks 60)
			)
			(KRAP8
				(Say pattiTalker "\"And, besides that,
					I overheard what `2 Live 2 Screw' thought were private
					conversations.
					They didn't realize they were meeting in a room with
					an open microphone!\""
					#dispose:
					#caller: self
				)
			)
			(KRAP8a
				(Say pattiTalker "\"Although I nearly got trapped by P. C. Hammer,
					I recorded `2 Live 2 Screw' on a reel-to-reel tape.\""
					#dispose:
					#caller: self
				)
			)
			(KRAP9
				(if (Btst pfGetRecordedTape)
					(Say desmondTalker "\"Great!
						Patti, you have proven yourself the right woman for the
						job!\""
						#dispose:
						#caller: self
					)
					(ego put: iReelToReelTape)
				else
					(Say desmondTalker "\"Good, Patti. May I have the tape?\""
						#dispose:
						#caller: self
					)
				)
			)
			(KRAP10
				(if (not (Btst pfGetRecordedTape))
					(Say pattiTalker "\"Uh, well, Inspector,
						I must confess: I left it on the tape recorder
						in the K-RAP control room.
						I'm sorry.\""
						#dispose:
						#caller: self
					)
				else
					(= cycles 2)
				)
			)
			(KRAP10a
				(if (not (Btst pfGetRecordedTape))
					(Say desmondTalker "\"Oh, that's all right, Patti.
						Mistakes happen; even to super-spies!\""
						#dispose:
						#caller: self
					)
				else
					(= cycles 2)
				)
			)

			;	Close out cartoon

			(END_DEBRIEFING
				(= ticks 30)
			)
			(WINDING_DOWN
				(if (and	(not (Btst pfGotByReceptionist))
							;**	user didn't do anything in K-RAP 	AND
							;**	user didn't do anything in Shill
							(not (or (Btst pfDatamanSol)
										(Btst pfFaxSol)
										(Btst	fDirectorySol))))
					(Say desmondTalker "\"Patti, I don't know if this is an
						appropriate time, but I must tell you:
						I find myself strongly attracted to you.\""
						#dispose:
						#caller: self
					)
				else
					(Say desmondTalker "\"Patti, you've done a wonderful job!
						I'm so proud of you!\""
						#dispose:
						#caller: self
					)
					(++ state)
				)
			)
			(bigTits
				(Say desmondTalker "\"You have such wonderful big, uh, er, eyes!
					Even though you weren't much help as a spy,
					I hoped you might feel something for me, too.
					So, here's a little favor.\""
					#dispose:
					#caller: self
				)
			)
			(WINDING_DOWN2
				(= ticks 30)
			)
			(didJaMissDaBra
				(if (Btst pfGotBrassiere)
					(= state (- WINDING_DOWN3 1))
					(= cycles 2)
				else
					(Say desmondTalker
						"\"I have a small souvenir for you, Patti--a little
						something for you to remember your experiences as
						an F.B.I. agent.\""
						#dispose:
						#caller: self
					)
				)
			)
			(bigBra2
				(Say desmondTalker
					"\"I want you to have Commander Twit's exploding 
					bra--and every time you wear it, think of me!\""
					#dispose:
					#caller: self
				)
				(ego get: iBra)
			)
			(thanksAsshole
				(Say pattiTalker "\"Oh, great! I'll wear it to my next
					heavy metal gig.\""
					#dispose:
					#caller: self
				)
			)
			(soonerThanUthink
				(Say desmondTalker "He laughs, \"And that may be sooner than
					you think, Patti!\""
					#dispose:
					#caller: self
				)
			)
			(WINDING_DOWN3
				(Say desmondTalker "\"In anticipation of your success,
					I made all the necessary arrangements for your
					White House...\""
					#dispose:
					#caller: self
				)
			)
			(MEANWHILE
				(DrawPic pBlackPic PLAIN)
				(theMusic2 stop:)
				(Display "Suddenly,\n
					a westbound flight\n
					heads South!"
					#p_mode:		teJustCenter
					#p_color: 	colVLRed
					#p_font:		niceFont
					#p_width:	318
					#p_at: 		1 (- 89 (/ (* 14 1) 2))
					;           ³             ³  ³  ³
					;           ³             ³  ³  ÀÄ this must be 2
					;           ³             ³  ÀÄÄÄÄ make this # of lines in msg
					;           ³             ÀÄÄÄÄÄÄÄ make this font height
					;           ÀÄÄÄÄÄÄÄÄÄÄÄÄÄÄÄÄÄÄÄÄÄ this must be 1
				)
				(= cycles 2)
			)
			(END
				(curRoom newRoom: 385)
			)
		)
	)
)

;*********** Talkers ************

(instance pattiTalker of Talker
	(properties
		name			"You"
		view			vPattiTalker
		loop			tTalkLoop
		nsLeft		PATTILEFT
		nsTop			PATTITOP
		x				PATTIX
		y				PATTIY
		talkWidth	318
	)
	(method (init)
		(= mouth pattiMouth)
		(super init: &rest)
	)
)

(instance pattiMouth of Prop
	(properties
		view			vPattiTalker
		loop			tMouthLoop
		nsLeft		1
		nsTop			0
		ticks			10
		cycleSpeed 	5
	)
)

(instance desmondTalker of Talker
	(properties
		name			"Desmond"
		view			vDesmondTalker
		loop			tTalkLoop
		nsLeft		DESMONDLEFT
		nsTop			DESMONDTOP
		x				DESMONDX
		y				DESMONDY
		talkWidth	180
	)
	(method (init)
		(= mouth desmondMouth)
		(super init: &rest)
	)
)

(instance desmondMouth of Prop
	(properties
		view			vDesmondTalker
		loop			tMouthLoop
		nsLeft	0
		nsTop		0
		ticks		10
		cycleSpeed 	5
	)
)
