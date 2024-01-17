# Message Queue Example

The project consists of 4 projects

- A Maven library for some utilities providing an abstraction to accessing the message queue in `messaging-utilities-3.4` which are installled using `mvn install` through the build script
- The student registration microservice in `student-registration-service` which calls the service in the student id microservice. The service offers a REST interface that is used by the end-to-end tests. The service used a message queue to talk to the `student-id-service`.
- The student id microservice in `student-id-service`, which communicates with the `student-registration-service` using a message queue. 
- The end-to-end tests in `end-to-end-queue-demo`

The main `docker-compose.yml` file is in the `end-to-end-queue-demo`.

To know how the project is build, deployed, and tested, inspect `build_and_run.sh`.

Note that the end-to-end tests may get stuck. The reason is, that one of the services has not finished starting yet, or has crashed because RabbitMq was not yet ready to accept connections. To make sure that all services are running, you can run `docker-compose up -d` repeatedly in the `end-to-end-queue-demo` directory until all services show that they are running.