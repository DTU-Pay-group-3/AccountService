#!/bin/bash
set -e
docker image prune -f
docker-compose up -d account-service-facade account-service

