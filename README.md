# spring-demo-kotlin
demo project to show dependencies error


# To reproduce the error
- Run the test TestExample

# How to reproduce : 
- Clone example project on my Github
- Go to deployment/local and run sh start.sh (it will start docker container locally)
- Run SpringBoot application
- Test using Postman with invalid url :` localhost:8100/invalidURI/1`

#### Please make sure there is no existing postgres image in your local as it might be conflict ⚠️⚠️⚠️⚠️
