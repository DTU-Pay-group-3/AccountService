#!/bin/bash
set -e

./build.sh

# Update the set of services and
# build and execute the system tests
./deploy.sh

# Cleanup the build images
docker image prune -f

