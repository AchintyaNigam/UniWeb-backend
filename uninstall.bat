@echo off

REM Define the service names
set SPRING_BOOT_SERVICE_NAME=SpringBootUniWeb
set NGINX_SERVICE_NAME=NginxUniWeb

REM Stop and remove the Spring Boot application service
echo Stopping and removing the Spring Boot application service...
nssm stop %SPRING_BOOT_SERVICE_NAME%
nssm remove %SPRING_BOOT_SERVICE_NAME% confirm

REM Stop and remove the Nginx service
echo Stopping and removing the Nginx service...
nssm stop %NGINX_SERVICE_NAME%
nssm remove %NGINX_SERVICE_NAME% confirm

echo Services stopped and removed successfully.
