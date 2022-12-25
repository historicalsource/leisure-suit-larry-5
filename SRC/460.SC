;**
;**	Logics for Room 460 -- End of Game
;**
;**	LSL5:  Passionate Patti Does A Little Undercover Work
;**				Copyright 1991
;**				by Al Lowe
;**
;**	Programmed by Steve Conrad
;**
;**	Last Update:  September 9, 1991
;**

(script#		460)

(public
	rm460		0
)

(local
	cycleColors
)

(define	pWhiteHouseDinnerLongShot	460)
(define	vWhiteHouse					460)
	(define	lCelStuff			0)
		(define	cChairR		0)
		(define 	cChairL		1)
		(define 	cDaisChair	2)
		(define 	cFoldChair	3)
	(define	lRoger				1)
	(define	lSleeper				2)
	(define	lHumphrey			3)
(define	vLarryInWhiteHouse		461)
	(define	lLookingLeft		0)
	(define	lLookingRight		1)
	(define	lTalkingToBigg		2)
	(define	lJumpingQuayle		3)
	(define	lSittingUp			4)
	(define	lBackInChair		5)
	(define	lLookLeftRight		6)
(define	vPattiInWhiteHouse		462)
	(define	lTalkToRight		0)
	(define	lTalkToLeft			1)
	(define	lFiringBra			2)
	(define	lSmokingBra			3)
	(define	lPattiInChair		4)
	(define	lPattiWalkEastSml	5)
	(define	lPattiFaceSouth	6)
	(define 	lPattiElbows		7)
(define	vQuayle						463)
	(define	lFidgeting			0)
	(define	lGettingUp			1)
	(define	lMadAtLarry			2)
	(define	lTalkingWPie		3)
	(define  lPie					4)
	(define	lLookAtMarilyn		5)
(define	vDesmond						464)
	(define	lDesTalking			0)
	(define	lGrabbingGun		1)
	(define	lGrabsBigg			2)
	(define 	lDesmondStanding	3)
	(define	lDesmondWalkWestL	4)
	(define	lDesmondWalkEastS	5)
	(define	lDesmondFaceEast	6)
(define	pBiggAndPatti					465)
(define	vBigg							465)
	(define	lTalkingToPatti	0)
	(define	lDrawingGun			1)
(define	vSSAgent						466)
	(define	lWalkingW/Chair	0)
	(define	lUnfoldsChair		1)
	(define	lWalkingWest		2)
(define	vMarilyn						467)
	(define	lReacting		0)
	(define	lPlacePie		1)

(define	pPattiEmbracesLarry			470)
(define	vHeart						470)
(define	vTalkingHeads				471)
	(define	lPattiFaceTalking	0)
	(define	lLarryFaceTalking	1)
	(define	lLegs					2)

(define	vPattiTalker				1465)
	(define	lMouth				0)
	(define	lEyes					2)
(define 	vBiggTalker					1466)

; Music defines

(define	mDinnerMusic					460)
(define	mPattiHero						461)
(define	mEnding							462)
(define	mLarryBigg						463)
(define		sPieInFace			464)
(define	mPattiBigg						465)
(define	mPullGun							466)
(define		sDinnerFX					467)
(define	mLoveTheme						470)
(define 		sFireBra				434)


(instance rm460 of LLRoom
	(properties
		picture		pWhiteHouseDinnerLongShot
	)

	(method (init)
		(LoadMany PICTURE
			pPattiEmbracesLarry

			pBiggAndPatti
			pBlackPic
		)
		(LoadMany VIEW
			vWhiteHouse
			vLarryInWhiteHouse
			vPattiInWhiteHouse
			vHeart
			vTalkingHeads
			vLarrySmall
			vMarilyn
			vPatti

			vSSAgent
			vPattiTalker
			vBiggTalker
		)
		(LoadMany SOUND
			mLoveTheme

			sPieInFace
			mPattiHero
			mEnding
			mLarryBigg
			mPattiBigg
			mPullGun
		)
		(LoadMany FONT
			niceFont
		)
		(quayle init:)
		(desmond init:)
		(larry
			init:			,
			setCycle:	Walk,
		)
		(bigg init:)
		(pattiChair init:)
		(patDaisChair init:)
		(desDaisChair init:)
		(marilyn init:)
		(roger init:)
		(humphrey init:)
		(sleeper init:)
		(ego
			init:			,
			normalize:	vPattiInWhiteHouse,
			get:			iBra,		;need this to shoot Bigg (if ff thru 380)
			setCycle:	0,
			setLoop:		lPattiInChair,
			setCel:		0,
			cycleSpeed:	5,
			moveSpeed:	5,
			posn:			131 127,
			actions:		pattiActions

		)
		(theMusic
			number:		mDinnerMusic,
			flags:		TRUE,
			setLoop:		-1,
			play:
		)
		(dinnerFX
			flags:		TRUE,
			loop:			-1,
			play:
		)
		(super init:)
		(SetFFRoom 480)
		(HandsOff)
		(curRoom setScript: sCartoon)
	)
)

;************* Scripts *************

(instance sCartoon of Script
	(method (doit)
		(super doit:)
		(if cycleColors
			(Palette PALCycle 243 255 -1)
		)
	)
	(method (changeState newState)
		(switchto (= state newState)
			(
				(= seconds 2)
			)
			(
				(TPrint "What an honor for you, Patti!
					Just look at all the dignitaries here to hear you perform."
					#dispose: self
					HIGH
				)
			)
			(
				(= ticks 30)
			)
			(
				(TPrint "(Well, you presume that's why they're here.)"
					#dispose: self
					LOW
				)
			)
			(
				(= ticks 30)
			)
			(
				(TPrint "You are seated at the table closest to the door,
					near the piano you are to perform upon after dinner.
					Inspector Desmond sits across from you,
					chatting with the woman beside him."
					#dispose: self
					BIG
				)
			)
			(
				(= ticks 30)
			)
			(
				(TPrint "You carry on a meaningless dialogue with the two men
					beside you.
					What a shame the President was too busy to be here, though.
					You feel sure he would have loved to meet you!"
					#dispose: self
					BIG
				)
			)
			(
				(= ticks 30)
			)
			(
				(larry
					setMotion:	MoveTo 300 157 self
				)
			)
			(
				(TPrint "A voice rings out from just outside the doorway,
					\"Ladies and Gentlemen, our Guest of Honor this evening,
					fresh from his heroic rescue of an airplane filled with
					U. S. citizens, including our Vice President's Mommy,
					Mr. Larry, Larry Laffer!\""
					BIG
				)
				(TPrint "\"What!\" you think. \"Larry? MY Larry Laffer?\""
					LOW
				)
				(larry
					setMotion:	MoveTo 247 157 self
				)
			)
			(
				(dinnerFX stop:)
				(theMusic
					pause:		TRUE
				)
				(theMusic2
					number:		mLoveTheme,
					flags:		TRUE,
					setLoop:		-1,
					play:
				)
				(Say ego "\"Larry!\" you shout. \"It's you!!\""
					HIGH
				)
				(TPrint "\"Patti!\" Larry shouts. \"It's you!!\""
					#title:		{Larry}
					LOW
				)
				(= cycles 1)
			)
			(
				(larry
					setLoop:		sGroop,
					cycleSpeed:	4,
					moveSpeed:	4,
					setStep:		5 3,
					setCycle:	StopWalk SAMEVIEW,
					setMotion:	MoveTo 168 142	self
				)
				(= cycles 4)
			)
			(
				(ego
					setPri:		10,
					setCycle:	CycleTo (- (ego lastCel?) 1) 1	self
				)
			)
			(
				(ego
					normalize:	,
					loop:			facingSouthEast,
					posn:			129 130,
					setPri:		10,
					cycleSpeed:	4,
					moveSpeed:	4,
					setMotion:	MoveTo 153 142
				)
			)
			(
				(ego hide:)
				(pattiChair hide:)
				(desmond hide:)
				(patDaisChair hide:)
				(desDaisChair hide:)
				(quayle hide:)
				(bigg hide:)
				(larry hide:)
				(marilyn hide:)
				(roger hide:)
				(humphrey hide:)
				(sleeper hide:)
				(heart init:)
				(larryHead init:)
				(pattiHead init:)
				(pattiLeg init:)
				(curRoom drawPic: pPattiEmbracesLarry)
				(= cycles 2)
			)
			(
				(= cycleColors TRUE)
				(= seconds 3)
			)
			(
				(pattiLeg
					setCel:	(pattiLeg lastCel?)
				)
				(pattiHead
					setCycle: ForwardCounter 4
				)
				(Say ego "\"Oh, Larry! I've missed you so!!\"
					You whisper in his ear."
					#dispose: self
					LOW
				)
			)
			(
				(= ticks 30)	; added BKH September 3, 1991
			)
			(
				(pattiHead setCycle: 0)
				(larryHead
					setCycle: ForwardCounter 5
				)
				(TPrint "\"What happened to you, Patti?\" Larry sighs.
					\"The last I remember,
					we were together on a deck overlooking a lake!
					Where did you go?
					Hell, where did I go?!\""
					#title:		{Larry}
					#dispose: self
					LOW
				)
			)
			(
				(= ticks 30)	; added BKH September 3, 1991
			)
			(
				(larryHead: setCycle: 0)
				(pattiHead
					setCycle: ForwardCounter 5
				)
				(Say ego "\"Oh, you sweet dear.
					You really don't remember a thing, do you?
					It's a long story that perhaps I'll tell you some day,
					but right now,
					all that matters is that we're together again!\""
					#dispose: self
					LOW
				)
			)
			(
				(= ticks 30)	; added BKH September 3, 1991
			)
			(
				(pattiHead setCycle: 0)
				(larryHead
					setCycle: ForwardCounter 5
				)
				(TPrint "\"Uh, say Patti,\" Larry says,
					\"Since I had a little trouble finding a date for tonight,
					why don't you join me on the dais?
					We can talk about old times.\""
					#title:		{Larry}
					#dispose: self
					LOW
				)
			)
			(
				(= ticks 30)	; added BKH September 3, 1991
			)
			(
				(larryHead: setCycle: 0)
				(pattiHead
					setCycle: ForwardCounter 3
				)
				(Say ego "\"Why, Larry! That would be wonderful.\""
					#dispose: self
					LOW
				)
			)
			(
				(= ticks 30)
			)
			(
				(pattiHead setCycle: 0)
				(pattiLeg dispose:)
				(= seconds 3)
			)
			(
				(heart dispose:)
				(larryHead dispose:)
				(pattiHead dispose:)
				(quayle show:)
				(bigg	show:)
				(ego
					show:			,
					cycleSpeed:	6,
					moveSpeed:	6,
				)
				(desmond
					show:			,
					cycleSpeed:	6,
					moveSpeed:	6,
					setLoop:		lDesmondStanding,
					setCel:		0,
					posn:			238 132
				)
				(larry 
					show:			,
					cycleSpeed:	6,
					moveSpeed:	6,
				)
				(pattiChair show:)
				(desmondChair init:)
				(patDaisChair show:)
				(desDaisChair show:)
				(marilyn show:)
				(roger show:)
				(humphrey show:)
				(sleeper show:)
				(= cycleColors FALSE)
				(curRoom drawPic: pWhiteHouseDinnerLongShot)
				(theMusic2
					fade:	0 15 12 TRUE self
				)
;SRC				(= cycles 1)
			)
			(
				(dinnerFX play:)
				(theMusic
					pause:	FALSE
				)
				(= seconds 3)
			)
			(
				(Say ego "\"Come on, Inspector Desmond,\" you say,
					\"we're gonna sit at the head table!\""
					HIGH
				)
				(larry
					setHeading:	90 self		 	;look at Desmond
				)
			)
			(
				(desmond
					setLoop:		lDesmondWalkWestL,
					setCycle:	Walk,
					setMotion:	MoveTo 194 147	self
				)
			)
			(
				(TPrint "\"Uh, Patti, er, well, I meant...\"
					Larry stutters, then finally gives up.
					\"Hi, Inspector. My name is Larry; Larry Laffer.\""
					#title:		{Larry}
					HIGH
				)
				(TPrint "\"Charmed,\" grunts Desmond."
					LOW
					#title:		{Inspector Desmond}
				)
				(desmond
					setMotion:	MoveTo 165 147 self
				)
			)
			(
				(larry setHeading: 135 self)
			)
			(
				(larry
					setHeading:	270
				)
				(ego
;**						normalize:	,
					cycleSpeed:	6,
					moveSpeed:	6,
					setMotion:	MoveTo -10 142
				)
				(desmond
					setMotion:	MoveTo -10 147
				)
				(= ticks 120)				;Make Larry wait for Patti and Desmond
			)
			(
				(larry
					setStep:		3 2,
					setMotion:	MoveTo -10 139 self
				)
			)
			;
			; Make entrance to dais
			;
			(
				(= seconds 5)
			)
			(
				(ego
					view:			vPattiInWhiteHouse,
					setLoop:		lPattiWalkEastSml,
					setCycle:	Walk,
					x:				-10,
					y:				87,
					setPri:		3,
					setMotion:	MoveTo 102 87 self
				)
				(desDaisChair setPri: 4)
				(= ticks 90)
			)
			(
				(desmond
					setScript:	sDesmondSits
				)
			)
			(
				(ego
					setLoop:		lPattiFaceSouth,
					setCel:		0,
					setCycle:	0,
					setMotion:	MoveTo 103 95 self
				)
			)
			(
				(ego
					setLoop:		lTalkToLeft,
					setCel:		0,
					setPri:		5,
					cycleSpeed:	12,
					x:				101,
					y:				71
				)
				(= cycles 1)
			)
			(
				(larry
					view:			vLarrySmall,
					loop:			0,
					setPri:		4,
					x:				-10,
					y:				82,
					setCycle:	Walk,
					setMotion:	MoveTo 26 82 self
				)
			)
			(
				(= ticks 60)
			)
			(
				(larry
					view:			vLarryInWhiteHouse,
					setLoop:		lLookLeftRight,
					setCel:		2							 	;looking to Larry's left
				)
				(= ticks 45)
			)
			(
				(larry
					setCycle:	CycleTo 1 FORWARD	self	;looking to Larry's right
				)
			)
			(
				(= ticks 45)
			)
			(
				(larry
					setCycle:	CycleTo 2 BACKWARD self	;looking to Larry's left
				)
			)
			(
				((larry looper?) dispose:)
				(= ticks 45)
			)
			(
				(larry
					view:			vLarrySmall,
					setLoop:		facingRight,
					setLoop:		RELEASE,
					setCycle:	Walk
				)
				(= cycles 1)
			)
			(
				(TPrint "\"Uh, excuse me, Patti.
							Would you two scoot over a little
							so your guest of honor can sit down, too?\""
							WIDE
							#title:		{Larry}
				)
				(= seconds 2)
			)
			;
			;	Scoot chairs over
			;
			(
				(patDaisChair
					setLoop:		lCelStuff,
					setCel:		cDaisChair,
					setMotion:	MoveTo (+ (patDaisChair x?) 3) (patDaisChair y?)
				)
				(ego
					setMotion:	MoveTo 105 72 self
				)
			)
			(
				(= cycles 3)
			)
			(
				(desDaisChair
					setLoop:		lCelStuff,
					setCel:		cDaisChair,
					setMotion:	MoveTo (+ (desDaisChair x?) 4) (desDaisChair y?)
				)
				(desmond
					setMotion:	MoveTo 81 71 self
				)
			)
			(
				(= cycles 5)
			)
			(
				(patDaisChair
					setMotion:	MoveTo (+ (patDaisChair x?) 4) (patDaisChair y?)
				)
				(ego
					setMotion:	MoveTo 108 72 self
				)
			)
			(
				(patDaisChair stopUpd:)
				(= cycles 3)
			)
			(
				(desDaisChair
					setMotion:	MoveTo (+ (desDaisChair x?) 3) (desDaisChair y?)
				)
				(desmond
					setMotion:	MoveTo 86 71 self
				)
			)
			(
				(desDaisChair stopUpd:)
				(ego setScript: sTalkDesmond)
				(ssguy
					init:			,
					setCycle:	Walk,
					setMotion:	MoveTo 49 81 self
				)
			)
			(
				(ssguy
					setLoop:		lUnfoldsChair,
					cycleSpeed:	10,
					setCycle:	CycleTo 5 1 self
				)
				(TPrint "From nowhere, a Secret Service man appears
					with a folding chair.")
			)
			(
				(chairBack init:)
				(= cycles 1)
			)
			(
				(ssguy
					setLoop:		lUnfoldsChair,
					setCycle:	EndLoop self
				)
			)
			(
				(ssguy								  			;walk back out
					setLoop:		lWalkingWest,
					setCycle:	Walk,
					cycleSpeed:	6,
					setPri:		0,
					setMotion:	MoveTo -50 81
				)
				(larry											;walk to chair
					setMotion:	MoveTo 64 82 self
				)
			)
			(
				(larry
					setHeading:	180 self
				)
			)
			(
				(larry
					loop:			2,								;facing south
					setCel:		0,
					setCycle:	0,
					setMotion:	MoveTo 63 86 self
				)
			)
			(
				(larry											;sitting down
					view:			vLarryInWhiteHouse,
					setLoop:		lLookingLeft,
					setCel:		0,
					setPri:		5,
					cycleSpeed:	10,
					x:				62,
					y:				74
				)
				(= seconds 3)
			)
			(
				(ssguy dispose:)
				(larry setCycle: EndLoop self)
			)
			(
				(TPrint "\"You know, Mr. Vice President,\" Larry says,
					\"you've always been a secret hero of mine!
					In fact, the Vice Presidency is one office
					to which I have always aspired to!\""
					WIDE
					#title:		{Larry}
				)
				(= seconds 3)
			)
			(
				(TPrint "\"Mr. Laffer,\" responds the Vice President,
					\"has anyone ever mentioned to you that we look alike?\""
					#title:	{Mr. Vice President}
				)
				(TPrint "\"Oh, of course. And am I proud!\""
					#title:		{Larry})
				(larry setCycle: BegLoop self)
			)
			(
				(= seconds 3)
			)
			(
				(larry
					setLoop:		lLookingRight,
					setCycle:	EndLoop self
				)
			)
			(
				(= seconds 3)
			)
			(
				(larry setCycle: BegLoop self)
			)
			(
				(sTalkDesmond dispose:)
				(= seconds 3)
			)
			(
				(bigg setCycle: EndLoop self)
			)
			(
				(ego
					setLoop:		lTalkToRight,
					setCycle:	EndLoop self
				)
			)
			(
				(TPrint "Before you can talk to Larry or Desmond,
					you realize the large burly gentleman to your left is
					already speaking to you."
					LOW
				)
				(= cycles 1)
			)
			(
				(ego hide:)
				(pattiChair hide:)
				(desmondChair hide:)
				(desmond hide:)
				(patDaisChair hide:)
				(desDaisChair hide:)
				(quayle hide:)
				(bigg hide:)
				(larry hide:)
				(marilyn hide:)
				(roger hide:)
				(humphrey hide:)
				(sleeper hide:)
				(chairBack hide:)
				(curRoom drawPic: pBiggAndPatti)
				(dinnerFX stop:)
				(theMusic
					number:		mPattiBigg,
					play:
				)
				(= seconds 3)
			)
			(
				(Say biggTalker "\"...and it's fortunate that you're here this evening
					as I'm only in town for a short time.
					Yes, I'm testifying tomorrow on Capitol Hill at the Senate
					hearings on pornography in the entertainment industry.\""
					#dispose:
					#caller: self
				)
			)
			(
				(Say pattiTalker "\"Oh, really?\" you say, feigning interest."
					#dispose:
					#caller: self
				)
			)
			(
				(Say biggTalker "\"You've heard of me, then?\" he swells up 
					noticeably."
					#caller: self
 				)
			)
			(
				(Say biggTalker "\"Yes, my job is simple:
					tomorrow I intend to convince Congress to create tough,
					anti-pornography legislation tough enough to make
					the airwaves \"Beaver\" clean again,
					just like they were back in the Fifties!\""
					#dispose:
					#caller: self
				)
			)
			(
				(= seconds 3)
			)
			(
				(TPrint "You love these dinners and being seated next to people
					who feel their sole mission is to impress you with
					themselves!"
					BIG
				)
				(TPrint "(Especially a man who's breath is enough to curdle
					hairspray!)" LOW)
				(= seconds 3)
			)
			(
				(Say biggTalker "\"Allow me to introduce myself,\" he says,
					\"I'm Mr. Bigg, a major West Coast corporate leader.\""
					LOW
					#dispose:
					#caller: self
				)
			)
			(
				(Say pattiTalker "\"My name is Passionate Patti,\" you reply."
					#dispose:
					#caller: self
				)
			)
			(
				(Say biggTalker "\"And I bet I can guess how you earned that name,\"
					he grins."
					LOW
					#dispose:
					#caller: self
				)
			)
			(
				(= seconds 3)
			)
			(
				(TPrint "What an obnoxious creep!
					Now he's humming at you.
					Some familiar song.
					A love song?
					Who does he think he's impressing?"
					BIG
				)
				(TPrint "\"What is that song,\" you think.
					\"It sounds so familiar.\""
					HIGH
				)
				(= seconds 3)
			)
			(
				(TPrint "Realizing you are still unimpressed, Bigg digs in deeper."
					LOW
				)
				(Say biggTalker "\"You know, Patti (may I call you Patti?),
					I find you a very sexy woman.
					In fact, I've been looking for just such a
					woman for a new position I recently created.\""
					#dispose:
					#caller: self
				)
			)
			(
				(TPrint "Between phrases, he continues to hum that melody."
					LOW
				)
				(= seconds 3)
			)
			(
				(Say biggTalker "\"Patti, you seem like a woman interested in quick
					success.
					Why don't you be the hostess of my new TV show?\""
					#caller: self
 				)
			)
			(
				(Say biggTalker "\"For months I've had minions out hunting for the
					sexiest woman in America.
					But tonight, I've discovered her myself!
					Just say the word, Babe, and the gig is yours!\""
					#dispose:
					#caller: self
				)
			)
			(
				(TPrint "At the mention of \"sexiest woman in America,\"
					Larry's head jerks toward Bigg,
					his attention riveted on your conversation."
					BIG
					#dispose: self
				)
			)
			(
				(= ticks 30)
			)
			(
				(Say pattiTalker "\"Really, Mr. Bigg, your offer is not interest...\""
					#dispose:
					#caller: self
				)
			)
			(
				(Say biggTalker "Bigg interrupts you.
					\"Oh, Patti! Stop being so formal.
					Please. Call me `Julius.'\""
					LOW
					#dispose:
					#caller: self
				)
			)
			(
				(= seconds 3)
			)
			(
				(TPrint "\"JULIUS!\" you think, your mind racing!"
					HIGH
				)
				(= seconds 3)
			)
			(
				(ego show:)
				(pattiChair show:)
				(desmondChair show:)
				(desmond show:)
				(patDaisChair show:)
				(desDaisChair show:)
				(quayle show:)
				(bigg show:)
				(larry show:)
				(marilyn show:)
				(roger show:)
				(humphrey show:)
				(sleeper show:)
				(chairBack show:)
				(curRoom drawPic: pWhiteHouseDinnerLongShot)
				(theMusic
					number:		mLarryBigg,
					play:
				)
				(= ticks 60)
			)
			(
				(ego setCycle: BegLoop)
				(bigg setCycle: BegLoop self)
			)
			(
				(larry
					setLoop:		lTalkingToBigg,
					setCycle:	EndLoop self
				)
			)
			(
				(= seconds 2)
			)
			(
				(TPrint "\"Patti, something's wrong,\" says Larry.
					\"He's offering you the job as hostess of
					`America's Sexiest Home Videos!'
					But he has nothing to do with that show!
					I should know, I do!
					I was sent by the producer to find...
					Say, wait a minute.
					Could he be the money behind the show...?\""
					#title:		{Larry}
					WIDELOW
				)
				(Say ego "\"JULIUS!\" you cry,
					suddenly putting everything together!
					\"Desmond! Arrest this man!!\""
				)
				(TPrint "\"WHAT?!\" shouts Bigg."
					#title:	{Mr. Bigg}
				)
 				(Say ego "\"You scoundrel!
					You've been humming the love theme that I wrote for the
					soundtrack of `Larry 4!'
					That song was never published and the recording was
					never released!
					There's no way you could know it unless you
					played Larry 4!\""
					WIDELOW
				)
				(Say ego "Of course, I see it all now.
					Bigg, you're the man who hired me to create the music,
					then erased the floppies to keep from paying me!
					You're the reason my poor Larry has amnesia!
					You're the man behind K-RAP and des Rever Records!
					Julius Bigg--you're the man I've been following this
					entire game!\""
					WIDELOW
				)
				(TPrint "\"You're so smart, Patti!\" screams Bigg,
					\"let's see you deduce your way out of this!\""
					#title:	{Mr. Bigg}
				)
				(TPrint "And leaping to his feet,
					he pulls a revolver and aims it directly at you!")
				(bigg
					setLoop:		lDrawingGun,
					setPri:		3,
					setCycle:	EndLoop self
				)
			)
			(
				(theMusic
					number:		mPullGun,
					play:
				)
				(TPrint "\"Everybody stay away or I'll shoot!\" shouts Bigg."
					#title:	{Mr. Bigg}
				)
				(TPrint "Larry cries out, \"I'll save you, Sir!\""
					#title:		{Larry})
				(= cycles 1)
 			)
			(
				(marilyn
					setCycle:	CycleTo 8 FORWARD self
				)
			)
			(
				(pie init:)
				(marilyn
					setCycle:	EndLoop self
				)
			)
			(
				(larry
					setLoop:		lJumpingQuayle,
					setCel:		0,
					cycleSpeed:	10,
					setCycle:	CycleTo 2 FORWARD self
				)
			)
			(
				(quayle hide:)
				(pie setPri: 5)
				(larry setCycle: EndLoop self)
			)
			(
				(marilyn
					setLoop:		lReacting,
					setCel:		0,
					setCycle: 	EndLoop self
				)
				(pieInFace play:)
			)
			(
				(HandsOn)
				(User canControl: FALSE)
				(= seconds 6)
			)

			(
				(TPrint "Do something, Patti!")
				(= seconds 6)
			)

			(
				(curRoom setScript: sCartoon2)
				(self dispose:)
			)
		)
	)
);sCartoon

(instance sCartoon2 of Script
	(method (changeState newState)
		(switchto (= state newState)
			(
				(HandsOff)
				(TPrint "\"It's a good thing I wore that F.B.I. bra!\"
					you think, touching your elbows behind your back.")
;SRC				(scuffle play:)
				(ego
					setLoop:		lPattiElbows,
					cycleSpeed:	5,
					setCel:		0,
					setPri:		4,
					setCycle:	EndLoop self
				)
			)
			(
				(= ticks 30)
			)
			(
				(fireBra play:)
				(ego
					setLoop:		lFiringBra,
					cycleSpeed:	6,
					setCel:		0,
					setCycle:	CycleTo 2 1 self
				)
			)
			(
				(bigg
					setLoop:		2,
					setCycle:	EndLoop
				)
				(= cycles 5)
			)
			(
				(fireBra play:)
				(ego
					setCycle:	CycleTo 5 1 self
				)
			)
			(
				(theMusic
					number:		mPattiHero,
					setLoop:		1,
					play:
				)
				(ego
					setLoop:		lSmokingBra,
					setCel:		0,
					cycleSpeed:	10,
					setCycle:	EndLoop self
				)
			)
			(
				(= ticks 180)
			)
			(
				(bigg hide:)
				(desmond
					x:				140,
					y:				74,
					setPri:		0,
					setLoop:		lGrabbingGun,
					setCel:		0,
					cycleSpeed:	12,
					setCycle:	EndLoop self
				)
			)
			(
				(desmond
					setLoop:		lGrabsBigg,
					setCel:		0,
					setCycle:	EndLoop self
				)
			)
			(
				(= ticks 180)
			)
			(
				(desmond
					cycleSpeed:	6,
					setMotion:	MoveTo (ego x?) 74 self,
				)
			)
			(
				(TPrint "\"Congratulations, Patti!\" says Desmond.
					\"You've saved the Vice President's life!
					We'll haul this guy down to headquarters right now!\""
					#title:	{Inspector Desmond}
				)
				(TPrint "He whispers to you under his breath,
					\"There goes our weekend together, Patti!
					I'll be stuck at the office filing paperwork on this
					low-life for days!\""
					#title:	{Inspector Desmond}
				)
				(desmond
					cycleSpeed:	5,
					setMotion:	MoveTo -50 74
				)
				(ego
					setMotion	MoveTo (ego x?) (+ (ego y?) 4) self
				)
			)
			(
				(ego
					setLoop:		lTalkToLeft,
					x:				108,
					y:				72,
					setPri:		5,
					setCel:		0
				)
				(theMusic 		;if music is still running, fade it
					fade:
				)
				(= ticks 240)
			)
			(
				(theMusic
					number:		mEnding,
					setVol:		127,
					setLoop:		-1,
					play:
				)
				(larry
					setLoop:		lSittingUp,
					setCel:		0,
					setCycle:	EndLoop self
				)
			)
			(
				(larry
					setLoop:		lBackInChair,
					setCel:		0,
					setCycle:	EndLoop self
				)
			)
			(
				(pie setPri: 6)
				(larry
					setLoop:		lLookingLeft,
					setCel:		0,
					x:				62,
					y:				74
				)
				(quayle
					show:			,
					setLoop:		lGettingUp,					;Face in pie
					setCel:		0,
					x:				42,
					y:				72,
					setCycle:	EndLoop self
				)
			)
			(
				(TPrint "\"I'm sorry I shoved your face in that custard pie,
					Mr. Vice President!\" Larry says."
					#title:		{Larry})
				(quayle
					setLoop:		lMadAtLarry,
					setCel:		0,
					posn:			38 71,						;adjustment since origins off
					setCycle:	EndLoop self
				)
			)
			(
				(= seconds 3)
			)
			(
				(larry
					setLoop:		lLookingRight,
					setCel:		0,
					setCycle:	EndLoop self
				)
			)
			(
				(Say ego "You turn to Larry and say,
					\"So... it looks like I'm free for the weekend.
					How about you?\""
				)
				(= ticks 90)
			)
			(
				(quayle
					setLoop:		lLookAtMarilyn,
					setCel:		0,
					posn:			42 72,						;adjustment since origins off
					cycleSpeed:	11,
					setCycle:	EndLoop self
				)
			)
			(
				(= ticks 60)
			)
			(
				(quayle
					setCycle:	BegLoop self
				)
			)
			(
				(quayle
					setLoop:		lTalkingWPie,
					setCel:		0,
					posn:			38 71,						;adjustment since origins off
					cycleSpeed:	6,
					setCycle:	ForwardCounter 2 self
				)
			)
			(
				(TPrint "Assuming you are talking to him, the Vice President
					whispers carefully, so as not to let his wife overhear,
					\"Er,	uh, well, yes.
					Perhaps I could set up a little excursion to Camp David...\""
					WIDE
					#title:	{Mr. Vice President}
				)
				(= cycles 1)
			)
			(
				(larry
					setCycle:	BegLoop self
				)
			)
			(
				(larry
					setLoop:		lLookingLeft,
					setCycle:	EndLoop self
				)
			)
			(
				(TPrint "Larry presumes the Vice President is being magnanimous.
					\"Gee, thanks!\" he says in a loud voice,
					\"and I hope you and the missus get a chance
					to come along, too."
					#title:		{Larry})
				(= seconds 2)
			)
			(
				(curRoom drawPic: pBlackPic IRISIN)
				(CenterDisplay 1 colLGreen
					"Later..."
				)
				(= seconds 3)
			)
			(
				(curRoom newRoom: 480)
				(self dispose:)
			)
		)
	)
);sCartoon2

(instance sDesmondSits of Script
	(method (changeState newState)
		(switchto (= state newState)
			(
				(desmond
					setLoop:		lDesmondWalkEastS,
					x:				-10,
					y:				84,
					setPri:		4,
					setMotion:	MoveTo 76 84 self
				)
			)
			(
				(desmond
					setLoop:		lDesmondFaceEast,
					setCel:		0,
					setCycle:	0,
					setMotion:	MoveTo 76 95 self
				)
				(desDaisChair setPri: 1)
			)
			(
				(desmond
					setLoop:		lDesTalking,
					setCel:		0,
					x:				76,
					y:				70,
					stopUpd:
				)
				(= cycles 1)
			)
			(
				(self dispose:)
			)
		)
	)
);sDesmondSits

(instance sTalkDesmond of Script
	(method (changeState newState)
		(switchto (= state newState)
			(
				(= seconds 4)
			)
			(
				(desmond
					setCycle:	EndLoop self
				)
				(ego
					setCycle:	EndLoop
				)
			)
			(
				(= seconds 4)
			)
			(
				(ego
					setCycle:	BegLoop
				)
				(desmond
					setCycle:	BegLoop self
				)
			)
			(
				(self changeState: 0)
			)
		)
	)
	(method (dispose)
		(if (desmond cel?)
			(desmond
				setCycle:	BegLoop
			)
		)
		(if (ego cel?)
			(ego
				setCycle:	BegLoop
			)
		)
		(super dispose: &rest)
	)
);sTalkDesmond

(instance sRogerSleeps of Script
	(method (changeState newState)
		(switchto (= state newState)
			(
				(roger
					setCel:		0,
					setCycle:	ForwardCounter 1 self
				)
			)
			(
				(= seconds (Random 3 5))
			)
			(
				(self changeState: 0)
			)
			(
				(self dispose:)
			)
		)
	)
);sRogerSleeps

(instance sHumphreyDrinks of Script
	(method (changeState newState)
		(switchto (= state newState)
			(
				(humphrey
					setCel:		0,
					setCycle:	ForwardCounter 1 self
				)
			)
			(
				(= seconds (Random 4 6))
			)
			(
				(self changeState: 0)
			)
			(
				(self dispose:)
			)
		)
	)
);sHumphreyDrinks

(instance sSleeperSleeps of Script
	(method (changeState newState)
		(switchto (= state newState)
			(
				(sleeper
					setCel:		0,
					setCycle:	ForwardCounter 1 self
				)
			)
			(
				(= seconds 3)
			)
			(
				(self changeState: 0)
			)
			(
				(self dispose:)
			)
		)
	)
);sSleeperSleeps

;************ Objects *************

(instance larry of Actor
	(properties
		view			vLarry
		loop			facingLeft
		cel			0
		x				320
		y				157
		cycleSpeed	5
		moveSpeed	5
		signal		(| ignrAct stopUpdOn)
		description	"Larry Laffer"
	)
)

(instance sGroop of GradualLooper)

(instance pattiActions of Actions
	(method (doVerb theVerb invItem)
		(if (or (and
						(== theVerb verbUse)
						(== invItem iBra)
				  	)
				  (and
				  		(== theVerb verbDo)
						(Btst fWearingBra)
				  	)
			)
				(Points ptsFiredBraAtBigg)
				(curRoom setScript: sCartoon2)
				(return TRUE)
		else
			(return FALSE)
		)
	)
)

(instance desmond of Actor
	(properties
		view			vDesmond
		loop			lDesmondStanding
		cel			1
		x				225;235
		y				127;130
		signal		(| ignrAct stopUpdOn)
		description	"Desmond"
	)
)

(instance desmondChair of View
	(properties
		view			vWhiteHouse
		loop			lCelStuff
		cel			cChairL
		x				236
		y				127
		signal		(| ignrAct stopUpdOn)
		description	"the chair"
	)
)

(instance pattiChair of View
	(properties
		view			vWhiteHouse
		loop			lCelStuff
		cel			cChairR
		x				120
		y				127
		signal		(| ignrAct stopUpdOn)
		description	"the chair"
	)
)

(instance roger of Prop
	(properties
		view			vWhiteHouse
		loop			lRoger
		cel			0
		x				139
		y				144
		detailLevel	1
		cycleSpeed	24
		priority		14
		signal		(| fixPriOn ignrAct)
		description	"Roger"
	)
	(method (init)
		(super init:)
		(self setScript: sRogerSleeps)
	)
)

(instance humphrey of Prop
	(properties
		view			vWhiteHouse
		loop			lHumphrey
		cel			0
		x				139
		y				101
		detailLevel	2
		cycleSpeed	12
		priority		10
		signal		(| fixPriOn ignrAct)
		description	"Humphrey"
	)
	(method (init)
		(super init:)
		(self setScript: sHumphreyDrinks)
	)
)

(instance sleeper of Prop
	(properties
		view			vWhiteHouse
		loop			lSleeper
		cel			0
		x				224
		y				169
		detailLevel	3
		cycleSpeed 	24
		priority		14
		signal		(| fixPriOn ignrAct)
		description	"the sleeping guest"
	)
	(method (init)
		(super init:)
		(self setScript: sSleeperSleeps)
	)
)

(instance ssguy of Actor
	(properties
		view			vSSAgent
		loop			lWalkingW/Chair
		cel			0
		x				-50
		y				81		;78
		cycleSpeed	6
		moveSpeed	6
		priority		4
		signal		(| fixPriOn ignrAct stopUpdOn)
		description	"the Secret Service man"
	)
)

(instance quayle of Prop
	(properties
		view			vQuayle
		loop			lFidgeting
		cel			0
		x				40
		y				74
		cycleSpeed	6
		priority		5
		signal		(| fixPriOn	stopUpdOn)
		description	"the Vice President"
	)
	(method (init)
		(self setCycle: Forward)
		(super init: &rest)
	)
)

(instance bigg of Prop
	(properties
		view			vBigg
		loop			lTalkingToPatti
		cel			0
		x				140
		y				74
		cycleSpeed	10
		priority		5
		signal		(| fixPriOn	stopUpdOn ignrAct)
		description	"Mr. Bigg"
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbUse
				(switch invItem
					(iBra
						(Points ptsFiredBraAtBigg)
					  	(curRoom setScript: sCartoon2)
					)
					(else
						(super doVerb: theVerb invItem &rest)
					)
				)
			)
			(else
				(super doVerb: theVerb invItem &rest)
			)
		)
	)
)

(instance pattiHead of Prop
	(properties
		view			vTalkingHeads
		loop			lPattiFaceTalking
		cel			0
		x				158
		y				74
		priority		5
		signal		fixPriOn
	)
)

(instance larryHead of Prop
	(properties
		view			vTalkingHeads
		loop			lLarryFaceTalking
		cel			0
		x				176
		y				84
		priority		5
		signal		fixPriOn
	)
)

(instance heart of Prop
	(properties
		view			vHeart
		loop			0
		cel			0
		x				176
		y				132
		cycleSpeed	6
		priority		0
		signal		fixPriOn
	)
	(method (init)
		(super init:)
		(self setCycle: Forward)
	)
)

(instance pattiLeg of Prop
	(properties
		view			vTalkingHeads
		loop			lLegs
		cel			0
		x				132
		y				149
	)
)

(instance chairBack of Actor
	(properties
		view			vWhiteHouse
		loop			lCelStuff
		cel			cFoldChair
		x				61
		y				71
		signal		(| ignrAct stopUpdOn)
		description	"the chair"
	)
)

(instance desDaisChair of Actor
	(properties
		view			vWhiteHouse
		loop			lCelStuff
		cel			cDaisChair
		x				75
		y				70
		signal		(| ignrAct stopUpdOn)
		description	"the chair"
	)
)

(instance patDaisChair of Actor
	(properties
		view			vWhiteHouse
		loop			lCelStuff
		cel			cDaisChair
		x				101
		y				70
		signal		(| ignrAct stopUpdOn)
		description	"the chair"
	)
)

(instance marilyn of Prop
	(properties
		view			vMarilyn
		loop			lPlacePie
		cel			0
		x				14
		y				75
		priority		5
		cycleSpeed	10
		signal		(| stopUpdOn fixPriOn ignrAct)
		description	"Marilyn Quayle"
	)
)

(instance pie of View
	(properties
		view			vQuayle
		loop			lPie
		cel			0
		x				37
		y				74
		priority		6
		signal		(| stopUpdOn fixPriOn ignrAct)
		description	"the pie"
	)
)

;*********** Sounds ***********

(instance dinnerFX of Sound
	(properties
		number	sDinnerFX
	)
)

(instance pieInFace of Sound
	(properties
		number	sPieInFace
		priority 15
	)
)

(instance fireBra of Sound
	(properties
		number	sFireBra
	)
)

;*************** Talker ****************

(instance biggTalker of Talker
	(properties
		name			"Mr. Bigg"
		view			vBiggTalker
		loop			3
		x				20
		y				185
		talkWidth	280
		nsLeft		150
		nsTop			60
		viewInPrint	FALSE
	)
	(method (init)
		(= mouth biggMouth)
		(super init: &rest)
	)
)

(instance biggMouth of Prop
	(properties
		view		   vBiggTalker
		loop			0
		nsLeft		6
		nsTop			8
		cycleSpeed 	5
	)
)


(instance pattiTalker of Talker
	(properties
		name			"You"
		view			vPattiTalker
		loop			3
		x				20
		y				5
		talkWidth	160
		nsLeft		100
		nsTop			70
		viewInPrint	FALSE
	)
	(method (init)
		(= mouth pattiMouth)
		(= eyes pattiEyes)
		(super init: &rest)
	)
)

(instance pattiMouth of Prop
	(properties
		view		   vPattiTalker
		loop			lMouth
		nsLeft		4
		nsTop			9
		cycleSpeed 	5
	)
)

(instance pattiEyes of Prop
	(properties
		view		   vPattiTalker
		loop			lEyes
		nsLeft		8
		nsTop			1
		cycleSpeed 	5
	)
)

