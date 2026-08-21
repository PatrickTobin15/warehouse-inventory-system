# Application Theory Questions

## Binary Search Tree

**Why does an inorder traversal of a BST return sorted results? Explain in your own words.**

A BST is built so that everything that is smaller than a node lives within it's left subtree and everything that is bigger lives within its right subtree. Inorder traversal visits the left subtree, then for the node itself, then the right subtree, and it does not recursively at every single node so at any point you've already pretty much visited everything that is smaller before you had visit the current node, and then everything bigger will come right after it. That pattern just holds all the way down the tree so the values there come out in a sorted order that is from smallest to the largest.


**What happens to the tree if you insert values in order (1,2,3,4,5)? How does this affect performance?**

Each of the new values is bigger than the one before it so when every insert goes right and only right the tree will end up as a straight line that is leaning tot he right instead of it being spread out so that basically it is a linked list that is wearing like a tree costume. What that means is operations like insert will find the highest or find the lower which are normally O(log n) on a balanced tree and then they become O(n) instead since you have to walk through every single node one at a time.


**What is the difference between average and worst-case time complexity for a BST?**

On average with values inserted within a reasonably random order a BST will stay roughly balanced and operations such as insert or search run in O(log n) because each comparison will cut the remaining nodes pretty much in half for the most part. In the worst case like the sorted insert example I gave the tree will degenerate into a line and operations will become O(n) since you might have to walk through every single not just to find what you are specifically looking for.


**Where would you place duplicate priority values in your tree? Explain your choice.**

Let's say I choose to send any duplicates to the right along with everything that is greater than the current node in `insertRecursive`the check is `if (priority < current) go left; else go right` so that equal values will fall into the `else` branch. I picked this over the separate "equal" branch because it keeps the insert logic pretty simple with only one comparsion instead of two and it still preserves the sorted property for inorder traversal so that equal priority orders will just end up grouped up next to each other in the output to which is exactly what I want when I am listing orders in a priority order.


## Sorting Algorithm

**Explain how your sorting algorithm works step-by-step using a small example.**

I used insertion sort because say the list is `[89.50, 25.99, 0.10]` prices. Start with an index at (25.99) I can compare it to the items before it like (89.50) so since 89.50 is bigger I shift it one spot to the right and drop the 25.99 into position 0: `[25.99, 89.50, 0.10]`. Which next at the index 2 (0.10) I now compare that to the 89.50 which is obviously bigger so I shift that to the right, now I compare it to the 25.99 which is obviously also bigger so shift that to the right again. Now I have run out of items to compare against and there the 0.10 goes into position 0 leaving me with: `[0.10, 25.99, 89.50]` and now the list is sorted.

**What is the time complexity of your algorithm?**

O(n²) in the most average and worst case since for each of the n elements I might have to shift through most of the already sorted part of the list. Best can which is that the list is already sorted is O(n) because the inner loop never executes and it is really just one single pass through the list.


**When would your sorting algorithm perform well?**

Insertion sort performs well on you know small list and on list that are already sorted or that are nearly sorted because it is one of the fastest and simple sorted for "almost sorted" data since it is only shifts elements that are actually out of place and it is also a stable sort so that records that are being compared to an equal will stay within their original relative order.

**Why is your sorting algorithm ideal or not ideal for very large datasets?**

It is simply just not ideal for very large datasets because of the O(n²) time complexity and the number of comparisons and shifts grow quadratically as the list itself grows so it will get slow fast compared to something such as a merge sort or a quicksort at O(n log n). For a warehouse with a huge product catalog I would want something that is much better with average case performance but for this projects scale insertion sort's simplicity is a reasonable tradeoff which the assignment just specifically asks for one of the O(n²) manual sorts anyway.

## System Design

**Why might you choose to sort data in your application instead of the database?**

Sorting in the application layer is what the assignment specifically ask for here and it is meant to demonstrate that I actually understand how a sorting system works instead of just calling it something like `ORDER BY` and letting MySQL do it. Generally a sorted in app can make more sense when the sort logic is more complex than SQL and handles well when I want to reuse the exact same in memory data that I had already pulled for something else without making a second query or when I need full control over the algorithm itself like this project requires.


**What is one advantage of using a BST in this system?**

Once the orders are in the tree finding the highest or lowest priotiy is fast (O(log n) on a blanced tree) since I just walk right or left to the end instead of having to scan the whole list by order. It just gives me sorted output essentially for free through an inorder traversal without writing a separate sort for orders.

**What is one limitation of your current design?**

The priority tree is an in memory and isn't balance because if orders with priority levels that trend upwards or downwards over time then the tree can lean and it will regrade towards O(n) performance instead of a O(log n) and the same issue I described in the "insert values in order" question above. A self balancing tree like an AVL or like a red black tree would fix that but that is outside of this project and not necessary nor asked for.
