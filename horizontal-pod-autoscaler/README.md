This application has three components:

- The Spring Boot application that renders the frontend
- ActiveMQ as a message broker
- The Spring Boot backend that processes transactions


#### running the application

- mvn clean install spring-boot:run

```
<dependency>
   <groupId>org.apache.activemq</groupId>
   <artifactId>activemq-kahadb-store</artifactId>
   <version>5.15.8</version>
   <scope>runtime</scope>
</dependency>
```

```
docker pull webcenter/activemq:5.14.3

docker run -p 61616:61616 -p 8161:8161 webcenter/activemq:5.14.3
```

- http://localhost:8161/admin

```shell
docker build -f docker/Dockerfile.arm64 -t horizontal-pod-autoscalar .
```

```shell
docker tag horizontal-pod-autoscalar siralexander/horizontal-pod-autoscalar-arm64:1.0.0
```

```shell
docker push siralexander/horizontal-pod-autoscalar-arm64:1.0.0
```

```shell
kubectl --kubeconfig ~/tools/pi-cluster/k3s/k3s.yaml create ns horizontal-pod-autoscaler
```
```shell
kubectl --kubeconfig ~/tools/pi-cluster/k3s/k3s.yaml apply -f k8s/
```

```shell
kubectl --kubeconfig ~/tools/pi-cluster/k3s/k3s.yaml get pods -l=app=queue --watch
```

Scaling manually to meet increasing demand