;**
;**	graphics.sh -- 
;**
;**	LSL5:  Passionate Patti Does A Little Undercover Work
;**				Copyright 1991
;**				by Al Lowe
;**
;**	Last Update:	June 13, 1991
;**
;**		Note:  Lines that begin with a semicolon (";") indicate views
;**	that are used in only one scene.
;**


;**	
;**           Background Scene, View, and Loop Numbers
;**	
;**	Naming convention:  Pictures (pix, pic) are referred to as p120, p130,
;**	etc. Views use "V", loops use "L", cells use "C", and rooms use "R". 
;**	Thus V0/L2/C7 refers to the eighth cell of a southbound Larry.
;**	
;**	Numbering convention:  Any view used as part of a background picture
;**	has the same number as that picture.  Any specialized ego (either
;**	Larry or Patti) used only in that one picture has the same number
;**	+1.  Any additional views for that picture begin with +2 and continue
;**	up.  Ego views used in multiple areas are numbered in the upper
;**	500s.  Inventory views are in the upper 900s.
;**	
;**	Graphic description convention:  Indentation, font size, and style
;**	denote whether a line refers to a pic, view, loop, or cell as shown
;**	in this example:
;**
;**	###	This is a pic description
;**		###	This is a view description
;**			#	This is a loop description
;**				#	This is a cell description
;**
;**
;**



;ษอออออออออออออออออออป
;บ                   บ
;บ INDEX OF REGIONS  บ
;บ                   บ
;ศอออออออออออออออออออผ

