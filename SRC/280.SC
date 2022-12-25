;**
;**	Logics for Room 280 -- Airport lobby, east
;**
;**	LSL5:  Passionate Patti Does A Little Undercover Work
;**				Copyright 1991
;**				by Al Lowe
;**
;**	Almost finished by C. Escobar
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

(script#		280)

(public
	rm280		0
)


(define	sPickUpPhone 			36)
(define 	sHangUpPhone			37)

(define	sAnnouncement1			273)
(define	sAnnouncement2			274)
(define	sAnnouncement3			275)

(define vRoom280 		  	280)

(define counterRect    	-3 105 86 124)
(define vTicketAgent2	277)
(define vTicketAgent1	276)
	(define lFight 2)

(define vLarryUsePhone	281)
(define vAds				263)


;phone states...

(define NO_NUMBER				-1)
(define CALLED_CHECKER 		10)
(define CALLED_NORTH_BEACH 20)
(define CALLED_TRAMPS		30)
(define CALLED_GREEN			40)
(define LONG_DIST 			50)
(define HANG_UP				60)

(local
	scaredBlonde
	scaredRed
	numberDialed
	validNumber
	onPhone
	fighting
	[string 500]
)

(instance rm280 of LLRoom
	(properties
		picture		280
		west			270
		lookStr		"This end of the lobby is distinguished only by
			a bank of pay telephones against the far wall."
	)

	(method (init)
		(ego
			init:,
			normalize:,
		)

		(switch prevRoomNum
			(west
				(if (and (> blondeX 330)(> redHeadX 330))
					(blonde
						init:,
						view:				vTicketAgent1,
						setLoop:			lFight,				;2
						posn: 			38 84,
						setCycle:		Forward,
						cycleSpeed:		6,
						moveSpeed:		10,
						setScript:		sFight,
					)
					(redHead
						init,
						hide:,
					)
				else
					(blonde
						init,
						posn: 			(- blondeX 319) 82,
						setLoop: 		blondeLoop,
						setScript:		sBlonde,
					)
					(redHead
						init:,
						posn: 			(- redHeadX 319) 82,
						setLoop:			redHeadLoop,
						setScript: 		sRedHead,
					)
				)

				(= style SCROLLLEFT)
			)

			(else
				(blonde
					init,
					x: 				(Random 8 22),
					y: 				82,
					setLoop:   		4,
					setScript 		sBlonde,
				)
				(redHead
					init,
					x: 				(Random 45 68),
					y:					82,
					setLoop:   		4,
					setScript 		sRedHead,
				)
				(ego
					posn: 			16 160,
					edgeHit:			0
				)
			)
		)

		(super init:)

		(self addObstacle:
			((Polygon new:)
				type: PBarredAccess,
				init:	0 0 319 0 319 189 0 189 0 187 316 187 315 161 264 112 124 112
						120 117 83 117 68 112 0 112,
				yourself
			)
			((Polygon new:)
				type: PBarredAccess,
				init:	216 127 218 122 230 119 255 119 263 119 268 124 267 132
						225 132,
				yourself
			)
			((Polygon new:)
				type: PBarredAccess,
				init:	9 175 27 147 181 147 198 158 230 158 236 161 234 166 214 166
						208 175,
				yourself
			)
		)


		;
		;set curRoom features...
		;

		(lostDesk 	init:)
		(largePlant init:)
		(smallPlant init:)
		(post    	init:)
		(ashtray 	init:)
		(theCounter init:)
		(sofa       init:)


		(phones
			init:				,
			approachVerbs: verbLook verbDo verbUse
		)
		(phoneBook1 init:, approachVerbs: verbLook verbDo verbUse)
		(phoneBook2 init:, approachVerbs: verbLook verbDo verbUse)

		(Load SCRIPT TTDIALER)



		(switch larryLoc
			(LosAngeles
				(blonde description: 	"Buffi")
				(redHead description: 	"Muffi")
				(p1
					init:
				)
				(p2
					init:
				)
				(p3
					init:
				)
				(p4
					init:
				)
				(ph1
					init:
				)
				(ph2
					init:
				)
				(ph3
					init:
				)
				(ph4
					init:
				)
				(ad1
					loop:				8,
					cel:				0,
					init:,
				)
				(ad2
					loop:				8,
					cel:				1,
					init:,
				)
				(ad3
					loop:				8,
					cel:				2,
					init:,
				)
				(ad4
					loop:				8,
					cel:				3,
					init:,
				)
			)

			(NewYork
				(blonde description: 	"Tracie")
				(redHead description: 	"Stacie")

			  	(thePhone         ;far left
					init,
					x 					155,
					y 					80,
					heading 			0,
					nsLeft 			147,
					nsTop 			70,
					nsBottom 		91,
					nsRight 			163,
					approachX:		138,
					approachY:		113,
				)
				(phoneHandle
					init,
					posn: 			152 85,
				)
				(p2
					init:
				)
				(p3
					init:
				)
				(p4
					init:
				)
				(ph2
					init:
				)
				(ph3
					init:
				)
				(ph4
					init:
				)
				(ad1
					loop:				9,
					cel:				0,
					init:,
				)
				(ad2
					loop:				9,
					cel:				1,
					init:,
				)
				(ad3
					loop:				9,
					cel:				2,
					init:,
				)
				(ad4
					loop:				9,
					cel:				3,
					init:,
				)

			)

			(AtlanticCity
				(blonde description: 	"Cherri")
				(redHead description: 	"Barri")
			  	(thePhone			;second from left
					init:,
					x					176,
					y					80,
					heading			0,
					nsLeft 			169,
					nsTop 			70,
					nsBottom 		91,
					nsRight 			183,
					approachX: 		160,
					approachY: 		113,
				)
				(phoneHandle
					init:,
					posn: 			173 85,
				)
				(p1
					init
				)
				(p3
					init
				)
				(p4
					init
				)
				(ph1
					init:
				)
				(ph3
					init:
				)
				(ph4
					init:
				)
				(ad1
					loop:				10,
					cel:				0,
					init:,
				)
				(ad2
					loop:				10,
					cel:				1,
					init:,
				)
				(ad3
					loop:				10,
					cel:				2,
					init:,
				)
				(ad4
					loop:				10,
					cel:				3,
					init:,
				)

			)
			(Miami
				(blonde description: 	"Dixie")
				(redHead description: 	"Trixie")
			  	(thePhone			;third from left
					init:,
					x 					200,
					y 					80,
					heading 			0,
					nsLeft 			192,
					nsTop 			70,
					nsBottom 		91,
					nsRight 			206,
					approachX: 		183,
					approachY:		113,
				)
				(phoneHandle
					init:,
					posn:				196 85,
				)
				(p1
					init:
				)
				(p2
					init:
				)
				(p4
					init:
				)
				(ph1
					init:
				)
				(ph2
					init:
				)
				(ph4
					init:
				)
				(ad1
					loop:				11,
					cel:				0,
					init:,
				)
				(ad2
					loop:				11,
					cel:				1,
					init:,
				)
				(ad3
					loop:				11,
					cel:				2,
					init:,
				)
				(ad4
					loop:				11,
					cel:				3,
					init:,
				)
			)
		)

		(addToPics doit:)
		(HandsOn)
		(Delay (Random 40 120) SECONDS self)

	);init


	(method (newRoom n)
		(blonde  dispose:)
		(redHead dispose:)
		(theTimer dispose:, delete:)
		(super newRoom n)
	)

	(method (doVerb theVerb &tmp [str 100])
		(switch theVerb
			(verbLook
				(Format @str
					"This end of the %s AeroDork Terminal lobby is distinguished
					only by a bank of pay telephones against the far wall.
					You feel AeroDork carries this \"identical terminals\"
					bit a little too far!"
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
		(if (not onPhone)
			(announcement
				number:			(Random sAnnouncement1 sAnnouncement3),
				setLoop:			1,
				flags:			TRUE,
				play:
			)
		)
		(Delay (Random 20 40) REALSECONDS self)
	)


	(method (notify what)

		;552-4668 Checker Limo Co.				New York
		;553-4468 Tramp Limo Service			Atlantic City
		;554-8544 North Beach Limo Service 	Miami
		;554-3627 Doc's							Miami
		;554-1272 Just Green Cards				Miami

		(= numberDialed what)	;save for later

		(switch what
			(4668
				(cond
					((!= larryLoc NewYork)
						(curRoom setScript sPhone NULL LONG_DIST)
					)
					((not (Btst fCalledLimo))
						(= validNumber TRUE)
						(curRoom setScript sPhone NULL CALLED_CHECKER)
					)
					(else
						(TPrint "Darn. Their line is busy.")
						(curRoom setScript sPhone NULL HANG_UP)
					)
				)
			)
			(4468
				(cond
					((!= larryLoc AtlanticCity)
						(curRoom setScript sPhone NULL LONG_DIST)
					)
					((not (Btst fCalledLimo))
						(= validNumber TRUE)
						(curRoom setScript sPhone NULL CALLED_TRAMPS)
					)
					(else
						(TPrint "Darn. Their line is busy.")
						(curRoom setScript sPhone NULL HANG_UP)
					)
				)
			)
			(8544	;MIAMI LIMO  two quarters possible!

				(cond
					((!= larryLoc Miami)
						(curRoom setScript sPhone NULL LONG_DIST)
					)
					((and (not (Btst fBeenToTown))
						 	(not (Btst fCalledLimo)))
						(= validNumber TRUE)
						(curRoom setScript sPhone NULL CALLED_NORTH_BEACH)
					)
					(else
						(TPrint "Darn. Their line is busy.")
						(curRoom setScript sPhone NULL HANG_UP)
					)
				)
			)
			(1272
				(cond
					((!= larryLoc Miami)
						(curRoom setScript sPhone NULL LONG_DIST)
					)
					((not (Btst fCalledGreenCard))
						(= validNumber TRUE)
						(curRoom setScript sPhone NULL CALLED_GREEN)
					)
					(else
						(TPrint "Darn. Their line is busy.")
						(curRoom setScript sPhone NULL HANG_UP)
					)
				)
			)
			(3627
				(cond
					((== larryLoc Miami)
						(TPrint "Darn. Their line is busy.")
						(curRoom setScript sPhone NULL HANG_UP)
					)
					(else
						(TPrint "You presume you must have dialed a wrong number.")
						(curRoom setScript sPhone NULL HANG_UP)
					)
				)
			)
			(NO_NUMBER
				(TPrint "You change your mind and hang up the phone.")
				(curRoom setScript sPhone NULL HANG_UP)
			)
			(else
				(TPrint "You presume you must have dialed a wrong number.")
				(curRoom setScript sPhone NULL HANG_UP)
			)
		)
	);notify


	(method (doit)
		(super doit:)

		(if (and
					(ego mover?)
					(== (ego view?) 550)
					(< (ego loop?) 8))

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

			((== (ego edgeHit?) WEST)
				(= redHeadX	 	(redHead x?))
				(= redHeadLoop (redHead loop?))
				(= blondeX 		(blonde x?))
				(= blondeLoop 	(blonde loop?))
			)

			((ego inRect counterRect)
				(if (and (not fighting)
							(not scaredBlonde))
					(= scaredBlonde TRUE)
					(blonde  setScript: sBlonde NULL -100)
				)
				(if (and (not fighting)
							(not scaredRed))
					(= scaredRed TRUE)
					(redHead setScript: sRedHead NULL -100)
				)
			)
		)

	)
)



(instance lostDesk of Feature
	(properties
		x 					300
		y 					91
		z 					0
		heading 			0
		nsLeft 			282
		nsTop 			55
		nsBottom 		128
		nsRight 			319
 		description 	"the lost and found department"
		sightAngle 		40
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbLook
				(TPrint "This was once AeroDork's proud department of lost
					luggage mishandling.
					Since those big cutbacks, it's never been the same!")
			)
			(verbDo
				(TPrint "You have no lost luggage to claim.
					Of course, if you did it wouldn't matter anyway,
					as there are no workers here to help you.")
			)
			(else
				(super doVerb: theVerb invItem &rest)
			)
		)
	)
)
(instance largePlant of Feature
	(properties
		y              1
		nsLeft 			76
		nsTop 			72
		nsBottom 		113
		nsRight 			123
 		description 	"the plant"
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbLook
				(TPrint "AeroDork has spared no expense to decorate
					this fabulous gateway."
				)
			)
			(else
				(super doVerb: theVerb invItem &rest)
			)
		)
	)

)
(instance smallPlant of Feature
	(properties
		y              1
		nsLeft 			256
		nsTop 			167
		nsBottom 		189
		nsRight 			319
 		description 	"the plant"
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbLook
				(TPrint "AeroDork has spared no expense to decorate
					this fabulous gateway."
				)
			)
			(else
				(super doVerb: theVerb invItem &rest)
			)
		)
	)

)
(instance post of Feature
	(properties
		y              1
		nsLeft 			225
		nsTop 			0
		nsBottom 		129
		nsRight			258
		description    "the post"
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbLook
				(TPrint "Why would such a modern structure require
					a post so near a corner?")
			)
			(verbDo
				(TPrint "Feeling that familiar sense of \"adventure game
					frustration,\" you long to bang your head against that post
					in the corner, but refuse to give up!")
			)
			(verbTalk
				(TPrint "\"You know, I once had a girl friend that
					reminds me of talking to you,\" you say to the post.")
			)
			(else
				(super doVerb: theVerb invItem &rest)
			)
		)
	)
)

