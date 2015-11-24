@echo off
@echo Running Testcase 006: different kinds of errors
@echo ==============================================
@echo The input is:
type testcases\tc-006.infix
@echo ----------------------------------------------
cd bin

rem : Run the testcase with input direction
java Postfix < ..\testcases\tc-006.infix

rem : Compare the expected output
@echo ----------------------------------------------
@echo The output should be: 
type ..\testcases\tc-006.postfix

cd ..
@echo ==============================================
pause
@echo on
