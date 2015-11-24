@echo off
@echo Running Testcase 005: illegal character.
@echo ==============================================
@echo The input is:
type testcases\tc-005.infix
@echo ----------------------------------------------
cd bin

rem : Run the testcase with input direction
java Postfix < ..\testcases\tc-005.infix

rem : Compare the expected output
@echo ----------------------------------------------
@echo The output should be: 
type ..\testcases\tc-005.postfix

cd ..
@echo ==============================================
pause
@echo on
