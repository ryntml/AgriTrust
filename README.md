
# AgriTrust: Distributed Agricultural Supply Chain Transparency System

**Course:** CENG 465 - Distributed Computing
**Project Type:** RESTful Web Service (v1)

## Project Abstract
The agricultural supply chain in Türkiye currently operates as a "Black Box," creating a severe disconnect between the point of harvest and the point of sale. AgriTrust is a distributed system designed to ensure **"Value Chain Integrity"** by utilizing a **Distributed Event-Log Architecture**

Unlike centralized systems which act as single points of failure, AgriTrust implements a **Federated Node Architecture** with sharding to ensure high availability and resilience across different geographic regions.

## Key Features
* **Distributed Architecture:** Federated nodes simulating regional instances (e.g., Producer Node, Consumer Node).
* **Algorithmic Auditing:** A "Compliance Engine" that calculates "Value Chain Delta" to detect ethical markup violations in real-time.
* **High Availability:** PostgreSQL Master-Slave replication to prevent data loss.
* **Resilience:** RabbitMQ integration to buffer high-volume requests.
* **Containerization:** Fully Dockerized environment for consistent deployment.

## Tech Stack
* **Language/Framework:** Java, Spring Boot (REST API)
* **Database:** PostgreSQL (Master-Slave Replication) 
* **Message Broker:** RabbitMQ 
* **Containerization:** Docker & Docker Compose 
* **Security:** JWT Authentication & Role-Based Access Control 

## Team
* **Aleyna Benzer:** API Design, Core Logic, Auth
* **Reyyan Temel:** Database Design, Containerisation, Documentation 
