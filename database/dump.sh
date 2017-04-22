#!/usr/bin/env bash

psql -h localhost -p 5432 -U postgres -d TestDB -f ./dump.sql