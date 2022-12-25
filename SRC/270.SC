;**
;**	Logics for Room 270 -- Airport lobby, center
;**
;**	LSL5:  Passionate Patti Does A Little Undercover Work
;**				Copyright 1991
;**				by Al Lowe
;**
;**	Code thrown together by C. Escobar
;**
;**	Last Update:  September 9, 1991
;**

;prefix
;
;	551 LosAngeles
;	552 NewYork
;	553 AtlanticCity
;	554 Miami
;	555 Gumbo


(script#		270)

(public
	rm270		0
)

;sounds

(define	sAnnouncement1			273)
(define	sAnnouncement2			274)
(define	sAnnouncement3			275)

(define	sfxCameraMove		  	271)
(define	sfxCameraNod		  	272)
(define	mAirportInt 			261)

(define 	vRoom270 		  		270)
(define  vDonationBox     		291)
(define  vNYCspecific     		273)
(define  vTicketAgent2	  		277)
(define  vTicketAgent1	  	   276)
(define  vLarryShowingCard    271)
(define  vAds						263)


(define  counterRect      167 105 325 124)

;globals used in this area:
;
;	blondeX  			164
;	blondeLoop   		165
;	redHeadX 			166
;	redHeadLoop			167

(local
	aCueCounter
	scaredRed
	scaredBlonde
	newRedX
	newBlondeX
	cueCounter
	lookCounter
	[string 500]
)

(instance rm270 of LLRoom
	(properties
		picture		270
		north			290
		south			250
		west			260
		east			280
		lookStr		"You are inside the AeroDork Airline waiting room and
			former ticket counter.
			A security camera peers at you from the far wall."
	)

	(method (init)

		(ego
			init:,
			normalize:
		)

		(blonde
			x: 				(Random 178 235),
			y: 				82,
			ignoreActors:  TRUE,
			setLoop: 		blondeLoop,
			init,
		)
		(redHead
			x: 				(Random 260 305),
			y: 				82,
			ignoreActors:  TRUE,
			setLoop: 		redHeadLoop,
			init,
		)

		(= newBlondeX	(blonde x?))
		(= newRedX		(redHead x?))

		(if (!= prevRoomNum (curRoom east?))
			(blonde  setLoop: 3)
			(redHead setLoop: 4)
		)


		(door
			init:,
			setPri:			4,
		)

		(camera init:)


		(switch prevRoomNum
			(south

				;camera starts out pointing at us

				(camera setCel:5)

				;we are arriving from town and want the limo to leave
				;
				(if (Btst fBeenToTown)
					(Bclr fLimoParked)
				)
			)
			(north
				; So room's super init won't try to walk him on the screen
				(ego edgeHit: 0)
			)
			(east
				(blonde
					init:,
					posn: 	(+ blondeX 319) 82,
				)

				(redHead
					init:,
					posn: 	(+ redHeadX 319) 82,
				)

				(HandsOn)
				(camera setCel: 255)
				(= style SCROLLRIGHT)
			)
			(west
				(HandsOn)
				(= style SCROLLLEFT)
			)
			(else
				(HandsOn)
				(ego
					posn:		      253 185,
					edgeHit:	      0,
					setHeading:    360,
				)
				(camera setCel:5)
			)
		)

		(super init:)

		(curRoom addObstacle:
			((Polygon new:)
				type: PBarredAccess,
				init:	0 0 319 0 319 113 96 113 84 102 76 102 68 113 0 113,
				yourself
			)
			((Polygon new:)
				type: PBarredAccess,
				init:	64 174 27 170 16 157 7 157 10 154 17 154 35 141 87 141
						234 141 241 151 258 151 268 155 269 162 253 168 250 174,
				yourself
			)
		)

		;init curRoom features...

		(plant      init:)
		(chairs		init:)
		(ashtray		init:)
		(vipSign		init:)		;used to be a view

		(theCounter init:)


		;init addToPics...


		(if (== larryLoc NewYork)
			(cannister
				init:,
				approachVerbs: verbLook verbDo
			)
		)


		(switch larryLoc
			(LosAngeles
				(blonde description: 	"Buffi")
				(redHead description: 	"Muffi")

				(ad1
					loop:				4,
					cel:				0,
					init:,
				)
				(ad2
					loop:				4,
					cel:				1,
					init:,
				)
				(ad3
					loop:				4,
					cel:				2,
					init:,
				)
				(ad4
					loop:				4,
					cel:				3,
					init:,
				)
			)

			(NewYork
				(blonde description: 	"Tracie")
				(redHead description: 	"Stacie")

				(ad1
					loop:				5,
					cel:				0,
					init:,
				)
				(ad2
					loop:				5,
					cel:				1,
					init:,
				)
				(ad3
					loop:				5,
					cel:				2,
					init:,
				)
				(ad4
					loop:				5,
					cel:				3,
					init:,
				)

			)

			(AtlanticCity
				(blonde description: 	"Cherri")
				(redHead description: 	"Barri")

				(ad1
					loop:				6,
					cel:				0,
					init:,
				)
				(ad2
					loop:				6,
					cel:				1,
					init:,
				)
				(ad3
					loop:				6,
					cel:				2,
					init:,
				)
				(ad4
					loop:				6,
					cel:				3,
					init:,
				)

			)
			(Miami

				(blonde description: 	"Dixie")
				(redHead description: 	"Trixie")

				(ad1
					loop:				7,
					cel:				0,
					init:,
				)
				(ad2
					loop:				7,
					cel:				1,
					init:,
				)
				(ad3
					loop:				7,
					cel:				2,
					init:,
				)
				(ad4
					loop:				7,
					cel:				3,
					init:,
				)
			)
		)


		(addToPics doit:)

		(blonde	setScript:	sBlonde)
		(redHead	setScript:	sRedHead)

		(DoSound	SetReverb: 10)

		(Delay (Random 40 150) SECONDS self)

	);init

	(method (doVerb theVerb &tmp [str 150])
		(switch theVerb
			(verbLook
				(Format @str
					"You are inside the %s AeroDork Terminal's waiting room (and
					former ticket counter).
					A security camera peers at you from the far wall.
					AeroDork is famous for building exactly the same terminal
					in every city they serve (except Muncie, Indiana--that's
					where they hide your lost luggage!)."
					(switch larryLoc
						(LosAngeles
							{Los Angeles}
						)
						(NewYork
							{New York}
						)
						(AtlanticCity
							{Atlantic City}
						)
						(Miami
							{Miami}
						)
					)
				)
				(TPrint @str)
			)
			(else
				(super doVerb: theVerb &rest)
			)
		)
	)

	(method (cue)
		(announcement
			number:			(Random sAnnouncement1 sAnnouncement3),
			setLoop:			1,
			flags:			TRUE,
			play:
		)
		(Delay (Random 20 40) REALSECONDS self)
	)


	(method (newRoom n)
		(if (not (OneOf n 260 280))
			(DoSound SetReverb: 0)
		)
		(theTimer dispose:, delete:)
		(super newRoom n)
	)

	(method (doit)
		(super doit:)

		(if (ego mover?)
			(switch (ego cel?)
				(1
					(if
						(!= (stepSound number?) 260)
						(stepSound
							number:	260,
							play:
						)
					)
				)
				(4
					(if
						(!= (stepSound number?) 261)
						(stepSound
							number:	261,
							play:
						)
					)
				)
			)
		)

		(cond
			(script)

			((== (ego edgeHit?) EAST)
				(redHead	setScript: FALSE)
				(blonde	setScript: FALSE)
				(= redHeadX		(redHead x?))
				(= redHeadLoop (redHead loop?))
				(= blondeX 		(blonde x?))
				(= blondeLoop	(blonde loop?))

;**					(Printf "
;**								blondeX      %d
;**								blondeLoop   %d
;**								redHeadX     %d
;**								redHeadLoop	 %d "
;**
;**								blondeX blondeLoop redHeadX redHeadLoop
;**					)

			)
			((ego inRect: counterRect)
				(if (not scaredBlonde)
					(= scaredBlonde TRUE)
					(blonde  setScript: sBlonde NULL 365)
				)
				(if (not scaredRed)
					(= scaredRed TRUE)
					(redHead setScript: sRedHead NULL 375)
				)
			)
		)
	)
)

