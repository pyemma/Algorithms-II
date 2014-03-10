public class Board {
    
    private int[][] board;
    private int size;
    private int zerox, zeroy;
    
    public Board(int[][] blocks) {
        size = blocks.length;
        board = new int[size][size];
        for(int i = 0; i < size; i++)
            for(int j = 0; j < size; j++) {
            board[i][j] = blocks[i][j];
        }           
    }
    
    public int dimension() {
        return size;
    }
    
    public int hamming() {
        int ham = 0;
        for(int i = 0; i < size; i++)
            for(int j = 0; j < size; j++) {
            if(board[i][j] != 0 && (i*size + j + 1) != board[i][j]) {
                ham += 1;
            }
        }
        return ham;   
    }
    
    public int manhattan() {
        int man = 0;
        for(int i = 0; i < size; i++)
            for(int j = 0; j < size; j++) {
            if(board[i][j] != 0) {
                if((board[i][j] - 1) / size - i > 0)
                man = man + (board[i][j] - 1) / size - i;
                else
                man = man + i - (board[i][j] - 1) / size;
            if((board[i][j] - 1) % size - j > 0)
                man = man + (board[i][j] - 1) % size - j;
            else
                man = man + j - (board[i][j] - 1) % size;
        }
                
        }
        return man;
    }
    
    public boolean isGoal() {
        if(hamming() != 0)
            return false;
        else
            return true;
    }
    
    public Board twin() {
        if(board[0][0] !=0 && board[0][1] != 0)
            return exch(0, 0, 0, 1);
        else
            return exch(1, 0, 1, 1);
    }
    
    public boolean equals(Object y) {
        if (y == this) return true;
        if (y == null) return false;
        if (y.getClass() != this.getClass()) return false;
        Board that = (Board) y;
        if (this.size != that.size) return false;
        for(int i = 0; i < size; i++)
            for(int j = 0; j < size; j++)
            if(this.board[i][j] != that.board[i][j])
            return false;
        return true;
    }
    
    private Board exch(int x1, int y1, int x2, int y2) {
        /*int[][] blocks = new int[size][size];
        for(int i = 0; i < size; i++)
            for(int j = 0; j < size; j++)
            blocks[i][j] = board[i][j];
         int tmp = blocks[x1][y1];
         blocks[x1][y1] = blocks[x2][y2];
         blocks[x2][y2] = tmp;
         return new Board(blocks);*/
        Board bo = new Board(board);
        bo.exch_f(x1, y1, x2, y2);
        return bo;
    }
    
    private void exch_f(int x1, int y1, int x2, int y2) {
        int tmp = board[x1][y1];
        board[x1][y1] = board[x2][y2];
        board[x2][y2] = tmp;
        
    }
    
    private void findzero() {
        for(int i = 0; i < size; i++)
            for(int j = 0; j < size; j++)
            if(board[i][j] == 0) {
            zerox = i; zeroy = j;
        }
    }
    
    public Iterable<Board> neighbors() {
        Stack<Board> bs = new Stack<Board>();
        findzero();
        int i = zerox; int j = zeroy;
        if(i == 0) {
            if(j == 0) {
                Board nb = exch(i, j, i, j+1);
                bs.push(nb);
                nb = exch(i, j, i+1, j);
                bs.push(nb);
            }
            else if(j == size-1) {
                Board nb = exch(i, j, i, j-1);
                bs.push(nb);
                nb = exch(i, j, i+1, j);
                bs.push(nb);
            }
            else {
                Board nb = exch(i, j, i, j-1);
                bs.push(nb);
                nb = exch(i, j, i+1, j);
                bs.push(nb);
                nb = exch(i, j, i, j+1);
                bs.push(nb);
            }
        }
        else if(i == size-1) {
            if(j == 0) {
                Board nb = exch(i, j, i, j+1);
                bs.push(nb);
                nb = exch(i, j, i-1, j);
                bs.push(nb);
            }
            else if(j == size-1) {
                Board nb = exch(i, j, i, j-1);
                bs.push(nb);
                nb = exch(i, j, i-1, j);
                bs.push(nb);
            }
            else {
                Board nb = exch(i, j, i, j-1);
                bs.push(nb);
                nb = exch(i, j, i-1, j);
                bs.push(nb);
                nb = exch(i, j, i, j+1);
                bs.push(nb);
            }
        }
        else {
            if(j == 0) {
                Board nb = exch(i, j, i, j+1);
                bs.push(nb);
                nb = exch(i, j, i-1, j);
                bs.push(nb);
                nb = exch(i, j, i+1, j);
                bs.push(nb);
            }
            else if(j == size-1) {
                Board nb = exch(i, j, i, j-1);
                bs.push(nb);
                nb = exch(i, j, i-1, j);
                bs.push(nb);
                nb = exch(i, j, i+1, j);
                bs.push(nb);
            }
            else {
                Board nb = exch(i, j, i, j-1);
                bs.push(nb);
                nb = exch(i, j, i-1, j);
                bs.push(nb);
                nb = exch(i, j, i, j+1);
                bs.push(nb);
                nb = exch(i, j, i+1, j);
                bs.push(nb);
            }
        }
        return bs;
    }
    
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(size + "\n");
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                s.append(String.format("%2d ", board[i][j]));
            }
            s.append("\n");
        }
        return s.toString();
    }
    
}
    
    