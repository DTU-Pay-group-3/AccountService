#!/bin/bash
set -e

pushd messaging-utilities-3.3
mvn clean
popd 

pushd account-service
mvn clean
popd 

pushd account-service-facade
mvn clean
popd 
