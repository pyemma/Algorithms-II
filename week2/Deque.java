import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item>{
    
    private int N;
    private Node first;
    private Node last;
    
    private class Node{
        private Item item;
        private Node next;
        private Node prior;
    }
    
    public Deque(){
        first = null;
        last = null;
        N = 0;
    }
    
    public boolean isEmpty(){
        return first == null;
    }
    
    public int size(){
        return N;
    }
    
    public void addFirst(Item item){
        
        if(item == null) 
            throw new NullPointerException("Illegial item");
        Node old = first;
        first = new Node();
        first.item = item;
        first.next = old;
        first.prior = null;
        if(size() == 0)
            last = first;
        else
            old.prior = first;
        N++;
    }
    
    public void addLast(Item item){
        
        if(item == null)
            throw new NullPointerException("Illegial item");
        Node old = last;
        last = new Node();
        last.item = item;
        last.next = null;
        last.prior = old;
        if(isEmpty())
            first = last;
        else
            old.next = last;
        N++;
    }
    
    public Item removeFirst(){
        
        if(isEmpty())
            throw new NoSuchElementException("Queue underflow");
        Item item = first.item;
        first = first.next;
        N--;
        if(isEmpty())
            last = null;
        else
            first.prior = null;
        return item;
    }
    
    public Item removeLast(){
        
        if(isEmpty())
            throw new NoSuchElementException("Queue underflow");
        Item item = last.item;
        last = last.prior;
        N--;
        if(size() == 0)
            first = null;
        else
            last.next = null;
        return item;
    }
    
    public Iterator<Item> iterator(){
        return new ListIterator();
    }
    
    private class ListIterator implements Iterator<Item>{
        
        private Node current = first;
        
        public boolean hasNext() {
            return current != null;
        }
        
        public void remove(){
            throw new UnsupportedOperationException();
        }
        
        public Item next(){
            if (!hasNext()) throw new NoSuchElementException();
            Item item = current.item;
            current = current.next;
            return item;
        }
    }
    
    public static void main(String[] args){
        Deque<String> q= new Deque<String>();
        while(!StdIn.isEmpty()){
            String item = StdIn.readString();
            if(!item.equals("-")) q.addFirst(item);
            //else if(!q.isEmpty()) StdOut.print(q.removeLast() + " ");
        }
        while(!q.isEmpty()) StdOut.print(q.removeFirst() + " ");
        StdOut.println("(" + q.size() + " left on queue)");
        q.addFirst("aaa");
        q.addFirst("bbb");
        StdOut.print(q.removeFirst() + " ");
        StdOut.print(q.removeLast() + " ");
        StdOut.println("(" + q.size() + " left on queue)");
        q.addLast("ccc");
        StdOut.println("(" + q.size() + " left on queue)");
        q.addLast("ddd");
        Iterator<String> iter = q.iterator();
        String item = iter.next();
        StdOut.println(item);
        item = iter.next();
        item = iter.next();
    }
}