# Article Assessment

Spring boot project having rest apis to create/update/delete article/s.

## Problem Statement
This is the situation: We are a publishing company that created an app for reading news articles.
To serve data to the app we need a backend to provide RESTful APIs for the following use cases:
* allow an editor to create/update/delete an article
* display one article
* list all articles for a given author
* list all articles for a given period
* find all articles for a specific keyword
Each API should only return the data that is really needed to fulfill the use case.
An article usually consists of the following information:
* header
* short description
* text
* publish date
* author(s)
* keyword(s)

## Solution

Rest APIs are implemented using spring boot. H2 is used as the in-memory database. 

### Start Application

Download the project and navigate to assignment folder. Below command can be used in the assignment folder to run the application.

```
mvn spring-boot:run
```
### Documentation
Swagger is leverage to document the apis.

http://localhost:8080/swagger-ui.html provides documentation to the implemented apis.

### Security

All the apis are secured using spring security's basic authentication hence authorization is needed in the request header. Below are the credentials to access the webservices.

``` 
username: user_test
password: secret  
```

### Sample Requests

Below is the request to list down all the articles
```
curl -X GET \
  http://localhost:8080/articles/ \
  -H 'Authorization: Basic dXNlcl90ZXN0OnNlY3JldA==' \
  -H 'Content-Type: application/json' \
  -H 'Postman-Token: 6b12173f-1534-4c8e-b941-07ac305a44a0' \
  -H 'cache-control: no-cache'
```

Below is the request to list down all the articles by [Author name]. Replace {Author name} in below request to Author's full name from DB.
```
curl -X GET \
  'http://localhost:8080/articles/?author={Author name}' \
  -H 'Authorization: Basic dXNlcl90ZXN0OnNlY3JldA==' \
  -H 'Content-Type: application/json' \
  -H 'Postman-Token: 69a6cc21-f62c-4801-b999-537d9db72211' \
  -H 'cache-control: no-cache'
```

Below is the request to list down all the articles by [Keyword].  Replace {Keyword} in below request to Author's full name from DB
```
curl -X GET \
  'http://localhost:8080/articles/?author={Keyword}' \
  -H 'Authorization: Basic dXNlcl90ZXN0OnNlY3JldA==' \
  -H 'Content-Type: application/json' \
  -H 'Postman-Token: 69a6cc21-f62c-4801-b999-537d9db72211' \
  -H 'cache-control: no-cache'
```

Below is the request to list down all the articles for a given period based on the publish date. Date needs to be provided in YYYY-MM-DD format
```
curl -X GET \
  'http://localhost:8080/articles/?fromDate=2019-01-02&toDate=2019-01-03' \
  -H 'Authorization: Basic dXNlcl90ZXN0OnNlY3JldA==' \
  -H 'Content-Type: application/json' \
  -H 'Postman-Token: b9b8ab51-5198-4403-a94f-d4987675668a' \
  -H 'cache-control: no-cache'
```

Below is the sample request to create an article. 
In case of successful response it returns 201 status code and location of new resource thus created in header like http://localhost:8080/articles/{newArticleId}

```
curl -X POST \
  http://localhost:8080/articles \
  -H 'Authorization: Basic dXNlcl90ZXN0OnNlY3JldA==' \
  -H 'Content-Type: application/json' \
  -H 'Postman-Token: 7720479f-7be4-41e6-bfbf-64bbbc4f6e4b' \
  -H 'cache-control: no-cache' \
  -d '{
    "header": "sample header for article",
    "shortDescription": "sample short description for article",
    "text": "sample text for article",
    "publishDate": "2019-01-02T22:44:52.999",
    "authors": [
        26,
        27
    ],
    "keywords": [
        8,
        9
    ]
}'
```

Below is the sample request to update an article. In case of successful response it returns 204 status code.

```
curl -X PUT \
  http://localhost:8080/articles/41 \
  -H 'Authorization: Basic dXNlcl90ZXN0OnNlY3JldA==' \
  -H 'Content-Type: application/json' \
  -H 'Postman-Token: b91cf569-fbd2-4ff0-bfc0-a99fbaa7f0e3' \
  -H 'cache-control: no-cache' \
  -d '{
    "header": "updated - sample header for article",
    "shortDescription": "updated - sample short description for article",
    "text": "supdated - ample text for article",
    "publishDate": "2019-01-03T01:01:01.999",
    "authors": [
        22,
        23
    ],
    "keywords": [
        6,
        7
    ]
}'
```

Below is the request to delete an article using articleId

```
curl -X DELETE \
  http://localhost:8080/articles/{articleId} \
  -H 'Authorization: Basic dXNlcl90ZXN0OnNlY3JldA==' \
  -H 'Content-Type: application/json' \
  -H 'Postman-Token: e773b500-c3ab-40ea-860e-2672ace97cb7' \
  -H 'cache-control: no-cache'
```

This sample application leverages h2 as the DB. Below are the details to access the same post running the application. Credentials to access the URL are same as mentioned above.
Spring security secures url of h2-console as well. A new user & role can be defined to access h2 if needed in WebSecurityConfigurer.

```
http://localhost:8080/h2-console with url='jdbc:h2:mem:testdb;', user='sa', pw=''
```

[DatabaseInitializer](./src/main/java/com/upday/assignment/dao/DatabaseInitializer.java) class implements CommandLineRunner hence it's run method is executed post start of the application 
which is used to setup sample data in the application.

[ArticleResource](./src/main/java/com/upday/assignment/rest/ArticlesResource.java) exposes secured rest apis to create/update/delete article/s.

There are separate unit test cases to test rest, service and web layers.