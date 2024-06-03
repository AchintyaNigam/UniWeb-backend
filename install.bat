@echo off
REM Define the service name
set SERVICE_NAME=UniWebBackendVer4

REM Define paths
set JAVA_PATH=C:\Program Files\Java\jdk-20\bin\java.exe
set JAR_PATH=C:\Users\AchintyaNigam\Downloads\UniWeb\UniWeb-backend\target\UniWeb-0.0.1-SNAPSHOT.jar
set WORKING_DIR=C:\Users\AchintyaNigam\Downloads\UniWeb\UniWeb-backend\target
set JASYPT_PASSWORD=UNIWEB

REM Install the service
nssm install %SERVICE_NAME% "%JAVA_PATH%"  -Djasypt.encryptor.password=%JASYPT_PASSWORD% -jar "%JAR_PATH%"

REM Set the working directory
nssm set %SERVICE_NAME% AppDirectory "%WORKING_DIR%"

REM Configure logging
nssm set %SERVICE_NAME% AppStdout "%WORKING_DIR%\%SERVICE_NAME%_stdout.log"
nssm set %SERVICE_NAME% AppStderr "%WORKING_DIR%\%SERVICE_NAME%_stderr.log"

REM Start the service
nssm start %SERVICE_NAME%

echo Service %SERVICE_NAME% installed and started.
