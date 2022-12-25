;**
;**	Logics for Room DISP -- Code that disposes modules between rooms
;**
;**	LSL5:  Passionate Patti Does A Little Undercover Work
;**				Copyright 1991
;**				by Al Lowe
;**
;**	Programmed by Brian K. Hughes & Carlos Escobar
;**
;**	Last Update:  June 11, 1991
;**

(script# DISP)

(public
	disposeCode	0
)

(instance disposeCode of Code
	(method (doit n)
		(LoadMany FALSE

			; System (?) Classes
				DOOR
				PRINTD
				TALKER
				FILE

			; Movers & Cyclers & Shakers
				MOVEFWD
				OSC
				FORCOUNT
				MOVECYC
				REVERSE
				RANDCYC

			; Modules
				TTDIALER
				CHARGER

			; Debug only
				WRITEFTR
		)

		; Toss ourselves
		(DisposeScript DISP)
	)
)

