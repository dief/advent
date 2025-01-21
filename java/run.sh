#! /bin/bash

cd $1

export CLASSPATH=target/classes:\
$(mvn dependency:build-classpath | grep -v '^\[')

exec java --enable-preview -Xlog:gc+heap+exit \
com.leynmaster.advent.aoc$1.day$2.Day$2
