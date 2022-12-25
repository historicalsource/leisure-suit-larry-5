;**
;**	Logics for Room 750 -- Mud Wrestling Medium Shot
;**
;**	LSL5:  Passionate Patti Does A Little Undercover Work
;**				Copyright 1991
;**				by Al Lowe
;**
;**	Programmed by Brian K. Hughes
;**
;**	Last Update:  September 10, 1991
;**


(script#		750)

(public
	rm750		0
)


(define	pMudWrestlingMediumShot		750)
(define	vRoom							750)
(define		lAudienceMembers	0-4)
(define		lAudienceOohing	5-8)
(define	vLarryLanaRoom				751)
(define		lGettingBetter		0-6)
(define	vFlyingClothing			752)
(define		lShirt				0)
(define		lPants				1)
(define		lJacket				2)
(define	vLanaSittingUp				753)
(define		lSittingUp			0)
(define		lLookAtBucephalus	1)
(define		lLookAtEachOther	2)
(define		lLookAtCamera		3)
(define	vLarrySittingUp			754)
;(define		lLookAtBucephalus	0)
;(define		lLookAtEachOther	1)
;(define		lLookAtCamera		2)

(define	mCan'tGetItUp					750)
(define  sZwoosh!					862)
(define	sCrowdGasp				751)

(local
	shirtPts =
	[
		0	0	183	137
		0	1	220	111
		0	2	258	101
		0	3	287	117
		0	4	319 	131
		0	4	340 	151
		0	4	360 	151
		PATHEND
	]

	pantsPts =
	[
		1 	0 	155 	137
		1 	1 	133	115
		1 	2 	104	102
		1 	3	61		108
		1	4	42		119
		1 	5 	12		141
		1 	5 	-5		155
		1 	5 	-35	155
		PATHEND
	]

	jacketPts =
	[
		2 	0 	157	143
		2 	1 	177	101
		2 	2 	206	73
		2 	3 	234	54
		2 	4 	264	49
		2 	5 	285	41
		2 	5 	305	35
		2 	5 	325	25
		2 	5 	345	15
		PATHEND
	]
)


(instance rm750 of LLRoom
	(properties
		picture		750
	)

	(method (init)
		(Load SCRIPT MOVECYC)
		(LoadMany VIEW
			vLanaSittingUp
			vLarrySittingUp
			vFlyingClothing
		)
		(LoadMany SOUND
			mCan'tGetItUp
			sZwoosh!
			sCrowdGasp
		)
		(ego
			init:	  		,
			x:				125,
			y:				130,
			view:	  		vLarrySittingUp,
			setLoop:		lSittingUp,
			setCycle:	0
		)
		(super init:)
		(lana init:)
		(dyke init:, stopUpd:)
		(guy1 init:, stopUpd:)
		(guy2 init:, stopUpd:)
		(guy3 init:, stopUpd:)
		(guy4 init:, stopUpd:)
		(guy5 init:, stopUpd:)
		(guy6 init:, stopUpd:)
		(guy7 init:, stopUpd:)
		(guy8 init:, stopUpd:)
		(self setScript: sCartoon)
		(HandsOff)
	)
)


;ษอออออออออออออออออออป
;บ                   บ
;บ Scripts           บ
;บ                   บ
;ศอออออออออออออออออออผ

(instance sCartoon of Script
	(method (changeState newState)
		(switchto (= state newState)
			(
				(= ticks 123)
			)
			(
				(Points ptsHadSexLL pfHadSexLL)
				(TPrint "As you become more and more aggressive,
					Lana becomes more and more passionate,
					until the two of you,
					oblivious to the hundreds of men staring at you,
					roll off the ring onto the floor and begin shedding clothes!"
					BIG
				)
				(= ticks 123)
			)
			(
				(soundFX
					number:		sZwoosh!,
					flags:		1,
					play:			guy1
				)
				(clothes
					init:			,
					setCycle:	MoveCycle @jacketPts self
				)
			)
			(
				(clothes hide:)
				(guy8 setCycle: EndLoop self)
			)
			(
				(guy8 stopUpd:)
				(dyke setCycle: EndLoop self)
			)
			(
				(dyke stopUpd:)
				(guy7 setCycle: EndLoop self)
			)
			(
				(guy7 stopUpd:)
				(guy1 setCycle: EndLoop self)
			)
			(
				(guy1 stopUpd:)
				(soundFX
					number:		sZwoosh!,
					play: 		guy1
				)
				(clothes
					show:			,
					setCycle:	MoveCycle @shirtPts self
				)
			)
			(
				(guy3 setCycle: EndLoop self)
				(clothes hide:)
			)
			(
				(guy3 stopUpd:)
				(guy2 setCycle: EndLoop self)
			)
			(
				(guy2 stopUpd:)
				(guy4 setCycle: EndLoop self)
			)
			(
				(guy4 stopUpd:)
				(soundFX
					number:		sZwoosh!,
					play: 		guy1
				)
				(clothes
					show:			,
					setCycle:	MoveCycle @pantsPts self
				)
			)
			(
				(clothes dispose:)
				(guy5 setCycle: EndLoop self)
			)
			(
				(guy5 stopUpd:)
				(guy6 setCycle: EndLoop self)
			)
			(
				(guy6 stopUpd:)
				(guy7 setCycle: EndLoop self)
			)
			(
				(guy7 stopUpd:)
				(guy8 setCycle: EndLoop self)
			)
			(
				(guy8 stopUpd:)
				(dyke setCycle: EndLoop self)
			)
			(
				(dyke stopUpd:)
				(theMusic
					loop:			1,
					number:		mCan'tGetItUp,
					play:,
				)
				(ego 	setCycle: EndLoop self)
			)
			(
				(ego 	stopUpd:)
				(lana setCycle: EndLoop self)
			)
			(
				(lana stopUpd:)
				(guy1 setCycle: EndLoop self)
			)
			(
				(guy1 stopUpd:)
				(guy3 setCycle: EndLoop self)
			)
			(
				(guy3 stopUpd:)
				(ego
					setLoop:		lLookAtBucephalus,
					setCel:		0,
					setCycle:	CycleTo 4 1 self
				)
				(lana
					setCel:		0,
					setLoop:		1,
					setCycle:	EndLoop
				)
			)
			(
				(= seconds 5)
			)
			(
				(TPrint "How embarrassing!" HIGH)
				(ego setCycle: EndLoop self)
			)
			(
				(ego
					setLoop:		lLookAtEachOther,
					setCel:		0,
					setCycle:	EndLoop self
				)
				(lana
					setCel:		0,
					setLoop:		2,
					setCycle:	EndLoop
				)
			)
			(
				(= ticks 123)
			)
			(
				(TPrint "\"Oh Lana, I just don't understand!
					I suppose it's because Bucephalus has never performed
					in front of 900 people before!\""
					#title:	{You}
					BIG
				)
				(= ticks 123)
			)
			(
				(if camcorderOn
					(RecordTape tapeRecordedLL)
					(Points ptsRecordedLL pfRecordedLL)
					(TPrint "Now you're sorry you even started that camcorder.
						What will the boys back in Hollywood think
						of your less-than-stellar performance?"
						BIG
					)
				else
					(TPrint "You're so glad you didn't get this on videotape!
						That's all you need--more humiliation back at the office!"
						BIG
					)
				)
				(ego
					setLoop:		lLookAtCamera,
					setCel:		0,
					setCycle:	EndLoop
				)
				(= seconds 5)
			)
			(
				(DrawPic pBlackPic IRISIN)
				(cast eachElementDo: #hide:)
				(= seconds 3)
			)
			(
				(TPrint "You slink out of the ballroom to the hisses and boos
					of hundreds of men, each of which is thinking,
					\"Thank God that wasn't me!\"")
				(= seconds 3)
			)
			(
				(curRoom newRoom: 700)
			)
		)
	)
);sCartoon


;ษอออออออออออออออออออป
;บ                   บ
;บ Objects           บ
;บ                   บ
;ศอออออออออออออออออออผ

(instance lana of Prop
	(properties
		view 			vLanaSittingUp
		x 				185
		y 				130
 		description "Lana"
		lookStr 		"How Lana has changed!
						Outside, she was just a beautiful young lady.
						In here, she's an animal!"
		sightAngle 	40
		signal		ignrAct
	)
)
(instance clothes of Prop
	(properties
		view			vFlyingClothing
		loop			2
		x				150
		y				150
		xStep			13
		yStep			13
		signal		ignrAct
		cycleSpeed	8
	)
)
(instance dyke of Prop
	(properties
		view			750
		x				143
		y				84
		cycleSpeed	6
		signal		ignrAct
	)
)
(instance guy1 of Prop
	(properties
		view			750
		loop			1
		x				171
		y				81
		cycleSpeed	7
		signal		ignrAct
	)
	(method (cue)
		(super cue:)
		(soundFX
			number:		sCrowdGasp,
			play:
		)
	)
)
(instance guy2 of Prop
	(properties
		view			750
		loop			2
		x				165
		y				46
		cycleSpeed	8
		signal		ignrAct
	)
)
(instance guy3 of Prop
	(properties
		view			750
		loop			3
		x				213
		y				45
		cycleSpeed	9
		signal		ignrAct
	)
)
(instance guy4 of Prop
	(properties
		view			750
		loop			4
		x				85
		y				64
		cycleSpeed	8
		signal		ignrAct
	)
)
(instance guy5 of Prop
	(properties
		view			750
		loop			5
		x				102
		y				82
		cycleSpeed	7
		signal		ignrAct
	)
)
(instance guy6 of Prop
	(properties
		view			750
		loop			6
		x				181
		y				59
		cycleSpeed	6
		signal		ignrAct
	)
)
(instance guy7 of Prop
	(properties
		view			750
		loop			7
		x				13
		y				77
		cycleSpeed	7
		signal		ignrAct
	)
)
(instance guy8 of Prop
	(properties
		view			750
		loop			8
		x				20
		y				114
		cycleSpeed	10
		signal		ignrAct
	)
)
(instance soundFX of Sound)
