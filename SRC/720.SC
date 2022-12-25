;**
;**	Logics for Room 720 -- "Tramp Casino" Video Poker
;**
;**	LSL5:  Passionate Patti Does A Little Undercover Work
;**				Copyright 1991
;**				by Al Lowe
;**
;**	Programmed by Brian K. Hughes
;**
;**	Last Update:  September 10, 1991
;**

(script#		720)

(procedure
	NewDeck
	DealCard
	TallyHand
	SortTallies
	PayBet
	DisplayStats
	CountScore
)

(public
	rm720		0
)


(define	pCasinoVideoPoker				720)
(define	vCasinoVideoPoker			720)
(define		lIncreaseBet		0)
(define		lDecreaseBet		1)
(define		lCashOut				2)
(define		lDeal					3)
(define		lHold					4)

(define	vCardDeck					722)
(define		lClubs				0)
(define		lDiamonds			1)
(define		lSpades				2)
(define		lHearts				3)

(define	sDeal						721)
(define	sButton					722)
(define	sCashOut					723)
(define	sWin						724)
(define	sBigWin					725)
(define	sLose						726)


(enum					;** hands
	hGarbage
	hPairJacks
	h2pair
	h3ofaKind
	hStraight
	hFlush
	hFullHouse
	h4ofaKind
	hStraightFlush
	hRoyalFlush
)

(enum 11
	Jack
	Queen
	King
	Ace
)

(enum
	Clubs
	Spades
	Diamonds
	Hearts
)


(local
	oldSpeed
	handsPlayed
	drop 		= [ 0 0 0 0 0 ]
	nTally 	= [ 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 ]
	sTally 	= [ 0 0 0 0 ]
	payoff 	= [ 0 1 2 3 4 5 10 20 50 -1 ]
	theHand
	winnings
	theBet
	credits
	paid
	nextCard
	holdOK
	dealOK
	wUnders
	bUnders
	cUnders
)


(instance rm720 of LLRoom
	(properties
		picture		pCasinoVideoPoker
	)

	(method (init)
		(SetupExit TRUE)
		(HandsOn)
		(theIconBar
			disable: talkIcon useIcon inventoryIcon,
			curIcon: (theIconBar at: doIcon)
		)
		(theGame setCursor: ((theIconBar curIcon?) cursor?))
		(super init:)

		; We want the speed to be constant while here
		(= oldSpeed speed)
		(= speed 6)


		(incBet	init:)
		(decBet	init:)
		(cashout init:)
		(hold1 	init:)
		(hold2 	init:)
		(hold3 	init:)
		(hold4 	init:)
		(hold5 	init:)
		(deal		init:)

		; Prepare the deck
		(deck add:
			cardD0
			cardD1
			cardD2
			cardD3
			cardD4
			cardD5
			cardD6
			cardD7
			cardD8
			cardD9
		)
		; Prepare the hand
		(hand add:
			cardH0
			cardH1
			cardH2
			cardH3
			cardH4
		)
		(= credits silvDollars)
		(= silvDollars 0)
		(if credits (= theBet 1))
		(ego put: iSilverDollar 0)
		(DisplayStats)
		(self setScript: sPlayPoker)
	)
	(method (doVerb theVerb &tmp [str 200])
		(switch theVerb
			(verbLook
				(Format @str
					"___________Video Poker Odds\n
					\n
					__Progressive Royal Flush___$%d\n
					\n
					__Straight Flush____________50 to 1\n
					__4 of a Kind_______________20 to 1\n
					__Full House________________10 to 1\n
					__Flush______________________5 to 1\n
					__Straight___________________4 to 1\n
					__3 of a Kind________________3 to 1\n
					__2 Pair_____________________2 to 1\n
					__Pair, Jacks or Better______1 to 1"
					pokerJackpot
				)
				(TPrint @str
					#title: 	{Welcome to Tramp's Casino}
					#font:	monoFont
					#at:		-1 15
					#width:	260
				)
			)
			(verbWalk
				(cashout doVerb: verbDo)
			)
			(else
				(TPrint "Click on a button instead of where you just clicked.")
			)
		)
	)
	(method (doit)
		(super doit: &rest)
		(if (GameIsRestarting)
			(DisplayStats)
		)
	)
	(method (dispose)
		(hand dispose:)
		(deck dispose:)
		(sortHand dispose:)
		(theIconBar enable:
							walkIcon
							talkIcon
							useIcon
							inventoryIcon
		)
		(= speed oldSpeed)
		(super dispose:)
	)
)


;ษอออออออออออออออออออป
;บ                   บ
;บ Procedures        บ
;บ                   บ
;ศอออออออออออออออออออผ

; Erases all cards & deals 10 new ones into the "deck" list

(procedure (NewDeck &tmp i theVal theSuit [str 4])
	; Reset our tallies
	(for 	((= i 0)) (< i 5) ((++ i))
		(= [drop i] 1)
	)
	(for 	((= i 0)) (< i 15) ((++ i))
		(= [nTally i] (* i 10))
	)
	(for 	((= i 0)) (< i 4) ((++ i))
		(= [sTally i] (* i 10))
	)

	(hand
		eachElementDo: #dispose:,
		eachElementDo: #perform clearCode
	)
	(deck eachElementDo: #perform clearCode)
	(= nextCard 0)

	; Randomly create 10 new cards for the deck
	(for	((= i 0))
			(< i 10)
			((++ i))

		(= theVal (= theSuit 0))
		(while (deck firstTrue: #perform uniqueCode theVal theSuit)
			(if debugging
				(Format @str "Card #%d" i)
				(= theVal
					(PrintD @str
						#new
						#button	{2}		2
						#button	{3}		3
						#button	{4}		4
						#button	{5}		5
						#button	{6}		6
						#button	{7}		7
						#button	{8}		8
						#button	{9}		9
						#new
						#button	{10}		10
						#button	{Jack}	11
						#button	{Queen}	12
						#button	{King}	13
						#button	{Ace}		14
					)
				)
				(= theSuit
					(PrintD @str
						#new #button	{Clubs}		1
						#new #button  	{Spades}		2
						#new #button  	{Diamonds}	3
						#new #button  	{Hearts}		4
					)
				)
				(-- theSuit)
			else
				(= theVal (Random 2 Ace))
				(= theSuit (Random Clubs Hearts))
			)
		)
		((deck at: i)
			view:		vCardDeck,
			loop:		theSuit,
			cel:		theVal
		)
	)
)


; Deals the next card from the deck into the hand

(procedure (DealCard which &tmp oldCard newCard)
	(= oldCard (hand at: which))
	(= newCard (deck at: nextCard))
	(dealSound play:)
	(oldCard
		view:		vCardDeck,
		setLoop:	(newCard loop?),
		setCel:	(newCard cel?),
		x:			(+ 55 (* which 44)),
		y:			50,
		init:		,
	)
	(++ nextCard)
)


; Tally the hand for winning cards

(procedure (TallyHand &tmp i loCard hiCard xCard consecutive)
	; Tally the numbers and suits in the hand, then sort the tallies
	(for	((= i 0)) (< i 5) ((++ i))
		(++ [nTally ((hand at: i) cel?)])
		(++ [sTally ((hand at: i) loop?)])
	)
	(SortTallies)

	; Now check for different kinds of hands
	(switch (mod [nTally 0] 10)
		(4
			(= theHand h4ofaKind)
		)
		(3
			(if (== (mod [nTally 1] 10) 2)
				(= theHand hFullHouse)
			else
				(= theHand h3ofaKind)
			)
		)
		(2
			(cond
				((== (mod [nTally 1] 10) 2)
					(= theHand h2pair)
				)
				((>= (/ [nTally 0] 10) Jack)
					(= theHand hPairJacks)
				)
				(else
					(= theHand hGarbage)
				)
			)
		)
		(else
			; We sort the hand by value, and if the difference from highest to
			;	lowest is 4, they're consecutive.
			;(if (not
			;	(and
			;		(IsObject hand)
			;		(IsObject sortHand)
			;		(IsObject sortCode)
			;	)
			;)
			;(Print "SOMETHING'S WRONG")
			;)
			(Sort hand sortHand sortCode)
			(= loCard ((sortHand at: 0) cel?))
			(= hiCard ((sortHand at: 4) cel?))
			(= xCard ((sortHand at: 3) cel?))
			(sortHand release:, dispose:)

			; Check for ace-low straight
			(if (and (== hiCard Ace)
						(== loCard 2)
						(== xCard 5)
					)
				(= consecutive TRUE)
			else
				(= consecutive (if (== (- hiCard loCard) 4) TRUE else FALSE))
			)

			(cond
				; Check for a flush
				((== (mod [sTally 0] 10) 5)
					(cond
						((not consecutive)
							(= theHand hFlush)
						)
						((== loCard 10)
							(= theHand hRoyalFlush)
						)
						(else
							(= theHand hStraightFlush)
						)
					)
				)

				; Ok, check for a straight
				(consecutive
					(= theHand hStraight)
				)

				; You lose, Buttweet
				(else
					(= theHand hGarbage)
				)
			)
		)
	)
)


; A little bubble sort for sorting the tallies

(procedure (SortTallies &tmp i j foo [str 100])
	(for	((= i 0)) (< i 14) ((++ i))
		(for ((= j (+ i 1))) (< j 15) ((++ j))
 			(if (> (mod [nTally j] 10) (mod [nTally i] 10))
				(= foo [nTally i])
				(= [nTally i] [nTally j])
				(= [nTally j] foo)
			)
		)
	)
	(for	((= i 0)) (< i 3) ((++ i))
		(for ((= j (+ i 1))) (< j 4) ((++ j))
 			(if (> (mod [sTally j] 10) (mod [sTally i] 10))
				(= foo [sTally i])
				(= [sTally i] [sTally j])
				(= [sTally j] foo)
			)
		)
	)
)


; Pay the bet from credits

(procedure (PayBet)
	(-= credits theBet)
	(= paid TRUE)
	(DisplayStats)
)


; Display our winnings, bet, and credits at the top of the screen

(procedure (DisplayStats &tmp [str 50])
	(Display "Win:$_____________Bet:$_____________Credits:$"
		#p_at:		60 25
		#p_font:		smallFont
;		#p_width:	210
		#p_color:	colWhite
		#p_back:		colBlack
	)
	(Display	"" #p_restore:	wUnders)
	(Format @str "%d__" winnings)
	(= wUnders
		(Display @str
			#p_at:		80 25
			#p_font:		smallFont
			#p_color:	colWhite
			#p_back:		colBlack
		)
	)

	(Display	"" #p_restore:	bUnders)
	(Format @str "%d___" theBet)
	(= bUnders
		(Display @str
			#p_at:		153 25
			#p_font:		smallFont
			#p_color:	colWhite
			#p_back:		colBlack
		)
	)

	(Display	"" #p_restore:	cUnders)
	(Format @str "%d___" credits)
	(= cUnders
		(Display @str
			#p_at:		242 25
			#p_font:		smallFont
			#p_color:	colWhite
			#p_back:		colBlack
	 	)
	)
)

(procedure (CountScore whichSound &tmp)
	(whichSound
		setLoop:		-1,
		play:
	)
	(User canInput: FALSE)
	(while winnings
		(DisplayStats)
		(if (> winnings 10)
			(-= winnings 10)
			(+= credits 10)
		else
			(-- winnings)
			(++ credits)
		)
		(Wait 0)
		(Wait 10)
	)
	(whichSound setLoop: 1)
	(User canInput: TRUE)
)


;ษอออออออออออออออออออป
;บ                   บ
;บ Objects           บ
;บ                   บ
;ศอออออออออออออออออออผ

(instance deck of Set)
(instance hand of Set)
(instance sortHand of Set)
(instance sortCode of Code
	(method (doit theObj &tmp theCel)
		(= theCel (theObj cel?))
		(if (not theCel)
			(= theCel 13)
		)
		(return theCel)
	)
)
(instance clearCode of Code
	(method (doit theObj)
		(theObj
			view:		vCardDeck,
			setLoop:	0,
			setCel:	0
		)
	)
)

(instance cardH0 of View)
(instance cardH1 of View)
(instance cardH2 of View)
(instance cardH3 of View)
(instance cardH4 of View)
(instance cardD0 of View)
(instance cardD1 of View)
(instance cardD2 of View)
(instance cardD3 of View)
(instance cardD4 of View)
(instance cardD5 of View)
(instance cardD6 of View)
(instance cardD7 of View)
(instance cardD8 of View)
(instance cardD9 of View)

; This guy returns true if the object (card in the deck) matches in suit and
;	value the new card we're trying to add
(instance uniqueCode of Code
	(method (doit theObj v s)
		(return	(and	(== (theObj loop?) s)
							(== (theObj cel?) v)
						)
		)
	)
)

(instance incBet of Prop
	(properties
		view				vCasinoVideoPoker
		loop				lIncreaseBet
		x					132
		y					176
      description 	"the Increase button"
		lookStr			"Click the Hand here to increase your bet."
	)

	(method (init)
		(super init:)
		(mouseDownHandler addToFront: self)
	)
	(method (dispose)
		(mouseDownHandler delete: self)
		(super dispose:)
	)
	(method (handleEvent event &tmp evt ticks)
		(if (self onMe: event)
			(if (and (and (== (event type?) mouseDown)
								(not (event modifiers?)))
						(== theCursor DO_CURSOR)
					)
				(while (and (!= ((= evt (Event new:)) type?) mouseUp)
								(self onMe: evt)
							)
					(= ticks (GetTime))
					(while (< (GetTime) (+ 7 ticks)))
					(self doVerb: verbDo)
					(evt dispose:)
				)
				(evt dispose:)
				(event claimed: TRUE)
			else
				(super handleEvent: event)
			)
	  	else
			(super handleEvent: event)
		)
	)
	(method (doVerb theVerb invItem &tmp [str 10])
		(switch theVerb
			(verbDo
				(if dealOK
					(if (< theBet 100)
						(cond
							(paid
								(TPrint "It's too late now to change your bet!")
							)
							((< theBet credits)
								(buttonSound play:)
								(++ theBet)
								(= winnings 0)
								(DisplayStats)
							)
							(else
								(TPrint "You can't bet more than your credits.")
							)
						)
					else
						(TPrint "The Tramp Casino poker machines are limited
							to bets of $100.")
					)
				)
			)
			(else
				(super doVerb: theVerb invItem &rest)
			)
		)
	)
)

(instance decBet of Prop
	(properties
		view				vCasinoVideoPoker
		loop				lDecreaseBet
		x					175
		y					172
      description 	"the Decrease button"
		lookStr			"Click the Hand here to decrease your bet."
	)
	(method (init)
		(super init:)
		(mouseDownHandler addToFront: self)
	)
	(method (dispose)
		(mouseDownHandler delete: self)
		(super dispose:)
	)
	(method (handleEvent event &tmp evt ticks)
		(if (self onMe: event)
			(if (and (and (== (event type?) mouseDown)
								(not (event modifiers?)))
						(== theCursor DO_CURSOR)
					)
				(while (and (!= ((= evt (Event new:)) type?) mouseUp)
								(self onMe: evt)
							)
					(= ticks (GetTime))
					(while (< (GetTime) (+ 7 ticks)))
					(self doVerb: verbDo)
					(evt dispose:)
				)
				(evt dispose:)
				(event claimed: TRUE)
			else
				(super handleEvent: event)
			)
	  	else
			(super handleEvent: event)
		)
	)
	(method (doVerb theVerb invItem &tmp [str 10])
		(switch theVerb
			(verbDo
				(if dealOK
					(if paid
						(TPrint "It's too late now to change your bet!")
					else
						(if
							(> theBet 1)
							(buttonSound play:)
							(if (not (-- theBet))
								(Cls)
							)
							(= winnings 0)
							(DisplayStats)
						else
							(TPrint "You must bet something!")
						)
					)
				)
			)
			(else
				(super doVerb: theVerb invItem &rest)
			)
		)
	)
)

(instance cashout of View
	(properties
		view				vCasinoVideoPoker
		loop				lCashOut
		x					287
		y					151
      description 	"the Cash Out button"
		lookStr			"Click the Hand here to stop playing this machine.
							Your winnings will be given to you automatically."
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbDo
				(buttonSound play:)
				(if (and dealOK
							(not (& ((theIconBar at: computerIcon) signal?) DISABLED))
					 )
					(self setCel: 1)
					(if credits
						(theMusic2
							number:		sCashOut,
							loop:			1,
							flags:		1,
							play:,
						)
						(TPrint "You take your money from the tray.")
						(= silvDollars credits)
						(ego get: iSilverDollar)
					)
					(SetupExit FALSE)
					(curRoom newRoom: 710)
				else
					(TPrint "First, finish this hand.")
				)
			)
			(else
				(super doVerb: theVerb invItem &rest)
			)
		)
	)
)

(instance deal of View
	(properties
		view				vCasinoVideoPoker
		loop				lDeal
		x					29
		y					151
      description 	"the Deal button"
		lookStr			"Click the Hand here to begin a new hand of poker."
		sightAngle 		40
	)
	(method (doVerb theVerb invItem &tmp i)
		(switch theVerb
			(verbDo
				(if dealOK
					(buttonSound play:)
					(DisplayStats)
					(Cls)
					(if theBet
						(if (not paid)
							(++ pokerJackpot)
							(PayBet)
						)
						(self setCel: 1)
						(= dealOK FALSE)
						(= holdOK FALSE)
						(sPlayPoker cue:)
					else
						(TPrint "You have no money and pretend to play the game,
							assuming that the other gamblers standing around you
							won't notice.
							You win zero dollars and have zero fun!")
					)
				)
			)
			(else
				(super doVerb: theVerb invItem &rest)
			)
		)
	)
)

(instance hold1 of View
	(properties
		view				vCasinoVideoPoker
		loop				lHold
		x					68
		y					151
 		description 	"the Hold Card 1 button"
		lookStr			"Click the Hand here to hold card #1."
		sightAngle 		40
	)
	(method (onMe &tmp theCard)
		(= theCard (hand at: 0))
		(return	(or	(super onMe: &rest)
							(and 	(IsObject theCard)
									(cast contains: theCard)
									(theCard onMe: &rest)
								)
						)
		)
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbDo
				(if holdOK
					(buttonSound play:)
					(if cel
						(= [drop 0] TRUE)
						(self setCel: 0)
					else
						(= [drop 0] FALSE)
						(self setCel: 1)
					)
				)
			)
			(else
				(super doVerb: theVerb invItem &rest)
			)
		)
	)
)

(instance hold2 of View
	(properties
		view				vCasinoVideoPoker
		loop				lHold
		x					114
		y					151
 		description 	"the Hold Card 2 button"
		lookStr			"Click the Hand here to hold card #2."
		sightAngle 		40
	)
	(method (onMe &tmp theCard)
		(= theCard (hand at: 1))
		(return	(or	(super onMe: &rest)
							(and 	(IsObject theCard)
									(cast contains: theCard)
									(theCard onMe: &rest)
								)
						)
		)
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbDo
				(if holdOK
					(buttonSound play:)
					(if cel
						(= [drop 1] TRUE)
						(self setCel: 0)
					else
						(= [drop 1] FALSE)
						(self setCel: 1)
					)
				)
			)
			(else
				(super doVerb: theVerb invItem &rest)
			)
		)
	)
)

(instance hold3 of View
	(properties
		view				vCasinoVideoPoker
		loop				lHold
		x					159
		y					151
 		description 	"the Hold Card 3 button"
		lookStr			"Click the Hand here to hold card #3."
		sightAngle 		40
	)
	(method (onMe &tmp theCard)
		(= theCard (hand at: 2))
		(return	(or	(super onMe: &rest)
							(and 	(IsObject theCard)
									(cast contains: theCard)
									(theCard onMe: &rest)
								)
						)
		)
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbDo
				(if holdOK
					(buttonSound play:)
					(if cel
						(= [drop 2] TRUE)
						(self setCel: 0)
					else
						(= [drop 2] FALSE)
						(self setCel: 1)
					)
				)
			)
			(else
				(super doVerb: theVerb invItem &rest)
			)
		)
	)
)

