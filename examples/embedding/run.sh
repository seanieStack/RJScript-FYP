#!/bin/bash
set -e

JAR="${1:?Usage: ./run.sh <rjscript.jar> [rules.rjs]}"
RULES="${2:-}"

mkdir -p out
javac -cp "$JAR" *.java -d out

echo ""
java -cp "$JAR:out" SensorMonitor ${RULES:+"$RULES"}