(script# NAMEFIND)

(procedure
	NameFind
)
(public
	NameFind	0
)

(procedure (NameFind theNearName theList)
	(theList firstTrue: #perform NameComp theNearName)
)

(instance NameComp of Code
	(properties name "NC")
	(method (doit theObj theName)
		(== 0 (StrCmp (theObj name) theName))
	)
)
