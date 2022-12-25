(script# WRITEFTR)
;;;;
;;;;	WRITEFTR.SC
;;;;	(c) Sierra On-Line, Inc, 1990
;;;;
;;;;	Author: J.Mark Hood
;;;;
;;;;	Simple C.A.S.E. Tools for positioning and adding response to
;;;;	Features, PicViews, Views, Props, Actors and their subclasses.
;;;;
;;;;	Classes:
;;;;		WriteCode
;;;;		CreateObject
;;;;
;;;; also several internal procedures
(local
	[nameString	100]
	[nounString 100]	
	[sCStr 		100] 
	[cCStr 		100] 
	[descString 100]
	[sightStr  	30]
	[getDistStr	30]
	[seeDistStr	30]
	toScreen	=	TRUE
	drawNSRect	= TRUE
	inited	=	FALSE
	theType 	=	FALSE
)

(procedure
	DoFeature
	DoView
	DoProperties
	Logit
	GetVerb
)
(enum
	exitPrint
	makeFeature
	makePicView
	makeView
	makeProp
	makeActor
)
(class WriteCode of RootObj  ; get check

	(methods
		doit
		writeList
	)

	(method (doit theObj &tmp [buffer 400] [vlcOrNsStr 40])
		(if (theObj isMemberOf: Feature)
			(Format @vlcOrNsStr 
				"______nsLeft %d\r
				 ______nsTop  %d\r
				 ______nsBottom %d\r
				 ______nsRight %d\r" 
				(theObj nsLeft?)
				(theObj nsTop?)
				(theObj nsBottom?)
				(theObj nsRight?)
			)
		else
			(Format @vlcOrNsStr
				"______view	%d\r
				 ______loop %d\r
				 ______cel  %d\r"
				 (theObj view?)
				 (theObj loop?)
				 (theObj cel?)
			)
		)
		(Format @buffer 
			"(instance %s of %s\r
				___(properties\r
				______x					%d		\r
				______y					%d		\r
				______z					%d		\r
				______heading			%d		\r
				______noun			 \'%s\'	\r
				%s
				 _____description	 \"%s\"	\r
				______sightAngle		%d		\r
				______closeRangeDist	%d		\r
				______longRangeDist 	%d		\r
				______shiftClick		%s		\r
				______contClick		%s		\r
				___)\r
				)\r 
			"
			@nameString
			(if (== theType makePicView)
				(PicView name?)
			else
				((theObj superClass?) name?)
			)
			(theObj x?)
			(theObj y?)							
			(theObj z?)							
			(theObj heading?)
			@nounString
			@vlcOrNsStr
			@descString
			(theObj sightAngle?)				
			(theObj closeRangeDist?)			
			(theObj longRangeDist?)			
			@sCStr 	
			@cCStr 	
		)
		(if toScreen
			(Print @buffer #font:999 #title:{Feature Writer V1.0})
		)
		(Logit @buffer)
		(if (theObj isMemberOf: Feature)
			(theObj dispose:)
		else
			(theObj addToPic:)
		)
	)

;; although currently not used
;; this is a useful method for writing out the cast, features list etc.
	(method (writeList theList)
		(theList eachElementDo: #perform: self)
		(CreateObject doit:)
;		(DisposeScript 800)
	); writeObjects
)



;(class stringHolder	of Object
;	(properties
;		who			0
;		noun			0
;		shiftClick	0
;		contClick	0
;	)
;)

(class CreateObject	of RootObj
	(methods 
		doit
	)
	(method (doit &tmp [thePath 15] theObj event)
		(if (not inited)
			(= thePath 0)
			(GetInput @thePath 30 {Enter path and filename})
			(Format @sysLogPath @thePath)
			(Format @sCStr	"verbLook")
			(Format @cCStr	"verbGet")
			(switch (Print "Outline Features?"
					#title:{Feature Writer V1.0}
					#button: {YES}	1
					#button: {NO}	2
				)
				(1 (= drawNSRect TRUE))
				(2 (= drawNSRect FALSE))
			)
			(switch (Print "Display code to screen?"
					#title:{Feature Writer V1.0}
					#button: {YES}	1
					#button: {NO}	2
				)
				(1 (= toScreen TRUE))
				(2 (= toScreen FALSE))
			)
			(= inited TRUE)
		)					 	
		(= theType
			(Print  "Class?"
				#title:	{Feature Writer V1.0}
				#button:	{Feature}	makeFeature
				#button:	{PicView}	makePicView
				#button:	{View}		makeView
				#button:	{Prop}		makeProp
				#button:	{Actor}		makeActor
			)
		)
		(if (not theType) (return))
		(= theObj
			(
				(switch theType
					(makeFeature
						Feature 
					)	
					(makePicView
						View 
					)
					(makeView
						View	
					)
					(makeProp
						Prop	
					)
					(makeActor
						Actor		
					)
				)
				new:
			)
		)
		(GetInput @nameString 30 {Name?})
		(StrCpy @descString @nameString)
		(DoProperties theObj)
		(if (== theType makeFeature)
			(DoFeature theObj)
		else	; something with a view, loop and cel
			(DoView theObj)
		)
		(if (Print  "Z property"
		 	#title:	{Feature Writer V1.0}
		 	#button:	{YES}	TRUE	
		 	#button:	{NO}	FALSE			
			)
			(Print "Please	click the mouse on the objects projection onto the ground")
			(while (!= ((= event (Event new:)) type?) mouseDown) 
				(event dispose:)
			)
			(GlobalToLocal event)
			(theObj z:(- (event y?)	(theObj y?)))
			(theObj y:(event y?))
			(event dispose:)
		)
		(WriteCode doit: theObj)
	)
)

(procedure (DoFeature obj &tmp event theX theY theTop theLeft theBottom theRight)
	(Print "Click left mouse button on top left corner")
	(while (!= ((= event (Event new:)) type?) mouseDown) (event dispose:))
	(GlobalToLocal event)
	(= theTop (event y?))
	(= theLeft (event x?))
	(Print "Click left mouse button on bottom right corner")
	(while (!= ((= event (Event new:)) type?)	mouseDown) (event dispose:))
	(GlobalToLocal event)
	(= theBottom 	(event y?))
	(= theRight 	(event x?))
	(= theX	(+ (/ (- theRight theLeft) 2) theLeft))
	(= theY	(+ (/ (- theBottom theTop) 2) theTop))
	(obj
		x:				theX,
		y:				theY,
		nsLeft:		theLeft,
		nsTop:		theTop,
		nsBottom:	theBottom,
		nsRight:		theRight,
	)
	(if drawNSRect
		(Graph GDrawLine theTop    theLeft 	 	theTop 		theRight 	VMAP vWHITE)
		(Graph GDrawLine theBottom theLeft 		theBottom	theRight 	VMAP vWHITE)
		(Graph GDrawLine theTop		theLeft 	  	theBottom 	theLeft 		VMAP vWHITE)
		(Graph GDrawLine theTop		theRight   	theBottom 	theRight 	VMAP vWHITE)
		(Graph GShowBits theTop 	theLeft 		theBottom 	theRight 	VMAP)
	)
 	(event dispose:)
)



(procedure (DoView obj &tmp event)
	(obj
		view:			(GetNumber {View?} 0),
		loop:			(GetNumber {Loop?} 0),
		cel:			(GetNumber {Cel?} 0),
		signal:		(| fixPriOn ignrAct),
		priority:	15,
		init:
	)
	(if (obj respondsTo:#illegalBits) (obj illegalBits:0))
	(while (!= ((= event (Event new:)) type?) mouseDown) 
		(GlobalToLocal event)
		(obj posn:(event x?) (event y?))
		(Animate (cast elements?) FALSE)
		(event dispose:)
	)
 	(event dispose:)
	
)

(procedure (Logit what)
	(File	
		name:	 @sysLogPath,
		writeString: what,
		close:
	)
	(DisposeScript FILE)
)

;(procedure (DoProperties obj 
;					&tmp 
;					theDialog 
;					nounT 
;					nounE 
;					scT
;					scE
;					ccT
;					ccE
;					lookT
;					lookE
;					descT
;					descE
;					sightAngT
;					sightAngE
;					getDistT
;					getDistE
;					seeDistT
;					seeDistE
;					[titleString 40]
;				)
;	(Format @titleString "instance %s of %s" @nameString ((obj superClass?) name?))
;	((= theDialog (Dialog new:))
;		window: systemWindow,
;		text:	  @titleString	
;	)
;	(StrCpy @nounString {/})
;	((= nounT (DText new:))
;		text:		{/noun?},
;		moveTo: 	MARGIN MARGIN, 
;		font:		999,
;		setSize:
;	)
;	((= nounE (DEdit new:))
;		text:		@nounString,
;		font: 	999, 
;		moveTo: 	MARGIN (+ (nounT nsBottom?) 4),
;		max:		40,
;		setSize:
;	)
;	(StrCpy @sCStr {#doLook})
;	((= scT (DText new:))
;		text:		{shiftClick selector?},
;		moveTo: 	MARGIN (+ (nounE nsBottom?) 4), 
;		font:		999,
;		setSize:
;	)
;	((= scE (DEdit new:))
;		text:		@sCStr,
;		font: 	999, 
;		moveTo: 	MARGIN (+ (scT nsBottom?) 4),
;		max:20,
;		setSize:
;	)
;	(StrCpy @cCStr {#doGet})
;	((= ccT (DText new:))
;		text:		{cntrlClick selector?},
;		moveTo: 	(+ (scT nsRight?) 8) (scT nsTop?), 
;		font:		999,
;		setSize:
;	)
;	((= ccE (DEdit new:))
;		text:		@cCStr,
;		font: 	999, 
;		moveTo: 	(ccT nsLeft?) (+ (ccT nsBottom?) 4),
;		max:		20,
;		setSize:
;	)
;	(= lookString 0)
;	((= lookT (DText new:))
;		text:		{look response?},
;		moveTo: 	MARGIN (+ (scE nsBottom?) 4), 
;		font:		999,
;		setSize:
;	)
;	((= lookE (DEdit new:))
;		text:		@lookString,
;		font: 	999, 
;		moveTo: 	MARGIN (+ (lookT nsBottom?) 4),
;		max:40,
;		setSize:
;	)
;	(= descString 0)
;	((= descT (DText new:))
;		text:		{description?},
;		moveTo: 	MARGIN (+ (lookE nsBottom?) 4), 
;		font:		999,
;		setSize:
;	)
;	((= descE (DEdit new:))
;		text:		@descString,
;		font: 	999, 
;		moveTo: 	MARGIN (+ (descT nsBottom?) 4),
;		max:		40,
;		setSize:
;	)
;	(StrCpy @sightStr {90})
;	((= sightAngT (DText new:))
;		text:		{sight angle?},
;		moveTo: 	MARGIN (+ (descE nsBottom?) 4), 
;		font:		999,
;		setSize:
;	)
;	((= sightAngE (DEdit new:))
;		text:		@sightStr,
;		font: 	999, 
;		moveTo: 	MARGIN (+ (sightAngT nsBottom?) 4),
;		max:		4,
;		setSize:
;	)
;
;	(StrCpy @getDistStr {50})
;	((= getDistT (DText new:))
;		text:		{get distance?},
;		moveTo: 	(+ (sightAngT nsRight?) 10)  (sightAngT nsTop?), 
;		font:		999,
;		setSize:
;	)
;	((= getDistE (DEdit new:))
;		text:		@getDistStr,
;		font: 	999, 
;		moveTo: 	(getDistT nsLeft?)	(+ (sightAngT nsBottom?) 4),
;		max:		4,
;		setSize:
;	)
;
;	(StrCpy @seeDistStr {100})
;	((= seeDistT (DText new:))
;		text:		{see distance?},
;		moveTo: 	(+ (getDistT nsRight?) 10)  (sightAngT nsTop?), 
;		font:		999,
;		setSize:
;	)
;	((= seeDistE (DEdit new:))
;		text:		@seeDistStr,
;		font: 	999, 
;		moveTo: 	(seeDistT nsLeft?)	(+ (sightAngT nsBottom?) 4),
;		max:		4,
;		setSize:
;	)
;	(theDialog 
;		add: 			
;			nounT 		nounE 
;			scT 			scE 
;			ccT 			ccE 
;			lookT 		lookE 
;			descT 		descE
;			sightAngT	sightAngE
;			getDistT		getDistE
;			seeDistT		seeDistE,
;		setSize:		, 
;		center:		,
;		open:			,
;		doit:
;	)
;	(obj
;		noun:				@nounString,
;		shiftClick:		@sCStr,
;		contClick:		@cCStr,
;		lookStr:			@lookString,
;		sightAngle:		(ReadNumber @sightStr),
;		getableDist:	(ReadNumber	@getDistStr),
;		seeableDist:	(ReadNumber	@seeDistStr),
;		description:	@descString
;	)
;	(theDialog dispose:)
;)



; less filling DoProperties light.

(procedure (DoProperties obj)
;	(featuresStrings add:
;		((stringHolder new:)
		(obj
;			who:obj,
			noun:(GetInput	@nounString 30 {/noun?}),
			shiftClick:(GetVerb	@sCStr 20 {shftClick verb?}),
			contClick:(GetVerb	@cCStr 20 {cntrlClick verb?}),
			sightAngle:(GetNumber {sight angle?} 90),	
			closeRangeDist:(GetNumber {getable dist?} 50),
			longRangeDist:(GetNumber {seeable dist?} 100),
			description:(GetInput @descString 50 {description?})
		)
;	)
)

(procedure (GetVerb theString theSize theTitle)
	(GetInput theString theSize theTitle)
	(return
		(cond
			((StrCmp theString {verbLook})
				verbLook
			)
;			((StrCmp theString {verbOpen})
;				verbOpen	
;			)
;			((StrCmp theString {verbClose})
;				verbClose
;			)
;			((StrCmp theString {verbSmell})
;				verbSmell
;			)
;			((StrCmp theString {verbMove})
;				verbMove	
;			)
;			((StrCmp theString {verbEat})
;				verbEat 	
;			)
;			((StrCmp theString {verbGet})
;				verbGet 	
;			)
		)
	)
)
