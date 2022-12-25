;**
;**	Logics for Room 385 -- Dying in First Class
;**
;**	LSL5:  Passionate Patti Does A Little Undercover Work
;**				Copyright 1991
;**				by Al Lowe
;**
;**	Programmed by Carlos Escobar
;**
;**	Last Update:  September 10, 1991
;**

(script#		385)

(public
	rm385		0
)

(define	sfxPlaneDive		386)
(define	sfxScreams			387)
(define	mFalling				390)

(local
	cueCounter
	step
	myTicks
	nonPerfect
)

(instance rm385 of LLRoom
	(properties
		picture		385
		style			SCROLLDOWN
	)

	(method (init)
		(super init:)

		(LoadMany SOUND sfxScreams)

		(= step 10)

	  	(cloud
	  		init:,
	  		setStep:			step step,
	  		posn:				(Random 250 255) 199,
	  		setMotion:		MoveTo (cloud x?) 14 cloud,
	  	)
		(body
			init:,
			setCycle:	Forward,
		)
		(head
			init:,
			setCycle:	Forward,
		)
		(theMusic
			number:				sfxPlaneDive,
			loop:					-1,
			play:
		)
		(theMusic2
			number:				mFalling,
			loop:					-1,
			play:					curRoom,
		)
		(theMusic3
			number:				sfxScreams,
			loop:					-1,
			play:					,
		)
		(curRoom setScript: sDyingInFirstClass)	;just another name for roomScript!
		(SetFFRoom SAMEROOM curRoom)
	);init

	(method (cue)
		(curRoom newRoom: 390)
	)

	(method (newRoom)
		(theTimer dispose:, delete:)
		(super newRoom: &rest)
	)
)

(instance cloud of Actor
	(properties
		view			385
		loop			0
		cel			0
		x				255
		y				189
		priority		1
		signal		(| fixedLoop fixPriOn ignrAct)
	)
	(method (cue)
		(switch (++ cueCounter)
			(1
				(Delay (Random 3 7) CYCLES self)
			)
			(2
				(if (< step 44)
					(+= step 2)
				)
	  			(cloud
	  				setStep:			step step,
	  				posn:				(Random 250 255) 199,
	  				setMotion:		MoveTo (cloud x?) 14 self,
	  			)
				(= cueCounter 0)
			)
		)
	);cue
)
(instance head of Prop
	(properties
		view			386
		loop			1
		cel			0
		x				215
		y				78
		priority		14
		signal		(| fixedLoop fixPriOn ignrAct)
	)
)
(instance body of Prop
	(properties
		view			386
		loop			0
		cel			0
		x				173
		y				146
		priority		10
		signal		(| fixedLoop fixPriOn ignrAct)
	)
)

(instance theMusic3 of Sound)

(instance sDyingInFirstClass of Script
	(method (changeState newState)
		(switchto (= state newState)
			(
				(HandsOff)
;SRC				(Bset pfSawTitty)			  	for testing purposes
;SRC				(Bset pfGotCharger)
;SRC				(Bset pfGotVideotapes)
;SRC				(Bset pfDegaussedTapes)
;SRC				(Bset pfOpenedDayTrotter)
;SRC				(Bset pfReadMagazine)
;SRC				(Bset pfReturnedSkates)
;SRC				(Bset pfTookDoily)
;SRC				(Bset pfFinishedSurvey)
;SRC				(Bset pfSawCCtits)
;SRC				(Bset pfHadSexCC)
;SRC				(Bset pfRecordedCC)
;SRC				(Bset pfHadSexMM)
;SRC				(Bset pfRecordedMM)
;SRC				(Bset pfHadSexLL)
;SRC				(Bset pfRecordedLL)
				(= seconds 6)
			)
			(										;This is the code that Larry thinks
				(TPrint "Hey! This is no dream!
					Your airliner is actually falling out of control!"
					#dispose: self
				)
			)
			(
				(= ticks 30)
			)
			(
				(TPrint "As your flight plummets from the sky,
					your life passes before your eyes."
					#dispose: self
				)
			)
			(
				(= ticks 30)
			)
			(
				(TPrint "For a while, you consider that redhead you dated
					during high school, wondering if there could have been
					some reason she wanted you to come over to her house
					that afternoon when her parents were out of town?"
					#dispose: self
				)
			)
			(
				(= ticks 30)
			)
			(
				(TPrint "But then you consider your performance since you
					were back at PornProdCorp...."
					#dispose: self
				)
			)
			(
				(= ticks 30)
				(= register pfFirstFlag)
			)
			(
				(if (Btst register)
					(= cycles 1)
					(= myTicks 2)
				else
					(switch register
						(pfSawTitty
							(Say ego "\"Was there an award back at good ol'
								PornProdCorp?
								I wish I had looked around more while I was there!\""
								#dispose:	self
							)
							(= nonPerfect TRUE)
							(= myTicks 30)
						)
						(pfGotCharger
							(Say ego "\"How I wish I had taken the charger
								for my camcorder!\""
								#dispose:	self
							)
							(= nonPerfect TRUE)
							(= myTicks 30)
						)
						(pfGotVideotapes
							(Say ego "\"What if I would have brought along some
								videotapes from PornProdCorp?
								I could have recorded those women I saw!\""
								#dispose:	self
							)
							(= nonPerfect TRUE)
							(= myTicks 30)
						)
						(pfDegaussedTapes
							(Say ego "\"Boy, I wish I had degaussed those three
								videotapes before I left my office.\""
								#dispose:	self
							)
							(= nonPerfect TRUE)
							(= myTicks 30)
						)
						(pfOpenedDayTrotter
							(Say ego "\"I wonder what was inside that
								DayTrotter\05 I found in that
								New York City limousine?\""
								#dispose:	self
							)
							(= nonPerfect TRUE)
							(= myTicks 30)
						)
						(pfReadMagazine
							(Say ego "\"And how about that AeroDork Airline magazine?
								What was in that?\""
								#dispose:	self
							)
							(= nonPerfect TRUE)
							(= myTicks 30)
						)
						(pfReturnedSkates
							(Say ego "\"I wonder how much the overdue charges
								are on those roller-skates?\""
								#dispose:	self
							)
							(= nonPerfect TRUE)
							(= myTicks 30)
						)
						(pfTookDoily
							(Say ego "\"Was there anything in that dentist's office
								I missed?\""
								#dispose:	self
							)
							(= nonPerfect TRUE)
							(= myTicks 30)
						)
						(pfFinishedSurvey
							(Say ego "\"Did I ever finish that dentist's
								receptionist's survey?\""
								#dispose:	self
							)
							(= nonPerfect TRUE)
							(= myTicks 30)
						)
						(pfSawCCtits
							(Say ego "\"I wish there had been some way I could
								have seen `more' of Chi Chi Lambada!\""
								#dispose:	self
							)
							(= nonPerfect TRUE)
							(= myTicks 30)
						)
						(pfHadSexCC
							(Say ego "\"That Chi Chi Lambada was so easy!
								I wonder what making love to a gymnast would have
								been like?\""
								#dispose:	self
							)
							(= nonPerfect TRUE)
							(= myTicks 30)
						)
						(pfRecordedCC
							(Say ego "\"Why didn't I record my encounter with
								Chi Chi Lambada?\""
								#dispose:	self
							)
							(= nonPerfect TRUE)
							(= myTicks 30)
						)
						(pfHadSexMM
							(Say ego "\"Why didn't I pursue Michelle Milken harder?
								She seemed so easy to get to know!\""
								#dispose:	self
							)
							(= nonPerfect TRUE)
							(= myTicks 30)
						)
						(pfRecordedMM
							(Say ego "\"Why didn't I record my encounter with
								Michelle Milken?\""
								#dispose:	self
							)
							(= nonPerfect TRUE)
							(= myTicks 30)
						)
						(pfHadSexLL
							(Say ego "\"I bet if I had gone to see Lana Luscious
								mud wrestle, I could have gotten a kiss!\""
								#dispose:	self
							)
							(= nonPerfect TRUE)
							(= myTicks 30)
						)
						(pfRecordedLL
							(Say ego "\"I could have recorded my mud wrestling
								session with Lana Luscious.\""
								#dispose:	self
							)
							(= nonPerfect TRUE)
							(= myTicks 30)
						)
						(else
							(= myTicks 2)
							(= cycles 1)
						)
					)
		 		)
			)
			(
				(if ( < (++ register) pfNextFlag)
					(-= state 2)
				)
				(= ticks myTicks)
			)
			(
				(if nonPerfect
					(= cycles 1)
				else
					(Say ego "You think to yourself,
						\"I did pretty dog-gone well this time,
						even if I do have to say so myself!\""
						#dispose: self
					)
				)
			)
			(
				(= ticks 60)
			)
			(
				(TPrint "All in all, you wish you had accepted that redhead's
					offer!"
					#dispose: self
				)
			)
			(
				(= ticks 60)
			)
			(
				(TPrint "The loudspeaker crackles with a panic-stricken voice,
					\"Any passengers with flight experience are urged to report
					to the cockpit immediately!\""
					WIDELOW
					#title:		{The P. A. System}
					#dispose: 	self
				)
			)
			(
				(= ticks 60)
			)
			(
				(Say ego "\"Hmm,\" you think,
					\"I used to sell flight simulator software door-to-door!
					Maybe I could help?\""
					#dispose: self
				)
			)
			(
				(= ticks 60)
			)
			(
				(Say ego "\"Oh, stewardess,\" you shout,
					\"What's wrong?\""
					#dispose: self
				)
			)
			(
				(= ticks 60)
			)
			(
				(TPrint "She replies, \"Oh, not to worry.
					It's just that the pilot's contract ran out a few moments ago
					and (being a good union man)
					he refuses to work without a contract!\""
					#title:		{The Stewardess}
					#dispose: 	self
				)
			)
			(
				(= ticks 60)
			)
			(
				(TPrint "\"Oh, sir, can you help us?\""
					#title:		{The Stewardess}
					#dispose: 	self
				)
			)
			(
				(= ticks 60)
			)
			(
				(Say ego "\"Well, I'll do the best I can,\" you volunteer.
					\"Did I mention I used to sell flight simulator software
					door-to-door?\""
					#dispose: self
				)
			)
			(
				(= ticks 60)
			)
			(
				(TPrint "\"Oh, that's wonderful, Sir!
					Hurry! Follow me up to... er, down to the cockpit!\""
					#title:		{The Stewardess}
					#dispose: 	self
				)
			)
			(
				(curRoom newRoom: 390)
			)
		)
	)
);sDyingInFirstClass