(instance cannister of PicView
	(properties
		view			 	vNYCspecific ;273
		loop			 	0
		x				 	142
		y				 	111
		z				 	0
		sightAngle 	 	90
		approachX    	139
		approachY    	114
		priority			4
 		description  	"the charity collection canister"
		signal			(| fixedLoop fixPriOn ignrAct ignrHrz)
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbDo
				(if (and
						  (not (ego has iQuarter))
						  (not (Btst fGotQuarterNY)))

					(Load VIEW vDonationBox)			;291
					(curRoom setScript: sStealChange)
				else
					(TPrint "You already took the quarter you need;
						leave the small change for those poor little kids!")
					(TPrint "(You big bully!)" LOW)
				)
			)
			(verbLook
				(if (and
						  (not (ego has iQuarter))
						  (not (Btst fGotQuarterNY)))
					(TPrint "Directly beneath the security camera is a
						charity cannister containing many pennies, nickels and dimes,
						but only one quarter."
					)
				else
					(TPrint "Directly beneath the security camera is a
						charity cannister containing many pennies, nickels and dimes,
						but no more quarters."
					)
				)
			)

			(verbUse
				(if (== invItem iQuarter)
					(++ lookCounter)
					(TPrint "You are much too frugal to ever part with
						a hard-found quarter!")
					(if (and	(> lookCounter 3)
							(not (Random 0 10)))
						(TPrint "\n(TIGHT-ASS!)\n" LOW)
						(= lookCounter 0)
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


(instance plant of Feature
	(properties
		x					46
		y					140
		z					0
		heading			0
		nsLeft			28
		nsTop				117
		nsBottom			164
		nsRight			64
 		description		"the plant"
		sightAngle		40
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbLook
				(TPrint "AeroDork has spared no expense to decorate
					this fabulous gateway.")
			)
			(else
				(super doVerb: theVerb invItem &rest)
			)
		)
	)
)

(instance chairs of Feature
	(properties
		x					156
		y					146
		z					0
		heading			0
		nsLeft			72
		nsTop				126
		nsBottom			167
		nsRight			240
 		description		"the chairs"
		sightAngle		40
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbLook
				(TPrint "Evidently AeroDork Airline's automated
					flight handling is efficient--there's not
					one person waiting in this waiting room.")
			)
			(verbDo
				(TPrint "You're not really interested in sitting in those
					little puddles of overnight sleeper drool.")
			)
			(else
				(super doVerb: theVerb invItem &rest)
			)
		)
	)
)

(instance ashtray of Feature
	(properties
		x 					249
		y 					148
		z					0
		heading 			0
		nsLeft         240
		nsTop          134
		nsBottom 		162
		nsRight        258
 		description    "the ashtray"
		sightAngle     40
	)

	(method (doVerb theVerb invItem &tmp [str 30])
		(switch theVerb
			(verbLook
				(TPrint "You appreciate the historical accuracy of
					an ashtray simulator in public places."
				)
			)
			(verbDo
				(if (Random 0 1)
					(TPrint "What are you expecting?  A disco admission card?")
				else
					(TPrint "You want to put your butt in the ashtray?")
				)
			)
			(else
				(super doVerb: theVerb invItem &rest)
			)
		)
	)
)

(instance vipSign of Feature

	(properties
		x 					82
		y 					31
		z 					0
		heading 			0
		nsLeft 			51
		nsTop 			19
		nsBottom 		44
		nsRight 			114
 		description 	"the sign"
		sightAngle 		40
	)

	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbLook
				(TPrint "The sign over the door reads,
							\"Chartreuse Carpet V.I.P. Lounge.\"")
			)
			(else
				(super doVerb: theVerb invItem &rest)
			)
		)
	)
)

(instance theCounter of Feature
	(properties
		y       			1
		heading 			0
		nsLeft 			162
		nsTop 			44
		nsBottom 		88
		nsRight 			317
 		description 	"the ticket counter"
		sightAngle 		40
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbLook
				(TPrint "This counter was once staffed by highly
					trained, courteous young men and women.")
				(TPrint "(Of course, that was when this area was
					part of the frozen yogurt concession!)" LOW)
			)
			(verbDo
				(Format @string "A few days ago, you could have bought a ticket here.
						But now %s and %s,
						AeroDork's last remaining employees,
						have nothing to do but stand around and gossip."
						(blonde description?) (redHead description?)
				)
				(TPrint @string)
				(TPrint "(So, in essence, not that much has changed!)" LOW)
			)
			(verbTalk
				(TPrint "You must have just missed them.
					Perhaps you should talk to one of the girls,
					instead of to the ticket counter!")
			)
			(else
				(super doVerb: theVerb invItem &rest)
			)
		)
	)
)

(instance blonde of Actor
	(properties
		view			   vTicketAgent2		;277
		illegalBits:   0
		sightAngle     40
	)

	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbLook
				(Format @string "It's %s, AeroDork's blonde bombshell.
					Once she was a highly motivated employee.
					Once she was a highly trained professional.
					Once she never forgot to ask,
					\"Didya wan'sum fries wit' dat?\""
					(blonde description?)
				)
				(TPrint @string)
			)

			(verbDo
				(Format @string "%s was once a ticket agent here.
					Now her only responsibility is to the few remaining employees:
					she fires them!"
					(blonde description?)
				)
				(TPrint @string)
			)
			(verbTalk
				(TPrint "\"Excuse me, Miss. Could you help me?\"")
				(TPrint "\"Buddy, I don't think ANYONE could help you!\"
					she replies sarcastically."
				)
			)
			(verbUse
				(Format @string "\"Get that away from me, dork!\" shouts %s.
					\"Anything YOU have, I don't want!\""
					(blonde description?)
				)
				(TPrint @string)
			)
			(else
				(super doVerb: theVerb invItem &rest)
			)
		)
	)
)

