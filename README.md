# Web Messaging Application

A web-based messaging platform built with Spring Boot, offering real-time communication features.

## Features

- **User Authentication**: Secure login and registration.
- **Message Persistence**: All messages are stored in a database for future retrieval.
- **Remember Me**: Option to stay logged in across sessions.
- **Public Message Viewing**: Non-logged-in users can view messages.

## Prerequisites

- **Java Development Kit (JDK)**: Version 21 or higher.
- **Gradle**: Version 7.0 or higher.
- **Database**: H2 (in-memory) for development; can be configured for other databases in production.

## Getting Started
./gradlew clean bootJar
#### Run
java -jar slasify.jar
http://localhost:8080


###### List Curl

```
curl --location --request POST 'http://localhost:8080/auth/register' \
--header 'Content-Type: application/json' \
--data-raw '{
"userName": "dungphan",
"email": null,
"passwordHash": "Phandung88"
}'
####



curl --location --request POST 'http://localhost:8080/auth/login' \
--header 'Content-Type: application/json' \
--data-raw '{
"identifier": "dungphan",
"password": "Phandung88",
"rememberMe": true
}'


########
curl --location --request POST 'http://localhost:8080/messages/post'  \
-H "Content-Type: application/json" \
-H "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJkdW5ncGhhbiIsImlhdCI6MTczOTIwNDQ5NywiZXhwIjoxNzQxNzk2NDk3fQ.SQWUuSge2SmyTQeq2zl2SKlO-zibxDPJEb1htLXMKQ0" \
-d '{
"content": "He expects candidates to know their primary backend language inside and out 2"
}'

#####


curl --location --request GET 'http://localhost:8080/messages/all'  \
-H "Content-Type: application/json" \

#####

curl --location --request POST 'http://localhost:8080/comments'\
-H "Content-Type: application/json" \
-H "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJkdW5ncGhhbiIsImlhdCI6MTczOTIwNDQ5NywiZXhwIjoxNzQxNzk2NDk3fQ.SQWUuSge2SmyTQeq2zl2SKlO-zibxDPJEb1htLXMKQ0" \
-d '{
"messageId": 1,
"parentCommentId": null,
"content": "comment on msg 1"
}'

#####

curl --location --request POST 'http://localhost:8080/comments'\
-H "Content-Type: application/json" \
-H "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJkdW5ncGhhbiIsImlhdCI6MTczOTIwNDQ5NywiZXhwIjoxNzQxNzk2NDk3fQ.SQWUuSge2SmyTQeq2zl2SKlO-zibxDPJEb1htLXMKQ0" \
-d '{
"messageId": 1,
"parentCommentId": 1,
"content": "reply on reply 3"
}'


```

