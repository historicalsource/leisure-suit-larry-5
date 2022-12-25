                              PolyMake

This version of PolyMake contains fixes for some bugs that were present in
previous versions as well as enhancements as noted below.  Some of the
revision numbers below are internal releases only.

Note to ex-Microsoft Make users:
  The Microsoft make program is different than Unix-like makes in that it
  requires that the dependencies be specified in the makefile in the order
  that they are required to be made.  If a makefile constructed for Microsoft
  Make is used with PolyMake (or other Unix-like makes), only the first
  target in the makefile will be made.  The solution is to create a new
  "first target" in the makefile that has as its source the target(s) that
  you wish to be made.
  

V2.1    (06 May 1987)
X041    (06 May 1987)

Corrections

- In certain cases, the wrong timestamp was being extracted from PVCS
  logfiles.
  
X040    (04 May 1987)

Enhancements

- Added more new features - .DEINIT target, filename matching macro, library
  member references.  See the file FEATURES.DOC for more details.
  
X039    (16 Apr 1987)

Corrections

- .DEFAULT rules were being executed even if the corresponding files existed.

X038    (10 Apr 1987)

Enhancements

- Two powerful new features have been added - internal commands and conditional
  constructs.  See the file FEATURES.DOC for more details.

X037    (04 Apr 1987)

Corrections

- Files having a relative path component in conjunction with a .PATH macro
  resulted in an incorrect filename being constructed.
  
- Certain macro expansions, typically longer ones, resulted in dropped
  characters.

Enhancements

- The macro expansion algorithm has been modified.  Previously, the $() and
  ${} macros were expanded left to right followed by the $[] macros
  being expand from right to left.  This strategy did not allow nested macro
  invocations of the form $($(FOO)BAR) which are quite useful especially for
  building a configurable makefile.

  The new macro expansion strategy is to make a single macro expansion pass
  from right to left, expanding $(), ${}, and $[] simultaneously.  This new
  method should be backward compatible, meaning that makefiles that used to
  work should still work.  If it is desirable or necessary, for some reason,
  to revert to the old macro expansion strategy, the new control .LR_EXPAND
  will do so.  As with all other makefile controls, the .LR_EXPAND control
  should begin in column 1 of the makefile.

V2.0    (16 Mar 1987)

Corrections

- Libraries with modules not having timestamps sometimes caused unusual
  error messages to appear.
  
Enhancements

- You have received either an insert for the old manual or a completely
  new manual that describes the new features of PolyMake.

  The manual insert omits the documentation of the include directive.
  This feature allows PolyMake to read multiple files in the same manner
  as many compilers and assemblers do.  The syntax of the include directive
  is:

  include <file>

  where the word "include" must begin in the first column and is case in-
  sensitive.  Upon encountering this directive, PolyMake suspends reading
  of the current makefile (or builtins file) and proceeds to read <file>.
  When the include file is exhausted, PolyMake resumes reading the original
  file.  Includes may be nested to about (FILES - 5) levels where FILES is
  the number of files specified in your CONFIG.SYS file.  (See your DOS
  manual on how to configure your system.)

  We have asserted that the new Polymake is up to 10 times faster than the
  older versions.  It is important to add that this measurement was taken
  when using the -N flag to PolyMake.  This allows the measurement to be
  affected solely by PolyMake performance.  It makes sense to talk in these
  terms because we can't do anything to speed up the executed commands.
  The net effect will depend on how long it takes to execute your commands.
  A makefile having one command that takes an hour to execute will show a
  negligible performance increase.  Commands that take zero time to execute
  (emulated by -N) will show the maximum performance increase.
  
- The distribution disk contains an additional program RSE.EXE.  This
  program allows you to Redirect Standard Error output to be the same as
  standard output.  This is most useful when redirecting standard output
  to a file so that ALL of the output, both to standard error and standard
  out, is redirected to the file.  The invocation syntax is:

      RSE <command>

  where <command> is any valid DOS command.  For example,

      RSE MAKE > MAKE.LOG

  will run make and redirect all output to the file MAKE.LOG.  RSE returns
  an exit code of 127 if the command cannot be executed.  Otherwise, it
  returns the exit code of the executed program.

Caveats

   The $* now correctly expands to the current target name less the extension.
   Previously, it expanded to just the root part of the target, ie. without
   drive/path and extension.  This change may break some makefiles.  To fix
   them, simply use $[r,$@] or $[r,$*] both of which expand to the root only.

X036    (17 Mar 1987)

- A procedure for modifying the default value of some PolyMake parameters
  is given in the file CONFIG.DOC.
