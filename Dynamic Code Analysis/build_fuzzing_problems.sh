#!/bin/sh
if [ ! -f /tmp/rers2020.zip ]; then
    curl http://rers-challenge.org/2020/problems/sequential/SeqLtlRers2020.zip -o /tmp/rers2020.zip
fi

unzip -n /tmp/rers2020.zip -d ./rers2020_problems/
mkdir -p -v instrumented/

mvn clean package

for PROBLEM_FILE in $(find ./rers2020_problems/*/*.java )
do
	echo ${PROBLEM_FILE}
    java -cp target/aistr.jar nl.tudelft.instrumentation.Main --type=fuzzing --file=${PROBLEM_FILE} > instrumented/$(basename ${PROBLEM_FILE})
    javac -cp target/aistr.jar:. instrumented/$(basename ${PROBLEM_FILE})
done