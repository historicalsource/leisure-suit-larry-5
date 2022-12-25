(script# 100)

(public
	testr 0
)

(instance testr of Room
	(properties
		picture	100
	)
	(method (init)
		(super init:)
		(keyDownHandler addToFront: self)
	)
	(method (handleEvent event)
		(if (== (event type?) keyDown)
			(Printf "%d, %c" (event message?) (event message?))
			(switch (event message?)
				(65
					(Print "AAAAAAAA")
				)
				(`"
					(Print "QUOOOOOOOOOTE")
				)
			)
		)
	)
)
