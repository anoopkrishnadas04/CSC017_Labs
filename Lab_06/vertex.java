//package CSC017_Lab_06;

import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.Optional;

class vertex<KT extends Comparable<KT>,VT> implements Node<KT,VT>, DisplayNode, Nonempty {
    KT key;
    VT val; //value contained in this node.
    Node<KT, VT> left;
    Node<KT, VT> right;
    int height;

    public vertex(KT k, VT v, Node<KT, VT> l, Node<KT, VT> r) {
        key = k; val = v; left = l; right = r; setheight();
    }//constructor

    // impl Nonempty interface for graphical display
    public DisplayNode left() { return (DisplayNode) left; }
    public DisplayNode right() { return (DisplayNode) right; }
    public String get_item() { return key+""; } // convert val to string

    public int height() { return height; }
    // setheight runs in O(1) time, returns height difference
    int setheight() {
        int lheight = left.height();
        int rheight = right.height();
        height = Math.max(lheight,rheight)+1;
        return lheight - rheight;
    }//setheight

    ////////////////////////////////////  CRITICAL METHOD
    void adjust() {  // override or modify this to balance tree
        int c = setheight();
        
        
        if(c < -1){ //right tree longer
            int c2 = ((vertex<KT, VT>) right).setheight();
            if(c2 > 0){ //RL
                ((vertex<KT, VT>) right).LL();
                this.RR();
            } else { //RR
                this.RR();
            }
        }
        if(c > 1){ //left tree longer
            int c2 = ((vertex<KT, VT>) left).setheight();
            if(c2 < 0){ //LR
                ((vertex<KT, VT>) left).RR();
                this.LL();
            } else { //LL
                this.LL();
            }
        }
    }
    ////////////////////////////////////

    public boolean isempty() { return false; }
    
    public void inorder(BiConsumer<KT, VT> c) {  // inorder traversal;
        left.inorder(c);
        c.accept(key, val);
        //System.out.print(val+ " ");
        right.inorder(c);
    }
        
    public void preorder(BiConsumer<KT, VT> c) {
        c.accept(key, val); //System.out.print(val+ " ");
        left.preorder(c);
        right.preorder(c);	
    }
        
    public void postorder(BiConsumer<KT, VT> c) {
        right.postorder(c);
        left.postorder(c);
        c.accept(key, val);
    }

    public Node<KT, VT> add(KT k, VT v) {
        if(k == null || v == null) return this;
        int ckey = k.compareTo(key);
        if (ckey < 0) left = ((vertex) left).add(k, v);
        else if (ckey > 0) right = ((vertex) right).add(k, v);
        // if c==0, do nothing (duplicates ignored)
            adjust();
        return this;
    }

    public int size() { return left.size()+right.size()+1; }

    public int depth() {
	    return Math.max(left.depth(), right.depth()) + 1;
    }

    public boolean search(KT k, VT v) {
        if (k == null || v == null) return false;
        int c = k.compareTo(key);
        /*
        if (c==0) return true;
        else if (c<0) return left.search(x);
        else return right.search(x);
        */
        return c==0 || (c<0 && ((vertex) left).search(k, v)) || (c>0 && ((vertex) right).search(k, v));
    }//search

    // auxiliary procedure called from remove, will be passed node to modify
    // once the largest value is found
    Node<KT, VT> delmax(vertex<KT, VT> modnode) {
        if (this.right.isempty()) {  // found largest node
            modnode.val = this.val; //move largest value to node being "removed"
            return left; // there may still be a left subtree beneath vertex
        }
        else right = ((vertex<KT, VT>)right).delmax(modnode);
            adjust();
        return this;
    }//

    // remove must similarly return a node
    public Node<KT, VT> remove(KT k) { // find and remove x from tree-set
        if (k == null) return this;
        // find it first:
        int c = k.compareTo(key);
        if (c<0) left = ((vertex) left).remove(k);
        else if (c>0) right = ((vertex) right).remove(k);
        else { // c==0, found it
            if (left.isempty()) return right; // move right subtree up
            else { // left subtree exists, take max value on left
            left = ((vertex<KT, VT>)left).delmax(this);
            }//left subtree exists
        }// found node to remove
            adjust();
        return this;
    }// remove

    public <R> R match(Function<nil<KT, VT>,R> nfun,
		       Function<vertex<KT, VT>,R> vfun) {
	    return vfun.apply(this);
    }

    public Node<KT, VT> makeclone(){
        return new vertex(this.key, this.val, this.left.makeclone(), this.right.makeclone());
    } //makeclone implementation

    @Override
    public Node<KT, VT> set(KT key, VT val) {
        this.key = key;
        this.val = val;
        adjust();
        return this;
    }

    @Override
    public Optional<VT> get(KT key) {
        int c = this.key.compareTo(key);
        if(c < 0) return right.get(key);
        if(c > 0) return left.get(key);
        return Optional.of(val);
        
    }

    public void LL(){
        vertex<KT, VT> lvert = (vertex<KT, VT>) left;
        Node<KT, VT> lr = lvert.right;
        lvert.right = this;
        this.left = lr;
    }

    public void RR(){
        vertex<KT, VT> rvert = (vertex<KT, VT>) right;
        Node<KT, VT> rl = rvert.left;
        rvert.left = this;
        this.right = rl;
    }
}// vertex class