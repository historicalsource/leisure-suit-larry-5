;**
;**	Sierra On-line basic standard menu
;**
;**		by Al Lowe
;**
;**		adapted by Pablo Ghenis
;**
;**	Last Update:	August 26, 1988
;**


;**	Break lines in "AddMenu" before a divider, for aesthetics
;**	= sets a menu item's starting value
;**	! makes the item non-selectable
;**	` denotes the following character as the key for the menu
;**	: separates menu items within a menu stack


(script# MENU)									;**	output to script.997


;************   FONTS   *************


(enum
$100 sierraM
	aboutI
	helpI

$200 fileM
	saveI
	restoreI
		divider201I
	restartI
	quitI

$300 actionM
	pauseI
	invI
	repeatI
;		divider301I
;	bossI
;	triteI

$400 speedM
	speedI
		divider401I
	fasterI
	normalI
	slowerI

$500 soundM
	volumeI
	soundI

;$600 debugM
;	gameI
;	castI
;	egoI
;	tglDebugI
;	memI
;		divider601I
;	visualI
;	priorityI
;	controlI
;		divider602I
;	gridI
;	writeEgoI

;$700 cheatM
;	teleportI
;	rgTimeI
;	clothingI
;	roomI
;	inputI
;	noteI
;	quickQuitI
)


(class TheMenuBar kindof MenuBar			;**	MENUBAR
	(method (init)
		(AddMenu { \01 }
			{About game`^a:Help`#1}
		)

		(AddMenu { File }
			{Save Game`#5:Restore Game`#7:--!
			:Restart Game`#9:Quit`^q}
		)

		(AddMenu { Action }
			{Pause Game`^p:Inventory`^I:Retype`#3:--!
;;;		:Boss Key`^b:Trite Phrase
			}
		)

		(AddMenu { Speed }
			{Change...`^s:--!:Faster`+:Normal`=:Slower`-}
		)

		(AddMenu { Sound }
			{Volume...`^v:Sound Off`#2=1}
		)
		(SetMenu soundI
			text:
				(if (DoSound SoundOn)
					{Turn sound off}
				else
					{Turn sound on}
				)
		)

;;;		(AddMenu { Debug }
;;;			{Game`^g:Cast`^c:Ego`^e:TglDebug`^d:Memory`^h:--!
;;;			:Visual`#2:Priority`#3:Control`#4:--!
;;;			:Add Grid`#0:WriteEgo`^w}
;;;		)

;;;		(AddMenu { Cheat}
;;;			{Tp:Time left`@t:ego View`@v:Room num`@r:Input`@i
;;;			:make Note`^n:QuickQuit\05`@z}
;;;		)

		(SetMenu saveI			said: 'save[/game]')
		(SetMenu restoreI		said: 'restore[/game]')
		(SetMenu restartI		said: 'restart[/game]')
		(SetMenu quitI			said: 'quit[/game]')
		(SetMenu	pauseI		said: 'pause[/game]')
		(SetMenu invI			said: 'inventory')
		(SetMenu normalI		said: 'normal')
;		(SetMenu fasterI		said: 'faster')
;		(SetMenu slowerI		said: 'slower')
		(SetMenu teleportI	said: 'tp')
	)


	(method (handleEvent event &tmp i oldPause)
		(switch (super handleEvent: event)


			;**************		SIERRA MENU		**************

			(aboutI
		 		(Print
						"\"Leisure Suit Larry goes\n
						Looking for Love\n
						(in Several Wrong Places)\"\n
						\n
						was created, designed,\n
						written and programmed by\n
						Al Lowe\n
						\n
						graphics by\n
						Bill Skirvin\n
						Bonnie Borucki\n
						Douglas Herring\n
						\n
						game development system by\n
						Jeff Stephenson\n
						Bob Heitman\n
						Pablo Ghenis\n
						Stuart Goldstein"
							font:		smallFont
							mode:		teJustCenter
							title:	{\"Looking For Love\"}
;;;							icon:		vAuthors
				)
				(Print
					"Version 0.009\n\n
					August 12, 1988
					"
							font:		smallFont
							mode:		teJustCenter
				)
				(Print (Format @str {You've been playing for
						%d days, %d hours, %d minutes and %d seconds.}
						gameDays gameHours gameMinutes gameSeconds
						)
							font: 	smallFont
				)
			)

			(helpI
				(Print  "DURING THE GAME:\n
							\ ESC opens and closes the menus,\n
							\ \ \ which show additional shortcuts.\n\n
							
							IN DIALOG WINDOWS:\n
							\ Your current choice is outlined.\n
							\ Tab and Shift-Tab move between\n
							\ \ \ choices.\n
							\ ESC always cancels.\n\n

							IN TYPING WINDOWS:\n
							\ Arrows, Home, End and ctrl-Arrows\n
							\ \ \ move the cursor.\n
							\ Ctrl-C clears the line."

							title:	{\"Looking for Love\" HELP}
							font:		smallFont
				)
			)


			;**************		FILE MENU		**************

			(saveI
				(theGame save:)
			)

			(restoreI
				(theGame restore:)
			)

			(restartI
				(if
					(Print "You mean you want to start over again
						from the very beginning?"
							title:	{Restart}
;;;							icon:		vBEdismay 1 0
							font:		bigFont
							button:	{Restart} 1
							button:	{Oops} 0
					)
					(theGame restart:)
				)
			)

			(quitI
				(= quit
					(Print "Are you just going to quit and
						leave me here all alone like this?"
							title:	{Quit}
;;;							icon:		vBEchagrin 1 0
							font:		bigFont
							button:	{Quit} 1
							button:	{Oops} 0
					)
				)
			)


			;**************		ACTION MENU		**************

			(pauseI
				(Print "Sure, you go ahead.
					I'll just wait in here until you get back..."
							title:	{This game is paused.}
;;;							icon: 	vEgoPause 0 0
							font:		bigFont
							button:	{Ok. I'm back.} 1
				)
			)

			(invI
				(inventory showSelf:	ego)
			)

			(repeatI
				(event message:(user echo?))
				(user getInput:event)
			)

;;;			(bossI
;;;				(Print "Maybe someday.")
;;;			)

;;;			(triteI
;;;				(GetInput (Format @str tritePhrase) 38
;;;					{Enter your favorite trite phrase:})
;;;				(if ( > (StrLen @str) 4)
;;;					(Format tritePhrase @str)
;;;				)
;;;			)


			;**************		SPEED MENU		**************

			(speedI
				(= i
					((Gauge new:)
						description:
							{Use the mouse or right and left arrow keys to
							select the speed at which characters move.}
						text:{Animation Speed}
						minimum:0
						normal:10
						maximum:15
						higher:{Faster}
						lower:{Slower}
						doit:(- 16 speed)
					)
				)
				(theGame setSpeed:(- 16 i))
				(DisposeScript GAUGE)
			)
				
			(fasterI
				(if (> speed (^ 1 (= i debugging)))	;**	This lets Al haul ass!
					(theGame setSpeed: (-- speed))
				)
			)

			(normalI
				(theGame setSpeed: 6)
			)

			(slowerI
				(theGame setSpeed: (++ speed))
			)


			;**************		SOUND MENU		**************

			(volumeI
				(= i
					((Gauge new:)
						description:
							{Use the mouse or right and left arrow keys to
							set the sound volume.}
						text:{Sound Volume}
						minimum:0
						normal:12
						maximum:15
						higher:{Louder}
						lower:{Softer}
						doit:(DoSound ChangeVolume)
					)
				)
				(DoSound ChangeVolume i)
				(DisposeScript GAUGE)
			)
				
			(soundI
				(= i (DoSound SoundOn))
				(SetMenu soundI
					text: (if i {Turn sound on} else {Turn sound off})
				)
				(DoSound SoundOn (not i))
			)


			;**************		DEBUG MENU		**************

;			(gameI
;				(theGame showSelf:)
;			)

;			(castI
;				(cast showSelf:)
;			)

;			(egoI
;				(Print (Format @str {view: %d loop: %d cel: %d posn: %d %d
;					pri: %d OnControl: $%x Origin on: $%x}
;					(ego view?) (ego loop?) (ego cel?) (ego x?) (ego y?)
;					(ego priority?) (ego onControl:) (ego onControl: origin))
;					icon: (ego view?) (ego loop?) (ego cel?))
;			)

;			(tglDebugI
;				(^= debugOn TRUE)
;			)

;			(memI
;				(theGame showMem:)
;			)

;			(visualI
;				(Show VMAP)
;			)

;			(priorityI
;				(Show PMAP)
;			)

;			(controlI
;				(Show CMAP)
;				(Animate (cast elements?))
;				(while (== 0 ((= event (Event new:(- allEvents mouseUp))) type?))
;					(event dispose:))
;				(event dispose:)
;				(Show VMAP)
;			)

;			(gridI
;				(if (Load PICTURE 999)
;					(DrawPic 999 1 1)
;				)
;			)

;			(writeEgoI
;				(= str  0)
;				(= str2 0)
;				(if (GetInput @str 50 {Writing to \"ego.log\"})
;					(Format @str2
;						"(ego\rroom: %d\rview: %d\rloop: %d\rcel: %d\rsetPri: %d\rposn: %d %d)"
;						curRoomNum (ego view?) (ego loop?) (ego cel?) (ego priority?) (ego x?) (ego y?)
;					)
;					(File writeStr: @str @str2 "\r" close:)
;				)
;			)


			;**************		CHEAT MENU		**************

;			(teleportI
;				(NormalEgo)
;				(curRoom newRoom: (GetNumber {Teleport to:}))
;			)

;			(rgTimeI
;				(Print (Format @str "%2d:%2d" rgMinutes rgSeconds))
;			)

;			(clothingI
;				(= currentEgoView (GetNumber {New Ego View #:}))
;				(NormalEgo)
;			)

;			(roomI
;				(Print (Format @str "This is room %d." curRoomNum))
;			)

;			(inputI
;				(user canInput: TRUE)
;			)

;			(noteI
;				(= str  0)
;				(= str2 0)
;				(if (GetInput @str2 50 {Writing to \"note.log\"})
;					(Format @str
;						"[r%-3d v%-3d L%-1d c%-2d p%-2d %-3dx/%-3dy CS%-4d]\ \ "
;						curRoomNum (ego view?) (ego loop?) (ego cel?)
;						(ego priority?) (ego x?) (ego y?) currentStatus
;					)
;					(File name: "note.log" writeStr: @str @str2 "\r" close:)
;				)
;			)

;			(quickQuitI
;				(= quit 1)
;			)

		)
	)
)

