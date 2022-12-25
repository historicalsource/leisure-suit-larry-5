;**
;**	Logics for Room 860 -- K-RAP Radio atrium
;**
;**	LSL5:  Passionate Patti Does A Little Undercover Work
;**				Copyright 1991
;**				by Al Lowe
;**
;**	Programmed by John Hartin
;**
;**	Last Update:  September 10, 1991
;**


(script#		860)

(public
	rm860		0
)


(define	pK-RAPRadioAtrium				860)
(define	vK-RAPRadioAtrium			860)
	(define	lSingleCells		0)
		(define	cShowerW/Patti	0)
	(define	lHeadsInWindows 	1-8)
(define	sHeyBaby		861)
(define	sZwoosh!		862)
(define	sElevator	853)

(define	mKRAPRap1	800)
(define	mKRAPRap2	801)
(define	mKRAPRap3	802)


(instance rm860 of LLRoom
	(properties
		picture		pK-RAPRadioAtrium
		south			870
	)
	(method (init)
		(LoadMany VIEW
			vK-RAPRadioAtrium
		)
		(LoadMany SOUND
			sHeyBaby
			sZwoosh!
			sElevator
			mKRAPRap1
			mKRAPRap2
			mKRAPRap3
		)
		(super init:)
		(HandsOff)
		(elevator
			init:,
		)
		(SetFFRoom 870)
		(self setScript: sCartoon)
	)
	(method (doit)
		(switch (elevator y?)
			(32
				(TPrint "Your \"elevator\" descends through the K-RAP atrium,
					past floor after floor of offices.
					At least there are no workers here to see you!
					This is just like a recurring nightmare of yours...." BIG)
				(zwooshSound play:)
				(heads2 init:, setScript: sPeeping)
			)
			(76
				(zwooshSound play:)
				(heads3 init:, setScript: sPeeping)
			)
			(94
				(zwooshSound play:)
				(heads5 init:, setScript: sPeeping)
				(heads7 init:, setScript: sPeeping)
				(heads9 init:, setScript: sPeeping)
			)
			(120
				(zwooshSound play:)
				(heads8 init:, setScript: sPeeping)
				(heads10 init:, setScript: sPeeping)
			)
			(138
				(zwooshSound play:)
				(heads4 init:, setScript: sPeeping)
			)
			(148
				(zwooshSound play:)
				(heads1 init:, setScript: sPeeping)
				(heads6 init:, setScript: sPeeping)
			)
			(178
				(TPrint "Oh, Patti! You're so em-bare-assed!!")
;				(wolfSound play:)
			)
		)
	)
)

(instance elevator of Actor
	(properties
		view			vK-RAPRadioAtrium
		loop			0
		cel			0
		x				153
		y				0
		moveSpeed 	8
	)
)

(instance heads1 of Prop
	(properties
		view			vK-RAPRadioAtrium
		loop			1
		cel			0
		x				116
		y				129
	)
)
(instance heads2 of Prop
	(properties
		view			vK-RAPRadioAtrium
		loop			2
		cel			0
		x				130
		y				23
	)
)
(instance heads3 of Prop
	(properties
		view			vK-RAPRadioAtrium
		loop			3
		cel			0
		x				178
		y				45
	)
)
(instance heads4 of Prop
	(properties
		view			vK-RAPRadioAtrium
		loop			4
		cel			0
		x				188
		y				129
	)
)
(instance heads5 of Prop
	(properties
		view			vK-RAPRadioAtrium
		loop			5
		cel			0
		x				285
		y				53
	)
)
(instance heads6 of Prop
	(properties
		view			vK-RAPRadioAtrium
		loop			6
		cel			0
		x				42
		y				96
	)
)
(instance heads7 of Prop
	(properties
		view			vK-RAPRadioAtrium
		loop			7
		cel			0
		x				12
		y				53
	)
)
(instance heads8 of Prop
	(properties
		view			vK-RAPRadioAtrium
		loop			8
		cel			0
		x				220
		y				98
	)
)
(instance heads9 of Prop
	(properties
		view			vK-RAPRadioAtrium
		loop			1
		cel			0
		x				118
		y				70
	)
)
(instance heads10 of Prop
	(properties
		view			vK-RAPRadioAtrium
		loop			1
		cel			0
		x				83
		y				100
	)
)

(instance zwooshSound of Sound
	(properties
		number	sZwoosh!
		priority 9
	)
)
(instance wolfSound of Sound
	(properties
		number	sHeyBaby
		priority 10
	)
)

(instance sCartoon of Script
	(method (changeState newState)
		(switchto (= state newState)
			(
				(elevator
					setCycle: 	0,
					setLoop: 	0,
					setPri:		pBLUE,
					setMotion: 	MoveTo 153 242 self,
				)
			)
			(
				(curRoom newRoom: 870)
			)
		)
	)
);sCartoon

(instance sPeeping of Script
	(method (changeState newState)
		(switchto (= state newState)
			(
				(client setCycle: EndLoop self)
			)
			(
				(wolfSound play:)
				(client stopUpd:)
				(self dispose:)
			)
		)
	)
);sCartoon


