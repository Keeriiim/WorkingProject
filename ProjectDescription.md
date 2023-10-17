# My Application - Starter Guide

## Description
This project is based on the assignment i have been given durning my education which is to create a Maven Java application where the user can enter data into a form (GUI or console).
This data is sent in JSON format to a localhost Web API. A Kafka Cluster with producers and consumers that stores the incoming data in a topic and saves it to a local database


## Table of Contents
- [Preinstallation](#Preinstallation)
- [Kafka](#installation-Kafka)
- [MySQL](#installation-MySQL)
- [Usage](#usage)
- [License](#license)
- [Contribute](#contribute)
- [Report](#report)

## Preinstallation
The project is built with Maven and the dependencies are listed in the pom.xml file.
Your IDE might ask for the SDK setup, if so then you should choose the latest working version.
This project was coded with java version 19.0.1.

## Installation-Kafka
This is a guide for Windows
- Press the link and download the latest Binary version of Apache Kafka https://kafka.apache.org/downloads
- Extract the folder to your desired location & rename it to kafka
- Open the kafka folder and go to config and open the server & zookeper file in a text editor 
- Change the log.dirs to your desired location - mine is at log.dirs=C:\kafka\kafka-logs
- Change the dataDir to your desired location  - mine is at dataDir=C:\kafka\zookeeper
- Your now done with the setup of kafka so follow the quick start guide to start the server https://kafka.apache.org/quickstart

## Installation-MySQL
This is a guide for Windows
- Press the link and download MySQL community version https://dev.mysql.com/downloads/installer/
- In the installation set up choose Full and press next
- Choose a login username and password and press next untill finished
- Open the workbench and create a new schema called "mydb"
- Open the config.properties file in the DynamicWebProject folder and change the username and password to the ones you chose during the installation
- Run the project and the database should be connected

## Usage
The main purpose for this project is to send messages to a kafka server and save them in a MySQL database via a spring boot application.

## License
Copyright (c) 2023 Kerim Kozo  
[MIT License](https://choosealicense.com/licenses/mit/).

## Contribute
Feel free to pull and expand on this project. Contributions are always welcome.
