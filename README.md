Algorithms-Princeton-Java
=========================

This contains my programming exercises of the course Algorithm Part I on coursera

My Week1's programming exercises got about 96. The main reason is the sytle, which I forgot to check.
Besides, I used a stupid way to prevent backwash, which utilized two WQUUF, one contain the virtual 
bottom grid and one does not contain. So the memory cost is quite large. I haven't come up with a better
way. I hope my code could give you some inspiration on the exercises.

My Week2's programming exercises got 100. In order to implement the function to remove at first and add at last
in a queue, I choose linked list, which is much more efficient in terms of removing items than array. In order to implment the RandomizedQueue, I choose resizingarray, due to its high performance in indexing. When dequeue an item, I randomly pick an item in the queue and change it with the first item. In this way, we can maintain the queue as well as reuse much of the original code.
