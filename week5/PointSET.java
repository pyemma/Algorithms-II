public class PointSET {
    
    private SET<Point2D> po;
    private int N;
    
    public PointSET() {
        
        po = new SET<Point2D> ();
        N = 0;
    }
    
    public boolean isEmpty() {
        return N == 0;
    }
    
    public int size() {
        return N;
    }
    
    public void insert(Point2D p) {
        if(!contains(p)) {
            po.add(p);
            N++;
        }
    }
    
    public boolean contains(Point2D p) {
        return po.contains(p);
    }
    
    public void draw() {
        
        for(Point2D point : po) {
            point.draw();
        }
    }
    
    public Iterable<Point2D> range(RectHV rect) {
        Stack<Point2D> st = new Stack<Point2D>();
        for(Point2D point : po) {
            if(rect.contains(point)) {
                st.push(point);
            }
        }
        return st;
    }
    
    public Point2D nearest(Point2D p) {
        double min = 2;
        Point2D tmp = null;
        for(Point2D point : po) {
            if(p.distanceTo(point) < min) {
                tmp = point;
                min = p.distanceTo(point);
            }
        }
        return tmp;
    }
    
}