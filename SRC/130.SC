;**
;**	Logics for Room 130 -- Mob Boss's Closeup
;**
;**	LSL5:  Passionate Patti Does A Little Undercover Work
;**				Copyright 1991
;**				by Al Lowe
;**
;**	Programmed by Brian K. Hughes & Carlos Escobar & John Hartin
;**
;**	Last Update:  July 22, 1991
;**

(script#		130)

(public
	rm130		0
)


(define	vRoom							130)
(define	vRoomEgo			 (+ 1 vRoom))

(define	pMrBiggCloseUp					130)
(define	vBiggFace					1130)

(define	pPresidentPornProdCorp		140)
(define mMobOfficeHit	130)
(define mMobOffice	120)
(define mPornProdOffice	140)

(define  tTalkLoop	 	3)
(define  tBustLoop	 	1)
(define  tEyesLoop	 	2)
(define  tMouthLoop		0)
(define	BIGGLEFT			111)
(define	BIGGTOP			79)
(define	BIGX				-1)
(define	BIGY				10)


(local
	seenMsg
	textBox
	cycleTime
)


(instance rm130 of LLRoom
	(properties
		picture		pMrBiggCloseUp
	)

	(method (init)
		(LoadMany VIEW
			vRoom
			vBiggFace					
		)
		(LoadMany SOUND
			mMobOfficeHit
			mPornProdOffice	
		)
		(HandsOff)
		(cigar init:, setPri: 15, addToPic:)
		(vMouth init:, setPri: 13, addToPic:)
		(brow1 init:, addToPic:)
		(brow2 init:, addToPic:)
		(addToPics doit:)
		(super init:)
		(SetFFRoom 155)
		(theMusic
			number:		mMobOfficeHit,
			flags:		TRUE,
			loop:			0,
			play:
		)
		(self setScript: sCartoon)
		(keyDownHandler add: sCartoon)
		(mouseDownHandler add: sCartoon)
	)

	(method (dispose)
		(mouseDownHandler delete: sCartoon)
		(keyDownHandler	delete: sCartoon)
		(super dispose:)
		(theMusic
			fade:	0 15 12 TRUE
		)
	)
)

(instance vMouth of View
	(properties
		view			vBiggFace
		loop			0
		cel			0
		x				154
		y				129
	)
)
(instance brow1 of View
	(properties
		view			vRoom
		loop			0
		cel			0
		x				127
		y				53
	)
)
(instance brow2 of View
	(properties
		view			vRoom
		loop			1
		cel			0
		x				179
		y				55
	)
)

(instance cigar of View
	(properties
		view			130
		loop			3
		cel			0
		x				201
		y				104
	)
)

(instance officeHit of Sound
	(properties
		number	mMobOfficeHit
	)
)

(instance biggTalker of Talker
	(properties
		name			"Mr. Bigg"
		view			vBiggFace
		loop			tTalkLoop
		nsLeft		BIGGLEFT
		nsTop			BIGGTOP
		x				BIGX
		y				BIGY
		priority		14
		signal		fixPriOn
		talkWidth	280
	)
	(method (init)
		(= mouth biggMouth)
		(super init: &rest)
	)
	(method (doit)
		(super doit:)
		(++ cycleTime)
		(if 
			(not (mod cycleTime 32))
			(Palette PALCycle 206 254 -1) ;fromWhat toWhat direction
		)
	)
	(method (show &tmp pnv)
		(if (not underBits)
			(= underBits (Graph GSaveBits nsTop nsLeft nsBottom nsRight VMAP))
		)
		(= pnv (PicNotValid))
		(PicNotValid TRUE)
		(if bust
			(DrawCel	
				(bust view?) (bust loop?) (bust cel?) 
				(+ (bust nsLeft?) nsLeft) (+ (bust nsTop?) nsTop)
				14			;arbitrary number to get my cigar uncovered
			)
		)
		(if eyes
			(DrawCel	
				(eyes	view?) (eyes loop?) (eyes cel?)	
				(+ (eyes nsLeft?) nsLeft) (+ (eyes nsTop?) nsTop)
				14			;arbitrary number to get my cigar uncovered
			)
		)
		(if mouth
			(DrawCel	
				(mouth view?) (mouth loop?) (mouth cel?)	
				(+ (mouth nsLeft?) nsLeft) (+ (mouth nsTop?) nsTop)
				14			;arbitrary number to get my cigar uncovered
			)
		)
		(DrawCel view loop cel nsLeft nsTop 10)
		(Graph GShowBits nsTop nsLeft nsBottom nsRight VMAP)
		(PicNotValid pnv)
	)
	(method (cycle obj &tmp theCel)
		;; call cycler doit and redraw obj if it has changed
		(if (and obj (obj cycler?))
			(= theCel 	(obj cel?))
			((obj cycler?)		doit:)
			(if (!= theCel (obj cel?))
				(DrawCel	
					(obj	view?)
					(obj	loop?)
					(obj	cel?)	
					(+ (obj nsLeft?) nsLeft)
					(+ (obj nsTop?) nsTop)
					14		;arbitrary number to get my cigar uncovered
				)
				(obj 
					nsRight: 
						(+ 
							(obj nsLeft) 
							(CelWide (obj view?) (obj loop?)  (obj cel?))
						)
				)
				(obj 
					nsBottom: 
						(+ 
							(obj nsTop) 
							(CelHigh (obj view?) (obj loop?)  (obj cel?))
						)
				)
		 		(Graph GShowBits 
					(+ (obj nsTop?) 		nsTop)
					(+ (obj nsLeft?) 	nsLeft)
					(+ (obj nsBottom?) 	nsTop)
					(+ (obj nsRight?) 	nsLeft)
					VMAP
				)
			)
		)
	)
)

(instance biggMouth of Prop
	(properties
		view			vBiggFace
		loop			tMouthLoop
		nsLeft		0
		nsTop			0
		cycleSpeed 	10
		priority		14
		signal		fixPriOn
	)
)

(instance sCartoon of Script
	(method (changeState newState)
		(switchto (= state newState)
			(
				(= cycles 2)
			)
			(
				(Say biggTalker "\"I understand the problem, but I want a solution!\""
			  		#dispose:
					#caller:		self
				)
			)
			(
				(= seconds 3)
			)
			(
				(theIconBar disable:)
				(DrawPic pBlackPic (+ IRISIN BLACKOUT))
				(= seconds 3)
			)
			(
				(theIconBar enable:)
				(CenterDisplay 3 colWhite
					"Six months later,\n
					and a continent away,\n
					another Board of Directors meets..."
				)
				(= seconds 3)
			)
			(
				(curRoom newRoom: pPresidentPornProdCorp)
			)
		)
	)
	(method (handleEvent event &tmp evType dir)
		;** get current position of mouse cursor
		;; the iconbar needs to handle all (even null) events
		(if 
			(and 
				(== (self state?) 4)
				(or 
					(== (event type?)	keyDown)
					(== (event type?)	mouseDown)
				)
			)
				(event claimed:	TRUE)
				(self changeState: 5)
		)
	)
);sCartoon


