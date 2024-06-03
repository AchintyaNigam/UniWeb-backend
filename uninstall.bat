@echo off
REM Define the service name
set SERVICE_NAME=UniWebBackendVer4

REM Stop the service
nssm stop %SERVICE_NAME%

REM Remove the service
nssm remove %SERVICE_NAME% confirm

echo Service %SERVICE_NAME% stopped and removed.
