;**
;**	Logics for INVENTRY.SC
;**
;**	LSL5:  Passionate Patti Does A Little Undercover Work
;**				Copyright 1991
;**				by Al Lowe
;**
;**	Programmed by Brian K. Hughes
;**
;**	Last Update:  September 12, 1991
;**

(script#	INVENTORY)

(procedure
	XRefMsg
)

(public
	invCode 	0
	invWin	1
)


(define	bench		(ScriptID 760 1))


(local
	months =
	[
		NULL
		{January}
		{February}
		{March}
		{April}
		{May}
		{June}
		{July}
		{August}
		{September}
		{October}
		{November}
		{December}
	]
)


(class LLIconItem of IconItem
	(methods
		ownedBy		; just so  (Inventory firstTrue: #ownedBy:) works
	)
	(method (ownedBy)
		(return FALSE)
	)
)


(procedure (XRefMsg baseItem clickItem &tmp offset)
	(TPrint (+ clickItem (if egoIsLarry 50 else 80)) baseItem)
)


(instance invCode of Code
	(method (init)
		; This part only gets executed once, from the (invCode init:) in game
		(Inventory
			init:,			; must init so global 'inventory' is set
			add:	ok,		; add the ok button just to get us rolling
			window:			invWin,
			helpIconItem:	invHelp,
			selectIcon:		invSelect,
			okButton:		ok
		)
	)

	(method (doit who)
		; This part gets executed whenever one of the egos invokes setUpInv:
		(switch who
			(LARRY				; test define instead of Larry so it won't load
				(Inventory
					add:
						camcorder
						batteryCharger
						Videotape1
						Videotape2
						Videotape3
						MM'sResume
						HDCNapkin
						goldCard
						boardingPass
						magazine
						quarter
						dayTrotter
						money
						creditCards
						membershipTape
						LL'sResume
						casinoMatchbook
						silverDollar
						rollerblades
						CC'sResume
						docPulliam'sCard
						greenCard
						doily

						invLook
						invHand
						invSelect
						invHelp
						ok,
					eachElementDo: #highlightColor:	colLBlue,
					eachElementDo: #lowlightColor:	colDBlue,
					eachElementDo: #init:				,
					state:			NOCLICKHELP
				)
			)
			(PATTI
				(Inventory
					add:
						dataMan
						dataPak1Biaz
						dataPak2Hammer
						fax1ReverseBiaz
						champagne
						goldRecord
						cassetteTape
						fax2PCHammer
						letterOpener
						deskKey
						folderOfEvidence
						photocopies
						reelToReelTape
						bra

						invLook
						invHand
						invSelect
						invHelp
						ok,
					eachElementDo: #highlightColor:	colLBlue,
					eachElementDo: #lowlightColor:	colDBlue,
					eachElementDo: #init:				,
					state:			NOCLICKHELP
				)
			)
		)
	)
)


;***************************************
; our inventory window. using an instance
; so we can change the properties
;***************************************

(instance invWin of InsetWindow
	(properties
		topBordHgt		28
		botBordHgt		5
		priority			-1
	)
	(method (open)
		(invLook nsLeft: (- (/ (- (self right?) (self left?)) 2) 68))
		(super open:)
	)
)



;******************************
;	icons for inventory window
;******************************

(instance ok	of LLIconItem
	(properties
		view				vInvIcons
		loop				3
		cel				0
		signal			(| HIDEBAR IMMEDIATE RELVERIFY)
		cursor			ARROW_CURSOR
		helpStr			"Click here to close this window and return to the game."
	)
	(method (init)
		(= highlightColor 0)
		(=	lowlightColor colYellow)
		(super init:)
	)
)

(instance invLook	of LLIconItem
	(properties
		view				vInvIcons
		loop				2
		cel				0
		signal			(| RELVERIFY FIXED_POSN)
		cursor			EYE_CURSOR
		type				userEvent
		message			verbLook
		helpStr			"To look more closely at one of your inventory items,
							first click here, then click on the item."
	)
	(method (init)
		(= highlightColor 0)
		(= lowlightColor colCyan)
		(super init:)
	)
)

(instance invHand	of LLIconItem
	(properties
		view				vInvIcons
		loop				0
		cel				0
		signal			RELVERIFY
		cursor			DO_CURSOR
		type				userEvent
		message			verbDo
		helpStr			"To do something to one of your inventory items,
							first click here, then click on the item."
	)
	(method (init)
		(= highlightColor 0)
		(= lowlightColor colLGreen)
		(super init:)
	)
)

(instance invHelp	of LLIconItem
	(properties
		view				vInvIcons
		loop				1
		cel				0
		signal			RELVERIFY
		type				userEvent
		message			verbHelp
		cursor			HELP_CURSOR
		helpStr			"To learn about the other icons in this window,
							first click here, then pass the question mark
							over the other icons."
	)
	(method (init)
		(= highlightColor 0)
		(= lowlightColor colLRed)
		(super init:)
	)
)

(instance invSelect	of LLIconItem
	(properties
	  	view				vInvIcons
		loop				4
		cel				0
		signal			RELVERIFY
		type				userEvent
		cursor			ARROW_CURSOR
		helpStr			"To use one of your inventory items in the game,
							first click here, then click on the item,
							then click on OK."
	)
	(method (init)
		(= highlightColor 0)
		(= lowlightColor colLMagenta)
		(super init:)
	)
)



;******************************
;	inventory items
;******************************

; 37 total!

(instance camcorder of InvItem
	(properties
		name			{Camcorder}
		; State property is 0 if camcorder empty, enum value of tape if tape
		;	is loaded.  Also, state is +100 if camcorder is on
		view			ivCamcorder
		loop			0
		cel			0
		cursor		ivCamcorder
		signal		IMMEDIATE
		description	"the camcorder"
	)
	(method (doVerb theVerb invItem &tmp [str 50] oldTape newTape)
		(= oldTape (mod state 100))
		(switch theVerb
			(verbLook
				(TPrint "Auto-focus, auto-level, auto-color balance, auto-zoom,
					auto-pan, .5-lux, Complete-Video-\n
					Studio-in-a-Pocket-Protector.\n
					'The Rob Lowe Model'"
					#width: 250
					#title: name
				)
				(Format @str  "\nPower:  %s\n\n
					Charge: %d min %d sec\n\n"
					(if (>= state 100) {On} else {Off})
					(/ (chargeTimer seconds?) 60)
					(mod (chargeTimer seconds?) 60)
				)
				(TPrint @str
 					#width:	123
					#mode:	teJustCenter
					#title: 	name
				)
			)
			(verbDo
				(cond
					((>= state 100)
						(-= state 100)
						(TPrint "Delicately exchanging your blue ballpoint with
							your yellow highlighter, you switch the camcorder off.")
						(Format @str "Charge: %d min %d sec\n"
							(/ (chargeTimer seconds?) 60)
							(mod (chargeTimer seconds?) 60)
						)
						(TPrint @str)
					)
					((and	oldTape (chargeTimer seconds?))
						(+= state 100)
						(Points ptsStartedCamcorder pfStartedCamcorder)
						(TPrint "Slipping your green felt-tip between the
							.5-mm lead mechanical pencil and the blue ink
							rolling ball pen, you switch the camcorder on.")
						(Format @str "Charge: %d min %d sec\n"
							(/ (chargeTimer seconds?) 60)
							(mod (chargeTimer seconds?) 60)
						)
						(TPrint @str)
					)
					(oldTape
						(TPrint "Your camcorder's batteries are exhausted.")
					)
					(else
						(TPrint "The camcorder will not operate without a videotape
							inside.")
					)
				)
			)
			(verbUse
				(if (OneOf invItem iVideotape1 iVideotape2 iVideotape3)
					; see if there's a tape already there
					(if oldTape
						(TPrint "Before installing another videotape,
							you first remove the tape in the camcorder.")
						((Inventory at: oldTape) owner: LARRY)
					)

					; see if our destination tape is empty
					(if (!= ((Inventory at: invItem) state?) tapeDegaussed)
						(TPrint "Because of its tiny size,
							your camcorder has no erase heads.
							It requires tapes that have been previously degaussed.")
						(TPrint "(You DID degauss them, didn't you, Larry?)" LOW)
						(= state 0)

					; load the tape
					else
						(Points ptsTapeIntoCamcorder pfTapeIntoCamcorder)
						(TPrint "You insert the tape into \"The Rob Lowe.\"")
						(= state invItem)
						((Inventory at: invItem) owner: camcorder)
						(theIconBar curInvIcon: 0)
						(Inventory
							curIcon:	0,
							hide:
						)
						(ego showInv:)
					)
				else
					(XRefMsg (Inventory indexOf: self) invItem)
				)
			)
			(else
				(super doVerb: theVerb invItem &rest)
			)
		)
	)

)

(instance batteryCharger of InvItem
	(properties
		name			{Battery Charger}
		view			ivBatteryCharger
		loop			0
		cel			0
		cursor		ivBatteryCharger
		signal		IMMEDIATE
		description	"the battery charger"
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbLook
				(TPrint "\"Insert into any 110-volt, 20-amp GFI-protected circuit.
					Recharges most batteries in one minute!\""
					#title: name
				)
			)
			(verbDo
				(TPrint "You open the battery charger and discover there's
					no battery inside!")
			)
			(verbUse
				(switch invItem
					(iCamcorder
						(TPrint "The battery recharger must be plugged into a wall
							socket before the camcorder may be installed in it.")
					)
					(else
						(XRefMsg (Inventory indexOf: self) invItem)
					)
				)
			)
			(else
				(super doVerb: theVerb invItem &rest)
			)
		)
	)
)

(instance Videotape1 of InvItem
	(properties
		name			{A Blank Videotape}
		view			ivVideotape
		loop			0
		cel			0
		cursor		ivVideotape
		signal		IMMEDIATE
		description	"the videotape"
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbLook
				(switch state
					(tapeBlank
						(TPrint "Genuine pretty-good quality\n
							Highly biased\n
							Degauss before using\n
							Floss before brushing\n
							\n
							T-10"
							#title: 		name
							#mode			teJustCenter
						)
					)
					(tapeDegaussed
						(TPrint "The videotape is already degaussed."
							#title: name
						)
					)
					(tapeRecordedMM
						(TPrint "You had no idea financial concepts could be so
							\"physically\" rewarding!"
							#title: name
						)
					)
					(tapeRecordedLL
						(TPrint "You had no idea mud wrestling could be so
							spiritually rewarding!"
							#title: name
						)
					)
					(tapeRecordedCC
						(TPrint "You had no idea dental pain could be so rewarding!"
					  		#title: name
						)
					)
				)
			)
			(verbDo
				(TPrint "You wiggle the tiny door on the videotape just to
					make sure it still works. It does.")
			)
			(verbUse
				(XRefMsg (Inventory indexOf: self) invItem)
			)
			(else
				(super doVerb: theVerb invItem &rest)
			)
		)
	)
)

(instance Videotape2 of InvItem
	(properties
		name			{A Blank Videotape}
		view			ivVideotape	; uses same view
		loop			0
		cel			0
		cursor		ivVideotape	; uses same cursor
		signal		IMMEDIATE
		description	"the videotape"
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbLook
				(switch state
					(tapeBlank
						(TPrint "Genuine pretty-good quality\n
							Highly biased\n
							Degauss before using\n
							Floss before brushing\n
							\n
							T-10"
							#title: 		name
							#mode			teJustCenter
						)
					)
					(tapeDegaussed
						(TPrint "The videotape is already degaussed."
							#title: name
						)
					)
					(tapeRecordedMM
						(TPrint "You had no idea financial concepts could be so
							\"physically\" rewarding!"
							#title: name
						)
					)
					(tapeRecordedLL
						(TPrint "You had no idea mud wrestling could be so
							spiritually rewarding!"
							#title: name
						)
					)
					(tapeRecordedCC
						(TPrint "You had no idea dental pain could be so rewarding!"
					  		#title: name
						)
					)
				)
			)
			(verbDo
				(TPrint "You shake the videotape violently,
					seeking to prevent premature shell stickage.")
			)
			(verbUse
				(XRefMsg (Inventory indexOf: self) invItem)
			)
			(else
				(super doVerb: theVerb &rest)
			)
		)
	)
)

(instance Videotape3 of InvItem
	(properties
		name			{A Blank Videotape}
		view			ivVideotape	; uses same view
		loop			0
		cel			0
		cursor		ivVideotape	; uses same cursor
		signal		IMMEDIATE
		description	"the videotape"
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbLook
				(switch state
					(tapeBlank
						(TPrint "Genuine pretty-good quality\n
							Highly biased\n
							Degauss before using\n
							Floss before brushing\n
							\n
							T-10"
							#title 		name
							#mode			teJustCenter
						)
					)
					(tapeDegaussed
						(TPrint "The videotape is already degaussed."
							#title: name
						)
					)
					(tapeRecordedMM
						(TPrint "You had no idea financial concepts could be so
							\"physically\" rewarding!"
							#title: name
						)
					)
					(tapeRecordedLL
						(TPrint "You had no idea mud wrestling could be so
							spiritually rewarding!"
							#title: name
						)
					)
					(tapeRecordedCC
						(TPrint "You had no idea dental pain could be so rewarding!"
					  		#title: name
						)
					)
				)
			)
			(verbDo
				(TPrint "You smooth down the label on the videotape with your
					hand.")
			)
			(verbUse
				(XRefMsg (Inventory indexOf: self) invItem)
			)
			(else
				(super doVerb: theVerb &rest)
			)
		)
	)
)

(instance MM'sResume of InvItem
	(properties
		name			{Michelle Milken's Resume}
		view			ivResume
		loop			0
		cel			0
		cursor		ivResume
		signal		IMMEDIATE
		description	"Michelle's resume"
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbLook
				(TPrint "NAME________Michelle Milken\n
					AGE_________28\n
					HAIR________Black\n
					EYES________Brown\n
					HEIGHT______6'_1\"\n
					WEIGHT______131\n
					"
					#font			monoFont
					#icon			ivResume 2 1
					#title		name
				)
				(TPrint
				  "OCCUPATION__junk bond sales\n
					HANGOUTS____Wall Street,\n
					____________computer stores\n
					TURNONS_____anything covered\n
					____________with whipped cream\n
					TURNOFFS____strong moral fiber\n
					"
					#font			monoFont
					#width		222
					#title		name
				)
				; If the Maitre D' didn't take the napkin & we don't have it,
				;	Give it to us now
				(if (not (OneOf (HDCNapkin owner?) LARRY 510))
					(Points ptsOpenedFolderMM pfOpenedFolderMM)
					(TPrint "Inside her folder, you find a napkin.")
					(ego get: iHDCNapkin)
					(theIconBar curInvIcon: 0)
					(Inventory
						curIcon:	0,
						hide:
					)
					(ego showInv:)
				)
			)
			(verbDo
				(TPrint "You open Michelle Milken's resume and look inside.")
				(self doVerb: verbLook)
			)
			(verbUse
				(XRefMsg (Inventory indexOf: self) invItem)
			)
			(else
				(super doVerb: theVerb invItem &rest)
			)
		)
	)
)

(instance HDCNapkin of InvItem
	(properties
		name			{Hard Disk Cafe Napkin}
		view			ivHDCNapkin
		loop			0
		cel			0
		cursor		ivHDCNapkin
		signal		IMMEDIATE
		description	"the Hard Disk Cafe napkin"
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbLook
				(TPrint "If computers are your life,\n
					grab a quick byte in our cafe!\n
					In the Heart of the Fabulous\n
					Times Square Wine Country\n
					920 W. 42nd Street\n
					New York City"
					#title:		name
					#width:		222
					#mode:		teJustCenter
				)
			)
			(verbDo
				(TPrint "Exploring for a secret message from someone in New
					York City, you unfold the Hard Disk Cafe napkin.
					Alas, you find nothing written on the napkin.")
			)
			(verbUse
				(XRefMsg (Inventory indexOf: self) invItem)
			)
			(else
				(super doVerb: theVerb invItem &rest)
			)
		)
	)
)

(instance goldCard of InvItem
	(properties
		name			{AeroDork Gold Card}
		view			ivGoldCard
		loop			0
		cel			0
		cursor		ivGoldCard
		signal		IMMEDIATE
		description	"the AeroDork Gold Card"
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbLook
				(TPrint "\"Whether in the U.S.A. or the South Pacific,
					it's Nontoonyt for you on AeroDork!\""
					#title: name
				)
			)
			(verbDo
				(TPrint "You scratch the AeroDork Gold Card and find it isn't
					made of solid gold.")
			)
			(verbUse
				(XRefMsg (Inventory indexOf: self) invItem)
			)
			(else
				(super doVerb: theVerb invItem &rest)
			)
		)
	)
)

(instance boardingPass of InvItem
	(properties
		name			{Boarding Pass}
		view			ivBoardingPass
		loop			0
		cel			0
		cursor		ivBoardingPass
		signal		IMMEDIATE
		description	"the boarding pass"
	)
	(method (doVerb theVerb invItem &tmp [str 200] date m d y)
		(switch theVerb
			(verbLook
				(= date 	(GetTime SYSDATE))
				(= d	 	(& date 31))
				(= m 	 	(& (>> date 5) 15))
				(= y 	 	(+ (>> date 9) 1980))
				(Format @str "This boarding pass good for one First
					Class Seat on AeroDork Flight #1, %s to %s, seat %d%c,
					%s %d, %d only."
					(switch larryLoc
						(LosAngeles
							{L.A.}
						)
						(NewYork
							{New York}
						)
						(AtlanticCity
							{Atlantic City}
						)
						(Miami
							{Miami}
						)
					)
					(switch state
						(LosAngeles
							{L.A.}
						)
						(NewYork
							{New York}
						)
						(AtlanticCity
							{Atlantic City}
						)
						(Miami
							{Miami}
						)
					)
					(/ airplaneSeat 100)
					(mod airplaneSeat 100)
					[months m]
					d
					y
				)
				(TPrint @str #title: name)
			)
			(verbDo
				(TPrint "You open the boarding pass and discover it looks
					like it would just fit inside the AeroDork Boarding Pass
					Folder that came in the box with your copy of this game!")
			)
			(verbUse
				(XRefMsg (Inventory indexOf: self) invItem)
			)
			(else
				(super doVerb: theVerb invItem &rest)
			)
		)
	)
)

(instance magazine of InvItem
	(properties
		name			{AeroDork's In-Flight Magazine}
		view			ivMagazine
		loop			0
		cel			0
		cursor		ivMagazine
		signal		IMMEDIATE
		description	"the magazine"
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbLook
				(TPrint "Good idea;
					save this in case the restroom runs out of paper!"
					#title:	name
				)
			)
			(verbDo
				(TPrint "Opening the magazine, you discover an interesting
					article. It says computer punch cards are descendants
					of a long line of mechanical products dating all the
					way back to the music box.")
				(Points ptsReadMagazine pfReadMagazine)
				(TPrint "You promise to remember this information,
					in case you ever run across a music box in a computer store.")
			)
			(verbUse
				(XRefMsg (Inventory indexOf: self) invItem)
			)
			(else
				(super doVerb: theVerb invItem &rest)
			)
		)
	)
)

(instance quarter	of InvItem
	(properties
		name			{Some Change}
		view			ivQuarter
		loop			0
		cel			0
		cursor		ivQuarter
		signal		IMMEDIATE
		description	"the quarter"
	)
	(method (doVerb theVerb invItem &tmp [str 40])
		(switch theVerb
			(verbLook
				(Format @str "You always were a %d-bit game player!"
					(* quarters 2)
				)
				(TPrint @str #title: name)
			)
			(verbDo
				(Format @str "You flip the quarter in the air.
					It comes up %s." (if (Random 0 1) {heads} else {tails})
				)
				(TPrint @str)
			)
			(verbUse
				(XRefMsg (Inventory indexOf: self) invItem)
			)
			(else
				(super doVerb: theVerb invItem &rest)
			)
		)
	)
)

(instance dayTrotter of InvItem
	(properties
		name			{DayTrotter\05}
		view			ivDayTrotter
		loop			0
		cel			0
		cursor		ivDayTrotter
		signal		IMMEDIATE
		description	"the DayTrotter\05"
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbLook
				(if state
					(TPrint "You always wanted to have one of these,
						but you never had anything to schedule!"
						#title: name
					)
				else
					(Points ptsOpenedDayTrotter pfOpenedDayTrotter)
					(TPrint "Inside you find: some folding money with more than two
						digits in the corners, many credit cards, some mutual fund
						account access codes, and a Swiss bank account number."
						#title: name
					)
					(theIconBar curInvIcon: 0)
					(Inventory
						curIcon:	0,
						hide:
					)
					(= state 1)
					(ego
						get: 		iMoney iCreditCards,
						showInv:
					)
				)
			)
			(verbDo
				(if state
					(TPrint "You thumb through the pages looking for anything
						that might help you, but find nothing except
						some alphabetical page dividers, which you rip out of the
						binder in disgust and throw away!"
						#title: name
					)
				else
					(self doVerb: verbLook)
				)
			)
			(verbUse
				(XRefMsg (Inventory indexOf: self) invItem)
			)
			(else
				(super doVerb: theVerb &rest)
			)
		)
	)
)

(instance money of InvItem
	(properties
		name			{Money}
		view			ivMoney
		loop			0
		cel			0
		cursor		ivMoney
		signal		IMMEDIATE
		description	"the money"
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbLook
				(TPrint "This is even better than having your own
					savings and loan (as you think you may have had in
					\"LSL4: The Missing Floppies!\")"
					#title: name
				)
			)
			(verbDo
				(TPrint "You really enjoy running your money through your
					fingers. However, counting that high is beyond your
					limited mathematical skills.")
			)
			(verbUse
				(XRefMsg (Inventory indexOf: self) invItem)
			)
			(else
				(super doVerb: theVerb invItem &rest)
			)
		)
	)
)

(instance creditCards of InvItem
	(properties
		name			{Credit Cards}
		view			ivCreditCards
		loop			0
		cel			0
		cursor		ivCreditCards
		signal		IMMEDIATE
		description	"the credit cards"
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbLook
				(TPrint "Whoever this Pat Patterson guy was, he was sure loaded!"
					#title: name
				)
			)
			(verbDo
				(TPrint "You carefully count the credit cards.
					There are more than you ever imagined having.")
			)
			(verbUse
				(XRefMsg (Inventory indexOf: self) invItem)
			)
			(else
				(super doVerb: theVerb invItem &rest)
			)
		)
	)
)

(instance membershipTape of InvItem
	(properties
		name			{Membership Tape}
		view			ivMembershipTape
		loop			0
		cel			0
		cursor		ivMembershipTape
		signal		IMMEDIATE
		description	"the membership tape"
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbLook
				(switch state
					(tLarry								;**	Just got it
						(TPrint "What an interesting way to make a
							club membership card!
							No one uses this antique, 9-track, paper tape any more!"
							#title: name
						)
					)
					(tSuper								;**	Maitre d' upgrade
						(TPrint "Whoever said you're not a man of credit?"
							#title: name
						)
					)
					(tHyatt								;**	After music box
						(TPrint "How clever you are!
							A few extra holes and you're a new man!"
							#title: name
						)
					)
				)
			)
			(verbDo
				(TPrint "You briefly consider folding, spindling or mutilating
					this tape, but remembering your early training,
					decide not to do it--at least, not here.")
			)
			(verbUse
				(XRefMsg (Inventory indexOf: self) invItem)
			)
			(else
				(super doVerb: theVerb &rest)
			)
		)
	)
)

(instance dataMan of InvItem
	(properties
		name			{DataMan}
		view			ivDataMan
		loop			0
		cel			0
		cursor		ivDataMan
		signal		IMMEDIATE
		owner			445
		description	"the DataMan"
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbLook
				(switch state
					(dmEmpty
						(TPrint "You are amazed the Japanese didn't come up
							with this product first!"
							#title: name
						)
					)
					(dmPak1
						(TPrint "Reverse Biaz\n
							des Rever Records\n
							Suite 900\n
							Shill Building\n
							1000 Upson Downs Lane\n
							Baltimore, MD\n
							"
							#title: 		name
							#mode:		teJustCenter
						)
						(Bset fSeenRBOffice)
					)
					(dmPak2
						(Points ptsGotKrapCode pfGotKrapCode)
						(TPrint "P. C. Hammer\n
							K-RAP Radio\n
							1709 Hillview Vista\n
							Philadelphia, PA\n
							Access code: 45954\n
							\n
							often in Control Room B\n
							Access code: n/a\n
							"
							#title: 		name
							#mode:		teJustCenter
						)
					)
				)
			)
			(verbDo
				(switch state
					(dmEmpty
						(TPrint "The DataMan has no on/off switch
							as it is always on.
							However, right now it is empty.")
					)
					(dmPak1
						(= state dmEmpty)
						(TPrint "You remove Reverse Biaz's DataPak from your
							DataMan.")
						(dataPak1Biaz owner: PATTI)
						(theIconBar curInvIcon: 0)
						(Inventory
							curIcon:	0,
							hide:
						)
						(ego showInv:)
					)
					(dmPak2
						(= state dmEmpty)
						(TPrint "You remove P. C. Hammer's DataPak from your
							DataMan.")
						(dataPak2Hammer owner: PATTI)
						(theIconBar curInvIcon: 0)
						(Inventory
							curIcon:	0,
							hide:
						)
						(ego showInv:)
					)
				)
			)
			(verbUse
				(switch invItem
					(iDataPak1Biaz
						(Points ptsInsertedDataPak pfInsertedDataPak)
						(if (== (dataMan state?) dmEmpty)
							(TPrint "You insert Reverse Biaz's DataPak into your
								DataMan.")
							(= state dmPak1)
							(dataPak1Biaz owner: self)
							(theIconBar curInvIcon: 0)
							(Inventory
								curIcon:	0,
								hide:
							)
							(ego showInv:)
						else
							(TPrint "The DataMan already contains a DataPak.")
						)
					)
					(iDataPak2Hammer
						(Points ptsInsertedDataPak pfInsertedDataPak)
						(if (== (dataMan state?) dmEmpty)
							(TPrint "You insert P. C. Hammer's DataPak into your
								DataMan.")
							(= state dmPak2)
							(dataPak2Hammer owner: self)
							(theIconBar curInvIcon: 0)
							(Inventory
								curIcon:	0,
								hide:
							)
							(ego showInv:)
						else
							(TPrint "The DataMan already contains a DataPak.")
						)
					)
					(else
						(XRefMsg (Inventory indexOf: self) invItem)
					)
				)
			)
			(else
				(super doVerb: theVerb &rest)
			)
		)
	)
)

(instance dataPak1Biaz of InvItem
	(properties
		name			{Reverse Biaz DataPak}
		view			ivDataPak
		loop			0
		cel			0
		cursor		ivDataPak
		signal		IMMEDIATE
		owner			445
		description	"the Reverse Biaz DataPak"
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbLook
				(TPrint "Reverse Biaz\n
					\n
					Insert one end first"
					#title: 		name
					#mode:		teJustCenter
				)
			)
			(verbDo
				(TPrint "You check the DataPak carefully for any signs of
					damage. You find none.")
			)
			(verbUse
				(XRefMsg (Inventory indexOf: self) invItem)
			)
			(else
				(super doVerb: theVerb invItem &rest)
			)
		)
	)
)

(instance dataPak2Hammer of InvItem
	(properties
		name			{P. C. Hammer DataPak}
		view			ivDataPak	; uses same view
		loop			0
		cel			0
		cursor		ivDataPak	; uses same cursor
		signal		IMMEDIATE
		owner			445
		description	"the P. C. Hammer DataPak"
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbLook
				(TPrint "P. C. Hammer\n
					\n
					Insert one end first"
					#title: 		name
					#mode:		teJustCenter
				)
			)
			(verbDo
				(TPrint "You check the DataPak carefully for any signs of
					damage. You find none.")
			)
			(verbUse
				(XRefMsg (Inventory indexOf: self) invItem)
			)
			(else
				(super doVerb: theVerb invItem &rest)
			)
		)
	)
)

(instance LL'sResume of InvItem
	(properties
		name			{Lana Luscious' Resume}
		view			ivResume	; uses same view
		loop			0
		cel			0
		cursor		ivResume	; uses same cursor
		signal		IMMEDIATE
		description	"Lana's resume"
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbLook
				(TPrint
				  "NAME________Lana Luscious\n
					AGE_________21\n
					HAIR________Blond\n
					EYES________Blue\n
					HEIGHT______5'_10\"\n
					WEIGHT______123\n
					"
					#font			monoFont
					#icon			ivResume 2 2
					#title		name
				)
				(TPrint
				  "OCCUPATION__professional mud wrestler,\n
					____________former \"Miss Wet T-Shirt\n
					____________of Atlantic City\"\n
					HANGOUTS____Tramp's Boardwalk\n
					TURNONS_____polyurethane, mud\n
					TURNOFFS____high IQ's\n
					"
					#font			monoFont
					#width		277
					#title		name
				)
				(if (not (ego has: iCasinoMatchbook))
					(Points ptsOpenedFolderLL pfOpenedFolderLL)
					(TPrint "Inside her folder, you find a matchbook.")
					(ego get: iCasinoMatchbook)
					(theIconBar curInvIcon: 0)
					(Inventory
						curIcon:	0,
						hide:
					)
					(ego showInv:)
				)
			)
			(verbDo
				(TPrint "You open Lana Luscious' resume and look inside.")
				(self doVerb: verbLook)
			)
			(verbUse
				(XRefMsg (Inventory indexOf: self) invItem)
			)
			(else
				(super doVerb: theVerb invItem &rest)
			)
		)
	)
)

(instance casinoMatchbook of InvItem
	(properties
		name			{Tramp Casino Matchbook}
		view			ivCasinoMatchbook
		loop			0
		cel			0
		cursor		ivCasinoMatchbook
		signal		IMMEDIATE
		description	"the Tramp's casino matchbook"
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbLook
				(TPrint "\"Visit beautiful Tramp Casino
					on the beautiful Tramp Boardwalk
					outside the luxurious Tramp Hotel
					near the palatial Tramp Overscale Miniature Golf Course
					right off filthy Tramp Beach!\n
					\n
					777 High Roller Drive\n
					Atlantic City\""
					#title: 		name
					#mode:		teJustCenter
				)
			)
			(verbDo
				(TPrint "Just for fun, you strike a match and hold it between
					your fingers as long as possible, until it burns down so
					far that you think you're about to burn yourself.
					Then you drop it and rub it out with your toe.")
				(TPrint "(Thus, you amuse yourself greatly!)" LOW)
			)
			(verbUse
				(XRefMsg (Inventory indexOf: self) invItem)
			)
			(else
				(super doVerb: theVerb invItem &rest)
			)
		)
	)
)

