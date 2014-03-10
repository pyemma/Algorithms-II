public class Solver {
    
    private MinPQ<SearchNode> pq1 = new MinPQ<SearchNode>();
    private MinPQ<SearchNode> pq2 = new MinPQ<SearchNode>();
    private SearchNode goal;
    private Board init;
    
    public Solver(Board initial) {
        init = initial;
        SearchNode init1 = new SearchNode(null, init, 0);
        SearchNode init2 = new SearchNode(null, init.twin(), 0);
        pq1.insert(init1);
        pq2.insert(init2);
        solving();
    }
    
        
    private void solving() {
        while(!pq1.isEmpty() && !pq2.isEmpty()) {
            SearchNode tmp1 = pq1.delMin();
            SearchNode tmp2 = pq2.delMin();
            if(tmp1.getBoard().isGoal()) {
                goal = tmp1;
                break;
            }
            if(tmp2.getBoard().isGoal()) {
                goal = tmp2;
                break;
            }
            for(Board board : tmp1.getBoard().neighbors()) {
                if(tmp1.getParent() == null) { 
                    SearchNode sn = new SearchNode(tmp1, board, tmp1.getMoves() + 1);
                    pq1.insert(sn);
                }
                else {
                    if(!board.equals(tmp1.getParent().getBoard())) {
                        SearchNode sn = new SearchNode(tmp1, board, tmp1.getMoves() + 1);
                        pq1.insert(sn);
                    }
                }
            }
            for(Board board : tmp2.getBoard().neighbors()) {
                if(tmp2.getParent() == null) { 
                    SearchNode sn = new SearchNode(tmp2, board, tmp2.getMoves() + 1);
                    pq2.insert(sn);
                }
                else {
                    if(!board.equals(tmp2.getParent().getBoard())) {
                        SearchNode sn = new SearchNode(tmp2, board, tmp2.getMoves() + 1);
                        pq2.insert(sn);
                    }
                }
            }
        }
    }
    
    public boolean isSolvable() {
        
        SearchNode tmp = goal;
        while(tmp.getParent() != null) {
            tmp = tmp.getParent();
        }
        if(tmp.getBoard().equals(init))
            return true;
        return false;
    }
    
    public int moves() {
        if(isSolvable())
            return goal.getMoves();
        return -1;
        
    }
    
    public Iterable<Board> solution() {
        Stack<Board> bs = new Stack<Board> ();
        if(isSolvable()) {
            SearchNode tmp = goal;
            while(tmp.getParent() != null) {
                bs.push(tmp.getBoard());
                tmp = tmp.getParent();
            }
            bs.push(tmp.getBoard());
        }
        else
            bs = null;
        return bs;
    }
    
    public static void main(String[] args) {
        
        // create initial board from file
    In in = new In(args[0]);
    int N = in.readInt();
    int[][] blocks = new int[N][N];
    for (int i = 0; i < N; i++)
        for (int j = 0; j < N; j++)
            blocks[i][j] = in.readInt();
    Board initial = new Board(blocks);

    // solve the puzzle
    Solver solver = new Solver(initial);

    // print solution to standard output
    if (!solver.isSolvable())
        StdOut.println("No solution possible");
    else {
        StdOut.println("Minimum number of moves = " + solver.moves());
        for (Board board : solver.solution())
            StdOut.println(board);
    }
        
    }
    
    private class SearchNode implements Comparable<SearchNode> {
        private SearchNode parent;
        private Board bo;
        private int moves;
        private int priority;
        
        public SearchNode(SearchNode parent, Board board, int moves) {
            this.parent = parent;
            this.bo = board;
            this.moves = moves;
            this.priority = this.moves + this.bo.manhattan();
        }
        
        public int compareTo(SearchNode that) {
            
            if(this.priority > that.priority) return 1;
            if(this.priority < that.priority) return -1;
            return 0;
        }
        
        public SearchNode getParent() {
            return parent;
        }
        
        public Board getBoard() {
            return bo;
        }
        
        public int getMoves() {
            return moves;
        }
        
        public int getPri() {
            return priority;
        }
    }
}