
# Hardware Store

Application for hardware store



## Backend part of application

**Project architecture**: monolith

_The main reason for choosing_: Since the project is small, a monolithic architecture is suitable for this size. However, if in the course of work it turns out that, for example, the company will need not only a version for web application, but also an API for mobile applications or integration with other services, then in this case it is better to choose a microservice architecture.

**Language**: Java

_The main reason for choosing_: The concept of OOP used in Java fits well with the presentation of project data. The same work on any platforms that support Java was a plus when choosing this language. One of the main advantages was the reliability provided by strict static typing of the language.

**Main framework**: Spring Boot

_The main reason for choosing_: A large number of functions provided out of the box to speed up the development process without losing quality.

**Database**: MariaDB (relational database)

_The main reason for choosing_: The relational database model was chosen based on the consideration that the subject area can be conveniently superimposed on a tabular representation. MariaDB is a mature product that also supports backward compatibility with MySQL. It can store a large amount of data and use optimized caches when executing queries. If the project grows, parallel query execution will be a useful feature.

**Database query executor**: Jooq

_The main reason for choosing_: Jooq is a small library well suited for small monolithic projects or microservice architecture. Unlike the JPA specification, with the Jooq in the project, it was convenient to perform complex database queries.

**Database Migration**: Liquibase

_The main reason for choosing_: Liquibase provides a convenient format for creating migrations for database tables in the form of XML files. The main advantage of using this technology in the further development of the project will be tracking changeset and using only those that have not yet been applied before. It will also be possible to control the execution of changeset for a specific environment by setting the context property.

**Build Tool**: Maven

_The main reason for choosing_: Maven, unlike Gradle, has stable versions with backward compatibility support. In this project, the Maven plugin was used to support Jooq code generation.

## Frontend part of application

**Framework**: Angular

_The main reason for choosing_: Angular is suitable for projects that will scale in the future. In this case, the hardware store will also increase sooner or later.  The MVVM design pattern guarantees convenient maintenance of modules (components in the context of Angular). Typing in TypeScript (which is used in Angular) made it possible to prevent errors made in the process of modifying the code before execution. High compilation speed was also a definite plus when choosing TypeScript.

## Authentication and authorization
All user passwords are securely encrypted with BCrypt. After a successful login, the user is issued a token. The token is hashed using the md5 algorithm. It is valid for 1 day.
A separate filter and provider were created to authenticate requests. The filter was implemented in the chain of filters provided by Spring Security.

The system uses roles for user authorization. Each request has its own set of user roles defined.

## Time limit
With more time, I would also add:

* A separate table with the customer's order history.
* For the product table, column for the product type.
* Filters by product type on the main window.
* Pagination for products on the main window.
* Registration of users.
* Verification by email.
* Support for different currencies (with appropriate conversion).



