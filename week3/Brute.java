import java.util.Arrays;

public class Brute {
    public static void main(String[] args) {
        
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        StdDraw.show(0);
        
        String filename = args[0];
        In in = new In(filename);
        int N = in.readInt();
        Point[] p = new Point[N];
        for (int i = 0; i < N; i++) {
            int x = in.readInt();
            int y = in.readInt();
            p[i] = new Point(x, y);
            p[i].draw();
        }
        Arrays.sort(p, 0, N);
        for (int i = 0; i < N-3; i++){
            for (int j = i+1; j < N-2; j++){
                for(int k = j+1; k < N-1; k++){
                    for(int l = k+1; l < N; l++){
                        
                        if(p[j].slopeTo(p[i]) == p[k].slopeTo(p[i]) && p[j].slopeTo(p[i]) == p[l].slopeTo(p[i])){
                            
                            p[i].drawTo(p[l]);
                            System.out.println(p[i].toString() + " -> " + p[j].toString() + " -> " + p[k].toString() + " -> " + p[l].toString());
                        }
                    }
                }
            }
        }
        StdDraw.show(0);
    }
}