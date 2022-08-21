# Trashify!
## About
![alt text](https://github.com/moksha51/ADProjTeam6Android/blob/master/app/src/main/res/drawable/logo2.png)
Trashify is an app that aims to make recycling easier (more accessible and more fun)

## Features of the Trashify Web App
1. Allows sign-in to track your recycling journey
2. Taking pictures of trash for classification with built-in Machine Learning support
3. Uploading of images to classify trash with built-in Machine Learning support
4. Find out locations of recycling bins
5. Track your weekly recycling statistics 
6. Get rewarded for accumulating enough recyclables
7. In-sync, and cross-compatible with accompanying web app

## Screenshot
The web app is a Spring MVC project with baked-in Machine-Learning support that is deployed as a docker container in the cloud. It connects to a seperate mySQL container in the same cloud environment for database support. The seperation provides database integrity in case the web app container goes down. The REST endpoints provided within the web app are also opened for the android app to connect to so userdata is consistent between the android app and web app. Dockerfile and docker-compose file for the webapp are also provided. 

## Permissions
On Android versions prior to Android 6.0, wallabag requires the following permissions:
- Full Network Access.
- View Network Connections.
- Run at startup.
- Read and write access to external storage.

The "Run at startup" permission is only used if Auto-Sync feature is enabled and is not utilised otherwise. The network access permissions are made use of for downloading content. The external storage permission is used to cache article images for viewing offline.
