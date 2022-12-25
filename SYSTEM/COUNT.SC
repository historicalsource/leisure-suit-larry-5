(script# COUNT)

(procedure
	Count
)

(public
	Count	0
)

(procedure (Count theList theCode &tmp theCount theNode)
	(for
	
		;; 
		
		(	(= theNode (FirstNode (theList elements)))
			(= theCount 0)
		)
		theNode
		(	(= theNode (NextNode theNode))
		)
		
		(if (theCode doit (NodeValue theNode) &rest)
			(++ theCount)
		)
	)
	(return theCount)
)
