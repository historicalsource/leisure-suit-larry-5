;;;;
;;;;	DOOR.SC
;;;;	(c) Sierra On-Line, Inc, 1990
;;;;
;;;;	Author: 	Al Lowe
;;;;	August 7, 1989
;;;;	Adapted for LL5 by Brian K. Hughes
;;;;
;;;;	Last Update: September 4, 1991
;;;;
;;;;	Classes:
;;;;	  Door


(script# DOOR)


;**	Door states (from "game.sh")
;**	(enum
;**		doorClosed
;**		doorOpening
;**		doorOpen
;**		doorClosing
;**	)

;**	;Door enter (->) and exit (<-) types (from "game.sh")
;**	(enum
;**		doorWalkEgo		; ->: 	ego will walk in through the door
;**							; <-:		ego will walk out the door to new room
;**		doorPlaceEgo	; ->: 	ego will appear in front of the door
;**							; <-:		ego will walk out the door & door will close
;**		doorLeaveEgo	; both: 	the door does not affect ego
;**	)

(class Door of Prop
	(properties
		entranceTo 		0					; room # beyond the door
		locked 			FALSE
		openSnd 			sDoorOpen
		closeSnd 		sDoorClose
		state 			doorClosed
		doubleDoor		0					; ID of instance to be used as double door
		forceOpen		FALSE				; TRUE if door should be inited open
	   caller			0					; who to cue when door done opening/closing
		moveToX			0					;\
		moveToY			0					;- where to move to when door is open
												;	Uses polypath, so ego could go around
												;	a corner.
		enterType		doorLeaveEgo
		exitType			doorLeaveEgo
		closeScript		0					; script before closing (if any)
		openScript		0					; script when done opening (if any)
		heading			0					; heading in which ego faces when placed
	)
	(methods
		open
		close
	)

	(method (init)
		(self approachVerbs: verbDo)

		; We're coming in through the door or we're forced open
		(if (or	forceOpen
					(and 	prevRoomNum
							(== prevRoomNum entranceTo)
						)
				)
			(= state doorOpen)
			(|= signal ignrAct)
			(if doubleDoor
				(doubleDoor ignoreActors: TRUE)
			)
		)

		(if (== state doorClosed)
			(= cel 0)
			(if doubleDoor
				(doubleDoor setCel: 0)
			)
		else
			(= cel (- (NumCels self) 1))
			(if doubleDoor
				(doubleDoor setCel: LASTCEL)
			)
		)

		(super init:)

		(if (== state doorOpen)		; we've just come through the door

			; We can intercept the default closing actions with a close script
			(if closeScript
				(self setScript: closeScript)
			else

				; See what to do with ego
				(switch enterType

					; We walk ego in through the open door
					(doorWalkEgo
						(HandsOff)
						(ego
							posn:			moveToX moveToY,
							setMotion:	PolyPath approachX approachY self
						)
					)

					; We just put ego in front of the open door
					(doorPlaceEgo
						(ego
							edgeHit:		0,
							posn: 		approachX approachY,
							setHeading:	heading
						)
						(self close:)
					)

					; We don't do anything with ego at all
					(doorLeaveEgo
						(self close:)
					)
				)
			)
		else
			(self stopUpd:)
		)
	)

	(method (doVerb theVerb invItem)
		(switch theVerb
			(verbDo
				(if (== state doorOpen)
					(self close:)
				else
					(self open:)
				)
			)
			(else
				(super doVerb: theVerb invItem &rest)
			)
		)
	)


	(method (open)
		(if locked
			(TPrint "This door is locked.")
		else
			(HandsOff)
			(= state doorOpening)
			(self setCycle: EndLoop self)
			(if openSnd
				((Sound new:) number: openSnd, play:)
			)
			(if doubleDoor
				(doubleDoor setCycle: EndLoop)
			)
		)
	)

	(method (close)
		(= state doorClosing)
		(self setCycle: BegLoop self)
		(if closeSnd
			((Sound new:) number: closeSnd, play:)
		)
		(if doubleDoor
			(doubleDoor setCycle: BegLoop)
		)
	)

	(method (cue)
		(switch state

			; The door has finished closing
			(doorClosing
				(= state doorClosed)
				(self
					stopUpd:			,
					ignoreActors:	FALSE
				)
				(if doubleDoor
					(doubleDoor ignoreActors: FALSE)
				)
				(if caller
					(caller cue:)
				)
			)

			; The door has finished opening
			(doorOpening
				(= state doorOpen)
				(self
					stopUpd:			,
					ignoreActors:	TRUE
				)
				(if doubleDoor
					(doubleDoor ignoreActors: TRUE)
				)
				(if caller
					(caller cue:)
				)

				; We can intercept the default opening actions with an open script
				(if openScript
					(self setScript: openScript)
				else

					; See what to do with ego
					(switch exitType
						(doorWalkEgo
							(if (or moveToX moveToY)
								(ego
									illegalBits:	$0000,
									setMotion: 		PolyPath moveToX moveToY self
								)
							)
						)
						(doorPlaceEgo
							(if (or moveToX moveToY)
								(ego setMotion: PolyPath moveToX moveToY self)
							)
						)
						(doorLeaveEgo
							(self cue:)	;cuz we still want to go to the newRoom
						)
					)
				)
			)

			; We've been cued by ego's mover (either coming in or going out)
			(else
				(cond

					; We're outside now
					((and	(== (ego x?) moveToX)
							(== (ego y?) moveToY)
						)

						(if entranceTo

							; We're either going to another room...
							(switch entranceTo
								((curRoom north?)
									(ego edgeHit: NORTH)
								)
								((curRoom south?)
									(ego edgeHit: SOUTH)
								)
								((curRoom west?)
									(ego edgeHit: WEST)
								)
								((curRoom east?)
									(ego edgeHit: EAST)
								)
							)
							(curRoom newRoom: entranceTo)
						else

							; ... or else just behind the door
							(self close:)
						)
					)

					; We're inside now
					((and	(== (ego x?) approachX)
							(== (ego y?) approachY)
						)
						(self close:)
					)
				)
				(HandsOn)
			)
		)
 	)
)
