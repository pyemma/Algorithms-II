import java.util.*;

public class WordNet {
    
    //private HashSet<String> noun = new HashSet<String>();
    //private HashMap<Integer, String[]> map = new HashMap<Integer, String[]>();
    private HashMap<String, ArrayList<Integer>> map = new HashMap<String, ArrayList<Integer>>();
    private ArrayList<String> noun = new ArrayList<String>();
    private Digraph G;
    private SAP sa;
    
    public WordNet(String synsets, String hypernyms) {
        In syn = new In(synsets);
        In hyp = new In(hypernyms);
        String str = " ";
        String[] domain;
        String[] nountmp;
        int id = 0;
        while(!syn.isEmpty()) {
            str = syn.readLine();
            domain = str.split("\\,");
            id = Integer.parseInt(domain[0]);
            nountmp = domain[1].split("\\ ");
            for(String st : nountmp) {
                if(map.containsKey(st) == true) map.get(st).add(id);
                else {
                    ArrayList<Integer> ar = new ArrayList<Integer>();
                    ar.add(id);
                    map.put(st, ar);
                }
            }
            noun.add(domain[1]);
        }
        G = new Digraph(id+1);
        while(!hyp.isEmpty()) {
            str = hyp.readLine();
            domain = str.split("\\,");
            for(int i = 1; i < domain.length; i++) 
                G.addEdge(Integer.parseInt(domain[0]), Integer.parseInt(domain[i]));
        }
        if(checkDAG() == false) throw new IllegalArgumentException();
        sa = new SAP(G);
    }
    
    public Iterable<String> nouns() {
        Set<String> set = map.keySet();
        return set;
    }
    
    public boolean isNoun(String word) {
        if(map.containsKey(word)) return true;
        return false;
    }
    
    public int distance(String nounA, String nounB) {
        if(!isNoun(nounA)) throw new IllegalArgumentException();
        if(!isNoun(nounB)) throw new IllegalArgumentException();
        ArrayList<Integer> idA = map.get(nounA);
        ArrayList<Integer> idB = map.get(nounB);
        int dist = sa.length(idA, idB);
        return dist;
    }
    
    public String sap(String nounA, String nounB) {
        if(!isNoun(nounA)) throw new IllegalArgumentException();
        if(!isNoun(nounB)) throw new IllegalArgumentException();
        ArrayList<Integer> idA = map.get(nounA);
        ArrayList<Integer> idB = map.get(nounB);
        int ancestor = sa.ancestor(idA, idB);
        return noun.get(ancestor);
    }
    
    private boolean checkDAG() {
        int edge = 0, cnt = 0;
        for(int i = 0; i < G.V(); i++) {
            edge = 0;
            for(int j : G.adj(i)) edge++;
            //StdOut.println(edge);
            if(edge == 0) cnt++;
        }
        DirectedCycle dc = new DirectedCycle(G);
        if(cnt == 1 && dc.hasCycle() == false) return true;
        else return false;
    }
    
    public static void main(String[] args) {
        WordNet wordnet = new WordNet(args[0], args[1]);
        //StdOut.println(wordnet.sap("stick_figure", "mountain_viscacha"));
        //StdOut.println(wordnet.distance("hyaline", "abstract_entity"));
        //StdOut.println(wordnet.distance("debut", "abstract_entity"));
    }
    
}