(instance silverDollar of InvItem
	(properties
		name			{Silver Dollar}
		view			ivSilverDollar
		loop			0
		cel			0
		cursor		ivSilverDollar
		signal		IMMEDIATE
		description	"the silver dollar"
	)
	(method (doVerb theVerb invItem &tmp [str 200])
		(switch theVerb
			(verbLook
		 		(Format @str "You have %d silver dollar%s,
					but you desire much, much more. "
		 		 	silvDollars
		 		 	(if (> silvDollars 1) {s} else {})
		 		)
				(TPrint @str #title: name)
				(TPrint "Hey, wait a minute!\n
					\n
					These aren't real silver dollars.
		 		 	Every single one of them has Donald Tramp's picture on it!")
			)
			(verbDo
				(TPrint "You enjoy the clinking sound these silver dollars make.")
			)
			(verbUse
				(XRefMsg (Inventory indexOf: self) invItem)
			)
			(else
				(super doVerb: theVerb &rest)
			)
		)
	)
)

(instance rollerblades of InvItem
	(properties
		name			{Roller-skates}
		view			ivRollerblades
		loop			0
		cel			0
		cursor		ivRollerblades
		signal		IMMEDIATE
		description	"the roller-blades"
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbLook
				(TPrint "With these on your feet you feel as though you could
					fly... right off the boardwalk."
					#title: name
				)
			)
			(verbDo
				(if (== curRoomNum 760)
					(Inventory hide:)
					(bench doVerb: verbUse iRollerblades)
				else
					(TPrint "You can't skate here, Larry!")
				)
			)
			(verbUse
				(XRefMsg (Inventory indexOf: self) invItem)
			)
			(else
				(super doVerb: theVerb invItem &rest)
			)
		)
	)
)

