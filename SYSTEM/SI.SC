(script# SLIDEICON)

(include menu.sh)

(public
	GameControls  0
)
(procedure (Eval obj sel) ; needed to delay binding till runtime
	(return (obj sel &rest))
)

(class Slider kindof IconItem
	(properties
		sliderView	0
		sliderLoop	0
		sliderCel	0
		sTop			0
		sLeft			0
		sBottom		0
		sRight 		0
		maxY			0	
		minY			0
		underBits	0
		yStep			1
		yLast			0
		theObj		NULL
		selector		0
		minValue		0
		maxValue		0
	)

	(methods
		advance
		retreat
		move
		valueToPosn
		posnToValue
	)

	(method (show)
		(super show:&rest)
		;; if not already set
		(if (not sRight)
			(= sLeft nsLeft)
			(= sRight nsRight)
			(= maxY (- nsBottom (CelHigh sliderView sliderLoop sliderCel)))
			(= minY nsTop)
			(= sTop (self valueToPosn?))
		)
	 	(DrawCel sliderView sliderLoop sliderCel sLeft sTop -1)
		(Graph GShowBits (- nsTop 1) (- nsLeft 1) (+ 2 nsBottom) (+ 2 nsRight) VMAP)
	)

	(method (select relVer &tmp event)
		(if (and argc relVer)
			(while (!= ((= event (Event new:))	type?) mouseUp)
				(event localize:)
				(cond
					((< (event y?)	(- yLast	yStep))
						(self move:1)
					)
					((> (event y?)	(+ yLast	yStep))
						(self move:-1)
					)
				)
				(event dispose:)
			)
			(event dispose:)
		else
			(return TRUE)
		)
	)

	(method (highlight)
	)

	;; move a specified number of steps
	(method (move amount &tmp newTop cHigh pnv newValue i dir retVal)
		(= dir (sign amount))
		(for ((= i amount)) i ((-= i dir))
			(= newTop (- sTop (* dir yStep)))
			(= cHigh	 (CelHigh sliderView sliderLoop sliderCel))
			(= sTop
				(cond
					((< newTop minY)
						minY
					)
					((> newTop maxY)
						maxY
					)
					(else
						newTop
					)
				)
			)
			(= pnv (PicNotValid))
			(= sBottom (+ sTop cHigh))
			(PicNotValid TRUE)
			(DrawCel view loop cel nsLeft nsTop -1)
			(DrawCel sliderView sliderLoop sliderCel sLeft sTop -1)
			(Graph GShowBits (- nsTop 1) (- nsLeft 1) (+ 2 nsBottom) (+ 2 nsRight) VMAP)
			(PicNotValid pnv)
			(= newValue (self posnToValue:(= yLast sTop)))
			(= retVal
				(if theObj
 					(Eval theObj selector newValue)
				)
			)
		)
		(return retVal)
	)

	(method (posnToValue yPosn)
		(return
			(/ 
				(* 
					(- maxY yPosn)	
					(- maxValue minValue)
				) 
				(- maxY minY) 
			)
		)
	)

	(method (valueToPosn val &tmp selVal)
		(= selVal (if argc val else (Eval theObj selector)))
		(return
			(+
				minY
				(/
					(*
						(- maxValue selVal)
						(- maxY minY)		
					)
					(- maxValue minValue)
				)
			)

		)
	)

	(method (advance)
		(self move:(/ (- maxY minY) (- maxValue minValue))) 
	)

	(method (retreat)
		(self move:(- (/ (- maxY minY) (- maxValue minValue))))
	)
	
)

(define	CTRL_BOX_TOP		46)
(define	CTRL_BOX_LEFT		24)
(define	CTRL_BOX_BOTTOM	155)
(define	CTRL_BOX_RIGHT		296)

(class GameControls of IconBar
	(properties
		window 	NULL
		height	200
		okButton	0
	)

	(method (show &tmp theX theY node nextNode obj)
		(|= state IB_ACTIVE)
		(if (IsObject window)
			(window open:)
		else
			(= window
				((systemWindow new:)
					top:			CTRL_BOX_TOP,	
					left:			CTRL_BOX_LEFT,
					bottom:		CTRL_BOX_BOTTOM,
					right:		CTRL_BOX_RIGHT,
					priority:	15,
					open:			,
					yourself?
				)
			)
		)
		(for	((= theX 30)(= theY 30)(= node (FirstNode elements)))	
			node
			((= node nextNode))
			(= nextNode (NextNode node))			
			(if (not (IsObject (= obj (NodeValue node)))) (return))
			; if nsRect not set yet
			(if (and (not (& (obj signal) FIXED_POSN)) (<= (obj nsRight?) 0))
				(obj show:theX theY)
				(= theX (+ 20 (obj nsRight?)))
			else
				(obj show:)
			)
		)
		(if (not okButton)
			(= okButton (NodeValue (self first?)))
		)
		(if curIcon 
			(theGame 
				setCursor: 
					theCursor	
					TRUE
					(+ (curIcon nsLeft?) (/ (- (curIcon nsRight?) (curIcon nsLeft?)) 2)) 
					(- (curIcon nsBottom?) 3)
			)
		)
		(self doit:,hide:)
	)

	(method (dispatchEvent event)
		(if (== (event type?) keyDown)
			(switch (event message?) 
				(DOWNARROW
					(event dispose:)
					(cond
						((and
								(IsObject highlightedIcon) 
								(highlightedIcon respondsTo: #retreat:)
							)	 	
							; move slider down
							(highlightedIcon retreat:)	
							(return FALSE)
						)
						((& (highlightedIcon signal?) VICON)
							(self advance:)
							(return FALSE)
						)
					)
				)
				(UPARROW
					(event dispose:)
					(cond
						((and
								(IsObject highlightedIcon) 
								(highlightedIcon respondsTo: #advance:)
							)
							; move slider up
							(highlightedIcon advance:)		
							(return FALSE)
						)
						((& (highlightedIcon signal?) VICON)
							(self retreat:)
							(return FALSE)
						)
					)
				)
				(else
					(return (super dispatchEvent: event))
				)

			)
		else
			(return (super dispatchEvent: event))
		)
	)

	(method (select theIcon relVer)
		(return
			(theIcon select:(if (>= argc 2) relVer))
		)
	)

	(method (advanceCurIcon &tmp theIcon)
;		(= theIcon curIcon)
;		(while (& ((= theIcon (self at:(mod (+ (self indexOf:theIcon) 1) size?))) signal?) IMMEDIATE)	
;		)
;		(= curIcon theIcon)
;		(theGame setCursor:(curIcon cursor?) TRUE)
	)

	(method (swapCurIcon)
;		(if (!= curIcon (self at:walkIcon))
;			(= prevIcon curIcon)
;			(= curIcon (self at:walkIcon))
;		else
;			(if prevIcon
;				(= curIcon prevIcon)
;			)
;		)
;		(theGame setCursor:(curIcon cursor?) TRUE)
	)

	(method (hide)
		(if window
			(window dispose:)
		)
		(&= state (~ IB_ACTIVE))
	)
)

(class	ControlIcon kindof IconItem
	(properties
		theObj	0
		selector	0
	)

	(method (select)
		(return
			(if theObj
				(if (super select:&rest)
					(GameControls hide:)
					(Eval theObj selector)
				)
			else 
		 		(super select:&rest)
			)
		)
	)
)

	


