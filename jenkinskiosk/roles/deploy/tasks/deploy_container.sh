#!/bin/bash
docker stop jenkinsmanager || true && docker rm jenkinsmanager || true
docker rmi rupeshkumar/images:jenkinsmanager || true
docker login --username=rupeshkumar --password=goldu123
docker pull rupeshkumar/images:jenkinsmanager
docker run -d --name jenkinsmanager -p 9090:8080 rupeshkumar/images:jenkinsmanager
exit 0