(instance fax1ReverseBiaz of InvItem
	(properties
		name			{Reverse Biaz Fax}
		view			ivFax
		loop			0
		cel			0
		cursor		ivFax
		signal		IMMEDIATE
		description	"the fax on Reverse Biaz"
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbLook
				(TPrint "Reverse Biaz\n
					des Rever Records\n
					Suite 900\n
					Shill Building\n
					1000 Upson Downs Lane\n
					Baltimore, MD\n
					"
					#title: 		name
					#mode:		teJustCenter
				)
				(Bset fSeenRBOffice)
			)
			(verbDo
				(TPrint "This fax paper sure is flimsy!")
			)
			(verbUse
				(XRefMsg (Inventory indexOf: self) invItem)
			)
			(else
				(super doVerb: theVerb invItem &rest)
			)
		)
	)
)

(instance champagne of InvItem
	(properties
		name			{Champagne}
		view			ivChampagne
		loop			0
		cel			0
		cursor		ivChampagne
		signal		IMMEDIATE
		description	"the champagne"
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbLook
				(TPrint "You do so love a nice wet Brute!"
					#title: name
				)
			)
			(verbDo
				(TPrint "You begin to pop the cork, but decide to wait
					for a more opportune moment.")
			)
			(verbUse
				(XRefMsg (Inventory indexOf: self) invItem)
			)
			(else
				(super doVerb: theVerb invItem &rest)
			)
		)
	)
)

