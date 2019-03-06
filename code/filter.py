#!/usr/bin/env python3

# Usage: ./filter.py document_id < result.res > result_filtered.res

def has_prefix(prefix, str):
	return str[:len(prefix)] == prefix

import sys

doc_id = sys.argv[1]
for line in sys.stdin:
	full_id = line.split(' ')[2]
	if has_prefix(doc_id, full_id):
		sys.stdout.write(line)
