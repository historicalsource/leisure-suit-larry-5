;**
;**	Logics for Hollywood Region
;**
;**	LSL5:  Passionate Patti Does A Little Undercover Work
;**				Copyright 1991
;**				by Al Lowe
;**
;**	Programmed by Brian K. Hughes
;**
;**	Last Update:  September 10, 1991
;**

(script#		HOLLYWOODRGN)

(public
	HollywoodRgn		0
)


(instance HollywoodRgn of Region
	(method (init)
		(if (Btst fBeenIn150)	; we're not in the "demo"
			(self setScript: sRemember)
		)
		(super init:)
	)

	(method (newRoom n)
		(= keep (OneOf n HOLLYWOOD))
	)
)


(instance sRemember of Script
	(method (changeState newState )

		(switchto (= state newState)
			(
				(= seconds 30)
			)
			(
				(TPrint "\"Where am I?\" you wonder.
					\"What's happened to me?
					What am I doing here?\"")
				(TPrint "The last you remember,
					you were writing your life story as a computer game,
					sitting on a deck with a half-naked woman,
					overlooking a beautiful lake nestled
					high in the Sierra Nevada.")
				(= seconds 120)
			)
			(
				(TPrint "\"I remember a woman named Patti;
					something about an island;
					and, and...
					a gig as a computer game programmer?\" you think.
					\"What's going on?\"")
				(TPrint "\"How did I end up in Hollywood--and
					what am I supposed to be doing here?\" you sigh.
					\"I'm so confused!\"")
				(TPrint "(Of course, you're quite accustomed
					to being confused!)" LOW)
				(= seconds 120)
			)
			(
				(TPrint "\"And what am I doing in Larry 5?\" you think.
					\"And whatever happened to Larry 4?\"")
				(TPrint "Slowly you come to the realization that you
					must be suffering from amnesia,
					although how a computer game character gets
					amnesia you do not understand!")
				(= seconds 120)
			)
			(
				(TPrint "\"Who was that Patti woman?
					Does she still exist?
					Did she ever exist?\" you think.
					\"Or is she just a series of partially-toggled bits
					in my memories?\"")
				(TPrint "You remember her most deeply in your heart,
					not to mention several other major organs.")
				(TPrint "But where is she?
					Will you see her again?
					Does she ever think of you?")
				(= seconds 120)
			)
			(
				(TPrint "Suddenly, your memories stir again,
					\"Patti was Passionate Patti, the famous pianist/entertainer.
					I remember: I loved her!\"")
				(TPrint "\"And wait! She loved me, too!
					Will I ever find her again?\"")
				(= seconds 120)
			)
		)
	)
)
