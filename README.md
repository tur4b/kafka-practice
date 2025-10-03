# Spring Boot + Apache Kafka Practice

Bu layihədə **Apache Kafka**-nın **Spring Boot** ilə istifadəsinə nəzər yetirəcəyik. Layihə həm **producer**, həm də **consumer** funksiyasını yerinə yetirir.

---

## 1️⃣ Apache Kafka-nın qurulması

Kafka-nı ya sisteminizə **install** edə bilərsiniz, ya da **Docker** istifadə edərək çalışdıra bilərsiniz. Bu layihədə **Docker** istifadə olunacaq.

### Docker Compose

Aşağıdakı `docker-compose.yml` faylı **3 brokerli Kafka cluster** və Kafka UI konfiqurasiyasını göstərir:

```yaml
version: '3.9'

services:
  kafka1:
    image: apache/kafka:4.0.0
    container_name: kafka1-practice
    user: root
    ports:
      - "19092:19092"
    environment:
      CLUSTER_ID: "kraft-cluster-001"
      KAFKA_NODE_ID: 1
      KAFKA_PROCESS_ROLES: broker,controller
      KAFKA_LISTENERS: INTERNAL://0.0.0.0:9092,EXTERNAL://0.0.0.0:19092,CONTROLLER://0.0.0.0:9093
      KAFKA_ADVERTISED_LISTENERS: INTERNAL://kafka1:9092,EXTERNAL://localhost:19092
      KAFKA_LISTENER_SECURITY_PROTOCOL_MAP: INTERNAL:PLAINTEXT,EXTERNAL:PLAINTEXT,CONTROLLER:PLAINTEXT
      KAFKA_CONTROLLER_LISTENER_NAMES: CONTROLLER
      KAFKA_INTER_BROKER_LISTENER_NAME: INTERNAL
      KAFKA_CONTROLLER_QUORUM_VOTERS: 1@kafka1:9093,2@kafka2:9093,3@kafka3:9093
      KAFKA_LOG_DIRS: /kafka/data
    volumes:
      - kafka1-data-practice:/kafka/data

  kafka2:
    image: apache/kafka:4.0.0
    container_name: kafka2-practice
    user: root
    ports:
      - "29092:29092"
    environment:
      CLUSTER_ID: "kraft-cluster-001"
      KAFKA_NODE_ID: 2
      KAFKA_PROCESS_ROLES: broker,controller
      KAFKA_LISTENERS: INTERNAL://0.0.0.0:9092,EXTERNAL://0.0.0.0:29092,CONTROLLER://0.0.0.0:9093
      KAFKA_ADVERTISED_LISTENERS: INTERNAL://kafka2:9092,EXTERNAL://localhost:29092
      KAFKA_LISTENER_SECURITY_PROTOCOL_MAP: INTERNAL:PLAINTEXT,EXTERNAL:PLAINTEXT,CONTROLLER:PLAINTEXT
      KAFKA_CONTROLLER_LISTENER_NAMES: CONTROLLER
      KAFKA_INTER_BROKER_LISTENER_NAME: INTERNAL
      KAFKA_CONTROLLER_QUORUM_VOTERS: 1@kafka1:9093,2@kafka2:9093,3@kafka3:9093
      KAFKA_LOG_DIRS: /kafka/data
    volumes:
      - kafka2-data-practice:/kafka/data

  kafka3:
    image: apache/kafka:4.0.0
    container_name: kafka3-practice
    user: root
    ports:
      - "39092:39092"
    environment:
      CLUSTER_ID: "kraft-cluster-001"
      KAFKA_NODE_ID: 3
      KAFKA_PROCESS_ROLES: broker,controller
      KAFKA_LISTENERS: INTERNAL://0.0.0.0:9092,EXTERNAL://0.0.0.0:39092,CONTROLLER://0.0.0.0:9093
      KAFKA_ADVERTISED_LISTENERS: INTERNAL://kafka3:9092,EXTERNAL://localhost:39092
      KAFKA_LISTENER_SECURITY_PROTOCOL_MAP: INTERNAL:PLAINTEXT,EXTERNAL:PLAINTEXT,CONTROLLER:PLAINTEXT
      KAFKA_CONTROLLER_LISTENER_NAMES: CONTROLLER
      KAFKA_INTER_BROKER_LISTENER_NAME: INTERNAL
      KAFKA_CONTROLLER_QUORUM_VOTERS: 1@kafka1:9093,2@kafka2:9093,3@kafka3:9093
      KAFKA_LOG_DIRS: /kafka/data
    volumes:
      - kafka3-data-practice:/kafka/data

  kafka-ui:
    image: provectuslabs/kafka-ui:latest
    container_name: kafka-ui-practice
    ports:
      - "8087:8080"
    environment:
      KAFKA_CLUSTERS_0_NAME: local
      KAFKA_CLUSTERS_0_BOOTSTRAPSERVERS: kafka1:9092,kafka2:9092,kafka3:9092
    depends_on:
      - kafka1
      - kafka2
      - kafka3

volumes:
  kafka1-data-practice:
  kafka2-data-practice:
  kafka3-data-practice:
```

**Docker konteynerləri işə salmaq:**
```
docker-compose up -d
```

***http://localhost:8080/tests*** url-nə GET sorğusu ataraq ***tests*** topic-nə mesaj produce edə bilərsiz

Mediumda yerləşən məqalədən daha ətraflı məlumat əldə edə bilərsiz - [Məqalə üçün kliklə](https://docs.oracle.com/javase/8/docs/jre/api/net/httpserver/spec/com/sun/net/httpserver/HttpServer.html).
