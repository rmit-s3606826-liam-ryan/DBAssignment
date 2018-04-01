#!/bin/bash
clear

echo "Preparing data."

echo "Removing \'BUSINESS NAMES\'."
cp BUSINESS_NAMES_201803.csv a.csv
sed -i '1d' a.csv
sed 's/BUSINESS NAMES\t//' < a.csv > b.csv

echo "Replacing BN_STATES with states_id."
sed 's/\tVIC\t/\t1\t/g' < b.csv > a.csv
sed 's/\tNSW\t/\t2\t/g' < a.csv > b.csv
sed 's/\tQLD\t/\t3\t/g' < b.csv > a.csv
sed 's/\tACT\t/\t4\t/g' < a.csv > b.csv
sed 's/\tNT\t/\t5\t/g' < b.csv > a.csv
sed 's/\tSA\t/\t6\t/g' < a.csv > b.csv
sed 's/\tWA\t/\t7\t/g' < b.csv > a.csv
sed 's/\tTAS\t/\t8\t/g' < a.csv > b.csv

echo "Replacing BN_STATUS wit status_id."
sed 's/\tRegistered\t/\t1\t/g' < b.csv > a.csv
sed 's/\tDeregistered\t/\t2\t/g' < a.csv > b.csv

echo "Adding id column."
awk '{printf "%d\t%s\n", NR, $0}' < b.csv > a.csv

