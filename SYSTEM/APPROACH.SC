(script# APPROACH)

(class Approach kindof Motion
	;; Try to get near an immobile object.
	
	(properties
		who 0					;who to approach
		distance 20			;how close to 'who' is considered a 'near'
	)
	
	
	(method (init actor whom howClose whoCares)
		(if (>= argc 1)				(= client actor)
			(if (>= argc 2)			(= who whom)
				(if (>= argc 3)		(= distance howClose)
					(if (>= argc 4)	(= caller whoCares)
					)
				)
			)
		)
		(super init: client (who x?) (who y?) caller)
	)
	
	
	(method (onTarget)
		(<= (client distanceTo: who) distance)
	)
	
	(method (setTarget)
		(cond
			(argc	
				(super setTarget: &rest)
			)
			((not (self onTarget?))
				(super setTarget: (who x?) (who y?))
				
			)
		)
	)
	
	(method (doit)
		(if (self onTarget?)
			(self moveDone:)
		else
			(super doit:)
		)
	)
)
