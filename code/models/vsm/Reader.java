import javax.print.Doc;
import java.io.*;
import java.util.*;

public class Reader {

    private Corpus corpus;
    private ArrayList<String> stopwords;

    public Reader(){
        corpus = new Corpus();
        stopwords = new ArrayList<String>();
        readStopWords();
    }

    private void readStopWords(){
        BufferedReader reader;

        try{
            reader = new BufferedReader(new FileReader("F:\\first year\\IN4325 Information Retrieval" +
                    "\\code\\english"));

            String str = reader.readLine();
            while(str != null){
                // remove punctuation mark in the stop word consistent with the text
                str = str.replaceAll("\\p{Punct}", "");
                stopwords.add(str);
                str = reader.readLine();
            }

        }catch(IOException e){
            e.printStackTrace();
        }

    }

    public void readTrec(){
        BufferedReader reader;
        try{
            reader = new BufferedReader(new FileReader("F:\\first year\\" +
                    "IN4325 Information Retrieval\\code\\TREC_passages.txt"));

            String str = reader.readLine();
            Map<String, Integer> docNum = new HashMap<String, Integer>();

            while(str != null){

                if(str.equals("<DOC>")){
                    DocVector doc = new DocVector();

                    String docno = reader.readLine();
                    doc.setDocId(docno.replaceAll("\\<[^>]*>",""));
                    String prefix = doc.getDocId().split("-")[0];
                    if(docNum.containsKey(prefix))
                        docNum.put(prefix, docNum.get(prefix) + 1);
                    else
                        docNum.put(prefix, 1);

                    reader.readLine();
                    String text = reader.readLine();
//                    System.out.println(text);
                    doc.calculateTermFrequencies(tokenization(text));

                    corpus.addDocVector(doc);
                }

                str = reader.readLine();
            }

            corpus.setDocNum(docNum);

        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public ArrayList<Result> retrieval(Question question){

        Set<String> tokensInQn = question.getTokens();

        // create vector for query
        corpus.generateBagOfWords(question.getQueriedDocID());
        ArrayList<Double> vecQn = new ArrayList<Double>();
        for(String term: corpus.getBagOfWords()){
            if(tokensInQn.contains(term))
                vecQn.add(TFIDF.idfScore(corpus.getDocNum(question.getQueriedDocID()),
                        corpus.getCollectionFrequency(question.getQueriedDocID(), term)));
            else
                vecQn.add(0.0);
        }
        System.out.println("Query vector" + vecQn);

        // retrieve documents by filtering prefix on id
        String prefix = question.getQueriedDocID();
        ArrayList<DocVector> docList = corpus.getDocList();
        ArrayList<Result> result = new ArrayList<Result>();

        for(DocVector doc: docList){

            if(doc.getDocId().startsWith(prefix)){

                // create vector for doc
                Map<String, Integer> tokensInDoc = doc.getTokens();
                ArrayList<Double> vecDoc = new ArrayList<Double>();

                for(String term: corpus.getBagOfWords()){
                    if(tokensInDoc.containsKey(term))
                        vecDoc.add(TFIDF.score(tokensInDoc.get(term), corpus.getDocNum(question.getQueriedDocID()),
                                corpus.getCollectionFrequency(question.getQueriedDocID(), term)));
                    else
                        vecDoc.add(TFIDF.score(0, corpus.getDocNum(question.getQueriedDocID()),
                                corpus.getCollectionFrequency(question.getQueriedDocID(), term)));
                }

                System.out.println("Doc Vector:" + vecDoc);
                double cosSimilarity = VectorMath.cosSimilarity(vecDoc, vecQn);
                System.out.println(doc.getDocId() + " has a score " + cosSimilarity);
                result.add(new Result(doc.getDocId(), cosSimilarity));
            }
        }

        return result;
    }

    public void readQuestion(){
        BufferedReader reader;

        try{
            reader = new BufferedReader(new FileReader("F:\\first year\\IN4325 Information Retrieval" +
                    "\\code\\WikiPassageQA\\test.tsv"));

            // skip header
            reader.readLine();

            String str = reader.readLine();
            while(str != null){

                String[] token = str.split("\t");

                Question question = new Question();
                question.setqID(token[0]);
                question.setQueriedDocID(token[2]);
                // tokenize query text
                Set<String> tokensInQn = tokenization(token[1]);
                question.setTokens(tokensInQn);

                ArrayList<Result> result = retrieval(question);
                Collections.sort(result);
                System.out.printf("list:%s\n", result);

                writeRanking(question.getqID(), result);

                str = reader.readLine();
            }
        }catch (IOException e){
            e.printStackTrace();
        }

    }


    private Set<String> tokenization(String text){

        // remove punctuations
        text = text.replaceAll("\\p{Punct}", "");

        // change to lower case
        text = text.toLowerCase();

        // split the text by space
        String[] tokens = text.split(" ");

        // remove stop words
        Set<String> wordBag = new HashSet<String>();
        for(int i = 0; i < tokens.length; i ++){
            if(!stopwords.contains(tokens[i]))
                wordBag.add(tokens[i]);
        }

        return wordBag;
    }

    public void writeRanking(String qID, ArrayList<Result> result){
        try{
            BufferedWriter out = new BufferedWriter(new FileWriter("vectorSpace.res", true));

            int i = 1;
            for(Result re: result){
                out.write(qID + " Q0 " + re.getDocId() + " " + i + " " + re.getScore() + " VectorSpace\n");
                i ++;
            }
//            for(int i = 0; i < result.size(); i ++){
//                Result re = result.get(i);
//                out.write(qID + " Q0 " + re.getDocId() + " " + (i+1) + " " + re.getScore() + " VectorSpace\n");
//            }
            out.close();
        }catch (IOException e){
            e.printStackTrace();
        }

    }

    public static void main(String[] args){

        Reader test = new Reader();
        test.readTrec();
        test.readQuestion();


    }
}
