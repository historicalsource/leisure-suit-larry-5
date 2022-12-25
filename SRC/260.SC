;**
;**	Logics for Room 260 -- Airport lobby, west
;**
;**	LSL5:  Passionate Patti Does A Little Undercover Work
;**				Copyright 1991
;**				by Al Lowe
;**
;**	Almost finished by C. Escobar
;**
;**	Last Update:  Monday, September 16, 1991 
;**


;prefix
;
;	551 LosAngeles
;	552 NewYork
;	553 AtlanticCity
;	554 Miami
;	555 Gumbo


(script#		260)

(public
	rm260		0
)


;misc sounds

(define	sAnnouncement1			273)
(define	sAnnouncement2			274)
(define	sAnnouncement3			275)

(define	sfxAirportIntFX		261)
(define	sfxSlots					262)
(define	sfxWinQuarter			263)

;**	(define	chargerPlugged
;**		(== ((Inventory at: iBatteryCharger) owner?) curRoom)
;**	)

(define vLarryRecharger 	 161)
(define vBendingOver    	 162)

(define vAds 					 263)
(define vAtlanticCity   	 264)
(define vCigaretteMachine   265)
(define vLarryPullingHandle 261)

(local
	chargeInOutlet
	whichSlot
)


(instance rm260 of LLRoom
	(properties
		picture		260
		east			270
		lookStr		"You are in the west wing of the lavish AeroDork Airline
						terminal building."
	)

	(method (init)
		(ego
			init:,
			illegalBits: $8000,		;needed to keep from going off bottom of screen!
			normalize:
		)
		(switch prevRoomNum
			(east
      		(= style SCROLLRIGHT)
			)
			(else
				(ego
					posn:			292 134,
					setHeading: 270,
					edgeHit:		0
				)
			)
		)
		(super init:)

		;camera
		(LoadMany VIEW
			vLarryRecharger 		;161
			vBendingOver    		;162
			vAds 						;263
			vAtlanticCity	 		;264
			vLarryShocked	 		;560
			vCigaretteMachine 	;265
		)
		(switch larryLoc
			(LosAngeles
				(ad1
					loop:				0,
					cel:				0,
					init:,
				)
				(ad2
					loop:				0,
					cel:				1,
					init:,
				)
				(ad3
					loop:				0,
					cel:				2,
					init:,
				)
				(ad4
					loop:				0,
					cel:				3,
					init:,
				)
			)
			(NewYork
				(ad1
					loop:				1,
					cel:				0,
					init:,
				)
				(ad2
					loop:				1,
					cel:				1,
					init:,
				)
				(ad3
					loop:				1,
					cel:				2,
					init:,
				)
				(ad4
					loop:				1,
					cel:				3,
					init:,
				)
			)
			(AtlanticCity
				(slots 	  	init:)
				(slot1		init:, approachVerbs: verbDo)
				(slot2		init:, approachVerbs: verbDo)
				(slot3		init:, approachVerbs: verbDo)
				(slot4		init:, approachVerbs: verbDo)
				(handle1
					init:				,
					approachVerbs: verbDo,
					approachX:		(slot1 approachX?),
					approachY:		(slot1 approachY?)
				)
				(handle2
					init:				,
					approachVerbs: verbDo,
					approachX:		(slot2 approachX?),
					approachY:		(slot2 approachY?)
				)
				(handle3
					init:				,
					approachVerbs: verbDo,
					approachX:		(slot3 approachX?),
					approachY:		(slot3 approachY?)
				)
				(handle4
					init:				,
					approachVerbs: verbDo,
					approachX:		(slot4 approachX?),
					approachY:		(slot4 approachY?)
				)
				(ad1
					loop:				2,
					cel:				0,
					init:
				)
				(ad2
					loop:				2,
					cel:				1,
					init:
				)
				(ad3
					loop:				2,
					cel:				2,
					init:
				)
				(ad4
					loop:				2,
					cel:				3,
					init:
				)
			)
			(Miami
		      (cigaretteMachine
					init:,
					approachVerbs: verbDo
				)
				(ad1
					loop:				3,
					cel:				0,
					init:,
				)
				(ad2
					loop:				3,
					cel:				1,
					init:,
				)
				(ad3
					loop:				3,
					cel:				2,
					init:,
				)
				(ad4
					loop:				3,
					cel:				3,
					init:,
				)
			)
		)

		(addToPics doit:)

		(switch larryLoc
			(Miami
				(self addObstacle:
					;this one goes around the cigarette machine...
					((Polygon new:)
						type: PBarredAccess,
						init:	0 0 319 0 319 112 306 112 303 119 280 122 267 120 259 113
								55 114 50 119 97 119 103 126 98 131 66 131 51 149 19 149
								5 164 5 187 319 187 319 189 0 189,
						yourself
					)
				)
			)
			(AtlanticCity
				(self addObstacle:
					;this one goes around the slot machines...
					((Polygon new:)
						type: PBarredAccess,
						init:	0 0 319 0 319 112 306 112 303 119 280 122 267 120 253 113
								223 113 223 120 100 123 94 121 85 119 82 114 53 113 4 162
								7 187 319 187 319 189 0 189,
						yourself
					)
;SRC					((Polygon new:)
;SRC						type: PBarredAccess,
;SRC						init:	0 0 319 0 319 112 306 112 303 119 280 122 267 120 253 113
;SRC								223 113 228 125 106 125 94 121 85 119 82 114 53 113 4 162
;SRC								7 187 319 187 319 189 0 189,
;SRC						yourself
;SRC					)
					;this one goes around the column
					((Polygon new:)
						type: PBarredAccess,
						init:	73 119 81 119 97 123 101 130 54 130 50 125 52 119,
						yourself
					)
				)
			)
			(else
				(self addObstacle:
					((Polygon new:)
						type: PBarredAccess,
						init:	0 0 319 0 319 112 306 112 303 119 280 122 267 120 259 113
								54 113 4 164 4 187 319 187 319 189 0 189,
						yourself
					)
					;around pole
					((Polygon new:)
						type: PBarredAccess,
						init:	95 120 105 122 105 126 89 130 63 130 50 126 51 122 62 120,
						yourself
					)
				)
			)
		)
		; sofa + ashtray
		(self addObstacle:
			((Polygon new:)
				type: PBarredAccess,
				init:	108 180 128 154 279 154 301 181,
				yourself
			)
			((Polygon new:)
				type: PBarredAccess,
				init:	107 157 113 160 114 165 107 171 93 171 80 170 75 162 85 157,
				yourself
			)
		)
		;
		;init features
		;
		(ashtray 	init:)
		(chairs  	init:)
		(post   		init:)
		(plant      init:)
		(plantSouth init:)
		(door       init:)
		(outlet
			init:,
			approachVerbs: verbUse verbDo
		)
		(HandsOn)
		(Delay (Random 40 120) SECONDS self)
	);init

	(method (doVerb theVerb &tmp [str 100])
		(switch theVerb
			(verbLook
				(Format @str
					"You are in the west wing of the lavish AeroDork Airline
					terminal building.
					It's easy to tell when you're traveling via AeroDork--you're
					bored!"
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

	(method (newRoom n)
		(theTimer dispose:, delete:)
		(super newRoom n)
	)

	(method (cue)
		(announcement
			number:			(Random sAnnouncement1 sAnnouncement3),
			flags:			TRUE,
			setLoop:			1,
			play:,
		)
		(Delay (Random 20 40) REALSECONDS self)
	)

	(method (doit)
		(super doit:)
		(if (and	(ego mover?)
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
	)
)

(instance cigaretteMachine of PicView
	(properties
		view			   vCigaretteMachine  ;265
		loop			   0
		x				   39
		y				   142
		priority       9
		signal		   (| fixPriOn ignrAct)
 		description    "the cigarette machine"
		approachX		64
		approachY		142
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbLook
				(TPrint "How quaint!
					A public cigarette vending machine.
					You thought those had all been eliminated by
					anti-smoking activists."
				)
			)
			(verbDo
				(HandsOff)
				(curRoom setScript: sGetChange)
			)
			(else
				(super doVerb: theVerb invItem &rest)
			)
		)
	)
)

(instance chairs of Feature
	(properties
		x 					203
		y 					152
		z 					0
		heading 			0
		nsLeft 			117
		nsTop 			131
		nsBottom 		173
		nsRight 			289
 		description 	"the chairs"
		sightAngle 		40
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

(instance post of Feature
	(properties
		x 					75
		y 					63
		z 					0
		heading 			0
		nsLeft 			59
		nsTop 			0
		nsBottom 		126
		nsRight 			91
 		description 	"the column"
		sightAngle 		40
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
				(Say ego "\"You know, I once had a girl friend that
					reminds me of talking to you,\" you say to the post."
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
		x 					285
		y 					90
		z 					0
		heading 			0
		nsLeft 			259
		nsTop 			70
		nsBottom 		111
		nsRight			312
 		description 	"the plant"
		sightAngle 		40
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

(instance door of Feature
	(properties
		x 					227
		y 					75
		z 					0
		heading 			0
		nsLeft 			205
		nsTop 			43
		nsBottom 		108
		nsRight 			249
 		description 	"the door"
		sightAngle     40
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbLook
				(TPrint "This door leads to a part of the game that
					Al never got around to writing.")
			)
			(verbDo
				(TPrint "This door not only is locked, it doesn't
					even have a knob!")
			)
			(else
				(super doVerb: theVerb invItem &rest)
			)
		)
	)
)

(instance sGetChange of Script
	(method (changeState newState &tmp var1 var2)
		(switchto (= state newState)
			(
				;handsOff set in doVerb of cigarette machine
				(Load VIEW vLarryRecharger)			;161
				(ego setHeading: 270 self)
			)
			(
				(= ticks 20)  ;problem with setHeading
			)
			(
				(ego
					view:    		vLarryRecharger,
					setLoop:			0,
					setCel:			0,
					cycleSpeed:    8,
					setCycle:      CycleTo 2 FORWARD self,
				)
			)
			(
				(= ticks 30)
			)
			(
				(ego
					setCycle: 		BegLoop self,
				)
			)
			(
				(ego
					setLoop:			1,
					normalize,
				)
            (= cycles 2)
         )

         (
				(TPrint "You feel around in the cigarette vending machine's
					change slot and find..." HIGH
				)
				(if (and
						 (not (Btst fTookQuarters260))
						 (not (Btst fBeenInMiami))			;safe coding
						 (not (ego has: iQuarter))
					 )
					(ego get: iQuarter)
					(Points ptsCigaretteMachineQuarters fTookQuarters260) 			;long enough Al?!
					(TPrint "...a pair of quarters!" HIGH)
					(= quarters 2) ;need two -- one for greenCard one for limo
				else
					(TPrint "...nothing.
						Someone must have taken whatever change was left here!"
                  HIGH
					)
				)
            (= cycles 2)
         )

         (
				(HandsOn)
				(self dispose:)
			)
		)
	)
)

(instance outlet of Feature
	(properties
		x 					5
		y 					131;161
;SRC		z 					30
		heading 			0
		nsLeft 			0
		nsTop 			118
		nsBottom 		143
		nsRight 			16
 		description 	"the outlet"
		sightAngle		40
		approachX      32
		approachY      145
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbDo
				; walk over; stick your finger in the socket
				; get a view of electrocution
				; but don't kill him
				(PlugFinger)
			)
			(verbUse
				(if (== invItem iBatteryCharger)
					(PlugCharger)
				else
					(TPrint "This outlet would be perfect for plugging in
						something electrical.")
				)
			)
			(verbLook
				(if chargeInOutlet
					(TPrint "Your battery pack is nearly recharged.")
				else
					(TPrint "There is an electrical outlet on the wall.")
				)
			)
			(else
				(super doVerb: theVerb invItem &rest)
			)
		)
	)
)

(instance plantSouth of Feature
	(properties
		x 					34
		y 					173
		z 					0
		heading 			0
		nsLeft 			0
		nsTop 			158
		nsBottom 		189
		nsRight 			69
 		description 	"the plant"
		sightAngle 		40
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

(instance camera of View
	(properties
		view				vLarryRecharger  ;161
		loop				4
		x					27
		y					146
		illegalBits 	0
		signal			ignrAct
	)
)

(instance ad1 of PicView
	(properties
		view			   vAds  		;263
		x					11
		y					22
 		description    "the sign"
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbLook
				(switch larryLoc
					(LosAngeles
						(TPrint "Injured in an Automobile Accident?
							Hurt in a Work-Related Accident?
							Been pulled over by the L.A.P.D.?
							Let our personal injury lawyers work for YOU!
							We collect only if you win your case.
							Free consultation.
							Ambulance Chasers, Inc.")
					)
					(NewYork
						(TPrint "Now in its 1st Straight Season on Broadway!
							Harold King's \"Kiss Me, My Fair Fiddler on the
							West Side of the Best Little Cabaret in Oklahoma!\"
							Sold out through April, 2003.
							Vincent Couldbe says,
							\"Even better than last year's
							`Hello, Annie's Greasy South Pacific Chorus Line!'\"
							Contact TicketMeister.")
					)
					(AtlanticCity
						(TPrint "Welcome to Atlantic City,
							\"Where Dreams Come True.\"
							Remember, bet your limit... never limit your bet!")
					)
					(Miami
						(TPrint "Welcome to Miami, \"America's Friendliest City.\"
							Warning: local ordinances forbid carrying lead pipes
							or tire irons in a concealed manner.
							Just keep them visible. Have a wonderful stay!")
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
		view				vAds 			;263
		x					146
		y					9
 		description    "the sign"
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbLook
				(switch larryLoc
					(LosAngeles
						(TPrint "For elegant dining in an informal atmosphere,
							come to Bubba's-On-The-Bay.
							Exquisite nouvelle cuisine served in the classic
							Deep South style.
							Chicken-fried Medallions of Veal,
							Blackened Carpaccio with Capers and Oleo,
							Jambalaya Sorbet.
							Check out our new puzzle placemats!")
					)
					(NewYork
						(TPrint "Welcome to New York City!
							Home of the San Francisco Giants, New Jersey Jets,
							and Los Angeles Dodgers!")
					)
					(AtlanticCity
						(TPrint "Broke?  Down on your luck?
							Stupidly lost all your money gambling?
							Looking for some kind person to give you a break?
							Don't hang out around here and we
							won't hang out in your cardboard box.")
					)
					(Miami
						(TPrint "Irv's Pre-Passing Extended Care Home.
							Finest nursing home arrangements in the country.
							TV night every other Sunday. Bingo.
							Guaranteed digestible food.
							Irv says, \"If you're waiting to pass away,
							stay with us today!\"")
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
		view				vAds 			;263
		x					235
		y					10
 		description    "the sign"
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbLook
				(switch larryLoc
					(LosAngeles
						(TPrint "Take the Yawniversal Studios Tour!
							Get bit by the Real Shark from \"Maws.\"
							Get crushed in the hand of the Giant Robot Ape
							from \"King Dong.\"
							Get stabbed in the abdomen at the Gates Hotel
							from \"Psicko.\"
							Camera rental and infirmary services FREE.")
					)
					(NewYork
						(Points ptsGotNYNum pfGotNYNum)
						(TPrint "From anywhere to anywhere,
							in New York City there's no limousine service
							that's precisely, totally, exactly like the
							fabulous Checker Limo Co.
							Nearly-instantaneous service from our fleet of
							radio-dispatched cars.
							Floors disinfected regularly!
							Call 552-4668, anytime.")
					)
					(AtlanticCity
						(TPrint "On your next trip, try TWAL, The Women's Airline.
							All female crew, all male flight attendants.
							Extra protection down the center of the plane,
							where women need it most.
							Remember our slogan:
							\"Fly TWAL... The Darn Thing Has Wings!\"")
					)
					(Miami
						(TPrint "Phil's Pharmaceuticals is open 24-hours
							for all your drug needs.
							53 varieties of addictive over-the-counter chemicals
							flown in fresh daily.
							Ask about our Spring Break specials.
							We do not condone illegal drug use.
							However, bulk discounts on Valium clones are
							available this month only!")
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
		x					311
		y					13
 		description    "the sign"
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbLook
				(switch larryLoc
					(LosAngeles	 				;same as ad 1 in room 270
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
					(NewYork						;same as ad 1 in room 270
						(TPrint "It's Carefree Living in the Eurasian Style
							in new Exorbitania Townhouses.
							Only 1/2 block from Central Park, Broadway,
							Madison Square Garden, Statue of Liberty, UN Building,
							World Trade Center, Automat.
							One-bedrooms start at $10,000,000.
							Security locks, no pets.
							And no vacancies to the likes of you, either, buddy!")
					)
					(AtlanticCity				;same as ad 1 in room 270
						(TPrint "Where does the Moral Majority turn to
							promote censorship and creeping Fascism?
							\"Slant,\" the Holier-Than-Thou Newsweekly.
							No liberal pandering, no pinko editorial cartoons,
							no objective journalism...
							just good ol' fashioned Fundamentalism.
							Printed on 100% recycled environmentalists.")
					)
					(Miami	  					;same as ad 1 in room 270
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

(instance ashtray of Feature
	(properties
		x 					94
		y 					149
		z 					0
		heading 			0
		nsLeft 			85
		nsTop 			133
		nsBottom 		165
		nsRight 			104
 		description 	"the ashtray"
		sightAngle 		40
	)
	(method (doVerb theVerb invItem &tmp [str 30])
		(switch theVerb
			(verbLook
				(TPrint "You appreciate the historical accuracy of
					an ashtray simulator in public places."
				)
			)
			(verbDo
				(TPrint "You want to put your butt in the ashtray?")
			)
			(else
				(super doVerb: theVerb invItem &rest)
			)
		)
	)
)

(instance stepSound of Sound)
(instance announcement of Sound)

(instance handle1 of PicView
	(properties
		view			vAtlanticCity		;264
		loop			1
		cel			0
		x				122
		y				100;86
		z				14
		priority		0
		signal		fixPriOn
		description	"the slot machine"
	)
	(method (doVerb)
		(= whichSlot 1)
		(slot1 doVerb: &rest)
	)
)
(instance handle2 of Prop
	(properties
		view				vAtlanticCity   ;264
		loop				1
		x					151
		y					86
		priority			11
		illegalBits    0
		signal			ignrAct
		description	"the slot machine"
	)
	(method (doVerb)
		(= whichSlot 2)
		(slot1 doVerb: &rest)
	)
)
(instance handle3 of PicView
	(properties
		view			vAtlanticCity		;264
		loop			1
		cel			0
		x				180
		y				86
		priority		6
		signal		fixPriOn
		description	"the slot machine"
	)
	(method (doVerb)
		(= whichSlot 3)
		(slot1 doVerb: &rest)
	)
)
(instance handle4 of PicView
	(properties
		view			vAtlanticCity		;264
		loop			1
		cel			0
		x				207
		y				85
		description	"the slot machine"
	)
	(method (doVerb)
		(= whichSlot 4)
		(slot1 doVerb: &rest)
	)
)
(instance slots of PicView
	(properties
		view				vAtlanticCity   ;264
		loop				0
		x					166
		y					22
		z					-100
		priority       4
 		description    "the slot machine"
		signal		   (| fixPriOn ignrAct)
	)
	(method (onMe)
		(return FALSE)
	)
)
(instance slot1 of Feature
	(properties
		x 					112
		y 					99
		z 					0
		nsLeft 			100
		nsTop 			77
		nsBottom 		121
		nsRight 			124
 		description    "the slot machine"
		sightAngle 		40
		heading			0
		approachX		117
		approachY		120
	)
	(method (doVerb theVerb invItem)
		(if (not whichSlot)
			(= whichSlot 1)
		)
		(switch theVerb
			(verbLook
				(TPrint "Slot machines!
					You haven't played the slots since \"Leisure Suit Larry 1:
					In the Land of the Lounge Lizards.\"
					Now available in an all-new, 256-color version with
					enhanced graphics, music and sound effects!!"
				)
				(= whichSlot 0)  	;SRC 9/12/91
			)
			(verbDo
				(curRoom setScript: sSlots)
			)
			(verbUse
				(if (== invItem iQuarter)
					(TPrint "You haven't played the slots since your old
						\"Land of the Lounge Lizards\" days!
						You have no desire to waste your (hard-found) money here.")
				else
					(super doVerb: theVerb invItem &rest)
				)
				(= whichSlot 0)  	;SRC 9/12/91
			)
			(else
				(super doVerb: theVerb invItem &rest)
				(= whichSlot 0)  	;SRC 9/12/91
			)
		)
	)
)
(instance slot2 of Feature			;the only working machine!
	(properties
		x 					142
		y 					98
		z 					0
		nsLeft 			128
		nsTop 			77
		nsBottom 		120
		nsRight 			156
 		description    "the slot machine"
		sightAngle 		40
		heading			0
		approachX		146
		approachY		122
	)
	(method (doVerb)
		(= whichSlot 2)
		(slot1 doVerb: &rest)
	)
)
(instance slot3 of Feature
	(properties
		x 					172
		y 					99
		z 					0
		nsLeft 			160
		nsTop 			77
		nsBottom 		121
		nsRight 			184
 		description    "the slot machine"
		sightAngle 		40
		heading			0
		approachX		176
		approachY		122
	)
	(method (doVerb)
		(= whichSlot 3)
		(slot1 doVerb: &rest)
	)
)
(instance slot4 of Feature
	(properties
		x 					199
		y 					98
		z 					0
		nsLeft 			187
		nsTop 			76
		nsBottom 		121
		nsRight 			211
 		description    "the slot machine"
		sightAngle 		40
		heading			0
		approachX		201
		approachY		122
	)
	(method (doVerb)
		(= whichSlot 4)
		(slot1 doVerb: &rest)
	)
)
(instance sSlots of Script
	(method (changeState newState &tmp var1 var2)
		(switchto (= state newState)
			(
				(HandsOff)
				(= register 0)
				(ego setLoop: 3)
				(= cycles 2)
			)
			(
				;machines all have handles!
				(ego
					view:				vLarryPullingHandle, ;261
					setLoop:       0,
					cycleSpeed:		12,
					setCel:			0,
					setCycle:      CycleTo 3 FORWARD self,
				)
			)
			(
				(if (and
						(not (Btst fPulledSlots))
						(== whichSlot 2)
					)
					(handle2
						setCel:			LASTCEL,
						cycleSpeed:		8,
						hide:,
					)
					(ego setCycle: EndLoop self)
				else
					(= ticks 90)
				)
			)
			(
				(if (and
						(not (Btst fPulledSlots))
						(== whichSlot 2)
					)
					(handle2
						show:,
						setCycle: 		BegLoop,
					)
					(theMusic2
						number:			sfxSlots,
						loop:				1,
						play:				self,
					)
				else
					(ego setCycle:	BegLoop self)
				)
			)
			(
				(cond
					((or
							(!= whichSlot 2)
							(Btst	fPulledSlots))
						(TPrint "You try to pull the handle of the slot machine
							but since there's no quarter in it, nothing happens."
							LOW
							#dispose: self
						)
					)
					((not (Btst fPulledSlots))
							(theMusic
								number:			sfxWinQuarter,
								loop:				1,
								play:				self,
							)
							(= register 1)
					)
					(else
						(= ticks 10)
					)
				)
			)
			(
				(= ticks 30)
			)
			(
				(theMusic stop:)
				(if (and register
							(not (Btst fPulledSlots)))
					(Points ptsPulledSlots fPulledSlots)
					(TPrint "Hey, all right!
						Someone left a quarter in that machine.
						Pulling the handle yields only one lonely cherry,
						but that's enough to pay you a quarter.
						You reach into the slot machine's coin bin and grab it.")
					(ego get:	iQuarter)
					(= quarters 1)				;important, leave alone!
				)
				(HandsOn)
				(ego
					setLoop:				3,
					normalize:,
				)
				(= whichSlot 0)
				(self dispose:)
			)
		)
	)
);sSlots

