;;;;
;;;;	(c) Sierra On-Line, Inc. 1991
;;;;
;;;;	Author: Corey Cole
;;;;
;;;;	Changes an actor's view, loop, and/or cel when he stops moving,
;;;;	and prevents his getting doits while stopped.Carlos Escobar
;;;;
;;;;	Usage example:
;;;;		(actor setCycle: StopWalk stoppedView)
;;;;
;;;;	The walking view will be the actor's current view at the time
;;;;	the StopWalk was inited.
;;;;
;;;;
;;;;
;;;;	Modified February 28, 1991 -- Brian K. Hughes
;;;;
;;;; 	If you pass -1 as the stoppedView the StopWalk will use the
;;;;	last loop of the actor's walking view and the cel number equal to his
;;;;	current walking loop as the stopped cel.
;;;;
;;;; 	For example, if the actor's current loop is 3 and there are 8 walking
;;;;	loops in his walking view (0 thru 7), the stopped cel will be loop 8,
;;;;	cel 3.


(script# STOPWALK)

(public
	StopWalk	0
)


(define	LastLoop	(- (NumLoops client) 1))


(class StopWalk kindof Forward
	(properties
		vWalking	0		; Normal view for actor (walking view).
		vStopped	0		; View to use when stopped.
	)

	(method (init who stopView)
		(if argc					(= vWalking ((= client who) view?))
			(if (>= argc 2)	(= vStopped stopView)
			)
		)
		(super init: client)
	)

	(method (dispose)
		(if (== (client view?) vStopped)
			(client  view: vWalking)			; Leave on normal view
		)
		(super dispose:)
	)

	(method (doit &tmp curLoop theMover)
		(if (client isStopped:)
			(cond

				; Single view - we're stopped but haven't changed yet
				((and	(== vStopped -1)
						(!= (client loop?) LastLoop)
					)
					(= curLoop (client loop?))
					(client
						loop: 	LastLoop,
						setCel:	curLoop
					)
					(= theMover (client mover?))
					(if (and	theMover (not (theMover completed?)))
						(client setMotion: 0)
					)
					(super doit:)
				)

				; Dual view - we're stopped but haven't changed yet
				((and	(!= vStopped -1)
						(== (client view?) vWalking)
					)
					(client view: vStopped)
					(= theMover (client mover?))

					; If we're stopped cuz we're blocked, kill the mover
					(if (and theMover (not (theMover completed?)))
						(client  setMotion: 0)
					)
					(super doit:)
				)
			)
		else
			; We're not stopped
			(switch vStopped
				((client view?)		; dual view
					(client view: vWalking)
				)
				(-1						; same view
					(client
						setLoop: -1,
						setCel:	-1
					)
				)
			)
			(super doit:)
		)
	)
)
