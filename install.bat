@echo off

REM Define the service names
set NGINX_SERVICE_NAME=NginxUniWeb
set SPRING_BOOT_SERVICE_NAME=SpringBootUniWeb

REM Define paths for Nginx
set NGINX_PATH=C:\nginx-1.27.0\nginx.exe
set NGINX_CONFIG_PATH=C:\nginx-1.27.0\conf\nginx.conf

REM Define paths for Spring Boot
set JAVA_PATH=C:\Program Files\Java\jdk-20\bin\java.exe
set JAR_PATH=C:\Users\AchintyaNigam\Downloads\UniWeb\UniWeb-backend\target\UniWeb-0.0.1-SNAPSHOT.jar
set WORKING_DIR=C:\Users\AchintyaNigam\Downloads\UniWeb\UniWeb-backend
set JASYPT_PASSWORD=UNIWEB

echo Installing Nginx service...
nssm install %NGINX_SERVICE_NAME% "%NGINX_PATH%" -c "%NGINX_CONFIG_PATH%"
nssm set %NGINX_SERVICE_NAME% Start SERVICE_AUTO_START

echo Starting Nginx service...
nssm start %NGINX_SERVICE_NAME%

echo Installing Spring Boot application service...
nssm install %SPRING_BOOT_SERVICE_NAME% "%JAVA_PATH%" -Djasypt.encryptor.password=%JASYPT_PASSWORD% -jar "%JAR_PATH%"
nssm set %SPRING_BOOT_SERVICE_NAME% AppDirectory "%WORKING_DIR%"
nssm set %SPRING_BOOT_SERVICE_NAME% AppStdout "%WORKING_DIR%\spring_boot_stdout.log"
nssm set %SPRING_BOOT_SERVICE_NAME% AppStderr "%WORKING_DIR%\spring_boot_stderr.log"
nssm set %SPRING_BOOT_SERVICE_NAME% Start SERVICE_AUTO_START

echo Starting Spring Boot application service...
nssm start %SPRING_BOOT_SERVICE_NAME%

echo Services installed and started successfully.
