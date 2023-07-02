#!/bin/sh

./gradlew buildFatJar
docker-compose -f src/docker/docker-compose.yml up -d
java -jar build/libs/fr.betclic.players-api-all.jar
