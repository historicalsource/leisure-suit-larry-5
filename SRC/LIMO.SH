;**
;**	Logics for Room 200 -- Limousine Defines
;**
;**	LSL5:  Passionate Patti Does A Little Undercover Work
;**				Copyright 1991
;**				by Al Lowe
;**
;**	Last Update:  July 29, 1991
;**

;DEBUG temp. sound:

(define	mDreamLand			320)

;limo sounds

(define	sfxTVClickOn		 	201)
(define	sfxWindowUpDown	 	202)
(define	sfxFax				 	203)
(define	sfxCarSnd			 	204)
(define	sfxFish				 	205)
(define	mMrBiggTheme		 	210)
(define	mMcGutterSlugTheme 	215)
(define	mJimBaker			 	220)
(define	mDreamTheme			 	230)
(define	sfxCoinsFall		 	241)

(define	tvX 					 	162)
(define	tvY						110)


;misc. limo interior props
(define	pLimousineInterior		200)
	(define	lLimoInterior		0)
		(define	cBottle 		0)
		(define	cDayTrotter 1)
		(define	cFaxPaper 	2)
		(define	cGlass 		3)
		(define	cWindowF		4)
	(define	lPhoneCel 			1)
	(define 	lWindowL				2)
	(define 	lWindowR				3)


(define	vLimoInterior				200)


;larry sitting in limo loops
(define	vLarry'sLimo				201)
	(define	lGetPhone				0)
		(define	cSit&Behave 	0)
	(define	lShowDriverStuff		1)
	(define	lGettingDayTrotter 	2)


;misc patti stuff
(define	vPatti'sLimo				202)
	(define	lTakingPhone 			0)
	(define	lTakingFax	 			1)
	(define	lTakingBottle 			2)
	(define	lPattiLegs				3)
	(define	lShowDriverFax			4)
	(define	lShowDriverDMan		5)

(define	vDriverMale					203)
(define	vDriverFemale				204)
	(define	lEyes	1)

(define	vTVpicture					205)

;fish swimming in tank loops
(define	vAquarium					206)
	(define	lSwimLeft		0)
	(define	lSwimRight		1)
	(define	lSwimSickLeft	2)
	(define	lSwimSickRight	3)
	(define	lFishDead		4)
	(define	lWave1 			5)
	(define	lWave2 			6)


(define	vBoatWaves 					230)
(define	vGatesTv						235)
(define	vBoatCouple					231)
	(define lCheriRolling	0)

(define	vGatesArms					236)
(define	vGoldPouring			  	240)
	(define lLooseChange		0)
(define	vShovel						241)
	(define	lPatti			0)
	(define	lShovel			1)

;(define	vBalloon						322)
(define	vDreamBubble 				207)


;	meanWhiles			471	; larry meanwhile... in limo
	(enum 1										;**	enums for meanWhiles
		MobWantsCane
		ScruemallGetsACall
		CaneGetsBigGrant
		CaneWorksCongress
	)

;(define	larryIsRiding	(== (ego name?) {Larry}))
;(define	larryIsRiding	(not (StrCmp (ego name?) {Larry})))

(define	goingToAirport	(not (OneOf prevRoomNum 205 250)))

;(define	LARRYROOMS		HOLLYWOOD AIRPORT AIRPLANE NEWYORK ATLANTICCITY MIAMI)
;(define	PATTIROOMS		FBI BALTIMORE PHILIDELPHIA)


(define	lTalkerMouth	0)
(define	lTalkerBust		1)
(define	lTalkerEyes		2)
(define	lTalkerFrame	3)

(define	vMobFlunky1Talker	1122)

(define RBIAZFAX 	 	1)
(define PCHAMMERFAX	2)

(define NONE 0)
