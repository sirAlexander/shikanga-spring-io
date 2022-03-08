### build & run local docker instance of prometheus
```shell
docker build -f Dockerfile.prometheus -t local-prometheus .

docker run -d -p 9090:9090 --name local-prometheus local-prometheus
```

Since we run Prometheus from inside Docker we need to enter the host-ip i.e. `host.docker.internal`

### run lightweight docker instance of grafana
```shell
docker volume create grafana-storage

docker run -d \
--name=local-grafana \
-p 3000:3000 \
-v grafana-storage:/var/lib/grafana \
-e "GF_SMTP_ENABLED=true" \
-e "GF_SMTP_HOST=smtp.gmail.com:465" \
-e "GF_SMTP_USER=alexander.shikanga.tindi@gmail.com" \
-e "GF_SMTP_PASSWORD=<your-password>" \
grafana/grafana-enterprise
```