(instance redHead of Actor
	(properties
		view			   vTicketAgent1	;276
		illegalBits:   0
		sightAngle     90
	)

	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbLook
				(Format @string "It's %s. Once she was AeroDork's red-headed,
					red-blooded, red-hot, temptress of the flight path."
					(redHead description?)
				)
				(TPrint @string)
				(TPrint "Now she's lucky if she even sees a pilot.")
			)
			(verbDo
				(TPrint "\"Excuse me, Clerk,\" you cry,
					\"Could I buy a ticket here?\"")
				(TPrint "\"Nope,\" she retorts with a snap of her gum,
					\"we're all out. Try our ATM outside, ok?\"")
			)
			(verbTalk
				(TPrint "\"Excuse me, Miss. Could you help me?\"")
				(TPrint "\"Fella, I ain't interested in anyone that can't
					support me in the style to which I've grown accustomed to,\"
					she says, interrupting your clever inquiry.
					\"And nobody I know is accustomed to a style like yours!\""
				)
			)
			(verbUse
				(Format @string "\"Get that away from me, dork!\" shouts %s.
					\"Anything YOU have, I don't want!\""
					(redHead description?)
				)
				(TPrint @string)
			)
			(else
				(super doVerb: theVerb invItem &rest)
			)
		)
	)
)


