# Overview
This project is a simple showcase of the Axon 3 CQRS framework. 

# Prerequisites
* [JDK 8](http://www.oracle.com/technetwork/java/index.html) installed and working
* Building under [Ubuntu Linux](http://www.ubuntu.com/) is supported and recommended 

# Building
Type `./gradlew` to build and assemble the service.

# Installation
TODO

# Tips and Tricks

## Verifying The Setup
TODO

## Running Integration Tests From Gradle
TODO

## Running Acceptance Tests From Gradle
TODO

## Running Acceptance Tests From IDEA
TODO

## Operations Endpoints
The services supports a variety of endpoints useful to an Operations engineer.

* /operations - Provides a hypermedia-based “discovery page” for the other endpoints.
* /operations/autoconfig - Displays an auto-configuration report showing all auto-configuration candidates and the reason why they ‘were’ or ‘were not’ applied.
* /operations/beans - Displays a complete list of all the Spring beans in your application.
* /operations/configprops - Displays a collated list of all `@ConfigurationProperties`.
* /operations/dump - Performs a thread dump.
* /operations/env - Exposes properties from Spring’s `ConfigurableEnvironment`.
* /operations/flyway - Shows any `Flyway` database migrations that have been applied.
* /operations/health - Shows application health information.
* /operations/info - Displays arbitrary application info.
* /operations/liquibase - Shows any `Liquibase` database migrations that have been applied.
* /operations/logfile - Returns the contents of the logfile (if logging.file or logging.path properties have been set).
* /operations/metrics - Shows ‘metrics’ information for the current application.
* /operations/mappings - Displays a collated list of all `@RequestMapping` paths.
* /operations/shutdown - Allows the application to be gracefully shutdown (not enabled by default).
* /operations/trace - Displays trace information (by default the last few HTTP requests).

## REST API Documentation
You can find the current API documentation at `/docs/index.hml`.

## CORS Support
All origins are allowed.  Here is an example conversation:

```
http --verbose OPTIONS http://192.168.1.64:8080/descriptor/application Origin:nowhere.com Access-Control-Request-Method:DELETE

OPTIONS /descriptor/application HTTP/1.1
Accept: */*
Accept-Encoding: gzip, deflate
Access-Control-Request-Method: DELETE
Connection: keep-alive
Content-Length: 0
Host: 192.168.1.64:8080
Origin: nowhere.com
User-Agent: HTTPie/0.9.6



HTTP/1.1 200
Access-Control-Allow-Credentials: true
Access-Control-Allow-Methods: GET,POST,DELETE,PUT,OPTIONS
Access-Control-Allow-Origin: nowhere.com
Access-Control-Max-Age: 1800
Allow: GET, HEAD, POST, PUT, DELETE, TRACE, OPTIONS, PATCH
Content-Length: 0
Date: Sun, 09 Oct 2016 16:54:48 GMT
Vary: Origin
X-Application-Context: jvm-guy-spring-boot-project:8080
```

# Troubleshooting

TODO

# License and Credits
This project is licensed under the [Apache License Version 2.0, January 2004](http://www.apache.org/licenses/).

