#this file should be in /var/results directory
#output files will be with _f.res, so remember to rename the output file to .res file (overwrite original) before evaluating
import os
import csv

file_loc = str(input('Enter exact current file location to proceed: ') )

queries = {}
with open('../../code/qrel/qrel_test.txt') as csv_file:
	reader = csv.reader(csv_file, delimiter=' ')
	for col in reader:
		if col[0] not in queries.keys():
			queries[col[0]] = [col[2]]
		else:
			queries[col[0]].append(col[2])

for file in os.listdir(file_loc):
	if file.endswith("_f.res"):
		recall_5 = []
		recall_10 = []
		recall_20 = []
		with open(file) as csv_file:
			reader = csv.reader(csv_file, delimiter=' ')
			rank = 0
			count = 0
			previous_q = " "
			for col in reader:
				if col[0] in queries.keys():
					if previous_q != col[0]:
						if rank != 0 and rank < 5 and previous_q is not " ":
							recall_5.append(count/len(queries[previous_q]))
						rank = 0
						previous_q = col[0]
						count = 0
				rank += 1
				if col[0] in queries.keys():
					#if any(col[2] in s for s in queries[col[0]]):
					if col[2] in queries[col[0]]:
						count += 1
					if rank == 5:
						recall_5.append(count/len(queries[col[0]]))
						print(str(count/len(queries[col[0]]))+"\n")
					if rank == 10:
						recall_10.append(count/len(queries[col[0]]))
						print(str(count/len(queries[col[0]]))+"\n")
					if rank == 20:
						recall_20.append(count/len(queries[col[0]]))
						print(str(count/len(queries[col[0]]))+"\n")
		
		total_5 = 0.0
		total_10 = 0.0
		total_20 = 0.0
		for value in recall_5:
			total_5 += value
		if len(recall_5) != 0:
			avg_recall_5 = total_5/len(recall_5)
		else:
			avg_recall_5 = 0
		for value in recall_10:
			total_10 += value
		if len(recall_10) != 0:
			avg_recall_10 = total_10/len(recall_10)
		else:
			avg_recall_10 = 0
		for value in recall_20:
			total_20 += value
		if len(recall_20) != 0:
			avg_recall_20 = total_20/len(recall_20)
		else:
			avg_recall_20 = 0
		print(str(len(recall_5))+"R5\n")
		print(str(len(recall_10))+"R10\n")
		print(str(len(recall_20))+"R20\n")
		recall_5.clear()
		recall_10.clear()
		recall_20.clear()
		new_name = file.split('.res')[0] + "_recall.txt"
		csv2 = open(new_name, "w") 
		row = "Recall@5" + " " + str(avg_recall_5) + "\n"
		csv2.write(row)
		row = "Recall@10" + " " + str(avg_recall_10) + "\n"
		csv2.write(row)
		row = "Recall@20" + " " + str(avg_recall_20) + "\n"
		csv2.write(row)

