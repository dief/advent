#! /bin/bash

cd $1

export CLASSPATH=target/classes:\
../advent-utils/target/classes:\
$HOME/.m2/repository/ch/qos/logback/logback-classic/1.5.21/logback-classic-1.5.21.jar:\
$HOME/.m2/repository/ch/qos/logback/logback-core/1.5.21/logback-core-1.5.21.jar:\
$HOME/.m2/repository/org/slf4j/slf4j-api/2.0.17/slf4j-api-2.0.17.jar:\
$HOME/.m2/repository/commons-io/commons-io/2.21.0/commons-io-2.21.0.jar

exec java -Xlog:gc,gc+exit com.leynmaster.advent.aoc$1.day$2.Day$2
