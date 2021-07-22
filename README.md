# 
User service coded with Quarkus, using the Panache extension and connecting to a mongoDB database.
# User Service Mongodb
User service that interact with the user schema for the coffeeshop. It interact with a MongoDB database.

#### :warning: Warning
The main branch contains only this README file. This repository is used as a placeholder for live coding sessions with [Quarkus](https://quarkus.io/).

#### HOW-TO

###### Prerequisites
* Your favorite IDE
* JDK 8 or 11+ 
* Maven 3.6.2+
* Local instance of mongoBD
  * [macOS community edition home brew install](https://github.com/mongodb/homebrew-brew)

###### Build Step

:exclamation:*Don't clone the project, generating the quarkus project will bootstrap your project.*

Generate the quarkus project.
```
mvn io.quarkus:quarkus-maven-plugin:2.0.2.Final:create \
    -DprojectGroupId=com.thecat \
    -DprojectArtifactId=user-service-mongodb \
    -DclassName="com.thecat.user.endpoint.UserResource" \
    -Dpath="/users" \
    -Dextensions="resteasy,resteasy-jackson"
```

Add the required extensions for mongo-db panache
```
./mvnw quarkus:add-extension -Dextensions="mongodb-panache"
```

Add the required extension for Kubernetes config if needed
```
./mvnw quarkus:add-extension -Dextensions=“Kubernetes-config"
```

:exclamation:*Steps to create a feature branch on this repository.*

1. Track changes with git 
```
git init -b <feature_branch_name>
```

2. Add the remote repository to the newly generated project
```
git remote add origin https://github.com/froberge/user-service-mongodb.git
```
4. Push changes to the remote repository
```
git push -u origin <feature_branch_name>
```