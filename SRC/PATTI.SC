;**
;**	PATTI.SC - The Patti ego
;**
;**	LSL5:  Passionate Patti Does A Little Undercover Work
;**				Copyright 1991
;**				by Al Lowe
;**
;**	Programmed by Brian K. Hughes
;**
;**	Last Update:  September 10, 1991
;**

(script# PATTI)

(class Patti kindof Ego
	(properties
		name			"Patti"
		view			vPatti
		sightAngle	180
		description	"Patti"
	)
	(methods
		normalize		; does what used to be in NormalEgo procedure
		setUpInv			; adds only Patti's items to inventory
		showInv			; show inventory for Patti
	)

	(method (doVerb theVerb invItem &tmp [str 80])
		; 'actions' will return TRUE if the action was claimed
		(if (and (IsObject actions)
					(actions doit: theVerb invItem)
				)
			(return TRUE)
		)
		(switch theVerb
			(verbLook
				(TPrint "You look yourself over. You like what you see.
					\"Although I could stand to lose a few pounds,\" you think.")
			)
			(verbTalk
				(Say ego "\"I really enjoy these little talks,\"
					you say to yourself.")
			)
			(verbDo
				(if (Btst fWearingBra)
					(TPrint "You begin to adjust a bra strap before remembering
						you'd need a crowbar!")
				else
					(TPrint "You begin to adjust your bra strap before
						remembering you'd need a bra!")
				)
			)
			(verbZipper
				(TPrint "Maintaining your cool,
					you surreptitiously check the zipper on your dress.
					It's fine.")
			)
			(verbUse
				(switch invItem
					(iChampagne
						(TPrint "As much as you'd love to celebrate,
							you hate to drink alone!")
					)
					(iGoldRecord
						(TPrint "You can't listen to the gold record--without
							equipment.")
					)
					(iLetterOpener
						(TPrint "You slide the letter opener inside the back of
							your dress and scratch that itch that's been driving
							you crazy!")
						(Say ego "\"Ahhh!\"")
					)
					(iBra
						(if (Btst fWearingBra)
							(Points ptsTakeOffBra)
							(TPrint "You gladly remove your double-barrelled bra.")
							(Bclr fWearingBra)
						else
							(Points ptsPutOnBra)
							(TPrint "You slip your genuine F.B.I. bra over
								your shoulders.")
							(Say ego "\"YOW! That sucker's cold!\"")
							(Bset fWearingBra)
						)
					)
					(else
						(Format @str "You don't need to give yourself %s, Patti;
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
		(= view (if argc theView else vPatti))
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
			moveSpeed: 		(theGame egoMoveSpeed?),
			cycleSpeed:		(theGame egoMoveSpeed?)
		)
	)
	(method (setUpInv)
		; We have to pass the define PATTI instead of ourselves so that
		;	the Inventory code won't try to load this module if ego is Patti
		((ScriptID INVENTORY 0) doit: PATTI)
	)
	(method (showInv)
		(if (Inventory firstTrue: #ownedBy PATTI)
			(Inventory showSelf: PATTI)
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
			(TPrint "You have nothing, Patti.")
		)
	)
	(method (get what &tmp i)
		(for	((= i 0))
				(< i argc)
				((++ i))
			((inventory at: [what i]) moveTo: PATTI)
		)
	)
	(method (has what &tmp theItem)
		(= theItem (inventory at: what))
		(return	(and 	theItem
							(theItem ownedBy: PATTI))
		)
	)
)

