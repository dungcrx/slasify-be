# Web Messaging Application

A web-based messaging platform built with Spring Boot, offering real-time communication features.

## Features

- **User Authentication**: Secure login and registration.
- **Real-Time Messaging**: Instant communication between users.
- **Message Persistence**: All messages are stored in a database for future retrieval.
- **Remember Me**: Option to stay logged in across sessions.
- **Public Message Viewing**: Non-logged-in users can view messages.

## Prerequisites

- **Java Development Kit (JDK)**: Version 21 or higher.
- **Gradle**: Version 7.0 or higher.
- **Database**: H2 (in-memory) for development; can be configured for other databases in production.

## Getting Started
./gradlew clean bootJar
java -jar  build/libs/slasify.jar
http://localhost:8080
### List Curl

curl --location --request POST 'http://localhost:8080/auth/register' \
--header 'Content-Type: application/json' \
--data-raw '{
"userName": "dungphan",
"email": null,
"passwordHash": "6f0f700d5949002af015d24e753df80e385b73d7ecf114ad53023c7f7349a870"
}'
####



curl --location --request POST 'http://localhost:8080/auth/login' \
--header 'Content-Type: application/json' \
--data-raw '{
"identifier": "dungphan",
"password": "6f0f700d5949002af015d24e753df80e385b73d7ecf114ad53023c7f7349a870",
"rememberMe": true
}'


########
curl --location --request POST 'http://localhost:8080/messages/post'  \
-H "Content-Type: application/json" \
-H "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJkdW5ncGhhbiIsImlhdCI6MTczOTExOTUzMCwiZXhwIjoxNzQxNzExNTMwfQ.JwhF-fuguv0sxkw8dxGHz8gfz2A9oM272RpDEsobtzo" \
-d '{
"content": "Your message content 2"
}'

#####


curl --location --request GET 'http://localhost:8080/messages/all'  \
-H "Content-Type: application/json" \

#####

curl --location --request POST 'http://localhost:8080/comments'\
-H "Content-Type: application/json" \
-H "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJkdW5ncGhhbiIsImlhdCI6MTczOTExOTUzMCwiZXhwIjoxNzQxNzExNTMwfQ.JwhF-fuguv0sxkw8dxGHz8gfz2A9oM272RpDEsobtzo" \
-d '{
"messageId": 1,
"parentCommentId": 1,
"content": "comment on msg 2"
}'

#####

curl --location --request POST 'http://localhost:8080/comments'\
-H "Content-Type: application/json" \
-H "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJkdW5ncGhhbiIsImlhdCI6MTczOTExOTUzMCwiZXhwIjoxNzQxNzExNTMwfQ.JwhF-fuguv0sxkw8dxGHz8gfz2A9oM272RpDEsobtzo" \
-d '{
"messageId": 1,
"parentCommentId": 2,
"content": "comment on msg 5"
}'
