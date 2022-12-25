;**
;**	Logics for Room 520 -- Paper tape reader close-up
;**
;**	LSL5:  Passionate Patti Does A Little Undercover Work
;**				Copyright 1991
;**				by Al Lowe
;**
;**	Programmed by Carlos Escobar
;**
;**	Last Update:  August 29, 1991
;**


(script#		520)


(public
	rm520		0
)

(procedure
	Restore
)

(define tapeX 302)
(define tapeY 55)

(define	vRoom				520)

(define	sCardSlot 		258)

(define DISPLAY_X  		75)
(define DISPLAY_Y1 		60)
(define DISPLAY_Y2 		72)
(define DISPLAY_Y3 		84)
(define DISPLAY_Y4 		96)
(define DISPLAY_Y5 		108)
(define DISPLAY_WIDTH	150)

(local
	dispSave1
	dispSave2
)

(instance rm520 of LLRoom
	(properties
		picture		520
		lookStr		"You are looking at \"The Hard Disk Cafe's\" exclusive
						paper membership tape reader.
						It controls admittance to the club."
	)

	(method (init)
		(Load SOUND
			sCardSlot
		)
		(HandsOff)
		(SetupExit TRUE)
		(super init:)
		(ego hide:)
		(switch prevRoomNum
			(510
				(self setScript: sLobby)
			)
			(else ;525
				(self setScript: sCafe)
			)
		)
	);init
	(method (doVerb theVerb invItem)
		(self setScript: sTapeout NULL prevRoomNum)
	)
	(method (newRoom n)
		(SetupExit FALSE)
		(super newRoom: n)
	)
)


(instance sLobby of Script

	(method (changeState newState &tmp var1 var2)
		(switch (= state newState)
			(0
				(if (not (ego has: iMembershipTape))
					(+= state 2)
				)
				(= cycles 2)
			)
			(1
				(TPrint "You carefully insert your paper membership tape
					into the ancient machine.")
				(tape
					init:,
					setCycle:	EndLoop,
					setMotion:	MoveTo 313 18 self,
				)
				(sucking play:)
			)
			(2
				(= seconds 2)
			)
			(3
				(if (not (ego has: iMembershipTape))
					(Display "Welcome to \"THE HARD DISK CAFE\" customer database.
						Please insert your membership tape now."
						#p_at:    DISPLAY_X DISPLAY_Y1
						#p_color: colDGreen
						#p_width: DISPLAY_WIDTH
						#p_font:  smallFont
					)
					(= seconds 5)
				else
					(= dispSave1
						(Display "Processing..."
							#p_at:    DISPLAY_X DISPLAY_Y1
							#p_color: colDGreen
							#p_width: DISPLAY_WIDTH
							#p_font:  smallFont
							#p_save:
						)
					)
					(++ state)
					(= seconds 3)
				)
			)
			(4
				(TPrint "Since you have no membership tape,
						you decide there's no use wasting your time here." LOW
				)
				(curRoom newRoom: 510)
			)
			(5
				(HandsOn)
       		(switch ((Inventory at: iMembershipTape) state?)
					(tLarry
						(Display "User #104725029871492 found.\n\n
							Welcome, Mr. Larry Laffer.
							Tonight you are assigned to Track 45, Sector 4F."
							#p_at:    DISPLAY_X DISPLAY_Y2
							#p_color: colDGreen
							#p_width: DISPLAY_WIDTH
							#p_font:  smallFont
						)
					)
					(tSuper
						(Display "User #104725029871491 found.\n\n
							Welcome, SuperUser Laffer.
							Go right on in. Sit anywhere you like.
							Make yourself at home."
							#p_at:    DISPLAY_X DISPLAY_Y2
							#p_color: colDGreen
							#p_width: DISPLAY_WIDTH
							#p_font:  smallFont
						)
					)
					(tHyatt
						(Display "SuperUser #1 found.\n\n
							Welcome, Mr. Gilbert Hyatt!
							Go right on in.
							Make yourself at home.
							Sit anywhere you like!"
							#p_at:    DISPLAY_X DISPLAY_Y2
							#p_color: colDGreen
							#p_width: DISPLAY_WIDTH
							#p_font:  smallFont
						)
					)
;**						(else
;**							(Print "error")
;**						)
				)
				(= seconds 5)
			)
			(6
				(curRoom setScript: sTapeout NULL 510)
			)
		)
	)
)


(instance sCafe of Script
	(method (changeState newState &tmp var1 var2)
		(switchto (= state newState)
			(
				(= cycles 2)
			)
			(
				(TPrint "You carefully insert your paper membership tape
					into the ancient machine.")
				(tape
					init:,
					setCycle:	EndLoop,
					setMotion:	MoveTo 313 18 self,
				)
				(sucking play:)
			)
			(
				(= seconds 2)
			)
			(
				(= dispSave1
					(Display "Processing..."
						#p_at:    DISPLAY_X DISPLAY_Y1
						#p_color: colDGreen
						#p_width: DISPLAY_WIDTH
						#p_font:  smallFont
						#p_save:
					)
				)
				(= seconds 3)
			)
			(
       		(switch ((Inventory at: iMembershipTape) state?)
					(tLarry
						(= dispSave2
							(Display "User 104725029871491 found.\n\n
								ALERT! ALERT!\n\n
								SysLevUser Error!"
								#p_at:    DISPLAY_X DISPLAY_Y2
								#p_color: colDGreen
								#p_width: DISPLAY_WIDTH
								#p_font:  smallFont
								#p_save:
							)
						)
						(= register TRUE)
					)
					(tSuper
						(HandsOn)
						(Points ptsHollerithLaffer pfEnteredHollerith)
						(Display "User #104725029871491 found.\n\n
							Welcome, SuperUser Laffer.
							You may now enter the \"Herman Hollerith Room.\""
							#p_at:    DISPLAY_X DISPLAY_Y2
							#p_color: colDGreen
							#p_width: DISPLAY_WIDTH
							#p_font:  smallFont
						)
					)
					(tHyatt
						(HandsOn)
						(Points ptsHollerithHyatt pfEnteredHollerith)
						(Display "SuperUser #1 found.
							Welcome, Mr. Gilbert Hyatt.
							Feel free to enter the \"Herman Hollerith Room.\"
							Sit wherever you wish."
							#p_at:    DISPLAY_X DISPLAY_Y2
							#p_color: colDGreen
							#p_width: DISPLAY_WIDTH
							#p_font:  smallFont
						)
					)
				)
				(= seconds 5)
			)
			(
				(if register
					(Restore)

					;**	clear previous display, then display this:

					(Display "Listen, Laffer:
						your lowly clearance level prevents access to the
						exclusive \"Herman Hollerith Room.\"
						You were assigned to Track 45, Sector 4F.
						Now sit there!"
						#p_at:    DISPLAY_X DISPLAY_Y2
						#p_color: colDGreen
						#p_width: DISPLAY_WIDTH
						#p_font:  smallFont
					)
					(HandsOn)
					(= seconds 6)
				else
					(= cycles 1)
				)
			)
			(
				(curRoom setScript: sTapeout NULL 525)
			)
		)
	)
)

(instance sTapeout of Script
	(method (changeState newState)
		(switchto (= state newState)
			(
				(if (and (> ((Inventory at: iMembershipTape) state?) tLarry)
							(== prevRoomNum 525))
					(Say ego "\"Hey! Come on! Gimme back my tape!\"")
					(TPrint "That stupid machine ate your tape!
						You hope you won't need it again, Larry.")
					((Inventory at: iMembershipTape) state: tDone)
					(ego put: iMembershipTape 0)
					(= cycles 1)
				else
					(sucking play:)
					(tape
						setCycle:	BegLoop,
						setMotion: 	MoveTo tapeX tapeY self
					)
				)
			)
			(
				(curRoom newRoom: register)
			)
		)
	)
)


(procedure (Restore)
	(Display "" #p_restore: dispSave1)
	(Display "" #p_restore: dispSave2)
)

(instance tape of Actor
   (properties
      view        vRoom
      loop        0
      x           tapeX
      y           tapeY
		yStep			2
      description "your tape"
      lookStr     "Your membership tape hangs from the machine's open lip."
      sightAngle  90
		cycleSpeed	20
		signal      (| ignrAct ignrHrz fixPriOn fixedLoop fixedCel)
   )
)

(instance sucking of Sound
	(properties
		number			sCardSlot
	)
)
