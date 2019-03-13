public class TFIDF {


    public static double score(int tf, int n, int df){
        return TFIDF.tfScore(tf) * TFIDF.idfScore(n, df);
    }

    /**
     *
     @param tf term frequency, the number of times that term t occurs in document d
     * @return
     */
    public static double tfScore(int tf){
//        double tf_score = 1 + Math.log10(tf);
//        return tf_score;
        return tf;
    }

    /**
     *
     * @param n the number of documents in the collection
     * @param df document frequency, the number of documents that the term occurs in
     * @return
     */
    public static double idfScore(int n, int df){
        double idf_score = Math.log10((n + 0.0)/df);
        return idf_score;
    }
}