(instance sStealChange of Script
	(method (changeState newState &tmp var1 var2)
		(switchto (= state newState)

			(
				(HandsOff)
				(ego
					view:          vDonationBox, ;291
					ignoreActors:	TRUE,
					illegalBits:	0,
					setLoop:       3,
					cycleSpeed:    6,
					setCel:			0,
					setCycle:      EndLoop self,
				)
			)

			(
				(TPrint "There goes your chance for a \"Larry's Kids\" telethon!")
				(ego get: iQuarter)
				(= quarters 1)
				(ego normalize:)
				(Points ptsStoleQuarter fGotQuarterNY)
				(HandsOn)
				(self dispose:)
			)
		)
	)
)


(instance sBlonde of Script
	(method (changeState newState &tmp oldX)
		(switchto (= state newState)
			(
				(= cycles 2)
			)

			(
				(= oldX (blonde x?))
				(if register
					(= newBlondeX register)						; forcing the position
				else
					(switch (Random 1 2)
						(1
							(= newBlondeX (Random 178 307))
						)
						(2
							(= newBlondeX (Random 332 375))
						)
					)
				)

				(if (and (not register)
				       (or
						   (< newBlondeX (redHead x?) oldX)	; redHead in the way
						   (< oldX (redHead x?) newBlondeX)))

					(self init:)
				else
					(blonde
						setLoop:       RELEASE,
						setCycle:      Walk,
						setMotion		MoveTo  newBlondeX  82 self,
					)
				)
			)

			(
				(if (== (blonde x?) newBlondeX)
					(blonde
						setLoop:       3,
					)
				)
				(= seconds (Random 2 5))
			)

			(
				(= register 0)
				(= scaredBlonde FALSE)
				(self init:)
			)
		)
	)
)

