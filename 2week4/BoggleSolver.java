import java.util.*;
public class BoggleSolver {

 private HashSet<String> dict;
 private TrieST<Integer> tries;
 private boolean mark[][];
 private int M, N;
 private BoggleBoard board;
 private HashSet<String> result;
 public BoggleSolver(String[] dictionary) {
  dict = new HashSet<String>();
  tries = new TrieST<Integer>();
  for(int i = 0; i < dictionary.length; i++) {
   dict.add(dictionary[i]);
   //for(int j = 0; j <= dictionary[i].length(); j++)
    tries.put(dictionary[i], i);
  }
 }

 public Iterable<String> getAllValidWords(BoggleBoard board) {
  M = board.rows();
  N = board.cols();
  this.board = board;
  mark = new boolean[M][N];
  result = new HashSet<String>();
  for(int i = 0; i < M; i++) {
   for(int j = 0; j < N; j++) {
    mark[i][j] = false;
   }
  }
  for(int i = 0; i < M; i++) {
   for(int j = 0; j < N; j++) {
    dfs(new StringBuilder(), i, j);
   }
  }
  return result;
 }

 public int scoreOf(String word) {
     if(dict.contains(word) == false) return 0;
  int length = word.length();
  if(length >= 0 && length <= 2) return 0;
  else if(length >= 3 && length <= 4) return 1;
  else if(length == 5) return 2;
  else if(length == 6) return 3;
  else if(length == 7) return 5;
  else return 11; 
 }

 private void dfs(StringBuilder prefix, int x, int y) {
  char s = board.getLetter(x, y);
  if(s == 'Q') prefix.append("QU");
  else prefix.append(s);
  //StdOut.println(prefix.toString());
  if(tries.contains(prefix.toString()) == true) {
   if(dict.contains(prefix.toString()) == true && result.contains(prefix.toString()) == false && prefix.toString().length() >= 3)
    result.add(prefix.toString());
   mark[x][y] = true;
   int left = Math.max(0, y-1);
   int right = Math.min(N-1, y+1);
   int up = Math.max(0, x-1);
   int down = Math.min(M-1, x+1);
   for(int i = up; i <= down; i++) {
    for(int j = left; j <= right; j++) {
     if(mark[i][j] == false) dfs(prefix, i, j);
    }
   }
   mark[x][y] = false;
  }
  if(s == 'Q') {
    prefix.deleteCharAt(prefix.length() - 1);
    prefix.deleteCharAt(prefix.length() - 1);
   }
  else prefix.deleteCharAt(prefix.length() - 1);
 }

 public static void main(String[] args) {
    In in = new In(args[0]);
    String[] dictionary = in.readAllStrings();
    BoggleSolver solver = new BoggleSolver(dictionary);
    BoggleBoard board = new BoggleBoard(args[1]);
    int score = 0;
    for (String word : solver.getAllValidWords(board))
    {
        StdOut.println(word);
        score += solver.scoreOf(word);
    }
    StdOut.println("Score = " + score);
}
}