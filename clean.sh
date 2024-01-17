#!/bin/bash
set -e

pushd messaging-utilities-3.3
mvn clean
popd 

pushd student-id-service 
mvn clean
popd 

pushd student-registration-service
mvn clean
popd 
