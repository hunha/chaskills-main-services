# realife-services

The main services for Realife, an application for lifelong learner.

## Maven plugin DB-Migrator

* Create a new migration file:
```mvn db-migrator:new -Dname=create_people_table```
* Run migrate:
```mvn db-migrator:migrate```

find out more at [http://javalite.io/database_migrations](http://javalite.io/database_migrations)

## Deploy

* Default run:
```$ java -jar demo-0.0.1-SNAPSHOT.jar```
* Run with fixed RAM:
```$ java -jar -Xmx256m demo-0.0.1-SNAPSHOT.jar```
* Run on Staging:
```$ java -jar demo-0.0.1-SNAPSHOT.jar spring.profiles.active=staging```
* Run on Production:
```$ java -jar demo-0.0.1-SNAPSHOT.jar spring.profiles.active=production```