(instance goldRecord of InvItem
	(properties
		name			{Gold Record}
		view			ivGoldRecord
		loop			0
		cel			0
		cursor		ivGoldRecord
		signal		IMMEDIATE
		description	"the gold record"
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbLook
				(if state
					(TPrint "Excellent detective work, Patti!"
						#title: name
					)
				else
					(TPrint "For years you've wondered what happened to
						\"The Four Shabangs!\""
						#title: name
					)
				)
			)
			(verbDo
				(TPrint "You have your doubts that this record is solid gold.")
			)
			(verbUse
				(XRefMsg (Inventory indexOf: self) invItem)
			)
			(else
				(super doVerb: theVerb &rest)
			)
		)
	)
)

(instance cassetteTape of InvItem
	(properties
		name			{Cassette Tape}
		view			ivCassetteTape
		loop			0
		cel			0
		cursor		ivCassetteTape
		signal		IMMEDIATE
		description	"the cassette tape"
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbLook
				(TPrint "A smoking gun of the subtle degradation of our nation's
					youth by those filth-mongering blasphemers,
					wouldn't you say, Patti?"
					#title: name
				)
			)
			(verbDo
				(TPrint "You hold the cassette tape up to your ear and listen
					carefully.  You do not hear the ocean roar!")
			)
			(verbUse
				(XRefMsg (Inventory indexOf: self) invItem)
			)
			(else
				(super doVerb: theVerb invItem &rest)
			)
		)
	)
)