;	Opening Cartoon								100
;	Larry Cartoon (Mr. Bigg's Office)		120
;	Porn Prod Corp (Hollywood)					160
;
;	Limousine										200
;	Meanwhile Scenes (Larry's Limo)			210
;	Patti's Dreams (Patti's limo)				230
;	Airport											250
;
;	Airplane											300
;	Larry's Dreams (airplane)					330
;
;	Patti Cartoon (Piano Pit)					400
;	FBI Headquarters (Washington D.C.)		420
;	Closing Cartoon (White House)				460
;
;	Hard Disk Cafe (New York City)			500
;	Shared Ego Views								550
;
;	Shill Building (Baltimore)					600
;
;	Tramp's Casino (Atlantic City)			700
;
;	K-RAP Radio (Philidelphia)					800
;
;	Doc Pulliam's (Miami)						900
;	Inventory Views								930
;	System Views									990
;
;	Talker Views									1122



;ษอออออออออออออออออออป
;บ                   บ
;บ GENERIC DEFINES   บ
;บ                   บ
;ศอออออออออออออออออออผ

(define pBlackPic							001)

(define	vPushbuttons				010)
;	(define	lBackground			0)
;	(define	lButtons				1-12)
;	(define	lHangUpButton		13)
;(define	vCopyProtButtons			011)


;ษอออออออออออออออออออป
;บ                   บ
;บ OPENING CARTOON   บ
;บ                   บ
;ศอออออออออออออออออออผ

;(define	pTitleSierra					100)
;(define	pTitleLSLarryIn				105)
;(define	vLarryOpen1					105)
;	(define	lSingleCels			0)
;		(define	cBucket		0)
;		(define cBrush		1)
;	(define	lGetGlue				1)
;	(define	lSpreadGlue			2)
;	(define	lSetDownBrush		3)
;	(define	lPutUp5				4)
;(define	vLarryOpen2					106)
;	(define	lStruggleW/Bucket	0)
;	(define	lLeaningOnWall		1)
;	(define	lFootDown			2)
;	(define	lHandStuck			3)
;	(define	lStepInBucket		4)
;(define	vLarryOpen3					107)
;	(define	lStruggleW/Hand	0)
;	(define	lPullBack			1)
;(define	vLarryOpen4					108)
;	(define	lAdmire				0)
;	(define	lStepBack			1)
;	(define	lThumbsUp			2)
;	(define	lBlankPanel			3)
;	(define	lSeePatti			4)
;(define vPattiOpen					109)

;(define	pCopyProtClosed				110)
;(define	pCopyProtOpen					111)

;(define	pTriviaQuiz						115)
;(define	vTriviaPatti				115)
;(define	vTriviaLarry				116)


;ษอออออออออออออออออออป
;บ                   บ
;บ LARRY CARTOON     บ
;บ (Bigg's Office)   บ
;บ                   บ
;ศอออออออออออออออออออผ

;(define	pMobBossOffice					120)
;(define	vSlideShow					120)
;	(define	lSlides				0)
;		(define	cChart1		0)
;		(define	cChart2		1)
;		(define	cChart3		2)
;(define	vMobFlunkee1				122)
;	(define	lTalking				0)
;(define	vMobFlunkee2				123)
;	(define	lTalking				0)
;(define	vMobBoss						124)
;	(define	lREyebrow			0)
;	(define	lLEyebrow			1)
;	(define	lCigar				2)
;	(define	lHand					3)

;(define	pMrBiggCloseUp					130)
;(define	vBiggFace					130)
;	(define	lREyebrow			0)
;	(define	lLEyebrow			1)
;	(define	lMouth				2)
;	(define	lCigar				3)

;(define	pPresidentPornProdCorp		140)
;(define	vFlunkiesATPs				140)
;(define	vLarryW/Coffee				141)
;	(define	lTakingPot			0)
;	(define	lWalkingW/Pot		1)
;	(define	lTurningSouth		2)
;(define	vPPCflunkee1Speaks		142)
;(define	vPPCflunkee2Speaks		143)
;(define	vSilasScruemallSpeaks	144)
;	(define	lLookingAround		0)

;(define	pScruemallScalded				145)
;(define	vScruemallScreams		 	145)
;	(define	lYells				0)
;	(define	lWaitAminute		1)
;	(define	lLightBulb			2)
;(define	vLarryCracksPot			146)
;	(define	lScared				0)
;	(define	lPotBreaking		1)

;(define	pScruemallLarry2shot		  	150)
;(define	vScruemall					150)
;	(define	lTalking				0)
;(define	vLarryW/Scruemall			151)
;	(define	lTalking				0)


;ษอออออออออออออออออออป
;บ                   บ
;บ PORN PROD CORP    บ
;บ (Hollywood)       บ
;บ                   บ
;ศอออออออออออออออออออผ

;(define	pPornProdCorpLobby			160)
;(define	vPPCLobby					160)
;	(define	lSingleCells		0)
;		(define	cCoffeePot	0)
;	(define	lDoorPresident		1)
;	(define	lDoorFileroom		2)
;	(define	lWaterCooler		3)
;	(define	lBelch				4)
;	(define	lLarrySheepish		5)
;(define	vLarryRecharger			161)
;	(define	lBendingOver		0)
;	(define	lPlacingRecharger 1)
;	(define	lPlacingCamcorder 2)
;	(define	lRechargerInOutlet 4)
;	(define	lCamcorderCharging 5)
;(define	vCoffeeYell					162)
;	(define	lCoffeeYell			0)

;(define	pLarryWorkroom					170)
;(define	vWorkroomATPs				170)
;	(define	lSingleCells		0)
;		(define	cThreeTapes 0)			
;		(define	cCharger		1)		
;(define	vWorkroom					171)
;	(define	lOpeningDrawer		0)
;	(define	lTakingTapes 		1)
;	(define	lLarryBody			2)
;	(define	lWorkroomDoor		3)

;(define	pFileroom						180)
;(define	vFileroomATPs				180)
;	(define	lCreditCard			0)
;	(define	lDrawerEmpty		1)
;	(define	lDrawerFull			2)
;(define	vFileroom					181)
;	(define	lOpeningDrawer		0)
;	(define	lTakingFolders		1)
;	(define	lTakingCard			2)

;(define	pPornProdCorpExterior		190)
(define	vPPCExterior				190)
	(define	lLimoParked			0)
;	(define	lDoorATP				1)
;(define	vFountain					192)
;	(define	lFountain			0)
;	(define	lWaves				1)
;	(define	lDoor					2)



;ษอออออออออออออออออออป
;บ                   บ
;บ LIMOUSINE         บ
;บ                   บ
;ศอออออออออออออออออออผ

;(define	pLimousineInterior			200)
;(define	vLimoInterior				200)
;	(define	lSingleCells		0)
;		(define	cChampagne	0)
;		(define	cDayTrotter	1)
;		(define	cFaxExtruding 2)
;		(define	cWindow		3)
;		(define	cFrontWindow  4)
;		(define	cPhone		5)
;	>>> Place Holder Loop <<<	1)
;	(define	lLeftWindow			2)
;	(define	lRightWindow		3)
;(define	vLarry'sLimo				201)
;	(define	lRaisingCellPhone	0)
;	(define	lShowingDriver		1)
;	(define	lTakingDayTrotter	2)
;(define	vPatti'sLimo				202)
;	(define	lRaisingCellPhone	0)
;	(define	lTakingChampagne	1)
;	(define	lTakingFax			2)
;	(define	lLegs					3)
;	(define	lShowingFax			4)
;	(define	lShowingDataMan	5)
;(define	vDriverMale					203)
;	(define	lDriving				0)
;	(define	lEyesInMirror		1)
;	(define	lTalking				2)
;(define	vDriverFemale				204)
;	(define	lDriving				0)
;	(define	lEyesInMirror		1)
;	(define	lTalking				2)
;(define	vTVpicture					205)
;	(define	lStatic				0)
;(define	vAquarium					206)
;	(define	lSwimLeft			0)
;	(define	lSwimRight			1)
;	(define	lSwimSickLeft		2)
;	(define	lSwimSickRight		3)
;	(define	lFishDead			4)
;	(define	lWave1 				5)
;	(define	lWave2 				6)
;(define vDreamBubbles				207)
;(define	vLimoWindows				208)



;ษอออออออออออออออออออป
;บ                   บ
;บ MEANWHILE SCENES  บ
;บ                   บ
;ศอออออออออออออออออออผ

;(define	pMeanwhileMobWantsCANE		210)
;	reuse v122, v124 

;(define	pMeanwhileScruemallOnPhone	215)
;(define	vScruemallSpeaks 			215)
;(define	vScruemallWorried			217)

;(define	pMeanwhileCANEgetsAgrant	220)
;(define	vCanePresident 			220)
;(define	vCaneFlunkee1				222)

;(define	pMeanwhileCANEcongress		225)
;(define	vCaneFlunkee1withDough	225)
;(define	vSmilingCongressman		227)


;ษอออออออออออออออออออป
;บ                   บ
;บ PATTI DREAMS      บ
;บ                   บ
;ศอออออออออออออออออออผ

;(define	pDonaldtramp					230)
;(define	vTrampYacht					230)
;	(define	lWavesAgainstShip	0-1)
;(define	vPatti&DonaldWriggling	231)

;(define	pWildWillyGates				235)
;(define	vGatesRoom					235)
;	(define	lComputerDisplays	0-3)
;(define	vPatti&WillyWriggling	236)

;(define	pScroogeMcDuck					240)
;(define	vScroogeRoom				240)
;	(define	lFallingCash		0)
;(define	vPattiWrigglingInMoney	241)
;	(define	lPattiOnMoney		0)
;	(define	lScoopLoader		1)


;ษอออออออออออออออออออป
;บ                   บ
;บ AIRPORT           บ
;บ                   บ
;ศอออออออออออออออออออผ

;(define	pAirportExterior	 			250)
;(define	vAirportExterior 			250)
;	(define	lSingleCells		0)
;		(define	cLAX			0)
;		(define	cNYC			1)
;		(define	cAC			2)
;		(define	cMiami		3)
;		(define	cAirplanes	4-7)
;		(define	cClouds		8-9)
;	>>> Place Holder Loop <<<	1)
;	(define	lEnvelope			2)
;	(define	lAirportLimo		3)
;(define vAirportExteriorDoor  	251)

;(define	pATMCloseUp						255)
;(define	vATMCLoseUp					255)
;	(define	lCard					0)
;	(define	lBoardingPass		1)

;(define	pAirportLobbyWest				260)
;(define	vLarryAirportW				261)
;	(define	lPullingHandle		0)
;(define	vNYCspecific				262)
;	(define	lSingleCells		0)
;		(define	cAdForCheckerLimo	0)
;(define	vAirportAds					263)
;(define	vAtlanticCitySpecific	264)
;	(define	lSlotMachines		0)
;	(define	lSlotHandle			1)
;(define	vMiamiSpecific				265)
;	(define	lSingleCells		0)
;		(define	cCigarette	0)

;(define	pAirportLobbyCenter			270)
;(define	vAirportLobbyCenter		270)
;	(define	lDoorToVIPlounge	0)
;	(define	lCameraHigh			1)
;	>>> Place Holder Loop <<<	2)
;	(define	lCameraNod			3)
;	>>> Place Holder Loop <<<	4)
;	(define	lCameraLow			5)
;(define	vLarryAirportC				271)
;	(define	lShowingCard		0)
;(define	vNYCspecific				273)
;	(define	lSingleCells		0)
;		(define	cCharity		0)
;(define	vAtlanticCitySpecific	274)
;	(define	lSingleCells		0)
;		(define	cLimoAd		0)
;(define	vMiamiSpecific				275)
;	(define	lSingleCells		0)
;		(define	cGreenCardAd 0)
;(define	vTicketAgent1				276)
;	(define	lWalkingEast		0)
;	(define	lWalkingWest		1)
;	(define	lArguing1			2)
;	(define	lArguing2			3)
;	(define	lStanding			4)
;(define	vTicketAgent2				277)
;	(define	lWalkingEast		0)
;	(define	lWalkingWest		1)
;	(define	lArguing				2)
;	(define	lStanding			3)

