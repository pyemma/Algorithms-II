import java.util.*;
import java.awt.Color;

public class SeamCarver {
    
    private int[][] colormap;
    //private double[][] energymap;
    private int w, h;
    private boolean horizontal = false;
    private double[][] dp;
    private int[][] edgeTo;
    public SeamCarver(Picture picture) {
        w = picture.width();
        h = picture.height();
        int ma = w < h ? h : w;
        colormap = new int[ma][ma];
        //energymap = new double[ma][ma];
        dp = new double[2][ma];
        edgeTo = new int[ma][ma];
        Color color;
        int r, g, b;
        for(int i = 0; i < h; i++) {
            for(int j = 0; j < w; j++) {
                color = picture.get(j, i);
                r = color.getRed();
                g = color.getGreen();
                b = color.getBlue();
                colormap[i][j] = (r<<16) + (g<<8) +b;
                //energymap[i][j] = 0;
            }
        }
        //energyMapCalc();
    }
    
    private void energyMapCalc() {
        for(int i = 0; i < h; i++) {
            for(int j = 0; j < w; j++) {
                //energymap[i][j] = energyPixel(i, j);
            }
        }
    }
    
    private double energyPixel(int x, int y) {
        if(x == 0 || x == h-1 || y == 0 || y == w-1) return 195075;
        //int yr = colormap[x][y+1][0] - colormap[x][y-1][0];
        //int yg = colormap[x][y+1][1] - colormap[x][y-1][1];
        //int yb = colormap[x][y+1][2] - colormap[x][y-1][2];
        //int xr = colormap[x+1][y][0] - colormap[x-1][y][0];
        //int xg = colormap[x+1][y][1] - colormap[x-1][y][1];
        //int xb = colormap[x+1][y][2] - colormap[x-1][y][2];
        int yr = (colormap[x][y+1]>>16) - (colormap[x][y-1]>>16);
        int yg = ((colormap[x][y+1]>>8)&255) - ((colormap[x][y-1]>>8)&255);
        int yb = (colormap[x][y+1]&255) - (colormap[x][y-1]&255);
        int xr = (colormap[x+1][y]>>16) - (colormap[x-1][y]>>16);
        int xg = ((colormap[x+1][y]>>8)&255) - ((colormap[x-1][y]>>8)&255);
        int xb = (colormap[x+1][y]&255) - (colormap[x-1][y]&255);
        double sum = xr*xr + xb*xb + xg*xg + yr*yr + yg*yg + yb*yb;
        return sum;
    }
    
    public Picture picture() {
        if(horizontal == true) {
            trans(); horizontal = false;
        }
        Picture nP = new Picture(w, h);
        for(int i = 0; i < h; i++) {
            for(int j = 0; j < w; j++) {
                nP.set(j, i, new Color(colormap[i][j]>>16, (colormap[i][j]>>8)&255, colormap[i][j]&255));
            }
        }
        return nP;
    }
    
    public int width() {
        if(horizontal == true) {
            trans(); horizontal = false;
        }
        return w;
    }
    
    public int height() {
        if(horizontal == true) {
            trans(); horizontal = false;
        }
        return h;
    }
    
    public double energy(int x, int y) {
        if(x < 0 || x > w-1 || y < 0 || y > h-1) throw new IndexOutOfBoundsException();
        return energyPixel(y, x);
    }
    private void trans() {
        int n = h;
        int m = w;
        int tmpc;double tmpe;
        int ma = n > m ? n : m;
        for(int i = 0; i < ma; i++) {
            for(int j = 0; j < i; j++) {
                tmpc = colormap[i][j];
                colormap[i][j] = colormap[j][i];
                colormap[j][i] = tmpc;
                //tmpe = energymap[i][j];
                //energymap[i][j] = energymap[j][i];
                //energymap[j][i] = tmpe;
            }
        }
        int tmp = h; h = w; w = tmp;
    }
    
    public int[] findHorizontalSeam() {
        if(horizontal == false) {
            trans(); horizontal = true;
        }
        return findPath();
    }
    
    public int[] findVerticalSeam() {
        if(horizontal == true) {
            trans(); horizontal = false;
        }
        return findPath();
    }
    
    private int[] findPath() {
        int n = h;
        int m = w;
        int stat = 0;
        for(int i = 0; i < m; i++) dp[stat][i] = energyPixel(0, i);
        stat = 1 - stat;
        for(int i = 1; i < n; i++) {
            for(int j = 0; j < m; j++) {
                if(j == 0) {
                    if(dp[1-stat][0] < dp[1-stat][1]) {
                        dp[stat][0] = dp[1-stat][0] + energyPixel(i, j);
                        edgeTo[i][j] = 0;
                    }
                    else {
                        dp[stat][0] = dp[1-stat][1] + energyPixel(i, j);
                        edgeTo[i][j] = 1;
                    }
                }
                else if(j == m-1) {
                    if(dp[1-stat][m-1] < dp[1-stat][m-2]) {
                        dp[stat][m-1] = dp[stat][m-1] + energyPixel(i, j);
                        edgeTo[i][j] = m-1;
                    }
                    else {
                        dp[stat][m-1] = dp[stat][m-2] + energyPixel(i, j);
                        edgeTo[i][j] = m-2;
                    }
                }
                else {
                    double mi = Math.min(dp[1-stat][j-1], dp[1-stat][j]);
                    mi = Math.min(mi, dp[1-stat][j+1]);
                    if(mi == dp[1-stat][j-1]) {
                        dp[stat][j] = dp[1-stat][j-1] + energyPixel(i, j);
                        edgeTo[i][j] = j-1;
                    }
                    else if(mi == dp[1-stat][j]) {
                        dp[stat][j] = dp[1-stat][j] + energyPixel(i, j);
                        edgeTo[i][j] = j;
                    }
                    else {
                        dp[stat][j] = dp[1-stat][j+1] + energyPixel(i, j);
                        edgeTo[i][j] = j+1;
                    }
                }
            }
            stat = 1 - stat;
        }
        double result = dp[1-stat][0];
        int index = 0;
        for(int i = 0; i < m; i++) {
            if(result > dp[1-stat][i]) {
                result = dp[1-stat][i];
                index = i;
            }
        }
        int[] path = new int[n];
        path[n-1] = index;
        for(int i = n - 2; i >= 0; i--) {
            path[i] = edgeTo[i+1][index];
            index = path[i];
        }
        return path;
    }
    
    public void removeHorizontalSeam(int[] a) {
        if(horizontal == false) {
            trans();
            horizontal = true;
        }
        if(w <= 1 || a.length != h) throw new IllegalArgumentException();
        removeSeam(a);
        w--;
        //energyMapCalc();
    }
    
    public void removeVerticalSeam(int[] a) {
        if(horizontal == true) {
            trans();
            horizontal = false;
        }
        if(w <= 1 || a.length != h) throw new IllegalArgumentException();
        removeSeam(a);
        w--;
        //energyMapCalc();
    }
    
    private void removeSeam(int[] a) {
        int n = h;
        int m = w;
        int index = 0;
        for(int i = 0; i < n; i++) {
            index = a[i];
            //System.arraycopy(energymap[i], index+1, energymap[i], index, m-1-index);
            System.arraycopy(colormap[i], index+1, colormap[i], index, m-1-index);
            //energyupdate(i, index);
        }
    }
}