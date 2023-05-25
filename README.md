# spring-demo-kotlin
demo project to show dependencies error after update to spring 3.1.0


# To reproduce the error 
- Make sure spring version is 3.1.0
- Run the test TestExample
- And if you change to spring version 3.0.6 test is working fine


# To run the project 
- Make sure you have docker installed in your local
- Start docker compose : cd deployment/local/sh start.sh
- Start SpringBoot application (build & run using java 17)
