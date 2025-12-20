package linkedList;

public class MyIterator {
    protected MyNode current = null;

    public MyNode getCurrent() {
        if (current == null) {
            return null;
        }
        return current.getNode();
    }

    public void moveNext() {
        if (current != null) {
            current = current.next;
        }
    }

}
