@echo off
	if not "%vcsdir%" == "" goto GotDir
	echo Run VCSCFG first.
	quit	

:GotDir
	shift

	if "%0"=="checkout" goto checkout
	if "%0"=="checkin"  goto checkin
	if "%0"=="whatsout" goto whatsout
	if "%0"=="massvcs"  goto massvcs
	if "%0"=="abandon"  goto abandon
	if "%0"=="about"    goto about
	if "%0"=="getowner" goto getowner
	if "%0"=="own"      goto own
	if "%0"=="acquire"  goto own
	if "%0"=="locks"    goto locks
	if "%0"=="isout"    goto locks
	if "%0"=="revertto" goto revert
	if "%0"=="differ"   goto differ

	echo You can't VCSDO %0!
	quit

:checkout
	echo .
	echo .
	if "%1" == "" quit
	get  -l %vcsdir%%1(%1)
	shift
	goto checkout

:checkin
	echo .
	echo .
	if "%1" == "" quit
	put %vcsdir%%1(%1)
	get %vcsdir%%1(%vcssrc%%1)
	shift
	goto checkin

:abandon
	if "%1" == "" quit
	vcs -u %vcsdir%%1
	if errorlevel 1 goto Next
		del %1 > nul
		get %vcsdir%%1(%vcssrc%%1)
	:Next
		shift
		goto abandon

:whatsout
	vlog -bl%1 %vcsdir%*.*
	quit

:locks
	if "%1" == "" quit
	vlog -b %vcsdir%%1 > %tmp%vcsdo.tmp
	find Locks < %tmp%vcsdo.tmp
	del %tmp%vcsdo.tmp
	shift
	goto locks
	quit

:revert
	if "%1" == "" quit
	if "%2" == "" quit
	get  -r%1 %vcsdir%%2(%2)
	quit

:massvcs
	put -T"No description (mass version control)" %vcsdir%%1(%1)
	get %vcsdir%%1(%vcssrc%%1)
	quit

:about
	if "%1" == "" quit
	vlog %vcsdir%%1 > %tmp%vcsdo.tmp
	more < %tmp%vcsdo.tmp
	del %tmp%vcsdo.tmp
	shift
	goto about

:getowner
	if "%1" == "" quit
	vlog -b %vcsdir%%1
	shift
	goto getowner

:own
	if "%1" == "" quit
	vcs -O%VCSID% %vcsdir%%1
	shift
	goto own
