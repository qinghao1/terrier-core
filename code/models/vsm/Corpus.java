import java.util.*;

public class Corpus {

    private ArrayList<DocVector> docList;
    private Map<String, HashMap<String, Integer>> collectionFrequency;
    private Map<String, Integer> docNum;
    private Set<String> bagOfWords;

    public Corpus(){
        docList = new ArrayList<DocVector>();
        collectionFrequency = new HashMap<String, HashMap<String, Integer>> ();
        docNum = new HashMap<String, Integer>();
        bagOfWords = new HashSet<String>();
    }

    public void addDocVector(DocVector vec){
        docList.add(vec);
    }

    public ArrayList<DocVector> getDocList() {
        return docList;
    }

    public Set<String> getBagOfWords() {
        return bagOfWords;
    }

    public void generateBagOfWords(String docId){
        bagOfWords.clear();

        bagOfWords = new HashSet<String>();

        for(DocVector doc: docList){
            if(doc.getDocId().startsWith(docId)) {
                Map<String, Integer> tokens = doc.getTokens();

                for(Map.Entry<String, Integer> entry: tokens.entrySet())
                    bagOfWords.add(entry.getKey());
            }
        }
    }

    public int getCollectionFrequency(String docId, String term){

        if(collectionFrequency.containsKey(docId) && collectionFrequency.get(docId).containsKey(term))
            return collectionFrequency.get(docId).get(term);

        // calculate collection frequency of term t
        int count = 0;
        for(DocVector doc: docList){
            if(doc.getDocId().startsWith(docId)) {
                Map<String, Integer> termFre = doc.getTokens();
                if (termFre.containsKey(term))
                    count++;
            }
        }

        if(!collectionFrequency.containsKey(docId))
            collectionFrequency.put(docId, new HashMap<String, Integer>());

        collectionFrequency.get(docId).put(term, count);
        return count;
    }

    public void setDocNum(Map<String, Integer> docNum) {
        this.docNum = docNum;
    }

    public int getDocNum(String docPrefix){
        if(docNum.containsKey(docPrefix))
            return docNum.get(docPrefix);
        else
            return 0;
    }


}
