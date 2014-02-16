public class Percolation {
    
    private boolean[][] grids;// Record the status of each grids
    private WeightedQuickUnionUF uf1;// In order to prevent backwash, I utilized 
    private WeightedQuickUnionUF uf2;// two WQUUF, I haven't come up with other idea
    private int gridSize;
    
    public Percolation(int N){
        
        // Initilize a array with size (N+2)*(N+2) in order to easy boundary situation
        // The acutal grids used are from 1~N, 1~N
        gridSize = N;
        grids = new boolean[N+2][N+2];
        for(int ii = 0;ii <= N+1;ii++){
            for(int jj = 0;jj <= N+1;jj++){
                grids[ii][jj] = false;
            }
        }
        
        uf1 = new WeightedQuickUnionUF((N+2)*(N+2));
        uf2 = new WeightedQuickUnionUF((N+2)*(N+2));
    }
    
    public void open(int i, int j){
        
        grids[i][j] = true;
        
        indicesCheck(i, j);
        int index = xyTo1D(i, j);
        
        // Only the open grid could be unioned, pay attention to that in this case we don't 
        // need to worry about the boundary, since they are all closed and could never be opened
        if(isOpen(i-1, j)){
            uf1.union(index, xyTo1D(i-1, j));
            uf2.union(index, xyTo1D(i-1, j));
        }
        if(isOpen(i+1, j)){
            uf1.union(index, xyTo1D(i+1, j));
            uf2.union(index, xyTo1D(i+1, j));
        }
        if(isOpen(i, j-1)){
            uf1.union(index, xyTo1D(i, j-1));
            uf2.union(index, xyTo1D(i, j-1));
        }
        if(isOpen(i, j+1)){
            uf1.union(index, xyTo1D(i, j+1));
            uf2.union(index, xyTo1D(i, j+1));
        }
        if(i == 1){
            uf1.union(index, xyTo1D(0, 0));
            uf2.union(index, xyTo1D(0, 0));
        }
        // Only uf1 will have a virtual bottom supper grid, uf1 is used for test system
        // percolation and uf2 is used for test whether a grid is full or not
        if(i == gridSize)
            uf1.union(index, xyTo1D(gridSize+1, gridSize+1));
        
    }
    
    public boolean isOpen(int i, int j){
        
        return grids[i][j];
    }
    
    public boolean isFull(int i, int j){
        // If the grid is connected to the (0, 0), which stands for the supper pixel
        // this grid is full
        return uf2.connected(xyTo1D(i, j), xyTo1D(0, 0));
    }
    
    public boolean percolates(){
        // If the virtual bottom grid connect to virtual top grid, the system is percolated
        return uf1.connected(xyTo1D(0, 0), xyTo1D(gridSize+1, gridSize+1));
    }
    // Check whether the indices are valid
    private void indicesCheck(int i, int j){
    
        if (i <= 0 || i > gridSize)
            throw new IndexOutOfBoundsException("row index i out of bounds.");
        if (j <= 0 || j > gridSize)
            throw new IndexOutOfBoundsException("column index j out of boudns.");
    }
    // Transform 2D to 1D
    private int xyTo1D(int i, int j){
    
        int index = i*(gridSize+2) + j;
        return index;
    }
}