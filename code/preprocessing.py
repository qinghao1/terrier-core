import json
import pandas as pd
import string


# reformat document file into TREC format
def reformat_documents(docs):
    f = open('TREC_passages.txt', 'w')
    for doc_id in range(len(docs)):
        for passage_id in docs[str(doc_id)].keys():
            f.write('<DOC>' + '\n')
            f.write('<DOCNO>' + str(doc_id) + '-' + str(passage_id) + '</DOCNO>\n')
            f.write('<TEXT>\n' + docs[str(doc_id)][str(passage_id)] + '\n</TEXT>\n')
            f.write('</DOC>\n')

    f.close()


# Remove irrelevant terms in question text
def preprocess_question(question):
    # print(question)

    # remove punctuation
    # todo: 's or / replaced with space or removed
    for c in string.punctuation:
        question = question.replace(c, "")

    # convert text to lowercase
    question = question.lower()

    # remove stop words
    f = open('english', 'r')
    stop_words = set(f.read().split('\n'))
    question = [w for w in question.split() if not w in stop_words]

    return ' '.join(question)


def write_qrels(data, filename):
    f = open(filename, 'w')
    for i in range(len(data)):
        for passage_id in data['RelevantPassages'][i].split(','):
            f.write(str(data['QID'][i]) + ' ' + '0' + ' ' + str(data['DocumentID'][i]) + '-' + str(passage_id) + ' 1\n')

    f.close()


docs = json.load(open('WikiPassageQA/document_passages.json'))
reformat_documents(docs)

dev = pd.read_csv('WikiPassageQA/dev.tsv', sep='\t')
train = pd.read_csv('WikiPassageQA/train.tsv', sep='\t')
test = pd.read_csv('WikiPassageQA/test.tsv', sep='\t')

write_qrels(dev, 'qrel_dev.txt')
write_qrels(train, 'qrel_train.txt')
write_qrels(test, 'qrel_test.txt')
