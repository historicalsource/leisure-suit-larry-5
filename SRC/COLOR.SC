;;;;
;;;;	(c) Sierra On-Line, Inc, 1990
;;;;
;;;;	Game:		Space Quest IV
;;;;	Author:	Mark Wilden
;;;;
;;;;	Initialize global variables for colors.
;;;;
;;;;	This method gathers together all color assignments in one place.  It
;;;;	enables programmers to use a mnemonic like 'colYellow' instead of looking
;;;;	up the index number.  It also makes it easier to convert the game to
;;;;	different palettes (like 16-color EGA) because colors are assigned based
;;;;	on their proportions of red, green and blue, and are thus not tied to
;;;;	any specific palette.
;;;;
;;;;	There are assignments here for colors and for uses.  Colors are 'colVLRed'
;;;;	(very light red) and 'colDYellow' (dark yellow), etc.  Uses are 'colLED'
;;;;	(used for LEDs in keypads) and 'colLaser' (the laser color), etc.

(script# COLOR)

(procedure	ColorInit)
(public		ColorInit 0)

(procedure (ColorInit)

	; commented out since (Graph GDetect) currently returns -1 for VGA320
	;(if (!= (Graph GDetect) 256)
	;	(Print "Error in ColorInit:  unknown palette")
	;	(= quit TRUE)
	;	(return)
	;)

	(if (> (Graph GDetect) 16)
		;                                   red grn blu   	# in std palette
		(= colBlack			(Palette PALMatch  31  31  31))	; 1
		(= colGray1			(Palette PALMatch  63  63  63))	; 2 is darkest gray
		(= colGray2			(Palette PALMatch  95  95  95))	; 3
		(= colGray3			(Palette PALMatch 127 127 127))	; 4
		(= colGray4			(Palette PALMatch 159 159 159))	; 5
		(= colGray5			(Palette PALMatch 191 191 191))	; 6 is lightest gray
		(= colWhite			(Palette PALMatch 223 223 223))	; 7
		(= colDRed			(Palette PALMatch 151  27  27))	; 10
		(= colLRed			(Palette PALMatch 223  71  71))	; 13
		(= colVLRed			(Palette PALMatch 235 135 135))	; 15
		(= colDYellow		(Palette PALMatch 187 187  35))	; 27
		(= colYellow		(Palette PALMatch 219 219  39))	; 28
		(= colLYellow		(Palette PALMatch 223 223  71))	; 29
		(= colVDGreen		(Palette PALMatch  15  87  15))  ; 32
		(= colDGreen		(Palette PALMatch  27 151  27))	; 34
		(= colLGreen		(Palette PALMatch  71 223  71))	; 37
		(= colVLGreen		(Palette PALMatch 135 235 135))	; 39
		(= colDBlue			(Palette PALMatch  23  23 119))	; 49
		(= colBlue			(Palette PALMatch  35  35 187))	; 51
		(= colLBlue			(Palette PALMatch  71  71 223))	; 53
		(= colVLBlue		(Palette PALMatch 135 135 235))	; 55
		(= colMagenta		(Palette PALMatch 219  39 219))	; 60
		(= colLMagenta		(Palette PALMatch 223  71 223))	; 61
		(= colCyan			(Palette PALMatch	 71 223 223))	; 45
		(= colLCyan			(Palette PALMatch	135 235 235))	; 47
		(= colWindow		(Palette PALMatch 235 235 135))	; 31 ;per Bill Davis
	else
		(= colBlack		0)
		(= colGray1		7)
		(= colGray2		8)
		(= colGray3		7)
		(= colGray4		7)
		(= colGray5		7)
		(= colWhite		15)
		(= colDRed		4)
		(= colLRed		12)
		(= colVLRed		12)
		(= colDYellow	6)
		(= colYellow	14)
		(= colLYellow	14)
		(= colVDGreen	2)
		(= colDGreen	2)
		(= colLGreen	10)
		(= colVLGreen	10)
		(= colDBlue		1)
		(= colBlue		1)
		(= colLBlue		9)
		(= colVLBlue	9)
		(= colMagenta	5)
		(= colLMagenta	13)
		(= colCyan		3)
		(= colLCyan		11)
		(= colWindow	14)
	)
)

; This table gives the three color values (red, green and blue) for each
; of the 64 colors in the "base" 256-color VGA palette

; black...white
; 0:   0   0   0 ; this guy looks sort of pink
; 1:  31  31  31
; 2:  63  63  63
; 3:  95  95  95
; 4: 127 127 127
; 5: 159 159 159
; 6: 191 191 191
; 7: 223 223 223

; red
; 8:  87  15  15
; 9: 119  23  23
;10: 151  27  27
;11: 187  35  35
;12: 219  39  39
;13: 223  71  71
;14: 231 103 103
;15: 235 135 135

; brown
;16:  87  51  15
;17: 119  71  23
;18: 151  91  27
;19: 187 111  35
;20: 219 127  39
;21: 223 147  71
;22: 231 167 103
;23: 235 187 135

; yellow
;24:  87  87  15
;25: 119 119  23
;26: 151 151  27
;27: 187 187  35
;28: 219 219  39
;29: 223 223  71
;30: 231 231 103
;31: 235 235 135

; green
;32:  15  87  15
;33:  23 119  23
;34:  27 151  27
;35:  35 187  35
;36:  39 219  39
;37:  71 223  71
;38: 103 231 103
;39: 135 235 135

; cyan
;40:  15  87  87
;41:  23 119 119
;42:  27 151 151
;43:  35 187 187
;44:  39 219 219
;45:  71 223 223
;46: 103 231 231
;47: 135 235 235

; blue
;48:  15  15  87
;49:  23  23 119
;50:  27  27 151
;51:  35  35 187
;52:  39  39 219
;53:  71  71 223
;54: 103 103 231
;55: 135 135 235

; magenta
;56:  87  15  87
;57: 119  23 119
;58: 151  27 151
;59: 187  35 187
;60: 219  39 219
;61: 223  71 223
;62: 231 103 231
;63: 235 135 235