(instance hold4 of View
	(properties
		view				vCasinoVideoPoker
		loop				lHold
		x					203
		y					151
 		description 	"the Hold Card 4 button"
		lookStr			"Click the Hand here to hold card #4."
		sightAngle 		40
	)
	(method (onMe &tmp theCard)
		(= theCard (hand at: 3))
		(return	(or	(super onMe: &rest)
							(and 	(IsObject theCard)
									(cast contains: theCard)
									(theCard onMe: &rest)
								)
						)
		)
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbDo
				(if holdOK
					(buttonSound play:)
					(if cel
						(= [drop 3] TRUE)
						(self setCel: 0)
					else
						(= [drop 3] FALSE)
						(self setCel: 1)
					)
				)
			)
			(else
				(super doVerb: theVerb invItem &rest)
			)
		)
	)
)

(instance hold5 of View
	(properties
		view				vCasinoVideoPoker
		loop				lHold
		x					246
		y					151
 		description 	"the Hold Card 5 button"
		lookStr			"Click the Hand here to hold card #5."
		sightAngle 		40
	)
	(method (onMe &tmp theCard)
		(= theCard (hand at: 4))
		(return	(or	(super onMe: &rest)
							(and 	(IsObject theCard)
									(cast contains: theCard)
									(theCard onMe: &rest)
								)
						)
		)
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbDo
				(if holdOK
					(buttonSound play:)
					(if cel
						(= [drop 4] TRUE)
						(self setCel: 0)
					else
						(= [drop 4] FALSE)
						(self setCel: 1)
					)
				)
			)
			(else
				(super doVerb: theVerb invItem &rest)
			)
		)
	)
)


