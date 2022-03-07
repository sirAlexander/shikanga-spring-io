### build & run local docker instance of prometheus
```shell
docker build -f Dockerfile.prometheus -t local-prometheus .

docker run -d -p 9090:9090 --name local-prometheus local-prometheus
```

Since we run Prometheus from inside Docker we need to enter the host-ip

### run lightweight docker instance of grafana
```shell
docker run -d --name=local-grafana -p 3000:3000 grafana/grafana-enterprise
```