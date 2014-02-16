public class HelloWorld {
    public static void main(String[] args) {
        WeightedQuickUnionUF uf = new WeightedQuickUnionUF(100);
        System.out.println(uf.find(2));
        System.out.println("Hello World");
    }
}