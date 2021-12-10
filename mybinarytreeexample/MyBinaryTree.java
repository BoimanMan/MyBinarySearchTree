package mybinarytreeexample;

public class MyBinaryTree<E extends Comparable<E>> {
    //root node default to null
    private Node<E> root = null;

    public class Node<E> {
        //the current node
        public E e = null;
        //left child
        public Node<E> left = null;
        //right child
        public Node<E> right = null;
    }

    public boolean insert(E e) {
        // if empty tree, insert a new node as the root node
        // and assign the element to it
        if (root == null) {
            root = new Node();
            root.e = e;
            return true;
        }

        // otherwise, binary search until a null child pointer 
        // is found
        Node<E> parent = null;
        Node<E> child = root;

        //this loop iterates until a node with no child exists. The child being left or right is determined by the conditionals below.
        while (child != null) {
            //if inserted value less than current node, current node becomes the parent and the value becomes the left child.
            if (e.compareTo(child.e) < 0) {
                parent = child;
                child = child.left;
            //if inserted value greater than current node, current node becomes parent and the value becomes the right child.
            } else if (e.compareTo(child.e) > 0) {
                parent = child;
                child = child.right;
            //otherwise, the current node and the value are equal.
            } else {
                return false;
            }
        }

        // if e < parent.e create a new left child node, link it to 
        // the binary tree and assign the element to it
        if (e.compareTo(parent.e) < 0) {
            parent.left = new Node();
            parent.left.e = e;
        } else {
            parent.right = new Node();
            parent.right.e = e;
        }
        return true;
    }

    //Print methods
    //left, root, right
    public void inorder() {
        System.out.print("inorder:   ");
        inorder(root);
        System.out.println();
    }
    private void inorder(Node<E> current) {
        if (current != null) {
            inorder(current.left);
            System.out.printf("%3s", current.e);
            inorder(current.right);
        }
    }
    //root, left, right
    public void preorder() {
        System.out.print("preorder:  ");
        preorder(root);
        System.out.println();
    }
    private void preorder(Node<E> current) {
        if (current != null) {
            System.out.printf("%3s", current.e);
            preorder(current.left);
            preorder(current.right);
        }
    }

    //left, right, root
    public void postorder() {
        System.out.print("postorder: ");
        postorder(root);
        System.out.println();
    }
    private void postorder(Node<E> current) {
        if (current != null) {
            postorder(current.left);
            postorder(current.right);
            System.out.printf("%3s", current.e);
        }
    }
    
    public boolean delete(E e) {
        
        // binary search until found or not in list
        boolean found = false;
        Node<E> parent = null;
        Node<E> child = root;
        
        while (child != null) {
            if (e.compareTo(child.e) < 0) {
                parent = child;
                child = child.left;
            } else if (e.compareTo(child.e) > 0) {
                parent = child;
                child = child.right;
            //current node and value match
            } else {
                found = true;
                break;
            }
        }        
        
        //Will only run if a match is found, child is the found node
        if (found) {
            // if root only is the only node, set root to null
            if (child == root && root.left == null && root.right == null)
                root = null;
            // if leaf, remove
            else if (child.left == null && child.right == null) {
                //if the node is a left child, remove the parent's left link
                if (parent.left == child)
                    parent.left = null;
                //if the node is a right child, remove the parent's right link
                else 
                    parent.right = null;
            } else
                // if the found node is not a leaf
                // and the found node only has a right child, 
                // connect the parent of the found node (the one 
                // to be deleted) to the right child of the 
                // found node 
                if (child.left == null) {
                    if (parent.left == child)
                        parent.left = child.right;
                    else 
                        parent.right = child.right;
            } else {
                // if the found node has a left child,
                // the node in the left subtree with the largest element 
                // (i. e. the right most node in the left subtree) 
                // takes the place of the node to be deleted
                Node<E> parentLargest = child;
                Node<E> largest = child.left;
                //iterate through the left subtree's right children until the last one is reached.
                while (largest.right != null) {
                    parentLargest = largest;
                    largest = largest.right;
                }
                
                // replace the element in the found node with the element in
                // the right most node of the left subtree
                child.e = largest.e;
                
                // if the parent of the node of the largest element in the 
                // left subtree is the found node, set the left pointer of the
                // found node to point to left child of its left child
                if (parentLargest == child)
                    child.left = largest.left;
                else 
                    // otherwise, set the right child pointer of the parent of 
                    // largest element in the left subtreeto point to the left
                    // subtree of the node of the largest element in the left 
                    // subtree
                    parentLargest.right = largest.left;
            }
            
        } // end if found
        
        return found;
    }
    
    
    // an iterator allows elements to be modified, but can mess with
    // the order if element not written with immutable key; it is better
    // to use delete to remove and delete/insert to remove or replace a
    // node
    public java.util.Iterator<E> iterator() {
        return new PreorderIterator();
    }
    
    private class PreorderIterator implements java.util.Iterator<E> {
        
        private java.util.LinkedList<E> ll = new java.util.LinkedList();
        private java.util.Iterator<E> pit= null;
        
        // create a LinkedList object that uses a linked list of nodes that
        // contain references to the elements of the nodes of the binary tree 
        // in preorder
        public PreorderIterator() {
            buildListInPreorder(root);
            pit = ll.iterator();
        }
        
        //recursively add to linked list in preorder
        private void buildListInPreorder(Node<E> current) {
            if (current != null) {
                ll.add(current.e);
                buildListInPreorder(current.left);
                buildListInPreorder(current.right);
            }
        }
        
        // check to see if their is another node in the LinkedList
        @Override
        public boolean hasNext() {
            return pit.hasNext();
        }

        // reference the next node in the LinkedList and return a 
        // reference to the element in the node of the binary tree
        @Override
        public E next() {
            return pit.next();
        }

        @Override
        public void remove() { 
            throw new UnsupportedOperationException("NO!");
        }
    }
}
