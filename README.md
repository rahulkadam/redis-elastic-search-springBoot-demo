# redis-elastic-search-springBoot-demo
Project demo for Redis , elastic search and Spring boot with pivotal and cloudfoundry



This project has simple integration of redis:

This project exposed 2 rest api to set and get data from redis.

https://redisdemo.cfapps.io/set/redis/cacheMemory
   This will set value cachememory to redis key.
   
https://redisdemo.cfapps.io/get/redis

 : This will return cachememory
 
 Elastic Search:
   This work is in progress.
   We have exposed 3 API for elastic Search.
   1. Create Index
   2. Create mapping/document
   3. Search in that document
           
Installation:
    We have done this on Pivotal.
    Create 2 service on Pivotal.
    1. redis.
    2. Elastic Search.
   Create Spring Boot app which will have dependency of this.
   you can do it using start.spring.io too.
     
     login to CF using CLI :
     cf login
     cf push appname -p target/jarFile.jar
     
  This work is in progress. we will add some more things here to make it more efficiant and easy to up and running.
     

Elastic Search:

Curl Request for creating Index : 
     curl -X POST \
       http://localhost:8888/article/createIndex \
       -H 'authorization: Basic cmthZGFtQHF1YWx5cy5jb206UXVhbHlzQDEyMzQ=' \
       -H 'cache-control: no-cache' \
       -H 'content-type: application/x-www-form-urlencoded' \
       -H 'postman-token: 9df4ca57-726f-ec9d-4d75-09b01642577e' \
       -d name=article

Curl Request For creating Document : 
    curl -X POST \
      http://localhost:8888/article/create \
      -H 'cache-control: no-cache' \
      -H 'content-type: application/json' \
      -H 'postman-token: 2f211670-6c7f-5000-e173-6b9e8a042528' \
      -d '{ "author" : "bb" , "content" : "bbb"}'
 
Request for fetch article from elastic Search : 
      https://redisdemo.cfapps.io/article/search/bb