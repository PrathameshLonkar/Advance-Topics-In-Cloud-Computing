#!/bin/bash

# test the hadoop cluster by running wordcount

# create input files
mkdir input

# create input directory on HDFS
hadoop fs -mkdir -p input

# put input files to HDFS
hdfs dfs -put ./input/* input

# run wordcount
hadoop jar wc.jar operation input output

# print the input files

echo -e "\ninput operations.txt:"
hdfs dfs -cat input/operations.txt

# print the output of wordcount
echo -e "\nOperations Output"
hdfs dfs -cat output/part-r-00000