(instance ashtray of Feature
	(properties
		x 					216
		y 					150
		z 					0
		heading 			0
		nsLeft 			207
		nsTop 			136
		nsBottom 		164
		nsRight 			226
 		description 	"the ashtray"
		sightAngle 		90
	)

	(method (doVerb theVerb invItem &tmp [str 30])
		(switch theVerb
			(verbLook
				(TPrint "You appreciate the historical accuracy of
						an ashtray simulator in public places."
				)
			)
			(verbDo
				(if (not (Random 0 1))
					(TPrint "What did you expect?  A disco admission card?")
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


(instance theCounter of Feature
	(properties
		x 					43
		y 					65
		z 					0
		heading 			0
		nsLeft 			0
		nsTop 			41
		nsBottom 		89
		nsRight 			87
		sightAngle 		90
		description    "the counter"
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbLook
				(Format @string "Once upon a time this ticket counter
					sold tickets.
					Now it provides a resting place for the last
					remaining AeroDork employees, %s and %s."
					(blonde description?) (redHead description?)
				)
				(TPrint @string)
			)
			(verbDo
				(Format @string "A few days ago, you could have bought a ticket
					here. But now %s and %s have nothing to do
					but stand around."
					(blonde description?) (redHead description?)
				)
				(TPrint @string)
				(TPrint "(So really, not that much has changed!)" LOW)
			)
			(verbTalk
				(TPrint "You must have just missed them.
					Perhaps you should click on one of the girls,
					instead of on the ticket counter!")
			)
			(else
				(super doVerb: theVerb invItem &rest)
			)
		)
	)
)

(instance sofa of Feature
	(properties
		x 					113
		y 					153
		z 					0
		heading 			0
		nsLeft 			25
		nsTop 			127
		nsBottom 		180
		nsRight 			202
 		description 	"the sofa"
		sightAngle 		40
	)
	(method (doVerb theVerb invItem)
		(switch	theVerb
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

(instance blonde of Actor
	(properties
		view			   vTicketAgent2 ;277
		sightAngle     40
		illegalBits:   0
		signal		   ignrAct
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
		view			   vTicketAgent1 ;276
		sightAngle     90
		illegalBits:   0
		signal		   ignrAct
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


(instance sRedHead of Script
	(method (changeState newState &tmp newX oldX)
		(switchto (= state newState)
			(
				(= oldX (redHead x?))
				(if register
					(= newX register)						; forcing the position
				else
					(switch (Random 1 2)
						(1
							(= newX (Random 8 65))
						)
						(2
							(= newX (Random -100 -12))
						)
					)
				)

				(if (and (not register)
				      (or
							(< newX (blonde x?) oldX)		; redHead in the way
							(< oldX (blonde x?) newX)))
					(= cycles 1)
				else
					(redHead
						setLoop:       RELEASE,
						setCycle:      Forward,
						setMotion:		MoveTo  newX  82 self,
					)
				)
			)

			(
				(redHead
					setLoop:       4,
					setCycle: 		FALSE,
					setMotion:		FALSE,
				)
				(= seconds (Random 3 7))
			)

			(
				(= register 0)
				(= scaredRed FALSE)
				(self init:)
			)
		)
	)
)

(instance sBlonde of Script
	(method (changeState newState &tmp newX oldX)
		(switchto (= state newState)
			(
				(= oldX (blonde x?))
				(if register
					(= newX register)						; forcing the position
				else
					(switch (Random 1 2)
						(1
							(= newX (Random 8 65))
						)
						(2
							(= newX (Random -100 -12))
						)
					)
				)

				(if (and (not register)
				       (or
						   (< newX (redHead x?) oldX)	; redHead in the way
						   (< oldX (redHead x?) newX)))
					(= cycles 1)
				else
					(blonde
						setLoop:       RELEASE,
						setCycle:      Forward,
						setMotion:		MoveTo  newX  82 self,
					)
				)
			)

			(
				(blonde
					setLoop:       3,
					setCycle: 		FALSE,
					setMotion:     FALSE,
				)
				(= seconds (Random 3 7))
			)

			(
				(= register 0)
				(= scaredBlonde FALSE)
				(self init:)
			)
		)
	)
)

(instance thePhone of Feature
	(properties
 		description 	"the pay phone"
		sightAngle 		40
	)

	(method (init)
		(self
			approachVerbs: verbUse verbDo verbTalk	verbLook
		)
		(super init:)
	)

	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbLook
				(TPrint "Finally, a working telephone!")
			)
			(verbDo
				(if (ego has: iQuarter)
					(TPrint "You insert a quarter into the only working pay
						telephone.")
					(curRoom setScript sPhone)
				else
					(TPrint "These telephones require a quarter.
						If you had a quarter, you could just stick it in the
						telephone and it would work."
					)
				)
			)
		   (verbUse
				(if (== invItem iQuarter)
					(curRoom setScript sPhone)
				else
					(TPrint "These telephones only accept quarters.")
				)
			)
			(verbTalk
				(TPrint "These telephones require a quarter.
					If you had a quarter, you could just stick it in the
					telephone and it would work."
				)
			)
			(else
				(super doVerb: theVerb invItem &rest)
			)
		)
	)
)


(instance phoneHandle of View
	(properties
		view				vRoom280
		loop				0
		cel				1
		priority       4
 		description 	"the pay phone"
		signal		   (| fixPriOn ignrAct)
	)
	(method (init)
		(self
			approachVerbs: verbUse verbDo verbTalk	verbLook,
			approachX:		(thePhone approachX?),
			approachY:		(thePhone approachY?)
		)
		(super init:)
	)
	(method (doVerb)
		(thePhone doVerb: &rest)
	)
)

(instance p1 of Feature
	(properties
		x 153
		y 80
		nsLeft 145
		nsTop 70
		nsBottom 90
		nsRight 162
		description    "the phone"
		sightAngle 40
	)
	(method (init)
		(self approachVerbs: verbLook verbDo verbUse)
		(super init:)
	)
	(method (doVerb)
		(p4 doVerb &rest)
	)
)

(instance p2 of Feature
	(properties
		x 175
		y 80
		nsLeft 167
		nsTop 70
		nsBottom 90
		nsRight 183
		description    "the phone"
		sightAngle 40
	)
	(method (init)
		(self approachVerbs: verbLook verbDo verbUse)
		(super init:)
	)
	(method (doVerb)
		(p4 doVerb &rest)
	)
)

(instance p3 of Feature
	(properties
		x 199
		y 80
		nsLeft 193
		nsTop 70
		nsBottom 91
		nsRight 206
		description    "the phone"
		sightAngle 40
	)
	(method (init)
		(self approachVerbs: verbLook verbDo verbUse)
		(super init:)
	)
	(method (doVerb)
		(p4 doVerb &rest)
	)
)

(instance p4 of Feature
	(properties
		x 221
		y 80
		nsLeft 214
		nsTop 70
		nsBottom 91
		nsRight 228
		description    "the phone"
		sightAngle 40
	)
	(method (init)
		(self approachVerbs: verbLook verbDo verbUse)
		(super init:)
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbLook
				(TPrint "This pay phone is broken.")
			)
			(verbDo
				(TPrint "You can't dial this telephone.  It's broken.")
			)
		   (verbUse
				(if (== invItem iQuarter)
					(TPrint "Save your quarter. This pay phone is broken.")
				else
					(TPrint "These telephones only accept quarters.")
				)
			)
			(else
				(super doVerb: theVerb invItem &rest)
			)
		)
	)
)

(instance ph1 of PicView
	(properties
		view			280
		loop			0
		cel			1
		x				152
		y				85
	)
	(method (init)
		(self approachVerbs: verbLook verbDo verbUse)
		(super init:)
	)
	(method (doVerb)
		(p4 doVerb &rest)
	)
)

(instance ph2 of PicView
	(properties
		view			280
		loop			0
		cel			1
		x				173
		y				85
	)
	(method (init)
		(self approachVerbs: verbLook verbDo verbUse)
		(super init:)
	)
	(method (doVerb)
		(p4 doVerb &rest)
	)
)

(instance ph3 of PicView
	(properties
		view			280
		loop			0
		cel			1
		x				196
		y				85
	)
	(method (init)
		(self approachVerbs: verbLook verbDo verbUse)
		(super init:)
	)
	(method (doVerb)
		(p4 doVerb &rest)
	)
)

(instance ph4 of PicView
	(properties
		view			280
		loop			0
		cel			1
		x				218
		y				85
	)
	(method (init)
		(self approachVerbs: verbLook verbDo verbUse)
		(super init:)
	)
	(method (doVerb)
		(p4 doVerb &rest)
	)
)

(instance ad1 of PicView
	(properties
		view			   vAds 		;263
		x					21
		y					9
		z 					0
		heading 			0
		nsLeft 			0
		nsTop 			0
		nsBottom 		14
		nsRight 			47
		sightAngle 		90
		priority       4
 		description    "the sign"
		signal		   (| fixedLoop fixedCel fixPriOn ignrHrz ignrAct)
	)
	(method (doVerb theVerb invItem)
		(switch theVerb

			(verbLook
				(switch larryLoc
					(LosAngeles					;same as ad 4 in room 270
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
					(NewYork						;same as ad 4 in room 270
						(TPrint "The NYC Ballet Company's 2015th production of
							\"Sleeping Beauty\" opens August 15th.
							The NYC Times called it,
							\"A classic... never gets all that boring,
							even though you've seen it several hundred times.\"
							Opening September 20:
							the 2016th production of \"Sleeping Beauty.\"
							Call The Ticketster for details.")
					)
					(AtlanticCity				;same as ad 4 in room 270
						(TPrint "Gambleholic's Anonymous.
							Kick the gambling habit!
							We'll lay you 2 to 1 we can clean up even
							the worst gambling problem.
							Meetings held on best 2 out of 3 Thursdays each month.
							Ex-major league baseball players admitted FREE!")
					)
					(Miami						;same as ad 4 in room 270
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

(instance ad2 of PicView
	(properties
		view			   vAds 		;263
		x					98
		y					9
		z 					0
		heading 			0
		nsLeft 			62
		nsTop 			0
		nsBottom 		15
		nsRight 			134
 		description 	"the sign"
		sightAngle 		90
		priority       4
		signal		   (| fixedCel fixedLoop fixPriOn ignrHrz ignrAct)
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbLook
				(switch larryLoc
					(LosAngeles
						(TPrint "Suffering from copy machine breakdown?
							Tired of messy toner bottles?
							Sick of paper cartridges and service calls?
							Ready to rip the guts out of your piece-of-crap,
							document-mangling Shmearox copier?
							Call us.
							We're ACME Carbon Paper.")
					)
					(NewYork
						(TPrint "We want your old jewelry.\n
							Rings, chains, earrings, brooches.\n
							No Money Paid.\n
							\n
							\"Stick 'em Up Pawn Shop\"\n
							1525 Park Avenue West"
							#mode 	teJustCenter
						)
					)
					(AtlanticCity
						(TPrint "Calling all married men!
							Entertain women discreetly and privately in
							our new \"Stayfree Bachelor Pads.\"
							Exclusive panel slides into place whenever
							your wife knocks on the front door.
							Vibrating beds, imitation leopard-skin rugs,
							fake fireplaces.
							Contact Escobar Enterprises Estates today!")
					)
					(Miami
						(Points ptsGotMNum pfGotMNum)
						(TPrint "Welcome to Miami.
							Rent a limo for your stay.
							North Beach Limousine Rentals.
							Cheap.
							No client too old!
							554-8544")
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
		x					183
		y					11
		z 					0
		heading 			0
		nsLeft 			148
		nsTop 			0
		nsBottom 		16
		nsRight 			221
		sightAngle 		90
		priority       4
 		description    "the sign"
		signal		   (| fixedLoop fixedCel fixPriOn ignrHrz ignrAct)
	)
	(method (doVerb theVerb invItem)
		(switch theVerb

			(verbLook
				(switch larryLoc
					(LosAngeles
						(TPrint "When the sharpest image counts,
							count on Canonan camcorders.
							The official camcorder of the Amateur Cinematographer's
							Committee on Los Angeles Police Conduct.
							\"I love it!
							I'm gonna get one when I get these bandages off!\"
							says a\n
							Mr. R. King of Los Angeles, California."
						)
					)
					(NewYork
						(TPrint "Dr. Seymour Weiners of the Weiners Clinic says,
							\"Chronic fatigue? Prostate problems?  Hemorrhoids?
							Kidney stones?  Liver spots? Osteoporosis?  Arthritis?
							Psoriasis?  Cataracts?  Incontinence? Allergies?
							Hernia?  Yecch! Go somewhere else, okay?\"")
					)
					(AtlanticCity
						(TPrint "New Jersey Associated Faux Health Clubs.
							Slim down the fast, easy way.
							No weight equipment, no exercycles, no treadmills.
							One aging anorexic celebrity says,
							\"Dieting doesn't give you a great body.
							But having your fat sucked out with a teeny
							little vacuum cleaner sure does!\"
							Come by today.")
					)
					(Miami
						(TPrint "This man is hanging in midair... by his gums!
							Is he crazy?
							Yes, crazy about \"Krazy Dentu-Poxy.\"
							Guaranteed to keep dentures from wiggling,
							slipping or falling out for the rest of your life.
							Bonds denture material to flesh permanently in seconds.
							Eat corn-on-the-cob, apples, rock candy, and
							furnace clinkers just like you used to.")
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
		x					314
		y					23
		z 					0
		heading 			0
		nsLeft 			295
		nsTop 			0
		nsBottom 		31
		nsRight 			319
		sightAngle 		90
		priority       4
 		description    "the sign"
		signal		   (| fixedCel fixedLoop fixPriOn ignrHrz ignrAct)
	)
	(method (doVerb theVerb invItem)
		(switch theVerb

			(verbLook
				(switch larryLoc
					(LosAngeles
						(TPrint "\t\t\tBIG-R'S\tCREDIT\n
							\n
							Need credit?
							Need a loan?
							Or just want to talk to someone who really understands
							what \"deep in debt\" really means?
							Call Roger H at Big R's Credit Carnival!
							Applications accepted over the phone.
							$10.00 non-refundable service charge might even
							be tax deductible!")
					)
					(NewYork
						(TPrint "The Mayor's Office is pleased to announce the
							25th Annual \"Why I Love New York Even Though You Take
							Your Life In Your Hands Every Time You Open Your Front
							Door and Who Can Afford To Live Here Anymore Anyway?\"
							Songwriting Contest.
							Forms available at City Hall.")
					)
					(AtlanticCity
						(TPrint "Now through December at the Conventional Center:
							\"An Evening With Bobby Vinton.\"
							New Jersey Herald says,
							\"Bobby sings all his hits,
							including `Blue Velvet,'
							`Red Roses For A Blue Lady,'
							`Blue Roses For A Red Lady,'
							`You Only Look Blue When You're Standing Next To
							All Those Red Roses,'
							and `Blue Ladies Plus Red Roses Equals Purple Velvet.'
							A must see!\"
							Call TicketMonster for details.")
					)
					(Miami
						(TPrint "Jiffy-Pace Lube-and-Battery service.
							We'll have your pacemaker in and out within 15 minutes
							or your money back!
							Free valve check.
							(We use only Catatonic State Motor Oil.)")
					)
				)
			)
			(else
				(super doVerb: theVerb invItem &rest)
			)
		)
	)
)

(instance stepSound of Sound)
(instance announcement of Sound)




(instance phoneBook1 of Feature
	(properties
		x 				169
		y 				91;141
;SRC		z 				50
		heading 		0
		nsLeft 		160
		nsTop 		86
		nsBottom 	97
		nsRight 		176
 		description "the telephone directory"
		sightAngle 	40
		onMeCheck	cBLUE
		approachX	169
		approachY	112
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbLook
				(TPrint "There are two local telephone directories hanging
					from the pay telephones.")
			)
			(verbDo
				(TPrint "Opening the plastic telephone directory covers,
					you notice someone has thoughtfully ripped out all the pages
					to prevent needless alphabetical confusion on your part!")
				(TPrint "Now how will you dial the right number?")
			)
			(else
				(super doVerb: theVerb invItem &rest)
			)
		)
	)
)
(instance phoneBook2 of Feature
	(properties
		x 				207
		y 				91;141
;SRC		z 				50
		heading 		0
		nsLeft 		200
		nsTop 		87
		nsBottom 	96
		nsRight 		214
 		description "the telephone directory"
		sightAngle 	40
		onMeCheck	cBLUE
		approachX	207
		approachY	112
	)
	(method (doVerb theVerb invItem)
		(phoneBook1 doVerb: theVerb &rest)
	)
)


(instance sFight of Script
	(method (changeState newState &tmp newX oldX)
		(switchto (= state newState)
			(
				(= fighting TRUE)
				(= cycles 2)
			)
			(
				(HandsOff)
				(blonde
					setMotion: 	MoveTo -40 84 self,
				)
			)

			(
				(blonde
					view:				277,
					posn:				-40 82,
					setScript:		sBlonde,
					ignoreActors:	TRUE,
					setLoop:			0,
				)
				(redHead
					view:				276,
					posn: 			-70 82,
					setLoop:			0,
					setScript: 		sRedHead,
					ignoreActors:	TRUE,
					show,
				)
				(HandsOn)
				(= fighting FALSE)
				(self dispose:)
			)
		)
	)
)

(instance sPhone of Script
	(method (changeState newState &tmp var1 var2 [str 80])
		(switch (= state newState)
			(0
				(HandsOff)
				(= onPhone TRUE)
				(if (not register)
					(ego
						view: 			vLarryUsePhone, ;281
						setLoop: 		0,
						setCel:			0,
						cycleSpeed:		10,
						setCycle: 		CycleTo 4 FORWARD self,
					)
				else
					(self changeState: register)
				)
			)
			(1
				(theMusic2
					number:			sPickUpPhone,
					loop:				1,
					play:				,
				)
				(phoneHandle hide:)
				(ego setCycle: EndLoop self,)
			)
			(2
				(User canInput TRUE)
				(DialPhone)
			)


			;**	make your decision here as to whether he dialed
			;**	the right limo, green card, or wrong number.
			;**	Handles Points for each call.  Handle 2 calls in Miami.
			;**	Branch to one of the `heCalled' states below.

			;----------------------------------------------------

			;**	call NYC limo


			(CALLED_CHECKER
				(Points ptsCalledNYlimo fCalledLimo)
				(Bset fLimoParked)
				(TPrint "\"Hello, Checker Limo.
					Wearya? Weardjawannago?\"" HIGH)
				(= ticks 30)
			)
			(11
				(TPrint "\"Uh, my name is Larry; Larry Laffer,\" you say,
					\"What was the question again?\"")
				(= ticks 30)
			)
			(12
				(TPrint "\"Waddaya deff? Wearya? Weardjawannago?\"" HIGH)
				(= ticks 30)
			)
			(13
				(TPrint "\"Oh, I'm at the airport. At the AeroDork Terminal.\"")
				(= ticks 30)
			)
			(14
				(TPrint "\"Weardjawannago?\"" HIGH)
				(= ticks 30)
			)
			(15
				(TPrint "\"Oh, I guess I'd like to go downtown.\"")
				(= ticks 30)
			)
			(16
				(TPrint "\"Gotcha. Mee'cha ou'side.\"" HIGH)
				(= ticks 30)
			)
			(17
				(TPrint "(And he hangs up the phone.)" LOW)
				(self changeState: HANG_UP)
			)



			;**	call Miami limo


			(CALLED_NORTH_BEACH
				(Points ptsCalledMlimo fCalledLimo)
				(Bset fLimoParked)
				(TPrint "\"Good afternoon, and welcome to lovely Miami,\"
					a pleasant voice answers the telephone.
					\"How may North Beach Limousine Service assist you today?\""
					 HIGH)
				(= ticks 30)
			)
			(21
				(TPrint "\"Hello. My name is Larry; Larry Laffer,\" you say,
					\"I'm at the AeroDork Terminal at the airport.\"")
				(= ticks 30)
			)
			(22
				(TPrint "\"Very well, Sir. And how will you be paying
					today?\"" HIGH)
				(= ticks 30)
			)
			(23
				(TPrint "\"Uh, would you take an AeroDork Gold Card?\" you say.")
				(= ticks 30)
			)
			(24
				(TPrint "\"Ahhh, but of course, sir!
					Your limousine will be outside in a few seconds!\"" HIGH)
				(TPrint "He concludes, \"And thank you for thinking of us!\""
					HIGH)
				(= ticks 30)
			)
			(25
				(TPrint "(And he hangs up the phone.)" LOW)
				(self changeState: HANG_UP)
			)



			;**	call Atlantic City limo


			(CALLED_TRAMPS
				(Points ptsCalledAClimo fCalledLimo)
				(Bset fLimoParked)
				(TPrint "\"Good day. Tramp Limousine at your service.\"" HIGH)
				(= ticks 30)
			)
			(31
				(TPrint "\"Good day, Mr. Tramp,\" you presume, mistakenly.
					\"I'm at the airport, and require transportation to the city.\"")
				(= ticks 30)
			)
			(32
				(TPrint "\"Yes sir!\" he replies,
					\"I'll send one of my best girls right over!\"" HIGH)
				(= ticks 30)
			)
			(33
				(TPrint "(And he hangs up the phone.)" LOW)
				(self changeState: HANG_UP)
			)



			;**	call Green Cards


			(CALLED_GREEN
				(Points ptsCalledGreenCard fCalledGreenCard)	;**	not a bug, ok?
;  			(Bset fLimoParked)
				(TPrint "\"`JesGreeenCars.'
					Si habla Espanol y Eeengleesh.
					Haf ju `lost jour carrrd?'\"")
				(= ticks 30)
			)
			(41
				(TPrint "\"Hello, I'm looking for a limousine to take me from the
					airport into town, I guess.\"")
				(= ticks 30)
			)
			(42
				(TPrint "\"Ho-kay. Aeropuerto? Ho-kay.
					Wee deeleefer jur carrrd!
					On dee trash can.
 					Outside de aeropuerto.
					Ju leeef a-ten-dousand pesos outside de aeropuerto.
					On dee trash can.
					No problemo!\"")
				(= ticks 30)
			)
			(43
				(TPrint "(And he hangs up the phone.)" LOW)
				(self changeState: HANG_UP)
			)


			(LONG_DIST
				(TPrint "\"Please deposit seventy-five cents for the
					next three minutes.\"")
				(= ticks 30)
			)
			(51
				(Format @str "\"Since you don't have three quarters, you
					decide not to call %s.\""
					(switch numberDialed
						(3627	{Miami})
						(4668 {New York City})
						(4468 {Atlantic City})
						(8544	{Miami})
						(1272 {Miami})
					)
				)
				(TPrint @str LOW)
				(= ticks 30)
			)

			(52
				(self changeState: HANG_UP)
			)

			;**	all sub-scripts end here


			(HANG_UP
				;**	play dial tone again for a few seconds
				(= ticks 10)
			)
			(61
				(ego setCycle: CycleTo 4 BACKWARD self)
			)
			(62
				(phoneHandle show)
				(ego setCycle: BegLoop self)
			)
			(63
				(theMusic2
					number:			sHangUpPhone,
					loop:				1,
					play:				,
				)
				(if (and validNumber
							(not (-- quarters)))
					(= quarters DONE)
					(ego put: iQuarter)
				)
				(ego
					setLoop:			3,
					normalize:
				)
				(= validNumber FALSE)
				(= register 0)
				(= onPhone FALSE)
				(HandsOn)
				(self dispose:)
			)
		)
	)
)


(instance phones of PicView
	(properties
		view				vRoom280
		loop				0
		x					185
		y					1
		z					-100
		priority			0
 		description 	"the pay phones"
		approachX		180
		approachY		112
		signal		   (| fixPriOn ignrAct)
	)

	(method (onMe)
		(return FALSE)
	)
)



