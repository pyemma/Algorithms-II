import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item>{
    
    private Item[] q;
    private int N = 0;
    private int first = 0;
    private int last = 0;
    private boolean shuffled = false; // This contorls whether shuffle or not
    
    
    public RandomizedQueue(){
        q = (Item[]) new Object[2];
    }
    
    public boolean isEmpty(){
        return N == 0;
    }
    
    public int size(){
        return N;
    }
    
    private void resize(int max){
        
        Item[] temp = (Item[]) new Object[max];
        for(int i = 0; i < N; i++){
            temp[i] = q[(first + i) % q.length];
        }
        q = temp;
        first = 0;
        last = N;
    }
    
    public void enqueue(Item item){
        
        if(item == null) throw new NullPointerException();
        
        if(N == q.length) resize(2*q.length);
        if(last == q.length) last = 0;
        q[last++] = item;
        if(last == q.length) last = 0;
        N++;
        
        if(shuffled == true)
            shuffled = false;
    }
    
    public Item dequeue(){
        
        if(isEmpty()) throw new NoSuchElementException("Queue underflow");
        
        int i = StdRandom.uniform(N);
        Item temp = q[(first+i) % q.length];
        q[(first+i) % q.length] = q[first];
        q[first] = temp;
        
        Item item = q[first];
        q[first] = null;
        N--;
        first++;
        if(first == q.length) first = 0;
        if(N > 0 && N == q.length/4) resize(q.length/2);
        return item;
    }
    
    public Item sample(){
        if(isEmpty()) throw new NoSuchElementException("Queue underflow");
        
        int i = StdRandom.uniform(N);
        
        Item item = q[(first+i) % q.length];
        return item;
    }
    
    public Iterator<Item> iterator(){
        return new ArrayIterator();
    }
    
    private class ArrayIterator implements Iterator<Item>{
        
        private int i = 0;
        private Item[] temp;
        
        public ArrayIterator(){
            temp = (Item[]) new Object[N];
            for(int i = 0; i < N; i++){
                temp[i] = q[(first + i) % q.length];
            }
            StdRandom.shuffle(temp, 0, N-1);
        }
        
        public boolean hasNext() { return i < N; }
        public void remove() { throw new UnsupportedOperationException();}
        
        public Item next(){
            if(!hasNext()) throw new NoSuchElementException();
            Item item = temp[i % temp.length];
            i++;
            return item;
        }
    }
    
    public static void main(String[] args){
        RandomizedQueue<String> q = new RandomizedQueue<String>();
        q.enqueue("1");
        q.enqueue("2");
        q.dequeue();
        q.enqueue("3");
        q.enqueue("4");
        q.enqueue("5");
        q.enqueue("6");
        Iterator<String> iter1 = q.iterator();
        Iterator<String> iter2 = q.iterator();
        while(iter1.hasNext())
            StdOut.print(iter1.next() + " ");
        while(iter2.hasNext())
            StdOut.print(iter2.next() + " ");
    }
}