package linkedList;

public class MyList {
    MyNode head;
    public MyList(MyNode dummyHead) {
        head = dummyHead;
    }

    public MyNode findNode(String name) {
        MyNode tmp = head.next;
        while (tmp != null) {
            if (tmp.getKey().equals(name)) {
                return tmp;
            }
            tmp = tmp.next;
        }
        return null;
    }

    public boolean addNewNode(MyNode newUser) {
        if (findNode(newUser.getKey()) != null) {
            return false;
        }

        if (onAddNewNode()) {
            newUser.next = head.next;
            head.next = newUser;
            return true;
        }
        return false;
    }

    public boolean onAddNewNode() {
        return true;
    }

    public boolean removeNode(String name) {
        MyNode prev = head;
        MyNode toDelelte = null;

        while(prev.next != null) {
            toDelelte = prev.next;
            if (toDelelte.getKey().equals(name)) {
                onRemoveNode();
                prev.next = toDelelte.next;
                return true;
            }
            prev = prev.next;
        }
        return false;
    }

    public void onRemoveNode() {}

    public MyIterator makeIterator() {
        MyIterator it = new MyIterator();
        it.current = head.next;
        return it;
    }
}
