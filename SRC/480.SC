;**
;**	Logics for Room 480 -- End of Game
;**
;**	LSL5:  Passionate Patti Does A Little Undercover Work
;**				Copyright 1991
;**				by Al Lowe
;**
;**	Programmed by Steve Conrad
;**
;**	Last Update:  September 12, 1991
;**

(script#		480)

(public
	rm480		0
)

(procedure
	FindSize
	Credits
)

(local
	soundIdx			; used in Pick-A-Sound
	soundNums =	[ 0 273 274 275 297 467 387 147 161 291 861 911 146 892
						752 753 754 755 756 751 526 851 482 483 484 ]

	sizeElem = [ 0 0 0 0 ]
	clr
)

(define	pHelicopteringIntoSunset	480)
(define	vHelicopter					480)
	(define	lCopterLarge		0)
	(define	lCopterMedium		1)
	(define	lCopterSmall		2)
(define	vAdditionalCredits		481)

; Music define
(define	mLarryTheme						100)
(define	mEnding							462)
(define 	sHelicopter					468)
(define	sAlSlogan					481)

(instance rm480 of LLRoom
	(properties
		picture		pHelicopteringIntoSunset
		style			IRISOUT
	)

	(method (init)
		(LoadMany PICTURE pBlackPic)
		(LoadMany FONT giantFont)
		(LoadMany SOUND mLarryTheme)
		(copter
			init:			,
			setCycle:	Forward
		)
		(super init:)
		(if (Btst fFFto480)
			(theMusic
				number:		mEnding,
				flags:		TRUE,
				setLoop:		-1,
				setVol:		127,
				play:
			)
		)
		(theMusic2
			number:		sHelicopter,
			flags:		TRUE,
			setLoop:		-1,
			setVol:		127,
			play:
		)
		(self setScript: sCartoon)
	)
	(method (dispose)
		(super dispose: &rest)
		(theMusic
			fade:	0 15 12 TRUE
		)
	)
)

;********** Scripts ***********

(instance sCartoon of Script
	(method (changeState newState)
		(switchto (= state newState)
			(
				(HandsOff)
				(= seconds 3)
			)
			(
				(Say ego "\"Larry? What's that bulge in your leisure suit?\""
					#dispose: self
					HIGH
				)
			)
			(
				(= ticks 30)
			)
			(
				(TPrint "\"Oh, Patti! Oh. I know what you mean.
					Why those are just some miniature video tapes
					I recorded while on the road.\""
					#title: 	{Larry}
					#dispose: self
					HIGH
				)
			)
			(
				(= ticks 30)
			)
			(
				(Say ego "\"Hey, fun!
					That'll give us something to watch at Camp David!\""
					#dispose: self
					HIGH
				)
			)
			(
				(= ticks 30)
			)
			(
				(TPrint "\"I just love home movies!\" chimes in the Vice President."
					#title:	{The Vice President}
					#dispose: self
					HIGH
				)
			)
			(
				(= ticks 30)
			)
			(
				(TPrint "\"Uh, Patti, er, Sir, ah, I don't think...
					Oh, never mind,\" Larry says.
					\"Grab your glasses everyone.
					Patti, I propose a toast to us--and
					to our wonderful future together!\""
					#title: 	{Larry}
					#dispose: self
					HIGH
				)
			)
			(
				(= ticks 30)
			)
			(
				(Say ego "\"Excellent, Larry. Yes--to a great week-end!!\""
					#dispose: self
					HIGH
				)
			)
			(
				(= ticks 30)
			)
			(
				(copter
					setLoop:			lCopterLarge,
					setCycle:		EndLoop self
				)
			)
			(
				(copter
					cycleSpeed:		8,
					setLoop:			lCopterMedium,
					setCel:			0,
					cycleSpeed:		8,
					moveSpeed:		8,
					setCycle:		EndLoop self,
					setMotion:		MoveTo 165 128
				)
			)
			(
				(copter
					x:					285,
					y:					79,
					xStep:			7,
					yStep:			7,
					setLoop:			lCopterSmall,
					setCel:			0,
					cycleSpeed:		6,
					moveSpeed:		6,
					setCycle:		Forward,
					setMotion:		MoveTo 350 61 self
				)
				(theMusic2
					fade:
				)
			)
			(
				(= ticks 120)
			)
			(
				(theMusic
					fade:	0 15 12 TRUE self
				)
			)
			(
				(theMusic
					number:		mLarryTheme,
					flags:		TRUE,
					setLoop:		-1,
					setVol:		127,
					play:
				)
				(= ticks 120)
			)
			(
				(TPrint "And so, all's well that ends well?"
					HIGH
				)
				(TPrint "Well, well!")
				(= seconds 3)
			)
			(
				(TPrint "It seems all your \"work\" videotaping those girls
					was for naught.
					Silas Scruemall, producer of \"America's Sexiest Home Videos,\"
					decided to \"simplify the audition process\" by magnanimously
					awarding the show's hostess gig to his current girlfriend."
					BIG)
				(TPrint "\"America's Sexiest Home Videos\" went on to become
					the megahit of the season, proving clearly that P. T. Barnum
					was right.")
				(= seconds 3)
			)
			(
				(TPrint "Congress never did get around to doing anything to
					\"clean up the airwaves.\""
					BIG)
				(TPrint "It seems every bill ever submitted somehow turned up
					mysteriously \"stuck in committee\" while they \"examined
					the evidence!\"")
				(= seconds 3)
			)
			(
				(TPrint "After a lengthy trial that increased tabloid sales
					nationwide, Julius Bigg was found innocent of all charges
					related to his incident at the White House dinner."
					BIG)
				(TPrint "In what was quickly dubbed the \"custard pie\" defense,
					he pleaded \"temporary insanity due to the high
					sugar content of those White House desserts\"
					and got off with a mild reprimand from the judge
					and six months of mandatory Jenny Craig meetings.")
				(= seconds 3)
			)
			(
				(TPrint "Reverse Biaz soon found himself out of a job when
					des Rever Records went bankrupt."
					BIG)
				(TPrint "Executives realized there's no longer any reason to add
					subliminal messages to recordings since kids can't figure out
					how to play their CDs backwards.")
				(= seconds 3)
			)
			(
				(TPrint "K-RAP became a national sensation when they expanded
					by buying a small television station outside of Atlanta
					and a 24-hour satellite channel."
					BIG)
				(TPrint "They promoted P. C. Hammer to his new position as
					the nation's first \"Nude VeeJay.\"
					Their new slogan: \"K-TV--More Rap, Less Wrap!\"")
				(= seconds 3)
			)
			(
				(TPrint "The Mob's pornography business continued its
					steady decline."
					BIG)
				(TPrint "But they'll find newer, more profitable investments
					somewhere.")
				(= seconds 3)
			)
			(
				(TPrint "Piano bars continue to change into karaoke bars and
					the entertainment industry remains exactly as honest as
					it has always been."
					BIG)
				(= seconds 3)
			)
			(
				(TPrint "But all is not lost:
					Leisure Suit Larry and Passionate Patti are together again!")
				(= seconds 3)
			)
			(
				(TPrint "...at least for the weekend!" LOW)
				(= seconds 3)
			)
			(
				(Credits {Art Team Manager\n\n
							Cheryl Loyd}
							3
				)
				(= seconds 3)
			)
			(
				(curRoom
					drawPic: pBlackPic SCROLLUP
				)
				(= clr
					(switch (Random 0 5)
						(0		colVLRed)
						(1		colLYellow)
						(2		colVLGreen)
						(3		colVLBlue)
						(4		colLMagenta)
						(else	colLCyan)
					)
				)
				(Display
					{Animators\n\n}
					#p_color: 	clr
					#p_at: 		1 (- 89 (/ (* 12 9) 2))
					#p_width: 	318
					#p_font: 	bigFont
					#p_mode: 	teJustCenter
				)
				(Display
					{\n\n
					Cheryl Loyd\n
					Barry T. Smith\n
					Roger Hardy Jr.\n
					Dana M. Dean\n
					Karin Young\n
					Marc Hudgins\n
					Eric Kasner}
					#p_color: 	clr
					#p_at: 		1 (- 89 (/ (* 12 9) 2))
					#p_width: 	159
					#p_font: 	bigFont
					#p_mode: 	teJustCenter
				)
				(Display
					{\n\n
					Richard Powell\n
					Phyllis Cucchiara\n
					Terry Falls\n
					Michael Hutchison\n
					Desie Hartman\n
					Willis Wong}
					#p_color: 	clr
					#p_at: 		161 (- 89 (/ (* 12 9) 2))
					#p_width: 	159
					#p_font: 	bigFont
					#p_mode: 	teJustCenter
				)
				(= seconds 8)
			)
			(
				(Credits {Background Artists\n\n
							Jane Cardinal\n
							Maurice Morgan\n
							Andy Hoyos\n
							Cheryl Loyd\n
							Jay Friedmann\n
							Jennifer Shontz\n
							Gloria Garland}
							9
				)
				(= seconds 5)
			)
			(
				(Credits {Programmers\n\n
							Brian K. Hughes\n
							Carlos Escobar\n
							Steve Conrad\n
							John Hartin\n
							Oliver Brelsford\n
							Al Lowe}
							8
				)
				(= seconds 5)
			)
			(
				(Credits {Music Director\n\n
							Mark Seibert}
							3
				)
				(= seconds 3)
			)
			(
				(curRoom
					drawPic: pBlackPic SCROLLUP
				)
				(= clr
					(switch (Random 0 5)
						(0		colVLRed)
						(1		colLYellow)
						(2		colVLGreen)
						(3		colVLBlue)
						(4		colLMagenta)
						(else	colLCyan)
					)
				)
				(Display
					{Sierra's Creative Interpreter\n
					 Development System\n\n}
					#p_color: 	clr
					#p_at: 		1 (- 89 (/ (* 12 11) 2))
					#p_width: 	318
					#p_font: 	bigFont
					#p_mode: 	teJustCenter
				)
				(Display
					{\n\n\n
					Jeff Stephenson\n
					Robert E. Heitman\n
					Dan Foy\n
					Larry Scott\n
					John Rettig\n
					J. Mark Hood\n
					Chris Smith\n
					Terry McHenry}
					#p_color: 	clr
					#p_at: 		1 (- 89 (/ (* 12 11) 2))
					#p_width: 	159
					#p_font: 	bigFont
					#p_mode: 	teJustCenter
				)
				(Display
					{\n\n\n
					Eric Hart\n
					Chad Bye\n
					Mark Wilden\n
					Ken Koch\n
					John Crane\n
					Steve Coallier\n
					Randy Moss}
					#p_color: 	clr
					#p_at: 		161 (- 89 (/ (* 12 11) 2))
					#p_width: 	159
					#p_font: 	bigFont
					#p_mode: 	teJustCenter
				)
				(= seconds 8)
			)
			(
				(Credits {Additional Music\n\n
							Chris Braymen}
							3
				)
				(= seconds 3)
			)
			(
				(Credits {Additional Written Material\n\n
							Josh Mandel}
							3
				)
				(= seconds 3)
			)
			(
				(Credits {Quality Assurance Team Leader\n\n
							Robin Bradley}
							3
				)
				(= seconds 3)
			)
			(
				(Credits {Coffee Grinder\n\n
							C. Escobar}
							3
				)
				(= ticks 100)
			)
			(
				(if (DoSound NumDACs)
					(theMusic2
						number:		161,	;coffee!
						setLoop:		1,
						play:
					)
				)
				(= seconds 3)
			)
			(
				(curRoom
					drawPic: pBlackPic SCROLLUP
				)
				(if (DoSound NumDACs)
					(TPrint "Now would be an excellent time for you to save your
						game.  We have a little surprise in store for you."
						#title		{AL says}
						HIGH
					)
				)
				(= seconds 5)
			)
			(
				(theMusic
					fade:	self
				)
			)
			(
				(if (not (DoSound NumDACs))
					(+= state 1)
				else
					(theGame setCursor: ARROW_CURSOR TRUE)
					(TPrint "You did it! You finished the entire game!
						Congratulations to you from all of us,
						the creators of \"Leisure Suit Larry 5:
						Passionate Patti Does a Little Undercover Work.\"\n
						\n
						We have a little reward for you.
						Since your computer has a DAC,
						here's a sample player so you can easily
						hear all the sounds from this game.
						We hope you enjoy hearing them again!"
						#title		{Congratulations! You Win!!}
						BIG
					)
				)
				(= cycles 2)
			)
			(
				(if
					(= soundIdx
						(PrintD
							{Aren't you glad you bought a DAC?} #x 50
							#new
							#button	{Airport PA #1}	1	;273
							#button	{Airport PA #2}	2	;274
							#button	{Airport PA #3}	3	;275
							#new
							#button	{Airport PA #4}	4	;297
							#button	{__Atmosphere__}		5	;467
							#button	{____Screams____}		6	;387
							#new
							#button	{______Yeow______}	7	;147
							#button	{_____Coffee_____}	8	;161
							#button	{Folding Chair}		9	;291
							#new
							#button	{_Wolf Whistle_}		10	;861
							#button	{___Knocking___}		11	;911
							#button	{___Glass #1___}		12	;146
							#new
							#button	{___Glass #2___}		13	;892
							#button	{_______Ah_______}	14	;752
							#button	{_______Oh_______}	15	;753
							#new
							#button	{_______AH!_______}	16	;754
							#button	{______OH!______}		17	;755
							#button	{_____Oooh!_____}		18	;756
							#new
							#button	{_Crowd Gasp_}			19	;751
							#button	{_Slob Eating_}		20	;526
							#button	{_____Flush_____}		21	;851
							#new
							#button	{_____Belch_____}		22	;482
							#button	{_____Barf_____}		23	;483
							#button	{Do Not Touch}			24	;484
							#new
							#button	{< Done >}				0	#x 115
							#title	{Thanks for buying \"Larry 5!\"}
							#first	soundIdx
						)
					)
					(theMusic
						number:	[soundNums soundIdx],
						setLoop:	1,
						play:
					)
					(self changeState: state)
				else
					(= cycles 1)
				)
			)
			(
				(curRoom
					drawPic: pBlackPic SCROLLUP
				)
				(Display {Remember Al Lowe's Slogan:}
					#p_color: 	colYellow
					#p_at: 		1 30
					#p_width: 	318
					#p_font: 	niceFont
					#p_mode: 	teJustCenter
				)
				(= cycles 2)
			)
			(
				(if (DoSound NumDACs)
					(LoadMany SOUND sAlSlogan)
				)
				(= seconds 3)
			)
			(
				(if (DoSound NumDACs)
					(theMusic
						number: 		sAlSlogan,
						setVol:		127,
						setLoop:		1,
						play:
					)
				)
				(Display {Better Babes Through\n
							Technology!}
					#p_color: 	colVLRed		;**	colWhite
					#p_at: 		1 90
					#p_width: 	318
					#p_font: 	giantFont
					#p_mode: 	teJustCenter
				)
				(= seconds 5)
			)
			(
				(= quit TRUE)
			)
		)
	)
);sCartoon

;*********** Objects ***********

(instance copter of Actor
	(properties
		view			vHelicopter
		loop			3;lCopterLarge
		cel			0
		x				160	;176
		y				144	;144
		cycleSpeed 	6
		moveSpeed	6
	)
)

;procedure for finding margin to center text on screen

(procedure (FindSize theText)
	(TextSize @sizeElem theText 0 315)
	(return (/ (- 180 (- [sizeElem 2] [sizeElem 0])) 2))
)

;procedure for scrolling credits

(procedure (Credits theText numLines &tmp margin c )
	(curRoom
		drawPic: pBlackPic SCROLLUP
	)

	;get one of the following colors
	(= c
		(switch (Random 0 5)
			(0		colVLRed)
			(1		colLYellow)
			(2		colVLGreen)
			(3		colVLBlue)
			(4		colLMagenta)
			(else	colLCyan)
		)
	)

	(Display
		theText
		#p_color: 	c
		#p_at: 		1 (- 89 (/ (* 12 numLines) 2))
		#p_width: 	318
		#p_font: 	bigFont
		#p_mode: 	teJustCenter
	)
)

