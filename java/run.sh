#! /bin/bash

cd $1

export CLASSPATH=target/classes:\
$HOME/.m2/repository/ch/qos/logback/logback-classic/1.5.12/logback-classic-1.5.12.jar:\
$HOME/.m2/repository/ch/qos/logback/logback-core/1.5.12/logback-core-1.5.12.jar:\
$HOME/.m2/repository/org/slf4j/slf4j-api/2.0.15/slf4j-api-2.0.15.jar

exec java --enable-preview -Xlog:gc+heap+exit \
com.leynmaster.advent.aoc$1.day$2.Day$2
