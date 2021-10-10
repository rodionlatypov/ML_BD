# ML Big Data: Home Assignment 1

**Файлы и код лежат в папке HA1**

Цены в файле **prices.txt**

Два скрипта (mapper.py и reducer.py), которые считают и среднее, и дисперсию

## Part 1

![Namenode](https://github.com/rodionlatypov/ML_BD/blob/HA1/HA1/screenshots/Namenode.png)

![Resoursemanager](https://github.com/rodionlatypov/ML_BD/blob/HA1/HA1/screenshots/Resourcemanager.png)

![Datanodes](https://github.com/rodionlatypov/ML_BD/blob/HA1/HA1/screenshots/Datanodes.png)

![Browse_directory](https://github.com/rodionlatypov/ML_BD/blob/HA1/HA1/screenshots/Browse_directory.png)

## Part 2

**Работа с флагами -mkdir и -touchz**

1. hdfs dfs -mkdir /test_folder
2. hdfs dfs -mkdir /test_folder/nested_tst_folder
3. Trash - папка с удаленными файлами. Использовать команду -skipTrash: hdfs dfs -rm -skipTrash <FILE>
4. hdfs dfs -touchz /test_folder/nested_tst_folder/empty_file
5. hdfs dfs -rm -skipTrash /test_folder/nested_tst_folder/empty_file
6. hdfs dfs -rmr -skipTrash /test_folder

**Работа с флагами -put, -cat, -tail, -cp**
   
1. hdfs dfs -mkdir /data_folder; hdfs dfs -put AB_NYC_2019.csv /data_folder/
2. hdfs dfs -cat /data_folder/AB_NYC_2019.csv
3. hdfs dfs -cat /data_folder/AB_NYC_2019.csv | tail
4. hdfs dfs -cat /data_folder/AB_NYC_2019.csv | head
5. hdfs dfs -cp /data_folder/AB_NYC_2019.csv /

**Работа с replication factor, fsck**
   
1. hdfs dfs -setrep -w 2 /data_folder/AB_NYC_2019.csv
   
   WARNING: the waiting time may be long for DECREASING the number of replications.
   
2. hdfs fsck / -files -blocks
   
   Последнее сообщение: The filesystem under path '/' is HEALTHY
   
3. hdfs fsck /data_folder -blockId blk_1073741835

   Block Id: blk_1073741835
   Block belongs to: /data_folder/AB_NYC_2019.csv
   No. of Expected Replica: 3
   No. of live Replica: 3
   No. of excess Replica: 0
   No. of stale Replica: 0
   No. of decommissioned Replica: 0
   No. of decommissioning Replica: 0
   No. of corrupted Replica: 0
   Block replica on datanode/rack: 24cc15ebea83/default-rack is HEALTHY
   Block replica on datanode/rack: 2921f95e3b07/default-rack is HEALTHY
   Block replica on datanode/rack: 219c861da2c0/default-rack is HEALTHY

   
## Part 3
   
Сравнение среднего и дисперсии по mapreduce и посчитанного с помощью numpy:
   
![Mean_variance](https://github.com/rodionlatypov/ML_BD/blob/HA1/HA1/screenshots/Mean_variance.png)
   
