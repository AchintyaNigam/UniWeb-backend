# UniWeb-backend
---
This is the backend for UniWeb a university management website
---
## Installation and Run Guide

The Following describes the steps required to set up this spring boot server on your machine:
  - Step 1: Set up MySQL server. After setting up the MySQL server for your database, you will need the database URL, your database username and your password.
  - Step 2: Create a directory for installing the server files.
  - Step 3: Run `git init` after opening your terminal.
  - Step 4: Clone this repository using: `git clone https://github.com/AchintyaNigam/UniWeb-backend.git`. This will download all the necessary files for running the Spring Boot Application.
  - Step 5: Open the file 'application.properties' located in '/UniWeb-backend/src/main/resources/' in your created directory.
  - Step 6: This file contains:
                                    `spring.datasource.username=root` and
                                    `spring.datasource.password=ENC(vUVms8n1izrt9q3XeBJiyt2zkYPPHUz1DlGJui5OJpWIQhLMFf64lrexrhx55ICU)`
                               Replace these with your database username and password. But the password needs to be encrypted. The code given above contains an encrypted password. See the next step on how to encrypt passwords using jasypt.  
  - Step 7: Encrypt datasource password. The application uses jasypt for encrypting and decrypting the database access password. to encrypt run the following in your terminal: 
  
                                        `mvn jasypt:encrypt-value -Djasypt.encryptor.password=<Your_secret_key> -Djasypt.plugin.value=<database_password>`

                                        Replace <Your_secret_key> with a key that will be used for encryption. This key will be required each time you start the spring boot application.
                                        Replace <database_password> with the password for your  database.
  - Step 8: Replace the password in the appliaction.properties file with the password.
  - Step 9: to generate the .JAR file use: `mvn clean install -Djasypt.encryptor.password=<your_secret_key>`. Make sure to replace <your_secret_key> with the key you used to generate your encrypted password in the previous step.
  - Step 10: to Run the server: `mvn spring-boot:run -Dspring-boot.run.jvmArguments="-Djasypt.encryptor.password=<your_secret_key"`. Make sure to replace <your_secret_key> with the key you used to generate your encrypted password in the 7th step.
  If you are using and IDE you change the VM arguments or VM option in the run configurations to include: `-Djasypt.encryptor.password=<your_secret_key` and then run the application as spring boot application from the IDE.

---
  



