# Taskmaster

Taskmaster is a RESTFUL Api that allows you to add / edit / delete tasks for users to do.

Currently setting up paths and creating tables for stored of data.

Taskmaster users DynamoDB for storage data.
It uploads files to S3, and there is a Lambda function that resizes images to 50x50, so both image urls full size and resized will be saved into dynamoDB and available for display on the frontend

## Setup
* Clone the repo via github\
```git clone <repo url>```
* Run the application via your IDE or\
```./gradlew bootRun```

## Paths

## Getting Started

### Reference Documentation
For further reference, please consider the following sections:

* [Official Gradle documentation](https://docs.gradle.org)

### Guides
The following guides illustrate how to use some features concretely:

* [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
* [Serving Web Content with Spring MVC](https://spring.io/guides/gs/serving-web-content/)
* [Building REST services with Spring](https://spring.io/guides/tutorials/bookmarks/)
* [Producing a SOAP web service](https://spring.io/guides/gs/producing-web-service/)
* [Accessing Data with JPA](https://spring.io/guides/gs/accessing-data-jpa/)

### Additional Links
These additional references should also help you:

* [Gradle Build Scans – insights for your project's build](https://scans.gradle.com#gradle)
