;**
;**	Logics for PASSWORD -- Password protection
;**
;**	LSL5:  Passionate Patti Does A Little Undercover Work
;**				Copyright 1991
;**				by Al Lowe
;**
;**	Programmed by Brian K. Hughes
;**
;**	Last Update:  September 10, 1991
;**

(script#	PASSWORD)

(public
	passwordTest	0
)

(define	pCopyProtClosed	110)
(define	pCopyProtOpen		111)


(procedure
	AskPassword
	ConvertCase
	Hash
	LoadPassword
	SavePassword
)

(local
	[passStr 10]
	[realStr 10]
	theCount
)


(instance passwordTest of Room
	(properties
		picture 	pCopyProtClosed
		style		IRISOUT
	)
	(method (init &tmp rc)
		(super init:)
		(LoadPassword)
		(if (StrCmp @realStr {NONE})
			(= passStr 0)
		else
			(StrCpy @passStr @realStr)
		)
		(while (and	(< theCount 5)
						(or	(not (= rc (AskPassword)))
								(StrCmp @realStr @passStr)
							)
					)
		)
		(cond
			((== theCount 5)
				(Print "I'm sorry, but you have failed to enter the correct
					password for this game.
					Please try again when you know the password."
					#title	{Five Strikes and You're Out!}
				)
				(= quit TRUE)
				(return)
			)
			((== rc 2)
				(GetInput @passStr 20 {Please enter your new password:})
				(ConvertCase)
				(StrCpy @realStr @passStr)
				(SavePassword)
				(Printf "Your password is now %s.\n
					\n
					Don't forget it!"
					@passStr
					#title	{Write this down!}
				)
			)
		)
		(DrawPic pCopyProtOpen)
		(if (Print "Would you like to restore\n
						a previously saved game?"
				#button		{Restore}	1
				#button		{Continue}	0
				#title		{Welcome back!}
				#width		160
			 	#at			3 -1
			)
			(theGame restore:)
		)
		(DisposeScript FILE)
		(curRoom newRoom: 160)
	)
)

(instance passwordFile of File
	(properties
		name		"MEMORY.DRV"
	)
)

(procedure (AskPassword &tmp rc)
	(if (not (StrCmp @realStr {NONE}))	; no password
		(= rc
			(Print  {If you would like to use a\n
						password to protect this game,\n
						select \"Password\" below.}
				#new
				#button 		{Why bother?}  	1
				#button 		{Password}		   2
				#title		{An ounce of prevention...}
				#width		160
				#at			3 -1
			)
		)
	else
		(++ theCount)
		(= rc
			(Print  {Please enter your password,\n
						exactly as you typed it:}
				#new
				#edit	 		@passStr	20
				#new
				#button 		{Ok}		1
				#button 		{Change} 2
				#title		{Hey! Is that you again?}
				#width		160
				#at			3 -1
			)
		)
	)
	(ConvertCase)
	(return rc)
)

(procedure (ConvertCase &tmp i char)
	(for 	((= i 0))
			(< i (StrLen @passStr))
			((++ i))
		(if (> (= char (StrAt @passStr i)) 90)		;lower case, convert to upper
			(StrAt @passStr i (- char 32))
		)
	)
)

(procedure (Hash &tmp i char seed)
	(for	((= i 0))
			(< i (StrLen @realStr))
			((++ i))
		(= seed (- 167 i))							; start with $A7 seed
		(= char (^ (StrAt @realStr i) seed))	; x-or char with dec'ing seed
		(StrAt @realStr i char)
	)
)

(procedure (LoadPassword &tmp [str 10])
	(if (!= (passwordFile open: fRead) NULL)
		(passwordFile
			readString: @realStr 20,
			read:			@str 8,
			close:
		)
		(= pokerJackpot (ReadNumber @str))
	)
	(Hash)
)

(procedure (SavePassword &tmp [str 10])
	(if (!= (passwordFile open: fTrunc) NULL)
		(Hash)
		(Format @str "%d" pokerJackpot)
		(passwordFile
			writeString: @realStr,
			writeString: {\n},
			writeString: @str,
			close:
		)
	)
)

