public class Outcast {
    
    private WordNet wordnet;
    
    public Outcast(WordNet wordnet) {
        this.wordnet = wordnet;
    }
    
    public String outcast(String[] nouns) {
        int dist = 0, ind = 0, result = 0;
        for(int i = 0; i < nouns.length; i++) {
            dist = 0;
            for(int j = 0; j < nouns.length; j++) {
                if(j == i) continue;
                dist = dist + wordnet.distance(nouns[j], nouns[i]);
            }
            if(result < dist) { result = dist; ind = i;}
        }
        return nouns[ind];
    }
    
    public static void main(String[] args) {
        WordNet wordnet = new WordNet(args[0], args[1]);
        Outcast outcast = new Outcast(wordnet);
        for (int t = 2; t < args.length; t++) {
            In in = new In(args[t]);
            String[] nouns = in.readAllStrings();
            StdOut.println(args[t] + ": " + outcast.outcast(nouns));
        }
    }
    
}