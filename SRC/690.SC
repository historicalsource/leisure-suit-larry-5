;**
;**	Logics for Room 690 -- Radar Men
;**
;**	LSL5:  Passionate Patti Does A Little Undercover Work
;**				Copyright 1991
;**				by Al Lowe
;**
;**	Programmed by Steve Conrad
;**
;**	Last Update:  September 10, 1991
;**

(script#		690)

(public
	rm690		0
)


(define	pMeanwhileBackAtFBI			690)
(define	vMeanwhileBackAtFBI		690)
	(define	lSonarScreen		0)
	(define	lFlashingBlip		1)
	(define	lSonarSweep			2)

; Sound define
(define		sSonarPing			691)


(local
	soundPlayed			; for radar ping
)


(instance rm690 of LLRoom
	(properties
		picture		pBlackPic
		style 		SCROLLLEFT
	)
	(method (init)
		(theIconBar disable:)
		(LoadMany PICTURE	pMeanwhileBackAtFBI)
		(LoadMany VIEW vMeanwhileBackAtFBI)
		(LoadMany FONT	niceFont)
		(LoadMany SOUND sSonarPing)
		(super init:)
		(self setScript: sFBI)
	)
)

(instance sFBI of Script
	(method (changeState newState)
		(switchto (= state newState)
			(
				(HandsOff)
				(CenterDisplay 3 colVLRed
				  "Meanwhile,\n
					Back at the F.B.I.'s\n
					Mission Control Headquarters..."
				)
				(= seconds 5)
			)
			(
				(sonarScreen init:)
				(sweep
					init:			,
					setCycle:	Forward
				)
				(blip
					init:			,
					setCycle:	Forward
				)
				(curRoom drawPic: 690 SCROLLLEFT)
				(= seconds 5)
			)
			(
				(theIconBar enable:)
				(TPrint "\"What's she doing now, Agent Smith?\""
					HIGH
					#title:		{Inspector Desmond}
					#dispose: 	self
				)
			)
			(
				(= seconds 3)
			)
			(
				(if (Btst fChampagneSolution)
				  	(TPrint "\"Well, I dunno, Inspector Desmond,
						it's kinda hard to figure out.
						Either I'm picking up a lot of RF interference,
						or she's consuming way too much fluid!\""
						HIGH
						#title:		{Agent Smith}
						#dispose: self
					)
				else
				  	(TPrint "\"Well, I dunno, Inspector Desmond,
						it's kinda hard to figure out.
						Either she's in a tunnel somewhere near Baltimore,
						or she's dilated 3 cm!\""
						HIGH
						#title:		{Agent Smith}
						#dispose: self
					)
				)
			)
			(
				(= seconds 3)
			)
			(
				(Cls)
				(sweep dispose:)
				(blip	dispose:)
				(curRoom drawPic: pBlackPic IRISIN)
				(= seconds 3)
			)
			(
				(if (Btst fChampagneSolution)
					(TPrint "After getting Reverse Biaz totally sloshed
 						(then finishing off the bottle yourself),
						you slip out of the control room,
						through the studio,
						down the elevator
						and back to your limo.")
				else
					(TPrint "After \"sacrificing\" yourself for your country,
						you slip out of the control room,
						through the studio,
						down the elevator
						and back to your limo.")
				)
				(= seconds 3)
			)
			(
				(curRoom newRoom: 200 IRISOUT)
				(self dispose:)
			)
		)
	)
);sFBI



;******** Objects *********

(instance sonarScreen of View
	(properties
		view			vMeanwhileBackAtFBI
		loop			lSonarScreen
		cel			0
		x				163
		y				114
		priority		1
		signal		(| fixPriOn ignrAct)
	)
)
(instance sweep of Prop
	(properties
		view			vMeanwhileBackAtFBI
		loop			lSonarSweep
		cel			0
		x				161
		y				86
		cycleSpeed	12
		priority		12
		signal		fixPriOn
	)
	(method (doit)
		(super doit:)
		(cond
			(cel
				(= soundPlayed FALSE)
			)
			((not soundPlayed)
			  	(sonarPing play:)
				(= soundPlayed TRUE)
			)
		)
	)
)
(instance blip of Prop
	(properties
		view			vMeanwhileBackAtFBI
		loop			lFlashingBlip
		cel			0
		x				170
		y				96
		cycleSpeed	12
		priority		12
		signal		fixPriOn
	)
)
(instance sonarPing of Sound
	(properties
		number  	sSonarPing
	)
)

