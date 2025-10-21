#!/bin/bash

echo "Building project"
./gradlew clean build

if [ $? -ne 0 ]; then
    echo "Build failed!"
    exit 1
fi

echo ""
echo "Build successful"
echo "Running project"
echo ""

java -jar build/libs/FYP-RJScript-0.0.1.jar "$@"