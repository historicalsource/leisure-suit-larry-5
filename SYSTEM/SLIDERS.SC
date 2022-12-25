(script# SLIDERS)

(include menu.sh)

(public setControls 0)


(local
 iBState
 activeBox
 [boxRect 36]
 [barCel 3]
 windowColor
 windowBack
)


(define	CTRL_BOX_TOP		46)
(define	CTRL_BOX_LEFT		24)
(define	CTRL_BOX_BOTTOM	155)
(define	CTRL_BOX_RIGHT		296)
(define	SLIDER_Y				47)
(define	ARROWS_Y				56)
(define	SPEED_SB_X			23)
(define	VOLUME_SB_X			86)
(define	DETAIL_SB_X			147)
(define	SPEED_A_X			(+ SPEED_SB_X 17))
(define	VOLUME_A_X			(+ VOLUME_SB_X 17))
(define	DETAIL_A_X			(+ DETAIL_SB_X 17))
(define	spdCel				[barCel 0])
(define	volCel				[barCel 1])
(define	dtlCel				[barCel 2])

(define	ULY					0)
(define	ULX 					1)
(define	LRY   				2)
(define	LRX  					3)

(define	SPEED					0)
(define	VOLUME				4)
(define	DETAIL   			8)
(define	R_RESTORE  			12)
(define	R_SAVE   			16)
(define	R_RESTART  			20)
(define	R_QUIT 	 			24)
(define	OK  					28)
(define	BOX_SIZE				4)

;** set the coordinates of the rectangle that encloses the text
(procedure (SetRect text xO yO font box &tmp i)

	;** get absolute coordinates of rectangle that encloses the text
	(TextSize @[boxRect box] text font)

	;** relocate coordinates of rectangle to the window port
	(for ((= i 0)) (< i 4) ((++ i))
		(+= [boxRect (+ i box)] (if (& i 1) xO else yO))
	)
)

;** set the	coordinates of the rectangle that encloses the slider bar
(procedure (SetBarPosn ctrl sX celNo)
	(= [boxRect (+ ctrl ULY)] (+ SLIDER_Y (* (- 15 celNo) 2) 3))
	(= [boxRect (+ ctrl ULX)] sX)
	(= [boxRect (+ ctrl LRY)] (+ [boxRect (+ ctrl ULY)] 6))
	(= [boxRect (+ ctrl LRX)] (+ [boxRect (+ ctrl ULX)] 13))
)

;** redraw the slider bar if it's in a new position
(procedure (UpdateBar bar eY &tmp celNo sX i)

	;** ignore the OK box - it does not have a slider bar
	(if (!= bar OK)

		;** compute the cell# that corresponds	to the event's Y coordinate
		(= celNo (/ (- (+ SLIDER_Y 33) eY) 2))

		;** make certain that cell# is valid
		(cond
			((< celNo 0)
				(= celNo 0)
			)
			((> celNo 15)
				(= celNo 15)
			)
		)

		;** compute bar#
		(= i (/ bar 4))

		;** see if slider bar is in a new position
		(if (!= celNo [barCel i])

			;** see if control box was invoked with the mouse
			(if (& statusFlags MOUSEINVOKED)

				;** get bar's X coordinate
				(= sX [boxRect (+ bar ULX)])

				;** update the bar's new position
				(SetBarPosn	bar sX celNo)
		 	else

				;** get bar's X coordinate
				(= sX (switch i
							(0
								SPEED_SB_X
							)
							(1
								VOLUME_SB_X
							)
							(2
								DETAIL_SB_X							
							)
						)
				)
			)

			;** draw the slider bar in its new position
			(DrawCel vSliderControl lSliderBar celNo sX SLIDER_Y -1)

			;** set the bar's new cell#
 			(= [barCel i] celNo)
		
			;** update the game variables
			(ego moveSpeed: (= egoMoveSpeed (- 15 spdCel)))
			(DoSound MasterVol (= lastVolume volCel))
			(= detailLevel (/ dtlCel 5))
			(if (< detailLevel 3)
				(++ detailLevel)
			)
		)
	)
)

;** see if mouse cursor is touching one of the slider bars or the OK box
(procedure (OnBar eX eY &tmp i)

	;** test each of the control boxes for contact with the mouse
	(for ((= i SPEED)) (< i (+ OK BOX_SIZE)) ((+= i BOX_SIZE))

		;** stop if a control is found that is being touched by the mouse
		(breakif (and
						(<= [boxRect i] eY)
						(<= [boxRect (+ i 1)] eX)
						(>= [boxRect (+ i 2)] eY)
						(>= [boxRect (+ i 3)] eX)
					)
		)
	)

	;** return # of control, if any, that is being touched by the mouse
	(return i)
)

;** Draw or erase selector box
(procedure (DrawBox box color offset &tmp t l b r)

	;** compute coordinates of the selector box
	(= t (- (+ [boxRect box] offset) 3))
	(= l (- (+ [boxRect (++ box)] offset) 2))
	(= b (+ (- [boxRect (++ box)] offset) 1))
	(= r (+ (- [boxRect (++ box)] offset) 2))

	;** Draw or erase selector box 
	(Graph GDrawLine t l t r color -1 -1)
	(Graph GDrawLine t r b r color -1 -1)
	(Graph GDrawLine b r b l color -1 -1)
	(Graph GDrawLine b l t l color -1 -1)
	(Graph GShowBits t l (+ b 1) (+ r 1) VMAP)
)

;** display control box for changing game controls
(instance setControls of Code
	(method (doit &tmp curPort controlBox evt evtType evtMsg evtX evtY bar i
							 mX mY curCursor	
			  )

		;** disable icon bar while control box is on the screen
		(theIconBar state: (& (= iBState (theIconBar state?)) (~ ENABLED)))

		;** save the current port
		(= curPort (GetPort))

		;** get a window for the control box
		(= controlBox (systemWindow new:))

		;** make window's colors match the system's colors
		(= windowColor (systemWindow color?))
		(= windowBack (systemWindow back?))

		;** open the control box
		(controlBox
			top:		CTRL_BOX_TOP,
			left:		CTRL_BOX_LEFT,
			bottom:	CTRL_BOX_BOTTOM,
			right:	CTRL_BOX_RIGHT,
			open
		)

		;** set the slider bars positions for the current game control values
		(= spdCel (- 15 egoMoveSpeed))
		(= volCel lastVolume)
		(= dtlCel (switch detailLevel
						(1
							0
						)
						(2
							7
						)
						(3
							15
						)
				 	)
		)

		;** draw the slider bars on the window
		(DrawCel vSliderControl lSliderBar spdCel  SPEED_SB_X  SLIDER_Y -1)
		(DrawCel vSliderControl lArrows    cArrows SPEED_A_X   ARROWS_Y -1)
		(DrawCel vSliderControl lSliderBar volCel  VOLUME_SB_X SLIDER_Y -1)
		(DrawCel vSliderControl lArrows    cArrows VOLUME_A_X  ARROWS_Y -1)
		(DrawCel vSliderControl lSliderBar dtlCel  DETAIL_SB_X SLIDER_Y -1)
		(DrawCel vSliderControl lArrows    cArrows DETAIL_A_X  ARROWS_Y -1)

		;** add the text to the window
		(Display	{CONTROLS}	#p_at: 74  10  #p_width 75 #p_font 0 #p_color windowColor)
		(Display	{Speed} 		#p_at: 13  30  #p_width 75 #p_font 0 #p_color windowColor)
		(Display	{Volume} 	#p_at: 74  30  #p_width 75 #p_font 0 #p_color windowColor)
		(Display	{Detail} 	#p_at: 135 30  #p_width 75 #p_font 0 #p_color windowColor)
		(Display	{Fast} 		#p_at: 38  47  #p_width 75 #p_font 4 #p_color windowColor)
		(Display	{Loud} 		#p_at: 102 47  #p_width 75 #p_font 4 #p_color windowColor)
		(Display	{Normal}		#p_at: 163 47  #p_width 75 #p_font 4 #p_color windowColor)
		(Display	{Slow} 		#p_at: 38  83  #p_width 75 #p_font 4 #p_color windowColor)
		(Display	{Soft} 		#p_at: 102 83  #p_width 75 #p_font 4 #p_color windowColor)
		(Display	{Minimum}	#p_at: 163 83  #p_width 75 #p_font 4 #p_color windowColor)
		(Display	{OK}			#p_at: 180 97  #p_width 25 #p_font 0 #p_color windowColor)

		(Display	{Restore}	#p_at: 220 20  #p_width 75 #p_font 0 #p_color windowColor)
		(Display	{Save} 		#p_at: 220 40  #p_width 75 #p_font 0 #p_color windowColor)
		(Display	{Restart}  	#p_at: 220 60  #p_width 75 #p_font 0 #p_color windowColor)
		(Display	{Quit} 		#p_at: 220 80  #p_width 75 #p_font 0 #p_color windowColor)

		;** see if control box was invoked with the mouse
		(if (& statusFlags MOUSEINVOKED)

			;** set the	coordinates of the rectangles that encloses the slider bars 
			(SetBarPosn SPEED	 SPEED_SB_X  spdCel)
			(SetBarPosn VOLUME VOLUME_SB_X volCel)
			(SetBarPosn DETAIL DETAIL_SB_X dtlCel)
	 	else
	 		;** set the coordinates of the rectangles that enclose the control names
			(SetRect	{Speed}  	13  30 0 SPEED)
			(SetRect	{Volume} 	74  30 0 VOLUME)
			(SetRect	{Detail} 	135 30 0 DETAIL)

			(SetRect	{Restore} 	220 20 0 R_RESTORE)
			(SetRect	{Save} 		220 40 0 R_SAVE)
			(SetRect	{Restart} 	220 60 0 R_RESTART)
			(SetRect	{Quit} 		220 80 0 R_QUIT)

		)

		;** set the coordinates of the rectangle that encloses the OK control
		(SetRect	{OK} 180 97 0 OK)

		;** loop until a selection is made
		(repeat

			;** see if control box was invoked with the mouse
			(if (& statusFlags MOUSEINVOKED)
			
				;** default to no control active
				(= activeBox -1)

				;** loop until a mouse down event is received
				(while (!= (= evtType ((= evt (Event new:)) type?)) mouseDown)
					(evt dispose:)

					;** get current position of mouse cursor
					(= evt (Event new: $FFFF))
					(cond
						((== (evt type?) keyDown)
							(&= statusFlags (~ MOUSEINVOKED))
						)
						(else
							(|= statusFlags MOUSEINVOKED)
						)
					)
					(evt localize:)
					(= evtX (evt x?))
					(= evtY (evt y?))
					(evt dispose:)

					;** get bar#, if any, of control that the mouse is touching
					(= bar (OnBar evtX evtY))

					;** check to see if mouse is touching a control
					(cond

						;** see if OK control was selected
						((== bar OK)

							;** draw selector box around the OK control
							(if (!= activeBox OK)
								(DrawBox OK windowColor 0)
								(= activeBox OK)
							)
						)

						;** see if a game control was selected
						((OneOf bar SPEED VOLUME DETAIL)

							;** draw selector box around the selected control
							(if (!= activeBox bar)
								(= mX (+ [boxRect (+ bar ULX)] 1))
								(+= mX (/ (+ (- [boxRect (+ bar LRX)] [boxRect (+ bar ULX)]) 1) 2))
								(= mY [boxRect (+ bar ULY)])
								(+= mY (/ (+ (- [boxRect (+ bar LRY)] [boxRect (+ bar ULY)]) 1) 2))

								;** change the mouse cursor to a hand
								(theGame setCursor: (= curCursor HAND_CURSOR) TRUE mX mY)
							
								(= activeBox bar)
							)
						)

						;** no control was selected - erase selector box/change cursor
			 	 		(else

			 				(cond
								((== activeBox OK)
							
									;** erase selector box from around OK control
									(DrawBox OK windowBack 0)
									(= activeBox -1)
								)
							
								((OneOf activeBox SPEED VOLUME DETAIL)

									;** change mouse cursor back to arrow cursor
									(theGame setCursor: ARROW_CURSOR TRUE)
									(= activeBox -1)
								)
							)
						)
					)
				)

				;** exit from control box if OK control was selected
				(if (== activeBox OK)
					(evt dispose:)
					(break)
				)

				;** get current position of mouse cursor
				(evt localize:)
				(= evtX (evt x?))
				(= evtY (evt y?))
				(evt dispose:)

				;** see if mouse was clicked on one of the game controls
				(if (OneOf (= bar (OnBar evtX evtY)) SPEED VOLUME DETAIL)

					;** loop until a mouse up event is received
					(while (!= (= evtType ((= evt (Event new:)) type?)) mouseUp)
						(evt dispose:)

						;** get current position of mouse cursor
						(= evt (Event new: $FFFF))
						(evt localize:)
						(= evtX (evt x?))
						(= evtY (evt y?))
						(cond
							((== (evt type?) keyDown)
								(&= statusFlags (~ MOUSEINVOKED))
							)
							(else
								(|= statusFlags MOUSEINVOKED)
							)
						)
						(evt dispose:)

						;** update the slider bar position if the hand cursor is	active
						(if (!= curCursor ARROW_CURSOR)
							(UpdateBar bar (- evtY (/ (+ (- [boxRect (+ bar LRY)] [boxRect (+ bar ULY)]) 1) 2)))
						)

						(cond
						
							;** change to the arrow cursor if the mouse is not on a control
							((and
								(!= bar (OnBar evtX evtY))
								(!= curCursor ARROW_CURSOR)
						 	)
								(theGame setCursor: (= curCursor ARROW_CURSOR) TRUE)
							)	

							;** change to the hand cursor if the mouse is on a control
							((and
								(== bar (OnBar evtX evtY))
								(== curCursor ARROW_CURSOR)
						 	)
								(theGame setCursor: (= curCursor HAND_CURSOR) TRUE)
							)
						)
					)

					(evt dispose:)

					;** change back to the hand cursor
					(theGame setCursor: ARROW_CURSOR TRUE)
			 	else

			 		;** eat mouse events until the mouse button is released
					(while (!= (= evtType ((= evt (Event new:)) type?)) mouseUp)
						(evt dispose:)
					)
					(evt dispose:)
				)

		 	else

		 		;** hide the mouse cursor
				(theGame setCursor: invCursor TRUE)

				;** draw selector boxes around all of the controllers
				(for ((= i 0)) (< i (+ OK BOX_SIZE)) ((+= i 4))
					(DrawBox i windowColor 0)
				)

				;** default to the speed control
				(DrawBox SPEED windowColor 1)
				(= activeBox SPEED)

				;** loop until a selection is made
		 		(repeat

					;** process all key events
					(while (or
								(not (OneOf	(= evtType ((= evt (Event new:)) type?))
												keyDown
												direction
							  	  	)
								)
								(!= (= evtMsg (evt message?)) ESC)
				 	 	 	)

						;** convert any arrow key events to direction events
						(MapKeyToDir evt)
						(= evtType (evt type?))
						(= evtMsg (evt message?))

						;** exit from control box if OK control was selected
						(breakif (and (== evtMsg ENTER) TRUE )); (== activeBox OK)))

						(evt dispose:)

						;** convert Tab key and +/- key events to direction events
						(if (!= evtType direction)
							(switch evtMsg
								(TAB
									(= evtType direction)
									(= evtMsg RIGHT)
								)
								(SHIFTTAB
									(= evtType direction)
									(= evtMsg LEFT)
								)
								(`-
									(= evtType direction)
									(= evtMsg DOWN)
								)
								(`+
									(= evtType direction)
									(= evtMsg UP)
								)
								(`=
									(= evtType direction)
									(= evtMsg UP)
								)
							)
						)

						;** check to see if a direction key was pressed
						(if (== evtType direction)

							;** process direction key events
							(switch evtMsg
								(RIGHT
									
									;** move one control to the right, wrap around at OK
									(DrawBox activeBox windowBack 1)
									(= activeBox (mod (+ activeBox 4) (+ OK BOX_SIZE)))
									(DrawBox activeBox windowColor 1)
								)
								(LEFT

									;** move one control to the left, wrap around at Speed
									(DrawBox activeBox windowBack 1)
									(if (< (-= activeBox 4)	0)
										(= activeBox OK)
									)
									(DrawBox activeBox windowColor 1)
								)
								(UP

									;** move slider bar toward top of control
									(UpdateBar activeBox (- (- (+ SLIDER_Y 33) (* [barCel (/ activeBox 4) ] 2)) 2))
								)
								(DOWN

									;** move slider bar toward bottom of control
									(UpdateBar activeBox (+ (- (+ SLIDER_Y 33) (* [barCel (/ activeBox 4) ] 2)) 2))
								)
								(UP_LEFT

									;** move	selector box to speed control
									(DrawBox activeBox windowBack 1)
									(= activeBox SPEED)
									(DrawBox SPEED windowColor 1)
								)
								(DOWN_LEFT

									;** move	selector box to OK
									(DrawBox activeBox windowBack 1)
									(= activeBox OK)
									(DrawBox OK windowColor 1)
								)
							)
						)
					)

					(evt dispose:)

					;** exit from control box if OK control was selected
					(breakif (or
								 	(and
										(== evtMsg ENTER)
										(OneOf activeBox OK R_RESTORE R_SAVE R_RESTART R_QUIT)
								 	)
									(== evtMsg ESC)
								)
					)
				)
				(if (IsObject evt)
					(evt dispose:)
				)
				(break)
			)
		)

		;** dispose control box window
		(controlBox dispose:)
		(SetPort curPort)

		;** restore the original state of the icon bar
		(theIconBar state: iBState)
		(if (== evtMsg ENTER)
			(switch activeBox
				(R_RESTORE
					(theGame restore:)
				)
				(R_SAVE
					(theGame save:)
				)
				(R_RESTART
					(theGame restart:)
				)
				(R_QUIT
					(= quit TRUE)
				)
			)
		)
		(DisposeScript SLIDERS)
	)
)

