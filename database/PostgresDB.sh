#!/usr/bin/env bash

docker run --rm \
--name test_postgres \
-e POSTGRES_PASSWORD=1234 \
-e POSTGRES_DB=TestDB \
-p 5432:5432 \
postgres:9.4.11