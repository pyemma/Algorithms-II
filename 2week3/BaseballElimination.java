import java.util.*;

public class BaseballElimination {
    private HashMap<String, Integer> teamName = new HashMap<String, Integer>();
    private HashMap<Integer, String> teamNum = new HashMap<Integer, String>();
    private int win[], lose[], remain[], match[][];
    private int num;
    private HashSet<String> set;
    private FordFulkerson ff;
    
    public BaseballElimination(String filename) {
        In in = new In(filename);
        num = in.readInt();
        win = new int[num]; lose = new int[num]; remain = new int[num];
        match = new int[num][num];
        for(int i = 0; i < num; i++) {
            String str = in.readString();
            teamName.put(str, i);
            teamNum.put(i, str);
            win[i] = in.readInt();
            lose[i] = in.readInt();
            remain[i] = in.readInt();
            for(int j = 0; j < num; j++) {
                match[i][j] = in.readInt();
            }
        }
    }
    
    public int numberOfTeams() {
        return num;
    }
    
    public Iterable<String> teams() {
        return teamName.keySet();
    }
    
    public int wins(String team) {
        if(teamName.containsKey(team) == false) 
            throw new IllegalArgumentException();
        int index = teamName.get(team);
        return win[index];
    }
    
    public int losses(String team) {
        if(teamName.containsKey(team) == false)
            throw new IllegalArgumentException();
        int index = teamName.get(team);
        return lose[index];
    }
    
    public int remaining(String team) {
        if(teamName.containsKey(team) == false)
            throw new IllegalArgumentException();
        int index = teamName.get(team);
        return remain[index];
    }
    
    public int against(String team1, String team2) {
        if(teamName.containsKey(team1) == false || teamName.containsKey(team2) == false)
            throw new IllegalArgumentException();
        int index1 = teamName.get(team1);
        int index2 = teamName.get(team2);
        return match[index1][index2];
    }
    
    private void exchange(String team) {
        int index = teamName.get(team);
        if(index == num - 1) return;
        int tmp = 0, end = num-1;
        for(int i = 0; i < num; i++) {
            tmp = match[index][i];
            match[index][i] = match[end][i];
            match[end][i] = tmp;
        }
        
        for(int i = 0; i < num; i++) {
            tmp = match[i][index];
            match[i][index] = match[i][end];
            match[i][end] = tmp;
        }
        tmp = win[index]; win[index] = win[end]; win[end] = tmp;
        tmp = lose[index]; lose[index] = lose[end]; lose[end] = tmp;
        tmp = remain[index]; remain[index] = remain[end]; remain[end] = tmp;
        String str = teamNum.get(end);
        teamName.put(str, index); teamName.put(team, end);
        teamNum.put(index, str); teamNum.put(end, team);
    }
    
    private FlowNetwork getNetwork(String team) {
        int games = (num-2) * (num-1) / 2;
        int V = 1 + games + num;
        FlowNetwork network = new FlowNetwork(V);
        int gameCnt = 1;
        for(int i = 0; i < num-1; i++) {
            for(int j = i+1; j < num-1 ; j++) {
                network.addEdge(new FlowEdge(0, gameCnt, match[i][j]));
                network.addEdge(new FlowEdge(gameCnt, games+i+1, Double.POSITIVE_INFINITY));
                network.addEdge(new FlowEdge(gameCnt, games+j+1, Double.POSITIVE_INFINITY));
                gameCnt++;
            }
        }
        for(int i = 0; i < num-1; i++) {
            network.addEdge(new FlowEdge(games+i+1, V-1, win[num-1] + remain[num-1] - win[i]));
        }
        return network;
    }
    
    public boolean isEliminated(String team) {
        if(teamName.containsKey(team) == false) 
            throw new IllegalArgumentException();
        exchange(team);
        set = new HashSet<String>();
        for(int i = 0; i < num-1; i++) {
            if(win[num-1] + remain[num-1] - win[i] < 0) {
                set.add(teamNum.get(i));
                return true;
            }
        }
        FlowNetwork fn = getNetwork(team);
        ff = new FordFulkerson(fn, 0, fn.V()-1);
        int games = (num-2) * (num-1) / 2;
        for(int i = 1; i <= games; i++) {
            if(ff.inCut(i) == true) {
                int v = i;
                int index1 = 0;
                while(v > num - index1 - 2) {
                    v = v - num + index1 + 2;
                    index1++;
                }
                int index2 = index1 + v;
                //StdOut.println(i + " " + index1 + " " + index2);
                set.add(teamNum.get(index1));
                set.add(teamNum.get(index2));
            }
        }
        if(set.size() != 0) return true;
        return false;
    }
    
    public Iterable<String> certificateOfElimination(String team) {
        if(teamName.containsKey(team) == false) 
            throw new IllegalArgumentException();
        if(isEliminated(team) == true) {
            //for(int i = 0; i < num-1; i++) StdOut.println(teamNum.get(i));
            return set;
        }
        return null;
    }
    
    public static void main(String[] args) {
        BaseballElimination division = new BaseballElimination(args[0]);
        for (String team : division.teams()) {
            if (division.isEliminated(team)) {
                StdOut.print(team + " is eliminated by the subset R = { ");
                for (String t : division.certificateOfElimination(team))
                    StdOut.print(t + " ");
                StdOut.println("}");
            }
            else {
                StdOut.println(team + " is not eliminated");
            }
        }
        for (String team : division.teams()) {
            StdOut.print(team + " is eliminated by the subset R = { ");
            for (String t : division.certificateOfElimination(team))
                    StdOut.print(t + " ");
                StdOut.println("}");
        }
    }
}