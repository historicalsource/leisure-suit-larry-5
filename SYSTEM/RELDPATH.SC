(script#	RELDPATH)

(class RelDPath of DPath
	(method (setTarget)
		(if (!= (points at value) PATHEND)
			(+= x (points at value))
			(+= y (points at (++ value)))
			(++ value)
		)
	)
);RelDPath