(instance CC'sResume of InvItem
	(properties
		name			{Chi Chi Lambada's Resume}
		view			ivResume	; uses same view
		loop			0
		cel			0
		cursor		ivResume	; uses same cursor
		signal		IMMEDIATE
		description	"Chi Chi's resume"
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbLook
				(TPrint "NAME________Chi Chi Lambada\n
					AGE_________24\n
					HAIR________Raven\n
					EYES________Chestnut\n
					HEIGHT______5'_8\"\n
					WEIGHT______101\n
					"
					#font			monoFont
					#icon			ivResume 2 0
					#title		name
				)
				(TPrint
				  "OCCUPATION__dental hygienist and\n
					____________former gymnast\n
					HANGOUTS____the gym; the office\n
					TURNONS_____locker room odor,\n
					____________dental irrigators\n
					TURNOFFS____burning rubber\n
					"
					#font			monoFont
					#width		240
					#title		name
				)
				(if (not (ego has: iDocPulliam'sCard))
					(Points ptsOpenedFolderCC pfOpenedFolderCC)
					(TPrint "Inside her folder, you find a business card.")
					(ego get: iDocPulliam'sCard)
					(theIconBar curInvIcon: 0)
					(Inventory
						curIcon:	0,
						hide:
					)
					(ego showInv:)
				)
			)
			(verbDo
				(TPrint "You open Chi Chi Lambada's resume and look inside.")
				(self doVerb: verbLook)
			)
			(verbUse
				(XRefMsg (Inventory indexOf: self) invItem)
			)
			(else
				(super doVerb: theVerb invItem &rest)
			)
		)
	)
)

(instance docPulliam'sCard of InvItem
	(properties
		name			{Doc Pulliam's Card}
		view			ivDocPulliam'sCard
		loop			0
		cel			0
		cursor		ivDocPulliam'sCard
		signal		IMMEDIATE
		description	"Doc Pulliam's card"
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbLook
				(Points ptsGotDocNum pfGotDocNum)
				(TPrint "\"Can Doc Fixem?\n
					Nope, but can 'Doc Pulliam!!'\"\n
					\n
					Doc Pulliam's Dental Hygiene Heaven,\n
					169 Lower Wacker Drive\n
					Miami, FL\n
					Dial 554-3627\n
					\n
					Our Motto:\n
					\"Beats a doorknob and string!\""
					#title: 		name
					#width:		222
					#mode:		teJustCenter
				)
			)
			(verbDo
				(TPrint "You turn the business card over expecting to find
					a secret clue on the back.")
				(TPrint "You don't.")
			)
			(verbUse
				(XRefMsg (Inventory indexOf: self) invItem)
			)
			(else
				(super doVerb: theVerb invItem &rest)
			)
		)
	)
)

(instance greenCard of InvItem
	(properties
		name			{Green Card}
		view			ivGreenCard
		loop			0
		cel			0
		cursor		ivGreenCard
		signal		IMMEDIATE
		description	"the green card"
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbLook
				(TPrint "Official U. S. Government Alien Resident Permit"
					#title: name
				)
			)
			(verbDo
				(TPrint "Whatever possessed you to \"purchase\" one of these?")
			)
			(verbUse
				(XRefMsg (Inventory indexOf: self) invItem)
			)
			(else
				(super doVerb: theVerb invItem &rest)
			)
		)
	)
)

