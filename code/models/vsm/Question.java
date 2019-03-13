import java.util.Set;

public class Question {

    private String qID;
    private Set<String> tokens;
    private String queriedDocID;

    public void setqID(String qID) {
        this.qID = qID;
    }

    public String getqID() {
        return qID;
    }

    public String getQueriedDocID() {
        return queriedDocID;
    }

    public void setQueriedDocID(String queriedDocID) {
        this.queriedDocID = queriedDocID;
    }

    public Set<String> getTokens() {
        return tokens;
    }

    public void setTokens(Set<String> tokens) {
        this.tokens = tokens;
    }


}
