;**
;**	Logics for Room 450 -- Dr. Phil Hopian's office
;**
;**	LSL5:  Passionate Patti Does A Little Undercover Work
;**				Copyright 1991
;**				by Al Lowe
;**
;**	Programmed by Oliver Brelsford
;**
;**	Last Update:  September 9, 1991
;**


(script#		450)

(public
	rm450		0
)

(define	pDrLyccusVonPelt'sOffice	450)
(define	vRoom						450)
(define  	lLookingUnder		0)
(define		lRaisingSheet		1)
(define		lSmilingBig			2)
(define		lUsingNeedle		3)
(define  		cNeedle			0)
(define  		cHeadTop			1)
(define	vRoomEgo			 		(+ 1 vRoom))
(define	vVonPeltWithHat		452)
(define		lGettingHat			0)
(define		lGoingUnderSheet	1)
(define		lComingUpW/OHat	2)
(define 			cDownUnder		0)
(define 			cSmileWDrill	1)

(define	vVonPeltTalker				1450)

(define	vPostPelting			458)
	(define	lSmoking				0)
	(define	lMissingHat			1)

(define mVonPelt1					450)
(define mVonPelt2					451)
(define mVonPelt3					452)
(define mVonPelt4					453)
(define mVonPelt5					454)
(define mVonPeltDone				458)
(define sDrill						455)
(define sGirlAh					752)
(define sGirlOh					753)

;**	RoomScript state values

(local
	seenMsg
)


(instance rm450 of LLRoom
	(properties
		picture		450
		west			445
	)
	(method (init)
		(SetFFRoom 445)
		(HandsOff)
		(super init:)
		(self setScript: sRoom)
		(doc init:)
		(sheet init:, hide:)
		(patti
			init:,
			stopUpd:
		)
		(Load SOUND
			sDrill
			sGirlAh
			sGirlOh
		)
	)
)

(instance sRoom of Script
	(method (changeState newState)
		(switchto (= state newState)
			(
				(ego edgeHit: WEST)
				(doc stopUpd:)
				(= seconds 2)
			)
			(
				(Say docTalker
					"\"Hello, Patti, and welcome aboard.
					I'm Dr. Phil Hopian, staff physician and
					volunteer gynecologist around here!\""
					#dispose:
					#caller:		self
				)
			)
			(
				(Say docTalker
					"\"It will be my pleasure to be serving you today.\""
					#dispose:
					#caller:		self
				)
			)
			(
				(doc setCycle: CycleTo 2 1 self)
			)
			(
				(sheet
					show:,
				)
				(doc setCycle: EndLoop self)
			)
			(
				(doc hide:)
				(theMusic
					number:		mVonPelt1,
					setLoop:		1,
					flags:		1,
					play:			self,
				)
			)
			(
				(doc
					show:			,
					setCycle: 	CycleTo 2 -1 self
				)
			)
			(
				(sheet hide:)
				(doc setCycle: BegLoop self)
			)
			(
				(= ticks 60)
			)
			(
				(doc
					setLoop:		lSmilingBig,
					stopUpd:
				)
				(= ticks 60)
			)
			(
				(Say docTalker
					"\"I love this job!!\""
					#dispose:
					#caller:		self
				)
			)
			(
				(= seconds 3)
			)
			(
				(doc
					setLoop:		lLookingUnder,
					stopUpd:
				)
				(= cycles 3)
			)
			(
				(Say docTalker
					"\"Now that you've passed your official departmental checkup
					(with flying colors, I might add),
					it's time for me to install one of our
					lab's latest inventions, our `Safety First
					Field Locator Device.'\""
					#dispose:
					#caller:		self
				)
			)
			(
				(Say ego "\"Is this really necessary, Doctor?\"" LOW)
				(= cycles 1)
			)
			(
				(Say docTalker
					"\"Of course, Patti.
					This device lets our team of highly-trained professional
					trackers follow you wherever you go.\""
					#dispose:
					#caller:		self
				)
			)
			(
				(Say ego "\"Well, okay, I guess. But will this procedure be
					painful?\"" LOW)
				(= ticks 60)
			)
			(
				(Say docTalker
					"\"Of course not, Patti.... Trust me!\""
					#dispose:
					#caller:		self
				)
			)
			(
				(TPrint "(You've heard that line before!)" LOW)
				(= cycles 10)
			)
			(
				(doc
					setLoop:		lUsingNeedle,
					setCel:		0,
				)
				(= seconds 3)
			)
			(
				(Say docTalker
					"\"Now just relax, Patti;
						you'll only feel a little prick.\""
					#dispose:
					#caller:		self
				)
			)
			(
				(= seconds 2)
			)
			(
				(TPrint "(A feeling not wholly unfamiliar to you!)" LOW)
				(= seconds 2)
			)
			(
				(doc
					setCycle:	EndLoop self
				)
				(sheet
					view:			452,
					show:			,
				)
			)
			(
				(theMusic
					number:		mVonPelt2,
					setLoop:		1,
					flags:		1,
					play:			self,
				)
			)
			(
				(ShakeScreen 6 1)
				(if
					(DoSound NumDACs)
					(soundFX
						number:		sGirlAh,
						play:
					)
				)
				(= seconds 2)
			)
			(
				(doc
					setCycle:	BegLoop self
				)
			)
			(
				(doc stopUpd:)
				(sheet stopUpd:)
				(= cycles 3)
			)
			(
				(Say docTalker
					"\"Oh, I'm sorry; let me warm that for you first.\""
					#dispose:
					#caller:		self
				)
			)
			(
				(= seconds 3)
			)
			(
				(sheet startUpd:)
				(doc
					setCycle:	EndLoop self
				)
			)
			(
				(theMusic
					number:		mVonPelt3,
					setLoop:		1,
					flags:		1,
					play:			self,
				)
			)
			(
				(if
					(DoSound NumDACs)
					(soundFX
						number:		sGirlOh,
						play:
					)
				)
				(ShakeScreen 6 3)
				(= seconds 2)
			)
			(
				(doc
					setCycle:	BegLoop self
				)
			)
			(
				(doc stopUpd:)
				(sheet stopUpd:)
				(= seconds 2)
			)
			(
				(Say docTalker
					"\"Oops! Perhaps you'd prefer something in a D-width?\""
					#dispose:
					#caller:		self
				)
			)
			(
				(= seconds 2)
			)
			(
				(sheet startUpd:)
				(doc
					setCycle:	EndLoop self
				)
			)
			(
				(theMusic
					number:		mVonPelt4,
					setLoop:		1,
					flags:		1,
					play:			self,
				)
			)
			(
				(sheet
					view:			vRoom,
					show:			,
				)
				(doc
					setLoop:		lLookingUnder,
					setCel:		255,
					setCycle:	CycleTo 2 -1 self
				)
			)
			(
				(sheet hide:)
				(doc
					setCycle:	BegLoop self
				)
			)
			(
				(doc stopUpd:)
				(= cycles 3)
			)
;**				(
;**					(Say docTalker
;**						"\"Now just relax, Patti;
;**							you'll only feel a little prick.\""
;**						#dispose:
;**						#caller:		self
;**					)
;**				)
;**				(
;**					(= seconds 2)
;**				)
;**				(
;**					(TPrint "(A feeling not wholly unfamiliar to you!)" LOW)
;**					(= seconds 2)
;**				)
			(
				(doc
					view:			vVonPeltWithHat,
					setLoop:		lGettingHat,
					setCel:		0,
					y:				(+ (doc y?) 2),
					setCycle:	CycleTo 8 1 self
				)
			)
			(
				(= seconds 2)
			)
			(
				(doc
					x:				166,
					y:				126,
					setCycle:	EndLoop self
				)
			)
			(
				(sheet
					view:			452,
					show:
				)	;new
				(= ticks 67)
			)
			(
				(soundFX
					number:		sDrill,
					play:
				)
				(= ticks 350)
			)
			(
				(doc
					y:				(- (doc y?) 2),
					setLoop:		lComingUpW/OHat,
					setCel:		cDownUnder,
				)
				(= seconds 2)
			)
			(
				(sheet hide:)	;new
				(doc
					setCel:		cSmileWDrill
				)
				(= seconds 3)
			)
			(
				(doc
					setCel:		cDownUnder
				)
				(sheet show:)
				(= cycles 10)
			)
			(
				(doc hide:)
				(= cycles 10)
			)
			(
				(theMusic
					number:		mVonPelt5,
					setLoop:		1,
					flags:		1,
					play:			self,
				)
				(sheet dispose:)
				(patti dispose:)
				(curRoom
					style:		IRISIN,
					drawPic:		pBlackPic
				)
				(= seconds 3)
			)
			(
				(CenterDisplay 2 colWhite
					"Two hours later..."
				)
				(= seconds 3)
			)
;**				(
;**					(curRoom
;**						style:		IRISOUT,
;**						drawPic:		(curRoom picture?),
;**					)
;**					(doc
;**						x:				155,
;**						y:				95,
;**						show:			,
;**						view:			vPostPelting,
;**						setLoop:		lSmoking,
;**						setCel:		0,
;**						cycleSpeed:	18,
;**						setCycle:	EndLoop self
;**					)
;**				)
;**				(
;**					(theMusic
;**						number:		mVonPelt3,
;**						setLoop:		1,
;**						flags:		1,
;**						play:
;**					)
;**					(= seconds 3)
;**				)
;**				(
;**					(doc
;**						setLoop:		lMissingHat,
;**						setCel:		0,
;**					)
;**					(= seconds 1)
;**				)
;**				(
;**					(doc
;**						setCycle:	EndLoop self
;**					)
;**				)
;**				(
;**					(doc
;**						setCycle:	BegLoop
;**					)
;**					(= seconds 2)
;**				)
			(
				(HandsOff)
				(curRoom newRoom: (curRoom west?))
			)
		)
	)
);sRoom


;ษอออออออออออออออออออป
;บ                   บ
;บ Props					บ
;บ                   บ
;ศอออออออออออออออออออผ

(instance doc of Prop
	(properties
		view			vRoom
		loop			lLookingUnder
		cel			0
		x				167
		y				98
		cycleSpeed	30
	)
)

(instance sheet of View
	(properties
		view			vRoom
		loop			lRaisingSheet
		x				166
		y				98
		priority		12
		signal		(| fixPriOn ignrAct)
	)
)

(instance patti of View
	(properties
		view			vRoomEgo
		x				162
		y				149
	)
)


;ษอออออออออออออออออออป
;บ                   บ
;บ Talkers           บ
;บ                   บ
;ศอออออออออออออออออออผ

(instance docTalker of Talker
	(properties
		name			"Dr. Phil Hopian"
		view			vVonPeltTalker
		loop			1
		nsLeft		145
		nsTop			54
		x				30
		y				5
		talkWidth	258
	)
	(method (init)
;**			(= bust 	docBust)
 		(= eyes 	docEyes)
		(= mouth docMouth)
		(super init: &rest)
	)
)

;**	(instance docBust of Prop
;**		(properties
;**			view			vVonPeltTalker
;**			loop			1
;**			nsLeft		0
;**			nsTop			0
;**		)
;**	)

(instance docEyes of Prop
	(properties
		view			vVonPeltTalker
		loop			2
		nsLeft		13
		nsTop			18
		cycleSpeed	30
	)
)

(instance docMouth of Prop
	(properties
		view			vVonPeltTalker
		loop			0
		nsLeft		14
		nsTop			21
		cycleSpeed	8
	)
)

(instance soundFX of Sound
	(properties
		flags		1
	)
)