;(define	pAirportLobbyEast				280)
;(define	vAirportLobbyEast			280)
;	(define	lSingleCells		0)
;		(define	cPayPhones	0)
;		(define	cHandset		1)
;(define vLarryOnPhone				281)
;(define	vAtlanticCitySpecific	284)
;	(define	lSingleCells		0)
;		(define	cT-ShirtAd	0)
;(define	vMiamiSpecific				285)
;	(define	lSingleCells		0)
;		(define	cLimoAd		0)

;(define	pVIPloungeEmpty				290)
;(define	vVIPLoungeEmpty			290)
;	(define	lSingleCells		0)
;		(define	cFoldingChair 0)
;	(define	lDoorToJetway		1)
;	(define	lNowBoarding		2)
;(define	vLarryLoungeEmpty			291)
;	(define	lSitsDown			0)
;	(define	lShakesHead			1)
;	(define	lStandsUp			2)
;	(define	lInsertingPass	  	3)
;	(define	lIntoJetway			4)
;	(define	lOutOfJetway		5)
;(define	pVIPloungeFull					295)
;(define	vVIPLoungeFull				295)
;	(define	lArm					0)
;	(define	lFlashbulbs			1)
;	(define	lLarryGrabsPhone	2)
;	(define	lLarryOnPhone		3)
;(define	vMiscReporters				297)
;	(define	lGroupShot			0)
;	(define	lPhotographers		1-4)
;	(define	lMaleReporter		5)
;	(define	lKid					6)
;	(define	lFemaleReporter	7)
;	(define	lLights				8-9)
;(define	vPeopleATPs					298)
;	(define	lPeople				0-5)


;ษอออออออออออออออออออป
;บ                   บ
;บ AIRPLANE          บ
;บ                   บ
;ศอออออออออออออออออออผ

;(define	pTakeoff							310)
;(define	vAirportATPs				310)
;	(define	lFreeway				0)
;	(define	lFlag					1)
;	(define	lWindsock			2)
;	(define	lFlag2				3)
;	(define	lBirdLeft			4)
;	(define	lBirdRight			5)
;(define	vAirplane					311)
;	(define	lFunnyLanding1		0)
;	(define	lFunnyLanding2		1)
;	(define	lCrosswiseLand		2)
;	(define	lTakeOff				3)
;	(define	lNormalLanding		4)
;	(define	lScreechToHalt		5)

;(define	pUSAMap							315)
;	(define vUSAMap 					315)
;	(define	lArrowheadEast		0)
;	(define	lArrowheadWest		1)
;	(define	lShortDashLine		2)

;(define	pSittingIn1stClass			320)
;(define	vSittingIn1stClass		320)
;	(define	lMagazine			0)
;	(define	lCloud				1)
;	(define	lChairArm			2)
;(define	vLarrySitting1stClass	321)
;	(define	lBody					0)
;	(define	lFallingAsleep		1)
;	(define	lSingleCels			2)
;		(define	cRArm			0)
;		(define	cLArm			1)
;(define	vDreamBubbles				322)
;(define	vLarrySqueezingNutBags	323)
;	(define	lSingleCels			0)
;		(define	cFace			0)
;		(define	cRArm			1)
;		(define	cLArm			2)
;(define	vLarrySpillingDrink 		324)
;(define	vLarryPushingUpTray		325)
;	(define	lSittingUp			0)
;	(define	lLookingAround		1)
;	(define	lRArm					2)
;	(define	lLArm					3)
;	(define	lSingleCels			4)
;		(define	cTrayDown	0)
;		(define	cTrayUp		1)


;ษอออออออออออออออออออป
;บ                   บ
;บ LARRY DREAMS      บ
;บ                   บ
;ศอออออออออออออออออออผ

;(define	pAthens							330)
;(define	vPattiPlayingPiano		330)

;(define	pAthensLongShot				335)
;(define	vPattiPlayingPiano		335)
;	(define	lAtPiano				0)
;	(define	lRising				1)

;(define	pAthensReverse					340)
;(define	vAthensReverse				340)
;	(define	lRosesInFlightEast 0)
;	(define	lRosesInFlightWest 1)
;(define	vPattiInAthens				341)
;	(define	lBowingRight		0)
;	(define	lBowingLeft			1)
;	(define	lTurning				2)
;	(define	lRoses				3-5)

;(define	pAfterAthens					345)
;(define	vLarryFBB					345)
;(define	vPattiFBB					346)

;(define	pVenice1							350)
;(define	vVenice1						350)
;	(define	lMoonlightOnWater	0)
;	(define	lWavesRipples		1-3)

;(define	vLarryGondolier			351)
;	(define	lPoling				0)
;	(define	lBoatWithPatti		1)
;	(define	lPole             2)

;(define	pVenice2							355)
;(define	vVenice2						355)
;	(define	lGondolaLeaking	0)
;(define	vPattiStrokingLarry		356)
;	(define	lKissing				0)

;(define	pTajMahal						370)
;(define	vTajMahal					370)
;	(define	lLSpeaker			0)
;	(define	lRSpeaker			1)
;	(define	lDrummer				2)
;(define	vPattiTajMahal				371)
;	(define	lPlayingSynth		0-2)