;ษออออออออป
;บ        บ
;บ Sounds บ
;บ        บ
;ศออออออออผ

(instance dealSound of Sound
	(properties
		number		sDeal
	)
)

(instance buttonSound of Sound
	(properties
		number		sButton
	)
)

(instance cashOutSound of Sound
	(properties
		number		sCashOut
	)
)

(instance winSound of Sound
	(properties
		number		sWin
		loop			-1
	)
)

(instance bigWinSound of Sound
	(properties
		number		sWin
		loop			-1
	)
)

(instance loseSound of Sound
	(properties
		number		sLose
		flags			1
	)
)


;ษอออออออออออออออออออป
;บ                   บ
;บ Scripts           บ
;บ                   บ
;ศอออออออออออออออออออผ

(instance sPlayPoker of Script
	(method (changeState newState &tmp i [str 50])
		(switchto (= state newState)
			(; Place bet, wait for Deal
				(= dealOK TRUE)
			 ; Enable control panel for next hand
			 	(theIconBar enable: computerIcon)
			)
			(; Get a fresh deck
				(NewDeck)
			 ; Disable control Panel while hand in play
			 	(theIconBar disable: computerIcon)
				(= cycles 2)
			)
			(; Deal the initial 5 cards
				(DealCard register)
				(= cycles 4)
			)
			(
				(if (< (++ register) 5)
					(-= state 2)
				)
				(= cycles 1)
			)
			(; The "deal" button cues us from here, with all "holds" set
				(deal setCel: 0)
				(hold1 setCel: 0)
				(hold2 setCel: 0)
				(hold3 setCel: 0)
				(hold4 setCel: 0)
				(hold5 setCel: 0)
				(= dealOK TRUE)
				(= holdOK TRUE)

			)
			(; Dispose the cards we're throwing away
				(for	((= i 0)) (< i 5) ((++ i))
					(if [drop i]
						((hand at: i) dispose:)
					)
				)
				(= register 0)
				(= cycles 2)
			)
			(; Deal our new cards
				(if [drop register]
					(DealCard register)
					(= cycles 4)
				else
					(self cue:)
				)
			)
			(
				(if (< (++ register) 5)
					(-= state 2)
				)
				(= cycles 1)
			)
			(; Calculate the bet & display credits
				(deal setCel: 0)
				(hold1 setCel: 0)
				(hold2 setCel: 0)
				(hold3 setCel: 0)
				(hold4 setCel: 0)
				(hold5 setCel: 0)
				(++ handsPlayed)

				(TallyHand)
				(if (== theHand hRoyalFlush)
					(Format @str "\n
										P R O G R E S S I V E\n
										\n
										J A C K P O T___!_!\n
										\n
										\n
										You win %d dollars!\n
										\n" pokerJackpot)
					(TPrint @str
						#width		200
						#title		{Another Lucky Winner}
						#font			bigFont
						#mode			teJustCenter
					)
					(= silvDollars pokerJackpot)
					(= pokerJackpot 1000)
					(ego get: iSilverDollar)
					(SetupExit FALSE)
					(curRoom newRoom: 710)
				else
					(= winnings (* theBet [payoff theHand]))
					(DisplayStats)
					(= cycles 5)
				)
			)
			(
				(if winnings
					(CountScore winSound) 	; differentiate between win & big win
				else
					(loseSound play:)
				)
				(= cycles 1)
			)
			(
				(if (> theBet credits)
					(= theBet credits)
				)
				(DisplayStats)
				(if credits 		; we still have money
					(= register 0)
					(= paid FALSE)
					(self init:)
				else
					(TPrint "You sadly walk away, your turned-out pockets
						telling everyone of your gambling \"problem.\"")
			 		; Enable control panel for next room
			 		(theIconBar enable: computerIcon)
					(SetupExit FALSE)
					(curRoom newRoom: 710)
				)
			)
		)
	)
);sPlayPoker

