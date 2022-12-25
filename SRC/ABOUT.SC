;**
;**	Logics for Room ABOUT -- The "about" screen
;**
;**	LSL5:  Passionate Patti Does A Little Undercover Work
;**				Copyright 1991
;**				by Al Lowe
;**
;**	Programmed by Brian K. Hughes
;**
;**	Last Update:  September 3, 1991
;**

(script#	ABOUT)

(public
	aboutCode	0
)

(instance aboutCode of Code
	(method (doit &tmp [string 200])
		(Load VIEW vAboutIcon)
		(TPrint
		  "\"Leisure Suit Larry 5:\n
			Passionate Patti Does A Little Undercover Work\"\n
			\n
			Executive Producer\n
			Ken Williams\n
			\n
			Creative Director\n
			Bill Davis\n
			\n
			Director\n
			Al Lowe\n
			\n
			Producer\n
			Guruka Singh Khalsa\n
			"
			#font			smallFont
			#mode			teJustCenter
			#width		222
			#title		{Sierra Presents}
		)
		(cycleIcon loop: 2)
		(TPrint
		  "Game Designer\n
			Al Lowe\n
			\n
			Production Designer\n
			Andy Hoyos\n
			\n
			Art Designer\n
			Jane Cardinal\n
			\n
			Lead Programmer\n
			Brian K. Hughes\n
			\n
			Composer\n
			Craig Safan\n
			\n
			Art Team Manager\n
			Cheryl Loyd\n
			"
			#font			smallFont
			#mode			teJustCenter
			#icon			cycleIcon
			#width		180
			#title		{The Bosses}
		)
		(cycleIcon loop: 3)
		(TPrint
		  "Animators\n
			Cheryl Loyd, Barry T. Smith,\n
			Roger Hardy, Jr., Dana M. Dean,\n
			Karin Young, Marc Hudgins, Eric Kasner,\n
			Richard Powell, Phyllis Cucchiara,\n
			Terry Falls, Michael Hutchison,\n
			Desie Hartman, and Willis Wong\n
			\n
			Background Artists\n
			Jane Cardinal, Maurice Morgan,\n
			Andy Hoyos, Cheryl Loyd, Jay Friedman,\n
			Jennifer Shontz, and Gloria Garland\n
			\n
			Programmers\n
			Brian K. Hughes, Carlos Escobar,\n
			Steve Conrad, John Hartin,\n
			Oliver Brelsford, and Al Lowe\n
			"
			#font			smallFont
			#mode			teJustCenter
			#icon			cycleIcon
			#width		200
			#title		{The Workers}
		)
		(cycleIcon loop: 1)
		(TPrint
		  "Music Director\n
			Mark Seibert\n
			\n
			Composer\n
			Craig Safan\n
			\n
			Additional Music\n
			Chris Braymen\n
			\n
			Theme Song Composed by\n
			Al Lowe\n
			"
			#font			smallFont
			#mode			teJustCenter
			#icon			cycleIcon
			#width		200
			#title		{The Boys in the Band}
		)
		(cycleIcon loop: 0)
		(Format @string
		  "Written Exclusively in S.C.I.\n
			\"Sierra's Creative Interpreter\"\n
			\n
			System Programmers\n
			Jeff Stephenson, Robert E. Heitman,\n
			Dan Foy, Larry Scott, John Rettig,\n
			J. Mark Hood, Chris Smith,\n
			Terry McHenry, Eric Hart, Chad Bye,\n
			Mark Wilden, Ken Koch, John Crane,\n
			Steve Coallier, and Randy Moss\n
 			\n
			Quality Assurance\n
			Robin Bradley\n
			Joe Perry\n
			\n
			Additional Written Material\n
			Josh Mandel\n
			\n
			Version %s %s
			"
			version
			versionDate
		)
		(TPrint @string
			#font			smallFont
			#mode			teJustCenter
			#icon			cycleIcon
			#width		222
			#title		{The Hackers}
		)
		(Format @string
		  "If you're having a good time playing this game and would
			like to order other fun products from the Sierra family of
			computer games, just give our Order Desk a telephone call at\n
			%s.\n
			(International customers call %s.)\n
			\n
			Our friendly and courteous order takers are probably sitting around
			doing nothing right now and would love to have someone to talk to!\n
			"
			versionPhone
			versionIntPhone
		)
		(TPrint @string
			#font			smallFont
			#mode			teJustCenter
			#width		222
			#title		{A Paid Commercial Announcement}
		)
		(TPrint
		  "You'll also want to order spare copies of all these\n
			other lovable Larry Laffer mis-adventures:\n
			\"Leisure Suit Larry In the Land of the Lounge Lizards,\"\n
		  	\"Leisure Suit Larry Goes Looking for Love\n
			(in Several Wrong Places),\"\n
			\"Leisure Suit Larry 3: Passionate Patti\n
			in Pursuit of the Pulsating Pectorals,\"\n
			\"Leisure Suit Larry 4: The Missing Floppies,\" and\n
			\"The Laffer Utilities,\" a collection of silly little programs
			designed just for fun at the office.\n
			\n
			(Well, maybe you shouldn't order your copies of Larry4 just yet;
			we seem to have misplaced our master disks.)\n
			"
			#font			smallFont
			#mode			teJustCenter
			#width		266
			#title		{More Laffer Laughs}
		)
		(TPrint
		  "Besides the Larry series, Al Lowe has written these
			other fine Sierra games: \"Bop-A-Bet,\"\n
			\"Dragon's Keep,\" \"Troll's Tale,\"\n
			\"The Gelfling Adventure,\" \"The Black Cauldron,\"\n
			\"Winnie the Pooh in the Hundred Acre Woods,\"\n
			and \"Donald Duck's Playground.\"\n
			"
			#font			smallFont
			#mode			teJustCenter
			#width		234
			#title		{Discontinued Products}
		)
	)
)


(instance cycleIcon of DCIcon
	(properties
		view			vAboutIcon
		cycleSpeed	15
	)
	(method (init)
		((= cycler (Forward new:)) init: self)
	)
)
