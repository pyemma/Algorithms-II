public class PercolationStats {
    
    private double[] x;
    private int numTest;
    
    public PercolationStats(int N, int T){
        
        if(N <= 0 || T <= 0)
            throw new IllegalArgumentException("The arguments are illegal");
        numTest = T;
        
        x = new double[T];
        for(int ii = 0;ii < T;ii++){
            
            int count = 0;
            Percolation pcl = new Percolation(N);
            // Repate until the system percolates
            while(!pcl.percolates()){
                // Random pick i and j from uniform distribution
                int i = StdRandom.uniform(1, N+1);
                int j = StdRandom.uniform(1, N+1);
                // If the grid is not open, then open it, and the counter increase
                if(pcl.isOpen(i, j) == false){
                    pcl.open(i, j);
                    count ++;
                }
            }
            
            x[ii] = count / ((double)N*(double)N);
        }
    }
    
    public double mean(){
        // Calculate the mean of the tests
        double sum = 0;
        for(int i = 0;i < numTest; i++){
            sum += x[i];
        }
        return (sum / (double)numTest);
    }
    
    public double stddev(){
        // When numTest == 1, there is no mean for stddev
        if(numTest == 1)
            return Double.NaN;
        // Calculate stddev
        double mu = mean();
        double sum = 0;
        for(int i = 0;i < numTest; i++){
            sum += (x[i] - mu)*(x[i] - mu);
        }
        return Math.sqrt(sum / (double)(numTest - 1));
    }
    
    public double confidenceLo(){
        
        return (mean() - 1.96*stddev()/Math.sqrt(numTest));
    }
    
    public double confidenceHi(){
        
        return (mean() + 1/96*stddev()/Math.sqrt(numTest));
    }
    
    public static void main(String[] args){
        
        int N = Integer.parseInt(args[0]);
        int T = Integer.parseInt(args[1]);
        PercolationStats pcls = new PercolationStats(N, T);
        System.out.println("mean  =  " + pcls.mean());
        System.out.println("stddev  =  " + pcls.stddev());
        System.out.println("95% confidence interval  =  " + pcls.confidenceLo() + ", " + pcls.confidenceHi());
    }
}