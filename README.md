# security-project-start
##Sematec School Project

# Sematec Final Project

### Reference Documentation

For further reference, please consider the following dependencies.
for more detail, please refer to POM file.

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/2.7.1/maven-plugin/reference/html/)
* [Spring Configuration Processor](https://docs.spring.io/spring-boot/docs/2.7.1/reference/htmlsingle/#appendix.configuration-metadata.annotation-processor)
* [Spring Boot DevTools](https://docs.spring.io/spring-boot/docs/2.7.1/reference/htmlsingle/#using.devtools)
* [Spring Data MongoDB](https://docs.spring.io/spring-boot/docs/2.7.1/reference/htmlsingle/#data.nosql.mongodb)
* [Spring Batch](https://docs.spring.io/spring-boot/docs/2.7.1/reference/htmlsingle/#howto.batch)
* [Rest Repositories](https://docs.spring.io/spring-boot/docs/2.7.1/reference/htmlsingle/#howto.data-access.exposing-spring-data-repositories-as-rest)
* [Spring Web](https://docs.spring.io/spring-boot/docs/2.7.1/reference/htmlsingle/#web)
* [Spring Security](https://docs.spring.io/spring-boot/docs/2.7.1/reference/htmlsingle/#web)
* [Spring Data JPA](https://docs.spring.io/spring-boot/docs/2.7.1/reference/htmlsingle/#web)
### Guides

This project uses spring security module and basic authentication to query some data. there are 2 version of APIs, that can 
do operation on a customer entity. JPA repositories used to do CRUD operations.
in case of low permission, certificate number field is not returned in response.

please use basic Authentication type with below user:
````
user : admin 
password: admin
user : mickey 
password: cheese
````
