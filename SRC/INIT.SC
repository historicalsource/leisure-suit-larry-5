;**
;**	Logics for Game Init -- called from 000
;**
;**	LSL5:  Passionate Patti Does A Little Undercover Work
;**				Copyright 1991
;**				by Al Lowe
;**
;**	Programmed by Brian K. Hughes
;**
;**	Last Update:  September 3, 1991
;**


(script# INIT)

(public
	ll5InitCode	0
)

(define	gcWin		(ScriptID 0 20))
(define 	ll5Win	(ScriptID 0 21))
(define	invWin	(ScriptID INVENTORY 1))

(instance ll5InitCode of Code
	(method (init &tmp cfgHandle)
		; We use bordered window
		(= systemWindow ll5Win)

		; set up color globals
		(ColorInit)

		(= useSortedFeatures TRUE)
		(= eatMice 30)

		; DEBUG - clear logger path initially so it will prompt
		(StrCpy @sysLogPath {})

		; set up initial move speed & put the cursor down in right corner
		(theGame
			egoMoveSpeed:	6,
			setCursor:		theCursor TRUE 304 172,
			detailLevel: 	5
		)

		(= waitCursor 		997)
		(= possibleScore 	1000)
		(= userFont 		2107)
		(= bigFont			2108)
		(= smallFont		1207)
		(= monoFont			2407)
		(= giantFont		4115)
		(= niceFont			2510)

		(= textSpeed 12)

		; find out what sound capability they have
		(= numVoices (DoSound NumVoices))

		; find out what graphic capability they have
		(= numColors (Graph GDetect))
		(if (and
				(>= numColors 2)
				(<= numColors 16)
			 )
			(Bclr isVga)
		else
			(Bset isVga)
		)

		; figure out what colors to use for sys, control panel, & inv windows
		(systemWindow
			color:			0,
			back:				colWindow,
		)
		(invWin
			color:			0,
			back:				colWindow,
			topBordColor:	colWindow,
			lftBordColor:	colWindow,
			rgtBordColor:	colWindow,
			botBordColor:	colWindow,
			insideColor:	colDBlue,
			topBordColor2:	colMagenta,
			lftBordColor2:	colMagenta,
			botBordColor2:	colLMagenta,
			rgtBordColor2:	colLMagenta,
		)
		(gcWin
			color:			0,	; text
			back:				colWindow
		)

		; set up inventory
		((ScriptID INVENTORY 0) init:)

		(UnLoad SCRIPT COLOR)
	)
)
