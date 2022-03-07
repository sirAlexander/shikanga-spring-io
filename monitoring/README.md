### build & run local instance of prometheus
```shell
docker build -f Dockerfile.prometheus -t local-prometheus .

docker run -d -p 9090:9090 --name local-prometheus local-prometheus
```

