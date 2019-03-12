How to get the results: 
[Note:] All .sh and no ending should be .bat when using windows

1). Adding corpus: (note that etc/collection.spec should be empty if you only want 1 corpus)
$bin/trec_setup.sh <absolute-path-to-collection-files> 

2). Indexing the corpus (note that var/index/ should be empty, delete all files in the directory before indexing)
$bin/terrier batchindexing

3). Check that index was done properly
$bin/terrier indexstats

4). Batch query the documents based on queries file and increase number of ranking saved to 10000 (which normally takes enough rankings)
$bin/terrier batchretrieval -D matching.retrieved_set_size=10000 -D trec.output.format.length=10000 -w <weight-model> -t <absolute-file-path-to-query-files>

5). After running all the models required following step4; Run the filter_passage.py script to filter results to documents
<open command prompt in var/results to run and follow instructions> $python ./filter_passage.py

6).Overwrite the existing .res file with the _f.res (rename this _f.res file to overwrite)

7). Batch evaluate the documents based on (relevance judgement) qrels file
$bin/terrier batchevaluate -q <abosulute-path-to-qrels-file>

8). Look at var/results directory to get the final results (.res, .eval)

9).(Optional) run batchevaluate with "-p" and run comparequery.py to see how each query performed [sorted]