(instance doily of InvItem
	(properties
		name			{Doily}
		view			ivDoily
		loop			0
		cel			0
		cursor		ivDoily
		signal		IMMEDIATE
		description	"the doily"
	)

	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbLook
				(TPrint "Isn't this sweet!
					It would look great back home in your apartment."
					#title: name
				)
				(TPrint "(If you HAD an apartment!)" LOW
					#title: name
				)
				(TPrint "(If you had a HOME!)" LOW
					#title: name
				)
			)
			(verbDo
				(cond
					((!= curRoomNum 905)
						(TPrint "You'd be too embarrassed to wear the doily now!")
					)
					((ego sitting?)
						(TPrint "Stand up first.")
					)
					(else
						(if state
							(ego
								view: 		vLarryW/Doily,
								setLoop:		4,
								setCel:		LASTCEL,
								cycleSpeed:	10,
								setCycle:	BegLoop curRoom,	; so room can do normalize:
							)
							(TPrint "Sadly, you remove the doily from your head.")
		 				else
							(ego
								view: 		vLarryW/Doily,
								setLoop:		4,
								setCel:		0,
								cycleSpeed:	10,
								setCycle:	EndLoop curRoom, ; so room can do normalize:
							)
							(Points ptsWoreDoily pfWoreDoily)
							(TPrint "Feeling rather silly,
								you wrap the doily around your head in a classic
								\"3 Stooges-toothache\" style.")
						)
						(= state (not state))
					)
				)
			)
			(verbUse
				(XRefMsg (Inventory indexOf: self) invItem)
			)
			(else
				(super doVerb: theVerb &rest)
			)
		)
	)
)

