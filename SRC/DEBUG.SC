(script#	DEBUG)

;;; Debug object for rooms that can handle it (i.e. enough heap)
;;; 04/05/89 J.M.H.
;;; February 26, 1991 BKH for LL5
;**	March 23, 1991 AL
;**	July 22, 1991 AL
;**	August 18, 1991 AL reduced extra-wide ALT-R window


(define	ALT_A			`@A)	; Actor Cast
(define  ALT_B			`@B)  ; polygon editor
(define	ALT_C			`@C)	; ControlMap
(define	ALT_D			`@D)	; detach a script
(define	ALT_E			`@E)	; show ego info
(define	ALT_F			`@F)	; show features
(define	ALT_G			`@G)	; setGlobal
(define	ALT_H			`@H)	; show a global
(define	ALT_I			`@I)	; get an inv item
(define	ALT_J			`@J)	; justify text (locate Display on screen)
(define	ALT_K			`@K)	; show palette
(define	ALT_L			`@L)	; QA Note Logger
(define	ALT_M			`@M)	; give yourself some money
(define	ALT_N			`@N)	; note logger
(define	ALT_O			`@O)	; UNUSED
(define	ALT_P			`@P)	; PriorityMap
(define	ALT_Q			`@Q)	; Quick
(define	ALT_R			`@R)	; RoomNum
(define	ALT_S			`@S)	; switch sex from Larry to Patti
(define	ALT_T			`@T)	; Teleport
(define	ALT_U			`@U)	; set user control to true
(define	ALT_V			`@V)	; VisualMap
(define	ALT_W			`@W)	; load feature writer
(define	ALT_X			`@X)	; quick quit
(define	ALT_Y			`@Y)	; UNUSED
(define	ALT_Z			`@Z)	; quick quit (same as @X)

(define	HELP			`?)	; help screen
(define	CTRL_F		`^F)	; justify text - change font
(define	CTRL_N		`^N)	; justify text - new text
(define	CTRL_C		`^C)	; justify text - change color


(public
	debugHandler	0
)

(extern
	NameFind		NAMEFIND 0
)

(local
	 yesI
	 [theText 25]
	 oldCursor
)

; cuz GetNumber has too small an input box for a 3 digit negative number
; used only in vanishing point thing
(procedure	(GetNum string default &tmp [theLine 40])
	(= theLine 0)
	(if (> argc 1)
		(Format @theLine "%d" default)
	)
	(return
		(if (GetInput @theLine 10 string)
			(ReadNumber @theLine)
		else
			-1
		)
	)
)

; if the current pic was drawn with a SCROLL style, the control or priority
; screens don't show correctly.  this just redraws the pic with the PLAIN style
; so the Show uses that style.
; i tried to just do (curRoom style: PLAIN), but it didn't work
(procedure	(CheckScroll)
	(if (OneOf (curRoom style?) SCROLLRIGHT SCROLLLEFT SCROLLUP SCROLLDOWN)
		(curRoom
			drawPic: (curRoom picture?) PLAIN,
			style:	PLAIN
		)
	)
)


(instance debugHandler of Feature
	(method (init)
		(super init:)
		(mouseDownHandler addToFront: self)
		(keyDownHandler addToFront: self)
	)
	(method (dispose)
		(mouseDownHandler delete: self)
		(keyDownHandler delete: self)
		(super dispose:)
		(DisposeScript NAMEFIND)
		(DisposeScript DEBUG)
	)
	(method 	(handleEvent event &tmp [str 75] [str1 75] [str2 10] wind evt num
					obj underbits palNum t l r b marginHigh marginWide
		)
		(switch (event type)
			(keyDown
				(event claimed: TRUE)	;by default, we unclaim in else clause
				(switch (event	message)

;** Show Cast

					(ALT_A
						(for ((= num (cast first:))) num ((= num (cast next: num)))
							(= obj (NodeValue num))
 							(Format @str
								"class: %s\n
								view: %d\n
								loop: %d\n
								cel:  %d\n
								posn: %d %d %d\n
							 	heading: %d\n
							 	pri: %d\n
							 	signal: $%x\n
							 	illBits: $%x\n
								"
								((obj superClass?) name?)
								(obj view?)
								(obj loop?)
								(obj cel?)
								(obj x?)
								(obj y?)
								(obj z?)
								(obj heading?)
								(obj priority?)
								(obj signal?)
								(if (or
										(== (obj superClass?) Actor)
										(== (obj superClass?) Ego)
										)
									(obj illegalBits?)
								else
									65535
								)
							)
							(Print @str
								#window:	SysWindow
								#title:	(obj name?)
								#icon:	(obj view?) (obj loop?) (obj cel?)
							)
						)
					)

;** Polygon Editor

					(ALT_B
						(PolygonEditor doit:)
					)

;** Control Map

					(ALT_C
						(CheckScroll)
						(Show CMAP)
						(Animate (cast elements?))
						(while (== 0 ((= event (Event new: (- allEvents mouseUp))) type?))
							(event dispose:)
						)
						(event dispose:)
						(Show VMAP)
					)

;;** Detach a script
;
;					(ALT_D
;						(= str1 0)
;						(GetInput @str1 30 {Enter object name: })
;						(if (not (= obj (NameFind @str1 cast)))
;							(if (not (= obj (NameFind @str1 regions)))
;								(if (not (= obj (NameFind @str1 mouseDownHandler)))
;									(if (not (= obj (NameFind @str1 keyDownHandler)))
;										(Format @str "Cannot find object: %s" @str1)
;										(Print @str)
;										(return FALSE)
;									)
;								)
;							)
;						)
;						(if (obj respondsTo: #setScript:)
;							(if (obj script?)
;								(obj setScript: 0)
;							else
;								(Format @str "%s has no script" @str1)
;								(Print @str)
;							)
;						else
;							(Format @str "script is not a selector for %s" @str1)
;							(Print @str)
;						)
;					)

;** Show Ego

					(ALT_E
						(Format @str
							"name: %s\n
						 	view: %d\n
							loop: %d\n
							cel: %d\n
							posn: %d %d %d\n
							heading: %d\n
							pri: %d\n
							signal: $%x\n
							illBits: $%x\n
							OnControl: $%x\n
							Origin on: $%x
							"
							(ego name?)
							(ego view?)
							(ego loop?)
							(ego cel?)
							(ego x?)
							(ego y?)
							(ego z?)
							(ego heading?)
							(ego priority?)
							(ego signal?)
							(ego illegalBits?)
							(ego onControl:)
							(ego onControl: origin)
						)
						(Print @str
							#icon: (ego view?) (ego loop?) (ego cel?)
						)
					)

;** Show features

					(ALT_F
						(features eachElementDo: #perform: showFeatureCode)
						(NameFeatureCode init:)
					)

;** Set global

					; these next 2 work cuz ego is global 0
					(ALT_G
						(= str 0)
						(GetInput @str 4 {Variable No.})
						(= num (ReadNumber @str))
						(= str 0)
						(GetInput @str 4 {Value})
						(= [ego num] (ReadNumber @str))
						(= str 0)
					)

;** Show global

					(ALT_H
						(= str 0)
						(GetInput @str 4 {Variable No.})
						(= num (ReadNumber @str))
						(if (IsObject [ego num])
							(Format @str " Global %d: %s " num ([ego num] name?))
							(Print @str)
						else
							(Format @str " Global %d: %d " num [ego num])
							(Print @str)
						)
						(= str 0)
					)

;** Get inventory items

					(ALT_I
						(dInvDialog doit:)
					)

;;** Justify text
;
;					(ALT_J
;						(JustifyText init:)
;					)

;;** Show palette
;
;					(ALT_K
;						(= wind (GetPort))
;						(SetPort 0)
;						(= marginHigh 5)
;						(= marginWide 16)
;						(= t 15)
;						(= l 80)
;						(= b (+ t (* 34 marginHigh)))
;						(= r (+ l (* 10 marginWide)))
;						(= underbits (Graph GSaveBits t l b r VMAP))
;   					(Graph GFillRect t l b r VMAP	255)
;						(for ((= palNum 0)) (< palNum 256) ((++ palNum))
;							(Graph GFillRect
;								(+ t marginHigh (* marginHigh (/ palNum 8)))
;								(+ l marginWide (* 16 (mod palNum 8)))
;								(+ t marginHigh marginHigh (* marginHigh (/ palNum 8)))
;								(+ l marginWide marginWide (* marginWide (mod palNum 8)))
;								VMAP
;								palNum
;							)
;						)
;						(Graph GShowBits t l b r VMAP)
;						(repeat
;							(= evt (Event new:))
;							(if (or (== (evt type?) mouseDown) (== (evt type?) keyDown))
;								(break)
;							)
;							(evt dispose:)
;						)
;						(evt dispose:)
;						(Graph GRestoreBits underbits)
;  						(Graph GShowBits t l b r VMAP)
;						(SetPort wind)
;					)

;** QA Note Logger

					(ALT_L
						(if (HaveMem $600)
						  	((ScriptID LOGGER) doit: @sysLogPath 0)
						else
							(Print "Not Enough Memory!!")
						)
					)

;** Get money

					(ALT_M
						(Format @str "%d" larryDollars)
						(Format @str2 "%d" silvDollars)
						(PrintD
							{Dollars: }
							#edit @str 6
							#new
							{Silver:__}
							#edit @str2 6
						)
						(ego put: iMoney 0)
						(ego put: iSilverDollar 0)
						(if (= larryDollars (ReadNumber @str))
							(ego get: iMoney)
						)
						(if (= silvDollars (ReadNumber @str2))
							(ego get: iSilverDollar)
						)
					)

;;** Note logger
;
;					(ALT_N
;						;
;						;Note: if this module gets too big, put this in ll5's
;						;handleEvent so QA can write bugs
;						;
;						;(if (HaveMem $600)
;						;	((ScriptID LOGGER) doit: @sysLogPath 0)
;						;else
;						;	(Print "Not Enough Memory!!")
;						;)
;
;
;					;(ALT_N						;**	make a Note
;						(= str	0)
;						(= str1	0)
;						(= str2	0)
;						(while (GetInput @str 50 {Press \"Enter\" or \"ESC\" when done.})
;							(Format @str2 "%d.txt" curRoomNum)
;							(Format @str1 ";[R%3d V%3d XY%3d/%3d] "
;  										curRoomNum (ego view) (ego x) (ego y)
;							)
;							(File
;								name: 			@str2,
;								writeString:	@str1 @str "\r",
;								close:			,
;							)
;							(= str 0)
;						)
;						(File
;							name: 			@str2,
;							writeString:	"\r",
;							close:			,
;						)
;					)

;** Priority Map

					(ALT_P
						(CheckScroll)
						(Show PMAP)
					)

;** Quick

					(ALT_Q
						(theGame detailLevel: 1)
					)

;** Show room

					(ALT_R
						(Format @str
							"name: %s\n
							number: %d\n
							current pic: %d\n
							style: %d\n
							horizon: %d\n
							north: %d\n
							south: %d\n
							east: %d\n
							west: %d\n
							script: %s
							"
							(curRoom name?)
							curRoomNum
							(curRoom curPic?)
							(curRoom style?)
							(curRoom horizon?)
							(curRoom north?)
							(curRoom south?)
							(curRoom east?)
							(curRoom west?)
							(if (IsObject (curRoom script?))
								((curRoom script?) name?)
							else
								{..none..}
							)
						)
						(Print @str
							#width		120
						)
						(theGame showMem:)
					)

;;** Switch sex
;
;					(ALT_S
;						(if (== (ego name?) {Larry})
;							(EgoIs PATTI)
;						else
;							(EgoIs LARRY)
;						)
;						(ego normalize:)
;					)

;** Teleport

					(ALT_T
						; just cuz you couldn't teleport while a modeless dialog
						; was up
						(Cls)
						(if (> (= num (GetNumber {Teleport to})) 0)
;							(Load SCRIPT num)
							(curRoom newRoom num)
							(= TPed num)
						)
					)

;** Set HandsOn

					(ALT_U
						(User
							canInput:	TRUE,
							canControl:	TRUE
						)

						(theIconBar enable:
											walkIcon
											lookIcon
											doIcon
											talkIcon
											zipperIcon
											ffIcon
											useIcon
											inventoryIcon
						)
					)

;** Visual Map

					(ALT_V
						(Show VMAP)
					)

;** Feature Writer

					(ALT_W
						(CreateObject doit:)
					)

;** Quick quit

					(ALT_X
						(= quit TRUE)
					)
					(ALT_Z
						(= quit TRUE)
					)

;;** Debug help
;
;					(HELP
;						(Print "Debug options:______(Page 1 of 5)\n\n
;							___A - Show cast\n
;							___B - Polygon editor\n
;							___C - Show control map\n
;							___D - Bring up debugger\n
;							___E - Show ego info\n
;							___F - Show feature outlines\n
;							___G - Set global\n"
;						)
;						(Print "Debug options:______(Page 2 of 5)\n\n
;							___H - Show global\n
;							___I - Get inventory item\n
;							___J - Justify text on screen\n
;							___K - Show palette\n
;							___M - Get money\n
;							___N - Note logger\n
;							___O - Show room description\n"
;						)
;						(Print "Debug options:______(Page 3 of 5)\n\n
;							___Q - Set Detail to 1\n
;							___P - Show priority map\n
;							___R - Show room info/free memory\n
;							___S - Switch sex\n
;							___T - Teleport\n
;							___U - Give HandsOn\n
;							___V - Show visual map\n"
;						)
;						(Print "Debug options:______(Page 4 of 5)\n\n
;							___W - Feature writer\n
;							___X,Z - Quick quit\n"
;						)
;						(Print "Debug options:______(Page 4 of 5)\n\n
;							__A=Alt, C=Ctrl, L=Left shift, R=Right shift\n\n
;							__Left click:\n
;							____A_______Move ego\n
;							____CL______Show ego\n
;							____CR______Show room\n
;							____CA______Show position\n"
;						)
;					)
					(else
						(event claimed: FALSE)
					)
				)
			)
			(mouseDown
				;
;				(define 	shiftRight			1)	 \
;				(define 	shiftLeft			2)	  \ these are defined in SYSTEM.SH
;				(define 	shiftDown			3)	  /
;				(define 	ctrlDown				4)	 /
;				(define 	altDown				8)
				(define	ctrlRShiftDown		5)
				(define	ctrlLShiftDown		6)
				(define	altRShiftDown		9)
				(define	altLShiftDown		10)
				(define	ctrlAltDown			12)
				(define	ctrlAltRShiftDown	13)
				(define	ctrlAltLShiftDown	14)
				;
				;
				(switch (event modifiers?)
					(ctrlAltRShiftDown
						NULL
					)
					(ctrlAltLShiftDown
						NULL
					)
					(ctrlAltDown
						(event claimed: TRUE)
						(Format @str "%d/%d" (event x?) (event y?))
						(= wind
							(Print @str
								#at: 		160 10
								#font: 	999
								#dispose:
							)
						)
						(while (!= mouseUp ((= evt (Event new:)) type?))
							(evt dispose:)
						)
						(evt dispose:)

						; put window away
						(wind dispose:)
					)
					(ctrlRShiftDown
						(event
							type:		keyDown,
							message:	ALT_R
						)
						(self handleEvent: event)
					)
					(ctrlLShiftDown
						(event
							type:		keyDown,
							message:	ALT_E
						)
						(self handleEvent: event)
					)

					(altRShiftDown
						NULL
					)
					(altLShiftDown
						NULL
					)

					(shiftRight
						; the iconBar uses this to advance the current icon
						NULL
					)
					(shiftLeft
						; the iconBar uses this to advance the current icon
						NULL
					)
					(ctrlDown
						; the iconBar uses this to toggle icon between walk & current
						NULL
					)
					(altDown
						(event claimed: TRUE)
						(while (!= mouseUp ((= evt (Event new:)) type?))
							((User alterEgo?)
								posn:			(evt x?) (- (evt y?) 10),
								setMotion:	0
							)
							(Animate (cast elements?) FALSE)
							(evt dispose:)
						)
						(evt dispose:)
					)
				)
			)
		)
	)
)


; an easy way to give ego inventory items
; uses some memory, but a better interface
(instance dInvDialog of Dialog
	(properties
		name	"dInvD"
	)

	(method (init &tmp lastX lastY widest num el node obj)
		;Init positioning vars.
		(= widest (= lastX (= lastY MARGIN)))
		(= num 0)

		; loop thru inventory list
		(for 	((= node (inventory first:)))
				node
				((= node (inventory next: node)))

			(= obj (NodeValue node))

			(++ num)
			; only add invItems cuz there are 5 items in the inventory list
			; that are IconItems, & when you chose them, they blew up the game
			(if (obj isKindOf: InvItem)
				(self add:
					((= el (DText new:))
						value: obj,
						text: (obj name?),
						nsLeft: lastX,
						nsTop: lastY,
						state:(| dActive dExit),
						font: smallFont,
						setSize:,
						yourself:
					)
				)
			)

			;Keep track of widest item.
			(if (< widest (- (el nsRight?) (el nsLeft?)))
				(=  widest (- (el nsRight?) (el nsLeft?)))
			)

			;Bump lastY by height of character this item.
			(+= lastY (+ (- (el nsBottom?) (el nsTop?)) 1))

			;Wrap to next column.
			(if (> lastY 140)
				(= lastY MARGIN)
				(+= lastX (+ widest 10))
				(= widest 0)
			)
		)

		; give ourself the ll5 window
		(= window systemWindow)

		;Size dialog and add button to lower right
		(self setSize:)
		(= yesI (DButton new:))
		(yesI
			text: "Outta here!",
			setSize:,
			moveTo:
				(- nsRight (+ MARGIN (yesI nsRight?)))
				nsBottom
		)
		(yesI
			move: (- (yesI nsLeft?) (yesI nsRight?)) 0
		)

		;Add button and resize the dialog.
		(self add: yesI, setSize:, center:)

		(return num)
	)


	(method (doit &tmp el)
		;Initialize the dialog
		(self init:)

		;Call the dialog with exit as default
		(self open: wTitled 15)
		(= el yesI)

		(repeat
			(= el (super doit: el))

			;These returns signal end of dialog
			(if (or (not el) (== el -1) (== el yesI))
				(break)
			)

			; this is where it goes when you choose an item by pressing enter
			(ego get: (Inventory indexOf: (el value?)))
			; ((el value?) owner: ego)
		)

		;Dispose of everything
		(self dispose:)
	)


	(method (handleEvent event &tmp msg typ)
		(= msg (event message?))
		(= typ (event type?))

		(switch typ
			(keyDown
				(switch msg
					(UPARROW
						(= msg SHIFTTAB)
					)
					(DOWNARROW
						(= msg TAB)
					)
				)
			)
			(direction
				(switch msg
					(dirN
						(= msg SHIFTTAB)
						(= typ keyDown)
					)
					(dirS
						(= msg TAB)
						(= typ keyDown)
					)
				)
			)
		)

		(event
			type: typ,
			message: msg
		)

		(return (super handleEvent: event))
	)
)

(instance showFeatureCode of Code
	(method (doit obj &tmp t l r b)
		(= t (obj nsTop?))
		(= l (obj nsLeft?))
		(= b (obj nsBottom?))
		(= r (obj nsRight?))
		(Graph GDrawLine t l t r VMAP vWHITE)
		(Graph GDrawLine b l b r VMAP vWHITE)
		(Graph GDrawLine t l b l VMAP vWHITE)
		(Graph GDrawLine t r b r VMAP vWHITE)
		(Graph GShowBits t l (+ b 1) (+ r 1) VMAP)
	)
)

(class NameFeatureCode of Code
	(methods
		handleEvent
	)

	(method (init)
		(keyDownHandler addToFront: self)
		(theDoits add: self)
	)
	(method (dispose)
		(DrawStatus 0)
		(DrawStatus {_} colBlack 0)
;**			(if
;**				(PrintD
;**					{Erase outlines?}
;**					#new
;**					#button {No}	0
;**					#button {Yes} 	1
;**				)
;**				(DrawPic (curRoom picture?) PLAIN)
;**				(if addToPics
;**					(addToPics doit:)
;**				)
;**			)
		(keyDownHandler delete: self)
		(theDoits delete: self)
	)
	(method (handleEvent event)
		(if (and	(== (event type?) keyDown)
					(== (event message?) ESC)
				)
			(event claimed: TRUE)
			(self dispose:)
		)
	)
	(method (doit &tmp obj [str 40])
	  	(OnMeAndLowY init:)
	  	(features eachElementDo: #perform OnMeAndLowY (User curEvent?))
	  	(if (= obj (OnMeAndLowY theObj?))
	  		(DrawStatus
	  			(Format @str "%d, %d______%s"
					((User curEvent?) x?)
					((User curEvent?) y?)
					(obj name?)
	  			)
	  		)
		)
	)
)
			

;(class JustifyText of Object
;	(properties
;		lastX		0
;		lastY		0
;		unders	0
;		font		2510
;		color		0
;	)
;	(methods
;		handleEvent
;		showCoord
;	)
;
;	(method (init)
;		(super init:)
;		(mouseDownHandler addToFront: self)
;		(keyDownHandler 	addToFront: self)
;		(directionHandler	addToFront: self)
;		(theDoits 			addToFront: self)
;		(= theText 0)
;		(GetInput @theText 50 {Enter text (after this, get help with '?')})
;		(theIconBar curIcon: (theIconBar at: useIcon))
;		(= oldCursor theCursor)
;		(theGame setCursor: 69 FALSE)
;		(self doit: TRUE)
;	)
;	(method (dispose)
;		(mouseDownHandler delete: self)
;		(keyDownHandler 	delete: self)
;		(directionHandler	delete: self)
;		(theDoits 			delete: self)
;		(theGame setCursor: oldCursor TRUE)
;		(UnLoad MEMORY unders)
;		(super dispose:)
;	)
;	(method (handleEvent event &tmp inc theX theY eType eMsg eMods [str 25])
;		(= theX (event x?))
;		(= theY (event y?))
;		(= eType (event type?))
;		(= eMsg	(event message?))
;		(= eMods (event modifiers?))
;		(cond
;			((== eType keyDown)
;				(switch eMsg
;					(ESC
;						(Display "" #p_restore: unders)
;						(self dispose:)
;					)
;					(ENTER
;						(self showCoord: event)
;					)
;					(CTRL_F
;						(switch font
;							(1207 (= font 2107))
;							(2107 (= font 2108))
;							(2108 (= font 2407))
;							(2407 (= font 2510))
;							(2510 (= font 4115))
;							(4115 (= font  999))
;							(999  (= font    0))
;							(else 
;								(= font (GetNumber {Font Number:}))
;							)
;						)
;						(theGame setCursor: theCursor FALSE theX theY)
;						(self doit: TRUE)
;					)
;					(CTRL_N
;						(= str 0)
;						(GetInput @str 50 {Enter text (then get help with `?')})
;						(if str
;							(StrCpy @theText @str)
;							(= lastX (= lastY 0))
;							(self doit: TRUE)
;						)
;					)
;					(CTRL_C
;						(if (> (++ color) 7)
;							(= color 0)
;						)
;						(self doit: TRUE)
;					)
;					(HELP
;						(Print
;							"Move text with mouse or direction keys\n
;							SHIFT + arrows for fine adjustment\n\n
;							ENTER or click shows text position\n\n
;							Ctrl-F (shift click) changes font\n
;							Ctrl-N to enter new text\n
;							Ctrl-C (control click) changes color\n
;							ESC aborts"
;						)
;					)
;
;				)
;				(event claimed: TRUE)
;			)
;			((== eType mouseDown)
;				(cond
;					((& eMods shiftDown)
;						(event
;							type:		keyDown,
;							message:	CTRL_F
;						)
;						(self handleEvent: event)
;					)
;					((& eMods ctrlDown)
;						(event
;							type:		keyDown,
;							message:	CTRL_C
;						)
;						(self handleEvent: event)
;					)
;					(else
;						(self showCoord: event)
;						(event claimed: TRUE)
;					)
;				)
;			)
;			((& eType direction)
;				(= inc (if (& eMods shiftDown) 1 else 10))
;				(if (OneOf eMsg dirE dirNE dirSE)
;					(+= theX inc)
;				)
;				(if (OneOf eMsg dirW dirNW dirSW)
;					(-= theX inc)
;				)
;				(if (OneOf eMsg dirNW dirN dirNE)
;					(-= theY inc)
;				)
;				(if (OneOf eMsg dirSW dirS dirSE)
;					(+= theY inc)
;				)
;				(event claimed: TRUE)
;				(theGame setCursor: theCursor FALSE theX theY)
;				(self doit:)
;			)
;		)
;	)
;	(method (showCoord event &tmp [str 20])
;		(Format @str "Position: %d, %d" (event x?) (event y?))
;		(Print @str)
;		(self dispose:)
;	)
;	(method (doit clear &tmp theX theY)
;		(if (and argc clear)
;			(= lastX (= lastY 0))
;		)
;		(= theX ((User curEvent) x?))
;		(= theY ((User curEvent) y?))
;		(if (or	(!= theX lastX)
;					(!= theY lastY)
;				)
;			(if unders
;				(Display "" #p_restore: unders)
;			)
;			(= unders
;				(Display @theText
;					#p_at:		theX theY
;					#p_color:	[colBlack color]
;					#p_back:		(if color colBlack else colWhite)
;					#p_font:		font
;					#p_save:
;				)
;			)
;			(= lastX theX)
;			(= lastY theY)
;		)
;	)
;)
