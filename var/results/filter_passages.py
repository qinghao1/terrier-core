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
			docid = col[2].split('-')[0]
			queries[col[0]] = docid
		
		
for file in os.listdir(file_loc):
	if file.endswith(".res") and not file.endswith("_f.res"):
		new_col = []
		with open(file) as csv_file:
			reader = csv.reader(csv_file, delimiter=' ')
			for col in reader:
				if col[0] in queries.keys():
					if col[2].split('-')[0] == queries[col[0]]:
						new_col.append(col)
						
		new_name = file.split('.res')[0] + "_f.res"
		csv2 = open(new_name, "w") 
		for lines in new_col:
			filtered = " ".join(lines)
			filtered = filtered + "\n"
			csv2.write(filtered)