(instance fax2PCHammer of InvItem
	(properties
		name			{P. C. Hammer Fax}
		view			ivFax 					; uses same view
		loop			0
		cel			0
		cursor		ivFax						; uses same cursor
		signal		IMMEDIATE
		description	"the fax on P. C. Hammer"
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbLook
				(TPrint "P. C. Hammer\n
					K-RAP Radio\n
					1709 Hillview Vista\n
					Philadelphia, PA\n
					Access code: 45954\n
					often in Control Room B\n
					Access code: n/a\n
					"
					#title: 		name
					#width:		200
					#mode:		teJustCenter
				)
			)
			(verbDo
				(TPrint "This fax paper sure is flimsy!")
			)
			(verbUse
				(XRefMsg (Inventory indexOf: self) invItem)
			)
			(else
				(super doVerb: theVerb invItem &rest)
			)
		)
	)
)

(instance letterOpener of InvItem
	(properties
		name			{Letter Opener}
		view			ivLetterOpener
		loop			0
		cel			0
		cursor		ivLetterOpener
		signal		IMMEDIATE
		description	"the letter opener"
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbLook
				(TPrint "This is merely a razor-sharp letter opener;
					don't try anything funny with it like picking locks!"
					#title: name
				)
			)
			(verbDo
				(TPrint "You nearly cut your hand on the needle-like
					letter opener.")
			)
			(verbUse
				(XRefMsg (Inventory indexOf: self) invItem)
			)
			(else
				(super doVerb: theVerb invItem &rest)
			)
		)
	)
)

