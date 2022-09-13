#!/bin/sh
mvn clean package
mkdir -p instrumented/
java -cp target/aistr.jar nl.tudelft.instrumentation.Main --type=symbolic --file=Problem1.java > instrumented/Problem1.java
javac -cp target/aistr.jar:lib/com.microsoft.z3.jar:. instrumented/Problem1.java