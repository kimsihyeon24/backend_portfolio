package linkedList;

abstract public class MyNode {
    protected MyNode next;

    public MyNode getNext() {
        return next;
    }

    abstract public String getKey();
    abstract public void printNode();
    abstract public MyNode getNode();
}
