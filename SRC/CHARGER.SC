;**
;**	Logics for CHARGER -- Plugging in and unplugging the camcorder charger
;**
;**	LSL5:  Passionate Patti Does A Little Undercover Work
;**				Copyright 1991
;**				by Al Lowe
;**
;**	Programmed by Brian K. Hughes
;**
;**	Last Update:  September 10, 1991
;**

(script#	CHARGER)

(public
	sPlugCharger	0
	sUnplugCharger	1
	sGetShocked		2
	charger			3
)


(define	vLarryRecharger				161)
(define		lBendingOver			0)
(define		lPlacingRecharger 	1)
(define		lPlacingCamcorder 	2)
(define		lRechargerInOutlet 	3)
(define		lCamcorderCharging 	4)

(define	sYeow!			147)
(define	sShock			561)

(local
	chargerCoords =
	[
		160	64 	108
		260	19		146
		NULL
	]
)

(instance sPlugCharger of Script
	(method (changeState newState &tmp i)
		(switchto (= state newState)
			(
				(HandsOff)
			  	(ego
			  		view:				vLarryRecharger,
			  		setLoop:			lBendingOver,
					setCel:			0,
					cycleSpeed:		10,
					ignoreActors:	TRUE,
					illegalBits:	0,
			  		setCycle:		EndLoop self
				)
			)
			(
				(ego
					setLoop:			lPlacingRecharger,
					setCel:			0,
					setCycle:		EndLoop self
				)
			)
			(
				(= i 0)
				(while (!= [chargerCoords i] NULL)
					(if (== [chargerCoords i] curRoomNum)
						(charger
							x:			[chargerCoords (++ i)],
							y:			[chargerCoords (++ i)],
							init:		,
							setLoop: lRechargerInOutlet
						)
						(break)
					else
						(+= i 3)
					)
				)
				(ego
					setLoop:			lBendingOver,
					setCel:			LASTCEL,
					setCycle:		BegLoop self
				)
			)
			(
				(ego
					put: 				iBatteryCharger curRoom,
					setLoop:			facingLeft,
					normalize:
				)
				(Points ptsPluggedInCharger pfPluggedInCharger)
				(TPrint "Cleverly spotting an empty electrical outlet,
					you bend down and stick your battery charger in the socket.")
				(HandsOn)
				(self dispose:)
			)
		)
	)
);sPlugCharger


(instance sPlugCamcorder of Script
	(method (changeState newState)
		(switchto (= state newState)
			(
				(HandsOff)
			  	(ego
			  		view:				vLarryRecharger,
			  		setLoop:			lBendingOver,
					setCel:			0,
					cycleSpeed:		10,
					ignoreActors:	TRUE,
					illegalBits:	0,
			  		setCycle:		EndLoop self
				)
			)
			(
				(ego
					setLoop:			lPlacingCamcorder,
					setCel:			0,
					setCycle:		EndLoop self
				)
			)
			(
				(charger setLoop: lCamcorderCharging)
				(ego
					setLoop:			lBendingOver,
					setCel:			LASTCEL,
					setCycle:		BegLoop self
				)
			)
			(
				(ego
					put: 				iCamcorder (Inventory at: iBatteryCharger),
					setLoop:			facingLeft,
					normalize:
				)
				(charger approachVerbs: verbDo verbUse verbLook)
				(Points ptsPutCorderInCharger pfPutCorderInCharger)
				(TPrint "Bending over, you insert your Pocket Camcorder into
					the battery charger and watch the \"Charging\" lamp
					illuminate.")
				(HandsOn)
				(self dispose:)
			)
		)
	)
);sPlugCamcorder


(instance sUnplugCharger of Script
	(method (changeState newState &tmp n)
		(switchto (= state newState)
			(
				(HandsOff)
			  	(ego
			  		view:				vLarryRecharger,
			  		setLoop:			lBendingOver,
					setCel:			0,
					cycleSpeed:		10,
					ignoreActors:	TRUE,
					illegalBits:	0,
			  		setCycle:		EndLoop self
				)
			)
			(
				(charger dispose:)
				(ego
					setLoop:			lPlacingRecharger,
					setCel:			LASTCEL,
					setCycle:		BegLoop self
				)
			)
			(
				(ego
					setLoop:			lBendingOver,
					setCel:			LASTCEL,
					setCycle:		BegLoop self
				)
			)
			(
				(ego
					get: 				iBatteryCharger,
					get: 				iCamcorder,
					setLoop:			facingLeft,
					normalize:
				)
				((Inventory at: iCamcorder) owner: LARRY)
				(Points ptsUnpluggedCharger pfUnpluggedCharger)
				(if (== (charger loop?) lCamcorderCharging)
					(TPrint "You bend down and remove your battery charger and
						camcorder from the electrical socket."
					)
				else
					(TPrint "You bend down and remove your battery charger
						from the electrical socket."
					)
				)
				(HandsOn)
				; Set the chargeTimer to same % of seconds as charge
				;	Example: 50% charge = 150 seconds, from 300 seconds total
				(= n (* 300 (/ camcorderCharge 20)))
				(chargeTimer setReal: chargeTimer (/ n 100))
				(self dispose:)
			)
		)
	)
);sUnplugCharger


(instance sGetShocked of Script
	(method (changeState newState)
		(switchto (= state newState)
			(
				(HandsOff)
				(ego
					posn:			(ego x?) (- (ego y?) 2),
					view: 		vLarryShocked,
					setLoop: 	0,
					setCel: 		0,
					cycleSpeed: 10,
					setCycle: 	EndLoop self,
				)
			)
			(
				(shockSound
					number:		sShock,
					setLoop:		1,
					play:
				)
				(ego
					setPri:		13,
					setLoop: 	1,
					setCycle:   Forward,
				)
				(= cycles (Random 25 40))
			)
			(
				(shockSound
					number:		sYeow!,
					play:
				)
				(ego
					setLoop:    2,
					setCel:		0,
					setCycle:   EndLoop self,
				)
			)
			(
				(ego
					setLoop:		3,
					setCel:		0,
					setCycle: 	EndLoop self,
				)
			)
			(
				(ego
					setLoop: 	2,
					normalize:
				)
				(= ticks 60)
			)
			(
				(HandsOn)
				(self dispose:)
			)
		)
	)
);sGetShocked

(instance shockSound of Sound
	(properties
		number		sShock
	)
)


(instance charger of View
	(properties
		view			vLarryRecharger
		loop			lRechargerInOutlet
		description	"the battery charger"
	)
	(method (init)
		(super init:)
		(self
			approachVerbs: verbDo verbUse,
			signal:			(&= signal (~ stopUpdOn))
		)
		(= approachX (+ x 12)) ;15
		(= approachY (+ y 1))  ;2
	)
	(method (doVerb theVerb invItem &tmp [str 50])
		(switch theVerb
			(verbLook
				(if (== ((Inventory at: iCamcorder) owner?) (Inventory at: iBatteryCharger))
					(Format @str  "______Battery Strength: %d%c"
						(/ camcorderCharge 20)
						37
					)
					(TPrint @str
						#mode:	teJustCenter
						#title:	{Camcorder Battery Strength}
					)
				else
					(TPrint "Your lonely battery charger sits all by itself,
						with plenty of power to suck from the wall,
						but with no place to push it!")
				)
			)
			(verbDo
				(curRoom setScript: sUnplugCharger)
			)
			(verbUse
				(switch invItem
					(iCamcorder
						(curRoom setScript: sPlugCamcorder)
					)
					(else
						(super doVerb: theVerb invItem &rest)
					)
				)
			)
			(verbZipper
				(TPrint "You want to stick it where?!")
			)
			(else
				(super doVerb: theVerb invItem &rest)
			)
		)
	)
)
