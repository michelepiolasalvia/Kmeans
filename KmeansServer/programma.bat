@echo off 

start "Server" java -jar Server.jar

timeout /t 5

start "Client" java -jar Client.jar 127.0.0.1 8080