(instance sRedHead of Script
	(method (changeState newState &tmp oldX)
		(switchto (= state newState)
			(
				(= cycles 2)
			)

			(
				(= oldX (redHead x?))
				(if register
					(= newRedX register)						; forcing the position
				else
					(switch (Random 1 2)
						(1
							(= newRedX (Random 178 307))
						)
						(2
							(= newRedX (Random 332 375))
						)
					)
				)

				(if (and (not register)
				      (or
							(< newRedX (blonde x?) oldX)		; blonde in the way
							(< oldX (blonde x?) newRedX)))
					(self init:)
				else
					(redHead
						setLoop:       RELEASE,
						setCycle:      Walk,
						setMotion		MoveTo  newRedX  82 self,
					)
				)
			)

			(
				(if (== (redHead x?) newRedX)
					(redHead
						setLoop:       4,
					)
				)
				(= seconds (Random 2 5))
			)

			(
				(= register 0)
				(= scaredRed FALSE)
				(self init:)
			)
		)
	)
)






(instance door of Door
 	(properties
		view			   vRoom270    ;270
		loop			   0
		x				   98
		y				   106
 		description    "the door"
		sightAngle     90
		entranceTo 		290				; room # beyond the door
		locked 			TRUE
		moveToX			81
		moveToY			105				; where to move to when going to door
		heading			0					; heading in which ego should exit
		approachX		81
		approachY		111
		enterType		doorWalkEgo
		exitType			doorWalkEgo
	)

	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbLook
				(TPrint "This door leads to the exclusive
					AeroDork Airline V.I.P. Chartreuse Carpet Lounge.")
			)
			(verbDo
				(TPrint "You can't open this door.
					It's secured from within.
					If only you had some form of identification to prove
					you really belong inside.")
			)
			(verbTalk
				(TPrint "\"Hello!\" you shout. \"Is there anyone inside?\"")
				(TPrint "(You hear no response.)" LOW)
			)
			(else
				(super doVerb: theVerb invItem &rest)
			)
		)
	)
)




