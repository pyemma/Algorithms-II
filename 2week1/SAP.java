import java.util.*;

public class SAP {
    
    private Digraph G;
    private BreadthFirstDirectedPaths bf1, bf2;
    
    
    public SAP(Digraph G) {
        this.G = new Digraph(G);
    }
    
    private void validate(int v, int w) {
        if(v < 0 || v >= G.V() || w < 0 || w >= G.V()) 
            throw new IndexOutOfBoundsException("Wrong Parameters!");
    }
    
    private void validate(Iterable<Integer> v, Iterable<Integer> w) {
        for(int i : v) {
            if(i < 0 || i >= G.V()) throw new IndexOutOfBoundsException();
        }
        for(int i : w) {
            if(i < 0 || i >= G.V()) throw new IndexOutOfBoundsException();
        }
    }
    
    public int length(int v, int w) {
        validate(v, w);
        bf1 = new BreadthFirstDirectedPaths(G, v);
        bf2 = new BreadthFirstDirectedPaths(G, w);
        int result = Integer.MAX_VALUE;
        for(int i = 0; i < G.V(); i++) {
            if(bf1.hasPathTo(i) && bf2.hasPathTo(i) && bf1.distTo(i) + bf2.distTo(i) < result) {
                result = bf1.distTo(i) + bf2.distTo(i);
            }
        }
        if(result == Integer.MAX_VALUE) return -1;       
        return result;
    }
    
    public int ancestor(int v, int w) {
        bf1 = new BreadthFirstDirectedPaths(G, v);
        bf2 = new BreadthFirstDirectedPaths(G, w);
        int result = Integer.MAX_VALUE;
        int ator = -1;
        for(int i = 0; i < G.V(); i++) {
            if(bf1.hasPathTo(i) && bf2.hasPathTo(i) && bf1.distTo(i) + bf2.distTo(i) < result) {
                result = bf1.distTo(i) + bf2.distTo(i);
                ator = i;
            }
        }
        return ator;
    }
    
    public int length(Iterable<Integer> v, Iterable<Integer> w) {
        validate(v, w);
        bf1 = new BreadthFirstDirectedPaths(G, v);
        bf2 = new BreadthFirstDirectedPaths(G, w);
        int result = Integer.MAX_VALUE;
        for(int i = 0; i < G.V(); i++) {
            if(bf1.hasPathTo(i) && bf2.hasPathTo(i) && bf1.distTo(i) + bf2.distTo(i) < result) {
                result = bf1.distTo(i) + bf2.distTo(i);
            }
        }
        if(result == Integer.MAX_VALUE) return -1;       
        return result;
    }
    
    public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
        validate(v, w);
        bf1 = new BreadthFirstDirectedPaths(G, v);
        bf2 = new BreadthFirstDirectedPaths(G, w);
        int result = Integer.MAX_VALUE;
        int ator = -1;
        for(int i = 0; i < G.V(); i++) {
            if(bf1.hasPathTo(i) && bf2.hasPathTo(i) && bf1.distTo(i) + bf2.distTo(i) < result) {
                result = bf1.distTo(i) + bf2.distTo(i);
                ator = i;
            }
        }
        return ator;
    }
    
    public static void main(String[] args) {
        In in = new In(args[0]);
        Digraph G = new Digraph(in);
        SAP sap = new SAP(G);
        while (!StdIn.isEmpty()) {
            int v = StdIn.readInt();
            int w = StdIn.readInt();
            int length = sap.length(v, w);
            int ancestor = sap.ancestor(v, w);
            StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
        }
    }
    
}