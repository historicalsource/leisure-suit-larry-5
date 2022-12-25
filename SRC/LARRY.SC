;**
;**	LARRY.SC - The Larry ego
;**
;**	LSL5:  Passionate Patti Does A Little Undercover Work
;**				Copyright 1991
;**				by Al Lowe
;**
;**	Programmed by Brian K. Hughes
;**
;**	Last Update:  September 10, 1991
;**

(script# LARRY)

(class Larry kindof Ego
	(properties
		name			"Larry"
		view			vLarry
		sightAngle	180
		description	"Larry"
		skating		FALSE		; Larry's on skates (used in 700, 760, 790)
		sitting		FALSE		; Larry's sitting down
	)
	(methods
		normalize		; does what used to be in NormalEgo procedure
		setUpInv			; adds only Larry's items to inventory
		showInv			; show inventory for Larry
	)

	(method (doVerb theVerb invItem &tmp [str 80])
		(switch theVerb
			(verbLook
				(if skating
					(TPrint "A guy never looks as good as he does on
						polystyrene wheels!")
				else
					(TPrint "You give yourself a thorough once over and
						remember why you love polyester!")
				)
			)
			(verbTalk
				(TPrint "And to think you used to laugh at your Mother when she
					would talk to herself!")
			)
			(verbDo
				(TPrint "You carefully smooth the wrinkles from your clothes.")
				(TPrint "(And spend quite a bit longer in some locations
					than others!)" LOW)
			)
			(verbZipper
				(TPrint "After a careful check to be sure no one is watching,
					you slyly \"adjust your clothing.\"")
			)
			(verbUse
				(switch invItem
					(iCamcorder
						(TPrint "You adjust your camcorder to make sure it
							has a clear view out of your pocket.")
					)
					(iHDCNapkin
						(TPrint "You mop your brow with the napkin.
							Fortunately, it's still readable.")
					)
					(iMoney
						(TPrint "You pay yourself all your money!")
					)
					(iCasinoMatchbook
						(TPrint "You attempt to set yourself on fire.")
						(TPrint "(You fail.)" LOW)
					)
					(iRollerblades
						((Inventory at: iRollerblades) doVerb: verbDo)
					)
					(iDocPulliam'sCard
						(TPrint "You pick your teeth with the business card.")
					)
					(iDoily
						((Inventory at: iDoily) doVerb: verbDo)
					)
					(else
						(Format @str "You don't need to give yourself %s, Larry;
							you already have it!"
							((Inventory at: invItem) description?)
						)
						(TPrint @str)
					)
				)
			)
			(else
				(super doVerb: theVerb invItem &rest)
			)
		)
	)
	(method (normalize theView)
		(= view (if argc theView else vLarry))
		(self
			setLoop:			RELEASE,
			setCel:			RELEASE,
			setPri:			RELEASE,
			setMotion:		FALSE,
			setCycle:		StopWalk SAMEVIEW,
			setStep:			3 2,
			z:					0,
			illegalBits:	$8000,
			ignoreActors:	FALSE,
			moveSpeed:		(theGame egoMoveSpeed?),
			cycleSpeed:		(theGame egoMoveSpeed?)
		)
	)
	(method (setUpInv)
		; We have to pass the define LARRY instead of ourselves so that
		;	the Inventory code won't try to load this module if ego is Patti
		((ScriptID INVENTORY 0) doit: LARRY)
	)
	(method (showInv)
		(if (Inventory firstTrue: #ownedBy LARRY)
			(Inventory showSelf: LARRY)
			(if (not (theIconBar curInvIcon?))
				(theIconBar
					curIcon: (theIconBar at: walkIcon),
					disable:	useIcon
				)
				(if (& ((theIconBar curIcon?) signal?) DISABLED)
					(theIconBar advanceCurIcon?)
				)
				(theGame setCursor: ((theIconBar curIcon?) cursor?))
			)
		else
			(TPrint "You have nothing, Larry.")
		)
	)
	(method (get what &tmp i)
		(for	((= i 0))
				(< i argc)
				((++ i))
			((inventory at: [what i]) moveTo: LARRY)
		)
	)
	(method (has what &tmp theItem)
		(= theItem (inventory at: what))
		(return	(and 	theItem
							(theItem ownedBy: LARRY))
		)
	)
)