;(define	pAfterTajMahal					375)
;(define	vAfterTajMahal				375)
;	(define	lSnakeInBasket		0)
;	(define	lSnakeDancing		1)
;(define	vLarryAfterTajMahal		376)
;	(define	lStrokingPatti		0-1)
;	(define	lLarry'sBody		2)
;(define	vPattiAfterTajMahal		377)
;	(define	lPlayingOboe		0)

;(define	pCasablanca						380)
;(define	vCasablanca					380)
;	(define	lAirplaneProp		0)
;(define	vLarryAsBogie				381)
;(define	vPattiAsIngrid				382)

;(define	pDyingIn1stClass				385)
;(define	vDyingIn1stClass			385)
;	(define	lClouds				0)
;(define	vLarryDyingIn1stClass	386)
;	(define	lBody					0)
;	(define	lHead					1)

;(define	pFlightStimulator				390)
;(define	vFlightStimulator			390)
;	(define	lLightsDials		0-7)
;	(define	lYoke					8)
;(define	vLarryStimulator			391)
;	(define	lSingleCellStuff	0)
;		(define	cFacialTakes 0-3)
;(define	vAirliner					392)
;	(define	lSingleCellStuff	0)
;		(define	cCloudCells	0-4)
;	(define	lSpinningLeft		1)
;	(define	lSpinningRight 	2)


;ษอออออออออออออออออออป
;บ                   บ
;บ PATTI CARTOON     บ
;บ (Piano Pit)       บ
;บ                   บ
;ศอออออออออออออออออออผ

;(define	pPianoPit						400)
;(define	vPianoPit					400)
;	(define	lPatrons				0)
;(define vPattiBowing				401)
;(define lPattiBows				0)

;(define	pClubOwner'sOffice			405)
;(define	vPattiInOffice				406)
;	(define	lSitsOnDesk			0)
;	(define	lWalkEast			1)
;	(define	lWalkWest			2)
;(define	vClubOwner					407)
;	(define	lSmoking				0)

;(define	pDarkAlleyExterior			410)
;(define	vDarkAlleyExterior		410)
;	(define	lSteam				0)
;	(define	lArrow				1)
;(define	vPattiInAlley				411)
;	(define	lWalkingWest		0)
;	(define	lWalkingEast		1)
;	(define	lReflectWest		2)
;	(define	lStanding			3)
;	(define	lReflectStanding	4)
;(define	vDesmondInAlley			412)
;	(define	lWalkingEast		0)
;	(define	lWalkingWest		1)
;	(define	lSmoking				2)
;	(define	lStartWalking		3)
;	(define	lStanding			4)

;(define	pDarkAlley2Shot				415)
;(define	vDesmondFacialAnimation	415)
;(define	vPattiFacialAnimation  	416)


;ษอออออออออออออออออออป
;บ                   บ
;บ FBI HEADQUARTERS  บ
;บ (Washington D.C.) บ
;บ                   บ
;ศอออออออออออออออออออผ

;(define	pDesmond'sOffice				420)
;(define	vDesmondTalking			420)
;(define	vPattiTalking				421)

;(define	pFBIExterior					425)
;(define	vFBIExterior				425)
;	(define	lDoorToInterior	0)

;(define	pWorkshopWest					430)
;(define	vWorkshopWest				430)
;	(define	lOfficeDoor			0)
;	(define	lDoorExterior		1)
;	(define	lDoorATPs			2)
(define	vTwit							432)
	(define	lWalkingR			0)
	(define	lWalkingL			1)
	(define	lWalkingD			2)
	(define	lGesturing			3)
	(define	lPointing			4)
	(define	lTalking				5)
;(define	vVibratorMan				434)
;	(define	lWorkingAtBench	0)
;	(define	lManipulating		1-2)
;	(define	lLiftoff				3)
;	(define	lVibrating			4)
;(define	vBraMan						436)
;	(define	lStanding			0)
;	(define	lBeingAdjusted		1)
;	(define	lStartScratching	2)
;	(define	lScratchingHead	3)
;	(define	lExplosion			4)
;	(define	lEmbarassed			5)
;	(define	lShrugging			6)
;(define	vTechMan						437)
;	(define	lStanding			0)
;	(define	lAdjustingBra		1)
;	(define	lListeningToTwit	2)
;	(define	lGettingShot		3)
;	(define	lAshes				4)
;	(define	lWorkingAtBench	5)
;	(define	lHammerToWrench	6)
;(define	vDesmondInLab				438)
;	(define	lWalkingEast		0)
;	(define	lWalkingWest		1)

;(define	pWorkshopEast					440)
;(define	vWorkshopEast				440)
;	(define	lSingleCells		0)
;		(define	cDataMan		0)
;		(define	cDataPak1	1)
;		(define	cDataPak2	2)
;	(define	lOfficeDoor			1)
;	(define	lComputer			2)
;(define	vPattiWorkshopEast		441)
;	(define	lTakingDataMan		0)
;(define	vTwitUsingComputer		442)
;	(define	lFacingNorth 		0)
;(define	vFartMan						444)
;	(define	lPouringRedFluid	0)
;	(define	lDrinkingBeaker	1)
;	(define	lFacialTake			2)
;	(define	lStomachExpansion	3)
;	(define	lIgnition		  	4)
;	(define	lLift-offShooting	5)

;(define	pDr.LyccusVonPelt'sOffice	450)
;(define	vDr.VonPelt					450)
;	(define	lRaisingSheet		0-1)
;	(define	lSmilingBig			2)
;	(define	lUsingNeedle		3)
;(define vPattiW/Dr.VonPelt		451)
;(define	vVonPeltW/Hat				452)
;	(define	lGettingHat			0)
;	(define	lGoingUnderSheet	1)
;	(define	lComingUpW/OHat	2)

;(define	vPost-Pelting				458)
;	(define	lSmoking				0)
;	(define	lMissingHat			1)


;ษอออออออออออออออออออป
;บ                   บ
;บ CLOSING CARTOON   บ
;บ (White House)     บ
;บ                   บ
;ศอออออออออออออออออออผ

;(define	pWhiteHouseDinnerLongShot	460)
;(define	vWhiteHouse					460)
;	(define	lSingleCellStuff	0)
;	(define		cPattiChair	0)
;	(define		cDesmondChair 1)
;	(define		cChairBack 	2)
;	(define		cFoldChairBack 3)
;	(define	lPeople				1-3)
;(define	vLarryInWhiteHouse		461)
;	(define	lLookingLeft		0)
;	(define	lLookingRight		1)
;	(define	lTalkingToBigg		2)
;	(define	lJumpingQuayle		3)
;	(define	lSittingUp			4)
;	(define	lBackInChair		5)
;	(define	lLookLeftRight		6)
;(define	vPattiInWhiteHouse		462)
;	(define	lTalkToRight		0)
;	(define	lTalkToLeft			1)
;	(define	lFiringBra			2)
;	(define	lSmokingBra			3)
;	(define	lSitting				4)
;	(define	lWalkingEastSmall	5)
;	(define	lStanding			6)
;	(define	lElbowsBack			7)
;(define	vQuayle						463)
;	(define	lFidgeting			0)
;	(define	lWipingFace			1)
;	(define	lMadAtLarry			2)
;	(define	lTalkRightWPie		3)
;	(define	lPie					4)
;	(define	lLookAtMarilyn		5)
;(define	vDesmond						464)
;	(define	lFidgeting			0)
;	(define	lGrabbingGun		1)
;	(define	lGrabsBigg			2)
;	(define	lSitting				3)
;	(define	lWalkingWest		4)
;	(define	lWalkingEastSmall	5)
;	(define	lStanding			6)
;(define	pBiggAndPatti					465)
;(define	vBigg							465)
;	(define	lTalkingToPatti	0)
;	(define	lDrawingGun			1)
;	(define	lGettingShot		2)
;(define	vSSAgent						466)
;	(define	lWalkingW/Chair	0)
;	(define	lUnfoldsChair		1)
;	(define	lWalkingEast		2)
;	(define	lWalkingWest		3)
;(define	vMarilynQuayle				467)
;	(define	lReacting			0)
;	(define	lPlacePie			1)

;(define	pPattiEmbracesLarry			470)
;(define	vHeart						470)
;(define	vTalkingHeads				471)
;	(define	lPattiFaceTalking	0)
;	(define	lLarryFaceTalking	1)
;	(define	lLegs					2)

;(define	pHelicopteringIntoSunset	480)
;(define	vHelicopter					480)
;	(define	lCopterLarge		0)
;	(define	lCopterMedium		1)
;	(define	lCopterSmall		2)
;	(define	lCopterBladesSpinning	3)
;(define	vAdditionalCredits		481)


;ษอออออออออออออออออออป
;บ                   บ
;บ HARD DISK CAFE    บ
;บ (New York City)   บ
;บ                   บ
;ศอออออออออออออออออออผ

;(define	pHardDiskCafeExterior		500)
;(define	vHDCExterior				500)
;	(define	lSingleCells		0)
;		(define	cLimo			0)
;		(define	cDoorATP		1)
;	(define	lFlashingLights	1)
;	(define	lDoor					2)

;(define	pHDCLobby						510)
;(define	vHDCLobby					510)
;	(define	lLDoorOpening		0)
;	(define	lRDoorOpening		1)
;	(define	lTapeMachine		2)
;	(define	lTapeComingOut		3)
;(define	vLarryHDCLobby				511)
;	(define	lTakingPaperTape	0)
;	(define	lWrappingMusicBox	1)
;	(define	lTalkingOnPhone	2)
;(define	vMaitreD'					512)
;	(define	lTalking				0)
;	(define	lUsingTape			1)
;	(define	lStandingAtReader	2)
;(define	vPhoneLady					513)
;	(define	lStanding			0)
;	(define	lMouth				1)
;(define	vMusicBox					514)
;	(define	lPlayingW/Otape	0)
;(define	vMaitreD'Walking			515)
;	(define	lWalkingEast		0)
;	(define	lWalkingWest		1)

;(define	pPaperTapeReaderCloseUp		520)
;(define	vPaperTape					520)

;(define	pHDCCafe							525)
;(define	vHDCCafe						525)
;	(define	lDoor					0)
;	(define	llarry'sChair		1)
;	(define	lDinersDining		2-7)
;(define	vLarryInCafe				526)
;	(define	lSitting				0)
;	(define	lInsertingTape		1)
;(define	vMMInCafe					527)
;	(define	lWalkingEast		0)
;	(define	lInsertingTape		1)

;(define	pHollerithRoom					530)
;(define	vHollerithRoom				530)
;	(define	lCurtains			0-3)
;	(define	lMMEating			4-5)
;	(define	lDiners				6)
;	(define	lSundae				7)
;(define	vLarryHollerithRoom		531)
;	(define	lSittingAtCart		0)
;	(define	lEnteringMBooth	1)

;(define	pMMCloseUp1						535)
;(define	vFacialExpressions		535)
;	(define	lBody					0)
;	(define	lEyes					1)
;	(define	lMouth				2)
;	(define	lTongue				3)
;	(define	lSingleCels			4)
;		(define	cIceCream	0)
;		(define	cCherry		1)
;(define	vMMAnimation1				536)
;	(define	lFingerIntoCream	0)
;	(define	lSuckingFinger	  	1)

;(define	pMMCloseUp2						540)
;(define	vMMAnimation2				540)
;	(define	lGrabbingBanana	0)
;	(define	lGoingDown			1)
;	(define	lSuckingBanana		2)
;	(define	lComesUp				3)
;(define vMMCloseUp3				541)
;	(define	lStartShoulder		14)
;	(define	lShoulderWalk		25)
;(define	vLarryCloseUp				545)
;(define	vLarryFace					545)
;	(define	lBeingSurprised	0)
;	(define	lNoseWoody			1)


;ษอออออออออออออออออออป
;บ                   บ
;บ EGO VIEWS         บ
;บ                   บ
;ศอออออออออออออออออออผ

(define	vLarry						550)	; 8-Loop
(define	vLarrySmall					551)	; 8-Loop
(define	vLarrySmaller				552)	; 8-Loop
(define	vLarrySmallest				553)	; 8-Loop
(define	vLarryShocked				560)	;
;	(define	lReachingOutlet	0)
;	(define	lGettingShocked	1)
;	(define	lRecovering			2)

(define	vPatti						570)	; 8-Loop
(define	vPattiSmaller				571)	; 8-Loop
(define	vPattiSmallest				572)	; 8-Loop


;ษอออออออออออออออออออป
;บ                   บ
;บ SHILL BUILDING    บ
;บ (Baltimore)       บ
;บ                   บ
;ศอออออออออออออออออออผ

;(define	pBackseatPatti					600)
;(define	vBackseatPatti				600)
;	(define	lPattiStruggling	0)
;	(define	lPattiEscaping		1)

;(define	pShillBuildingExterior		610)
;(define	vShillBuildingExterior	610)
;	(define	lFlagLeft			0)
;	(define	lFlagRight			1)
;	(define	lDoorIn				2)
;	(define	lDoorOut			3)

;(define	pShillBuildingLobby			620)
;(define	vShillBuildingLobby		620)
;	(define	lElevatorDoorL		0)
;	(define	lElevatorDoorR		1)
;(define	vGuard						622)
;	(define	lFallingAsleep		0)
;	(define	lDozing				1)
;	(define	lTalking				2)
;	(define	lRaisingTelephone	3)
;	(define	lOnTelephone		4)
;	(define	lWorkingPanel 		5)

;(define	pBuildingDirectory			630)

;(define	pWaitingRoom					640)
;(define	vWaitingRoom				640)
;	(define	lElevatorDoor		0)
;	(define	lStudioAdoor		1)
;	(define	lGoldRecord			2)
;(define	vPattiWaitingRoom			641)
;	(define	lTakingGoldRecord	0)
;(define	vReverseBiaz				642)
;	(define	lWorkingPanel		0-1)

;(define	pStereoCloseUp					650)
;(define	vStereoCloseUp				650)
;	(define	lButtons				0-5)
;	(define	lToneArm				6)
;	(define	lTurntable			7)
;	(define	lSpeakerCone		8-9)
;	(define	lTurntableWRecord	10)

;(define	pRecordingStudio				660)
;(define	vRecordingStudio			660)
;	(define	lcontrolRoomDoor	0)
;(define	vReverseBiaz				662)
;	(define	lTalkingToPatti	0)

;(define	pSynthCloseUp					670)
;(define	vSynthCloseUp				670)
;	(define	lVUMeter				0-1)
;	(define	lTapeReels			2-5)
;	(define	lPanelATP			6)
;(define	vReverseBiaz				672)
;	(define	lPlayingWithKnobs	0-1)
;	(define	lTalkingToPatti	2)
;(define	vSynth						673)
;	(define	lBlackKeyDown		0)
;	(define	lWhiteKey1Down		1)
;	(define	lWhiteKey2Down		2)
;	(define	lWhiteKey3Down		3)

;(define	pControlRoom					680)
;(define	vPattiInControlRoom		681)
;	(define	lReverseBiaz		0)
;	(define	lPatti				1)
;(define	vPattiSexSolution			682)
;	(define	lPattiJumpsRB		0)
;	(define	lPattiPullsRBDown	1)
;	(define	lPattiLeg			2)
;	(define	lHeadphones			3)
;	(define	lMicrophone			4)
;	(define	lTapeReel			5)
;	(define	lShoe					6)
;	(define	lPanties				7)
;	(define	lRBHandingTape		8)
;(define vPattiChampagneSolution	683
;	(define	lPattiPouring		0)
;	(define	lRBDrinking			1)
;	(define	lRBSitting			2)
;	(define	lRBHandingTape		3)

;(define	pMeanwhileBackAtFBI			690)
;(define	vMeanwhileBackAtFBI		690)
;	(define	lSonarScreen		0)
;	(define	lFlashingBlip		1)
;	(define	lSonarSweep			2)
;	(define	lTechieHead			3)
;	(define	lTechieMouth		4)
;	(define	lDesmondHead		5)
;	(define	lDesmondMouth		6)


;ษอออออออออออออออออออป
;บ                   บ
;บ TRAMP'S CASINO    บ
;บ (Atlantic City)   บ
;บ                   บ
;ศอออออออออออออออออออผ

;(define	pCasinoExterior				700)
;(define	vCasinoExterior			700)
;	(define	lLimo					0)
;	(define	lFlashyLights		1-7)
;	(define	lBirdFlying			8)
;	(define	lFlashyLights		9-15)
;(define	vCasinoChangeGirl			702)
;	(define	lGivingMoney		1)
;(define	vCasinoDoorman				703)
;	(define	lHailingLimo		0)
;	(define	lLookingAway		1)
;	(define	lGettingTip			2)

;(define	pCasinoInterior				710)
;(define	vCasinoInterior			710)
;	(define	lGamblers			0-5)
;(define	vLarryInCasino				711)
;	(define	lWalking				0-3)

;(define	pCasinoVideoPoker				720)
;(define	vCasinoVideoPoker			720)
;	(define	lIncreaseBet		0)
;	(define	lDecreaseBet		1)
;	(define	lCashOut				2)
;	(define	lDeal					3)
;	(define	lHold					4)
;(define	vCardDeck					722)
;	(define	lClubs				0)
;	(define	lDiamonds			1)
;	(define	lSpades				2)
;	(define	lHearts				3)

;(define	pCasinoBallroom				730)
;(define	vCasinoBallroom			730)
;	(define	lMudWrestlingRing	0)
;(define	vLarryInBallroom			731)
;	(define	lSittingInFront	0)
;	(define	lWalkingDownAisle	1)
;	(define	lWalkingUpAisle	2)
;	(define	lMountingRing		3)
;(define	vBouncer						732)
;	(define	lBlockingAisle		0)
;	(define	lAcceptingMoney	1)
;(define	vWetT-ShirtContestant	733)
;	(define	lWalkingEast		0)
;	(define	lWalkingWest		1)
;	(define	lShowingOff			2)
;(define	vLanaInMudWrestlingSuit	734)
;	(define	lWalkingW/Robe		0)
;	(define	lTakingOffRobe		1)
;	(define	lThrowingRobe		2)
;	(define	lRobe					3)
;	(define	lWalkingW/ORobe	4)
;	(define	lJumpingIntoRing	5)
;	(define	lWalkingInMudWest	6)
;	(define	lWalkingInMudEast	7)
;	(define	lTaunting			8)
;	(define	lHandBackDown		9)

;(define	pT&ACloseUp						738)
;(define	vT&ACloseUp					738)
;	(define	lWinking				0)
;	(define	lKissing				1)

;(define	pWrestlingArcade				740)
;(define	vWrestlingArcade			740)
;	(define	lSingleCels			0)
;		(define	cBodyParts	0-6)
;(define	vLarryWrestling			741)
;	(define	lRolling				0)
;	(define	lFallingOutOfRing	1)
;(define	vLanaWrestling				742)
;	(define	lRolling				0)
;	(define	lFallingOutOfRing	1)
;(define	vLarryLanaWrestling		743)
;	(define	lCircling			0)
;	(define	lFallingOutOfRing	1)

;(define	pWrestlingMediumShot			750)
;(define	vWrestlingMediumShot		750)
;	(define	lAudienceOohing	0-11)
;(define	vFlyingClothing			752)
;	(define	lShirt				0)
;	(define	lPants				1)
;	(define	lJacket				2)
;(define	vLanaSittingUp				753)
;	(define	lSittingUp			0)
;	(define	lLookAtBucephalus	1)
;	(define	lLookAtEachOther	2)
;(define	vLarrySittingUp			754)
;	(define	lSittingUp			0)
;	(define	lLookAtBucephalus	1)
;	(define	lLookAtEachOther	2)
;	(define	lLookAtCamera		3)

;(define	pBoardwalk						760)
;(define	vBoardWalkATPs				760)
;(define	vLarryWalkingSmallest	761)
;	(define	lEast					0)
;	(define	lWest					1)
;	(define	lSouthEOLonly		2)
;	(define	lNorth				3)
;(define	vLarryWalkingSmaller		762)
;	(define	lEast					0)
;	(define	lWest					1)
;	(define	lSouthEOLonly		2)
;	(define	lNorthEOLonly		3)
;(define	vLarryWalkingSmall		763)
;	(define	lEast					0)
;	(define	lWest					1)
;	(define	lSouthEOLonly		2)
;	(define	lNorthEOLonly		3)
;(define	vLarryWalkingNormal		764)
;	(define	lEast					0)
;	(define	lWest					1)
;	(define	lSouth				2)
;	(define	lNorthEOLonly		3)
;(define	vLarrySkatingSmallest	765)
;(define	vLarrySkatingSmaller		766)
;(define vLarrySkatingSmall		767)
;(define	vLarrySkatingNormal		768)
;(define	vLanaSkating				769)
;	(define	lSkatingWest		0)
;	(define	lSkatingEast		1)
;	(define	lSpinning			2)
;	(define	lSlowing				3)
;	(define	lGliding				4)
;	(define	lSitting				5)
;	(define	lTurnsToLarry		6)
;(define	vLarryFalling				770)
;	(define	lFallingTake		0)
;	(define	lZipOutOfSight		1)
;	(define	lSplash				2)
;(define	vMiscSkaters				771-779)

;(define	pUnderBoardwalk				780)
;(define	vUnderBoardwalk			780)
;	(define	lPierWaves			0-1)
;(define	vLarrySwimming				781)
;	(define	lSwimmingEast		0)
;	(define	lSwimmingWest		1)

;(define	pIvanaSkateShop				790)
;(define	vIvanaSkateShop			790)
;	(define	lCurtain				0)
;	(define	lNeonSign			1)
;(define	vLarryInSkateShop			791)
;	(define	lSittingShoes		0)
;	(define	lChangingShoes		1)
;	(define	lSittingSkates		2)
;	(define	lSlowing				3)
;	(define	lSitting				4)
;	(define	lTurnsToLana		5)
;(define	vIvanaTramp					792)
;	(define	lWalking				0-2)
;	(define	lBending				3)

;(define	pLanaLusciousCloseUp			795)
;(define	vLanaCloseUp				795)
;	(define	lREye					0)
;	(define	lNose					1)
;	(define	lLEye					2)
;	(define	lMouth				3)
;	(define	lMouthPuckered		4)

	
;ษอออออออออออออออออออป
;บ                   บ
;บ K-RAP RADIO       บ
;บ (Philadelphia)    บ
;บ                   บ
;ศอออออออออออออออออออผ

;(define	pK-RAPRadioExterior			800)
;(define	vK-RAPRadioExterior		800)
;	(define	lK-RAPSign			0)
;	(define	lDoor					1)
;	(define	lLSpeaker			2)
;	(define	lRSpeaker			3)

;(define	pK-RAPRadioReverse			810)
;(define	vK-RAPRadioReverse		810)
;	(define	lKRAPsign			0)
;	(define	lAirplane			1)
;	(define	lLightsOfTheCity	2-3)

;(define	pK-RAPRadioLobby				820)
;(define	vK-RAPRadioLobby			820)
;	(define	lDoorToOffice		0)

;(define	pJohnKrapper'sOffice			840)
;(define	vJohnKrapper'sOffice		840)
;	(define	lSingleCells		0)
;		(define	cLetterOpener 0)
;		(define	cGlintingKey  1)
;	(define	lDoorToBath			1)
;	(define	lDoorToLobby		2)
;	(define	lCopierRunning		3)
;	(define	lCopierJamming		4)
;(define	vPattiInOffice				841)
;	(define	lOpeningDrawer		0)
;	(define	lTakingPapers		1)
;	(define	lTakingKey			2)
;	(define	lPuttingPapers		3)
;	(define	lPressingButton	4)
;	(define	lOpeningCopier		5)
;	(define	lCopierExploding	6)
;(define	vPattiBlackface			842)

;(define	pJohnKrapper'sBathroom		850)
;(define	vJohnKrapper'sBathroom	850)
;	(define	lSingleCells		0)
;		(define	cShowerW/oPatti 0)
;		(define	cPatti'sClothes 1)
;	(define	lDoorToOffice		1)
;	(define	lDoorToShower		2)
;(define	vPattiInBathroom			851)
;	(define	lSittingOnCan		0)
;	(define	lWiping				1)
;	(define	lDisrobing			2)
;	(define	lEnteringShower	3)

;(define	pK-RAPRadioAtrium				860)
;(define	vK-RAPRadioAtrium			860)
;	(define	lSingleCells		0)
;		(define	cShowerW/Patti	0)
;	(define	lHeadsInWindows 	1-8)

;(define	pK-RAPRadioStudios			870)
;(define	vK-RAPRadioStudios		870)
;	(define	lSingleCells		0)
;		(define	cShowerW/OPatti 0)
;		(define	cShowerSide		1)
;		(define	cClothesOnHook	2)
;	(define	lShowerDoor			1)
;	(define	lGroupsTalking		2-6)
;(define	vPattiInStudios			871)
;	(define	lRobing				0)
;(define	vPattiHammerpants			872)
;(define vPattiNaked					873)

;(define	pK-RAPRadioControlRoom		880)
;(define	vK-RAPRadioControlRoom	880)
;	(define	lSingleCells		0)
;		(define	cMikeStand	0)
;		(define	cBlockingDoor 1)
;	(define	lDoorToA				1)
;	(define	lDoorToB				2)
;(define	vPCHammer					882)
;	(define	lWalking 			0-1)
;(define	vHammerActions				883)
;	(define	lUsingMixer			0)
;	(define	lLookingAtPatti	1)
;	(define	lBlockingDoor		2)
;	(define	lUsingMixerLarge	3)

;(define	pInsideControlRoomB			890)
;(define	vInsideControlRoomB		890)
;	(define	lRecorderReels		0-1)
;	(define	lBreakingWindow	2)
;	(define	lMeterLights 		3-4)
;(define	vPattiInControlRoomB		891)
;	(define	lGettingTape		0)
;	(define	lUsingRecorder		1)
;	(define	lUsingBoard			2)
;	(define	lSingingIntoMike	3)
;	(define	lClimbingOutWindow 4-5)


;ษอออออออออออออออออออป
;บ                   บ
;บ DOC PULLIAM'S     บ
;บ (Miami)           บ
;บ                   บ
;ศอออออออออออออออออออผ

;(define	pDentistExterior				900)
;(define	vDentistExterior			900)
;	(define	lSingleCells		0)
;		(define	cLimo			0)
;	(define	lDoor					1)
;	(define	lRevolvingMolar	2)

;(define	pDentistWaitingRoom			905)
;(define	vDentistWaitingRoom		905)
;	(define	lOpeningWindow		0)
;	(define	lDoily				1)
;	(define	lPhone				2)
;	(define	lDoor					3)
;(define	vLarryInWaitingRoom		906)
;	(define	lKnockingOnWindow	0)
;	(define	lSitting				1)
;	(define	lFidgeting			2)
;	(define	lTakingPhone		3)
;	(define	lTakingDoily		4)
;	(define	lTalkingOnPhone	5)
(define vLarryW/Doily				907)
;	(define	lKnockingOnWindow	0)
;	(define	lSitting				1)
;	(define	lFidgeting			2)
;	(define	lTakingTelephone	3)
;	(define	lInstallingDoily	4)
(define	vLarryWearingDoily 		908)
;	(define	lWalking				0-3)
;(define	vReceptionist				909)
;	(define	cSitting				0)
;	(define	lMotioning			1)
;	(define	lLiftingPhone		2)

;(define	pChiChiCubicle					910)
;(define	vChiChiCubicle				910)
;	(define	lDrill				0)
;	(define	lSink					1)
;(define	vLarryInCubicle			911)
;	(define	lLookingAtChiChi	0)
;	(define	lOpeningWide		1)
;(define	vChiChi 						912)
;	(define	lWalkingWest		0)
;(define	vChiChiActions				913)
;	(define	lInsertingSuction	0)
;	(define	lRubbingLarry		1)
;	(define	lDancing				2-4)
;	(define	lWorking				5-6)

;(define	pChiChiCloseUp					915)
;(define	vChiChiCloseUp				915)
;	(define	lLEye					0)
;	(define	lTalking				1)
;	(define	lSmiling				2)
;	(define	lFrowning			3)

;(define	pChiChi'sGym					920)
;(define	vChiChi'sGym				920)
;	(define	lBlinds				0)
;	(define	lWallCrack			1)
;	(define	lEquipment			2)
;(define	vLarryGymnastics			921)
;	(define	lWalkingOnBeam		0)
;	(define	lFallingOnBeam		1)
;	(define	lSpinOver			2)
;	(define	lGettingOnBeam		3)
;(define	vChiChiGymnastics			922)
;	(define	lWalkOnBeam			0)
;	(define	lHandsStand			1)
;	(define	lSwingingOnRope	2)
;	(define	lJumpOffBeam		3)
;	(define	lLookingAtLarry	4)
;(define	vLarryOnRope				923)
;	(define	lSwinging			0)
;	(define	lSplat				1)
;	(define	lRope					2)
;(define	vLarryRunning				924)
;(define	vChiChiStradlingBars		925)


;ษอออออออออออออออออออป
;บ                   บ
;บ INVENTORY VIEWS   บ
;บ                   บ
;ศอออออออออออออออออออผ

	(define	ivCamcorder				931)
	(define	ivBatteryCharger		932)
	(define	ivDegausser				933)
	(define	ivVideotape				934)
	(define	ivResume		 			937)
	(define	ivHDCNapkin			 	938)
	(define	ivGoldCard				939)
	(define	ivBoardingPass			940)
	(define	ivMagazine				941)
	(define	ivQuarter			 	942)
	(define	ivDayTrotter		 	943)
	(define	ivMoney				 	944)
	(define	ivCreditCards			945)
	(define	ivMembershipTape	 	946)
	(define	ivDataMan			  	948)
	(define	ivDataPak				949)
	(define	ivCasinoMatchbook		952)
	(define	ivSilverDollar			953)
	(define	ivRollerblades			954)
	(define	ivFax						955)
	(define	ivChampagne				956)
	(define	ivGoldRecord			957)
	(define	ivCassetteTape			958)
	(define	ivDocPulliam'sCard	960)
	(define	ivGreenCard				961)
	(define	ivDoily					962)
	(define	ivLetterOpener			964)
	(define	ivDeskKey				965)
	(define	ivFolderOfEvidence	966)
	(define	ivPhotocopies			967)
	(define	ivReelToReelTape		968)
	(define	ivBra						969)


;ษอออออออออออออออออออป
;บ                   บ
;บ SYSTEM VIEWS      บ
;บ                   บ
;ศอออออออออออออออออออผ

(define	vIcons						990)
	(define	lWalkerIcon			0)
	(define	lExitIcon			15)
(define	vInvIcons					991)
(define	vQuitIcon					992)
(define	vAboutIcon					993)
(define	vGameControls				995)


;ษอออออออออออออออออออป
;บ                   บ
;บ TALKER VIEWS      บ
;บ                   บ
;ศอออออออออออออออออออผ

;(define	vMobFlunky1Talker			1122)	; Vinnie
;(define	vMobFlunky2Talker			1123)	; Bruno
;(define	vMrBiggClose-Up			1130)
;(define	vCANEFlunky1Talker		1142)	; Biffie
;(define	vCANEFlunky2Talker		1143)	; Scooter
;(define	vScruemallIntroTalker	1150)
;(define	vScruemallTalker			1215)
;(define	vCANEPresidentTalker		1220)
;(define vMobFlunky3Talker			1221)	; Luiggi
;(define	vCANEFlunkyTalker			1225)	; Mortie
;(define	vBushTalker					1295)
;(define	vLarryBogieTalker			1381)
;(define	vPattiIngridTalker		1382)
;(define	vAndyTalker					1407)
;(define	vDesmondAllyTalker		1412)
;(define	vPattiAlleyTalker			1416)
;(define	vDesmondOfficeTalker		1420)
;(define	vPattiOfficeTalker		1421)
;(define	vTwitTalker					1430)
;(define	vVonPeltTalker				1450)
;(define	vMaitreDTalker				1512)
;(define vMichelleTalker			1530)
;(define	vGuardTalker				1622)
;(define	vPattiShillTalker			1670)	; Patti talker?
;(define	vReverseBiazTalker		1672)
;(define	vChangeGirlTalker			1702)
;(define	vDoormanTalker				1703)
;(define	vLanaTalker					1731)
;(define	vBouncerTalker				1732)
;(define	vIvanaTalker				1792)
;(define	vLanaClose-upTalker		1795)
;(define	vReceptionistTalker		1909)
;(define	vChiChiTalker				1910)
