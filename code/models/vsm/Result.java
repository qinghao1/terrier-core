public class Result implements Comparable{

    private String docId;
    private double score;

    public Result(String docId, double score){
        this.docId = docId;
        this.score = score;
    }


    public double getScore() {
        return score;
    }

    public String getDocId() {
        return docId;
    }

    @Override
    public int compareTo(Object o) {
        Result re = (Result) o;
        return - Double.compare(score, re.getScore());
    }

    @Override
    public String toString(){
        return docId + " " + score;
    }

}
