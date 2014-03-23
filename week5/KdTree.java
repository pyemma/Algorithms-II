public class KdTree {
    
    private Node root = null;
    private int N;
    private Stack<Point2D> st;
    private RectHV plane = new RectHV(0.0, 0.0, 1.0, 1.0);
    
    private static class Node {
        
        private Point2D p;
        private RectHV rect;
        private Node lb;
        private Node rt;
        private RectHV lbrect, rtrect;
        
        public Node(Point2D p, RectHV rect) {
            this.p = p;
            this.rect = rect;
        }
    }
    
    public KdTree() {
        root = null;
        N = 0;
    }
    
    public boolean isEmpty() {
        return size() == 0;
    }
    
    public int size() {
        return N;
    }
    
    public void insert(Point2D p) {
        
        if(!contains(p)) {
        boolean orient = true; // ture means vertical and false means horizontal
        root = put(root, p, plane, orient);
        N++;
        }
    }
    
    /*private Node put(Node node, Point2D p, RectHV rect, boolean orient) {
        
        if(node == null) {
            node = new Node(p, rect);
            return node;
        }
        
        int cmp = compare(p, node.p, orient);
        if(cmp < 0) {
            if(orient == true) {
                double xmin = node.rect.xmin();
                double xmax = node.p.x();
                double ymin = node.rect.ymin();
                double ymax = node.rect.ymax();
                node.lb = put(node.lb, p, new RectHV(xmin, ymin, xmax, ymax), !orient);
            }
            else {
                double xmin = node.rect.xmin();
                double xmax = node.rect.xmax();
                double ymin = node.rect.ymin();
                double ymax = node.p.y();
                node.lb = put(node.lb, p, new RectHV(xmin, ymin, xmax, ymax), !orient);
            }
        }
        else if(cmp >= 0) {
            if(orient == true) {
                double xmin = node.p.x();
                double xmax = node.rect.xmax();
                double ymin = node.rect.ymin();
                double ymax = node.rect.ymax();
                node.rt = put(node.rt, p, new RectHV(xmin, ymin, xmax, ymax), !orient);
            }
            else {
                double xmin = node.rect.xmin();
                double xmax = node.rect.xmax();
                double ymin = node.p.y();
                double ymax = node.rect.ymax();
                node.rt = put(node.rt, p, new RectHV(xmin, ymin, xmax, ymax), !orient);
            }
        }
        return node;
    }*/
    
    private Node put(Node node, Point2D p, RectHV rect, boolean orient) {
        if(node == null) {
            node = new Node(p, rect);
            double xmin = rect.xmin();
            double xmax = rect.xmax();
            double ymin = rect.ymin();
            double ymax = rect.ymax(); 
            if(orient == true) {
                node.lbrect = new RectHV(xmin, ymin, p.x(), ymax);
                node.rtrect = new RectHV(p.x(), ymin, xmax, ymax);
            }
            else {
                node.lbrect = new RectHV(xmin, ymin, xmax, p.y());
                node.rtrect = new RectHV(xmin, p.y(), xmax, ymax);
            }
            return node;
        }
        
        int cmp = compare(p, node.p, orient);
        if(cmp < 0) {
                node.lb = put(node.lb, p, node.lbrect, !orient);
        }
        else if(cmp >= 0) {
                node.rt = put(node.rt, p, node.rtrect, !orient);
        }
        return node;
    }
    
    private int compare(Point2D p1, Point2D p2, boolean orient) {
        if(orient == true) {
            if(p1.x() < p2.x()) return -1;
            else return 1;
        }
        else {
            if(p1.y() < p2.y()) return -1;
            else return 1;
        }
    }
    
    public boolean contains(Point2D p) {
        return contains(root, p, true);
    }
    
    private boolean contains(Node node, Point2D p, boolean orient) {
        if(node == null) return false;
        if(p.equals(node.p)) return true;
        int cmp = compare(p, node.p, orient);
        if(cmp < 0) return contains(node.lb, p, !orient);
        else return contains(node.rt, p, !orient);
    }
    
    public void draw() {
        draw(root, true);
    }
    
    private void draw(Node node, boolean orient) {
        if(node != null) {
            draw(node.lb, !orient);
            draw(node.rt, !orient);
            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.setPenRadius(.01);
            node.p.draw();
            StdDraw.setPenRadius();
            if(orient == true) {
                StdDraw.setPenColor(StdDraw.RED);
                StdDraw.line(node.p.x(), node.rect.ymin(), node.p.x(), node.rect.ymax());
            }
            else {
                StdDraw.setPenColor(StdDraw.BLUE);
                StdDraw.line(node.rect.xmin(), node.p.y(), node.rect.xmax(), node.p.y());
            }
            
        }
    }
    
    public Iterable<Point2D> range(RectHV rect) {
        st = new Stack<Point2D>();
        range(root, rect);
        return st;
    }
    
    private void range(Node node, RectHV rect) {
        if(node == null)
            return ;
        if(rect.contains(node.p))
            st.push(node.p);
        if(rect.intersects(node.rect)) {
            range(node.lb, rect);
            range(node.rt, rect);
        }
    }
    
    private Point2D ne;
    private double min;
    
    public Point2D nearest(Point2D p) {
        ne = null;
        min = 999;
        nearest(root, p, true);
        return ne;
    }
    
    private void nearest(Node node, Point2D p, boolean orient) {
        if(node == null)
            return ;
        if(node.rect.distanceSquaredTo(p) < min) {
            double tmp = p.distanceSquaredTo(node.p);
            if(tmp < min) {
                min = tmp;
                ne = node.p;
            }
            int cmp = compare(p, node.p, orient);
            if(cmp < 0) {
                nearest(node.lb, p, !orient);
                nearest(node.rt, p, !orient);
            }
            else {
                
                nearest(node.rt, p, !orient);
                nearest(node.lb, p, !orient);
            }
        }
    }
    
}