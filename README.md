# Jave EE 8 - Proof of Concepts

I'm using this project to create proof of concepts in java ee 8.

## Includes

- Docker files to run Payara application server with a MySql database
- Embedded glassfish resources

## How to use

To start this application, run the following commands from the project root directory
- `$ ./gradlew.bat build`
- `$ docker-compose up`

If you're using IntelliJ this can be automated by doing the following
- Install the docker IDEA plugin
- Create a new run configuration for docker-compose
- Add a gradle task that runs 'build' as a 'before launch' task