;;;;
;;;;	(c) Sierra On-Line, Inc, 1990
;;;;
;;;;	Author: 	Doug Oldfield
;;;;	November 7, 1990
;;;;	Adapted for LL5 by Brian K. Hughes
;;;;	Our game has changes to the init & doit methods of Room.
;;;;	In order for us to keep these changes without having to maintain our
;;;;	own GAME.SC, I have subclassed Room.  All our Rooms are now LLRooms
;;;;
;;;;	Classes:
;;;;	  LLRoom


(script# LLROOM)

(public
	enterRmScript	0
)


(local
	chargerCoords =
	[
		160	64 	108
		260	19		146
		NULL
	]
)



; this is here just so the stupid thing will compile
(instance roomControls of Controls
	(properties
		name "controls"
	)
)

(class LLRoom kindof Room
	(method (init &tmp wide high i theLoop)
		(= number curRoomNum)
		(= controls roomControls)

		; Search the table to find where the charger goes, if in this room
		(if chargerPlugged
			(= i 0)
			(while (!= [chargerCoords i] NULL)
				(if (== [chargerCoords i] curRoomNum)
					(if (== ((Inventory at: iCamcorder) owner?) (Inventory at: iBatteryCharger))
						(= theLoop 4)
					else
						(= theLoop 3)
					)
					(theCharger
						x:	  				[chargerCoords (++ i)],
						y:	  				[chargerCoords (++ i)],
						setLoop:			theLoop,
						init:				,
						approachVerbs:	verbLook verbDo verbUse
					)
					(break)
				else
					(+= i 3)
				)
			)
		)

		(= perspective picAngle)
		;(= skipFactor 1)

		;Draw a picture (if non zero) in proper style
		(if picture
			(self drawPic:picture)
		)

		; if it's not a scrolling room and if the room doesn't have a script
		; we're assuming the script is for entering room, since we're in the init:
		(cond
			; in case we're doing speed test or something
			((not (cast contains: ego))
				NULL
			)
			(script
				NULL
			)
			((not ((User alterEgo?) edgeHit?))
				NULL
			)
			((OneOf style SCROLLRIGHT SCROLLLEFT SCROLLUP SCROLLDOWN)
				(= wide (+ 1 (/ (CelWide ((User alterEgo?) view?) ((User alterEgo?) loop?) ((User alterEgo?) cel?)) 2)))
				(= high (+ 1 (/ (CelHigh ((User alterEgo?) view?) ((User alterEgo?) loop?) ((User alterEgo?) cel?)) 2)))

				;Reposition ego if he hit an edge in the previous room.
				(switch ((User alterEgo?) edgeHit?)
					(NORTH
						((User alterEgo?) y: (- southEdge 1))
					)
					(WEST
						((User alterEgo?) x: (- eastEdge wide))
					)
					(SOUTH
						((User alterEgo?) y: (+ horizon high))
					)
					(EAST
						((User alterEgo?) x: (+ westEdge wide))
					)
				)
				((User alterEgo?) edgeHit:0)
			)
			(else
				(self setScript:	enterRmScript NULL prevRoomNum)
			)
		)
	)

	(method	(doit &tmp nRoom)
		;; Send the doit: to any script, then check to see if ego has
		;; hit the edge of the screen.
		;; - revised by Pablo 11/19/88 to save space

		(cond
			(script
				(script doit:)
			)
			; in case we're doing speed test or something
			((not (cast contains: ego))
				NULL
			)
			((= nRoom
				(switch ((User alterEgo?) edgeHit?)
					(NORTH	north)
					(EAST		east)
					(SOUTH	south)
					(WEST		west)
				)
			 )
				(self setScript: leaveRmScript NULL nRoom)
			)
		)
	);doit

	(method (doVerb theVerb &tmp [str 200])
		(if (and (== theVerb verbLook) lookStr)
			(Format @str "%s" lookStr)
			(TPrint @str)
			(return TRUE)
		else
			(return FALSE)
		)
	);doVerb
)


; room doit method calls this script when ego hits an edge
; room number that corresponds to which edge ego hit is passed in register
; default behavior:
;	hit north (horizon)
;		just change rooms
;	hit south, east or west
;		move forward in current heading til off screen

(instance leaveRmScript of Script
	(properties
		name	"lRS"
	)
	(method (changeState ns &tmp high wide)
		(switchto (= state ns)
			(
				(HandsOff)
				(= wide (CelWide (ego view?) (ego loop?) (ego cel?)))
				(= high (CelHigh (ego view?) (ego loop?) (ego cel?)))

				(switch register
					((client north?)
						(curRoom newRoom: register)
					)
					((client south?)
						(ego setMotion: PolyPath (ego x?) (+ southEdge high) self)
					)
					((client east?)
						(ego setMotion: PolyPath (+ eastEdge wide) (ego y?) self)
					)
					((client west?)
						(ego setMotion: PolyPath (- westEdge wide) (ego y?) self)
					)
				)
			)
			(
				; cuz sometimes ego hits an illegal spot before moving completely off
				; screen.  this looks bad with scrolling
				(ego hide:)
				(= cycles 2)
			)
			(
				(curRoom
					setScript:	0,
					newRoom:		register
				)
			)
		)
	)
);leaveRmScript


; room init method calls this script if there's not a script already attached
; to the room and it's style isn't one of the SCROLLs
; prevRoomNum is passed in register
; default behavior
;	from north
;		ego appear at (+ horizon yStep)
;	from south
;		start below screen & walk up
;	from east
;		walk from east
;	from west
;		walk form west

(instance enterRmScript of Script
	(properties
		name	"eRS"
	)

	(procedure	(SeeIfOffX)
		(cond
			((< (ego x?) westEdge)
				(ego x: (+ westEdge (* (ego xStep?) 2)))
			)
			((> (ego x?) eastEdge)
				(ego x: (- eastEdge (* (ego xStep?) 2)))
			)
		)
	)
	(procedure	(SeeIfOffY)
		(cond
			((< (ego y?) (curRoom horizon?))
				(ego y: (+ (curRoom horizon?) (* (ego yStep?) 2)))
			)
			((> (ego y?) southEdge)
				(ego y: (- southEdge (* (ego yStep?) 2)))
			)
		)
	)

	(method (changeState ns &tmp high wide)
		(switchto (= state ns)
			(
				; cuz sometimes there were cycles left over from a previous
				; invocation
				(= cycles 0)

				(HandsOff)
				(= high (CelHigh (ego view?) (ego loop?) (ego cel?)))
				(= wide (CelWide (ego view?) (ego loop?) (ego cel?)))

				(switch register
					((client north?)
						(SeeIfOffX)
						(ego y: (+ (curRoom horizon?) (ego yStep?)))
						(= cycles 1)
					)
					((client south?)
						(SeeIfOffX)
						(ego
							y:	(+ southEdge high),
							setMotion: PolyPath (ego x?) (- southEdge (* (ego yStep?) 2)) self
						)
					)
					((client east?)
						(SeeIfOffY)
						(ego
							x:	(+ eastEdge (/ wide 2)),
							setMotion: PolyPath (- eastEdge (* (ego xStep?) 2)) (ego y?) self
						)
					)
					((client west?)
						(SeeIfOffY)
						(ego
							x: (- westEdge (/ wide 2)),
							setMotion: PolyPath (+ westEdge (* (ego xStep?) 2)) (ego y?) self
						)
					)
					; for when you come into a room via teleport or if the game
					; legitimately tosses you into a room from somewhere else
					(else
						(= cycles 1)
					)
				)
			)
			(
				(HandsOn)
				(self dispose:)
			)
		)
	)
);enterRmScript
