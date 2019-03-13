public class TFIDF {

    /**
     * Calculates original tf-idf score
     */
    public static double score(int tf, int n, int df){
        return tfScore(tf) * idfScore(n, df);
    }

    /**
     * Calculates original tf score
     * @param tf term frequency, the number of times that term t occurs in document d
     */
    public static double tfScore(int tf){
        return tf;
    }

    /**
     * Adapted version: calculates tf score
     * @param tf term frequency
     * @param docLength length of doc
     * @param avgLength average length of doc in the corpus
     * @return
     */
    public static double tfScore(int tf, int docLength, double avgLength){
        double b = 0.9;
        double g = 1;

        double tf_score = 1 + Math.log(1 + Math.log(tf / (1 - b + b * (docLength / avgLength) ) + g));
        return tf_score;
    }


    /**
     * Adapted version: calculate tf-idf weight
     */
    public static double score(int tf, int docLength, double avg, int n, int df){
        return TFIDF.tfScore(tf, docLength, avg) * TFIDF.idfScore(n, df);
    }

    /**
     * Calculates idf score
     * @param n the number of documents in the collection
     * @param df document frequency, the number of documents that the term occurs in
     * @return
     */
    public static double idfScore(int n, int df){
        double idf_score = Math.log10((n + 0.0)/df);
        return idf_score;
    }
}
