#Back Service - Config Server
This module integrates application settings into one central location.

##Security
The programs of AUTH-SERVICE and REGISTRY_SERVICE have access to information directly using a fixed password. (WebSecurity)
other Applications must be authenticated using oauth2 (AUTH-SERVICE).

##Reference
[Spring Cloud Config](https://spring.io/projects/spring-cloud-config)

##Sequence
| No | From | To | Action |
| :----: | :---- | :----: | :---- |
| 1 | (Micro Service) | (Spring Auth Service) | Request with Client Credential Grant |
| 2 | (Spring Auth Service) | (Micro Service) | Token |
| 3 | (Micro Services) | (Spring Config Server) | Request Configuration with bearer Token |
| 4 | (Spring Config Server) | (Spring Auth Service) | Validate Token |
| 5 | (Spring Auth Service) | (Spring Config Server) | Return UserInfo |
| 6 | (Spring Config Server) | (Spring Config Server) | Validate Client Request's Url With Client's Scope |
| 7 | (Spring Config Server) | (Micro Service) | Return Configuration |