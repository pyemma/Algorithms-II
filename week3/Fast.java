import java.util.Arrays;

public class Fast {
    
    public static void main (String[] args) {
        
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
        Point[] tp = new Point[N];
        Point[] cp = new Point[N];
        for (int i = 0; i < N; i++){
            for(int j = 0;j < N; j++)
                tp[j] = p[j];
            Arrays.sort(tp, 0, N, p[i].SLOPE_ORDER);
            int now = 0;
            int next;
            int cnt = 0;
            for(next = 1; next < N; next++){
                if(tp[next].slopeTo(p[i]) == tp[now].slopeTo(p[i])){
                    cnt++; 
                }
                else {
                    if(cnt >= 2){
                        for(int j = 0; j <= cnt; j++)
                            cp[j] = tp[now++];
                        cp[cnt+1] = p[i];
                        Arrays.sort(cp, 0, cnt+2);
                        if(cp[0] == p[i]){
                            cp[0].drawTo(cp[cnt+1]);
                            for(int j = 0; j < cnt+2; j++){
                                System.out.print(cp[j].toString());
                                if(j == cnt+1){
                                    System.out.print('\n');
                                    continue;
                                }
                                System.out.print(" -> ");
                            }
                        }
                    }
                    now = next;
                    cnt = 0;
                }
                
                if(next == N - 1){
                    if(cnt >= 2){
                        for(int j = 0; j <= cnt; j++)
                            cp[j] = tp[now++];
                        cp[cnt+1] = p[i];
                        Arrays.sort(cp, 0, cnt+2);
                        if(cp[0] == p[i]){
                            cp[0].drawTo(cp[cnt+1]);
                            for(int j = 0; j < cnt+2; j++){
                                System.out.print(cp[j].toString());
                                if(j == cnt+1){
                                    System.out.print('\n');
                                    continue;
                                }
                                System.out.print(" -> ");
                            }
                        }
                    }
                    now = next;
                    cnt = 0;
                }
            }
            StdDraw.show(0);
                                   
    }
    }
}