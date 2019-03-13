import java.util.HashMap;
import java.util.Map;
import java.util.Set;


public class DocVector {

    private String docId;
    private Map<String, Integer> tokens;

    public String getDocId() {
        return docId;
    }

    public void setDocId(String docId) {
        this.docId = docId;
    }

    public Map getTokens() {
        return tokens;
    }

    public void setTokens(Map<String, Integer> tokens) {
        this.tokens = tokens;
    }

    public void calculateTermFrequencies(Set<String> tokens){

        // calculate term frequency
        Map<String, Integer> map = new HashMap<String, Integer>();
        for(String token: tokens){
            if(map.containsKey(token))
                map.put(token, map.get(token) + 1);
            else
                map.put(token, 1);
        }

        setTokens(map);
    }


}
