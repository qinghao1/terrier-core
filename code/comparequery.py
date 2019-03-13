#this file should be in /var/results directory
#this file should be run after "batchevaluate -p", which evaluates per query
#this file compares the scores for each query and ranks them
#output files will be with _c.eval
import os
import csv
import operator

file_loc = str(input('Enter exact current file location to proceed: ') )

for file in os.listdir(file_loc):
	if file.endswith(".eval") and not file.endswith("_c.eval"):
		compared_ap = {}
		compared_rr = {}
		with open(file) as csv_file:
			reader = csv.reader(csv_file, delimiter='\t')
			for col in reader:
				if "map" in col[0]:
					if col[1] not in compared_ap.keys() and col[1] is not "all":
						compared_ap[col[1]] = col[2]
				if "recip_rank" in col[0]:
					if col[1] not in compared_rr.keys() and col[1] is not "all":
						compared_rr[col[1]] = col[2]
		
		sorted_ap = sorted(compared_ap.items(), key=operator.itemgetter(1), reverse=True)
		sorted_rr = sorted(compared_rr.items(), key=operator.itemgetter(1), reverse=True)
						
		new_name = file.split('.eval')[0] + "_c.eval"
		csv2 = open(new_name, "w") 
		for qnum, value in sorted_ap:
			row = "map" + " " + str(qnum) +" "+ str(value) + "\n"
			csv2.write(row)
		for qnum, value in sorted_rr:
			row = "recip_rank" + " " + str(qnum) +" "+ str(value) + "\n"
			csv2.write(row)
