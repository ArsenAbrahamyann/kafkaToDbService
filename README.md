# Kafka to Database Service

This project provides a service for sending messages to Kafka and storing them in a database.

## Prerequisites

Before running this service, ensure you have the following:

- Docker installed on your machine
- Kafka and Zookeeper running using Docker Compose
- MySQL database running using Docker Compose
- Environment variables configured (refer to the `.env` files)

## Running the Service

1. Clone this repository to your local machine.
2. Navigate to the root directory of the project.
3. Run `docker-compose up` to start Kafka, Zookeeper, MySQL, and the service.
4. The service will be available at `http://localhost:8080`.

## Endpoints

### Send Message to Kafka

- **URL:** `/api/messages`
- **Method:** POST
- **Request Body:** JSON containing the message
- **Response:** Response indicating success or failure

### Get Messages from Database

- **URL:** `/api/messages`
- **Method:** GET
- **Response:** JSON array containing messages stored in the database

## Dependencies

- Spring Boot
- Kafka
- MySQL
- Lombok
- Jackson

## Authors

- Arsen Abrahamyan