(instance deskKey of InvItem
	(properties
		name			{Desk Key}
		view			ivDeskKey
		loop			0
		cel			0
		cursor		ivDeskKey
		signal		IMMEDIATE
		description	"the desk key"
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbLook
				(TPrint "Did you know that one key will fit the lock of every
					piece of pressed-cardboard furniture in the world!?"
					#title: name
				)
			)
			(verbUse
				(XRefMsg (Inventory indexOf: self) invItem)
			)
			(verbDo
				(TPrint "Did you know that one key will fit the lock of every
					piece of pressed-cardboard furniture in the world!?"
					#title: name
				)
			)
			(else
				(super doVerb: theVerb invItem &rest)
			)
		)
	)
)

(instance folderOfEvidence of InvItem
	(properties
		name			{Folder Of Evidence}
		view			ivFolderOfEvidence
		loop			0
		cel			0
		cursor		ivFolderOfEvidence
		signal		IMMEDIATE
		description	"the folder of evidence"
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbLook
				(Points ptsLookInFolder	pfLookInFolder)
				(TPrint "Examining the papers inside the folder carefully,
					you find one with some references to gangster activities.
					It is signed only with a scrawled name you make out
					to be \"Julius.\""
					#title: name
				)
				(TPrint "These papers provide proof positive of Mob payola
					at K-RAP Radio! But how will you get them out of here
					without Krapper finding out?"
					#title: name
				)
			)
			(verbDo
				(if (ego has: iPhotocopies)	;ego can never lose these once copied
					(TPrint "There is nothing more to find in this folder.
						Besides, you already have photocopies of everything
						inside.")
				else
					(TPrint "Checking through these papers makes you realize
						you need some way to photocopy them.")
				)
			)
			(verbUse
				(XRefMsg (Inventory indexOf: self) invItem)
			)
			(else
				(super doVerb: theVerb invItem &rest)
			)
		)
	)
)

(instance photocopies of InvItem
	(properties
		name			{Photocopied Evidence}
		view			ivPhotocopies
		loop			0
		cel			0
		cursor		ivPhotocopies
		signal		IMMEDIATE
		description	"the photocopies"
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbLook
				(TPrint "These copies provide proof positive of Mob payola
					at K-RAP Radio!"
					#title: name
				)
			)
			(verbDo
				(TPrint "Good job sneaking these photocopies out of K-RAP,
					Patti!")
			)
			(verbUse
				(XRefMsg (Inventory indexOf: self) invItem)
			)
			(else
				(super doVerb: theVerb invItem &rest)
			)
		)
	)
)

(instance reelToReelTape of InvItem
	(properties
		name			{Reel To Reel Tape}
		view			ivReelToReelTape
		loop			0
		cel			0
		cursor		ivReelToReelTape
		signal		IMMEDIATE
		description	"the reel-to-reel tape"
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbLook
				(if state
					(TPrint "\t\t\t\t\"D D D\"\n
						\n
						This tape is filled with digitally-recorded
						incriminating evidence!"
						#title: name
					)
				else
					(TPrint "If you could look closely enough,
						you could see billions of magnetic particles
						pointing in random directions."
						#title: name
					)
					(TPrint "(In other words, this tape is still blank!)" LOW)
				)
			)
			(verbDo
				(TPrint "You really need a tape deck to use this, Patti.")
			)
			(verbUse
				(XRefMsg (Inventory indexOf: self) invItem)
			)
			(else
				(super doVerb: theVerb &rest)
			)
		)
	)
)

(instance bra of InvItem
	(properties
		name			{Hooter Shooter}
		view			ivBra
		loop			0
		cel			0
		cursor		ivBra
		signal		IMMEDIATE
		description	"the \"Hooter Shooter\" bra"
	)
	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbLook
				(Points ptsLookedBra pfLookedBra)
				(TPrint "Commander Twit's boys may be into explosives,
					but they know nothing about comfort or adequate support."
					#title:		name
				)
			)
			(verbDo
				(self doVerb: verbLook)
			)
			(verbUse
				(XRefMsg (Inventory indexOf: self) invItem)
			)
			(else
				(super doVerb: theVerb &rest)
			)
		)
	)
)

