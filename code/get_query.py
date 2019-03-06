import csv

queries = {}

with open('WikiPassageQA/dev.tsv') as csv_file:
	reader = csv.reader(csv_file, delimiter='\t')
	next(reader)
	for col in reader:
		queries[col[0]] = col[1]
		
csv2 = open("query_dev.trec", "w") 
for key in queries:
	csv2.write("<top>\n")
	row = "<num>" + str(key) + "</num>" + "<title>\n"
	csv2.write(row)
	row2 = queries[key] + "\n"
	csv2.write(row2)
	csv2.write("</title>\n")
	csv2.write("</top>\n")

queries2 = {}

with open('WikiPassageQA/train.tsv') as csv_file:
	reader = csv.reader(csv_file, delimiter='\t')
	next(reader)
	for col in reader:
		queries2[col[0]] = col[1]
		
csv3 = open("query_train.trec", "w") 
for key in queries2:
	csv3.write("<top>\n")
	row = "<num>" + str(key) + "</num>" + "<title>\n"
	csv3.write(row)
	row2 = queries2[key] + "\n"
	csv3.write(row2)
	csv3.write("</title>\n")
	csv3.write("</top>\n")

queries3 = {}

with open('WikiPassageQA/test.tsv') as csv_file:
	reader = csv.reader(csv_file, delimiter='\t')
	next(reader)
	for col in reader:
		queries3[col[0]] = col[1]
csv4 = open("query_test.trec", "w") 
for key in queries3:
	csv4.write("<top>\n")
	row = "<num>" + str(key) + "</num>" + "<title>\n"
	csv4.write(row)
	row2 = queries3[key] + "\n"
	csv4.write(row2)
	csv4.write("</title>\n")
	csv4.write("</top>\n")