(instance ad1 of PicView
	(properties
		view			   vAds 		;263
		x					11
		y					11
 		description		"the sign"
		sightAngle		90
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbLook
				(switch larryLoc
					(LosAngeles	 				;same as ad 4 in room 260
						(TPrint "While you're here in Los Angeles,
							be sure to visit wonderful \"Wizneyland.\"
							Your children will be terrified when they see their
							favorite cartoon characters running up to them in
							enormous grinning overstuffed costumes...
							horrified when they ride through the
							bowel-loosening haunted house!
							Wizneyland!
							It's a million trips to the bathroom!!")
					)
					(NewYork						;same as ad 4 in room 260
						(TPrint "It's Carefree Living in the Eurasian Style
							in new Exorbitania Townhouses.
							Only 1/2 block from Central Park, Broadway,
							Madison Square Garden, Statue of Liberty, UN Building,
							World Trade Center, Automat.
							One-bedrooms start at $10,000,000.
							Security locks, no pets.
							And no vacancies to the likes of you, either, buddy!")
					)
					(AtlanticCity				;same as ad 4 in room 260
						(TPrint "Where does the Moral Majority turn to
							promote censorship and creeping Fascism?
							\"Slant,\" the Holier-Than-Thou Newsweekly.
							No liberal pandering, no pinko editorial cartoons,
							no objective journalism...
							just good ol' fashioned Fundamentalism.
							Printed on 100% recycled environmentalists.")
					)
					(Miami	  					;same as ad 4 in room 260
						(TPrint "\t\t\tRIOT GEAR!\n
							\n
							Explosives, weapons, armor.
							Brass knuckles, nunchuks, pipe bombs, switchblades,
							guns, bazookas, tanks, stealth bombers
							(24-hours notice, please).
							Senior citizen discounts.")
					)
				)
			)
			(else
				(super doVerb: theVerb invItem &rest)
			)
		)
	)
)

(instance ad2 of PicView
	(properties
		view			   vAds 		;263
		x					163
		y					11
 		description    "the sign"
		sightAngle 	 	90
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbLook
				(switch larryLoc
					(LosAngeles
						(TPrint "The Los Angeles Chamber of Commerce
							welcomes YOU to Los Angeles!
							See Movie Stars' homes!
							See the Beverly Hills Wax Museum!
							See your favorite TV shows actually being taped
							for future broadcast!
							Some culture also available; call for details.")
					)
					(NewYork
						(TPrint "Gay? Lesbian? Divorced? Single? Widowed? Depressed?
							Sorry, but the `Blecchnaven Center' offers weekly
							seminars for happy, straight couples only.")
					)
					(AtlanticCity
						(Points ptsGotACNum pfGotACNum)
						(TPrint "For the biggest ride you've ever had,
							give us a call right now.
							Let us expose your blatant consumerist backside.
							\"Tramp Limo Service, 553-4468\"")
					)
					(Miami
					 	(TPrint "Luxury Accommodations are merely a brick's throw
							away at the Miami Fountainblooey Hotel.
							28-story shatterproof glass atrium,
							fire-resistant deluxe suites.
							Every Thursday night is Singles Night
							at \"The Castro Room.\"
							Valet parking, coat check, firearms rack.")
					)
				)
			)
			(else
				(super doVerb: theVerb invItem &rest)
			)
		)
	)
)

(instance ad3 of PicView
	(properties
		view			   vAds 		;263
		x					249
		y					11
 		description    "the sign"
		sightAngle 	   90
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbLook
				(switch larryLoc
					(LosAngeles
						(TPrint "It's the second-most fun you've had getting wet!
							Visit MARINELAND,\n
							U. S. A.! See authentic U. S. Marines swimming, playing,
							doing tricks.
							Watch Porca, `The Killer Marine,'
							leap out of the water at feeding time for fishsticks!")
					)
					(NewYork
						(TPrint "Now through September 31st at
							Madison's Glare Garden!
							Monster Trucks! Funny Cars!
							Stupid Motorcycles! Mutant Mopeds!
							We've turned the floor of the coliseum
							into a giant mud bog!
							See audience members with necks twice
							as thick as their heads!
							Call Ticket-O-Rama for details.")
					)
					(AtlanticCity
						(TPrint "`Honest' Noah's Check-Cashing Service.
							The fastest way to get money when you've gambled
							away all your cash.
							Sorry, no second-party checks, third-party checks,
							bank checks, personal checks, out-of-state Czechs,
							wheat checks, rice checks.
							Plenty of corn checks!")
					)
					(Miami
						(TPrint "\"Wet 'n Watery Wild Whooshing World!\"
							Home of the \"PsychoKiller Two-mile Waterslide.\"
							Speed downhill at 75 miles an hour with nothing to
							protect you but a thin slice of cheap foam rubber and
							your bathing suit (assuming it stays on).
							You must be under 90 to ride.
							\"It was soooooooo scary!
							Luckily, at 75 miles an hour,
							who can tell you're peeing?\"
							says Barry Smith, of East Miami.")
					)
				)
			)
			(else
				(super doVerb: theVerb invItem &rest)
			)
		)
	)
)

(instance ad4 of PicView
	(properties
		view			   vAds 		;263
		x					317
		y					8
 		description    "the sign"
		sightAngle 	 	90
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbLook
				(switch larryLoc
					(LosAngeles					;same as ad 1 in room 280
						(TPrint "How do busy executives stay on
							top of world finance?
							\"Businessmen's Financial Hourly,\" of course.
							All the financial news you need,
							delivered to your home or office every hour on the hour,
							all day, all night.
							Latest markets.
							Millions of pages a year of the information YOU need
							every minute to keep your business from
							sinking like a rock.")
					)
					(NewYork						;same as ad 1 in room 280
						(TPrint "The NYC Ballet Company's 2015th production of
							\"Sleeping Beauty\" opens August 15th.
							The NYC Times called it,
							\"A classic... never gets all that boring,
							even though you've seen it several hundred times.\"
							Opening September 20:
							the 2016th production of \"Sleeping Beauty.\"
							Call The Ticketster for details.")
					)
					(AtlanticCity				;same as ad 1 in room 280
						(TPrint "Gambleholic's Anonymous.
							Kick the gambling habit!
							We'll lay you 2 to 1 we can clean up even
							the worst gambling problem.
							Meetings held on best 2 out of 3 Thursdays each month.
							Ex-major league baseball players admitted FREE!")
					)
					(Miami						;same as ad 1 in room 280
						(Points ptsGotGCNum pfGotGCNum)
						(TPrint "\"Misplace\" something?
							\"Lose\" something important?
							\"Can't find\" that important documentation?
							Let us help.
							We're \"Just Green Cards\"
							and we're here to help any alien who has
							\"lost\" their card!
							Just call me, Carlos, at 554-1272!
							You'll get your \"replacement\" immediately.
							And remember: \"We deliver!!\"")
					)
				)
			)
			(else
				(super doVerb: theVerb invItem &rest)
			)
		)
	)
)

(instance camera of Prop
	(properties
	   view         	vRoom270	;270
		loop         	1
		x 				 	126
		y 				 	58
		nsLeft 		 	112
		nsTop 		 	46
		nsBottom 	 	63
		nsRight 		 	130
 		description  	"the security camera"
		approachX    	92
		approachY   	113
		sightAngle 	 	90
	)

	(method (init)
		(super init:)
		(self
			approachVerbs: verbUse verbDo verbLook 		;verbNone erases
		)
	)

	(method (doit &tmp theLoop theCel oldCel oldLoop [str 20])
		(super doit:)
		(if (not (curRoom script?))
			(= oldCel cel)
			(= oldLoop loop)
			(= theCel (/ (- (GetAngle x y (ego x?) (ego y?)) 110) 14))
			(self
				setLoop:		(if (> (ego y?) 159) 1 else 5),
				setCel:		(- 9 theCel)
			)
			(if (or	(!= oldLoop loop)
						(!= oldCel cel)
					)
				(theMusic2
					number:			sfxCameraMove,
					loop:				1,
					play:
				)
			)
		)
	)

	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbDo
				(TPrint "Hey! You're smudging the lens!")
			)
			(verbLook
				(TPrint "You have the distinct feeling that someone is
					watching you.")
			)
			(verbUse
				(switch invItem
					(iGoldCard
						(curRoom setScript sShowCard)
					)
					(iBoardingPass
						(TPrint "While the boarding pass proves you have a flight
							on this airline, it doesn't prove you have enough class
							to be admitted to the exclusive AeroDork Chartreuse
							Carpet V.I.P. lounge."
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


(instance sShowCard of Script
	(method (changeState newState &tmp var1 var2)
		(switchto (= state newState)
			(
				(HandsOff)
				(= register 5)
				(Points ptsEnteredLounge pfEnteredLounge)
				(ego
					view:          vLarryShowingCard,	;271
					setLoop:       1,
					cycleSpeed:    4,
					setCel:			0,
					setCycle:      EndLoop self,
				)
			)
			(
				(= start state)
				(= cycles 6)

			)
			(
				(theMusic2
					number:	sfxCameraNod,
					setLoop:	1,
					play: 	self
				)
			)
			(
				(if (not (-- register))
					(= cycles 1)
				else
					(if (not (camera cel?))
						(camera
							setCel: 	  1,
							setLoop:	  3,
						)
					else
						(camera
							setCel: 	  0,
							setLoop:	  3,
						)
					)
					(self init:)
				)
			)
			(
				(camera setCel:0)
				(ego
					normalize:,
				)
				(door
					locked: 			FALSE,
					open:
				)
				(self dispose:)
			)

		)
	)
)


(instance stepSound of Sound)
(instance announcement of Sound)
