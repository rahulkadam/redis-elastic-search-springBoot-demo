# redis-elastic-search-springBoot-demo
Project demo for Redis , elastic search and Spring boot with pivotal and cloudfoundry



This project has simple integration of redis:

This project exposed 2 rest api to set and get data from redis.

https://rediselasticdemo.cfapps.io/set/redis/cacheMemory
   This will set value cachememory to redis key.
   
https://rediselasticdemo.cfapps.io/get/redis

 : This will return cachememory
 
 Elastic Search:
   This work is in progress.
   We have exposed 3 API for elastic Search.
   1. Create Index
   2. Creeate mapping/document
   3. Search in that document
   
   
   
   Installation:
      We have done this on Pivotal.
      Create 2 service on Pivotal.
       1. redis
       2. Elastic Search
     Create Spring Boot app which will have dependency of this.
     you can do it using start.spring.io too.
     
     login to CF using CLI :
     cf login
     cf push appname -p target/jarFile.jar
     
  This work is in progress.. we will add some more things here to make it more efficiant and easy to up and running.
     

