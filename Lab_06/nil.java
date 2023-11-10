//package CSC017_Lab_06;

import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.Optional;

class nil<KT extends Comparable<KT>,VT> implements Node<KT,VT>, DisplayNode, Nonempty {
    public boolean isempty() { return true; }
    public int size() {return 0;}

    //public boolean search(T x) { return false; }
    public Node<KT,VT> set(KT key, VT val){return this;}
    //boolean search(T x); // true if x is in the tree
    public Optional<VT> get(KT key){return Optional.empty();}; // replaces search

    public int depth() { return 0;  }
    public int height() { return 0; }
    public Node<KT, VT> add(KT key, VT val) {
	    if (key == null || val == null) return this;
	    else return new vertex<KT, VT>(key, val,this,this); // reuse nil object
    }
    public void inorder(BiConsumer<KT,VT> c){}
    public void preorder(BiConsumer<KT,VT> c){}
    public void postorder(BiConsumer<KT,VT> c){}
    public Node<KT, VT> remove(KT key) { return this; }

    public <R> R match(Function<nil<KT, VT>,R> nfun,
		       Function<vertex<KT, VT>,R> vfun) {
	    return nfun.apply(this);
    }

    public Node<KT, VT> makeclone(){
        return this;
    }

    @Override
    public DisplayNode left() {
        return null;
    }
    @Override
    public DisplayNode right() {
        return null;
    }
    @Override
    public String get_item() {
        return "";
    }
}//nil class
