//package CSC017_Lab_06;

import java.util.function.BiConsumer;
import java.util.Optional;

// KT = type of keys, VT = type of values
interface Node<KT extends Comparable<KT>, VT> {
    boolean isempty();
    int size();
    int depth();
    default int height() { return depth(); }  // change this
    Node<KT,VT> set(KT key, VT val); //add or change: t = t.set(key,val)
    //boolean search(T x); // true if x is in the tree
    Optional<VT> get(KT key); // replaces search
    Node<KT,VT> remove(KT key);  // still call as t = t.remove(key);
    void inorder(BiConsumer<KT,VT> c);
    void preorder(BiConsumer<KT,VT> c);
    void postorder(BiConsumer<KT,VT> c);
    Node<KT,VT> makeclone();
}//Node interface for maps